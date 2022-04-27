package action.member;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.*;

import action.CommandAction;

public class MyPageAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		System.out.println("MyPageAction의 mem_id=>"+mem_id); //null이라면? ...
		
		MemberDAO memMgr=new MemberDAO();
		memMgr.syncPG(mem_id);
		
		//만약 등급이 바뀐다면 반영될 수 있도록
		MemberDTO npg=memMgr.getNPG(mem_id);
		System.out.println("등급을 다시 얻기 위한 객체(npg)=>"+npg);
    	int mem_grade=npg.getMem_grade();
    	System.out.println("mem_grade=>"+mem_grade);
		request.setAttribute("mem_grade", mem_grade);
		
		return "/member/MyPage.jsp";
	}

}
