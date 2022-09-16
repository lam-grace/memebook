package com.purple.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    public enum UserRole {
        Standard,
        Admin,
        GUEST,
        Vip;

        public static UserRole fromString(String value){
            if (value != null) {
                for (UserRole each : UserRole.values()) {
                    if (each.name().compareToIgnoreCase(value) == 0) {
                        return each;
                    }
                }
            }
            return Standard;
        }
    }

    private long userId;
    private UserRole role;
	private String username;
    private Boolean active;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String confirmPassword;

    private String token;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String salt;

    private Long profileImageId;


    public User(){
        this.role = UserRole.GUEST;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public long getUserId() {
        return userId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return the role
     */
    public UserRole getRole() {

    	return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(UserRole role) {

    	this.role = role;
    }
    public String getRoleString() {
        return this.role.toString();
    }

    public String getUsername() {

    	return username;
    }

    public void setUsername(String userName) {

    	this.username = userName;
    }

    public String getPassword() {

    	return password;
    }

    public void setPassword(String password) {

    	this.password = password;
    }

    public String getConfirmPassword() {

    	return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {

    	this.confirmPassword = confirmPassword;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(String active) {
        if (active.equals("true"))
            this.active = true;
        else
            this.active = false;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isMemberOfRole(String userRoleRequired) {
        return ((this.role != null) && (this.role.toString().equalsIgnoreCase(userRoleRequired)));
    }
    public boolean isMemberOfRole(UserRole userRoleRequired) {
        return ((this.role != null) && (this.role.equals(userRoleRequired)));
    }

    public Long getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(Long profileImageId) {
        this.profileImageId = profileImageId;
    }
}
