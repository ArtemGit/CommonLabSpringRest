package dao;

import entities.StaticAssetStatus;

import java.util.List;

/**
 * Created by Тёма on 13.04.2017.
 */
public interface StaticAssetStatusDao {

    List<StaticAssetStatus> getAll();
}
