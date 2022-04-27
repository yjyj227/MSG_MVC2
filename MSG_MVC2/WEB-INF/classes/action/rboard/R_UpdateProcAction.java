package action.rboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.RBoardDAO;
import beans.RBoardDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class R_UpdateProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		String pageNum=request.getParameter("pageNum");
		System.out.println("R_UpdateProcAction에서의 pageNum=>"+pageNum);
		String r_ref=request.getParameter("r_ref");
		
		RBoardDTO article=new RBoardDTO();
		
		article.setR_number(Integer.parseInt(request.getParameter("r_number")));
		article.setMem_id(request.getParameter("mem_id"));
		article.setR_nickname(request.getParameter("r_nickname"));
		article.setR_title(request.getParameter("r_title"));
		article.setR_body(request.getParameter("r_body"));
		article.setR_category(Integer.parseInt(request.getParameter("r_category")));
		article.setR_ref(Integer.parseInt(request.getParameter("r_ref")));
		
		RBoardDAO dbPro=new RBoardDAO();
		int check=dbPro.updateArticle(article);
		
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("check", check); //${check} 데이터 수정성공유무
		request.setAttribute("r_ref", r_ref);
		
		return "/board/ring/R_UpdateProc.jsp";
	}

}
