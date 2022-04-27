package action.hboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.HBoardDAO;
import beans.HBoardDTO;

import action.CommandAction;

public class H_UpdateFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		int h_number=Integer.parseInt(request.getParameter("h_number"));
		String pageNum=request.getParameter("pageNum");
		System.out.println("H_UpdateFormAction에서의 pageNum=>"+pageNum+", h_number=>"+h_number);
		
		String h_ref=request.getParameter("h_ref");
		
		HBoardDAO dbPro=new HBoardDAO();
		HBoardDTO article=dbPro.updateGetArticle(h_number);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("article", article); //${article.num}
		request.setAttribute("h_ref", h_ref);
		
		return "/board/harry/H_UpdateForm.jsp";
	}

}
