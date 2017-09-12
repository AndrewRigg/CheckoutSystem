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
	private ProductList pl;
	private ArrayList<Item> shoppingList;
	private double totalOriginalCost = 0.0;
	private double totalDiscountCost = 0.0;
	private double totalSavings = 0.0;
	
	
	private Date date;
	private int numberOfItems;
	
	public Checkout(){
		shoppingList = new ArrayList<Item>();
		pl = new ProductList();
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
		ArrayList<Item> products = pl.getProducts();
		for(Item item: shoppingList){
			System.out.print("\nItem " + (shoppingList.indexOf(item)+1) + ": " + item.getName());
			if(item.getQuantity() > 1){
				System.out.print(" x" + item.getQuantity());
			}
			totalOriginalCost += item.getPrice() * item.getQuantity();
			totalDiscountCost += item.getQuantity() % item.getPromotionQuantity() * item.getPrice() 
				+ (item.getQuantity() - (item.getQuantity() % item.getPromotionQuantity())) * item.getPromotionPrice();
			Item prod = products.get(products.indexOf(item));
			prod.setStock(prod.getStock() - item.getQuantity());
		}
		totalSavings = totalOriginalCost - totalDiscountCost;
		totalOriginalCost = (double)Math.round(totalOriginalCost * 1000d) / 1000d;
		totalDiscountCost = (double)Math.round(totalDiscountCost * 1000d) / 1000d;
		totalSavings = (double)Math.round(totalSavings * 1000d) / 1000d;
		System.out.println("\n\nTotal cost:  \u00A3" + df.format(totalDiscountCost));
		System.out.println("Today you have saved  \u00A3" + df.format(totalSavings));
		System.out.println("\nThank you for shopping at QikMarket\nHave a nice day!");
		pl.updateStock(products);
	}
	
	public void addItemsToShoppingList(String name, int quantity){
		if(pl.getItemFromProductList(name) != null){
			Item newItem = pl.getItemFromProductList(name);
			newItem.setQuantity(quantity);
			shoppingList.add(newItem);
			shoppingList = compileList(shoppingList);
		}
	}
	
	public static void main (String [] args){
		Checkout checkout = new Checkout();		
		checkout.addItemsToShoppingList("Juice", 2);
		checkout.addItemsToShoppingList("Microwave Meal", 1);
		checkout.addItemsToShoppingList("Steak", 1);
		checkout.addItemsToShoppingList("Biscuits", 3);
		checkout.processItems(checkout.shoppingList);
	}
}


	