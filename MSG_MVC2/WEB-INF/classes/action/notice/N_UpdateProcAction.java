package action.notice;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.NoticeDAO;
import beans.NoticeDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class N_UpdateProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		String pageNum=request.getParameter("pageNum");
		System.out.println("N_UpdateProcAction에서의 pageNum=>"+pageNum);
		
		NoticeDTO article=new NoticeDTO();
		
		article.setNotice_number(Integer.parseInt(request.getParameter("notice_number")));
		article.setAdmin_id(request.getParameter("mem_id"));
		article.setNot_title(request.getParameter("not_title"));
		article.setNot_body(request.getParameter("not_body"));
		
		NoticeDAO dbPro=new NoticeDAO();
		int check=dbPro.updateArticle(article);
		
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("check", check); //${check} 데이터 수정성공유무
		
		return "/board/notice/N_UpdateProc.jsp";
	}

}
