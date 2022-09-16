package com.purple.dao;

import javax.sql.DataSource;

import com.purple.model.User;
import com.purple.model.UserSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class UserDaoJdbc implements UserDao {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public UserDaoJdbc(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public User saveUser(User user) {
		String sql = (user.getUserId()>0) ?
			"update person set " +
					" username=:username, " +
					" role=:role " +
					" where person_id=:person_id;"
		:
			"insert into person" +
					" (password, username, role, active, salt)" +
					" values" +
					" (:password, :username, :role, true, :salt)" +
					" returning person_id;";

		Map<String,Object> params = new HashMap<>();
		params.put("person_id",user.getUserId());
		params.put("username",user.getUsername());
		params.put("password",user.getPassword());
		params.put("role",user.getRoleString());
		params.put("salt",user.getSalt());
		if (user.getUserId()==0) {
			Long id = jdbcTemplate.queryForObject(sql, params, Long.class);
			return getUserById(id);
		} else {
			jdbcTemplate.update(sql,params);
		}
		return getUserById(user.getUserId());
	}

	@Override
	public void updatePassword(String username, String password, String salt) {
		String sql = "update person set password = :password, salt = :salt where username = :username;";
		Map<String,Object> params = new HashMap<>();
		params.put("username",username);
		params.put("password",password);
		params.put("salt",salt);
		jdbcTemplate.update(sql,params);
	}
	@Override
	public User deleteUser(Long id) {
		String sql = "update person " +
				" set active=false " +
				" where person_id = :person_id;" +
				"update meme\n" +
				"set active=false \n" +
				"where (meme_id in (select ma.meme_id from meme_author ma where ma.author_id = :person_id));" +
				"update meme\n" +
				"set popularity=popularity - 1 \n" +
				"where (meme_id in (select fm.meme_id from favourite_meme fm where fm.person_id = :person_id));" +
				"delete from favourite_meme " +
				"where " +
				"person_id = :person_id";
		Map<String,Object> params = new HashMap<>();
		params.put("person_id",id);
		jdbcTemplate.update(sql,params);

		return getUserById(id);
	}

	//upgrade user to admin
	@Override
	public User upgradeUserToAdmin(Long personId) {
		String sql = "update person " +
				" set role='admin'" +
				" where person_id = :personId" +
				" returning *;";
		Map<String,Object> params = new HashMap<>();
		params.put("personId",personId);
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql,params);
		if(results.next()) {
			return mapUserFromRowSet(results,false);
		}
		return null;
	}

	@Override
	public boolean checkMemeIsFavourite(Long memeId, Long userId) {
		String sql = "select *\n" +
						"from favourite_meme\n" +
						"where person_id=:userId and meme_id=:memeId;";
		Map<String,Object> params = new HashMap<>();
		params.put("person_id",userId);
		params.put("meme_id",memeId);
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql,params);
		if(results.next()) {
			return true;
		}
		return false;
	}

	//get role by ID
	public User getRole(Long id) {
		String sql = "select role from person where person_id = :person_id;";
		Map<String,Object> params = new HashMap<>();
		params.put("person_id",id);
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, params);
		if(rowSet.next()) {
			return mapUserFromRowSet(rowSet,true);
		}
		return null;
	}

	//downgrade admin to user
	@Override
	public User downgradeAdminToUser(Long personId) {
		String sql = "update person " +
				" set role='Standard'" +
				" where person_id = :personId" +
				" returning *;";
		Map<String,Object> params = new HashMap<>();
		params.put("personId",personId);
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql,params);
		if(results.next()) {
			return mapUserFromRowSet(results,false);
		}
		return null;
	}

	@Override
	public User getUserByUsername(String username, boolean returnPassword) {
		String sqlSearchForUsername ="select p.* from person p where upper(p.username) = :username and p.active=true;";
		Map<String,Object> params = new HashMap<>();
		params.put("username",username.toUpperCase());
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlSearchForUsername, params);
		if(rowSet.next()) {
			return mapUserFromRowSet(rowSet,returnPassword);
		}
		return null;
	}

	@Override
	public List<User> getAllAdmins() {
		List<User> users = new ArrayList<>();
		String sql ="select p.* "+
				" from person p "+
				" where p.active=true " +
				" and p.role ilike 'Admin';";
		Map<String,Object> params = new HashMap<>();
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, params);
		return mapUsersFromRowSets(rowSet,false);
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		String sql ="select p.* "+
				" from person p "+
				" where p.active=true;";
		Map<String,Object> params = new HashMap<>();
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, params);
		return mapUsersFromRowSets(rowSet,false);
	}

	@Override
	public List<User> getAllStandardUsers() {
		List<User> users = new ArrayList<>();
		String sql ="select p.* "+
				" from person p "+
				" where p.active=true " +
				" and p.role ilike 'standard';";
		Map<String,Object> params = new HashMap<>();
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, params);
		return mapUsersFromRowSets(rowSet,false);
	}

	@Override
	public User getUserById(Long id) {
		String sqlSearchForUsername ="select p.* "+
				" from person p "+
				" where person_id = :person_id and p.active=true;";
		Map<String,Object> params = new HashMap<>();
		params.put("person_id",id);
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlSearchForUsername, params);
		if(rowSet.next()) {
			return mapUserFromRowSet(rowSet,false);
		}
		return null;
	}

	@Override
	public List<User> getUsers(User currentUser, UserSearchCriteria searchCriteria) {
		String sql ="select p.* "+
				" from person p "+
				" where p.active=true " +
				" and p.username ilike :username;";
		Map<String,Object> params = new HashMap<>();
		params.put("username",searchCriteria.getName());
		//params.put("groupname",searchCriteria.getGroupName());
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, params);
		return mapUsersFromRowSets(rowSet,false);
	}

	private List<User> mapUsersFromRowSets(SqlRowSet results,boolean returnPassword) {
		List<User> users = new ArrayList<>();
		while(results.next()) {
			users.add(mapUserFromRowSet(results,returnPassword));
		}
		return users;
	}
	private User mapUserFromRowSet(SqlRowSet results, boolean returnPassword) {
		User thisUser;
		thisUser = new User();
		thisUser.setUserId(results.getLong("person_id"));
		thisUser.setUsername(results.getString("username"));
		thisUser.setPassword(results.getString("password"));
		thisUser.setRole(User.UserRole.fromString(results.getString("role")));
		thisUser.setActive(results.getString("active"));

		if (returnPassword){
			thisUser.setPassword(results.getString("password"));
			thisUser.setSalt(results.getString("salt"));
		}
		return thisUser;
	}

}
