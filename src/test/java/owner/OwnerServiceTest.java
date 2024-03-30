package owner;

import org.junit.jupiter.api.Test;
import ru.homework.pets.owner.dto.NewOwnerDto;
import ru.homework.pets.owner.dto.OwnerDto;
import ru.homework.pets.owner.dto.UpdateOwnerDto;
import ru.homework.pets.owner.service.OwnerService;
import ru.homework.pets.pet.dto.PetDto;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class OwnerServiceTest {
    private final OwnerService service = mock(OwnerService.class);

    final PetDto petDto = PetDto.builder()
            .id(1)
            .type("собака")
            .name("Шарик")
            .age(2)
            .build();

    final PetDto petDto2 = PetDto.builder()
            .id(2)
            .type("кот")
            .name("Мурзик")
            .age(1)
            .build();

    final NewOwnerDto newOwnerDto = NewOwnerDto.builder()
            .name("Иван")
            .petsIds(List.of(1))
            .build();

    final OwnerDto ownerDto = OwnerDto.builder()
            .id(1)
            .name("Иван")
            .pets(List.of(petDto))
            .build();

    final UpdateOwnerDto updateOwnerDto = UpdateOwnerDto.builder()
            .name("Петя")
            .petsIds(List.of(2))
            .build();


    @Test
    public void addOwner_thenOwnerAdded() throws SQLException {
        service.addOwner(newOwnerDto);
        verify(service, times(1)).addOwner(any());
    }

    @Test
    public void getPet_thenReturnedOwner() throws SQLException {
        when(service.getOwnerById(anyInt())).thenReturn(ownerDto);

        OwnerDto actual = service.getOwnerById(anyInt());

        assertEquals(ownerDto, actual);
    }

    @Test
    public void getAllOwners_thenReturnedListOwners() throws SQLException {
        when(service.getAllOwners()).thenReturn(List.of(ownerDto));

        List<OwnerDto> actual = service.getAllOwners();

        assertEquals(List.of(ownerDto), actual);
    }

    @Test
    public void deleteOwner_thenOwnerDeleted() throws SQLException {
        service.deleteOwner(anyInt());
        verify(service, times(1)).deleteOwner(anyInt());
    }

    @Test
    public void updateOwner_thenOwnerUpdated() throws SQLException {
        service.updateOwner(1, updateOwnerDto);
        verify(service, times(1)).updateOwner(anyInt(), any());
    }
}
