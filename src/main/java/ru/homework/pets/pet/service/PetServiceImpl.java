package ru.homework.pets.pet.service;

import ru.homework.pets.pet.dto.NewPetDto;
import ru.homework.pets.pet.dto.PetDto;
import ru.homework.pets.pet.dto.UpdatePetDto;
import ru.homework.pets.pet.mapper.PetMapper;
import ru.homework.pets.pet.model.Pet;
import ru.homework.pets.pet.repository.PetDaoHandler;
import ru.homework.pets.pet.repository.PetRepository;

import java.sql.SQLException;
import java.util.List;

public class PetServiceImpl implements PetService {
    PetRepository petRepository = new PetDaoHandler();
    @Override
    public void addPet(NewPetDto newPetDto) throws SQLException {
        Pet newPet = PetMapper.PET_MAPPER.toPet(newPetDto);
        petRepository.save(newPet);
    }

    @Override
    public PetDto getPetById(int id) throws SQLException {
        Pet pet = petRepository.getById(id);
        return PetMapper.PET_MAPPER.toPetDto(pet);
    }

    @Override
    public List<PetDto> getAllPets() throws SQLException {
        List<Pet> pets = petRepository.getAll();
        return PetMapper.PET_MAPPER.toPetDtoList(pets);
    }

    @Override
    public void updatePet(int id, UpdatePetDto updatePetDto) throws SQLException {
        Pet updatePet = PetMapper.PET_MAPPER.toPet(updatePetDto);
        petRepository.update(id, updatePet);
    }

    @Override
    public void deletePet(int id) throws SQLException {
        petRepository.delete(id);
    }

    @Override
    public List<Pet> getPetsByIds(List<Integer> idsPets) throws SQLException {
        List<Pet> pets = petRepository.getPetsByIds(idsPets);
        return pets;
    }
}
