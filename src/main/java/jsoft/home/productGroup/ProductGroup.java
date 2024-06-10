package jsoft.home.productGroup;

import java.sql.ResultSet;
import java.util.ArrayList;

import jsoft.ShareControl;

public interface ProductGroup extends ShareControl{
	public ArrayList<ResultSet> getProductGroups();
}
