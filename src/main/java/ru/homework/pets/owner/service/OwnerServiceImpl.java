package ru.homework.pets.owner.service;

import ru.homework.pets.owner.dto.NewOwnerDto;
import ru.homework.pets.owner.dto.OwnerDto;
import ru.homework.pets.owner.dto.UpdateOwnerDto;
import ru.homework.pets.owner.mapper.OwnerMapper;
import ru.homework.pets.owner.model.Owner;
import ru.homework.pets.owner.repository.OwnerDaoHandler;
import ru.homework.pets.owner.repository.OwnerRepository;
import ru.homework.pets.pet.model.Pet;
import ru.homework.pets.pet.service.PetService;
import ru.homework.pets.pet.service.PetServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class OwnerServiceImpl implements OwnerService {

    PetService petService = new PetServiceImpl();
    OwnerRepository ownerRepository = new OwnerDaoHandler();

    public void addOwner(NewOwnerDto newOwnerDto) throws SQLException {
        Owner owner = OwnerMapper.OWNER_MAPPER.toOwner(newOwnerDto);
        List<Pet> pets = petService.getPetsByIds(newOwnerDto.getPetsIds());
        owner.setPets(pets);
        ownerRepository.save(owner);
    }

    @Override
    public OwnerDto getOwnerById(int id) throws SQLException {
        Owner owner = ownerRepository.getById(id);
        return OwnerMapper.OWNER_MAPPER.toOwnerDto(owner);
    }

    @Override
    public List<OwnerDto> getAllOwners() throws SQLException {
        List<Owner> owners = ownerRepository.getAll();
        return OwnerMapper.OWNER_MAPPER.toOwnerDtoList(owners);
    }

    @Override
    public void updateOwner(int id, UpdateOwnerDto updateOwnerDto) throws SQLException {
        Owner updateOwner = OwnerMapper.OWNER_MAPPER.toOwner(updateOwnerDto);
        List<Pet> pets = petService.getPetsByIds(updateOwnerDto.getPetsIds());
        updateOwner.setPets(pets);
        ownerRepository.update(id, updateOwner);
    }

    @Override
    public void deleteOwner(int id) throws SQLException {
        ownerRepository.delete(id);
    }
}
