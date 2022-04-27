package action.hboard.hcomment;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.HCommentDAO;
import beans.HCommentDTO;

import action.CommandAction;

public class H_CommentDeleteFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int h_cnumber=Integer.parseInt(request.getParameter("h_cnumber"));
		int h_number=Integer.parseInt(request.getParameter("h_number"));
		int h_cref=Integer.parseInt(request.getParameter("h_cref"));
		int h_cre_level=Integer.parseInt(request.getParameter("h_cre_level"));
		String h_cmem_id=request.getParameter("h_cmem_id");
		String pageNum=request.getParameter("pageNum");
		System.out.println("H_CommentDeleteFormAction의 h_cnumber=>"+h_cnumber+", pageNum=>"+pageNum+", h_number=>"+h_number+", h_cref=>"+h_cref);
		
		request.setAttribute("h_cnumber", h_cnumber);
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("h_number", h_number);
		request.setAttribute("h_cref", h_cref);
		request.setAttribute("h_cre_level", h_cre_level);
		request.setAttribute("h_cmem_id", h_cmem_id);
		
		return "/board/harry/H_CommentDeleteForm.jsp";
	}

}
