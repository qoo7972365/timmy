/*    */ package com.sun.org.apache.xerces.internal.dom;
/*    */ 
/*    */ import java.util.Vector;
/*    */ import org.w3c.dom.DOMImplementation;
/*    */ import org.w3c.dom.DOMImplementationList;
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
/*    */ public class DOMImplementationListImpl
/*    */   implements DOMImplementationList
/*    */ {
/*    */   private Vector fImplementations;
/*    */   
/*    */   public DOMImplementationListImpl() {
/* 44 */     this.fImplementations = new Vector();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DOMImplementationListImpl(Vector params) {
/* 51 */     this.fImplementations = params;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DOMImplementation item(int index) {
/*    */     try {
/* 61 */       return this.fImplementations.elementAt(index);
/* 62 */     } catch (ArrayIndexOutOfBoundsException e) {
/* 63 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLength() {
/* 73 */     return this.fImplementations.size();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/DOMImplementationListImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */