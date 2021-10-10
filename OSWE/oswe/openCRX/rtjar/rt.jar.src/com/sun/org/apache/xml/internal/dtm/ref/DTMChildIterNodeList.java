/*     */ package com.sun.org.apache.xml.internal.dtm.ref;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DTMChildIterNodeList
/*     */   extends DTMNodeListBase
/*     */ {
/*     */   private int m_firstChild;
/*     */   private DTM m_parentDTM;
/*     */   
/*     */   private DTMChildIterNodeList() {}
/*     */   
/*     */   public DTMChildIterNodeList(DTM parentDTM, int parentHandle) {
/*  80 */     this.m_parentDTM = parentDTM;
/*  81 */     this.m_firstChild = parentDTM.getFirstChild(parentHandle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node item(int index) {
/*  98 */     int handle = this.m_firstChild;
/*  99 */     while (--index >= 0 && handle != -1) {
/* 100 */       handle = this.m_parentDTM.getNextSibling(handle);
/*     */     }
/* 102 */     if (handle == -1) {
/* 103 */       return null;
/*     */     }
/* 105 */     return this.m_parentDTM.getNode(handle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/* 113 */     int count = 0;
/* 114 */     int handle = this.m_firstChild;
/* 115 */     for (; handle != -1; 
/* 116 */       handle = this.m_parentDTM.getNextSibling(handle)) {
/* 117 */       count++;
/*     */     }
/* 119 */     return count;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/dtm/ref/DTMChildIterNodeList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */