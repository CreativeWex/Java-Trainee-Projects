public class Word {
    public String body;
    public int occurrence;
    private Word() {}
    public Word(String body, int occurrence) {
        this.body = body;
        this.occurrence = occurrence;
    }
}