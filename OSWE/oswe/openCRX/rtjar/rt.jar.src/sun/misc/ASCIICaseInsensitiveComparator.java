/*    */ package sun.misc;
/*    */ 
/*    */ import java.util.Comparator;
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
/*    */ public class ASCIICaseInsensitiveComparator
/*    */   implements Comparator<String>
/*    */ {
/* 37 */   public static final Comparator<String> CASE_INSENSITIVE_ORDER = new ASCIICaseInsensitiveComparator();
/*    */ 
/*    */   
/*    */   public int compare(String paramString1, String paramString2) {
/* 41 */     int i = paramString1.length(), j = paramString2.length();
/* 42 */     int k = (i < j) ? i : j;
/* 43 */     for (byte b = 0; b < k; b++) {
/* 44 */       char c1 = paramString1.charAt(b);
/* 45 */       char c2 = paramString2.charAt(b);
/* 46 */       assert c1 <= '' && c2 <= '';
/* 47 */       if (c1 != c2) {
/* 48 */         c1 = (char)toLower(c1);
/* 49 */         c2 = (char)toLower(c2);
/* 50 */         if (c1 != c2) {
/* 51 */           return c1 - c2;
/*    */         }
/*    */       } 
/*    */     } 
/* 55 */     return i - j;
/*    */   }
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
/*    */   public static int lowerCaseHashCode(String paramString) {
/* 73 */     int i = 0;
/* 74 */     int j = paramString.length();
/*    */     
/* 76 */     for (byte b = 0; b < j; b++) {
/* 77 */       i = 31 * i + toLower(paramString.charAt(b));
/*    */     }
/*    */     
/* 80 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   static boolean isLower(int paramInt) {
/* 85 */     return ((paramInt - 97 | 122 - paramInt) >= 0);
/*    */   }
/*    */   
/*    */   static boolean isUpper(int paramInt) {
/* 89 */     return ((paramInt - 65 | 90 - paramInt) >= 0);
/*    */   }
/*    */   
/*    */   static int toLower(int paramInt) {
/* 93 */     return isUpper(paramInt) ? (paramInt + 32) : paramInt;
/*    */   }
/*    */   
/*    */   static int toUpper(int paramInt) {
/* 97 */     return isLower(paramInt) ? (paramInt - 32) : paramInt;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/ASCIICaseInsensitiveComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */