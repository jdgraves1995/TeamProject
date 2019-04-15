package server;

import java.util.ArrayList;

import ocsf.server.ConnectionToClient;

public class HostedLobbies {
	private ArrayList<HostedLobby> hllist;
	private String username; 
	private ConnectionToClient con;
	
	// Getters for the username and con.
	  public ArrayList<String> getUsernames()
	  {
		  ArrayList<String> temp = new ArrayList<String>();
		  
		  for(int i = 0;i<hllist.size();i++) {
			  temp.add(hllist.get(i).getUsername());
		  }
		  
		  return temp;
	  }
	  
	  public ConnectionToClient getConnection(String un)
	  {
		int temp = 0;
		for(int i = 0; i < hllist.size();i++)
	    	if (hllist.get(i).getUsername().equals(un))
	    		temp = i;
		
	    return hllist.get(temp).getConnection();
	  }
	  
	  // Setters for the username and con.
	public void addHost(HostedLobby hl)
	  {
	    hllist.add(hl);
	  }
	  public void setConnection(ConnectionToClient con)
	  {
	    this.con = con;
	  }
	  
	  // Constructor that initializes the username and con.
	  public HostedLobbies()
	  {
		  this.hllist = new ArrayList<HostedLobby>();
	  }
}
