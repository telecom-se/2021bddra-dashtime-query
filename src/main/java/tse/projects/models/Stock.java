package tse.projects.models;

public class Stock implements DatabaseModel {
    public Long id;
    public String symbol;
    public Long timestamp;
    public Double value;

    public Stock(Long id, String symbol, Long timestamp, Double value) {
        this.id = id;
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
