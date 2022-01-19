package org.tse.db.types;

import java.io.Serializable;
import java.util.Date;

public class Timestamp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -592635719730128730L;
	private Long timestamp;

	public Timestamp() {
		this.timestamp = new Date().getTime();
	}

	public Timestamp(Long time) {
		this.timestamp = time;
	}

	public long getValue() {
		return this.timestamp;
	}

	public Timestamp(String time) {
		this.timestamp = Long.parseLong(time);
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public void setValue(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Timestamp [timestamp=" + timestamp + "]";
	}

}

