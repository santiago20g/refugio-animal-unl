package ec.edu.unl.refugio.controller;

import ec.edu.unl.refugio.business.ShelterService;
import ec.edu.unl.refugio.model.*;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class AnimalBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private ShelterService service;

    private String selectedType = "perro";
    private String name;
    private String breed;
    private String sex;
    private int ageMonths;
    private String energyLevel;
    private boolean independent;
    private List<Animal> animals;

    @PostConstruct
    public void init() {
        animals = service.getAllAnimals();
    }

    public void registerAnimal() {
        Animal animal;
        String id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        if ("perro".equals(selectedType)) {
            animal = new Dog(id, name, breed, ageMonths, AdoptionStatus.AVAILABLE, sex, energyLevel);
        } else {
            animal = new Cat(id, name, breed, ageMonths, AdoptionStatus.AVAILABLE, sex, independent);
        }
        service.registerIntake(animal);
        animals = service.getAllAnimals();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Animal registrado exitosamente"));
        clearFields();
    }

    private void clearFields() {
        name = "";
        breed = "";
        sex = "";
        ageMonths = 0;
        energyLevel = "";
        independent = false;
    }

    // Getters y Setters
    public String getSelectedType() { return selectedType; }
    public void setSelectedType(String selectedType) { this.selectedType = selectedType; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }
    public int getAgeMonths() { return ageMonths; }
    public void setAgeMonths(int ageMonths) { this.ageMonths = ageMonths; }
    public String getEnergyLevel() { return energyLevel; }
    public void setEnergyLevel(String energyLevel) { this.energyLevel = energyLevel; }
    public boolean isIndependent() { return independent; }
    public void setIndependent(boolean independent) { this.independent = independent; }
    public List<Animal> getAnimals() { return animals; }
}