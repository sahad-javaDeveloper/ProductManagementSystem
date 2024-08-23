//Productmanagementsystem

package com.product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ProductDao {

	List<Product>listOfProduct=new ArrayList<Product>();
	
	public void createTheProduct(String productName,double price) throws ClassNotFoundException, SQLException {
		Product product=new Product(productName, price);//stored in heap memory
		
		Connection connection=	DbConnectionNew.getConnection();
		String query="insert into products(productName ,productPrice) values(?,?);";
		
		PreparedStatement preparedStatement=   connection.prepareStatement(query);
		preparedStatement.setString(1, productName);
		preparedStatement.setDouble(2, price);

		int rows=preparedStatement.executeUpdate();
		System.out.println(rows+"rows affected");
		
		listOfProduct.add(product);
		
		
	}
	public List<Product> readTheData() throws ClassNotFoundException, SQLException {
		
		Connection connection=	DbConnectionNew.getConnection();
		Statement statement=	connection.createStatement();
		String query="SELECT * FROM productdb.products;";
		ResultSet resultSet= statement.executeQuery(query);
		while(resultSet.next()) {
		int id= resultSet.getInt("id");
		 String productName=resultSet.getString("productName");
		 Double productPrice=resultSet.getDouble("productPrice");
		 
		 Product product=new Product(id, productName, productPrice);
		 listOfProduct.add(product);
		
		 
		}
		return listOfProduct;
	
	}
	public Product retriveTheDataForUpdateForm(int id) throws ClassNotFoundException, SQLException {
	    Product product = null;
	    Connection connection = DbConnectionNew.getConnection();
	    String query = "SELECT productName, productPrice FROM products WHERE id = ?";
	    
	    // Prepare the SQL statement
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    
	    // Set the id parameter in the prepared statement
	    preparedStatement.setInt(1, id);
	    
	    // Execute the query and get the result set
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    // Process the result set
	    if (resultSet.next()) {
	        String productName = resultSet.getString("productName");
	        double price = resultSet.getDouble("productPrice");
	        
	        // Create a new Product object with the retrieved values
	        product = new Product(id, productName, price);
	    }
	    
	    // Close resources
	    resultSet.close();
	    preparedStatement.close();
	    connection.close();
	    
	    return product;
	}
	
	public void updateTheData(int id,String productName,double price) throws ClassNotFoundException, SQLException {
		Connection connection=	DbConnectionNew.getConnection();
		String query="update products set productName=?,productPrice=? where id=?";
		PreparedStatement preparedStatement=   connection.prepareStatement(query);
		preparedStatement.setString(1, productName);
		preparedStatement.setDouble(2, price);
		preparedStatement.setInt(3, id);
		int rows=preparedStatement.executeUpdate();
		System.out.println(rows+"1 rows affected");
		
	}
	public void deletetheData(int id) throws ClassNotFoundException, SQLException {
		Connection connection=	DbConnectionNew.getConnection();
		String deleteQuery="delete from products where id=?";
		PreparedStatement preparedStatement=	connection.prepareStatement(deleteQuery);
		preparedStatement.setInt(1, id);
		int rows=preparedStatement.executeUpdate();
		System.out.println(rows+"1 rows affected");
		}
		
	}
	

