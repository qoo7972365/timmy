/*    */ package com.sun.org.apache.xerces.internal.dom;
/*    */ 
/*    */ import java.util.Vector;
/*    */ import org.w3c.dom.DOMStringList;
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
/*    */ public class DOMStringListImpl
/*    */   implements DOMStringList
/*    */ {
/*    */   private Vector fStrings;
/*    */   
/*    */   public DOMStringListImpl() {
/* 45 */     this.fStrings = new Vector();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DOMStringListImpl(Vector params) {
/* 52 */     this.fStrings = params;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String item(int index) {
/*    */     try {
/* 60 */       return this.fStrings.elementAt(index);
/* 61 */     } catch (ArrayIndexOutOfBoundsException e) {
/* 62 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLength() {
/* 70 */     return this.fStrings.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean contains(String param) {
/* 77 */     return this.fStrings.contains(param);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(String param) {
/* 87 */     this.fStrings.add(param);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/DOMStringListImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */