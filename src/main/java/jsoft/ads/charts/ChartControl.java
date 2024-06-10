package jsoft.ads.charts;

import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Quartet;
import org.javatuples.Quintet;

import jsoft.ConnectionPool;
import jsoft.objects.ProductObject;

public class ChartControl {
	private ChartModel cm;

	public ChartControl(ConnectionPool cp) {
		this.cm = new ChartModel(cp);
	}

	public ConnectionPool getCP() {
		return this.cm.getCP();
	}

	public void releaseConnection() {
		this.cm.releaseConnection();
	}
	
	
	
	
	public ArrayList<String> viewChart (int month, int year, String statisticDYM) {
		Quintet<
		HashMap<String, Integer>,
		HashMap<String, Float>, 
		ArrayList<ProductObject>, 
		HashMap<String, Short>,
		HashMap<String, Float>> datas = this.cm.getCharts(month, year, statisticDYM);

		return ChartLibrary.viewChart(
				datas.getValue0(),
				datas.getValue1(),
				datas.getValue2(),
				datas.getValue3(),
				datas.getValue4(),
				statisticDYM);
	}
}
