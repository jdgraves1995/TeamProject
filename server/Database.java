package server;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

public class Database
{
	private Connection con;
	private FileInputStream fis;
	

	public Database() throws FileNotFoundException {
		Properties prop = new Properties();//Basically a HashMap
		FileInputStream fis;
		fis = new FileInputStream("server/db(1)(1).properties");
		try
		{
			prop.load(fis);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = prop.getProperty("url");
		String password = prop.getProperty("password");
		String username = prop.getProperty("user");

		try
		{
			con = DriverManager.getConnection(url,username,password);

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	public boolean hasLogin(String username, String password) 
	{

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT username, aes_decrypt(password, 'key')  from UserData where username = ? and aes_decrypt(password, 'key') = ?");

			stmt.setString(1, username);
			stmt.setString(2, password);

			ResultSet result = stmt.executeQuery();
			if(result.next()) 
			{
				stmt.close();
				result.close();
				return true;
			}
			else {
				stmt.close();
				result.close();
				return false;
			}


		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	public boolean executeDML(String username, String Password) throws SQLException
	{
		
		boolean bool = false;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try {
			
			stmt=con.prepareStatement("Select username from userdata where username = ?");
			stmt.setString(1, username);
			result = stmt.executeQuery();
			
			if(!result.next())
			{
				stmt.close();
				stmt=con.prepareStatement("Insert into UserData(username, password) Values(?, aes_encrypt(?, 'key'))");
				stmt.setString(1, username);
				stmt.setString(2, Password);
				stmt.executeUpdate();
				stmt.close();
				result.close();
				bool = true;
			}

		}catch(SQLException e)
		{
			e.printStackTrace();
		}

		return bool;

	}
	
	//Testing Sequence
	public void setConnection(String fn) throws FileNotFoundException
	{
		fis = new FileInputStream(fn);
		Properties prop = new Properties();
		try
		{
			prop.load(fis);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = prop.getProperty("url");
		String password = prop.getProperty("password");
		String username = prop.getProperty("user");

		try
		{
			con = DriverManager.getConnection(url,username,password);

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Connection getConnection()
	{
		return con;
	}
	
	public ArrayList<String> query(String query) throws SQLException
	{
		 ArrayList<String> query1 = new ArrayList<String>();
		   Statement stmt=con.createStatement();
		    ResultSet rs=stmt.executeQuery(query);  
		    
		    
		   ResultSetMetaData rmd = rs.getMetaData();
		    
		    //Get the # of columns
		    int no_columns = rmd.getColumnCount();
		  
		    //Get a column name
		    String name = rmd.getColumnName(1);
		    
		    
		    while(rs.next()) 
		    {
		      
		      for(int i = 1; i <= no_columns; i++)
		      {
		          name = rs.getString(i)+"  ";   
		      }
		      query1.add(name + ","); 
		    }
		    
		    con.close();  
		    System.out.println("Success");
		    return query1;
		
	}
	
	public void executeTestDML(String DML) throws SQLException
	{
		Statement stmt1=con.createStatement();
	     stmt1.execute(DML);
	}



}
