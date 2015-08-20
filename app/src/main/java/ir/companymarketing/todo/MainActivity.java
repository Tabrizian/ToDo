package ir.companymarketing.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ToDo> todos = DataProvider.getData(this);
        ArrayAdapter<ToDo> toDoArrayAdapter = new ToDoArrayAdapter(this, 0, todos);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(toDoArrayAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void newNoteClickHandler(MenuItem item) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    class ToDoArrayAdapter extends ArrayAdapter<ToDo> {
        List<ToDo> todos;
        Context context;

        public ToDoArrayAdapter(Context context, int resource, List<ToDo> objects) {
            super(context, resource, objects);

            this.context = context;
            todos = objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.listview, null);
            final TextView tv = (TextView) view.findViewById(R.id.title);
            tv.setText(todos.get(position).toString());
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.done);
            Button delete = (Button) view.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean deleted = MainActivity.this.deleteFile(tv.getText().toString() + ".todo");
                    ListView lv = (ListView) findViewById(android.R.id.list);
                    lv.invalidateViews();
                    if (deleted) {
                        Toast.makeText(MainActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();

                    }
                }
            });
            checkBox.setChecked(todos.get(position).isDone());
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    todos.get(position).setDone(((CheckBox) v).isChecked());
                    try {
                        todos.get(position).write(MainActivity.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return view;

        }
    }
}
