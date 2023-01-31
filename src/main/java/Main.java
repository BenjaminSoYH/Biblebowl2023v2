import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
public class Main {
    public static void main(String[] args) throws IOException {
   
        File outputfile = new File("src/main/java/book1.xls");
        File pastefile = new File("src/main/java/pasteintothis.txt");

        Scanner reader = new Scanner(pastefile);

        ArrayList<Question> questions = new ArrayList<Question>();

        while (reader.hasNextLine()) {
            String temp = reader.nextLine();
            String temp2 = reader.nextLine();

            if (temp.startsWith("Start of set")) {
                reader.nextLine();
                continue;
            }


         String[] strArr = temp2.split("([(]|[:])");

            if (strArr.length == 3) {
                Question question = new Question(temp,strArr[0],strArr[1],strArr[2]);
                questions.add(question);

            }
            else {
                Question question = new Question(temp,strArr[0]);
                questions.add(question);

            }

        }

        Collections.sort(questions, new Comparator<Question>() {
            @Override
            public int compare(Question o1, Question o2) {
                return o1.getChapter() - o2.getChapter();
            }
        });


        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("page");

        for (int i = 0; i < questions.size(); i++) {
            Row row = sheet.createRow(i);
            Cell first = row.createCell(0);
            first.setCellValue(questions.get(i).getQuestion());
            Cell second = row.createCell(1);
            second.setCellValue(questions.get(i).getAnswer());
        }


        book.write(new FileOutputStream(outputfile));
        book.close();


    }


}