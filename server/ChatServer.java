package server;

import ocsf.server.AbstractServer;
import java.util.*;
import ocsf.server.ConnectionToClient;
import javax.swing.*;

import client.CreateAccountData;
import client.LoginData;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class ChatServer extends AbstractServer 
{
	private JTextArea log;
	private JLabel status;
	private String returner;
	private Database database;
	private HostedLobbies hl;
	private Set<Game> games;

	public ChatServer()
	{
		super(8300);
		super.setTimeout(500);
		try {
			database = new Database();
			hl = new HostedLobbies();
			games = new HashSet<Game>();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ChatServer(int port)
	{
		super(port);
	}

	public void setLog(JTextArea log)
	{
		this.log = log;
	}

	public JTextArea getLog()
	{
		return log;
	}

	public void setStatus(JLabel status)
	{
		this.status = status;
	}

	public JLabel getStatus()
	{
		return status;
	} 

	void setDatabase(Database Database)
	{
		this.database = Database;
	}




	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) 
	{
		log.append("Message from Client" + arg0.toString() + arg1.toString() + "\n");


		if (arg0 instanceof LoginData)
		{
			LoginData loginData = (LoginData)arg0; 

			try {
				boolean user = database.hasLogin(loginData.getUsername(), loginData.getPassword());

				if(user)
				{
					this.returner = "LoginSuccess"; 
					log.append("Username/Password = " + loginData.getUsername() + ", " + loginData.getPassword() + "\n");
				}
				else {
					this.returner = "LoginFailuer";
				}

				arg1.sendToClient(returner);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		else if(arg0 instanceof CreateAccountData)
		{
			CreateAccountData createData = (CreateAccountData)arg0;
			try {
				boolean create = database.executeDML(createData.getUsername(), createData.getPassword());
				if(create)
				{
					this.returner = "CreateAccountSuccess";
					log.append("User Created: Username/Password = " + createData.getUsername() + ", " + createData.getPassword() + "\n");
				}
				else
				{
					this.returner = "CreateAccountFailure";
				}
				arg1.sendToClient(returner);
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if(arg0 instanceof HostGame) {
			HostGame hg = (HostGame)arg0;
			HostedLobby temp = new HostedLobby(hg.getUsername(), arg1);
			hl.addHost(temp);
			
			this.sendToAllClients(hl.getUsernames());
		}
		
		else if(arg0 instanceof JoinGame) {
			JoinGame jg = (JoinGame)arg0;
			Game temp = new Game(arg1, hl.getConnection(jg.getOpponent()));
			games.add(temp);
		}
	}


	protected void listeningException(Throwable exception) 
	{
		//Display info about the exception
		log.append("Listening Exception:" + exception + "\n");
		exception.printStackTrace();
		log.append(exception.getMessage() + "\n");

		if (this.isListening())
		{
			log.append("Server not Listening\n");
			status.setText("Not Connected");
			status.setForeground(Color.RED);
		}

	}


	protected void serverStarted() 
	{
		log.append("Server Started\n");
	}

	protected void serverStopped() 
	{

		log.append("Server Stopped Accepting New Clients - Press Listen to Start Accepting New Clients\n");
	}

	protected void serverClosed() 
	{
		log.append("Server and all current clients are closed - Press Listen to Restart\n");
	}


	protected void clientConnected(ConnectionToClient client) 
	{
		log.append("Client Connected\n");
		status.setText("Connected");
		status.setForeground(Color.GREEN);
	}





}
