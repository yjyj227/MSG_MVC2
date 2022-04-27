package action.admin;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.*;

import action.CommandAction;

public class MemberModifyProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		MemberDTO memUP=new MemberDTO();
		System.out.println("regmem=>"+memUP);
		
		String mem_id=request.getParameter("mem_id");
		
		
		memUP.setMem_passwd(request.getParameter("mem_passwd"));
		memUP.setMem_name(request.getParameter("mem_name"));
		memUP.setMem_nickname(request.getParameter("member_nickname"));
		memUP.setMem_birth(request.getParameter("mem_birth"));
		memUP.setMem_tel(request.getParameter("mem_tel"));
		memUP.setMem_email(request.getParameter("mem_email"));
		memUP.setMem_addr(request.getParameter("mem_addr"));
		memUP.setMem_genre(request.getParameter("mem_genre"));
		memUP.setMem_point(Integer.parseInt(request.getParameter("mem_point")));
		memUP.setMem_grade(Integer.parseInt(request.getParameter("mem_grade")));
		memUP.setMem_id(request.getParameter("member_id"));
		
		MemberDAO memMgr=new MemberDAO();
		boolean updatecheck=memMgr.memberUpdate(memUP);
		System.out.println("MemberModifyProcAction의 updatecheck=>"+updatecheck);
		
		request.setAttribute("updatecheck", updatecheck);
		request.setAttribute("mem_id", mem_id);
		
		return "/admin/MemberModifyProc.jsp";
	}

}
