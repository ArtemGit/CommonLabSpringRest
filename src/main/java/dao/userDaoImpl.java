package dao;

import entities.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Тёма on 01.12.2016.
 */
public class userDaoImpl implements userDao {

    @Autowired
    private SessionFactory sessionFactory;

    public userDaoImpl(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Users findByUserName(String login) {

        List<Users> list = (List<Users>) sessionFactory.getCurrentSession()
                .createCriteria(Users.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
        for (Users user : list) {
            if (user.getUsername().equals(login)) {
                return user;
            }
        }
            return null;
    }


    @Transactional
    public boolean saveUser(Users user) {
       if(findByUserName(user.getUsername())==null) {
           sessionFactory.getCurrentSession()
                   .save(user);
       return true;
       }
        else return false;
    }

    @Transactional
    public boolean updateUser(Users user) {
        if(isUserExist(user)) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
            return true;
        }
        else return false;
    }

    @Transactional
    public boolean deleteUserByUserName(Users user) {
        try {
            sessionFactory.getCurrentSession()
                    .delete(user);
            return true;
        }
        catch(Exception e)
        {
         return false;
        }
    }

    @Transactional
    public List<Users> findAllUsers() {
        List<Users> list = (List<Users>) sessionFactory.getCurrentSession()
                .createCriteria(Users.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
        return list;
    }
    @Transactional
    public void deleteAllUsers() {
        List<Users> list = findAllUsers();
        Session session=sessionFactory.getCurrentSession();
        for(Users user:list)
                session.delete(user);
    }
    @Transactional
    public boolean isUserExist(Users user)
    {
        if(findByUserName(user.getUsername())==null)
        return false;
        else return true;
    }
}
