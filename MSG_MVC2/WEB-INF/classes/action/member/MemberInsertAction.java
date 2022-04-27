package action.member;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.*;

import action.CommandAction;

public class MemberInsertAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		MemberDTO regmem=new MemberDTO();
		System.out.println("regmem=>"+regmem);
		
		regmem.setMem_id(request.getParameter("mem_id"));
		regmem.setMem_passwd(request.getParameter("mem_passwd"));
		regmem.setMem_name(request.getParameter("mem_name"));
		regmem.setMem_nickname(request.getParameter("mem_nickname"));
		regmem.setMem_birth(request.getParameter("mem_birth"));
		regmem.setMem_tel(request.getParameter("mem_tel"));
		regmem.setMem_email(request.getParameter("mem_email"));
		regmem.setMem_addr(request.getParameter("mem_addr"));
		regmem.setMem_genre(request.getParameter("mem_genre"));
		regmem.setMem_point(0);
		regmem.setMem_grade(1);
		
		MemberDAO memMgr=new MemberDAO();
		boolean check=memMgr.memberInsert(regmem);
		System.out.println("MemberInsertAction의 check=>"+check);
		
		request.setAttribute("check", check);
		
		return "/member/MemberInsert.jsp";
	}

}
