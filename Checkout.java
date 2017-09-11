/**
@author Andrew Rigg
QikServe checkout software

//Checkout shopping list from a supermarket
//Items can be scanned in any order
//Offers should always be applied
//Customer should see how much they have saved
//New discounts may be added in future
*/

import java.util.Date;
import java.util.ArrayList;

public class Checkout{
	private ProductList products;
	private ArrayList<Item> shoppingList;
	private double totalOriginalCost = 0.0;
	private double totalDiscountCost = 0.0;
	private double totalSavings = 0.0;
	private Date date;
	private int numberOfItems;
	
	public Checkout(){
		shoppingList = new ArrayList<Item>();
		products = new ProductList();
		this.date = new Date();
	}
	
	/**
	Update the list to show quantities of each product
	This should be done after each item is scanned
	*/
	public ArrayList<Item> compileList(ArrayList<Item> shoppingList){
		numberOfItems = shoppingList.size();
		ArrayList<Item> temp = new ArrayList<Item>();
		for(Item item: shoppingList){
			if(temp.contains(item)){
				Item thisItem = temp.get(temp.indexOf(item));
				thisItem.setQuantity(thisItem.getQuantity() + item.getQuantity());
			}else{
				temp.add(item);
			}
		}
		return temp;
	}
	
	/**
	Process the items from the compiled list
	*/
	public void processItems(ArrayList<Item> shoppingList){
		System.out.println("\n\nQIKMARKET CHECKOUT RECEIPT");
		System.out.println(date.toString());
		System.out.println("\n\nTotal of " + numberOfItems + " items");
		for(Item item: shoppingList){
			System.out.print("\nItem " + (shoppingList.indexOf(item)+1) + ": " + item.getName());
			if(item.getQuantity() > 1){
				System.out.print(" x" + item.getQuantity());
			}
			totalOriginalCost += item.getPrice() * item.getQuantity();
			totalDiscountCost += item.getQuantity() % item.getPromotionQuantity() * item.getPrice() 
				+ (item.getQuantity() - (item.getQuantity() % item.getPromotionQuantity())) * item.getPromotionPrice();
			item.setStock(item.getStock()-item.getQuantity());
		}
		totalSavings = totalOriginalCost - totalDiscountCost;
		totalOriginalCost = (double)Math.round(totalOriginalCost * 1000d) / 1000d;
		totalDiscountCost = (double)Math.round(totalDiscountCost * 1000d) / 1000d;
		totalSavings = (double)Math.round(totalSavings * 1000d) / 1000d;
		System.out.println("\n\nTotal cost:  \u00A3" + totalDiscountCost);
		System.out.println("Today you have saved  \u00A3" + totalSavings);
		System.out.println("\nThank you for shopping at QikMarket\nHave a nice day!");
		products.updateStock(shoppingList);
	}
	
	public void addItemsToShoppingList(String name, int quantity){
		if(products.getItemFromProductList(name) != null){
			Item newItem = products.getItemFromProductList(name);
			newItem.setQuantity(quantity);
			shoppingList.add(newItem);
			shoppingList = compileList(shoppingList);
		}
	}
	
	public static void main (String [] args){
		Checkout checkout = new Checkout();		
		checkout.addItemsToShoppingList("Biscuits", 3);
		checkout.addItemsToShoppingList("Juice", 2);
		checkout.addItemsToShoppingList("Microwave Meal", 5);
		checkout.addItemsToShoppingList("Chicken", 4);
		checkout.addItemsToShoppingList("Steak", 1);
		checkout.addItemsToShoppingList("Biscuits", 3);
		checkout.addItemsToShoppingList("Microwave Meal", 5);
		checkout.addItemsToShoppingList("Chicken", 4);
		checkout.addItemsToShoppingList("Steak", 1);
		checkout.processItems(checkout.shoppingList);
	}
}


	