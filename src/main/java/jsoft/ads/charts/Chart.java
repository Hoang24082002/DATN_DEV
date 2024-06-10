package jsoft.ads.charts;

import java.sql.ResultSet;
import java.util.ArrayList;

import jsoft.ShareControl;

public interface Chart extends ShareControl {
	public ArrayList<ResultSet> getCharts(int month, int year, String statisticDYM);
}
