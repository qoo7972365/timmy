/*     */ package com.sun.org.apache.xml.internal.utils;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ElemDesc
/*     */ {
/*  38 */   Map<String, Integer> m_attrs = null;
/*     */ 
/*     */ 
/*     */   
/*     */   int m_flags;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int EMPTY = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int FLOW = 4;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int BLOCK = 8;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int BLOCKFORM = 16;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int BLOCKFORMFIELDSET = 32;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int CDATA = 64;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int PCDATA = 128;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int RAW = 256;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int INLINE = 512;
/*     */ 
/*     */   
/*     */   static final int INLINEA = 1024;
/*     */ 
/*     */   
/*     */   static final int INLINELABEL = 2048;
/*     */ 
/*     */   
/*     */   static final int FONTSTYLE = 4096;
/*     */ 
/*     */   
/*     */   static final int PHRASE = 8192;
/*     */ 
/*     */   
/*     */   static final int FORMCTRL = 16384;
/*     */ 
/*     */   
/*     */   static final int SPECIAL = 32768;
/*     */ 
/*     */   
/*     */   static final int ASPECIAL = 65536;
/*     */ 
/*     */   
/*     */   static final int HEADMISC = 131072;
/*     */ 
/*     */   
/*     */   static final int HEAD = 262144;
/*     */ 
/*     */   
/*     */   static final int LIST = 524288;
/*     */ 
/*     */   
/*     */   static final int PREFORMATTED = 1048576;
/*     */ 
/*     */   
/*     */   static final int WHITESPACESENSITIVE = 2097152;
/*     */ 
/*     */   
/*     */   static final int ATTRURL = 2;
/*     */ 
/*     */   
/*     */   static final int ATTREMPTY = 4;
/*     */ 
/*     */ 
/*     */   
/*     */   ElemDesc(int flags) {
/* 125 */     this.m_flags = flags;
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
/*     */   boolean is(int flags) {
/* 148 */     return ((this.m_flags & flags) != 0);
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
/*     */   void setAttr(String name, int flags) {
/* 161 */     if (null == this.m_attrs) {
/* 162 */       this.m_attrs = new HashMap<>();
/*     */     }
/* 164 */     this.m_attrs.put(name, Integer.valueOf(flags));
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
/*     */   boolean isAttrFlagSet(String name, int flags) {
/* 181 */     if (null != this.m_attrs) {
/*     */       
/* 183 */       Integer _flags = this.m_attrs.get(name);
/*     */       
/* 185 */       if (null != _flags)
/*     */       {
/* 187 */         return ((_flags.intValue() & flags) != 0);
/*     */       }
/*     */     } 
/*     */     
/* 191 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/utils/ElemDesc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */