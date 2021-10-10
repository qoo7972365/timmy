/*    */ package com.sun.org.apache.xml.internal.utils;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NameSpace
/*    */   implements Serializable
/*    */ {
/*    */   static final long serialVersionUID = 1471232939184881839L;
/* 39 */   public NameSpace m_next = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String m_prefix;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String m_uri;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NameSpace(String prefix, String uri) {
/* 58 */     this.m_prefix = prefix;
/* 59 */     this.m_uri = uri;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/utils/NameSpace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */