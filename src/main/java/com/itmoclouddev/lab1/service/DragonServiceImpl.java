package com.itmoclouddev.lab1.service;

import java.util.Collection;

import javax.inject.Inject;

import com.itmoclouddev.lab1.core.Dragon;
import com.itmoclouddev.lab1.core.DragonCharacter;
import com.itmoclouddev.lab1.core.DragonType;
import com.itmoclouddev.lab1.core.InvalidValueException;
import com.itmoclouddev.lab1.dao.Dao;
import com.itmoclouddev.lab1.dao.DaoException;
import com.itmoclouddev.lab1.dao.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DragonServiceImpl implements DragonService {

    @Inject
    private Dao dragonDao;

    @Override
    public Dragon get(long id) {
        try {
            Dragon res = dragonDao.get(id);
            return res;
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (InvalidValueException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                    "Broken database row: %s".formatted(e.getMessage()));
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public long add(Dragon dragon) {
        try {
            long id = dragonDao.add(dragon);
            return id;
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @Override
    public void update(long id, Dragon dragon) {
        try {
            dragonDao.update(id, dragon);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Dragon delete(long id) {
        try {
            Dragon res = dragonDao.delete(id);
            return res;
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (InvalidValueException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                    "Broken database row: %s".formatted(e.getMessage()));
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Collection<Dragon> getAll() {
        try {
            Collection<Dragon> res = dragonDao.getAll();
            return res;
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (InvalidValueException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                    "Broken database row: %s".formatted(e.getMessage()));
        }
    }

    @Override
    public Collection<Dragon> getFiltered(Dragon filter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long countTypeLessThan(DragonType type) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long countCharacterMoreThan(DragonCharacter character) {
        // TODO Auto-generated method stub
        return 0;
    }

}
