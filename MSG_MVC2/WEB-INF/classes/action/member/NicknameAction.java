package action.member;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.MemberDAO;

import action.CommandAction;

public class NicknameAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		response.setHeader("Cache-Control", "no-cache"); //요청시 메모리에 저장X
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0); //보관기간 0->보관하지 마라
		
		String mem_nickname=request.getParameter("mem_nickname");
		System.out.println("NicknameCheck.do의 mem_nickname=>"+mem_nickname);
		MemberDAO memMgr=new MemberDAO();
		boolean check=memMgr.checkNickname(mem_nickname);
		System.out.println("NicknameCheck.do의 check=>"+check);
		
		request.setAttribute("check2", new Boolean(check));
		
		return "/member/NicknameCheck.jsp";
	}

}
