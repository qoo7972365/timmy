/*     */ package com.sun.org.apache.xerces.internal.impl.xs.identity;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.xpath.XPathException;
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*     */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*     */ import com.sun.org.apache.xerces.internal.xs.ShortList;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;
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
/*     */ public class Selector
/*     */ {
/*     */   protected final XPath fXPath;
/*     */   protected final IdentityConstraint fIdentityConstraint;
/*     */   protected IdentityConstraint fIDConstraint;
/*     */   
/*     */   public Selector(XPath xpath, IdentityConstraint identityConstraint) {
/*  63 */     this.fXPath = xpath;
/*  64 */     this.fIdentityConstraint = identityConstraint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public com.sun.org.apache.xerces.internal.impl.xpath.XPath getXPath() {
/*  73 */     return this.fXPath;
/*     */   }
/*     */ 
/*     */   
/*     */   public IdentityConstraint getIDConstraint() {
/*  78 */     return this.fIdentityConstraint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XPathMatcher createMatcher(FieldActivator activator, int initialDepth) {
/*  89 */     return new Matcher(this.fXPath, activator, initialDepth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  98 */     return this.fXPath.toString();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static class XPath
/*     */     extends com.sun.org.apache.xerces.internal.impl.xpath.XPath
/*     */   {
/*     */     public XPath(String xpath, SymbolTable symbolTable, NamespaceContext context) throws XPathException {
/* 121 */       super(normalize(xpath), symbolTable, context);
/*     */       
/* 123 */       for (int i = 0; i < this.fLocationPaths.length; i++) {
/* 124 */         com.sun.org.apache.xerces.internal.impl.xpath.XPath.Axis axis = ((this.fLocationPaths[i]).steps[(this.fLocationPaths[i]).steps.length - 1]).axis;
/*     */         
/* 126 */         if (axis.type == 2) {
/* 127 */           throw new XPathException("c-selector-xpath");
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static String normalize(String xpath) {
/* 141 */       StringBuffer modifiedXPath = new StringBuffer(xpath.length() + 5);
/* 142 */       int unionIndex = -1;
/*     */       while (true) {
/* 144 */         if (!XMLChar.trim(xpath).startsWith("/") && !XMLChar.trim(xpath).startsWith(".")) {
/* 145 */           modifiedXPath.append("./");
/*     */         }
/* 147 */         unionIndex = xpath.indexOf('|');
/* 148 */         if (unionIndex == -1) {
/* 149 */           modifiedXPath.append(xpath);
/*     */           break;
/*     */         } 
/* 152 */         modifiedXPath.append(xpath.substring(0, unionIndex + 1));
/* 153 */         xpath = xpath.substring(unionIndex + 1, xpath.length());
/*     */       } 
/* 155 */       return modifiedXPath.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class Matcher
/*     */     extends XPathMatcher
/*     */   {
/*     */     protected final FieldActivator fFieldActivator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final int fInitialDepth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected int fElementDepth;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected int fMatchedDepth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Matcher(Selector.XPath xpath, FieldActivator activator, int initialDepth) {
/* 191 */       super(xpath);
/* 192 */       this.fFieldActivator = activator;
/* 193 */       this.fInitialDepth = initialDepth;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void startDocumentFragment() {
/* 201 */       super.startDocumentFragment();
/* 202 */       this.fElementDepth = 0;
/* 203 */       this.fMatchedDepth = -1;
/*     */     }
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
/*     */     public void startElement(QName element, XMLAttributes attributes) {
/* 216 */       super.startElement(element, attributes);
/* 217 */       this.fElementDepth++;
/*     */ 
/*     */ 
/*     */       
/* 221 */       if (isMatched()) {
/*     */ 
/*     */         
/* 224 */         this.fMatchedDepth = this.fElementDepth;
/* 225 */         this.fFieldActivator.startValueScopeFor(Selector.this.fIdentityConstraint, this.fInitialDepth);
/* 226 */         int count = Selector.this.fIdentityConstraint.getFieldCount();
/* 227 */         for (int i = 0; i < count; i++) {
/* 228 */           Field field = Selector.this.fIdentityConstraint.getFieldAt(i);
/* 229 */           XPathMatcher matcher = this.fFieldActivator.activateField(field, this.fInitialDepth);
/* 230 */           matcher.startElement(element, attributes);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void endElement(QName element, XSTypeDefinition type, boolean nillable, Object actualValue, short valueType, ShortList itemValueType) {
/* 237 */       super.endElement(element, type, nillable, actualValue, valueType, itemValueType);
/* 238 */       if (this.fElementDepth-- == this.fMatchedDepth) {
/* 239 */         this.fMatchedDepth = -1;
/* 240 */         this.fFieldActivator.endValueScopeFor(Selector.this.fIdentityConstraint, this.fInitialDepth);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public IdentityConstraint getIdentityConstraint() {
/* 246 */       return Selector.this.fIdentityConstraint;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getInitialDepth() {
/* 251 */       return this.fInitialDepth;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/identity/Selector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */