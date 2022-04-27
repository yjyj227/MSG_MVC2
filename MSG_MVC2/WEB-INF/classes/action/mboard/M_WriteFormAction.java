package action.mboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import action.CommandAction;

public class M_WriteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String pageNum=request.getParameter("pageNum");
		
		int m_number=0;

		//noticeContent.jsp에서 매개변수가 전달됨
		if (request.getParameter("m_number")!=null) {
			m_number=Integer.parseInt(request.getParameter("m_number"));
			
			System.out.println("M_Content.do에서 넘어온 매개변수 확인");
			System.out.println("m_number=>"+m_number);
		}
		System.out.println("m_number가 null일 때-->> m_number=>"+m_number);
		
		request.setAttribute("m_number", m_number); //${num}
		
		return "/board/marvel/M_WriteForm.jsp";
	}

}
