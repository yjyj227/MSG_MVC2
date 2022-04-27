package action.gboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.GBoardDAO;
import beans.GBoardDTO;

import action.CommandAction;

public class G_UpdateFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		int g_number=Integer.parseInt(request.getParameter("g_number"));
		String pageNum=request.getParameter("pageNum");
		System.out.println("G_UpdateFormAction에서의 pageNum=>"+pageNum+", g_number=>"+g_number);
		
		String g_ref=request.getParameter("g_ref");
		
		GBoardDAO dbPro=new GBoardDAO();
		GBoardDTO article=dbPro.updateGetArticle(g_number);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("article", article); //${article.num}
		request.setAttribute("g_ref", g_ref);
		
		return "/board/ghibli/G_UpdateForm.jsp";
	}

}
