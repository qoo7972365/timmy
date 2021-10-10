/*     */ package com.sun.org.apache.xml.internal.security.encryption;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.HashMap;
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
/*     */ public abstract class AbstractSerializer
/*     */   implements Serializer
/*     */ {
/*     */   protected Canonicalizer canon;
/*     */   
/*     */   public void setCanonicalizer(Canonicalizer paramCanonicalizer) {
/*  48 */     this.canon = paramCanonicalizer;
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
/*     */   public String serialize(Element paramElement) throws Exception {
/*  63 */     return canonSerialize(paramElement);
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
/*     */   public byte[] serializeToByteArray(Element paramElement) throws Exception {
/*  76 */     return canonSerializeToByteArray(paramElement);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String serialize(NodeList paramNodeList) throws Exception {
/* 102 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 103 */     this.canon.setWriter(byteArrayOutputStream);
/* 104 */     this.canon.notReset();
/* 105 */     for (byte b = 0; b < paramNodeList.getLength(); b++) {
/* 106 */       this.canon.canonicalizeSubtree(paramNodeList.item(b));
/*     */     }
/* 108 */     String str = byteArrayOutputStream.toString("UTF-8");
/* 109 */     byteArrayOutputStream.reset();
/* 110 */     return str;
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
/*     */   public byte[] serializeToByteArray(NodeList paramNodeList) throws Exception {
/* 123 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 124 */     this.canon.setWriter(byteArrayOutputStream);
/* 125 */     this.canon.notReset();
/* 126 */     for (byte b = 0; b < paramNodeList.getLength(); b++) {
/* 127 */       this.canon.canonicalizeSubtree(paramNodeList.item(b));
/*     */     }
/* 129 */     return byteArrayOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String canonSerialize(Node paramNode) throws Exception {
/* 139 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 140 */     this.canon.setWriter(byteArrayOutputStream);
/* 141 */     this.canon.notReset();
/* 142 */     this.canon.canonicalizeSubtree(paramNode);
/* 143 */     String str = byteArrayOutputStream.toString("UTF-8");
/* 144 */     byteArrayOutputStream.reset();
/* 145 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] canonSerializeToByteArray(Node paramNode) throws Exception {
/* 155 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 156 */     this.canon.setWriter(byteArrayOutputStream);
/* 157 */     this.canon.notReset();
/* 158 */     this.canon.canonicalizeSubtree(paramNode);
/* 159 */     return byteArrayOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Node deserialize(String paramString, Node paramNode) throws XMLEncryptionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Node deserialize(byte[] paramArrayOfbyte, Node paramNode) throws XMLEncryptionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static byte[] createContext(byte[] paramArrayOfbyte, Node paramNode) throws XMLEncryptionException {
/* 180 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*     */     try {
/* 182 */       OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, "UTF-8");
/* 183 */       outputStreamWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><dummy");
/*     */ 
/*     */       
/* 186 */       HashMap<Object, Object> hashMap = new HashMap<>();
/* 187 */       Node node = paramNode;
/* 188 */       while (node != null) {
/* 189 */         NamedNodeMap namedNodeMap = node.getAttributes();
/* 190 */         if (namedNodeMap != null) {
/* 191 */           for (byte b = 0; b < namedNodeMap.getLength(); b++) {
/* 192 */             Node node1 = namedNodeMap.item(b);
/* 193 */             String str = node1.getNodeName();
/* 194 */             if ((str.equals("xmlns") || str.startsWith("xmlns:")) && 
/* 195 */               !hashMap.containsKey(node1.getNodeName())) {
/* 196 */               outputStreamWriter.write(" ");
/* 197 */               outputStreamWriter.write(str);
/* 198 */               outputStreamWriter.write("=\"");
/* 199 */               outputStreamWriter.write(node1.getNodeValue());
/* 200 */               outputStreamWriter.write("\"");
/* 201 */               hashMap.put(str, node1.getNodeValue());
/*     */             } 
/*     */           } 
/*     */         }
/* 205 */         node = node.getParentNode();
/*     */       } 
/* 207 */       outputStreamWriter.write(">");
/* 208 */       outputStreamWriter.flush();
/* 209 */       byteArrayOutputStream.write(paramArrayOfbyte);
/*     */       
/* 211 */       outputStreamWriter.write("</dummy>");
/* 212 */       outputStreamWriter.close();
/*     */       
/* 214 */       return byteArrayOutputStream.toByteArray();
/* 215 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 216 */       throw new XMLEncryptionException("empty", unsupportedEncodingException);
/* 217 */     } catch (IOException iOException) {
/* 218 */       throw new XMLEncryptionException("empty", iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String createContext(String paramString, Node paramNode) {
/* 224 */     StringBuilder stringBuilder = new StringBuilder();
/* 225 */     stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><dummy");
/*     */ 
/*     */     
/* 228 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 229 */     Node node = paramNode;
/* 230 */     while (node != null) {
/* 231 */       NamedNodeMap namedNodeMap = node.getAttributes();
/* 232 */       if (namedNodeMap != null) {
/* 233 */         for (byte b = 0; b < namedNodeMap.getLength(); b++) {
/* 234 */           Node node1 = namedNodeMap.item(b);
/* 235 */           String str = node1.getNodeName();
/* 236 */           if ((str.equals("xmlns") || str.startsWith("xmlns:")) && 
/* 237 */             !hashMap.containsKey(node1.getNodeName())) {
/* 238 */             stringBuilder.append(" " + str + "=\"" + node1.getNodeValue() + "\"");
/* 239 */             hashMap.put(str, node1.getNodeValue());
/*     */           } 
/*     */         } 
/*     */       }
/* 243 */       node = node.getParentNode();
/*     */     } 
/* 245 */     stringBuilder.append(">" + paramString + "</dummy>");
/* 246 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/encryption/AbstractSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */