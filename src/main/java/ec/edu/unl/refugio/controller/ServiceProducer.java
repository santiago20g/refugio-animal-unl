package ec.edu.unl.refugio.controller;

import ec.edu.unl.refugio.business.ShelterService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class ServiceProducer {
    private ShelterService service;

    @PostConstruct
    public void init() {
        service = new ShelterService("Refugio Animal UNL");
    }

    @Produces
    @ApplicationScoped
    public ShelterService getService() {
        return service;
    }
}