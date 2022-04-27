package action.member;

import java.sql.Timestamp;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.*;

import action.CommandAction;

public class ScrapAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey");
		System.out.println("ScrapAction의 mem_id=>"+mem_id);
		
		BoardDTO article=new BoardDTO();
		System.out.println("article=>"+article);
		
		String scrapurl="";
		int s_category=Integer.parseInt(request.getParameter("s_category"));
		int s_number=Integer.parseInt(request.getParameter("s_number"));
		
		BoardDAO board=new BoardDAO();
		scrapurl=board.makeScrapUrl(s_category, s_number);
		System.out.println("scrapurl=>"+scrapurl);
		
		int scrap_number=0;
		
		if (request.getParameter("scrap_number")!=null) {
			scrap_number=Integer.parseInt(request.getParameter("scrap_number"));
			System.out.println("scrap_number!=null일 때=>"+scrap_number);
		}else {
			System.out.println("scrap_number==null일 때=>"+scrap_number);
		}
		
		article.setScrap_number(scrap_number);
		article.setMem_id(mem_id);
		article.setS_number(Integer.parseInt(request.getParameter("s_number")));
		article.setS_category(Integer.parseInt(request.getParameter("s_category")));
		article.setS_title(request.getParameter("s_title"));
		article.setS_date(new Timestamp(System.currentTimeMillis()));
		article.setS_url(scrapurl);
		article.setS_nickname(request.getParameter("s_nickname"));
		
		board.scrap(article);
		
		request.setAttribute("scrapurl", scrapurl);
		
		return "/member/Scrap.jsp";
	}

}
