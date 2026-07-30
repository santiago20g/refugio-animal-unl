package ec.edu.unl.refugio.business.people;

import ec.edu.unl.refugio.business.dao.GenericDAO;
import ec.edu.unl.refugio.model.Adopter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

@ApplicationScoped
public class AdopterDAO extends GenericDAO<Adopter, Long> {

    @PersistenceContext(unitName = "refugioPU")
    private EntityManager entityManager;

    public AdopterDAO() {
        super(Adopter.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    public Optional<Adopter> findByIdCard(String idCard) {
        try {
            Adopter adopter = getEntityManager()
                    .createQuery("SELECT a FROM Adopter a WHERE a.idCard = :idCard", Adopter.class)
                    .setParameter("idCard", idCard)
                    .getSingleResult();
            return Optional.of(adopter);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
