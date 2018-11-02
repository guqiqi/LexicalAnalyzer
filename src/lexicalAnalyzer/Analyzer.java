package lexicalAnalyzer;

import constant.SpecificWords;

import java.util.ArrayList;

public class Analyzer {
    public static ArrayList<Token> analyze(ArrayList<Character> chars) {
        // 结果的token序列
        ArrayList<Token> result = new ArrayList<>();
        // index记录源代码的字符数组扫描到的数组下标
        int index = 0;
        // row记录目前行数
        int row = 0;
        // temp用于存储临时的字符串，后面会来判断是否有保留字或者标识符
        String temp = "";
        // 当未扫描到终结符则一直往下扫描
        while (index < chars.size()) {
            // 当开头为字母时，可能为保留字或是标识符
            if (isLetter(chars.get(index))) {
                temp += chars.get(index);
                //当下一个字符不为字母或数字，则停止扫描，并将扫描结果存入temp
                while (chars.size() > index + 1 && (isLetter(chars.get(index + 1)) || isDigit(chars.get(index + 1)))) {
                    index++;
                    temp += chars.get(index);
                }
                //将temp与保留字数组匹配，匹配成功即为保留字，否则为标识符
                if (isReserve(temp) != -1)
                    result.add(new Token(isReserve(temp), temp, "保留字"));
                else
                    result.add(new Token(83, temp, "标识符"));
            }
            // 当开头为数字时，可能为整数或小数
            else if (isDigit(chars.get(index))) {
                temp += chars.get(index);
                while (chars.size() > index + 1 && isDigit(chars.get(index + 1))) {
                    index++;
                    temp += chars.get(index);
                }
                //若在数字后有小数点，继续判断
                if (chars.get(index + 1) == '.') {
                    index++;
                    //小数点后无数字，检测出错
                    if (chars.size() > index + 1 && !isDigit(chars.get(index + 1))) {
                        result.add(new Token("line " + row + " 此处有误，小数点后无数字"));
                    }
                    //小数点后有数字，检测为小数
                    else {
                        temp += chars.get(index);
                        while (chars.size() > index + 1 && isDigit(chars.get(index + 1))) {
                            index++;
                            temp += chars.get(index);
                        }
                    }
                    result.add(new Token(83, temp, "标识符"));
                }
                //无小数点，检测为整数
                else {
                    result.add(new Token(81, temp, "整数"));
                }
            }
            // 换行符
            else if (chars.get(index) == '\n') {
                row++;
            }
            // 既不是数字也不是字母也不是换行也不是空格，则为界符或运算符，跳过空格
            else if (chars.get(index) != ' ') {
                temp += chars.get(index);
                /*由于界符只有一个字符长度，则temp放入一个字符后直接开始匹配界符数组，
                 * 匹配成功则continue循环，匹配失败则继续扫描
                 */
                if (isDivide(temp) != -1) {
                    result.add(new Token(isDivide(temp), temp, "分界符"));
                    temp = "";
                    index++;
                    continue;
                }
                //判断是否为运算符
                else {
                    //若下一个字符也是符号类型则加入temp
                    while (chars.size() > index + 1 && (isDivide(chars.get(index + 1) + "") == -1) && (!isDigit(chars.get(index
                            + 1))) && (!isLetter(chars.get(index + 1))) && chars.get(index + 1) != ' ') {
                        index++;
                        temp = temp + chars.get(index);
                    }

                    //与运算符数组匹配，匹配成功，则为运算符，失败，则可能出现了检测不了的字符。
                    if (isOperator(temp) != -1)
                        result.add(new Token(isOperator(temp), temp, "运算符"));
                    else
                        result.add(new Token("line " + row + " 无法识别，可能为中文字符"));
                }

            }
            temp = "";
            index++;
        }

        return result;
    }


    private static char[] removeComment(char[] chars) {
        char[] newChar = new char[10000];
        int index = 0;
        if (chars.length != 0) {
            for (int i = 0; i < chars.length; i++) {
                //去掉//注释后的一整行
                if (chars[i] == '/' && i < chars.length - 1 && chars[i + 1] == '/') {
                    while (chars[i] != '\n') {
                        i++;
                    }
                }
                //去掉/**/型注释中间的字符，若只检测到/*，未检测到*/，则提示注释有误
                if (chars[i] == '/' && chars[i + 1] == '*') {
                    i = i + 2;
                    while (chars[i] != '*' || chars[i + 1] != '/') {
                        i++;
                        if (i == (chars.length - 1)) {
                            System.out.println("注释有误，未找到注释尾");
                            char[] error = {};
                            return error;
                        }
                    }
                    i = i + 2;
                }
                if (chars[i] != '\n' && chars[i] != '\r' && chars[i] != '\t') {
                    newChar[index] = chars[i];
                    index++;
                }
            }
            index++;
            newChar[index] = '\0';
        }
        return newChar;
    }


    // 判断是否为字符
    private static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c < 'Z');
    }

    //判断是否为保留字,并返回编号
    private static int isReserve(String s) {
        String[] reservedWords = SpecificWords.reservedWords;
        int index = -1;
        for (int i = 0; i < reservedWords.length; i++) {
            if (s.equals(reservedWords[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    //判断是否为运算符,并返回编号
    private static int isOperator(String s) {
        String[] operators = SpecificWords.operators;
        int index = -1;
        for (int i = 0; i < operators.length; i++) {
            if (s.equals(operators[i])) {
                index = i + 30;
                break;
            }
        }
        return index;
    }

    //判断是否为界符,并返回编号
    private static int isDivide(String s) {
        char[] divisions = SpecificWords.divisions;
        int index = -1;
        for (int i = 0; i < divisions.length; i++) {
            if (s.equals(divisions[i] + "")) {
                index = i + 60;
                break;
            }
        }
        return index;
    }

    //判断是否为数字
    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
}
