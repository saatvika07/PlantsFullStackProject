package plantsPackage;

import java.util.*;
import java.sql.*;

public class HangingPlantProductsDAO {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	
	public HangingPlantProductsDAO(Connection con) {
		this.con = con;
	}
	
	public List<HangingPlantProducts> getAllHangingPlantProducts(){
		List<HangingPlantProducts> hangProd = new ArrayList<>();
		
		try {
			query = "select * from hangplants";
			pst = this.con.prepareStatement(query);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				HangingPlantProducts row = new HangingPlantProducts();
				row.setPname(rs.getString("pname"));
				row.setOriginalprice(rs.getBigDecimal("original_price"));
				row.setDiscountprice(rs.getBigDecimal("discount_price"));
				row.setImage(rs.getString("image"));
				
				hangProd.add(row);
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return hangProd;
		
	}
}
