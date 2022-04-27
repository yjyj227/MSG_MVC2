package action.zboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import action.CommandAction;

public class Z_WriteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String pageNum=request.getParameter("pageNum");
		
		int z_number=0;

		//noticeContent.jsp에서 매개변수가 전달됨
		if (request.getParameter("z_number")!=null) {
			z_number=Integer.parseInt(request.getParameter("z_number"));
			
			System.out.println("Z_Content.do에서 넘어온 매개변수 확인");
			System.out.println("z_number=>"+z_number);
		}
		System.out.println("z_number가 null일 때-->> z_number=>"+z_number);
		
		request.setAttribute("z_number", z_number); //${num}
		
		return "/board/zzang/Z_WriteForm.jsp";
	}

}
