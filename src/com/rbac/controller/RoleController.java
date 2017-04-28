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
import com.rbac.dao.RoleHome;
import com.rbac.dao.RolePermissionHome;
import com.rbac.dao.UserHome;
import com.rbac.model.Permission;
import com.rbac.model.Resource;
import com.rbac.model.Role;
import com.rbac.model.RolePermission;
import com.rbac.model.User;
import com.rbac.model.UserRole;
import com.rbac.util.FileUtil;

import it.unisa.dia.gas.crypto.jpbc.fe.abe.gghsw13.params.GGHSW13SecretKeyParameters;

/**
 * The class RoleController is use to perform CRUD operations
 */
public class RoleController {

	AcessTypeHome acessTypeHome = new AcessTypeHome();
	ResourceHome resourceTypeHome = new ResourceHome();
	UserHome userHome = new UserHome();
	PermissionHome permissionHome = new PermissionHome();
	RoleHome roleHome = new RoleHome();
	RolePermissionHome rolePermissionHome = new RolePermissionHome();

	public void saveRole(Role role) {
		roleHome.attachDirty(role);
	}

	public List<Role> getAllRoles() {
		return roleHome.getAllRoles();
	}

	public Role getRoleById(Integer id) {
		return roleHome.findById(id);
	}

	/**
	 * The saveRolePermission() method is use to save role permission and invoke
	 * re-encrypt the effected permission's resource w.r.t. role's bits
	 * 
	 * @param rolePermission
	 *            to be saved
	 */
	public void saveRolePermission(RolePermission rolePermission) {

		rolePermissionHome.attachDirty(rolePermission);

		Resource resource = Common.permissionController.getResourceById(rolePermission.getPermission().getResourceid());

		byte[] bytes = FileUtil.readFile(resource.getName());

		if (bytes == null || bytes.length == 0) {
			return;
		}

		CryptoCommon.lastFileName = resource.getName();

		byte[] reEncryptedBytes = CryptoCommon.encryptionController.reEncrypt(bytes, rolePermission.getRole().getBits());

		try {
			FileUtil.writeIntoFile(CryptoCommon.lastFileName, reEncryptedBytes, Boolean.TRUE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<RolePermission> getRolePermissoins(RolePermission rolePermission) {
		List<RolePermission> rolePermissions = rolePermissionHome.findByExample(rolePermission);

		List<RolePermission> selectedRolePermissions = new ArrayList<>();

		for (RolePermission roleP : rolePermissions) {
			if (rolePermission.getPermission() != null
					&& (rolePermission.getPermission().getAccesstypeid().equals(roleP.getPermission().getAccesstypeid())
							&& rolePermission.getPermission().getResourceid().equals(roleP.getPermission().getResourceid()))) {
				selectedRolePermissions.add(roleP);
			}
		}

		return selectedRolePermissions;
	}

	/**
	 * The deleteRolePermission() method is use to delete role permission,
	 * invoke re-encrypt the effected permission's resource w.r.t. role's bits
	 * and invoke RoleUpdate method for effected role
	 * 
	 * @param rolePermission
	 *            to be deleted
	 */
	public void deleteRolePermission(RolePermission rolePermission) {
		rolePermissionHome.delete(rolePermission);

		Resource resource = Common.permissionController.getResourceById(rolePermission.getPermission().getResourceid());

		byte[] bytes = FileUtil.readFile(resource.getName());

		if (bytes == null || bytes.length == 0) {
			return;
		}

		CryptoCommon.lastFileName = resource.getName();

		byte[] reEncryptedBytes = CryptoCommon.encryptionController.reEncrypt(bytes, rolePermission.getRole().getBits());

		try {
			FileUtil.writeIntoFile(CryptoCommon.lastFileName, reEncryptedBytes, Boolean.TRUE);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Role role = Common.roleController.getRoleById(rolePermission.getRole().getId());

		this.RoleUpdate(role);
	}

	public List<RolePermission> getAllRolePermissoins() {
		return rolePermissionHome.getAllRolePermission();
	}

	public List<RolePermission> getAllRolePermissoins(RolePermission rolePermission) {
		return rolePermissionHome.findByExample(rolePermission);
	}

	public RolePermission getRolePermissionById(Integer id) {
		return rolePermissionHome.findById(id);
	}

	/**
	 * The RoleUpdate() method is use to update the role. It means to update the
	 * role's bits, re-encrypted all the permissions' resources, w.r.t.
	 * effected-role's bits, of effected-role and re-generate all user's secret
	 * keys who has effected-role
	 * 
	 * @param role
	 *            effected role
	 */
	public void RoleUpdate(Role role) {

		CryptoCommon.lastBytesUserForCircuit++;
		CryptoCommon.lastBitsUsedForCircuit = BitsUtil.get32BitString(Integer.toBinaryString(CryptoCommon.lastBytesUserForCircuit));

		role.setBits(CryptoCommon.lastBitsUsedForCircuit);
		role.setBytes(CryptoCommon.lastBytesUserForCircuit);

		this.saveRole(role);

		role = this.getRoleById(role.getId());

		RolePermission rolePermission = new RolePermission();

		rolePermission.setRole(role);

		List<RolePermission> rolerPermissions = Common.permissionController.getRolePermission(rolePermission);

		for (RolePermission rolePerm : rolerPermissions) {
			Permission permission = rolePerm.getPermission();

			Resource resource = Common.permissionController.getResourceById(permission.getResourceid());

			byte[] bytes = FileUtil.readFile(resource.getName());

			if (bytes == null || bytes.length == 0) {
				return;
			}

			CryptoCommon.lastFileName = resource.getName();

			byte[] reEncryptedBytes = CryptoCommon.encryptionController.reEncrypt(bytes, role.getBits());

			try {
				FileUtil.writeIntoFile(CryptoCommon.lastFileName, reEncryptedBytes, Boolean.TRUE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		UserRole userRole = new UserRole();

		userRole.setRole(role);

		List<UserRole> userRoles = Common.userController.getUserRoles(userRole);

		for (UserRole userR : userRoles) {

			User user = userR.getUser();

			userRole.setRole(null);
			userRole.setUser(user);

			List<UserRole> eachUserRoles = Common.userController.getUserRoles(userRole);

			Integer bytes = BitsUtil.getBytesFromUserRoles(eachUserRoles);

			CipherParameters secretKey = CryptoCommon.keyController.generateUserKey(BitsUtil.get32BitString(Integer.toBinaryString(bytes)));

			try {

				KeyStoreUtil.serializeSecretKey((GGHSW13SecretKeyParameters) secretKey,
						new FileOutputStream(CryptoConstants.KEY_DIRECTORY + user.getUsername() + ".txt"));

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Role> getRoles(Role role) {
		return roleHome.findByExample(role);
	}

}
