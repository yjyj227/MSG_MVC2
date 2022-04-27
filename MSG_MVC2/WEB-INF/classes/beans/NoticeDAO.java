package beans;

import java.sql.*;
import java.util.*;
import beans.*;

public class NoticeDAO {

	private DBConnectionMgr pool = null; // 1. 연결객체선언
	// 공통
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";

	// 2. 생성자 통해서 연결
	public NoticeDAO() {

		try {
			pool = DBConnectionMgr.getInstance();
			System.out.println("pool=>" + pool);
		} catch (Exception e) {
			System.out.println("DB접속 오류=>" + e);
		}
	}

	// article~
	private NoticeDTO makeArticleFromResult() throws Exception {

		NoticeDTO article = new NoticeDTO();

		article.setNotice_number(rs.getInt("notice_number"));
		article.setAdmin_id(rs.getString("admin_id"));
		article.setNot_title(rs.getString("not_title"));
		article.setNot_body(rs.getString("not_body"));
		article.setNot_date(rs.getTimestamp("not_date"));
		article.setNot_count(rs.getInt("not_count")); // 조회수

		return article;
	}

	// 메서드 작성~

	// 총 레코드(게시물) 수
	public int getArticleCount() {

		int x = 0;

		try {
			con = pool.getConnection();
			System.out.println("con=>" + con);
			sql = "select count(*) from notice";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("getArticleCount() 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return x;
	}

	// 검색어에 해당되는 총 레코드 수
	public int getArticleSearchCount(String search, String searchtext) {

		int x = 0;

		try {
			con = pool.getConnection();
			System.out.println("con=>" + con);
			// --------------------------------------------------------------------------
			if (search == null || search == "") { // 검색분야를 선택X
				sql = "select count(*) from notice";
			} else { // 검색분야(제목, 작성자, 제목+본문)
				if (search.equals("not_title_not_body")) { // 제목+본문
					sql = "select count(*) from notice where not_title like '%" + searchtext + "%' or not_body like '%"
							+ searchtext + "%' ";
				} else { // 제목, 작성자->매개변수를 이용해서 하나의 sql로 통합 가능
					sql = "select count(*) from notice where " + search + " like '%" + searchtext + "%' ";
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
			System.out.println("getArticleSearchCount() 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return x;
	}

	/*
	 * // 검색어에 해당되는 총 레코드 수 ref public int getArticleSearchCount(String search,
	 * String searchtext, String h_ref) {
	 * 
	 * int x = 0;
	 * 
	 * try { con = pool.getConnection(); System.out.println("con=>" + con); //
	 * -------------------------------------------------------------------------- if
	 * (search == null || search == "") { // 검색분야를 선택X sql =
	 * "select count(*) from h_table where h_ref=" + h_ref; } else { // 검색분야(제목,
	 * 작성자, 제목+본문) if (search.equals("h_title_h_body")) { // 제목+본문 sql =
	 * "select count(*) from h_table where h_ref=" + h_ref + " and h_title like '%"
	 * + searchtext + "%' or h_body like '%" + searchtext + "%' "; } else { // 제목,
	 * 작성자->매개변수를 이용해서 하나의 sql로 통합 가능 sql =
	 * "select count(*) from h_table where h_ref=" + h_ref + " and " + search +
	 * " like '%" + searchtext + "%' "; } }
	 * System.out.println("getArticleSearchCount()h_ref 검색어=>" + sql); //
	 * --------------------------------------------------------------------------
	 * pstmt = con.prepareStatement(sql); rs = pstmt.executeQuery();
	 * 
	 * if (rs.next()) { x = rs.getInt(1); System.out.println("검색된 총 레코드 수=>" + x); }
	 * } catch (Exception e) {
	 * System.out.println("getArticleSearchCount()h_ref 에러 발생=>" + e); } finally {
	 * pool.freeConnection(con, pstmt, rs); }
	 * 
	 * return x; }
	 * 
	 * // 검색어에 해당되는 총 레코드 수 HOT public int getHotArticleSearchCount(String search,
	 * String searchtext) {
	 * 
	 * int x = 0;
	 * 
	 * try { con = pool.getConnection(); System.out.println("con=>" + con); //
	 * -------------------------------------------------------------------------- if
	 * (search == null || search == "") { // 검색분야를 선택X sql =
	 * "select count(*) from h_table where h_count > 50"; } else { // 검색분야(제목, 작성자,
	 * 제목+본문) if (search.equals("h_title_h_body")) { // 제목+본문 sql =
	 * "select count(*) from h_table where h_count > 50 and (h_title like '%" +
	 * searchtext + "%' or h_body like '%" + searchtext + "%') "; } else { // 제목,
	 * 작성자->매개변수를 이용해서 하나의 sql로 통합 가능 sql =
	 * "select count(*) from h_table where h_count > 50 and " + search + " like '%"
	 * + searchtext + "%' "; } } System.out.println("getArticleSearchCount 검색어=>" +
	 * sql); //
	 * --------------------------------------------------------------------------
	 * pstmt = con.prepareStatement(sql); rs = pstmt.executeQuery();
	 * 
	 * if (rs.next()) { x = rs.getInt(1); System.out.println("검색된 총 레코드 수=>" + x); }
	 * } catch (Exception e) {
	 * System.out.println("getHotArticleSearchCount() 에러 발생=>" + e); } finally {
	 * pool.freeConnection(con, pstmt, rs); }
	 * 
	 * return x; }
	 */

	// 글목록조회
	public List<NoticeDTO> getArticles(int start, int end) {

		List<NoticeDTO> articleList = null;

		try {
			con = pool.getConnection();
			sql = "select * from (select notice.*, rownum as rn from (select * from notice order by notice_number desc) notice) where rn between ? and (? - 1)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, start + end);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				articleList = new ArrayList(end);
				do {
					NoticeDTO article = this.makeArticleFromResult();
					articleList.add(article);
				} while (rs.next());
			}
		} catch (Exception e) {
			System.out.println("getArticles() 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return articleList;
	}

	// 글목록조회-검색결과 포함
	public List<NoticeDTO> getBoardArticles(int start, int end, String search, String searchtext) {

		List<NoticeDTO> articleList = null;

		try {
			con = pool.getConnection();

			if (search == null || search == "") {
				sql = "select * from (select notice.*, rownum as rn from (select * from notice order by notice_number desc) notice) where rn >= ? and rn <= ?";
			} else { // 제목+본문
				if (search.equals("not_title_not_body")) { // 제목+본문
					sql = "select * from (select notice.*, rownum as rn from (select * from notice "
							+ "where not_title like '%" + searchtext + "%' or not_body like '%" + searchtext
							+ "%') notice) where rn >= ? and rn <= ?";
				} else {
					sql = "select * from (select notice.*, rownum as rn from (select * from notice " + " where "
							+ search + " like '%" + searchtext + "%') notice) where rn >= ? and rn <= ?";
				}
			}
			System.out.println("getBoardArticles()의 sql=>" + sql);
			// -------------------------------------------------------
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, start); // mysql은 start-1
			pstmt.setInt(2, end); // end-1??
			rs = pstmt.executeQuery();
			if (rs.next()) {
				articleList = new ArrayList(end);
				do {
					NoticeDTO article = this.makeArticleFromResult();
					articleList.add(article); // 데이터 저장
				} while (rs.next());
			}
		} catch (Exception e) {
			System.out.println("getBoardArticles() 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return articleList;
	}

	/*
	 * // 글목록조회-검색결과, h_ref 포함 public List<HBoardDTO> getBoardArticles(int start,
	 * int end, String search, String searchtext, String h_ref) {
	 * 
	 * List<HBoardDTO> articleList = null;
	 * 
	 * try { con = pool.getConnection();
	 * 
	 * if (search == null || search == "" || h_ref == "") { sql =
	 * "select * from (select h_table.*, rownum as rn from (select * from h_table where h_ref="
	 * + h_ref + " order by h_number desc) h_table) where rn >= ? and rn <= ?"; }
	 * else { // 제목+본문 if (search.equals("subject_content")) { // 제목+본문 sql =
	 * "select * from (select h_table.*, rownum as rn from (select * from h_table "
	 * + "where h_title like '%" + searchtext + "%' or h_body like '%" + searchtext
	 * + "%') h_table) where rn >= ? and rn <= ?"; } else { sql =
	 * "select * from (select h_table.*, rownum as rn from (select * from h_table "
	 * + " where " + search + " like '%" + searchtext +
	 * "%') h_table) where rn >= ? and rn <= ?"; } }
	 * System.out.println("getBoardArticles()h_ref 의 sql=>" + sql); //
	 * ------------------------------------------------------- pstmt =
	 * con.prepareStatement(sql); pstmt.setInt(1, start); // mysql은 start-1
	 * pstmt.setInt(2, end); // end-1?? rs = pstmt.executeQuery(); if (rs.next()) {
	 * articleList = new ArrayList(end); do { HBoardDTO article =
	 * this.makeArticleFromResult(); articleList.add(article); // 데이터 저장 } while
	 * (rs.next()); } } catch (Exception e) {
	 * System.out.println("getBoardArticles()h_ref 에러 발생=>" + e); } finally {
	 * pool.freeConnection(con, pstmt, rs); }
	 * 
	 * return articleList; }
	 * 
	 * // 글목록조회-검색결과 포함 public List<HBoardDTO> getHotBoardArticles(int start, int
	 * end, String search, String searchtext) {
	 * 
	 * List<HBoardDTO> articleList = null;
	 * 
	 * try { con = pool.getConnection();
	 * 
	 * if (search == null || search == "") { sql =
	 * "select * from (select h_table.*, rownum as rn from (select * from h_table where h_count > 50 order by h_number desc) h_table) where rn >= ? and rn <= ?"
	 * ; } else { // 제목+본문 if (search.equals("subject_content")) { // 제목+본문 sql =
	 * "select * from (select h_table.*, rownum as rn from (select * from h_table "
	 * + "where h_count > 50 and (h_title like '%" + searchtext +
	 * "%' or h_body like '%" + searchtext +
	 * "%')) h_table) where rn >= ? and rn <= ?"; } else { sql =
	 * "select * from (select h_table.*, rownum as rn from (select * from h_table "
	 * + " where h_count > 50 " + search + " like '%" + searchtext +
	 * "%') h_table) where rn >= ? and rn <= ?"; } }
	 * System.out.println("getHotBoardArticles()의 sql=>" + sql); //
	 * ------------------------------------------------------- pstmt =
	 * con.prepareStatement(sql); pstmt.setInt(1, start); // mysql은 start-1
	 * pstmt.setInt(2, end); // end-1?? rs = pstmt.executeQuery(); if (rs.next()) {
	 * articleList = new ArrayList(end); do { HBoardDTO article =
	 * this.makeArticleFromResult(); articleList.add(article); // 데이터 저장 } while
	 * (rs.next()); } } catch (Exception e) {
	 * System.out.println("getBoardArticles() 에러 발생=>" + e); } finally {
	 * pool.freeConnection(con, pstmt, rs); }
	 * 
	 * return articleList; }
	 */

	// 페이징 처리 계산
	public Hashtable pageList(String pageNum, int count) {

		Hashtable<String, Integer> pgList = new Hashtable<String, Integer>();

		int pageSize = 10; // numPerPage=>페이지당 보여주는 게시물 수(=레코드 수) default:10
		int blockSize = 10; // pagePerBlock=>블럭당 보여주는 페이지수 default:10

		// 게시판을 맨 처음 실행시키면 무조건 1페이지부터 출력->가장 최근의 글이 나오기 때문
		if (pageNum == null) {
			pageNum = "1"; // default(무조건 1페이지는 선택하지 않아도 보여줘야 하기 때문)
		}
		int currentPage = Integer.parseInt(pageNum); // "1"->1(=nowPage)(현재 페이지)
		// (1-1)*10+1=1, (2-1)*10+1=11, (3-1)*10+1=21
		int startRow = (currentPage - 1) * pageSize + 1; // 시작 레코드 번호
		int endRow = currentPage * pageSize; // 1*10=10, 2*10=20, 3*10=30
		int number = 0; // beginPerPage->페이지별로 시작하는 맨 처음에 나오는 게시물번호
		System.out.println("현재 레코드 수(count)=>" + count);
		number = count - (currentPage - 1) * pageSize;
		System.out.println(currentPage + "페이지별 number=>" + number);
		// 모델1의 list.jsp 페이징 처리 복사
		// 122/10=12.2+1.0=13.2=13
		int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		// 2. 시작페이지 @@@@중요@@@@
		int startPage = 0;
		if (currentPage % blockSize != 0) { // 페이지 수가 1~9, 11~19. 21~29 (10의 배수가 아닌 경우)
			startPage = currentPage / blockSize * blockSize + 1; // 경계선 때문에
		} else { // 10%10=0(10,20,30,40,,,)
					// ((10/10)-1)*10+1=1
			startPage = ((currentPage / blockSize) - 1) * blockSize + 1;
		}
		// 종료페이지
		int endPage = startPage + blockSize - 1; // 1+10-1=10, 2+10-1=11
		System.out.println("startPage=>" + startPage + ", endPage=>" + endPage);
		// 블럭별로 구분해서 링크 걸어서 출력(마지막 페이지 > 총 페이지 수 면 안 됨... 같아야 함)
		// 11>10=>endPage=10
		if (endPage > pageCount)
			endPage = pageCount; // 마지막 페이지=총 페이지 수 이도록 만들어주기
		// 페이징 처리에 대한 계산결과->Hashtable, HashMap=>ListAction에 전달->list.jsp
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

	// 글쓰기
	public void insertArticle(NoticeDTO article) {

		// 1. article->신규글인지 답변글(기존 게시물 번호)인지 확인
		int notice_number = article.getNot_count();

		int number = 0; // 데이터를 저장하기 위한 게시물 번호(=new)
		System.out.println("insertArticle()메서드 내부 notice_number=>" + notice_number);

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			sql = "select max(notice_number) from notice";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				number = rs.getInt(1) + 1; // 최대값+1
				System.out.println("글 번호(number)=>" + number);
			} else {
				number = 1;
			}
			con.commit();

			sql = "insert into notice(notice_number, admin_id, not_title, not_body, not_date) ";
			sql += " values(?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, number); // article.getNum()이 아니었음
			pstmt.setString(2, article.getAdmin_id());
			pstmt.setString(3, article.getNot_title());
			pstmt.setString(4, article.getNot_body());
			pstmt.setTimestamp(5, article.getNot_date());

			int insert = pstmt.executeUpdate();
			con.commit();
			System.out.println("게시판 글쓰기 성공여부(insert)=>" + insert + " //(1성공 0실패)");
		} catch (Exception e) {
			System.out.println("insertArticle()메서드 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

	}

	// 글 내용 조회
	public NoticeDTO getArticle(int num) {

		NoticeDTO article = null;

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);

			sql = "update notice set not_count=not_count+1 where notice_number=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);

			int update = pstmt.executeUpdate();
			con.commit();
			System.out.println("조회수 증가여부(update)=>" + update + " //(1성공 0실패)");

			sql = "select * from notice where notice_number=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 보여주는 결과가 있다면
				article = this.makeArticleFromResult();
				con.commit();
			}
		} catch (Exception e) {
			System.out.println("getArticle() 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return article;
	}

	// 글수정-수정할 데이터 찾기
	public NoticeDTO updateGetArticle(int num) {

		NoticeDTO article = null;

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			sql = "select * from notice where notice_number=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				article = makeArticleFromResult();
				con.commit();
			}
		} catch (Exception e) {
			System.out.println("updateGetArticle() 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return article;
	}

	// 글수정
	public int updateArticle(NoticeDTO article) {

		int x = -1; // 게시물 수정여무 1성공 0실패

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			con.commit();
			
			sql = "update notice set not_title=?, not_body=? where notice_number=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getNot_title());
			pstmt.setString(2, article.getNot_body());
			pstmt.setInt(3, article.getNotice_number());

			int update = pstmt.executeUpdate();
			con.commit();
			System.out.println("게시판 글수정 성공 여부(update)=>" + update + " //(1성공 0실패)");
			x = 1; // 수정성공
		} catch (Exception e) {
			System.out.println("updateArticle() 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return x;
	}

	// 글삭제
	public int deleteArticle(int num) {
		// String dbpasswd="";
		int x = -1;

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			con.commit();

			sql = "delete from notice where notice_number=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);

			int delete = pstmt.executeUpdate();
			con.commit();
			System.out.println("게시판 글삭제 성공여부=>" + delete + " //(1성공 0실패)");
			x = 1;
			
		} catch (Exception e) {
			System.out.println("deleteArticle() 에러발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return x;
	}

}
