import java.util.Date;
import java.util.Calendar;
import java.time.temporal.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * This class determines which days a tool is charged on.
 *
 * @author Cameron Costello
 * @version 1/2/2021
 */
public class ChargeableDays {

    private Date checkoutDate;
    private String dueDate;
    private Calendar calendar;
    private int year;
    private DateTimeFormatter dateFormat;
    private TemporalAdjuster laborDay;
    private DayOfWeek julyFourthDay;

    private LocalDate localDueDate;
    private LocalDate september;
    private LocalDate julyFourth;
    private LocalDate currentLaborDay;
    private LocalDate julyFourthChargeDay;

    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    /**
    * Constructor for objects of class ChargeableDays
    */
    public ChargeableDays(Date checkoutDate, String dueDate, boolean weekdayCharge, boolean weekendCharge,
    boolean holidayCharge) {

        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;

        this.calendar = Calendar.getInstance();
        this.calendar.setTime(checkoutDate);

        this.year = this.calendar.get(Calendar.YEAR);

        this.dateFormat =  DateTimeFormatter.ofPattern("MM/dd/yy");
        this.localDueDate = LocalDate.parse(dueDate, dateFormat);
        this.september = LocalDate.of(year, Month.SEPTEMBER, 1);
        this.laborDay = TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY);
        this.currentLaborDay = september.with(laborDay);
        this.julyFourth = LocalDate.of(year, Month.JULY, 4);
        this.julyFourthDay = DayOfWeek.of(julyFourth.get(ChronoField.DAY_OF_WEEK));
        this.julyFourthChargeDay = LocalDate.now();

    }

    /**
     * Calculates the chargeable days for a tool
     * 
     * @return int The number of chargeable days for a tool
     */
    public int calculateChargeableDays() {

        if (this.julyFourthDay == DayOfWeek.SATURDAY) {
            this.julyFourthChargeDay = LocalDate.of(this.year, Month.JULY, 3);
        } else if (this.julyFourthDay == DayOfWeek.SUNDAY) {
            this.julyFourthChargeDay = LocalDate.of(this.year, Month.JULY, 4);
        } else {
            this.julyFourthChargeDay = this.julyFourth;
        }

        LocalDate localCheckoutDate = checkoutDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        int chargeDays = 0;

        for (LocalDate date = localCheckoutDate; date.isBefore(this.localDueDate); date = date.plusDays(1)) {

            boolean isSatuday = date.getDayOfWeek().toString().equalsIgnoreCase("Saturday");
            boolean isSunday = date.getDayOfWeek().toString().equalsIgnoreCase("Sunday");

            if (isSatuday || isSunday) {
                if (this.weekendCharge) {
                    chargeDays++;
                }

            } else {
                if (this.weekdayCharge && this.holidayCharge) {
                    chargeDays++;

                } else if (this.weekdayCharge && !this.holidayCharge) {
                    if (!date.equals(this.currentLaborDay) && !date.equals(this.julyFourthChargeDay)) {
                        chargeDays++;
                    }
                }

            }
        }

        return chargeDays;

    }

}
