package com.rbac.common;

import com.rbac.controller.PermissionController;
import com.rbac.controller.RoleController;
import com.rbac.controller.UserController;
import com.rbac.model.User;

public class Common {
	
	public static User user = null;
	public static String userRole = null;
	public static Boolean isAdmin = Boolean.FALSE;
	
	public static PermissionController permissionController = new PermissionController();
	public static RoleController roleController = new RoleController();
	public static UserController userController = new UserController();
	
	public static void updateUser() {
		userController.getUserById(user.getId());
	}
	
}
