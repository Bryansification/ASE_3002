package com.example.jj.bryancare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import static com.example.jj.bryancare.R.id.listViewPolyclinic;

public class BrowsePolyclinicActivity extends AppCompatActivity {


    private String queueFilter;
    private PolyclinicManagerDao polyclinicManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);
        setContentView(R.layout.activity_browse_polyclinic);

        polyclinicManager = new PolyclinicManager();

        Spinner queueFilterSpinner = (Spinner) findViewById(R.id.spinnerFilterQueue);
        ArrayAdapter<CharSequence> queueFilterAdapter = ArrayAdapter.createFromResource(this, R.array.queues_array, android.R.layout.simple_spinner_item);
        queueFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        queueFilterSpinner.setAdapter(queueFilterAdapter);

        queueFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                queueFilter = parent.getItemAtPosition(position).toString();
                loadPolyclinicList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                queueFilter = "Average Wait Time";
                Log.v("this", queueFilter);
                loadPolyclinicList();
            }
        });


    }

    private void loadPolyclinicList() {
        polyclinicManager.loadPolyclinicList(this, queueFilter);
        ListView polyclinicListView = (ListView) findViewById(listViewPolyclinic);
        polyclinicListView.setAdapter(polyclinicManager.getPolyclinicAdapter());

        polyclinicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                polyclinicManager.setCurrentPolyclinic(polyclinicManager.getPolyclinics().get(position));
                Intent takeQueueNumberIntent = new Intent(BrowsePolyclinicActivity.this, TakeQueueNumberActivity.class);
                startActivity(takeQueueNumberIntent);
            }
        });
    }

}
