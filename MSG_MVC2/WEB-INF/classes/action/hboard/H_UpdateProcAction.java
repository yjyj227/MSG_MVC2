package action.hboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.HBoardDAO;
import beans.HBoardDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class H_UpdateProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		String pageNum=request.getParameter("pageNum");
		System.out.println("H_UpdateProcAction에서의 pageNum=>"+pageNum);
		String h_ref=request.getParameter("h_ref");
		
		HBoardDTO article=new HBoardDTO();
		
		article.setH_number(Integer.parseInt(request.getParameter("h_number")));
		article.setMem_id(request.getParameter("mem_id"));
		article.setH_nickname(request.getParameter("h_nickname"));
		article.setH_title(request.getParameter("h_title"));
		article.setH_body(request.getParameter("h_body"));
		article.setH_category(Integer.parseInt(request.getParameter("h_category")));
		article.setH_ref(Integer.parseInt(request.getParameter("h_ref")));
		
		HBoardDAO dbPro=new HBoardDAO();
		int check=dbPro.updateArticle(article);
		
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("check", check); //${check} 데이터 수정성공유무
		request.setAttribute("h_ref", h_ref);
		
		return "/board/harry/H_UpdateProc.jsp";
	}

}
