import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
public class Main {
    public static void main(String[] args) throws IOException {

        Random r = new Random();

        File outputfile = new File("src/main/java/book1.xls");
        File pastefile = new File("src/main/java/pasteintothis.txt");
        File names = new File("src/main/java/Names.txt");

        Scanner reader = new Scanner(pastefile);
        Scanner readerName = new Scanner(names);

        ArrayList<Question> questions = new ArrayList<Question>();
        ArrayList<String> namesArray = new ArrayList<String>();
        while(readerName.hasNextLine()){
            namesArray.add(readerName.nextLine());
        }

        while (reader.hasNextLine()) {
            String temp = reader.nextLine();
            String bookz = reader.nextLine();

            if (temp.startsWith("Start of set")) {
                reader.nextLine();
                continue;
            }


            String versez = bookz;
            if ((bookz.substring(bookz.length()-6,bookz.length()).contains("-"))) {
                bookz= bookz.substring(0, bookz.indexOf("-")).trim();
            }
            bookz =bookz.substring(bookz.length()-6,bookz.length()).trim();
            bookz = bookz.replaceAll("[a-z]","");
            versez = versez.replaceAll("[(]+[0-9]+:+[0-9]+[)]","");
            versez = versez.replaceAll("[(]+[0-9]+:+[0-9]+[-]+[0-9]+[)]","");
            versez = versez.replaceAll("\\[+.+\\]","");
            versez = versez.replaceAll("\\(+.+\\)","");

            String[] strArr = bookz.split("([:])");

            System.out.println(strArr[0]);
            System.out.println(strArr[1]);
            Question question = new Question(temp,versez,strArr[0].trim(),strArr[1]);
            questions.add(question);

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

            if(questions.get(i).getQuestion().toLowerCase().contains("who")){


                ArrayList<String> temp = new ArrayList<>();
                temp.add(questions.get(i).getAnswer());
                 for(int j = 2; j < 5; j++) {

                     Cell third = row.createCell(j);

                     boolean loop = true;
                     String tempword;
                     while (loop) {
                         tempword = namesArray.get(r.nextInt(namesArray.size()));
                         if (!temp.contains(tempword)) {
                             temp.add(tempword);
                             third.setCellValue(tempword);
                             loop = false;
                         }
                     }


                }
            }

            Cell fifth = row.createCell(5);
            fifth.setCellValue(questions.get(i).getBook());

        }


        book.write(new FileOutputStream(outputfile));
        book.close();


    }


}