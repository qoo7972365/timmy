/*    */ package com.sun.org.apache.xml.internal.dtm.ref;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomStringPool
/*    */   extends DTMStringPool
/*    */ {
/* 50 */   final Map<String, Integer> m_stringToInt = new HashMap<>();
/*    */ 
/*    */   
/*    */   public static final int NULL = -1;
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeAllElements() {
/* 58 */     this.m_intToString.removeAllElements();
/* 59 */     if (this.m_stringToInt != null) {
/* 60 */       this.m_stringToInt.clear();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String indexToString(int i) throws ArrayIndexOutOfBoundsException {
/* 72 */     return this.m_intToString.elementAt(i);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int stringToIndex(String s) {
/* 80 */     if (s == null) {
/* 81 */       return -1;
/*    */     }
/* 83 */     Integer iobj = this.m_stringToInt.get(s);
/* 84 */     if (iobj == null) {
/* 85 */       this.m_intToString.addElement(s);
/* 86 */       iobj = Integer.valueOf(this.m_intToString.size());
/* 87 */       this.m_stringToInt.put(s, iobj);
/*    */     } 
/* 89 */     return iobj.intValue();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/dtm/ref/CustomStringPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */