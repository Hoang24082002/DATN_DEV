package jsoft.home.user;

import jsoft.*;
import jsoft.library.ORDER;
import jsoft.objects.*;

import java.sql.ResultSet;
import java.util.*;
import org.javatuples.*;

public class UserControl {
	private UserModel um;

	public UserControl(ConnectionPool cp) {
		this.um = new UserModel(cp);
	}

	public ConnectionPool getCP() {
		return this.um.getCP();
	}

	public void releaseConnection() {
		this.um.releaseConnection();
	}

//	------------------------------------------
	public boolean addUser(UserObject item) {
		return this.um.addUser(item);
	}
}
