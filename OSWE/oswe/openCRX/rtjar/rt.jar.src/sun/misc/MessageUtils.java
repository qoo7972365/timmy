/*     */ package sun.misc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageUtils
/*     */ {
/*     */   public static String subst(String paramString1, String paramString2) {
/*  42 */     String[] arrayOfString = { paramString2 };
/*  43 */     return subst(paramString1, arrayOfString);
/*     */   }
/*     */   
/*     */   public static String subst(String paramString1, String paramString2, String paramString3) {
/*  47 */     String[] arrayOfString = { paramString2, paramString3 };
/*  48 */     return subst(paramString1, arrayOfString);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String subst(String paramString1, String paramString2, String paramString3, String paramString4) {
/*  53 */     String[] arrayOfString = { paramString2, paramString3, paramString4 };
/*  54 */     return subst(paramString1, arrayOfString);
/*     */   }
/*     */   
/*     */   public static String subst(String paramString, String[] paramArrayOfString) {
/*  58 */     StringBuffer stringBuffer = new StringBuffer();
/*  59 */     int i = paramString.length();
/*  60 */     for (byte b = 0; b && b < i; b++) {
/*  61 */       char c = paramString.charAt(b);
/*  62 */       if (c == '%') {
/*  63 */         if (b != i) {
/*  64 */           int j = Character.digit(paramString.charAt(b + 1), 10);
/*  65 */           if (j == -1) {
/*  66 */             stringBuffer.append(paramString.charAt(b + 1));
/*  67 */             b++;
/*  68 */           } else if (j < paramArrayOfString.length) {
/*  69 */             stringBuffer.append(paramArrayOfString[j]);
/*  70 */             b++;
/*     */           } 
/*     */         } 
/*     */       } else {
/*  74 */         stringBuffer.append(c);
/*     */       } 
/*     */     } 
/*  77 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public static String substProp(String paramString1, String paramString2) {
/*  81 */     return subst(System.getProperty(paramString1), paramString2);
/*     */   }
/*     */   
/*     */   public static String substProp(String paramString1, String paramString2, String paramString3) {
/*  85 */     return subst(System.getProperty(paramString1), paramString2, paramString3);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String substProp(String paramString1, String paramString2, String paramString3, String paramString4) {
/*  90 */     return subst(System.getProperty(paramString1), paramString2, paramString3, paramString4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static native void toStderr(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static native void toStdout(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void err(String paramString) {
/* 111 */     toStderr(paramString + "\n");
/*     */   }
/*     */   
/*     */   public static void out(String paramString) {
/* 115 */     toStdout(paramString + "\n");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void where() {
/* 121 */     Throwable throwable = new Throwable();
/* 122 */     StackTraceElement[] arrayOfStackTraceElement = throwable.getStackTrace();
/* 123 */     for (byte b = 1; b < arrayOfStackTraceElement.length; b++)
/* 124 */       toStderr("\t" + arrayOfStackTraceElement[b].toString() + "\n"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/MessageUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */