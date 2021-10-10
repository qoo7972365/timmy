/*    */ package org.jcp.xml.dsig.internal.dom;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.signature.NodeFilter;
/*    */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*    */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import javax.xml.crypto.NodeSetData;
/*    */ import org.w3c.dom.Node;
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
/*    */ public class ApacheNodeSetData
/*    */   implements ApacheData, NodeSetData
/*    */ {
/*    */   private XMLSignatureInput xi;
/*    */   
/*    */   public ApacheNodeSetData(XMLSignatureInput paramXMLSignatureInput) {
/* 47 */     this.xi = paramXMLSignatureInput;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator iterator() {
/* 52 */     if (this.xi.getNodeFilters() != null && !this.xi.getNodeFilters().isEmpty()) {
/* 53 */       return 
/* 54 */         Collections.<Node>unmodifiableSet(getNodeSet(this.xi.getNodeFilters())).iterator();
/*    */     }
/*    */     try {
/* 57 */       return Collections.<Node>unmodifiableSet(this.xi.getNodeSet()).iterator();
/* 58 */     } catch (Exception exception) {
/*    */       
/* 60 */       throw new RuntimeException("unrecoverable error retrieving nodeset", exception);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public XMLSignatureInput getXMLSignatureInput() {
/* 66 */     return this.xi;
/*    */   }
/*    */   
/*    */   private Set<Node> getNodeSet(List<NodeFilter> paramList) {
/* 70 */     if (this.xi.isNeedsToBeExpanded())
/*    */     {
/* 72 */       XMLUtils.circumventBug2650(XMLUtils.getOwnerDocument(this.xi.getSubNode()));
/*    */     }
/*    */     
/* 75 */     LinkedHashSet<Node> linkedHashSet1 = new LinkedHashSet();
/* 76 */     XMLUtils.getSet(this.xi.getSubNode(), linkedHashSet1, null, 
/* 77 */         !this.xi.isExcludeComments());
/* 78 */     LinkedHashSet<Node> linkedHashSet2 = new LinkedHashSet();
/* 79 */     for (Node node : linkedHashSet1) {
/* 80 */       Iterator<NodeFilter> iterator = paramList.iterator();
/* 81 */       boolean bool = false;
/* 82 */       while (iterator.hasNext() && !bool) {
/* 83 */         NodeFilter nodeFilter = iterator.next();
/* 84 */         if (nodeFilter.isNodeInclude(node) != 1) {
/* 85 */           bool = true;
/*    */         }
/*    */       } 
/* 88 */       if (!bool) {
/* 89 */         linkedHashSet2.add(node);
/*    */       }
/*    */     } 
/* 92 */     return linkedHashSet2;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/ApacheNodeSetData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */