package MovieDB_examples;

import java.sql.*;

/**
 * Creates (or recreates) and populates the Movie table in the target database
 * @author Kari
 * @version 1.0
 */
public class Create_Movie_Table {
	
	public static void main(String[] args) {
		String username = ConnectionParameters.username;			 
		String password = ConnectionParameters.password;
		String databaseURL = ConnectionParameters.databaseURL;
		Connection dbConnection = null;	
					
		try {
			// 1. Open a database connection
			//    NB! MariaDB in HH: This does not work, if you have not opened an SSH tunnel to the server.
			dbConnection = DriverManager.getConnection(databaseURL, username, password);
			
			System.out.println("CREATING AND POPULATING THE MOVIE TABLE IN " + 
					dbConnection.getMetaData().getDatabaseProductName());
			
			// 2. Create and prepare a statement object
			Statement statement = dbConnection.createStatement();
			
			// 3. Drop the Movie table
			try {
				statement.executeUpdate("DROP TABLE Movie");
				System.out.println("1. The existing Movie table is dropped.");
				
			} catch(SQLException sqle) {
				// This time, no message here. If the table does not exist yet, DROP TABLE fails.
			}
						
			// 4. Create the Movie table
			String sqlCreateTable = 
					"CREATE TABLE Movie (" +
					"id INTEGER PRIMARY KEY, " +
					"name VARCHAR(50) NOT NULL, " +
					"director VARCHAR(50) NOT NULL, " +
					"year INTEGER NOT NULL)";
					
			statement.executeUpdate(sqlCreateTable);
			System.out.println("2. The Movie table is created.");
			
			// 5. Populate the Movie table
			String sqlInsertMovie =
				"INSERT INTO Movie (id, name, director, year) VALUES " +
				"(10, 'Casablanca','Michael Curtiz', 1942), " +
				"(20, 'Citizen Kane','Orson Wells', 1941), " +
				"(30, 'The Talk of the Town','George Stevens', 1942)";
							
			statement.executeUpdate(sqlInsertMovie);
			System.out.println("3. The Movie table is populated with test data.");
			
		} catch (SQLException sqle) {
				System.out.println("===== Database error =====\n" + sqle.getMessage());
				
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