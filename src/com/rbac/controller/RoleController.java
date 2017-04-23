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
		
		Resource resource = Common.permissionController.getResourceById(rolePermission.getPermission().getResourceid());
		
		byte[] bytes = FileUtil.readFile(resource.getName()).getBytes();
		
		if(bytes == null || bytes.length == 0) {
			return;
		}
		
		CryptoCommon.lastFileName = resource.getName();
		
		CryptoCommon.encryptionController.reEncrypt(bytes, rolePermission.getRole().getBits());
	}
	
	public List<RolePermission> getRolePermissoins(RolePermission rolePermission) {
		List<RolePermission> rolePermissions = rolePermissionHome.findByExample(rolePermission);
		
		List<RolePermission> selectedRolePermissions = new ArrayList<>(); 
		
		for(RolePermission roleP : rolePermissions) {
			if(rolePermission.getPermission() != null && (rolePermission.getPermission().getAccesstypeid().equals(roleP.getPermission().getAccesstypeid())
					&& rolePermission.getPermission().getResourceid().equals(roleP.getPermission().getResourceid()))) {
				selectedRolePermissions.add(roleP);
			}
		}
		
		return selectedRolePermissions;
	}

	public void deleteRolePermission(RolePermission rolePermission) {
		rolePermissionHome.delete(rolePermission);
		
		Resource resource = Common.permissionController.getResourceById(rolePermission.getPermission().getResourceid());
		
		byte[] bytes = FileUtil.readFile(resource.getName()).getBytes();
		
		if(bytes == null || bytes.length == 0) {
			return;
		}
		
		CryptoCommon.lastFileName = resource.getName();
		
		CryptoCommon.encryptionController.reEncrypt(bytes, rolePermission.getRole().getBits());
		
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
		
		List<RolePermission> rolerPermissions = Common.permissionController.getRolePermission(rolePermission);
		
		for(RolePermission rolePerm : rolerPermissions) {
			Permission permission = rolePerm.getPermission();
			
			Resource resource = Common.permissionController.getResourceById(permission.getResourceid());
			
			byte[] bytes = FileUtil.readFile(resource.getName()).getBytes();
			
			if(bytes == null || bytes.length == 0) {
				return;
			}
			
			CryptoCommon.lastFileName = resource.getName();
			
			CryptoCommon.encryptionController.reEncrypt(bytes, role.getBits());
		}
		
		UserRole userRole = new UserRole();
		
		userRole.setRole(role);
		
		List<UserRole> userRoles = Common.userController.getUserRoles(userRole);
		
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
	
	public List<Role> getRoles(Role role) {
		return roleHome.findByExample(role);
	}
	
}
