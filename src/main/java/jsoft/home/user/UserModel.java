package jsoft.home.user;

import jsoft.*;
import jsoft.library.ORDER;
import jsoft.objects.*;

import java.sql.*;
import java.util.*;

import org.javatuples.Pair;
import org.javatuples.Triplet;

public class UserModel {
	private User u;
	public UserModel(ConnectionPool cp) {
		this.u = new UserImpl(cp);
	}
	
	public ConnectionPool getCP() {
		return this.u.getCP();
	}
	
	public void releaseConnection() {
		this.u.releaseConnection();
	}
	
//	---------------------------------------
	public boolean addUser(UserObject item) {
		return this.u.addUser(item);
	}

}
