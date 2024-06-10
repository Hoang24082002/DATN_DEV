package jsoft.home.product;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;
import jsoft.library.ORDER;
import jsoft.objects.ProductObject;
import jsoft.objects.UserObject;

public class ProductImpl extends BasicImpl implements Product {

	public ProductImpl(ConnectionPool cp) {
		super(cp, "Product-Home");
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResultSet getProduct(int id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblproduct AS p WHERE p.product_enable = 1 AND p.product_delete = 0 ");
		sql.append("AND p.product_id = ?");
		return this.get(sql.toString(), id);
	}

	@Override
	public synchronized ArrayList<ResultSet> getProductObjects(Quintet<ProductObject, Short, Byte, UserObject, String> infos, Pair<PRODUCT_SORT, ORDER> so, boolean isDetail) {		
		// đối tượng lưu chữ thông tin lọc kết quả
		ProductObject similar = infos.getValue0();

		// số bản ghi đc lấy trong 1 lần
		byte total = infos.getValue2();

		// vị trí bắt đầu lấy bản ghi
		int at = (infos.getValue1() - 1) * total;
		if(at<0) {
			at = 0;
		}

		// tai khoan dang nhap
		UserObject user = infos.getValue3();
		
		StringBuilder sql = new StringBuilder();

		// res(0) danh sach nhom san pham
		sql.append("SELECT * FROM tblproductgroup WHERE pg_delete = 0 AND pg_enable = 1; ");
		
		// res(1) TOP san pham yeu thich
		sql.append("SELECT p.product_pg_id, p.product_id, product_image, product_name, product_price, product_promotion_price, product_best_seller FROM tblproduct AS p ");
		sql.append("LEFT JOIN tblproductgroup AS pg ON p.product_pg_id = pg.pg_id  ");
		sql.append("LEFT JOIN tblproductcolor AS pcolor ON p.product_id = pcolor.product_id  ");
		sql.append("LEFT JOIN tblproductsize AS psize ON p.product_id = psize.product_id  ");
		sql.append("WHERE product_delete = 0 AND product_enable = 1 ");
		sql.append("GROUP BY p.product_id ");
		sql.append("ORDER BY product_sold DESC LIMIT 16; ");
		
		// res(2) san pham moi
		sql.append("SELECT * FROM tblproduct ORDER BY product_id DESC LIMIT 20; ");
		
		// res(3) san pham sieu giam gia
		sql.append("SELECT *, MAX(((product_price - product_promotion_price ) / product_price) *100) AS percent_discount ");
		sql.append("FROM tblproduct ");
		sql.append("WHERE product_best_seller=1 ");
		sql.append("GROUP BY product_id ");
		sql.append("HAVING percent_discount > 10 ");
		sql.append("ORDER BY percent_discount DESC ");
		sql.append("LIMIT 20; ");
		
		// res(4) danh sach thanh phan san pham theo danh muc
		sql.append("SELECT * FROM tblproductsection WHERE ps_pg_id = ").append(similar.getProduct_pg_id()).append("; ");
		
		// res(5) danh sach thanh phan san pham theo danh muc
		sql.append("SELECT * FROM tblproductcategory WHERE pc_pg_id = ").append(similar.getProduct_pg_id()).append(" AND pc_ps_id =  ").append(similar.getProduct_ps_id()).append("; ");
		
		// res(6) danh sach kich co san pham
		sql.append("SELECT * FROM tblsize WHERE s_delete=0; ");
		
		// res(7) danh sach mau sac san pham
		sql.append("SELECT * FROM tblcolor WHERE c_delete=0; ");
		
		// res(8) danh sach loc san pham
		if(!isDetail) {
			sql.append("SELECT * FROM tblproduct WHERE product_delete = 0 AND product_enable = 1 ");	
			if(similar.getProduct_pg_id() > 0) {
				sql.append("AND product_pg_id = ").append(similar.getProduct_pg_id());
				if(similar.getProduct_ps_id() > 0) {
					sql.append(" AND product_ps_id =").append(similar.getProduct_ps_id());
					if(similar.getProduct_pc_id() > 0) {
						sql.append(" AND product_pc_id =").append(similar.getProduct_pc_id());
					} else {
						sql.append(" ");
					}
				} else {
					sql.append(" ");
				}
			} else {
				sql.append(" ");
			}
			System.out.println(so.getValue0());
			switch (so.getValue0()) {
			case ID:
				sql.append(" ORDER BY product_id ").append(so.getValue1().name());
				break;
			case PRICE:
				sql.append(" ORDER BY product_price  ").append(so.getValue1().name());
				break;
			default:
				sql.append(" ORDER BY product_id DESC ");
			}
			sql.append(" LIMIT ").append(at).append(", ").append(total).append("; ");			
		} else {
			sql.append("SELECT * FROM tblproduct WHERE product_enable = 1 AND product_delete = 0 AND product_id = ").append(similar.getProduct_id());
			if(similar.getProduct_pc_id() > 0) {
				sql.append(" OR product_pc_id =").append(similar.getProduct_pc_id()).append("; ");
			} else {
				sql.append("; ");
			}
		}

		System.out.println(sql.toString());
		return this.getMR(sql.toString());
	}

}
