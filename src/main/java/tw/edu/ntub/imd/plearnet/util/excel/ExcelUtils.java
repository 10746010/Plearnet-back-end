package tw.edu.ntub.imd.plearnet.util.excel;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExcelUtils {
    public int formatColumnNameToColumnIndex(String cellName) {
        String upperCaseColumnName = cellName.toUpperCase();
        char[] charArray = upperCaseColumnName.toCharArray();
        int result = 0;
        int base = 26;
        for (int i = charArray.length - 1; i >= 0; i--) {
            int englishOneBasedNumber = charArray[i] - 'A';
            int multiple = charArray.length - i - 1;
            result = result + (englishOneBasedNumber * (int) Math.pow(base, multiple));
        }
        return result;
    }

    public String formatColumnIndexToEnglish(int columnIndex) {
        int columnNumber = columnIndex + 1;
        StringBuilder result = new StringBuilder();
        int base = 26;
        while (columnNumber > base) {
            int quotient = columnNumber / base;
            int remainder = columnNumber % base;
            result.insert(0, toEnglish(quotient));
            columnNumber = remainder;
        }
        return result.insert(0, toEnglish(columnNumber)).toString();
    }

    private String toEnglish(int number) {
        int start = 'A' - 1;
        int result = start + number;
        return String.valueOf((char) result);
    }
}
