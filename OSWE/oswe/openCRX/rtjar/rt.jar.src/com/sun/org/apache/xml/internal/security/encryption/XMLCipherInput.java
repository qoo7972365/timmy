/*     */ package com.sun.org.apache.xml.internal.security.encryption;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transforms;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
/*     */ import java.io.IOException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.w3c.dom.Attr;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLCipherInput
/*     */ {
/*  53 */   private static Logger logger = Logger.getLogger(XMLCipherInput.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private CipherData cipherData;
/*     */ 
/*     */ 
/*     */   
/*     */   private int mode;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean secureValidation;
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLCipherInput(CipherData paramCipherData) throws XMLEncryptionException {
/*  70 */     this.cipherData = paramCipherData;
/*  71 */     this.mode = 2;
/*  72 */     if (this.cipherData == null) {
/*  73 */       throw new XMLEncryptionException("CipherData is null");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLCipherInput(EncryptedType paramEncryptedType) throws XMLEncryptionException {
/*  85 */     this.cipherData = (paramEncryptedType == null) ? null : paramEncryptedType.getCipherData();
/*  86 */     this.mode = 2;
/*  87 */     if (this.cipherData == null) {
/*  88 */       throw new XMLEncryptionException("CipherData is null");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSecureValidation(boolean paramBoolean) {
/*  96 */     this.secureValidation = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBytes() throws XMLEncryptionException {
/* 106 */     if (this.mode == 2) {
/* 107 */       return getDecryptBytes();
/*     */     }
/* 109 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] getDecryptBytes() throws XMLEncryptionException {
/* 118 */     String str = null;
/*     */     
/* 120 */     if (this.cipherData.getDataType() == 2) {
/*     */       
/* 122 */       if (logger.isLoggable(Level.FINE)) {
/* 123 */         logger.log(Level.FINE, "Found a reference type CipherData");
/*     */       }
/* 125 */       CipherReference cipherReference = this.cipherData.getCipherReference();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 130 */       Attr attr = cipherReference.getURIAsAttr();
/* 131 */       XMLSignatureInput xMLSignatureInput = null;
/*     */ 
/*     */       
/*     */       try {
/* 135 */         ResourceResolver resourceResolver = ResourceResolver.getInstance(attr, null, this.secureValidation);
/* 136 */         xMLSignatureInput = resourceResolver.resolve(attr, null, this.secureValidation);
/* 137 */       } catch (ResourceResolverException resourceResolverException) {
/* 138 */         throw new XMLEncryptionException("empty", resourceResolverException);
/*     */       } 
/*     */       
/* 141 */       if (xMLSignatureInput != null) {
/* 142 */         if (logger.isLoggable(Level.FINE)) {
/* 143 */           logger.log(Level.FINE, "Managed to resolve URI \"" + cipherReference.getURI() + "\"");
/*     */         }
/*     */       }
/* 146 */       else if (logger.isLoggable(Level.FINE)) {
/* 147 */         logger.log(Level.FINE, "Failed to resolve URI \"" + cipherReference.getURI() + "\"");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 152 */       Transforms transforms = cipherReference.getTransforms();
/* 153 */       if (transforms != null) {
/* 154 */         if (logger.isLoggable(Level.FINE)) {
/* 155 */           logger.log(Level.FINE, "Have transforms in cipher reference");
/*     */         }
/*     */         
/*     */         try {
/* 159 */           Transforms transforms1 = transforms.getDSTransforms();
/* 160 */           transforms1.setSecureValidation(this.secureValidation);
/* 161 */           xMLSignatureInput = transforms1.performTransforms(xMLSignatureInput);
/* 162 */         } catch (TransformationException transformationException) {
/* 163 */           throw new XMLEncryptionException("empty", transformationException);
/*     */         } 
/*     */       } 
/*     */       
/*     */       try {
/* 168 */         return xMLSignatureInput.getBytes();
/* 169 */       } catch (IOException iOException) {
/* 170 */         throw new XMLEncryptionException("empty", iOException);
/* 171 */       } catch (CanonicalizationException canonicalizationException) {
/* 172 */         throw new XMLEncryptionException("empty", canonicalizationException);
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     if (this.cipherData.getDataType() == 1) {
/* 177 */       str = this.cipherData.getCipherValue().getValue();
/*     */     } else {
/* 179 */       throw new XMLEncryptionException("CipherData.getDataType() returned unexpected value");
/*     */     } 
/*     */     
/* 182 */     if (logger.isLoggable(Level.FINE)) {
/* 183 */       logger.log(Level.FINE, "Encrypted octets:\n" + str);
/*     */     }
/*     */     
/*     */     try {
/* 187 */       return Base64.decode(str);
/* 188 */     } catch (Base64DecodingException base64DecodingException) {
/* 189 */       throw new XMLEncryptionException("empty", base64DecodingException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/encryption/XMLCipherInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */