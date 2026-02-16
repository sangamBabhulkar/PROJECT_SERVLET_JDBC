package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {

	private Connection connection;
	private Scanner scanner;
	
	
	public Doctors(Connection connection,Scanner scanner) {
		this.connection=connection;
		this.scanner=scanner;
	}
	
	
	public void viewDoctors() {
		String query = "select * from doctors";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultset = preparedStatement.executeQuery();
			System.out.println("Doctors: ");
			System.out.println("+-----------+--------------------------+------------------+");
			System.out.println("|doctors id | Name                      |specialization   |");
			System.out.println("+-----------+--------------------------+-------+------");
			while(resultset.next()) {
				int id= resultset.getInt("id");
				String name = resultset.getString("Name");
				String specialization = resultset.getString("specialization");
				
				System.out.printf("|%-11s|%-19s|%-12s|\n",id,name,specialization);
				System.out.println("+-----------+--------------------------+-------+------");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getDoctortById(int id) {
		String query = "select * from doctors where id = ?";
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
