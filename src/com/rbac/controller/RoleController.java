package com.rbac.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.bouncycastle.crypto.CipherParameters;

import com.rbac.crypto.common.CryptoCommon;
import com.rbac.crypto.controller.EncryptionController;
import com.rbac.crypto.controller.KeyController;
import com.rbac.crypto.util.BitsUtil;
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
import com.rbac.model.UserRole;
import com.rbac.util.FileUtil;

import it.unisa.dia.gas.crypto.jpbc.fe.abe.gghsw13.params.GGHSW13SecretKeyParameters;

public class RoleController {
	
	AcessTypeHome acessTypeHome = new AcessTypeHome();
	ResourceHome resourceTypeHome = new ResourceHome();
	UserHome userHome = new UserHome();
	PermissionHome permissionHome = new PermissionHome();
	RoleHome roleHome = new RoleHome();
	RolePermissionHome rolePermissionHome = new RolePermissionHome();
	
	PermissionController permissionController = new PermissionController();
	EncryptionController encryptionController = new EncryptionController();
	UserController userController = new UserController();
	KeyController keyController = new KeyController();
	
	public void saveRole(Role role) {
		roleHome.attachDirty(role);
	}
	
	public List<Role> getAllRoles() {
		return roleHome.getAllRoles();
	}
	
	public Role getRoleById(Integer id) {
		return roleHome.findById(id);
	}
	
	public void saveRolePermission(RolePermission rolePermission) {
		rolePermissionHome.attachDirty(rolePermission);
		
		Resource resource = permissionController.getResourceById(rolePermission.getPermission().getResourceid());
		
		encryptionController.reEncrypt(FileUtil.readFile(resource.getPath()).getBytes(), rolePermission.getRole().getBits());
	}
	
	public List<RolePermission> getRolePermissoins(RolePermission rolePermission) {
		return rolePermissionHome.findByExample(rolePermission);
	}

	public void deleteRolePermission(RolePermission rolePermission) {
		rolePermissionHome.delete(rolePermission);
		
		Resource resource = permissionController.getResourceById(rolePermission.getPermission().getResourceid());
		
		encryptionController.reEncrypt(FileUtil.readFile(resource.getPath()).getBytes(), rolePermission.getRole().getBits());
		
		this.RoleUpdate(rolePermission.getRole());
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
	
	/*public void deleteRolePermission(RolePermission rolePermission) {
		rolePermissionHome.delete(rolePermission);
	}*/
	
	public void RoleUpdate(Role role) {
		
		CryptoCommon.lastBytesUserForCircuit++;
		CryptoCommon.lastBitsUsedForCircuit = BitsUtil.get32BitString(Integer.toBinaryString(CryptoCommon.lastBytesUserForCircuit));
		
		role.setBits(CryptoCommon.lastBitsUsedForCircuit);
		role.setBytes(CryptoCommon.lastBytesUserForCircuit);
		
		this.saveRole(role);
		
		role = this.getRoleById(role.getId());
		
		RolePermission rolePermission = new RolePermission();
		
		rolePermission.setRole(role);
		
		List<RolePermission> rolerPermissions = permissionController.getRolePermission(rolePermission);
		
		for(RolePermission rolePerm : rolerPermissions) {
			Permission permission = rolePerm.getPermission();
			
			Resource resource = permissionController.getResourceById(permission.getResourceid());
			
			String fileContent = FileUtil.readFile(resource.getPath());
			
			encryptionController.reEncrypt(fileContent.getBytes(), role.getBits());
		}
		
		UserRole userRole = new UserRole();
		
		userRole.setRole(role);
		
		List<UserRole> userRoles = userController.getUserRoles(userRole);
		
		Integer bytes = 0;
		
		for(UserRole userR : userRoles) {
			bytes += userR.getRole().getBytes();
		}
		
		CipherParameters secretKey = keyController.generateUserKey(BitsUtil.get32BitString(Integer.toBinaryString(bytes)));
		
		try {
			
			KeyStoreUtil.serializeSecretKey((GGHSW13SecretKeyParameters) secretKey, new FileOutputStream(userRole.getUser().getUsername() + ".txt"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
