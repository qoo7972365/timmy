/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.CodeSigner;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Base64;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarException;
/*     */ import java.util.jar.Manifest;
/*     */ import sun.security.jca.Providers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ManifestEntryVerifier
/*     */ {
/*  45 */   private static final Debug debug = Debug.getInstance("jar");
/*     */   
/*     */   HashMap<String, MessageDigest> createdDigests;
/*     */   
/*     */   ArrayList<MessageDigest> digests;
/*     */   ArrayList<byte[]> manifestHashes;
/*     */   
/*     */   private static class SunProviderHolder
/*     */   {
/*  54 */     private static final Provider instance = Providers.getSunProvider();
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
/*  66 */   private String name = null;
/*     */   
/*     */   private Manifest man;
/*     */   
/*     */   private boolean skip = true;
/*     */   
/*     */   private JarEntry entry;
/*  73 */   private CodeSigner[] signers = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ManifestEntryVerifier(Manifest paramManifest) {
/*  80 */     this.createdDigests = new HashMap<>(11);
/*  81 */     this.digests = new ArrayList<>();
/*  82 */     this.manifestHashes = (ArrayList)new ArrayList<>();
/*  83 */     this.man = paramManifest;
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
/*     */   public void setEntry(String paramString, JarEntry paramJarEntry) throws IOException {
/*  95 */     this.digests.clear();
/*  96 */     this.manifestHashes.clear();
/*  97 */     this.name = paramString;
/*  98 */     this.entry = paramJarEntry;
/*     */     
/* 100 */     this.skip = true;
/* 101 */     this.signers = null;
/*     */     
/* 103 */     if (this.man == null || paramString == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     this.skip = false;
/*     */     
/* 112 */     Attributes attributes = this.man.getAttributes(paramString);
/* 113 */     if (attributes == null) {
/*     */ 
/*     */ 
/*     */       
/* 117 */       attributes = this.man.getAttributes("./" + paramString);
/* 118 */       if (attributes == null) {
/* 119 */         attributes = this.man.getAttributes("/" + paramString);
/* 120 */         if (attributes == null) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/* 125 */     for (Map.Entry<Object, Object> entry : attributes.entrySet()) {
/* 126 */       String str = entry.getKey().toString();
/*     */       
/* 128 */       if (str.toUpperCase(Locale.ENGLISH).endsWith("-DIGEST")) {
/*     */         
/* 130 */         String str1 = str.substring(0, str.length() - 7);
/*     */         
/* 132 */         MessageDigest messageDigest = this.createdDigests.get(str1);
/*     */         
/* 134 */         if (messageDigest == null) {
/*     */           
/*     */           try {
/*     */             
/* 138 */             messageDigest = MessageDigest.getInstance(str1, SunProviderHolder.instance);
/* 139 */             this.createdDigests.put(str1, messageDigest);
/* 140 */           } catch (NoSuchAlgorithmException noSuchAlgorithmException) {}
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 145 */         if (messageDigest != null) {
/* 146 */           messageDigest.reset();
/* 147 */           this.digests.add(messageDigest);
/* 148 */           this.manifestHashes.add(
/* 149 */               Base64.getMimeDecoder().decode((String)entry.getValue()));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(byte paramByte) {
/* 159 */     if (this.skip)
/*     */       return; 
/* 161 */     for (byte b = 0; b < this.digests.size(); b++) {
/* 162 */       ((MessageDigest)this.digests.get(b)).update(paramByte);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 170 */     if (this.skip)
/*     */       return; 
/* 172 */     for (byte b = 0; b < this.digests.size(); b++) {
/* 173 */       ((MessageDigest)this.digests.get(b)).update(paramArrayOfbyte, paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JarEntry getEntry() {
/* 182 */     return this.entry;
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
/*     */   public CodeSigner[] verify(Hashtable<String, CodeSigner[]> paramHashtable1, Hashtable<String, CodeSigner[]> paramHashtable2) throws JarException {
/* 197 */     if (this.skip) {
/* 198 */       return null;
/*     */     }
/*     */     
/* 201 */     if (this.digests.isEmpty()) {
/* 202 */       throw new SecurityException("digest missing for " + this.name);
/*     */     }
/*     */     
/* 205 */     if (this.signers != null) {
/* 206 */       return this.signers;
/*     */     }
/* 208 */     for (byte b = 0; b < this.digests.size(); b++) {
/*     */       
/* 210 */       MessageDigest messageDigest = this.digests.get(b);
/* 211 */       byte[] arrayOfByte1 = this.manifestHashes.get(b);
/* 212 */       byte[] arrayOfByte2 = messageDigest.digest();
/*     */       
/* 214 */       if (debug != null) {
/* 215 */         debug.println("Manifest Entry: " + this.name + " digest=" + messageDigest
/* 216 */             .getAlgorithm());
/* 217 */         debug.println("  manifest " + toHex(arrayOfByte1));
/* 218 */         debug.println("  computed " + toHex(arrayOfByte2));
/* 219 */         debug.println();
/*     */       } 
/*     */       
/* 222 */       if (!MessageDigest.isEqual(arrayOfByte2, arrayOfByte1)) {
/* 223 */         throw new SecurityException(messageDigest.getAlgorithm() + " digest error for " + this.name);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 228 */     this.signers = paramHashtable2.remove(this.name);
/* 229 */     if (this.signers != null) {
/* 230 */       paramHashtable1.put(this.name, this.signers);
/*     */     }
/* 232 */     return this.signers;
/*     */   }
/*     */ 
/*     */   
/* 236 */   private static final char[] hexc = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String toHex(byte[] paramArrayOfbyte) {
/* 246 */     StringBuffer stringBuffer = new StringBuffer(paramArrayOfbyte.length * 2);
/*     */     
/* 248 */     for (byte b = 0; b < paramArrayOfbyte.length; b++) {
/* 249 */       stringBuffer.append(hexc[paramArrayOfbyte[b] >> 4 & 0xF]);
/* 250 */       stringBuffer.append(hexc[paramArrayOfbyte[b] & 0xF]);
/*     */     } 
/* 252 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/ManifestEntryVerifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */