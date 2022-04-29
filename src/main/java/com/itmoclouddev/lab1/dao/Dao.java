package com.itmoclouddev.lab1.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.itmoclouddev.lab1.core.Dragon;
import com.itmoclouddev.lab1.core.DragonCharacter;
import com.itmoclouddev.lab1.core.DragonType;

public interface Dao {
    
    public Dragon get(long id) throws SQLException;
    public long add(Dragon dragon) throws SQLException;
    public void update(long id, Dragon dragon);
    public Dragon delete(long id);
    public Collection<Dragon> getAll() throws SQLException;
    public Collection<Dragon> getFiltered(Dragon filter);

    public long countTypeLessThan(DragonType type);
    public long countCharacterMoreThan(DragonCharacter character);
    
}
