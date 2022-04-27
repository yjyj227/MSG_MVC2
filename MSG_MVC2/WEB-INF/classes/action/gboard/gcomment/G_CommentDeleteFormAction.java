package action.gboard.gcomment;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.GCommentDAO;
import beans.GCommentDTO;

import action.CommandAction;

public class G_CommentDeleteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int g_cnumber=Integer.parseInt(request.getParameter("g_cnumber"));
		int g_number=Integer.parseInt(request.getParameter("g_number"));
		int g_cref=Integer.parseInt(request.getParameter("g_cref"));
		int g_cre_level=Integer.parseInt(request.getParameter("g_cre_level"));
		String pageNum=request.getParameter("pageNum");
		System.out.println("G_CommentDeleteFormAction의 g_cnumber=>"+g_cnumber+", pageNum=>"+pageNum+", g_number=>"+g_number+", g_cref=>"+g_cref);
		
		request.setAttribute("g_cnumber", g_cnumber);
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("g_number", g_number);
		request.setAttribute("g_cref", g_cref);
		request.setAttribute("g_cre_level", g_cre_level);
		
		return "/board/ghibli/G_CommentDeleteForm.jsp";
	}

}
