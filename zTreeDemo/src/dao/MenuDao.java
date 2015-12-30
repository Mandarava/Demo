package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import entity.Menu;

@SuppressWarnings("rawtypes")
public class MenuDao {

	public List getRoot() throws Exception {
		Connection conn = DBUtil.getConnection();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from dept where pid is null");
		Menu menu;
		List<Menu> menus = new ArrayList<Menu>();
		while (rs.next()) {
			menu = new Menu();
			menu.setId(rs.getInt("id"));
			menu.setPid(rs.getInt("pid"));
			menu.setName(rs.getString("name"));
			if ("1".equals(rs.getString("isParent"))) {
				menu.setIsParent(true);
			} else {
				menu.setIsParent(false);
			}
			menus.add(menu);
		}
		for (int i = 0; i < menus.size(); i++) {
			System.out.println(menus.get(i).getName());
		}
		return menus;
	}

	public List getNextNodes(int pid) throws Exception {
		Connection conn = DBUtil.getConnection();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from dept where pid =" + pid);
		Menu menu;
		List<Menu> menus = new ArrayList<Menu>();
		while (rs.next()) {
			menu = new Menu();
			menu.setId(rs.getInt("id"));
			menu.setPid(rs.getInt("pid"));
			menu.setName(rs.getString("name"));
			if ("1".equals(rs.getString("isParent"))) {
				menu.setIsParent(true);
			} else {
				menu.setIsParent(false);
			}
			menus.add(menu);
		}
		for (int i = 0; i < menus.size(); i++) {
			System.out.println(menus.get(i).getName());
		}
		return menus;

	}

}