package action.zboard.zcomment;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.ZCommentDAO;
import beans.ZCommentDTO;

import action.CommandAction;

public class Z_CommentDeleteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		String pageNum=request.getParameter("pageNum"); //hidden
		//추가
		int z_cnumber=Integer.parseInt(request.getParameter("z_cnumber"));
		int z_number=Integer.parseInt(request.getParameter("z_number"));
		int z_cref=Integer.parseInt(request.getParameter("z_cref"));
		int z_cre_level=Integer.parseInt(request.getParameter("z_cre_level"));
		System.out.println("Z_CommentDeleteProcAction의 z_number=>"+z_cnumber+", pageNum=>"+pageNum+", z_number=>"+z_number+", z_cref=>"+z_cref);
		
		ZCommentDAO dbPro=new ZCommentDAO();
		
		
		
		int check=dbPro.upDeleteComment(z_cnumber, z_cref, z_cre_level);
		
		request.setAttribute("check", check); //${check} 데이터 수정성공유무
		request.setAttribute("z_cnumber", z_cnumber);
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("z_number", z_number);
		request.setAttribute("z_cref", z_cref);
		request.setAttribute("z_cre_level", z_cre_level);
		
		return "/board/zzang/Z_CommentDeleteProc.jsp";
	}

}
