package dao;

import entities.GroupMembers;

import java.util.List;

/**
 * Created by Тёма on 01.12.2016.
 */
public interface daoGroupMembers {


    boolean addUserToGroup(String userName,int groupId);

    boolean updateUser(String oldUsername, String newUserName,int oldGroupId,int newGroupId);

    boolean deleteUserByUserName(String id);

    List<GroupMembers> findAllUsers();

    void deleteAllUsers();

    int findRoleUserByLogin(String login);

}
