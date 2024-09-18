package ru.practicum.shareit.comments.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.comments.Comments;
import ru.practicum.shareit.comments.CommentsDto;

@Component
public class CommentMapperImpl {

    public static CommentsDto parseCommentInCommentDto(Comments comment) {
        return new CommentsDto(comment.getId(), comment.getText(), comment.getItem(), comment.getAuthor().getName(), comment.getCreated());
    }

    public static Comments parseCommentDtoInComment(CommentsDto commentDto) {
        Comments comment = new Comments();

        comment.setId(commentDto.getId());
        comment.setText(commentDto.getText());
        comment.setItem(commentDto.getItem());
        comment.setCreated(commentDto.getCreated());

        return comment;
    }
}