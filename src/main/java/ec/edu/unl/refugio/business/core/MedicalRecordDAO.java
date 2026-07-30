package ec.edu.unl.refugio.business.core;

import ec.edu.unl.refugio.business.dao.GenericDAO;
import ec.edu.unl.refugio.model.Animal;
import ec.edu.unl.refugio.model.MedicalRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

@ApplicationScoped
public class MedicalRecordDAO extends GenericDAO<MedicalRecord, Long> {

    @PersistenceContext(unitName = "refugioPU")
    private EntityManager entityManager;

    public MedicalRecordDAO() {
        super(MedicalRecord.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    public Optional<MedicalRecord> findByAnimal(Animal animal) {
        try {
            MedicalRecord record = getEntityManager()
                    .createQuery("SELECT m FROM MedicalRecord m WHERE m.animal = :animal", MedicalRecord.class)
                    .setParameter("animal", animal)
                    .getSingleResult();
            return Optional.of(record);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
