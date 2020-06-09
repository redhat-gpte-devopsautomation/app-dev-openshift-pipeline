package com.redhat.rotation.market;

import com.redhat.rotation.market.service.FruitService;
import com.redhat.rotation.market.service.FruitServiceEphemeral;
import com.redhat.rotation.market.service.FruitServiceMongo;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

@Provider
public class FruitProviderFactory {
    FruitService fruitService;

    @Inject
    FruitProviderFactory(@ConfigProperty(name = "fruit.storage.ephemeral", defaultValue = "true") boolean ephemeral) {
        fruitService = getFruitService(ephemeral);
    }

    @Produces
    @ApplicationScoped
    FruitService fruitService() {
        return fruitService;
    }

    private FruitService getFruitService(boolean ephemeral) {
        return ephemeral ? new FruitServiceEphemeral() : new FruitServiceMongo();
    }
}
