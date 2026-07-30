package ec.edu.unl.refugio.business.people;

import ec.edu.unl.refugio.business.dao.GenericDAO;
import ec.edu.unl.refugio.model.Employee;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

@ApplicationScoped
public class EmployeeDAO extends GenericDAO<Employee, Long> {

    @PersistenceContext(unitName = "refugioPU")
    private EntityManager entityManager;

    public EmployeeDAO() {
        super(Employee.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    public Optional<Employee> findByLoginUser(String loginUser) {
        try {
            Employee employee = getEntityManager()
                    .createQuery("SELECT e FROM Employee e WHERE e.loginUser = :loginUser", Employee.class)
                    .setParameter("loginUser", loginUser)
                    .getSingleResult();
            return Optional.of(employee);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
