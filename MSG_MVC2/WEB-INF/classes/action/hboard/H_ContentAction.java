package action.hboard;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.jsp.*;


import beans.HBoardDAO;
import beans.HBoardDTO;
import beans.HCommentDAO;
import beans.HCommentDTO;

import action.CommandAction;

public class H_ContentAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int h_number=Integer.parseInt(request.getParameter("h_number"));
		String pageNum=request.getParameter("pageNum");
		System.out.println("H_ContentAction의 pageNum=>"+pageNum+", h_number=>"+h_number);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		HBoardDAO dbPro=new HBoardDAO();
		HBoardDTO article=dbPro.getArticle(h_number);
		
		//2. 실행결과를 서버의 메모리에 저장->request에 저장->jsp ${키명}으로 불러올 수 있도록 공유
		request.setAttribute("h_number", h_number); //${num}
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("article", article); //${article.ref}, ${article.re_step} ~
		
		
		int h_cnumber=0, h_cref=1, h_cre_step=0, h_cre_level=0;
		HCommentDAO cd=new HCommentDAO();
		
		if (request.getParameter("h_cnumber")!=null) {
			h_cnumber=Integer.parseInt(request.getParameter("h_cnumber"));
			h_cref=Integer.parseInt(request.getParameter("h_cref"));
			h_cre_step=Integer.parseInt(request.getParameter("h_cre_step"));
			h_cre_level=Integer.parseInt(request.getParameter("h_cre_level"));
			System.out.println("H_Content.do에서 넘어온 매개변수 확인");
			System.out.println("h_cnumber=>"+h_cnumber+", h_cref=>"+h_cref+", h_cre_step=>"+h_cre_step+", h_cre_level=>"+h_cre_level);
		}else {
		
		System.out.println("h_cnumber가 null일 때-->> h_cnumber=>"+h_cnumber+", h_cref=>"+h_cref+", h_cre_step=>"+h_cre_step+", h_cre_level=>"+h_cre_level);
		}
		
		request.setAttribute("h_cnumber", h_cnumber);
		request.setAttribute("h_cref", h_cref);
		request.setAttribute("h_cre_step", h_cre_step);
		request.setAttribute("h_cre_level", h_cre_level);
		
		
		//댓글작업
		List<HCommentDTO> commentList=cd.getComment(h_number);
		request.setAttribute("commentList", commentList);
		
		//댓글 수
		int cocount=0; //댓글 수
		HCommentDAO com=new HCommentDAO();
		cocount=com.getCommentCount(h_number);
		System.out.println("댓글 수=>"+cocount);
		request.setAttribute("cocount", cocount);
		
		
		
		
		return "/board/harry/H_Content.jsp";
	}

}