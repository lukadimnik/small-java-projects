package JdbcExercises;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import MovieDB_examples.ConnectionParameters;

public class SimpleStudentListProgram {

	public static void main(String[] args) {
		String username = ConnectionParameters.username;
		String password = ConnectionParameters.password;
		String databaseURL = ConnectionParameters.databaseURL;
		Connection dbConnection = null;

		System.out.println("=== LISTING ALL STUDENTS ===");

		try {
			// 1. Open a database connection
			// NB! MariaDB in HH: This does not work, if you have not opened an SSH tunnel
			// to the server.
			dbConnection = DriverManager.getConnection(databaseURL, username, password);

			// 2. Define the SQL query text with the statement object
			String sqlText = "SELECT id, firstname, lastname, streetaddress, postcode, postoffice FROM Student ORDER BY lastname";

			// 3. Create a statement object
			Statement statement = dbConnection.createStatement();

			// 4. Execute the SQL query
			ResultSet resultSet = statement.executeQuery(sqlText);
			boolean rowFound = false;

			// 5. Iterate through the result set
			// NB! resultSet.next() moves the cursor to the next available row in the result
			// It returns false if there are no more rows.
			while (resultSet.next()) {
				rowFound = true;

				// 6. Each column value has to be retrieved separately as below
				String firstName = resultSet.getString("firstname");
				String lastName = resultSet.getString("lastname");
				String street = resultSet.getString("streetaddress");
				String postCode = resultSet.getString("postcode");
				String postOffice = resultSet.getString("postoffice");

				System.out.println(firstName + " " + lastName + ", " + street + ", " + postCode + ", " + postOffice);
			}

			if (rowFound == false) {
				System.out.println("Currently, there are no students available.");
			}

		} catch (SQLException sqle) {
			// If any JDBC operation fails, we display an error message here
			System.out.println("===== Database error =====\n" + sqle.getMessage());

		} finally {
			// 7. The database connection should be closed as soon as we don't need it
			// anymore
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
