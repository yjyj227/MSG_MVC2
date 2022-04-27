package action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.CommandAction;

public class MemberDropAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String member_id=request.getParameter("member_id");
		System.out.println("MemberDropAction의 member_id=>"+member_id);
		request.setAttribute("member_id", member_id);
		return "/admin/MemberDrop.jsp";
	}

}
