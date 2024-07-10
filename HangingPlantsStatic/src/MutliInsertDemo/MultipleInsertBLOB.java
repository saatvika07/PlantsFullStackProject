package MutliInsertDemo;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MultiInsertBlobIntoDB{
    public static void main(String[] args) throws CsvValidationException {
        Connection con = null;
        PreparedStatement pst = null;
        String csvFilePath = "D:\\SaatFiles\\JFSD\\Project\\plants.csv";

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFilePath)).withSkipLines(1).build()) {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            String mysqlUrl = "jdbc:mysql://localhost:3306/hangingplantsdata";
            con = DriverManager.getConnection(mysqlUrl, "root", "1234");
            System.out.println("Connection established...");

            // Prepare the SQL insert statement
            String query = "INSERT INTO plants (pname, original_price, discount_price, category, rating, image) VALUES (?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(query);

            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                String pname = nextLine[0];
                double originalPrice = Double.parseDouble(nextLine[1]);
                double discountPrice = Double.parseDouble(nextLine[2]);
                String category = nextLine[3];
                int rating = Integer.parseInt(nextLine[4]);
                String imagePath = nextLine[5];

                // Read image file as binary data using ByteArrayOutputStream
                byte[] imageData = null;
                if (imagePath != null && !imagePath.isEmpty()) {
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        try (FileInputStream inputStream = new FileInputStream(imageFile);
                             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                            imageData = outputStream.toByteArray();
                        } catch (IOException e) {
                            System.err.println("Failed to read image file: " + imagePath);
                            e.printStackTrace();
                        }
                    } else {
                        System.err.println("Image file not found: " + imagePath);
                    }
                }

                // Set parameters
                pst.setString(1, pname);
                pst.setBigDecimal(2, new java.math.BigDecimal(originalPrice));
                pst.setBigDecimal(3, new java.math.BigDecimal(discountPrice));
                pst.setString(4, category);
                pst.setInt(5, rating);
                pst.setBytes(6, imageData);

                // Add to batch
                pst.addBatch();
            }

            // Execute batch
            pst.executeBatch();
            System.out.println("Data inserted successfully.");

        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            // Close resources in finally block
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
