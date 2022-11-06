package com.example.finalproj_alpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import static com.example.finalproj_alpha.DB_refs.refRequests;

public class settings_page extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner sides_spinner, color_spinner, annotation_spinner;
    EditText copies_et;
    int side = 0, color = 0, annotation = 0;

    String[] sides = {"both sides", "front side only"};
    String[] colors = {"Black and White", "Colorful"};
    String[] annotations = {"Vertical", "Horizontal"};

    String image_uri;

    String requestName, amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        Intent pdf_upload = getIntent();
        image_uri = pdf_upload.getStringExtra("uri");


        sides_spinner = findViewById(R.id.sides_spinner);
        color_spinner = findViewById(R.id.color_spinner);
        copies_et = findViewById(R.id.copies_et);
        annotation_spinner = findViewById(R.id.annotation_spinner);
        copies_et = findViewById(R.id.copies_et);

        sides_spinner.setOnItemSelectedListener(this);
        color_spinner.setOnItemSelectedListener(this);
        annotation_spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> sides_adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, sides);
        sides_spinner.setAdapter(sides_adapter);

        ArrayAdapter<String> colors_adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, colors);
        color_spinner.setAdapter(colors_adapter);

        ArrayAdapter<String> annotations_adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, annotations);
        annotation_spinner.setAdapter(annotations_adapter);
    }


    public void onItemSelected(AdapterView<?> parent, View view, int pos, long rowid) {
        if (parent.getId() == R.id.sides_spinner){
            side = pos;
        }
        else if (parent.getId() == R.id.color_spinner){
            color = pos;
        }
        else if (parent.getId() == R.id.annotation_spinner){
            annotation = pos;
        }
    }


    public void onNothingSelected(AdapterView<?> parent) {
    }


    public void next_page_btn(View view) {
        //Intent send_request_intent = new Intent(this,send_request.class);
        //send_request_intent.putExtra("side",side);
        //send_request_intent.putExtra("color",color);
        //send_request_intent.putExtra("annotation",annotation);
        //startActivity(send_request_intent);

        requestName = "RQ" + System.currentTimeMillis();
        amount = copies_et.getText().toString();

        refRequests.child(requestName).setValue("");
        refRequests.child(requestName).child("side").setValue(side);
        refRequests.child(requestName).child("color").setValue(color);
        refRequests.child(requestName).child("annotation").setValue(annotation);
        refRequests.child(requestName).child("copies").setValue(Integer.parseInt(amount));
        refRequests.child(requestName).child("uri").setValue(image_uri);
    }
}