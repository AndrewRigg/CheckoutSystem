import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.text.DecimalFormat;

public class ProductList{

	private ArrayList<Item> products;
	private Item item;
	private String [] headers = {"NAME", "PRICE", "STOCK", "PROMOTION_QUANTITY", "PROMOTION_PRICE"};
	
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
		br.close();
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
	
	public ArrayList<Item> getProducts(){
		return products;
	}
	
	public String align(String element){
		int tabs = element.length()/4;
		for(int i = tabs; i < 6; i++){
					element += "\t";
				}
		return element;
	}
	
	public void updateStock(ArrayList<Item> items){
		File original_stock = new File("products.txt");
		File new_stock = new File("products_temp.txt");
		try{
			PrintWriter pw = new PrintWriter(new FileWriter(new_stock));
			String line = "";
			for(int i = 0; i < headers.length; i++){
				line += align(headers[i]);
			}
			pw.println(line);
			pw.flush();
			for(Item item: products) {
				line = align(item.getName()) + align(Double.toString(item.getPrice())) + align(Integer.toString(item.getStock())) + 
					align(Integer.toString(item.getPromotionQuantity())) + align(Double.toString(item.getPromotionPrice()));
				pw.println(line);
				pw.flush();
			}
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