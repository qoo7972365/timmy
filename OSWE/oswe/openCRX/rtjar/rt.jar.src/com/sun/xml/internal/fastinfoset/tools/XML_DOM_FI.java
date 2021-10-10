/*    */ package com.sun.xml.internal.fastinfoset.tools;
/*    */ 
/*    */ import com.sun.xml.internal.fastinfoset.dom.DOMDocumentSerializer;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import org.w3c.dom.Document;
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
/*    */ public class XML_DOM_FI
/*    */   extends TransformInputOutput
/*    */ {
/*    */   public void parse(InputStream document, OutputStream finf, String workingDirectory) throws Exception {
/* 43 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 44 */     dbf.setNamespaceAware(true);
/* 45 */     DocumentBuilder db = dbf.newDocumentBuilder();
/* 46 */     if (workingDirectory != null) {
/* 47 */       db.setEntityResolver(createRelativePathResolver(workingDirectory));
/*    */     }
/* 49 */     Document d = db.parse(document);
/*    */     
/* 51 */     DOMDocumentSerializer s = new DOMDocumentSerializer();
/* 52 */     s.setOutputStream(finf);
/* 53 */     s.serialize(d);
/*    */   }
/*    */   
/*    */   public void parse(InputStream document, OutputStream finf) throws Exception {
/* 57 */     parse(document, finf, (String)null);
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 61 */     XML_DOM_FI p = new XML_DOM_FI();
/* 62 */     p.parse(args);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/tools/XML_DOM_FI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */