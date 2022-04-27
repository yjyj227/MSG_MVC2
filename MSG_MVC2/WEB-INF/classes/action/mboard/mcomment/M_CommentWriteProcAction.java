package action.mboard.mcomment;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.MBoardDAO;
import beans.MBoardDTO;
import beans.MCommentDAO;
import beans.MCommentDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class M_CommentWriteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		String mem_nickname=(String)session.getAttribute("mem_nickname");
		System.out.println("M_CommentWriteProc.do의 mem_id=>"+mem_id+", mem_nickname=>"+mem_nickname);
		
		//String m_ref=request.getParameter("m_ref");
		
		MCommentDTO comment=new MCommentDTO();
		
		comment.setM_cnumber(Integer.parseInt(request.getParameter("m_cnumber")));
		comment.setMem_id(request.getParameter("mem_id"));
		comment.setM_number(Integer.parseInt(request.getParameter("m_number")));
		comment.setM_cbody(request.getParameter("m_cbody"));
		comment.setM_cnickname(request.getParameter("m_cnickname"));
		comment.setM_cdate(new Timestamp(System.currentTimeMillis()));
		
		int m_cref=Integer.parseInt(request.getParameter("m_cref"));
		int m_cre_step=Integer.parseInt(request.getParameter("m_cre_step"));
		int m_cre_level=Integer.parseInt(request.getParameter("m_cre_level"));
		System.out.println("M_CommentWriteProc.do의 m_cref=>"+m_cref+", m_cre_step=>"+m_cre_step+", m_cre_level=>"+m_cre_level);
		
		comment.setM_cref(Integer.parseInt(request.getParameter("m_cref")));
		comment.setM_cre_step(Integer.parseInt(request.getParameter("m_cre_step")));
		comment.setM_cre_level(Integer.parseInt(request.getParameter("m_cre_level")));
		
		MCommentDAO dbPro=new MCommentDAO();
		dbPro.insertComment(comment);
		
		
		return "/board/marvel/M_CommentWriteProc.jsp";
	}

}
