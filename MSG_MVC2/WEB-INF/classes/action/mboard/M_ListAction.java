package action.mboard;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import beans.MBoardDAO;
import beans.MBoardDTO;
import beans.NoticeDAO;
import beans.NoticeDTO;
//import beans.HCommentDAO;
import action.CommandAction;

public class M_ListAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		String pageNum=request.getParameter("pageNum");
		//추가(검색분야, 검색어)
		String search=request.getParameter("search"); //검색분야
		String searchtext=request.getParameter("searchtext"); //검색
		
		String m_ref=request.getParameter("m_ref");
		
		System.out.println("G_ListAction에서의 매개변수 확인");
		System.out.println("pageNum=>"+pageNum+", search=>"+search+", searchtext=>"+searchtext+", m_ref=>"+m_ref);
		
		int count=0; //총 레코드수
		List<MBoardDTO> articleList=null; //화면에 출력할 레코드를 저장할 변수
		
		MBoardDAO dbPro=new MBoardDAO();
		if (m_ref==null) {
			count=dbPro.getArticleSearchCount(search, searchtext);
		}else if (Integer.parseInt(m_ref)==1 || Integer.parseInt(m_ref)==2) {
			count=dbPro.getArticleSearchCount(search, searchtext, m_ref);
		}else if (Integer.parseInt(m_ref)==4)  {
			count=dbPro.getHotArticleSearchCount(search, searchtext);
		}
		System.out.println("현재 레코드수(count)=>"+count);
		//1. 화면에 출력할 페이지번호   2. 출력할 레코드 개수
		Hashtable<String, Integer> pgList=dbPro.pageList(pageNum, count);
		if (count > 0) {
			System.out.println(pgList.get("startRow")+", "+pgList.get("endRow"));
			if (m_ref==null){
				articleList=dbPro.getBoardArticles(pgList.get("startRow"), pgList.get("endRow"), search, searchtext);
			}else if (Integer.parseInt(m_ref)==1 || Integer.parseInt(m_ref)==2) {
				articleList=dbPro.getBoardArticles(pgList.get("startRow"), pgList.get("endRow"), search, searchtext, m_ref);
			}else if (Integer.parseInt(m_ref)==4) {
				articleList=dbPro.getHotBoardArticles(pgList.get("startRow"), pgList.get("endRow"), search, searchtext);
			}
			System.out.println("M_ListAction의 articleList=>"+articleList);
		}else {
			articleList=Collections.EMPTY_LIST; //비어있는 List객체
		}
		
		//2. 처리한 결과를 공유(서버 메모리에 저장)=>이동할 페이지에 공유해서 사용(request)
		request.setAttribute("search", search); //${search}
		request.setAttribute("searchtext", searchtext); //
		request.setAttribute("pgList", pgList); //10개의 페이징 처리 정보
		request.setAttribute("articleList", articleList); //출력할 레코드값들 ${articleList}
		request.setAttribute("m_ref", m_ref);
		
		//공지사항-------------------------------------------------------
		int count2=0; //총 레코드수
		List<NoticeDTO> noticeList=null; //화면에 출력할 레코드를 저장할 변수
				
		NoticeDAO dbPro2=new NoticeDAO();
		count2=dbPro2.getArticleSearchCount(search, searchtext);
		System.out.println("현재 레코드 수(count2)=>"+count2);
		//1. 화면에 출력할 페이지번호   2. 출력할 레코드 개수
		Hashtable<String, Integer> pgList2=dbPro2.pageList(pageNum, count2);
		if (count2 > 0) {
			System.out.println(pgList.get("startRow")+", "+pgList2.get("endRow"));
			noticeList=dbPro2.getBoardArticles(pgList2.get("startRow"), pgList2.get("endRow"), search, searchtext);
			System.out.println("N_ListAction의 noticeList=>"+noticeList);
		}else {
			noticeList=Collections.EMPTY_LIST; //비어있는 List객체
		}
				
		request.setAttribute("pgList2", pgList2); //10개의 페이징 처리 정보
		request.setAttribute("noticeList", noticeList); //출력할 레코드값들 ${articleList}
		
		
		
		return "/board/marvel/M_List.jsp";
	}

}
