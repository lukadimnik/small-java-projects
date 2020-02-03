package JdbcExercises;

public class Student {

	private int id;
	private String firstName;
	private String lastName;
	private String street;
	private String postCode;
	private String postOffice;

	public Student(int id, String firstName, String lastName, String street, String postCode, String postOffice) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.postCode = postCode;
		this.postOffice = postOffice;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getStreet() {
		return street;
	}

	public String getPostCode() {
		return postCode;
	}

	public String getPostOffice() {
		return postOffice;
	}

}
