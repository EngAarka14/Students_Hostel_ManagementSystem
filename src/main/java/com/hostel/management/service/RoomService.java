package com.hostel.management.service;

import com.hostel.management.entity.Block;
import com.hostel.management.entity.Room;
import com.hostel.management.entity.enums.RoomStatus;
import com.hostel.management.exception.BadRequestException;
import com.hostel.management.exception.ResourceNotFoundException;
import com.hostel.management.entity.enums.AllocationStatus;
import com.hostel.management.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final BlockService blockService;
    private final AllocationRepository allocationRepository;

    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    public List<Room> getByBlock(Long blockId) {
        return roomRepository.findByBlockId(blockId);
    }

    public List<Room> getAvailable() {
        return roomRepository.findByStatus(RoomStatus.AVAILABLE);
    }

    public Room getById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    @Transactional
    public Room create(Room room, Long blockId) {
        Block block = blockService.getById(blockId);
        if (roomRepository.existsByBlockIdAndRoomNumberIgnoreCase(blockId, room.getRoomNumber())) {
            throw new BadRequestException("A room with this number already exists in the block");
        }
        room.setBlock(block);
        return roomRepository.save(room);
    }

    @Transactional
    public Room update(Long id, Room updated) {
        Room existing = getById(id);
        existing.setRoomNumber(updated.getRoomNumber());
        existing.setRoomType(updated.getRoomType());
        existing.setCapacity(updated.getCapacity());
        existing.setMonthlyFee(updated.getMonthlyFee());
        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }
        return roomRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Room existing = getById(id);
        roomRepository.delete(existing);
    }

    /**
     * Recalculates and persists a room's status based on active allocation count vs capacity.
     */
    @Transactional
    public void refreshRoomStatus(Room room) {
        if (room.getStatus() == RoomStatus.UNDER_MAINTENANCE) {
            return; // maintenance status is managed explicitly
        }
        long activeOccupants = allocationRepository.countByRoomIdAndStatus(room.getId(), AllocationStatus.ACTIVE);
        room.setStatus(activeOccupants >= room.getCapacity() ? RoomStatus.FULLY_OCCUPIED : RoomStatus.AVAILABLE);
        roomRepository.save(room);
    }
}
