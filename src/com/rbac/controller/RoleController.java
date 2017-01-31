package com.rbac.controller;

import java.util.List;

import com.rbac.dao.AcessTypeHome;
import com.rbac.dao.PermissionHome;
import com.rbac.dao.ResourceHome;
import com.rbac.dao.RoleHome;
import com.rbac.dao.UserHome;
import com.rbac.model.Role;

public class RoleController {
	
	AcessTypeHome acessTypeHome = new AcessTypeHome();
	ResourceHome resourceTypeHome = new ResourceHome();
	UserHome userHome = new UserHome();
	PermissionHome permissionHome = new PermissionHome();
	RoleHome roleHome = new RoleHome();
	
	public void saveRole(Role role) {
		
		roleHome.attachDirty(role);
		
	}
	
	public List<Role> getAllRoles() {
		return roleHome.getAllRoles();
	}
	
	public Role getRoleById(Integer id) {
		return roleHome.findById(id);
	}
	
}
