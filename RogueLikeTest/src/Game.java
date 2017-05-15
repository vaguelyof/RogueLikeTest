import java.awt.Color;
import java.util.ArrayList;

import asciiPanel.AsciiPanel;

import squidpony.squidgrid.Radius;
import squidpony.squidgrid.FOV;

public class Game {
	private ArrayList<DungeonLevel> levels;
	private AsciiPanel panel;
	private Player player;
	private int currentLevel;
	private ArrayList<String> helpItems;

	public static int NORTH = 0;
	public static int NORTH_EAST = 1;
	public static int EAST = 2;
	public static int SOUTH_EAST = 3;
	public static int SOUTH = 4;
	public static int SOUTH_WEST = 5;
	public static int WEST = 6;
	public static int NORTH_WEST = 7;
	
	public Game() {
		
		levels = new ArrayList<DungeonLevel>();
		helpItems = new ArrayList<String>();
		helpItems.add("W - Move North");
		helpItems.add("A - Move West");
		helpItems.add("S - Move South");
		helpItems.add("D - Move East");
	}
	
	public void start(){
		createPlayer();
		generateNextLevel();
		currentLevel = 0;
		createPlayer();
		insertEntity(player, levels.get(0).getEntrance());
		displayMapAroundTile(player.getTile(), 0);
	}

	public void addPanel(AsciiPanel p) {
		panel = p;
	}

	public void generateNextLevel() {
		levels.add(new DungeonLevel(levels.size(), 51));
		levels.get(levels.size() - 1).generateLevel();
	}
	
	public void createPlayer(){
		player = new Player("Player", "", 20, 3);
	}
	
	public void insertEntity(Entity e, Tile t){
		t.addEntity(e);
	}

	public void displayMapAroundTile(Tile t, int level) {
		DungeonLevel d = getLevel(level);

		for (int i = 0; i < panel.getHeightInCharacters(); i++) {

			for (int j = 0; j < panel.getWidthInCharacters(); j++) {
				int posX = i + t.getX() - panel.getHeightInCharacters()/2 - 3;
				int posY = j + t.getY() - panel.getWidthInCharacters()/2 + 8;
				ArrayList<Tile> currentlySeenTiles = calcFOV(t.getX(), t.getY(),d);
				
				
				if (d.getTile(posX,posY) != null && !currentlySeenTiles.contains(d.getTile(posX, posY))) {
					if (d.getTile(posX, posY).getIsRock()) {
						panel.setCursorPosition(j, i);
						panel.write('#');
						
						
					} else {
						panel.setCursorPosition(j, i);
						Entity e = d.getTile(posX,posY).getTopEntity();
						if(e == null)
							panel.write(' ', Color.WHITE);
						else if(e instanceof Player)
							panel.write('@', Color.BLUE);
						else if(e instanceof Door)
							panel.write('+', Color.GRAY);
						else if(e instanceof UpStairs)
							panel.write('<', Color.GRAY);
						else if(e instanceof DownStairs)
							panel.write('>', Color.GRAY);
						else 
							panel.write('?', Color.PINK);
					}
				}
				else{
					panel.setCursorPosition(j, i);
					panel.write(' ', Color.WHITE, Color.GRAY);
				}
			}

		}
		
		
		createUpperBorder();
		createHelpMenu();
		panel.updateUI();
	}
	
	public void createUpperBorder(){
		int borderHeight = 3;
		for(int i = 0; i < panel.getWidthInCharacters(); i++){
			for(int j = 0; j < borderHeight; j++){
				panel.setCursorPosition(i, j);
				panel.write(' ');
			}
			panel.setCursorPosition(i, borderHeight);
			panel.write('=');
		}
		
	}
	
	public void createHelpMenu(){
		int menuWidth = 20;
		for(int i = 4; i < panel.getHeightInCharacters(); i++){
			for(int j = panel.getWidthInCharacters() - menuWidth; j < panel.getWidthInCharacters(); j++){
				panel.setCursorPosition(j, i);
				panel.write(' ');
			}
			panel.setCursorPosition(panel.getWidthInCharacters() - menuWidth - 1, i);
			panel.write('|');
		}
		for(int i = 4; i < panel.getHeightInCharacters() && i - 4  < helpItems.size(); i++){
			panel.setCursorPosition(panel.getWidthInCharacters() - menuWidth, i);
			panel.write(helpItems.get(i - 4));
		}
		
	}
	
	public ArrayList<Tile> calcFOV(int x, int y, DungeonLevel dun){
		double[][] fovmap;
		FOV fov = new FOV(FOV.SHADOW);
		
				
		int startx = x;
		int starty = y;
		
		fovmap = fov.calculateFOV(generateResistances(dun), startx, starty, 8, Radius.SQUARE);
		
		
		ArrayList<Tile> seen = new ArrayList<Tile>();
		for(int i = 0; i < fovmap.length; i++){
			for(int j = 0 ; j < fovmap[0].length; j++){
				if(fovmap[i][j] < 0.5){
					seen.add(dun.getTile(i, j));
				}
			}
		}
		return seen;
	}
	
	private double[][] generateResistances(DungeonLevel dun){
		double [][] map = new double[dun.getMap().length][dun.getMap().length];
		for(int i = 0; i < dun.getMap().length; i++){
			for(int j = 0; j < dun.getMap().length; j++){
				if(dun.getTile(i, j).getIsRock() || dun.getTile(i, j).getTopEntity() instanceof Door){
					map[i][j] = 1;
				}
				
			}
		}
		return map;
	}

	public DungeonLevel getLevel(int d) {
		if (d < levels.size() && d >= 0) {
			return levels.get(d);
		}
		return null;
	}
	
	public boolean creatureCanMoveInDirection(Creature c, int direction){
		if(!c.getTile().getTileInDirection(direction).getIsRock()){
			return true;
		}
		return false;
	}
	
	public void movePlayer(int direction){
		if(creatureCanMoveInDirection(player,direction)){
			player.getTile().getTileInDirection(direction).addEntity(player);
		}
		displayMapAroundTile(player.getTile(), currentLevel);
	}
	
	public void getKeyPress(String keyText){
		
		if(keyText.length() == 1){
			switch(keyText.charAt(0)){
			case 'W':
				movePlayer(NORTH);
				break;
			case 'A':
				movePlayer(WEST);
				break;
			case 'S':
				movePlayer(SOUTH);
				break;
			case 'D':
				movePlayer(EAST);
				break;
			}
			return;
		}
		
		
	}
}
