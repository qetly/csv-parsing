package Operations;


import java.time.LocalDate;

public class Operation {
    private LocalDate date;
    private String contractor;
    private double count;
    private OperationTypes type;


    public Operation(LocalDate date, String contractor, double count){
        this.date=date;
        this.contractor = contractor;
        if (count>0) {
            type=OperationTypes.INCOME;
            this.count=count;
        }
        else {
            type=OperationTypes.OUTGO;
            this.count=-count;
        }


    }
    public boolean isIncome(){
        return type==OperationTypes.INCOME;
    }
    public LocalDate getDate(){
        return date;
    }

    public double getCount(){
        return count;
    }

    public String getContractor(){
        return contractor;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "date=" + date +
                ", contractor='" + contractor + '\'' +
                ", count=" + count +
                ", type=" + type +
                '}';
    }

}
