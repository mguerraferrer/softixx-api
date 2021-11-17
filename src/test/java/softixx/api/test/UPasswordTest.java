package softixx.api.test;

import softixx.api.util.UPassword;

public class UPasswordTest {
	public static void main(String[] args) {
		String user1 = "u$3rPwd1";
		String user2 = "u$3rPwd2";
		String user3 = "u$3rPwd3";
		String user4 = "u$3rPwd4";
		
		System.out.println("passwd1: " + UPassword.encryptedPassword(user1));
		System.out.println("passwd2: " + UPassword.encryptedPassword(user2));
		System.out.println("passwd3: " + UPassword.encryptedPassword(user3));
		System.out.println("passwd4: " + UPassword.encryptedPassword(user4));
	}
}