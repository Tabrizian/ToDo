package ir.companymarketing.todo;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by dell-iman on 8/14/2015.
 */
public class DataProvider {


    public static ArrayList<ToDo> getData(Context context) {
        ArrayList<ToDo> todos = new ArrayList<>();
        File file = new File(System.getProperty("user.dir"));
        Log.d("DataProvider", "Hello");
        for (String file1 : context.fileList()) {
            Log.d("DataProvider", file1);
            if (file1.endsWith(".todo")) {
                Log.d("DataProvider", file1);
//                new File(file1).delete();
                try {
                    FileInputStream inputStream = context.openFileInput(file1);
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                    String string = br.readLine();
                    String str = null;
                    String current = null;
                    while ((str = br.readLine()) != null) {
                        current += str;
                    }
                    br.close();
                    ToDo todo = new ToDo(file1.substring(0, file1.length() - 5), current);
                    todos.add(todo);

                    inputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return todos;
    }
}
