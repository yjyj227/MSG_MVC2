package action.gboard.gcomment;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.GBoardDAO;
import beans.GBoardDTO;
import beans.GCommentDAO;
import beans.GCommentDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class G_CommentWriteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		String mem_nickname=(String)session.getAttribute("mem_nickname");
		System.out.println("G_CommentWriteProc.do의 mem_id=>"+mem_id+", mem_nickname=>"+mem_nickname);
		
		//String g_ref=request.getParameter("g_ref");
		
		GCommentDTO comment=new GCommentDTO();
		
		comment.setG_cnumber(Integer.parseInt(request.getParameter("g_cnumber")));
		comment.setMem_id(request.getParameter("mem_id"));
		comment.setG_number(Integer.parseInt(request.getParameter("g_number")));
		comment.setG_cbody(request.getParameter("g_cbody"));
		comment.setG_cnickname(request.getParameter("g_cnickname"));
		comment.setG_cdate(new Timestamp(System.currentTimeMillis()));
		
		int g_cref=Integer.parseInt(request.getParameter("g_cref"));
		int g_cre_step=Integer.parseInt(request.getParameter("g_cre_step"));
		int g_cre_level=Integer.parseInt(request.getParameter("g_cre_level"));
		System.out.println("G_CommentWriteProc.do의 g_cref=>"+g_cref+", g_cre_step=>"+g_cre_step+", g_cre_level=>"+g_cre_level);
		
		comment.setG_cref(Integer.parseInt(request.getParameter("g_cref")));
		comment.setG_cre_step(Integer.parseInt(request.getParameter("g_cre_step")));
		comment.setG_cre_level(Integer.parseInt(request.getParameter("g_cre_level")));
		
		GCommentDAO dbPro=new GCommentDAO();
		dbPro.insertComment(comment);
		
		
		return "/board/ghibli/G_CommentWriteProc.jsp";
	}

}
