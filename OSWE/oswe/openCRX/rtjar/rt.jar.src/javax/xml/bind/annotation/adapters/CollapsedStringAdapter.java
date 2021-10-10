/*     */ package javax.xml.bind.annotation.adapters;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CollapsedStringAdapter
/*     */   extends XmlAdapter<String, String>
/*     */ {
/*     */   public String unmarshal(String text) {
/*  47 */     if (text == null) return null;
/*     */     
/*  49 */     int len = text.length();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     int s = 0;
/*  55 */     while (s < len && 
/*  56 */       !isWhiteSpace(text.charAt(s)))
/*     */     {
/*  58 */       s++;
/*     */     }
/*  60 */     if (s == len)
/*     */     {
/*  62 */       return text;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  67 */     StringBuilder result = new StringBuilder(len);
/*     */     
/*  69 */     if (s != 0) {
/*  70 */       for (int j = 0; j < s; j++)
/*  71 */         result.append(text.charAt(j)); 
/*  72 */       result.append(' ');
/*     */     } 
/*     */     
/*  75 */     boolean inStripMode = true;
/*  76 */     for (int i = s + 1; i < len; i++) {
/*  77 */       char ch = text.charAt(i);
/*  78 */       boolean b = isWhiteSpace(ch);
/*  79 */       if (!inStripMode || !b) {
/*     */ 
/*     */         
/*  82 */         inStripMode = b;
/*  83 */         if (inStripMode) {
/*  84 */           result.append(' ');
/*     */         } else {
/*  86 */           result.append(ch);
/*     */         } 
/*     */       } 
/*     */     } 
/*  90 */     len = result.length();
/*  91 */     if (len > 0 && result.charAt(len - 1) == ' ') {
/*  92 */       result.setLength(len - 1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  97 */     return result.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String marshal(String s) {
/* 106 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isWhiteSpace(char ch) {
/* 114 */     if (ch > ' ') return false;
/*     */ 
/*     */     
/* 117 */     return (ch == '\t' || ch == '\n' || ch == '\r' || ch == ' ');
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/annotation/adapters/CollapsedStringAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */