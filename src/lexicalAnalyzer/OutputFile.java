package lexicalAnalyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OutputFile {
    public static void fileOutput(List<Token> tokenList, String filename) throws IOException {
        File outputFile = new File(filename.split("\\.")[0] + "_result.txt");
        if (!outputFile.exists())
            outputFile.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false));

        for (Token t : tokenList) {
            System.out.println(t.toString());
            bw.write(t.toString());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }
}
