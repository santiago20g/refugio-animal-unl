package ec.edu.unl.refugio.controller;

import ec.edu.unl.refugio.business.service.ShelterService;
import ec.edu.unl.refugio.exception.InvalidCredentialsException;
import ec.edu.unl.refugio.model.Employee;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private ShelterService service;

    private String username;
    private String password;
    private Employee loggedEmployee;

    public String login() {
        try {
            loggedEmployee = service.login(username, password);
            return "animals?faces-redirect=true";
        } catch (InvalidCredentialsException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
    }

    public String logout() {
        loggedEmployee = null;
        username = null;
        password = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee getLoggedEmployee() {
        return loggedEmployee;
    }

    public void setLoggedEmployee(Employee loggedEmployee) {
        this.loggedEmployee = loggedEmployee;
    }
}