package action.zboard;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import beans.BoardDAO;
import beans.BoardDTO;

import action.CommandAction;

public class Z_RateProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		System.out.println("H_RateProcAction의 mem_id=>"+mem_id);
		
		BoardDTO article=new BoardDTO();
		
		article.setV_number(Integer.parseInt(request.getParameter("v_number")));
		article.setMem_id(request.getParameter("mem_id"));
		article.setV_movie(Integer.parseInt(request.getParameter("v_movie")));
		if (request.getParameter("v_star")!=null) {
			article.setV_star(Double.parseDouble(request.getParameter("v_star")));
		}
		if (request.getParameter("v_like")!=null) {
			article.setV_like(Integer.parseInt(request.getParameter("v_like")));
		}
		
		BoardDAO dbPro=new BoardDAO();
		dbPro.rate(article);
		
		int v_number=Integer.parseInt(request.getParameter("v_number"));
		
		return "/board/zzang/Z_RateProc.jsp";
	}

}
