package ru.practicum.shareit.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.comments.Comments;

public interface CommentRepository extends JpaRepository<Comments, Long> {
}
