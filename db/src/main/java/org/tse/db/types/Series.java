package org.tse.db.types;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

// Une série contient une date, une valeur et un nom
// La db stocke des collections de séries
public class Series implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8962558508769405528L;
	private LinkedList<Data> dataList;

	public Series() {
		this.dataList = new LinkedList<Data>();
	}

	public Series(Collection<Data> data) {
		this.dataList = new LinkedList<Data>(data);
	}

	public LinkedList<Data> getData() {
		return dataList;
	}

	public void setData(Collection<Data> data) {
		this.dataList = new LinkedList<Data>(data);
	}
	

	public LinkedList<Data> getDataList() {
		return dataList;
	}

	public void setDataList(LinkedList<Data> dataList) {
		this.dataList = dataList;
	}

	public void addData(Data data) {
		this.dataList.add(data);
	}

	public void removeData(Data data) {
		this.dataList.remove(data);
	}

	@Override
	public String toString() {
		return "Series [dataList=" + dataList + "]";
	}

	public void deltaCompression() {
		Double previousValue = 0.0;

		for (Data d : this.dataList) {
			d.setValue(d.getValue() - previousValue);
			previousValue += d.getValue();
		}
	}

	public void deltaDecompression() {
		Double previousValue = 0.0;

		for (Data d : this.dataList) {
			d.setValue(d.getValue() + previousValue);
			previousValue = d.getValue();
		}
	}

}
