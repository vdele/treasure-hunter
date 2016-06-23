/**
 *
 */
package treasures.hunter.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import treasures.hunter.bo.Adventurer;
import treasures.hunter.bo.Board;
import treasures.hunter.bo.Case;
import treasures.hunter.bo.Mountain;
import treasures.hunter.bo.Plain;
import treasures.hunter.bo.Treasure;

/**
 * @author 20002845
 * @date 24 juin 2015
 *
 */
public class Displayer extends JPanel implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 4552272332881935593L;

	Timer timer = new Timer(60, this);

	public Displayer() {
		setBackground(Color.gray);
		timer.start();
		setFocusable(true);

	}

	public void actionPerformed(final ActionEvent ev) {
		if (ev.getSource() == timer) {
			repaint();// Screen will be repainted every X milliseconds (defined
						// in instanciation of timer object)
		}
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		drawCard(g);
		drawAdventurers(g);

	}
	
	public final String WEST_OR="⇐ ";
	public final String EAST_OR="⇒";
	public final String NORTH_OR="⇑"; 
	public final String SOUTH_OR="⇓";

	private void drawAdventurers(Graphics g) {
		for(Adventurer adventurer : Board.adventurers){
			g.setColor(Color.lightGray);
			g.fillRect(adventurer.getX() * Board.SIZE_CASE, adventurer.getY() * Board.SIZE_CASE, Board.SIZE_CASE, Board.SIZE_CASE);
			g.setColor(Color.white);
			g.drawString(adventurer.getNameAdventurer(), (adventurer.getX() * Board.SIZE_CASE) +35 , (adventurer.getY() * Board.SIZE_CASE+45));
			
			String displayOrientation=getOrientation(adventurer.getOrientation());
			g.drawString(displayOrientation, (adventurer.getX() * Board.SIZE_CASE) +55 , (adventurer.getY() * Board.SIZE_CASE+65));
			g.drawString(String.valueOf(adventurer.getNbTreasure()), (adventurer.getX() * Board.SIZE_CASE) +15 , (adventurer.getY() * Board.SIZE_CASE+65));
			
		}

	}


	private String getOrientation(int orientation) {
		switch (orientation) {
		case Adventurer.EAST_ORIENTATION:
			return EAST_OR;
		case Adventurer.WEST_ORIENTATION:
			return WEST_OR;
		case Adventurer.NORTH_ORIENTATION:
			return NORTH_OR;
		case Adventurer.SOUTH_ORIENTATION:
			return SOUTH_OR;

		default:
			break;
		}
		return null;
	}

	private void drawCard(Graphics g) {
		for (int i = 0; i < Board.card.length; i++) {
			for (int j = 0; j < Board.card[i].length; j++) {
				Case c = Board.card[i][j];
				if (c instanceof Plain) {
					g.setColor(Color.green);
					g.fillRect(j * Board.SIZE_CASE, i * Board.SIZE_CASE, Board.SIZE_CASE, Board.SIZE_CASE);
				}
				if (c instanceof Mountain) {
					g.setColor(Color.black);
					g.fillRect(j * Board.SIZE_CASE, i * Board.SIZE_CASE, Board.SIZE_CASE, Board.SIZE_CASE);
					g.setColor(Color.white);
					g.drawString("X", (j * Board.SIZE_CASE) +35 , (i * Board.SIZE_CASE+45));
				}
				if (c instanceof Treasure) {
					g.setColor(Color.yellow);
					g.fillRect(j * Board.SIZE_CASE, i * Board.SIZE_CASE, Board.SIZE_CASE, Board.SIZE_CASE);
					g.setColor(Color.black);
					g.drawString(String.valueOf(((Treasure)c).getNbTreasure()), (j * Board.SIZE_CASE) +35 , (i * Board.SIZE_CASE+45));
				}

			}
		}
	}

}
