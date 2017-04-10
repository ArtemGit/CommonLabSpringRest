package dao;

import entities.Users;

import java.util.List;

/**
 * Created by Тёма on 01.12.2016.
 */

public interface userDao {

    Users findByUserName(String login);

    boolean saveUser(Users user);

    boolean updateUser(Users user);

    boolean deleteUserByUserName(Users user);

    List<Users> findAllUsers();

    void deleteAllUsers();

    public boolean isUserExist(Users user);
}
