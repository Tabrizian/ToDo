package ir.companymarketing.todo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class EditActivity extends Activity {

    private DataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        dataProvider = new DataProvider(this);
        dataProvider.open();
        if (getIntent().getStringArrayExtra("Data") != null) {
            EditText title = (EditText) findViewById(R.id.title);
            EditText editText = (EditText) findViewById(R.id.editText);
            title.setText(getIntent().getStringArrayExtra("Data")[0]);
            editText.setText(getIntent().getStringArrayExtra("Data")[1]);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataProvider.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataProvider.close();
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
        if (getIntent().getStringArrayExtra("Data") == null) {
            EditText title = (EditText) findViewById(R.id.title);
            EditText mainText = (EditText) findViewById(R.id.editText);
            ToDo todo = new ToDo(title.getText().toString(), mainText.getText().toString());
            todo.setDone(false);
            dataProvider.addData(todo);
            if (dataProvider.update(todo))
                Toast.makeText(this, "Todo successfully created!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Todo creation failed!", Toast.LENGTH_SHORT).show();
        } else {
            EditText title = (EditText) findViewById(R.id.title);
            EditText mainText = (EditText) findViewById(R.id.editText);
            ToDo todo = new ToDo(title.getText().toString(), mainText.getText().toString());
            todo.setId(Long.valueOf(getIntent().getExtras().getStringArray("Data")[2]));
            todo.setDone(false);
            if (dataProvider.update(todo))
                Toast.makeText(this, "Todo successfully updated!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Todo update failed!", Toast.LENGTH_SHORT).show();
        }
    }

}
