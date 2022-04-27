package action.hboard.hcomment;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.HCommentDAO;
import beans.HCommentDTO;

import action.CommandAction;

public class H_CommentDeleteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		System.out.println("H_CommentDeleteProcAction의 mem_id=>"+mem_id); //null이라면? ...
		
		String pageNum=request.getParameter("pageNum"); //hidden
		//추가
		int h_cnumber=Integer.parseInt(request.getParameter("h_cnumber"));
		int h_number=Integer.parseInt(request.getParameter("h_number"));
		int h_cref=Integer.parseInt(request.getParameter("h_cref"));
		int h_cre_level=Integer.parseInt(request.getParameter("h_cre_level"));
		String h_cmem_id=request.getParameter("h_cmem_id");
		System.out.println("H_CommentDeleteProcAction의 h_number=>"+h_cnumber+", pageNum=>"+pageNum+", h_number=>"+h_number+", h_cref=>"+h_cref);
		
		HCommentDAO dbPro=new HCommentDAO();
		
		
		
		int check=dbPro.upDeleteComment(h_cnumber, h_cref, h_cre_level, h_cmem_id);
		
		request.setAttribute("check", check); //${check} 데이터 수정성공유무
		request.setAttribute("h_cnumber", h_cnumber);
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("h_number", h_number);
		request.setAttribute("h_cref", h_cref);
		request.setAttribute("h_cre_level", h_cre_level);
		request.setAttribute("h_cmem_id", h_cmem_id);
		
		return "/board/harry/H_CommentDeleteProc.jsp";
	}

}
