package com.rbac.controller;

import java.util.List;

import com.rbac.dao.AcessTypeHome;
import com.rbac.dao.PermissionHome;
import com.rbac.dao.ResourceHome;
import com.rbac.dao.UserHome;
import com.rbac.dao.UserRoleHome;
import com.rbac.model.User;
import com.rbac.model.UserRole;

public class UserController {
	
	AcessTypeHome acessTypeHome = new AcessTypeHome();
	ResourceHome resourceHome = new ResourceHome();
	UserHome userHome = new UserHome();
	PermissionHome permissionHome = new PermissionHome();
	UserRoleHome userRoleHome = new UserRoleHome();
	
	RoleController roleController = new RoleController();
	
	public void saveUser(User user) {
		userHome.attachDirty(user);
	}
	
	public List<User> getAllUser() {
		return userHome.getAllUser();
	}
	
	public User getUserById(Integer id) {
		return userHome.findById(id);
	}
	
	public void assignUserRole(UserRole userRole) {
		userRoleHome.attachDirty(userRole);
	}
	
	public User login(String username, String password) {
		
		User user = new User();
		
		user.setUsername(username);
		user.setPassword(password);
		
		List<User> users = userHome.findByExample(user);
		
		if(users != null && Boolean.FALSE.equals(users.isEmpty())) {
			return users.get(0);
		} else {
			return null;
		}
	}
	
	public List<UserRole> getAllUserRoles() {
		return userRoleHome.getAllUserRoles();
	}
	
	public List<UserRole> getUserRoles(UserRole userRole) {
		return userRoleHome.findByExample(userRole);
	}
	
	public void deleteUserRole(UserRole userRole) {
		userRoleHome.delete(userRole);
		
		roleController.RoleUpdate(userRole.getRole());
	}
	
	public void deleteUser(User user) {
		userHome.delete(user);
		
		List<UserRole> userRoles = this.getAllUserRoles();
		
		for(UserRole userRole : userRoles) {
			deleteUserRole(userRole);
		}
	}
	
	public UserRole getUserRoleById(Integer id) {
		return userRoleHome.findById(id);
	}
	
}
