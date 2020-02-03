package MovieDB_examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DAO class for accessing movies. 
 * NB! There is no user input/output in this class!
 * 
 * @author Kari
 * @version 1.1 2017-10-22
 */
public class MovieDAO 
{
	private final String username;			 
	private final String password; 
	private final String databaseURL;

	public MovieDAO() throws Exception {
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
	 * @throws SQLException
	 */
	private Connection openConnection() throws SQLException	{
		Connection dbConnection = DriverManager.getConnection(databaseURL, username, password);
		return dbConnection;
	}
	
	/**
	 * Closes an existing database connection 
	 * @throws SQLException
	 */
	private void closeConnection(Connection dbConnection) throws SQLException {
		if (dbConnection != null) {
			dbConnection.close();
		}
	}
		
	/**
	 * Retrieves all movies from the database. 
	 * @return ArrayList<Movie> - a List of Movies
	 * @throws SQLException
	 */
	public ArrayList<Movie> getAllMovies() throws SQLException {
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		Connection dbConnection = null;
		
		try {
			dbConnection = openConnection();

			String sqlText = 
					"SELECT id, name, director, year " +
					"FROM Movie ORDER BY year, name";
			
			PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlText);
			
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String name = resultSet.getString("name");
				String director = resultSet.getString("director");
				int year = resultSet.getInt("year");

				movieList.add(new Movie(id, name, director, year));
			}
			
			return movieList;
			
		} catch (SQLException sqle)	{
			throw sqle;	// Let the caller decide what to do with the exception
			
		} finally {
			closeConnection(dbConnection);
		}
	}
	
	/**
	 * Retrieves movies from the database for the given year 
	 * @return ArrayList<Movie> - a List of Movies
	 * @throws SQLException
	 */
	public ArrayList<Movie> getMoviesForGivenYear(int givenYear) throws SQLException {
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		Connection dbConnection = null;
		
		try {
			dbConnection = openConnection();

			String sqlText = 
					"SELECT id, name, director, year " +
					"FROM Movie WHERE year = ? ORDER BY name";
			
			PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlText);
			preparedStatement.setInt(1, givenYear);
			
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String name = resultSet.getString("name");
				String director = resultSet.getString("director");
				int year = resultSet.getInt("year");

				movieList.add(new Movie(id, name, director, year));
			}
			
			return movieList;
			
		} catch (SQLException sqle)	{
			throw sqle;  // Let the caller decide what to do with the exception
			
		} finally {
			closeConnection(dbConnection);
		}
	}
}
// End