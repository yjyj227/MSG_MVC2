package action.zboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.ZBoardDAO;
import beans.ZBoardDTO;

import action.CommandAction;

public class Z_DeleteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int z_number=Integer.parseInt(request.getParameter("z_number"));
		String z_title=request.getParameter("z_title");
		String pageNum=request.getParameter("pageNum");
		System.out.println("Z_DeleteFormAction의 z_number=>"+z_number+", z_title=>"+z_title+", pageNum=>"+pageNum);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("z_number", z_number);
		request.setAttribute("z_title", z_title);
		
		return "/board/zzang/Z_DeleteForm.jsp";
	}

}
