package com.example.lab4;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<TodoItem> todoList;
    private TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoList = new ArrayList<>();
        adapter = new TodoAdapter();

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        EditText editText = findViewById(R.id.editText);
        Switch switchUrgent = findViewById(R.id.switchUrgent);
        Button buttonAdd = findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(view -> {
            String text = editText.getText().toString();
            boolean isUrgent = switchUrgent.isChecked();
            todoList.add(new TodoItem(text, isUrgent));
            adapter.notifyDataSetChanged();
            editText.setText("");
        });

        listView.setOnItemLongClickListener((adapterView, view, position, id) -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(R.string.dialog_title)
                    .setMessage(getString(R.string.dialog_message) + position)
                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                        todoList.remove(position);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.no, null)
                    .show();
            return true;
        });
    }

    private class TodoAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return todoList.size();
        }

        @Override
        public Object getItem(int position) {
            return todoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.todo_item, parent, false);
            }

            TextView textView = (TextView) convertView;
            TodoItem item = todoList.get(position);
            textView.setText(item.getText());

            if (item.isUrgent()) {
                convertView.setBackgroundColor(Color.RED);
                textView.setTextColor(Color.WHITE);
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
                textView.setTextColor(Color.BLACK);
            }

            return convertView;
        }
    }
}