package dao;

import entities.Application;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Тёма on 07.12.2016.
 */
public class applicationDaoImpl implements applicationDao {

    @Autowired
    private SessionFactory sessionFactory;

    public applicationDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Application> findAllApplications() {
        List<Application> list = (List<Application>) sessionFactory.getCurrentSession()
                .createCriteria(Application.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
        return list;
    }
   @Transactional
    public boolean saveApplication(Application appl) {
        try {
            if (!this.isApplicationExist(appl)) {
                sessionFactory.getCurrentSession()
                        .save(appl);
                return true;
            } else return false;
        } catch (Exception e) {
            return false;
        }
    }
    @Transactional
    public boolean isApplicationExist(Application appl) {
        List<Application> list = findAllApplications();
        for (Application asset : list) {
            if (asset.equals(appl)) {
                return true;
            }
        }
        return false;
    }
}
