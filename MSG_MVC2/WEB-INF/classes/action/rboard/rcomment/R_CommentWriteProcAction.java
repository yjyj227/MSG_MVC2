package action.rboard.rcomment;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.RBoardDAO;
import beans.RBoardDTO;
import beans.RCommentDAO;
import beans.RCommentDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class R_CommentWriteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		String mem_nickname=(String)session.getAttribute("mem_nickname");
		System.out.println("R_CommentWriteProc.do의 mem_id=>"+mem_id+", mem_nickname=>"+mem_nickname);
		
		//String r_ref=request.getParameter("r_ref");
		
		RCommentDTO comment=new RCommentDTO();
		
		comment.setR_cnumber(Integer.parseInt(request.getParameter("r_cnumber")));
		comment.setMem_id(request.getParameter("mem_id"));
		comment.setR_number(Integer.parseInt(request.getParameter("r_number")));
		comment.setR_cbody(request.getParameter("r_cbody"));
		comment.setR_cnickname(request.getParameter("r_cnickname"));
		comment.setR_cdate(new Timestamp(System.currentTimeMillis()));
		
		int r_cref=Integer.parseInt(request.getParameter("r_cref"));
		int r_cre_step=Integer.parseInt(request.getParameter("r_cre_step"));
		int r_cre_level=Integer.parseInt(request.getParameter("r_cre_level"));
		System.out.println("R_CommentWriteProc.do의 r_cref=>"+r_cref+", r_cre_step=>"+r_cre_step+", r_cre_level=>"+r_cre_level);
		
		comment.setR_cref(Integer.parseInt(request.getParameter("r_cref")));
		comment.setR_cre_step(Integer.parseInt(request.getParameter("r_cre_step")));
		comment.setR_cre_level(Integer.parseInt(request.getParameter("r_cre_level")));
		
		RCommentDAO dbPro=new RCommentDAO();
		dbPro.insertComment(comment);
		
		
		return "/board/ring/R_CommentWriteProc.jsp";
	}

}
