package ru.homework.pets.pet.service;

import ru.homework.pets.pet.dto.NewPetDto;
import ru.homework.pets.pet.dto.PetDto;
import ru.homework.pets.pet.dto.UpdatePetDto;
import ru.homework.pets.pet.model.Pet;

import java.sql.SQLException;
import java.util.List;

public interface PetService {

    void addPet(NewPetDto newPetDto) throws SQLException;

    PetDto getPetById(int id) throws SQLException;

    List<PetDto> getAllPets() throws SQLException;

    void updatePet(int id, UpdatePetDto updatePetDto) throws SQLException;

    void deletePet(int id) throws SQLException;

    List<Pet> getPetsByIds(List<Integer> idsPets) throws SQLException;
}
