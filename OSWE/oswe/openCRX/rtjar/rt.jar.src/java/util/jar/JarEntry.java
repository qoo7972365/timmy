/*     */ package java.util.jar;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.CodeSigner;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.zip.ZipEntry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JarEntry
/*     */   extends ZipEntry
/*     */ {
/*     */   Attributes attr;
/*     */   Certificate[] certs;
/*     */   CodeSigner[] signers;
/*     */   
/*     */   public JarEntry(String paramString) {
/*  52 */     super(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JarEntry(ZipEntry paramZipEntry) {
/*  62 */     super(paramZipEntry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JarEntry(JarEntry paramJarEntry) {
/*  72 */     this(paramJarEntry);
/*  73 */     this.attr = paramJarEntry.attr;
/*  74 */     this.certs = paramJarEntry.certs;
/*  75 */     this.signers = paramJarEntry.signers;
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
/*     */   public Attributes getAttributes() throws IOException {
/*  87 */     return this.attr;
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
/*     */   public Certificate[] getCertificates() {
/* 108 */     return (this.certs == null) ? null : (Certificate[])this.certs.clone();
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
/*     */   public CodeSigner[] getCodeSigners() {
/* 127 */     return (this.signers == null) ? null : (CodeSigner[])this.signers.clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/jar/JarEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */