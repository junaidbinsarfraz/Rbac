package com.rbac.controller;

import java.util.List;

import com.rbac.common.Common;
import com.rbac.dao.AcessTypeHome;
import com.rbac.dao.PermissionHome;
import com.rbac.dao.ResourceHome;
import com.rbac.dao.RolePermissionHome;
import com.rbac.dao.UserHome;
import com.rbac.model.AcessType;
import com.rbac.model.Permission;
import com.rbac.model.Resource;
import com.rbac.model.RolePermission;
import com.rbac.model.User;
import com.rbac.model.UserRole;

/**
 * The class PermissionController is use to check permissions and also use to
 * CRUD operations of permission related objects
 */
public class PermissionController {

	AcessTypeHome acessTypeHome = new AcessTypeHome();
	ResourceHome resourceTypeHome = new ResourceHome();
	UserHome userHome = new UserHome();
	PermissionHome permissionHome = new PermissionHome();
	ResourceHome resourceHome = new ResourceHome();
	RolePermissionHome rolePermissionHome = new RolePermissionHome();

	/**
	 * The isPermitted() method is use to check the permission i.e. check if
	 * user has access-type on resource
	 * 
	 * @param user
	 * @param resource
	 * @param acessType
	 * @return true if permission is granted else false
	 */
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

	/**
	 * The deleteResource() method is use to delete the resource and also it is
	 * responsible to revoke respective permissions from all roles and erase it
	 * from permission table
	 * 
	 * @param resource
	 *            to be deleted
	 */
	public void deleteResource(Resource resource) {

		Permission dummyPermission = new Permission();

		dummyPermission.setResourceid(resource.getId());

		List<Permission> permissions = this.getPerissions(dummyPermission);

		for (Permission permission : permissions) {
			RolePermission dummyRolePermission = new RolePermission();

			dummyRolePermission.setPermission(permission);

			List<RolePermission> rolePermissions = Common.roleController.getAllRolePermissoins(dummyRolePermission);

			for (RolePermission rolePermission : rolePermissions) {
				Common.roleController.deleteRolePermission(rolePermission);
			}
		}

		resourceHome.delete(resource);
	}

	public List<Permission> getPerissions(Permission permission) {
		return permissionHome.findByExample(permission);
	}

	public List<RolePermission> getRolePermission(RolePermission rolePermission) {
		return rolePermissionHome.findByExample(rolePermission);
	}

}
