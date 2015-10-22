/*
 * Class model for BNI statement file
 * Dev by : Titus Adi Prasetyo
 * 
 */

package com.agit.tcash.recon.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.ffpojo.metadata.delimited.annotation.DelimitedField;
import com.github.ffpojo.metadata.delimited.annotation.DelimitedRecord;

@DelimitedRecord(delimiter = "|")
public class BNIStatement extends BaseModel {
	@DelimitedField(positionIndex = 1)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@DelimitedField(positionIndex = 2)
	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public void setPostDate(String postDate) throws ParseException {
		this.postDate = sdf.parse(postDate);
	}

	@DelimitedField(positionIndex = 3)
	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	@DelimitedField(positionIndex = 4)
	public String getJournalNo() {
		return journalNo;
	}

	public void setJournalNo(String journalNo) {
		this.journalNo = journalNo;
	}

	@DelimitedField(positionIndex = 5)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	/*
	 * @DelimitedField(positionIndex = 6) public String getDescription2() {
	 * return description2; }
	 * 
	 * public void setDescription2(String description2) { this.description2 =
	 * description2; }
	 * 
	 * @DelimitedField(positionIndex = 7) public String getDescription3() {
	 * return description3; }
	 * 
	 * public void setDescription3(String description3) { this.description3 =
	 * description3; }
	 */

	@DelimitedField(positionIndex = 6)
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setAmount(String amount) {
		this.amount = new BigDecimal(amount);
		//this.amount = BigDecimal.valueOf(new Double(amount));
		//System.out.println("public void setAmount(String amount) : " + this.amount);
	}

	@DelimitedField(positionIndex = 7)
	public String getDbcr() {
		return dbcr;
	}

	public void setDbcr(String dbcr) {
		this.dbcr = dbcr;
	}

	@DelimitedField(positionIndex = 8)
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public void setBalance(String balance) {
		this.balance = BigDecimal.valueOf(new Double(balance));
	}

	private String no;
	private Date postDate;
	private String branch;
	private String journalNo;
	private String description;
	// private String description2;
	// private String description3;
	private BigDecimal amount;
	private String dbcr;
	private BigDecimal balance;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh.mm.ss");
}
