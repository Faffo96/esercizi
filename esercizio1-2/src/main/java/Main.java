import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static boolean palindroma(String string) {
        String newString = "";
        int invertedIndex = string.length() - 1;

        for (int i = 0; i < string.length(); i++) {
            newString += string.charAt(invertedIndex);
            invertedIndex--;
        }
        return string.equals(newString);
    }

    static List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));


    public static List<Integer> insertIntoArray(List<Integer> inputArray, int position, int value) {
        List<Integer> newList = new java.util.ArrayList<>(List.of());
        for (int i = 0; i < inputArray.size() + 1; i++) {
            if (i == position) {
                newList.add(value);
            } else if (i > 0){
                newList.add(inputArray.get(i -1));
            } else {
                newList.add(inputArray.get(i));
            }
        }

        return newList;
    }

    public static void main(String[] args) {
        System.out.println("ESERCIZIO 1:");
        System.out.println(palindroma("anna"));
        System.out.println(palindroma("paola"));

        System.out.println("ESERCIZIO 2:");
        System.out.println(insertIntoArray(numbers, 1, 10));
    }
}
