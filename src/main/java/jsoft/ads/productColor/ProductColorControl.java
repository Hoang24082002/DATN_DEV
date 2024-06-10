//package jsoft.ads.productColor;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import org.javatuples.Pair;
//import org.javatuples.Quartet;
//import jsoft.ConnectionPool;
//import jsoft.library.ORDER;
//import jsoft.objects.ProductGroupObject;
//import jsoft.objects.UserObject;
//
//public class ProductColorControl {
//	private ProductColorModel pgm;
//
//	public ProductColorControl(ConnectionPool cp) {
//		this.pgm = new ProductColorModel(cp);
//	}
//
//	public ConnectionPool getCP() {
//		return this.pgm.getCP();
//	}
//
//	public void releaseConnection() {
//		this.pgm.releaseConnection();
//	}
//
////	------------------------------------------
//	public boolean addProductGroup(ProductGroupObject item) {
//		return this.pgm.addProductGroup(item);
//	}
//
//	public boolean editProductGroup(ProductGroupObject item, PRODUCTCOLOR_EDIT_TYPE et) {
//		return this.pgm.editProductGroup(item, et);
//	}
//
//	public boolean delProductGroup(ProductGroupObject item) {
//		return this.pgm.delProductGroup(item);
//	}
//
////	---------------------------------------------
//	public ProductGroupObject getProductGroup(short id) {
//		return this.pgm.getProductGroup(id);
//	}
//
////	----------------------------------------------
//	public ArrayList<String> viewProductGroup (
//			Quartet<ProductGroupObject, Short, Byte, UserObject> infos,
//			Pair<PRODUCTCOLOR_SOFT, ORDER> so) {
//				Quartet<ArrayList<ProductGroupObject>, Short, HashMap<Integer, String>, ArrayList<UserObject>> datas = this.pgm.getProductGroups(infos, so);
//
//		return ProductColorLibrary.viewProductGroup(datas.getValue0(), infos.getValue0(), infos.getValue3(), datas.getValue2(),
//				datas.getValue3());
//	}
//}
