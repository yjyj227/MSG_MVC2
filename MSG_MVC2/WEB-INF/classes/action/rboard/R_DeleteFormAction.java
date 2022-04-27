package action.rboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.RBoardDAO;
import beans.RBoardDTO;

import action.CommandAction;

public class R_DeleteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int r_number=Integer.parseInt(request.getParameter("r_number"));
		String r_title=request.getParameter("r_title");
		String pageNum=request.getParameter("pageNum");
		System.out.println("R_DeleteFormAction의 r_number=>"+r_number+", r_title=>"+r_title+", pageNum=>"+pageNum);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("r_number", r_number);
		request.setAttribute("r_title", r_title);
		
		return "/board/ring/R_DeleteForm.jsp";
	}

}
