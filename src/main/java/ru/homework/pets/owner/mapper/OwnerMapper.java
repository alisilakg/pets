package ru.homework.pets.owner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.homework.pets.owner.dto.NewOwnerDto;
import ru.homework.pets.owner.dto.OwnerDto;
import ru.homework.pets.owner.dto.UpdateOwnerDto;
import ru.homework.pets.owner.model.Owner;

import java.util.List;

@Mapper
public interface OwnerMapper {
    OwnerMapper OWNER_MAPPER = Mappers.getMapper(OwnerMapper.class);
    @Mapping(target = "pets", ignore = true)
    Owner toOwner(NewOwnerDto newOwnerDto);
    @Mapping(target = "pets", ignore = true)
    Owner toOwner(UpdateOwnerDto updateOwnerDto);
    OwnerDto toOwnerDto(Owner owner);
    List<OwnerDto> toOwnerDtoList(List<Owner> owners);
}
