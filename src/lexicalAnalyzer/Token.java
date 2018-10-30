package lexicalAnalyzer;

public class Token {
    private String type;
    private int code;
    private String string;
    private String error;

    public Token(int code, String string, String type) {
        this.type = type;
        this.code = code;
        this.string = string;
        this.error = null;
    }

    public Token(String error) {
        this.type = "ERROR: ";
        this.error = error;
    }

    public String toString() {
        if (this.error != null) {
            return this.type + this.error;
        } else {
            return this.type + " <" + this.string + ", " + this.code + ">";
        }
    }
}
