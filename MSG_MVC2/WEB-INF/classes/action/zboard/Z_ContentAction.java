package action.zboard;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.jsp.*;


import beans.ZBoardDAO;
import beans.ZBoardDTO;
import beans.ZCommentDAO;
import beans.ZCommentDTO;

import action.CommandAction;

public class Z_ContentAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int z_number=Integer.parseInt(request.getParameter("z_number"));
		String pageNum=request.getParameter("pageNum");
		System.out.println("Z_ContentAction의 pageNum=>"+pageNum+", z_number=>"+z_number);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		ZBoardDAO dbPro=new ZBoardDAO();
		ZBoardDTO article=dbPro.getArticle(z_number);
		
		//String content=article.getContent().replace("\r\n", "<br>");
		//content=content.replace("\r\n", "<br>");
		
		
		//2. 실행결과를 서버의 메모리에 저장->request에 저장->jsp ${키명}으로 불러올 수 있도록 공유
		request.setAttribute("z_number", z_number); //${num}
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("article", article); //${article.ref}, ${article.re_step} ~
		
		
		int z_cnumber=0, z_cref=1, z_cre_step=0, z_cre_level=0;
		ZCommentDAO cd=new ZCommentDAO();
		
		if (request.getParameter("z_cnumber")!=null) {
			z_cnumber=Integer.parseInt(request.getParameter("z_cnumber"));
			z_cref=Integer.parseInt(request.getParameter("z_cref"));
			z_cre_step=Integer.parseInt(request.getParameter("z_cre_step"));
			z_cre_level=Integer.parseInt(request.getParameter("z_cre_level"));
			System.out.println("Z_Content.do에서 넘어온 매개변수 확인");
			System.out.println("z_cnumber=>"+z_cnumber+", z_cref=>"+z_cref+", z_cre_step=>"+z_cre_step+", z_cre_level=>"+z_cre_level);
		}else {
		
		System.out.println("z_cnumber가 null일 때-->> z_cnumber=>"+z_cnumber+", z_cref=>"+z_cref+", z_cre_step=>"+z_cre_step+", z_cre_level=>"+z_cre_level);
		}
		
		request.setAttribute("z_cnumber", z_cnumber);
		request.setAttribute("z_cref", z_cref);
		request.setAttribute("z_cre_step", z_cre_step);
		request.setAttribute("z_cre_level", z_cre_level);
		
		
		//댓글작업
		List<ZCommentDTO> commentList=cd.getComment(z_number);
		request.setAttribute("commentList", commentList);
		
		//댓글 수
		int cocount=0; //댓글 수
		ZCommentDAO com=new ZCommentDAO();
		cocount=com.getCommentCount(z_number);
		System.out.println("댓글 수=>"+cocount);
		request.setAttribute("cocount", cocount);
		
		
		
		
		return "/board/zzang/Z_Content.jsp";
	}

}