/*     */ package sun.tools.jar;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Base64;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
/*     */ import sun.net.www.MessageHeader;
/*     */ import sun.security.pkcs.PKCS7;
/*     */ import sun.security.pkcs.SignerInfo;
/*     */ import sun.security.x509.AlgorithmId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignatureFile
/*     */ {
/*     */   static final boolean debug = false;
/*  69 */   private Vector<MessageHeader> entries = new Vector<>();
/*     */ 
/*     */   
/*  72 */   static final String[] hashes = new String[] { "SHA" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Manifest manifest;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String rawName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PKCS7 signatureBlock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Hashtable<String, MessageDigest> digests;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final void debug(String paramString) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SignatureFile(String paramString, boolean paramBoolean) throws JarException {
/* 118 */     this(paramString);
/*     */     
/* 120 */     if (paramBoolean) {
/* 121 */       MessageHeader messageHeader = new MessageHeader();
/* 122 */       messageHeader.set("Signature-Version", "1.0");
/* 123 */       this.entries.addElement(messageHeader);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureFile(Manifest paramManifest, String paramString) throws JarException {
/* 142 */     this(paramString, true);
/*     */     
/* 144 */     this.manifest = paramManifest;
/* 145 */     Enumeration<MessageHeader> enumeration = paramManifest.entries();
/* 146 */     while (enumeration.hasMoreElements()) {
/* 147 */       MessageHeader messageHeader = enumeration.nextElement();
/* 148 */       String str = messageHeader.findValue("Name");
/* 149 */       if (str != null) {
/* 150 */         add(str);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureFile(Manifest paramManifest, String[] paramArrayOfString, String paramString) throws JarException {
/* 172 */     this(paramString, true);
/* 173 */     this.manifest = paramManifest;
/* 174 */     add(paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureFile(InputStream paramInputStream, String paramString) throws IOException {
/* 185 */     this(paramString);
/* 186 */     while (paramInputStream.available() > 0) {
/* 187 */       MessageHeader messageHeader = new MessageHeader(paramInputStream);
/* 188 */       this.entries.addElement(messageHeader);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureFile(InputStream paramInputStream) throws IOException {
/* 199 */     this(paramInputStream, (String)null);
/*     */   }
/*     */   
/*     */   public SignatureFile(byte[] paramArrayOfbyte) throws IOException {
/* 203 */     this(new ByteArrayInputStream(paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 210 */     return "META-INF/" + this.rawName + ".SF";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBlockName() {
/* 217 */     String str = "DSA";
/* 218 */     if (this.signatureBlock != null) {
/* 219 */       SignerInfo signerInfo = this.signatureBlock.getSignerInfos()[0];
/* 220 */       str = signerInfo.getDigestEncryptionAlgorithmId().getName();
/* 221 */       String str1 = AlgorithmId.getEncAlgFromSigAlg(str);
/* 222 */       if (str1 != null) str = str1; 
/*     */     } 
/* 224 */     return "META-INF/" + this.rawName + "." + str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS7 getBlock() {
/* 231 */     return this.signatureBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlock(PKCS7 paramPKCS7) {
/* 238 */     this.signatureBlock = paramPKCS7;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String[] paramArrayOfString) throws JarException {
/* 245 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 246 */       add(paramArrayOfString[b]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String paramString) throws JarException {
/* 254 */     MessageHeader messageHeader2, messageHeader1 = this.manifest.getEntry(paramString);
/* 255 */     if (messageHeader1 == null) {
/* 256 */       throw new JarException("entry " + paramString + " not in manifest");
/*     */     }
/*     */     
/*     */     try {
/* 260 */       messageHeader2 = computeEntry(messageHeader1);
/* 261 */     } catch (IOException iOException) {
/* 262 */       throw new JarException(iOException.getMessage());
/*     */     } 
/* 264 */     this.entries.addElement(messageHeader2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageHeader getEntry(String paramString) {
/* 272 */     Enumeration<MessageHeader> enumeration = entries();
/* 273 */     while (enumeration.hasMoreElements()) {
/* 274 */       MessageHeader messageHeader = enumeration.nextElement();
/* 275 */       if (paramString.equals(messageHeader.findValue("Name"))) {
/* 276 */         return messageHeader;
/*     */       }
/*     */     } 
/* 279 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageHeader entryAt(int paramInt) {
/* 285 */     return this.entries.elementAt(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<MessageHeader> entries() {
/* 292 */     return this.entries.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MessageHeader computeEntry(MessageHeader paramMessageHeader) throws IOException {
/* 300 */     MessageHeader messageHeader = new MessageHeader();
/*     */     
/* 302 */     String str = paramMessageHeader.findValue("Name");
/* 303 */     if (str == null) {
/* 304 */       return null;
/*     */     }
/* 306 */     messageHeader.set("Name", str);
/*     */     
/*     */     try {
/* 309 */       for (byte b = 0; b < hashes.length; b++) {
/* 310 */         MessageDigest messageDigest = getDigest(hashes[b]);
/* 311 */         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 312 */         PrintStream printStream = new PrintStream(byteArrayOutputStream);
/* 313 */         paramMessageHeader.print(printStream);
/* 314 */         byte[] arrayOfByte1 = byteArrayOutputStream.toByteArray();
/* 315 */         byte[] arrayOfByte2 = messageDigest.digest(arrayOfByte1);
/* 316 */         messageHeader.set(hashes[b] + "-Digest", Base64.getMimeEncoder().encodeToString(arrayOfByte2));
/*     */       } 
/* 318 */       return messageHeader;
/* 319 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 320 */       throw new JarException(noSuchAlgorithmException.getMessage());
/*     */     } 
/*     */   }
/*     */   
/* 324 */   private SignatureFile(String paramString) throws JarException { this.digests = new Hashtable<>(); this.entries = new Vector<>(); if (paramString != null) {
/*     */       if (paramString.length() > 8 || paramString.indexOf('.') != -1)
/*     */         throw new JarException("invalid file name"); 
/*     */       this.rawName = paramString.toUpperCase(Locale.ENGLISH);
/* 328 */     }  } private MessageDigest getDigest(String paramString) throws NoSuchAlgorithmException { MessageDigest messageDigest = this.digests.get(paramString);
/* 329 */     if (messageDigest == null) {
/* 330 */       messageDigest = MessageDigest.getInstance(paramString);
/* 331 */       this.digests.put(paramString, messageDigest);
/*     */     } 
/* 333 */     messageDigest.reset();
/* 334 */     return messageDigest; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stream(OutputStream paramOutputStream) throws IOException {
/* 346 */     MessageHeader messageHeader = this.entries.elementAt(0);
/* 347 */     if (messageHeader.findValue("Signature-Version") == null) {
/* 348 */       throw new JarException("Signature file requires Signature-Version: 1.0 in 1st header");
/*     */     }
/*     */ 
/*     */     
/* 352 */     PrintStream printStream = new PrintStream(paramOutputStream);
/* 353 */     messageHeader.print(printStream);
/*     */     
/* 355 */     for (byte b = 1; b < this.entries.size(); b++) {
/* 356 */       MessageHeader messageHeader1 = this.entries.elementAt(b);
/* 357 */       messageHeader1.print(printStream);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/tools/jar/SignatureFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */