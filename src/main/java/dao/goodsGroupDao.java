package dao;

import entities.Goodsgroup;

import java.util.List;

/**
 * Created by Тёма on 04.12.2016.
 */
public interface goodsGroupDao {
    boolean saveGroup(Goodsgroup group);

    boolean deleteGroup(Goodsgroup group );

    List<Goodsgroup> findAllGoodsgroups();

    public boolean isUserExist(Goodsgroup group);

    public Goodsgroup findGroupByName(String name);
}
