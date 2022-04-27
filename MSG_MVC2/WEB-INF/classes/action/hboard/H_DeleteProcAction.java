package action.hboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.HBoardDAO;
import beans.HBoardDTO;

import action.CommandAction;

public class H_DeleteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String
		
		String pageNum=request.getParameter("pageNum"); //hidden
		
		int h_number=Integer.parseInt(request.getParameter("h_number"));
		String h_title=request.getParameter("h_title");
		String h_mem_id=request.getParameter("h_mem_id");
		System.out.println("H_DeleteProcAction의 h_number=>"+h_number+", h_title=>"+h_title+", pageNum=>"+pageNum);
		
		HBoardDAO dbPro=new HBoardDAO();
		int check=dbPro.deleteArticle(h_number, h_title, h_mem_id);
		
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("check", check); //${check} 데이터 수정성공유무
		
		return "/board/harry/H_DeleteProc.jsp";
	}

}
