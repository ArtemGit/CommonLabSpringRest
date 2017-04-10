package dao;

import entities.Asset;

import java.util.List;

/**
 * Created by Тёма on 06.12.2016.
 */
public interface goodsDao {
    Asset findByIdAndGroupName(int id, String group);

    Asset findById(int id);

    boolean saveGoods(Asset asset);

    boolean updateAsset(Asset asset);

    boolean deleteGoods(Asset asset);

    List<Asset> findAllAssets(String groupname);

    int getCount();
    List<Asset> findAllAssets();

    public boolean isAssetExist(Asset asset);

    public int checkAssetsInGroup(String groupname);
}
