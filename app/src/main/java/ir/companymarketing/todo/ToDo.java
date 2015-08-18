package ir.companymarketing.todo;

/**
 * Created by dell-iman on 8/14/2015.
 */
public class ToDo {
    private String title;
    private String text;
    private boolean done = false;

    public ToDo(String title, String text) {
        this.text = text;
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean status) {
        done = status;
    }

    @Override
    public String toString() {
        return title;
    }
}
