/*      */ package java.lang;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Character
/*      */   implements Serializable, Comparable<Character>
/*      */ {
/*      */   public static final int MIN_RADIX = 2;
/*      */   public static final int MAX_RADIX = 36;
/*      */   public static final char MIN_VALUE = '\000';
/*      */   public static final char MAX_VALUE = '￿';
/*  189 */   public static final Class<Character> TYPE = (Class)Class.getPrimitiveClass("char");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte UNASSIGNED = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte UPPERCASE_LETTER = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte LOWERCASE_LETTER = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte TITLECASE_LETTER = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte MODIFIER_LETTER = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte OTHER_LETTER = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte NON_SPACING_MARK = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte ENCLOSING_MARK = 7;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte COMBINING_SPACING_MARK = 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DECIMAL_DIGIT_NUMBER = 9;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte LETTER_NUMBER = 10;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte OTHER_NUMBER = 11;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte SPACE_SEPARATOR = 12;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte LINE_SEPARATOR = 13;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte PARAGRAPH_SEPARATOR = 14;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte CONTROL = 15;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte FORMAT = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte PRIVATE_USE = 18;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte SURROGATE = 19;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DASH_PUNCTUATION = 20;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte START_PUNCTUATION = 21;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte END_PUNCTUATION = 22;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte CONNECTOR_PUNCTUATION = 23;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte OTHER_PUNCTUATION = 24;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte MATH_SYMBOL = 25;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte CURRENCY_SYMBOL = 26;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte MODIFIER_SYMBOL = 27;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte OTHER_SYMBOL = 28;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte INITIAL_QUOTE_PUNCTUATION = 29;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte FINAL_QUOTE_PUNCTUATION = 30;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int ERROR = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_UNDEFINED = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_LEFT_TO_RIGHT = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_RIGHT_TO_LEFT = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_EUROPEAN_NUMBER = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_ARABIC_NUMBER = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_COMMON_NUMBER_SEPARATOR = 7;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_NONSPACING_MARK = 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_BOUNDARY_NEUTRAL = 9;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_PARAGRAPH_SEPARATOR = 10;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_SEGMENT_SEPARATOR = 11;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_WHITESPACE = 12;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_OTHER_NEUTRALS = 13;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING = 14;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE = 15;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE = 17;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte DIRECTIONALITY_POP_DIRECTIONAL_FORMAT = 18;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final char MIN_HIGH_SURROGATE = '?';
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final char MAX_HIGH_SURROGATE = '?';
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final char MIN_LOW_SURROGATE = '?';
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final char MAX_LOW_SURROGATE = '?';
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final char MIN_SURROGATE = '?';
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final char MAX_SURROGATE = '?';
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int MIN_SUPPLEMENTARY_CODE_POINT = 65536;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int MIN_CODE_POINT = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int MAX_CODE_POINT = 1114111;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final char value;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 3786198910865385080L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int SIZE = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int BYTES = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Subset
/*      */   {
/*      */     private String name;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Subset(String param1String) {
/*  614 */       if (param1String == null) {
/*  615 */         throw new NullPointerException("name");
/*      */       }
/*  617 */       this.name = param1String;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final boolean equals(Object param1Object) {
/*  628 */       return (this == param1Object);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int hashCode() {
/*  639 */       return super.hashCode();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final String toString() {
/*  646 */       return this.name;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class UnicodeBlock
/*      */     extends Subset
/*      */   {
/*  663 */     private static Map<String, UnicodeBlock> map = new HashMap<>(256);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private UnicodeBlock(String param1String) {
/*  670 */       super(param1String);
/*  671 */       map.put(param1String, this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private UnicodeBlock(String param1String1, String param1String2) {
/*  679 */       this(param1String1);
/*  680 */       map.put(param1String2, this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private UnicodeBlock(String param1String, String... param1VarArgs) {
/*  688 */       this(param1String);
/*  689 */       for (String str : param1VarArgs) {
/*  690 */         map.put(str, this);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  697 */     public static final UnicodeBlock BASIC_LATIN = new UnicodeBlock("BASIC_LATIN", new String[] { "BASIC LATIN", "BASICLATIN" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  706 */     public static final UnicodeBlock LATIN_1_SUPPLEMENT = new UnicodeBlock("LATIN_1_SUPPLEMENT", new String[] { "LATIN-1 SUPPLEMENT", "LATIN-1SUPPLEMENT" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  715 */     public static final UnicodeBlock LATIN_EXTENDED_A = new UnicodeBlock("LATIN_EXTENDED_A", new String[] { "LATIN EXTENDED-A", "LATINEXTENDED-A" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  724 */     public static final UnicodeBlock LATIN_EXTENDED_B = new UnicodeBlock("LATIN_EXTENDED_B", new String[] { "LATIN EXTENDED-B", "LATINEXTENDED-B" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  733 */     public static final UnicodeBlock IPA_EXTENSIONS = new UnicodeBlock("IPA_EXTENSIONS", new String[] { "IPA EXTENSIONS", "IPAEXTENSIONS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  742 */     public static final UnicodeBlock SPACING_MODIFIER_LETTERS = new UnicodeBlock("SPACING_MODIFIER_LETTERS", new String[] { "SPACING MODIFIER LETTERS", "SPACINGMODIFIERLETTERS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  751 */     public static final UnicodeBlock COMBINING_DIACRITICAL_MARKS = new UnicodeBlock("COMBINING_DIACRITICAL_MARKS", new String[] { "COMBINING DIACRITICAL MARKS", "COMBININGDIACRITICALMARKS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  763 */     public static final UnicodeBlock GREEK = new UnicodeBlock("GREEK", new String[] { "GREEK AND COPTIC", "GREEKANDCOPTIC" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  772 */     public static final UnicodeBlock CYRILLIC = new UnicodeBlock("CYRILLIC");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  779 */     public static final UnicodeBlock ARMENIAN = new UnicodeBlock("ARMENIAN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  786 */     public static final UnicodeBlock HEBREW = new UnicodeBlock("HEBREW");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  793 */     public static final UnicodeBlock ARABIC = new UnicodeBlock("ARABIC");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  800 */     public static final UnicodeBlock DEVANAGARI = new UnicodeBlock("DEVANAGARI");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  807 */     public static final UnicodeBlock BENGALI = new UnicodeBlock("BENGALI");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  814 */     public static final UnicodeBlock GURMUKHI = new UnicodeBlock("GURMUKHI");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  821 */     public static final UnicodeBlock GUJARATI = new UnicodeBlock("GUJARATI");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  828 */     public static final UnicodeBlock ORIYA = new UnicodeBlock("ORIYA");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  835 */     public static final UnicodeBlock TAMIL = new UnicodeBlock("TAMIL");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  842 */     public static final UnicodeBlock TELUGU = new UnicodeBlock("TELUGU");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  849 */     public static final UnicodeBlock KANNADA = new UnicodeBlock("KANNADA");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  856 */     public static final UnicodeBlock MALAYALAM = new UnicodeBlock("MALAYALAM");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  863 */     public static final UnicodeBlock THAI = new UnicodeBlock("THAI");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  870 */     public static final UnicodeBlock LAO = new UnicodeBlock("LAO");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  877 */     public static final UnicodeBlock TIBETAN = new UnicodeBlock("TIBETAN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  884 */     public static final UnicodeBlock GEORGIAN = new UnicodeBlock("GEORGIAN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  891 */     public static final UnicodeBlock HANGUL_JAMO = new UnicodeBlock("HANGUL_JAMO", new String[] { "HANGUL JAMO", "HANGULJAMO" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  900 */     public static final UnicodeBlock LATIN_EXTENDED_ADDITIONAL = new UnicodeBlock("LATIN_EXTENDED_ADDITIONAL", new String[] { "LATIN EXTENDED ADDITIONAL", "LATINEXTENDEDADDITIONAL" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  909 */     public static final UnicodeBlock GREEK_EXTENDED = new UnicodeBlock("GREEK_EXTENDED", new String[] { "GREEK EXTENDED", "GREEKEXTENDED" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  918 */     public static final UnicodeBlock GENERAL_PUNCTUATION = new UnicodeBlock("GENERAL_PUNCTUATION", new String[] { "GENERAL PUNCTUATION", "GENERALPUNCTUATION" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  928 */     public static final UnicodeBlock SUPERSCRIPTS_AND_SUBSCRIPTS = new UnicodeBlock("SUPERSCRIPTS_AND_SUBSCRIPTS", new String[] { "SUPERSCRIPTS AND SUBSCRIPTS", "SUPERSCRIPTSANDSUBSCRIPTS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  937 */     public static final UnicodeBlock CURRENCY_SYMBOLS = new UnicodeBlock("CURRENCY_SYMBOLS", new String[] { "CURRENCY SYMBOLS", "CURRENCYSYMBOLS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  949 */     public static final UnicodeBlock COMBINING_MARKS_FOR_SYMBOLS = new UnicodeBlock("COMBINING_MARKS_FOR_SYMBOLS", new String[] { "COMBINING DIACRITICAL MARKS FOR SYMBOLS", "COMBININGDIACRITICALMARKSFORSYMBOLS", "COMBINING MARKS FOR SYMBOLS", "COMBININGMARKSFORSYMBOLS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  960 */     public static final UnicodeBlock LETTERLIKE_SYMBOLS = new UnicodeBlock("LETTERLIKE_SYMBOLS", new String[] { "LETTERLIKE SYMBOLS", "LETTERLIKESYMBOLS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  969 */     public static final UnicodeBlock NUMBER_FORMS = new UnicodeBlock("NUMBER_FORMS", new String[] { "NUMBER FORMS", "NUMBERFORMS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  978 */     public static final UnicodeBlock ARROWS = new UnicodeBlock("ARROWS");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  985 */     public static final UnicodeBlock MATHEMATICAL_OPERATORS = new UnicodeBlock("MATHEMATICAL_OPERATORS", new String[] { "MATHEMATICAL OPERATORS", "MATHEMATICALOPERATORS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  994 */     public static final UnicodeBlock MISCELLANEOUS_TECHNICAL = new UnicodeBlock("MISCELLANEOUS_TECHNICAL", new String[] { "MISCELLANEOUS TECHNICAL", "MISCELLANEOUSTECHNICAL" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1003 */     public static final UnicodeBlock CONTROL_PICTURES = new UnicodeBlock("CONTROL_PICTURES", new String[] { "CONTROL PICTURES", "CONTROLPICTURES" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1012 */     public static final UnicodeBlock OPTICAL_CHARACTER_RECOGNITION = new UnicodeBlock("OPTICAL_CHARACTER_RECOGNITION", new String[] { "OPTICAL CHARACTER RECOGNITION", "OPTICALCHARACTERRECOGNITION" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1021 */     public static final UnicodeBlock ENCLOSED_ALPHANUMERICS = new UnicodeBlock("ENCLOSED_ALPHANUMERICS", new String[] { "ENCLOSED ALPHANUMERICS", "ENCLOSEDALPHANUMERICS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1030 */     public static final UnicodeBlock BOX_DRAWING = new UnicodeBlock("BOX_DRAWING", new String[] { "BOX DRAWING", "BOXDRAWING" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1039 */     public static final UnicodeBlock BLOCK_ELEMENTS = new UnicodeBlock("BLOCK_ELEMENTS", new String[] { "BLOCK ELEMENTS", "BLOCKELEMENTS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1048 */     public static final UnicodeBlock GEOMETRIC_SHAPES = new UnicodeBlock("GEOMETRIC_SHAPES", new String[] { "GEOMETRIC SHAPES", "GEOMETRICSHAPES" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1057 */     public static final UnicodeBlock MISCELLANEOUS_SYMBOLS = new UnicodeBlock("MISCELLANEOUS_SYMBOLS", new String[] { "MISCELLANEOUS SYMBOLS", "MISCELLANEOUSSYMBOLS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1066 */     public static final UnicodeBlock DINGBATS = new UnicodeBlock("DINGBATS");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1073 */     public static final UnicodeBlock CJK_SYMBOLS_AND_PUNCTUATION = new UnicodeBlock("CJK_SYMBOLS_AND_PUNCTUATION", new String[] { "CJK SYMBOLS AND PUNCTUATION", "CJKSYMBOLSANDPUNCTUATION" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1082 */     public static final UnicodeBlock HIRAGANA = new UnicodeBlock("HIRAGANA");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1089 */     public static final UnicodeBlock KATAKANA = new UnicodeBlock("KATAKANA");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1096 */     public static final UnicodeBlock BOPOMOFO = new UnicodeBlock("BOPOMOFO");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1103 */     public static final UnicodeBlock HANGUL_COMPATIBILITY_JAMO = new UnicodeBlock("HANGUL_COMPATIBILITY_JAMO", new String[] { "HANGUL COMPATIBILITY JAMO", "HANGULCOMPATIBILITYJAMO" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1112 */     public static final UnicodeBlock KANBUN = new UnicodeBlock("KANBUN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1119 */     public static final UnicodeBlock ENCLOSED_CJK_LETTERS_AND_MONTHS = new UnicodeBlock("ENCLOSED_CJK_LETTERS_AND_MONTHS", new String[] { "ENCLOSED CJK LETTERS AND MONTHS", "ENCLOSEDCJKLETTERSANDMONTHS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1128 */     public static final UnicodeBlock CJK_COMPATIBILITY = new UnicodeBlock("CJK_COMPATIBILITY", new String[] { "CJK COMPATIBILITY", "CJKCOMPATIBILITY" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1137 */     public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS", new String[] { "CJK UNIFIED IDEOGRAPHS", "CJKUNIFIEDIDEOGRAPHS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1146 */     public static final UnicodeBlock HANGUL_SYLLABLES = new UnicodeBlock("HANGUL_SYLLABLES", new String[] { "HANGUL SYLLABLES", "HANGULSYLLABLES" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1155 */     public static final UnicodeBlock PRIVATE_USE_AREA = new UnicodeBlock("PRIVATE_USE_AREA", new String[] { "PRIVATE USE AREA", "PRIVATEUSEAREA" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1165 */     public static final UnicodeBlock CJK_COMPATIBILITY_IDEOGRAPHS = new UnicodeBlock("CJK_COMPATIBILITY_IDEOGRAPHS", new String[] { "CJK COMPATIBILITY IDEOGRAPHS", "CJKCOMPATIBILITYIDEOGRAPHS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1174 */     public static final UnicodeBlock ALPHABETIC_PRESENTATION_FORMS = new UnicodeBlock("ALPHABETIC_PRESENTATION_FORMS", new String[] { "ALPHABETIC PRESENTATION FORMS", "ALPHABETICPRESENTATIONFORMS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1184 */     public static final UnicodeBlock ARABIC_PRESENTATION_FORMS_A = new UnicodeBlock("ARABIC_PRESENTATION_FORMS_A", new String[] { "ARABIC PRESENTATION FORMS-A", "ARABICPRESENTATIONFORMS-A" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1193 */     public static final UnicodeBlock COMBINING_HALF_MARKS = new UnicodeBlock("COMBINING_HALF_MARKS", new String[] { "COMBINING HALF MARKS", "COMBININGHALFMARKS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1202 */     public static final UnicodeBlock CJK_COMPATIBILITY_FORMS = new UnicodeBlock("CJK_COMPATIBILITY_FORMS", new String[] { "CJK COMPATIBILITY FORMS", "CJKCOMPATIBILITYFORMS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1211 */     public static final UnicodeBlock SMALL_FORM_VARIANTS = new UnicodeBlock("SMALL_FORM_VARIANTS", new String[] { "SMALL FORM VARIANTS", "SMALLFORMVARIANTS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1220 */     public static final UnicodeBlock ARABIC_PRESENTATION_FORMS_B = new UnicodeBlock("ARABIC_PRESENTATION_FORMS_B", new String[] { "ARABIC PRESENTATION FORMS-B", "ARABICPRESENTATIONFORMS-B" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1230 */     public static final UnicodeBlock HALFWIDTH_AND_FULLWIDTH_FORMS = new UnicodeBlock("HALFWIDTH_AND_FULLWIDTH_FORMS", new String[] { "HALFWIDTH AND FULLWIDTH FORMS", "HALFWIDTHANDFULLWIDTHFORMS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1239 */     public static final UnicodeBlock SPECIALS = new UnicodeBlock("SPECIALS");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/* 1251 */     public static final UnicodeBlock SURROGATES_AREA = new UnicodeBlock("SURROGATES_AREA");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1258 */     public static final UnicodeBlock SYRIAC = new UnicodeBlock("SYRIAC");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1265 */     public static final UnicodeBlock THAANA = new UnicodeBlock("THAANA");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1272 */     public static final UnicodeBlock SINHALA = new UnicodeBlock("SINHALA");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1279 */     public static final UnicodeBlock MYANMAR = new UnicodeBlock("MYANMAR");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1286 */     public static final UnicodeBlock ETHIOPIC = new UnicodeBlock("ETHIOPIC");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1293 */     public static final UnicodeBlock CHEROKEE = new UnicodeBlock("CHEROKEE");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1300 */     public static final UnicodeBlock UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS = new UnicodeBlock("UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS", new String[] { "UNIFIED CANADIAN ABORIGINAL SYLLABICS", "UNIFIEDCANADIANABORIGINALSYLLABICS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1309 */     public static final UnicodeBlock OGHAM = new UnicodeBlock("OGHAM");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1316 */     public static final UnicodeBlock RUNIC = new UnicodeBlock("RUNIC");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1323 */     public static final UnicodeBlock KHMER = new UnicodeBlock("KHMER");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1330 */     public static final UnicodeBlock MONGOLIAN = new UnicodeBlock("MONGOLIAN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1337 */     public static final UnicodeBlock BRAILLE_PATTERNS = new UnicodeBlock("BRAILLE_PATTERNS", new String[] { "BRAILLE PATTERNS", "BRAILLEPATTERNS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1346 */     public static final UnicodeBlock CJK_RADICALS_SUPPLEMENT = new UnicodeBlock("CJK_RADICALS_SUPPLEMENT", new String[] { "CJK RADICALS SUPPLEMENT", "CJKRADICALSSUPPLEMENT" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1355 */     public static final UnicodeBlock KANGXI_RADICALS = new UnicodeBlock("KANGXI_RADICALS", new String[] { "KANGXI RADICALS", "KANGXIRADICALS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1364 */     public static final UnicodeBlock IDEOGRAPHIC_DESCRIPTION_CHARACTERS = new UnicodeBlock("IDEOGRAPHIC_DESCRIPTION_CHARACTERS", new String[] { "IDEOGRAPHIC DESCRIPTION CHARACTERS", "IDEOGRAPHICDESCRIPTIONCHARACTERS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1373 */     public static final UnicodeBlock BOPOMOFO_EXTENDED = new UnicodeBlock("BOPOMOFO_EXTENDED", new String[] { "BOPOMOFO EXTENDED", "BOPOMOFOEXTENDED" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1382 */     public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A", new String[] { "CJK UNIFIED IDEOGRAPHS EXTENSION A", "CJKUNIFIEDIDEOGRAPHSEXTENSIONA" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1391 */     public static final UnicodeBlock YI_SYLLABLES = new UnicodeBlock("YI_SYLLABLES", new String[] { "YI SYLLABLES", "YISYLLABLES" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1400 */     public static final UnicodeBlock YI_RADICALS = new UnicodeBlock("YI_RADICALS", new String[] { "YI RADICALS", "YIRADICALS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1409 */     public static final UnicodeBlock CYRILLIC_SUPPLEMENTARY = new UnicodeBlock("CYRILLIC_SUPPLEMENTARY", new String[] { "CYRILLIC SUPPLEMENTARY", "CYRILLICSUPPLEMENTARY", "CYRILLIC SUPPLEMENT", "CYRILLICSUPPLEMENT" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1420 */     public static final UnicodeBlock TAGALOG = new UnicodeBlock("TAGALOG");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1427 */     public static final UnicodeBlock HANUNOO = new UnicodeBlock("HANUNOO");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1434 */     public static final UnicodeBlock BUHID = new UnicodeBlock("BUHID");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1441 */     public static final UnicodeBlock TAGBANWA = new UnicodeBlock("TAGBANWA");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1448 */     public static final UnicodeBlock LIMBU = new UnicodeBlock("LIMBU");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1455 */     public static final UnicodeBlock TAI_LE = new UnicodeBlock("TAI_LE", new String[] { "TAI LE", "TAILE" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1464 */     public static final UnicodeBlock KHMER_SYMBOLS = new UnicodeBlock("KHMER_SYMBOLS", new String[] { "KHMER SYMBOLS", "KHMERSYMBOLS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1473 */     public static final UnicodeBlock PHONETIC_EXTENSIONS = new UnicodeBlock("PHONETIC_EXTENSIONS", new String[] { "PHONETIC EXTENSIONS", "PHONETICEXTENSIONS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1482 */     public static final UnicodeBlock MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A = new UnicodeBlock("MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A", new String[] { "MISCELLANEOUS MATHEMATICAL SYMBOLS-A", "MISCELLANEOUSMATHEMATICALSYMBOLS-A" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1491 */     public static final UnicodeBlock SUPPLEMENTAL_ARROWS_A = new UnicodeBlock("SUPPLEMENTAL_ARROWS_A", new String[] { "SUPPLEMENTAL ARROWS-A", "SUPPLEMENTALARROWS-A" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1500 */     public static final UnicodeBlock SUPPLEMENTAL_ARROWS_B = new UnicodeBlock("SUPPLEMENTAL_ARROWS_B", new String[] { "SUPPLEMENTAL ARROWS-B", "SUPPLEMENTALARROWS-B" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1510 */     public static final UnicodeBlock MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B = new UnicodeBlock("MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B", new String[] { "MISCELLANEOUS MATHEMATICAL SYMBOLS-B", "MISCELLANEOUSMATHEMATICALSYMBOLS-B" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1520 */     public static final UnicodeBlock SUPPLEMENTAL_MATHEMATICAL_OPERATORS = new UnicodeBlock("SUPPLEMENTAL_MATHEMATICAL_OPERATORS", new String[] { "SUPPLEMENTAL MATHEMATICAL OPERATORS", "SUPPLEMENTALMATHEMATICALOPERATORS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1530 */     public static final UnicodeBlock MISCELLANEOUS_SYMBOLS_AND_ARROWS = new UnicodeBlock("MISCELLANEOUS_SYMBOLS_AND_ARROWS", new String[] { "MISCELLANEOUS SYMBOLS AND ARROWS", "MISCELLANEOUSSYMBOLSANDARROWS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1540 */     public static final UnicodeBlock KATAKANA_PHONETIC_EXTENSIONS = new UnicodeBlock("KATAKANA_PHONETIC_EXTENSIONS", new String[] { "KATAKANA PHONETIC EXTENSIONS", "KATAKANAPHONETICEXTENSIONS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1549 */     public static final UnicodeBlock YIJING_HEXAGRAM_SYMBOLS = new UnicodeBlock("YIJING_HEXAGRAM_SYMBOLS", new String[] { "YIJING HEXAGRAM SYMBOLS", "YIJINGHEXAGRAMSYMBOLS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1558 */     public static final UnicodeBlock VARIATION_SELECTORS = new UnicodeBlock("VARIATION_SELECTORS", new String[] { "VARIATION SELECTORS", "VARIATIONSELECTORS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1567 */     public static final UnicodeBlock LINEAR_B_SYLLABARY = new UnicodeBlock("LINEAR_B_SYLLABARY", new String[] { "LINEAR B SYLLABARY", "LINEARBSYLLABARY" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1576 */     public static final UnicodeBlock LINEAR_B_IDEOGRAMS = new UnicodeBlock("LINEAR_B_IDEOGRAMS", new String[] { "LINEAR B IDEOGRAMS", "LINEARBIDEOGRAMS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1585 */     public static final UnicodeBlock AEGEAN_NUMBERS = new UnicodeBlock("AEGEAN_NUMBERS", new String[] { "AEGEAN NUMBERS", "AEGEANNUMBERS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1594 */     public static final UnicodeBlock OLD_ITALIC = new UnicodeBlock("OLD_ITALIC", new String[] { "OLD ITALIC", "OLDITALIC" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1603 */     public static final UnicodeBlock GOTHIC = new UnicodeBlock("GOTHIC");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1610 */     public static final UnicodeBlock UGARITIC = new UnicodeBlock("UGARITIC");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1617 */     public static final UnicodeBlock DESERET = new UnicodeBlock("DESERET");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1624 */     public static final UnicodeBlock SHAVIAN = new UnicodeBlock("SHAVIAN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1631 */     public static final UnicodeBlock OSMANYA = new UnicodeBlock("OSMANYA");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1638 */     public static final UnicodeBlock CYPRIOT_SYLLABARY = new UnicodeBlock("CYPRIOT_SYLLABARY", new String[] { "CYPRIOT SYLLABARY", "CYPRIOTSYLLABARY" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1647 */     public static final UnicodeBlock BYZANTINE_MUSICAL_SYMBOLS = new UnicodeBlock("BYZANTINE_MUSICAL_SYMBOLS", new String[] { "BYZANTINE MUSICAL SYMBOLS", "BYZANTINEMUSICALSYMBOLS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1656 */     public static final UnicodeBlock MUSICAL_SYMBOLS = new UnicodeBlock("MUSICAL_SYMBOLS", new String[] { "MUSICAL SYMBOLS", "MUSICALSYMBOLS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1665 */     public static final UnicodeBlock TAI_XUAN_JING_SYMBOLS = new UnicodeBlock("TAI_XUAN_JING_SYMBOLS", new String[] { "TAI XUAN JING SYMBOLS", "TAIXUANJINGSYMBOLS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1675 */     public static final UnicodeBlock MATHEMATICAL_ALPHANUMERIC_SYMBOLS = new UnicodeBlock("MATHEMATICAL_ALPHANUMERIC_SYMBOLS", new String[] { "MATHEMATICAL ALPHANUMERIC SYMBOLS", "MATHEMATICALALPHANUMERICSYMBOLS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1685 */     public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B", new String[] { "CJK UNIFIED IDEOGRAPHS EXTENSION B", "CJKUNIFIEDIDEOGRAPHSEXTENSIONB" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1694 */     public static final UnicodeBlock CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT = new UnicodeBlock("CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT", new String[] { "CJK COMPATIBILITY IDEOGRAPHS SUPPLEMENT", "CJKCOMPATIBILITYIDEOGRAPHSSUPPLEMENT" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1703 */     public static final UnicodeBlock TAGS = new UnicodeBlock("TAGS");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1711 */     public static final UnicodeBlock VARIATION_SELECTORS_SUPPLEMENT = new UnicodeBlock("VARIATION_SELECTORS_SUPPLEMENT", new String[] { "VARIATION SELECTORS SUPPLEMENT", "VARIATIONSELECTORSSUPPLEMENT" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1721 */     public static final UnicodeBlock SUPPLEMENTARY_PRIVATE_USE_AREA_A = new UnicodeBlock("SUPPLEMENTARY_PRIVATE_USE_AREA_A", new String[] { "SUPPLEMENTARY PRIVATE USE AREA-A", "SUPPLEMENTARYPRIVATEUSEAREA-A" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1731 */     public static final UnicodeBlock SUPPLEMENTARY_PRIVATE_USE_AREA_B = new UnicodeBlock("SUPPLEMENTARY_PRIVATE_USE_AREA_B", new String[] { "SUPPLEMENTARY PRIVATE USE AREA-B", "SUPPLEMENTARYPRIVATEUSEAREA-B" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1743 */     public static final UnicodeBlock HIGH_SURROGATES = new UnicodeBlock("HIGH_SURROGATES", new String[] { "HIGH SURROGATES", "HIGHSURROGATES" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1756 */     public static final UnicodeBlock HIGH_PRIVATE_USE_SURROGATES = new UnicodeBlock("HIGH_PRIVATE_USE_SURROGATES", new String[] { "HIGH PRIVATE USE SURROGATES", "HIGHPRIVATEUSESURROGATES" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1768 */     public static final UnicodeBlock LOW_SURROGATES = new UnicodeBlock("LOW_SURROGATES", new String[] { "LOW SURROGATES", "LOWSURROGATES" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1777 */     public static final UnicodeBlock ARABIC_SUPPLEMENT = new UnicodeBlock("ARABIC_SUPPLEMENT", new String[] { "ARABIC SUPPLEMENT", "ARABICSUPPLEMENT" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1786 */     public static final UnicodeBlock NKO = new UnicodeBlock("NKO");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1793 */     public static final UnicodeBlock SAMARITAN = new UnicodeBlock("SAMARITAN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1800 */     public static final UnicodeBlock MANDAIC = new UnicodeBlock("MANDAIC");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1807 */     public static final UnicodeBlock ETHIOPIC_SUPPLEMENT = new UnicodeBlock("ETHIOPIC_SUPPLEMENT", new String[] { "ETHIOPIC SUPPLEMENT", "ETHIOPICSUPPLEMENT" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1817 */     public static final UnicodeBlock UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED = new UnicodeBlock("UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED", new String[] { "UNIFIED CANADIAN ABORIGINAL SYLLABICS EXTENDED", "UNIFIEDCANADIANABORIGINALSYLLABICSEXTENDED" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1826 */     public static final UnicodeBlock NEW_TAI_LUE = new UnicodeBlock("NEW_TAI_LUE", new String[] { "NEW TAI LUE", "NEWTAILUE" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1835 */     public static final UnicodeBlock BUGINESE = new UnicodeBlock("BUGINESE");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1842 */     public static final UnicodeBlock TAI_THAM = new UnicodeBlock("TAI_THAM", new String[] { "TAI THAM", "TAITHAM" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1851 */     public static final UnicodeBlock BALINESE = new UnicodeBlock("BALINESE");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1858 */     public static final UnicodeBlock SUNDANESE = new UnicodeBlock("SUNDANESE");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1865 */     public static final UnicodeBlock BATAK = new UnicodeBlock("BATAK");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1872 */     public static final UnicodeBlock LEPCHA = new UnicodeBlock("LEPCHA");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1879 */     public static final UnicodeBlock OL_CHIKI = new UnicodeBlock("OL_CHIKI", new String[] { "OL CHIKI", "OLCHIKI" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1888 */     public static final UnicodeBlock VEDIC_EXTENSIONS = new UnicodeBlock("VEDIC_EXTENSIONS", new String[] { "VEDIC EXTENSIONS", "VEDICEXTENSIONS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1898 */     public static final UnicodeBlock PHONETIC_EXTENSIONS_SUPPLEMENT = new UnicodeBlock("PHONETIC_EXTENSIONS_SUPPLEMENT", new String[] { "PHONETIC EXTENSIONS SUPPLEMENT", "PHONETICEXTENSIONSSUPPLEMENT" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1908 */     public static final UnicodeBlock COMBINING_DIACRITICAL_MARKS_SUPPLEMENT = new UnicodeBlock("COMBINING_DIACRITICAL_MARKS_SUPPLEMENT", new String[] { "COMBINING DIACRITICAL MARKS SUPPLEMENT", "COMBININGDIACRITICALMARKSSUPPLEMENT" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1917 */     public static final UnicodeBlock GLAGOLITIC = new UnicodeBlock("GLAGOLITIC");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1924 */     public static final UnicodeBlock LATIN_EXTENDED_C = new UnicodeBlock("LATIN_EXTENDED_C", new String[] { "LATIN EXTENDED-C", "LATINEXTENDED-C" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1933 */     public static final UnicodeBlock COPTIC = new UnicodeBlock("COPTIC");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1940 */     public static final UnicodeBlock GEORGIAN_SUPPLEMENT = new UnicodeBlock("GEORGIAN_SUPPLEMENT", new String[] { "GEORGIAN SUPPLEMENT", "GEORGIANSUPPLEMENT" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1949 */     public static final UnicodeBlock TIFINAGH = new UnicodeBlock("TIFINAGH");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1956 */     public static final UnicodeBlock ETHIOPIC_EXTENDED = new UnicodeBlock("ETHIOPIC_EXTENDED", new String[] { "ETHIOPIC EXTENDED", "ETHIOPICEXTENDED" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1965 */     public static final UnicodeBlock CYRILLIC_EXTENDED_A = new UnicodeBlock("CYRILLIC_EXTENDED_A", new String[] { "CYRILLIC EXTENDED-A", "CYRILLICEXTENDED-A" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1974 */     public static final UnicodeBlock SUPPLEMENTAL_PUNCTUATION = new UnicodeBlock("SUPPLEMENTAL_PUNCTUATION", new String[] { "SUPPLEMENTAL PUNCTUATION", "SUPPLEMENTALPUNCTUATION" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1983 */     public static final UnicodeBlock CJK_STROKES = new UnicodeBlock("CJK_STROKES", new String[] { "CJK STROKES", "CJKSTROKES" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1992 */     public static final UnicodeBlock LISU = new UnicodeBlock("LISU");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1999 */     public static final UnicodeBlock VAI = new UnicodeBlock("VAI");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2006 */     public static final UnicodeBlock CYRILLIC_EXTENDED_B = new UnicodeBlock("CYRILLIC_EXTENDED_B", new String[] { "CYRILLIC EXTENDED-B", "CYRILLICEXTENDED-B" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2015 */     public static final UnicodeBlock BAMUM = new UnicodeBlock("BAMUM");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2022 */     public static final UnicodeBlock MODIFIER_TONE_LETTERS = new UnicodeBlock("MODIFIER_TONE_LETTERS", new String[] { "MODIFIER TONE LETTERS", "MODIFIERTONELETTERS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2031 */     public static final UnicodeBlock LATIN_EXTENDED_D = new UnicodeBlock("LATIN_EXTENDED_D", new String[] { "LATIN EXTENDED-D", "LATINEXTENDED-D" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2040 */     public static final UnicodeBlock SYLOTI_NAGRI = new UnicodeBlock("SYLOTI_NAGRI", new String[] { "SYLOTI NAGRI", "SYLOTINAGRI" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2049 */     public static final UnicodeBlock COMMON_INDIC_NUMBER_FORMS = new UnicodeBlock("COMMON_INDIC_NUMBER_FORMS", new String[] { "COMMON INDIC NUMBER FORMS", "COMMONINDICNUMBERFORMS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2058 */     public static final UnicodeBlock PHAGS_PA = new UnicodeBlock("PHAGS_PA", "PHAGS-PA");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2066 */     public static final UnicodeBlock SAURASHTRA = new UnicodeBlock("SAURASHTRA");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2073 */     public static final UnicodeBlock DEVANAGARI_EXTENDED = new UnicodeBlock("DEVANAGARI_EXTENDED", new String[] { "DEVANAGARI EXTENDED", "DEVANAGARIEXTENDED" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2082 */     public static final UnicodeBlock KAYAH_LI = new UnicodeBlock("KAYAH_LI", new String[] { "KAYAH LI", "KAYAHLI" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2091 */     public static final UnicodeBlock REJANG = new UnicodeBlock("REJANG");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2098 */     public static final UnicodeBlock HANGUL_JAMO_EXTENDED_A = new UnicodeBlock("HANGUL_JAMO_EXTENDED_A", new String[] { "HANGUL JAMO EXTENDED-A", "HANGULJAMOEXTENDED-A" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2107 */     public static final UnicodeBlock JAVANESE = new UnicodeBlock("JAVANESE");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2114 */     public static final UnicodeBlock CHAM = new UnicodeBlock("CHAM");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2121 */     public static final UnicodeBlock MYANMAR_EXTENDED_A = new UnicodeBlock("MYANMAR_EXTENDED_A", new String[] { "MYANMAR EXTENDED-A", "MYANMAREXTENDED-A" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2130 */     public static final UnicodeBlock TAI_VIET = new UnicodeBlock("TAI_VIET", new String[] { "TAI VIET", "TAIVIET" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2139 */     public static final UnicodeBlock ETHIOPIC_EXTENDED_A = new UnicodeBlock("ETHIOPIC_EXTENDED_A", new String[] { "ETHIOPIC EXTENDED-A", "ETHIOPICEXTENDED-A" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2148 */     public static final UnicodeBlock MEETEI_MAYEK = new UnicodeBlock("MEETEI_MAYEK", new String[] { "MEETEI MAYEK", "MEETEIMAYEK" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2157 */     public static final UnicodeBlock HANGUL_JAMO_EXTENDED_B = new UnicodeBlock("HANGUL_JAMO_EXTENDED_B", new String[] { "HANGUL JAMO EXTENDED-B", "HANGULJAMOEXTENDED-B" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2166 */     public static final UnicodeBlock VERTICAL_FORMS = new UnicodeBlock("VERTICAL_FORMS", new String[] { "VERTICAL FORMS", "VERTICALFORMS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2175 */     public static final UnicodeBlock ANCIENT_GREEK_NUMBERS = new UnicodeBlock("ANCIENT_GREEK_NUMBERS", new String[] { "ANCIENT GREEK NUMBERS", "ANCIENTGREEKNUMBERS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2184 */     public static final UnicodeBlock ANCIENT_SYMBOLS = new UnicodeBlock("ANCIENT_SYMBOLS", new String[] { "ANCIENT SYMBOLS", "ANCIENTSYMBOLS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2193 */     public static final UnicodeBlock PHAISTOS_DISC = new UnicodeBlock("PHAISTOS_DISC", new String[] { "PHAISTOS DISC", "PHAISTOSDISC" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2202 */     public static final UnicodeBlock LYCIAN = new UnicodeBlock("LYCIAN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2209 */     public static final UnicodeBlock CARIAN = new UnicodeBlock("CARIAN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2216 */     public static final UnicodeBlock OLD_PERSIAN = new UnicodeBlock("OLD_PERSIAN", new String[] { "OLD PERSIAN", "OLDPERSIAN" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2225 */     public static final UnicodeBlock IMPERIAL_ARAMAIC = new UnicodeBlock("IMPERIAL_ARAMAIC", new String[] { "IMPERIAL ARAMAIC", "IMPERIALARAMAIC" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2234 */     public static final UnicodeBlock PHOENICIAN = new UnicodeBlock("PHOENICIAN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2241 */     public static final UnicodeBlock LYDIAN = new UnicodeBlock("LYDIAN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2248 */     public static final UnicodeBlock KHAROSHTHI = new UnicodeBlock("KHAROSHTHI");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2255 */     public static final UnicodeBlock OLD_SOUTH_ARABIAN = new UnicodeBlock("OLD_SOUTH_ARABIAN", new String[] { "OLD SOUTH ARABIAN", "OLDSOUTHARABIAN" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2264 */     public static final UnicodeBlock AVESTAN = new UnicodeBlock("AVESTAN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2271 */     public static final UnicodeBlock INSCRIPTIONAL_PARTHIAN = new UnicodeBlock("INSCRIPTIONAL_PARTHIAN", new String[] { "INSCRIPTIONAL PARTHIAN", "INSCRIPTIONALPARTHIAN" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2280 */     public static final UnicodeBlock INSCRIPTIONAL_PAHLAVI = new UnicodeBlock("INSCRIPTIONAL_PAHLAVI", new String[] { "INSCRIPTIONAL PAHLAVI", "INSCRIPTIONALPAHLAVI" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2289 */     public static final UnicodeBlock OLD_TURKIC = new UnicodeBlock("OLD_TURKIC", new String[] { "OLD TURKIC", "OLDTURKIC" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2298 */     public static final UnicodeBlock RUMI_NUMERAL_SYMBOLS = new UnicodeBlock("RUMI_NUMERAL_SYMBOLS", new String[] { "RUMI NUMERAL SYMBOLS", "RUMINUMERALSYMBOLS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2307 */     public static final UnicodeBlock BRAHMI = new UnicodeBlock("BRAHMI");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2314 */     public static final UnicodeBlock KAITHI = new UnicodeBlock("KAITHI");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2321 */     public static final UnicodeBlock CUNEIFORM = new UnicodeBlock("CUNEIFORM");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2329 */     public static final UnicodeBlock CUNEIFORM_NUMBERS_AND_PUNCTUATION = new UnicodeBlock("CUNEIFORM_NUMBERS_AND_PUNCTUATION", new String[] { "CUNEIFORM NUMBERS AND PUNCTUATION", "CUNEIFORMNUMBERSANDPUNCTUATION" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2338 */     public static final UnicodeBlock EGYPTIAN_HIEROGLYPHS = new UnicodeBlock("EGYPTIAN_HIEROGLYPHS", new String[] { "EGYPTIAN HIEROGLYPHS", "EGYPTIANHIEROGLYPHS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2347 */     public static final UnicodeBlock BAMUM_SUPPLEMENT = new UnicodeBlock("BAMUM_SUPPLEMENT", new String[] { "BAMUM SUPPLEMENT", "BAMUMSUPPLEMENT" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2356 */     public static final UnicodeBlock KANA_SUPPLEMENT = new UnicodeBlock("KANA_SUPPLEMENT", new String[] { "KANA SUPPLEMENT", "KANASUPPLEMENT" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2366 */     public static final UnicodeBlock ANCIENT_GREEK_MUSICAL_NOTATION = new UnicodeBlock("ANCIENT_GREEK_MUSICAL_NOTATION", new String[] { "ANCIENT GREEK MUSICAL NOTATION", "ANCIENTGREEKMUSICALNOTATION" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2375 */     public static final UnicodeBlock COUNTING_ROD_NUMERALS = new UnicodeBlock("COUNTING_ROD_NUMERALS", new String[] { "COUNTING ROD NUMERALS", "COUNTINGRODNUMERALS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2384 */     public static final UnicodeBlock MAHJONG_TILES = new UnicodeBlock("MAHJONG_TILES", new String[] { "MAHJONG TILES", "MAHJONGTILES" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2393 */     public static final UnicodeBlock DOMINO_TILES = new UnicodeBlock("DOMINO_TILES", new String[] { "DOMINO TILES", "DOMINOTILES" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2402 */     public static final UnicodeBlock PLAYING_CARDS = new UnicodeBlock("PLAYING_CARDS", new String[] { "PLAYING CARDS", "PLAYINGCARDS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2412 */     public static final UnicodeBlock ENCLOSED_ALPHANUMERIC_SUPPLEMENT = new UnicodeBlock("ENCLOSED_ALPHANUMERIC_SUPPLEMENT", new String[] { "ENCLOSED ALPHANUMERIC SUPPLEMENT", "ENCLOSEDALPHANUMERICSUPPLEMENT" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2422 */     public static final UnicodeBlock ENCLOSED_IDEOGRAPHIC_SUPPLEMENT = new UnicodeBlock("ENCLOSED_IDEOGRAPHIC_SUPPLEMENT", new String[] { "ENCLOSED IDEOGRAPHIC SUPPLEMENT", "ENCLOSEDIDEOGRAPHICSUPPLEMENT" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2432 */     public static final UnicodeBlock MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS = new UnicodeBlock("MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS", new String[] { "MISCELLANEOUS SYMBOLS AND PICTOGRAPHS", "MISCELLANEOUSSYMBOLSANDPICTOGRAPHS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2441 */     public static final UnicodeBlock EMOTICONS = new UnicodeBlock("EMOTICONS");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2448 */     public static final UnicodeBlock TRANSPORT_AND_MAP_SYMBOLS = new UnicodeBlock("TRANSPORT_AND_MAP_SYMBOLS", new String[] { "TRANSPORT AND MAP SYMBOLS", "TRANSPORTANDMAPSYMBOLS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2457 */     public static final UnicodeBlock ALCHEMICAL_SYMBOLS = new UnicodeBlock("ALCHEMICAL_SYMBOLS", new String[] { "ALCHEMICAL SYMBOLS", "ALCHEMICALSYMBOLS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2467 */     public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C", new String[] { "CJK UNIFIED IDEOGRAPHS EXTENSION C", "CJKUNIFIEDIDEOGRAPHSEXTENSIONC" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2477 */     public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D", new String[] { "CJK UNIFIED IDEOGRAPHS EXTENSION D", "CJKUNIFIEDIDEOGRAPHSEXTENSIOND" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2486 */     public static final UnicodeBlock ARABIC_EXTENDED_A = new UnicodeBlock("ARABIC_EXTENDED_A", new String[] { "ARABIC EXTENDED-A", "ARABICEXTENDED-A" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2495 */     public static final UnicodeBlock SUNDANESE_SUPPLEMENT = new UnicodeBlock("SUNDANESE_SUPPLEMENT", new String[] { "SUNDANESE SUPPLEMENT", "SUNDANESESUPPLEMENT" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2504 */     public static final UnicodeBlock MEETEI_MAYEK_EXTENSIONS = new UnicodeBlock("MEETEI_MAYEK_EXTENSIONS", new String[] { "MEETEI MAYEK EXTENSIONS", "MEETEIMAYEKEXTENSIONS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2513 */     public static final UnicodeBlock MEROITIC_HIEROGLYPHS = new UnicodeBlock("MEROITIC_HIEROGLYPHS", new String[] { "MEROITIC HIEROGLYPHS", "MEROITICHIEROGLYPHS" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2522 */     public static final UnicodeBlock MEROITIC_CURSIVE = new UnicodeBlock("MEROITIC_CURSIVE", new String[] { "MEROITIC CURSIVE", "MEROITICCURSIVE" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2531 */     public static final UnicodeBlock SORA_SOMPENG = new UnicodeBlock("SORA_SOMPENG", new String[] { "SORA SOMPENG", "SORASOMPENG" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2540 */     public static final UnicodeBlock CHAKMA = new UnicodeBlock("CHAKMA");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2547 */     public static final UnicodeBlock SHARADA = new UnicodeBlock("SHARADA");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2554 */     public static final UnicodeBlock TAKRI = new UnicodeBlock("TAKRI");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2561 */     public static final UnicodeBlock MIAO = new UnicodeBlock("MIAO");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2569 */     public static final UnicodeBlock ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS = new UnicodeBlock("ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS", new String[] { "ARABIC MATHEMATICAL ALPHABETIC SYMBOLS", "ARABICMATHEMATICALALPHABETICSYMBOLS" });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2574 */     private static final int[] blockStarts = new int[] { 0, 128, 256, 384, 592, 688, 768, 880, 1024, 1280, 1328, 1424, 1536, 1792, 1872, 1920, 1984, 2048, 2112, 2144, 2208, 2304, 2432, 2560, 2688, 2816, 2944, 3072, 3200, 3328, 3456, 3584, 3712, 3840, 4096, 4256, 4352, 4608, 4992, 5024, 5120, 5760, 5792, 5888, 5920, 5952, 5984, 6016, 6144, 6320, 6400, 6480, 6528, 6624, 6656, 6688, 6832, 6912, 7040, 7104, 7168, 7248, 7296, 7360, 7376, 7424, 7552, 7616, 7680, 7936, 8192, 8304, 8352, 8400, 8448, 8528, 8592, 8704, 8960, 9216, 9280, 9312, 9472, 9600, 9632, 9728, 9984, 10176, 10224, 10240, 10496, 10624, 10752, 11008, 11264, 11360, 11392, 11520, 11568, 11648, 11744, 11776, 11904, 12032, 12256, 12272, 12288, 12352, 12448, 12544, 12592, 12688, 12704, 12736, 12784, 12800, 13056, 13312, 19904, 19968, 40960, 42128, 42192, 42240, 42560, 42656, 42752, 42784, 43008, 43056, 43072, 43136, 43232, 43264, 43312, 43360, 43392, 43488, 43520, 43616, 43648, 43744, 43776, 43824, 43968, 44032, 55216, 55296, 56192, 56320, 57344, 63744, 64256, 64336, 65024, 65040, 65056, 65072, 65104, 65136, 65280, 65520, 65536, 65664, 65792, 65856, 65936, 66000, 66048, 66176, 66208, 66272, 66304, 66352, 66384, 66432, 66464, 66528, 66560, 66640, 66688, 66736, 67584, 67648, 67680, 67840, 67872, 67904, 67968, 68000, 68096, 68192, 68224, 68352, 68416, 68448, 68480, 68608, 68688, 69216, 69248, 69632, 69760, 69840, 69888, 69968, 70016, 70112, 71296, 71376, 73728, 74752, 74880, 77824, 78896, 92160, 92736, 93952, 94112, 110592, 110848, 118784, 119040, 119296, 119376, 119552, 119648, 119680, 119808, 120832, 126464, 126720, 126976, 127024, 127136, 127232, 127488, 127744, 128512, 128592, 128640, 128768, 128896, 131072, 173792, 173824, 177984, 178208, 194560, 195104, 917504, 917632, 917760, 918000, 983040, 1048576 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2833 */     private static final UnicodeBlock[] blocks = new UnicodeBlock[] { BASIC_LATIN, LATIN_1_SUPPLEMENT, LATIN_EXTENDED_A, LATIN_EXTENDED_B, IPA_EXTENSIONS, SPACING_MODIFIER_LETTERS, COMBINING_DIACRITICAL_MARKS, GREEK, CYRILLIC, CYRILLIC_SUPPLEMENTARY, ARMENIAN, HEBREW, ARABIC, SYRIAC, ARABIC_SUPPLEMENT, THAANA, NKO, SAMARITAN, MANDAIC, null, ARABIC_EXTENDED_A, DEVANAGARI, BENGALI, GURMUKHI, GUJARATI, ORIYA, TAMIL, TELUGU, KANNADA, MALAYALAM, SINHALA, THAI, LAO, TIBETAN, MYANMAR, GEORGIAN, HANGUL_JAMO, ETHIOPIC, ETHIOPIC_SUPPLEMENT, CHEROKEE, UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS, OGHAM, RUNIC, TAGALOG, HANUNOO, BUHID, TAGBANWA, KHMER, MONGOLIAN, UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED, LIMBU, TAI_LE, NEW_TAI_LUE, KHMER_SYMBOLS, BUGINESE, TAI_THAM, null, BALINESE, SUNDANESE, BATAK, LEPCHA, OL_CHIKI, null, SUNDANESE_SUPPLEMENT, VEDIC_EXTENSIONS, PHONETIC_EXTENSIONS, PHONETIC_EXTENSIONS_SUPPLEMENT, COMBINING_DIACRITICAL_MARKS_SUPPLEMENT, LATIN_EXTENDED_ADDITIONAL, GREEK_EXTENDED, GENERAL_PUNCTUATION, SUPERSCRIPTS_AND_SUBSCRIPTS, CURRENCY_SYMBOLS, COMBINING_MARKS_FOR_SYMBOLS, LETTERLIKE_SYMBOLS, NUMBER_FORMS, ARROWS, MATHEMATICAL_OPERATORS, MISCELLANEOUS_TECHNICAL, CONTROL_PICTURES, OPTICAL_CHARACTER_RECOGNITION, ENCLOSED_ALPHANUMERICS, BOX_DRAWING, BLOCK_ELEMENTS, GEOMETRIC_SHAPES, MISCELLANEOUS_SYMBOLS, DINGBATS, MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A, SUPPLEMENTAL_ARROWS_A, BRAILLE_PATTERNS, SUPPLEMENTAL_ARROWS_B, MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B, SUPPLEMENTAL_MATHEMATICAL_OPERATORS, MISCELLANEOUS_SYMBOLS_AND_ARROWS, GLAGOLITIC, LATIN_EXTENDED_C, COPTIC, GEORGIAN_SUPPLEMENT, TIFINAGH, ETHIOPIC_EXTENDED, CYRILLIC_EXTENDED_A, SUPPLEMENTAL_PUNCTUATION, CJK_RADICALS_SUPPLEMENT, KANGXI_RADICALS, null, IDEOGRAPHIC_DESCRIPTION_CHARACTERS, CJK_SYMBOLS_AND_PUNCTUATION, HIRAGANA, KATAKANA, BOPOMOFO, HANGUL_COMPATIBILITY_JAMO, KANBUN, BOPOMOFO_EXTENDED, CJK_STROKES, KATAKANA_PHONETIC_EXTENSIONS, ENCLOSED_CJK_LETTERS_AND_MONTHS, CJK_COMPATIBILITY, CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A, YIJING_HEXAGRAM_SYMBOLS, CJK_UNIFIED_IDEOGRAPHS, YI_SYLLABLES, YI_RADICALS, LISU, VAI, CYRILLIC_EXTENDED_B, BAMUM, MODIFIER_TONE_LETTERS, LATIN_EXTENDED_D, SYLOTI_NAGRI, COMMON_INDIC_NUMBER_FORMS, PHAGS_PA, SAURASHTRA, DEVANAGARI_EXTENDED, KAYAH_LI, REJANG, HANGUL_JAMO_EXTENDED_A, JAVANESE, null, CHAM, MYANMAR_EXTENDED_A, TAI_VIET, MEETEI_MAYEK_EXTENSIONS, ETHIOPIC_EXTENDED_A, null, MEETEI_MAYEK, HANGUL_SYLLABLES, HANGUL_JAMO_EXTENDED_B, HIGH_SURROGATES, HIGH_PRIVATE_USE_SURROGATES, LOW_SURROGATES, PRIVATE_USE_AREA, CJK_COMPATIBILITY_IDEOGRAPHS, ALPHABETIC_PRESENTATION_FORMS, ARABIC_PRESENTATION_FORMS_A, VARIATION_SELECTORS, VERTICAL_FORMS, COMBINING_HALF_MARKS, CJK_COMPATIBILITY_FORMS, SMALL_FORM_VARIANTS, ARABIC_PRESENTATION_FORMS_B, HALFWIDTH_AND_FULLWIDTH_FORMS, SPECIALS, LINEAR_B_SYLLABARY, LINEAR_B_IDEOGRAMS, AEGEAN_NUMBERS, ANCIENT_GREEK_NUMBERS, ANCIENT_SYMBOLS, PHAISTOS_DISC, null, LYCIAN, CARIAN, null, OLD_ITALIC, GOTHIC, null, UGARITIC, OLD_PERSIAN, null, DESERET, SHAVIAN, OSMANYA, null, CYPRIOT_SYLLABARY, IMPERIAL_ARAMAIC, null, PHOENICIAN, LYDIAN, null, MEROITIC_HIEROGLYPHS, MEROITIC_CURSIVE, KHAROSHTHI, OLD_SOUTH_ARABIAN, null, AVESTAN, INSCRIPTIONAL_PARTHIAN, INSCRIPTIONAL_PAHLAVI, null, OLD_TURKIC, null, RUMI_NUMERAL_SYMBOLS, null, BRAHMI, KAITHI, SORA_SOMPENG, CHAKMA, null, SHARADA, null, TAKRI, null, CUNEIFORM, CUNEIFORM_NUMBERS_AND_PUNCTUATION, null, EGYPTIAN_HIEROGLYPHS, null, BAMUM_SUPPLEMENT, null, MIAO, null, KANA_SUPPLEMENT, null, BYZANTINE_MUSICAL_SYMBOLS, MUSICAL_SYMBOLS, ANCIENT_GREEK_MUSICAL_NOTATION, null, TAI_XUAN_JING_SYMBOLS, COUNTING_ROD_NUMERALS, null, MATHEMATICAL_ALPHANUMERIC_SYMBOLS, null, ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS, null, MAHJONG_TILES, DOMINO_TILES, PLAYING_CARDS, ENCLOSED_ALPHANUMERIC_SUPPLEMENT, ENCLOSED_IDEOGRAPHIC_SUPPLEMENT, MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS, EMOTICONS, null, TRANSPORT_AND_MAP_SYMBOLS, ALCHEMICAL_SYMBOLS, null, CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B, null, CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C, CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D, null, CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT, null, TAGS, null, VARIATION_SELECTORS_SUPPLEMENT, null, SUPPLEMENTARY_PRIVATE_USE_AREA_A, SUPPLEMENTARY_PRIVATE_USE_AREA_B };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static UnicodeBlock of(char param1Char) {
/* 3110 */       return of(param1Char);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static UnicodeBlock of(int param1Int) {
/* 3130 */       if (!Character.isValidCodePoint(param1Int)) {
/* 3131 */         throw new IllegalArgumentException();
/*      */       }
/*      */ 
/*      */       
/* 3135 */       int j = 0;
/* 3136 */       int i = blockStarts.length;
/* 3137 */       int k = i / 2;
/*      */ 
/*      */       
/* 3140 */       while (i - j > 1) {
/* 3141 */         if (param1Int >= blockStarts[k]) {
/* 3142 */           j = k;
/*      */         } else {
/* 3144 */           i = k;
/*      */         } 
/* 3146 */         k = (i + j) / 2;
/*      */       } 
/* 3148 */       return blocks[k];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final UnicodeBlock forName(String param1String) {
/* 3188 */       UnicodeBlock unicodeBlock = map.get(param1String.toUpperCase(Locale.US));
/* 3189 */       if (unicodeBlock == null) {
/* 3190 */         throw new IllegalArgumentException();
/*      */       }
/* 3192 */       return unicodeBlock;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum UnicodeScript
/*      */   {
/* 3214 */     COMMON,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3219 */     LATIN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3224 */     GREEK,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3229 */     CYRILLIC,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3234 */     ARMENIAN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3239 */     HEBREW,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3244 */     ARABIC,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3249 */     SYRIAC,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3254 */     THAANA,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3259 */     DEVANAGARI,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3264 */     BENGALI,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3269 */     GURMUKHI,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3274 */     GUJARATI,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3279 */     ORIYA,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3284 */     TAMIL,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3289 */     TELUGU,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3294 */     KANNADA,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3299 */     MALAYALAM,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3304 */     SINHALA,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3309 */     THAI,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3314 */     LAO,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3319 */     TIBETAN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3324 */     MYANMAR,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3329 */     GEORGIAN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3334 */     HANGUL,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3339 */     ETHIOPIC,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3344 */     CHEROKEE,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3349 */     CANADIAN_ABORIGINAL,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3354 */     OGHAM,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3359 */     RUNIC,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3364 */     KHMER,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3369 */     MONGOLIAN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3374 */     HIRAGANA,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3379 */     KATAKANA,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3384 */     BOPOMOFO,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3389 */     HAN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3394 */     YI,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3399 */     OLD_ITALIC,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3404 */     GOTHIC,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3409 */     DESERET,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3414 */     INHERITED,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3419 */     TAGALOG,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3424 */     HANUNOO,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3429 */     BUHID,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3434 */     TAGBANWA,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3439 */     LIMBU,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3444 */     TAI_LE,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3449 */     LINEAR_B,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3454 */     UGARITIC,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3459 */     SHAVIAN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3464 */     OSMANYA,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3469 */     CYPRIOT,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3474 */     BRAILLE,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3479 */     BUGINESE,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3484 */     COPTIC,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3489 */     NEW_TAI_LUE,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3494 */     GLAGOLITIC,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3499 */     TIFINAGH,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3504 */     SYLOTI_NAGRI,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3509 */     OLD_PERSIAN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3514 */     KHAROSHTHI,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3519 */     BALINESE,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3524 */     CUNEIFORM,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3529 */     PHOENICIAN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3534 */     PHAGS_PA,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3539 */     NKO,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3544 */     SUNDANESE,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3549 */     BATAK,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3554 */     LEPCHA,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3559 */     OL_CHIKI,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3564 */     VAI,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3569 */     SAURASHTRA,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3574 */     KAYAH_LI,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3579 */     REJANG,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3584 */     LYCIAN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3589 */     CARIAN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3594 */     LYDIAN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3599 */     CHAM,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3604 */     TAI_THAM,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3609 */     TAI_VIET,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3614 */     AVESTAN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3619 */     EGYPTIAN_HIEROGLYPHS,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3624 */     SAMARITAN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3629 */     MANDAIC,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3634 */     LISU,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3639 */     BAMUM,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3644 */     JAVANESE,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3649 */     MEETEI_MAYEK,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3654 */     IMPERIAL_ARAMAIC,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3659 */     OLD_SOUTH_ARABIAN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3664 */     INSCRIPTIONAL_PARTHIAN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3669 */     INSCRIPTIONAL_PAHLAVI,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3674 */     OLD_TURKIC,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3679 */     BRAHMI,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3684 */     KAITHI,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3689 */     MEROITIC_HIEROGLYPHS,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3694 */     MEROITIC_CURSIVE,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3699 */     SORA_SOMPENG,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3704 */     CHAKMA,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3709 */     SHARADA,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3714 */     TAKRI,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3719 */     MIAO,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3724 */     UNKNOWN;
/*      */     
/* 3726 */     private static final int[] scriptStarts = new int[] { 0, 65, 91, 97, 123, 170, 171, 186, 187, 192, 215, 216, 247, 248, 697, 736, 741, 746, 748, 768, 880, 884, 885, 894, 900, 901, 902, 903, 904, 994, 1008, 1024, 1157, 1159, 1329, 1417, 1418, 1425, 1536, 1548, 1549, 1563, 1566, 1567, 1568, 1600, 1601, 1611, 1622, 1632, 1642, 1648, 1649, 1757, 1758, 1792, 1872, 1920, 1984, 2048, 2112, 2208, 2304, 2385, 2387, 2404, 2406, 2433, 2561, 2689, 2817, 2946, 3073, 3202, 3330, 3458, 3585, 3647, 3648, 3713, 3840, 4053, 4057, 4096, 4256, 4347, 4348, 4352, 4608, 5024, 5120, 5760, 5792, 5867, 5870, 5888, 5920, 5941, 5952, 5984, 6016, 6144, 6146, 6148, 6149, 6150, 6320, 6400, 6480, 6528, 6624, 6656, 6688, 6912, 7040, 7104, 7168, 7248, 7360, 7376, 7379, 7380, 7393, 7394, 7401, 7405, 7406, 7412, 7413, 7424, 7462, 7467, 7468, 7517, 7522, 7526, 7531, 7544, 7545, 7615, 7616, 7680, 7936, 8192, 8204, 8206, 8305, 8308, 8319, 8320, 8336, 8352, 8400, 8448, 8486, 8487, 8490, 8492, 8498, 8499, 8526, 8527, 8544, 8585, 10240, 10496, 11264, 11360, 11392, 11520, 11568, 11648, 11744, 11776, 11904, 12272, 12293, 12294, 12295, 12296, 12321, 12330, 12334, 12336, 12344, 12348, 12353, 12441, 12443, 12445, 12448, 12449, 12539, 12541, 12549, 12593, 12688, 12704, 12736, 12784, 12800, 12832, 12896, 12927, 13008, 13055, 13056, 13144, 13312, 19904, 19968, 40960, 42192, 42240, 42560, 42656, 42752, 42786, 42888, 42891, 43008, 43056, 43072, 43136, 43232, 43264, 43312, 43360, 43392, 43520, 43616, 43648, 43744, 43777, 43968, 44032, 55292, 63744, 64256, 64275, 64285, 64336, 64830, 64848, 65021, 65024, 65040, 65056, 65072, 65136, 65279, 65313, 65339, 65345, 65371, 65382, 65392, 65393, 65438, 65440, 65504, 65536, 65792, 65856, 65936, 66045, 66176, 66208, 66304, 66352, 66432, 66464, 66560, 66640, 66688, 67584, 67648, 67840, 67872, 67968, 68000, 68096, 68192, 68352, 68416, 68448, 68608, 69216, 69632, 69760, 69840, 69888, 70016, 71296, 73728, 77824, 92160, 93952, 110592, 110593, 118784, 119143, 119146, 119163, 119171, 119173, 119180, 119210, 119214, 119296, 119552, 126464, 126976, 127488, 127489, 131072, 917505, 917760, 918000 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4049 */     private static final UnicodeScript[] scripts = new UnicodeScript[] { COMMON, LATIN, COMMON, LATIN, COMMON, LATIN, COMMON, LATIN, COMMON, LATIN, COMMON, LATIN, COMMON, LATIN, COMMON, LATIN, COMMON, BOPOMOFO, COMMON, INHERITED, GREEK, COMMON, GREEK, COMMON, GREEK, COMMON, GREEK, COMMON, GREEK, COPTIC, GREEK, CYRILLIC, INHERITED, CYRILLIC, ARMENIAN, COMMON, ARMENIAN, HEBREW, ARABIC, COMMON, ARABIC, COMMON, ARABIC, COMMON, ARABIC, COMMON, ARABIC, INHERITED, ARABIC, COMMON, ARABIC, INHERITED, ARABIC, COMMON, ARABIC, SYRIAC, ARABIC, THAANA, NKO, SAMARITAN, MANDAIC, ARABIC, DEVANAGARI, INHERITED, DEVANAGARI, COMMON, DEVANAGARI, BENGALI, GURMUKHI, GUJARATI, ORIYA, TAMIL, TELUGU, KANNADA, MALAYALAM, SINHALA, THAI, COMMON, THAI, LAO, TIBETAN, COMMON, TIBETAN, MYANMAR, GEORGIAN, COMMON, GEORGIAN, HANGUL, ETHIOPIC, CHEROKEE, CANADIAN_ABORIGINAL, OGHAM, RUNIC, COMMON, RUNIC, TAGALOG, HANUNOO, COMMON, BUHID, TAGBANWA, KHMER, MONGOLIAN, COMMON, MONGOLIAN, COMMON, MONGOLIAN, CANADIAN_ABORIGINAL, LIMBU, TAI_LE, NEW_TAI_LUE, KHMER, BUGINESE, TAI_THAM, BALINESE, SUNDANESE, BATAK, LEPCHA, OL_CHIKI, SUNDANESE, INHERITED, COMMON, INHERITED, COMMON, INHERITED, COMMON, INHERITED, COMMON, INHERITED, COMMON, LATIN, GREEK, CYRILLIC, LATIN, GREEK, LATIN, GREEK, LATIN, CYRILLIC, LATIN, GREEK, INHERITED, LATIN, GREEK, COMMON, INHERITED, COMMON, LATIN, COMMON, LATIN, COMMON, LATIN, COMMON, INHERITED, COMMON, GREEK, COMMON, LATIN, COMMON, LATIN, COMMON, LATIN, COMMON, LATIN, COMMON, BRAILLE, COMMON, GLAGOLITIC, LATIN, COPTIC, GEORGIAN, TIFINAGH, ETHIOPIC, CYRILLIC, COMMON, HAN, COMMON, HAN, COMMON, HAN, COMMON, HAN, INHERITED, HANGUL, COMMON, HAN, COMMON, HIRAGANA, INHERITED, COMMON, HIRAGANA, COMMON, KATAKANA, COMMON, KATAKANA, BOPOMOFO, HANGUL, COMMON, BOPOMOFO, COMMON, KATAKANA, HANGUL, COMMON, HANGUL, COMMON, KATAKANA, COMMON, KATAKANA, COMMON, HAN, COMMON, HAN, YI, LISU, VAI, CYRILLIC, BAMUM, COMMON, LATIN, COMMON, LATIN, SYLOTI_NAGRI, COMMON, PHAGS_PA, SAURASHTRA, DEVANAGARI, KAYAH_LI, REJANG, HANGUL, JAVANESE, CHAM, MYANMAR, TAI_VIET, MEETEI_MAYEK, ETHIOPIC, MEETEI_MAYEK, HANGUL, UNKNOWN, HAN, LATIN, ARMENIAN, HEBREW, ARABIC, COMMON, ARABIC, COMMON, INHERITED, COMMON, INHERITED, COMMON, ARABIC, COMMON, LATIN, COMMON, LATIN, COMMON, KATAKANA, COMMON, KATAKANA, COMMON, HANGUL, COMMON, LINEAR_B, COMMON, GREEK, COMMON, INHERITED, LYCIAN, CARIAN, OLD_ITALIC, GOTHIC, UGARITIC, OLD_PERSIAN, DESERET, SHAVIAN, OSMANYA, CYPRIOT, IMPERIAL_ARAMAIC, PHOENICIAN, LYDIAN, MEROITIC_HIEROGLYPHS, MEROITIC_CURSIVE, KHAROSHTHI, OLD_SOUTH_ARABIAN, AVESTAN, INSCRIPTIONAL_PARTHIAN, INSCRIPTIONAL_PAHLAVI, OLD_TURKIC, ARABIC, BRAHMI, KAITHI, SORA_SOMPENG, CHAKMA, SHARADA, TAKRI, CUNEIFORM, EGYPTIAN_HIEROGLYPHS, BAMUM, MIAO, KATAKANA, HIRAGANA, COMMON, INHERITED, COMMON, INHERITED, COMMON, INHERITED, COMMON, INHERITED, COMMON, GREEK, COMMON, ARABIC, COMMON, HIRAGANA, COMMON, HAN, COMMON, INHERITED, UNKNOWN };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4373 */     private static HashMap<String, UnicodeScript> aliases = new HashMap<>(128); static {
/* 4374 */       aliases.put("ARAB", ARABIC);
/* 4375 */       aliases.put("ARMI", IMPERIAL_ARAMAIC);
/* 4376 */       aliases.put("ARMN", ARMENIAN);
/* 4377 */       aliases.put("AVST", AVESTAN);
/* 4378 */       aliases.put("BALI", BALINESE);
/* 4379 */       aliases.put("BAMU", BAMUM);
/* 4380 */       aliases.put("BATK", BATAK);
/* 4381 */       aliases.put("BENG", BENGALI);
/* 4382 */       aliases.put("BOPO", BOPOMOFO);
/* 4383 */       aliases.put("BRAI", BRAILLE);
/* 4384 */       aliases.put("BRAH", BRAHMI);
/* 4385 */       aliases.put("BUGI", BUGINESE);
/* 4386 */       aliases.put("BUHD", BUHID);
/* 4387 */       aliases.put("CAKM", CHAKMA);
/* 4388 */       aliases.put("CANS", CANADIAN_ABORIGINAL);
/* 4389 */       aliases.put("CARI", CARIAN);
/* 4390 */       aliases.put("CHAM", CHAM);
/* 4391 */       aliases.put("CHER", CHEROKEE);
/* 4392 */       aliases.put("COPT", COPTIC);
/* 4393 */       aliases.put("CPRT", CYPRIOT);
/* 4394 */       aliases.put("CYRL", CYRILLIC);
/* 4395 */       aliases.put("DEVA", DEVANAGARI);
/* 4396 */       aliases.put("DSRT", DESERET);
/* 4397 */       aliases.put("EGYP", EGYPTIAN_HIEROGLYPHS);
/* 4398 */       aliases.put("ETHI", ETHIOPIC);
/* 4399 */       aliases.put("GEOR", GEORGIAN);
/* 4400 */       aliases.put("GLAG", GLAGOLITIC);
/* 4401 */       aliases.put("GOTH", GOTHIC);
/* 4402 */       aliases.put("GREK", GREEK);
/* 4403 */       aliases.put("GUJR", GUJARATI);
/* 4404 */       aliases.put("GURU", GURMUKHI);
/* 4405 */       aliases.put("HANG", HANGUL);
/* 4406 */       aliases.put("HANI", HAN);
/* 4407 */       aliases.put("HANO", HANUNOO);
/* 4408 */       aliases.put("HEBR", HEBREW);
/* 4409 */       aliases.put("HIRA", HIRAGANA);
/*      */ 
/*      */       
/* 4412 */       aliases.put("ITAL", OLD_ITALIC);
/* 4413 */       aliases.put("JAVA", JAVANESE);
/* 4414 */       aliases.put("KALI", KAYAH_LI);
/* 4415 */       aliases.put("KANA", KATAKANA);
/* 4416 */       aliases.put("KHAR", KHAROSHTHI);
/* 4417 */       aliases.put("KHMR", KHMER);
/* 4418 */       aliases.put("KNDA", KANNADA);
/* 4419 */       aliases.put("KTHI", KAITHI);
/* 4420 */       aliases.put("LANA", TAI_THAM);
/* 4421 */       aliases.put("LAOO", LAO);
/* 4422 */       aliases.put("LATN", LATIN);
/* 4423 */       aliases.put("LEPC", LEPCHA);
/* 4424 */       aliases.put("LIMB", LIMBU);
/* 4425 */       aliases.put("LINB", LINEAR_B);
/* 4426 */       aliases.put("LISU", LISU);
/* 4427 */       aliases.put("LYCI", LYCIAN);
/* 4428 */       aliases.put("LYDI", LYDIAN);
/* 4429 */       aliases.put("MAND", MANDAIC);
/* 4430 */       aliases.put("MERC", MEROITIC_CURSIVE);
/* 4431 */       aliases.put("MERO", MEROITIC_HIEROGLYPHS);
/* 4432 */       aliases.put("MLYM", MALAYALAM);
/* 4433 */       aliases.put("MONG", MONGOLIAN);
/* 4434 */       aliases.put("MTEI", MEETEI_MAYEK);
/* 4435 */       aliases.put("MYMR", MYANMAR);
/* 4436 */       aliases.put("NKOO", NKO);
/* 4437 */       aliases.put("OGAM", OGHAM);
/* 4438 */       aliases.put("OLCK", OL_CHIKI);
/* 4439 */       aliases.put("ORKH", OLD_TURKIC);
/* 4440 */       aliases.put("ORYA", ORIYA);
/* 4441 */       aliases.put("OSMA", OSMANYA);
/* 4442 */       aliases.put("PHAG", PHAGS_PA);
/* 4443 */       aliases.put("PLRD", MIAO);
/* 4444 */       aliases.put("PHLI", INSCRIPTIONAL_PAHLAVI);
/* 4445 */       aliases.put("PHNX", PHOENICIAN);
/* 4446 */       aliases.put("PRTI", INSCRIPTIONAL_PARTHIAN);
/* 4447 */       aliases.put("RJNG", REJANG);
/* 4448 */       aliases.put("RUNR", RUNIC);
/* 4449 */       aliases.put("SAMR", SAMARITAN);
/* 4450 */       aliases.put("SARB", OLD_SOUTH_ARABIAN);
/* 4451 */       aliases.put("SAUR", SAURASHTRA);
/* 4452 */       aliases.put("SHAW", SHAVIAN);
/* 4453 */       aliases.put("SHRD", SHARADA);
/* 4454 */       aliases.put("SINH", SINHALA);
/* 4455 */       aliases.put("SORA", SORA_SOMPENG);
/* 4456 */       aliases.put("SUND", SUNDANESE);
/* 4457 */       aliases.put("SYLO", SYLOTI_NAGRI);
/* 4458 */       aliases.put("SYRC", SYRIAC);
/* 4459 */       aliases.put("TAGB", TAGBANWA);
/* 4460 */       aliases.put("TALE", TAI_LE);
/* 4461 */       aliases.put("TAKR", TAKRI);
/* 4462 */       aliases.put("TALU", NEW_TAI_LUE);
/* 4463 */       aliases.put("TAML", TAMIL);
/* 4464 */       aliases.put("TAVT", TAI_VIET);
/* 4465 */       aliases.put("TELU", TELUGU);
/* 4466 */       aliases.put("TFNG", TIFINAGH);
/* 4467 */       aliases.put("TGLG", TAGALOG);
/* 4468 */       aliases.put("THAA", THAANA);
/* 4469 */       aliases.put("THAI", THAI);
/* 4470 */       aliases.put("TIBT", TIBETAN);
/* 4471 */       aliases.put("UGAR", UGARITIC);
/* 4472 */       aliases.put("VAII", VAI);
/* 4473 */       aliases.put("XPEO", OLD_PERSIAN);
/* 4474 */       aliases.put("XSUX", CUNEIFORM);
/* 4475 */       aliases.put("YIII", YI);
/* 4476 */       aliases.put("ZINH", INHERITED);
/* 4477 */       aliases.put("ZYYY", COMMON);
/* 4478 */       aliases.put("ZZZZ", UNKNOWN);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static UnicodeScript of(int param1Int) {
/* 4495 */       if (!Character.isValidCodePoint(param1Int))
/* 4496 */         throw new IllegalArgumentException(); 
/* 4497 */       int i = Character.getType(param1Int);
/*      */       
/* 4499 */       if (i == 0)
/* 4500 */         return UNKNOWN; 
/* 4501 */       int j = Arrays.binarySearch(scriptStarts, param1Int);
/* 4502 */       if (j < 0)
/* 4503 */         j = -j - 2; 
/* 4504 */       return scripts[j];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final UnicodeScript forName(String param1String) {
/* 4529 */       param1String = param1String.toUpperCase(Locale.ENGLISH);
/*      */       
/* 4531 */       UnicodeScript unicodeScript = aliases.get(param1String);
/* 4532 */       if (unicodeScript != null)
/* 4533 */         return unicodeScript; 
/* 4534 */       return valueOf(param1String);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Character(char paramChar) {
/* 4556 */     this.value = paramChar;
/*      */   }
/*      */ 
/*      */   
/*      */   private static class CharacterCache
/*      */   {
/* 4562 */     static final Character[] cache = new Character[128];
/*      */     
/*      */     static {
/* 4565 */       for (byte b = 0; b < cache.length; b++) {
/* 4566 */         cache[b] = new Character((char)b);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Character valueOf(char paramChar) {
/* 4588 */     if (paramChar <= '') {
/* 4589 */       return CharacterCache.cache[paramChar];
/*      */     }
/* 4591 */     return new Character(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char charValue() {
/* 4600 */     return this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 4611 */     return hashCode(this.value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int hashCode(char paramChar) {
/* 4624 */     return paramChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/* 4638 */     if (paramObject instanceof Character) {
/* 4639 */       return (this.value == ((Character)paramObject).charValue());
/*      */     }
/* 4641 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 4654 */     char[] arrayOfChar = { this.value };
/* 4655 */     return String.valueOf(arrayOfChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(char paramChar) {
/* 4668 */     return String.valueOf(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isValidCodePoint(int paramInt) {
/* 4686 */     int i = paramInt >>> 16;
/* 4687 */     return (i < 17);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isBmpCodePoint(int paramInt) {
/* 4702 */     return (paramInt >>> 16 == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSupplementaryCodePoint(int paramInt) {
/* 4721 */     return (paramInt >= 65536 && paramInt < 1114112);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isHighSurrogate(char paramChar) {
/* 4747 */     return (paramChar >= '?' && paramChar < '?');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLowSurrogate(char paramChar) {
/* 4770 */     return (paramChar >= '?' && paramChar < '');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSurrogate(char paramChar) {
/* 4794 */     return (paramChar >= '?' && paramChar < '');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSurrogatePair(char paramChar1, char paramChar2) {
/* 4816 */     return (isHighSurrogate(paramChar1) && isLowSurrogate(paramChar2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int charCount(int paramInt) {
/* 4836 */     return (paramInt >= 65536) ? 2 : 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int toCodePoint(char paramChar1, char paramChar2) {
/* 4856 */     return (paramChar1 << 10) + paramChar2 + -56613888;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int codePointAt(CharSequence paramCharSequence, int paramInt) {
/* 4884 */     char c = paramCharSequence.charAt(paramInt);
/* 4885 */     if (isHighSurrogate(c) && ++paramInt < paramCharSequence.length()) {
/* 4886 */       char c1 = paramCharSequence.charAt(paramInt);
/* 4887 */       if (isLowSurrogate(c1)) {
/* 4888 */         return toCodePoint(c, c1);
/*      */       }
/*      */     } 
/* 4891 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int codePointAt(char[] paramArrayOfchar, int paramInt) {
/* 4916 */     return codePointAtImpl(paramArrayOfchar, paramInt, paramArrayOfchar.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int codePointAt(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 4945 */     if (paramInt1 >= paramInt2 || paramInt2 < 0 || paramInt2 > paramArrayOfchar.length) {
/* 4946 */       throw new IndexOutOfBoundsException();
/*      */     }
/* 4948 */     return codePointAtImpl(paramArrayOfchar, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   static int codePointAtImpl(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 4953 */     char c = paramArrayOfchar[paramInt1];
/* 4954 */     if (isHighSurrogate(c) && ++paramInt1 < paramInt2) {
/* 4955 */       char c1 = paramArrayOfchar[paramInt1];
/* 4956 */       if (isLowSurrogate(c1)) {
/* 4957 */         return toCodePoint(c, c1);
/*      */       }
/*      */     } 
/* 4960 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int codePointBefore(CharSequence paramCharSequence, int paramInt) {
/* 4985 */     char c = paramCharSequence.charAt(--paramInt);
/* 4986 */     if (isLowSurrogate(c) && paramInt > 0) {
/* 4987 */       char c1 = paramCharSequence.charAt(--paramInt);
/* 4988 */       if (isHighSurrogate(c1)) {
/* 4989 */         return toCodePoint(c1, c);
/*      */       }
/*      */     } 
/* 4992 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int codePointBefore(char[] paramArrayOfchar, int paramInt) {
/* 5017 */     return codePointBeforeImpl(paramArrayOfchar, paramInt, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int codePointBefore(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 5048 */     if (paramInt1 <= paramInt2 || paramInt2 < 0 || paramInt2 >= paramArrayOfchar.length) {
/* 5049 */       throw new IndexOutOfBoundsException();
/*      */     }
/* 5051 */     return codePointBeforeImpl(paramArrayOfchar, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   static int codePointBeforeImpl(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 5056 */     char c = paramArrayOfchar[--paramInt1];
/* 5057 */     if (isLowSurrogate(c) && paramInt1 > paramInt2) {
/* 5058 */       char c1 = paramArrayOfchar[--paramInt1];
/* 5059 */       if (isHighSurrogate(c1)) {
/* 5060 */         return toCodePoint(c1, c);
/*      */       }
/*      */     } 
/* 5063 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char highSurrogate(int paramInt) {
/* 5091 */     return (char)((paramInt >>> 10) + 55232);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char lowSurrogate(int paramInt) {
/* 5120 */     return (char)((paramInt & 0x3FF) + 56320);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int toChars(int paramInt1, char[] paramArrayOfchar, int paramInt2) {
/* 5155 */     if (isBmpCodePoint(paramInt1)) {
/* 5156 */       paramArrayOfchar[paramInt2] = (char)paramInt1;
/* 5157 */       return 1;
/* 5158 */     }  if (isValidCodePoint(paramInt1)) {
/* 5159 */       toSurrogates(paramInt1, paramArrayOfchar, paramInt2);
/* 5160 */       return 2;
/*      */     } 
/* 5162 */     throw new IllegalArgumentException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[] toChars(int paramInt) {
/* 5183 */     if (isBmpCodePoint(paramInt))
/* 5184 */       return new char[] { (char)paramInt }; 
/* 5185 */     if (isValidCodePoint(paramInt)) {
/* 5186 */       char[] arrayOfChar = new char[2];
/* 5187 */       toSurrogates(paramInt, arrayOfChar, 0);
/* 5188 */       return arrayOfChar;
/*      */     } 
/* 5190 */     throw new IllegalArgumentException();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void toSurrogates(int paramInt1, char[] paramArrayOfchar, int paramInt2) {
/* 5196 */     paramArrayOfchar[paramInt2 + 1] = lowSurrogate(paramInt1);
/* 5197 */     paramArrayOfchar[paramInt2] = highSurrogate(paramInt1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int codePointCount(CharSequence paramCharSequence, int paramInt1, int paramInt2) {
/* 5224 */     int i = paramCharSequence.length();
/* 5225 */     if (paramInt1 < 0 || paramInt2 > i || paramInt1 > paramInt2) {
/* 5226 */       throw new IndexOutOfBoundsException();
/*      */     }
/* 5228 */     int j = paramInt2 - paramInt1;
/* 5229 */     for (int k = paramInt1; k < paramInt2;) {
/* 5230 */       if (isHighSurrogate(paramCharSequence.charAt(k++)) && k < paramInt2 && 
/* 5231 */         isLowSurrogate(paramCharSequence.charAt(k))) {
/* 5232 */         j--;
/* 5233 */         k++;
/*      */       } 
/*      */     } 
/* 5236 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int codePointCount(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 5259 */     if (paramInt2 > paramArrayOfchar.length - paramInt1 || paramInt1 < 0 || paramInt2 < 0) {
/* 5260 */       throw new IndexOutOfBoundsException();
/*      */     }
/* 5262 */     return codePointCountImpl(paramArrayOfchar, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   static int codePointCountImpl(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 5266 */     int i = paramInt1 + paramInt2;
/* 5267 */     int j = paramInt2;
/* 5268 */     for (int k = paramInt1; k < i;) {
/* 5269 */       if (isHighSurrogate(paramArrayOfchar[k++]) && k < i && 
/* 5270 */         isLowSurrogate(paramArrayOfchar[k])) {
/* 5271 */         j--;
/* 5272 */         k++;
/*      */       } 
/*      */     } 
/* 5275 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int offsetByCodePoints(CharSequence paramCharSequence, int paramInt1, int paramInt2) {
/* 5302 */     int i = paramCharSequence.length();
/* 5303 */     if (paramInt1 < 0 || paramInt1 > i) {
/* 5304 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/* 5307 */     int j = paramInt1;
/* 5308 */     if (paramInt2 >= 0) {
/*      */       byte b;
/* 5310 */       for (b = 0; j < i && b < paramInt2; b++) {
/* 5311 */         if (isHighSurrogate(paramCharSequence.charAt(j++)) && j < i && 
/* 5312 */           isLowSurrogate(paramCharSequence.charAt(j))) {
/* 5313 */           j++;
/*      */         }
/*      */       } 
/* 5316 */       if (b < paramInt2) {
/* 5317 */         throw new IndexOutOfBoundsException();
/*      */       }
/*      */     } else {
/*      */       int k;
/* 5321 */       for (k = paramInt2; j > 0 && k < 0; k++) {
/* 5322 */         if (isLowSurrogate(paramCharSequence.charAt(--j)) && j > 0 && 
/* 5323 */           isHighSurrogate(paramCharSequence.charAt(j - 1))) {
/* 5324 */           j--;
/*      */         }
/*      */       } 
/* 5327 */       if (k < 0) {
/* 5328 */         throw new IndexOutOfBoundsException();
/*      */       }
/*      */     } 
/* 5331 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int offsetByCodePoints(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 5369 */     if (paramInt2 > paramArrayOfchar.length - paramInt1 || paramInt1 < 0 || paramInt2 < 0 || paramInt3 < paramInt1 || paramInt3 > paramInt1 + paramInt2)
/*      */     {
/* 5371 */       throw new IndexOutOfBoundsException();
/*      */     }
/* 5373 */     return offsetByCodePointsImpl(paramArrayOfchar, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */   
/*      */   static int offsetByCodePointsImpl(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 5378 */     int i = paramInt3;
/* 5379 */     if (paramInt4 >= 0) {
/* 5380 */       int j = paramInt1 + paramInt2;
/*      */       byte b;
/* 5382 */       for (b = 0; i < j && b < paramInt4; b++) {
/* 5383 */         if (isHighSurrogate(paramArrayOfchar[i++]) && i < j && 
/* 5384 */           isLowSurrogate(paramArrayOfchar[i])) {
/* 5385 */           i++;
/*      */         }
/*      */       } 
/* 5388 */       if (b < paramInt4) {
/* 5389 */         throw new IndexOutOfBoundsException();
/*      */       }
/*      */     } else {
/*      */       int j;
/* 5393 */       for (j = paramInt4; i > paramInt1 && j < 0; j++) {
/* 5394 */         if (isLowSurrogate(paramArrayOfchar[--i]) && i > paramInt1 && 
/* 5395 */           isHighSurrogate(paramArrayOfchar[i - 1])) {
/* 5396 */           i--;
/*      */         }
/*      */       } 
/* 5399 */       if (j < 0) {
/* 5400 */         throw new IndexOutOfBoundsException();
/*      */       }
/*      */     } 
/* 5403 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLowerCase(char paramChar) {
/* 5438 */     return isLowerCase(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLowerCase(int paramInt) {
/* 5470 */     return (getType(paramInt) == 2 || 
/* 5471 */       CharacterData.of(paramInt).isOtherLowercase(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isUpperCase(char paramChar) {
/* 5506 */     return isUpperCase(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isUpperCase(int paramInt) {
/* 5536 */     return (getType(paramInt) == 1 || 
/* 5537 */       CharacterData.of(paramInt).isOtherUppercase(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTitleCase(char paramChar) {
/* 5578 */     return isTitleCase(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTitleCase(int paramInt) {
/* 5614 */     return (getType(paramInt) == 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDigit(char paramChar) {
/* 5653 */     return isDigit(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDigit(int paramInt) {
/* 5687 */     return (getType(paramInt) == 9);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDefined(char paramChar) {
/* 5716 */     return isDefined(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDefined(int paramInt) {
/* 5740 */     return (getType(paramInt) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLetter(char paramChar) {
/* 5779 */     return isLetter(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLetter(int paramInt) {
/* 5812 */     return 
/*      */ 
/*      */ 
/*      */       
/* 5816 */       ((62 >> getType(paramInt) & 0x1) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLetterOrDigit(char paramChar) {
/* 5845 */     return isLetterOrDigit(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLetterOrDigit(int paramInt) {
/* 5866 */     return 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5871 */       ((574 >> getType(paramInt) & 0x1) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean isJavaLetter(char paramChar) {
/* 5905 */     return isJavaIdentifierStart(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean isJavaLetterOrDigit(char paramChar) {
/* 5944 */     return isJavaIdentifierPart(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAlphabetic(int paramInt) {
/* 5970 */     return ((1086 >> 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5975 */       getType(paramInt) & 0x1) != 0 || 
/* 5976 */       CharacterData.of(paramInt).isOtherAlphabetic(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isIdeographic(int paramInt) {
/* 5990 */     return CharacterData.of(paramInt).isIdeographic(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isJavaIdentifierStart(char paramChar) {
/* 6024 */     return isJavaIdentifierStart(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isJavaIdentifierStart(int paramInt) {
/* 6056 */     return CharacterData.of(paramInt).isJavaIdentifierStart(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isJavaIdentifierPart(char paramChar) {
/* 6096 */     return isJavaIdentifierPart(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isJavaIdentifierPart(int paramInt) {
/* 6132 */     return CharacterData.of(paramInt).isJavaIdentifierPart(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isUnicodeIdentifierStart(char paramChar) {
/* 6161 */     return isUnicodeIdentifierStart(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isUnicodeIdentifierStart(int paramInt) {
/* 6185 */     return CharacterData.of(paramInt).isUnicodeIdentifierStart(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isUnicodeIdentifierPart(char paramChar) {
/* 6220 */     return isUnicodeIdentifierPart(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isUnicodeIdentifierPart(int paramInt) {
/* 6249 */     return CharacterData.of(paramInt).isUnicodeIdentifierPart(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isIdentifierIgnorable(char paramChar) {
/* 6284 */     return isIdentifierIgnorable(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isIdentifierIgnorable(int paramInt) {
/* 6314 */     return CharacterData.of(paramInt).isIdentifierIgnorable(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char toLowerCase(char paramChar) {
/* 6345 */     return (char)toLowerCase(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int toLowerCase(int paramInt) {
/* 6374 */     return CharacterData.of(paramInt).toLowerCase(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char toUpperCase(char paramChar) {
/* 6405 */     return (char)toUpperCase(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int toUpperCase(int paramInt) {
/* 6434 */     return CharacterData.of(paramInt).toUpperCase(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char toTitleCase(char paramChar) {
/* 6466 */     return (char)toTitleCase(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int toTitleCase(int paramInt) {
/* 6493 */     return CharacterData.of(paramInt).toTitleCase(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int digit(char paramChar, int paramInt) {
/* 6547 */     return digit(paramChar, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int digit(int paramInt1, int paramInt2) {
/* 6599 */     return CharacterData.of(paramInt1).digit(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getNumericValue(char paramChar) {
/* 6637 */     return getNumericValue(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getNumericValue(int paramInt) {
/* 6670 */     return CharacterData.of(paramInt).getNumericValue(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean isSpace(char paramChar) {
/* 6699 */     return (paramChar <= ' ' && (4294981120L >> paramChar & 0x1L) != 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSpaceChar(char paramChar) {
/* 6732 */     return isSpaceChar(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSpaceChar(int paramInt) {
/* 6755 */     return 
/*      */       
/* 6757 */       ((28672 >> getType(paramInt) & 0x1) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isWhitespace(char paramChar) {
/* 6793 */     return isWhitespace(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isWhitespace(int paramInt) {
/* 6825 */     return CharacterData.of(paramInt).isWhitespace(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isISOControl(char paramChar) {
/* 6849 */     return isISOControl(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isISOControl(int paramInt) {
/* 6870 */     return (paramInt <= 159 && (paramInt >= 127 || paramInt >>> 5 == 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getType(char paramChar) {
/* 6918 */     return getType(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getType(int paramInt) {
/* 6960 */     return CharacterData.of(paramInt).getType(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char forDigit(int paramInt1, int paramInt2) {
/* 6988 */     if (paramInt1 >= paramInt2 || paramInt1 < 0) {
/* 6989 */       return Character.MIN_VALUE;
/*      */     }
/* 6991 */     if (paramInt2 < 2 || paramInt2 > 36) {
/* 6992 */       return Character.MIN_VALUE;
/*      */     }
/* 6994 */     if (paramInt1 < 10) {
/* 6995 */       return (char)(48 + paramInt1);
/*      */     }
/* 6997 */     return (char)(87 + paramInt1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte getDirectionality(char paramChar) {
/* 7038 */     return getDirectionality(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte getDirectionality(int paramInt) {
/* 7075 */     return CharacterData.of(paramInt).getDirectionality(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMirrored(char paramChar) {
/* 7098 */     return isMirrored(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMirrored(int paramInt) {
/* 7117 */     return CharacterData.of(paramInt).isMirrored(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(Character paramCharacter) {
/* 7136 */     return compare(this.value, paramCharacter.value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int compare(char paramChar1, char paramChar2) {
/* 7154 */     return paramChar1 - paramChar2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int toUpperCaseEx(int paramInt) {
/* 7173 */     assert isValidCodePoint(paramInt);
/* 7174 */     return CharacterData.of(paramInt).toUpperCaseEx(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static char[] toUpperCaseCharArray(int paramInt) {
/* 7190 */     assert isBmpCodePoint(paramInt);
/* 7191 */     return CharacterData.of(paramInt).toUpperCaseCharArray(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char reverseBytes(char paramChar) {
/* 7220 */     return (char)((paramChar & 0xFF00) >> 8 | paramChar << 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getName(int paramInt) {
/* 7252 */     if (!isValidCodePoint(paramInt)) {
/* 7253 */       throw new IllegalArgumentException();
/*      */     }
/* 7255 */     String str = CharacterName.get(paramInt);
/* 7256 */     if (str != null)
/* 7257 */       return str; 
/* 7258 */     if (getType(paramInt) == 0)
/* 7259 */       return null; 
/* 7260 */     UnicodeBlock unicodeBlock = UnicodeBlock.of(paramInt);
/* 7261 */     if (unicodeBlock != null) {
/* 7262 */       return unicodeBlock.toString().replace('_', ' ') + " " + 
/* 7263 */         Integer.toHexString(paramInt).toUpperCase(Locale.ENGLISH);
/*      */     }
/* 7265 */     return Integer.toHexString(paramInt).toUpperCase(Locale.ENGLISH);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/Character.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */