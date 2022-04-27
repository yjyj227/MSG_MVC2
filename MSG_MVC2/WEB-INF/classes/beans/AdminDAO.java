package beans;

import java.sql.*;
import java.util.*;

public class AdminDAO {

	//1) 멤버변수에 연결할 클래스의 객체 선언
	private DBConnectionMgr pool=null;
		
	//2. 공통으로 접속할 경우 필요로 하는 멤버변수 선언
	private Connection con=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
	private String sql="";
		
	//2) 생성자를 통해서 자동으로 객체를 얻어올 수 있도록 연결
	public AdminDAO() {
		try {
			pool=DBConnectionMgr.getInstance();
			System.out.println("pool=>"+pool);
		}catch (Exception e) {
			System.out.println("DB연결 실패=>"+e);
		}
	}
	
	
	//메서드
	
	
	//1) 관리자로그인
	//select id, passwd from member where id='nup' and passwd='1234';
	public boolean adminLoginCheck(String id, String passwd) {
		//1. DB연결
		boolean check=false;
		//2. SQL 실행->결과
		try {
			con=pool.getConnection();
			System.out.println("con=>"+con);
			sql="select admin_id, admin_passwd from admin where admin_id=? and admin_passwd=?";
			//pstmt객체 생략->NullPointerException 발생됨
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, passwd);
			rs=pstmt.executeQuery();
			check=rs.next(); //데이터O->true, x->false
		}catch (Exception e) {
			System.out.println("adminLoginCheck() 에러발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return check;
	}	
	
	
	//5-3) 관리자닉네임 찾기 위한 메서드
	public AdminDTO getAdminNickname(String mem_id) {
		AdminDTO nick=null; //id값에 해당되는 레코드 1개를 저장
		try {
			con=pool.getConnection();
			sql="select admin_nickname from admin where admin_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_id); //책의 방법, sql구문 확인불가
			rs=pstmt.executeQuery();
			//찾은 레코드가 1개->if(rs.next())
			if (rs.next()) {
				//찾은 값->Setter Method의 매개변수로 저장=>웹에 출력=>Getter Method
				nick=new AdminDTO();
				nick.setAdmin_nickname(rs.getString("admin_nickname"));
			}
			System.out.println("admin_nickname=>"+nick.getAdmin_nickname());
		}catch (Exception e) {
			System.out.println("getAdminNickname 에러발생=>"+e);
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return nick;
	}	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
