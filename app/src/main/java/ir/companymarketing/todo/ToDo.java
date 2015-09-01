package ir.companymarketing.todo;

/**
 * Created by dell-iman on 8/14/2015.
 */
public class ToDo {
    private long id;
    private String title;
    private String text;
    private boolean done = false;

    public ToDo() {

    }

    public ToDo(String title, String text) {
        this.setText(text);
        this.setTitle(title);
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean status) {
        done = status;
    }

    @Override
    public String toString() {
        return getTitle();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
