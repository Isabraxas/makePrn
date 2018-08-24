package cc.viridian.provider.corebank;

import cc.viridian.provider.model.Statement;
import cc.viridian.provider.model.StatementDetail;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class GeneratePrn {

    public static void main(String ars[]){

        try {
            PrintWriter writer = new PrintWriter("/home/isvar/Documents/Fix-dummy-bank/old/adaptercorebankprn/src/test/resources/filename.prn", "UTF-8");

            int i=0;
            while (i < 5){
                writer=myPrint(writer);
                i++;
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Review comand
        //consoleCMD= cat -A /home/isvar/Documents/Fix-dummy-bank/old/adaptercorebankprn/src/test/resources/filename.prn

    }

    public static PrintWriter myPrint(PrintWriter myWriter){
        PrintWriter writer = myWriter;
        Statement statement = DummyStatement.createRandom("A00010201","CTAAHR",
                "BOB", LocalDate.of(2015,01,01), LocalDate.of(2016,02,28));

        if (statement.getDetails().size() > 0) {
            writer.printf("Bank: %s", "DUMMY BANK\n");
            writer.printf("Address: %s", "1425 JAMES ST, PO BOX 4000\n");
            writer.printf("Statement Period: %s", "2003-10-08 to 2003-10-20\n");
            writer.printf("Customer: %s", "JOHN JONES\n");
            writer.printf("Account: %s", "A00010201\n\n");

            int dateSize = 12, descSize = 25, refSize = 30, amountSize = 15, operationSize = 15;

            String date = "Date:     ";
            writer.print(returnDelimArray(date, dateSize));
            String desc = "Description:     ";
            writer.print(returnDelimArray(desc, descSize));
            String ref = "Ref:";
            writer.print(returnDelimArray(ref, refSize));
            String amount = "Amount:";
            writer.print(returnDelimArray(amount, amountSize));
            String operation = "Operation:";
            writer.println(returnDelimArray(operation, operationSize));

            List<StatementDetail> statementDetailList = statement.getDetails();
            BigDecimal totalAmount = BigDecimal.valueOf(0L);
            for (StatementDetail statementDetail : statementDetailList) {

                writer.print(returnDelimArray(statementDetail.getDate(), dateSize));
                writer.print(returnDelimArray(statementDetail.getAnnotation(), descSize));
                writer.print(returnDelimArray(statementDetail.getReferenceNumber(), refSize));
                System.out.println("Ref (" + ") :" + statementDetail.getReferenceNumber());
                writer.print(returnDelimArray(statementDetail.getAmount().toString(), amountSize));
                System.out.println("Amount (" + ") :" + statementDetail.getAmount());
                writer.println(returnDelimArray(statementDetail.getDebitCredit(), operationSize));

                totalAmount = totalAmount.add(statementDetail.getAmount());
            }

            writer.print("\n");
            writer.print(returnDelimArray("",dateSize));
            writer.print(returnDelimArray("***Totals*** ",descSize));
            writer.print(returnDelimArray("",refSize));
            writer.println(returnDelimArray(totalAmount.toString(), amountSize));

            writer.println("\n");
            writer.println("-----------------");
        }

        return writer;
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
