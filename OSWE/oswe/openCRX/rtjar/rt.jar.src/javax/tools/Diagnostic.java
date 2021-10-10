/*    */ package javax.tools;
/*    */ 
/*    */ import java.util.Locale;
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
/*    */ public interface Diagnostic<S>
/*    */ {
/*    */   public static final long NOPOS = -1L;
/*    */   
/*    */   Kind getKind();
/*    */   
/*    */   S getSource();
/*    */   
/*    */   long getPosition();
/*    */   
/*    */   long getStartPosition();
/*    */   
/*    */   long getEndPosition();
/*    */   
/*    */   long getLineNumber();
/*    */   
/*    */   long getColumnNumber();
/*    */   
/*    */   String getCode();
/*    */   
/*    */   String getMessage(Locale paramLocale);
/*    */   
/*    */   public enum Kind
/*    */   {
/* 66 */     ERROR,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 71 */     WARNING,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 78 */     MANDATORY_WARNING,
/*    */ 
/*    */ 
/*    */     
/* 82 */     NOTE,
/*    */ 
/*    */ 
/*    */     
/* 86 */     OTHER;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/tools/Diagnostic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */