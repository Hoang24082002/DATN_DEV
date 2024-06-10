package jsoft.ads.size;

import java.util.ArrayList;
import java.util.HashMap;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.SizeObject;
import jsoft.objects.UserObject;

public class SizeControl {
	private SizeModel sm;

	public SizeControl(ConnectionPool cp) {
		this.sm = new SizeModel(cp);
	}

	public ConnectionPool getCP() {
		return this.sm.getCP();
	}

	public void releaseConnection() {
		this.sm.releaseConnection();
	}

//	------------------------------------------
	public boolean addSize(SizeObject item) {
		return this.sm.addSize(item);
	}

	public boolean editSize(SizeObject item, SIZE_EDIT_TYPE et) {
		return this.sm.editSize(item, et);
	}

	public boolean delSize(SizeObject item) {
		return this.sm.delSize(item);
	}

//	---------------------------------------------
	public SizeObject getSize(short id) {
		return this.sm.getSize(id);
	}

//	----------------------------------------------
	public ArrayList<String> viewSize (
			Quartet<SizeObject, Short, Byte, UserObject> infos,
			Pair<SIZE_SOFT, ORDER> so) {
				Quartet<ArrayList<SizeObject>, Short, HashMap<Integer, String>, ArrayList<UserObject>> datas = this.sm.getSizes(infos, so);

		return SizeLibrary.viewSize(datas.getValue0(), infos.getValue0(), infos.getValue3(), datas.getValue2(),
				datas.getValue3());
	}
}
