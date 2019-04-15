package server;

import java.io.Serializable;

public class HostGame implements Serializable
{
  // Private data fields for the username.
  private String username;
  
  public String getUsername()
  {
    return username;
  }
  
  // Setters for the username.
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  // Constructor that initializes the username.
  public HostGame(String username)
  {
    setUsername(username);
  }
}
