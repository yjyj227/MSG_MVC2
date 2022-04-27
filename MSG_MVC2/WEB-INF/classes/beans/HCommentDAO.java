package beans;

import java.sql.*;
import java.util.*;

public class HCommentDAO {

	private DBConnectionMgr pool = null; // 1. 연결객체선언
	// 공통
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";

	public HCommentDAO() {

		try {
			pool = DBConnectionMgr.getInstance();
			System.out.println("pool=>" + pool);
		} catch (Exception e) {
			System.out.println("DB접속 오류=>" + e);
		}
	}

	
	private HCommentDTO makeArticleFromResult() throws Exception {

		HCommentDTO article = new HCommentDTO();

		article.setH_cnumber(rs.getInt("h_cnumber"));
		article.setMem_id(rs.getString("mem_id"));
		article.setH_number(rs.getInt("h_number"));
		article.setH_cbody(rs.getString("h_cbody"));
		article.setH_cnickname(rs.getString("h_cnickname"));
		article.setH_cdate(rs.getTimestamp("h_cdate"));
		article.setH_cref(rs.getInt("h_cref"));
		article.setH_cre_step(rs.getInt("h_cre_step"));
		article.setH_cre_level(rs.getInt("h_cre_level"));

		return article;
	}

	public List<HCommentDTO> getComment(int h_number) {

		List<HCommentDTO> commentList = new ArrayList<HCommentDTO>();

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			System.out.println("con=>" + con);
			sql = "select * from h_comment where h_number=? order by h_cref asc, h_cre_step asc, h_cdate asc";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, h_number);
			con.commit();
			rs = pstmt.executeQuery();

