/*     */ package java.net;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class URISyntaxException
/*     */   extends Exception
/*     */ {
/*     */   private static final long serialVersionUID = 2137979680897488891L;
/*     */   private String input;
/*     */   private int index;
/*     */   
/*     */   public URISyntaxException(String paramString1, String paramString2, int paramInt) {
/*  62 */     super(paramString2);
/*  63 */     if (paramString1 == null || paramString2 == null)
/*  64 */       throw new NullPointerException(); 
/*  65 */     if (paramInt < -1)
/*  66 */       throw new IllegalArgumentException(); 
/*  67 */     this.input = paramString1;
/*  68 */     this.index = paramInt;
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
/*     */   public URISyntaxException(String paramString1, String paramString2) {
/*  82 */     this(paramString1, paramString2, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInput() {
/*  91 */     return this.input;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReason() {
/* 100 */     return super.getMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/* 110 */     return this.index;
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
/* 124 */     StringBuffer stringBuffer = new StringBuffer();
/* 125 */     stringBuffer.append(getReason());
/* 126 */     if (this.index > -1) {
/* 127 */       stringBuffer.append(" at index ");
/* 128 */       stringBuffer.append(this.index);
/*     */     } 
/* 130 */     stringBuffer.append(": ");
/* 131 */     stringBuffer.append(this.input);
/* 132 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/URISyntaxException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */