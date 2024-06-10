package jsoft.objects;

public class SizeObject {
	private int s_id; 
	private String s_name; 
	private int s_manager_id; 
	private String s_notes; 
	private boolean s_delete; 
	private String s_deleted_date; 
	private String s_deleted_author; 
	private String s_modified_date; 
	private String s_created_date;  
	private int s_created_author_id;
	
	public SizeObject() {
		
	}

	public int getS_id() {
		return s_id;
	}

	public void setS_id(int s_id) {
		this.s_id = s_id;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public int getS_manager_id() {
		return s_manager_id;
	}

	public void setS_manager_id(int s_manager_id) {
		this.s_manager_id = s_manager_id;
	}

	public String getS_notes() {
		return s_notes;
	}

	public void setS_notes(String s_notes) {
		this.s_notes = s_notes;
	}

	public boolean isS_delete() {
		return s_delete;
	}

	public void setS_delete(boolean s_delete) {
		this.s_delete = s_delete;
	}

	public String getS_deleted_date() {
		return s_deleted_date;
	}

	public void setS_deleted_date(String s_deleted_date) {
		this.s_deleted_date = s_deleted_date;
	}

	public String getS_deleted_author() {
		return s_deleted_author;
	}

	public void setS_deleted_author(String s_deleted_author) {
		this.s_deleted_author = s_deleted_author;
	}

	public String getS_modified_date() {
		return s_modified_date;
	}

	public void setS_modified_date(String s_modified_date) {
		this.s_modified_date = s_modified_date;
	}

	public String getS_created_date() {
		return s_created_date;
	}

	public void setS_created_date(String s_created_date) {
		this.s_created_date = s_created_date;
	}

	public int getS_created_author_id() {
		return s_created_author_id;
	}

	public void setS_created_author_id(int s_created_author_id) {
		this.s_created_author_id = s_created_author_id;
	}
	
	
}
