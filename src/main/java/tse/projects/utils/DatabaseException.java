package tse.projects.utils;

public class DatabaseException {
    public String code;
    public String message;

    public DatabaseException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
