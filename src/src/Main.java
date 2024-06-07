import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Input: ");
        String expression = in.nextLine();
        in.close();

        String[] expressionSplit = expression.split(" ");

        try{
            if(expressionSplit.length > 3){
                throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            }
            if(expressionSplit.length < 3){
                throw new Exception("строка не является математической операцией");
            }
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }

        int numberTypeOperand1 = numberType(expressionSplit[0]);
        int numberTypeOperand2 = numberType(expressionSplit[2]);


    }

    private static final int[] intervals={0, 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    private static final String[] numerals={"", "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};

    private static int findFloor(final int number, final int firstIndex, final int lastIndex) {
        if(firstIndex==lastIndex)
            return firstIndex;
        if(intervals[firstIndex]==number)
            return firstIndex;
        if(intervals[lastIndex]==number)
            return lastIndex;
        final int median=(lastIndex+firstIndex)/2;
        if(median==firstIndex)
            return firstIndex;
        if(number == intervals[median])
            return median;
        if(number > intervals[median])
            return findFloor(number, median, lastIndex);
        else
            return findFloor(number, firstIndex, median);
    }

    public static String toRoman(final int number) {
        int floorIndex=findFloor(number, 0, intervals.length-1);
        if(number==intervals[floorIndex])
            return numerals[floorIndex];
        return numerals[floorIndex]+toRoman(number-intervals[floorIndex]);
    }

    static int toArabic(String roman) {
        int result = 0;
        for (int i = intervals.length-1; i >= 0; i-- ) {
            while (roman.indexOf(numerals[i]) == 0 && !numerals[i].isEmpty()) {
                result += intervals[i];
                roman = roman.substring(numerals[i].length());
            }
        }
        return result;
    }

    static int numberType(String number) {  //0 - ошибка, 1 - арабская, 2- римская
        int res = 0;
        try{

            int res1 = Integer.parseInt(number);
            res = 1;
        }
        catch(NumberFormatException ex){
            //System.out.println("Ошибка преобразования из строки в число");
            try{
                int res2 = toArabic(number);
                if (res2 == 0){
                    throw new Exception("Ошибка преобразования из строки в число римское");
                }
                res = 2;
            }
            catch(Exception exc){
                //System.out.println("Ошибка преобразования из строки в число римское 2");
            }
        }
        return res;
    }
}