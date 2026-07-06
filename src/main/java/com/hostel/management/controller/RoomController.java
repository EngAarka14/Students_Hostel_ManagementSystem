package com.hostel.management.controller;

import com.hostel.management.entity.Room;
import com.hostel.management.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> getAll(@RequestParam(required = false) Long blockId,
                                              @RequestParam(required = false) Boolean availableOnly) {
        if (blockId != null) {
            return ResponseEntity.ok(roomService.getByBlock(blockId));
        }
        if (Boolean.TRUE.equals(availableOnly)) {
            return ResponseEntity.ok(roomService.getAvailable());
        }
        return ResponseEntity.ok(roomService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Room> create(@Valid @RequestBody Room room, @RequestParam Long blockId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.create(room, blockId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Room> update(@PathVariable Long id, @Valid @RequestBody Room room) {
        return ResponseEntity.ok(roomService.update(id, room));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
