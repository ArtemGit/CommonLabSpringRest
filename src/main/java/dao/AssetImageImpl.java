package dao;

import entities.AssetImage;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тёма on 13.04.2017.
 */
class AssetImageImpl implements AssetImageDao {

    @Autowired
    private SessionFactory sessionFactory;

    public AssetImageImpl(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<AssetImage> getAll() {
        return (List<AssetImage>) sessionFactory.getCurrentSession()
                .createCriteria(AssetImage.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
    }

    @Transactional
    public List<AssetImage> getAllImagesByAsset(int idAsset) {
        List<AssetImage> list = getAll();
        List<AssetImage> l = new ArrayList<AssetImage>();
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getAssetIdAsset() == idAsset)
                l.add(list.get(i));
        return l;
    }

    @Transactional
    public AssetImage findById(int idAssetImage) {
        List<AssetImage> list = getAll();
        for (AssetImage image : list) {
            if (image.getIdassetImage() == idAssetImage) {
                return image;
            }
        }
        return null;
    }

    @Transactional
    public boolean deleteImage(AssetImage image) {
        try {
            sessionFactory.getCurrentSession()
                    .delete(image);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public boolean deleteImage(int idAsset) {
        try {
        List<AssetImage> list = getAllImagesByAsset(idAsset);
        list.removeIf(el->el.getAssetIdAsset()==idAsset);
            return true;
    } catch (Exception e) {
        return false;
    }
    }

    @Transactional
    public boolean save(AssetImage image) {
        try {
            int count = count() + 1;
            image.setIdassetImage(count);
            sessionFactory.getCurrentSession()
                    .save(image);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public int count() {
        List<AssetImage> list = getAll();
        return list != null ? list.size() : 0;
    }


}
