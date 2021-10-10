/*    */ package com.sun.xml.internal.fastinfoset.tools;
/*    */ 
/*    */ import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetResult;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import javax.xml.transform.Result;
/*    */ import javax.xml.transform.Transformer;
/*    */ import javax.xml.transform.TransformerFactory;
/*    */ import javax.xml.transform.dom.DOMSource;
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
/*    */ 
/*    */ public class XML_DOM_SAX_FI
/*    */   extends TransformInputOutput
/*    */ {
/*    */   public void parse(InputStream document, OutputStream finf, String workingDirectory) throws Exception {
/* 48 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 49 */     dbf.setNamespaceAware(true);
/* 50 */     DocumentBuilder db = dbf.newDocumentBuilder();
/* 51 */     if (workingDirectory != null) {
/* 52 */       db.setEntityResolver(createRelativePathResolver(workingDirectory));
/*    */     }
/* 54 */     Document d = db.parse(document);
/*    */     
/* 56 */     TransformerFactory tf = TransformerFactory.newInstance();
/* 57 */     Transformer t = tf.newTransformer();
/* 58 */     t.transform(new DOMSource(d), (Result)new FastInfosetResult(finf));
/*    */   }
/*    */   
/*    */   public void parse(InputStream document, OutputStream finf) throws Exception {
/* 62 */     parse(document, finf, (String)null);
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 66 */     XML_DOM_SAX_FI p = new XML_DOM_SAX_FI();
/* 67 */     p.parse(args);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/tools/XML_DOM_SAX_FI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */