package com.rbac.controller;

import java.util.List;

import com.rbac.dao.AcessTypeHome;
import com.rbac.dao.PermissionHome;
import com.rbac.dao.ResourceHome;
import com.rbac.dao.RoleHome;
import com.rbac.dao.RolePermissionHome;
import com.rbac.dao.UserHome;
import com.rbac.model.Role;
import com.rbac.model.RolePermission;

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
	}
	
	public List<RolePermission> getRolePermissoins(RolePermission rolePermission) {
		return rolePermissionHome.findByExample(rolePermission);
	}

	public void deleteRolePermission(RolePermission rolePermission) {
		rolePermissionHome.delete(rolePermission);
	}
	
	public List<RolePermission> getAllRolePermissoins() {
		return rolePermissionHome.getAllRolePermission();
	}
	
	public RolePermission getRolePermissionById(Integer id) {
		return rolePermissionHome.findById(id);
	}
	
	/*public void deleteRolePermission(RolePermission rolePermission) {
		rolePermissionHome.delete(rolePermission);
	}*/
	
}
