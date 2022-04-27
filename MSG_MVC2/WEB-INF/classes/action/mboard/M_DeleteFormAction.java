package action.mboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.MBoardDAO;
import beans.MBoardDTO;

import action.CommandAction;

public class M_DeleteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int m_number=Integer.parseInt(request.getParameter("m_number"));
		String m_title=request.getParameter("m_title");
		String pageNum=request.getParameter("pageNum");
		System.out.println("M_DeleteFormAction의 m_number=>"+m_number+", m_title=>"+m_title+", pageNum=>"+pageNum);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("m_number", m_number);
		request.setAttribute("m_title", m_title);
		
		return "/board/marvel/M_DeleteForm.jsp";
	}

}
