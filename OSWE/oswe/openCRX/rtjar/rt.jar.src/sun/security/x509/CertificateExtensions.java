/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertificateExtensions
/*     */   implements CertAttrSet<Extension>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions";
/*     */   public static final String NAME = "extensions";
/*  58 */   private static final Debug debug = Debug.getInstance("x509");
/*     */   
/*  60 */   private Map<String, Extension> map = Collections.synchronizedMap(new TreeMap<>());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean unsupportedCritExt = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, Extension> unparseableExtensions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertificateExtensions(DerInputStream paramDerInputStream) throws IOException {
/*  78 */     init(paramDerInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(DerInputStream paramDerInputStream) throws IOException {
/*  84 */     DerValue[] arrayOfDerValue = paramDerInputStream.getSequence(5);
/*     */     
/*  86 */     for (byte b = 0; b < arrayOfDerValue.length; b++) {
/*  87 */       Extension extension = new Extension(arrayOfDerValue[b]);
/*  88 */       parseExtension(extension);
/*     */     } 
/*     */   }
/*     */   
/*  92 */   private static Class[] PARAMS = new Class[] { Boolean.class, Object.class };
/*     */ 
/*     */   
/*     */   private void parseExtension(Extension paramExtension) throws IOException {
/*     */     try {
/*  97 */       Class<?> clazz = OIDMap.getClass(paramExtension.getExtensionId());
/*  98 */       if (clazz == null) {
/*  99 */         if (paramExtension.isCritical()) {
/* 100 */           this.unsupportedCritExt = true;
/*     */         }
/* 102 */         if (this.map.put(paramExtension.getExtensionId().toString(), paramExtension) == null) {
/*     */           return;
/*     */         }
/* 105 */         throw new IOException("Duplicate extensions not allowed");
/*     */       } 
/*     */       
/* 108 */       Constructor<?> constructor = clazz.getConstructor(PARAMS);
/*     */ 
/*     */       
/* 111 */       Object[] arrayOfObject = { Boolean.valueOf(paramExtension.isCritical()), paramExtension.getExtensionValue() };
/*     */       
/* 113 */       CertAttrSet certAttrSet = (CertAttrSet)constructor.newInstance(arrayOfObject);
/* 114 */       if (this.map.put(certAttrSet.getName(), (Extension)certAttrSet) != null) {
/* 115 */         throw new IOException("Duplicate extensions not allowed");
/*     */       }
/* 117 */     } catch (InvocationTargetException invocationTargetException) {
/* 118 */       Throwable throwable = invocationTargetException.getTargetException();
/* 119 */       if (!paramExtension.isCritical()) {
/*     */         
/* 121 */         if (this.unparseableExtensions == null) {
/* 122 */           this.unparseableExtensions = new TreeMap<>();
/*     */         }
/* 124 */         this.unparseableExtensions.put(paramExtension.getExtensionId().toString(), new UnparseableExtension(paramExtension, throwable));
/*     */         
/* 126 */         if (debug != null) {
/* 127 */           debug.println("Debug info only. Error parsing extension: " + paramExtension);
/*     */           
/* 129 */           throwable.printStackTrace();
/* 130 */           HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/* 131 */           System.err.println(hexDumpEncoder.encodeBuffer(paramExtension.getExtensionValue()));
/*     */         } 
/*     */         return;
/*     */       } 
/* 135 */       if (throwable instanceof IOException) {
/* 136 */         throw (IOException)throwable;
/*     */       }
/* 138 */       throw new IOException(throwable);
/*     */     }
/* 140 */     catch (IOException iOException) {
/* 141 */       throw iOException;
/* 142 */     } catch (Exception exception) {
/* 143 */       throw new IOException(exception);
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
/*     */   public void encode(OutputStream paramOutputStream) throws CertificateException, IOException {
/* 157 */     encode(paramOutputStream, false);
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
/*     */   public void encode(OutputStream paramOutputStream, boolean paramBoolean) throws CertificateException, IOException {
/* 170 */     DerOutputStream derOutputStream3, derOutputStream1 = new DerOutputStream();
/* 171 */     Collection<Extension> collection = this.map.values();
/* 172 */     Object[] arrayOfObject = collection.toArray();
/*     */     
/* 174 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/* 175 */       if (arrayOfObject[b] instanceof CertAttrSet) {
/* 176 */         ((CertAttrSet)arrayOfObject[b]).encode(derOutputStream1);
/* 177 */       } else if (arrayOfObject[b] instanceof Extension) {
/* 178 */         ((Extension)arrayOfObject[b]).encode(derOutputStream1);
/*     */       } else {
/* 180 */         throw new CertificateException("Illegal extension object");
/*     */       } 
/*     */     } 
/* 183 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 184 */     derOutputStream2.write((byte)48, derOutputStream1);
/*     */ 
/*     */     
/* 187 */     if (!paramBoolean) {
/* 188 */       derOutputStream3 = new DerOutputStream();
/* 189 */       derOutputStream3.write(DerValue.createTag(-128, true, (byte)3), derOutputStream2);
/*     */     } else {
/*     */       
/* 192 */       derOutputStream3 = derOutputStream2;
/*     */     } 
/* 194 */     paramOutputStream.write(derOutputStream3.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 204 */     if (paramObject instanceof Extension) {
/* 205 */       this.map.put(paramString, (Extension)paramObject);
/*     */     } else {
/* 207 */       throw new IOException("Unknown extension type.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Extension get(String paramString) throws IOException {
/* 217 */     Extension extension = this.map.get(paramString);
/* 218 */     if (extension == null) {
/* 219 */       throw new IOException("No extension found with name " + paramString);
/*     */     }
/* 221 */     return extension;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Extension getExtension(String paramString) {
/* 227 */     return this.map.get(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 236 */     Object object = this.map.get(paramString);
/* 237 */     if (object == null) {
/* 238 */       throw new IOException("No extension found with name " + paramString);
/*     */     }
/* 240 */     this.map.remove(paramString);
/*     */   }
/*     */   
/*     */   public String getNameByOid(ObjectIdentifier paramObjectIdentifier) throws IOException {
/* 244 */     for (String str : this.map.keySet()) {
/* 245 */       if (((Extension)this.map.get(str)).getExtensionId().equals(paramObjectIdentifier)) {
/* 246 */         return str;
/*     */       }
/*     */     } 
/* 249 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<Extension> getElements() {
/* 257 */     return Collections.enumeration(this.map.values());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Extension> getAllExtensions() {
/* 265 */     return this.map.values();
/*     */   }
/*     */   
/*     */   public Map<String, Extension> getUnparseableExtensions() {
/* 269 */     if (this.unparseableExtensions == null) {
/* 270 */       return Collections.emptyMap();
/*     */     }
/* 272 */     return this.unparseableExtensions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 280 */     return "extensions";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasUnsupportedCriticalExtension() {
/* 288 */     return this.unsupportedCritExt;
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
/*     */   public boolean equals(Object paramObject) {
/* 303 */     if (this == paramObject)
/* 304 */       return true; 
/* 305 */     if (!(paramObject instanceof CertificateExtensions)) {
/* 306 */       return false;
/*     */     }
/* 308 */     Collection<Extension> collection = ((CertificateExtensions)paramObject).getAllExtensions();
/* 309 */     Object[] arrayOfObject = collection.toArray();
/*     */     
/* 311 */     int i = arrayOfObject.length;
/* 312 */     if (i != this.map.size()) {
/* 313 */       return false;
/*     */     }
/*     */     
/* 316 */     String str = null;
/* 317 */     for (byte b = 0; b < i; b++) {
/* 318 */       if (arrayOfObject[b] instanceof CertAttrSet)
/* 319 */         str = ((CertAttrSet)arrayOfObject[b]).getName(); 
/* 320 */       Extension extension1 = (Extension)arrayOfObject[b];
/* 321 */       if (str == null)
/* 322 */         str = extension1.getExtensionId().toString(); 
/* 323 */       Extension extension2 = this.map.get(str);
/* 324 */       if (extension2 == null)
/* 325 */         return false; 
/* 326 */       if (!extension2.equals(extension1))
/* 327 */         return false; 
/*     */     } 
/* 329 */     return getUnparseableExtensions().equals(((CertificateExtensions)paramObject)
/* 330 */         .getUnparseableExtensions());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 339 */     return this.map.hashCode() + getUnparseableExtensions().hashCode();
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
/* 351 */     return this.map.toString();
/*     */   }
/*     */   
/*     */   public CertificateExtensions() {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/CertificateExtensions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */