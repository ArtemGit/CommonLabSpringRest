package dao;

import entities.Application;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Transactional
    public List<Application> getAllForClient(String mobilePhone) {
        List<Application> l = findAllApplications();
        List<Application> result = new ArrayList<Application>();
        for (Application appl : l)
            if (appl.getPhone().equals(mobilePhone))
                result.add(appl);
        return result;
    }

    @Transactional
    public Application findApplicationByAssetIdAndPhone(int id, String phone) {
        List<Application> l = findAllApplications();
        for (Application appl : l)
            if (appl.getAssetIdAsset() == id && appl.getPhone().equals(phone))
                return appl;
        return null;
    }

    @Transactional
    public boolean updateApplication(Application application) {
        if (this.findApplicationByAssetIdAndPhone(application.getAssetIdAsset(), application.getPhone()) != null){
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(application);
            session.getTransaction().commit();
            return true;
        } else return false;
    }

    @Transactional
    public boolean existApplicationForAsset(int idAsset) {
        List<Application> l = findAllApplications();
        for (Application appl : l)
            if (appl.getAssetIdAsset() == idAsset )
                return true;
        return false;
    }

}
