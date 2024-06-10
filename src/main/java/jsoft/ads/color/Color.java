package jsoft.ads.color;

import java.sql.ResultSet;
import java.util.ArrayList;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import jsoft.ShareControl;
import jsoft.library.ORDER;
import jsoft.objects.ColorObject;
import jsoft.objects.UserObject;

public interface Color extends ShareControl {
	// các chức năng cập nhật
	public boolean addColor(ColorObject item);
	public boolean editColor(ColorObject item, COLOR_EDIT_TYPE et);
	public boolean delColor(ColorObject item);
	
	// các chức năng lấy dữ liệu
	public ResultSet getColor(int id);
	
	public ArrayList<ResultSet> getColors(Quartet<ColorObject, Short, Byte, UserObject> infos, Pair<COLOR_SOFT, ORDER> so);
}
