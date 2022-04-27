package action.admin;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.CommandAction;
import beans.MemberDAO;
import beans.MemberDTO;

public class MemberListAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey");
		System.out.println("MemberListAction의 mem_id=>"+mem_id);
		
		String pageNum=request.getParameter("pageNum");
		String search=request.getParameter("search"); //검색분야
		String searchtext=request.getParameter("searchtext"); //검색
		
		System.out.println("MemberListAction에서의 매개변수 확인");
		System.out.println("pageNum=>"+pageNum+", search=>"+search+", searchtext=>"+searchtext);
		
		int count=0;
		List<MemberDTO> memberList=null;
		
		MemberDAO mem=new MemberDAO();
		count=mem.getMemberCount(search, searchtext);
		System.out.println("현재 회원 수=>"+count);
		
		Hashtable<String, Integer> pgList=mem.pageList(pageNum, count);
		if (count > 0) {
			System.out.println(pgList.get("startRow")+", "+pgList.get("endRow"));
			memberList=mem.getMembers(pgList.get("startRow"), pgList.get("endRow"), search, searchtext);
			System.out.println("MemberListAction의 memberList=>"+memberList);
		}else {
			memberList=Collections.EMPTY_LIST;
		}
		
		request.setAttribute("search", search); //${search}
		request.setAttribute("searchtext", searchtext); //
		request.setAttribute("pgList", pgList); //10개의 페이징 처리 정보
		request.setAttribute("memberList", memberList); //출력할 레코드값들 ${articleList}
		
		return "/admin/MemberList.jsp";
	}

}
