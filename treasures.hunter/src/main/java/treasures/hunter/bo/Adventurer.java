package treasures.hunter.bo;

import java.util.Vector;

public class Adventurer extends Thread {
	public static final int NORTH_ORIENTATION = 0;
	public static final int EAST_ORIENTATION = 1;
	public static final int SOUTH_ORIENTATION = 2;
	public static final int WEST_ORIENTATION = 3;

	public static final String LEFT_MOVEMENT = "G";
	public static final String RIGHT_MOVEMENT = "D";
	public static final String AHEAD_MOVEMENT = "A";

	private static int timeMovement = 1000;

	private String name;
	private int orientation;
	private Vector<String> movements;
	private int X;
	private int Y;
	private int nbTreasure;

	public int getNbTreasure() {
		return nbTreasure;
	}

	public void setNbTreasure(int nbTreasure) {
		this.nbTreasure = nbTreasure;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public String getNameAdventurer() {
		return name;
	}

	public void setNameAdventurer(String name) {
		this.name = name;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public Vector<String> getMovements() {
		return movements;
	}

	public void setMovements(Vector<String> movements) {
		this.movements = movements;
	}

	public Adventurer(String name, int X, int Y, String orientation, String movements) {
		this.name = name;
		this.X = X;
		this.Y = Y;
		setOrientation(orientation);
		setMovements(movements);
	}

	private void setMovements(String movements) {
		if (movements != null) {
			for (int i = 0; i < movements.length(); i++) {
				if (this.movements == null) {
					this.movements = new Vector<String>();
				}
				this.movements.add(String.valueOf(movements.charAt(i)));
			}
		}
	}

	private void setOrientation(String orientation) {
		if (orientation != null) {
			if (orientation.equals("N"))
				setOrientation(NORTH_ORIENTATION);
			if (orientation.equals("E"))
				setOrientation(EAST_ORIENTATION);
			if (orientation.equals("S"))
				setOrientation(SOUTH_ORIENTATION);
			if (orientation.equals("O"))
				setOrientation(WEST_ORIENTATION);
		}
	}

	private void executeMovement() {
		for (String movement : movements) {
			if (AHEAD_MOVEMENT.equals(movement)) {
				switch (getOrientation()) {
				case NORTH_ORIENTATION:
					move(X, Y - 1);
					break;
				case SOUTH_ORIENTATION:
					move(X, Y + 1);
					break;
				case WEST_ORIENTATION:
					move(X - 1, Y);
					break;
				case EAST_ORIENTATION:
					move(X + 1, Y);
					break;

				default:
					break;
				}
			}
			if (LEFT_MOVEMENT.equals(movement)) {
				if (orientation == NORTH_ORIENTATION)
					orientation = WEST_ORIENTATION;
				else
					orientation--;
			}
			if (RIGHT_MOVEMENT.equals(movement)) {
				if (orientation == WEST_ORIENTATION)
					orientation = NORTH_ORIENTATION;
				else
					orientation++;
			}
			
			try {
				Thread.sleep(timeMovement);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			

			lookForTreasure();
		}


	}

	private void lookForTreasure() {
		if (Board.card[Y][X] instanceof Treasure){
			if(((Treasure)Board.card[Y][X]).getNbTreasure()>0){
				((Treasure)Board.card[Y][X]).decreaseNbTreasure();
				nbTreasure++;
			}
		}
			
	}

	private void move(int x, int y) {
		if (isCaseAvailable(x, y)) {
			while (isThereAdventurerInCase(x, y)) {
				makePause();
			}
			X = x;
			Y = y;
		}
	}

	private void makePause() {
		try {
			Thread.sleep(timeMovement);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean isThereAdventurerInCase(int x2, int y2) {
		for (Adventurer adv : Board.adventurers) {
			if (!this.equals(adv)) {
				if (x2 == adv.getX() && y2 == adv.getY())
					return true;
			}
		}
		return false;
	}

	private boolean isCaseAvailable(int x, int y) {
		if (x >= Board.card[0].length || y >= Board.card.length)
			return false;
		if (x < 0 || y < 0)
			return false;
		if (Board.card[y][x] instanceof Mountain)
			return false;
		return true;
	}

	public void run() {
		executeMovement();
	}

}
