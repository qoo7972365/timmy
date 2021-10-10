/*     */ package com.sun.org.apache.xml.internal.security.transforms.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*     */ import com.sun.org.apache.xml.internal.security.utils.I18n;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import com.sun.org.apache.xpath.internal.NodeSetDTM;
/*     */ import com.sun.org.apache.xpath.internal.XPathContext;
/*     */ import com.sun.org.apache.xpath.internal.functions.Function;
/*     */ import com.sun.org.apache.xpath.internal.objects.XNodeSet;
/*     */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*     */ import java.util.Vector;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class FuncHere
/*     */   extends Function
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public XObject execute(XPathContext paramXPathContext) throws TransformerException {
/*  78 */     Node node = (Node)paramXPathContext.getOwnerObject();
/*     */     
/*  80 */     if (node == null) {
/*  81 */       return null;
/*     */     }
/*     */     
/*  84 */     int i = paramXPathContext.getDTMHandleFromNode(node);
/*     */     
/*  86 */     int j = paramXPathContext.getCurrentNode();
/*  87 */     DTM dTM = paramXPathContext.getDTM(j);
/*  88 */     int k = dTM.getDocument();
/*     */     
/*  90 */     if (-1 == k) {
/*  91 */       error(paramXPathContext, "ER_CONTEXT_HAS_NO_OWNERDOC", null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     Document document1 = XMLUtils.getOwnerDocument(dTM.getNode(j));
/*  99 */     Document document2 = XMLUtils.getOwnerDocument(node);
/*     */     
/* 101 */     if (document1 != document2) {
/* 102 */       throw new TransformerException(I18n.translate("xpath.funcHere.documentsDiffer"));
/*     */     }
/*     */ 
/*     */     
/* 106 */     XNodeSet xNodeSet = new XNodeSet(paramXPathContext.getDTMManager());
/* 107 */     NodeSetDTM nodeSetDTM = xNodeSet.mutableNodeset();
/*     */ 
/*     */     
/* 110 */     int m = -1;
/*     */     
/* 112 */     switch (dTM.getNodeType(i)) {
/*     */ 
/*     */       
/*     */       case 2:
/*     */       case 7:
/* 117 */         m = i;
/*     */         
/* 119 */         nodeSetDTM.addNode(m);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 126 */         m = dTM.getParent(i);
/*     */         
/* 128 */         nodeSetDTM.addNode(m);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     nodeSetDTM.detach();
/*     */     
/* 140 */     return xNodeSet;
/*     */   }
/*     */   
/*     */   public void fixupVariables(Vector paramVector, int paramInt) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/implementations/FuncHere.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */