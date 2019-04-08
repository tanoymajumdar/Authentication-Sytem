import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Hash {
	
	private String username;
	private String password;
	private String name;
	private String email_id;
	private String Phone_number;
	int login_count;
	String llogin;
	String alg = "SHA-1";
	boolean acc_locked;
	
	
	User()
	{
		this.username = "";
		this.password = "";
		this.name = "";
		this.email_id = "";
		this.Phone_number = "";
		this.login_count = 0;
		this.acc_locked = false;
		this.llogin = "";
				
				
	}


	public String getLlogin() {
		return llogin;
	}


	public void setLlogin(String llogin) {
		this.llogin = llogin;
	}


	/**
	 * @return returns the username details of the specific User object
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username updates the username details of the specific User object in the database
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return returns the password details of the specific User object
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password updates the password details of the specific User object in the database
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return returns the full name details of the specific User object
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name updates the full name details of the specific User object in the database
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return returns the Email id details of the specific User object
	 */
	public String getEmail_id() {
		return email_id;
	}


	/**
	 * @param email_id updates the Email id details of the specific User object in the database
	 */
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}


	/**
	 * @return returns the Phone number details of the specific User object
	 */
	public String getPhone_number() {
		return Phone_number;
	}


	/**
	 * @param phone_number updates the phone number details of the specific User object in the database
	 */
	public void setPhone_number(String phone_number) {
		Phone_number = phone_number;
	}


	/**
	 * @return returns the number of attempts made by the user to login to the account
	 */
	public int getLogin_count() {
		return login_count;
	}


	/**
	 * @param login_count updates the number of failed attempts at login in of the User object in the database
	 */
	public void setLogin_count(int login_count) {
		this.login_count = login_count;
	}


	/**
	 * @return checks whether the account is locked due to excessive login attempts made by the specific User object
	 */
	public boolean isAcc_locked() {
		return acc_locked;
	}


	/**
	 * @param acc_locked updates whether the account is locked due to excessive login attempts made by the specific User object
	 */
	public void setAcc_locked(boolean acc_locked) {
		this.acc_locked = acc_locked;
	}


	public String hashthis(String a) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(alg);
		md.update(a.getBytes());
		byte[] hash = md.digest();
		StringBuilder sb = new StringBuilder();
		for (byte b : hash) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	


	
	
	

}
