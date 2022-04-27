package action.rboard.rcomment;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.RCommentDAO;
import beans.RCommentDTO;

import action.CommandAction;

public class R_CommentDeleteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int r_cnumber=Integer.parseInt(request.getParameter("r_cnumber"));
		int r_number=Integer.parseInt(request.getParameter("r_number"));
		int r_cref=Integer.parseInt(request.getParameter("r_cref"));
		int r_cre_level=Integer.parseInt(request.getParameter("r_cre_level"));
		String pageNum=request.getParameter("pageNum");
		System.out.println("R_CommentDeleteFormAction의 r_cnumber=>"+r_cnumber+", pageNum=>"+pageNum+", r_number=>"+r_number+", r_cref=>"+r_cref);
		
		request.setAttribute("r_cnumber", r_cnumber);
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("r_number", r_number);
		request.setAttribute("r_cref", r_cref);
		request.setAttribute("r_cre_level", r_cre_level);
		
		return "/board/ring/R_CommentDeleteForm.jsp";
	}

}
