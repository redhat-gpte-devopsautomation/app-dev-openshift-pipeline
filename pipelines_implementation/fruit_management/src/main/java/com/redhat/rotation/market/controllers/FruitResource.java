package com.redhat.rotation.market.controllers;

import com.redhat.rotation.market.model.Fruit;
import com.redhat.rotation.market.service.FruitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.Response.*;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("market/fruits")
public class FruitResource {
    private final Logger log = LoggerFactory.getLogger(FruitResource.class);

    @Inject
    FruitService fruitService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createFruit(Fruit fruit) {
        log.debug("Creating a new fruit: {}", fruit);
        fruitService.createFruit(fruit);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fruit> findAllFruits() {
        log.debug("Finding all existing fruits ...");
        return fruitService.findAllFruits();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findFruitById(@PathParam("id") long id) {
        Optional<Fruit> found = fruitService.findFruitById(id);
        log.debug("Fruit found for a given id: {} is: {}", id, found);
        return found.isPresent() ? ok(found.get()).build() : status(NOT_FOUND).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFruitById(@PathParam("id") long id, Fruit fruit) {
        log.debug("Fruit to be updated: {}", id);
        Optional<Fruit> found = fruitService.updateFruitById(id, fruit);
        return found.isPresent() ? ok(found.get()).build() : status(NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFruitById(@PathParam("id") long id) {
        log.debug("Fruit to be deleted: {}", id);
        return fruitService.deleteFruitById(id) ? noContent().build() : status(NOT_FOUND).build();
    }
}
