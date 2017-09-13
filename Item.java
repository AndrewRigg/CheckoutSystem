/**
Class for creating items in supermarket 
*/

public class Item{
	
	private String name;				/**Name of the product*/
	private double price;				/**Price of the product*/
	private int stock;					/**Number of available stock*/
	private int quantity;				/**Desired quantity of product*/
	private int promotionQuantity;		/**Quantity required for promotion*/
	private double promotionPrice;		/**Relative price while on promotion*/
	
	public Item(String name, double price, int stock, int promotionQuantity, double promotionPrice){
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.promotionQuantity = promotionQuantity;
		this.promotionPrice = promotionPrice;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public double getPrice(){
		return price;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	public int getStock(){
		return stock;
	}
	
	public void setStock(int stock){
		this.stock = stock;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
	
	public int getPromotionQuantity(){
		return promotionQuantity;
	}
	
	public void setPromotionQuantity(int promotionQuantity){
		this.promotionQuantity = promotionQuantity;
	}
	
	public double getPromotionPrice(){
		return promotionPrice;
	}
	
	public void setPromotionPrice(double promotionPrice){
		this.promotionPrice = promotionPrice;
	}
}