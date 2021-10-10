/*     */ package com.sun.org.apache.xerces.internal.dom;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.xs.ElementPSVI;
/*     */ import com.sun.org.apache.xerces.internal.xs.ShortList;
/*     */ import com.sun.org.apache.xerces.internal.xs.StringList;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSElementDeclaration;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSModel;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSNotationDeclaration;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSSimpleTypeDefinition;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;
/*     */ import java.io.IOException;
/*     */ import java.io.NotSerializableException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
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
/*     */ public class PSVIElementNSImpl
/*     */   extends ElementNSImpl
/*     */   implements ElementPSVI
/*     */ {
/*     */   static final long serialVersionUID = 6815489624636016068L;
/*     */   protected XSElementDeclaration fDeclaration;
/*     */   protected XSTypeDefinition fTypeDecl;
/*     */   protected boolean fNil;
/*     */   protected boolean fSpecified;
/*     */   protected String fNormalizedValue;
/*     */   protected Object fActualValue;
/*     */   protected short fActualValueType;
/*     */   protected ShortList fItemValueTypes;
/*     */   protected XSNotationDeclaration fNotation;
/*     */   protected XSSimpleTypeDefinition fMemberType;
/*     */   protected short fValidationAttempted;
/*     */   protected short fValidity;
/*     */   protected StringList fErrorCodes;
/*     */   protected String fValidationContext;
/*     */   protected XSModel fSchemaInformation;
/*     */   
/*     */   public PSVIElementNSImpl(CoreDocumentImpl ownerDocument, String namespaceURI, String qualifiedName, String localName) {
/*  50 */     super(ownerDocument, namespaceURI, qualifiedName, localName);
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
/*  62 */     this.fDeclaration = null;
/*     */ 
/*     */     
/*  65 */     this.fTypeDecl = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     this.fNil = false;
/*     */ 
/*     */ 
/*     */     
/*  74 */     this.fSpecified = true;
/*     */ 
/*     */     
/*  77 */     this.fNormalizedValue = null;
/*     */ 
/*     */     
/*  80 */     this.fActualValue = null;
/*     */ 
/*     */     
/*  83 */     this.fActualValueType = 45;
/*     */ 
/*     */     
/*  86 */     this.fItemValueTypes = null;
/*     */ 
/*     */     
/*  89 */     this.fNotation = null;
/*     */ 
/*     */     
/*  92 */     this.fMemberType = null;
/*     */ 
/*     */     
/*  95 */     this.fValidationAttempted = 0;
/*     */ 
/*     */     
/*  98 */     this.fValidity = 0;
/*     */ 
/*     */     
/* 101 */     this.fErrorCodes = null;
/*     */ 
/*     */     
/* 104 */     this.fValidationContext = null;
/*     */ 
/*     */     
/* 107 */     this.fSchemaInformation = null; } public PSVIElementNSImpl(CoreDocumentImpl ownerDocument, String namespaceURI, String qualifiedName) { super(ownerDocument, namespaceURI, qualifiedName); this.fDeclaration = null; this.fTypeDecl = null; this.fNil = false; this.fSpecified = true; this.fNormalizedValue = null; this.fActualValue = null; this.fActualValueType = 45; this.fItemValueTypes = null; this.fNotation = null; this.fMemberType = null; this.fValidationAttempted = 0; this.fValidity = 0; this.fErrorCodes = null; this.fValidationContext = null; this.fSchemaInformation = null; }
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
/*     */   public String getSchemaDefault() {
/* 120 */     return (this.fDeclaration == null) ? null : this.fDeclaration.getConstraintValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSchemaNormalizedValue() {
/* 131 */     return this.fNormalizedValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsSchemaSpecified() {
/* 140 */     return this.fSpecified;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getValidationAttempted() {
/* 150 */     return this.fValidationAttempted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getValidity() {
/* 161 */     return this.fValidity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringList getErrorCodes() {
/* 171 */     return this.fErrorCodes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValidationContext() {
/* 177 */     return this.fValidationContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getNil() {
/* 186 */     return this.fNil;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XSNotationDeclaration getNotation() {
/* 195 */     return this.fNotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XSTypeDefinition getTypeDefinition() {
/* 204 */     return this.fTypeDecl;
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
/*     */   public XSSimpleTypeDefinition getMemberTypeDefinition() {
/* 217 */     return this.fMemberType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XSElementDeclaration getElementDeclaration() {
/* 227 */     return this.fDeclaration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XSModel getSchemaInformation() {
/* 237 */     return this.fSchemaInformation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPSVI(ElementPSVI elem) {
/* 246 */     this.fDeclaration = elem.getElementDeclaration();
/* 247 */     this.fNotation = elem.getNotation();
/* 248 */     this.fValidationContext = elem.getValidationContext();
/* 249 */     this.fTypeDecl = elem.getTypeDefinition();
/* 250 */     this.fSchemaInformation = elem.getSchemaInformation();
/* 251 */     this.fValidity = elem.getValidity();
/* 252 */     this.fValidationAttempted = elem.getValidationAttempted();
/* 253 */     this.fErrorCodes = elem.getErrorCodes();
/* 254 */     this.fNormalizedValue = elem.getSchemaNormalizedValue();
/* 255 */     this.fActualValue = elem.getActualNormalizedValue();
/* 256 */     this.fActualValueType = elem.getActualNormalizedValueType();
/* 257 */     this.fItemValueTypes = elem.getItemValueTypes();
/* 258 */     this.fMemberType = elem.getMemberTypeDefinition();
/* 259 */     this.fSpecified = elem.getIsSchemaSpecified();
/* 260 */     this.fNil = elem.getNil();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getActualNormalizedValue() {
/* 267 */     return this.fActualValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getActualNormalizedValueType() {
/* 274 */     return this.fActualValueType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortList getItemValueTypes() {
/* 281 */     return this.fItemValueTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 289 */     throw new NotSerializableException(getClass().getName());
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 294 */     throw new NotSerializableException(getClass().getName());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/PSVIElementNSImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */