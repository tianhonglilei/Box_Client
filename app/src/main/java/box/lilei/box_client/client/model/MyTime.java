package box.lilei.box_client.client.model;

/**
 * Created by lilei on 2017/10/17.
 */

public class MyTime {

    private String timeDay;
    private String timeWeek;
    private String timeMinute;

    public MyTime(String timeDay, String timeWeek, String timeMinute) {
        this.timeDay = timeDay;
        this.timeWeek = timeWeek;
        this.timeMinute = timeMinute;
    }

    public String getTimeMinute() {
        return timeMinute;
    }

    public void setTimeMinute(String timeMinute) {
        this.timeMinute = timeMinute;
    }

    public String getTimeDay() {
        return timeDay;
    }

    public void setTimeDay(String timeDay) {
        this.timeDay = timeDay;
    }

    public String getTimeWeek() {
        return timeWeek;
    }

    public void setTimeWeek(String timeWeek) {
        this.timeWeek = timeWeek;
    }



}
