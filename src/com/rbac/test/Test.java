package com.rbac.test;

import java.io.IOException;
import java.util.ArrayList;
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
				isAdmin = Boolean.TRUE;
				break;
			}
		}

		while (true) {

			test.printMenu();

			String input = scan.nextLine();

			if (Boolean.FALSE.equals(isAdmin)) {

				if ("1".equals(input)) {
					// List Files

					List<String> files = FileUtil.listFiles();

					for (String fileName : files) {
						System.out.println(fileName);
					}

				} else if ("2".equals(input)) {
					// Create File

					// Add in resource table
					System.out.print("Enter file name : ");
					String fileName = scan.nextLine();

					FileUtil.writeIntoFile(fileName, "");

					Resource resource = new Resource();

					resource.setName(fileName);
					resource.setStatus(Boolean.TRUE);

					test.permissionController.saveResource(resource);

					// Permission permission = new Permission();

					resource = test.permissionController.getResources(resource) != null ? test.permissionController.getResources(resource).get(0)
							: null;

					if (resource == null) {
						System.out.println("Unable to save resource");
						continue;
					}

					List<AcessType> acessTypes = test.permissionController.getAllAcessTypes();

					List<Permission> permissions = new ArrayList<Permission>();

					for (AcessType acessType : acessTypes) {

						if (!(Constants.ACCESS_TYPE_CREATE.equals(acessType.getName()) || Constants.ACCESS_TYPE_DELETE.equals(acessType.getName())
								|| Constants.ACCESS_TYPE_UPDATE.equals(acessType.getName())
								|| Constants.ACCESS_TYPE_VIEW.equals(acessType.getName()))) {
							continue;
						}

						Permission newPermission = new Permission();

						newPermission.setAccesstypeid(acessType.getId());
						newPermission.setResourceid(resource.getId());
						newPermission.setStatus(Boolean.TRUE);

						permissions.add(newPermission);
					}

					for (Permission newPermission : permissions) {
						test.permissionController.savePermission(newPermission);
					}

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
							test.deleteResource(resource);
							
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

						// Add in resource table
						System.out.print("Enter schedule file name : ");
						String fileName = scan.nextLine();

						FileUtil.writeIntoFile(fileName, "");

						Resource resource = new Resource();

						resource.setName(fileName);
						resource.setStatus(Boolean.TRUE);

						test.permissionController.saveResource(resource);

						// Permission permission = new Permission();

						resource = test.permissionController.getResources(resource) != null ? test.permissionController.getResources(resource).get(0)
								: null;

						if (resource == null) {
							System.out.println("Unable to save resource");
							continue;
						}

						List<AcessType> acessTypes = test.permissionController.getAllAcessTypes();

						List<Permission> permissions = new ArrayList<Permission>();

						for (AcessType acessType : acessTypes) {

							if (Constants.ACCESS_TYPE_CREATE.equals(acessType.getName()) || Constants.ACCESS_TYPE_DELETE.equals(acessType.getName())
									|| Constants.ACCESS_TYPE_UPDATE.equals(acessType.getName())
									|| Constants.ACCESS_TYPE_VIEW.equals(acessType.getName())) {
								continue;
							}

							Permission newPermission = new Permission();

							newPermission.setAccesstypeid(acessType.getId());
							newPermission.setResourceid(resource.getId());

							permissions.add(newPermission);
						}

						for (Permission newPermission : permissions) {
							test.permissionController.savePermission(newPermission);
						}

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
								test.deleteResource(resource);
								
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

				}
			} else {

				if ("1".equals(input)) {
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

				} else if ("2".equals(input)) {
					// Delete User
					try {
						List<User> users = test.userController.getAllUser();

						for (User user : users) {
							System.out.println(user.toString());
						}

						System.out.println();
						System.out.println("Select one of the users");
						String selectedUserOpt = scan.nextLine();

						Integer selectedUserInt = Integer.parseInt(selectedUserOpt);

						User myUser = test.userController.getUserById(selectedUserInt);

						if (myUser == null) {
							throw new Exception();
						}

						// Get User's roles

						UserRole userRole = new UserRole();

						userRole.setUser(myUser);

						List<UserRole> userRoles = test.userController.getUserRoles(userRole);

						for (UserRole myUserRole : userRoles) {
							if (myUser.equals(myUserRole.getUser())) {
								test.userController.deleteUserRole(myUserRole);
							}
						}

						test.userController.deleteUser(myUser);

					} catch (Exception e) {
						System.out.println("Invalid selection");
					}

				} else if ("3".equals(input)) {
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

						User myUser = test.userController.getUserById(selectedUser);

						if (myUser == null) {
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

						Role role = test.roleController.getRoleById(selectedRole);

						if (role == null) {
							throw new Exception();
						}

						// Assign user role

						UserRole userRole = new UserRole();

						userRole.setStatus(Boolean.TRUE);
						userRole.setRole(role);
						userRole.setUser(myUser);

						test.userController.assignUserRole(userRole);

					} catch (Exception e) {
						System.out.println("Invalid selection");
					}

				} else if ("4".equals(input)) {
					// Delete User Role

					try {
						List<UserRole> userRoles = test.userController.getAllUserRoles();

						for (UserRole userRole : userRoles) {
							System.out.println(userRole.toString());
						}

						System.out.println();
						System.out.println("Select one of the user-roles");
						String selectedUserRoleOpt = scan.nextLine();

						Integer selectedUserRole = Integer.parseInt(selectedUserRoleOpt);

						UserRole userRole = test.userController.getUserRoleById(selectedUserRole);

						if (userRole == null) {
							throw new Exception();
						}

						test.userController.deleteUserRole(userRole);

					} catch (Exception e) {
						System.out.println("Invalid selection");
					}

				} else if ("5".equals(input)) {
					// Create Permission
					try {
						System.out.println();

						List<AcessType> acessTypes = test.permissionController.getAllAcessTypes();

						for (AcessType acessType : acessTypes) {
							System.out.println(acessType.toString());
						}

						System.out.println();
						System.out.println("Select one of the access types");
						String selectedAcessTypeOpt = scan.nextLine();

						Integer selectedAcessTypeInt = Integer.parseInt(selectedAcessTypeOpt);

						AcessType acessType = test.permissionController.getAcessTypeById(selectedAcessTypeInt);

						if (acessType == null) {
							throw new Exception();
						}

						List<Resource> resources = test.permissionController.getAllResources();

						for (Resource resource : resources) {
							System.out.println(resource.toString());
						}

						System.out.println();
						System.out.println("Select one of the resources");
						String selectedResourceOpt = scan.nextLine();

						Integer selectedResourceInt = Integer.parseInt(selectedResourceOpt);

						Resource resource = test.permissionController.getResourceById(selectedResourceInt);

						if (resource == null) {
							throw new Exception();
						}

						Permission permission = new Permission();

						permission.setAccesstypeid(acessType.getId());
						permission.setResourceid(resource.getId());

						test.permissionController.savePermission(permission);

					} catch (Exception e) {
						System.out.println("Invalid selection");
					}

				} else if ("6".equals(input)) {
					// Delete Permission

					List<Permission> allPermissions = test.permissionController.getAllPermissions();

					for (Permission permission : allPermissions) {

						AcessType acessType = test.permissionController.getAcessTypeById(permission.getId());
						Resource resource = test.permissionController.getResourceById(permission.getResourceid());

						System.out.println(permission.getId() + ") Can " + acessType.getName() + " " + resource.getName());

					}

					System.out.println();
					System.out.println("Select one of the permissions");
					String selectedPermissionOpt = scan.nextLine();

					Integer selectedPermissionInt = Integer.parseInt(selectedPermissionOpt);

					Permission selectedPermission = test.permissionController.getPermissionById(selectedPermissionInt);

					if (selectedPermission == null) {
						System.out.println("Invalid selection");
						continue;
					}

					// Delete Role Permission

					RolePermission rolePermission = new RolePermission();

					rolePermission.setPermission(selectedPermission);

					List<RolePermission> rolePermissions = test.roleController.getRolePermissoins(rolePermission);

					for (RolePermission myRolePermission : rolePermissions) {
						if (selectedPermission.equals(myRolePermission.getPermission())) {
							test.roleController.deleteRolePermission(myRolePermission);
						}
					}

					test.permissionController.deletePermission(selectedPermission);

				} else if ("7".equals(input)) {
					// Create Role Permission

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

						Role role = test.roleController.getRoleById(selectedRole);

						if (role == null) {
							throw new Exception();
						}

						List<Permission> allPermissions = test.permissionController.getAllPermissions();

						for (Permission permission : allPermissions) {

							AcessType acessType = test.permissionController.getAcessTypeById(permission.getAccesstypeid());
							Resource resource = test.permissionController.getResourceById(permission.getResourceid());

							System.out.println(permission.getId() + ") Can " + acessType.getName() + " " + resource.getName());

						}

						System.out.println();
						System.out.println("Select one of the permissions");
						String selectedPermissionOpt = scan.nextLine();

						Integer selectedPermissionInt = Integer.parseInt(selectedPermissionOpt);

						Permission selectedPermission = test.permissionController.getPermissionById(selectedPermissionInt);

						if (selectedPermission == null) {
							throw new Exception();
						}

						RolePermission rolePermission = new RolePermission();

						rolePermission.setPermission(selectedPermission);
						rolePermission.setRole(role);
						rolePermission.setStatus(Boolean.TRUE);

						test.roleController.saveRolePermission(rolePermission);

					} catch (Exception e) {
						System.out.println("Invalid selection");
					}
				} else if ("8".equals(input)) {
					// Delete Role Permission

					try {

						List<RolePermission> rolePermissions = test.roleController.getAllRolePermissoins();

						for (RolePermission rolePermission : rolePermissions) {

							Permission permission = rolePermission.getPermission();

							AcessType acessType = test.permissionController.getAcessTypeById(permission.getAccesstypeid());
							Resource resource = test.permissionController.getResourceById(permission.getResourceid());

							System.out.println(rolePermission.getId() + ") Role " + rolePermission.getRole().getName() + " can " + acessType.getName()
									+ " " + resource.getName());
						}

						System.out.println();
						System.out.println("Select one of the role-permissions");
						String selectedRolePermissionOpt = scan.nextLine();

						Integer selectedRolePermissionInt = Integer.parseInt(selectedRolePermissionOpt);

						RolePermission rolePermission = test.roleController.getRolePermissionById(selectedRolePermissionInt);

						if (rolePermission == null) {
							throw new Exception();
						}

						test.roleController.deleteRolePermission(rolePermission);

					} catch (Exception e) {
						System.out.println("Invalid selection");
					}

				} else {
					System.out.println("Invalid Option");
				}

			}
		}

	}

	private void deleteResource(Resource resource) {
		// Get resource
		
		List<Resource> resources = this.permissionController.getResources(resource);
		
		if(resources == null || Boolean.TRUE.equals(resources.isEmpty())) {
			return;
		}
		
		resource = resources.get(0);
		
		// Get permission by resource id
		List<Permission> permissions = this.permissionController.getAllPermissions();
		
		for(Permission permission: permissions) {
			if(resource.getId().equals(permission.getResourceid())) {
				
				// Get role permission by permission id
				List<RolePermission> rolePermissions = this.roleController.getAllRolePermissoins();
				
				for(RolePermission rolePermission : rolePermissions) {
					if(permission.equals(rolePermission.getPermission())) {
						
						// Delete Role Permission
						this.roleController.deleteRolePermission(rolePermission);
					}
				}
				// Delete Permission 
				this.permissionController.deletePermission(permission);
			}
		}
		
		// Delete Resource
		this.permissionController.deleteResource(resource);
	}

	public void printMenu() {
		System.out.println();
		System.out.println("Choose one of following options");
		System.out.println();

		if (Boolean.FALSE.equals(isAdmin)) {
			System.out.println("1) List Files");
			System.out.println("2) Create File");
			System.out.println("3) View File");
			System.out.println("4) Update File");
			System.out.println("5) Delete File");
		}

		// Print Doctor Menu
		if (Boolean.TRUE.equals(isDoctor)) {
			System.out.println("6) View Schedule");
			System.out.println("7) Create Schedule");
			System.out.println("8) Update Schedule");
			System.out.println("9) Delete Schedule");
		}

		// Print Doctor Menu
		if (Boolean.TRUE.equals(isAdmin)) {
			System.out.println("1) Create User");
			System.out.println("2) Delete User");
			System.out.println("3) Create User Role");
			System.out.println("4) Delete User Role");
			System.out.println("5) Create Permission");
			System.out.println("6) Delete Permission");
			System.out.println("7) Create Role Permission");
			System.out.println("8) Delete Role Permission");
		}

		System.out.println();

	}

}
