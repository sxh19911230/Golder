package connection.userInfo;

import java.awt.Color;
import java.sql.SQLException;

import connection.GolderConnection;
import Course.*;

/**
 * Database Connection For User Information
 * @author Forrest Sun
 * @author Wesley Pollek
 * @version Feb 10 2015
 *
 */
public class UsersConnection extends GolderConnection{
	
	
	
	/**
	 * Registration for users
	 * @param username
	 * @param password
	 * @param email
	 * @param major
	 * @return User ID
	 * @throws SQLException 
	 */
	public static int Register(String username, String password, String email, String major) throws SQLException {
		if (email.equals(""))
			email = "null";
		else
			email = "'" + email + '\'';

		if (major.equals(""))
			major = "null";
		else
			major = "'" + major + '\'';

			
		stmt.executeUpdate(String.format("INSERT INTO users (user_name,user_password,email_address,major) VALUES ('%s','%s',%s,%s);", 
					username, password, email, major));


		
		
		return getID(username,password);
	}	
	
	/**
	 * Login with username and password
	 * @param username
	 * @param password
	 * @return User ID 
	 * -1 if no username
	 * -2 if wrong password
	 * @throws SQLException 
	 */
	public static int getID(String username, String password) throws SQLException {
		int id = 0;
		

			
			if (stmt.execute(String.format("SELECT ID, user_password FROM `users` WHERE user_name='%s';", username))) {
		        rs = stmt.getResultSet();
		        if (rs.next()) {
			        if (!rs.getString("user_password").equals(password)) return -2; //password wrong
			        id = rs.getInt("ID");
		        } else {
		        	return -1; //no username
		        }
		    }
	
		
		
		return id;
		
	}
	
	/**
	 * Check if email is used
	 * @param email
	 * @return User Id
	 * -1 if no username
	 * @throws SQLException 
	 */
	public static int getIdByEmail(String email) throws SQLException {
		int id = 0;
		

			
			if (stmt.execute(String.format("SELECT ID FROM `users` WHERE email_address='%s';", email))) {
		        rs = stmt.getResultSet();
		        if (rs.next()) 
			        return rs.getInt("ID");
		        else
		        	return -1; //no username
		        
		    }


		
		return id;
	}
	
	/**
	 * get User Infomation
	 * @param ID User ID
	 * @return User
	 * @throws SQLException 
	 */
	public static User getInfo(int ID) throws SQLException{
		
		User u = null;
		

			
			if (stmt.execute(String.format("SELECT * FROM `users` WHERE ID='%s';", ID))) {
		        rs = stmt.getResultSet();
		        if (rs.isLast()) 
			        return null;
		        rs.next();
		        u = new User(rs.getString("user_name"), rs.getString("user_password"), rs.getString("email_address"), rs.getString("major"), rs.getInt("ID"));
		    }


		return u;
	}
	
	public static User getInfo(String userName, String password) throws SQLException{
		
		User u = null;
			
			if (stmt.execute(String.format("SELECT * FROM `users` WHERE user_name='%s' AND user_password='%s';", userName, password))) {
		        rs = stmt.getResultSet();
		        if (rs.isLast()) 
			        return null;
		        rs.next();
		        u = new User(rs.getString("user_name"), rs.getString("user_password"), rs.getString("email_address"), rs.getString("major"), rs.getInt("ID"));
		    }


		return u;
	}	
	
	/**
	 * update password
	 * @param ID
	 * @param pw
	 * @throws SQLException 
	 */
	public static void setPassword(int ID, String pw) throws SQLException {
		

		stmt.executeUpdate(String.format("UPDATE `users` SET user_password='%s' WHERE ID='%s';",pw,ID));

	}
	
	/**
	 * update email
	 * @param ID
	 * @param email
	 * @throws SQLException 
	 */
	public static void setEmail(int ID, String email) throws SQLException {

		stmt.executeUpdate(String.format("UPDATE `users` SET email_address='%s' WHERE ID='%s';",email,ID));

		
	}
	
	/**
	 * update major
	 * @param ID
	 * @param mj
	 * @throws SQLException 
	 */
	public static void setMajor(int ID, String mj) throws SQLException  {

		stmt.executeUpdate(String.format("UPDATE `users` SET major='%s' WHERE ID='%s';",mj,ID));

	}
	
	/**
	 * Save the User Data
	 * @param u
	 * @param c
	 */
	//TODO
	public static void saveCourse(User u, Course c) throws SQLException {
		
		int i = c.getLect().id;
		c.getLect().col.getRGB();
		
	}
	
	
	/*
	 * test 
	 
	
	public static void main(String[] args) {
		System.out.println(check());
	}
	*/
	

}
