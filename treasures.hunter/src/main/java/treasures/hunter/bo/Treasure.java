package treasures.hunter.bo;

public class Treasure extends Case {

	private int nbTreasure;
	
	public int getNbTreasure() {
		return nbTreasure;
	}

	public void setNbTreasure(int nbTreasure) {
		this.nbTreasure = nbTreasure;
	}

	public Treasure() {
		super(TREASURE);
	}

	public void decreaseNbTreasure() {
		if(nbTreasure>0)
		nbTreasure --;
	}

}
