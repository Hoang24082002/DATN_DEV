package jsoft.ads.size;

import java.sql.ResultSet;
import java.util.ArrayList;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import jsoft.ShareControl;
import jsoft.library.ORDER;
import jsoft.objects.SizeObject;
import jsoft.objects.UserObject;

public interface Size extends ShareControl {
	// các chức năng cập nhật
	public boolean addSize(SizeObject item);
	public boolean editSize(SizeObject item, SIZE_EDIT_TYPE et);
	public boolean delSize(SizeObject item);
	
	// các chức năng lấy dữ liệu
	public ResultSet getSize(int id);
	
	public ArrayList<ResultSet> getSizes(Quartet<SizeObject, Short, Byte, UserObject> infos, Pair<SIZE_SOFT, ORDER> so);
}
