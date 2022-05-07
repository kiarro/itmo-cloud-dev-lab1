package com.itmoclouddev.lab1.core;

public class DragonCave {
    private float depth; //Поле не может быть null
    private double numberOfTreasures; //Значение поля должно быть больше 0
    
    public DragonCave(float depth, double numberOfTreasures) throws InvalidValueException {
        this.setDepth(depth);
        this.setNumberOfTreasures(numberOfTreasures);
    }

    public double getNumberOfTreasures() {
        return numberOfTreasures;
    }

    public void setNumberOfTreasures(double numberOfTreasures) throws InvalidValueException {
        if (numberOfTreasures <=0) {
            throw new InvalidValueException("Field 'numberOfTreasures' should be positive");
        }
        this.numberOfTreasures = numberOfTreasures;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }
}
