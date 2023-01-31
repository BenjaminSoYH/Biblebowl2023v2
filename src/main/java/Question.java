public class Question {

    String question;
    String answer;

    int chapter;
    String verse;


    public Question(String question) {
        this.question = question;
    }
    public Question(String question, String answer) {
        this.question = question.trim().replaceFirst("[0-9]+.+\\t","");
        this.answer = answer.replaceFirst("A.\t","");
    }
    public Question(String question, String answer, String book, String verse) {
        this.question = question.trim().replaceFirst("[0-9]+.+\\t","");
        this.answer = answer.replaceFirst("A.\t","");
        this.chapter = Integer.parseInt(book.replaceAll("([(]|[:]|[)])",""));
        this.verse = verse.trim().replaceAll("([(]|[:]|[)])","");
    }
    public Question(String question, String answer, int book, int verse) {
        this.question = question;
        this.answer = answer;
        this.chapter = book;
        this.verse = String.valueOf(verse);
    }

    public void setAnswer(String answer ) {
        this.answer = answer;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswerAndBook() {
        return this.answer +"(" + chapter +":" +verse+ ")";
    }
    public String getAnswer() {
    return this.answer;
    }
    public int getChapter() {
        return this.chapter;
    }
    public String getVerse() {
        return this.verse;
    }

    @Override
    public String toString() {
        if (chapter ==0) {
            return question +"\n" + answer;
        }
        return question +"\n" + answer +"(" + chapter +":" +verse+ ")";
    }
}
