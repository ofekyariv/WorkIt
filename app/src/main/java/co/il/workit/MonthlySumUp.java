package co.il.workit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MonthlySumUp extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {
    Button btnWI;
    Button btnCS;
    Button btnMS;
    private ListView listView;
    private ShiftAdapter adapter;
    private ArrayList<Shift> Shift_list;
    private ShiftDataBase shiftDataBase;
    public static TextView txtsum;
    public static double sum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_sum_up);
        sum=0;
        listView = findViewById(R.id.list);
        shiftDataBase = new ShiftDataBase(this);
        Shift_list = ShiftDataBase.getShifts();
        adapter = new ShiftAdapter(this, Shift_list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);
        btnWI=(Button)findViewById(R.id.btnWI);
        btnWI.setOnClickListener(this);
        btnCS=(Button)findViewById(R.id.btnCS);
        btnCS.setOnClickListener(this);
        btnMS=(Button)findViewById(R.id.btnMS);
        btnMS.setOnClickListener(this);
        txtsum = findViewById(R.id.sum);
        calcsum();
    }
    public void calcsum(){
        for (Shift shift:Shift_list) {
            long time = shift.getTime();
            int hours = (int)time/3600;
            int minutes = (int)time/60 - hours*60;
            int seconds = (int)time - minutes*60 - hours*3600;
            double money =0;
            if(hours>=MainActivity.over_time){
                money+=MainActivity.over_time*MainActivity.salary;
                money+=(hours-MainActivity.over_time)*(MainActivity.salary*1.25);
                money+=minutes*(MainActivity.salary*1.25/60.0);
                money+=seconds*(MainActivity.salary*1.25/3600.0);
            }
            else{
                money+=hours*MainActivity.salary;
                money+=minutes*(MainActivity.salary/60.0);
                money+=seconds*(MainActivity.salary/3600.0);
            }
            money+=MainActivity.travel_ex;
            sum+=money;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        txtsum.setText(df.format(MonthlySumUp.sum) + "₪");
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("delete shift");
        alert.setMessage("are you sure you want to delete shift?");
        alert.setIcon(R.drawable.x);
        alert.setCancelable(false);

        alert.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                shiftDataBase.deleteShiftByRow(Shift_list.get(position).getId());
                //ShiftDataBase.getShifts().remove(position);
                adapter.notifyDataSetChanged();
                startActivity(new Intent(MonthlySumUp.this,MonthlySumUp.class));
                dialogInterface.dismiss();
            }
        });

        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.create();
        alert.show();
        adapter.notifyDataSetChanged();
        return true;
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

    }
}