package ru.homework.pets.owner.service;

import ru.homework.pets.owner.dto.NewOwnerDto;
import ru.homework.pets.owner.dto.OwnerDto;
import ru.homework.pets.owner.dto.UpdateOwnerDto;

import java.sql.SQLException;
import java.util.List;

public interface OwnerService {
    void addOwner(NewOwnerDto newOwnerDto) throws SQLException;

    OwnerDto getOwnerById(int id) throws SQLException;

    List<OwnerDto> getAllOwners() throws SQLException;

    void updateOwner(int id, UpdateOwnerDto updateOwnerDto) throws SQLException;

    void deleteOwner(int id) throws SQLException;

}
