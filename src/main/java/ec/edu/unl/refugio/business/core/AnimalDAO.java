package ec.edu.unl.refugio.business.core;

import ec.edu.unl.refugio.business.dao.GenericDAO;
import ec.edu.unl.refugio.model.Animal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;

@ApplicationScoped
public class AnimalDAO extends GenericDAO<Animal, Long> {

    @PersistenceContext(unitName = "refugioPU")
    private EntityManager entityManager;

    public AnimalDAO() { super(Animal.class); }

    @Override
    protected EntityManager getEntityManager() { return this.entityManager; }

    public Optional<Animal> findByAnimalId(String animalId) {
        try {
            Animal animal = getEntityManager().createQuery("SELECT a FROM Animal a WHERE a.animalId = :id", Animal.class)
                    .setParameter("id", animalId)
                    .getSingleResult();
            return Optional.of(animal);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
