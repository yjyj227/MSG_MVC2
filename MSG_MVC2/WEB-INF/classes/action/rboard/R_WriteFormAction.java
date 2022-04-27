package action.rboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import action.CommandAction;

public class R_WriteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String pageNum=request.getParameter("pageNum");
		
		int r_number=0;

		//noticeContent.jsp에서 매개변수가 전달됨
		if (request.getParameter("r_number")!=null) {
			r_number=Integer.parseInt(request.getParameter("r_number"));
			
			System.out.println("R_Content.do에서 넘어온 매개변수 확인");
			System.out.println("r_number=>"+r_number);
		}
		System.out.println("r_number가 null일 때-->> r_number=>"+r_number);
		
		request.setAttribute("r_number", r_number); //${num}
		
		return "/board/ring/R_WriteForm.jsp";
	}

}
