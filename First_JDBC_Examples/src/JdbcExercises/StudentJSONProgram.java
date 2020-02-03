package JdbcExercises;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentJSONProgram {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		try {
			System.out.println("=== LISTING ALL STUDENTS ===");

			// 1. Create a DAO object for accessing the data
			StudentDAO studentDAO = new StudentDAO();

			// 2. Get a list of all students
			ArrayList<String> studentListJSON = studentDAO.getAllStudentsJSON();

			for (String string : studentListJSON) {
				System.out.println(string);
			}

		} catch (Exception ex) {
			System.out.println("The database is temporarily unavailable. Please try again later. \n");
			System.out.println("=== System error message (for the developer's eyes only) === \n" + ex.getMessage());
		}

		input.close();
	}
}