package action.member;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.*;

import action.CommandAction;

public class MemberUpdateProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		MemberDTO memUP=new MemberDTO();
		System.out.println("regmem=>"+memUP);
		
		String mem_id=request.getParameter("mem_id");
		
		
		memUP.setMem_passwd(request.getParameter("mem_passwd"));
		memUP.setMem_name(request.getParameter("mem_name"));
		memUP.setMem_nickname(request.getParameter("mem_nickname"));
		memUP.setMem_birth(request.getParameter("mem_birth"));
		memUP.setMem_tel(request.getParameter("mem_tel"));
		memUP.setMem_email(request.getParameter("mem_email"));
		memUP.setMem_addr(request.getParameter("mem_addr"));
		memUP.setMem_genre(request.getParameter("mem_genre"));
		memUP.setMem_id(request.getParameter("mem_id"));
		
		MemberDAO memMgr=new MemberDAO();
		boolean updatecheck=memMgr.memberUpdate(memUP);
		System.out.println("MemberUpdateProcAction의 updatecheck=>"+updatecheck);
		
		request.setAttribute("updatecheck", new Boolean(updatecheck));
		request.setAttribute("mem_id", new String(mem_id));
		
		return "/member/MemberUpdateProc.jsp";
	}

}
