package jsoft.home.productCategory;

import java.sql.ResultSet;
import java.util.ArrayList;

import jsoft.ShareControl;

public interface ProductCategory extends ShareControl{
	public ArrayList<ResultSet> getProductCategorys();
}
