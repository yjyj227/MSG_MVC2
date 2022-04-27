package action.gboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.GBoardDAO;
import beans.GBoardDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class G_UpdateProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		String pageNum=request.getParameter("pageNum");
		System.out.println("G_UpdateProcAction에서의 pageNum=>"+pageNum);
		String g_ref=request.getParameter("g_ref");
		
		GBoardDTO article=new GBoardDTO();
		
		article.setG_number(Integer.parseInt(request.getParameter("g_number")));
		article.setMem_id(request.getParameter("mem_id"));
		article.setG_nickname(request.getParameter("g_nickname"));
		article.setG_title(request.getParameter("g_title"));
		article.setG_body(request.getParameter("g_body"));
		article.setG_category(Integer.parseInt(request.getParameter("g_category")));
		article.setG_ref(Integer.parseInt(request.getParameter("g_ref")));
		
		GBoardDAO dbPro=new GBoardDAO();
		int check=dbPro.updateArticle(article);
		
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("check", check); //${check} 데이터 수정성공유무
		request.setAttribute("g_ref", g_ref);
		
		return "/board/ghibli/G_UpdateProc.jsp";
	}

}
