package JdbcExercises;

import java.util.Scanner;

public class StudentInsertProgram {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		try {
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

			StudentDAO studentDAO = new StudentDAO();
			Student student = new Student(id, firstName, lastName, street, postCode, postOffice);

			int blabla = studentDAO.insertStudent(student);
			if (blabla == 0) {
				System.out.println("Student is successfully imported!");
			} else if (blabla == 1) {
				System.out.println("This student ID is already in use.");
			} else if (blabla == -1) {
				System.out.println("Unknown error.");
			}

		} catch (Exception ex) {

			System.out.println("The database is temporarily unavailable. Please try again later. \n");
			System.out.println("=== System error message (for the developer's eyes only) === \n" + ex.getMessage());
		}
		input.close();
	}
}
