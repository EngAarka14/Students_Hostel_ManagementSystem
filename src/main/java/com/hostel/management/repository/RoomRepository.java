package com.hostel.management.repository;

import com.hostel.management.entity.Room;
import com.hostel.management.entity.enums.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByBlockId(Long blockId);
    List<Room> findByStatus(RoomStatus status);
    boolean existsByBlockIdAndRoomNumberIgnoreCase(Long blockId, String roomNumber);
}
