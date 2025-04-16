package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.enums.Status;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(
            Integer bookerId, LocalDateTime start, LocalDateTime end, Pageable pageable
    );

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(Integer bookerId, LocalDateTime start, Pageable pageable);

    List<Booking> findAllByBookerIdAndStartBeforeAndEndBeforeOrderByStartDesc(
            Integer bookerId, LocalDateTime start, LocalDateTime end, Pageable pageable
    );

    List<Booking> findAllByBookerIdAndStatusOrderByStartDesc(Integer bookerId, Status bookingStatus, Pageable pageable);

    List<Booking> findAllByBookerIdOrderByStartDesc(Integer bookerId, Pageable pageable);

    List<Booking> findAllByItemIdInAndStartBeforeAndEndAfterOrderByStartDesc(
            List<Integer> itemIds, LocalDateTime start, LocalDateTime end, Pageable pageable
    );

    List<Booking> findAllByItemIdInAndStartAfterOrderByStartDesc(List<Integer> itemIds, LocalDateTime start, Pageable pageable);

    List<Booking> findAllByItemIdInAndStartBeforeAndEndBeforeOrderByStartDesc(
            List<Integer> itemIds, LocalDateTime start, LocalDateTime end, Pageable pageable
    );

    List<Booking> findAllByItemIdInAndStatusOrderByStartDesc(List<Integer> itemIds, Status bookingStatus, Pageable pageable);

    List<Booking> findAllByItemIdInOrderByStartDesc(List<Integer> itemIds, Pageable pageable);

    @Query("SELECT new ru.practicum.shareit.booking.model.Booking(" +
            "b.id, MAX(b.start), b.end, b.item, b.booker, b.status) " +
            "FROM Booking b " +
            "WHERE b.item.id IN (?1) " +
            "AND b.item.owner.id = ?2 " +
            "AND b.start < ?3 " +
            "AND b.status NOT IN (?4) " +
            "GROUP BY b.id " +
            "ORDER BY b.item.id, MAX(b.start) DESC"
    )
    List<Booking> findLastByItemIdsAndItemOwnerIdAndStartIsBeforeAndStatusNotIn(
            List<Integer> itemIds, int ownerId, LocalDateTime start, List<Status> statuses
    );

    @Query("SELECT new ru.practicum.shareit.booking.model.Booking(" +
            "b.id, MIN(b.start), b.end, b.item, b.booker, b.status) " +
            "FROM Booking b " +
            "WHERE b.item.id IN (?1) " +
            "AND b.item.owner.id = ?2 " +
            "AND b.start >= ?3 " +
            "AND b.status NOT IN (?4) " +
            "GROUP BY b.id " +
            "ORDER BY b.item.id, MIN(b.start)"
    )
    List<Booking> findNextByItemIdsAndItemOwnerIdAndStartIsAfterAndStatusNotIn(
            List<Integer> itemIds, int ownerId, LocalDateTime start, List<Status> statuses
    );

    Optional<Booking> findFirstByItemIdAndBookerIdAndEndIsBefore(int itemId, int bookerId, LocalDateTime end);

}
