package action.notice;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.NoticeDAO;
import beans.NoticeDTO;

import action.CommandAction;

public class N_UpdateFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		int notice_number=Integer.parseInt(request.getParameter("notice_number"));
		String pageNum=request.getParameter("pageNum");
		System.out.println("N_UpdateFormAction에서의 pageNum=>"+pageNum+", notice_number=>"+notice_number);
		
		NoticeDAO dbPro=new NoticeDAO();
		NoticeDTO article=dbPro.updateGetArticle(notice_number);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("article", article); //${article.num}
		
		return "/board/notice/N_UpdateForm.jsp";
	}

}
