package com.itmoclouddev.lab1.controller;

import java.net.URI;
import java.util.Collection;

import javax.inject.Inject;

import com.itmoclouddev.lab1.core.Dragon;
import com.itmoclouddev.lab1.core.DragonCharacter;
import com.itmoclouddev.lab1.core.DragonFilter;
import com.itmoclouddev.lab1.core.DragonType;
import com.itmoclouddev.lab1.service.DragonService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dragons")
public class DragonController {

    @Inject
    private DragonService dragonService;

    @GetMapping
    public Collection<Dragon> getAll(@RequestParam(name = "offset", defaultValue = "0") Long offset,
            @RequestParam(name = "limit", defaultValue = "10") Long limit,
            @RequestParam(name = "sort", required = false) String[] sortvalues,
            DragonFilter filter) {

        Collection<Dragon> dragons = dragonService.getAll();
        if (!(filter == null)) {
            dragons = dragonService.getFiltered(dragons, filter);
        }
        if (!(sortvalues == null)) {
            dragons = dragonService.getSorted(dragons, sortvalues);
        }
        return dragonService.getPage(dragons, offset, limit);
    }

    @GetMapping("/test")
    public String getTestMessage() {
        return "This is test message from dragons";
    }

    @GetMapping("/{id}")
    public Dragon getDragon(@PathVariable("id") long id) {
        return dragonService.get(id);
    }

    @PostMapping
    public ResponseEntity<String> addDragon(@RequestBody Dragon dragon) {
        long id = dragonService.add(dragon);
        if (id > 0) {
            URI uri = URI.create("/dragons/" + id);
            // System.out.println(uri.toString());
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

    @GetMapping("/typeless")
    public long getTypeLessThan(@RequestParam(name = "type", required = true) DragonType type) {
        return dragonService.countTypeLessThan(dragonService.getAll(), type);
    }

    @GetMapping("/charactermore")
    public long getCharacterLessThan(@RequestParam(name = "character", required = true) DragonCharacter character) {
        return dragonService.countCharacterMoreThan(dragonService.getAll(), character);
    }

    @GetMapping("/namestarts")
    public Collection<Dragon> getNameStartsWith(@RequestParam(name = "name", required = true) String name) {
        return dragonService.nameStartsWith(dragonService.getAll(), name);
    }

}
