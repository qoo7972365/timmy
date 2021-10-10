/*     */ package com.sun.org.apache.xpath.internal.objects;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMIterator;
/*     */ import com.sun.org.apache.xml.internal.utils.WrappedRuntimeException;
/*     */ import com.sun.org.apache.xpath.internal.XPathContext;
/*     */ import com.sun.org.apache.xpath.internal.axes.OneStepIterator;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.traversal.NodeIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XObjectFactory
/*     */ {
/*     */   public static XObject create(Object val) {
/*     */     XObject result;
/*  50 */     if (val instanceof XObject) {
/*     */       
/*  52 */       result = (XObject)val;
/*     */     }
/*  54 */     else if (val instanceof String) {
/*     */       
/*  56 */       result = new XString((String)val);
/*     */     }
/*  58 */     else if (val instanceof Boolean) {
/*     */       
/*  60 */       result = new XBoolean((Boolean)val);
/*     */     }
/*  62 */     else if (val instanceof Double) {
/*     */       
/*  64 */       result = new XNumber((Double)val);
/*     */     }
/*     */     else {
/*     */       
/*  68 */       result = new XObject(val);
/*     */     } 
/*     */     
/*  71 */     return result;
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
/*     */   public static XObject create(Object val, XPathContext xctxt) {
/*     */     XObject result;
/*  89 */     if (val instanceof XObject) {
/*     */       
/*  91 */       result = (XObject)val;
/*     */     }
/*  93 */     else if (val instanceof String) {
/*     */       
/*  95 */       result = new XString((String)val);
/*     */     }
/*  97 */     else if (val instanceof Boolean) {
/*     */       
/*  99 */       result = new XBoolean((Boolean)val);
/*     */     }
/* 101 */     else if (val instanceof Number) {
/*     */       
/* 103 */       result = new XNumber((Number)val);
/*     */     }
/* 105 */     else if (val instanceof DTM) {
/*     */       
/* 107 */       DTM dtm = (DTM)val;
/*     */       
/*     */       try {
/* 110 */         int dtmRoot = dtm.getDocument();
/* 111 */         DTMAxisIterator iter = dtm.getAxisIterator(13);
/* 112 */         iter.setStartNode(dtmRoot);
/* 113 */         DTMIterator iterator = new OneStepIterator(iter, 13);
/* 114 */         iterator.setRoot(dtmRoot, xctxt);
/* 115 */         result = new XNodeSet(iterator);
/*     */       }
/* 117 */       catch (Exception ex) {
/*     */         
/* 119 */         throw new WrappedRuntimeException(ex);
/*     */       }
/*     */     
/* 122 */     } else if (val instanceof DTMAxisIterator) {
/*     */       
/* 124 */       DTMAxisIterator iter = (DTMAxisIterator)val;
/*     */       
/*     */       try {
/* 127 */         DTMIterator iterator = new OneStepIterator(iter, 13);
/* 128 */         iterator.setRoot(iter.getStartNode(), xctxt);
/* 129 */         result = new XNodeSet(iterator);
/*     */       }
/* 131 */       catch (Exception ex) {
/*     */         
/* 133 */         throw new WrappedRuntimeException(ex);
/*     */       }
/*     */     
/* 136 */     } else if (val instanceof DTMIterator) {
/*     */       
/* 138 */       result = new XNodeSet((DTMIterator)val);
/*     */ 
/*     */     
/*     */     }
/* 142 */     else if (val instanceof Node) {
/*     */       
/* 144 */       result = new XNodeSetForDOM((Node)val, xctxt);
/*     */ 
/*     */     
/*     */     }
/* 148 */     else if (val instanceof NodeList) {
/*     */       
/* 150 */       result = new XNodeSetForDOM((NodeList)val, xctxt);
/*     */     }
/* 152 */     else if (val instanceof NodeIterator) {
/*     */       
/* 154 */       result = new XNodeSetForDOM((NodeIterator)val, xctxt);
/*     */     }
/*     */     else {
/*     */       
/* 158 */       result = new XObject(val);
/*     */     } 
/*     */     
/* 161 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/objects/XObjectFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */