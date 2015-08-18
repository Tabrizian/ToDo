package ir.companymarketing.todo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


public class EditActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveClickHandler(MenuItem item) {
        EditText title = (EditText) findViewById(R.id.title);
        try {
            FileOutputStream outputStream = openFileOutput(
                    (title.getText()).toString() + ".todo", Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(outputStream);
            pw.write("false\n");
            EditText mainText = (EditText) findViewById(R.id.editText);
            pw.write(mainText.getText().toString());
            Toast.makeText(this, (mainText.getText().toString()), Toast.LENGTH_LONG).show();
            Log.d("EditActivity", outputStream.getFD().toString());
            Toast.makeText(this, "Successfully wrote to " + String.valueOf(title.getText()) + ".todo", Toast.LENGTH_SHORT).show();
            outputStream.close();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
