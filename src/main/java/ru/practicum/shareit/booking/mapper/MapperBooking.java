package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.mapper.MapperItem;
import ru.practicum.shareit.user.mapper.MapperUser;

public class MapperBooking {
    public static Booking toBooking(BookingDto bookingDto) {
        return new Booking(bookingDto.getId(), bookingDto.getStart(), bookingDto.getEnd(), MapperItem.toItem(bookingDto.getItem()), MapperUser.toUser(bookingDto.getBooker()), bookingDto.getStatus());
    }

    public static BookingDto toBookingDto(Booking booking) {
        return new BookingDto(booking.getId(), booking.getStart(), booking.getEnd(), MapperItem.toItemDto(booking.getItem()), null, MapperUser.toUserDto(booking.getBooker()), booking.getBooker().getId(), booking.getStatus());
    }
}
