package MutliInsertDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageIntoDB {
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hangingplantsdata";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "1234";

	public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException {
		FileInputStream fin =null;
		PreparedStatement pst = null;
		Connection con = null;
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
			System.out.println("Connected to database.");
			File file = new File("D:\\SaatFiles\\JFSD\\Project\\PlantImages\\HangingPlants\\peperomia_creeper.jpg");

			fin = new FileInputStream(file);

			String query = "INSERT INTO insertBLOB (name, price, image) VALUES (?, ?, ?)";
			pst = con.prepareStatement(query);
			pst.setString(1, "Plant1");
			pst.setDouble(2, 600.00);
			pst.setBinaryStream(3, fin, (int) file.length());

			int rowsInserted = pst.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Image inserted successfully into database.");
			} else {
				System.out.println("Failed to insert image into database.");
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (pst != null) {
					pst.close();
				}
				if (fin != null) {
					fin.close();
				}
				
				if (con != null) {
					con.close();
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}

	}

}
