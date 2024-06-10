package jsoft.objects;

public class CartObject {
	private int cart_id; 
	private int user_id; 
	private int product_id; 
	private int  product_quantity; 
	private String product_color; 
	private String product_size;
	private int product_discount_price; 
	private int product_price; 
	private int count_ordered;
	private boolean is_product_ordered;
	
	public CartObject() {
		
	}

	public int getCart_id() {
		return cart_id;
	}

	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getProduct_quantity() {
		return product_quantity;
	}

	public void setProduct_quantity(int product_quantity) {
		this.product_quantity = product_quantity;
	}

	public String getProduct_color() {
		return product_color;
	}

	public void setProduct_color(String product_color) {
		this.product_color = product_color;
	}

	public String getProduct_size() {
		return product_size;
	}

	public void setProduct_size(String product_size) {
		this.product_size = product_size;
	}

	public int getProduct_discount_price() {
		return product_discount_price;
	}

	public void setProduct_discount_price(int product_discount_price) {
		this.product_discount_price = product_discount_price;
	}

	public int getProduct_price() {
		return product_price;
	}

	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}

	public int getCount_ordered() {
		return count_ordered;
	}

	public void setCount_ordered(int count_ordered) {
		this.count_ordered = count_ordered;
	}

	public boolean isIs_product_ordered() {
		return is_product_ordered;
	}

	public void setIs_product_ordered(boolean is_product_ordered) {
		this.is_product_ordered = is_product_ordered;
	}

	
	
}
