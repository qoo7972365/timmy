/*    */ package com.sun.xml.internal.org.jvnet.fastinfoset;
/*    */ 
/*    */ import com.sun.xml.internal.fastinfoset.sax.SAXDocumentSerializer;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.transform.sax.SAXResult;
/*    */ import org.xml.sax.ContentHandler;
/*    */ import org.xml.sax.ext.LexicalHandler;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FastInfosetResult
/*    */   extends SAXResult
/*    */ {
/*    */   OutputStream _outputStream;
/*    */   
/*    */   public FastInfosetResult(OutputStream outputStream) {
/* 60 */     this._outputStream = outputStream;
/*    */   }
/*    */   public ContentHandler getHandler() {
/*    */     SAXDocumentSerializer sAXDocumentSerializer;
/* 64 */     ContentHandler handler = super.getHandler();
/* 65 */     if (handler == null) {
/* 66 */       sAXDocumentSerializer = new SAXDocumentSerializer();
/* 67 */       setHandler((ContentHandler)sAXDocumentSerializer);
/*    */     } 
/* 69 */     sAXDocumentSerializer.setOutputStream(this._outputStream);
/* 70 */     return (ContentHandler)sAXDocumentSerializer;
/*    */   }
/*    */   
/*    */   public LexicalHandler getLexicalHandler() {
/* 74 */     return (LexicalHandler)getHandler();
/*    */   }
/*    */   
/*    */   public OutputStream getOutputStream() {
/* 78 */     return this._outputStream;
/*    */   }
/*    */   
/*    */   public void setOutputStream(OutputStream outputStream) {
/* 82 */     this._outputStream = outputStream;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/fastinfoset/FastInfosetResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */