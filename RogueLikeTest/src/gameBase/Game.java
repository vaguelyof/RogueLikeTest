package gameBase;
import java.awt.Color;
import java.util.ArrayList;

import creatures.*;


import asciiPanel.AsciiPanel;
import creatures.Creature;
import creatures.Monster;
import creatures.Player;
import gameEntities.Door;
import gameEntities.Entity;
import gameEntities.Searcher;
import level.DungeonLevel;
import level.Tile;
import squidpony.squidgrid.Radius;
import squidpony.squidgrid.FOV;

public class Game {
	private ArrayList<DungeonLevel> levels;
	private AsciiPanel panel;
	private Player player;
	private int currentLevel;
	private ArrayList<String> helpItems;
	private ArrayList<ArrayList<Tile>> seenTiles;
	private Searcher search;

	public static int NORTH = 0;
	public static int NORTH_EAST = 1;
	public static int EAST = 2;
	public static int SOUTH_EAST = 3;
	public static int SOUTH = 4;
	public static int SOUTH_WEST = 5;
	public static int WEST = 6;
	public static int NORTH_WEST = 7;

	private double fovmap[][];

	private boolean searching;

	FOV fov;

	public Game() {

		levels = new ArrayList<DungeonLevel>();
		helpItems = new ArrayList<String>();

		helpItems.add("W - Move North");
		helpItems.add("A - Move West");
		helpItems.add("S - Move South");
		helpItems.add("D - Move East");
		helpItems.add("F - Inspect");

		fov = new FOV(FOV.SHADOW);
		seenTiles = new ArrayList<ArrayList<Tile>>();
		searching = false;
	}

	public void start() {
		createPlayer();
		generateNextLevel();
		currentLevel = 0;
		createPlayer();
		insertEntity((Entity) player, levels.get(0).getEntrance());
		// addRegionToSeen(1,0);
		displayMapAroundTile(player.getTile(), 0);
	}

	public void addPanel(AsciiPanel p) {
		panel = p;
	}

	public void generateNextLevel() {
		levels.add(new DungeonLevel(levels.size(), 51));
		levels.get(levels.size() - 1).generateLevel();
		seenTiles.add(new ArrayList<Tile>());
	}

	public void createPlayer() {
		player = new Player("Player", "", 20, 3, this);
	}

	public static Monster createLevel1Monster() {
		return new Monster("Wispy Spirit", "The weakest monster", 1, 1);
	}

	public void insertEntity(Entity e, Tile t) {
		t.addEntity(e);
	}

	public void displayMapAroundTile(Tile t, int level) {
		DungeonLevel d = getLevel(level);
		ArrayList<Tile> currentlySeenTiles = new ArrayList<Tile>();
		currentlySeenTiles = calcFOV(player, 14);

		for (Tile tile : currentlySeenTiles) {
			if (!seenTiles.get(level).contains(tile)) {
				seenTiles.get(level).add(tile);
			}
		}

		for (int i = 0; i < panel.getHeightInCharacters(); i++) {

			for (int j = 0; j < panel.getWidthInCharacters(); j++) {
				int posX = i + t.getX() - panel.getHeightInCharacters() / 2 - 3;
				int posY = j + t.getY() - panel.getWidthInCharacters() / 2 + 8;

				if (d.getTile(posX, posY) != null && seenTiles.get(level).contains(d.getTile(posX, posY))) {
					Color c;
					if(searching && search.getTile() == d.getTile(posX, posY))
						c = Color.YELLOW;
					else if (!currentlySeenTiles.contains(d.getTile(posX, posY))) {
						c = Color.DARK_GRAY;
					} else {
						c = AsciiPanel.black;
					}
					if (d.getTile(posX, posY).getIsRock()) {
						panel.setCursorPosition(j, i);
						panel.write('#', Color.WHITE, c);

					} else {
						panel.setCursorPosition(j, i);
						Entity e = d.getTile(posX, posY).getTopEntity();
						
						
						if (e == null)
							panel.write(' ', Color.WHITE, c);
						else if (!(e instanceof Creature))
							panel.write(e.getChar(), e.getColor(), c);

						else if (currentlySeenTiles.contains(e.getTile()))
							panel.write(e.getChar(), e.getColor(), c);
						
						else {
							panel.setCursorPosition(j, i);
							panel.write(' ', Color.WHITE, Color.DARK_GRAY);
						}
						

					}

				} else {
					panel.setCursorPosition(j, i);
					panel.write(' ', Color.WHITE, Color.GRAY);
				}
			}

		}
		createUpperBorder();
		createHelpMenu();
		panel.updateUI();
	}

	public void createUpperBorder() {
		int borderHeight = 3;
		for (int i = 0; i < panel.getWidthInCharacters(); i++) {
			for (int j = 0; j < borderHeight; j++) {
				panel.setCursorPosition(i, j);
				panel.write(' ');
			}
			panel.setCursorPosition(i, borderHeight);
			panel.write('=');
		}

	}

