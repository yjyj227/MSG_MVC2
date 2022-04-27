package action.hboard.hcomment;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.HBoardDAO;
import beans.HBoardDTO;
import beans.HCommentDAO;
import beans.HCommentDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class H_CommentWriteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		String mem_nickname=(String)session.getAttribute("mem_nickname");
		System.out.println("H_CommentWriteProc.do의 mem_id=>"+mem_id+", mem_nickname=>"+mem_nickname);
		
		//String h_ref=request.getParameter("h_ref");
		
		HCommentDTO comment=new HCommentDTO();
		
		comment.setH_cnumber(Integer.parseInt(request.getParameter("h_cnumber")));
		comment.setMem_id(request.getParameter("mem_id"));
		comment.setH_number(Integer.parseInt(request.getParameter("h_number")));
		comment.setH_cbody(request.getParameter("h_cbody"));
		comment.setH_cnickname(request.getParameter("h_cnickname"));
		comment.setH_cdate(new Timestamp(System.currentTimeMillis()));
		
		int h_cref=Integer.parseInt(request.getParameter("h_cref"));
		int h_cre_step=Integer.parseInt(request.getParameter("h_cre_step"));
		int h_cre_level=Integer.parseInt(request.getParameter("h_cre_level"));
		System.out.println("H_CommentWriteProc.do의 h_cref=>"+h_cref+", h_cre_step=>"+h_cre_step+", h_cre_level=>"+h_cre_level);
		
		comment.setH_cref(Integer.parseInt(request.getParameter("h_cref")));
		comment.setH_cre_step(Integer.parseInt(request.getParameter("h_cre_step")));
		comment.setH_cre_level(Integer.parseInt(request.getParameter("h_cre_level")));
		
		HCommentDAO dbPro=new HCommentDAO();
		dbPro.insertComment(comment);
		
		
		return "/board/harry/H_CommentWriteProc.jsp";
	}

}
