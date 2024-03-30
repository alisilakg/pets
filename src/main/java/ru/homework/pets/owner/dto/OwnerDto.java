package ru.homework.pets.owner.dto;

import lombok.*;
import ru.homework.pets.pet.dto.PetDto;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {
    private int id;
    private String name;
    private List<PetDto> pets;
}
