/*    */ package com.sun.org.apache.xpath.internal.objects;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*    */ import com.sun.org.apache.xpath.internal.XPathContext;
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
/*    */ public final class DTMXRTreeFrag
/*    */ {
/*    */   private DTM m_dtm;
/* 34 */   private int m_dtmIdentity = -1;
/*    */   private XPathContext m_xctxt;
/*    */   
/*    */   public DTMXRTreeFrag(int dtmIdentity, XPathContext xctxt) {
/* 38 */     this.m_xctxt = xctxt;
/* 39 */     this.m_dtmIdentity = dtmIdentity;
/* 40 */     this.m_dtm = xctxt.getDTM(dtmIdentity);
/*    */   }
/*    */   
/*    */   public final void destruct() {
/* 44 */     this.m_dtm = null;
/* 45 */     this.m_xctxt = null;
/*    */   }
/*    */   
/* 48 */   final DTM getDTM() { return this.m_dtm; }
/* 49 */   public final int getDTMIdentity() { return this.m_dtmIdentity; } final XPathContext getXPathContext() {
/* 50 */     return this.m_xctxt;
/*    */   } public final int hashCode() {
/* 52 */     return this.m_dtmIdentity;
/*    */   } public final boolean equals(Object obj) {
/* 54 */     if (obj instanceof DTMXRTreeFrag) {
/* 55 */       return (this.m_dtmIdentity == ((DTMXRTreeFrag)obj).getDTMIdentity());
/*    */     }
/* 57 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/objects/DTMXRTreeFrag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */