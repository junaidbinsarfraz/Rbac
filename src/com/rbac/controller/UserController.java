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
import com.rbac.crypto.util.CryptoConstants;
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

/**
 * The class UserController is use to perform CRUD operations on user and
 * related objects
 */
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

	/**
	 * The assignUserRole() method is use to assign role to user. It will
	 * re-generate user's secret key w.r.t. user's new roles (combined)
	 * 
	 * @param userRole
	 *            to be saved
	 */
	public void assignUserRole(UserRole userRole) {

		userRoleHome.attachDirty(userRole);

		UserRole updatedUserRole = new UserRole();

		updatedUserRole.setUser(userRole.getUser());

		List<UserRole> userRoles = this.getUserRoles(userRole);

		Integer bytes = BitsUtil.getBytesFromUserRoles(userRoles);

		CipherParameters secretKey = CryptoCommon.keyController.generateUserKey(BitsUtil.get32BitString(Integer.toBinaryString(bytes)));

		try {

			KeyStoreUtil.serializeSecretKey((GGHSW13SecretKeyParameters) secretKey,
					new FileOutputStream(CryptoConstants.KEY_DIRECTORY + userRole.getUser().getUsername() + ".txt"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check if user's credentials are correct
	 * 
	 * @param username
	 * @param password
	 * @return true if user's credentials are correct else false
	 */
	public User login(String username, String password) {

		User user = new User();

		user.setUsername(username);
		user.setPassword(password);

		List<User> users = userHome.findByExample(user);

		if (users != null && Boolean.FALSE.equals(users.isEmpty())) {
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

		for (UserRole userR : userRoles) {
			if ((userRole.getRole() != null && (userRole.getRole().getName().equalsIgnoreCase(userR.getRole().getName())))
					|| userRole.getUser() != null && (userRole.getUser().getUsername().equalsIgnoreCase(userR.getUser().getUsername()))) {
				selectedUserRoles.add(userR);
			}
		}

		return selectedUserRoles;
	}

	/**
	 * The deleteUserRole() method is use to delete user role and it will invoke
	 * RoleUpdate method for effected role
	 * 
	 * @param userRole
	 *            to be deleted
	 */
	public void deleteUserRole(UserRole userRole) {
		Role role = userRole.getRole();

		userRoleHome.delete(userRole);

		role = Common.roleController.getRoleById(role.getId());

		Common.roleController.RoleUpdate(role);
	}

	/**
	 * The deleteUser() method is use to delete user and it will invoke
	 * deleteUserRole method for each user role
	 * 
	 * @param user
	 *            to be deleted
	 */
	public void deleteUser(User user) {
		UserRole userRole = new UserRole();

		userRole.setUser(user);

		List<UserRole> userRoles = this.getUserRoles(userRole);

		for (UserRole userR : userRoles) {
			deleteUserRole(userR);
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
