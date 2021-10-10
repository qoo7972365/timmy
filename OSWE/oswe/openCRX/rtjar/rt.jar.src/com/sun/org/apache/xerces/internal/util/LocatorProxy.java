/*    */ package com.sun.org.apache.xerces.internal.util;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.xni.XMLLocator;
/*    */ import org.xml.sax.ext.Locator2;
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
/*    */ public class LocatorProxy
/*    */   implements Locator2
/*    */ {
/*    */   private final XMLLocator fLocator;
/*    */   
/*    */   public LocatorProxy(XMLLocator locator) {
/* 49 */     this.fLocator = locator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPublicId() {
/* 58 */     return this.fLocator.getPublicId();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSystemId() {
/* 63 */     return this.fLocator.getExpandedSystemId();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLineNumber() {
/* 68 */     return this.fLocator.getLineNumber();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getColumnNumber() {
/* 73 */     return this.fLocator.getColumnNumber();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getXMLVersion() {
/* 81 */     return this.fLocator.getXMLVersion();
/*    */   }
/*    */   
/*    */   public String getEncoding() {
/* 85 */     return this.fLocator.getEncoding();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/LocatorProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */