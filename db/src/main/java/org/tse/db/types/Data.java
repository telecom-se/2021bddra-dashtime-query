package org.tse.db.types;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.Serializable;

public class Data implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8309456898207869492L;
    private Timestamp timeStamp;
    private Double value;

    public Data() {

    }

    public Data(Timestamp timeStamp, Double value) {
        this.timeStamp = timeStamp;
        this.value = value;
    }

    public Data(String timeStamp, String value) {
        this.timeStamp = new Timestamp(timeStamp);
        this.value = Double.parseDouble(value);
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Data [timeStamp=" + timeStamp + ", value=" + value + "]";
    }

    public ObjectNode serialize(ObjectMapper mapper) {
        ObjectNode dataNode = mapper.createObjectNode();
        dataNode.put("timestamp", this.timeStamp.getValue());
        dataNode.put("value", this.value);
        return dataNode;
    }
}

