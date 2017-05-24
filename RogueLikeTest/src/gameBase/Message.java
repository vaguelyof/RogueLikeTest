package gameBase;

import java.awt.Color;

public class Message{
		private String msg;
		private Color priority;
		public Message(String m, Color c){
			msg = m; priority = c;
		}
		public String toString(){
			return msg;
		}
		public Color getColor(){
			return priority;
		}
	}