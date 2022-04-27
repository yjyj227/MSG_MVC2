package action.gboard;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.jsp.*;


import beans.GBoardDAO;
import beans.GBoardDTO;
import beans.GCommentDAO;
import beans.GCommentDTO;

import action.CommandAction;

public class G_ContentAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int g_number=Integer.parseInt(request.getParameter("g_number"));
		String pageNum=request.getParameter("pageNum");
		System.out.println("G_ContentAction의 pageNum=>"+pageNum+", g_number=>"+g_number);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		GBoardDAO dbPro=new GBoardDAO();
		GBoardDTO article=dbPro.getArticle(g_number);
		
		//String content=article.getContent().replace("\r\n", "<br>");
		//content=content.replace("\r\n", "<br>");
		
		
		//2. 실행결과를 서버의 메모리에 저장->request에 저장->jsp ${키명}으로 불러올 수 있도록 공유
		request.setAttribute("g_number", g_number); //${num}
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("article", article); //${article.ref}, ${article.re_step} ~
		
		
		int g_cnumber=0, g_cref=1, g_cre_step=0, g_cre_level=0;
		GCommentDAO cd=new GCommentDAO();
		
		if (request.getParameter("g_cnumber")!=null) {
			g_cnumber=Integer.parseInt(request.getParameter("g_cnumber"));
			g_cref=Integer.parseInt(request.getParameter("g_cref"));
			g_cre_step=Integer.parseInt(request.getParameter("g_cre_step"));
			g_cre_level=Integer.parseInt(request.getParameter("g_cre_level"));
			System.out.println("G_Content.do에서 넘어온 매개변수 확인");
			System.out.println("g_cnumber=>"+g_cnumber+", g_cref=>"+g_cref+", g_cre_step=>"+g_cre_step+", g_cre_level=>"+g_cre_level);
		}else {
		
		System.out.println("g_cnumber가 null일 때-->> g_cnumber=>"+g_cnumber+", g_cref=>"+g_cref+", g_cre_step=>"+g_cre_step+", g_cre_level=>"+g_cre_level);
		}
		
		request.setAttribute("g_cnumber", g_cnumber);
		request.setAttribute("g_cref", g_cref);
		request.setAttribute("g_cre_step", g_cre_step);
		request.setAttribute("g_cre_level", g_cre_level);
		
		
		//댓글작업
		List<GCommentDTO> commentList=cd.getComment(g_number);
		request.setAttribute("commentList", commentList);
		
		//댓글 수
		int cocount=0; //댓글 수
		GCommentDAO com=new GCommentDAO();
		cocount=com.getCommentCount(g_number);
		System.out.println("댓글 수=>"+cocount);
		request.setAttribute("cocount", cocount);
		
		
		
		
		return "/board/ghibli/G_Content.jsp";
	}

}