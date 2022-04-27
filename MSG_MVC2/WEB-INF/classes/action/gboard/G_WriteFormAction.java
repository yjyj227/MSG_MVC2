package action.gboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import action.CommandAction;

public class G_WriteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String pageNum=request.getParameter("pageNum");
		
		int g_number=0;

		//noticeContent.jsp에서 매개변수가 전달됨
		if (request.getParameter("g_number")!=null) {
			g_number=Integer.parseInt(request.getParameter("g_number"));
			
			System.out.println("G_Content.do에서 넘어온 매개변수 확인");
			System.out.println("g_number=>"+g_number);
		}
		System.out.println("g_number가 null일 때-->> g_number=>"+g_number);
		
		request.setAttribute("g_number", g_number); //${num}
		
		return "/board/ghibli/G_WriteForm.jsp";
	}

}
