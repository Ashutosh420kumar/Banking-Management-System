package BankingManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class User {
	private Connection connection;
	private Scanner scanner;
	User(Connection connection,Scanner scanner){
		this.connection=connection;
		this.scanner=scanner;
	}
	public void register() {
		scanner.nextLine();
		System.out.println("Full Name: ");
		String full_name = scanner.nextLine();
		System.out.println("Email: ");
		String email = scanner.nextLine();
		System.out.println("Password: ");
		String password = scanner.nextLine();
		if(user_exit(email)) {
			System.out.println("User Already exist for this email Address!!");
			return;
		}
		String register_query = "INSERT INTO USERS(EMAIL,FULL_NAME,PASSWORD) VALUES(?,?,?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(register_query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, full_name);
			preparedStatement.setString(3, password);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows > 0) {
                System.out.println("Registration Successfull!");
            } else {
                System.out.println("Registration Failed!");
            }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean user_exit(String email) {
		String query = "SELECT * FROM USERS WHERE EMAIL=?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public String login() {
		scanner.nextLine();
		System.out.println("Email: ");
		String email = scanner.nextLine();
		System.out.println("password: ");
		String password = scanner.nextLine();
		String login_query = "SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(login_query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return email;
			}
			else {
				return null;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
