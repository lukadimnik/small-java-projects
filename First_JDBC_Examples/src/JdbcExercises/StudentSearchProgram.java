package JdbcExercises;

import java.util.Scanner;

public class StudentSearchProgram {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		try {
			// 1. Create a DAO object for accessing the data
			StudentDAO studentDAO = new StudentDAO();

			// 2. Get a student with id
			System.out.print("Enter student id: ");
			int id = Integer.parseInt(input.nextLine());

			Student student = studentDAO.getStudentById(id);

			System.out.println(student.getId() + ", " + student.getFirstName() + " " + student.getLastName() + ", "
					+ student.getStreet() + ", " + student.getPostCode() + ", " + student.getPostOffice());

		} catch (Exception ex) {
			System.out.println("The database is temporarily unavailable. Please try again later. \n");
			System.out.println("=== System error message (for the developer's eyes only) === \n" + ex.getMessage());
		}
		input.close();
	}
}
// End