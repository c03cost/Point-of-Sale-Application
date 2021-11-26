
/**
 * This class is responsible for handling tool types
 *
 * @author Cameron Costello
 * @version 11/24/2021
 */
public class ToolType {

    public String tool;
    private double dailyCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    /**
     * Constructor for objects of class ToolType
     */
    public ToolType(String tool, double dailyCharge, boolean weekdayCharge, boolean weekendCharge,
            boolean holidayCharge) {

        this.tool = tool;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;

    }

    /**
     * Returns a tool
     * 
     * @return String tool
     */
    public String getTool() {
        return this.tool;
    }

    /**
     * Returns the tool daily charge
     * 
     * @return double daily charge
     */
    public double getDailyCharge() {
        return this.dailyCharge;
    }

    /**
     * Returns true if a tool has a weekday charge
     * 
     * @return boolean weekday charge
     */
    public boolean getWeekdayCharge() {
        return this.weekdayCharge;
    }

    /**
     * Returns true if a tool has a weekend charge
     * 
     * @return boolean weekend charge
     */
    public boolean getWeekendCharge() {
        return this.weekendCharge;
    }

    /**
     * Returns true if a tool has a holiday charge
     * 
     * @return boolean holiday charge
     */
    public boolean getHolidayCharge() {
        return this.holidayCharge;
    }

}
