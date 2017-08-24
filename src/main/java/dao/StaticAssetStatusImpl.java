package dao;

import entities.StaticAssetStatus;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Тёма on 13.04.2017.
 */
public class StaticAssetStatusImpl implements StaticAssetStatusDao{

    @Autowired
    private SessionFactory sessionFactory;

    public StaticAssetStatusImpl(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<StaticAssetStatus> getAll() {
        List<StaticAssetStatus> list = (List<StaticAssetStatus>) sessionFactory.getCurrentSession()
                .createCriteria(StaticAssetStatus.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
        return list;
    }

}
