/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLLocator;
/*     */ import org.xml.sax.Locator;
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
/*     */ public class LocatorWrapper
/*     */   implements XMLLocator
/*     */ {
/*     */   private final Locator locator;
/*     */   
/*     */   public LocatorWrapper(Locator _loc) {
/*  76 */     this.locator = _loc;
/*     */   }
/*  78 */   public int getColumnNumber() { return this.locator.getColumnNumber(); }
/*  79 */   public int getLineNumber() { return this.locator.getLineNumber(); }
/*  80 */   public String getBaseSystemId() { return null; }
/*  81 */   public String getExpandedSystemId() { return this.locator.getSystemId(); }
/*  82 */   public String getLiteralSystemId() { return this.locator.getSystemId(); }
/*  83 */   public String getPublicId() { return this.locator.getPublicId(); } public String getEncoding() {
/*  84 */     return null;
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
/*     */   public int getCharacterOffset() {
/*  96 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getXMLVersion() {
/* 107 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/LocatorWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */