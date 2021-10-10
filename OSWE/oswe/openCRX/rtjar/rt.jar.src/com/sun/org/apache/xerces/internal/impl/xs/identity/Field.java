/*     */ package com.sun.org.apache.xerces.internal.impl.xs.identity;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.xpath.XPathException;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.util.ShortListImpl;
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*     */ import com.sun.org.apache.xerces.internal.xs.ShortList;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSComplexTypeDefinition;
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
/*     */ public class Field
/*     */ {
/*     */   protected XPath fXPath;
/*     */   protected IdentityConstraint fIdentityConstraint;
/*     */   
/*     */   public Field(XPath xpath, IdentityConstraint identityConstraint) {
/*  60 */     this.fXPath = xpath;
/*  61 */     this.fIdentityConstraint = identityConstraint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public com.sun.org.apache.xerces.internal.impl.xpath.XPath getXPath() {
/*  70 */     return this.fXPath;
/*     */   }
/*     */ 
/*     */   
/*     */   public IdentityConstraint getIdentityConstraint() {
/*  75 */     return this.fIdentityConstraint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XPathMatcher createMatcher(FieldActivator activator, ValueStore store) {
/*  82 */     return new Matcher(this.fXPath, activator, store);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  91 */     return this.fXPath.toString();
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
/* 120 */       super((xpath.trim().startsWith("/") || xpath.trim().startsWith(".")) ? xpath : ("./" + xpath), symbolTable, context);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 125 */       for (int i = 0; i < this.fLocationPaths.length; i++) {
/* 126 */         for (int j = 0; j < (this.fLocationPaths[i]).steps.length; j++) {
/* 127 */           com.sun.org.apache.xerces.internal.impl.xpath.XPath.Axis axis = ((this.fLocationPaths[i]).steps[j]).axis;
/*     */           
/* 129 */           if (axis.type == 2 && j < (this.fLocationPaths[i]).steps.length - 1)
/*     */           {
/* 131 */             throw new XPathException("c-fields-xpaths");
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class Matcher
/*     */     extends XPathMatcher
/*     */   {
/*     */     protected FieldActivator fFieldActivator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected ValueStore fStore;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Matcher(Field.XPath xpath, FieldActivator activator, ValueStore store) {
/* 163 */       super(xpath);
/* 164 */       this.fFieldActivator = activator;
/* 165 */       this.fStore = store;
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
/*     */     protected void matched(Object actualValue, short valueType, ShortList itemValueType, boolean isNil) {
/* 177 */       super.matched(actualValue, valueType, itemValueType, isNil);
/* 178 */       if (isNil && Field.this.fIdentityConstraint.getCategory() == 1) {
/* 179 */         String code = "KeyMatchesNillable";
/* 180 */         this.fStore.reportError(code, new Object[] { this.this$0.fIdentityConstraint
/* 181 */               .getElementName(), this.this$0.fIdentityConstraint.getIdentityConstraintName() });
/*     */       } 
/* 183 */       this.fStore.addValue(Field.this, actualValue, convertToPrimitiveKind(valueType), convertToPrimitiveKind(itemValueType));
/*     */ 
/*     */ 
/*     */       
/* 187 */       this.fFieldActivator.setMayMatch(Field.this, Boolean.FALSE);
/*     */     }
/*     */ 
/*     */     
/*     */     private short convertToPrimitiveKind(short valueType) {
/* 192 */       if (valueType <= 20) {
/* 193 */         return valueType;
/*     */       }
/*     */       
/* 196 */       if (valueType <= 29) {
/* 197 */         return 2;
/*     */       }
/*     */       
/* 200 */       if (valueType <= 42) {
/* 201 */         return 4;
/*     */       }
/*     */       
/* 204 */       return valueType;
/*     */     }
/*     */     
/*     */     private ShortList convertToPrimitiveKind(ShortList itemValueType) {
/* 208 */       if (itemValueType != null) {
/*     */         
/* 210 */         int length = itemValueType.getLength(); int i;
/* 211 */         for (i = 0; i < length; i++) {
/* 212 */           short type = itemValueType.item(i);
/* 213 */           if (type != convertToPrimitiveKind(type)) {
/*     */             break;
/*     */           }
/*     */         } 
/* 217 */         if (i != length) {
/* 218 */           short[] arr = new short[length];
/* 219 */           for (int j = 0; j < i; j++) {
/* 220 */             arr[j] = itemValueType.item(j);
/*     */           }
/* 222 */           for (; i < length; i++) {
/* 223 */             arr[i] = convertToPrimitiveKind(itemValueType.item(i));
/*     */           }
/* 225 */           return new ShortListImpl(arr, arr.length);
/*     */         } 
/*     */       } 
/* 228 */       return itemValueType;
/*     */     }
/*     */     
/*     */     protected void handleContent(XSTypeDefinition type, boolean nillable, Object actualValue, short valueType, ShortList itemValueType) {
/* 232 */       if (type == null || (type
/* 233 */         .getTypeCategory() == 15 && ((XSComplexTypeDefinition)type)
/* 234 */         .getContentType() != 1))
/*     */       {
/*     */ 
/*     */         
/* 238 */         this.fStore.reportError("cvc-id.3", new Object[] { this.this$0.fIdentityConstraint
/* 239 */               .getName(), this.this$0.fIdentityConstraint
/* 240 */               .getElementName() });
/*     */       }
/*     */       
/* 243 */       this.fMatchedString = actualValue;
/* 244 */       matched(this.fMatchedString, valueType, itemValueType, nillable);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/identity/Field.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */