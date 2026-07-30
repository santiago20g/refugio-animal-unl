package ec.edu.unl.refugio.business.security;

import ec.edu.unl.refugio.business.people.EmployeeDAO;
import ec.edu.unl.refugio.exception.InvalidCredentialsException;
import ec.edu.unl.refugio.model.Employee;
import ec.edu.unl.refugio.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthService {

    @Inject
    private EmployeeDAO employeeDAO;

    public User login(String username, String password) throws InvalidCredentialsException {
        Employee employee = employeeDAO.findByLoginUser(username)
                .filter(e -> e.checkCredentials(username, password))
                .orElseThrow(InvalidCredentialsException::new);
        return employee;
    }
}
