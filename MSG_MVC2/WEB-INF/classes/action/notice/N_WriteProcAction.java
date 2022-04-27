package action.notice;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.NoticeDAO;
import beans.NoticeDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class N_WriteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		System.out.println("N_WriteProc.do의 mem_id=>"+mem_id);
		
		//String h_ref=request.getParameter("h_ref");
		
		NoticeDTO article=new NoticeDTO();
		
		article.setNotice_number(Integer.parseInt(request.getParameter("notice_number")));
		article.setAdmin_id(request.getParameter("admin_id"));
		article.setNot_title(request.getParameter("not_title"));
		article.setNot_body(request.getParameter("not_body"));
		
		article.setNot_date(new Timestamp(System.currentTimeMillis()));
		
		NoticeDAO dbPro=new NoticeDAO();
		dbPro.insertArticle(article);
		
		
		return "/board/notice/N_WriteProc.jsp";
	}

}
