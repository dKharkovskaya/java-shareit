package ru.practicum.shareit.booking.controller;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.enums.State;
import ru.practicum.shareit.booking.mapper.MapperBooking;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/bookings")
@Slf4j
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingResponseDto create(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                     @RequestBody BookingDto bookingDto) {
        log.debug("received POST /booking by user Id/{}", userId);
        return bookingService.create(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approveBooking(@RequestHeader("X-Sharer-User-Id") Integer ownerId, @PathVariable("bookingId") Integer bookingId,
                                     @RequestParam("approved") Boolean isApproved) {
        Booking booking = bookingService.approveBooking(bookingId, ownerId, isApproved);
        return MapperBooking.toBookingDto(booking);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@RequestHeader("X-Sharer-User-Id") Integer ownerId, @PathVariable("bookingId") Integer bookingId) {
        Booking booking = bookingService.getBooking(bookingId, ownerId);
        return MapperBooking.toBookingDto(booking);
    }

    @GetMapping
    public List<BookingDto> getAllBookingsOfUserByState(
            @RequestHeader("X-Sharer-User-Id") Integer ownerId,
            @RequestParam(value = "state", required = false, defaultValue = "ALL") String state,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        State bookingState;
        try {
            bookingState = State.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException();
        }
        List<Booking> bookings = bookingService.getAllBookingsOfUserByState(ownerId, bookingState, from, size);
        return bookings.stream().map(MapperBooking::toBookingDto).collect(Collectors.toList());
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllBookingsOfUserItems(
            @RequestHeader("X-Sharer-User-Id") Integer ownerId,
            @RequestParam(value = "state", required = false, defaultValue = "ALL") String state,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        State bookingState;
        try {
            bookingState = State.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException();
        }
        List<Booking> bookings = bookingService.getAllBookingsOfUserItems(ownerId, bookingState, from, size);
        return bookings.stream().map(MapperBooking::toBookingDto).collect(Collectors.toList());
    }

}
