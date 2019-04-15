package server;

import java.io.Serializable;

public class JoinGame implements Serializable
{
  // Private data fields for the username.
  private String username;
  private String opponent;
  
  public String getUsername()
  {
    return username;
  }
  
  public String getOpponent()
  {
    return opponent;
  }
  
  // Setters for the username.
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public void setOpponent(String opponent)
  {
    this.opponent = opponent;
  }
  
  // Constructor that initializes the username.
  public JoinGame(String username, String opponent)
  {
    setUsername(username);
    setOpponent(opponent);
  }
}
