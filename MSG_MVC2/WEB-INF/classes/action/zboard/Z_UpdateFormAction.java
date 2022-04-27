package action.zboard;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.ZBoardDAO;
import beans.ZBoardDTO;

import action.CommandAction;

public class Z_UpdateFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		int z_number=Integer.parseInt(request.getParameter("z_number"));
		String pageNum=request.getParameter("pageNum");
		System.out.println("Z_UpdateFormAction에서의 pageNum=>"+pageNum+", z_number=>"+z_number);
		
		String z_ref=request.getParameter("z_ref");
		
		ZBoardDAO dbPro=new ZBoardDAO();
		ZBoardDTO article=dbPro.updateGetArticle(z_number);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("article", article); //${article.num}
		request.setAttribute("z_ref", z_ref);
		
		return "/board/zzang/Z_UpdateForm.jsp";
	}

}
