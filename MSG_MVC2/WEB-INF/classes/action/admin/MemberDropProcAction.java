package action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.CommandAction;
import beans.MemberDAO;

public class MemberDropProcAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		String member_id=request.getParameter("member_id");
		System.out.println("MemberDropAction의 member_id=>"+member_id);
		
		MemberDAO memMgr=new MemberDAO();
		
		//글삭제 or 글 아이디 수정
		
		
		int delcheck=memMgr.memberDrop(member_id); //회원탈퇴 메서드 호출
		System.out.println("MemberDropProcAction의 회원삭제성공유무(delcheck)=>"+delcheck); //1탈퇴 0실패
		request.setAttribute("member_id", member_id);
		request.setAttribute("delcheck", delcheck);
		
		return "/admin/MemberDropProc.jsp";
	}

}
