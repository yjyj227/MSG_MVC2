package action.gboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.GBoardDAO;
import beans.GBoardDTO;

import action.CommandAction;

public class G_DeleteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int g_number=Integer.parseInt(request.getParameter("g_number"));
		String g_title=request.getParameter("g_title");
		String pageNum=request.getParameter("pageNum");
		System.out.println("G_DeleteFormAction의 g_number=>"+g_number+", g_title=>"+g_title+", pageNum=>"+pageNum);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("g_number", g_number);
		request.setAttribute("g_title", g_title);
		
		return "/board/ghibli/G_DeleteForm.jsp";
	}

}
