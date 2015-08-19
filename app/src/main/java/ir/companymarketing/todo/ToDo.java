package ir.companymarketing.todo;

import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

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

    public void write(Context context) throws IOException {
        FileOutputStream outputStream = context.openFileOutput(
                title + ".todo", Context.MODE_PRIVATE);
        PrintWriter pw = new PrintWriter(outputStream);
        if (!done) {
            pw.write("f");
            Log.d("ToDo", "Wrote F.");
        } else {
            pw.write("t");
            Log.d("ToDo", "Wrote T.");
        }

        if (text != null)
            pw.write(text);
        else
            Log.d("ToDo", "Message wasn't empty!");
        outputStream.close();
        pw.close();
    }

    @Override
    public String toString() {
        return title;
    }

}
