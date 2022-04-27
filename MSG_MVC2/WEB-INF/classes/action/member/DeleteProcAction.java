package action.member;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.*;

import action.CommandAction;

public class DeleteProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		String mem_id=request.getParameter("mem_id"); //입력X
		String passwd=request.getParameter("passwd"); //입력O
		
		System.out.println("DeleteProcAction의 mem_id=>"+mem_id+", passwd=>"+passwd);
		//-------------------------------------------------------------------------
		MemberDAO memMgr=new MemberDAO();
		int delcheck=memMgr.memberDelete(mem_id, passwd); //회원탈퇴 메서드 호출
		System.out.println("DeleteProc.do의 회원탈퇴성공유무(delcheck)=>"+delcheck); //1탈퇴 0실패
		
		request.setAttribute("delcheck", delcheck);
		
		return "/member/DeleteProc.jsp";
	}

}
