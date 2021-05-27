package co.il.workit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Salary extends AppCompatActivity implements View.OnClickListener
{
    Button btnWI;
    Button btnCS;
    Button btnMS;
    Button btnSave;
    EditText et4H;
    TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        et4H=findViewById(R.id.et4H);
        tvDisplay=findViewById(R.id.tvDisplay);
        btnWI=(Button)findViewById(R.id.btnWI);
        btnWI.setOnClickListener(this);
        btnCS=(Button)findViewById(R.id.btnCS);
        btnCS.setOnClickListener(this);
        btnMS=(Button)findViewById(R.id.btnMS);
        btnMS.setOnClickListener(this);
        btnSave=(Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (btnMS == v)
        {
            Intent intent = new Intent(this, MonthlySumUp.class);
            startActivity(intent);
        }
        if (btnWI == v)
        {
            Intent intent = new Intent(this, WorkInformation.class);
            startActivity(intent);
        }
        if (btnCS == v)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (btnSave == v)
        {
            if(et4H.getText().length()>0)
            {
                MainActivity.salary = Integer.parseInt(et4H.getText().toString());
                tvDisplay.setText(et4H.getText());
                et4H.setText("");
            }
        }
    }
}