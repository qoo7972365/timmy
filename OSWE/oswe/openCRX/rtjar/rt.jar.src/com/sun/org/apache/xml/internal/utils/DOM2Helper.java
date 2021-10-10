/*     */ package com.sun.org.apache.xml.internal.utils;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeProxy;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.NamedNodeMap;
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
/*     */ public final class DOM2Helper
/*     */ {
/*     */   public static String getLocalNameOfNode(Node n) {
/*  55 */     String name = n.getLocalName();
/*  56 */     return (null == name) ? getLocalNameOfNodeFallback(n) : name;
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
/*     */   private static String getLocalNameOfNodeFallback(Node n) {
/*  72 */     String qname = n.getNodeName();
/*  73 */     int index = qname.indexOf(':');
/*     */     
/*  75 */     return (index < 0) ? qname : qname.substring(index + 1);
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
/*     */   public static String getNamespaceOfNode(Node n) {
/*  93 */     return n.getNamespaceURI();
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
/*     */   public static boolean isNodeAfter(Node node1, Node node2) {
/* 112 */     if (node1 == node2 || isNodeTheSame(node1, node2)) {
/* 113 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 117 */     boolean isNodeAfter = true;
/*     */     
/* 119 */     Node parent1 = getParentOfNode(node1);
/* 120 */     Node parent2 = getParentOfNode(node2);
/*     */ 
/*     */     
/* 123 */     if (parent1 == parent2 || isNodeTheSame(parent1, parent2)) {
/*     */       
/* 125 */       if (null != parent1) {
/* 126 */         isNodeAfter = isNodeAfterSibling(parent1, node1, node2);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 138 */       int nParents1 = 2, nParents2 = 2;
/*     */       
/* 140 */       while (parent1 != null) {
/* 141 */         nParents1++;
/* 142 */         parent1 = getParentOfNode(parent1);
/*     */       } 
/*     */       
/* 145 */       while (parent2 != null) {
/* 146 */         nParents2++;
/*     */         
/* 148 */         parent2 = getParentOfNode(parent2);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 153 */       Node startNode1 = node1, startNode2 = node2;
/*     */ 
/*     */ 
/*     */       
/* 157 */       if (nParents1 < nParents2) {
/*     */         
/* 159 */         int adjust = nParents2 - nParents1;
/*     */         
/* 161 */         for (int i = 0; i < adjust; i++) {
/* 162 */           startNode2 = getParentOfNode(startNode2);
/*     */         }
/* 164 */       } else if (nParents1 > nParents2) {
/*     */         
/* 166 */         int adjust = nParents1 - nParents2;
/*     */         
/* 168 */         for (int i = 0; i < adjust; i++) {
/* 169 */           startNode1 = getParentOfNode(startNode1);
/*     */         }
/*     */       } 
/*     */       
/* 173 */       Node prevChild1 = null, prevChild2 = null;
/*     */ 
/*     */       
/* 176 */       while (null != startNode1) {
/* 177 */         if (startNode1 == startNode2 || isNodeTheSame(startNode1, startNode2)) {
/*     */           
/* 179 */           if (null == prevChild1) {
/*     */ 
/*     */ 
/*     */             
/* 183 */             isNodeAfter = (nParents1 < nParents2);
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 188 */           isNodeAfter = isNodeAfterSibling(startNode1, prevChild1, prevChild2);
/*     */ 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */ 
/*     */         
/* 196 */         prevChild1 = startNode1;
/* 197 */         startNode1 = getParentOfNode(startNode1);
/* 198 */         prevChild2 = startNode2;
/* 199 */         startNode2 = getParentOfNode(startNode2);
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     return isNodeAfter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNodeTheSame(Node node1, Node node2) {
/* 214 */     if (node1 instanceof DTMNodeProxy && node2 instanceof DTMNodeProxy) {
/* 215 */       return ((DTMNodeProxy)node1).equals(node2);
/*     */     }
/* 217 */     return (node1 == node2);
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
/*     */   public static Node getParentOfNode(Node node) {
/* 234 */     Node parent = node.getParentNode();
/* 235 */     if (parent == null && 2 == node.getNodeType()) {
/* 236 */       parent = ((Attr)node).getOwnerElement();
/*     */     }
/* 238 */     return parent;
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
/*     */   private static boolean isNodeAfterSibling(Node parent, Node child1, Node child2) {
/* 258 */     boolean isNodeAfterSibling = false;
/* 259 */     short child1type = child1.getNodeType();
/* 260 */     short child2type = child2.getNodeType();
/*     */     
/* 262 */     if (2 != child1type && 2 == child2type) {
/*     */ 
/*     */ 
/*     */       
/* 266 */       isNodeAfterSibling = false;
/* 267 */     } else if (2 == child1type && 2 != child2type) {
/*     */ 
/*     */ 
/*     */       
/* 271 */       isNodeAfterSibling = true;
/* 272 */     } else if (2 == child1type) {
/* 273 */       NamedNodeMap children = parent.getAttributes();
/* 274 */       int nNodes = children.getLength();
/* 275 */       boolean found1 = false, found2 = false;
/*     */ 
/*     */       
/* 278 */       for (int i = 0; i < nNodes; i++) {
/* 279 */         Node child = children.item(i);
/*     */         
/* 281 */         if (child1 == child || isNodeTheSame(child1, child)) {
/* 282 */           if (found2) {
/* 283 */             isNodeAfterSibling = false;
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 288 */           found1 = true;
/* 289 */         } else if (child2 == child || isNodeTheSame(child2, child)) {
/* 290 */           if (found1) {
/* 291 */             isNodeAfterSibling = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 296 */           found2 = true;
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 311 */       Node child = parent.getFirstChild();
/* 312 */       boolean found1 = false, found2 = false;
/*     */       
/* 314 */       while (null != child) {
/*     */ 
/*     */         
/* 317 */         if (child1 == child || isNodeTheSame(child1, child)) {
/* 318 */           if (found2) {
/* 319 */             isNodeAfterSibling = false;
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 324 */           found1 = true;
/* 325 */         } else if (child2 == child || isNodeTheSame(child2, child)) {
/* 326 */           if (found1) {
/* 327 */             isNodeAfterSibling = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 332 */           found2 = true;
/*     */         } 
/*     */         
/* 335 */         child = child.getNextSibling();
/*     */       } 
/*     */     } 
/*     */     
/* 339 */     return isNodeAfterSibling;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/utils/DOM2Helper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */