package beans;

import java.sql.*;
import java.util.*;

public class GCommentDAO {

	private DBConnectionMgr pool = null; // 1. 연결객체선언
	// 공통
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";

	public GCommentDAO() {

		try {
			pool = DBConnectionMgr.getInstance();
			System.out.println("pool=>" + pool);
		} catch (Exception e) {
			System.out.println("DB접속 오류=>" + e);
		}
	}

	private GCommentDTO makeArticleFromResult() throws Exception {

		GCommentDTO article = new GCommentDTO();

		article.setG_cnumber(rs.getInt("g_cnumber"));
		article.setMem_id(rs.getString("mem_id"));
		article.setG_number(rs.getInt("g_number"));
		article.setG_cbody(rs.getString("g_cbody"));
		article.setG_cnickname(rs.getString("g_cnickname"));
		article.setG_cdate(rs.getTimestamp("g_cdate"));
		article.setG_cref(rs.getInt("g_cref"));
		article.setG_cre_step(rs.getInt("g_cre_step"));
		article.setG_cre_level(rs.getInt("g_cre_level"));

		return article;
	}

	public List<GCommentDTO> getComment(int g_number) {

		List<GCommentDTO> commentList = new ArrayList<GCommentDTO>();

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			System.out.println("con=>" + con);
			sql = "select * from g_comment where g_number=? order by g_cref asc, g_cre_step asc, g_cdate asc";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, g_number);
			con.commit();
			rs = pstmt.executeQuery();

			if (rs.next()) {
				do {
					GCommentDTO comment = new GCommentDTO();
					comment = this.makeArticleFromResult();
					commentList.add(comment);
					con.commit();
				} while (rs.next());
			}
			con.commit();
		} catch (Exception e) {
			System.out.println("getComment() 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return commentList;
	}


	// 게시판의 글쓰기 및 답변글 쓰기

	public void insertComment(GCommentDTO comment) {
		// 1. article->신규글인지 답변글(기존 게시물 번호)인지 확인
		int g_cnumber = comment.getG_cnumber();
		int g_cref = comment.getG_cref();
		int g_cre_step = comment.getG_cre_step();
		int g_cre_level = comment.getG_cre_level();

		int number = 0; // 데이터를 저장하기 위한 게시물 번호(=new)
		System.out.println("insertComment()메서드 내부 g_cnumber=>" + g_cnumber);
		System.out.println("g_cref=>" + g_cref + ", g_cre_step=>" + g_cre_step + ", g_cre_level=>" + g_cre_level);
		
		int point, grade;

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			sql = "select max(g_cnumber) from g_comment";
			// System.out.println("insertComment()의 첫 번째 sql=>"+sql);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				number = rs.getInt(1) + 1; // 최대값+1
				System.out.println("댓글 번호(number)=>" + number);
			} else {
				number = 1;
			}
			con.commit();
			// 답변글이라면(게시글번호가 양수이면서 1 이상인 경우)
			if (g_cnumber != 0) {
				// 같은 그룹번호를 가지고 있으면서(ref=?) 나보다 step값이 큰 게시물(re_step>?)을 찾아서 그 step 하나 증가
				// @@----->증가시켜놓고 자기가 1이 돼서 문제가 생김

				System.out.println("update 전의 g_cre_step=>" + g_cre_step);

				// g_cref가 같은 댓글들 중 step값이 제일 높은 걸 찾아서 그것보다 1 크게 만들기 위해
				String sql2 = "select max(g_cre_step) from g_comment where g_cref=?";
				pstmt = con.prepareStatement(sql2);
				pstmt.setInt(1, g_cref);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					g_cre_step = rs.getInt(1) + 1;
					System.out.println("g_cre_step의 최댓값+1=>" + g_cre_step);
				} else {
					g_cre_step = g_cre_step + 1;
				}
				g_cre_level = g_cre_level + 1;
				System.out.println("update 후의 g_cre_step=>" + g_cre_step + ", g_cre_level=>" + g_cre_level);
				con.commit();

				/*
				 * sql="update g_comment set g_cre_step=g_cre_step+1 where g_cref=? and g_cre_step>?"
				 * ;
				 * 
				 * System.out.println("insertComment()의 sql=>"+sql);
				 * pstmt=con.prepareStatement(sql); pstmt.setInt(1, g_cref); pstmt.setInt(2,
				 * g_cre_step); int update=pstmt.executeUpdate(); con.commit();
				 * System.out.println("댓글수정여부(update)=>"+update+" //(1성공 0실패)"); //1성공 0실패
				 * System.out.println("update 하고 나서의 g_cre_step()=>"+g_cre_step);
				 */
				// 답변글
				// g_cre_step=g_cre_step + 1;

			} else { // 신규글이라면 num=0
				g_cref = number;
				g_cre_step = 0;
				g_cre_level = 0;
				con.commit();
			}
			sql = "insert into g_comment(g_cnumber, mem_id, g_number, g_cbody, g_cnickname, g_cdate, ";
			sql += " g_cref, g_cre_step, g_cre_level) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, number); // article.getNum()이 아니었음
			pstmt.setString(2, comment.getMem_id());
			pstmt.setInt(3, comment.getG_number());
			pstmt.setString(4, comment.getG_cbody());
			pstmt.setString(5, comment.getG_cnickname());
			pstmt.setTimestamp(6, comment.getG_cdate());

			pstmt.setInt(7, g_cref);
			pstmt.setInt(8, g_cre_step);
			pstmt.setInt(9, g_cre_level);

			int insert = pstmt.executeUpdate();
			con.commit();
			System.out.println("댓글쓰기 성공여부(insert)=>" + insert + " //(1성공 0실패)");
			
			if (insert > 0) {
				String sql3="update login set log_point=log_point+5 where mem_id=?";
				pstmt=con.prepareStatement(sql3);
				pstmt.setString(1, comment.getMem_id());
				
				int pointup=pstmt.executeUpdate();
				con.commit();
				System.out.println("댓글 작성 포인트 적립여부(pointup)=>"+pointup);
				
				if (pointup > 0) {
					String sql4 = "select log_point, log_grade from login where mem_id=?";
					pstmt = con.prepareStatement(sql4);
					pstmt.setString(1, comment.getMem_id());
					rs = pstmt.executeQuery();
					if (rs.next()) {
						point = rs.getInt(1);
						grade = rs.getInt(2);
						System.out.println("현재 포인트(point)=>"+point+", 등급(grade)=>"+grade);
						
						String sql5="";
						if (point < 100) {
							sql5="update login set log_grade=1 where mem_id=?";
						}else if (point >= 100 && point < 500) {
							sql5="update login set log_grade=2 where mem_id=?";
						}else if (point >= 500 && point < 2000) {
							sql5="update login set log_grade=3 where mem_id=?";
						}else if (point >= 2000 && point < 10000) {
							sql5="update login set log_grade=4 where mem_id=?";
						}else if (point >= 10000) {
							sql5="update login set log_grade=5 where mem_id=?";
						}
						System.out.println("sql5=>"+sql5);
						pstmt=con.prepareStatement(sql5);
						pstmt.setString(1, comment.getMem_id());
							
						int gradeup=pstmt.executeUpdate();
						con.commit();
						System.out.println("댓글 작성 후 등급 변동여부(gradeup)=>"+gradeup);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("insertComment()메서드 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
	}

	// 댓글 수정

	// 댓글 삭제->테이블에서 삭제하는 게 아니라 '삭제된 댓글입니다' 로 변경@@@
	public int deleteComment(int g_cnumber) {

		int x = -1;

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			con.commit();

			sql = "delete from g_comment where g_cnumber=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, g_cnumber);

			int delete = pstmt.executeUpdate();
			con.commit();
			System.out.println("게시판 글삭제 성공여부=>" + delete + " //(1성공 0실패)");
			x = 1;

		} catch (Exception e) {
			System.out.println("deleteComment() 에러발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return x;
	}

	// 글삭제->삭제된 댓글입니다
	public int upDeleteComment(int g_cnumber, int g_cref, int g_cre_level) {

		int x = -1; // 게시물 수정여무 1성공 0실패

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			con.commit();
			
			String sql1="select * from g_comment where g_cref=? and g_cre_level>?";
			pstmt=con.prepareStatement(sql1);
			pstmt.setInt(1, g_cref);
			pstmt.setInt(2, g_cre_level);
			rs=pstmt.executeQuery();
			
			if(rs.next()) { //대댓글들이 있는 댓글이라면
				sql = "update g_comment set g_cnickname=' ', g_cbody='삭제된 댓글입니다.&nbsp;' where g_cnumber=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, g_cnumber);
			}else {
				sql = "delete from g_comment where g_cnumber=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, g_cnumber);
			}
			
			int update = pstmt.executeUpdate();
			con.commit();
			System.out.println("게시판 글삭제(수정) 성공 여부(update)=>" + update + " //(1성공 0실패)");
			x = 1; // 수정성공
		} catch (Exception e) {
			System.out.println("upDeleteComment() 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return x;
	}

	// 댓글 수
	public int getCommentCount(int g_number) { // getmemberCount()->MemberDAO에서 작성
		int x = 0;
		try {
			con = pool.getConnection();
			System.out.println("con=>" + con);
			sql = "select count(*) from g_comment where g_number=? and g_cbody!='삭제된 댓글입니다.&nbsp;'";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, g_number);
			rs = pstmt.executeQuery();
			if (rs.next()) { // 보여주는 결과가 있다면
				x = rs.getInt(1); // 변수명=rs.get자료형(필드명 또는 인덱스번호)=>여기서는 필드명X(필드명이 없기 때문)
			}
		} catch (Exception e) {
			System.out.println("getCommentCount() 에러발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return x;
	}

}
