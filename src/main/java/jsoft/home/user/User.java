package jsoft.home.user;

import jsoft.ShareControl;
import jsoft.ads.basic.Basic;
import jsoft.library.ORDER;
import jsoft.objects.*;
import java.sql.*;
import java.util.*;

import org.javatuples.*;

public interface User extends ShareControl {
	// các chức năng cập nhật
	public boolean addUser(UserObject item);

}
