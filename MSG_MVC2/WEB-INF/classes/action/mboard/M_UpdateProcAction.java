package action.mboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.MBoardDAO;
import beans.MBoardDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class M_UpdateProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		String pageNum=request.getParameter("pageNum");
		System.out.println("M_UpdateProcAction에서의 pageNum=>"+pageNum);
		String m_ref=request.getParameter("m_ref");
		
		MBoardDTO article=new MBoardDTO();
		
		article.setM_number(Integer.parseInt(request.getParameter("m_number")));
		article.setMem_id(request.getParameter("mem_id"));
		article.setM_nickname(request.getParameter("m_nickname"));
		article.setM_title(request.getParameter("m_title"));
		article.setM_body(request.getParameter("m_body"));
		article.setM_category(Integer.parseInt(request.getParameter("m_category")));
		article.setM_ref(Integer.parseInt(request.getParameter("m_ref")));
		
		MBoardDAO dbPro=new MBoardDAO();
		int check=dbPro.updateArticle(article);
		
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("check", check); //${check} 데이터 수정성공유무
		request.setAttribute("m_ref", m_ref);
		
		return "/board/marvel/M_UpdateProc.jsp";
	}

}