			if (rs.next()) {
				do {
					HCommentDTO comment = new HCommentDTO();
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

	//댓글 수 담기
	public List<HCommentDTO> getCommentCountForList(int start, int end) {

		List<HCommentDTO> commentCountList = new ArrayList<HCommentDTO>();
		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			System.out.println("con=>" + con);
			sql = "select count(*) from (select h_comment.*, rownum as rn from (select * from h_comment) h_comment) where rn >= ? and rn <= ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			con.commit();
			rs = pstmt.executeQuery();

			if (rs.next()) {
				commentCountList=new ArrayList(end);
				do {
					HCommentDTO comment = new HCommentDTO();
					comment.setH_commentcount(rs.getInt(1));
					commentCountList.add(comment);
					con.commit();
				} while (rs.next());
			}
			con.commit();
		} catch (Exception e) {
			System.out.println("getCommentCountForList() 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return commentCountList;
	}
	
	/*
	 * public void insertComment(HCommentDTO comment) {
	 * 
	 * int h_cnumber = comment.getH_cnumber(); int result = 0; int number = 0;
	 * String sql1 = "select max(h_cnumber) from h_comment"; sql =
	 * "insert into h_comment values(?, ?, ?, ?, ?, ?, ?, ?, ?)"; String sql2 =
	 * "update h_comment set h_cre_step=h_cre_step+1 where h_cref=? and h_cre_step>?"
	 * ; try { con = pool.getConnection(); con.setAutoCommit(false);
	 * System.out.println("con=>" + con);
	 * 
	 * if (h_cnumber != 0) { pstmt = con.prepareStatement(sql2); pstmt.setInt(1,
	 * comment.getH_cref()); pstmt.setInt(2, comment.getH_cre_step());
	 * pstmt.executeUpdate(); pstmt.close();
	 * comment.setH_cre_step(comment.getH_cre_step() + 1);
	 * comment.setH_cre_level(comment.getH_cre_level() + 1); con.commit(); }
	 * 
	 * pstmt = con.prepareStatement(sql1); rs = pstmt.executeQuery(); con.commit();
	 * if (rs.next()) { number = rs.getInt(1); con.commit(); } rs.close();
	 * pstmt.close();
	 * 
	 * if (h_cnumber==0) { comment.setH_cref(number); con.commit(); }
	 * 
	 * pstmt=con.prepareStatement(sql); pstmt.setInt(1, number); pstmt.setString(2,
	 * comment.getMem_id()); pstmt.setInt(3, comment.getH_number());
	 * pstmt.setString(4, comment.getH_cbody()); pstmt.setString(5,
	 * comment.getH_cnickname()); pstmt.setTimestamp(6, comment.getH_cdate());
	 * pstmt.setInt(7, comment.getH_cref()); pstmt.setInt(8,
	 * comment.getH_cre_step()); pstmt.setInt(9, comment.getH_cre_level());
	 * result=pstmt.executeUpdate(); con.commit();
	 * System.out.println("댓글쓰기 성공여부(result)=>"+result); } catch (Exception e) {
	 * System.out.println("insertComment() 에러 발생=>"+e); } finally {
	 * pool.freeConnection(con, pstmt, rs); }
	 * 
	 * }
	 */

	// 게시판의 글쓰기 및 답변글 쓰기

	public void insertComment(HCommentDTO comment) {
		// 1. article->신규글인지 답변글(기존 게시물 번호)인지 확인
		int h_cnumber = comment.getH_cnumber();
		int h_cref = comment.getH_cref();
		int h_cre_step = comment.getH_cre_step();
		int h_cre_level = comment.getH_cre_level();

		int number = 0; // 데이터를 저장하기 위한 게시물 번호(=new)
		System.out.println("insertComment()메서드 내부 h_cnumber=>" + h_cnumber);
		System.out.println("h_cref=>" + h_cref + ", h_cre_step=>" + h_cre_step + ", h_cre_level=>" + h_cre_level);

		int point, grade;
		
		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			sql = "select max(h_cnumber) from h_comment";
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
			if (h_cnumber != 0) {
				// 같은 그룹번호를 가지고 있으면서(ref=?) 나보다 step값이 큰 게시물(re_step>?)을 찾아서 그 step 하나 증가
				// @@----->증가시켜놓고 자기가 1이 돼서 문제가 생김

				System.out.println("update 전의 h_cre_step=>" + h_cre_step);

				// h_cref가 같은 댓글들 중 step값이 제일 높은 걸 찾아서 그것보다 1 크게 만들기 위해
				String sql2 = "select max(h_cre_step) from h_comment where h_cref=?";
				pstmt = con.prepareStatement(sql2);
				pstmt.setInt(1, h_cref);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					h_cre_step = rs.getInt(1) + 1;
					System.out.println("h_cre_step의 최댓값+1=>" + h_cre_step);
				} else {
					h_cre_step = h_cre_step + 1;
				}
				h_cre_level = h_cre_level + 1;
				System.out.println("update 후의 h_cre_step=>" + h_cre_step + ", h_cre_level=>" + h_cre_level);
				con.commit();

				/*
				 * sql="update h_comment set h_cre_step=h_cre_step+1 where h_cref=? and h_cre_step>?"
				 * ;
				 * 
				 * System.out.println("insertComment()의 sql=>"+sql);
				 * pstmt=con.prepareStatement(sql); pstmt.setInt(1, h_cref); pstmt.setInt(2,
				 * h_cre_step); int update=pstmt.executeUpdate(); con.commit();
				 * System.out.println("댓글수정여부(update)=>"+update+" //(1성공 0실패)"); //1성공 0실패
				 * System.out.println("update 하고 나서의 h_cre_step()=>"+h_cre_step);
				 */
				// 답변글
				// h_cre_step=h_cre_step + 1;

			} else { // 신규글이라면 num=0
				h_cref = number;
				h_cre_step = 0;
				h_cre_level = 0;
				con.commit();
			}
			sql = "insert into h_comment(h_cnumber, mem_id, h_number, h_cbody, h_cnickname, h_cdate, ";
			sql += " h_cref, h_cre_step, h_cre_level) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, number); // article.getNum()이 아니었음
			pstmt.setString(2, comment.getMem_id());
			pstmt.setInt(3, comment.getH_number());
			pstmt.setString(4, comment.getH_cbody());
			pstmt.setString(5, comment.getH_cnickname());
			pstmt.setTimestamp(6, comment.getH_cdate());

			pstmt.setInt(7, h_cref);
			pstmt.setInt(8, h_cre_step);
			pstmt.setInt(9, h_cre_level);

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
	public int deleteComment(int h_cnumber, String mem_id) {

		int x = -1;
		int point, grade;
		
		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			con.commit();

			sql = "delete from h_comment where h_cnumber=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, h_cnumber);

			int delete = pstmt.executeUpdate();
			con.commit();
			System.out.println("댓글 삭제 성공여부=>" + delete + " //(1성공 0실패)");
			
			if (delete > 0) {
				String sql4="update login set log_point=log_point-5 where mem_id=?";
				pstmt=con.prepareStatement(sql4);
				pstmt.setString(1, mem_id);
				
				int pointdown=pstmt.executeUpdate();
				con.commit();
				System.out.println("댓글 삭제시 포인트 회수여부(pointdown)=>"+pointdown);
				
				if (pointdown > 0) {
					String sql5 = "select log_point, log_grade from login where mem_id=?";
					pstmt = con.prepareStatement(sql5);
					pstmt.setString(1, mem_id);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						point = rs.getInt(1);
						grade = rs.getInt(2);
						System.out.println("현재 포인트(point)=>"+point+", 등급(grade)=>"+grade);
						
						String sql6="";
						if (point < 100) {
							sql4="update login set log_grade=1 where mem_id=?";
						}else if (point >= 100 && point < 500) {
							sql4="update login set log_grade=2 where mem_id=?";
						}else if (point >= 500 && point < 2000) {
							sql4="update login set log_grade=3 where mem_id=?";
						}else if (point >= 2000 && point < 10000) {
							sql4="update login set log_grade=4 where mem_id=?";
						}else if (point >= 10000) {
							sql4="update login set log_grade=5 where mem_id=?";
						}
						System.out.println("sql6=>"+sql6);
						pstmt=con.prepareStatement(sql6);
						pstmt.setString(1, mem_id);
							
						int gradeup=pstmt.executeUpdate();
						con.commit();
						System.out.println("댓글 삭제 후 등급 변동여부(gradeup)=>"+gradeup);
					}
				}
			}
			
			x = 1;

		} catch (Exception e) {
			System.out.println("deleteComment() 에러발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return x;
	}

	// 글삭제->삭제된 댓글입니다
	public int upDeleteComment(int h_cnumber, int h_cref, int h_cre_level, String h_cmem_id) {

		int x = -1; // 게시물 수정여무 1성공 0실패
		int point, grade;
		
		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			con.commit();
			
			String sql1="select * from h_comment where h_cref=? and h_cre_level>?";
			pstmt=con.prepareStatement(sql1);
			pstmt.setInt(1, h_cref);
			pstmt.setInt(2, h_cre_level);
			rs=pstmt.executeQuery();
			
			if(rs.next()) { //대댓글들이 있는 댓글이라면
				sql = "update h_comment set h_cnickname=' ', h_cbody='삭제된 댓글입니다.&nbsp;' where h_cnumber=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, h_cnumber);
			}else {
				sql = "delete from h_comment where h_cnumber=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, h_cnumber);
			}
			
			int update = pstmt.executeUpdate();
			con.commit();
			System.out.println("댓글삭제(수정) 성공 여부(update)=>" + update + " //(1성공 0실패)");
			
			if (update > 0) {
				String sql4="update login set log_point=log_point-5 where mem_id=?";
				pstmt=con.prepareStatement(sql4);
				pstmt.setString(1, h_cmem_id);
				
				int pointdown=pstmt.executeUpdate();
				con.commit();
				System.out.println("댓글 삭제시 포인트 회수여부(pointdown)=>"+pointdown);
				
				if (pointdown > 0) {
					String sql5 = "select log_point, log_grade from login where mem_id=?";
					pstmt = con.prepareStatement(sql5);
					pstmt.setString(1, h_cmem_id);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						point = rs.getInt(1);
						grade = rs.getInt(2);
						System.out.println("현재 포인트(point)=>"+point+", 등급(grade)=>"+grade);
						
						String sql6="";
						if (point < 100) {
							sql6="update login set log_grade=1 where mem_id=?";
						}else if (point >= 100 && point < 500) {
							sql6="update login set log_grade=2 where mem_id=?";
						}else if (point >= 500 && point < 2000) {
							sql6="update login set log_grade=3 where mem_id=?";
						}else if (point >= 2000 && point < 10000) {
							sql6="update login set log_grade=4 where mem_id=?";
						}else if (point >= 10000) {
							sql6="update login set log_grade=5 where mem_id=?";
						}
						System.out.println("sql6=>"+sql6);
						pstmt=con.prepareStatement(sql6);
						pstmt.setString(1, h_cmem_id);
							
						int gradeup=pstmt.executeUpdate();
						con.commit();
						System.out.println("댓글 삭제 후 등급 변동여부(gradeup)=>"+gradeup);
					}
				}
			}
			
			
			x = 1; // 수정성공
		} catch (Exception e) {
			System.out.println("upDeleteComment() 에러 발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return x;
	}

	// 댓글 수
	public int getCommentCount(int h_number) { // getmemberCount()->MemberDAO에서 작성
		int x = 0;
		try {
			con = pool.getConnection();
			System.out.println("con=>" + con);
			sql = "select count(*) from h_comment where h_number=? and h_cbody!='삭제된 댓글입니다.&nbsp;'";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, h_number);
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
