package treasures.hunter.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.JFrame;

import treasures.hunter.bo.Adventurer;
import treasures.hunter.bo.Board;
import treasures.hunter.bo.Case;
import treasures.hunter.bo.Mountain;
import treasures.hunter.bo.Plain;
import treasures.hunter.bo.Treasure;
import treasures.hunter.gui.Displayer;

public class StartHunting {

	public static void main(String[] args) {
		
		// first install all elements on the board
		initBoard();
		  
		// then display the card
		display();

		// Just wait 1.5 seconds for window displaying...
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// start adventurers movements
		for(Adventurer adv : Board.adventurers)
			adv.start();
		
		
		while(!everyAdventurersHasFinishedMovements()){};
		
		writeFinalAdventurerPosition();
		
		System.exit(0);
	}

	private static void writeFinalAdventurerPosition() {
		for(Adventurer adv : Board.adventurers){
			String data = adv.getNameAdventurer() + " " + adv.getX()+"-"+adv.getY()+ " " + adv.getNbTreasure();
			
			writeDatasInFile(data);
		}
	}
	
	 protected static void writeDatasInFile(final String datas) {
	        try{
	            final PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("finalAdventurerPosition.txt", true)));
	            out.println(datas);
	            out.close();
	        }
	        catch(final IOException e){
	            e.printStackTrace();
	        }
	    }

	private static boolean everyAdventurersHasFinishedMovements() {

		for(Adventurer adv : Board.adventurers){
			if(!adv.isFinished())
				return false;
		}
		
		return true;
	}

	private static void initBoard() {
		Vector<String> linesCardFile = getDatasInFile("card.config");
		initPlain(linesCardFile);
		initMountains(linesCardFile);
		initTreasures(linesCardFile);
		Vector<String> adventurersFile = getDatasInFile("adventurer.config");
		initAdventurers(adventurersFile);
		
		
	}

	private static void initTreasures(Vector<String> linesCardFile) {

		for (String line : linesCardFile) {

			if (line != null && line.startsWith("T")) {
				String[] infoLine = line.split(" ");
				String[] coord = infoLine[1].split("-");
				int X = Integer.valueOf(coord[0]) - 1;
				int Y = Integer.valueOf(coord[1]) - 1;
				int nbTreasure = Integer.valueOf(infoLine[2]);
				Treasure treasure = new Treasure();
				treasure.setNbTreasure(nbTreasure);
				Board.card[Y][X] = treasure;
			}
		}

	}

	private static void initAdventurers(Vector<String> adventurers) {
		for (String line : adventurers) {
			if (line != null ) {
				String[] infoLine = line.split(" ");
				String name= infoLine[0];
				String[] coord = infoLine[1].split("-");
				int X = Integer.valueOf(coord[0]) - 1;
				int Y = Integer.valueOf(coord[1]) - 1;
				String orientation = infoLine[2];
				String movements = infoLine[3];
				int timeMovement = Integer.valueOf(infoLine[4]);
				Adventurer adv = new Adventurer(name, X, Y, orientation, movements,timeMovement);
				if(Board.adventurers == null)
					Board.adventurers = new Vector<Adventurer>();
				Board.adventurers.add(adv );
			}
		}

	}

	private static void initMountains(Vector<String> linesCardFile) {
		for (String line : linesCardFile) {
			if (line != null && line.startsWith("M")) {
				String[] infoLine = line.split(" ");

				String[] coord = infoLine[1].split("-");
				int X = Integer.valueOf(coord[0]) - 1;
				int Y = Integer.valueOf(coord[1]) - 1;
				Mountain mountain = new Mountain();
				Board.card[Y][X] = mountain;
			}
		}
	}

	private static void initPlain(Vector<String> linesCardFile) {
		for (String line : linesCardFile) {
			if (line != null && line.startsWith("C")) {
				String[] infoLine = line.split(" ");
				int width = Integer.valueOf(infoLine[1]);
				int height = Integer.valueOf(infoLine[2]);

				Board.card = new Case[height][width];

			}
		}

		for (int i = 0; i < Board.card.length; i++) {
			for (int j = 0; j < Board.card[i].length; j++) {
				Board.card[i][j] = new Plain();
			}
		}
	}

	protected static Vector<String> getDatasInFile(String fileName) {
		Vector<String> lines = null;
		
		// Create object of FileReader
		FileReader inputFile;
		try {
			inputFile = new FileReader(fileName);
			// Instantiate the BufferedReader Class
			final BufferedReader bufferReader = new BufferedReader(inputFile);

			// Variable to hold the one line data
			String line;

			// Read file line by line
			while ((line = bufferReader.readLine()) != null) {
				if (!"".equals(line.trim())) {
					if (lines == null) {
						lines = new Vector<String>();
					}
					if (!line.startsWith("#")) {
						lines.add(line);
					}
				}
			}
			// Close the buffer reader
			bufferReader.close();
		} catch (final IOException e) {
			// Nothing todo, if the file doesn't exist, the method return null
		}

		return lines;
	}

	final static public JFrame p = new JFrame();

	public static void display() {

		p.setSize(Board.card[0].length * Board.SIZE_CASE +30, Board.card.length * Board.SIZE_CASE+50);
		p.setResizable(true);
		p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final Displayer displayer = new Displayer();

		p.add(displayer);

		p.setVisible(true);
	}

}
