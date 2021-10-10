/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMStructure;
/*     */ import javax.xml.crypto.dsig.keyinfo.PGPData;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
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
/*     */ public final class DOMPGPData
/*     */   extends DOMStructure
/*     */   implements PGPData
/*     */ {
/*     */   private final byte[] keyId;
/*     */   private final byte[] keyPacket;
/*     */   private final List<XMLStructure> externalElements;
/*     */   
/*     */   public DOMPGPData(byte[] paramArrayOfbyte, List<? extends XMLStructure> paramList) {
/*  73 */     if (paramArrayOfbyte == null) {
/*  74 */       throw new NullPointerException("keyPacket cannot be null");
/*     */     }
/*  76 */     if (paramList == null || paramList.isEmpty()) {
/*  77 */       this.externalElements = Collections.emptyList();
/*     */     } else {
/*  79 */       this
/*  80 */         .externalElements = Collections.unmodifiableList(new ArrayList<>(paramList)); byte b; int i;
/*  81 */       for (b = 0, i = this.externalElements.size(); b < i; b++) {
/*  82 */         if (!(this.externalElements.get(b) instanceof XMLStructure)) {
/*  83 */           throw new ClassCastException("other[" + b + "] is not a valid PGPData type");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     this.keyPacket = (byte[])paramArrayOfbyte.clone();
/*  89 */     checkKeyPacket(paramArrayOfbyte);
/*  90 */     this.keyId = null;
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
/*     */   public DOMPGPData(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, List<? extends XMLStructure> paramList) {
/* 116 */     if (paramArrayOfbyte1 == null) {
/* 117 */       throw new NullPointerException("keyId cannot be null");
/*     */     }
/*     */     
/* 120 */     if (paramArrayOfbyte1.length != 8) {
/* 121 */       throw new IllegalArgumentException("keyId must be 8 bytes long");
/*     */     }
/* 123 */     if (paramList == null || paramList.isEmpty()) {
/* 124 */       this.externalElements = Collections.emptyList();
/*     */     } else {
/* 126 */       this
/* 127 */         .externalElements = Collections.unmodifiableList(new ArrayList<>(paramList)); byte b; int i;
/* 128 */       for (b = 0, i = this.externalElements.size(); b < i; b++) {
/* 129 */         if (!(this.externalElements.get(b) instanceof XMLStructure)) {
/* 130 */           throw new ClassCastException("other[" + b + "] is not a valid PGPData type");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     this.keyId = (byte[])paramArrayOfbyte1.clone();
/* 136 */     this
/* 137 */       .keyPacket = (paramArrayOfbyte2 == null) ? null : (byte[])paramArrayOfbyte2.clone();
/* 138 */     if (paramArrayOfbyte2 != null) {
/* 139 */       checkKeyPacket(paramArrayOfbyte2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMPGPData(Element paramElement) throws MarshalException {
/* 150 */     byte[] arrayOfByte1 = null;
/* 151 */     byte[] arrayOfByte2 = null;
/* 152 */     NodeList nodeList = paramElement.getChildNodes();
/* 153 */     int i = nodeList.getLength();
/* 154 */     ArrayList<DOMStructure> arrayList = new ArrayList(i);
/* 155 */     for (byte b = 0; b < i; b++) {
/* 156 */       Node node = nodeList.item(b);
/* 157 */       if (node.getNodeType() == 1) {
/* 158 */         Element element = (Element)node;
/* 159 */         String str = element.getLocalName();
/*     */         try {
/* 161 */           if (str.equals("PGPKeyID")) {
/* 162 */             arrayOfByte1 = Base64.decode(element);
/* 163 */           } else if (str.equals("PGPKeyPacket")) {
/* 164 */             arrayOfByte2 = Base64.decode(element);
/*     */           } else {
/* 166 */             arrayList
/* 167 */               .add(new DOMStructure(element));
/*     */           } 
/* 169 */         } catch (Base64DecodingException base64DecodingException) {
/* 170 */           throw new MarshalException(base64DecodingException);
/*     */         } 
/*     */       } 
/*     */     } 
/* 174 */     this.keyId = arrayOfByte1;
/* 175 */     this.keyPacket = arrayOfByte2;
/* 176 */     this.externalElements = Collections.unmodifiableList((List)arrayList);
/*     */   }
/*     */   
/*     */   public byte[] getKeyId() {
/* 180 */     return (this.keyId == null) ? null : (byte[])this.keyId.clone();
/*     */   }
/*     */   
/*     */   public byte[] getKeyPacket() {
/* 184 */     return (this.keyPacket == null) ? null : (byte[])this.keyPacket.clone();
/*     */   }
/*     */   
/*     */   public List getExternalElements() {
/* 188 */     return this.externalElements;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 194 */     Document document = DOMUtils.getOwnerDocument(paramNode);
/* 195 */     Element element = DOMUtils.createElement(document, "PGPData", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */ 
/*     */ 
/*     */     
/* 199 */     if (this.keyId != null) {
/* 200 */       Element element1 = DOMUtils.createElement(document, "PGPKeyID", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */ 
/*     */       
/* 203 */       element1
/* 204 */         .appendChild(document.createTextNode(Base64.encode(this.keyId)));
/* 205 */       element.appendChild(element1);
/*     */     } 
/*     */ 
/*     */     
/* 209 */     if (this.keyPacket != null) {
/* 210 */       Element element1 = DOMUtils.createElement(document, "PGPKeyPacket", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */ 
/*     */ 
/*     */       
/* 214 */       element1
/* 215 */         .appendChild(document.createTextNode(Base64.encode(this.keyPacket)));
/* 216 */       element.appendChild(element1);
/*     */     } 
/*     */ 
/*     */     
/* 220 */     for (XMLStructure xMLStructure : this.externalElements) {
/* 221 */       DOMUtils.appendChild(element, ((DOMStructure)xMLStructure)
/* 222 */           .getNode());
/*     */     }
/*     */     
/* 225 */     paramNode.appendChild(element);
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
/*     */   private void checkKeyPacket(byte[] paramArrayOfbyte) {
/* 238 */     if (paramArrayOfbyte.length < 3) {
/* 239 */       throw new IllegalArgumentException("keypacket must be at least 3 bytes long");
/*     */     }
/*     */ 
/*     */     
/* 243 */     byte b = paramArrayOfbyte[0];
/*     */     
/* 245 */     if ((b & 0x80) != 128) {
/* 246 */       throw new IllegalArgumentException("keypacket tag is invalid: bit 7 is not set");
/*     */     }
/*     */ 
/*     */     
/* 250 */     if ((b & 0x40) != 64) {
/* 251 */       throw new IllegalArgumentException("old keypacket tag format is unsupported");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 256 */     if ((b & 0x6) != 6 && (b & 0xE) != 14 && (b & 0x5) != 5 && (b & 0x7) != 7)
/*     */     {
/* 258 */       throw new IllegalArgumentException("keypacket tag is invalid: must be 6, 14, 5, or 7");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMPGPData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */