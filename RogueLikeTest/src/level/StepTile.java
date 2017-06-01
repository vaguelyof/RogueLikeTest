package level;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import creatures.Creature;
import gameBase.Game;

public class StepTile extends Tile{

	int step;
	
	public StepTile(boolean type, DungeonLevel dungeon, int x, int y, int s) {
		super(type, dungeon, x, y);
		step = s;
	}
	
	/*
     * removes duplicates from a2 that already exist in a1
     */
    public ArrayList<StepTile> removeDuplicates(ArrayList<StepTile> a1, ArrayList<StepTile> a2)
    {
    	ArrayList<StepTile> routeAddOn = new ArrayList<StepTile>();
    	
    	//only add to 
    	for(StepTile outer : a1)
    	{
    		for(StepTile inner : a2)
    		{
    			if(inner.getX() != outer.getY() && inner.getY() != outer.getY() && inner.getStep() == outer.getStep() + 1)
    			{
    				routeAddOn.add(inner);
    			}
    		}
    	}
    	/*
    	// add everything to a HashSet
    	Set<StepTile> hs = new HashSet<StepTile>();	//set does not allow duplicates
    	hs.addAll(a1);
    	hs.addAll(a2);
    	a2.clear();		//reset array
    	a2.addAll(hs);	//add the set onto the array
    					//array now has the entire route 
    	*/
    	for(StepTile st : a2)
    	{
    		System.out.println("(" + st.getX() +", " + st.getY() + ", " + st.getStep() + ")");
    	}
    	System.out.println("\n\n");
    	return routeAddOn;
    }
    
    /*
    public boolean routeHasTargetTile(ArrayList<StepTile> route, Tile t)
    {
    	for(StepTile k : route)
    	{
    		if(((Tile)k).equals(t))
    			return true;
    	}
    	return false;
    }
    */
    
    public void setStep(int i)
    {
    	step = i;
    }
	
	public int getStep()
	{
		return step;
	}

	public String toString()
	{
		return super.toString() + "Step = " + step;
	}	
}
