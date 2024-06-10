package jsoft.home.cart;

import java.sql.ResultSet;
import java.util.ArrayList;
import org.javatuples.Pair;
import jsoft.ShareControl;
import jsoft.objects.CartObject;
import jsoft.objects.UserObject;

public interface Cart extends ShareControl{
	// các chức năng cập nhật
	public boolean addOrUpdateCart(CartObject item);
	public boolean editCart(CartObject item);
	public boolean delCart(CartObject item);
	
	// các chức năng lấy dữ liệu
	public ResultSet getCart(int id);
	
	public ArrayList<ResultSet> getCarts(Pair<CartObject, UserObject> infos);
}
