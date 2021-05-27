package co.il.workit;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShiftAdapter extends ArrayAdapter<Shift> {
    Context context;
    public ShiftAdapter(Context context, ArrayList<Shift> Shifts) {
        super(context, 0, Shifts);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Shift Shift = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_shift, parent, false);
        }
        // Lookup view for data population
        TextView tvDate = (TextView) convertView.findViewById(R.id.itemDate);
        TextView tvTime = (TextView) convertView.findViewById(R.id.itemTime);
        TextView tvMoney = (TextView) convertView.findViewById(R.id.money);

        // Populate the data into the template view using the data object
        assert Shift != null;
        /*DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(Shift.getDate());*/
        tvDate.setText(Shift.getDate());
        tvTime.setText(Shift.getTxtTime());

        long time = Shift.getTime();
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
        DecimalFormat df = new DecimalFormat("#.##");
        String salary = df.format(money)+"₪";
        tvMoney.setText(salary);
        // Return the completed view to render on screen
        return convertView;
    }

}