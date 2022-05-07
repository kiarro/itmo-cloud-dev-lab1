package com.itmoclouddev.lab1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import javax.inject.Named;

import com.itmoclouddev.lab1.core.Coordinates;
import com.itmoclouddev.lab1.core.Dragon;
import com.itmoclouddev.lab1.core.DragonCave;
import com.itmoclouddev.lab1.core.DragonCharacter;
import com.itmoclouddev.lab1.core.DragonType;
import com.itmoclouddev.lab1.core.InvalidValueException;

@Named
public class DaoImpl implements Dao {
    private Connection connection = null;

    public DaoImpl() throws DaoException {

        // set connection to postgresql
        String url = "jdbc:postgresql://localhost:5432/dragon_base";
        Properties props = new Properties();
        props.setProperty("user", "dragon");
        props.setProperty("password", "dragon");
        props.setProperty("ssl", "false");
        try {
            this.connection = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            throw new DaoException("Error in establishing database connection", e);
        }
    }

    @Override
    public Dragon get(long id) throws DaoException, InvalidValueException, NotFoundException {
        try {
            PreparedStatement psGet = connection.prepareStatement(
                    "select * from" +
                            " dragons join caves on dragons.cave_id = caves.cave_id" +
                            " where dragon_id=?");

            psGet.setLong(1, id);
            ResultSet rs = psGet.executeQuery();

            if (rs.next()) {
                return new Dragon(
                        rs.getLong("dragon_id"),
                        rs.getString("name"),
                        new Coordinates(rs.getFloat("coordinate_x"),
                                rs.getFloat("coordinate_y")),
                        rs.getObject("creation_date", OffsetDateTime.class).toZonedDateTime()
                                .withZoneSameInstant(ZoneId.of(rs.getString("creation_date_zone"))),
                        rs.getLong("age"),
                        rs.getLong("weight"),
                        DragonType.valueOf(rs.getString("type")),
                        DragonCharacter.valueOf(rs.getString("character")),
                        new DragonCave(
                                rs.getFloat("depth"),
                                rs.getDouble("number_of_treasures")));
            } else {
                throw new NotFoundException(String.format("Dragon with id=%d not found", id));
            }
        } catch (SQLException e) {
            throw new DaoException("Error in database", e);
        }
    }

    @Override
    public long add(Dragon dragon) throws DaoException {
        try {
            PreparedStatement psAdd = connection.prepareStatement(
                    "with ins1 as (" +
                            " insert into caves(depth, number_of_treasures)" +
                            " values (?, ?)" +
                            " RETURNING cave_id as new_cave_id" +
                            " )" +
                            " insert into dragons (name, coordinate_x, coordinate_y, creation_date, creation_date_zone, age, weight, type, character, cave_id)"+
                            " select ?, ?, ?, ?, ?, ?, ?, ?, ?, new_cave_id from ins1" +
                            " returning dragon_id");
            // cave params
            psAdd.setFloat(1, dragon.getCave().getDepth());
            psAdd.setDouble(2, dragon.getCave().getNumberOfTreasures());
            // dragon params
            psAdd.setString(3, dragon.getName());
            psAdd.setFloat(4, dragon.getCoordinates().getX());
            psAdd.setFloat(5, dragon.getCoordinates().getY());
            psAdd.setObject(6, dragon.getCreationDate().toOffsetDateTime());
            psAdd.setString(7, dragon.getCreationDate().getZone().getId());
            psAdd.setLong(8, dragon.getAge());
            psAdd.setLong(9, dragon.getWeight());
            psAdd.setString(10, dragon.getType().name());
            psAdd.setString(11, dragon.getCharacter().name());

            // execute
            ResultSet rs = psAdd.executeQuery();
            rs.next();
            return rs.getLong("dragon_id");
        } catch (SQLException e) {
            throw new DaoException("Error in database", e);
        }
    }

