package com.itmoclouddev.lab1.dao;

import java.util.Collection;

import com.itmoclouddev.lab1.core.Dragon;
import com.itmoclouddev.lab1.core.DragonCharacter;
import com.itmoclouddev.lab1.core.DragonType;
import com.itmoclouddev.lab1.core.InvalidValueException;

public interface Dao {
    
    public Dragon get(long id) throws DaoException, InvalidValueException, NotFoundException;
    public long add(Dragon dragon) throws DaoException;
    public void update(long id, Dragon dragon) throws DaoException, NotFoundException;
    public Dragon delete(long id) throws DaoException, InvalidValueException, NotFoundException;
    public Collection<Dragon> getAll() throws DaoException, InvalidValueException;
    public Collection<Dragon> getFiltered(Dragon filter);

    public long countTypeLessThan(DragonType type);
    public long countCharacterMoreThan(DragonCharacter character);
    
}
