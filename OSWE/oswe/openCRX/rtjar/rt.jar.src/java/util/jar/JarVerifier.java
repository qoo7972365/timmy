/*     */ package java.util.jar;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.security.CodeSigner;
/*     */ import java.security.CodeSource;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SignatureException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.zip.ZipEntry;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.ManifestDigester;
/*     */ import sun.security.util.ManifestEntryVerifier;
/*     */ import sun.security.util.SignatureFileVerifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class JarVerifier
/*     */ {
/*  48 */   static final Debug debug = Debug.getInstance("jar");
/*     */ 
/*     */ 
/*     */   
/*     */   private Hashtable<String, CodeSigner[]> verifiedSigners;
/*     */ 
/*     */ 
/*     */   
/*     */   private Hashtable<String, CodeSigner[]> sigFileSigners;
/*     */ 
/*     */ 
/*     */   
/*     */   private Hashtable<String, byte[]> sigFileData;
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<SignatureFileVerifier> pendingBlocks;
/*     */ 
/*     */   
/*     */   private ArrayList<CodeSigner[]> signerCache;
/*     */ 
/*     */   
/*     */   private boolean parsingBlockOrSF = false;
/*     */ 
/*     */   
/*     */   private boolean parsingMeta = true;
/*     */ 
/*     */   
/*     */   private boolean anyToVerify = true;
/*     */ 
/*     */   
/*     */   private ByteArrayOutputStream baos;
/*     */ 
/*     */   
/*     */   private volatile ManifestDigester manDig;
/*     */ 
/*     */   
/*  85 */   byte[] manifestRawBytes = null;
/*     */ 
/*     */   
/*     */   boolean eagerValidation;
/*     */ 
/*     */   
/*  91 */   private Object csdomain = new Object();
/*     */ 
/*     */   
/*     */   private List<Object> manifestDigests;
/*     */   
/*     */   private Map<URL, Map<CodeSigner[], CodeSource>> urlToCodeSourceMap;
/*     */   
/*     */   private Map<CodeSigner[], CodeSource> signerToCodeSource;
/*     */   
/*     */   private URL lastURL;
/*     */   
/*     */   private Map<CodeSigner[], CodeSource> lastURLMap;
/*     */   
/*     */   private CodeSigner[] emptySigner;
/*     */   
/*     */   private Map<String, CodeSigner[]> signerMap;
/*     */   
/*     */   private Enumeration<String> emptyEnumeration;
/*     */   
/*     */   private List<CodeSigner[]> jarCodeSigners;
/*     */ 
/*     */   
/*     */   public void beginEntry(JarEntry paramJarEntry, ManifestEntryVerifier paramManifestEntryVerifier) throws IOException {
/* 114 */     if (paramJarEntry == null) {
/*     */       return;
/*     */     }
/* 117 */     if (debug != null) {
/* 118 */       debug.println("beginEntry " + paramJarEntry.getName());
/*     */     }
/*     */     
/* 121 */     String str = paramJarEntry.getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     if (this.parsingMeta) {
/* 134 */       String str1 = str.toUpperCase(Locale.ENGLISH);
/* 135 */       if (str1.startsWith("META-INF/") || str1
/* 136 */         .startsWith("/META-INF/")) {
/*     */         
/* 138 */         if (paramJarEntry.isDirectory()) {
/* 139 */           paramManifestEntryVerifier.setEntry(null, paramJarEntry);
/*     */           
/*     */           return;
/*     */         } 
/* 143 */         if (str1.equals("META-INF/MANIFEST.MF") || str1
/* 144 */           .equals("META-INF/INDEX.LIST")) {
/*     */           return;
/*     */         }
/*     */         
/* 148 */         if (SignatureFileVerifier.isBlockOrSF(str1)) {
/*     */           
/* 150 */           this.parsingBlockOrSF = true;
/* 151 */           this.baos.reset();
/* 152 */           paramManifestEntryVerifier.setEntry(null, paramJarEntry);
/*     */ 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 162 */     if (this.parsingMeta) {
/* 163 */       doneWithMeta();
/*     */     }
/*     */     
/* 166 */     if (paramJarEntry.isDirectory()) {
/* 167 */       paramManifestEntryVerifier.setEntry(null, paramJarEntry);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 173 */     if (str.startsWith("./")) {
/* 174 */       str = str.substring(2);
/*     */     }
/*     */ 
/*     */     
/* 178 */     if (str.startsWith("/")) {
/* 179 */       str = str.substring(1);
/*     */     }
/*     */ 
/*     */     
/* 183 */     if (!str.equals("META-INF/MANIFEST.MF") && (
/* 184 */       this.sigFileSigners.get(str) != null || this.verifiedSigners
/* 185 */       .get(str) != null)) {
/* 186 */       paramManifestEntryVerifier.setEntry(str, paramJarEntry);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 192 */     paramManifestEntryVerifier.setEntry(null, paramJarEntry);
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
/*     */   public void update(int paramInt, ManifestEntryVerifier paramManifestEntryVerifier) throws IOException {
/* 204 */     if (paramInt != -1) {
/* 205 */       if (this.parsingBlockOrSF) {
/* 206 */         this.baos.write(paramInt);
/*     */       } else {
/* 208 */         paramManifestEntryVerifier.update((byte)paramInt);
/*     */       } 
/*     */     } else {
/* 211 */       processEntry(paramManifestEntryVerifier);
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
/*     */   public void update(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, ManifestEntryVerifier paramManifestEntryVerifier) throws IOException {
/* 223 */     if (paramInt1 != -1) {
/* 224 */       if (this.parsingBlockOrSF) {
/* 225 */         this.baos.write(paramArrayOfbyte, paramInt2, paramInt1);
/*     */       } else {
/* 227 */         paramManifestEntryVerifier.update(paramArrayOfbyte, paramInt2, paramInt1);
/*     */       } 
/*     */     } else {
/* 230 */       processEntry(paramManifestEntryVerifier);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processEntry(ManifestEntryVerifier paramManifestEntryVerifier) throws IOException {
/* 240 */     if (!this.parsingBlockOrSF) {
/* 241 */       JarEntry jarEntry = paramManifestEntryVerifier.getEntry();
/* 242 */       if (jarEntry != null && jarEntry.signers == null) {
/* 243 */         jarEntry.signers = paramManifestEntryVerifier.verify(this.verifiedSigners, this.sigFileSigners);
/* 244 */         jarEntry.certs = mapSignersToCertArray(jarEntry.signers);
/*     */       } 
/*     */     } else {
/*     */       
/*     */       try {
/* 249 */         this.parsingBlockOrSF = false;
/*     */         
/* 251 */         if (debug != null) {
/* 252 */           debug.println("processEntry: processing block");
/*     */         }
/*     */ 
/*     */         
/* 256 */         String str1 = paramManifestEntryVerifier.getEntry().getName().toUpperCase(Locale.ENGLISH);
/*     */         
/* 258 */         if (str1.endsWith(".SF")) {
/* 259 */           String str = str1.substring(0, str1.length() - 3);
/* 260 */           byte[] arrayOfByte = this.baos.toByteArray();
/*     */           
/* 262 */           this.sigFileData.put(str, arrayOfByte);
/*     */ 
/*     */           
/* 265 */           Iterator<SignatureFileVerifier> iterator = this.pendingBlocks.iterator();
/* 266 */           while (iterator.hasNext()) {
/* 267 */             SignatureFileVerifier signatureFileVerifier1 = iterator.next();
/* 268 */             if (signatureFileVerifier1.needSignatureFile(str)) {
/* 269 */               if (debug != null) {
/* 270 */                 debug.println("processEntry: processing pending block");
/*     */               }
/*     */ 
/*     */               
/* 274 */               signatureFileVerifier1.setSignatureFile(arrayOfByte);
/* 275 */               signatureFileVerifier1.process(this.sigFileSigners, this.manifestDigests);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 283 */         String str2 = str1.substring(0, str1.lastIndexOf("."));
/*     */         
/* 285 */         if (this.signerCache == null) {
/* 286 */           this.signerCache = (ArrayList)new ArrayList<>();
/*     */         }
/* 288 */         if (this.manDig == null) {
/* 289 */           synchronized (this.manifestRawBytes) {
/* 290 */             if (this.manDig == null) {
/* 291 */               this.manDig = new ManifestDigester(this.manifestRawBytes);
/* 292 */               this.manifestRawBytes = null;
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 299 */         SignatureFileVerifier signatureFileVerifier = new SignatureFileVerifier(this.signerCache, this.manDig, str1, this.baos.toByteArray());
/*     */         
/* 301 */         if (signatureFileVerifier.needSignatureFileBytes()) {
/*     */           
/* 303 */           byte[] arrayOfByte = this.sigFileData.get(str2);
/*     */           
/* 305 */           if (arrayOfByte == null) {
/*     */ 
/*     */ 
/*     */             
/* 309 */             if (debug != null) {
/* 310 */               debug.println("adding pending block");
/*     */             }
/* 312 */             this.pendingBlocks.add(signatureFileVerifier);
/*     */             return;
/*     */           } 
/* 315 */           signatureFileVerifier.setSignatureFile(arrayOfByte);
/*     */         } 
/*     */         
/* 318 */         signatureFileVerifier.process(this.sigFileSigners, this.manifestDigests);
/*     */       }
/* 320 */       catch (IOException iOException) {
/*     */         
/* 322 */         if (debug != null) debug.println("processEntry caught: " + iOException);
/*     */       
/* 324 */       } catch (SignatureException signatureException) {
/* 325 */         if (debug != null) debug.println("processEntry caught: " + signatureException);
/*     */       
/* 327 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 328 */         if (debug != null) debug.println("processEntry caught: " + noSuchAlgorithmException);
/*     */       
/* 330 */       } catch (CertificateException certificateException) {
/* 331 */         if (debug != null) debug.println("processEntry caught: " + certificateException);
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Certificate[] getCerts(String paramString) {
/* 345 */     return mapSignersToCertArray(getCodeSigners(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public Certificate[] getCerts(JarFile paramJarFile, JarEntry paramJarEntry) {
/* 350 */     return mapSignersToCertArray(getCodeSigners(paramJarFile, paramJarEntry));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CodeSigner[] getCodeSigners(String paramString) {
/* 360 */     return this.verifiedSigners.get(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public CodeSigner[] getCodeSigners(JarFile paramJarFile, JarEntry paramJarEntry) {
/* 365 */     String str = paramJarEntry.getName();
/* 366 */     if (this.eagerValidation && this.sigFileSigners.get(str) != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */         
/* 372 */         InputStream inputStream = paramJarFile.getInputStream(paramJarEntry);
/* 373 */         byte[] arrayOfByte = new byte[1024];
/* 374 */         int i = arrayOfByte.length;
/* 375 */         while (i != -1) {
/* 376 */           i = inputStream.read(arrayOfByte, 0, arrayOfByte.length);
/*     */         }
/* 378 */         inputStream.close();
/* 379 */       } catch (IOException iOException) {}
/*     */     }
/*     */     
/* 382 */     return getCodeSigners(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Certificate[] mapSignersToCertArray(CodeSigner[] paramArrayOfCodeSigner) {
/* 392 */     if (paramArrayOfCodeSigner != null) {
/* 393 */       ArrayList<Certificate> arrayList = new ArrayList();
/* 394 */       for (byte b = 0; b < paramArrayOfCodeSigner.length; b++) {
/* 395 */         arrayList.addAll(paramArrayOfCodeSigner[b]
/* 396 */             .getSignerCertPath().getCertificates());
/*     */       }
/*     */ 
/*     */       
/* 400 */       return arrayList.<Certificate>toArray(
/* 401 */           new Certificate[arrayList.size()]);
/*     */     } 
/* 403 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean nothingToVerify() {
/* 413 */     return !this.anyToVerify;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void doneWithMeta() {
/* 424 */     this.parsingMeta = false;
/* 425 */     this.anyToVerify = !this.sigFileSigners.isEmpty();
/* 426 */     this.baos = null;
/* 427 */     this.sigFileData = null;
/* 428 */     this.pendingBlocks = null;
/* 429 */     this.signerCache = null;
/* 430 */     this.manDig = null;
/*     */ 
/*     */     
/* 433 */     if (this.sigFileSigners.containsKey("META-INF/MANIFEST.MF")) {
/* 434 */       CodeSigner[] arrayOfCodeSigner = this.sigFileSigners.remove("META-INF/MANIFEST.MF");
/* 435 */       this.verifiedSigners.put("META-INF/MANIFEST.MF", arrayOfCodeSigner);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static class VerifierStream
/*     */     extends InputStream
/*     */   {
/*     */     private InputStream is;
/*     */     
/*     */     private JarVerifier jv;
/*     */     
/*     */     private ManifestEntryVerifier mev;
/*     */     private long numLeft;
/*     */     
/*     */     VerifierStream(Manifest param1Manifest, JarEntry param1JarEntry, InputStream param1InputStream, JarVerifier param1JarVerifier) throws IOException {
/* 451 */       this.is = param1InputStream;
/* 452 */       this.jv = param1JarVerifier;
/* 453 */       this.mev = new ManifestEntryVerifier(param1Manifest);
/* 454 */       this.jv.beginEntry(param1JarEntry, this.mev);
/* 455 */       this.numLeft = param1JarEntry.getSize();
/* 456 */       if (this.numLeft == 0L) {
/* 457 */         this.jv.update(-1, this.mev);
/*     */       }
/*     */     }
/*     */     
/*     */     public int read() throws IOException {
/* 462 */       if (this.numLeft > 0L) {
/* 463 */         int i = this.is.read();
/* 464 */         this.jv.update(i, this.mev);
/* 465 */         this.numLeft--;
/* 466 */         if (this.numLeft == 0L)
/* 467 */           this.jv.update(-1, this.mev); 
/* 468 */         return i;
/*     */       } 
/* 470 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 475 */       if (this.numLeft > 0L && this.numLeft < param1Int2) {
/* 476 */         param1Int2 = (int)this.numLeft;
/*     */       }
/*     */       
/* 479 */       if (this.numLeft > 0L) {
/* 480 */         int i = this.is.read(param1ArrayOfbyte, param1Int1, param1Int2);
/* 481 */         this.jv.update(i, param1ArrayOfbyte, param1Int1, param1Int2, this.mev);
/* 482 */         this.numLeft -= i;
/* 483 */         if (this.numLeft == 0L)
/* 484 */           this.jv.update(-1, param1ArrayOfbyte, param1Int1, param1Int2, this.mev); 
/* 485 */         return i;
/*     */       } 
/* 487 */       return -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 494 */       if (this.is != null)
/* 495 */         this.is.close(); 
/* 496 */       this.is = null;
/* 497 */       this.mev = null;
/* 498 */       this.jv = null;
/*     */     }
/*     */     
/*     */     public int available() throws IOException {
/* 502 */       return this.is.available();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JarVerifier(byte[] paramArrayOfbyte) {
/* 509 */     this.urlToCodeSourceMap = new HashMap<>();
/* 510 */     this.signerToCodeSource = (Map)new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 551 */     this.emptySigner = new CodeSigner[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 783 */     this.emptyEnumeration = new Enumeration<String>()
/*     */       {
/*     */         public boolean hasMoreElements() {
/* 786 */           return false;
/*     */         }
/*     */         
/*     */         public String nextElement() {
/* 790 */           throw new NoSuchElementException();
/*     */         }
/*     */       }; this.manifestRawBytes = paramArrayOfbyte; this.sigFileSigners = (Hashtable)new Hashtable<>(); this.verifiedSigners = (Hashtable)new Hashtable<>(); this.sigFileData = (Hashtable)new Hashtable<>(11); this.pendingBlocks = new ArrayList<>(); this.baos = new ByteArrayOutputStream(); this.manifestDigests = new ArrayList();
/*     */   } private synchronized CodeSource mapSignersToCodeSource(URL paramURL, CodeSigner[] paramArrayOfCodeSigner) { Map<Object, Object> map; if (paramURL == this.lastURL) { Map<CodeSigner[], CodeSource> map1 = this.lastURLMap; } else { map = (Map)this.urlToCodeSourceMap.get(paramURL); if (map == null) { map = new HashMap<>(); this.urlToCodeSourceMap.put(paramURL, map); }  this.lastURLMap = (Map)map; this.lastURL = paramURL; }  CodeSource codeSource = (CodeSource)map.get(paramArrayOfCodeSigner); if (codeSource == null) { codeSource = new VerifierCodeSource(this.csdomain, paramURL, paramArrayOfCodeSigner); this.signerToCodeSource.put(paramArrayOfCodeSigner, codeSource); }  return codeSource; } private CodeSource[] mapSignersToCodeSources(URL paramURL, List<CodeSigner[]> paramList, boolean paramBoolean) { ArrayList<CodeSource> arrayList = new ArrayList(); for (byte b = 0; b < paramList.size(); b++) arrayList.add(mapSignersToCodeSource(paramURL, paramList.get(b)));  if (paramBoolean) arrayList.add(mapSignersToCodeSource(paramURL, null));  return arrayList.<CodeSource>toArray(new CodeSource[arrayList.size()]); } private CodeSigner[] findMatchingSigners(CodeSource paramCodeSource) { if (paramCodeSource instanceof VerifierCodeSource) { VerifierCodeSource verifierCodeSource = (VerifierCodeSource)paramCodeSource; if (verifierCodeSource.isSameDomain(this.csdomain))
/*     */         return ((VerifierCodeSource)paramCodeSource).getPrivateSigners();  }  CodeSource[] arrayOfCodeSource = mapSignersToCodeSources(paramCodeSource.getLocation(), getJarCodeSigners(), true); ArrayList<CodeSource> arrayList = new ArrayList(); int i; for (i = 0; i < arrayOfCodeSource.length; i++)
/*     */       arrayList.add(arrayOfCodeSource[i]);  i = arrayList.indexOf(paramCodeSource); if (i != -1) { CodeSigner[] arrayOfCodeSigner = ((VerifierCodeSource)arrayList.get(i)).getPrivateSigners(); if (arrayOfCodeSigner == null)
/* 796 */         arrayOfCodeSigner = this.emptySigner;  return arrayOfCodeSigner; }  return null; } static boolean isSigningRelated(String paramString) { return SignatureFileVerifier.isSigningRelated(paramString); } private static class VerifierCodeSource extends CodeSource {
/*     */     private static final long serialVersionUID = -9047366145967768825L; URL vlocation; CodeSigner[] vsigners; Certificate[] vcerts; Object csdomain; VerifierCodeSource(Object param1Object, URL param1URL, CodeSigner[] param1ArrayOfCodeSigner) { super(param1URL, param1ArrayOfCodeSigner); this.csdomain = param1Object; this.vlocation = param1URL; this.vsigners = param1ArrayOfCodeSigner; } VerifierCodeSource(Object param1Object, URL param1URL, Certificate[] param1ArrayOfCertificate) { super(param1URL, param1ArrayOfCertificate); this.csdomain = param1Object; this.vlocation = param1URL; this.vcerts = param1ArrayOfCertificate; } public boolean equals(Object param1Object) { if (param1Object == this) return true;  if (param1Object instanceof VerifierCodeSource) { VerifierCodeSource verifierCodeSource = (VerifierCodeSource)param1Object; if (isSameDomain(verifierCodeSource.csdomain)) { if (verifierCodeSource.vsigners != this.vsigners || verifierCodeSource.vcerts != this.vcerts) return false;  if (verifierCodeSource.vlocation != null) return verifierCodeSource.vlocation.equals(this.vlocation);  if (this.vlocation != null) return this.vlocation.equals(verifierCodeSource.vlocation);  return true; }  }  return super.equals(param1Object); } boolean isSameDomain(Object param1Object) { return (this.csdomain == param1Object); } private CodeSigner[] getPrivateSigners() { return this.vsigners; } private Certificate[] getPrivateCertificates() { return this.vcerts; } } private synchronized Map<String, CodeSigner[]> signerMap() { if (this.signerMap == null) { this.signerMap = (Map)new HashMap<>(this.verifiedSigners.size() + this.sigFileSigners.size()); this.signerMap.putAll(this.verifiedSigners); this.signerMap.putAll(this.sigFileSigners); }  return this.signerMap; } public synchronized Enumeration<String> entryNames(JarFile paramJarFile, CodeSource[] paramArrayOfCodeSource) { Map<String, CodeSigner[]> map = signerMap(); final Iterator itor = map.entrySet().iterator(); boolean bool = false; ArrayList<CodeSigner[]> arrayList1 = new ArrayList(paramArrayOfCodeSource.length); for (byte b = 0; b < paramArrayOfCodeSource.length; b++) { CodeSigner[] arrayOfCodeSigner = findMatchingSigners(paramArrayOfCodeSource[b]); if (arrayOfCodeSigner != null) { if (arrayOfCodeSigner.length > 0) { arrayList1.add(arrayOfCodeSigner); } else { bool = true; }  } else { bool = true; }  }  final ArrayList<CodeSigner[]> signersReq = arrayList1; final Enumeration<String> enum2 = bool ? unsignedEntryNames(paramJarFile) : this.emptyEnumeration; return new Enumeration<String>() { String name; public boolean hasMoreElements() { if (this.name != null) return true;  while (itor.hasNext()) { Map.Entry entry = itor.next(); if (signersReq.contains(entry.getValue())) { this.name = (String)entry.getKey(); return true; }  }  if (enum2.hasMoreElements()) { this.name = enum2.nextElement(); return true; }  return false; } public String nextElement() { if (hasMoreElements()) { String str = this.name; this.name = null; return str; }  throw new NoSuchElementException(); } }
/*     */       ; } public Enumeration<JarEntry> entries2(final JarFile jar, Enumeration<? extends ZipEntry> paramEnumeration) { final HashMap<Object, Object> map = new HashMap<>(); hashMap.putAll(signerMap()); final Enumeration<? extends ZipEntry> enum_ = paramEnumeration; return new Enumeration<JarEntry>() {
/*     */         Enumeration<String> signers = null; JarEntry entry; public boolean hasMoreElements() { if (this.entry != null) return true;  while (enum_.hasMoreElements()) { ZipEntry zipEntry = enum_.nextElement(); if (JarVerifier.isSigningRelated(zipEntry.getName())) continue;  this.entry = jar.newEntry(zipEntry); return true; }  if (this.signers == null) this.signers = Collections.enumeration(map.keySet());  if (this.signers.hasMoreElements()) { String str = this.signers.nextElement(); this.entry = jar.newEntry(new ZipEntry(str)); return true; }  return false; } public JarEntry nextElement() { if (hasMoreElements()) { JarEntry jarEntry = this.entry; map.remove(jarEntry.getName()); this.entry = null; return jarEntry; }  throw new NoSuchElementException(); }
/* 800 */       }; } private Enumeration<String> unsignedEntryNames(JarFile paramJarFile) { final Map<String, CodeSigner[]> map = signerMap();
/* 801 */     final Enumeration<JarEntry> entries = paramJarFile.entries();
/* 802 */     return new Enumeration<String>()
/*     */       {
/*     */         String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public boolean hasMoreElements() {
/* 811 */           if (this.name != null) {
/* 812 */             return true;
/*     */           }
/* 814 */           while (entries.hasMoreElements()) {
/*     */             
/* 816 */             ZipEntry zipEntry = entries.nextElement();
/* 817 */             String str = zipEntry.getName();
/* 818 */             if (zipEntry.isDirectory() || JarVerifier.isSigningRelated(str)) {
/*     */               continue;
/*     */             }
/* 821 */             if (map.get(str) == null) {
/* 822 */               this.name = str;
/* 823 */               return true;
/*     */             } 
/*     */           } 
/* 826 */           return false;
/*     */         }
/*     */         
/*     */         public String nextElement() {
/* 830 */           if (hasMoreElements()) {
/* 831 */             String str = this.name;
/* 832 */             this.name = null;
/* 833 */             return str;
/*     */           } 
/* 835 */           throw new NoSuchElementException();
/*     */         }
/*     */       }; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized List<CodeSigner[]> getJarCodeSigners() {
/* 843 */     if (this.jarCodeSigners == null) {
/* 844 */       HashSet<? extends CodeSigner> hashSet = new HashSet();
/* 845 */       hashSet.addAll(signerMap().values());
/* 846 */       this.jarCodeSigners = (List)new ArrayList<>();
/* 847 */       this.jarCodeSigners.addAll(hashSet);
/*     */     } 
/* 849 */     return this.jarCodeSigners;
/*     */   }
/*     */   
/*     */   public synchronized CodeSource[] getCodeSources(JarFile paramJarFile, URL paramURL) {
/* 853 */     boolean bool = unsignedEntryNames(paramJarFile).hasMoreElements();
/*     */     
/* 855 */     return mapSignersToCodeSources(paramURL, getJarCodeSigners(), bool);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CodeSource getCodeSource(URL paramURL, String paramString) {
/* 861 */     CodeSigner[] arrayOfCodeSigner = signerMap().get(paramString);
/* 862 */     return mapSignersToCodeSource(paramURL, arrayOfCodeSigner);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CodeSource getCodeSource(URL paramURL, JarFile paramJarFile, JarEntry paramJarEntry) {
/* 868 */     return mapSignersToCodeSource(paramURL, getCodeSigners(paramJarFile, paramJarEntry));
/*     */   }
/*     */   
/*     */   public void setEagerValidation(boolean paramBoolean) {
/* 872 */     this.eagerValidation = paramBoolean;
/*     */   }
/*     */   
/*     */   public synchronized List<Object> getManifestDigests() {
/* 876 */     return Collections.unmodifiableList(this.manifestDigests);
/*     */   }
/*     */   
/*     */   static CodeSource getUnsignedCS(URL paramURL) {
/* 880 */     return new VerifierCodeSource(null, paramURL, (Certificate[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isTrustedManifestEntry(String paramString) {
/* 889 */     CodeSigner[] arrayOfCodeSigner1 = this.verifiedSigners.get("META-INF/MANIFEST.MF");
/* 890 */     if (arrayOfCodeSigner1 == null) {
/* 891 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 895 */     CodeSigner[] arrayOfCodeSigner2 = this.sigFileSigners.get(paramString);
/* 896 */     if (arrayOfCodeSigner2 == null) {
/* 897 */       arrayOfCodeSigner2 = this.verifiedSigners.get(paramString);
/*     */     }
/*     */     
/* 900 */     return (arrayOfCodeSigner2 != null && arrayOfCodeSigner2.length == arrayOfCodeSigner1.length);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/jar/JarVerifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */