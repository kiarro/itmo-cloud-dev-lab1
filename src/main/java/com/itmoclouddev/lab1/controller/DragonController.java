package com.itmoclouddev.lab1.controller;

import java.net.URI;
import java.util.Collection;

import javax.inject.Inject;

import com.itmoclouddev.lab1.core.Dragon;
import com.itmoclouddev.lab1.service.DragonService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/dragons")
public class DragonController {
    
    @Inject
    private DragonService dragonService;

    @GetMapping
    public Collection<Dragon> getAll() {
        return dragonService.getAll();
    }

    @GetMapping("/{id}")
    public Dragon getDragon(@PathVariable("id") long id) {
        return dragonService.get(id);
    }

    @PostMapping
    public ResponseEntity<String> addDragon(@RequestBody Dragon dragon) {
        dragonService.add(dragon);
        if (dragon.getId() > 0){
            URI uri = URI.create("/dragons/"+dragon.getId());
            return ResponseEntity.accepted().location(uri).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Dragon> deleteDragon(@PathVariable("id") long id) {
        Dragon deleted = dragonService.delete(id);
        if (deleted != null) { // if there was dragon with such id
            return ResponseEntity.ok().body(deleted);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> putDragon(@PathVariable("id") long id, @RequestBody Dragon dragon) {
        Dragon currentDragon = dragonService.get(id);
        if (currentDragon == null) { // dragon not found
            return ResponseEntity.notFound().build();
        }
        // dragon exists -> update it
        dragonService.update(id, dragon);
        return ResponseEntity.noContent().build();
    }
    
}
