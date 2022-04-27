package action.zboard.zcomment;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.ZBoardDAO;
import beans.ZBoardDTO;
import beans.ZCommentDAO;
import beans.ZCommentDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class Z_CommentWriteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		String mem_nickname=(String)session.getAttribute("mem_nickname");
		System.out.println("Z_CommentWriteProc.do의 mem_id=>"+mem_id+", mem_nickname=>"+mem_nickname);
		
		//String z_ref=request.getParameter("z_ref");
		
		ZCommentDTO comment=new ZCommentDTO();
		
		comment.setZ_cnumber(Integer.parseInt(request.getParameter("z_cnumber")));
		comment.setMem_id(request.getParameter("mem_id"));
		comment.setZ_number(Integer.parseInt(request.getParameter("z_number")));
		comment.setZ_cbody(request.getParameter("z_cbody"));
		comment.setZ_cnickname(request.getParameter("z_cnickname"));
		comment.setZ_cdate(new Timestamp(System.currentTimeMillis()));
		
		int z_cref=Integer.parseInt(request.getParameter("z_cref"));
		int z_cre_step=Integer.parseInt(request.getParameter("z_cre_step"));
		int z_cre_level=Integer.parseInt(request.getParameter("z_cre_level"));
		System.out.println("Z_CommentWriteProc.do의 z_cref=>"+z_cref+", z_cre_step=>"+z_cre_step+", z_cre_level=>"+z_cre_level);
		
		comment.setZ_cref(Integer.parseInt(request.getParameter("z_cref")));
		comment.setZ_cre_step(Integer.parseInt(request.getParameter("z_cre_step")));
		comment.setZ_cre_level(Integer.parseInt(request.getParameter("z_cre_level")));
		
		ZCommentDAO dbPro=new ZCommentDAO();
		dbPro.insertComment(comment);
		
		
		return "/board/zzang/Z_CommentWriteProc.jsp";
	}

}
