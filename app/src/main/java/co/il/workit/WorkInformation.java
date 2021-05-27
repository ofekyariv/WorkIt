package co.il.workit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WorkInformation extends AppCompatActivity implements View.OnClickListener {
    Button btnWI;
    Button btnCS;
    Button btnMS;
    Button btnSY;
    Button btnTE;
    Button btnSD;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_information);
        btnWI=(Button)findViewById(R.id.btnWI);
        btnWI.setOnClickListener(this);
        btnCS=(Button)findViewById(R.id.btnCS);
        btnCS.setOnClickListener(this);
        btnMS=(Button)findViewById(R.id.btnMS);
        btnMS.setOnClickListener(this);
        btnSY=(Button)findViewById(R.id.btnSY);
        btnSY.setOnClickListener(this);
        btnTE=(Button)findViewById(R.id.btnTE);
        btnTE.setOnClickListener(this);
        btnSD=(Button)findViewById(R.id.btnSD);
        btnSD.setOnClickListener(this);
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
        if (btnSY == v)
        {
            Intent intent = new Intent(this, Salary.class);
            startActivity(intent);
        }
        if (btnTE == v)
        {
            Intent intent = new Intent(this, Travel_Ex.class);
            startActivity(intent);
        }
        if (btnSD == v)
        {
            Intent intent = new Intent(this, Over_Time_Hours.class);
            startActivity(intent);
        }


    }
}