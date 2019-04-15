package server;

import ocsf.server.ConnectionToClient;

public class HostedLobby {
	private String username; 
	private ConnectionToClient con;
	
	// Getters for the username and con.
	  public String getUsername()
	  {
	    return username;
	  }
	  public ConnectionToClient getConnection()
	  {
	    return con;
	  }
	  
	  // Setters for the username and con.
	  public void setUsername(String username)
	  {
	    this.username = username;
	  }
	  public void setConnection(ConnectionToClient con)
	  {
	    this.con = con;
	  }
	  
	  // Constructor that initializes the username and con.
	  public HostedLobby(String un, ConnectionToClient con)
	  {
	    setUsername(un);
	    setConnection(con);
	  }
}
