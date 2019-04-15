package server;

import java.util.*;

import ocsf.server.ConnectionToClient;

public class Game {
	private ArrayList<String> deck = new ArrayList<String>();
	private String[] deckName = {"JH", "JC", "JS", "JD", "TC", "9C","8C","7C","7H","8H","9H","TH",
			"TD","KD","6C","5C","4C","4H","5H","6H","KS","TS",
			"9D","6D","QD","3C","2C","2H","3H","QS","6S","9S",
			"8D","5D","3D","QC","AC","AH","QH","3S","5S","8S",
			"7D","4D","2D","AS","KC","KH","AS","2S","4S","7S"};
	private ArrayList<String> discard = new ArrayList<String>();
	private String[] p1Hand = new String[5];
	private String[] p2Hand = new String[5];
	private int rand;
	
	//Need to pass in player 1 and player 2 from server
	
	
	public Game(ConnectionToClient player1, ConnectionToClient player2)
	{
		//Need to send message to players so that GUI swaps to game board
		fix this
		
		//Adds all cards to the deck
		for (int i = 0; i < deckName.length; i++)
		{
			deck.add(deckName[i]);
		}
		
		//Deal Starting hands
		for(int i = 0; i < 5; i++)
		{
			rand =  ((int)Math.random()*deck.size());
			p1Hand[i] = deck.get(rand);
			deck.remove(rand);
			rand = ((int)Math.random()*deck.size());
			p2Hand[i] = deck.get(rand);
			deck.remove(rand);
		}
		
		//Send players their starting hands
		add here
		
		
	}
	
	

}
