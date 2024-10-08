package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.request.id = :requestId AND i.request IS NOT NULL")
    List<Item> getItemByRequestId(@Param("requestId") Long requestId);

    @Query(value = "SELECT * FROM item WHERE owner_id = :ownerId", nativeQuery = true)
    List<Item> findItemsByOwnerId(@Param("ownerId") Long ownerId);

    @Query(" select i from Item i " +
            "where upper(i.name) like upper(concat('%', ?1, '%')) " +
            "   or upper(i.description) like upper(concat('%', ?1, '%'))")
    List<Item> search(String text, Long ownerId);

}
