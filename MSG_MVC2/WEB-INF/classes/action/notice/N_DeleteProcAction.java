package action.notice;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.NoticeDAO;
import beans.NoticeDTO;

import action.CommandAction;

public class N_DeleteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		String pageNum=request.getParameter("pageNum"); //hidden
		//추가
		String passwd=request.getParameter("passwd"); //입력O //이제 필요 없을듯
		int notice_number=Integer.parseInt(request.getParameter("notice_number"));
		System.out.println("N_DeleteProcAction의 notice_number=>"+notice_number+", passwd=>"+passwd+", pageNum=>"+pageNum);
		
		NoticeDAO dbPro=new NoticeDAO();
		int check=dbPro.deleteArticle(notice_number);
		
		request.setAttribute("pageNum", pageNum); //수정한 레코드가 있는 페이지로 이동
		request.setAttribute("check", check); //${check} 데이터 수정성공유무
		
		return "/board/notice/N_DeleteProc.jsp";
	}

}
