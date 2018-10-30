package util;

public class SpecificWords {
    // 字母为80，整数为81，小数为82，任意字符串为83

    // 保留词 20个(0~29)
    public static final String[] reservedWords = {"if", "else", "while", "throw", "this", "int", "String", "char",
            "double", "float", "this", "static", "public", "private", "default", "switch", "catch", "void", "try",
            "return"};

    // 运算符 24个(30~59)
    public static final String[] operators = {"+", "-", "*", "/", "++", "--", "==", "!=", ">", "<", ">=", "<=",
            "&&", "||", "!", "&", "|", "^", "~", "<<", ">>", "-=", "+=", "="};

    // 边界符 17个(60~79)
    public static final char[] divisions = {'<','>','(',')','{','}','[',']','\'','"',',',';','?','/','\\',':','.'};
}
