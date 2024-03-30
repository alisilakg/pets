package ru.homework.pets.owner.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import ru.homework.pets.owner.dto.NewOwnerDto;
import ru.homework.pets.owner.dto.OwnerDto;
import ru.homework.pets.owner.dto.UpdateOwnerDto;
import ru.homework.pets.owner.model.Owner;
import ru.homework.pets.pet.dto.PetDto;
import ru.homework.pets.pet.model.Pet;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-30T00:01:24+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.20.1 (Amazon.com Inc.)"
)
public class OwnerMapperImpl implements OwnerMapper {

    @Override
    public Owner toOwner(NewOwnerDto newOwnerDto) {
        if ( newOwnerDto == null ) {
            return null;
        }

        Owner owner = new Owner();

        owner.setName( newOwnerDto.getName() );

        return owner;
    }

    @Override
    public Owner toOwner(UpdateOwnerDto updateOwnerDto) {
        if ( updateOwnerDto == null ) {
            return null;
        }

        Owner owner = new Owner();

        owner.setName( updateOwnerDto.getName() );

        return owner;
    }

    @Override
    public OwnerDto toOwnerDto(Owner owner) {
        if ( owner == null ) {
            return null;
        }

        OwnerDto ownerDto = new OwnerDto();

        ownerDto.setId( owner.getId() );
        ownerDto.setName( owner.getName() );
        ownerDto.setPets( petListToPetDtoList( owner.getPets() ) );

        return ownerDto;
    }

    @Override
    public List<OwnerDto> toOwnerDtoList(List<Owner> owners) {
        if ( owners == null ) {
            return null;
        }

        List<OwnerDto> list = new ArrayList<OwnerDto>( owners.size() );
        for ( Owner owner : owners ) {
            list.add( toOwnerDto( owner ) );
        }

        return list;
    }

    protected PetDto petToPetDto(Pet pet) {
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

    protected List<PetDto> petListToPetDtoList(List<Pet> list) {
        if ( list == null ) {
            return null;
        }

        List<PetDto> list1 = new ArrayList<PetDto>( list.size() );
        for ( Pet pet : list ) {
            list1.add( petToPetDto( pet ) );
        }

        return list1;
    }
}
