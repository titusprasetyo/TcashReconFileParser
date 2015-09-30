/*
 * Class model for Bank Mandiri statement file
 * Dev by : Titus Adi Prasetyo
 * 
 */

package com.agit.tcash.recon.model;

import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.github.ffpojo.metadata.positional.annotation.PositionalField;
import com.github.ffpojo.metadata.positional.annotation.PositionalRecord;

@PositionalRecord
public class BMRStatement extends BaseModel {

	@PositionalField(initialPosition = 14, finalPosition = 23)
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public void setDateTime(String dateTime) throws ParseException {
		this.dateTime = sdf.parse(dateTime);
	}

	@PositionalField(initialPosition = 24, finalPosition = 33)
	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public void setValueDate(String valueDate) throws ParseException {
		this.valueDate = sdf.parse(valueDate);
	}

	@PositionalField(initialPosition = 38, finalPosition = 142)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@PositionalField(initialPosition = 143, finalPosition = 159)
	public String getReferenceNo() {

		if (referenceNo.length() >= 5)
			return referenceNo.substring(0, 5);

		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	@PositionalField(initialPosition = 165, finalPosition = 178)
	public BigDecimal getDebit() {
		return debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	public void setDebit(String debit) {
		this.debit = BigDecimal.valueOf(new Double(debit));
	}

	@PositionalField(initialPosition = 179, finalPosition = 194)
	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public void setCredit(String credit) {
		this.credit = BigDecimal.valueOf(new Double(credit));
	}

	private Date dateTime;
	private Date valueDate;
	private String description;
	private String referenceNo;
	private BigDecimal debit;
	private BigDecimal credit;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
}
