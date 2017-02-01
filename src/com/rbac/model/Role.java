package com.rbac.model;
// Generated Jan 31, 2017 9:22:44 AM by Hibernate Tools 4.3.1.Final

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Role generated by hbm2java
 */
public class Role implements java.io.Serializable {

	private Integer id;
	private String name;
	private List<UserRole> userRoles = new ArrayList<UserRole>(0);
	private List<RolePermission> rolePermissions = new ArrayList<RolePermission>(0);

	public Role() {
	}

	public Role(String name, List<UserRole> userRoles, List<RolePermission> rolePermissions) {
		this.name = name;
		this.userRoles = userRoles;
		this.rolePermissions = rolePermissions;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public List<RolePermission> getRolePermissions() {
		return this.rolePermissions;
	}

	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}
	
	@Override
	public String toString() {
		return id + ") " + name;
	}

}