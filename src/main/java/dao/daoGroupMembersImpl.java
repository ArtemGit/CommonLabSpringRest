package dao;

import entities.GroupMembers;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Тёма on 01.12.2016.
 */
public class daoGroupMembersImpl implements  daoGroupMembers {
    private SessionFactory sessionFactory;

    @Autowired
    public daoGroupMembersImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public boolean addUserToGroup(String userName, int groupId) {
        GroupMembers member=new GroupMembers();
        member.setUsername(userName);
        member.setGroupId(2);
            sessionFactory.getCurrentSession()
                    .save(member);
            return true;


    }
    @Transactional
    public boolean updateUser(String oldUsername, String newUserName, int oldGroupId, int newGroupId) {
        return false;
    }
    @Transactional
    public boolean deleteUserByUserName(String id) {
        return false;
    }
    @Transactional
    public List<GroupMembers> findAllUsers() {
        return null;
    }
    @Transactional
    public void deleteAllUsers() {

    }
}