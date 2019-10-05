package my.rockpilgrim.moneyfortravel.other;

import java.text.DecimalFormat;

public class ConvertFloat {

    public static String getLine(float num) {
        if (num >= 0) {
            if (num % 1 == 0) {
                DecimalFormat dFormat = new DecimalFormat("+#.##");
                return dFormat.format(num);
            } else
                return String.format("+%.2f", num);
        } else {
            if (num % 1 == 0) {
                DecimalFormat dFormat = new DecimalFormat("#.##");
                return dFormat.format(num);
            } else {
                return String.format("%.2f", num);
            }
        }
    }
}
