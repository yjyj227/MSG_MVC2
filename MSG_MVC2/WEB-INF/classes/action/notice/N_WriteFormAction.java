package action.notice;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import action.CommandAction;

public class N_WriteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String pageNum=request.getParameter("pageNum");
		
		int notice_number=0;

		//noticeContent.jsp에서 매개변수가 전달됨
		if (request.getParameter("notice_number")!=null) {
			notice_number=Integer.parseInt(request.getParameter("notice_number"));
			
			System.out.println("N_Content.do에서 넘어온 매개변수 확인");
			System.out.println("notice_number=>"+notice_number);
		}
		
		request.setAttribute("notice_number", notice_number); //${num}
		
		return "/board/notice/N_WriteForm.jsp";
	}

}
