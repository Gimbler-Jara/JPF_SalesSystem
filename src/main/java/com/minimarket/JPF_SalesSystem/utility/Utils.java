package com.minimarket.JPF_SalesSystem.utility;

import org.mindrot.jbcrypt.BCrypt;

public class Utils {
	
	public static String extraerHash(String passwordFormulario) {
		return BCrypt.hashpw(passwordFormulario, BCrypt.gensalt());
	}

	public static boolean checkPassword(String passwordFormulario, String hashPassword) {
		return BCrypt.checkpw(passwordFormulario, hashPassword);
	}

}
