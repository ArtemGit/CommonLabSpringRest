package dao;

import entities.Application;
import entities.Asset;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Тёма on 07.12.2016.
 */
public interface applicationDao {

    List<Application> findAllApplications();

    boolean saveApplication(Application appl);

    boolean isApplicationExist(Application appl);

    List<Application> getAllForClient(String mobilePhone);

    Application findApplicationByAssetIdAndPhone(int id,String phone);

    boolean updateApplication(Application application);

    boolean existApplicationForAsset(int idAsset);

}
