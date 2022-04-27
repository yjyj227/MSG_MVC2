package action.rboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.RBoardDAO;
import beans.RBoardDTO;

import action.CommandAction;

public class R_DeleteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		String pageNum=request.getParameter("pageNum"); //hidden
		
		int r_number=Integer.parseInt(request.getParameter("r_number"));
		String r_title=request.getParameter("r_title");
		System.out.println("R_DeleteProcAction의 r_number=>"+r_number+", r_title=>"+r_title+", pageNum=>"+pageNum);
		
		RBoardDAO dbPro=new RBoardDAO();
		int check=dbPro.deleteArticle(r_number, r_title);
		
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("check", check); //${check} 데이터 수정성공유무
		
		return "/board/ring/R_DeleteProc.jsp";
	}

}
