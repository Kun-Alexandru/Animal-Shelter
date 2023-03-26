package com.example.lab2;

import static org.junit.Assert.assertEquals;
import com.example.lab2.Service.AnimalService;
import com.example.lab2.Service.ShelterService;
import com.example.lab2.model.Animal;
import com.example.lab2.model.Shelter;
import com.example.lab2.model.ShelterNoAnimalsDTO;
import com.example.lab2.repository.AnimalRepository;
import com.example.lab2.repository.ShelterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class Lab2ApplicationTests {

    @MockBean
    private AnimalRepository animalRepository;

    @Autowired
    private AnimalService animalService;

    @MockBean
    private ShelterRepository shelterRepository;

    @Autowired
    private ShelterService shelterService;

    @Test
    void animalFilterTest() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2021, 4, 24, 14, 33, 48, 123456789);
        Animal animal1 = new Animal("Name1","Breed1",3,"Desc1",localDateTime1,Boolean.TRUE);
        Animal animal2 = new Animal("Name2","Breed2",5,"Desc2",localDateTime1,Boolean.TRUE);
        Animal animal3 = new Animal("Name3","Breed3",8,"Desc3",localDateTime1,Boolean.TRUE);
        animalRepository.save(animal1);
        animalRepository.save(animal2);
        animalRepository.save(animal3);
        when(animalService.filterAnimal(4)).thenReturn(Stream.of(new Animal("Name2","Breed2",5,"Desc2",localDateTime1,Boolean.TRUE),new Animal("Name3","Breed3",8,"Desc3",localDateTime1,Boolean.TRUE)).collect(Collectors.toList()));
        List<Animal> listOfAnimalsFiltered = animalService.filterAnimal(4);
        assertEquals(2,listOfAnimalsFiltered.size());
    }

    @Test
    void shelterStatisticsTest(){
        LocalDateTime localDateTime1 = LocalDateTime.of(2021, 4, 24, 14, 33, 48, 123456789);
        Shelter shelter1 = new Shelter("Location1","Name1",30,"Desc1",localDateTime1);
        Shelter shelter2 = new Shelter("Location2","Name2",50,"Desc2",localDateTime1);
        Shelter shelter3 = new Shelter("Location3","Name3",70,"Desc2",localDateTime1);

        Animal animal1 = new Animal("Name1","Breed1",3,"Desc1",localDateTime1,Boolean.TRUE);
        Animal animal2 = new Animal("Name2","Breed2",5,"Desc2",localDateTime1,Boolean.TRUE);
        Animal animal3 = new Animal("Name3","Breed3",8,"Desc3",localDateTime1,Boolean.TRUE);
        Animal animal4 = new Animal("Name4","Breed4",10,"Desc4",localDateTime1,Boolean.TRUE);
        Animal animal5 = new Animal("Name5","Breed5",12,"Desc5",localDateTime1,Boolean.TRUE);

        animal1.setShelter(shelter2);
        animal2.setShelter(shelter2);
        animal3.setShelter(shelter1);
        animal4.setShelter(shelter2);
        animal5.setShelter(shelter1);

        shelterRepository.save(shelter1);
        shelterRepository.save(shelter2);
        shelterRepository.save(shelter3);
        animalRepository.save(animal1);
        animalRepository.save(animal2);
        animalRepository.save(animal3);
        animalRepository.save(animal4);
        animalRepository.save(animal5);

        ShelterNoAnimalsDTO shelterNoAnimalsDTO1 = new ShelterNoAnimalsDTO(3,shelter3.getName(),shelter3.getLocation(),0);
        ShelterNoAnimalsDTO shelterNoAnimalsDTO2 = new ShelterNoAnimalsDTO(1,shelter1.getName(),shelter1.getLocation(),2);
        ShelterNoAnimalsDTO shelterNoAnimalsDTO3 = new ShelterNoAnimalsDTO(2,shelter2.getName(),shelter2.getLocation(),3);


        when(shelterService.orderShelterByNumberAnimals()).thenReturn(Stream.of(
                new ShelterNoAnimalsDTO(3,shelter3.getName(),shelter3.getLocation(),0),
                new ShelterNoAnimalsDTO(1,shelter1.getName(),shelter1.getLocation(),2),
                new ShelterNoAnimalsDTO(2,shelter2.getName(),shelter2.getLocation(),3))
                .collect(Collectors.toList())
        );
        List<ShelterNoAnimalsDTO> shelterNoAnimalsDTOS = shelterService.orderShelterByNumberAnimals();
        assertEquals(3,shelterNoAnimalsDTOS.size());
        assertEquals("Name3",shelterNoAnimalsDTOS.get(0).getName());
        assertEquals("Name1",shelterNoAnimalsDTOS.get(1).getName());
        assertEquals("Name2",shelterNoAnimalsDTOS.get(2).getName());
    }

}
