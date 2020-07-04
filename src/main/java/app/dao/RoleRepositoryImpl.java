package app.dao;

import app.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class RoleRepositoryImpl implements RoleRepository {

    private final EntityManager entityManager;

    @Autowired
    public RoleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role findByName(String name) {
        Query query = entityManager.createQuery("from Role where name = :name");
        query.setParameter("name", name);
        return (Role) query.getSingleResult();
    }

    @Cacheable("rolesData")
    @Override
    @SuppressWarnings("unchecked")
    public List<Role> findAll() {
        TypedQuery<Role> query = (TypedQuery<Role>) entityManager.createQuery("from Role");
        return query.getResultList();
    }
}
