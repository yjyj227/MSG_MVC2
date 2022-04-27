package beans;

import java.sql.Timestamp;

public class RBoardDTO {

	private int r_number;
	private String mem_id;
	private String r_nickname;
	private String r_title;
	private String r_body;
	private Timestamp r_date;
	private int r_count;
	private int r_scrap;
	private int r_category; //글카테고리->11:해리포터1, 12:해리포터2, 13:해리포터3,,,
	private int r_ref; //게시판분류->1:자유, 2:정보, 3:별점, 4:hot
	
	private int r_comments; //댓글 수 담을

	public int getR_number() {
		return r_number;
	}

	public void setR_number(int r_number) {
		this.r_number = r_number;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getR_nickname() {
		return r_nickname;
	}

	public void setR_nickname(String r_nickname) {
		this.r_nickname = r_nickname;
	}

	public String getR_title() {
		return r_title;
	}

	public void setR_title(String r_title) {
		this.r_title = r_title;
	}

	public String getR_body() {
		return r_body;
	}

	public void setR_body(String r_body) {
		this.r_body = r_body;
	}

	public Timestamp getR_date() {
		return r_date;
	}

	public void setR_date(Timestamp r_date) {
		this.r_date = r_date;
	}

	public int getR_count() {
		return r_count;
	}

	public void setR_count(int r_count) {
		this.r_count = r_count;
	}

	public int getR_scrap() {
		return r_scrap;
	}

	public void setR_scrap(int r_scrap) {
		this.r_scrap = r_scrap;
	}

	public int getR_category() {
		return r_category;
	}

	public void setR_category(int r_category) {
		this.r_category = r_category;
	}

	public int getR_ref() {
		return r_ref;
	}

	public void setR_ref(int r_ref) {
		this.r_ref = r_ref;
	}

	public int getR_comments() {
		return r_comments;
	}

	public void setR_comments(int r_comments) {
		this.r_comments = r_comments;
	}

	
	
	
}
