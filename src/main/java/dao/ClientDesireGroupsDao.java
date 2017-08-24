package dao;

import entities.ClientDesireGroups;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Тёма on 12.04.2017.
 */
public interface ClientDesireGroupsDao {

    List<ClientDesireGroups> getAllForClient(String userName);

    boolean saveAll(String userName, List<String> lgroup);
}
