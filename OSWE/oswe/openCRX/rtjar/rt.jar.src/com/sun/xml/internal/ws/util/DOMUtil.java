/*     */ package com.sun.xml.internal.ws.util;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.FactoryConfigurationError;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
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
/*     */ public class DOMUtil
/*     */ {
/*     */   private static DocumentBuilder db;
/*     */   
/*     */   public static Document createDom() {
/*  56 */     synchronized (DOMUtil.class) {
/*  57 */       if (db == null) {
/*     */         try {
/*  59 */           DocumentBuilderFactory dbf = XmlUtil.newDocumentBuilderFactory();
/*  60 */           db = dbf.newDocumentBuilder();
/*  61 */         } catch (ParserConfigurationException e) {
/*  62 */           throw new FactoryConfigurationError(e);
/*     */         } 
/*     */       }
/*  65 */       return db.newDocument();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void serializeNode(Element node, XMLStreamWriter writer) throws XMLStreamException {
/*  76 */     writeTagWithAttributes(node, writer);
/*     */     
/*  78 */     if (node.hasChildNodes()) {
/*  79 */       NodeList children = node.getChildNodes();
/*  80 */       for (int i = 0; i < children.getLength(); i++) {
/*  81 */         Node child = children.item(i);
/*  82 */         switch (child.getNodeType()) {
/*     */           case 7:
/*  84 */             writer.writeProcessingInstruction(child.getNodeValue());
/*     */             break;
/*     */ 
/*     */           
/*     */           case 4:
/*  89 */             writer.writeCData(child.getNodeValue());
/*     */             break;
/*     */           case 8:
/*  92 */             writer.writeComment(child.getNodeValue());
/*     */             break;
/*     */           case 3:
/*  95 */             writer.writeCharacters(child.getNodeValue());
/*     */             break;
/*     */           case 1:
/*  98 */             serializeNode((Element)child, writer);
/*     */             break;
/*     */         } 
/*     */       
/*     */       } 
/*     */     } 
/* 104 */     writer.writeEndElement();
/*     */   }
/*     */   
/*     */   public static void writeTagWithAttributes(Element node, XMLStreamWriter writer) throws XMLStreamException {
/* 108 */     String nodePrefix = fixNull(node.getPrefix());
/* 109 */     String nodeNS = fixNull(node.getNamespaceURI());
/*     */     
/* 111 */     String nodeLocalName = (node.getLocalName() == null) ? node.getNodeName() : node.getLocalName();
/*     */ 
/*     */ 
/*     */     
/* 115 */     boolean prefixDecl = isPrefixDeclared(writer, nodeNS, nodePrefix);
/* 116 */     writer.writeStartElement(nodePrefix, nodeLocalName, nodeNS);
/*     */     
/* 118 */     if (node.hasAttributes()) {
/* 119 */       NamedNodeMap attrs = node.getAttributes();
/* 120 */       int numOfAttributes = attrs.getLength();
/*     */ 
/*     */ 
/*     */       
/* 124 */       for (int i = 0; i < numOfAttributes; i++) {
/* 125 */         Node attr = attrs.item(i);
/* 126 */         String nsUri = fixNull(attr.getNamespaceURI());
/* 127 */         if (nsUri.equals("http://www.w3.org/2000/xmlns/")) {
/*     */           
/* 129 */           String local = attr.getLocalName().equals("xmlns") ? "" : attr.getLocalName();
/* 130 */           if (local.equals(nodePrefix) && attr.getNodeValue().equals(nodeNS)) {
/* 131 */             prefixDecl = true;
/*     */           }
/* 133 */           if (local.equals("")) {
/* 134 */             writer.writeDefaultNamespace(attr.getNodeValue());
/*     */           } else {
/*     */             
/* 137 */             writer.setPrefix(attr.getLocalName(), attr.getNodeValue());
/* 138 */             writer.writeNamespace(attr.getLocalName(), attr.getNodeValue());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     if (!prefixDecl) {
/* 145 */       writer.writeNamespace(nodePrefix, nodeNS);
/*     */     }
/*     */ 
/*     */     
/* 149 */     if (node.hasAttributes()) {
/* 150 */       NamedNodeMap attrs = node.getAttributes();
/* 151 */       int numOfAttributes = attrs.getLength();
/*     */       
/* 153 */       for (int i = 0; i < numOfAttributes; i++) {
/* 154 */         Node attr = attrs.item(i);
/* 155 */         String attrPrefix = fixNull(attr.getPrefix());
/* 156 */         String attrNS = fixNull(attr.getNamespaceURI());
/* 157 */         if (!attrNS.equals("http://www.w3.org/2000/xmlns/")) {
/* 158 */           String localName = attr.getLocalName();
/* 159 */           if (localName == null)
/*     */           {
/*     */             
/* 162 */             localName = attr.getNodeName();
/*     */           }
/* 164 */           boolean attrPrefixDecl = isPrefixDeclared(writer, attrNS, attrPrefix);
/* 165 */           if (!attrPrefix.equals("") && !attrPrefixDecl) {
/*     */ 
/*     */             
/* 168 */             writer.setPrefix(attr.getLocalName(), attr.getNodeValue());
/* 169 */             writer.writeNamespace(attrPrefix, attrNS);
/*     */           } 
/* 171 */           writer.writeAttribute(attrPrefix, attrNS, localName, attr.getNodeValue());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isPrefixDeclared(XMLStreamWriter writer, String nsUri, String prefix) {
/* 178 */     boolean prefixDecl = false;
/* 179 */     NamespaceContext nscontext = writer.getNamespaceContext();
/* 180 */     Iterator<String> prefixItr = nscontext.getPrefixes(nsUri);
/* 181 */     while (prefixItr.hasNext()) {
/* 182 */       if (prefix.equals(prefixItr.next())) {
/* 183 */         prefixDecl = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 187 */     return prefixDecl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getFirstChild(Element e, String nsUri, String local) {
/* 194 */     for (Node n = e.getFirstChild(); n != null; n = n.getNextSibling()) {
/* 195 */       if (n.getNodeType() == 1) {
/* 196 */         Element c = (Element)n;
/* 197 */         if (c.getLocalName().equals(local) && c.getNamespaceURI().equals(nsUri)) {
/* 198 */           return c;
/*     */         }
/*     */       } 
/*     */     } 
/* 202 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static String fixNull(@Nullable String s) {
/* 208 */     if (s == null) {
/* 209 */       return "";
/*     */     }
/* 211 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Element getFirstElementChild(Node parent) {
/* 221 */     for (Node n = parent.getFirstChild(); n != null; n = n.getNextSibling()) {
/* 222 */       if (n.getNodeType() == 1) {
/* 223 */         return (Element)n;
/*     */       }
/*     */     } 
/* 226 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public static List<Element> getChildElements(Node parent) {
/* 231 */     List<Element> elements = new ArrayList<>();
/* 232 */     for (Node n = parent.getFirstChild(); n != null; n = n.getNextSibling()) {
/* 233 */       if (n.getNodeType() == 1) {
/* 234 */         elements.add((Element)n);
/*     */       }
/*     */     } 
/* 237 */     return elements;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/DOMUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */