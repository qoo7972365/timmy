/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.security.cert.CRLException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CRLExtensions
/*     */ {
/*  66 */   private Map<String, Extension> map = Collections.synchronizedMap(new TreeMap<>());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean unsupportedCritExt = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CRLExtensions() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CRLExtensions(DerInputStream paramDerInputStream) throws CRLException {
/*  83 */     init(paramDerInputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   private void init(DerInputStream paramDerInputStream) throws CRLException {
/*     */     try {
/*  89 */       DerInputStream derInputStream = paramDerInputStream;
/*     */       
/*  91 */       byte b = (byte)paramDerInputStream.peekByte();
/*     */       
/*  93 */       if ((b & 0xC0) == 128 && (b & 0x1F) == 0) {
/*     */         
/*  95 */         DerValue derValue = derInputStream.getDerValue();
/*  96 */         derInputStream = derValue.data;
/*     */       } 
/*     */       
/*  99 */       DerValue[] arrayOfDerValue = derInputStream.getSequence(5);
/* 100 */       for (byte b1 = 0; b1 < arrayOfDerValue.length; b1++) {
/* 101 */         Extension extension = new Extension(arrayOfDerValue[b1]);
/* 102 */         parseExtension(extension);
/*     */       } 
/* 104 */     } catch (IOException iOException) {
/* 105 */       throw new CRLException("Parsing error: " + iOException.toString());
/*     */     } 
/*     */   }
/*     */   
/* 109 */   private static final Class[] PARAMS = new Class[] { Boolean.class, Object.class };
/*     */ 
/*     */   
/*     */   private void parseExtension(Extension paramExtension) throws CRLException {
/*     */     try {
/* 114 */       Class<?> clazz = OIDMap.getClass(paramExtension.getExtensionId());
/* 115 */       if (clazz == null) {
/* 116 */         if (paramExtension.isCritical())
/* 117 */           this.unsupportedCritExt = true; 
/* 118 */         if (this.map.put(paramExtension.getExtensionId().toString(), paramExtension) != null)
/* 119 */           throw new CRLException("Duplicate extensions not allowed"); 
/*     */         return;
/*     */       } 
/* 122 */       Constructor<?> constructor = clazz.getConstructor(PARAMS);
/*     */       
/* 124 */       Object[] arrayOfObject = { Boolean.valueOf(paramExtension.isCritical()), paramExtension.getExtensionValue() };
/* 125 */       CertAttrSet certAttrSet = (CertAttrSet)constructor.newInstance(arrayOfObject);
/* 126 */       if (this.map.put(certAttrSet.getName(), (Extension)certAttrSet) != null) {
/* 127 */         throw new CRLException("Duplicate extensions not allowed");
/*     */       }
/* 129 */     } catch (InvocationTargetException invocationTargetException) {
/* 130 */       throw new CRLException(invocationTargetException.getTargetException().getMessage());
/* 131 */     } catch (Exception exception) {
/* 132 */       throw new CRLException(exception.toString());
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
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream, boolean paramBoolean) throws CRLException {
/*     */     try {
/* 147 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/* 148 */       Collection<Extension> collection = this.map.values();
/* 149 */       Object[] arrayOfObject = collection.toArray();
/*     */       
/* 151 */       for (byte b = 0; b < arrayOfObject.length; b++) {
/* 152 */         if (arrayOfObject[b] instanceof CertAttrSet) {
/* 153 */           ((CertAttrSet)arrayOfObject[b]).encode(derOutputStream1);
/* 154 */         } else if (arrayOfObject[b] instanceof Extension) {
/* 155 */           ((Extension)arrayOfObject[b]).encode(derOutputStream1);
/*     */         } else {
/* 157 */           throw new CRLException("Illegal extension object");
/*     */         } 
/*     */       } 
/* 160 */       DerOutputStream derOutputStream2 = new DerOutputStream();
/* 161 */       derOutputStream2.write((byte)48, derOutputStream1);
/*     */       
/* 163 */       DerOutputStream derOutputStream3 = new DerOutputStream();
/* 164 */       if (paramBoolean) {
/* 165 */         derOutputStream3.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/*     */       } else {
/*     */         
/* 168 */         derOutputStream3 = derOutputStream2;
/*     */       } 
/* 170 */       paramOutputStream.write(derOutputStream3.toByteArray());
/* 171 */     } catch (IOException iOException) {
/* 172 */       throw new CRLException("Encoding error: " + iOException.toString());
/* 173 */     } catch (CertificateException certificateException) {
/* 174 */       throw new CRLException("Encoding error: " + certificateException.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Extension get(String paramString) {
/*     */     String str1;
/* 184 */     X509AttributeName x509AttributeName = new X509AttributeName(paramString);
/*     */     
/* 186 */     String str2 = x509AttributeName.getPrefix();
/* 187 */     if (str2.equalsIgnoreCase("x509")) {
/* 188 */       int i = paramString.lastIndexOf(".");
/* 189 */       str1 = paramString.substring(i + 1);
/*     */     } else {
/* 191 */       str1 = paramString;
/* 192 */     }  return this.map.get(str1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) {
/* 203 */     this.map.put(paramString, (Extension)paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) {
/* 212 */     this.map.remove(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<Extension> getElements() {
/* 220 */     return Collections.enumeration(this.map.values());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Extension> getAllExtensions() {
/* 228 */     return this.map.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasUnsupportedCriticalExtension() {
/* 236 */     return this.unsupportedCritExt;
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
/*     */   public boolean equals(Object paramObject) {
/* 250 */     if (this == paramObject)
/* 251 */       return true; 
/* 252 */     if (!(paramObject instanceof CRLExtensions)) {
/* 253 */       return false;
/*     */     }
/* 255 */     Collection<Extension> collection = ((CRLExtensions)paramObject).getAllExtensions();
/* 256 */     Object[] arrayOfObject = collection.toArray();
/*     */     
/* 258 */     int i = arrayOfObject.length;
/* 259 */     if (i != this.map.size()) {
/* 260 */       return false;
/*     */     }
/*     */     
/* 263 */     String str = null;
/* 264 */     for (byte b = 0; b < i; b++) {
/* 265 */       if (arrayOfObject[b] instanceof CertAttrSet)
/* 266 */         str = ((CertAttrSet)arrayOfObject[b]).getName(); 
/* 267 */       Extension extension1 = (Extension)arrayOfObject[b];
/* 268 */       if (str == null)
/* 269 */         str = extension1.getExtensionId().toString(); 
/* 270 */       Extension extension2 = this.map.get(str);
/* 271 */       if (extension2 == null)
/* 272 */         return false; 
/* 273 */       if (!extension2.equals(extension1))
/* 274 */         return false; 
/*     */     } 
/* 276 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 285 */     return this.map.hashCode();
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
/*     */   public String toString() {
/* 297 */     return this.map.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/CRLExtensions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */