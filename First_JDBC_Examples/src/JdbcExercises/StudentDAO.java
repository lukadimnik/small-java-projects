package JdbcExercises;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import MovieDB_examples.ConnectionParameters;

// * DAO class for accessing movies. NB! There is no user input/output in this
// * class!

public class StudentDAO {
	private final String username;
	private final String password;
	private final String databaseURL;

	public StudentDAO() throws Exception {
		username = ConnectionParameters.username;
		password = ConnectionParameters.password;
		databaseURL = ConnectionParameters.databaseURL;

		// In *Tomcat 8* the JDBC driver must be explicitly loaded as below
		try {
			Class.forName(ConnectionParameters.jdbcDriver);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Opens a new database connection
	 * 
	 * @throws SQLException
	 */
	private Connection openConnection() throws SQLException {
		Connection dbConnection = DriverManager.getConnection(databaseURL, username, password);
		return dbConnection;
	}

	/**
	 * Closes an existing database connection
	 * 
	 * @throws SQLException
	 */
	private void closeConnection(Connection dbConnection) throws SQLException {
		if (dbConnection != null) {
			dbConnection.close();
		}
	}

	// Retrieves all students from the database

	public ArrayList<Student> getAllStudents() throws SQLException {
		ArrayList<Student> studentList = new ArrayList<Student>();
		Connection dbConnection = null;

		try {
			dbConnection = openConnection();

			String sqlText = "SELECT id, firstname, lastname, streetaddress, postcode, postoffice "
					+ "FROM Student ORDER BY lastname";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlText);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstname");
				String lastName = resultSet.getString("lastname");
				String street = resultSet.getString("streetaddress");
				String postCode = resultSet.getString("postcode");
				String postOffice = resultSet.getString("postoffice");

				studentList.add(new Student(id, firstName, lastName, street, postCode, postOffice));
			}

			return studentList;

		} catch (SQLException sqle) {
			throw sqle; // Let the caller decide what to do with the exception

		} finally {
			closeConnection(dbConnection);
		}
	}

	public ArrayList<String> getAllStudentsJSON() throws SQLException {
		ArrayList<String> studentListJSON = new ArrayList<String>();
		Connection dbConnection = null;

		try {
			dbConnection = openConnection();

			String sqlText = "SELECT id, firstname, lastname, streetaddress, postcode, postoffice "
					+ "FROM Student ORDER BY lastname";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlText);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstname");
				String lastName = resultSet.getString("lastname");
				String street = resultSet.getString("streetaddress");
				String postCode = resultSet.getString("postcode");
				String postOffice = resultSet.getString("postoffice");

				studentListJSON.add(
						"{ id:" + id + ", firstName:\"" + firstName + "\", lastName:\"" + lastName + "\", street:\""
								+ street + "\", postcode:\"" + postCode + "\", postOffice:\"" + postOffice + "\" }");

			}

			return studentListJSON;

		} catch (SQLException sqle) {
			throw sqle; // Let the caller decide what to do with the exception

		} finally {
			closeConnection(dbConnection);
		}
	}

//	public String getAllStudentsJSON() throws SQLException {
//		Connection dbConnection = null;
//		String JSONString = "";
//
//		try {
//			dbConnection = openConnection();
//
//			String sqlText = "SELECT id, firstname, lastname, streetaddress, postcode, postoffice "
//					+ "FROM Student ORDER BY lastname";
//
//			PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlText);
//
//			ResultSet resultSet = preparedStatement.executeQuery();
//
//			while (resultSet.next()) {
//
//				int id = resultSet.getInt("id");
//				String firstName = resultSet.getString("firstname");
//				String lastName = resultSet.getString("lastname");
//				String street = resultSet.getString("streetaddress");
//				String postCode = resultSet.getString("postcode");
//				String postOffice = resultSet.getString("postoffice");
//
//				JSONString += "{ id:" + id + ", firstName:\"" + firstName + "\", lastName:\"" + lastName + "\", street:\""
//						+ street + "\", postcode:\"" + postCode + "\", postOffice:\"" + postOffice + "\" }\n";
//
//			}
//
//			return JSONString;
//
//		} catch (SQLException sqle) {
//			throw sqle; // Let the caller decide what to do with the exception
//
//		} finally {
//			closeConnection(dbConnection);
//		}
//	}

	// Retrieves student by id

	public Student getStudentById(int id) throws SQLException {
		ArrayList<Student> studentList = new ArrayList<Student>();
		Connection dbConnection = null;

		try {
			dbConnection = openConnection();

			String sqlText = "SELECT id, firstname, lastname, streetaddress, postcode, postoffice "
					+ "FROM Student WHERE id = ? ORDER BY firstname";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlText);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstname");
				String lastName = resultSet.getString("lastname");
				String street = resultSet.getString("streetaddress");
				String postCode = resultSet.getString("postcode");
				String postOffice = resultSet.getString("postoffice");

				studentList.add(new Student(id, firstName, lastName, street, postCode, postOffice));
			}
			Student studentWithId = studentList.get(0);
			return studentWithId;

		} catch (SQLException sqle) {
			throw sqle; // Let the caller decide what to do with the exception

		} finally {
			closeConnection(dbConnection);
		}
	}

	// Insert new student row

	public int insertStudent(Student student) throws SQLException {
		Connection dbConnection = null;

		try {
			dbConnection = openConnection();

			String sqlText = "INSERT INTO Student (id, firstname, lastname, streetaddress, postcode, postoffice) VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlText);
			preparedStatement.setInt(1, student.getId());
			preparedStatement.setString(2, student.getFirstName());
			preparedStatement.setString(3, student.getLastName());
			preparedStatement.setString(4, student.getStreet());
			preparedStatement.setString(5, student.getPostCode());
			preparedStatement.setString(6, student.getPostOffice());

			preparedStatement.executeUpdate();

			return 0;

		} catch (SQLException sqle) {
			if (sqle.getErrorCode() == ConnectionParameters.PK_VIOLATION_ERROR) {
//				System.out.println("\nCannot insert student. " + "The student id (" + student.getId() + ") is already in use.");
				return 1;
			} else {
				System.out.println("===== Database error =====\n" + sqle.getMessage());
				return -1;
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