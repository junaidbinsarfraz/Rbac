package com.rbac.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.crypto.CipherParameters;

import com.rbac.common.Common;
import com.rbac.crypto.common.CryptoCommon;
import com.rbac.crypto.util.BitsUtil;
import com.rbac.crypto.util.KeyStoreUtil;
import com.rbac.dao.AcessTypeHome;
import com.rbac.dao.PermissionHome;
import com.rbac.dao.ResourceHome;
import com.rbac.dao.UserHome;
import com.rbac.dao.UserRoleHome;
import com.rbac.model.Role;
import com.rbac.model.User;
import com.rbac.model.UserRole;

import it.unisa.dia.gas.crypto.jpbc.fe.abe.gghsw13.params.GGHSW13SecretKeyParameters;

public class UserController {
	
	AcessTypeHome acessTypeHome = new AcessTypeHome();
	ResourceHome resourceHome = new ResourceHome();
	UserHome userHome = new UserHome();
	PermissionHome permissionHome = new PermissionHome();
	UserRoleHome userRoleHome = new UserRoleHome();
	
	public void saveUser(User user) {
		userHome.attachDirty(user);
	}
	
	public List<User> getAllUser() {
		return userHome.getAllUser();
	}
	
	public User getUserById(Integer id) {
		return userHome.findById(id);
	}
	
	public void saveUserRole(UserRole userRole) {
		userRoleHome.attachDirty(userRole);
	}
	
	public void assignUserRole(UserRole userRole) {
		userRoleHome.attachDirty(userRole);
		
		UserRole updatedUserRole = new UserRole();
		
		updatedUserRole.setUser(userRole.getUser());
		
		List<UserRole> userRoles = this.getUserRoles(userRole);
		
		Integer bytes = BitsUtil.getBytesFromUserRoles(userRoles);
		
		CipherParameters secretKey = CryptoCommon.keyController.generateUserKey(BitsUtil.get32BitString(Integer.toBinaryString(bytes)));
		
		try {
			
			KeyStoreUtil.serializeSecretKey((GGHSW13SecretKeyParameters) secretKey, new FileOutputStream(userRole.getUser().getUsername() + ".txt"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		List<UserRole> userRoles = userRoleHome.findByExample(userRole);
		
		List<UserRole> selectedUserRoles = new ArrayList<>();
		
		for(UserRole userR : userRoles) {
			if((userRole.getRole() != null && (userRole.getRole().getName().equalsIgnoreCase(userR.getRole().getName()))) 
					&& userRole.getUser() != null && (userRole.getUser().getUsername().equalsIgnoreCase(userR.getUser().getUsername()))) {
				selectedUserRoles.add(userR);
			}
		}
			
		
		return selectedUserRoles;
	}
	
	public void deleteUserRole(UserRole userRole) {
		Role role = userRole.getRole();
		
		userRoleHome.delete(userRole);
		
		Common.roleController.RoleUpdate(role);
	}
	
	public void deleteUser(User user) {
		List<UserRole> userRoles = this.getAllUserRoles();
		
		for(UserRole userRole : userRoles) {
			deleteUserRole(userRole);
		}
		
		userHome.delete(user);
	}
	
	public UserRole getUserRoleById(Integer id) {
		return userRoleHome.findById(id);
	}
	
	public List<User> getUsers(User user) {
		return userHome.findByExample(user);
	}
	
}
