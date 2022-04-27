package beans;

import java.sql.*;
import java.util.*;

public class MemberDAO {

	//멤버변수에 연결할 클래스의 객체 선언
	private DBConnectionMgr pool=null;
	
	//공통으로 접속할 경우 필요로 하는 멤버변수 선언
	private Connection con=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
	private String sql="";
	
	//생성자를 통해서 자동으로 객체를 얻어올 수 있도록 연결
	public MemberDAO() {
		try {
			pool=DBConnectionMgr.getInstance();
			System.out.println("pool=>"+pool);
		}catch (Exception e) {
			System.out.println("DB연결 실패=>"+e);
		}
	}
	
	//메서드 작성
	
	//1) 회원로그인
	public boolean loginCheck(String id, String passwd) {
		boolean check=false;
		try {
			con=pool.getConnection();
			System.out.println("con=>"+con);
			sql="select mem_id, log_passwd from login where mem_id=? and log_passwd=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, passwd);
			rs=pstmt.executeQuery();
			check=rs.next();
		}catch (Exception e) {
			System.out.println("loginCheck() 에러발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return check;
	}

	// 1-2) 닉네임, 포인트, 등급 세션처리 위한 메서드
	public MemberDTO getNPG(String mem_id) {
		MemberDTO npg = null;
		try {
			con = pool.getConnection();
			sql = "select log_nickname, log_point, log_grade from login where mem_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				npg = new MemberDTO();
				npg.setMem_nickname(rs.getString("log_nickname"));
				npg.setMem_point(rs.getInt("log_point"));
				npg.setMem_grade(rs.getInt("log_grade"));
			}
			System.out.println("log_nickname=>" + npg.getMem_nickname());
			System.out.println("log_point=>" + npg.getMem_point());
			System.out.println("log_grade=>" + npg.getMem_grade());
		} catch (Exception e) {
			System.out.println("getNPG 에러발생=>" + e);
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return npg;
	}

	
	//2) id 중복체크
	public boolean checkId(String id) {
		boolean check=false;
		try {
			con=pool.getConnection();
			sql="select mem_id from member where mem_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			check=rs.next();
		}catch (Exception e) {
			System.out.println("checkId() 에러발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return check;
	}
	
	//2-1) 닉네임 중복체크
	public boolean checkNickname(String nickname) {
		boolean check=false;
		
		try {
			con=pool.getConnection();
			sql="select mem_nickname from member where mem_nickname=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			rs=pstmt.executeQuery();
			check=rs.next();
		}catch (Exception e) {
			System.out.println("checkNickname() 에러발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return check;
	}
	
	
	//3) 우편번호
	
	
	//4) 회원가입
	public boolean memberInsert(MemberDTO mem) {
		boolean check=false;
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			sql="insert into member values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem.getMem_id());
			pstmt.setString(2, mem.getMem_passwd());
			pstmt.setString(3, mem.getMem_name());
			pstmt.setString(4, mem.getMem_nickname());
			pstmt.setString(5, mem.getMem_birth());
			pstmt.setString(6, mem.getMem_tel());
			pstmt.setString(7, mem.getMem_email());
			pstmt.setString(8, mem.getMem_addr());
			pstmt.setString(9, mem.getMem_genre());
			pstmt.setInt(10, mem.getMem_point());
			pstmt.setInt(11, mem.getMem_grade());
			
			int insert1=pstmt.executeUpdate();
			con.commit();
			System.out.println("insert1(멤버 테이블 데이터 입력유무)=>"+insert1);
			if (insert1 > 0) {
				check=true;
			}
		}catch (Exception e) {
			System.out.println("memberInsert() 에러발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt);
		}
		
		//로그인 테이블에 insert
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			sql="insert into login values(?, ?, ?, ?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem.getMem_id());
			pstmt.setString(2, mem.getMem_passwd());
			pstmt.setString(3, mem.getMem_nickname());
			pstmt.setInt(4, mem.getMem_point());
			pstmt.setInt(5, mem.getMem_grade());
			
			int insert2=pstmt.executeUpdate();
			con.commit();
			System.out.println("insert2(로그인 테이블 데이터 입력유무)=>"+insert2);
			if (insert2 > 0) {
				check=true;
			}
		}catch (Exception e) {
			System.out.println("loginInsert() 에러발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt);
		}
		
		return check;
	}
	
	//5) 회원수정->특정 회원 찾기
	public MemberDTO getMember(String mem_id) {
		MemberDTO mem=null; //id값에 해당되는 레코드 1개를 저장
		try {
			con=pool.getConnection();
			sql="select * from member where mem_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_id); //책의 방법, sql구문 확인불가
			rs=pstmt.executeQuery();
			//찾은 레코드가 1개->if(rs.next())
			if (rs.next()) {
				//찾은 값->Setter Method의 매개변수로 저장=>웹에 출력=>Getter Method
				mem=new MemberDTO();
				mem.setMem_id(rs.getString("mem_id")); //<%=mem.getMem_id()%>
				mem.setMem_passwd(rs.getString("mem_passwd"));
				mem.setMem_name(rs.getString("mem_name"));
				mem.setMem_nickname(rs.getString("mem_nickname"));
				mem.setMem_birth(rs.getString("mem_birth"));
				mem.setMem_tel(rs.getString("mem_tel"));
				mem.setMem_email(rs.getString("mem_email"));
				mem.setMem_addr(rs.getString("mem_addr"));
				mem.setMem_genre(rs.getString("mem_genre"));
				mem.setMem_point(rs.getInt("mem_point"));
				mem.setMem_grade(rs.getInt("mem_grade"));
			}
		}catch (Exception e) {
			System.out.println("getMember 에러발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return mem;
	}
	
	
	
	
	
	//6) 찾은 회원을 수정=>회원가입해주는 메서드와 동일(sql구문이 다르다)
	public boolean memberUpdate(MemberDTO mem) {
		boolean check=false;// 회원수정 성공유무
		
		System.out.println("==memberUpdate() 내부==");
		System.out.println("mem.getMem_id()=>"+mem.getMem_id());
		//-------------------------------------------------------
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			//-----------------------
			sql="update member set mem_passwd=?, mem_name=?, mem_nickname=?, mem_birth=?, mem_tel=?, mem_email=?, mem_addr=?, mem_genre=?, mem_point=?, mem_grade=? where mem_id=?";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setString(1, mem.getMem_passwd());
			pstmt.setString(2, mem.getMem_name());
			pstmt.setString(3, mem.getMem_nickname());
			pstmt.setString(4, mem.getMem_birth());
			pstmt.setString(5, mem.getMem_tel());
			pstmt.setString(6, mem.getMem_email());
			pstmt.setString(7, mem.getMem_addr());
			pstmt.setString(8, mem.getMem_genre());
			pstmt.setInt(9, mem.getMem_point());
			pstmt.setInt(10, mem.getMem_grade());
			pstmt.setString(11, mem.getMem_id());
			
			int update1=pstmt.executeUpdate(); //반환값 1(성공), 0(실패)
			con.commit();
			System.out.println("update1(멤버 테이블 데이터 성공유무)=>"+update1);
			if (update1 == 1) {
				check=true;
			}
		}catch (Exception e) {
			System.out.println("memberUpdate() 에러발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt);
		}
		
		if (check==true) {
		//login테이블 update
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			//-----------------------
			sql="update login set log_passwd=?, log_nickname=?, log_point=?, log_grade=? where mem_id=?";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setString(1, mem.getMem_passwd());
			pstmt.setString(2, mem.getMem_nickname());
			pstmt.setInt(3, mem.getMem_point());
			pstmt.setInt(4, mem.getMem_grade());
			pstmt.setString(5, mem.getMem_id());
			
			
			int update2=pstmt.executeUpdate(); //반환값 1(성공), 0(실패)
			con.commit();
			System.out.println("update2(로그인 테이블 데이터 성공유무)=>"+update2);
			if (update2 == 1) {
				check=true;
			}
		}catch (Exception e) {
			System.out.println("loginUpdate() 에러발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt);
		}
		}
		
		return check; //memberUpdate.jsp에서 메서드의 반환값
	}
	
	//7) 회원탈퇴
	
	public int beforeMemberDelete(String id) {
		int x=-1;
		
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			
			String sqlh="update h_table set mem_id='dropped' where mem_id=?";
			pstmt=con.prepareStatement(sqlh);
			pstmt.setString(1, id);
			int deleteh=pstmt.executeUpdate();
			System.out.println("탈퇴시 h_table 아이디 처리 성공 여부=>"+deleteh);
			con.commit();
			
			String sqlhc="update h_comment set mem_id='dropped' where mem_id=?";
			pstmt=con.prepareStatement(sqlhc);
			pstmt.setString(1, id);
			int deletehc=pstmt.executeUpdate();
			System.out.println("탈퇴시 h_comment 아이디 처리 성공 여부=>"+deletehc);
			con.commit();
			
			
			String sqlz="update z_table set mem_id='dropped' where mem_id=?";
			pstmt=con.prepareStatement(sqlz);
			pstmt.setString(1, id);
			int deletez=pstmt.executeUpdate();
			System.out.println("탈퇴시 z_table 아이디 처리 성공 여부=>"+deletez);
			con.commit();
			
			String sqlzc="update z_comment set mem_id='dropped' where mem_id=?";
			pstmt=con.prepareStatement(sqlzc);
			pstmt.setString(1, id);
			int deletezc=pstmt.executeUpdate();
			System.out.println("탈퇴시 z_comment 아이디 처리 성공 여부=>"+deletezc);
			con.commit();
			
			
			String sqlm="update m_table set mem_id='dropped' where mem_id=?";
			pstmt=con.prepareStatement(sqlm);
			pstmt.setString(1, id);
			int deletem=pstmt.executeUpdate();
			System.out.println("탈퇴시 m_table 아이디 처리 성공 여부=>"+deletem);
			con.commit();
			
			String sqlmc="update m_comment set mem_id='dropped' where mem_id=?";
			pstmt=con.prepareStatement(sqlmc);
			pstmt.setString(1, id);
			int deletemc=pstmt.executeUpdate();
			System.out.println("탈퇴시 m_comment 아이디 처리 성공 여부=>"+deletemc);
			con.commit();
			
			
			String sqlg="update g_table set mem_id='dropped' where mem_id=?";
			pstmt=con.prepareStatement(sqlg);
			pstmt.setString(1, id);
			int deleteg=pstmt.executeUpdate();
			System.out.println("탈퇴시 g_table 아이디 처리 성공 여부=>"+deleteg);
			con.commit();
			
			String sqlgc="update g_comment set mem_id='dropped' where mem_id=?";
			pstmt=con.prepareStatement(sqlgc);
			pstmt.setString(1, id);
			int deletegc=pstmt.executeUpdate();
			System.out.println("탈퇴시 g_comment 아이디 처리 성공 여부=>"+deletegc);
			con.commit();
			
			
			String sqlr="update r_table set mem_id='dropped' where mem_id=?";
			pstmt=con.prepareStatement(sqlr);
			pstmt.setString(1, id);
			int deleter=pstmt.executeUpdate();
			System.out.println("탈퇴시 r_table 아이디 처리 성공 여부=>"+deleter);
			con.commit();
			
			String sqlrc="update r_comment set mem_id='dropped' where mem_id=?";
			pstmt=con.prepareStatement(sqlrc);
			pstmt.setString(1, id);
			int deleterc=pstmt.executeUpdate();
			System.out.println("탈퇴시 r_comment 아이디 처리 성공 여부=>"+deleterc);
			con.commit();
			
			//스크랩테이블에서는 삭제
			String sqls="delete from scrap where mem_id=?";
			pstmt=con.prepareStatement(sqls);
			pstmt.setString(1, id);
			int deletes=pstmt.executeUpdate();
			System.out.println("탈퇴시 scrap 삭제 성공 여부=>"+deletes);
			con.commit();
			
			//별점테이블에서도 삭제
			String sqlstar="delete from star where mem_id=?";
			pstmt=con.prepareStatement(sqlstar);
			pstmt.setString(1, id);
			int deletestar=pstmt.executeUpdate();
			System.out.println("탈퇴시 star 삭제 성공 여부=>"+deletestar);
			con.commit();
			
			x=1;
		}catch (Exception e) {
			System.out.println("beforeMemberDelete() 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt);
		}
		
		return x;
	}
	
	
	
	//select passwd from chmember where id='admin';
	//delete from chmember where id='admin'; //id 매개변수 받아야 함
	public int memberDelete(String id, String passwd) {
		String dbpasswd=""; //DB상에서 찾은 암호를 저장할 변수
		int x=-1; //회원탈퇴유무
		
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			sql="select log_passwd from login where mem_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			//암호를 찾았다면
			if (rs.next()) {
				dbpasswd=rs.getString("log_passwd");
				System.out.println("dbpasswd=>"+dbpasswd);
				
				if (dbpasswd.equals(passwd)) {
					//먼저 기존 글, 댓글들 아이디 dropped로 바꾸기
					int beforeDelete=this.beforeMemberDelete(id);
					con.commit();
					System.out.println("beforeDelete=>"+beforeDelete);
					
					if (beforeDelete > 0) {
						pstmt=con.prepareStatement("delete from login where mem_id=?");
						pstmt.setString(1, id);
						int delete=pstmt.executeUpdate();
						System.out.println("delete(회원탈퇴 성공유무)=>"+delete);
						con.commit();
						x=1; //회원탈퇴 성공
					}
					
				}else {
					x=0; //회원탈퇴 실패
				}
			}else { //암호가 존재하지 않는 경우
				x=-1;
			}
		}catch (Exception e) {
			System.out.println("memberDelete() 에러발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return x;
	}
	
	//포인트, 등급 동기화
	public void syncPG(String mem_id) {
		
		int log_point, log_grade;
		
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			sql="select log_point, log_grade from login where mem_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				log_point=rs.getInt(1);
				log_grade=rs.getInt(2);
				con.commit();
				
				sql="update member set mem_point=?, mem_grade=? where mem_id=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, log_point);
				pstmt.setInt(2, log_grade);
				pstmt.setString(3, mem_id);
				
				int sync=pstmt.executeUpdate();
				con.commit();
				System.out.println("포인트, 등급 동기화 성공 여부(sync)=>"+sync+" //(1성공 0실패)");
			}
		}catch (Exception e) {
			
		}finally {
			
		}
		
	}
	
	
	//회원리스트
	
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
	
	//회원 수
	public int getMemberCount(String search, String searchtext) {
		
		int x=0;
		
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			if (search==null || search == "") {
				sql="select count(*) from login";
			}else {
				sql="select count(*) from login where " + search + " like '%" + searchtext + "%' ";
			}
			System.out.println("getMemberCount() 검색어=>"+sql);
			con.commit();
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				x=rs.getInt(1);
				con.commit();
				System.out.println("검색된 회원 수=>"+x);
			}
			con.commit();
		}catch (Exception e) {
			System.out.println("getMemberCount() 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return x;
	}
	
	//회원 리스트-등급순
	public List<MemberDTO> getMembers(int start, int end, String search, String searchtext) {
		
		List<MemberDTO> memberList=null;
		
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			if (search==null || search=="") {
				sql="select * from (select login.*, rownum as rn from (select * from login order by log_grade desc, log_point desc) login) where rn >=? and rn <=?";
			}else {
				sql="select * from (select login.*, rownum as rn from (select * from login where " + search + " like '%" + searchtext + "%' order by log_grade desc, log_point desc) login) where rn >=? and rn <=?";
			}
			System.out.println("getMembers() 검색어=>"+sql);
			
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				memberList=new ArrayList(end);
				con.commit();
				do {
					MemberDTO member=new MemberDTO();
					member.setMem_id(rs.getString("mem_id"));
					member.setLog_passwd(rs.getString("log_passwd"));
					member.setLog_nickname(rs.getString("log_nickname"));
					member.setLog_point(rs.getInt("log_point"));
					member.setLog_grade(rs.getInt("log_grade"));
					memberList.add(member);
					con.commit();
				}while (rs.next());
			}
			con.commit();
		}catch (Exception e) {
			System.out.println("getMembers() 에러 발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return memberList;
	}
	
	//회원강제탈퇴
	public int memberDrop(String mem_id) {
		int x=-1; //회원탈퇴유무
		
		try {
			con=pool.getConnection();
			con.setAutoCommit(false);
			
			//먼저 기존 글, 댓글들 아이디 dropped로 바꾸기
			int beforeDelete=this.beforeMemberDelete(mem_id);
			con.commit();
			System.out.println("beforeDelete=>"+beforeDelete);
			if (beforeDelete > 0) {
				sql="delete from login where mem_id=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, mem_id);
				int delete=pstmt.executeUpdate();
				System.out.println("delete(회원 강퇴 성공 여부)=>"+delete);
				con.commit();
				x=1; //회원탈퇴 성공
			}
		}catch (Exception e) {
			System.out.println("memberDelete() 에러발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return x;
	}
	
	
	
}
