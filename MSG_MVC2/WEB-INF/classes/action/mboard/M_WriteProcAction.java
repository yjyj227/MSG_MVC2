package action.mboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.MBoardDAO;
import beans.MBoardDTO;
import java.sql.Timestamp;

import action.CommandAction;

public class M_WriteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		System.out.println("M_WriteProc.do의 mem_id=>"+mem_id);
		
		//String h_ref=request.getParameter("h_ref");
		
		MBoardDTO article=new MBoardDTO();
		
		article.setM_number(Integer.parseInt(request.getParameter("m_number")));
		article.setMem_id(request.getParameter("mem_id"));
		article.setM_nickname(request.getParameter("m_nickname"));
		article.setM_title(request.getParameter("m_title"));
		article.setM_body(request.getParameter("m_body"));
		
		article.setM_date(new Timestamp(System.currentTimeMillis()));
		
		article.setM_category(Integer.parseInt(request.getParameter("m_category")));
		article.setM_ref(Integer.parseInt(request.getParameter("m_ref")));
		//article.setFilesize(Integer.parseInt(request.getParameter("filesize")));
		
		MBoardDAO dbPro=new MBoardDAO();
		dbPro.insertArticle(article);
		
		int m_number=Integer.parseInt(request.getParameter("m_number"));
		
		return "/board/marvel/M_WriteProc.jsp";
	}

}
