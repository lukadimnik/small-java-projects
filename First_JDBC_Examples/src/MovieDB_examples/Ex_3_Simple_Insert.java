package MovieDB_examples;

import java.sql.*;

/**
 * A minimal example of executing an SQL INSERT statement
 * 
 * @author Kari
 * @version 1.0
 */
public class Ex_3_Simple_Insert {
	
	public static void main(String[] args) {
		String username = ConnectionParameters.username;			 
		String password = ConnectionParameters.password;
		String databaseURL = ConnectionParameters.databaseURL;
		Connection dbConnection = null;	
 
		System.out.println("=== INSERTING A NEW MOVIE === \n");
		
		int movieId = 55;
		String movieName = "Traffic";
		String movieDirector = "Jaques Tati";
		int movieYear = 1971;
				
		try {
			dbConnection = DriverManager.getConnection(databaseURL, username, password);
							
			String sqlText = "INSERT INTO Movie (id, name, director, year) VALUES (?, ?, ?, ?)";
			
			PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlText);
			preparedStatement.setInt(1, movieId);
			preparedStatement.setString(2, movieName);
			preparedStatement.setString(3, movieDirector);
			preparedStatement.setInt(4, movieYear);
			
			preparedStatement.executeUpdate();

			System.out.println("The movie is succesfully inserted.");
			
		} catch (SQLException sqle) {
			
			// First, check if the problem is primary key violation (the error code is vendor-dependent)
			if (sqle.getErrorCode() == ConnectionParameters.PK_VIOLATION_ERROR) {
				System.out.println("Cannot insert the movie. " +
			        "The movie id (" + movieId + ") is already in use.");
			} else {
				System.out.println("===== Database error =====\n" + sqle.getMessage());
			}
			
		} finally {
			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException sqle) {
					System.out.println("\nClose connection failed. \n" + sqle.getMessage());
				}
			}
		}
		
	}
}
// End