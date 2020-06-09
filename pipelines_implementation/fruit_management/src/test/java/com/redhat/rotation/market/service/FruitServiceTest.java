package com.redhat.rotation.market.service;

import com.redhat.rotation.market.model.Fruit;
import com.redhat.rotation.market.utils.MockDataUtils;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class FruitServiceTest {

    @Inject
    FruitService fruitService;

    @BeforeEach
    void init() {
        // we initialize the list before each test.
        fruitService.deleteAll();
    }

    @Test
    public void createFruit() {
        // creates 3 fruits
        fruitService.createFruit(MockDataUtils.createFruit("Orange", "Orange", 3));
        fruitService.createFruit(MockDataUtils.createFruit("Banana", "Banana", 7));
        fruitService.createFruit(MockDataUtils.createFruit("Apple", "Apple", 1));
        int total = fruitService.findAllFruits().size();
        // make sure 3 fruits were created
        assertEquals(3, total);
    }

    @Test
    public void findFruitById() {
        // creates 4 fruits
        fruitService.createFruit(MockDataUtils.createFruit("Orange", "Orange", 3));
        fruitService.createFruit(MockDataUtils.createFruit("Banana", "Banana", 7));
        fruitService.createFruit(MockDataUtils.createFruit("Apple", "Apple", 9));
        fruitService.createFruit(MockDataUtils.createFruit("Pineapple", "Pineapple", 1));

        // finds the first occurrence in the list to get the generated id.
        Fruit firstFruit = fruitService.findAllFruits().get(0);
        long id = firstFruit.getId();

        Fruit fruitFound = fruitService.findFruitById(id).get();

        assertEquals("Orange", fruitFound.getName());
        assertEquals("Orange", fruitFound.getDescription());
        assertEquals(3, fruitFound.getQuantity());
    }

    @Test
    public void findFruitById_NotFound() {
        // creates 2 fruits
        fruitService.createFruit(MockDataUtils.createFruit("Orange", "Orange", 3));
        fruitService.createFruit(MockDataUtils.createFruit("Banana", "Banana", 7));

        // this id: 1001 would never exist
        Optional<Fruit> fruitNotFound = fruitService.findFruitById(10001);
        assertEquals(false, fruitNotFound.isPresent());
    }

    @Test
    public void updateFruitById() {
        // creates the first fruit
        Fruit originalFruit = MockDataUtils.createFruit("Orange", "Orange", 3);
        fruitService.createFruit(originalFruit);
        long idGenerated = fruitService.findAllFruits().get(0).getId();

        // now, we change the description
        originalFruit.setDescription("This is a new description for Orange");

        Fruit fruitUpdated = fruitService.updateFruitById(idGenerated, originalFruit).get();

        // the description is different
        assertEquals("This is a new description for Orange", fruitUpdated.getDescription());
    }

    @Test
    public void deleteFruitById() {
        // creates the first fruit
        Fruit originalFruit = MockDataUtils.createFruit("Orange", "Orange", 3);
        fruitService.createFruit(originalFruit);
        long idGenerated = fruitService.findAllFruits().get(0).getId();

        // now, we remove it
        boolean removed = fruitService.deleteFruitById(idGenerated);
        assertEquals(true, removed);
        // now, we try to remove a fruit that does not exist
        assertEquals(false, fruitService.deleteFruitById(10001));
    }
}