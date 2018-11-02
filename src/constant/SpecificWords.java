package constant;

public class SpecificWords {
    // 字母为80，整数为81，小数为82，任意字符串为83

    // 保留词 21个(0~29)
    public static final String[] reservedWords = {"if", "else", "while", "throw", "this", "int", "String", "char",
            "double", "float", "static", "public", "private", "protected", "default", "switch", "case", "catch",
            "void", "try", "return", "class"};

    // 运算符 24个(30~59)
    public static final String[] operators = {"+", "-", "*", "/", "++", "--", "==", "!=", ">", "<", ">=", "<=",
            "&&", "||", "!", "&", "|", "^", "~", "<<", ">>", "-=", "+=", "="};

    // 边界符 17个(60~79)
    public static final char[] divisions = {'<','>','(',')','{','}','[',']','\'','"',',',';','?','/','\\',':','.'};
}
