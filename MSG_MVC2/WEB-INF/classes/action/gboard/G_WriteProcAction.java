package action.gboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.GBoardDAO;
import beans.GBoardDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class G_WriteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		System.out.println("G_WriteProc.do의 mem_id=>"+mem_id);
		
		//String h_ref=request.getParameter("h_ref");
		
		GBoardDTO article=new GBoardDTO();
		
		article.setG_number(Integer.parseInt(request.getParameter("g_number")));
		article.setMem_id(request.getParameter("mem_id"));
		article.setG_nickname(request.getParameter("g_nickname"));
		article.setG_title(request.getParameter("g_title"));
		article.setG_body(request.getParameter("g_body"));
		
		article.setG_date(new Timestamp(System.currentTimeMillis()));
		
		article.setG_category(Integer.parseInt(request.getParameter("g_category")));
		article.setG_ref(Integer.parseInt(request.getParameter("g_ref")));
		//article.setFilesize(Integer.parseInt(request.getParameter("filesize")));
		
		GBoardDAO dbPro=new GBoardDAO();
		dbPro.insertArticle(article);
		
		int g_number=Integer.parseInt(request.getParameter("g_number"));
		
		return "/board/ghibli/G_WriteProc.jsp";
	}

}
