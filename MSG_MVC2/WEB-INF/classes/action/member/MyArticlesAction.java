package action.member;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import beans.HBoardDAO;
import beans.HBoardDTO;
import beans.BoardDAO;
import beans.BoardDTO;
import beans.HCommentDAO;
import action.CommandAction;

import action.CommandAction;

public class MyArticlesAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		System.out.println("MyArticlesAction의 mem_id=>"+mem_id);
		
		String pageNum=request.getParameter("pageNum");
		//추가(검색분야, 검색어)
		//String search=request.getParameter("search"); //검색분야
		//String searchtext=request.getParameter("searchtext"); //검색
		String genre=request.getParameter("genre");
		
		//String h_ref=request.getParameter("h_ref");
		
		System.out.println("MyArticlesAction에서의 매개변수 확인");
		System.out.println("pageNum=>"+pageNum+", genre=>"+genre);
		
		int count=0; //총 레코드수
		List<BoardDTO> articleList=null; //화면에 출력할 레코드를 저장할 변수
		
		BoardDAO dbPro=new BoardDAO();
		/*
		 * if (genre==null) { count=0; }else if (Integer.parseInt(genre)==10) {
		 * count=dbPro.getMyArticleCount("h", mem_id); }else if
		 * (Integer.parseInt(genre)==20) { count=dbPro.getMyArticleCount("z", mem_id);
		 * }else if (Integer.parseInt(genre)==30) { count=dbPro.getMyArticleCount("m",
		 * mem_id); }else if (Integer.parseInt(genre)==40) {
		 * count=dbPro.getMyArticleCount("g", mem_id); }else if
		 * (Integer.parseInt(genre)==50) { count=dbPro.getMyArticleCount("r", mem_id); }
		 */
		
		if (genre==null) {
			count=0;
		}else {
			count=dbPro.getMyArticleCount(genre, mem_id);
		}
		
		System.out.println("현재 레코드수(count)=>"+count);
		//1. 화면에 출력할 페이지번호   2. 출력할 레코드 개수
		Hashtable<String, Integer> pgList=dbPro.pageList(pageNum, count);
		/*
		 * if (count > 0) {
		 * System.out.println(pgList.get("startRow")+", "+pgList.get("endRow")); if
		 * (Integer.parseInt(genre)==10) {
		 * articleList=dbPro.getMyArticles(pgList.get("startRow"), pgList.get("endRow"),
		 * "h", mem_id); }else if (Integer.parseInt(genre)==20) {
		 * articleList=dbPro.getMyArticles(pgList.get("startRow"), pgList.get("endRow"),
		 * "z", mem_id); }else if (Integer.parseInt(genre)==30) {
		 * articleList=dbPro.getMyArticles(pgList.get("startRow"), pgList.get("endRow"),
		 * "m", mem_id); }else if (Integer.parseInt(genre)==40) {
		 * articleList=dbPro.getMyArticles(pgList.get("startRow"), pgList.get("endRow"),
		 * "g", mem_id); }else if (Integer.parseInt(genre)==50) {
		 * articleList=dbPro.getMyArticles(pgList.get("startRow"), pgList.get("endRow"),
		 * "r", mem_id); }
		 */
		
		if (count > 0) {
			System.out.println(pgList.get("startRow")+", "+pgList.get("endRow"));
			articleList=dbPro.getMyArticles(pgList.get("startRow"), pgList.get("endRow"), genre, mem_id);
			
			System.out.println("MyArticlesAction의 articleList=>"+articleList);
		}else {
			articleList=Collections.EMPTY_LIST; //비어있는 List객체
		}
		
		//2. 처리한 결과를 공유(서버 메모리에 저장)=>이동할 페이지에 공유해서 사용(request)
		//request.setAttribute("search", search); //${search}
		//request.setAttribute("searchtext", searchtext); //
		request.setAttribute("pgList", pgList); //10개의 페이징 처리 정보
		request.setAttribute("articleList", articleList); //출력할 레코드값들 ${articleList}
		//request.setAttribute("h_ref", h_ref);
		request.setAttribute("genre", genre);
		
		return "/member/MyArticles.jsp";
	}

}
