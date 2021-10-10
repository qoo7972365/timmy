/*    */ package sun.tools.jar;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.FileReader;
/*    */ import java.io.IOException;
/*    */ import java.io.StreamTokenizer;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class CommandLine
/*    */ {
/*    */   public static String[] parse(String[] paramArrayOfString) throws IOException {
/* 58 */     ArrayList<String> arrayList = new ArrayList(paramArrayOfString.length);
/* 59 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 60 */       String str = paramArrayOfString[b];
/* 61 */       if (str.length() > 1 && str.charAt(0) == '@') {
/* 62 */         str = str.substring(1);
/* 63 */         if (str.charAt(0) == '@') {
/* 64 */           arrayList.add(str);
/*    */         } else {
/* 66 */           loadCmdFile(str, arrayList);
/*    */         } 
/*    */       } else {
/* 69 */         arrayList.add(str);
/*    */       } 
/*    */     } 
/* 72 */     return arrayList.<String>toArray(new String[arrayList.size()]);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static void loadCmdFile(String paramString, List<String> paramList) throws IOException {
/* 78 */     BufferedReader bufferedReader = new BufferedReader(new FileReader(paramString));
/* 79 */     StreamTokenizer streamTokenizer = new StreamTokenizer(bufferedReader);
/* 80 */     streamTokenizer.resetSyntax();
/* 81 */     streamTokenizer.wordChars(32, 255);
/* 82 */     streamTokenizer.whitespaceChars(0, 32);
/* 83 */     streamTokenizer.commentChar(35);
/* 84 */     streamTokenizer.quoteChar(34);
/* 85 */     streamTokenizer.quoteChar(39);
/* 86 */     while (streamTokenizer.nextToken() != -1) {
/* 87 */       paramList.add(streamTokenizer.sval);
/*    */     }
/* 89 */     bufferedReader.close();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/tools/jar/CommandLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */