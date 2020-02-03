package MovieDB_examples;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * This is a minimal example of executing an SQL SELECT statement.
 * In this example, the SELECT statement has no parameters.
 * 
 * @author Kari
 * @version 1.0
 */
public class Ex_1_Simple_Query {
	
	public static void main(String[] args) {		
		String username = ConnectionParameters.username;			 
		String password = ConnectionParameters.password;
		String databaseURL = ConnectionParameters.databaseURL;
		Connection dbConnection = null;	
		
		System.out.println("=== LISTING ALL MOVIES ===");
		
		try {
			// 1. Open a database connection 
			//    NB! MariaDB in HH: This does not work, if you have not opened an SSH tunnel to the server.
			dbConnection = DriverManager.getConnection(databaseURL, username, password);
							
			// 2. Define the SQL query text  with the statement object
			String sqlText = "SELECT year, name, director FROM Movie ORDER BY year, name";
			
			// 3. Create a statement object
			Statement statement = dbConnection.createStatement();
			
			// 4. Execute the SQL query
			ResultSet resultSet = statement.executeQuery(sqlText);
			boolean rowFound = false;
			
			// 5. Iterate through the result set
			//    NB! resultSet.next() moves the cursor to the next available row in the result
			//        It returns false if there are no more rows.
			while (resultSet.next()) {
				rowFound = true;
				
				// 6. Each column value has to be retrieved separately as below
				int year = resultSet.getInt("year");
				String name = resultSet.getString("name");
				String director = resultSet.getString("director");

				System.out.println(year + " " + name + " (" + director + ")");
			}

			if (rowFound == false) {
				System.out.println("Currently, there are no movies available.");
			}
			
		} catch (SQLException sqle) {
			// If any JDBC operation fails, we display an error message here
			System.out.println("===== Database error =====\n" + sqle.getMessage());
			
		} finally {
			// 7. The database connection should be closed as soon as we don't need it anymore
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