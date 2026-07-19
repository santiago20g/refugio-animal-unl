package ec.edu.unl.refugio.controller;

import ec.edu.unl.refugio.business.ShelterService;
import ec.edu.unl.refugio.model.AdoptionStatus;
import ec.edu.unl.refugio.model.Animal;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ReportBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private ShelterService service;

    private List<Animal> animals;
    private long totalAnimals;
    private long availableCount;
    private long inProcessCount;
    private long adoptedThisMonth;
    private String reportText;

    @PostConstruct
    public void init() {
        animals = service.getAllAnimals();
        totalAnimals = animals.size();
        availableCount = animals.stream().filter(a -> a.getStatus() == AdoptionStatus.AVAILABLE).count();
        inProcessCount = animals.stream().filter(a -> a.getStatus() == AdoptionStatus.IN_PROCESS).count();
        adoptedThisMonth = animals.stream().filter(a -> a.getStatus() == AdoptionStatus.ADOPTED).count();
        reportText = service.generateMonthlyReport();
    }

    // Getters
    public List<Animal> getAnimals() { return animals; }
    public long getTotalAnimals() { return totalAnimals; }
    public long getAvailableCount() { return availableCount; }
    public long getInProcessCount() { return inProcessCount; }
    public long getAdoptedThisMonth() { return adoptedThisMonth; }
    public String getReportText() { return reportText; }
}