package action.hboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.HBoardDAO;
import beans.HBoardDTO;

import action.CommandAction;

public class H_DeleteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int h_number=Integer.parseInt(request.getParameter("h_number"));
		String h_title=request.getParameter("h_title");
		String h_mem_id=request.getParameter("h_mem_id");
		String pageNum=request.getParameter("pageNum");
		System.out.println("H_DeleteFormAction의 h_number=>"+h_number+", pageNum=>"+pageNum);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("h_number", h_number);
		request.setAttribute("h_title", h_title);
		request.setAttribute("h_mem_id", h_mem_id);
		
		return "/board/harry/H_DeleteForm.jsp";
	}

}
