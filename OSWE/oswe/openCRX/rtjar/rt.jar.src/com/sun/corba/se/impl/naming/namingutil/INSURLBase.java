/*    */ package com.sun.corba.se.impl.naming.namingutil;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public abstract class INSURLBase
/*    */   implements INSURL
/*    */ {
/*    */   protected boolean rirFlag = false;
/* 41 */   protected ArrayList theEndpointInfo = null;
/* 42 */   protected String theKeyString = "NameService";
/* 43 */   protected String theStringifiedName = null;
/*    */   
/*    */   public boolean getRIRFlag() {
/* 46 */     return this.rirFlag;
/*    */   }
/*    */   
/*    */   public List getEndpointInfo() {
/* 50 */     return this.theEndpointInfo;
/*    */   }
/*    */   
/*    */   public String getKeyString() {
/* 54 */     return this.theKeyString;
/*    */   }
/*    */   
/*    */   public String getStringifiedName() {
/* 58 */     return this.theStringifiedName;
/*    */   }
/*    */   
/*    */   public abstract boolean isCorbanameURL();
/*    */   
/*    */   public void dPrint() {
/* 64 */     System.out.println("URL Dump...");
/* 65 */     System.out.println("Key String = " + getKeyString());
/* 66 */     System.out.println("RIR Flag = " + getRIRFlag());
/* 67 */     System.out.println("isCorbanameURL = " + isCorbanameURL());
/* 68 */     for (byte b = 0; b < this.theEndpointInfo.size(); b++) {
/* 69 */       ((IIOPEndpointInfo)this.theEndpointInfo.get(b)).dump();
/*    */     }
/* 71 */     if (isCorbanameURL())
/* 72 */       System.out.println("Stringified Name = " + getStringifiedName()); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/namingutil/INSURLBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */