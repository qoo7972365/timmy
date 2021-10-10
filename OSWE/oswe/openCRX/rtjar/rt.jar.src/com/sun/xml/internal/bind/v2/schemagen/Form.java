/*     */ package com.sun.xml.internal.bind.v2.schemagen;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.LocalAttribute;
/*     */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.LocalElement;
/*     */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Schema;
/*     */ import com.sun.xml.internal.txw2.TypedXmlWriter;
/*     */ import javax.xml.bind.annotation.XmlNsForm;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ enum Form
/*     */ {
/*  42 */   QUALIFIED(XmlNsForm.QUALIFIED, true) {
/*     */     void declare(String attName, Schema schema) {
/*  44 */       schema._attribute(attName, "qualified");
/*     */     }
/*     */   },
/*  47 */   UNQUALIFIED(XmlNsForm.UNQUALIFIED, false)
/*     */   {
/*     */     void declare(String attName, Schema schema)
/*     */     {
/*  51 */       schema._attribute(attName, "unqualified");
/*     */     }
/*     */   },
/*  54 */   UNSET(XmlNsForm.UNSET, false)
/*     */   {
/*     */     void declare(String attName, Schema schema) {}
/*     */   };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final XmlNsForm xnf;
/*     */ 
/*     */   
/*     */   public final boolean isEffectivelyQualified;
/*     */ 
/*     */ 
/*     */   
/*     */   Form(XmlNsForm xnf, boolean effectivelyQualified) {
/*  70 */     this.xnf = xnf;
/*  71 */     this.isEffectivelyQualified = effectivelyQualified;
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
/*     */   public void writeForm(LocalElement e, QName tagName) {
/*  84 */     _writeForm((TypedXmlWriter)e, tagName);
/*     */   }
/*     */   
/*     */   public void writeForm(LocalAttribute a, QName tagName) {
/*  88 */     _writeForm((TypedXmlWriter)a, tagName);
/*     */   }
/*     */   
/*     */   private void _writeForm(TypedXmlWriter e, QName tagName) {
/*  92 */     boolean qualified = (tagName.getNamespaceURI().length() > 0);
/*     */     
/*  94 */     if (qualified && this != QUALIFIED) {
/*  95 */       e._attribute("form", "qualified");
/*     */     }
/*  97 */     else if (!qualified && this == QUALIFIED) {
/*  98 */       e._attribute("form", "unqualified");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Form get(XmlNsForm xnf) {
/* 105 */     for (Form v : values()) {
/* 106 */       if (v.xnf == xnf)
/* 107 */         return v; 
/*     */     } 
/* 109 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   abstract void declare(String paramString, Schema paramSchema);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/schemagen/Form.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */