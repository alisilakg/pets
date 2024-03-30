package ru.homework.pets.pet.repository;

import ru.homework.pets.pet.model.Pet;

import java.sql.SQLException;
import java.util.List;

public interface PetRepository {

    void save(Pet pet) throws SQLException;

    Pet getById(int id) throws SQLException;

    List<Pet> getAll() throws SQLException;

    void update(int id, Pet pet) throws SQLException;

    void delete(int id) throws SQLException;

    List<Pet> getPetsByIds(List<Integer> idsPets) throws SQLException;
}
