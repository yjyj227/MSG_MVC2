package action.rboard.rcomment;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.RCommentDAO;
import beans.RCommentDTO;

import action.CommandAction;

public class R_CommentDeleteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		String pageNum=request.getParameter("pageNum"); //hidden
		//추가
		int r_cnumber=Integer.parseInt(request.getParameter("r_cnumber"));
		int r_number=Integer.parseInt(request.getParameter("r_number"));
		int r_cref=Integer.parseInt(request.getParameter("r_cref"));
		int r_cre_level=Integer.parseInt(request.getParameter("r_cre_level"));
		System.out.println("R_CommentDeleteProcAction의 r_number=>"+r_cnumber+", pageNum=>"+pageNum+", r_number=>"+r_number+", r_cref=>"+r_cref);
		
		RCommentDAO dbPro=new RCommentDAO();
		
		
		
		int check=dbPro.upDeleteComment(r_cnumber, r_cref, r_cre_level);
		
		request.setAttribute("check", check); //${check} 데이터 수정성공유무
		request.setAttribute("r_cnumber", r_cnumber);
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("r_number", r_number);
		request.setAttribute("r_cref", r_cref);
		request.setAttribute("r_cre_level", r_cre_level);
		
		return "/board/ring/R_CommentDeleteProc.jsp";
	}

}
