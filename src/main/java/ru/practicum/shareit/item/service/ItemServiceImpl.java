package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.comments.Comments;
import ru.practicum.shareit.comments.CommentsDto;
import ru.practicum.shareit.comments.ItemAndCommentDto;
import ru.practicum.shareit.comments.mapper.CommentMapperImpl;
import ru.practicum.shareit.comments.mapper.ItemAndCommentDtoMapperImpl;
import ru.practicum.shareit.comments.repository.CommentRepository;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Override
    public Item createItem(Item item, Long ownerId) {
        validationItem(item);
        userService.isUserExist(ownerId).orElseThrow(() -> new NotFoundException("нету такого user"));
        log.info("Добавление новой вещи");
        item.setOwner(ownerId);
        return itemRepository.save(item);
    }

    public CommentsDto addNewComment(Long itemId, CommentsDto commentDto, Long userId) {
        Booking optionalBooking = bookingRepository.findAll().stream()
                .filter(elem -> Objects.equals(elem.getItem().getId(), itemId))
                .findFirst().orElseThrow(() -> new ValidationException("Бронирование не возможно"));

        User user = userService.isUserExist(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        commentDto.setAuthorName(user.getName());
        commentDto.setItem(optionalBooking.getItem());
        commentDto.setCreated(LocalDateTime.now());

        if (optionalBooking.getEnd().isBefore(commentDto.getCreated())) {
            Comments comment = CommentMapperImpl.parseCommentDtoInComment(commentDto);
            comment.setAuthor(user);
            commentRepository.save(comment);
            return commentDto;
        }

        throw new ValidationException("Cannot add comment before the end of the rental period");
    }

    @Override
    public Item updateItem(Long itemId, Item item, Long ownerId) {
        userService.isUserExist(ownerId).orElseThrow(() -> new NotFoundException("нету такого user"));

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
    public List<Item> fetchItemsByOwnerId(Long ownerId) {
        userService.isUserExist(ownerId);
        List<Item> items = itemRepository.findItemsByOwnerId(ownerId);
        if (items == null || items.isEmpty()) {
            log.warn("No items found for ownerId: {}", ownerId);
        }
        return items;
    }

    @Override
    public ItemAndCommentDto fetchItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));

        ItemAndCommentDto itemAndCommentDto = ItemAndCommentDtoMapperImpl.parseItemInItemAndCommentDto(item);

        List<CommentsDto> comments = commentRepository.findAll().stream()
                .filter(comment -> Objects.equals(comment.getItem().getId(), id))
                .map(comment -> new CommentsDto(comment.getId(), comment.getText(), comment.getCreated()))
                .collect(Collectors.toList());

        itemAndCommentDto.setComments(comments);
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

    private void validationItem(Item item) {
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