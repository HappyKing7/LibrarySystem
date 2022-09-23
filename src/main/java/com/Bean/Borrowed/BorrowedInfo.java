package com.Bean.Borrowed;

public class BorrowedInfo {
	private String BORROWED_ID;
	private String USER_ID;
	private String BOOK_ID;
	private String BORROWED_START_DATE;
	private String BORROWED_END_DATE;
	private String BORROWED_STATUS;
	private Integer BORROWED_NUMBER;

	public Integer getBORROWED_NUMBER() {
		return BORROWED_NUMBER;
	}

	public void setBORROWED_NUMBER(Integer BORROWED_NUMBER) {
		this.BORROWED_NUMBER = BORROWED_NUMBER;
	}

	public String getBORROWED_ID() {
		return BORROWED_ID;
	}

	public void setBORROWED_ID(String BORROWED_ID) {
		this.BORROWED_ID = BORROWED_ID;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String USER_ID) {
		this.USER_ID = USER_ID;
	}

	public String getBOOK_ID() {
		return BOOK_ID;
	}

	public void setBOOK_ID(String BOOK_ID) {
		this.BOOK_ID = BOOK_ID;
	}

	public String getBORROWED_START_DATE() {
		return BORROWED_START_DATE;
	}

	public void setBORROWED_START_DATE(String BORROWED_START_DATE) {
		this.BORROWED_START_DATE = BORROWED_START_DATE;
	}

	public String getBORROWED_END_DATE() {
		return BORROWED_END_DATE;
	}

	public void setBORROWED_END_DATE(String BORROWED_END_DATE) {
		this.BORROWED_END_DATE = BORROWED_END_DATE;
	}

	public String getBORROWED_STATUS() {
		return BORROWED_STATUS;
	}

	public void setBORROWED_STATUS(String BORROWED_STATUS) {
		this.BORROWED_STATUS = BORROWED_STATUS;
	}
}
