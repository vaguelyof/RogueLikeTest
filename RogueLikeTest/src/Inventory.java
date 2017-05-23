import java.util.ArrayList;

public class Inventory {
	private ArrayList<Item> itemList;
	private Player myOwner;
	public Inventory(Player p ){
		itemList = new ArrayList<Item>();
		myOwner = p;
	}
	
	public String addToInventoryFromGround(Item i){
		for(Item item : itemList){
			if(item.getName().equals(i.getName())){
				i.getTile().removeEntity(i);
				item.setStack(item.getStack() + 1);
				return "You picked up " + item.getName() + ".";
			}
		}
		if(itemList.size() < 10){
			itemList.add(i);
			i.getTile().removeEntity(i);
			return "You picked up " + i.getName() + ".";
		}
		
		return "Your inventory is full!";
	}
}
