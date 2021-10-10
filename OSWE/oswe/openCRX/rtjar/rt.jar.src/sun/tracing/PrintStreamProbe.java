/*     */ package sun.tracing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PrintStreamProbe
/*     */   extends ProbeSkeleton
/*     */ {
/*     */   private PrintStreamProvider provider;
/*     */   private String name;
/*     */   
/*     */   PrintStreamProbe(PrintStreamProvider paramPrintStreamProvider, String paramString, Class<?>[] paramArrayOfClass) {
/*  92 */     super(paramArrayOfClass);
/*  93 */     this.provider = paramPrintStreamProvider;
/*  94 */     this.name = paramString;
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/*  98 */     return true;
/*     */   }
/*     */   
/*     */   public void uncheckedTrigger(Object[] paramArrayOfObject) {
/* 102 */     StringBuffer stringBuffer = new StringBuffer();
/* 103 */     stringBuffer.append(this.provider.getName());
/* 104 */     stringBuffer.append(".");
/* 105 */     stringBuffer.append(this.name);
/* 106 */     stringBuffer.append("(");
/* 107 */     boolean bool = true;
/* 108 */     for (Object object : paramArrayOfObject) {
/* 109 */       if (!bool) {
/* 110 */         stringBuffer.append(",");
/*     */       } else {
/* 112 */         bool = false;
/*     */       } 
/* 114 */       stringBuffer.append(object.toString());
/*     */     } 
/* 116 */     stringBuffer.append(")");
/* 117 */     this.provider.getStream().println(stringBuffer.toString());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/tracing/PrintStreamProbe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */