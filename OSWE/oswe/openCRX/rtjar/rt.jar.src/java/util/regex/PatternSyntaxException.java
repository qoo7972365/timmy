/*     */ package java.util.regex;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PatternSyntaxException
/*     */   extends IllegalArgumentException
/*     */ {
/*     */   private static final long serialVersionUID = -3864639126226059218L;
/*     */   private final String desc;
/*     */   private final String pattern;
/*     */   private final int index;
/*     */   
/*     */   public PatternSyntaxException(String paramString1, String paramString2, int paramInt) {
/*  63 */     this.desc = paramString1;
/*  64 */     this.pattern = paramString2;
/*  65 */     this.index = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/*  75 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  84 */     return this.desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPattern() {
/*  93 */     return this.pattern;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  98 */   private static final String nl = AccessController.<String>doPrivileged(new GetPropertyAction("line.separator"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 108 */     StringBuffer stringBuffer = new StringBuffer();
/* 109 */     stringBuffer.append(this.desc);
/* 110 */     if (this.index >= 0) {
/* 111 */       stringBuffer.append(" near index ");
/* 112 */       stringBuffer.append(this.index);
/*     */     } 
/* 114 */     stringBuffer.append(nl);
/* 115 */     stringBuffer.append(this.pattern);
/* 116 */     if (this.index >= 0 && this.pattern != null && this.index < this.pattern.length()) {
/* 117 */       stringBuffer.append(nl);
/* 118 */       for (byte b = 0; b < this.index; ) { stringBuffer.append(' '); b++; }
/* 119 */        stringBuffer.append('^');
/*     */     } 
/* 121 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/regex/PatternSyntaxException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */