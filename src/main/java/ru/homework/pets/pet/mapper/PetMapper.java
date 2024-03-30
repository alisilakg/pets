package ru.homework.pets.pet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.homework.pets.pet.dto.NewPetDto;
import ru.homework.pets.pet.dto.PetDto;
import ru.homework.pets.pet.dto.UpdatePetDto;
import ru.homework.pets.pet.model.Pet;

import java.util.List;

@Mapper
public interface PetMapper {
    PetMapper PET_MAPPER = Mappers.getMapper(PetMapper.class);
    Pet toPet(NewPetDto newPetDto);
    Pet toPet(UpdatePetDto updatePetDto);
    PetDto toPetDto(Pet pet);
    List<PetDto> toPetDtoList(List<Pet> pets);
}
