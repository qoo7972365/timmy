/*    */ package sun.reflect;
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
/*    */ public class SignatureIterator
/*    */ {
/*    */   private final String sig;
/*    */   private int idx;
/*    */   
/*    */   public SignatureIterator(String paramString) {
/* 35 */     this.sig = paramString;
/* 36 */     reset();
/*    */   }
/*    */   
/*    */   public void reset() {
/* 40 */     this.idx = 1;
/*    */   }
/*    */   
/*    */   public boolean atEnd() {
/* 44 */     return (this.sig.charAt(this.idx) == ')');
/*    */   }
/*    */   
/*    */   public String next() {
/* 48 */     if (atEnd()) return null; 
/* 49 */     char c = this.sig.charAt(this.idx);
/* 50 */     if (c != '[' && c != 'L') {
/* 51 */       this.idx++;
/* 52 */       return new String(new char[] { c });
/*    */     } 
/*    */     
/* 55 */     int i = this.idx;
/* 56 */     if (c == '[') {
/* 57 */       while ((c = this.sig.charAt(i)) == '[') {
/* 58 */         i++;
/*    */       }
/*    */     }
/*    */     
/* 62 */     if (c == 'L') {
/* 63 */       while (this.sig.charAt(i) != ';') {
/* 64 */         i++;
/*    */       }
/*    */     }
/*    */     
/* 68 */     int j = this.idx;
/* 69 */     this.idx = i + 1;
/* 70 */     return this.sig.substring(j, this.idx);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String returnType() {
/* 76 */     if (!atEnd()) {
/* 77 */       throw new InternalError("Illegal use of SignatureIterator");
/*    */     }
/* 79 */     return this.sig.substring(this.idx + 1, this.sig.length());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/SignatureIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */