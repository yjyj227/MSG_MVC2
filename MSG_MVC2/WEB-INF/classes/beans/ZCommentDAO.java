package beans;

import java.sql.*;
import java.util.*;

public class ZCommentDAO {

	private DBConnectionMgr pool = null; // 1. 연결객체선언
	// 공통
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";

	public ZCommentDAO() {

		try {
			pool = DBConnectionMgr.getInstance();
			System.out.println("pool=>" + pool);
		} catch (Exception e) {
			System.out.println("DB접속 오류=>" + e);
		}
	}

	private ZCommentDTO makeArticleFromResult() throws Exception {

		ZCommentDTO article = new ZCommentDTO();

		article.setZ_cnumber(rs.getInt("z_cnumber"));
		article.setMem_id(rs.getString("mem_id"));
		article.setZ_number(rs.getInt("z_number"));
		article.setZ_cbody(rs.getString("z_cbody"));
		article.setZ_cnickname(rs.getString("z_cnickname"));
		article.setZ_cdate(rs.getTimestamp("z_cdate"));
		article.setZ_cref(rs.getInt("z_cref"));
		article.setZ_cre_step(rs.getInt("z_cre_step"));
		article.setZ_cre_level(rs.getInt("z_cre_level"));

		return article;
	}

	public List<ZCommentDTO> getComment(int z_number) {

		List<ZCommentDTO> commentList = new ArrayList<ZCommentDTO>();

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			System.out.println("con=>" + con);
			sql = "select * from z_comment where z_number=? order by z_cref asc, z_cre_step asc, z_cdate asc";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, z_number);
			con.commit();
			rs = pstmt.executeQuery();

