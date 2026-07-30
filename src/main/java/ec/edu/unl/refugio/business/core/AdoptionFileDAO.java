package ec.edu.unl.refugio.business.core;

import ec.edu.unl.refugio.model.AdoptionFile;
import ec.edu.unl.refugio.business.dao.GenericDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class AdoptionFileDAO extends GenericDAO<AdoptionFile, Long> {

    @PersistenceContext(unitName = "refugioPU")
    private EntityManager entityManager;

    public AdoptionFileDAO() { super(AdoptionFile.class); }

    @Override
    protected EntityManager getEntityManager() { return this.entityManager; }
}
