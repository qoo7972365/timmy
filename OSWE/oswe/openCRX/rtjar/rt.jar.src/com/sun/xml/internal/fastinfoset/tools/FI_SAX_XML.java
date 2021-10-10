/*    */ package com.sun.xml.internal.fastinfoset.tools;
/*    */ 
/*    */ import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetSource;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.transform.Source;
/*    */ import javax.xml.transform.Transformer;
/*    */ import javax.xml.transform.TransformerFactory;
/*    */ import javax.xml.transform.stream.StreamResult;
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
/*    */ public class FI_SAX_XML
/*    */   extends TransformInputOutput
/*    */ {
/*    */   public void parse(InputStream finf, OutputStream xml) throws Exception {
/* 43 */     Transformer tx = TransformerFactory.newInstance().newTransformer();
/* 44 */     tx.transform((Source)new FastInfosetSource(finf), new StreamResult(xml));
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 48 */     FI_SAX_XML p = new FI_SAX_XML();
/* 49 */     p.parse(args);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/tools/FI_SAX_XML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */