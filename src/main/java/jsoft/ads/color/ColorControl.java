package jsoft.ads.color;

import java.util.ArrayList;
import java.util.HashMap;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.ColorObject;
import jsoft.objects.UserObject;

public class ColorControl {
	private ColorModel cm;

	public ColorControl(ConnectionPool cp) {
		this.cm = new ColorModel(cp);
	}

	public ConnectionPool getCP() {
		return this.cm.getCP();
	}

	public void releaseConnection() {
		this.cm.releaseConnection();
	}

//	------------------------------------------
	public boolean addColor(ColorObject item) {
		return this.cm.addColor(item);
	}

	public boolean editColor(ColorObject item, COLOR_EDIT_TYPE et) {
		return this.cm.editColor(item, et);
	}

	public boolean delColor(ColorObject item) {
		return this.cm.delColor(item);
	}

//	---------------------------------------------
	public ColorObject getColor(short id) {
		return this.cm.getColor(id);
	}

//	----------------------------------------------
	public ArrayList<String> viewColor (
			Quartet<ColorObject, Short, Byte, UserObject> infos,
			Pair<COLOR_SOFT, ORDER> so) {
				Quartet<ArrayList<ColorObject>, Short, HashMap<Integer, String>, ArrayList<UserObject>> datas = this.cm.getColors(infos, so);

		return ColorLibrary.viewColor(datas.getValue0(), infos.getValue0(), infos.getValue3(), datas.getValue2(),
				datas.getValue3());
	}
}
