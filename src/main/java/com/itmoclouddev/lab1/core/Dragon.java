package com.itmoclouddev.lab1.core;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class Dragon {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long age; //Значение поля должно быть больше 0, Поле не может быть null
    private Long weight; //Значение поля должно быть больше 0, Поле не может быть null
    private DragonType type; //Поле может быть null
    private DragonCharacter character; //Поле может быть null
    private DragonCave cave; //Поле не может быть null

    public Dragon(String name, Coordinates coordinates, long age, long weight, DragonType type,
            DragonCharacter character, DragonCave cave) throws InvalidValueException {
        // fill with parameters
        this.setName(name);
        this.setCoordinates(coordinates);
        this.setAge(age);
        this.setWeight(weight);
        this.setType(type);
        this.setCharacter(character);
        this.setCave(cave);
        
        // fill auto parameters
        this.creationDate = java.time.ZonedDateTime.now();
    }

    public Dragon(Long id, String name, Coordinates coordinates, ZonedDateTime creationDate, long age, long weight,
            DragonType type, DragonCharacter character, DragonCave cave) throws InvalidValueException {
        // use other constructor
        this(name, coordinates, age, weight, type, character, cave);
        // add auto parameters
        this.creationDate = creationDate;
        this.id = id;
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

    public void setWeight(Long weight) throws InvalidValueException {
        if (weight <= 0) {
            throw new InvalidValueException("Field 'weight' should be positive");
        }
        this.weight = weight;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) throws InvalidValueException {
        if (age <= 0) {
            throw new InvalidValueException("Field 'age' should be positive");
        }
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

    public void setName(String name) throws InvalidValueException {
        if (name == null) {
            throw new InvalidValueException("Field 'name' shouldn't be null");
        }
        if (name == "") {
            throw new InvalidValueException("Field 'name' shouldn't be empty");
        }

        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
