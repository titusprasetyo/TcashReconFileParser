/*
 * Abstract class for Base Model of RK Model
 * Dev by : Titus Adi Prasetyo
 * 
 */

package com.agit.tcash.recon.model;

import java.util.Calendar;
import java.util.Date;

public abstract class BaseModel {
	private Date dtStamp;

	public Date getDtStamp() {
		return dtStamp;
	}

	public void setDtStamp() {
		this.dtStamp = Calendar.getInstance().getTime();
	}

}
