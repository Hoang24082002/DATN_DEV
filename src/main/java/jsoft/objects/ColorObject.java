package jsoft.objects;

public class ColorObject {
	private int c_id; 
	private String c_name; 
	private int c_manager_id; 
	private String c_notes; 
	private boolean c_delete; 
	private String c_deleted_date; 
	private String c_deleted_author; 
	private String c_modified_date; 
	private String c_created_date;  
	private int c_created_author_id;
	
	public ColorObject() {
		
	}

	public int getC_id() {
		return c_id;
	}

	public void setC_id(int c_id) {
		this.c_id = c_id;
	}

	public String getC_name() {
		return c_name;
	}

	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	public int getC_manager_id() {
		return c_manager_id;
	}

	public void setC_manager_id(int c_manager_id) {
		this.c_manager_id = c_manager_id;
	}

	public String getC_notes() {
		return c_notes;
	}

	public void setC_notes(String c_notes) {
		this.c_notes = c_notes;
	}

	public boolean isC_delete() {
		return c_delete;
	}

	public void setC_delete(boolean c_delete) {
		this.c_delete = c_delete;
	}

	public String getC_deleted_date() {
		return c_deleted_date;
	}

	public void setC_deleted_date(String c_deleted_date) {
		this.c_deleted_date = c_deleted_date;
	}

	public String getC_deleted_author() {
		return c_deleted_author;
	}

	public void setC_deleted_author(String c_deleted_author) {
		this.c_deleted_author = c_deleted_author;
	}

	public String getC_modified_date() {
		return c_modified_date;
	}

	public void setC_modified_date(String c_modified_date) {
		this.c_modified_date = c_modified_date;
	}

	public String getC_created_date() {
		return c_created_date;
	}

	public void setC_created_date(String c_created_date) {
		this.c_created_date = c_created_date;
	}

	public int getC_created_author_id() {
		return c_created_author_id;
	}

	public void setC_created_author_id(int c_created_author_id) {
		this.c_created_author_id = c_created_author_id;
	}
	
	
}
