package com.purple.dao;
import com.purple.model.User;
import com.purple.model.UserSearchCriteria;

import java.util.List;

public interface UserDao {

	User saveUser(User user);
	void updatePassword(String username, String password, String salt);

	User getUserByUsername(String username, boolean returnPassword);
	User getUserById(Long id);
	User getRole(Long id);
	User deleteUser(Long id);
	public List<User> getAllStandardUsers();
	public List<User> getAllAdmins();
	public List<User> getAllUsers();
	public User downgradeAdminToUser(Long personId);
	public User upgradeUserToAdmin(Long personId);
	public boolean checkMemeIsFavourite(Long memeId, Long userId);

	List<User> getUsers(User currentUser, UserSearchCriteria searchCriteria);
}
