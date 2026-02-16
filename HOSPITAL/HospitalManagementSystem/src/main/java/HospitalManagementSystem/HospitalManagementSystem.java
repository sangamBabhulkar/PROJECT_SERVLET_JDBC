package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {

	private static  final String url = "jdbc:mysql://localhost:3306/hospital";
	private static final String username ="root";
	private static final String password= "2004";

	public static void main(String[] args) {
	
		Scanner scanner = new Scanner(System.in);
		
		try {
			Connection connection = DriverManager.getConnection(url,username,password);
			Patient patient = new Patient(connection,scanner);
			Doctors doctor = new Doctors(connection,scanner);
			while(true) {
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				System.out.println("1. Add patient");
				System.out.println("2. view patient");
				System.out.println("3.view Doctor");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter your choice: ");
				int choice = scanner.nextInt();
				
				switch(choice) {
				case 1:
					//add patient
					patient.addPatient();
					System.out.println();
					break;
					
				case 2:
					//view patient
					patient.viewPatients();
					System.out.println();
					break;
				case 3:
					//view Doctor
					doctor.viewDoctors();
					System.out.println();
					break;
				case 4:
					//Book Appointment
					bookAppointment(patient,doctor,connection,scanner);
					System.out.println();
					break;
				case 5:
					return;
				default:
					System.out.println("Enter valid choice!!!");
				}
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void bookAppointment(Patient patient, Doctors doctors, Connection connection,Scanner scanner) {
		System.out.println("Enter patient id: ");
		int patientId = scanner.nextInt();
		System.out.println("Enter Doctor ID: ");
		int doctorId = scanner.nextInt();
		System.out.println("Enter appointment date(yyyy-mm-dd): ");
		String appointmentDate = scanner.next();
		if(patient.getPatientById(patientId) && doctors.getDoctortById(doctorId)) {
			if(checkDoctorAvailability(doctorId, appointmentDate,connection)) {
				String appointmentQuery = "insert into appointment(patient_id , doctor_id ,  appointment_date) values(?,?,?)";
				try {
					PreparedStatement preparedStatement =connection.prepareStatement(appointmentQuery);
					preparedStatement.setInt(1, patientId);
					preparedStatement.setInt(2, doctorId);
					preparedStatement.setString(3, appointmentDate);
					int rowsAffected = preparedStatement.executeUpdate();
					if(rowsAffected>0) {
						System.out.println("Appointment book");
					}
					else {
						System.out.println("Failed to book appointment ");
					}
							
				}catch (SQLException e) {
					e.printStackTrace();
				}
				
			}else {
				System.out.println("doctor not available on this date!!");
			}
		}
		else
		{
			System.out.println("Either doctor or patient doesn't exist!!!");
		}
	}
	
	
	public static boolean checkDoctorAvailability(int doctorId,String appointmentDate, Connection connection) {
		String query = "select count(*) from appointment where doctor_id = ? AND appointment_date = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			 preparedStatement.setInt(1, doctorId);
			 preparedStatement.setString(2,appointmentDate);
			 ResultSet resultset = preparedStatement.executeQuery();
			 if(resultset.next()) {
				 int count = resultset.getInt(1);
				 if(count==0) {
					 return true;
				 }
				
			 }
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	
	return false;

	}
}
