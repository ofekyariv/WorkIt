package co.il.workit;


public interface IShift extends Comparable {
    long getTime();

    void setTime(long time);

    String getDate();

    void setDate(String date);

    long getId();

    void setId(long id);

    String getTxtTime();


    @Override
    int compareTo(Object o);
}
