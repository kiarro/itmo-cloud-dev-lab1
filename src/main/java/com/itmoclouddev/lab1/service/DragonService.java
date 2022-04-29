package com.itmoclouddev.lab1.service;

import java.util.Collection;

import com.itmoclouddev.lab1.core.*;

public interface DragonService {
    public Dragon get(long id);
    public void add(Dragon dragon);
    public void update(long id, Dragon dragon);
    public Dragon delete(long id);
    public Collection<Dragon> getAll();
    public Collection<Dragon> getFiltered(Dragon filter);

    public long countTypeLessThan(DragonType type);
    public long countCharacterMoreThan(DragonCharacter character);
}
