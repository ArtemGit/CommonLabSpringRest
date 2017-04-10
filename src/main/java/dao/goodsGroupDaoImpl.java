package dao;

import entities.Goodsgroup;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class goodsGroupDaoImpl implements  goodsGroupDao {

    @Autowired
    private SessionFactory sessionFactory;

    public goodsGroupDaoImpl(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public boolean saveGroup(Goodsgroup group) {

        if(!isUserExist(group)) {
            sessionFactory.getCurrentSession()
                    .save(group);
            return true;
        }
        else return false;
    }
    @Transactional
    public boolean deleteGroup(Goodsgroup group) {
        try {
            sessionFactory.getCurrentSession()
                    .delete(group);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    @Transactional
    public List<Goodsgroup> findAllGoodsgroups() {
        List<Goodsgroup> list = (List<Goodsgroup>) sessionFactory.getCurrentSession()
                .createCriteria(Goodsgroup.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
        return list;
    }
    @Transactional
    public boolean isUserExist(Goodsgroup group) {
        if(this.findGroupByName(group.getName())==null)
            return false;
        else return true;
    }
    @Transactional
    public Goodsgroup findGroupByName(String name) {
        List<Goodsgroup> list = (List<Goodsgroup>) sessionFactory.getCurrentSession()
                .createCriteria(Goodsgroup.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
        for (Goodsgroup group : list) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }
}