			if (rs.next()) {
				do {
					ZCommentDTO comment = new ZCommentDTO();
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

	/*
	 * public void insertComment(ZCommentDTO comment) {
	 * 
	 * int z_cnumber = comment.getZ_cnumber(); int result = 0; int number = 0;
	 * String sql1 = "select max(z_cnumber) from z_comment"; sql =
	 * "insert into z_comment values(?, ?, ?, ?, ?, ?, ?, ?, ?)"; String sql2 =
	 * "update z_comment set z_cre_step=z_cre_step+1 where z_cref=? and z_cre_step>?"
	 * ; try { con = pool.getConnection(); con.setAutoCommit(false);
	 * System.out.println("con=>" + con);
	 * 
	 * if (z_cnumber != 0) { pstmt = con.prepareStatement(sql2); pstmt.setInt(1,
	 * comment.getZ_cref()); pstmt.setInt(2, comment.getZ_cre_step());
	 * pstmt.executeUpdate(); pstmt.close();
	 * comment.setZ_cre_step(comment.getZ_cre_step() + 1);
	 * comment.setZ_cre_level(comment.getZ_cre_level() + 1); con.commit(); }
	 * 
	 * pstmt = con.prepareStatement(sql1); rs = pstmt.executeQuery(); con.commit();
	 * if (rs.next()) { number = rs.getInt(1); con.commit(); } rs.close();
	 * pstmt.close();
	 * 
	 * if (z_cnumber==0) { comment.setZ_cref(number); con.commit(); }
	 * 
	 * pstmt=con.prepareStatement(sql); pstmt.setInt(1, number); pstmt.setString(2,
	 * comment.getMem_id()); pstmt.setInt(3, comment.getZ_number());
	 * pstmt.setString(4, comment.getZ_cbody()); pstmt.setString(5,
	 * comment.getZ_cnickname()); pstmt.setTimestamp(6, comment.getZ_cdate());
	 * pstmt.setInt(7, comment.getZ_cref()); pstmt.setInt(8,
	 * comment.getZ_cre_step()); pstmt.setInt(9, comment.getZ_cre_level());
	 * result=pstmt.executeUpdate(); con.commit();
	 * System.out.println("댓글쓰기 성공여부(result)=>"+result); } catch (Exception e) {
	 * System.out.println("insertComment() 에러 발생=>"+e); } finally {
	 * pool.freeConnection(con, pstmt, rs); }
	 * 
	 * }
	 */

	// 게시판의 글쓰기 및 답변글 쓰기

	public void insertComment(ZCommentDTO comment) {
		// 1. article->신규글인지 답변글(기존 게시물 번호)인지 확인
		int z_cnumber = comment.getZ_cnumber();
		int z_cref = comment.getZ_cref();
		int z_cre_step = comment.getZ_cre_step();
		int z_cre_level = comment.getZ_cre_level();

		int number = 0; // 데이터를 저장하기 위한 게시물 번호(=new)
		System.out.println("insertComment()메서드 내부 z_cnumber=>" + z_cnumber);
		System.out.println("z_cref=>" + z_cref + ", z_cre_step=>" + z_cre_step + ", z_cre_level=>" + z_cre_level);
		
		int point, grade;

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			sql = "select max(z_cnumber) from z_comment";
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
			if (z_cnumber != 0) {
				// 같은 그룹번호를 가지고 있으면서(ref=?) 나보다 step값이 큰 게시물(re_step>?)을 찾아서 그 step 하나 증가
				// @@----->증가시켜놓고 자기가 1이 돼서 문제가 생김

				System.out.println("update 전의 z_cre_step=>" + z_cre_step);

				// z_cref가 같은 댓글들 중 step값이 제일 높은 걸 찾아서 그것보다 1 크게 만들기 위해
				String sql2 = "select max(z_cre_step) from z_comment where z_cref=?";
				pstmt = con.prepareStatement(sql2);
				pstmt.setInt(1, z_cref);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					z_cre_step = rs.getInt(1) + 1;
					System.out.println("z_cre_step의 최댓값+1=>" + z_cre_step);
				} else {
					z_cre_step = z_cre_step + 1;
				}
				z_cre_level = z_cre_level + 1;
				System.out.println("update 후의 z_cre_step=>" + z_cre_step + ", z_cre_level=>" + z_cre_level);
				con.commit();

				/*
				 * sql="update z_comment set z_cre_step=z_cre_step+1 where z_cref=? and z_cre_step>?"
				 * ;
				 * 
				 * System.out.println("insertComment()의 sql=>"+sql);
				 * pstmt=con.prepareStatement(sql); pstmt.setInt(1, z_cref); pstmt.setInt(2,
				 * z_cre_step); int update=pstmt.executeUpdate(); con.commit();
				 * System.out.println("댓글수정여부(update)=>"+update+" //(1성공 0실패)"); //1성공 0실패
				 * System.out.println("update 하고 나서의 z_cre_step()=>"+z_cre_step);
				 */
				// 답변글
				// z_cre_step=z_cre_step + 1;

			} else { // 신규글이라면 num=0
				z_cref = number;
				z_cre_step = 0;
				z_cre_level = 0;
				con.commit();
			}
			sql = "insert into z_comment(z_cnumber, mem_id, z_number, z_cbody, z_cnickname, z_cdate, ";
			sql += " z_cref, z_cre_step, z_cre_level) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, number); // article.getNum()이 아니었음
			pstmt.setString(2, comment.getMem_id());
			pstmt.setInt(3, comment.getZ_number());
			pstmt.setString(4, comment.getZ_cbody());
			pstmt.setString(5, comment.getZ_cnickname());
			pstmt.setTimestamp(6, comment.getZ_cdate());

			pstmt.setInt(7, z_cref);
			pstmt.setInt(8, z_cre_step);
			pstmt.setInt(9, z_cre_level);

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
	public int deleteComment(int z_cnumber) {

		int x = -1;

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			con.commit();

			sql = "delete from z_comment where z_cnumber=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, z_cnumber);

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
	public int upDeleteComment(int z_cnumber, int z_cref, int z_cre_level) {

		int x = -1; // 게시물 수정여무 1성공 0실패

		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			con.commit();
			
			String sql1="select * from z_comment where z_cref=? and z_cre_level>?";
			pstmt=con.prepareStatement(sql1);
			pstmt.setInt(1, z_cref);
			pstmt.setInt(2, z_cre_level);
			rs=pstmt.executeQuery();
			
			if(rs.next()) { //대댓글들이 있는 댓글이라면
				sql = "update z_comment set z_cnickname=' ', z_cbody='삭제된 댓글입니다.&nbsp;' where z_cnumber=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, z_cnumber);
			}else {
				sql = "delete from z_comment where z_cnumber=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, z_cnumber);
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
	public int getCommentCount(int z_number) { // getmemberCount()->MemberDAO에서 작성
		int x = 0;
		try {
			con = pool.getConnection();
			System.out.println("con=>" + con);
			sql = "select count(*) from z_comment where z_number=? and z_cbody!='삭제된 댓글입니다.&nbsp;'";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, z_number);
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
