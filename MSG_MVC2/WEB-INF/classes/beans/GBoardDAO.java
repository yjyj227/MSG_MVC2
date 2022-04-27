package beans;

import java.io.File;
import java.sql.*;
import java.util.*;
import beans.*;

public class GBoardDAO {
	
	private DBConnectionMgr pool=null; //1. 연결객체선언
	//공통
	private Connection con=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
	private String sql="";
	
	//2. 생성자 통해서 연결
	public GBoardDAO() {
		
		try {
			pool=DBConnectionMgr.getInstance();
			System.out.println("pool=>"+pool);
		}catch (Exception e) {
			System.out.println("DB접속 오류=>"+e);
		}
	}

	// 댓글 수 담기
	public int getCommentCountForList(int g_number) throws Exception {

		int x = 0;

		con = pool.getConnection();
		System.out.println("con=>" + con);
		sql = "select count(*) from g_comment where g_number=? and g_cbody!='삭제된 댓글입니다.&nbsp;'";
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, g_number);
		rs = pstmt.executeQuery();
		if (rs.next()) { // 보여주는 결과가 있다면
			x = rs.getInt(1); // 변수명=rs.get자료형(필드명 또는 인덱스번호)=>여기서는 필드명X(필드명이 없기 때문)
		}
		pool.freeConnection(con, pstmt, rs);
		return x;
	}

	//article~
	private GBoardDTO makeArticleFromResult() throws Exception {
		
		GBoardDTO article=new GBoardDTO();
		GBoardDAO gboard=new GBoardDAO();
		article.setG_number(rs.getInt("g_number"));
		article.setMem_id(rs.getString("mem_id"));
		article.setG_nickname(rs.getString("g_nickname"));
		article.setG_title(rs.getString("g_title"));
		article.setG_body(rs.getString("g_body"));
		article.setG_date(rs.getTimestamp("g_date"));
		article.setG_count(rs.getInt("g_count"));
		article.setG_scrap(rs.getInt("g_scrap"));
		article.setG_category(rs.getInt("g_category")); //글카테고리->11:해리포터1, 12:해리포터2, 13:해리포터3,,,
		article.setG_ref(rs.getInt("g_ref")); //게시판분류->1:자유, 2:정보, 3:별점, 4:hot
		article.setG_comments(gboard.getCommentCountForList(rs.getInt("g_number")));
		
		return article;
	}
	
	//메서드 작성~
	
	//총 레코드(게시물) 수
	public int getArticleCount() {
		
		int x=0;
		
		try {
			con=pool.getConnection();
			System.out.println("con=>"+con);
			sql="select count(*) from g_table";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if (rs.next()) {
				x=rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.println("getArticleCount() 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return x;
	}
	
	//검색어에 해당되는 총 레코드 수
	public int getArticleSearchCount(String search, String searchtext) {
		
		int x=0;
		
		try {
			con=pool.getConnection();
			System.out.println("con=>"+con);
			//--------------------------------------------------------------------------
			if (search==null || search=="") { //검색분야를 선택X
				sql="select count(*) from g_table";
			}else { //검색분야(제목, 작성자, 제목+본문)
				if (search.equals("g_title_g_body")) { //제목+본문
					sql="select count(*) from g_table where g_title like '%"+
						searchtext+"%' or g_body like '%"+searchtext+"%' ";
				}else { //제목, 작성자->매개변수를 이용해서 하나의 sql로 통합 가능
					sql="select count(*) from g_table where "+search+" like '%"+
						searchtext+"%' ";
				}
			}
			System.out.println("getArticleSearchCount 검색어=>"+sql);
			//--------------------------------------------------------------------------
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if (rs.next()) {
				x=rs.getInt(1);
				System.out.println("검색된 총 레코드 수=>"+x);
			}
		}catch (Exception e) {
			System.out.println("getArticleSearchCount() 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return x;
	}
	
	// 검색어에 해당되는 총 레코드 수 ref
	public int getArticleSearchCount(String search, String searchtext, String g_ref) {

		int x = 0;

		try {
			con = pool.getConnection();
			System.out.println("con=>" + con);
			// --------------------------------------------------------------------------
			if (search == null || search == "") { // 검색분야를 선택X
				sql = "select count(*) from g_table where g_ref=" + g_ref;
			} else { // 검색분야(제목, 작성자, 제목+본문)
				if (search.equals("g_title_g_body")) { // 제목+본문
					sql = "select count(*) from g_table where g_ref=" + g_ref + " and g_title like '%" + searchtext
							+ "%' or g_body like '%" + searchtext + "%' ";
				} else { // 제목, 작성자->매개변수를 이용해서 하나의 sql로 통합 가능
					sql = "select count(*) from g_table where g_ref=" + g_ref + " and " + search + " like '%"
							+ searchtext + "%' ";
				}
			}
			System.out.println("getArticleSearchCount()g_ref 검색어=>" + sql);
			// --------------------------------------------------------------------------
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
				System.out.println("검색된 총 레코드 수=>" + x);
			}
		} catch (Exception e) {
			System.out.println("getArticleSearchCount()g_ref 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return x;
	}
	
	// 검색어에 해당되는 총 레코드 수 HOT
	public int getHotArticleSearchCount(String search, String searchtext) {

		int x = 0;

		try {
			con = pool.getConnection();
			System.out.println("con=>" + con);
			// --------------------------------------------------------------------------
			if (search == null || search == "") { // 검색분야를 선택X
				sql = "select count(*) from g_table where g_count > 50";
			} else { // 검색분야(제목, 작성자, 제목+본문)
				if (search.equals("g_title_g_body")) { // 제목+본문
					sql = "select count(*) from g_table where g_count > 50 and (g_title like '%" + searchtext + "%' or g_body like '%"
							+ searchtext + "%') ";
				} else { // 제목, 작성자->매개변수를 이용해서 하나의 sql로 통합 가능
					sql = "select count(*) from g_table where g_count > 50 and " + search + " like '%" + searchtext + "%' ";
				}
			}
			System.out.println("getArticleSearchCount 검색어=>" + sql);
			// --------------------------------------------------------------------------
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
				System.out.println("검색된 총 레코드 수=>" + x);
			}
		} catch (Exception e) {
			System.out.println("getHotArticleSearchCount() 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return x;
	}
	
	
	
	
	
	
	//글목록조회
	public List<GBoardDTO> getArticles(int start, int end) {
		
		List<GBoardDTO> articleList=null;
		
		try {
			con=pool.getConnection();
			sql="select * from (select g_table.*, rownum as rn from (select * from g_table order by g_number desc) g_table) where rn between ? and (? - 1)";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, start+end);
			rs=pstmt.executeQuery();
			
			if (rs.next()) {
				articleList=new ArrayList(end);
				do {
					GBoardDTO article=this.makeArticleFromResult();
					articleList.add(article);
				}while (rs.next());
			}
		}catch (Exception e) {
			System.out.println("getArticles() 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return articleList;
	}
	
	//글목록조회-검색결과 포함
	public List<GBoardDTO> getBoardArticles(int start, int end, String search, String searchtext) {
		
		List<GBoardDTO> articleList=null;
		
		try {
			con=pool.getConnection();
			
			if (search==null || search=="") {
				sql="select * from (select g_table.*, rownum as rn from (select * from g_table order by g_number desc) g_table) where rn >= ? and rn <= ?";
				}else { //제목+본문
					if (search.equals("g_title_g_body")) { //제목+본문
						sql="select * from (select g_table.*, rownum as rn from (select * from g_table "+"where g_title like '%"+searchtext+"%' or g_body like '%"+searchtext+"%') g_table) where rn >= ? and rn <= ?";
					}else {
						sql="select * from (select g_table.*, rownum as rn from (select * from g_table "+" where "+search+" like '%"+searchtext+"%') g_table) where rn >= ? and rn <= ?";
					}
				}
			System.out.println("getBoardArticles()의 sql=>"+sql);
			//-------------------------------------------------------
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, start); //mysql은 start-1
			pstmt.setInt(2, end); //end-1??
			rs=pstmt.executeQuery();
			if (rs.next()) {
				articleList=new ArrayList(end);
				do {
					GBoardDTO article=this.makeArticleFromResult();
					articleList.add(article); //데이터 저장
				}while (rs.next());
			}
		}catch (Exception e) {
			System.out.println("getBoardArticles() 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return articleList;
	}
	
	//글목록조회-검색결과, h_ref 포함
		public List<GBoardDTO> getBoardArticles(int start, int end, String search, String searchtext, String g_ref) {
			
			List<GBoardDTO> articleList=null;
			
			try {
				con=pool.getConnection();
				
				if (search==null || search=="" || g_ref=="") {
					sql="select * from (select g_table.*, rownum as rn from (select * from g_table where g_ref="+g_ref+" order by g_number desc) g_table) where rn >= ? and rn <= ?";
					}else { //제목+본문
						if (search.equals("subject_content")) { //제목+본문
							sql="select * from (select g_table.*, rownum as rn from (select * from g_table "+"where g_title like '%"+searchtext+"%' or g_body like '%"+searchtext+"%') g_table) where rn >= ? and rn <= ?";
						}else {
							sql="select * from (select g_table.*, rownum as rn from (select * from g_table "+" where "+search+" like '%"+searchtext+"%') g_table) where rn >= ? and rn <= ?";
						}
					}
				System.out.println("getBoardArticles()g_ref 의 sql=>"+sql);
				//-------------------------------------------------------
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, start); //mysql은 start-1
				pstmt.setInt(2, end); //end-1??
				rs=pstmt.executeQuery();
				if (rs.next()) {
					articleList=new ArrayList(end);
					do {
						GBoardDTO article=this.makeArticleFromResult();
						articleList.add(article); //데이터 저장
					}while (rs.next());
				}
			}catch (Exception e) {
				System.out.println("getBoardArticles()g_ref 에러 발생=>"+e);
			}finally {
				pool.freeConnection(con, pstmt, rs);
			}
			
			return articleList;
		}
	
	
	//글목록조회-검색결과 포함
	public List<GBoardDTO> getHotBoardArticles(int start, int end, String search, String searchtext) {
			
		List<GBoardDTO> articleList=null;
			
		try {
			con=pool.getConnection();
				
			if (search==null || search=="") {
				sql="select * from (select g_table.*, rownum as rn from (select * from g_table where g_count > 50 order by g_number desc) h_table) where rn >= ? and rn <= ?";
				}else { //제목+본문
					if (search.equals("subject_content")) { //제목+본문
						sql="select * from (select g_table.*, rownum as rn from (select * from g_table "+"where g_count > 50 and (g_title like '%"+searchtext+"%' or g_body like '%"+searchtext+"%')) g_table) where rn >= ? and rn <= ?";
					}else {
						sql="select * from (select g_table.*, rownum as rn from (select * from g_table "+" where g_count > 50 "+search+" like '%"+searchtext+"%') g_table) where rn >= ? and rn <= ?";
					}
				}
			System.out.println("getHotBoardArticles()의 sql=>"+sql);
			//-------------------------------------------------------
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, start); //mysql은 start-1
			pstmt.setInt(2, end); //end-1??
			rs=pstmt.executeQuery();
			if (rs.next()) {
				articleList=new ArrayList(end);
				do {
					GBoardDTO article=this.makeArticleFromResult();
					articleList.add(article); //데이터 저장
				}while (rs.next());
			}
		}catch (Exception e) {
			System.out.println("getBoardArticles() 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
			
		return articleList;
	}	
		
		
		
		
		
	
	//페이징 처리 계산
	public Hashtable pageList(String pageNum, int count) {
		
		Hashtable<String, Integer> pgList=new Hashtable<String, Integer>();
		
		int pageSize=10; //numPerPage=>페이지당 보여주는 게시물 수(=레코드 수) default:10
		int blockSize=10; //pagePerBlock=>블럭당 보여주는 페이지수 default:10
	
		//게시판을 맨 처음 실행시키면 무조건 1페이지부터 출력->가장 최근의 글이 나오기 때문
		if (pageNum==null) {
			pageNum="1"; //default(무조건 1페이지는 선택하지 않아도 보여줘야 하기 때문)
		}
		int currentPage=Integer.parseInt(pageNum); //"1"->1(=nowPage)(현재 페이지)
		//				(1-1)*10+1=1, (2-1)*10+1=11, (3-1)*10+1=21
		int startRow=(currentPage-1)*pageSize+1; //시작 레코드 번호
		int endRow=currentPage*pageSize; //1*10=10, 2*10=20, 3*10=30
		int number=0; //beginPerPage->페이지별로 시작하는 맨 처음에 나오는 게시물번호
		System.out.println("현재 레코드 수(count)=>"+count);
		number=count-(currentPage-1)*pageSize;
		System.out.println(currentPage+"페이지별 number=>"+number);
		//모델1의 list.jsp 페이징 처리 복사
		//				122/10=12.2+1.0=13.2=13
		int pageCount=count/pageSize+(count%pageSize==0?0:1);
		//2. 시작페이지 @@@@중요@@@@
		int startPage=0;
		if (currentPage%blockSize!=0) { //페이지 수가 1~9, 11~19. 21~29 (10의 배수가 아닌 경우)
			startPage=currentPage/blockSize*blockSize+1; //경계선 때문에
		}else { //10%10=0(10,20,30,40,,,)
					 // ((10/10)-1)*10+1=1
			startPage=((currentPage/blockSize)-1)*blockSize+1;
		}
		//종료페이지
		int endPage=startPage+blockSize-1; //1+10-1=10, 2+10-1=11
		System.out.println("startPage=>"+startPage+", endPage=>"+endPage);
		//블럭별로 구분해서 링크 걸어서 출력(마지막 페이지 > 총 페이지 수  면 안 됨... 같아야 함)
		//11>10=>endPage=10
		if (endPage > pageCount) endPage=pageCount; //마지막 페이지=총 페이지 수  이도록 만들어주기
		//페이징 처리에 대한 계산결과->Hashtable, HashMap=>ListAction에 전달->list.jsp
		pgList.put("pageSize", pageSize); // <->pgList(키명)("pageSize")
		pgList.put("blockSize", blockSize);
		pgList.put("currentPage", currentPage);
		pgList.put("startRow", startRow);
		pgList.put("endRow", endRow);
		pgList.put("count", count);
		pgList.put("number", number);
		pgList.put("startPage", startPage);
		pgList.put("endPage", endPage);
		pgList.put("pageCount", pageCount);
		
		return pgList;
	}
	
	//글쓰기
	public void insertArticle(GBoardDTO article) {

		// 1. article->신규글인지 답변글(기존 게시물 번호)인지 확인
		int g_number = article.getG_number();
		
		int number = 0; // 데이터를 저장하기 위한 게시물 번호(=new)
		System.out.println("insertArticle()메서드 내부 g_number=>" + g_number);
		
		int point, grade;

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			sql = "select max(g_number) from g_table";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				number = rs.getInt(1) + 1; // 최대값+1
				System.out.println("글 번호(number)=>" + number);
			} else {
				number = 1;
			}
			con.commit();
			
			sql = "insert into g_table(g_number, mem_id, g_nickname, g_title, g_body, g_date, g_category, g_ref) ";
			sql += " values(?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, number); // article.getNum()이 아니었음
			pstmt.setString(2, article.getMem_id());
			pstmt.setString(3, article.getG_nickname());
			pstmt.setString(4, article.getG_title());
			pstmt.setString(5, article.getG_body());
			pstmt.setTimestamp(6, article.getG_date());
			pstmt.setInt(7, article.getG_category());
			pstmt.setInt(8, article.getG_ref());

			int insert = pstmt.executeUpdate();
			con.commit();
			System.out.println("게시판 글쓰기 성공여부(insert)=>" + insert + " //(1성공 0실패)");
			
			if (insert > 0) {
				String sql2 = "update login set log_point=log_point+10 where mem_id=?";
				pstmt = con.prepareStatement(sql2);
				pstmt.setString(1, article.getMem_id());

				int pointup = pstmt.executeUpdate();
				con.commit();
				System.out.println("글 작성 포인트 적립여부(pointup)=>" + pointup);

				if (pointup > 0) {
					String sql3 = "select log_point, log_grade from login where mem_id=?";
					pstmt = con.prepareStatement(sql3);
					pstmt.setString(1, article.getMem_id());
					rs = pstmt.executeQuery();
					if (rs.next()) {
						point = rs.getInt(1);
						grade = rs.getInt(2);
						System.out.println("현재 포인트(point)=>" + point + ", 등급(grade)=>" + grade);

						String sql4 = "";
						if (point < 100) {
							sql4 = "update login set log_grade=1 where mem_id=?";
						} else if (point >= 100 && point < 500) {
							sql4 = "update login set log_grade=2 where mem_id=?";
						} else if (point >= 500 && point < 2000) {
							sql4 = "update login set log_grade=3 where mem_id=?";
						} else if (point >= 2000 && point < 10000) {
							sql4 = "update login set log_grade=4 where mem_id=?";
						} else if (point >= 10000) {
							sql4 = "update login set log_grade=5 where mem_id=?";
						}
						System.out.println("sql4=>" + sql4);
						pstmt = con.prepareStatement(sql4);
						pstmt.setString(1, article.getMem_id());

						int gradeup = pstmt.executeUpdate();
						con.commit();
						System.out.println("글 작성 후 등급 변동여부(gradeup)=>" + gradeup);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("insertArticle()메서드 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

	}
	
	//글 내용 조회
	public GBoardDTO getArticle(int num) {
		
		GBoardDTO article=null;
		
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			
			sql="update g_table set g_count=g_count+1 where g_number=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			int update=pstmt.executeUpdate();
			con.commit();
			System.out.println("조회수 증가여부(update)=>"+update+" //(1성공 0실패)");
			
			sql="select * from g_table where g_number=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			
			if (rs.next()) { //보여주는 결과가 있다면
				article=this.makeArticleFromResult();
				con.commit();
			}
		}catch (Exception e) {
			System.out.println("getArticle() 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return article;
	}
	
	//글수정-수정할 데이터 찾기
	public GBoardDTO updateGetArticle(int num) {
		
		GBoardDTO article=null;
		
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			sql="select * from g_table where g_number=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				article=makeArticleFromResult();
				con.commit();
			}
		}catch (Exception e) {
			System.out.println("updateGetArticle() 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return article;
	}
	
	//글수정
	public int updateArticle(GBoardDTO article) {
		
		int x=-1; //게시물 수정여부 1성공 0실패
		
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			con.commit();
			
			sql="update g_table set g_title=?, g_body=?, g_category=?, g_ref=? where g_number=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, article.getG_title());
			pstmt.setString(2, article.getG_body());
			pstmt.setInt(3, article.getG_category());
			pstmt.setInt(4,article.getG_ref());
			pstmt.setInt(5, article.getG_number());
					
			int update=pstmt.executeUpdate();
			con.commit();
			System.out.println("게시판 글수정 성공 여부(update)=>"+update+" //(1성공 0실패)");
			x=1; //수정성공
			
		}catch (Exception e) {
			System.out.println("updateArticle() 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return x; 
	}
	
	//글삭제
	public int deleteArticle(int g_number, String g_title) {
		
		int x=-1;
		
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			con.commit();
			
			sql="delete from g_table where g_number=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, g_number);
					
			int delete1=pstmt.executeUpdate();
			con.commit();
			System.out.println("게시판 글삭제 성공여부=>"+delete1+" //(1성공 0실패)");
				
			String sql2="delete from g_comment where g_number=?";
			pstmt=con.prepareStatement(sql2);
			pstmt.setInt(1, g_number);
					
			int delete2=pstmt.executeUpdate();
			con.commit();
			System.out.println("게시판 글삭제 후 댓글 삭제 성공여부=>"+delete2+" //(1성공 0실패)");
			
			String sql3="delete from scrap where s_number=? and s_title=?";
			pstmt=con.prepareStatement(sql3);
			pstmt.setInt(1, g_number);
			pstmt.setString(2, g_title);
			
			int delete3=pstmt.executeUpdate();
			con.commit();
			System.out.println("게시판 글삭제 후 스크랩 삭제 성공여부=>"+delete3+" //(1성공 0실패)");
			
			x=1;
			
		}catch (Exception e) {
			System.out.println("deleteArticle() 에러발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return x;
	}
	
	
	
	
	
	
}
