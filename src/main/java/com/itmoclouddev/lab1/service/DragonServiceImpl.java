package com.itmoclouddev.lab1.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.itmoclouddev.lab1.core.Dragon;
import com.itmoclouddev.lab1.core.DragonCharacter;
import com.itmoclouddev.lab1.core.DragonFilter;
import com.itmoclouddev.lab1.core.DragonType;
import com.itmoclouddev.lab1.core.InvalidValueException;
import com.itmoclouddev.lab1.dao.Dao;
import com.itmoclouddev.lab1.dao.DaoException;
import com.itmoclouddev.lab1.dao.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Named
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
                    String.format("Broken database row: %s", e.getMessage()));
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
                    String.format("Broken database row: %s", e.getMessage()));
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
                    String.format("Broken database row: %s", e.getMessage()));
        }
    }

    @Override
    public Collection<Dragon> getFiltered(Collection<Dragon> collection, DragonFilter filter) {
        Predicate<Dragon> predicate;

        if (filter == null) {
            predicate = item -> true;
        } else {
            predicate = item -> (filter.getAge() == null || item.getAge().equals(filter.getAge()))
                    && (filter.getCharacter() == null || item.getCharacter().equals(filter.getCharacter()))
                    && (filter.getCreationDate() == null || item.getCreationDate().equals(filter.getCreationDate()))
                    && (filter.getId() == null || item.getId().equals(filter.getId()))
                    && (filter.getName() == null || item.getName().equals(filter.getName()))
                    && (filter.getType() == null || item.getType().equals(filter.getType()))
                    && (filter.getWeight() == null || item.getWeight().equals(filter.getWeight()))
                    && (filter.getCoordinateX() == null || item.getCoordinates().getX() == (filter.getCoordinateX()))
                    && (filter.getCoordinateY() == null || item.getCoordinates().getY() == (filter.getCoordinateY()))
                    && (filter.getCaveDepth() == null || item.getCave().getDepth() == (filter.getCaveDepth()))
                    && (filter.getCaveNumberOfTreasures() == null
                            || item.getCave().getNumberOfTreasures() == (filter.getCaveNumberOfTreasures()));
        }

        collection = collection.stream().filter(predicate).collect(Collectors.toList());
        return collection;
    }

    @Override
    public long countTypeLessThan(Collection<Dragon> collection, DragonType type) {
        return collection.stream().filter(item -> item.getType().compareTo(type)<0).count();
    }

    @Override
    public long countCharacterMoreThan(Collection<Dragon> collection, DragonCharacter character) {
        return collection.stream().filter(item -> item.getCharacter().compareTo(character)>0).count();
    }

    @Override
    public Collection<Dragon> nameStartsWith(Collection<Dragon> collection, String substr) {
        return collection.stream().filter(item -> item.getName().startsWith(substr)).collect(Collectors.toList());
    }

    @Override
    public Collection<Dragon> getPage(Collection<Dragon> collection, long offset, long limit) {
        return collection.stream().skip(offset).limit(limit).collect(Collectors.toList());
    }

    @Override
    public Collection<Dragon> getSorted(Collection<Dragon> collection, String[] sortvalues) {
        Comparator<Dragon> comparator = Comparator.comparing(a -> -1);
        for (String val : sortvalues) {
            int sign = val.charAt(0) == '-' ? -1 : 1;
            String field = val.substring(1).toLowerCase();
            switch (field) {
                case "id": {
                    comparator = comparator.thenComparing(Dragon::getId, (o1, o2) -> o1.compareTo(o2)*sign);
                    break;
                }
                case "name": {
                    comparator = comparator.thenComparing(Dragon::getName, (o1, o2) -> o1.compareTo(o2)*sign);
                    break;
                }
                case "x": {
                    comparator = comparator.thenComparing(Dragon::getCoordinates, (o1, o2) -> (int)(o1.getX()-o2.getX())*sign);
                    break;
                }
                case "y": {
                    comparator = comparator.thenComparing(Dragon::getCoordinates, (o1, o2) -> (int)(o1.getY()-o2.getY())*sign);
                    break;
                }
                case "creationdate": {
                    comparator = comparator.thenComparing(Dragon::getCreationDate, (o1, o2) -> o1.compareTo(o2)*sign);
                    break;
                }
                case "age": {
                    comparator = comparator.thenComparing(Dragon::getAge, (o1, o2) -> o1.compareTo(o2)*sign);
                    break;
                }
                case "weight": {
                    comparator = comparator.thenComparing(Dragon::getWeight, (o1, o2) -> o1.compareTo(o2)*sign);
                    break;
                }
                case "type": {
                    comparator = comparator.thenComparing(Dragon::getType, (o1, o2) -> o1.compareTo(o2)*sign);
                    break;
                }
                case "character": {
                    comparator = comparator.thenComparing(Dragon::getCharacter, (o1, o2) -> o1.compareTo(o2)*sign);
                    break;
                }
                case "cavedepth": {
                    comparator = comparator.thenComparing(Dragon::getCave, (o1, o2) -> (int)(o1.getDepth()-o2.getDepth())*sign);
                    break;
                }
                case "cavenumberoftreasures": {
                    comparator = comparator.thenComparing(Dragon::getCave, (o1, o2) -> (int)(o1.getNumberOfTreasures()-o2.getNumberOfTreasures())*sign);
                    break;
                }
            }
        }

        collection = collection.stream().sorted(comparator).collect(Collectors.toList());
        return collection;
    }

}
