package ir.companymarketing.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends Activity {

    private ArrayAdapter<ToDo> toDoArrayAdapter;
    private List<ToDo> todos;
    private DataProvider data;
    private boolean state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = new DataProvider(this);
        data.open();
        state = false;
        List<ToDo> todos = data.getData();
        toDoArrayAdapter = new ToDoArrayAdapter(this, 0, todos);
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
        state = true;
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        data.open();
        state = true;
        todos = data.getData();
        toDoArrayAdapter.clear();
        toDoArrayAdapter.addAll(todos);
        toDoArrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        data.close();
        state = true;
    }

    public void searchBtnClickHandler(MenuItem item) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    class ToDoArrayAdapter extends ArrayAdapter<ToDo> {

        Context context;
        private int time = 0;

        public ToDoArrayAdapter(Context context, int resource, List<ToDo> objects) {
            super(context, resource, objects);

            this.context = context;
            todos = objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.listview, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    String[] extras = {todos.get(position).getText(),
                            todos.get(position).getTitle(), String.valueOf(todos.get(position).getId())};
                    intent.putExtra("Data", extras);
                    startActivity(intent);
                }
            });
            final TextView tv = (TextView) view.findViewById(R.id.title);
            tv.setText(todos.get(position).toString());
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.done);
            ImageButton delete = (ImageButton) view.findViewById(R.id.delete);
            Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.animation);
            view.setAnimation(anim);
            time++;
            anim.setStartOffset(time * 90);
            if (!state)
                anim.start();
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean deleted = data.remove(todos.get(position));
                    state = true;
                    if (deleted) {
                        time--;
                        todos = data.getData();
                        addAll(todos);
                        toDoArrayAdapter.notifyDataSetChanged();
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
                    data.update(todos.get(position));
                }
            });
            return view;

        }
    }
}
