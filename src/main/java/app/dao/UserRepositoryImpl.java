package app.dao;

import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean insertUser(User user) {
        entityManager.persist(user);
        return true;
    }

    @Override
    public User selectUserByUsername(String name) {
        Query query = entityManager.createQuery("select us from User us join fetch us.roles where us.username = :name");
        query.setParameter("name", name);
        return (User) query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> selectAllUsers() {
        TypedQuery<User> query = (TypedQuery<User>) entityManager.createQuery("select distinct us from User us join fetch us.roles");
        return query.getResultList();
    }

    public User selectUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public boolean deleteUser(Long id) {
        Query query = entityManager.createQuery("delete from User where id = :id");
        query.setParameter("id", id);
        return query.executeUpdate() > 0;
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }
}
