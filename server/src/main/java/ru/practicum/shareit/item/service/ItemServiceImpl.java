package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.comments.Comments;
import ru.practicum.shareit.comments.CommentsDto;
import ru.practicum.shareit.comments.ItemAndCommentDto;
import ru.practicum.shareit.comments.mapper.CommentMapper;
import ru.practicum.shareit.comments.mapper.ItemAndCommentDtoMapper;
import ru.practicum.shareit.comments.repository.CommentRepository;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;
    private final ItemRequestService itemRequestService;

    @Override
    public ItemDto createItem(ItemDto item, Long ownerId) {
        validationItem(item);
        userService.verifyUserId(ownerId);
        log.info("Добавление новой вещи");
        Item item1 = ItemMapper.parseItem(item);
        item1.setOwner(ownerId);
        if (item.getRequestId() != null) {
            item1.setRequest(itemRequestService.validate(item.getRequestId()));
        }

        itemRepository.save(item1);

        return ItemMapper.parseItemNoDto(item1);
    }

    public CommentsDto addNewComment(Long itemId, CommentsDto commentDto, Long userId) {
        Booking optionalBooking = bookingRepository.findAll().stream()
                .filter(elem -> Objects.equals(elem.getItem().getId(), itemId))
                .findFirst().orElseThrow(() -> new ValidationException("Бронирование не возможно"));

        User user = userService.getUserById(userId);

        commentDto.setAuthorName(user.getName());
        commentDto.setItem(optionalBooking.getItem());
        commentDto.setCreated(LocalDateTime.now());

        if (optionalBooking.getEnd().isBefore(commentDto.getCreated())) {
            Comments comment = CommentMapper.parseCommentDtoInComment(commentDto);
            comment.setAuthor(user);
            commentRepository.save(comment);
            return commentDto;
        }

        throw new ValidationException("Cannot add comment before the end of the rental period");
    }

    @Override
    public Item updateItem(Long itemId, Item item, Long ownerId) {
        userService.verifyUserId(ownerId);

        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));

        if (item.getName() != null && !item.getName().isBlank()) {
            existingItem.setName(item.getName());
        }

        if (item.getDescription() != null && !item.getDescription().isBlank()) {
            existingItem.setDescription(item.getDescription());
        }

        if (item.getAvailable() != null) {
            existingItem.setAvailable(item.getAvailable());
        }

        log.info("Обновление вещи с id = {}", itemId);
        return itemRepository.save(existingItem);
    }

    @Override
    public List<ItemAndCommentDto> fetchItemsByOwnerId(Long ownerId) {
        userService.verifyUserId(ownerId);

        List<Item> items = itemRepository.findItemsByOwnerId(ownerId);

        if (items == null || items.isEmpty()) {
            log.warn("No items found for ownerId: {}", ownerId);
            return Collections.emptyList();
        }

        Map<Long, List<Booking>> bookingsMap = bookingRepository.findBookingsByItemId(ownerId)
                .stream()
                .collect(Collectors.groupingBy(booking -> booking.getItem().getId()));

        Map<Long, List<Comments>> commentsMap = commentRepository.findCommentsByItemId(ownerId)
                .stream()
                .collect(Collectors.groupingBy(comment -> comment.getItem().getId()));

        List<ItemAndCommentDto> itemList = new ArrayList<>();

        for (Item item : items) {
            List<Booking> bookings = bookingsMap.getOrDefault(item.getId(), Collections.emptyList());
            List<Comments> comments = commentsMap.getOrDefault(item.getId(), Collections.emptyList());

            ItemAndCommentDto itemAndCommentDto = ItemMapper.toItemDetails(item, bookings, comments);

            findLastAndNextBooking(bookings, itemAndCommentDto);

            itemList.add(itemAndCommentDto);
        }

        return itemList;
    }

    @Override
    public ItemAndCommentDto fetchItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));

        ItemAndCommentDto itemAndCommentDto = ItemAndCommentDtoMapper.parseItemInItemAndCommentDto(item);

        List<CommentsDto> comments = commentRepository.findByItemId(id).stream()
                .map(comment -> new CommentsDto(comment.getId(), comment.getText(), comment.getItem(), comment.getCreated()))
                .collect(Collectors.toList());

        itemAndCommentDto.setComments(comments);

        List<Booking> bookings = bookingRepository.findBookingsByItemId(id);

        findLastAndNextBooking(bookings, itemAndCommentDto);

        return itemAndCommentDto;
    }

    @Override
    public List<Item> search(String text, Long ownerId) {
        if (text == null || text.isEmpty() || text.isBlank()) {
            return List.of();
        }
        return itemRepository.search(text, ownerId).stream()
                .filter(elem -> Objects.equals(elem.getAvailable(), true))
                .toList();
    }

    private void findLastAndNextBooking(List<Booking> bookings, ItemAndCommentDto itemAndCommentDto) {
        LocalDateTime lastBooking = null;
        LocalDateTime nextBooking = null;

        for (Booking booking : bookings) {
            if (booking.getEnd().isBefore(LocalDateTime.now())) {
                if (booking.getEnd() == null) {
                    lastBooking = booking.getEnd();
                }
            } else {
                if (booking.getStart() == null) {
                    lastBooking = booking.getStart();
                }
            }
        }

        itemAndCommentDto.setLastBooking(lastBooking);
        itemAndCommentDto.setNextBooking(nextBooking);
    }

    private void validationItem(ItemDto item) {
        String starMessage = "Ошибка валидации: ";

        if (item.getName() == null || item.getName().isBlank()) {
            log.error("Название не может быть пустым");
            throw new ValidationException(starMessage + "Название не может быть пустым");
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            log.error("Описание предмета не может быть пустым");
            throw new ValidationException(starMessage + "Описание предмета не может быть пустым");
        }
        if (item.getAvailable() == null) {
            log.error("Статус доступности должен быть указан");
            throw new ValidationException(starMessage + "Статус доступности должен быть указан");
        }
    }

}