package ec.edu.unl.refugio.controller;

import ec.edu.unl.refugio.business.service.ShelterService;
import ec.edu.unl.refugio.exception.AnimalNotAvailableException;
import ec.edu.unl.refugio.exception.AnimalNotFoundException;
import ec.edu.unl.refugio.model.*;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class AdoptionBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private ShelterService service;

    private String animalId;
    private Animal animal;
    private String adopterIdCard;
    private String adopterName;
    private String adopterEmail;
    private String adopterPhone;
    private String adopterAddress;
    private String speciesPreference;
    private boolean hasOtherPets;
    private float livingSpaceSqm;
    private String interviewNotes;
    private String contract;

    public void searchAnimal() {
        try {
            animal = service.findAnimalById(animalId);
        } catch (AnimalNotFoundException e) {
            animal = null;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        }
    }

    public void processAdoption() {
        try {
            Adopter adopter = new Adopter(adopterIdCard, adopterName, adopterEmail, adopterPhone,
                    adopterAddress, speciesPreference, hasOtherPets, livingSpaceSqm);
            AdoptionFile file = service.processAdoption(animalId, adopter, interviewNotes);
            contract = file.buildContractContent();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Adopción procesada exitosamente"));
            // Limpiar campos
            animal = null;
            animalId = "";
        } catch (AnimalNotFoundException | AnimalNotAvailableException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        }
    }

    // Getters y Setters
    public String getAnimalId() { return animalId; }
    public void setAnimalId(String animalId) { this.animalId = animalId; }
    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }
    public String getAdopterIdCard() { return adopterIdCard; }
    public void setAdopterIdCard(String adopterIdCard) { this.adopterIdCard = adopterIdCard; }
    public String getAdopterName() { return adopterName; }
    public void setAdopterName(String adopterName) { this.adopterName = adopterName; }
    public String getAdopterEmail() { return adopterEmail; }
    public void setAdopterEmail(String adopterEmail) { this.adopterEmail = adopterEmail; }
    public String getAdopterPhone() { return adopterPhone; }
    public void setAdopterPhone(String adopterPhone) { this.adopterPhone = adopterPhone; }
    public String getAdopterAddress() { return adopterAddress; }
    public void setAdopterAddress(String adopterAddress) { this.adopterAddress = adopterAddress; }
    public String getSpeciesPreference() { return speciesPreference; }
    public void setSpeciesPreference(String speciesPreference) { this.speciesPreference = speciesPreference; }
    public boolean isHasOtherPets() { return hasOtherPets; }
    public void setHasOtherPets(boolean hasOtherPets) { this.hasOtherPets = hasOtherPets; }
    public float getLivingSpaceSqm() { return livingSpaceSqm; }
    public void setLivingSpaceSqm(float livingSpaceSqm) { this.livingSpaceSqm = livingSpaceSqm; }
    public String getInterviewNotes() { return interviewNotes; }
    public void setInterviewNotes(String interviewNotes) { this.interviewNotes = interviewNotes; }
    public String getContract() { return contract; }
}