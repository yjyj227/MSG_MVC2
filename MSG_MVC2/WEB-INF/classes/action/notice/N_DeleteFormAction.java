package action.notice;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.NoticeDAO;
import beans.NoticeDTO;

import action.CommandAction;

public class N_DeleteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int notice_number=Integer.parseInt(request.getParameter("notice_number"));
		String pageNum=request.getParameter("pageNum");
		System.out.println("N_DeleteFormAction의 notice_number=>"+notice_number+", pageNum=>"+pageNum);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("notice_number", notice_number);
		
		return "/board/notice/N_DeleteForm.jsp";
	}

}
