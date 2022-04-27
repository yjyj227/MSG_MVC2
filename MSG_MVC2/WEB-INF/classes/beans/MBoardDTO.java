package beans;

import java.sql.Timestamp;

public class MBoardDTO {

	private int m_number;
	private String mem_id;
	private String m_nickname;
	private String m_title;
	private String m_body;
	private Timestamp m_date;
	private int m_count;
	private int m_scrap;
	private int m_category; //글카테고리->11:해리포터1, 12:해리포터2, 13:해리포터3,,,
	private int m_ref; //게시판분류->1:자유, 2:정보, 3:별점, 4:hot
	
	private int m_comments; //댓글 수 담을

	public int getM_number() {
		return m_number;
	}

	public void setM_number(int m_number) {
		this.m_number = m_number;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getM_nickname() {
		return m_nickname;
	}

	public void setM_nickname(String m_nickname) {
		this.m_nickname = m_nickname;
	}

	public String getM_title() {
		return m_title;
	}

	public void setM_title(String m_title) {
		this.m_title = m_title;
	}

	public String getM_body() {
		return m_body;
	}

	public void setM_body(String m_body) {
		this.m_body = m_body;
	}

	public Timestamp getM_date() {
		return m_date;
	}

	public void setM_date(Timestamp m_date) {
		this.m_date = m_date;
	}

	public int getM_count() {
		return m_count;
	}

	public void setM_count(int m_count) {
		this.m_count = m_count;
	}

	public int getM_scrap() {
		return m_scrap;
	}

	public void setM_scrap(int m_scrap) {
		this.m_scrap = m_scrap;
	}

	public int getM_category() {
		return m_category;
	}

	public void setM_category(int m_category) {
		this.m_category = m_category;
	}

	public int getM_ref() {
		return m_ref;
	}

	public void setM_ref(int m_ref) {
		this.m_ref = m_ref;
	}

	public int getM_comments() {
		return m_comments;
	}

	public void setM_comments(int m_comments) {
		this.m_comments = m_comments;
	}

	
	
}
