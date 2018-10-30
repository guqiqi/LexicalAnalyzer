package lexicalAnalyzer;

import java.io.*;

public class ReadFile {
    // 处理输入文件,转换成char数组,以\0结尾
    public char[] readFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
        String line;
        int index = 0;
        char input[] = new char[500];
        char[] tempChars;

        while (null != (line = br.readLine())) {
            tempChars = line.toCharArray();
            for (char c : tempChars) {
                if (c == '\t') {
                    continue;
                }
                input[index++] = c;
            }
            input[index++] = '\n';
        }

        input[index] = '\0';
        br.close();

        return input;
    }
}
