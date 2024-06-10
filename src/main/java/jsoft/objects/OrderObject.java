package jsoft.objects;

public class OrderObject {
	private int order_id; 
	private int user_id; 
	private String order_date; 
	private int order_price; 
	private int order_discount_price; 
	private int order_status; 
	private String order_address;
	private int order_payment_method; 
	private String order_detail_cartId; 
	private boolean order_destroy;
	private String order_delete_date;
	
	public OrderObject() {
		
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public int getOrder_price() {
		return order_price;
	}

	public void setOrder_price(int order_price) {
		this.order_price = order_price;
	}

	public int getOrder_discount_price() {
		return order_discount_price;
	}

	public void setOrder_discount_price(int order_discount_price) {
		this.order_discount_price = order_discount_price;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public String getOrder_address() {
		return order_address;
	}

	public void setOrder_address(String order_address) {
		this.order_address = order_address;
	}

	public int getOrder_payment_method() {
		return order_payment_method;
	}

	public void setOrder_payment_method(int order_payment_method) {
		this.order_payment_method = order_payment_method;
	}

	public String getOrder_detail_cartId() {
		return order_detail_cartId;
	}

	public void setOrder_detail_cartId(String order_detail_cartId) {
		this.order_detail_cartId = order_detail_cartId;
	}

	public boolean isOrder_destroy() {
		return order_destroy;
	}

	public void setOrder_destroy(boolean order_destroy) {
		this.order_destroy = order_destroy;
	}

	public String getOrder_delete_date() {
		return order_delete_date;
	}

	public void setOrder_delete_date(String order_delete_date) {
		this.order_delete_date = order_delete_date;
	}

	
	
}
