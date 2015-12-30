package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import com.alibaba.fastjson.JSON;

import dao.MenuDao;
import entity.Menu;

public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		System.out.println("pid:" + request.getParameter("pid"));
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		int pid = 0;
		MenuDao dao = new MenuDao();
		List<Menu> menus = new ArrayList<Menu>();
		if (request.getParameter("pid") == null
				|| "".equals(request.getParameter("pid"))) {
			try {
				menus = dao.getRoot();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			pid = Integer.parseInt(request.getParameter("pid"));
			try {
				menus = dao.getNextNodes(pid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String str = JSON.toJSONString(menus);
		System.out.println("str:" + str);
		out.print(str);

	}
}