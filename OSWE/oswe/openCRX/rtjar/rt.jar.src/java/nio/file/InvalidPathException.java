/*     */ package java.nio.file;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InvalidPathException
/*     */   extends IllegalArgumentException
/*     */ {
/*     */   static final long serialVersionUID = 4355821422286746137L;
/*     */   private String input;
/*     */   private int index;
/*     */   
/*     */   public InvalidPathException(String paramString1, String paramString2, int paramInt) {
/*  58 */     super(paramString2);
/*  59 */     if (paramString1 == null || paramString2 == null)
/*  60 */       throw new NullPointerException(); 
/*  61 */     if (paramInt < -1)
/*  62 */       throw new IllegalArgumentException(); 
/*  63 */     this.input = paramString1;
/*  64 */     this.index = paramInt;
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
/*     */ 
/*     */   
/*     */   public InvalidPathException(String paramString1, String paramString2) {
/*  78 */     this(paramString1, paramString2, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInput() {
/*  87 */     return this.input;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReason() {
/*  96 */     return super.getMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/* 106 */     return this.index;
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
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 120 */     StringBuffer stringBuffer = new StringBuffer();
/* 121 */     stringBuffer.append(getReason());
/* 122 */     if (this.index > -1) {
/* 123 */       stringBuffer.append(" at index ");
/* 124 */       stringBuffer.append(this.index);
/*     */     } 
/* 126 */     stringBuffer.append(": ");
/* 127 */     stringBuffer.append(this.input);
/* 128 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/InvalidPathException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */