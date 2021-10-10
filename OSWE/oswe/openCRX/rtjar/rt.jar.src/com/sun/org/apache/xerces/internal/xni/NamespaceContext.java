/*    */ package com.sun.org.apache.xerces.internal.xni;
/*    */ 
/*    */ import java.util.Enumeration;
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
/*    */ public interface NamespaceContext
/*    */ {
/* 49 */   public static final String XML_URI = "http://www.w3.org/XML/1998/namespace".intern();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 56 */   public static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/".intern();
/*    */   
/*    */   void pushContext();
/*    */   
/*    */   void popContext();
/*    */   
/*    */   boolean declarePrefix(String paramString1, String paramString2);
/*    */   
/*    */   String getURI(String paramString);
/*    */   
/*    */   String getPrefix(String paramString);
/*    */   
/*    */   int getDeclaredPrefixCount();
/*    */   
/*    */   String getDeclaredPrefixAt(int paramInt);
/*    */   
/*    */   Enumeration getAllPrefixes();
/*    */   
/*    */   void reset();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xni/NamespaceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */