/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.xml.crypto.XMLCryptoContext;
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
/*     */ public final class Utils
/*     */ {
/*     */   public static byte[] readBytesFromStream(InputStream paramInputStream) throws IOException {
/*     */     int i;
/*  51 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*  52 */     byte[] arrayOfByte = new byte[1024];
/*     */     do {
/*  54 */       i = paramInputStream.read(arrayOfByte);
/*  55 */       if (i == -1) {
/*     */         break;
/*     */       }
/*  58 */       byteArrayOutputStream.write(arrayOfByte, 0, i);
/*  59 */     } while (i >= 1024);
/*     */ 
/*     */ 
/*     */     
/*  63 */     return byteArrayOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Set<Node> toNodeSet(Iterator<Node> paramIterator) {
/*  74 */     HashSet<Node> hashSet = new HashSet();
/*  75 */     while (paramIterator.hasNext()) {
/*  76 */       Node node = paramIterator.next();
/*  77 */       hashSet.add(node);
/*     */       
/*  79 */       if (node.getNodeType() == 1) {
/*  80 */         NamedNodeMap namedNodeMap = node.getAttributes(); byte b; int i;
/*  81 */         for (b = 0, i = namedNodeMap.getLength(); b < i; b++) {
/*  82 */           hashSet.add(namedNodeMap.item(b));
/*     */         }
/*     */       } 
/*     */     } 
/*  86 */     return hashSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String parseIdFromSameDocumentURI(String paramString) {
/*  93 */     if (paramString.length() == 0) {
/*  94 */       return null;
/*     */     }
/*  96 */     String str = paramString.substring(1);
/*  97 */     if (str != null && str.startsWith("xpointer(id(")) {
/*  98 */       int i = str.indexOf('\'');
/*  99 */       int j = str.indexOf('\'', i + 1);
/* 100 */       str = str.substring(i + 1, j);
/*     */     } 
/* 102 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean sameDocumentURI(String paramString) {
/* 109 */     return (paramString != null && (paramString.length() == 0 || paramString.charAt(0) == '#'));
/*     */   }
/*     */   
/*     */   static boolean secureValidation(XMLCryptoContext paramXMLCryptoContext) {
/* 113 */     if (paramXMLCryptoContext == null) {
/* 114 */       return false;
/*     */     }
/* 116 */     return getBoolean(paramXMLCryptoContext, "org.jcp.xml.dsig.secureValidation");
/*     */   }
/*     */   
/*     */   private static boolean getBoolean(XMLCryptoContext paramXMLCryptoContext, String paramString) {
/* 120 */     Boolean bool = (Boolean)paramXMLCryptoContext.getProperty(paramString);
/* 121 */     return (bool != null && bool.booleanValue());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */