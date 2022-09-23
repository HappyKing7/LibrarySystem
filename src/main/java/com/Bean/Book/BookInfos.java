package com.Bean.Book;

public class BookInfos {
	private String BOOK_ID;
	private String BOOK_NAME;
	private String BOOK_TYPE_ID;
	private String BOOK_TYPE;
	private String AUTHOR_ID;
	private String AUTHOR_NAME;
	private String PRESS_ID;
	private String PRESS_NAME;

	public String getBOOK_TYPE() {
		return BOOK_TYPE;
	}

	public void setBOOK_TYPE(String BOOK_TYPE) {
		this.BOOK_TYPE = BOOK_TYPE;
	}

	public String getAUTHOR_NAME() {
		return AUTHOR_NAME;
	}

	public void setAUTHOR_NAME(String AUTHOR_NAME) {
		this.AUTHOR_NAME = AUTHOR_NAME;
	}

	public String getPRESS_NAME() {
		return PRESS_NAME;
	}

	public void setPRESS_NAME(String PRESS_NAME) {
		this.PRESS_NAME = PRESS_NAME;
	}

	private Integer BORROWED_NUMBER;
	private Integer REMAINING_NUMBER;
	private String BOOK_STATUS;

	public String getBOOK_ID() {
		return BOOK_ID;
	}

	public void setBOOK_ID(String BOOK_ID) {
		this.BOOK_ID = BOOK_ID;
	}

	public String getBOOK_NAME() {
		return BOOK_NAME;
	}

	public void setBOOK_NAME(String BOOK_NAME) {
		this.BOOK_NAME = BOOK_NAME;
	}

	public String getBOOK_TYPE_ID() {
		return BOOK_TYPE_ID;
	}

	public void setBOOK_TYPE_ID(String BOOK_TYPE_ID) {
		this.BOOK_TYPE_ID = BOOK_TYPE_ID;
	}

	public String getAUTHOR_ID() {
		return AUTHOR_ID;
	}

	public void setAUTHOR_ID(String AUTHOR_ID) {
		this.AUTHOR_ID = AUTHOR_ID;
	}

	public String getPRESS_ID() {
		return PRESS_ID;
	}

	public void setPRESS_ID(String PRESS_ID) {
		this.PRESS_ID = PRESS_ID;
	}

	public Integer getBORROWED_NUMBER() {
		return BORROWED_NUMBER;
	}

	public void setBORROWED_NUMBER(Integer BORROWED_NUMBER) {
		this.BORROWED_NUMBER = BORROWED_NUMBER;
	}

	public Integer getREMAINING_NUMBER() {
		return REMAINING_NUMBER;
	}

	public void setREMAINING_NUMBER(Integer REMAINING_NUMBER) {
		this.REMAINING_NUMBER = REMAINING_NUMBER;
	}

	public String getBOOK_STATUS() {
		return BOOK_STATUS;
	}

	public void setBOOK_STATUS(String BOOK_STATUS) {
		this.BOOK_STATUS = BOOK_STATUS;
	}
}
