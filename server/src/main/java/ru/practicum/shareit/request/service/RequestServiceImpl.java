package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.MapperItem;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.MapperItemRequests;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final UserRepository userRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @Override
    public List<ItemRequest> getAllByUserId(Long id) {
        userRepository.findById(id).orElseThrow(() -> new NotFoundException(""));
        return itemRequestRepository.findByRequestorIdOrderByCreatedDesc(id);
    }

    @Override
    @Transactional
    public ItemRequest add(ItemRequest itemRequest, Long requestorId) {
        User requestor = userRepository.findById(requestorId).orElseThrow(() -> new NotFoundException(""));
        itemRequest.setRequestor(requestor);
        itemRequest.setCreated(LocalDateTime.now());
        return itemRequestRepository.save(itemRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public ItemRequestDto getById(Long requestId, Long requestorId) {
        //userRepository.findById(requestorId).orElseThrow(() -> new NotFoundException(""));
        //return itemRequestRepository.findById(requestId).orElseThrow(() -> new NotFoundException(""));
        userRepository.findById(requestorId).orElseThrow(() -> new NotFoundException(""));
        ItemRequest itemRequest = itemRequestRepository.findById(requestId).orElseThrow(() -> new NotFoundException(""));
        ItemRequestDto itemRequestDto = MapperItemRequests.toItemRequestDto(itemRequest);
        List <ItemDto> itemDto1 = itemService.findAllByRequestId(itemRequestDto.getId());
        List <ItemDto> itemDto2 = itemService.findAllByRequestId(requestId);
        List <ItemDto> itemDto3 = itemService.findAllByRequestId(requestorId);
        List <Item> itemDto4 = itemRepository.findByRequestId(requestorId);
        List <Item> itemDto5 = itemRepository.findAllByRequestId(requestorId);
        itemRequestDto.setItems(itemService.findAllByRequestId(itemRequestDto.getId()));
        return itemRequestDto;




    }
}
