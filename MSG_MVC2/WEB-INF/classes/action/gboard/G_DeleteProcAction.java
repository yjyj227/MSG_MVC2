package action.gboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.GBoardDAO;
import beans.GBoardDTO;

import action.CommandAction;

public class G_DeleteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		String pageNum=request.getParameter("pageNum"); //hidden
		
		int g_number=Integer.parseInt(request.getParameter("g_number"));
		String g_title=request.getParameter("g_title");
		System.out.println("G_DeleteProcAction의 g_number=>"+g_number+", g_title=>"+g_title+", pageNum=>"+pageNum);
		
		GBoardDAO dbPro=new GBoardDAO();
		int check=dbPro.deleteArticle(g_number, g_title);
		
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("check", check); //${check} 데이터 수정성공유무
		
		return "/board/ghibli/G_DeleteProc.jsp";
	}

}
