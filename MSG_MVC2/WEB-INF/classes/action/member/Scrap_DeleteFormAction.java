package action.member;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.BoardDAO;
import beans.BoardDTO;

import action.CommandAction;

public class Scrap_DeleteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int scrap_number=Integer.parseInt(request.getParameter("scrap_number"));
		String s_title=request.getParameter("s_title");
		int s_category=Integer.parseInt(request.getParameter("s_category"));
		int s_number=Integer.parseInt(request.getParameter("s_number"));
		String pageNum=request.getParameter("pageNum");
		System.out.println("Scrap_DeleteFormAction의 scrap_number=>"+scrap_number+", s_title=>"+s_title+", pageNum=>"+pageNum);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("scrap_number", scrap_number);
		request.setAttribute("s_category", s_category);
		request.setAttribute("s_number", s_number);
		request.setAttribute("s_title", s_title);
		
		return "/member/Scrap_DeleteForm.jsp";
	}

}
