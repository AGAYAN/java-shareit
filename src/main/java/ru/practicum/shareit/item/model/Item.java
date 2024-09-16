package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"id"})
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Boolean available;

    @Column(name = "owner_id")
    private Long owner;

    @Column(name = "request_id")
    private Long request;
}
