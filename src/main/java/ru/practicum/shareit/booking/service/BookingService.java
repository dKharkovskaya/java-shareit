package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.enums.State;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingService {

    Booking approveBooking(Integer bookingId, Integer ownerId, Boolean isApproved);

    Booking getBooking(Integer bookingId, Integer userId);

    List<Booking> getAllBookingsOfUserByState(Integer bookerId, State state, Integer from, Integer size);

    List<Booking> getAllBookingsOfUserItems(Integer ownerId, State state, Integer from, Integer size);

    BookingResponseDto create(BookingDto bookingDto, Integer userId);



}
