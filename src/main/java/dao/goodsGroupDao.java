package dao;

import entities.ClientDesireGroups;
import entities.Goodsgroup;

import java.util.List;

/**
 * Created by Тёма on 04.12.2016.
 */
public interface goodsGroupDao {
    boolean saveGroup(Goodsgroup group);

    boolean deleteGroup(Goodsgroup group );

    List<Goodsgroup> findAllGoodsgroups();

    boolean isUserExist(Goodsgroup group);

    Goodsgroup findGroupByName(String name);

    int count();

    List<Goodsgroup> findAllDesire(List<ClientDesireGroups> l);

}
