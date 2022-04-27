package action.rboard;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.jsp.*;


import beans.RBoardDAO;
import beans.RBoardDTO;
import beans.RCommentDAO;
import beans.RCommentDTO;

import action.CommandAction;

public class R_ContentAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int r_number=Integer.parseInt(request.getParameter("r_number"));
		String pageNum=request.getParameter("pageNum");
		System.out.println("R_ContentAction의 pageNum=>"+pageNum+", r_number=>"+r_number);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		RBoardDAO dbPro=new RBoardDAO();
		RBoardDTO article=dbPro.getArticle(r_number);
		
		//String content=article.getContent().replace("\r\n", "<br>");
		//content=content.replace("\r\n", "<br>");
		
		
		//2. 실행결과를 서버의 메모리에 저장->request에 저장->jsp ${키명}으로 불러올 수 있도록 공유
		request.setAttribute("r_number", r_number); //${num}
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("article", article); //${article.ref}, ${article.re_step} ~
		
		
		int r_cnumber=0, r_cref=1, r_cre_step=0, r_cre_level=0;
		RCommentDAO cd=new RCommentDAO();
		
		if (request.getParameter("r_cnumber")!=null) {
			r_cnumber=Integer.parseInt(request.getParameter("r_cnumber"));
			r_cref=Integer.parseInt(request.getParameter("r_cref"));
			r_cre_step=Integer.parseInt(request.getParameter("r_cre_step"));
			r_cre_level=Integer.parseInt(request.getParameter("r_cre_level"));
			System.out.println("R_Content.do에서 넘어온 매개변수 확인");
			System.out.println("r_cnumber=>"+r_cnumber+", r_cref=>"+r_cref+", r_cre_step=>"+r_cre_step+", r_cre_level=>"+r_cre_level);
		}else {
		
		System.out.println("r_cnumber가 null일 때-->> r_cnumber=>"+r_cnumber+", r_cref=>"+r_cref+", r_cre_step=>"+r_cre_step+", r_cre_level=>"+r_cre_level);
		}
		
		request.setAttribute("r_cnumber", r_cnumber);
		request.setAttribute("r_cref", r_cref);
		request.setAttribute("r_cre_step", r_cre_step);
		request.setAttribute("r_cre_level", r_cre_level);
		
		
		//댓글작업
		List<RCommentDTO> commentList=cd.getComment(r_number);
		request.setAttribute("commentList", commentList);
		
		//댓글 수
		int cocount=0; //댓글 수
		RCommentDAO com=new RCommentDAO();
		cocount=com.getCommentCount(r_number);
		System.out.println("댓글 수=>"+cocount);
		request.setAttribute("cocount", cocount);
		
		
		
		
		return "/board/ring/R_Content.jsp";
	}

}