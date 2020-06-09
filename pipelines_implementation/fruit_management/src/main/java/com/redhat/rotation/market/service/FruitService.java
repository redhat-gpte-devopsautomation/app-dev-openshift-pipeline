package com.redhat.rotation.market.service;

import com.redhat.rotation.market.model.Fruit;

import java.util.List;
import java.util.Optional;

public interface FruitService {
    void createFruit(Fruit fruit);

    List<Fruit> findAllFruits();

    Optional<Fruit> findFruitById(long id);

    Optional<Fruit> updateFruitById(long id, Fruit fruit);

    boolean deleteFruitById(long id);

    void deleteAll();
}