package action.rboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.RBoardDAO;
import beans.RBoardDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class R_WriteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		System.out.println("R_WriteProc.do의 mem_id=>"+mem_id);
		
		//String h_ref=request.getParameter("h_ref");
		
		RBoardDTO article=new RBoardDTO();
		
		article.setR_number(Integer.parseInt(request.getParameter("r_number")));
		article.setMem_id(request.getParameter("mem_id"));
		article.setR_nickname(request.getParameter("r_nickname"));
		article.setR_title(request.getParameter("r_title"));
		article.setR_body(request.getParameter("r_body"));
		
		article.setR_date(new Timestamp(System.currentTimeMillis()));
		
		article.setR_category(Integer.parseInt(request.getParameter("r_category")));
		article.setR_ref(Integer.parseInt(request.getParameter("r_ref")));
		//article.setFilesize(Integer.parseInt(request.getParameter("filesize")));
		
		RBoardDAO dbPro=new RBoardDAO();
		dbPro.insertArticle(article);
		
		int r_number=Integer.parseInt(request.getParameter("r_number"));
		
		return "/board/ring/R_WriteProc.jsp";
	}

}
