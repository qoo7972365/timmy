/*    */ package com.sun.corba.se.impl.orb;
/*    */ 
/*    */ import com.sun.corba.se.spi.orb.Operation;
/*    */ import java.util.Properties;
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
/*    */ public class NormalParserAction
/*    */   extends ParserActionBase
/*    */ {
/*    */   public NormalParserAction(String paramString1, Operation paramOperation, String paramString2) {
/* 36 */     super(paramString1, false, paramOperation, paramString2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object apply(Properties paramProperties) {
/* 45 */     String str = paramProperties.getProperty(getPropertyName());
/* 46 */     if (str != null) {
/* 47 */       return getOperation().operate(str);
/*    */     }
/* 49 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orb/NormalParserAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */