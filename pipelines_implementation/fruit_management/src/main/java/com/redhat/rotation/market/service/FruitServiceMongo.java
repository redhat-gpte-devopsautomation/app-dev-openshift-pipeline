package com.redhat.rotation.market.service;

import com.redhat.rotation.market.model.Fruit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class FruitServiceMongo implements FruitService {
    private final Logger log = LoggerFactory.getLogger(FruitService.class);

    public void createFruit(Fruit fruit) {
        log.debug("Creating a new mongo fruit with: {}", fruit);
        // creates a random id
        fruit.setId(new Random().nextInt(10000));
        fruit.persist();
    }

    public List<Fruit> findAllFruits() {
        log.debug("Retrieving all the mongo fruits...");
        return Fruit.list("{}");
    }

    public Optional<Fruit> findFruitById(long id) {
        return Fruit.findByIdOptional(id);
    }

    public Optional<Fruit> updateFruitById(long id, Fruit fruit) {
        Optional<Fruit> found = findFruitById(id);
        if (found.isPresent()) {
            // performs the update
            found.get().setDescription(fruit.getDescription());
            found.get().setName(fruit.getName());
            found.get().setQuantity(fruit.getQuantity());

            found.get().update();
        }
        return found;
    }

    public boolean deleteFruitById(long id) {
        // performs the deletion
        Optional<Fruit> found = findFruitById(id);
        if (found.isPresent()) {
            found.get().delete();
            return true;
        } else {
            return false;
        }
    }

    public void deleteAll() {
        // removes all the elements
        Fruit.deleteAll();
    }
}
