package JdbcExercises;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import MovieDB_examples.ConnectionParameters;

public class SimpleStudentInsertProgram {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		String username = ConnectionParameters.username;
		String password = ConnectionParameters.password;
		String databaseURL = ConnectionParameters.databaseURL;
		Connection dbConnection = null;

		System.out.println("Enter student data");

		System.out.print("Id: ");
		int id = Integer.parseInt(input.nextLine());

		System.out.print("First name: ");
		String firstName = input.nextLine();

		System.out.print("Last name: ");
		String lastName = input.nextLine();

		System.out.print("Street: ");
		String street = input.nextLine();

		System.out.print("Postcode: ");
		String postCode = input.nextLine();

		System.out.print("Post office: ");
		String postOffice = input.nextLine();

		try {
			dbConnection = DriverManager.getConnection(databaseURL, username, password);

			String sqlText = "INSERT INTO Student (id, firstname, lastname, streetaddress, postcode, postoffice) VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlText);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, lastName);
			preparedStatement.setString(4, street);
			preparedStatement.setString(5, postCode);
			preparedStatement.setString(6, postOffice);

			preparedStatement.executeUpdate();

			System.out.println("\nStudent data saved successfully!");

		} catch (SQLException sqle) {

			// First, check if the problem is primary key violation (the error code is
			// vendor-dependent)
			if (sqle.getErrorCode() == ConnectionParameters.PK_VIOLATION_ERROR) {
				System.out.println("\nCannot insert student. " + "The student id (" + id + ") is already in use.");
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
		input.close();
	}

}
