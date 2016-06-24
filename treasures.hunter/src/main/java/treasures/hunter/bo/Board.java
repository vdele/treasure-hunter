package treasures.hunter.bo;

import java.util.Vector;

public class Board {


	public static final int SIZE_CASE = 75;
	
	public static Case[][] card;
	
	 
	
	public static Vector<Adventurer> adventurers = null;
	
	public static void displayCardInLog(){
		for(int i = 0; i < Board.card.length; i++){
			for(int j = 0; j < Board.card[i].length;j++){
				System.out.print(card[i][j]);
			}
			System.out.println();
		}
	}
}
