/*    */ package com.sun.istack.internal;
/*    */ 
/*    */ import org.xml.sax.ContentHandler;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.XMLReader;
/*    */ import org.xml.sax.helpers.XMLFilterImpl;
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
/*    */ public class FragmentContentHandler
/*    */   extends XMLFilterImpl
/*    */ {
/*    */   public FragmentContentHandler() {}
/*    */   
/*    */   public FragmentContentHandler(XMLReader parent) {
/* 42 */     super(parent);
/*    */   }
/*    */ 
/*    */   
/*    */   public FragmentContentHandler(ContentHandler handler) {
/* 47 */     setContentHandler(handler);
/*    */   }
/*    */   
/*    */   public void startDocument() throws SAXException {}
/*    */   
/*    */   public void endDocument() throws SAXException {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/istack/internal/FragmentContentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */