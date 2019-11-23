import Operations.Operation;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
    private static final String COUNT_REGEX = "[^0-9,]";
    private static final String FILE = "src/main/resources/movementList.csv";

    public static void main(String[] args) {
        double income = 0;
        double outgo =0;
        HashMap <String, Double> outgoTypes = new HashMap<>();
        List<Operation> operations = parseOperationsList();
//        operations.forEach(System.out::println);
        for (Operation o: operations){
            if (o.isIncome()) income+=o.getCount();
            else {
                outgo+=o.getCount();
                if (outgoTypes.containsKey(o.getContractor()))
                    outgoTypes.replace(o.getContractor(),outgoTypes.get(o.getContractor())+o.getCount());
                else outgoTypes.put(o.getContractor(),o.getCount());
            }
        }
        System.out.println("Общий доход "+income);
        System.out.println("Общий расход "+outgo);
        System.out.println("Расход по типам ");
        for (Map.Entry<String, Double> entry: outgoTypes.entrySet()){
            System.out.println(entry.getKey() + " - " + entry.getValue() + " руб");
        }
    }

    private static ArrayList<Operation> parseOperationsList (){
        ArrayList<Operation> operations = new ArrayList<>();
        try{
            List<String> lines = Files.readAllLines(Paths.get(FILE));
            lines.remove(0);
            lines.stream().spliterator().
                    forEachRemaining(l -> {
                        String[] parts = l.split(",",8);
                        if (parts.length==8) {
                            if (parts[6].equals("0")) {
                                parts[7] = parts[7].replaceAll(COUNT_REGEX, "").replace(',', '.');
                                operations.add(new Operation(LocalDate.parse(parts[3], formatter), parseContractor(parts[5]), -Double.parseDouble(parts[7])));
                            } else {
                                parts[7] = parts[7].replaceAll(COUNT_REGEX, "").replace(',', '.');
                                operations.add(new Operation(LocalDate.parse(parts[3], formatter), parseContractor(parts[5]), Double.parseDouble(parts[6])));
                            }
                        }
                        else System.out.println("В строке "+ l + " ошибка");
                    });
            System.out.println();
        }catch (Exception e){
            e.printStackTrace();
        }
        return operations;
    }
    private static String parseContractor (String s){
        return s.replaceAll("[+.\\s\\d-()]","");
    }
}
