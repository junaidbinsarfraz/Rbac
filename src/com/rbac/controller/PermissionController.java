package com.rbac.controller;

import java.util.List;

import com.rbac.dao.AcessTypeHome;
import com.rbac.dao.PermissionHome;
import com.rbac.dao.ResourceHome;
import com.rbac.dao.UserHome;
import com.rbac.model.AcessType;
import com.rbac.model.Permission;
import com.rbac.model.Resource;
import com.rbac.model.RolePermission;
import com.rbac.model.User;
import com.rbac.model.UserRole;

public class PermissionController {

	AcessTypeHome acessTypeHome = new AcessTypeHome();
	ResourceHome resourceTypeHome = new ResourceHome();
	UserHome userHome = new UserHome();
	PermissionHome permissionHome = new PermissionHome();
	ResourceHome resourceHome = new ResourceHome();

	public Boolean isPermitted(User user, Resource resource, AcessType acessType) {

		List<AcessType> acessTypes = acessTypeHome.findByExample(acessType);
		List<User> users = userHome.findByExample(user);
		List<Resource> resources = resourceTypeHome.findByExample(resource);

		if (acessTypes != null && Boolean.FALSE.equals(acessTypes.isEmpty()) && users != null && Boolean.FALSE.equals(users.isEmpty())
				&& resources != null && Boolean.FALSE.equals(resources.isEmpty())) {

			AcessType acessTypeDb = acessTypes.get(0);
			User userDb = users.get(0);
			Resource resourceDb = resources.get(0);

			if (userDb.getUserRoles() != null && Boolean.FALSE.equals(userDb.getUserRoles().isEmpty())) {

				Permission newPermission = new Permission();

				newPermission.setAccesstypeid(acessTypeDb.getId());
				newPermission.setResourceid(resourceDb.getId());

				List<Permission> permissions = permissionHome.findByExample(newPermission);

				if (permissions != null && Boolean.FALSE.equals(permissions.isEmpty())) {

					// permission = permissions.get(0);

					// User's role
					for (UserRole userRole : userDb.getUserRoles()) {

						// Role's permission
						for (RolePermission rolePermission : userRole.getRole().getRolePermissions()) {

							// Resource's accessType permission
							for (Permission permission : permissions) {
								if (Boolean.TRUE.equals(permission.getStatus())
										&& permission.getId().equals(rolePermission.getPermission().getId())) {
									return Boolean.TRUE;
								}
							}
						}
					}
				} else {
					return Boolean.FALSE;
				}

			} else {
				return Boolean.FALSE;
			}
		}

		return Boolean.FALSE;
	}
	
	public AcessType getAcessTypeById(Integer id) {
		return acessTypeHome.findById(id);
	}
	
	public Resource getResourceById(Integer id) {
		return resourceTypeHome.findById(id);
	}
	
	public List<Permission> getAllPermissions() {
		return permissionHome.getAllPermissions();
	}
	
	public Permission getPermissionById(Integer id) {
		return permissionHome.findById(id);
	}
	
	public List<AcessType> getAllAcessTypes() {
		return acessTypeHome.getAllAcessTypes();
	}
	
	public List<Resource> getAllResources() {
		return resourceHome.getAllResources();
	}
	
	public void savePermission(Permission permission) {
		permissionHome.attachDirty(permission);
	}
	
	public void deletePermission(Permission permission) {
		permissionHome.delete(permission);
	}
	
	public void saveResource(Resource resource) {
		resourceHome.attachDirty(resource);
	}
	
	public List<Resource> getResources(Resource resource) {
		return resourceHome.findByExample(resource);
	}
	
	public void deleteResource(Resource resource) {
		resourceHome.delete(resource);
	}
	
	public List<Permission> getPerissions(Permission permission) {
		return permissionHome.findByExample(permission);
	}
	
}
