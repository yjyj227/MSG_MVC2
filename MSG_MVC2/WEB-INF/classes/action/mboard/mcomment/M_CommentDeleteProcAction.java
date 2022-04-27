package action.mboard.mcomment;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.MCommentDAO;
import beans.MCommentDTO;

import action.CommandAction;

public class M_CommentDeleteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		String pageNum=request.getParameter("pageNum"); //hidden
		//추가
		int m_cnumber=Integer.parseInt(request.getParameter("m_cnumber"));
		int m_number=Integer.parseInt(request.getParameter("m_number"));
		int m_cref=Integer.parseInt(request.getParameter("m_cref"));
		int m_cre_level=Integer.parseInt(request.getParameter("m_cre_level"));
		System.out.println("M_CommentDeleteProcAction의 m_number=>"+m_cnumber+", pageNum=>"+pageNum+", m_number=>"+m_number+", m_cref=>"+m_cref);
		
		MCommentDAO dbPro=new MCommentDAO();
		
		
		
		int check=dbPro.upDeleteComment(m_cnumber, m_cref, m_cre_level);
		
		request.setAttribute("check", check); //${check} 데이터 수정성공유무
		request.setAttribute("m_cnumber", m_cnumber);
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("m_number", m_number);
		request.setAttribute("m_cref", m_cref);
		request.setAttribute("m_cre_level", m_cre_level);
		
		return "/board/marvel/M_CommentDeleteProc.jsp";
	}

}
