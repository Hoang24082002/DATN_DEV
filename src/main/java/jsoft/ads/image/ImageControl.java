package jsoft.ads.image;

import java.util.ArrayList;
import java.util.HashMap;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.ImageObject;
import jsoft.objects.UserObject;

public class ImageControl {
	private ImageModel im;

	public ImageControl(ConnectionPool cp) {
		this.im = new ImageModel(cp);
	}

	public ConnectionPool getCP() {
		return this.im.getCP();
	}

	public void releaseConnection() {
		this.im.releaseConnection();
	}

//	------------------------------------------
	public boolean addImage(ImageObject item) {
		return this.im.addImage(item);
	}

	public boolean editImage(ImageObject item, IMAGE_EDIT_TYPE et) {
		return this.im.editImage(item, et);
	}

	public boolean delImage(ImageObject item) {
		return this.im.delImage(item);
	}

//	---------------------------------------------
	public ImageObject getImage(short id) {
		return this.im.getImage(id);
	}

//	----------------------------------------------
	public ArrayList<String> viewImage (
			Quartet<ImageObject, Short, Byte, UserObject> infos,
			Pair<IMAGE_SOFT, ORDER> so) {
				Quartet<ArrayList<ImageObject>, Short, HashMap<Integer, String>, ArrayList<UserObject>> datas = this.im.getImages(infos, so);

		return ImageLibrary.viewImage(datas.getValue0(), infos.getValue0(), infos.getValue3(), datas.getValue2(),
				datas.getValue3());
	}
}
