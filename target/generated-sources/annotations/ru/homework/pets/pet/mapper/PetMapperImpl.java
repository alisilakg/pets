package ru.homework.pets.pet.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import ru.homework.pets.pet.dto.NewPetDto;
import ru.homework.pets.pet.dto.PetDto;
import ru.homework.pets.pet.dto.UpdatePetDto;
import ru.homework.pets.pet.model.Pet;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-30T00:01:25+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.20.1 (Amazon.com Inc.)"
)
public class PetMapperImpl implements PetMapper {

    @Override
    public Pet toPet(NewPetDto newPetDto) {
        if ( newPetDto == null ) {
            return null;
        }

        Pet pet = new Pet();

        pet.setType( newPetDto.getType() );
        pet.setName( newPetDto.getName() );
        pet.setAge( newPetDto.getAge() );

        return pet;
    }

    @Override
    public Pet toPet(UpdatePetDto updatePetDto) {
        if ( updatePetDto == null ) {
            return null;
        }

        Pet pet = new Pet();

        pet.setType( updatePetDto.getType() );
        pet.setName( updatePetDto.getName() );
        pet.setAge( updatePetDto.getAge() );

        return pet;
    }

    @Override
    public PetDto toPetDto(Pet pet) {
        if ( pet == null ) {
            return null;
        }

        PetDto petDto = new PetDto();

        petDto.setId( pet.getId() );
        petDto.setType( pet.getType() );
        petDto.setName( pet.getName() );
        petDto.setAge( pet.getAge() );

        return petDto;
    }

    @Override
    public List<PetDto> toPetDtoList(List<Pet> pets) {
        if ( pets == null ) {
            return null;
        }

        List<PetDto> list = new ArrayList<PetDto>( pets.size() );
        for ( Pet pet : pets ) {
            list.add( toPetDto( pet ) );
        }

        return list;
    }
}
