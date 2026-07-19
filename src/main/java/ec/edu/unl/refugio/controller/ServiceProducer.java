package ec.edu.unl.refugio.controller;

import ec.edu.unl.refugio.business.ShelterService;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

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