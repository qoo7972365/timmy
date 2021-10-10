/*     */ package com.sun.xml.internal.fastinfoset.stax.events;
/*     */ 
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.events.Attribute;
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
/*     */ public class AttributeBase
/*     */   extends EventBase
/*     */   implements Attribute
/*     */ {
/*     */   private QName _QName;
/*     */   private String _value;
/*  42 */   private String _attributeType = null;
/*     */   
/*     */   private boolean _specified = false;
/*     */ 
/*     */   
/*     */   public AttributeBase() {
/*  48 */     super(10);
/*     */   }
/*     */   
/*     */   public AttributeBase(String name, String value) {
/*  52 */     super(10);
/*  53 */     this._QName = new QName(name);
/*  54 */     this._value = value;
/*     */   }
/*     */   
/*     */   public AttributeBase(QName qname, String value) {
/*  58 */     this._QName = qname;
/*  59 */     this._value = value;
/*     */   }
/*     */   
/*     */   public AttributeBase(String prefix, String localName, String value) {
/*  63 */     this(prefix, null, localName, value, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributeBase(String prefix, String namespaceURI, String localName, String value, String attributeType) {
/*  68 */     if (prefix == null) prefix = ""; 
/*  69 */     this._QName = new QName(namespaceURI, localName, prefix);
/*  70 */     this._value = value;
/*  71 */     this._attributeType = (attributeType == null) ? "CDATA" : attributeType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(QName name) {
/*  76 */     this._QName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/*  83 */     return this._QName;
/*     */   }
/*     */   
/*     */   public void setValue(String value) {
/*  87 */     this._value = value;
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/*  91 */     return this._QName.getLocalPart();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  97 */     return this._value;
/*     */   }
/*     */   
/*     */   public void setAttributeType(String attributeType) {
/* 101 */     this._attributeType = attributeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDTDType() {
/* 110 */     return this._attributeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpecified() {
/* 120 */     return this._specified;
/*     */   }
/*     */   
/*     */   public void setSpecified(boolean isSpecified) {
/* 124 */     this._specified = isSpecified;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 129 */     String prefix = this._QName.getPrefix();
/* 130 */     if (!Util.isEmptyString(prefix)) {
/* 131 */       return prefix + ":" + this._QName.getLocalPart() + "='" + this._value + "'";
/*     */     }
/* 133 */     return this._QName.getLocalPart() + "='" + this._value + "'";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/stax/events/AttributeBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */