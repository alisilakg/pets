package ru.homework.pets.owner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.homework.pets.pet.model.Pet;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    private int id;
    private String name;
    private List<Pet> pets;
}
