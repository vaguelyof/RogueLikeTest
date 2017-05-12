import java.util.ArrayList;

import asciiPanel.AsciiPanel;

public class Game {
	ArrayList<DungeonLevel> levels;
	AsciiPanel panel;
	

	public Game() {
		levels = new ArrayList<DungeonLevel>();
	}

	public void addPanel(AsciiPanel p) {
		panel = p;
	}

	public void generateNextLevel() {
		levels.add(new DungeonLevel(levels.size(), 51));
		levels.get(levels.size() - 1).generateLevel();
	}

	public void displayMapAroundTile(Tile t, int level) {
		DungeonLevel d = getLevel(level);

		for (int i = 0; i < panel.getHeightInCharacters(); i++) {

			for (int j = 0; j < panel.getWidthInCharacters(); j++) {
				int posX = i + t.getX() - panel.getHeightInCharacters()/2;
				int posY = j + t.getY() - panel.getWidthInCharacters()/2;
				if (d.getTile(posX,posY) != null) {
					if (d.getTile(posX, posY).getIsRock()) {
						panel.setCursorPosition(i, j);
						panel.write('#');

					} else {
						panel.setCursorPosition(i, j);
						panel.write(' ');
					}
				}
			}

		}
		createBorder();
	}
	
	public void createBorder(){
		int borderHeight = 3;
		for(int i = 0; i < panel.getWidthInCharacters(); i++){
			for(int j = 0; j < borderHeight; j++){
				panel.setCursorPosition(i, j);
				panel.write(' ');
			}
			panel.setCursorPosition(i, borderHeight);
			panel.write('=');
		}
		
		panel.updateUI();
	}

	public DungeonLevel getLevel(int d) {
		if (d < levels.size() && d >= 0) {
			return levels.get(d);
		}
		return null;
	}
	
	public void getKeyPress(String keyText){
		
		System.out.println(keyText + " press recieved.");
	}
}
