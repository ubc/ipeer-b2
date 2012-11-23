package ca.ubc.ctlt.ipeerb2.domain;

public class Role {
	public static final String PREIX = "role";
	public static final int SUPER_ADMIN = 1;
	public static final int ADMIN = 2;
	public static final int INSTRUCTOR = 3;
	public static final int TUTOR = 4;
	public static final int STUDENT = 5;
	
	public static final String[] ALL_ROLE_NAMES = { "", "Super Admin", "Admin", "Instructor", "Tutor", "Student" };
	public static final int[] ROLE_NAMES_FOR_MAPPING = {INSTRUCTOR, TUTOR, STUDENT};
}
