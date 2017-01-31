package com.rbac.test;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.rbac.controller.PermissionController;
import com.rbac.controller.RoleController;
import com.rbac.controller.UserController;
import com.rbac.model.AcessType;
import com.rbac.model.Permission;
import com.rbac.model.Resource;
import com.rbac.model.Role;
import com.rbac.model.RolePermission;
import com.rbac.model.User;
import com.rbac.model.UserRole;
import com.rbac.util.Constants;
import com.rbac.util.FileUtil;

public class Test {

	private User user;
	private static Boolean isDoctor = Boolean.FALSE;
	private static Boolean isAdmin = Boolean.FALSE;

	PermissionController permissionController = new PermissionController();
	RoleController roleController = new RoleController();
	UserController userController = new UserController();

	public static void main(String[] args) throws IOException {

		/*
		 * File currentDirectory = new File(new File(".").getAbsolutePath());
		 * System.out.println(currentDirectory.getCanonicalPath());
		 * System.out.println(currentDirectory.getAbsolutePath());
		 */

		Test test = new Test();

		Scanner scan = new Scanner(System.in);
		
		System.out.println("Please login");
		System.out.println();
		
		do {

			System.out.print("Enter username : ");
			String uname = scan.nextLine();
			System.out.print("Enter password : ");
			String pass = scan.nextLine();

			test.user = test.userController.login(uname, pass);

		} while (test.user == null);

		for (UserRole userRole : test.user.getUserRoles()) {
			if (Constants.ROLE_DOCTOR.equals(userRole.getRole().getName())) {
				isDoctor = Boolean.TRUE;
				break;
			} else if (Constants.ROLE_ADMIN.equals(userRole.getRole().getName())) {
				isDoctor = Boolean.TRUE;
				break;
			}
		}

		while (true) {

			test.printMenu();

			String input = scan.nextLine();

			if ("1".equals(input)) {
				// List Files

				List<String> files = FileUtil.listFiles();

				for (String fileName : files) {
					System.out.println(fileName);
				}

			} else if ("2".equals(input)) {
				// Create File

				// Check permissions - By default any user can create resource

			} else if ("3".equals(input)) {
				// View File

				System.out.println();
				System.out.print("Enter file name : ");
				input = scan.nextLine();

				Resource resource = new Resource();

				resource.setName(input);

				AcessType acessType = new AcessType();

				acessType.setName(Constants.ACCESS_TYPE_VIEW);

				Boolean isPermitted = test.permissionController.isPermitted(test.user, resource, acessType);

				if (Boolean.TRUE.equals(isPermitted)) {

					String fileContent = FileUtil.readFile(input);

					System.out.println(fileContent);

				} else {
					System.out.println("Not Permitted");
				}

			} else if ("4".equals(input)) {
				// Update File

				System.out.println();
				System.out.print("Enter file name : ");
				input = scan.nextLine();

				Resource resource = new Resource();

				resource.setName(input);

				AcessType acessType = new AcessType();

				acessType.setName(Constants.ACCESS_TYPE_UPDATE);

				Boolean isPermitted = test.permissionController.isPermitted(test.user, resource, acessType);

				if (Boolean.TRUE.equals(isPermitted)) {

					String content = scan.nextLine();

					FileUtil.appendIntoFile(input, content);

					System.out.println("File Updated");

				} else {
					System.out.println("Not Permitted");
				}

			} else if ("5".equals(input)) {
				// Delete File

				System.out.println();
				System.out.print("Enter file name : ");
				input = scan.nextLine();

				Resource resource = new Resource();

				resource.setName(input);

				AcessType acessType = new AcessType();

				acessType.setName(Constants.ACCESS_TYPE_DELETE);

				Boolean isPermitted = test.permissionController.isPermitted(test.user, resource, acessType);

				if (Boolean.TRUE.equals(isPermitted)) {

					if (Boolean.TRUE.equals(FileUtil.deleteFile(input))) {
						// TODO: Delete permission
						System.out.println("File Deleted");
					} else {
						System.out.println("Not able to delete file");
					}

				} else {
					System.out.println("Not Permitted");
				}

			} else if (isDoctor) {

				if ("6".equals(input)) {
					// View Schedule

					System.out.println();
					System.out.print("Enter file name : ");
					input = scan.nextLine();

					Resource resource = new Resource();

					resource.setName(input);

					AcessType acessType = new AcessType();

					acessType.setName(Constants.ACCESS_TYPE_VIEW_SCHEDULE);

					Boolean isPermitted = test.permissionController.isPermitted(test.user, resource, acessType);

					if (Boolean.TRUE.equals(isPermitted)) {

						String fileContent = FileUtil.readFile(input);

						System.out.println(fileContent);

					} else {
						System.out.println("Not Permitted");
					}

				} else if ("7".equals(input)) {
					// Create Schedule

					// Check permissions - By default any user can create
					// resource

				} else if ("8".equals(input)) {
					// Update Schedule

					System.out.println();
					System.out.print("Enter file name : ");
					input = scan.nextLine();

					Resource resource = new Resource();

					resource.setName(input);

					AcessType acessType = new AcessType();

					acessType.setName(Constants.ACCESS_TYPE_UPDATE_SCHEDULE);

					Boolean isPermitted = test.permissionController.isPermitted(test.user, resource, acessType);

					if (Boolean.TRUE.equals(isPermitted)) {

						String content = scan.nextLine();

						FileUtil.appendIntoFile(input, content);

						System.out.println("File Updated");

					} else {
						System.out.println("Not Permitted");
					}

				} else if ("9".equals(input)) {
					// Delete Schedule

					System.out.println();
					System.out.print("Enter file name : ");
					input = scan.nextLine();

					Resource resource = new Resource();

					resource.setName(input);

					AcessType acessType = new AcessType();

					acessType.setName(Constants.ACCESS_TYPE_DELETE);

					Boolean isPermitted = test.permissionController.isPermitted(test.user, resource, acessType);

					if (Boolean.TRUE.equals(isPermitted)) {

						if (Boolean.TRUE.equals(FileUtil.deleteFile(input))) {
							// TODO: Delete permissions
							System.out.println("File Deleted");
						} else {
							System.out.println("Not able to delete file");
						}

					} else {
						System.out.println("Not Permitted");
					}

				} else {
					System.out.println("Invalid Option");
				}

			} else if (isAdmin) {

				if ("6".equals(input)) {
					// Create User

					System.out.println();
					System.out.print("Enter name : ");
					String name = scan.nextLine();
					System.out.print("Enter username : ");
					String username = scan.nextLine();
					System.out.print("Enter password : ");
					String password = scan.nextLine();

					User newUser = new User();

					newUser.setName(name);
					newUser.setPassword(password);
					newUser.setUsername(username);

					test.userController.saveUser(newUser);

				} else if ("7".equals(input)) {
					// Delete User

				} else if ("8".equals(input)) {
					// Create Role

					System.out.println();
					System.out.print("Enter name : ");
					String name = scan.nextLine();

					Role newRole = new Role();

					newRole.setName(name);

					test.roleController.saveRole(newRole);

				} else if ("9".equals(input)) {
					// Delete Role
				} else if ("10".equals(input)) {
					// Create User Role

					List<User> allUsers = test.userController.getAllUser();

					for (User myUser : allUsers) {
						System.out.println(myUser.toString());
					}

					System.out.println();
					System.out.println("Select one of the users");
					String selectedUserOpt = scan.nextLine();

					try {
						Integer selectedUser = Integer.parseInt(selectedUserOpt);

						if (selectedUser > allUsers.size()) {
							throw new Exception();
						}

						List<Role> allRoles = test.roleController.getAllRoles();

						for (Role myRole : allRoles) {
							System.out.println(myRole.toString());
						}

						System.out.println();
						System.out.println("Select one of the roles");
						String selectedRoleOpt = scan.nextLine();

						Integer selectedRole = Integer.parseInt(selectedRoleOpt);

						if (selectedRole > allRoles.size()) {
							throw new Exception();
						}

						// Assign role

						UserRole userRole = new UserRole();

						userRole.setStatus(Boolean.TRUE);
						userRole.setRole(test.roleController.getRoleById(selectedRole));
						userRole.setUser(test.userController.getUserById(selectedUser));

						test.userController.assignUserRole(userRole);

					} catch (Exception e) {
						System.out.println("Invalid selection");
						continue;
					}

				} else if ("11".equals(input)) {
					// Delete User Role
				} else if ("12".equals(input)) {
					// Create Permission

					// Select assign type

				} else if ("13".equals(input)) {
					// Delete Permission
				} else if ("14".equals(input)) {
					// Create Role Permission

					System.out.println();
					System.out.println("1) " + Constants.ACCESS_TYPE_CREATE);
					System.out.println("2) " + Constants.ACCESS_TYPE_DELETE);
					System.out.println("3) " + Constants.ACCESS_TYPE_UPDATE);
					System.out.println("4) " + Constants.ACCESS_TYPE_VIEW);
					System.out.println("5) " + Constants.ACCESS_TYPE_CREATE_SCHEDULE);
					System.out.println("6) " + Constants.ACCESS_TYPE_DELETE_SCHEDULE);
					System.out.println("7) " + Constants.ACCESS_TYPE_UPDATE_SCHEDULE);
					System.out.println("8) " + Constants.ACCESS_TYPE_VIEW_SCHEDULE);

					// Select role

					try {
						List<Role> allRoles = test.roleController.getAllRoles();

						for (Role myRole : allRoles) {
							System.out.println(myRole.toString());
						}

						System.out.println();
						System.out.println("Select one of the roles");
						String selectedRoleOpt = scan.nextLine();

						Integer selectedRole = Integer.parseInt(selectedRoleOpt);

						if (selectedRole > allRoles.size()) {
							throw new Exception();
						}

						List<Permission> allPermissions = test.permissionController.getAllPermissions();

						for (Permission permission : allPermissions) {
							// TODO: Do it in a better way
						}

						System.out.println();
						System.out.println("Select one of the roles");
						String selectedAccessTypeOpt = scan.nextLine();

						Integer selectedAccessType = Integer.parseInt(selectedAccessTypeOpt);

						if (selectedRole > 8) {
							throw new Exception();
						}

						/*RolePermission newRolePermission = new RolePermission();

						newRolePermission.setPermission(permission);*/

					} catch (Exception e) {
						System.out.println("Invalid selection");
						continue;
					}
				} else if ("15".equals(input)) {
					// Delete Role Permission
				} else {
					System.out.println("Invalid Option");
				}

			} else {
				System.out.println("Invalid Option");
			}
		}

	}

	public void printMenu() {
		System.out.println();
		System.out.println("Choose one of following options");
		System.out.println();
		System.out.println("1) List Files");
		System.out.println("2) Create File");
		System.out.println("3) View File");
		System.out.println("4) Update File");
		System.out.println("5) Delete File");

		// Print Doctor Menu
		if (Boolean.TRUE.equals(isDoctor)) {
			System.out.println("6) View Schedule");
			System.out.println("7) Create Schedule");
			System.out.println("8) Update Schedule");
			System.out.println("9) Delete Schedule");
		}

		// Print Doctor Menu
		if (Boolean.TRUE.equals(isAdmin)) {
			System.out.println("6) Create User");
			System.out.println("7) Delete User");
			System.out.println("10) Create User Role");
			System.out.println("11) Delete User Role");
			System.out.println("12) Create Permission");
			System.out.println("13) Delete Permission");
			System.out.println("14) Create Role Permission");
			System.out.println("15) Delete Role Permission");
		}

		System.out.println();

	}

}
