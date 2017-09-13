/**
Class to retrieve items from the product list database (textfile)
Has method to update the database when items are processed
*/

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.text.DecimalFormat;

public class Products{

	private Item item;
	private String [] headers = {"NAME", "PRICE", "STOCK", "PROMOTION_QUANTITY", "PROMOTION_PRICE"};
	
	public Products(){
	}
	
	/**
	Gets the item with relevant details from product database
	*/
	public Item getItemFromProductList(String prod){
		try{
			FileReader fileReader = new FileReader(new File("products.txt"));
			BufferedReader br = new BufferedReader(fileReader);
			Scanner sc;
			String line = null;
			br.readLine();
			boolean found = false;
			while ((line = br.readLine()) != null && !found) {
				sc = new Scanner(line).useDelimiter("\\s*\t\\s*");
				try{
					String name = sc.next();
					if(name.equals(prod)){
						double price = Double.parseDouble(sc.next());
						int stock = Integer.parseInt(sc.next());
						int promotionQuantity = Integer.parseInt(sc.next());
						double promotionPrice = Double.parseDouble(sc.next());
						item = new Item(name, price, stock, promotionQuantity, promotionPrice);
						found = true;
						return item;
					}
				}catch(Exception e){
					System.err.println("Error in product data!");
				}
			}
		br.close();
		}catch(IOException exception){
			System.err.println("No file found!");
		}
		System.err.println(prod + " out of stock!");
		return null;
	}
	
	/**
	Helper class to properly tab Strings of data
	for putting into the database
	*/
	public String align(String element){
		int tabs = element.length()/4;
		for(int i = tabs; i < 6; i++){
					element += "\t";
				}
		return element;
	}
	
	/**
	Method to update the database with the relevant stock
	information when an item is bought 
	Can be used to change the promotion of an item as 
	references by name
	*/
	public void updateStock(ArrayList<Item> list){
		File original_stock = new File("products.txt");
		File new_stock = new File("products_temp.txt");
		try{
			FileReader fileReader = new FileReader(original_stock);
			BufferedReader br = new BufferedReader(fileReader);
			PrintWriter pw = new PrintWriter(new FileWriter(new_stock));
			Scanner sc;
			String lineToWrite = "";
			for(int i = 0; i < headers.length; i++){
				lineToWrite += align(headers[i]);
			}
			pw.println(lineToWrite);
			pw.flush();
			br.readLine();
			String lineToRead = null;
			while ((lineToRead = br.readLine()) != null) {
				boolean found = false;
				sc = new Scanner(lineToRead).useDelimiter("\\s*\t\\s*");
				try{
					String name = sc.next();
					for(Item item: list) {
						if(name.equals(item.getName())){
							found = true;
							lineToWrite = align(item.getName()) + align(Double.toString(item.getPrice())) + align(Integer.toString(item.getStock())) + 
								align(Integer.toString(item.getPromotionQuantity())) + align(Double.toString(item.getPromotionPrice()));
						}
					}
					if(!found){
						lineToWrite = align(name) + align(sc.next()) + align(sc.next()) + align(sc.next()) + align(sc.next());
					}
					pw.println(lineToWrite);
					pw.flush();
				}catch(Exception e){
					System.err.println("Error in product data!");
				}
			}
			br.close();
			pw.close();
		}catch(IOException exception){
			System.err.println("No file found!");
		}
		if(!original_stock.delete()){
			System.err.println("Could not delete file");
		}
		
		if(!new_stock.renameTo(original_stock)){
			System.err.println("Could not rename file");
		}
	}
}