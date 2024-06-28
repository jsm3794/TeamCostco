package main.utils;

import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.text.NumberFormatter;

public class CustomNumberFormatter extends NumberFormatter {

    public CustomNumberFormatter(NumberFormat format) {
        super(format);
    }

    @Override
    public Object stringToValue(String text) throws ParseException {
        if (text == null || text.trim().isEmpty()) {
            return null;  // 공백을 null로 처리
        }
        // +, -, 숫자, 공백만 포함하는지 확인
        if (!text.matches("[\\d\\s\\+\\-]*")) {
            throw new ParseException("Invalid characters", 0);
        }
        return super.stringToValue(text);
    }

    public static CustomNumberFormatter createFormatter() {
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setGroupingUsed(false);  // 콤마 제거

        CustomNumberFormatter formatter = new CustomNumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        return formatter;
    }
}
