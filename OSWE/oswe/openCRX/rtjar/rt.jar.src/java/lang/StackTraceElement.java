/*     */ package java.lang;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StackTraceElement
/*     */   implements Serializable
/*     */ {
/*     */   private String declaringClass;
/*     */   private String methodName;
/*     */   private String fileName;
/*     */   private int lineNumber;
/*     */   private static final long serialVersionUID = 6992337162326171013L;
/*     */   
/*     */   public StackTraceElement(String paramString1, String paramString2, String paramString3, int paramInt) {
/*  71 */     this.declaringClass = Objects.<String>requireNonNull(paramString1, "Declaring class is null");
/*  72 */     this.methodName = Objects.<String>requireNonNull(paramString2, "Method name is null");
/*  73 */     this.fileName = paramString3;
/*  74 */     this.lineNumber = paramInt;
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
/*     */   public String getFileName() {
/*  90 */     return this.fileName;
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
/*     */   public int getLineNumber() {
/* 105 */     return this.lineNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassName() {
/* 116 */     return this.declaringClass;
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
/*     */   public String getMethodName() {
/* 131 */     return this.methodName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNativeMethod() {
/* 142 */     return (this.lineNumber == -2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 172 */     return getClassName() + "." + this.methodName + (
/* 173 */       isNativeMethod() ? "(Native Method)" : ((this.fileName != null && this.lineNumber >= 0) ? ("(" + this.fileName + ":" + this.lineNumber + ")") : ((this.fileName != null) ? ("(" + this.fileName + ")") : "(Unknown Source)")));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 199 */     if (paramObject == this)
/* 200 */       return true; 
/* 201 */     if (!(paramObject instanceof StackTraceElement))
/* 202 */       return false; 
/* 203 */     StackTraceElement stackTraceElement = (StackTraceElement)paramObject;
/* 204 */     return (stackTraceElement.declaringClass.equals(this.declaringClass) && stackTraceElement.lineNumber == this.lineNumber && 
/*     */       
/* 206 */       Objects.equals(this.methodName, stackTraceElement.methodName) && 
/* 207 */       Objects.equals(this.fileName, stackTraceElement.fileName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 214 */     int i = 31 * this.declaringClass.hashCode() + this.methodName.hashCode();
/* 215 */     i = 31 * i + Objects.hashCode(this.fileName);
/* 216 */     i = 31 * i + this.lineNumber;
/* 217 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/StackTraceElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */