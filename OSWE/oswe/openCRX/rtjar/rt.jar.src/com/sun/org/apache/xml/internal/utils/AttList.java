/*     */ package com.sun.org.apache.xml.internal.utils;
/*     */ 
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.Attributes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttList
/*     */   implements Attributes
/*     */ {
/*     */   NamedNodeMap m_attrs;
/*     */   int m_lastIndex;
/*     */   
/*     */   public AttList(NamedNodeMap attrs) {
/*  53 */     this.m_attrs = attrs;
/*  54 */     this.m_lastIndex = this.m_attrs.getLength() - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/*  65 */     return this.m_attrs.getLength();
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
/*     */   public String getURI(int index) {
/*  78 */     String ns = DOM2Helper.getNamespaceOfNode(this.m_attrs.item(index));
/*  79 */     if (null == ns)
/*  80 */       ns = ""; 
/*  81 */     return ns;
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
/*     */   public String getLocalName(int index) {
/*  94 */     return DOM2Helper.getLocalNameOfNode(this.m_attrs.item(index));
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
/*     */   public String getQName(int i) {
/* 107 */     return ((Attr)this.m_attrs.item(i)).getName();
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
/*     */   public String getType(int i) {
/* 120 */     return "CDATA";
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
/*     */   public String getValue(int i) {
/* 133 */     return ((Attr)this.m_attrs.item(i)).getValue();
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
/*     */   public String getType(String name) {
/* 146 */     return "CDATA";
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
/*     */   public String getType(String uri, String localName) {
/* 161 */     return "CDATA";
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
/*     */   public String getValue(String name) {
/* 174 */     Attr attr = (Attr)this.m_attrs.getNamedItem(name);
/* 175 */     return (null != attr) ? attr
/* 176 */       .getValue() : null;
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
/*     */   public String getValue(String uri, String localName) {
/* 190 */     Node a = this.m_attrs.getNamedItemNS(uri, localName);
/* 191 */     return (a == null) ? null : a.getNodeValue();
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
/*     */   public int getIndex(String uri, String localPart) {
/* 205 */     for (int i = this.m_attrs.getLength() - 1; i >= 0; i--) {
/*     */       
/* 207 */       Node a = this.m_attrs.item(i);
/* 208 */       String u = a.getNamespaceURI();
/* 209 */       if (((u == null) ? (uri == null) : u.equals(uri)) && a
/*     */         
/* 211 */         .getLocalName().equals(localPart))
/* 212 */         return i; 
/*     */     } 
/* 214 */     return -1;
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
/*     */   public int getIndex(String qName) {
/* 226 */     for (int i = this.m_attrs.getLength() - 1; i >= 0; i--) {
/*     */       
/* 228 */       Node a = this.m_attrs.item(i);
/* 229 */       if (a.getNodeName().equals(qName))
/* 230 */         return i; 
/*     */     } 
/* 232 */     return -1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/utils/AttList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */