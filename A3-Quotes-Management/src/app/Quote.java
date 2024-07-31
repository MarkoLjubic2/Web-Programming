package app;

public class Quote {

    private final String author;
    private final String quote;

    public Quote(String author, String quote) {
        this.author = author;
        this.quote = quote;
    }

    @Override
    public String toString() {
        return author + " : " + "\"" + quote + "\"";
    }

    public String getAuthor() {
        return author;
    }

    public String getQuote() {
        return quote;
    }
}
