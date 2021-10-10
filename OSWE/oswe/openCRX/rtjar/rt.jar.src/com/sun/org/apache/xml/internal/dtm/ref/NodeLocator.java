/*     */ package com.sun.org.apache.xml.internal.dtm.ref;
/*     */ 
/*     */ import javax.xml.transform.SourceLocator;
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
/*     */ public class NodeLocator
/*     */   implements SourceLocator
/*     */ {
/*     */   protected String m_publicId;
/*     */   protected String m_systemId;
/*     */   protected int m_lineNumber;
/*     */   protected int m_columnNumber;
/*     */   
/*     */   public NodeLocator(String publicId, String systemId, int lineNumber, int columnNumber) {
/*  53 */     this.m_publicId = publicId;
/*  54 */     this.m_systemId = systemId;
/*  55 */     this.m_lineNumber = lineNumber;
/*  56 */     this.m_columnNumber = columnNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPublicId() {
/*  66 */     return this.m_publicId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/*  76 */     return this.m_systemId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineNumber() {
/*  86 */     return this.m_lineNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnNumber() {
/*  97 */     return this.m_columnNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 108 */     return "file '" + this.m_systemId + "', line #" + this.m_lineNumber + ", column #" + this.m_columnNumber;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/dtm/ref/NodeLocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */