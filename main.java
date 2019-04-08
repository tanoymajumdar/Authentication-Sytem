import java.security.*;
import java.io.*;
import java.text.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Tanoy Majumdar this creates a database of the users of Comp2396 
 *
 */
public class main {

	private static ArrayList<User> DBase = new ArrayList<User>();

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException if the algorithm is not valid
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException {
		main obj = new main();
		obj.runningprog();
	}

	/**
	 * this method is used to display the menu of the program.
	 */
	public static void display() {
		System.out.println("Welcome to the COMP2396 Authentication system!");
		System.out.println("1. Authenticate user");
		System.out.println("2. Add user record");
		System.out.println("3. Edit user record");
		System.out.println("4. Reset user password");
		System.out.println("What would you like to perform?");
		System.out.println("Please enter your command (1-4, or 0 to terminate the system):");

	}

	/**
	 * @param user to add users to the database record the details of the user in
	 *             the database
	 * @throws NoSuchAlgorithmException exception if the algorithm isnt valid
	 * 
	 */
	private void Add_user(User user) throws NoSuchAlgorithmException {
		user = new User();
		System.out.print("Please enter your username: ");
		Scanner sc = new Scanner(System.in);
		user.setUsername(sc.next());
		System.out.println("Please enter your password: ");
		String a;
		String b;
		do {
			a = sc.next();
			if (checkpass(a) == true) {
				break;
			}
		} while (checkpass(a) == false);
		System.out.println("Please re-enter your password: ");
		do {
			b = sc.next();
			if (matchpass(a, b) == true) {
				break;
			}
		} while (matchpass(a, b) == false);

		String hashPass = user.hashthis(b);

		user.setPassword(hashPass);
		System.out.println("Please enter your full name: ");
		sc.next();
		user.setName(sc.nextLine());
		System.out.println("Please enter your email address: ");
		user.setEmail_id(sc.next());
		System.out.println("Please enter your phone number: ");
		user.setPhone_number(sc.next());
		DBase.add(user);
		System.out.println("Record added successfully!");
	}

	/**
	 * @param admin adds the administrator record in the database
	 * @throws NoSuchAlgorithmException exception if the algorithm isnt valid
	 * 
	 */
	private void addAdmin() throws NoSuchAlgorithmException {

		User admin = new User();
		Scanner sc = new Scanner(System.in);
		String a;
		String b;
		System.out.println("Administrator account not exist, please create the administrator account by setting up a password for it.");
		System.out.println("Please enter the password: ");

		do {
			a = sc.next();
			if (checkpass(a) == true) {
				break;
			}

		} while (checkpass(a) == false);
		System.out.println("Please re-enter the password: ");

		do {
			b = sc.next();
			if (matchpass(a, b) == true) {
				break;
			}

		} while (matchpass(a, b) == false);
		String hashPass = admin.hashthis(b);
		admin.setPassword(hashPass);
		admin.setUsername("administrator");
		admin.setEmail_id("admin@cs.hku.hk");
		DBase.add(admin);
		System.out.println("Administrator account created successfully!");

	}

	/**
	 * @return whether an administrator exists or not
	 * @throws NoSuchAlgorithmException exception if the algorithm isnt valid
	 */
	private boolean isAdmin() throws NoSuchAlgorithmException {
		User us = Find("administrator");
		if (us.getUsername() == "administrator") {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param user to modify the details of existing users in the database
	 * @throws NoSuchAlgorithmException exception if the algorithm isnt valid
	 * 
	 */
	private void Edit_user(User user) throws NoSuchAlgorithmException {

		Scanner sc = new Scanner(System.in);
		String b = "";
		String a = "";
		String x = user.getUsername();
		String y;
		String hashPass;
		String hashPass1;
		int c = 0;
		if (user.isAcc_locked() == false) {
			String l = user.getPassword();
			do {
				System.out.println("Please enter your password: ");
				y = sc.next();
				hashPass1 = user.hashthis(y);
				if (l.equals(hashPass1)) {
					user.setLogin_count(0);
					
					System.out.println("Login success! Hello " + x + "!");
					String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
					user.setLlogin(date);
					System.out.println("Please enter your new password:");
					do {
						a = sc.next();
						if (checkpass(a) == true) {
							break;
						}
					} while (checkpass(a) == false);

					

					do {
						System.out.println("Please re-enter your password: ");
						b = sc.next();
						if (matchpass(a, b) == true) {
							break;
						}

					} while (matchpass(a, b) == false);
					 hashPass = user.hashthis(b);
						user.setPassword(hashPass);
						System.out.println("Please enter your full name: ");
						sc.next();
						user.setName(sc.nextLine());
						System.out.println("Please enter your email address: ");
						user.setEmail_id(sc.next());
						DBase.add(user);
						System.out.println("Record update successfully! ");
					break;

				}
				else {
					System.out.println("Login failed!");
					user.setLogin_count(c++);
					break;
				}
			} 
			while (user.getLogin_count() < 3);			

		}
		else {
			System.out.println("User locked out contact administrator!");
		}

		if (user.login_count == 3) {
			System.out.println("Login failed! Your account has been locked!");
			user.acc_locked = true;
		}
	}

	/**
	 * @param user reset the password details of an existing user checks whether
	 *             administrator account exists administrator uses his password to
	 *             update the record
	 */
	private void reset_user(User user) throws NoSuchAlgorithmException {
		Scanner sc = new Scanner(System.in);
		String a;
		String b;
		String p;
		String q;
		String hashPass;
		String hashPass1;
		String date;

		if (isAdmin() == true) {
			User admin = Find("administrator");
			System.out.println("Please enter the password of administrator: ");
			do {
				p = sc.next();
				hashPass = user.hashthis(p);
				q = admin.getPassword();
				if (q.equals(hashPass)) {
					System.out.println("Please enter the user account need to reset: ");
					String u = sc.next();

					user = Find(u);

					if (!user.getUsername().equals( "notfound")) {
						System.out.println("Please enter the password: ");

						do {
							a = sc.next();
							if (checkpass(a) == true) {
								break;
							}

						} while (checkpass(a) == false);
						System.out.println("Please re-enter your password: ");

						do {
							b = sc.next();
							if (matchpass(a, b) == true) {
								break;
							}

						} while (matchpass(a, b) == false);
						System.out.println("Password update successfully!");
						date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
						user.setLlogin(date);
						hashPass1 = user.hashthis(b);
						user.setPassword(hashPass1);
					} else {
						System.out.println("User does not exist!");
						break;
					}

				} else {
					System.out.println("Incorrect password!");
				}
			} while (!q.equals(hashPass) );
		} else {
			addAdmin();
		}

	}

	/**
	 * @param user is the User object to authenticate the login details of the user.
	 * @throws NoSuchAlgorithmException exception if the algorithm isnt valid
	 * 
	 */
	private void authenticate_user(User user) throws NoSuchAlgorithmException {

		Scanner sc = new Scanner(System.in);
		String x;
		String y;
		String hashPass;
		String date;
		if (user.acc_locked == false) {
			do {
				System.out.println("Please enter your password: ");
				x = user.getPassword();
				y = sc.next();
				hashPass = user.hashthis(y);
				if (x.equals(hashPass)) {
					user.login_count = 0;
					date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
					user.setLlogin(date);
					break;
				} else {
					System.out.println("Login failed!");
					user.login_count++;
				}

			} while (user.login_count < 3);

		} else {
			System.out.println("Contact system administrator and reset passwrod!");
		}

		if (user.login_count == 3) {
			System.out.println("Login failed! Your account has been locked!");
			user.acc_locked = true;
		}

	}

	/**
	 * @param a finds a specific in the user
	 * @throws NoSuchAlgorithmException exception if the algorithm isn't valid
	 */
	private User Find(String a) throws NoSuchAlgorithmException

	{
		for (int i = 0; i < DBase.size(); i++) {
			User u = DBase.get(i);
			if (u.getUsername().equals(a)) {
				return u;
			}
		}
		User c = new User();
		c.setUsername("notfound");
		return c;
	}

	/**
	 * @param a is the password entered by the user to check whether it satisfies
	 *          all conditions
	 * @return will return whether the password satisfies the conditions or not
	 * @throws NoSuchAlgorithmException exception if the algorithm isnt valid
	 */
	private boolean checkpass(String a) throws NoSuchAlgorithmException {
		boolean l1 = false;
		boolean u1 = false;
		boolean n1 = false;
		User user = new User();
		for (int i = 0; i < a.length(); i++) {
			char x = a.charAt(i);
			if (Character.isLowerCase(x)) {
				l1 = true;
			}
			if (Character.isUpperCase(x)) {
				u1 = true;
			}
			if (Character.isDigit(x)) {
				n1 = true;
			}
		}
		if (!l1 || !u1 || !n1) {
			System.out.println("Your password has to fulfill: at least 1 small letter, 1 capital letter, 1 digit!");
			System.out.print("Please enter your new password: ");
			return false;
		} else {	
			return true;
			
		}

	}

	/**
	 * @param a is the first password entered by the user
	 * @param b is the re-entered password by the user
	 * @return to return whether the passwords match or not.
	 * @throws NoSuchAlgorithmException
	 */
	private boolean matchpass(String a, String b) throws NoSuchAlgorithmException

	{
		if (!a.equals(b)) {
			System.out.println("The passwords you have entered do not match!");
			return false;
		} else {
			return true;
		}

	}

	/**
	 * This method is used to execute the entire program.
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	private void runningprog() throws NoSuchAlgorithmException {
		display();
		Scanner sc = new Scanner(System.in);
		int n;
		int c = 0;
		do {
			n = sc.nextInt();
			switch (n) {
			case 0: {
				c = 1;
				break;
			}
			case 1: {
				if (DBase.size() == 0) {
					System.out.println("No user found!");
					break;
				} else {
					System.out.println("Please enter your username: ");
					String m = sc.next();
					User us = Find(m);
					if (us.getUsername() != "notfound") {
						authenticate_user(us);
						break;
					} else {
						System.out.println("Username does not exist!");
						break;
					}
				}
			}
			case 2: {
				User us = new User();
				Add_user(us);
				break;
			}
			case 3: {
				if (DBase.size() == 0) {
					System.out.println("No users available!");
					break;
				} else {
					System.out.print("Please enter your username: ");
					String x = sc.next();
					User us = Find(x);
					if (!us.getUsername().equals("notfound")) {
						Edit_user(us);
						break;
					}
				}
				break;
			}
			case 4: {
				if (DBase.size() == 0) {
					System.out.println("No users available!");
					break;
				} else {

					if (isAdmin() == true) {
						
						User us = new User();
						reset_user(us);
						break;
					} else {
						addAdmin();
					}

				}
				break;
			}
			default: {
				System.out.println("Wrong Choice entered");
				break;
			}

			}
			if (c == 0) {
				System.out.print("Please enter your command (1-4, or 0 to terminate the system) : ");
			}

		} while (n != 0);
	}

	/**
	 * @throws IOException if there arises error in Input and Output
	 * @throws NoSuchAlgorithmException if the algorithm not valid
	 * @throws ParseException if there arises error during parsing
	 */
	private void exec() throws IOException, NoSuchAlgorithmException, ParseException {
		File file = new File("./User.txt");
		try {
			JSONParser parser = new JSONParser();
			JSONObject o = (JSONObject) parser.parse(new FileReader(file));
			JSONArray jar = (JSONArray) o.get("user_array");
			Iterator<Object> iterator = jar.iterator();
			while (iterator.hasNext()) {
				Object obj = iterator.next();
				if (obj instanceof JSONObject) {
					String u;
					String p1;
					String na;
					String ea;
					String ph;
					int w;
					String d;
					boolean l;
					JSONObject json = (JSONObject) obj;
					u = json.get("username").toString();
					p1 = json.get("hash_password").toString();
					na = json.get("Full Name").toString();
					ea = json.get("Email").toString();
					ph = json.get("Phone Number").toString();
					w = Integer.parseInt(json.get("Fail Count").toString());
					d = json.get("Last Login Date").toString();
					l = Boolean.parseBoolean(json.get("Account Locked").toString());
					User user = new User();
					user.setAcc_locked(l);
					user.setEmail_id(ea);
					user.setLlogin(d);
					user.setLogin_count(w);
					user.setName(na);
					user.setPassword(p1);
					user.setPhone_number(ph);
					user.setUsername(u);
					
					DBase.add(user);
				}
			}
			runningprog();
		} catch (FileNotFoundException e) {
			file.createNewFile();
			runningprog();
		}
		PrintStream p = new PrintStream(file);
		JSONObject ob = new JSONObject();
		JSONArray ja = new JSONArray();
		for (int i = 0; i < DBase.size(); i++) {
			User a = DBase.get(i);
			JSONObject obj = new JSONObject();
			obj = CreateJSON(obj, a);
			ja.add(obj);
		}
		ob.put("user_array", ja);
		p.println(ob.toJSONString());
		p.close();
	}

	/**
	 * @param obj the JSON object to be created
	 * @param a   The user object that is to be used to create obj
	 * @return the JSON object after creation
	 */
	@SuppressWarnings("unchecked")
	private JSONObject CreateJSON(JSONObject obj, User a) {
		obj.put("username", a.getUsername());
		obj.put("hash_password", a.getPassword());
		obj.put("Full Name", a.getName());
		obj.put("Email", a.getEmail_id());
		obj.put("Phone Number", a.getPhone_number());
		obj.put("Fail Count", a.getLogin_count());
		obj.put("Last Login Date", a.getLlogin());
		obj.put("Account Locked", a.isAcc_locked());
		return obj;
	}


}
