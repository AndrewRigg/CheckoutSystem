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
import java.text.DecimalFormat;

public class Checkout{
	DecimalFormat df = new DecimalFormat("#0.00");
	private Products products;
	private ArrayList<Item> shoppingList;
	private double totalOriginalCost = 0.0;
	private double totalDiscountCost = 0.0;
	private double totalSavings = 0.0;
	
	
	private Date date;
	private int numberOfItems;
	
	public Checkout(){
		shoppingList = new ArrayList<Item>();
		products = new Products();
		this.date = new Date();
	}
	
	/**
	Update the shopping list to show quantities of each product
	This should be done after each item is scanned
	*/
	public ArrayList<Item> compileList(ArrayList<Item> shoppingList){
		numberOfItems = shoppingList.size();
		ArrayList<Item> temp = new ArrayList<Item>();
		for(Item item: shoppingList){
			boolean found = false;
			for(Item tempItem: temp){
				if(tempItem.getName().equals(item.getName())){
					tempItem.setQuantity(tempItem.getQuantity() + item.getQuantity());
					found = true;
				}
			}
			if(!found){
				temp.add(item);
			}
		}
		return temp;
	}
	
	/**
	Process the items from the compiled list and prints
	out the receipt (to the console) this should show the
	total price, the customer savings and any unavailable items
	*/
	public void processItems(ArrayList<Item> shoppingList){
		System.out.println("\n\nQIKMARKET CHECKOUT RECEIPT");
		System.out.println(date.toString());
		System.out.println("\n\nTotal of " + numberOfItems + " items");
		for(Item item: shoppingList){
			Item prod = products.getItemFromProductList(item.getName());
			if(prod != null){
				System.out.print("\nItem " + (shoppingList.indexOf(item)+1) + ": " + item.getName());
				if(item.getQuantity() > 1){
					System.out.print(" x" + item.getQuantity());
				}
				totalOriginalCost += item.getPrice() * item.getQuantity();
				totalDiscountCost += item.getQuantity() % item.getPromotionQuantity() * item.getPrice() 
					+ (item.getQuantity() - (item.getQuantity() % item.getPromotionQuantity())) * item.getPromotionPrice();
				item.setStock(item.getStock() - item.getQuantity());
			}else{
				shoppingList.remove(item);
				System.err.println("Product not listed.");
			}
		}
		totalSavings = totalOriginalCost - totalDiscountCost;
		totalOriginalCost = (double)Math.round(totalOriginalCost * 1000d) / 1000d;
		totalDiscountCost = (double)Math.round(totalDiscountCost * 1000d) / 1000d;
		totalSavings = (double)Math.round(totalSavings * 1000d) / 1000d;
		System.out.println("\n\nTotal cost:  \u00A3" + df.format(totalDiscountCost));
		System.out.println("Today you have saved  \u00A3" + df.format(totalSavings));
		System.out.println("\nThank you for shopping at QikMarket\nHave a nice day!\n\n\n");
		products.updateStock(shoppingList);
	}
	
	/**
	Adds items by quantity to the shopping list (e.g. when a number 
	is typed before scanning an item to give quantity)
	*/
	public void addItemsToShoppingList(String name, int quantity){
		addItems(name, quantity);
	}
		
	/**
	Override method which adds a single item (e.g. single item
	scanned at the checkout)
	*/
	public void addItemsToShoppingList(String name){
		addItems(name, 1);
	}
	
	/**
	Gets the item from the product database (in this case a text file) 
	and adds it to the shopping list
	*/
	public void addItems(String name, int quantity){
			if(products.getItemFromProductList(name) != null){
			Item newItem = products.getItemFromProductList(name);
			newItem.setQuantity(quantity);
			shoppingList.add(newItem);
			shoppingList = compileList(shoppingList);
		}
	}
	
	public static void main (String [] args){
		Checkout checkout = new Checkout();		
		checkout.addItemsToShoppingList("Juice", 2);
		checkout.addItemsToShoppingList("Microwave Meal", 1);
		checkout.addItemsToShoppingList("Steak");
		checkout.addItemsToShoppingList("Steak");
		checkout.addItemsToShoppingList("Cucumber");
		checkout.addItemsToShoppingList("Biscuits", 3);
		checkout.addItemsToShoppingList("Cucumber");
		checkout.addItemsToShoppingList("Biscuits", 1);
		checkout.addItemsToShoppingList("Biscuits", 6);
		checkout.addItemsToShoppingList("Microwave Meal");
		checkout.addItemsToShoppingList("Microwave Meal");
		checkout.processItems(checkout.shoppingList);
	}
}


	