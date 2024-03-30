package ru.homework.pets.owner.repository;

import ru.homework.pets.owner.model.Owner;

import java.sql.SQLException;
import java.util.List;

public interface OwnerRepository {
    void save(Owner owner) throws SQLException;

    Owner getById(int id) throws SQLException;

    List<Owner> getAll() throws SQLException;

    void update(int id, Owner owner) throws SQLException;

    void delete(int id) throws SQLException;
}
