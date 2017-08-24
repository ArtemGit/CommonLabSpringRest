package dao;

import entities.Asset;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тёма on 06.12.2016.
 */
public class goodsDaoImpl  implements goodsDao {

    @Autowired
    private SessionFactory sessionFactory;

    public goodsDaoImpl(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Asset findByIdAndGroupName(int id, String group) {
        List<Asset> list = findAllAssets();
        for (Asset asset : list) {
            if (asset.getIdAsset()==id && asset.getGoodsGroupName().equals(group)) {
                return asset;
            }
        }
        return null;
    }
    @Transactional
    public Asset findById(int id) {
        List<Asset> list = findAllAssets();
        for (Asset asset : list) {
            if (asset.getIdAsset()==id) {
                return asset;
            }
        }
        return null;
    }

    @Transactional
    public boolean saveGoods(Asset asset) {
        try {
            if (!this.isAssetExist(asset)) {
                sessionFactory.getCurrentSession()
                        .save(asset);
                return true;
            } else return false;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Transactional
    public boolean updateAsset(Asset asset) {
        if(this.isAssetExist(asset)) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(asset);
            session.getTransaction().commit();
            return true;
        }
        else return false;
    }
    @Transactional
    public boolean deleteGoods(Asset assset) {
        try {
            sessionFactory.getCurrentSession()
                    .delete(assset);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    @Transactional
    public List<Asset> findAllAssets() {
        List<Asset> list = (List<Asset>) sessionFactory.getCurrentSession()
                .createCriteria(Asset.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
        return list;
    }
    @Transactional
    public List<Asset> findAllAssets(String groupname) {
        List<Asset> list = (List<Asset>) sessionFactory.getCurrentSession()
                .createCriteria(Asset.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
        List<Asset> res=new ArrayList<Asset>();
        for(Asset asset:list)
            if(asset.getGoodsGroupName().equals(groupname) )
                res.add(asset);
        return res;
    }
    @Transactional
    public int getCount() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        int count= ((Number) session.createCriteria(Asset.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();
        return count;
    }

    @Transactional
    public boolean isAssetExist(Asset asset) {
        if(this.findByIdAndGroupName(asset.getIdAsset(),asset.getGoodsGroupName())==null)
            return false;
        else return true;
    }
    @Transactional
    public int checkAssetsInGroup(String groupname) {
        List<Asset> list=this.findAllAssets(groupname);
        return list.size();
    }
}
