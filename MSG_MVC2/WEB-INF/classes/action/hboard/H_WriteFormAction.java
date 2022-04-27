package action.hboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import action.CommandAction;

public class H_WriteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String pageNum=request.getParameter("pageNum");
		
		int h_number=0;

		//noticeContent.jsp에서 매개변수가 전달됨
		if (request.getParameter("h_number")!=null) {
			h_number=Integer.parseInt(request.getParameter("h_number"));
			
			System.out.println("H_Content.do에서 넘어온 매개변수 확인");
			System.out.println("h_number=>"+h_number);
		}
		System.out.println("h_number가 null일 때-->> h_number=>"+h_number);
		
		request.setAttribute("h_number", h_number); //${num}
		
		return "/board/harry/H_WriteForm.jsp";
	}

}
