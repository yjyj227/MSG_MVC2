package action.admin;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.*;

import action.CommandAction;

public class MemberModifyAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		response.setHeader("Cache-Control", "no-cache"); //요청시 메모리에 저장X
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0); //보관기간 0->보관하지 마라
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		System.out.println("MemberModifyAction의 mem_id=>"+mem_id); //null이라면? ...
		String member_id=request.getParameter("member_id");
		System.out.println("MemberModifyAction의 member_id=>"+member_id);
		
		MemberDAO memMgr=new MemberDAO();
		MemberDTO mem=memMgr.getMember(member_id);
		System.out.println("MemberModifyAction의 객체(mem)=>"+mem);
		
		memMgr.syncPG(member_id);
		
		request.setAttribute("mem_id", mem_id); //id값을 서버에 저장하기 위해 session처리를 한 것
		request.setAttribute("member_id", member_id);
		request.setAttribute("mem", mem);
		
		return "/admin/MemberModify.jsp";
	}

}
