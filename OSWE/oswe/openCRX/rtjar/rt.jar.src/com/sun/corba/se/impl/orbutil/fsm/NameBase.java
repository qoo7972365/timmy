/*    */ package com.sun.corba.se.impl.orbutil.fsm;
/*    */ 
/*    */ import java.util.StringTokenizer;
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
/*    */ public class NameBase
/*    */ {
/*    */   private String name;
/*    */   private String toStringName;
/*    */   
/*    */   private String getClassName() {
/* 42 */     String str1 = getClass().getName();
/* 43 */     StringTokenizer stringTokenizer = new StringTokenizer(str1, ".");
/* 44 */     String str2 = stringTokenizer.nextToken();
/* 45 */     while (stringTokenizer.hasMoreTokens())
/* 46 */       str2 = stringTokenizer.nextToken(); 
/* 47 */     return str2;
/*    */   }
/*    */ 
/*    */   
/*    */   private String getPreferredClassName() {
/* 52 */     if (this instanceof com.sun.corba.se.spi.orbutil.fsm.Action)
/* 53 */       return "Action"; 
/* 54 */     if (this instanceof com.sun.corba.se.spi.orbutil.fsm.State)
/* 55 */       return "State"; 
/* 56 */     if (this instanceof com.sun.corba.se.spi.orbutil.fsm.Guard)
/* 57 */       return "Guard"; 
/* 58 */     if (this instanceof com.sun.corba.se.spi.orbutil.fsm.Input)
/* 59 */       return "Input"; 
/* 60 */     return getClassName();
/*    */   }
/*    */ 
/*    */   
/*    */   public NameBase(String paramString) {
/* 65 */     this.name = paramString;
/* 66 */     this.toStringName = getPreferredClassName() + "[" + paramString + "]";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 71 */     return this.name;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 75 */     return this.toStringName;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/fsm/NameBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */