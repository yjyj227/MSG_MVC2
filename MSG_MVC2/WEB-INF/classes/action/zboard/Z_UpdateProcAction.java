package action.zboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.ZBoardDAO;
import beans.ZBoardDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class Z_UpdateProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		String pageNum=request.getParameter("pageNum");
		System.out.println("Z_UpdateProcAction에서의 pageNum=>"+pageNum);
		String z_ref=request.getParameter("z_ref");
		
		ZBoardDTO article=new ZBoardDTO();
		
		article.setZ_number(Integer.parseInt(request.getParameter("z_number")));
		article.setMem_id(request.getParameter("mem_id"));
		article.setZ_nickname(request.getParameter("z_nickname"));
		article.setZ_title(request.getParameter("z_title"));
		article.setZ_body(request.getParameter("z_body"));
		article.setZ_category(Integer.parseInt(request.getParameter("z_category")));
		article.setZ_ref(Integer.parseInt(request.getParameter("z_ref")));
		
		ZBoardDAO dbPro=new ZBoardDAO();
		int check=dbPro.updateArticle(article);
		
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("check", check); //${check} 데이터 수정성공유무
		request.setAttribute("z_ref", z_ref);
		
		return "/board/zzang/Z_UpdateProc.jsp";
	}

}
