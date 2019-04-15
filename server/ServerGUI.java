//Jacob Graves
//Software Engineering
//1/28/2019
//lab2out

package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ServerGUI extends JFrame {
	private ChatServer server;
	private JLabel statusTag = new JLabel("Status:");
	private JLabel status = new JLabel("Not Connected"); //Initialized to “Not Connected”
	private String[] labels = {"Port #", "Timeout"};
	private JLabel[] labels1 = new JLabel[2];
	private JTextField[] textFields = new JTextField[labels.length];
	private JLabel logLabel = new JLabel("Server Log Below");
	private JTextArea log = new JTextArea(20,50);
	private JScrollPane logPane = new JScrollPane(log);
	private JButton listen = new JButton("Listen");
	private JButton close = new JButton("Close");
	private JButton stop = new JButton("Stop");
	private JButton quit = new JButton("Quit");
	
	
	public ServerGUI()
	{
		server = new ChatServer();
		//declaring and initializing my JPanels
		JPanel north = new JPanel();
		JPanel left = new JPanel(new GridLayout(0,1,0,45));
		JPanel right = new JPanel(new GridLayout(0,1,0,40));
		JPanel center =  new JPanel();
		JPanel center1 = new JPanel(new GridLayout(0,1,45,0));
		JPanel south = new JPanel();
		
		//adding of my panels to their corresponding position in the frame
		this.add(north,BorderLayout.NORTH);
		north.add(left,BorderLayout.WEST);
		north.add(right, BorderLayout.EAST);
		this.add(center, BorderLayout.CENTER);
		center.add(center1, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
		
		//adding each label to their corresponding panel.
		left.add(statusTag);
		status.setForeground(Color.RED); //simply sets the status text to red
		right.add(status);
		
		//adding the label and textarea to the center panel
		log.setLineWrap(true);
		center1.add(logLabel);
		center1.add(logPane);
		
		//adding my buttons to the south JPanel
		south.add(listen, BorderLayout.CENTER);
		south.add(close, BorderLayout.CENTER);
		south.add(stop,BorderLayout.CENTER);
		south.add(quit, BorderLayout.CENTER);
		

		//this loop will iterate through each of the arrays
		//placing them one by one to their corresponding panes
		for(int i = 0; i < labels1.length; i++)
		{
			labels1[i] = new JLabel(labels[i]); //initializes each JLabel with an element of labels;
			left.add(labels1[i]); //adds labels1 to left panel
			textFields[i] = new JTextField(""); //initializes each textfield to empty
			right.add(textFields[i]); //adds textfields to the right panel
		}
		//adding actionListeners to each of my buttons
		listen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
					if(textFields[0].getText().equals("")) //if any two of the textfields are empty append them to the text area
					{
						log.append("Port Number/timeout not entered before pressing Listen \n");
					}
					else if(textFields[1].getText().equals("")) //if any two of the textfields are empty append them to the text area
					{
						log.append("Port Number/timeout not entered before pressing Listen \n");
					}
					else
					{
						//setting my designated ports to be whatever is entered from the textboxes
						int port = Integer.parseInt(textFields[0].getText());						
						int timeout = Integer.parseInt(textFields[1].getText());
						server.setPort(port);
						server.setTimeout(timeout);
						server.setLog(log); //passing the textArea over to ChatServer for manipulation
						server.setStatus(status); //passing the JLabel over to ChatServer for manipulation
						//If connected this try/catch will listen for the server.
						try {
							server.listen();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						listen.getModel().setSelected(true); //Signals the button has been pressed			
					}
	
			}
		});
		
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(listen.getModel().isSelected() == false) // if the button has not been pressed display this to the textarea
				{
					log.append("Server not currently started \n");
				}
				else if(listen.getModel().isSelected() == true) // if the button has been pressed close the connection with the server and clients.
				{
					listen.getModel().setSelected(false);
					status.setText("Connection Closed");
					status.setForeground(Color.RED);
					
					try {
						server.close(); //OSCF method that calls the ChatServers serverClosed method to close out the server
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(listen.getModel().isSelected() == false) // if the button has not been pressed display this to the textarea
				{
					log.append("Server not currently started \n");
				}
				else if(listen.getModel().isSelected() == true) // if the button has been pressed then we stop listening to the server
				{
					server.stopListening(); //OSCF method that calls the ChatServers serverStopped method to stop listening to the server
					status.setText("Not Listening");
					status.setForeground(Color.RED);
				}
			}
		});
		
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0); //exits the frame
				server.stopListening();
			}
		});
		
				
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	
	
	static public void main(String[] args)
	{
		new ServerGUI();
	}
}
