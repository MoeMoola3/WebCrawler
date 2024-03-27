package service;

import java.util.Date;

public class Day {

    private static final Day INSTANCE = new Day(new Date(), false);
    private boolean flag;
    private final Date date;

    // Private constructor to prevent instantiation from outside
    Day(Date date, boolean flag) {
        this.date = date;
        this.flag = flag;
    }

    // Static method to get the singleton instance
    public static Day getInstance() {
        return INSTANCE;
    }

    public Date getDate() {
        return date;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
