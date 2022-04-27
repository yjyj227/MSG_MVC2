package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class BoardDAO {

	private DBConnectionMgr pool = null; // 1. 연결객체선언
	// 공통
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";

	// 2. 생성자 통해서 연결
	public BoardDAO() {

		try {
			pool = DBConnectionMgr.getInstance();
			System.out.println("pool=>" + pool);
		} catch (Exception e) {
			System.out.println("DB접속 오류=>" + e);
		}
	}

	

	// H
	private BoardDTO makeHArticleFromResult() throws Exception {

		BoardDTO article = new BoardDTO();

		article.setNumber(rs.getInt("h_number"));
		article.setMem_id(rs.getString("mem_id"));
		article.setNickname(rs.getString("h_nickname"));
		article.setTitle(rs.getString("h_title"));
		article.setBody(rs.getString("h_body"));
		article.setDate(rs.getTimestamp("h_date"));
		article.setCount(rs.getInt("h_count"));
		article.setScrap(rs.getInt("h_scrap"));
		article.setCategory(rs.getInt("h_category"));
		article.setRef(rs.getInt("h_ref"));
		
		HBoardDAO hboard=new HBoardDAO();
		article.setComments(hboard.getCommentCountForList(rs.getInt("h_number")));

		return article;
	}

	// Z
	private BoardDTO makeZArticleFromResult() throws Exception {

		BoardDTO article = new BoardDTO();

		article.setNumber(rs.getInt("z_number"));
		article.setMem_id(rs.getString("mem_id"));
		article.setNickname(rs.getString("z_nickname"));
		article.setTitle(rs.getString("z_title"));
		article.setBody(rs.getString("z_body"));
		article.setDate(rs.getTimestamp("z_date"));
		article.setCount(rs.getInt("z_count"));
		article.setScrap(rs.getInt("z_scrap"));
		article.setCategory(rs.getInt("z_category"));
		article.setRef(rs.getInt("z_ref"));
		
		ZBoardDAO zboard=new ZBoardDAO();
		article.setComments(zboard.getCommentCountForList(rs.getInt("z_number")));
		
		return article;
	}

	// M
	private BoardDTO makeMArticleFromResult() throws Exception {

		BoardDTO article = new BoardDTO();

		article.setNumber(rs.getInt("m_number"));
		article.setMem_id(rs.getString("mem_id"));
		article.setNickname(rs.getString("m_nickname"));
		article.setTitle(rs.getString("m_title"));
		article.setBody(rs.getString("m_body"));
		article.setDate(rs.getTimestamp("m_date"));
		article.setCount(rs.getInt("m_count"));
		article.setScrap(rs.getInt("m_scrap"));
		article.setCategory(rs.getInt("m_category"));
		article.setRef(rs.getInt("m_ref"));
		
		MBoardDAO mboard=new MBoardDAO();
		article.setComments(mboard.getCommentCountForList(rs.getInt("m_number")));

		return article;
	}

	// G
	private BoardDTO makeGArticleFromResult() throws Exception {

		BoardDTO article = new BoardDTO();

		article.setNumber(rs.getInt("g_number"));
		article.setMem_id(rs.getString("mem_id"));
		article.setNickname(rs.getString("g_nickname"));
		article.setTitle(rs.getString("g_title"));
		article.setBody(rs.getString("g_body"));
		article.setDate(rs.getTimestamp("g_date"));
		article.setCount(rs.getInt("g_count"));
		article.setScrap(rs.getInt("g_scrap"));
		article.setCategory(rs.getInt("g_category"));
		article.setRef(rs.getInt("g_ref"));
		
		GBoardDAO gboard=new GBoardDAO();
		article.setComments(gboard.getCommentCountForList(rs.getInt("g_number")));

		return article;
	}

	// R
	private BoardDTO makeRArticleFromResult() throws Exception {

		BoardDTO article = new BoardDTO();

		article.setNumber(rs.getInt("r_number"));
		article.setMem_id(rs.getString("mem_id"));
		article.setNickname(rs.getString("r_nickname"));
		article.setTitle(rs.getString("r_title"));
		article.setBody(rs.getString("r_body"));
		article.setDate(rs.getTimestamp("r_date"));
		article.setCount(rs.getInt("r_count"));
		article.setScrap(rs.getInt("r_scrap"));
		article.setCategory(rs.getInt("r_category"));
		article.setRef(rs.getInt("r_ref"));
		
		RBoardDAO rboard=new RBoardDAO();
		article.setComments(rboard.getCommentCountForList(rs.getInt("r_number")));

		return article;
	}

	// 내가 쓴 글 모아보기
	// 레코드 수
	public int getMyArticleCount(String init, String mem_id) {
		int x = 0;
		try {
			con = pool.getConnection();
			System.out.println("con=>" + con);
			sql = "select count(*) from " + init + "_table where mem_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("getMyArticleCount() 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return x;
	}

	// 글 목록 조회
	public List<BoardDTO> getMyArticles(int start, int end, String init, String mem_id) {
		List<BoardDTO> articleList = null;
		try {
			con = pool.getConnection();
			sql = "select * from (select " + init + "_table.*, rownum as rn from (select * from " 
					+ init + "_table where mem_id='"+mem_id+"' order by " + init + "_number desc) " 
					+ init + "_table) where rn >= ? and rn <=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				articleList = new ArrayList(end);
				if (init.equals("h")) {
					do {
						BoardDTO article = this.makeHArticleFromResult();
						articleList.add(article);
					} while (rs.next());
				}else if (init.equals("z")) {
					do {
						BoardDTO article = this.makeZArticleFromResult();
						articleList.add(article);
					} while (rs.next());
				}else if (init.equals("m")) {
					do {
						BoardDTO article = this.makeMArticleFromResult();
						articleList.add(article);
					} while (rs.next());
				}else if (init.equals("g")) {
					do {
						BoardDTO article = this.makeGArticleFromResult();
						articleList.add(article);
					} while (rs.next());
				}else if (init.equals("r")) {
					do {
						BoardDTO article = this.makeRArticleFromResult();
						articleList.add(article);
					} while (rs.next());
				}
			}
		} catch (Exception e) {
			System.out.println("getMyArticles() 에러 발생=>" + e);
		} finally {
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
		
		
		
	//--------------------------------------------------------------------------------------------------------
	// Scrap
	private BoardDTO makeSArticleFromResult() throws Exception {
		BoardDTO article = new BoardDTO();
		article.setScrap_number(rs.getInt("scrap_number"));
		article.setMem_id(rs.getString("mem_id"));
		article.setS_number(rs.getInt("s_number"));
		article.setS_category(rs.getInt("s_category"));
		article.setS_title(rs.getString("s_title"));
		article.setS_date(rs.getTimestamp("s_date"));
		article.setS_url(rs.getString("s_url"));
		article.setS_nickname(rs.getString("s_nickname"));

		return article;
	}
		
		
		
	//url 만들기
	public String makeScrapUrl(int s_category, int s_number) {
		String scrapurl="";
		if (s_category>=10 && s_category<20) {
			scrapurl="H_Content.do?h_number="+s_number;
		}else if (s_category>=20 && s_category<30) {
			scrapurl="Z_Content.do?z_number="+s_number;
		}else if (s_category>=30 && s_category<40) {
			scrapurl="M_Content.do?m_number="+s_number;
		}else if (s_category>=40 && s_category<50) {
			scrapurl="G_Content.do?g_number="+s_number;
		}else if (s_category>=50 && s_category<60) {
			scrapurl="R_Content.do?r_number="+s_number;
		}
		return scrapurl;
	}
	
	//
	public String makeInit(int s_category) {
		String init="";
		if (s_category>=10 && s_category<20) {
			init="h";
		}else if (s_category>=20 && s_category<30) {
			init="z";
		}else if (s_category>=30 && s_category<40) {
			init="m";
		}else if (s_category>=40 && s_category<50) {
			init="g";
		}else if (s_category>=50 && s_category<60) {
			init="r";
		}
		return init;
	}
	
	
	//스크랩
	public void scrap(BoardDTO article) {
		int scrap_number=article.getScrap_number();
		int number=0;
		System.out.println("scrap()메서드 내부 scrap_number=>"+scrap_number);
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			sql="select max(scrap_number) from scrap";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				number=rs.getInt(1)+1;
				System.out.println("스크랩 순번(number)=>"+number);
			}else {
				number=1;
			}
			con.commit();
			sql="insert into scrap(scrap_number, mem_id, s_number, s_category, s_title, s_date, s_url, s_nickname)";
			sql+=" values(?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.setString(2, article.getMem_id());
			pstmt.setInt(3, article.getS_number());
			pstmt.setInt(4, article.getS_category());
			pstmt.setString(5, article.getS_title());
			pstmt.setTimestamp(6, article.getS_date());
			pstmt.setString(7, article.getS_url());
			pstmt.setString(8, article.getS_nickname());
			int scrap=pstmt.executeUpdate();
			con.commit();
			System.out.println("스크랩 성공여부(scrap)=>"+scrap);
			
			//스크랩수 증가
			int s_category=article.getS_category();
			String init=this.makeInit(s_category);
			if (scrap > 0) {
				sql="update "+init+"_table set "+init+"_scrap="+init+"_scrap+1 where "+init+"_number=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, article.getS_number());
				int update=pstmt.executeUpdate();
				con.commit();
			}
		}catch (Exception e) {
			System.out.println("scrap()메서드 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
	}
	
	
	//스크랩 레코드 수
	public int getScrapArticleCount(String mem_id) {
		
		int x=0;
		
		try {
			con=pool.getConnection();
			System.out.println("con=>"+con);
			sql="select count(*) from scrap where mem_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs=pstmt.executeQuery();
			
			if (rs.next()) {
				x=rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.println("getScrapArticleCount() 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return x;
	}
	
	
	//스크랩 목록 조회
	public List<BoardDTO> getScrapArticles(String mem_id, int start, int end) {
		List<BoardDTO> articleList=null;
		try {
			con=pool.getConnection();
			sql="select * from (select scrap.*, rownum as rn from "
			+" (select * from scrap where mem_id=? order by scrap_number desc) "
			+" scrap) where rn >= ? and rn <= ?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				articleList=new ArrayList(end);
				do {
					BoardDTO article = new BoardDTO();
					article.setScrap_number(rs.getInt("scrap_number"));
					article.setMem_id(rs.getString("mem_id"));
					article.setS_number(rs.getInt("s_number"));
					article.setS_category(rs.getInt("s_category"));
					article.setS_title(rs.getString("s_title"));
					article.setS_date(rs.getTimestamp("s_date"));
					article.setS_url(rs.getString("s_url"));
					article.setS_nickname(rs.getString("s_nickname"));
					
					articleList.add(article);
				}while (rs.next());
			}
		}catch (Exception e) {
			System.out.println("getScrapArticles() 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return articleList;
	}
	
	//스크랩 삭제
	public int deleteScrapArticle(int scrap_number, int s_category, int s_number) {
		
		int x=-1;
		
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			con.commit();
				
			sql="delete from scrap where scrap_number=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, scrap_number);
					
			int delete=pstmt.executeUpdate();
			con.commit();
			System.out.println("스크랩 글삭제 성공여부=>"+delete+" //(1성공 0실패)");
			
			//스크랩수 감소
			String init=this.makeInit(s_category);
			System.out.println("init=>"+init);
			
			if (delete > 0) {
				sql="update "+init+"_table set "+init+"_scrap="+init+"_scrap-1 where "+init+"_number=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, s_number);
				
				int update=pstmt.executeUpdate();
				con.commit();
				System.out.println("스크랩수 감소여부(update)=>"+update+" //(1성공 0실패)");
			}
			
			
			
			x=1;
		}catch (Exception e) {
			System.out.println("deleteScrapArticle() 에러발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return x;
	}
	
	
	//별점
	
	// Rate
	private BoardDTO makeRateArticleFromResult() throws Exception {

		BoardDTO article = new BoardDTO();

		article.setV_number(rs.getInt("v_number"));
		article.setMem_id(rs.getString("mem_id"));
		article.setV_movie(rs.getInt("v_movie"));
		article.setV_star(rs.getDouble("v_star"));
		article.setV_like(rs.getInt("v_like"));

		System.out.println("makeRateArticleFromResult()의 article=>"+article);
		return article;
	}
	
	//별점 평균내기
	public double avgStar(int v_movie) {
		double x=0;
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			sql="select round(avg(v_star), 2) from star where v_movie=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, v_movie);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				x=rs.getDouble(1);
			}
			con.commit();
		}catch (Exception e) {
			System.out.println("avgStar() 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return x;
	}
	
	//좋아요 총합
	public int countLikes(int v_movie) {
		int x=0;
		try {
			con=pool.getConnection();
			sql="select sum(v_like) from star where v_movie=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, v_movie);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				x=rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.println("countLikes() 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return x;
	}
	
	
	
	//내 별점 표시
	public BoardDTO getMyRate(String mem_id, int v_movie) {
		 BoardDTO article=null;
		 try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			sql="select * from star where mem_id=? and v_movie=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.setInt(2, v_movie);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				article=makeRateArticleFromResult();
				con.commit();
				System.out.println("getMyRate()의 article=>"+article);
			}
		 }catch (Exception e) {
			 System.out.println("getMyRate() 에러 발생=>"+e);
		 }finally {
			 pool.freeConnection(con, pstmt, rs);
		 }
		 return article;
	}
	
	
	
	
	//별점, 좋아요 입력
	public void rate(BoardDTO article) {
		int v_number=article.getV_number();
		int number=0;
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			sql="select * from star where mem_id=? and v_movie=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, article.getMem_id());
			pstmt.setInt(2, article.getV_movie());
			rs=pstmt.executeQuery();
			if (rs.next()) {
				sql="update star set v_star=?, v_like=? where mem_id=? and v_movie=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setDouble(1, article.getV_star());
				pstmt.setInt(2, article.getV_like());
				pstmt.setString(3, article.getMem_id());
				pstmt.setInt(4, article.getV_movie());
				int update=pstmt.executeUpdate();
				con.commit();
			}else {
				sql="select max(v_number) from star";
				pstmt=con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				if (rs.next()) {
					number=rs.getInt(1)+1;
				}else {
					number=1;
				}
				con.commit();
				sql="insert into star(v_number, mem_id, v_movie, v_star, v_like) ";
				sql+=" values(?, ?, ?, ?, ?)";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, number);
				pstmt.setString(2, article.getMem_id());
				pstmt.setInt(3, article.getV_movie());
				pstmt.setDouble(4, article.getV_star());
				pstmt.setInt(5, article.getV_like());
				int insert=pstmt.executeUpdate();
				con.commit();
			}
		}catch (Exception e) {
			System.out.println("rate() 메서드 에러 발생=>"+e);
		}finally {
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
