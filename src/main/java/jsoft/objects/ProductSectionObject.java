package jsoft.objects;

public class ProductSectionObject {
	private int ps_id; 
	private String ps_name; 
	private int ps_pg_id; 
	private int ps_manager_id; 
	private String ps_notes; 
	private boolean ps_delete; 
	private String ps_deleted_date; 
	private String ps_deleted_author; 
	private String ps_modified_date; 
	private String ps_created_date; 
	private String ps_image; 
	private boolean ps_enable; 
	private int ps_created_author_id;
	
	public ProductSectionObject() {
		
	}

	public int getPs_id() {
		return ps_id;
	}

	public void setPs_id(int ps_id) {
		this.ps_id = ps_id;
	}

	public String getPs_name() {
		return ps_name;
	}

	public void setPs_name(String ps_name) {
		this.ps_name = ps_name;
	}

	public int getPs_pg_id() {
		return ps_pg_id;
	}

	public void setPs_pg_id(int ps_pg_id) {
		this.ps_pg_id = ps_pg_id;
	}

	public int getPs_manager_id() {
		return ps_manager_id;
	}

	public void setPs_manager_id(int ps_manager_id) {
		this.ps_manager_id = ps_manager_id;
	}

	public String getPs_notes() {
		return ps_notes;
	}

	public void setPs_notes(String ps_notes) {
		this.ps_notes = ps_notes;
	}

	public boolean isPs_delete() {
		return ps_delete;
	}

	public void setPs_delete(boolean ps_delete) {
		this.ps_delete = ps_delete;
	}

	public String getPs_deleted_date() {
		return ps_deleted_date;
	}

	public void setPs_deleted_date(String ps_deleted_date) {
		this.ps_deleted_date = ps_deleted_date;
	}

	public String getPs_deleted_author() {
		return ps_deleted_author;
	}

	public void setPs_deleted_author(String ps_deleted_author) {
		this.ps_deleted_author = ps_deleted_author;
	}

	public String getPs_modified_date() {
		return ps_modified_date;
	}

	public void setPs_modified_date(String ps_modified_date) {
		this.ps_modified_date = ps_modified_date;
	}

	public String getPs_created_date() {
		return ps_created_date;
	}

	public void setPs_created_date(String ps_created_date) {
		this.ps_created_date = ps_created_date;
	}

	public String getPs_image() {
		return ps_image;
	}

	public void setPs_image(String ps_image) {
		this.ps_image = ps_image;
	}

	public boolean isPs_enable() {
		return ps_enable;
	}

	public void setPs_enable(boolean ps_enable) {
		this.ps_enable = ps_enable;
	}

	public int getPs_created_author_id() {
		return ps_created_author_id;
	}

	public void setPs_created_author_id(int ps_created_author_id) {
		this.ps_created_author_id = ps_created_author_id;
	}
	
	
}
