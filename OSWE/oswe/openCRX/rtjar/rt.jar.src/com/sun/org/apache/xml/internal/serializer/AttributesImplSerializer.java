/*     */ package com.sun.org.apache.xml.internal.serializer;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AttributesImplSerializer
/*     */   extends AttributesImpl
/*     */ {
/*  51 */   private final Map<String, Integer> m_indexFromQName = new HashMap<>();
/*     */   
/*  53 */   private final StringBuffer m_buff = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX = 12;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAXMinus1 = 11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getIndex(String qname) {
/*     */     int index;
/*  77 */     if (getLength() < 12) {
/*     */ 
/*     */ 
/*     */       
/*  81 */       index = super.getIndex(qname);
/*  82 */       return index;
/*     */     } 
/*     */ 
/*     */     
/*  86 */     Integer i = this.m_indexFromQName.get(qname);
/*  87 */     if (i == null) {
/*  88 */       index = -1;
/*     */     } else {
/*  90 */       index = i.intValue();
/*  91 */     }  return index;
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
/*     */   public final void addAttribute(String uri, String local, String qname, String type, String val) {
/* 112 */     int index = getLength();
/* 113 */     super.addAttribute(uri, local, qname, type, val);
/*     */ 
/*     */ 
/*     */     
/* 117 */     if (index < 11) {
/*     */       return;
/*     */     }
/*     */     
/* 121 */     if (index == 11) {
/*     */       
/* 123 */       switchOverToHash(12);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 129 */       Integer i = Integer.valueOf(index);
/* 130 */       this.m_indexFromQName.put(qname, i);
/*     */ 
/*     */       
/* 133 */       this.m_buff.setLength(0);
/* 134 */       this.m_buff.append('{').append(uri).append('}').append(local);
/* 135 */       String key = this.m_buff.toString();
/* 136 */       this.m_indexFromQName.put(key, i);
/*     */     } 
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
/*     */   private void switchOverToHash(int numAtts) {
/* 150 */     for (int index = 0; index < numAtts; index++) {
/*     */       
/* 152 */       String qName = getQName(index);
/* 153 */       Integer i = Integer.valueOf(index);
/* 154 */       this.m_indexFromQName.put(qName, i);
/*     */ 
/*     */       
/* 157 */       String uri = getURI(index);
/* 158 */       String local = getLocalName(index);
/* 159 */       this.m_buff.setLength(0);
/* 160 */       this.m_buff.append('{').append(uri).append('}').append(local);
/* 161 */       String key = this.m_buff.toString();
/* 162 */       this.m_indexFromQName.put(key, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clear() {
/* 174 */     int len = getLength();
/* 175 */     super.clear();
/* 176 */     if (12 <= len)
/*     */     {
/*     */ 
/*     */       
/* 180 */       this.m_indexFromQName.clear();
/*     */     }
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
/*     */   public final void setAttributes(Attributes atts) {
/* 196 */     super.setAttributes(atts);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     int numAtts = atts.getLength();
/* 202 */     if (12 <= numAtts) {
/* 203 */       switchOverToHash(numAtts);
/*     */     }
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
/*     */   public final int getIndex(String uri, String localName) {
/*     */     int index;
/* 218 */     if (getLength() < 12) {
/*     */ 
/*     */ 
/*     */       
/* 222 */       index = super.getIndex(uri, localName);
/* 223 */       return index;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 228 */     this.m_buff.setLength(0);
/* 229 */     this.m_buff.append('{').append(uri).append('}').append(localName);
/* 230 */     String key = this.m_buff.toString();
/* 231 */     Integer i = this.m_indexFromQName.get(key);
/* 232 */     if (i == null) {
/* 233 */       index = -1;
/*     */     } else {
/* 235 */       index = i.intValue();
/* 236 */     }  return index;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serializer/AttributesImplSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */