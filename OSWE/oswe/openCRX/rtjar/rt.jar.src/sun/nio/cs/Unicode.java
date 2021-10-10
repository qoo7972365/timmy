/*    */ package sun.nio.cs;
/*    */ 
/*    */ import java.nio.charset.Charset;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class Unicode
/*    */   extends Charset
/*    */   implements HistoricallyNamedCharset
/*    */ {
/*    */   public Unicode(String paramString, String[] paramArrayOfString) {
/* 34 */     super(paramString, paramArrayOfString);
/*    */   }
/*    */   
/*    */   public boolean contains(Charset paramCharset) {
/* 38 */     return (paramCharset instanceof US_ASCII || paramCharset instanceof ISO_8859_1 || paramCharset instanceof ISO_8859_15 || paramCharset instanceof MS1252 || paramCharset instanceof UTF_8 || paramCharset instanceof UTF_16 || paramCharset instanceof UTF_16BE || paramCharset instanceof UTF_16LE || paramCharset instanceof UTF_16LE_BOM || paramCharset
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 47 */       .name().equals("GBK") || paramCharset
/* 48 */       .name().equals("GB18030") || paramCharset
/* 49 */       .name().equals("ISO-8859-2") || paramCharset
/* 50 */       .name().equals("ISO-8859-3") || paramCharset
/* 51 */       .name().equals("ISO-8859-4") || paramCharset
/* 52 */       .name().equals("ISO-8859-5") || paramCharset
/* 53 */       .name().equals("ISO-8859-6") || paramCharset
/* 54 */       .name().equals("ISO-8859-7") || paramCharset
/* 55 */       .name().equals("ISO-8859-8") || paramCharset
/* 56 */       .name().equals("ISO-8859-9") || paramCharset
/* 57 */       .name().equals("ISO-8859-13") || paramCharset
/* 58 */       .name().equals("JIS_X0201") || paramCharset
/* 59 */       .name().equals("x-JIS0208") || paramCharset
/* 60 */       .name().equals("JIS_X0212-1990") || paramCharset
/* 61 */       .name().equals("GB2312") || paramCharset
/* 62 */       .name().equals("EUC-KR") || paramCharset
/* 63 */       .name().equals("x-EUC-TW") || paramCharset
/* 64 */       .name().equals("EUC-JP") || paramCharset
/* 65 */       .name().equals("x-euc-jp-linux") || paramCharset
/* 66 */       .name().equals("KOI8-R") || paramCharset
/* 67 */       .name().equals("TIS-620") || paramCharset
/* 68 */       .name().equals("x-ISCII91") || paramCharset
/* 69 */       .name().equals("windows-1251") || paramCharset
/* 70 */       .name().equals("windows-1253") || paramCharset
/* 71 */       .name().equals("windows-1254") || paramCharset
/* 72 */       .name().equals("windows-1255") || paramCharset
/* 73 */       .name().equals("windows-1256") || paramCharset
/* 74 */       .name().equals("windows-1257") || paramCharset
/* 75 */       .name().equals("windows-1258") || paramCharset
/* 76 */       .name().equals("windows-932") || paramCharset
/* 77 */       .name().equals("x-mswin-936") || paramCharset
/* 78 */       .name().equals("x-windows-949") || paramCharset
/* 79 */       .name().equals("x-windows-950") || paramCharset
/* 80 */       .name().equals("windows-31j") || paramCharset
/* 81 */       .name().equals("Big5") || paramCharset
/* 82 */       .name().equals("Big5-HKSCS") || paramCharset
/* 83 */       .name().equals("x-MS950-HKSCS") || paramCharset
/* 84 */       .name().equals("ISO-2022-JP") || paramCharset
/* 85 */       .name().equals("ISO-2022-KR") || paramCharset
/* 86 */       .name().equals("x-ISO-2022-CN-CNS") || paramCharset
/* 87 */       .name().equals("x-ISO-2022-CN-GB") || paramCharset
/* 88 */       .name().equals("Big5-HKSCS") || paramCharset
/* 89 */       .name().equals("x-Johab") || paramCharset
/* 90 */       .name().equals("Shift_JIS"));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/Unicode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */