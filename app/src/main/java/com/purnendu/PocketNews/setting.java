package com.purnendu.PocketNews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class setting extends AppCompatActivity {
    Spinner spinner;
    TextView cb;
    SwitchMaterial sb;
    String [] Country={"Argentina","Austria","Australia","Belgium","Bulgaria","Brazil","Canada","Colombia","Cuba","Czech Republic","Egypt","France","Germany","Great Britain(UK)","Greece","Hong kong","Hungary","Indonesia","Ireland","Israel","India","Italy","Japan","Korea(South)","Lithuania","Latvia","Morocco","Mexico","Malaysia","Nigeria","Netherlands","Norway","New Zealand","Philippines","Poland","Portugal","Romania","Russian Federation","Serbia","Saudi Arabia","Sweden","Singapore","Slovenia","Slovakia","Switzerland","Thailand","Turkey","Taiwan","Ukraine","United States","Venezuela","Zambia"};
    String temp;
    SharedPreferences sharedpreferences,sharedpreferences1;
    private Boolean mIsSpinnerFirstCall = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        spinner=findViewById(R.id.spinner);
        cb=findViewById(R.id.cb);
        sb=findViewById(R.id.sb);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Country);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(!mIsSpinnerFirstCall) {
                    temp="";
                    switch (Country[position]) {
                        case ("Argentina"):
                            temp = "ar";
                            break;
                        case ("Austria"):
                            temp = "at";
                            break;
                        case ("Australia"):
                            temp = "au";
                            break;
                        case ("Belgium"):
                            temp = "be";
                            break;
                        case ("Bulgaria"):
                            temp = "bg";
                            break;
                        case ("Brazil"):
                            temp = "br";
                            break;
                        case ("Canada"):
                            temp = "ca";
                            break;
                        case ("Colombia"):
                            temp = "co";
                            break;
                        case ("Cuba"):
                            temp = "cu";
                            break;
                        case ("Czech Republic"):
                            temp = "cz";
                            break;
                        case ("Egypt"):
                            temp = "eg";
                            break;
                        case ("France"):
                            temp = "fr";
                            break;
                        case ("Germany"):
                            temp = "de";
                            break;
                        case ("Great Britain(UK)"):
                            temp = "gb";
                            break;
                        case ("Greece"):
                            temp = "gr";
                            break;
                        case ("Hong kong"):
                            temp = "hk";
                            break;
                        case ("Hungary"):
                            temp = "hu";
                            break;
                        case ("Indonesia"):
                            temp = "id";
                            break;
                        case ("Ireland"):
                            temp = "ie";
                            break;
                        case ("Israel"):
                            temp = "il";
                            break;
                        case ("India"):
                            temp = "in";
                            break;
                        case ("Italy"):
                            temp = "it";
                            break;
                        case ("Japan"):
                            temp = "jp";
                            break;
                        case ("Korea(South)"):
                            temp = "kr";
                            break;
                        case ("Lithuania"):
                            temp = "lt";
                            break;
                        case ("Latvia"):
                            temp = "lv";
                            break;
                        case ("Morocco"):
                            temp = "ma";
                            break;
                        case ("Mexico"):
                            temp = "mx";
                            break;
                        case ("Malaysia"):
                            temp = "my";
                            break;
                        case ("Nigeria"):
                            temp = "ng";
                            break;
                        case ("Netherlands"):
                            temp = "nl";
                            break;
                        case ("Norway"):
                            temp = "no";
                            break;
                        case ("New Zealand"):
                            temp = "nz";
                            break;
                        case ("Philippines"):
                            temp = "ph";
                            break;
                        case ("Poland"):
                            temp = "pl";
                            break;
                        case ("Portugal"):
                            temp = "pt";
                            break;
                        case ("Romania"):
                            temp = "ro";
                            break;
                        case ("Russian Federation"):
                            temp = "ru";
                            break;
                        case ("Serbia"):
                            temp = "rs";
                            break;
                        case ("Saudi Arabia"):
                            temp = "sa";
                            break;
                        case ("Sweden"):
                            temp = "se";
                            break;
                        case ("Singapore"):
                            temp = "sg";
                            break;
                        case ("Slovenia"):
                            temp = "si";
                            break;
                        case ("Slovakia"):
                            temp = "sk";
                            break;
                        case ("Switzerland"):
                            temp = "ch";
                            break;
                        case ("Thailand"):
                            temp = "th";
                            break;
                        case ("Turkey"):
                            temp = "tr";
                            break;
                        case ("Taiwan"):
                            temp = "tw";
                            break;
                        case ("Ukraine"):
                            temp = "ua";
                            break;
                        case ("United States"):
                            temp = "us";
                            break;
                        case ("Venezuela"):
                            temp = "ve";
                            break;
                        case ("Zambia"):
                            temp = "za";
                            break;
                    }
                    sharedpreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("country", temp);
                    editor.apply();
                    Toast.makeText(setting.this, Country[position]+" selected", Toast.LENGTH_SHORT).show();
               }
                mIsSpinnerFirstCall = false;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getSharedPreferences("BOOKMARKS", MODE_PRIVATE);
                sharedpreferences.edit().clear().apply();
                sharedpreferences1=getSharedPreferences("count",MODE_PRIVATE);
                sharedpreferences1.edit().clear().apply();
                Toast.makeText(setting.this, "Bookmarks Cleared Successfully", Toast.LENGTH_SHORT).show();

            }
        });
        sb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sb.isChecked())
                {
                    Toast.makeText(setting.this, "Some devices may not work,to use this feature manually select theme from phone's setting", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(setting.this,MainActivity.class);
        startActivity(intent);
    }
}