package jsoft.home.productSection;

import java.sql.ResultSet;
import java.util.ArrayList;

import jsoft.ShareControl;

public interface ProductSection extends ShareControl{
	public ArrayList<ResultSet> getProductSections();
}
