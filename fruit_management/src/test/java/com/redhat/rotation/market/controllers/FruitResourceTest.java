package com.redhat.rotation.market.controllers;

import com.redhat.rotation.market.model.Fruit;
import com.redhat.rotation.market.service.FruitService;
import com.redhat.rotation.market.utils.MockDataUtils;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.mockito.InjectMock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@QuarkusTest
public class FruitResourceTest {

    @InjectMock
    FruitService fruitService;

    @Test
    public void createFruit() {
        Fruit newFruit = MockDataUtils.createFruit("Apple", "A simple apple", 3);

        given().contentType(ContentType.JSON)
                .body(newFruit)
                .post("market/fruits")
                .then()
                .statusCode(204);
    }

    @Test
    public void findAllFruits() {
        List<Fruit> fruitFound = new ArrayList<>();
        fruitFound.add(MockDataUtils.createFruit("Apple", "A simple apple", 3));
        fruitFound.add(MockDataUtils.createFruit("Orange", "A simple orange", 6));

        when(fruitService.findAllFruits()).thenReturn(fruitFound);

        // performs the call ...
        ValidatableResponse response = given()
                .get("market/fruits")
                .then();

        response.statusCode(200); // returned ok
        List<Fruit> list = response.extract().body().jsonPath().getList(".", Fruit.class);

        assertEquals(fruitFound.size(), list.size());
    }

    @Test
    public void findFruitById() {
        // prepare the object to be found.
        long fruitId = 43;
        Fruit fruit = MockDataUtils.createFruit(fruitId, "Orange", "A simple orange", 4);

        Optional<Fruit> fruitFound = Optional.of(fruit);
        when(fruitService.findFruitById(anyLong())).thenReturn(fruitFound);

        // performs the call ...
        ValidatableResponse response = given()
                .get("market/fruits/" + fruitId)
                .then();

        response.statusCode(200); // returned ok
        Fruit fruitResponseFound = response.extract().body().jsonPath().getObject(".", Fruit.class);
        assertEquals(fruitFound.get(), fruitResponseFound);
    }

    @Test
    public void findFruitById_notFound() {
        // prepare the object to be found.
        long fruitId = 43;

        // no fruit was found
        Optional<Fruit> fruitFound = Optional.ofNullable(null);
        when(fruitService.findFruitById(anyLong())).thenReturn(fruitFound);

        // performs the call ...
        ValidatableResponse response = given()
                .get("market/fruits/" + fruitId)
                .then();

        response.statusCode(404); // returned NOT_FOUND
    }

    @Test
    public void updateFruitById() {
        // prepare the object to be found.
        long fruitId = 43;
        Fruit fruitToUpdate = MockDataUtils.createFruit("Banana", "A simple banana", 8);

        Optional<Fruit> fruitUpdated = Optional.of(MockDataUtils.createFruit("Banana", "A simple banana", 8));
        when(fruitService.updateFruitById(anyLong(), any(Fruit.class))).thenReturn(fruitUpdated);

        // performs the call ...
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(fruitToUpdate)
                .put("market/fruits/" + fruitId)
                .then();

        response.statusCode(200); // returned ok
        Fruit fruitResponseUpdated = response.extract().body().jsonPath().getObject(".", Fruit.class);
        assertEquals(fruitUpdated.get(), fruitResponseUpdated);
    }

    @Test
    public void updateFruitById_notFound() {
        // prepare the object to be found.
        long fruitId = 43;
        Fruit fruitToUpdate = MockDataUtils.createFruit("Banana", "A simple banana", 8);

        Optional<Fruit> fruitUpdated = Optional.ofNullable(null);
        when(fruitService.updateFruitById(anyLong(), any(Fruit.class))).thenReturn(fruitUpdated);

        // performs the call ...
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(fruitToUpdate)
                .put("market/fruits/" + fruitId)
                .then();

        response.statusCode(404); // returned NOT_FOUND
    }

    @Test
    public void deleteFruitById() {
        // prepare the object to be deleted.
        long fruitId = 99;
        when(fruitService.deleteFruitById(anyLong())).thenReturn(true);

        // performs the call ...
        ValidatableResponse response = given()
                .delete("market/fruits/" + fruitId)
                .then();

        response.statusCode(204); // returned ok
    }

    @Test
    public void deleteFruitById_notFound() {
        // prepare the object to be deleted.
        long fruitId = 99;
        when(fruitService.deleteFruitById(anyLong())).thenReturn(false);

        // performs the call ...
        ValidatableResponse response = given()
                .delete("market/fruits/" + fruitId)
                .then();

        response.statusCode(404); // returned NOT_FOUND
    }
}