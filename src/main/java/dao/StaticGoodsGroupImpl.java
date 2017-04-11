package dao;

import entities.StaticGoodsGroup;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class StaticGoodsGroupImpl implements StaticGoodsGroupDao {

    @Autowired
    private SessionFactory sessionFactory;

    public StaticGoodsGroupImpl(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    public List<StaticGoodsGroup> getAll() {
        List<StaticGoodsGroup> list = (List<StaticGoodsGroup>) sessionFactory.getCurrentSession()
                .createCriteria(StaticGoodsGroup.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
        return list;
    }
}
