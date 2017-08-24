package dao;

import entities.AssetImage;

import java.util.List;

/**
 * Created by Тёма on 13.04.2017.
 */
public interface AssetImageDao {

    List<AssetImage> getAllImagesByAsset(int idAsset);

    AssetImage findById(int idAssetImage);

    boolean deleteImage(AssetImage image);

    boolean deleteImage(int idAsset);

    boolean save(AssetImage image);

    int count();
}

