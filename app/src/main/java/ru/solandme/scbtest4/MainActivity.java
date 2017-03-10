package ru.solandme.scbtest4;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> catNames;
    Boolean isNeedShowDialog = false;
    int delPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            catNames = savedInstanceState.getStringArrayList("list");
            isNeedShowDialog = savedInstanceState.getBoolean("isNeedShowDialog");
            delPosition = savedInstanceState.getInt("position");

            showDeleteDialog(delPosition);
        } else {
            catNames = new ArrayList<>(Arrays.asList("Рыжик", "Барсик", "Мурзик", "Мурка", "Васька",
                    "Томасина", "Кристина", "Пушок", "Дымка", "Кузя",
                    "Китти", "Масяня", "Симба", "Шустрик", "Шуня", "Пушинка"));
        }

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, catNames);

        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                showDeleteDialog(position);
            }
        });
    }

    private void showDeleteDialog(final int position) {
        isNeedShowDialog = true;
        delPosition = position;

        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setTitle("Delete?");
        adb.setMessage("Are you sure you want to delete " + catNames.get(position));
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                catNames.remove(delPosition);
                adapter.notifyDataSetChanged();
                isNeedShowDialog = false;
            }
        });
        adb.show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putStringArrayList("list", catNames);
        savedInstanceState.putBoolean("isNeedShowDialog", isNeedShowDialog);
        savedInstanceState.putInt("position", delPosition);

        super.onSaveInstanceState(savedInstanceState);
    }
}
