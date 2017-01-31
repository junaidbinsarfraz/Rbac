package com.rbac.model;
// Generated Jan 31, 2017 9:22:44 AM by Hibernate Tools 4.3.1.Final

import java.util.ArrayList;
import java.util.List;

/**
 * Permission generated by hbm2java
 */
public class Permission implements java.io.Serializable {

	private Integer id;
	private Integer accesstypeid;
	private Integer resourceid;
	private Boolean status;
	private List<RolePermission> rolePermissions = new ArrayList<RolePermission>(0);

	public Permission() {
	}

	public Permission(Integer accesstypeid, Integer resourceid, Boolean status, List<RolePermission> rolePermissions) {
		this.accesstypeid = accesstypeid;
		this.resourceid = resourceid;
		this.status = status;
		this.rolePermissions = rolePermissions;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccesstypeid() {
		return this.accesstypeid;
	}

	public void setAccesstypeid(Integer accesstypeid) {
		this.accesstypeid = accesstypeid;
	}

	public Integer getResourceid() {
		return this.resourceid;
	}

	public void setResourceid(Integer resourceid) {
		this.resourceid = resourceid;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<RolePermission> getRolePermissions() {
		return this.rolePermissions;
	}

	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

}
