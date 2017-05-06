import java.util.ArrayList;
/**
 * Write a description of class DungeonLevel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DungeonLevel
{
    Tile[][] map;
    ArrayList<int[]> rooms;
    final int level;
    int region;
    ArrayList<Integer> connectedRegions;

    public DungeonLevel(int depth){
        this(depth,101);
    }
    
    public DungeonLevel(int depth, int x){
        map = new Tile[x][x];
        level = depth;
        rooms = new ArrayList<int[]>();
        region = 1;
        connectedRegions = new ArrayList<Integer>();
    }

    public void generateLevel(){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                map[i][j] = new Tile(true, this, i, j); //runs through each tile and initializes every tile as a "rock" tile
            }
        }
        createRooms(100);
        createMaze();
        createConnectors();
        cullMaze();
    }

    private void createRooms(int attemptLimit){
        int x;
        int y;
        int xLen;
        int yLen;
        for(int i = 0; i < attemptLimit; i++){
            xLen = generateValidLen();
            yLen = generateValidLen();
            x = generateValidMidpoint(xLen);
            y = generateValidMidpoint(yLen);
            if(validSpace(x,y,xLen,yLen)){
                rooms.add(new int[]{x,y});
                for(int j = x-xLen; j <= x+xLen; j++){
                    for(int k = y-yLen; k <= y+yLen; k++){
                        map[j][k].setType(false);
                        map[j][k].setRegion(region);
                    }
                }
                region++;
            }
        }
    }

    public void printMap(){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j].getIsRock())
                    System.out.print("[]");
                else
                    System.out.print("  ");
            }
            System.out.println();
        }
        System.out.println("Rooms: " + rooms.size());
    }

    private boolean validSpace(int x, int y, int xLen, int yLen){
        for(int i = x-xLen-1; i <= x+xLen+1; i++){
            for(int j = y-yLen-1; j <= y+yLen+1; j++){
                if(!map[i][j].getIsRock()){
                    return false;
                }
            }
        }
        return true;
    }

    private int generateValidMidpoint(int len){
        return 1+len+(2 * (int)(Math.random() * ((map.length / 2)-len)));
    }

    private int generateValidLen(){
        //return 2 + 2 * (int)(Math.random() * 2);
        return 2 + (int)(Math.random() * 4);
    }

    private void createMaze(){
        for(int i = 1; i < map.length - 1; i+=2){
            for(int j = 1; j < map[0].length; j+=2){
                if(map[i][j].getIsRock()){
                    region++;
                    fill(i,j);
                }
            }
        }
    }

    private void fill(int x, int y){
        map[x][y].setType(false);
        ArrayList<int[]> validSpots = getValidSpotsOverOne(x,y);
        while(validSpots.size() > 0){
            
            int chosenOne = (int)(Math.random() * validSpots.size());
            int tempX = (validSpots.get(chosenOne)[0] + x)/2;
            int tempY = (validSpots.get(chosenOne)[1] + y)/2;
            map[tempX][tempY].setType(false);
            map[tempX][tempY].setRegion(region);
            fill(validSpots.get(chosenOne)[0],validSpots.get(chosenOne)[1]);
            validSpots = getValidSpotsOverOne(x,y);
        }

    }

    private ArrayList<int[]> getValidSpotsOverOne(int x, int y){
        int[][] spots = {{x-2, y}, {x+2,y}, {x,y-2} ,{x, y+2}};
        for(int i = 0; i < 4; i++){
            if(spots[i][0] < 1 || spots[i][0] > map.length - 2 || spots[i][1] < 1 || spots[i][1] > map.length - 2 || !map[spots[i][0]][spots[i][1]].getIsRock()){
                spots[i] = null;
            }
        }
        ArrayList<int[]> validSpots = new ArrayList<int[]>();

        for(int[] a : spots){
            if(a != null){
                validSpots.add(a);
            }
        }
        return validSpots;
    }

    private void createConnectors(){
        connectedRegions.add(1);

        while(openConnector()){}
        int choice;
        ArrayList<Tile> connectors;
        for(int i = 0; i < 30; i++){
            connectors = findAllConnectors();
            if(connectors.size() == 0)
                break;
            choice = (int)(Math.random() * connectors.size());
            connectors.get(choice).setType(false);
            connectors.get(choice).setRegion(-1);
            connectors.get(choice).addEntity(new Door());
        }
    }

    private ArrayList<Tile> findAllConnectors(){
        ArrayList<Tile> connectors = new ArrayList<Tile>();
        ArrayList<Integer> nearRegions;
        ArrayList<int[]> spotsAroundTile;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                nearRegions = new ArrayList<Integer>();
                spotsAroundTile = getValidSpots(i,j);
                for(int[] place : spotsAroundTile){
                    if(map[place[0]][place[1]].getRegion() > 0 ){
                        
                        nearRegions.add(map[place[0]][place[1]].getRegion());
                    }
                }

                for(int k = 0; k < nearRegions.size() - 1; k++){
                    if(nearRegions.get(k).equals(nearRegions.get(k+1))){
                        nearRegions.remove(k);
                        k--;
                    }
                }

                if(nearRegions.size() == 2){

                    connectors.add(map[i][j]);
                }
            }
        }
        return connectors;
    }

    private ArrayList<Tile> getConnectorsToMainRegion(){
        ArrayList<Tile> connectors = findAllConnectors();
        //ArrayList<Integer> nearRegions;
        ArrayList<int[]> spotsAroundTile;
        int count = 0;
        for(int i = 0; i < connectors.size(); i++){
            count = 0;
            //nearRegions = new ArrayList<Integer>();
            spotsAroundTile = getValidSpots(connectors.get(i).getX(), connectors.get(i).getY());
            for(int[] place : spotsAroundTile){
                if(isMainRegion(map[place[0]][place[1]].getRegion())){
                    count++;
                }
            }
            if(count != 1){
                connectors.remove(i);
                i--;
            }
        }
        return connectors;
    }

    private boolean openConnector(){
        ArrayList<Tile> connectors = getConnectorsToMainRegion();

        if(connectors.size() == 0){
            return false;
        }
        int choice = (int)(connectors.size() * Math.random());
        
        Tile tileToOpen = connectors.get(choice);
        tileToOpen.setType(false);
        tileToOpen.addEntity(new Door());
        tileToOpen.setRegion(-1);
        for(int[] place : getValidSpots(tileToOpen.getX(), tileToOpen.getY())){
            connectedRegions.add(map[place[0]][place[1]].getRegion());
        }
        return true;
    }

    private boolean isMainRegion(int r)
    {
        for (Integer i:connectedRegions){
            if (r==i){
                return true;
            }
        }
        return false;
    }

    private ArrayList<int[]> getValidSpots(int x, int y){
        int[][] spots = {{x-1, y}, {x+1,y}, {x,y-1} ,{x, y+1}};
        for(int i = 0; i < 4; i++){
            if(spots[i][0] < 1 || spots[i][0] > map.length - 2 || spots[i][1] < 1 || spots[i][1] > map.length - 2 || map[spots[i][0]][spots[i][1]].getIsRock()){
                spots[i] = null;
            }
        }
        ArrayList<int[]> validSpots = new ArrayList<int[]>();

        for(int[] a : spots){
            if(a != null){
                validSpots.add(a);
            }
        }
        return validSpots;
    }

    private void cullMaze(){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(!map[i][j].getIsRock() && getValidSpots(i,j).size() <= 1){
                    map[i][j].setType(true);
                    i=0;
                    j=0;
                }
            }
        }
    }
}
