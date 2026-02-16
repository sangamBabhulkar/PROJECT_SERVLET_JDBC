package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

public class Patient {

	private Connection connection;
	private Scanner scanner;
	
	public Patient(Connection connection, Scanner scanner) {
		this.connection=connection;
		this.scanner=scanner;
	}
	
	public void addPatient()
	{
		System.out.print("Enter patinet Name: ");
		String name = scanner.next();
		System.out.print("Enter patient Age: ");
		int age = scanner.nextInt();
		System.out.println("Enter Gender: ");
		String gender = scanner.next();
		
		try {
			String query= "insert into patients(name,age,gender)values(?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3, gender);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows>0) {
				System.out.println("pateint added successfully");
			}
			else {
				System.out.println("failed to added pateint..");
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void viewPatients() {
		String query = "select * from patients";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultset = preparedStatement.executeQuery();
			System.out.println("pateints: ");
			System.out.println("+-----------+--------------------------+-------+------");
			System.out.println("|patient id | Name                      |Age   |Gender");
			System.out.println("+-----------+--------------------------+-------+------");
			while(resultset.next()) {
				int id= resultset.getInt("id");
				String name = resultset.getString("Name");
				int age = resultset.getInt("age");
				String gender = resultset.getString("gender");
				System.out.printf("|%-11s|%-19s|%-10s|%-12s|\n",id,name,age,gender);
				System.out.println("+-----------+--------------------------+-------+------");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getPatientById(int id) {
		String query = "select * from patients where id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultset = preparedStatement.executeQuery();
			if(resultset.next()) {
				return true;
			}else {
				return false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
