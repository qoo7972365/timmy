/*     */ package javax.script;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScriptException
/*     */   extends Exception
/*     */ {
/*     */   private static final long serialVersionUID = 8265071037049225001L;
/*     */   private String fileName;
/*     */   private int lineNumber;
/*     */   private int columnNumber;
/*     */   
/*     */   public ScriptException(String paramString) {
/*  52 */     super(paramString);
/*  53 */     this.fileName = null;
/*  54 */     this.lineNumber = -1;
/*  55 */     this.columnNumber = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScriptException(Exception paramException) {
/*  65 */     super(paramException);
/*  66 */     this.fileName = null;
/*  67 */     this.lineNumber = -1;
/*  68 */     this.columnNumber = -1;
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
/*     */ 
/*     */   
/*     */   public ScriptException(String paramString1, String paramString2, int paramInt) {
/*  84 */     super(paramString1);
/*  85 */     this.fileName = paramString2;
/*  86 */     this.lineNumber = paramInt;
/*  87 */     this.columnNumber = -1;
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
/*     */   
/*     */   public ScriptException(String paramString1, String paramString2, int paramInt1, int paramInt2) {
/* 102 */     super(paramString1);
/* 103 */     this.fileName = paramString2;
/* 104 */     this.lineNumber = paramInt1;
/* 105 */     this.columnNumber = paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 114 */     String str = super.getMessage();
/* 115 */     if (this.fileName != null) {
/* 116 */       str = str + " in " + this.fileName;
/* 117 */       if (this.lineNumber != -1) {
/* 118 */         str = str + " at line number " + this.lineNumber;
/*     */       }
/*     */       
/* 121 */       if (this.columnNumber != -1) {
/* 122 */         str = str + " at column number " + this.columnNumber;
/*     */       }
/*     */     } 
/*     */     
/* 126 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineNumber() {
/* 134 */     return this.lineNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnNumber() {
/* 142 */     return this.columnNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFileName() {
/* 152 */     return this.fileName;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/script/ScriptException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */