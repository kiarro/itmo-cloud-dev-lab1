package com.itmoclouddev.lab1.core;

import java.time.ZonedDateTime;

public class DragonFilter {
    public Long id = null;
    public String name = null;
    public Float coordinateX = null;
    public Float coordinateY = null;
    public ZonedDateTime creationDate = null;
    public Long age = null;
    public Long weight = null;
    public DragonType type = null;
    public DragonCharacter character = null;
    public Float caveDepth = null;
    public Double caveNumberOfTreasures = null;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Float getCoordinateX() {
        return coordinateX;
    }
    public void setCoordinateX(Float coordinateX) {
        this.coordinateX = coordinateX;
    }
    public Float getCoordinateY() {
        return coordinateY;
    }
    public void setCoordinateY(Float coordinateY) {
        this.coordinateY = coordinateY;
    }
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public Long getAge() {
        return age;
    }
    public void setAge(Long age) {
        this.age = age;
    }
    public Long getWeight() {
        return weight;
    }
    public void setWeight(Long weight) {
        this.weight = weight;
    }
    public DragonType getType() {
        return type;
    }
    public void setType(DragonType type) {
        this.type = type;
    }
    public DragonCharacter getCharacter() {
        return character;
    }
    public void setCharacter(DragonCharacter character) {
        this.character = character;
    }
    public Float getCaveDepth() {
        return caveDepth;
    }
    public void setCaveDepth(Float caveDepth) {
        this.caveDepth = caveDepth;
    }
    public Double getCaveNumberOfTreasures() {
        return caveNumberOfTreasures;
    }
    public void setCaveNumberOfTreasures(Double caveNumberOfTreasures) {
        this.caveNumberOfTreasures = caveNumberOfTreasures;
    }
}
