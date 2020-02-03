package MovieDB_examples;

import java.util.ArrayList;

/**
 * Database access example using the DAO design pattern.
 * NB! There is no JDBC-related code in this class!
 * 
 * @author Kari
 * @version 1.0
 */
public class Ex_4_Using_a_DAO_Class {
	
	public static void main(String[] args) {
		
		try {
			System.out.println("=== LISTING ALL MOVIES ===");
			
			// 1. Create a DAO object for accessing the data
			MovieDAO movieDAO = new MovieDAO();
			
			// 2. Get a list of all movies
			ArrayList<Movie> movieList = movieDAO.getAllMovies();
		
			for (Movie movie : movieList) {
				System.out.println(movie.getYear() + " " + movie.getName() + " (" + movie.getDirector() + ")");		
			}	
			

			int givenYear = 1942;
			System.out.println("\n=== LISTING MOVIES FOR THE GIVEN YEAR (" + givenYear + ") ===");
			
			// 3. Get a list of movies for the given year only
			movieList = movieDAO.getMoviesForGivenYear(givenYear);
			
			for (Movie movie : movieList) {
				System.out.println(movie.getYear() + " " + movie.getName() + " (" + movie.getDirector() + ")");		
			}
		
		} catch (Exception ex) {
			System.out.println("The database is temporarily unavailable. Please try again later. \n");
			System.out.println("=== System error message (for the developer's eyes only) === \n" + ex.getMessage());
		}
	}
}
// End