package com.purple.handler;

import com.purple.model.User;
import com.purple.model.UserSearchCriteria;

import java.util.List;
import java.util.Map;

public interface UserHandlerable {
    User login(String username, String password);

    User getUserById(User callingUser, long id);

    boolean updateUserPassword(User callingUser, User userToChangePassword, Map<String, String> errorMessages);

    User registerUser(User callingUser, User newUser, Map<String, String> errorMessages);

//    void deleteUser(User user);

    User saveUser(User callingUser, User newUser, Map<String, String> errorMessages);
    List<User> getUsers(User callingUser, UserSearchCriteria searchCriteria);
    User deleteUser(User callingUser, long id);

    public List<User> getAllStandardUsers();
    public List<User> getAllAdmins();
    public List<User> getAllUsers();
    public User downgradeAdminToUser(Long personId);
    public User upgradeUserToAdmin(Long personId);
    public User getRole(Long personId);
    public boolean checkMemeIsFavourite(Long memeId, User user);
}
