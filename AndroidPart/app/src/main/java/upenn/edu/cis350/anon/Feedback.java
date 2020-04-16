package upenn.edu.cis350.anon;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Feedback implements Serializable {

    String content;
    Calendar date;

    ArrayList<Reply> replies;

    public Feedback(Calendar date, String content)  {
        this.date = date;
        this.content = content;
    }

    public Calendar getDate(){
        return date;
    }

    public String getDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date.getTime());
    }


    public String getContent() {
        return content;
    }
}