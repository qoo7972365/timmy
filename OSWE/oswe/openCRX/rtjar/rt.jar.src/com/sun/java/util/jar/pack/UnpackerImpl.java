/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashSet;
/*     */ import java.util.SortedMap;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarInputStream;
/*     */ import java.util.jar.JarOutputStream;
/*     */ import java.util.jar.Pack200;
/*     */ import java.util.zip.CRC32;
/*     */ import java.util.zip.CheckedOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnpackerImpl
/*     */   extends TLGlobals
/*     */   implements Pack200.Unpacker
/*     */ {
/*     */   Object _nunp;
/*     */   
/*     */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/*  64 */     this.props.addListener(paramPropertyChangeListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/*  73 */     this.props.removeListener(paramPropertyChangeListener);
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
/*     */   public SortedMap<String, String> properties() {
/*  85 */     return this.props;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  93 */     return Utils.getVersionString();
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
/*     */   public synchronized void unpack(InputStream paramInputStream, JarOutputStream paramJarOutputStream) throws IOException {
/* 112 */     if (paramInputStream == null) {
/* 113 */       throw new NullPointerException("null input");
/*     */     }
/* 115 */     if (paramJarOutputStream == null) {
/* 116 */       throw new NullPointerException("null output");
/*     */     }
/* 118 */     assert Utils.currentInstance.get() == null;
/* 119 */     boolean bool = !this.props.getBoolean("com.sun.java.util.jar.pack.default.timezone") ? true : false;
/*     */     try {
/* 121 */       Utils.currentInstance.set(this);
/* 122 */       if (bool) {
/* 123 */         Utils.changeDefaultTimeZoneToUtc();
/*     */       }
/* 125 */       int i = this.props.getInteger("com.sun.java.util.jar.pack.verbose");
/* 126 */       BufferedInputStream bufferedInputStream = new BufferedInputStream(paramInputStream);
/* 127 */       if (Utils.isJarMagic(Utils.readMagic(bufferedInputStream))) {
/* 128 */         if (i > 0) {
/* 129 */           Utils.log.info("Copying unpacked JAR file...");
/*     */         }
/* 131 */         Utils.copyJarFile(new JarInputStream(bufferedInputStream), paramJarOutputStream);
/* 132 */       } else if (this.props.getBoolean("com.sun.java.util.jar.pack.disable.native")) {
/* 133 */         (new DoUnpack()).run(bufferedInputStream, paramJarOutputStream);
/* 134 */         bufferedInputStream.close();
/* 135 */         Utils.markJarFile(paramJarOutputStream);
/*     */       } else {
/*     */         try {
/* 138 */           (new NativeUnpack(this)).run(bufferedInputStream, paramJarOutputStream);
/* 139 */         } catch (UnsatisfiedLinkError|NoClassDefFoundError unsatisfiedLinkError) {
/*     */           
/* 141 */           (new DoUnpack()).run(bufferedInputStream, paramJarOutputStream);
/*     */         } 
/* 143 */         bufferedInputStream.close();
/* 144 */         Utils.markJarFile(paramJarOutputStream);
/*     */       } 
/*     */     } finally {
/* 147 */       this._nunp = null;
/* 148 */       Utils.currentInstance.set(null);
/* 149 */       if (bool) {
/* 150 */         Utils.restoreDefaultTimeZone();
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
/*     */   public synchronized void unpack(File paramFile, JarOutputStream paramJarOutputStream) throws IOException {
/* 165 */     if (paramFile == null) {
/* 166 */       throw new NullPointerException("null input");
/*     */     }
/* 168 */     if (paramJarOutputStream == null) {
/* 169 */       throw new NullPointerException("null output");
/*     */     }
/*     */ 
/*     */     
/* 173 */     try (FileInputStream null = new FileInputStream(paramFile)) {
/* 174 */       unpack(fileInputStream, paramJarOutputStream);
/*     */     } 
/* 176 */     if (this.props.getBoolean("com.sun.java.util.jar.pack.unpack.remove.packfile"))
/* 177 */       paramFile.delete(); 
/*     */   }
/*     */   private class DoUnpack { final int verbose; final Package pkg; final boolean keepModtime; final boolean keepDeflateHint;
/*     */     
/*     */     private DoUnpack() {
/* 182 */       this.verbose = UnpackerImpl.this.props.getInteger("com.sun.java.util.jar.pack.verbose");
/*     */ 
/*     */       
/* 185 */       UnpackerImpl.this.props.setInteger("unpack.progress", 0);
/*     */ 
/*     */ 
/*     */       
/* 189 */       this.pkg = new Package();
/*     */       
/* 191 */       this
/* 192 */         .keepModtime = "keep".equals(UnpackerImpl.this.props
/* 193 */           .getProperty("com.sun.java.util.jar.pack.unpack.modification.time", "keep"));
/* 194 */       this
/* 195 */         .keepDeflateHint = "keep".equals(UnpackerImpl.this.props
/* 196 */           .getProperty("unpack.deflate.hint", "keep"));
/*     */ 
/*     */ 
/*     */       
/* 200 */       if (!this.keepModtime) {
/* 201 */         this.modtime = UnpackerImpl.this.props.getTime("com.sun.java.util.jar.pack.unpack.modification.time");
/*     */       } else {
/* 203 */         this.modtime = this.pkg.default_modtime;
/*     */       } 
/*     */       
/* 206 */       this
/* 207 */         .deflateHint = this.keepDeflateHint ? false : UnpackerImpl.this.props.getBoolean("unpack.deflate.hint");
/*     */ 
/*     */ 
/*     */       
/* 211 */       this.crc = new CRC32();
/* 212 */       this.bufOut = new ByteArrayOutputStream();
/* 213 */       this.crcOut = new CheckedOutputStream(this.bufOut, this.crc);
/*     */     } final int modtime; final boolean deflateHint; final CRC32 crc; final ByteArrayOutputStream bufOut; final OutputStream crcOut;
/*     */     public void run(BufferedInputStream param1BufferedInputStream, JarOutputStream param1JarOutputStream) throws IOException {
/* 216 */       if (this.verbose > 0) {
/* 217 */         UnpackerImpl.this.props.list(System.out);
/*     */       }
/* 219 */       for (byte b = 1;; b++) {
/* 220 */         unpackSegment(param1BufferedInputStream, param1JarOutputStream);
/*     */ 
/*     */         
/* 223 */         if (!Utils.isPackMagic(Utils.readMagic(param1BufferedInputStream)))
/* 224 */           break;  if (this.verbose > 0)
/* 225 */           Utils.log.info("Finished segment #" + b); 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void unpackSegment(InputStream param1InputStream, JarOutputStream param1JarOutputStream) throws IOException {
/* 230 */       UnpackerImpl.this.props.setProperty("unpack.progress", "0");
/*     */       
/* 232 */       (new PackageReader(this.pkg, param1InputStream)).read();
/*     */       
/* 234 */       if (UnpackerImpl.this.props.getBoolean("unpack.strip.debug")) this.pkg.stripAttributeKind("Debug"); 
/* 235 */       if (UnpackerImpl.this.props.getBoolean("unpack.strip.compile")) this.pkg.stripAttributeKind("Compile"); 
/* 236 */       UnpackerImpl.this.props.setProperty("unpack.progress", "50");
/* 237 */       this.pkg.ensureAllClassFiles();
/*     */       
/* 239 */       HashSet<Package.Class> hashSet = new HashSet<>(this.pkg.getClasses());
/* 240 */       for (Package.File file : this.pkg.getFiles()) {
/* 241 */         String str = file.nameString;
/* 242 */         JarEntry jarEntry = new JarEntry(Utils.getJarEntryName(str));
/*     */ 
/*     */         
/* 245 */         boolean bool1 = this.keepDeflateHint ? (((file.options & 0x1) != 0 || (this.pkg.default_options & 0x20) != 0) ? true : false) : this.deflateHint;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 250 */         boolean bool2 = !bool1 ? true : false;
/*     */         
/* 252 */         if (bool2) this.crc.reset(); 
/* 253 */         this.bufOut.reset();
/* 254 */         if (file.isClassStub()) {
/* 255 */           Package.Class clazz = file.getStubClass();
/* 256 */           assert clazz != null;
/* 257 */           (new ClassWriter(clazz, bool2 ? this.crcOut : this.bufOut)).write();
/* 258 */           hashSet.remove(clazz);
/*     */         } else {
/*     */           
/* 261 */           file.writeTo(bool2 ? this.crcOut : this.bufOut);
/*     */         } 
/* 263 */         jarEntry.setMethod(bool1 ? 8 : 0);
/* 264 */         if (bool2) {
/* 265 */           if (this.verbose > 0) {
/* 266 */             Utils.log.info("stored size=" + this.bufOut.size() + " and crc=" + this.crc.getValue());
/*     */           }
/* 268 */           jarEntry.setMethod(0);
/* 269 */           jarEntry.setSize(this.bufOut.size());
/* 270 */           jarEntry.setCrc(this.crc.getValue());
/*     */         } 
/* 272 */         if (this.keepModtime) {
/* 273 */           jarEntry.setTime(file.modtime);
/*     */           
/* 275 */           jarEntry.setTime(file.modtime * 1000L);
/*     */         } else {
/* 277 */           jarEntry.setTime(this.modtime * 1000L);
/*     */         } 
/* 279 */         param1JarOutputStream.putNextEntry(jarEntry);
/* 280 */         this.bufOut.writeTo(param1JarOutputStream);
/* 281 */         param1JarOutputStream.closeEntry();
/* 282 */         if (this.verbose > 0)
/* 283 */           Utils.log.info("Writing " + Utils.zeString(jarEntry)); 
/*     */       } 
/* 285 */       assert hashSet.isEmpty();
/* 286 */       UnpackerImpl.this.props.setProperty("unpack.progress", "100");
/* 287 */       this.pkg.reset();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/UnpackerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */