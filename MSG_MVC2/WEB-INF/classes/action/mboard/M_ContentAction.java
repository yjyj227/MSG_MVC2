package action.mboard;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.jsp.*;


import beans.MBoardDAO;
import beans.MBoardDTO;
import beans.MCommentDAO;
import beans.MCommentDTO;

import action.CommandAction;

public class M_ContentAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int m_number=Integer.parseInt(request.getParameter("m_number"));
		String pageNum=request.getParameter("pageNum");
		System.out.println("M_ContentAction의 pageNum=>"+pageNum+", m_number=>"+m_number);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		MBoardDAO dbPro=new MBoardDAO();
		MBoardDTO article=dbPro.getArticle(m_number);
		
		//String content=article.getContent().replace("\r\n", "<br>");
		//content=content.replace("\r\n", "<br>");
		
		
		//2. 실행결과를 서버의 메모리에 저장->request에 저장->jsp ${키명}으로 불러올 수 있도록 공유
		request.setAttribute("m_number", m_number); //${num}
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("article", article); //${article.ref}, ${article.re_step} ~
		
		
		int m_cnumber=0, m_cref=1, m_cre_step=0, m_cre_level=0;
		MCommentDAO cd=new MCommentDAO();
		
		if (request.getParameter("m_cnumber")!=null) {
			m_cnumber=Integer.parseInt(request.getParameter("m_cnumber"));
			m_cref=Integer.parseInt(request.getParameter("m_cref"));
			m_cre_step=Integer.parseInt(request.getParameter("m_cre_step"));
			m_cre_level=Integer.parseInt(request.getParameter("m_cre_level"));
			System.out.println("M_Content.do에서 넘어온 매개변수 확인");
			System.out.println("m_cnumber=>"+m_cnumber+", m_cref=>"+m_cref+", m_cre_step=>"+m_cre_step+", m_cre_level=>"+m_cre_level);
		}else {
		
		System.out.println("m_cnumber가 null일 때-->> m_cnumber=>"+m_cnumber+", m_cref=>"+m_cref+", m_cre_step=>"+m_cre_step+", m_cre_level=>"+m_cre_level);
		}
		
		request.setAttribute("m_cnumber", m_cnumber);
		request.setAttribute("m_cref", m_cref);
		request.setAttribute("m_cre_step", m_cre_step);
		request.setAttribute("m_cre_level", m_cre_level);
		
		
		//댓글작업
		List<MCommentDTO> commentList=cd.getComment(m_number);
		request.setAttribute("commentList", commentList);
		
		//댓글 수
		int cocount=0; //댓글 수
		MCommentDAO com=new MCommentDAO();
		cocount=com.getCommentCount(m_number);
		System.out.println("댓글 수=>"+cocount);
		request.setAttribute("cocount", cocount);
		
		
		
		
		return "/board/marvel/M_Content.jsp";
	}

}