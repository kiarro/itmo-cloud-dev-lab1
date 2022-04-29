package com.itmoclouddev.lab1.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Dragon {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long age; //Значение поля должно быть больше 0, Поле не может быть null
    private Long weight; //Значение поля должно быть больше 0, Поле не может быть null
    private DragonType type; //Поле может быть null
    private DragonCharacter character; //Поле может быть null
    private DragonCave cave; //Поле не может быть null

    public Dragon(Long id, String name, Coordinates coordinates, long age, long weight, DragonType type,
            DragonCharacter character, DragonCave cave) {
        // fill with parameters
        this.setName(name);
        this.setCoordinates(coordinates);
        this.setAge(age);
        this.setWeight(weight);
        this.setType(type);
        this.setCharacter(character);
        this.setCave(cave);
        this.id = id;

        // fill auto parameters
        this.creationDate = java.time.ZonedDateTime.now();
    }

    public DragonCave getCave() {
        return cave;
    }

    public void setCave(DragonCave cave) {
        this.cave = cave;
    }

    public DragonCharacter getCharacter() {
        return character;
    }

    public void setCharacter(DragonCharacter character) {
        this.character = character;
    }

    public DragonType getType() {
        return type;
    }

    public void setType(DragonType type) {
        this.type = type;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public java.time.ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
