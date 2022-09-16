package com.purple.handler;

import com.purple.dao.UserDao;
import com.purple.model.User;
import com.purple.model.UserSearchCriteria;
import com.purple.security.Hasher;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserHandler implements UserHandlerable {

    private final UserDao userDao;
    private final Hasher hasher;

    public UserHandler(UserDao userDao, Hasher hasher) {
        this.userDao = userDao;
        this.hasher = hasher;
    }

    @Override
    public User login(String username, String password){
        User user = userDao.getUserByUsername(username,true);
        return validatePassword(password,user) ? user: null;
    }

    private boolean validatePassword(String password, User user){
        String givenPassword = hasher.computeHash(password, Base64.decode(user.getSalt()));
        return user.getPassword().equals(givenPassword);
    }

    private void updatePasswordToHashAndAddSalt(User user) {
        byte[] salt = hasher.generateRandomSalt();
        user.setSalt(new String(Base64.encode(salt)));
        String givenPassword = hasher.computeHash(user.getPassword(), salt);
        user.setPassword(givenPassword);
    }

    @Override
    public User getUserById(User callingUser, long id) {
        if (callingUser.isMemberOfRole(User.UserRole.Admin) ||callingUser.isMemberOfRole(User.UserRole.Standard) ) {
            return userDao.getUserById(id);
        } else if (callingUser.getUserId()==id) {
            return userDao.getUserById(id);
        }
        return null;
    }

    @Override
    public boolean updateUserPassword(User callingUser, User userToChangePassword, Map<String, String> errorMessages){
        if (!userToChangePassword.getPassword().equals(userToChangePassword.getConfirmPassword())){
            errorMessages.put("PasswordsDoNotMatch", "Passwords do not match");
            return false;
        }
        this.updatePasswordToHashAndAddSalt(userToChangePassword);
        if (callingUser.isMemberOfRole(User.UserRole.Admin)) {
            userDao.updatePassword(userToChangePassword.getUsername(), userToChangePassword.getPassword(), userToChangePassword.getSalt());
        } else if (callingUser.getUserId()==userToChangePassword.getUserId()) {
            userDao.updatePassword(userToChangePassword.getUsername(), userToChangePassword.getPassword(), userToChangePassword.getSalt());
        }
        return false;
    }
    @Override
    public User registerUser(User callingUser, User newUser, Map<String, String> errorMessages) {
        if ((callingUser == null) || (callingUser.equals(newUser))) {
            if (userDao.getUserByUsername(newUser.getUsername(), false) != null) {
                errorMessages.put("UsernameExists", "UserName already exists");
                return null;
            }
            updatePasswordToHashAndAddSalt(newUser);
            return userDao.saveUser(newUser);
        }
        if (callingUser.isMemberOfRole(User.UserRole.Admin)) {
            if (userDao.getUserByUsername(newUser.getUsername(), false) != null) {
                errorMessages.put("UsernameExists", "UserName already exists");
                return null;
            }
            updatePasswordToHashAndAddSalt(newUser);
            return userDao.saveUser(newUser);
        }
        errorMessages.put("CannotCreateUser", "No permission to add user");
        return null;
    }
//    @Override
//    public User deleteUser(User user){
//        return userDao.deleteUser(user.getUserId());
//    }
    @Override
    public User saveUser(User callingUser, User newUser, Map<String, String> errorMessages){
        if ((callingUser==null)||(callingUser.getUserId() == (newUser.getUserId()))) {
            return userDao.saveUser(newUser);
        }
        if (callingUser.isMemberOfRole(User.UserRole.Admin)) {
            return userDao.saveUser(newUser);
        }

        errorMessages.put("CannotCreateUser", "No permission to add user");
        return null;
    }
    public List<User> getUsers(User callingUser, UserSearchCriteria searchCriteria){

        return this.userDao.getUsers(callingUser,searchCriteria);
    }
    @Override
    public User deleteUser(User callingUser, long id) {
        if (callingUser.isMemberOfRole(User.UserRole.Admin)) {
            return userDao.deleteUser(id);
        } else if (callingUser.getUserId()==id) {
            return userDao.deleteUser(id);
        }
        return null;
    }

    @Override
    public List<User> getAllStandardUsers() {return userDao.getAllStandardUsers();}
    @Override
    public List<User> getAllAdmins() {return userDao.getAllAdmins();}
    @Override
    public List<User> getAllUsers() {return userDao.getAllUsers();}
    @Override
    public User downgradeAdminToUser(Long personId) {return userDao.downgradeAdminToUser(personId);}
    @Override
    public User upgradeUserToAdmin(Long personId) {return userDao.upgradeUserToAdmin(personId);}
    @Override
    public User getRole(Long personId) {return userDao.getRole(personId);}

    @Override
    public boolean checkMemeIsFavourite(Long memeId, User user) {
        return this.userDao.checkMemeIsFavourite(memeId, user.getUserId());
    }

}
