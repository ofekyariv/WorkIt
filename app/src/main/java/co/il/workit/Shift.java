package co.il.workit;

import java.util.Date;

public class Shift implements IShift {

    private long time;
    private String date;
    private long id;

    public Shift(String date, long time) {
        this.time = time;
        this.date = date;
    }

    public Shift(String date, long time, long id) {
        this.time = time;
        this.date = date;
        this.id = id;
    }


    @Override
    public long getTime() {
        return time;
    }

    @Override
    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getTxtTime(){
        int hours = (int)time/3600;
        int minutes = (int)time/60 - hours*60;
        int seconds = (int)time - minutes*60 - hours*3600;
        String result = hours + ":" + minutes + ":" + seconds;
        return result;
    }
    @Override
    public int compareTo(Object o) {
        if(o instanceof Shift){
            return ((IShift) o).getId()==id? 1:0;
        }
        return 0;
    }
}
