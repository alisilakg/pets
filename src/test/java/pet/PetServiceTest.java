package pet;

import org.junit.jupiter.api.Test;
import ru.homework.pets.pet.dto.NewPetDto;
import ru.homework.pets.pet.dto.PetDto;
import ru.homework.pets.pet.dto.UpdatePetDto;
import ru.homework.pets.pet.service.PetService;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PetServiceTest {
    private final PetService service = mock(PetService.class);

    final NewPetDto newPetDto = NewPetDto.builder()
            .type("собака")
            .name("Шарик")
            .age(2)
            .build();

    final PetDto petDto = PetDto.builder()
            .id(1)
            .type("собака")
            .name("Шарик")
            .age(2)
            .build();

    final UpdatePetDto updatePetDto = UpdatePetDto.builder()
            .type("кот")
            .name("Мурзик")
            .age(1)
            .build();


    @Test
    public void addPet_thenPetAdded() throws SQLException {
        service.addPet(newPetDto);
        verify(service, times(1)).addPet(any());
    }

    @Test
    public void getPet_thenReturnedPet() throws SQLException {
        when(service.getPetById(anyInt())).thenReturn(petDto);

        PetDto actual = service.getPetById(anyInt());

        assertEquals(petDto, actual);
    }

    @Test
    public void getAllPets_thenReturnedListPets() throws SQLException {
        when(service.getAllPets()).thenReturn(List.of(petDto));

        List<PetDto> actual = service.getAllPets();

        assertEquals(List.of(petDto), actual);
    }

    @Test
    public void deletePet_thenPetDeleted() throws SQLException {
        service.deletePet(anyInt());
        verify(service, times(1)).deletePet(anyInt());
    }

    @Test
    public void updatePet_thenPetUpdated() throws SQLException {
        service.updatePet(1, updatePetDto);
        verify(service, times(1)).updatePet(anyInt(), any());
    }
}
