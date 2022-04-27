package action.zboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.ZBoardDAO;
import beans.ZBoardDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class Z_WriteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		System.out.println("Z_WriteProc.do의 mem_id=>"+mem_id);
		
		//String h_ref=request.getParameter("h_ref");
		
		ZBoardDTO article=new ZBoardDTO();
		
		article.setZ_number(Integer.parseInt(request.getParameter("z_number")));
		article.setMem_id(request.getParameter("mem_id"));
		article.setZ_nickname(request.getParameter("z_nickname"));
		article.setZ_title(request.getParameter("z_title"));
		article.setZ_body(request.getParameter("z_body"));
		
		article.setZ_date(new Timestamp(System.currentTimeMillis()));
		
		article.setZ_category(Integer.parseInt(request.getParameter("z_category")));
		article.setZ_ref(Integer.parseInt(request.getParameter("z_ref")));
		//article.setFilesize(Integer.parseInt(request.getParameter("filesize")));
		
		ZBoardDAO dbPro=new ZBoardDAO();
		dbPro.insertArticle(article);
		
		int z_number=Integer.parseInt(request.getParameter("z_number"));
		
		return "/board/zzang/Z_WriteProc.jsp";
	}

}
