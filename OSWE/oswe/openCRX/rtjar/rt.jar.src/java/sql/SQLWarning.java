/*     */ package java.sql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SQLWarning
/*     */   extends SQLException
/*     */ {
/*     */   private static final long serialVersionUID = 3917336774604784856L;
/*     */   
/*     */   public SQLWarning(String paramString1, String paramString2, int paramInt) {
/*  61 */     super(paramString1, paramString2, paramInt);
/*  62 */     DriverManager.println("SQLWarning: reason(" + paramString1 + ") SQLState(" + paramString2 + ") vendor code(" + paramInt + ")");
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
/*     */   public SQLWarning(String paramString1, String paramString2) {
/*  81 */     super(paramString1, paramString2);
/*  82 */     DriverManager.println("SQLWarning: reason(" + paramString1 + ") SQLState(" + paramString2 + ")");
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
/*     */   public SQLWarning(String paramString) {
/*  99 */     super(paramString);
/* 100 */     DriverManager.println("SQLWarning: reason(" + paramString + ")");
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
/*     */   public SQLWarning() {
/* 115 */     DriverManager.println("SQLWarning: ");
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
/*     */   public SQLWarning(Throwable paramThrowable) {
/* 131 */     super(paramThrowable);
/* 132 */     DriverManager.println("SQLWarning");
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
/*     */   public SQLWarning(String paramString, Throwable paramThrowable) {
/* 148 */     super(paramString, paramThrowable);
/* 149 */     DriverManager.println("SQLWarning : reason(" + paramString + ")");
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
/*     */   public SQLWarning(String paramString1, String paramString2, Throwable paramThrowable) {
/* 164 */     super(paramString1, paramString2, paramThrowable);
/* 165 */     DriverManager.println("SQLWarning: reason(" + paramString1 + ") SQLState(" + paramString2 + ")");
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
/*     */   public SQLWarning(String paramString1, String paramString2, int paramInt, Throwable paramThrowable) {
/* 182 */     super(paramString1, paramString2, paramInt, paramThrowable);
/* 183 */     DriverManager.println("SQLWarning: reason(" + paramString1 + ") SQLState(" + paramString2 + ") vendor code(" + paramInt + ")");
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
/*     */   public SQLWarning getNextWarning() {
/*     */     try {
/* 197 */       return (SQLWarning)getNextException();
/* 198 */     } catch (ClassCastException classCastException) {
/*     */ 
/*     */ 
/*     */       
/* 202 */       throw new Error("SQLWarning chain holds value that is not a SQLWarning");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNextWarning(SQLWarning paramSQLWarning) {
/* 213 */     setNextException(paramSQLWarning);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/sql/SQLWarning.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */