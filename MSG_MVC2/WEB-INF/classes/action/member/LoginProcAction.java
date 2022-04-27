package action.member;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.MemberDAO;
import beans.MemberDTO;

import action.CommandAction;

public class LoginProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		String mem_id=request.getParameter("mem_id");
	    String mem_passwd=request.getParameter("mem_passwd");
	    System.out.println("mem_id=>"+mem_id+", mem_passwd=>"+mem_passwd);
	    
	    MemberDAO memMgr=new MemberDAO();
	    boolean loginCheck=memMgr.loginCheck(mem_id, mem_passwd);
	    System.out.println("LoginProc.do의 loginCheck=>"+loginCheck);
		
	    request.setAttribute("loginCheck", loginCheck);
	    request.setAttribute("mem_id", mem_id);
    	request.setAttribute("mem_passwd", mem_passwd);
    	
    	if (loginCheck) {
	    	MemberDTO npg=memMgr.getNPG(mem_id);
			System.out.println("닉네임, 포인트, 등급을 얻기 위한 객체(npg)=>"+npg);
	    	String mem_nickname=npg.getMem_nickname();
	    	int mem_point=npg.getMem_point();
	    	int mem_grade=npg.getMem_grade();
	    	
	    	System.out.println("mem_nickname=>"+mem_nickname);
	    	System.out.println("mem_point=>"+mem_point);
	    	System.out.println("mem_grade=>"+mem_grade);
			
			request.setAttribute("mem_nickname", mem_nickname);
			request.setAttribute("mem_point", mem_point);
			request.setAttribute("mem_grade", mem_grade);
    	}
		return "/member/LoginProc.jsp";
	}

}
