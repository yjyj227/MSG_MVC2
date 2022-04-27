package action.mboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.MBoardDAO;
import beans.MBoardDTO;

import action.CommandAction;

public class M_UpdateFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		int m_number=Integer.parseInt(request.getParameter("m_number"));
		String pageNum=request.getParameter("pageNum");
		System.out.println("M_UpdateFormAction에서의 pageNum=>"+pageNum+", m_number=>"+m_number);
		
		String m_ref=request.getParameter("m_ref");
		
		MBoardDAO dbPro=new MBoardDAO();
		MBoardDTO article=dbPro.updateGetArticle(m_number);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("article", article); //${article.num}
		request.setAttribute("m_ref", m_ref);
		
		return "/board/marvel/M_UpdateForm.jsp";
	}

}
