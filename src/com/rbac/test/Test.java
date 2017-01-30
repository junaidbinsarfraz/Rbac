package com.rbac.test;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.rbac.model.User;
import com.rbac.model.UserRole;
import com.rbac.util.Constants;
import com.rbac.util.FileUtil;

public class Test {

	private User user;
	private static Boolean isDoctor = Boolean.FALSE;
	private static Boolean isAdmin = Boolean.FALSE;

	public static void main(String[] args) throws IOException {

		/*File currentDirectory = new File(new File(".").getAbsolutePath());
		System.out.println(currentDirectory.getCanonicalPath());
		System.out.println(currentDirectory.getAbsolutePath());*/
		
		Test test = new Test();

		Scanner scan = new Scanner(System.in);

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
//				 List Files
				
				List<String> files = FileUtil.listFiles();
				
				for(String fileName : files) {
					System.out.println(fileName);
				}
				
			} else if ("2".equals(input)) {
//				Create File
			} else if ("3".equals(input)) {
//				View File
			} else if ("4".equals(input)) {
//				Update File
			} else if ("5".equals(input)) {
//				Delete File
			} else if (isDoctor) {

				if ("6".equals(input)) {
//					View Schedule
				} else if ("7".equals(input)) {
//					Create Schedule
				} else if ("8".equals(input)) {
//					Update Schedule
				} else if ("9".equals(input)) {
//					Delete Schedule
				} else {
					System.out.println("Invalid Option");
				}

			} else if (isAdmin) {

				if ("6".equals(input)) {
//					Create User
				} else if ("7".equals(input)) {
//					Delete User
				} else if ("8".equals(input)) {
//					Create Role
				} else if ("9".equals(input)) {
//					Delete Role
				} else if ("10".equals(input)) {
//					Create User Role
				} else if ("11".equals(input)) {
//					Delete User Role
				} else if ("12".equals(input)) {
//					Create Permission
				} else if ("13".equals(input)) {
//					Delete Permission
				} else if ("14".equals(input)) {
//					Create Role Permission
				} else if ("15".equals(input)) {
//					Delete Role Permission
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
			System.out.println("8) Create Role");
			System.out.println("9) Delete Role");
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