    @Override
    public void update(long id, Dragon dragon) throws DaoException, NotFoundException {
        try {
            PreparedStatement psUpdate = connection.prepareStatement(
                    "with upd1 as (" +
                            " update dragons" +
                            " set name=?, coordinate_x=?, coordinate_y=?," +
                            " age=?, weight=?, type=?, character=?" +
                            " where dragon_id=?" +
                            " returning cave_id as cave_id" +
                            " )" +
                            " update caves" +
                            " set depth=?, number_of_treasures=?" +
                            " where cave_id in (SELECT cave_id from upd1)");

            psUpdate.setString(1, dragon.getName());
            psUpdate.setFloat(2, dragon.getCoordinates().getX());
            psUpdate.setFloat(3, dragon.getCoordinates().getY());
            psUpdate.setLong(4, dragon.getAge());
            psUpdate.setLong(5, dragon.getWeight());
            psUpdate.setString(6, dragon.getType().name());
            psUpdate.setString(7, dragon.getCharacter().name());
            psUpdate.setLong(8, id);
            psUpdate.setFloat(9, dragon.getCave().getDepth());
            psUpdate.setDouble(10, dragon.getCave().getNumberOfTreasures());

            int changedRows = psUpdate.executeUpdate();
            if (changedRows == 0) {
                throw new NotFoundException(String.format("Dragon with id=%d not found", id));
            }
        } catch (SQLException e) {
            throw new DaoException("Error in database", e);
        }
    }

    @Override
    public Dragon delete(long id) throws DaoException, InvalidValueException, NotFoundException {
        try {
            PreparedStatement psDelete = connection.prepareStatement(
                    
                            " with del1 as ("+
                                " delete from dragons"+
                                " where dragon_id=?"+
                                " returning *"+
                            " )"+
                            " , del2 as ("+
                                " delete from caves"+
                                " where cave_id in (select cave_id from del1)"+
                                " returning *"+
                            " )"+
                            " select * from del1 join del2"+
                            " on del1.cave_id = del2.cave_id"
                            );

            psDelete.setLong(1, id);

            ResultSet rs = psDelete.executeQuery();

            if (!rs.next()) {
                throw new NotFoundException(String.format("Dragon with id=%d not found",id));
            }

            return new Dragon(
                    rs.getLong("dragon_id"),
                    rs.getString("name"),
                    new Coordinates(rs.getFloat("coordinate_x"),
                            rs.getFloat("coordinate_y")),
                    rs.getObject("creation_date", OffsetDateTime.class).toZonedDateTime()
                            .withZoneSameInstant(ZoneId.of(rs.getString("creation_date_zone"))),
                    rs.getLong("age"),
                    rs.getLong("weight"),
                    DragonType.valueOf(rs.getString("type")),
                    DragonCharacter.valueOf(rs.getString("character")),
                    new DragonCave(
                            rs.getFloat("depth"),
                            rs.getDouble("number_of_treasures")));
        } catch (SQLException e) {
            throw new DaoException("Error in database", e);
        }
    }

    @Override
    public Collection<Dragon> getAll() throws DaoException, InvalidValueException {
        ArrayList<Dragon> collection = new ArrayList<Dragon>();

        try {
            PreparedStatement psGetAll = connection.prepareStatement(
                            "select * from"+
                            " dragons join caves on dragons.cave_id = caves.cave_id"
                            );

            ResultSet rs = psGetAll.executeQuery();
            while (rs.next()) {
                collection.add(
                        new Dragon(rs.getLong("dragon_id"),
                                rs.getString("name"),
                                new Coordinates(rs.getFloat("coordinate_x"),
                                        rs.getFloat("coordinate_y")),
                                rs.getObject("creation_date", OffsetDateTime.class).toZonedDateTime()
                                        .withZoneSameInstant(ZoneId.of(rs.getString("creation_date_zone"))),
                                rs.getLong("age"),
                                rs.getLong("weight"),
                                DragonType.valueOf(rs.getString("type")),
                                DragonCharacter.valueOf(rs.getString("character")),
                                new DragonCave(
                                        rs.getFloat("depth"),
                                        rs.getDouble("number_of_treasures"))));
            }

            return collection;
        } catch (SQLException e) {
            throw new DaoException("Error in database", e);
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
