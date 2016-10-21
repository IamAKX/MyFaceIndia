package Connection;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Akash on 20-10-2016.
 */
public class CurrentDateTime {
    public String timeNow()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return  formattedDate;
    }
}
