package dao;

import entities.ClientDesireGroups;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тёма on 12.04.2017.
 */
public class ClientDesireGroupsDaoImpl implements ClientDesireGroupsDao {


    @Autowired
    private SessionFactory sessionFactory;

    public ClientDesireGroupsDaoImpl(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<ClientDesireGroups> getAllForClient(String userName) {
        List<ClientDesireGroups> list = (List<ClientDesireGroups>) sessionFactory.getCurrentSession()
                .createCriteria(ClientDesireGroups.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
        List<ClientDesireGroups> res = new ArrayList<ClientDesireGroups>();
        if (list != null) {
            for (ClientDesireGroups dg : list) {
                if (dg.getUsername().equals(userName)) {
                    res.add(dg);
                }
            }
            if (res.isEmpty())
                return null;
            else return res;
        } else return null;
    }

    @Transactional
    public boolean saveAll(String userName, List<String> lgroup) {
        try {
            List<ClientDesireGroups> l = new ArrayList<ClientDesireGroups>();
            for (int i = 0; i < lgroup.size(); i++) {
                Session session = sessionFactory.openSession();
                session.beginTransaction();

                ClientDesireGroups temp = new ClientDesireGroups();
                temp.setUsername(userName);
                temp.setGroupGoods(lgroup.get(i));
                session.save(temp);
                session.getTransaction().commit();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
