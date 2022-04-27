package action.member;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.BoardDAO;
import beans.BoardDTO;

import action.CommandAction;

public class Scrap_ListAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		System.out.println("Scrap_ListAction의 mem_id=>"+mem_id);
		
		String pageNum=request.getParameter("pageNum");
		
		
		System.out.println("Scrap_ListAction에서의 매개변수 확인");
		System.out.println("pageNum=>"+pageNum);
		
		int count=0; //총 레코드수
		List<BoardDTO> articleList=null; //화면에 출력할 레코드를 저장할 변수
		
		BoardDAO dbPro=new BoardDAO();
		count=dbPro.getScrapArticleCount(mem_id);
		System.out.println("현재 레코드수(count)=>"+count);
		//1. 화면에 출력할 페이지번호   2. 출력할 레코드 개수
		Hashtable<String, Integer> pgList=dbPro.pageList(pageNum, count);
		if (count > 0) {
			System.out.println(pgList.get("startRow")+", "+pgList.get("endRow"));
			articleList=dbPro.getScrapArticles(mem_id, pgList.get("startRow"), pgList.get("endRow"));
			System.out.println("Scrap_ListAction의 articleList=>"+articleList);
		}else {
			articleList=Collections.EMPTY_LIST; //비어있는 List객체
		}
		
		//2. 처리한 결과를 공유(서버 메모리에 저장)=>이동할 페이지에 공유해서 사용(request)
		request.setAttribute("pgList", pgList); //10개의 페이징 처리 정보
		request.setAttribute("articleList", articleList); //출력할 레코드값들 ${articleList}
		request.setAttribute("pageNum", pageNum);
		
		return "/member/Scrap_List.jsp";
	}

}
