/*     */ package com.sun.org.apache.xml.internal.security.transforms.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.signature.NodeFilter;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ class XPath2NodeFilter
/*     */   implements NodeFilter
/*     */ {
/*     */   boolean hasUnionFilter;
/*     */   boolean hasSubtractFilter;
/*     */   boolean hasIntersectFilter;
/*     */   Set<Node> unionNodes;
/*     */   Set<Node> subtractNodes;
/*     */   Set<Node> intersectNodes;
/* 169 */   int inSubtract = -1;
/* 170 */   int inIntersect = -1;
/* 171 */   int inUnion = -1;
/*     */ 
/*     */   
/*     */   XPath2NodeFilter(List<NodeList> paramList1, List<NodeList> paramList2, List<NodeList> paramList3) {
/* 175 */     this.hasUnionFilter = !paramList1.isEmpty();
/* 176 */     this.unionNodes = convertNodeListToSet(paramList1);
/* 177 */     this.hasSubtractFilter = !paramList2.isEmpty();
/* 178 */     this.subtractNodes = convertNodeListToSet(paramList2);
/* 179 */     this.hasIntersectFilter = !paramList3.isEmpty();
/* 180 */     this.intersectNodes = convertNodeListToSet(paramList3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int isNodeInclude(Node paramNode) {
/* 187 */     byte b = 1;
/*     */     
/* 189 */     if (this.hasSubtractFilter && rooted(paramNode, this.subtractNodes)) {
/* 190 */       b = -1;
/* 191 */     } else if (this.hasIntersectFilter && !rooted(paramNode, this.intersectNodes)) {
/* 192 */       b = 0;
/*     */     } 
/*     */ 
/*     */     
/* 196 */     if (b == 1) {
/* 197 */       return 1;
/*     */     }
/* 199 */     if (this.hasUnionFilter) {
/* 200 */       if (rooted(paramNode, this.unionNodes)) {
/* 201 */         return 1;
/*     */       }
/* 203 */       b = 0;
/*     */     } 
/* 205 */     return b;
/*     */   }
/*     */   
/*     */   public int isNodeIncludeDO(Node paramNode, int paramInt) {
/* 209 */     byte b = 1;
/* 210 */     if (this.hasSubtractFilter) {
/* 211 */       if (this.inSubtract == -1 || paramInt <= this.inSubtract) {
/* 212 */         if (inList(paramNode, this.subtractNodes)) {
/* 213 */           this.inSubtract = paramInt;
/*     */         } else {
/* 215 */           this.inSubtract = -1;
/*     */         } 
/*     */       }
/* 218 */       if (this.inSubtract != -1) {
/* 219 */         b = -1;
/*     */       }
/*     */     } 
/* 222 */     if (b != -1 && this.hasIntersectFilter && (this.inIntersect == -1 || paramInt <= this.inIntersect))
/*     */     {
/* 224 */       if (!inList(paramNode, this.intersectNodes)) {
/* 225 */         this.inIntersect = -1;
/* 226 */         b = 0;
/*     */       } else {
/* 228 */         this.inIntersect = paramInt;
/*     */       } 
/*     */     }
/*     */     
/* 232 */     if (paramInt <= this.inUnion) {
/* 233 */       this.inUnion = -1;
/*     */     }
/* 235 */     if (b == 1) {
/* 236 */       return 1;
/*     */     }
/* 238 */     if (this.hasUnionFilter) {
/* 239 */       if (this.inUnion == -1 && inList(paramNode, this.unionNodes)) {
/* 240 */         this.inUnion = paramInt;
/*     */       }
/* 242 */       if (this.inUnion != -1) {
/* 243 */         return 1;
/*     */       }
/* 245 */       b = 0;
/*     */     } 
/*     */     
/* 248 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean rooted(Node paramNode, Set<Node> paramSet) {
/* 259 */     if (paramSet.isEmpty()) {
/* 260 */       return false;
/*     */     }
/* 262 */     if (paramSet.contains(paramNode)) {
/* 263 */       return true;
/*     */     }
/* 265 */     for (Node node : paramSet) {
/* 266 */       if (XMLUtils.isDescendantOrSelf(node, paramNode)) {
/* 267 */         return true;
/*     */       }
/*     */     } 
/* 270 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean inList(Node paramNode, Set<Node> paramSet) {
/* 281 */     return paramSet.contains(paramNode);
/*     */   }
/*     */   
/*     */   private static Set<Node> convertNodeListToSet(List<NodeList> paramList) {
/* 285 */     HashSet<Node> hashSet = new HashSet();
/* 286 */     for (NodeList nodeList : paramList) {
/* 287 */       int i = nodeList.getLength();
/*     */       
/* 289 */       for (byte b = 0; b < i; b++) {
/* 290 */         Node node = nodeList.item(b);
/* 291 */         hashSet.add(node);
/*     */       } 
/*     */     } 
/* 294 */     return hashSet;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/implementations/XPath2NodeFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */