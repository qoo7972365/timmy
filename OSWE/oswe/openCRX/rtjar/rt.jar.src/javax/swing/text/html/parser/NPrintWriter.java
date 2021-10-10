/*     */ package javax.swing.text.html.parser;
/*     */ 
/*     */ import java.io.PrintWriter;
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
/*     */ class NPrintWriter
/*     */   extends PrintWriter
/*     */ {
/* 168 */   private int numLines = 5;
/* 169 */   private int numPrinted = 0;
/*     */   
/*     */   public NPrintWriter(int paramInt) {
/* 172 */     super(System.out);
/* 173 */     this.numLines = paramInt;
/*     */   }
/*     */   
/*     */   public void println(char[] paramArrayOfchar) {
/* 177 */     if (this.numPrinted >= this.numLines) {
/*     */       return;
/*     */     }
/*     */     
/* 181 */     char[] arrayOfChar = null;
/*     */     
/* 183 */     for (byte b = 0; b < paramArrayOfchar.length; b++) {
/* 184 */       if (paramArrayOfchar[b] == '\n') {
/* 185 */         this.numPrinted++;
/*     */       }
/*     */       
/* 188 */       if (this.numPrinted == this.numLines) {
/* 189 */         System.arraycopy(paramArrayOfchar, 0, arrayOfChar, 0, b);
/*     */       }
/*     */     } 
/*     */     
/* 193 */     if (arrayOfChar != null) {
/* 194 */       print(arrayOfChar);
/*     */     }
/*     */     
/* 197 */     if (this.numPrinted == this.numLines) {
/*     */       return;
/*     */     }
/*     */     
/* 201 */     super.println(paramArrayOfchar);
/* 202 */     this.numPrinted++;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/parser/NPrintWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */