package dao;

import entities.Application;

import java.util.List;

/**
 * Created by Тёма on 07.12.2016.
 */
public interface applicationDao {

    List<Application> findAllApplications();

    boolean saveApplication(Application appl);

    boolean isApplicationExist(Application appl);

}
