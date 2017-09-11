import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class ProductList{

ArrayList<Item> products;
Item item;

	public ProductList(){
		products = new ArrayList<Item>();
		try{
			FileReader fileReader = new FileReader(new File("products.txt"));
			BufferedReader br = new BufferedReader(fileReader);
			Scanner sc;
			String line = null;
			br.readLine();
			while ((line = br.readLine()) != null) {
				sc = new Scanner(line).useDelimiter("\\s*\t\\s*");
				try{
				String name = sc.next();
				double price = Double.parseDouble(sc.next());
				int stock = Integer.parseInt(sc.next());
				int promotionQuantity = Integer.parseInt(sc.next());
				double promotionPrice = Double.parseDouble(sc.next());
				item = new Item(name, price, stock, promotionQuantity, promotionPrice);
				products.add(item);
				}catch(Exception e){
					System.err.println("Error in input data!");
				}
		}
		}catch(IOException exception){
			System.err.println("No file found!");
		}
	}
	
	public Item getItemFromProductList(String name){
		for(Item item: products){
			if(item.getName().equals(name)){
				return item;
			}
		}
		System.err.println(name + " out of stock!");
		return null;
	}
	
	public void updateStock(ArrayList<Item> items){
		/*try{
			File original_stock = new File("products.txt");
			File new_stock = new File("products_temp.txt");
			FileReader fileReader = new FileReader(new File(original_stock));
			BufferedReader br = new BufferedReader(fileReader);
			PrintWriter pw = new PrintWriter(new FileWriter(new_stock));
			Scanner sc;
			String line = null;
			br.readLine();
			while ((line = br.readLine()) != null) {
				sc = new Scanner(line).useDelimiter("\\s*\t\\s*");
				try{
				String name = sc.next();
				
				double price = Double.parseDouble(sc.next());
				int stock = Integer.parseInt(sc.next());
				int promotionQuantity = Integer.parseInt(sc.next());
				double promotionPrice = Double.parseDouble(sc.next());
				item = new Item(name, price, stock, promotionQuantity, promotionPrice);
				products.add(item);
				}catch(Exception e){
					System.err.println("Error in input data!");
				}
				pw.println(line);
				pw.flush();
			}
		pw.close();
		br.close();
		}catch(IOException exception){
			System.err.println("No file found!");
		}
		
		if(!original_stock.delete()){
			System.err.println("Could not delete file");
		}
		
		if(!new_stock.renameTo(original_stock)){
			System.err.prinln("Could not rename file");
		}
		*/
	}
}