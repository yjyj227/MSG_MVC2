package action.hboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.HBoardDAO;
import beans.HBoardDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class H_WriteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		System.out.println("H_WriteProc.do의 mem_id=>"+mem_id);
		
		//String h_ref=request.getParameter("h_ref");
		
		HBoardDTO article=new HBoardDTO();
		
		article.setH_number(Integer.parseInt(request.getParameter("h_number")));
		article.setMem_id(request.getParameter("mem_id"));
		article.setH_nickname(request.getParameter("h_nickname"));
		article.setH_title(request.getParameter("h_title"));
		article.setH_body(request.getParameter("h_body"));
		
		article.setH_date(new Timestamp(System.currentTimeMillis()));
		
		article.setH_category(Integer.parseInt(request.getParameter("h_category")));
		article.setH_ref(Integer.parseInt(request.getParameter("h_ref")));
		//article.setFilesize(Integer.parseInt(request.getParameter("filesize")));
		
		HBoardDAO dbPro=new HBoardDAO();
		dbPro.insertArticle(article);
		
		int h_number=Integer.parseInt(request.getParameter("h_number"));
		
		return "/board/harry/H_WriteProc.jsp";
	}

}
