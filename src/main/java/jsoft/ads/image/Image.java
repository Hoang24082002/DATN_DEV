package jsoft.ads.image;

import java.sql.ResultSet;
import java.util.ArrayList;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import jsoft.ShareControl;
import jsoft.library.ORDER;
import jsoft.objects.ImageObject;
import jsoft.objects.UserObject;

public interface Image extends ShareControl {
	// các chức năng cập nhật
	public boolean addImage(ImageObject item);
	public boolean editImage(ImageObject item, IMAGE_EDIT_TYPE et);
	public boolean delImage(ImageObject item);
	
	// các chức năng lấy dữ liệu
	public ResultSet getImage(int id);
	
	public ArrayList<ResultSet> getImages(Quartet<ImageObject, Short, Byte, UserObject> infos, Pair<IMAGE_SOFT, ORDER> so);
}
