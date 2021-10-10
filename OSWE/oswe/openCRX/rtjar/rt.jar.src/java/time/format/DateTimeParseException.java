/*     */ package java.time.format;
/*     */ 
/*     */ import java.time.DateTimeException;
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
/*     */ public class DateTimeParseException
/*     */   extends DateTimeException
/*     */ {
/*     */   private static final long serialVersionUID = 4304633501674722597L;
/*     */   private final String parsedString;
/*     */   private final int errorIndex;
/*     */   
/*     */   public DateTimeParseException(String paramString, CharSequence paramCharSequence, int paramInt) {
/* 100 */     super(paramString);
/* 101 */     this.parsedString = paramCharSequence.toString();
/* 102 */     this.errorIndex = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeParseException(String paramString, CharSequence paramCharSequence, int paramInt, Throwable paramThrowable) {
/* 114 */     super(paramString, paramThrowable);
/* 115 */     this.parsedString = paramCharSequence.toString();
/* 116 */     this.errorIndex = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParsedString() {
/* 126 */     return this.parsedString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getErrorIndex() {
/* 135 */     return this.errorIndex;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/format/DateTimeParseException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */