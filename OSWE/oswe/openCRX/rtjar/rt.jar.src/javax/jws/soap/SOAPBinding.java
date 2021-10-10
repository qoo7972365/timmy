/*    */ package javax.jws.soap;
/*    */ 
/*    */ import java.lang.annotation.ElementType;
/*    */ import java.lang.annotation.Retention;
/*    */ import java.lang.annotation.RetentionPolicy;
/*    */ import java.lang.annotation.Target;
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
/*    */ @Retention(RetentionPolicy.RUNTIME)
/*    */ @Target({ElementType.TYPE, ElementType.METHOD})
/*    */ public @interface SOAPBinding
/*    */ {
/*    */   Style style() default Style.DOCUMENT;
/*    */   
/*    */   Use use() default Use.LITERAL;
/*    */   
/*    */   ParameterStyle parameterStyle() default ParameterStyle.WRAPPED;
/*    */   
/*    */   public enum Style
/*    */   {
/* 35 */     DOCUMENT,
/* 36 */     RPC;
/*    */   }
/*    */   
/*    */   public enum Use {
/* 40 */     LITERAL,
/* 41 */     ENCODED;
/*    */   }
/*    */   
/*    */   public enum ParameterStyle {
/* 45 */     BARE,
/* 46 */     WRAPPED;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/jws/soap/SOAPBinding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */