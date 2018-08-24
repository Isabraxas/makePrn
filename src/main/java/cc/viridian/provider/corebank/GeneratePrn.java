package cc.viridian.provider.corebank;

import cc.viridian.provider.model.Statement;
import cc.viridian.provider.model.StatementDetail;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class GeneratePrn {

    public static void main(String ars[]){

        try {
            PrintWriter writer = new PrintWriter("/home/isvar/Documents/Fix-dummy-bank/old/adaptercorebankprn/src/test/resources/filename.txt", "UTF-8");

            writer.printf("BANCO: %s","DUMMY BANK\n");
            writer.printf("DIRECCION: %s","1425 JAMES ST, PO BOX 4000\n");
            writer.printf("STATEMENT PERIOD: %s","2003-10-08 to 2003-10-20\n");
            writer.printf("CLIENTE: %s","JOHN JONES\n");
            writer.printf("CUENTA: %s","A00010201\n\n");

            int dateSize=10, descSize=20, refSize=30, amountSize=15, operationSize=15 ;

            String date= "Date:     ";
            writer.print(returnDelimArray(date,dateSize));
            String desc= "Description:     ";
            writer.print(returnDelimArray(desc,descSize));
            String ref= "Ref:";
            writer.print(returnDelimArray(ref,refSize));
            String amount= "Amount:";
            writer.print(returnDelimArray(amount,amountSize));
            String operation= "Operation:";
            writer.println(returnDelimArray(operation,operationSize));

            Statement statement = DummyStatement.createRandom("A00010201","CTAAHR",
                    "BOB", LocalDate.of(2015,01,01), LocalDate.of(2016,02,28));

            List<StatementDetail> statementDetailList= statement.getDetails();

            for (StatementDetail statementDetail: statementDetailList) {

                writer.print(returnDelimArray(statementDetail.getDate(),dateSize));
                writer.print(returnDelimArray("No la encuentro",descSize));
                writer.print(returnDelimArray(statementDetail.getReferenceNumber(),refSize));
                System.out.println("Ref ("+") :"+statementDetail.getReferenceNumber());
                writer.print(returnDelimArray(statementDetail.getAmount().toString(),amountSize));
                System.out.println("Amount ("+") :"+statementDetail.getAmount());
                writer.println(returnDelimArray(statementDetail.getDebitCredit(),operationSize));
            }

            writer.println("\n-----------------");

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static char[] returnDelimArray(String string, int sizeArray){
        char[] charArray = new char[sizeArray];
        int i= 0;

        while ( i < sizeArray ){
            if(i < string.length()) {
                charArray[i] = string.toCharArray()[i];
            }else {
                charArray[i] = ' ';
            }
            i++;
        }
        return charArray;
    }
}
