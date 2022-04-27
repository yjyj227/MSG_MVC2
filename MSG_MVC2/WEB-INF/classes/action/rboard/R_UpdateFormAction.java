package action.rboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.RBoardDAO;
import beans.RBoardDTO;

import action.CommandAction;

public class R_UpdateFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		int r_number=Integer.parseInt(request.getParameter("r_number"));
		String pageNum=request.getParameter("pageNum");
		System.out.println("R_UpdateFormAction에서의 pageNum=>"+pageNum+", r_number=>"+r_number);
		
		String r_ref=request.getParameter("r_ref");
		
		RBoardDAO dbPro=new RBoardDAO();
		RBoardDTO article=dbPro.updateGetArticle(r_number);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("article", article); //${article.num}
		request.setAttribute("r_ref", r_ref);
		
		return "/board/ring/R_UpdateForm.jsp";
	}

}
