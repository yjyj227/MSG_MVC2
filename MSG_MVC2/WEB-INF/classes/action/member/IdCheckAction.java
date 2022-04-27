package action.member;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.MemberDAO;

import action.CommandAction;

public class IdCheckAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		response.setHeader("Cache-Control", "no-cache"); //요청시 메모리에 저장X
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0); //보관기간 0->보관하지 마라
		
		String mem_id=request.getParameter("mem_id");
		System.out.println("IdCheck.do의 mem_id=>"+mem_id);
		MemberDAO memMgr=new MemberDAO();
		boolean check=memMgr.checkId(mem_id);
		System.out.println("IdCheck.do의 check=>"+check);
		
		request.setAttribute("check1", new Boolean(check));
		
		return "/member/IdCheck.jsp";
	}

}
