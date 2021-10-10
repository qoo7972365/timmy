/*     */ package com.sun.org.apache.xml.internal.security.c14n.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Comment;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.ProcessingInstruction;
/*     */ import org.xml.sax.SAXException;
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
/*     */ public class CanonicalizerPhysical
/*     */   extends CanonicalizerBase
/*     */ {
/*  57 */   private final SortedSet<Attr> result = new TreeSet<>(COMPARE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CanonicalizerPhysical() {
/*  63 */     super(true);
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
/*     */   public byte[] engineCanonicalizeXPathNodeSet(Set<Node> paramSet, String paramString) throws CanonicalizationException {
/*  78 */     throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
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
/*     */   public byte[] engineCanonicalizeSubTree(Node paramNode, String paramString) throws CanonicalizationException {
/*  93 */     throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
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
/*     */   protected Iterator<Attr> handleAttributesSubtree(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException {
/* 113 */     if (!paramElement.hasAttributes()) {
/* 114 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 118 */     SortedSet<Attr> sortedSet = this.result;
/* 119 */     sortedSet.clear();
/*     */     
/* 121 */     if (paramElement.hasAttributes()) {
/* 122 */       NamedNodeMap namedNodeMap = paramElement.getAttributes();
/* 123 */       int i = namedNodeMap.getLength();
/*     */       
/* 125 */       for (byte b = 0; b < i; b++) {
/* 126 */         Attr attr = (Attr)namedNodeMap.item(b);
/* 127 */         sortedSet.add(attr);
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     return sortedSet.iterator();
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
/*     */   protected Iterator<Attr> handleAttributes(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException {
/* 147 */     throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void circumventBugIfNeeded(XMLSignatureInput paramXMLSignatureInput) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleParent(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public final String engineGetURI() {
/* 162 */     return "http://santuario.apache.org/c14n/physical";
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean engineGetIncludeComments() {
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void outputPItoWriter(ProcessingInstruction paramProcessingInstruction, OutputStream paramOutputStream, int paramInt) throws IOException {
/* 174 */     super.outputPItoWriter(paramProcessingInstruction, paramOutputStream, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void outputCommentToWriter(Comment paramComment, OutputStream paramOutputStream, int paramInt) throws IOException {
/* 181 */     super.outputCommentToWriter(paramComment, paramOutputStream, 0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/implementations/CanonicalizerPhysical.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */