package ru.practicum.shareit.comments.mapper;

import ru.practicum.shareit.comments.Comments;
import ru.practicum.shareit.comments.CommentsDto;

public interface CommentMapper {
    CommentsDto parseCommentInCommentDto(Comments comment);

    Comments parseCommentDtoInComment(CommentsDto commentDto);
}