	public void createHelpMenu() {
		int menuWidth = 20;
		for (int i = 4; i < panel.getHeightInCharacters(); i++) {
			for (int j = panel.getWidthInCharacters() - menuWidth; j < panel.getWidthInCharacters(); j++) {
				panel.setCursorPosition(j, i);
				panel.write(' ');
			}
			panel.setCursorPosition(panel.getWidthInCharacters() - menuWidth - 1, i);
			panel.write('|');
		}
		for (int i = 4; i < panel.getHeightInCharacters() && i - 4 < helpItems.size(); i++) {
			panel.setCursorPosition(panel.getWidthInCharacters() - menuWidth, i);
			panel.write(helpItems.get(i - 4));
		}

	}

	public static ArrayList<Tile> calcFOV(Creature c, int diameter) {
		FOV fov = new FOV();
		Game g = new Game();
		ArrayList<Tile> seen = new ArrayList<Tile>();

		int startx = c.getTile().getX();
		int starty = c.getTile().getY();

		double[][] fovmap = fov.calculateFOV(g.generateResistances(c.getTile().getDungeon()), startx, starty, diameter,
				Radius.DIAMOND);

		seen.clear();
		for (int i = 0; i < fovmap.length; i++) {
			for (int j = 0; j < fovmap[0].length; j++) {
				if (fovmap[i][j] > .5) {
					seen.add(c.getTile().getDungeon().getTile(i, j));
				}
			}
		}
		for(Tile t : c.getTile().getAdjacentTiles()){
			if(t.getTopEntity() instanceof Door){
				for(Tile j : t.getAdjacentTiles())
					for(int i = 0; i < 8; i += 2){
						if(j.getTileInDirection(i) == t){
							seen.add(j);
						}
					}
			}
		}
		return seen;
	}

	private double[][] generateResistances(DungeonLevel dun) {
		double[][] map = new double[dun.getMap().length][dun.getMap().length];
		for (int i = 0; i < dun.getMap().length; i++) {
			for (int j = 0; j < dun.getMap().length; j++) {
				if (dun.getTile(i, j).getIsRock()) {
					map[i][j] = 1;
				}
				if (dun.getTile(i, j).getTopEntity() instanceof Door) {
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

	public static boolean creatureCanMoveInDirection(Creature c, int direction) {
		if (!c.getTile().getTileInDirection(direction).getIsRock()
				&& !(c.getTile().getTileInDirection(direction).getTopEntity() instanceof Creature)) {
			return true;
		}
		return false;
	}

	public void addRegionToSeen(int region, int level) {
		Tile[][] map = getLevel(level).getMap();
		int upperBound;
		Tile t;
		for (int i = 1; i < map.length - 1; i++) {
			upperBound = 8;
			for (int j = 1; j < map[i].length - 1; j++) {
				if (map[i][j].getRegion() == region && !map[i][j].getIsRock()) {
					for (int k = 0; k < upperBound; k++) {
						t = map[i][j].getTileInDirection(k);
						if (t.getIsRock() || t.getRegion() == -1)
							seenTiles.get(level).add(t);
					}
					upperBound = 4;
				} else
					upperBound = 8;
			}
		}
	}

	public void movePlayer(int direction) {
		if (creatureCanMoveInDirection(player, direction)) {
			player.getTile().getTileInDirection(direction).addEntity(player);
			endTurn();
		}

	}

	public void moveAction(int direction) {
		if (!searching) {
			movePlayer(direction);
		} else {
			if (search.getTile().getTileInDirection(direction) != null) {
				search.setTile(search.getTile().getTileInDirection(direction));
			}
			displayMapAroundTile(search.getTile(), currentLevel);
		}
	}

	public void endTurn() {

		for (Monster m : getLevel(currentLevel).getAllMonsters()) {
			m.act();
		}

		displayMapAroundTile(player.getTile(), currentLevel);

	}

	public void getKeyPress(String keyText) {

		if (keyText.length() == 1) {
			switch (keyText.charAt(0)) {
			case 'W':
				moveAction(NORTH);
				break;
			case 'A':
				moveAction(WEST);
				break;
			case 'S':
				moveAction(SOUTH);
				break;
			case 'D':
				moveAction(EAST);
				break;
			case 'F':
				if (!searching) {
					search = new Searcher(player.getTile());
					searching = true;
				} else {
					if(calcFOV(player, 14).contains(search.getTile())){
						System.out.println(search.getPropertiesOfTile());
					}
					else{
						System.out.println("You can't see what is there");
					}
					searching = false;
				}
				panel.updateUI();
				break;
			}
			return;
		}

	}

	public void end() {
		// String
		// panel.setCursorPosition(panel.getWidthInCharacters()/2, y);
	}
}