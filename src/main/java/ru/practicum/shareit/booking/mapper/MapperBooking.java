package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.dto.BookingResponseShortDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.item.mapper.MapperItem;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.MapperUser;

public class MapperBooking {
    public static Booking toBooking(BookingDto bookingDto) {
        return new Booking(bookingDto.getId(), bookingDto.getStart(), bookingDto.getEnd(), MapperItem.toItem(bookingDto.getItem()), MapperUser.toUser(bookingDto.getBooker()), bookingDto.getStatus());
    }

    public static BookingDto toBookingDto(Booking booking) {
        return new BookingDto(booking.getId(), booking.getStart(), booking.getEnd(), MapperItem.toItemDto(booking.getItem()), null, MapperUser.toUserDto(booking.getBooker()), booking.getBooker().getId(), booking.getStatus());
    }

    public static BookingResponseDto toBookingReturnDto(Booking booking) {
        BookingResponseDto bookingResponseDto = new BookingResponseDto();
        bookingResponseDto.setId(booking.getId());
        bookingResponseDto.setStart(booking.getStart());
        bookingResponseDto.setEnd(booking.getEnd());
        bookingResponseDto.setItem(new ItemShortDto(booking.getItem().getId(), booking.getItem().getName()));
        bookingResponseDto.setBooker(new UserDto(booking.getBooker().getId(), booking.getBooker().getName(), booking.getBooker().getEmail()));
        bookingResponseDto.setStatus(booking.getStatus());
        return bookingResponseDto;
    }
}
