/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.JarInputStream;
/*     */ import java.util.jar.Pack200;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PackerImpl
/*     */   extends TLGlobals
/*     */   implements Pack200.Packer
/*     */ {
/*     */   public SortedMap<String, String> properties() {
/*  73 */     return this.props;
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
/*     */   public synchronized void pack(JarFile paramJarFile, OutputStream paramOutputStream) throws IOException {
/*  88 */     assert Utils.currentInstance.get() == null;
/*     */     
/*  90 */     boolean bool = !this.props.getBoolean("com.sun.java.util.jar.pack.default.timezone") ? true : false;
/*     */     try {
/*  92 */       Utils.currentInstance.set(this);
/*  93 */       if (bool) {
/*  94 */         Utils.changeDefaultTimeZoneToUtc();
/*     */       }
/*     */       
/*  97 */       if ("0".equals(this.props.getProperty("pack.effort"))) {
/*  98 */         Utils.copyJarFile(paramJarFile, paramOutputStream);
/*     */       } else {
/* 100 */         (new DoPack()).run(paramJarFile, paramOutputStream);
/*     */       } 
/*     */     } finally {
/* 103 */       Utils.currentInstance.set(null);
/* 104 */       if (bool) {
/* 105 */         Utils.restoreDefaultTimeZone();
/*     */       }
/* 107 */       paramJarFile.close();
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
/*     */   public synchronized void pack(JarInputStream paramJarInputStream, OutputStream paramOutputStream) throws IOException {
/* 126 */     assert Utils.currentInstance.get() == null;
/* 127 */     boolean bool = !this.props.getBoolean("com.sun.java.util.jar.pack.default.timezone") ? true : false;
/*     */     try {
/* 129 */       Utils.currentInstance.set(this);
/* 130 */       if (bool) {
/* 131 */         Utils.changeDefaultTimeZoneToUtc();
/*     */       }
/* 133 */       if ("0".equals(this.props.getProperty("pack.effort"))) {
/* 134 */         Utils.copyJarFile(paramJarInputStream, paramOutputStream);
/*     */       } else {
/* 136 */         (new DoPack()).run(paramJarInputStream, paramOutputStream);
/*     */       } 
/*     */     } finally {
/* 139 */       Utils.currentInstance.set(null);
/* 140 */       if (bool) {
/* 141 */         Utils.restoreDefaultTimeZone();
/*     */       }
/* 143 */       paramJarInputStream.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 152 */     this.props.addListener(paramPropertyChangeListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 160 */     this.props.removeListener(paramPropertyChangeListener);
/*     */   }
/*     */   private class DoPack { final int verbose; final Package pkg; final String unknownAttrCommand; final String classFormatCommand; final Map<Attribute.Layout, Attribute> attrDefs; final Map<Attribute.Layout, String> attrCommands; final boolean keepFileOrder; final boolean keepClassOrder; final boolean keepModtime; final boolean latestModtime; final boolean keepDeflateHint; long totalOutputSize; int segmentCount; long segmentTotalSize;
/*     */     long segmentSize;
/*     */     final long segmentLimit;
/*     */     final List<String> passFiles;
/*     */     private int nread;
/*     */     
/* 168 */     private DoPack() { this.verbose = PackerImpl.this.props.getInteger("com.sun.java.util.jar.pack.verbose");
/*     */ 
/*     */       
/* 171 */       PackerImpl.this.props.setInteger("pack.progress", 0);
/* 172 */       if (this.verbose > 0) Utils.log.info(PackerImpl.this.props.toString());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 177 */       this
/*     */         
/* 179 */         .pkg = new Package(Package.Version.makeVersion(PackerImpl.this.props, "min.class"), Package.Version.makeVersion(PackerImpl.this.props, "max.class"), Package.Version.makeVersion(PackerImpl.this.props, "package"));
/*     */ 
/*     */ 
/*     */       
/* 183 */       String str = PackerImpl.this.props.getProperty("pack.unknown.attribute", "pass");
/* 184 */       if (!"strip".equals(str) && 
/* 185 */         !"pass".equals(str) && 
/* 186 */         !"error".equals(str)) {
/* 187 */         throw new RuntimeException("Bad option: pack.unknown.attribute = " + str);
/*     */       }
/* 189 */       this.unknownAttrCommand = str.intern();
/*     */ 
/*     */ 
/*     */       
/* 193 */       str = PackerImpl.this.props.getProperty("com.sun.java.util.jar.pack.class.format.error", "pass");
/* 194 */       if (!"pass".equals(str) && 
/* 195 */         !"error".equals(str)) {
/* 196 */         throw new RuntimeException("Bad option: com.sun.java.util.jar.pack.class.format.error = " + str);
/*     */       }
/* 198 */       this.classFormatCommand = str.intern();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 204 */       HashMap<Object, Object> hashMap1 = new HashMap<>();
/* 205 */       HashMap<Object, Object> hashMap2 = new HashMap<>();
/* 206 */       String[] arrayOfString = { "pack.class.attribute.", "pack.field.attribute.", "pack.method.attribute.", "pack.code.attribute." };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 212 */       int[] arrayOfInt = { 0, 1, 2, 3 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 218 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/* 219 */         String str1 = arrayOfString[b];
/* 220 */         SortedMap<String, String> sortedMap = PackerImpl.this.props.prefixMap(str1);
/* 221 */         for (String str2 : sortedMap.keySet()) {
/* 222 */           assert str2.startsWith(str1);
/* 223 */           String str3 = str2.substring(str1.length());
/* 224 */           String str4 = PackerImpl.this.props.getProperty(str2);
/* 225 */           Attribute.Layout layout = Attribute.keyForLookup(arrayOfInt[b], str3);
/* 226 */           if ("strip".equals(str4) || "pass"
/* 227 */             .equals(str4) || "error"
/* 228 */             .equals(str4)) {
/* 229 */             hashMap2.put(layout, str4.intern()); continue;
/*     */           } 
/* 231 */           Attribute.define((Map)hashMap1, arrayOfInt[b], str3, str4);
/* 232 */           if (this.verbose > 1) {
/* 233 */             Utils.log.fine("Added layout for " + Constants.ATTR_CONTEXT_NAME[b] + " attribute " + str3 + " = " + str4);
/*     */           }
/* 235 */           assert hashMap1.containsKey(layout);
/*     */         } 
/*     */       } 
/*     */       
/* 239 */       this.attrDefs = hashMap1.isEmpty() ? null : (Map)hashMap1;
/* 240 */       this.attrCommands = hashMap2.isEmpty() ? null : (Map)hashMap2;
/*     */ 
/*     */       
/* 243 */       this
/* 244 */         .keepFileOrder = PackerImpl.this.props.getBoolean("pack.keep.file.order");
/* 245 */       this
/* 246 */         .keepClassOrder = PackerImpl.this.props.getBoolean("com.sun.java.util.jar.pack.keep.class.order");
/*     */       
/* 248 */       this
/* 249 */         .keepModtime = "keep".equals(PackerImpl.this.props.getProperty("pack.modification.time"));
/* 250 */       this
/* 251 */         .latestModtime = "latest".equals(PackerImpl.this.props.getProperty("pack.modification.time"));
/* 252 */       this
/* 253 */         .keepDeflateHint = "keep".equals(PackerImpl.this.props.getProperty("pack.deflate.hint"));
/*     */       
/* 255 */       if (!this.keepModtime && !this.latestModtime) {
/* 256 */         int j = PackerImpl.this.props.getTime("pack.modification.time");
/* 257 */         if (j != 0) {
/* 258 */           this.pkg.default_modtime = j;
/*     */         }
/*     */       } 
/* 261 */       if (!this.keepDeflateHint) {
/* 262 */         boolean bool = PackerImpl.this.props.getBoolean("pack.deflate.hint");
/* 263 */         if (bool) {
/* 264 */           this.pkg.default_options |= 0x20;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 269 */       this.totalOutputSize = 0L;
/* 270 */       this.segmentCount = 0;
/* 271 */       this.segmentTotalSize = 0L;
/* 272 */       this.segmentSize = 0L;
/*     */ 
/*     */ 
/*     */       
/* 276 */       if (PackerImpl.this.props.getProperty("pack.segment.limit", "").equals("")) {
/* 277 */         l = -1L;
/*     */       } else {
/* 279 */         l = PackerImpl.this.props.getLong("pack.segment.limit");
/* 280 */       }  long l = Math.min(2147483647L, l);
/* 281 */       l = Math.max(-1L, l);
/* 282 */       if (l == -1L)
/* 283 */         l = Long.MAX_VALUE; 
/* 284 */       this.segmentLimit = l;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 290 */       this.passFiles = PackerImpl.this.props.getProperties("pack.pass.file.");
/* 291 */       for (ListIterator<String> listIterator = this.passFiles.listIterator(); listIterator.hasNext(); ) {
/* 292 */         String str1 = listIterator.next();
/* 293 */         if (str1 == null) { listIterator.remove(); continue; }
/* 294 */          str1 = Utils.getJarEntryName(str1);
/* 295 */         if (str1.endsWith("/"))
/* 296 */           str1 = str1.substring(0, str1.length() - 1); 
/* 297 */         listIterator.set(str1);
/*     */       } 
/* 299 */       if (this.verbose > 0) Utils.log.info("passFiles = " + this.passFiles);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 304 */       int i = PackerImpl.this.props.getInteger("com.sun.java.util.jar.pack.archive.options");
/* 305 */       if (i != 0) {
/* 306 */         this.pkg.default_options |= i;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 402 */       this.nread = 0; } boolean isClassFile(String param1String) { if (!param1String.endsWith(".class")) return false;  String str = param1String; while (true) { if (this.passFiles.contains(str)) return false;  int i = str.lastIndexOf('/'); if (i < 0) break;  str = str.substring(0, i); }  return true; }
/*     */     boolean isMetaInfFile(String param1String) { return (param1String.startsWith("/META-INF") || param1String.startsWith("META-INF")); }
/* 404 */     private void noteRead(InFile param1InFile) { this.nread++;
/* 405 */       if (this.verbose > 2)
/* 406 */         Utils.log.fine("...read " + param1InFile.name); 
/* 407 */       if (this.verbose > 0 && this.nread % 1000 == 0)
/* 408 */         Utils.log.info("Have read " + this.nread + " files...");  }
/*     */ 
/*     */     
/*     */     private void makeNextPackage() {
/*     */       this.pkg.reset();
/*     */     }
/* 414 */     void run(JarInputStream param1JarInputStream, OutputStream param1OutputStream) throws IOException { if (param1JarInputStream.getManifest() != null) {
/* 415 */         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 416 */         param1JarInputStream.getManifest().write(byteArrayOutputStream);
/* 417 */         ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
/* 418 */         this.pkg.addFile(readFile("META-INF/MANIFEST.MF", byteArrayInputStream));
/*     */       }  JarEntry jarEntry;
/* 420 */       while ((jarEntry = param1JarInputStream.getNextJarEntry()) != null) {
/* 421 */         InFile inFile = new InFile(jarEntry);
/*     */         
/* 423 */         String str = inFile.name;
/* 424 */         Package.File file1 = readFile(str, param1JarInputStream);
/* 425 */         Package.File file2 = null;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 430 */         long l = isMetaInfFile(str) ? 0L : inFile.getInputLength();
/*     */         
/* 432 */         if ((this.segmentSize += l) > this.segmentLimit) {
/* 433 */           this.segmentSize -= l;
/* 434 */           byte b = -1;
/* 435 */           flushPartial(param1OutputStream, b);
/*     */         } 
/* 437 */         if (this.verbose > 1) {
/* 438 */           Utils.log.fine("Reading " + str);
/*     */         }
/*     */         
/* 441 */         assert jarEntry.isDirectory() == str.endsWith("/");
/*     */         
/* 443 */         if (isClassFile(str)) {
/* 444 */           file2 = readClass(str, file1.getInputStream());
/*     */         }
/* 446 */         if (file2 == null) {
/* 447 */           file2 = file1;
/* 448 */           this.pkg.addFile(file2);
/*     */         } 
/* 450 */         inFile.copyTo(file2);
/* 451 */         noteRead(inFile);
/*     */       } 
/* 453 */       flushAll(param1OutputStream); }
/*     */     final class InFile {
/*     */       final String name;
/*     */       final JarFile jf;
/* 457 */       final JarEntry je; final File f; int modtime = 0; int options; InFile(String param2String) { this.name = Utils.getJarEntryName(param2String); this.f = new File(param2String); this.jf = null; this.je = null; int i = getModtime(this.f.lastModified()); if (PackerImpl.DoPack.this.keepModtime && i != 0) { this.modtime = i; } else if (PackerImpl.DoPack.this.latestModtime && i > PackerImpl.DoPack.this.pkg.default_modtime) { PackerImpl.DoPack.this.pkg.default_modtime = i; }  } InFile(JarFile param2JarFile, JarEntry param2JarEntry) { this.name = Utils.getJarEntryName(param2JarEntry.getName()); this.f = null; this.jf = param2JarFile; this.je = param2JarEntry; int i = getModtime(param2JarEntry.getTime()); if (PackerImpl.DoPack.this.keepModtime && i != 0) { this.modtime = i; } else if (PackerImpl.DoPack.this.latestModtime && i > PackerImpl.DoPack.this.pkg.default_modtime) { PackerImpl.DoPack.this.pkg.default_modtime = i; }  if (PackerImpl.DoPack.this.keepDeflateHint && param2JarEntry.getMethod() == 8) this.options |= 0x1;  } InFile(JarEntry param2JarEntry) { this((JarFile)null, param2JarEntry); } long getInputLength() { long l = (this.je != null) ? this.je.getSize() : this.f.length(); assert l >= 0L : this + ".len=" + l; return Math.max(0L, l) + this.name.length() + 5L; } int getModtime(long param2Long) { long l = (param2Long + 500L) / 1000L; if ((int)l == l) return (int)l;  Utils.log.warning("overflow in modtime for " + this.f); return 0; } void copyTo(Package.File param2File) { if (this.modtime != 0) param2File.modtime = this.modtime;  param2File.options |= this.options; } InputStream getInputStream() throws IOException { if (this.jf != null) return this.jf.getInputStream(this.je);  return new FileInputStream(this.f); } public String toString() { return this.name; } } void run(JarFile param1JarFile, OutputStream param1OutputStream) throws IOException { List<InFile> list = scanJar(param1JarFile);
/*     */       
/* 459 */       if (this.verbose > 0) {
/* 460 */         Utils.log.info("Reading " + list.size() + " files...");
/*     */       }
/* 462 */       byte b = 0;
/* 463 */       for (InFile inFile : list) {
/* 464 */         String str = inFile.name;
/*     */ 
/*     */ 
/*     */         
/* 468 */         long l = isMetaInfFile(str) ? 0L : inFile.getInputLength();
/* 469 */         if ((this.segmentSize += l) > this.segmentLimit) {
/* 470 */           this.segmentSize -= l;
/*     */           
/* 472 */           float f1 = (b + 1);
/* 473 */           float f2 = (this.segmentCount + 1);
/* 474 */           float f3 = list.size() - f1;
/* 475 */           float f4 = f3 * f2 / f1;
/* 476 */           if (this.verbose > 1)
/* 477 */             Utils.log.fine("Estimated segments to do: " + f4); 
/* 478 */           flushPartial(param1OutputStream, (int)Math.ceil(f4));
/*     */         } 
/* 480 */         InputStream inputStream = inFile.getInputStream();
/* 481 */         if (this.verbose > 1)
/* 482 */           Utils.log.fine("Reading " + str); 
/* 483 */         Package.File file = null;
/* 484 */         if (isClassFile(str)) {
/* 485 */           file = readClass(str, inputStream);
/* 486 */           if (file == null) {
/* 487 */             inputStream.close();
/* 488 */             inputStream = inFile.getInputStream();
/*     */           } 
/*     */         } 
/* 491 */         if (file == null) {
/* 492 */           file = readFile(str, inputStream);
/* 493 */           this.pkg.addFile(file);
/*     */         } 
/* 495 */         inFile.copyTo(file);
/* 496 */         inputStream.close();
/* 497 */         noteRead(inFile);
/* 498 */         b++;
/*     */       } 
/* 500 */       flushAll(param1OutputStream); }
/*     */ 
/*     */     
/*     */     Package.File readClass(String param1String, InputStream param1InputStream) throws IOException {
/* 504 */       this.pkg.getClass(); Package.Class clazz = new Package.Class(this.pkg, param1String);
/* 505 */       param1InputStream = new BufferedInputStream(param1InputStream);
/* 506 */       ClassReader classReader = new ClassReader(clazz, param1InputStream);
/* 507 */       classReader.setAttrDefs(this.attrDefs);
/* 508 */       classReader.setAttrCommands(this.attrCommands);
/* 509 */       classReader.unknownAttrCommand = this.unknownAttrCommand;
/*     */       try {
/* 511 */         classReader.read();
/* 512 */       } catch (IOException iOException) {
/* 513 */         String str = "Passing class file uncompressed due to";
/* 514 */         if (iOException instanceof Attribute.FormatException) {
/* 515 */           Attribute.FormatException formatException = (Attribute.FormatException)iOException;
/*     */           
/* 517 */           if (formatException.layout.equals("pass")) {
/* 518 */             Utils.log.info(formatException.toString());
/* 519 */             Utils.log.warning(str + " unrecognized attribute: " + param1String);
/*     */             
/* 521 */             return null;
/*     */           } 
/* 523 */         } else if (iOException instanceof ClassReader.ClassFormatException) {
/* 524 */           ClassReader.ClassFormatException classFormatException = (ClassReader.ClassFormatException)iOException;
/* 525 */           if (this.classFormatCommand.equals("pass")) {
/* 526 */             Utils.log.info(classFormatException.toString());
/* 527 */             Utils.log.warning(str + " unknown class format: " + param1String);
/*     */             
/* 529 */             return null;
/*     */           } 
/*     */         } 
/*     */         
/* 533 */         throw iOException;
/*     */       } 
/* 535 */       this.pkg.addClass(clazz);
/* 536 */       return clazz.file;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Package.File readFile(String param1String, InputStream param1InputStream) throws IOException {
/* 542 */       this.pkg.getClass(); Package.File file = new Package.File(this.pkg, param1String);
/* 543 */       file.readFrom(param1InputStream);
/* 544 */       if (file.isDirectory() && file.getFileLength() != 0L)
/* 545 */         throw new IllegalArgumentException("Non-empty directory: " + file.getFileName()); 
/* 546 */       return file;
/*     */     }
/*     */     
/*     */     void flushPartial(OutputStream param1OutputStream, int param1Int) throws IOException {
/* 550 */       if (this.pkg.files.isEmpty() && this.pkg.classes.isEmpty()) {
/*     */         return;
/*     */       }
/* 553 */       flushPackage(param1OutputStream, Math.max(1, param1Int));
/* 554 */       PackerImpl.this.props.setInteger("pack.progress", 25);
/*     */       
/* 556 */       makeNextPackage();
/* 557 */       this.segmentCount++;
/* 558 */       this.segmentTotalSize += this.segmentSize;
/* 559 */       this.segmentSize = 0L;
/*     */     }
/*     */     
/*     */     void flushAll(OutputStream param1OutputStream) throws IOException {
/* 563 */       PackerImpl.this.props.setInteger("pack.progress", 50);
/* 564 */       flushPackage(param1OutputStream, 0);
/* 565 */       param1OutputStream.flush();
/* 566 */       PackerImpl.this.props.setInteger("pack.progress", 100);
/* 567 */       this.segmentCount++;
/* 568 */       this.segmentTotalSize += this.segmentSize;
/* 569 */       this.segmentSize = 0L;
/* 570 */       if (this.verbose > 0 && this.segmentCount > 1) {
/* 571 */         Utils.log.info("Transmitted " + this.segmentTotalSize + " input bytes in " + this.segmentCount + " segments totaling " + this.totalOutputSize + " bytes");
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void flushPackage(OutputStream param1OutputStream, int param1Int) throws IOException {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: getfield pkg : Lcom/sun/java/util/jar/pack/Package;
/*     */       //   4: getfield files : Ljava/util/ArrayList;
/*     */       //   7: invokevirtual size : ()I
/*     */       //   10: istore_3
/*     */       //   11: aload_0
/*     */       //   12: getfield keepFileOrder : Z
/*     */       //   15: ifne -> 53
/*     */       //   18: aload_0
/*     */       //   19: getfield verbose : I
/*     */       //   22: iconst_1
/*     */       //   23: if_icmple -> 34
/*     */       //   26: getstatic com/sun/java/util/jar/pack/Utils.log : Lcom/sun/java/util/jar/pack/Utils$Pack200Logger;
/*     */       //   29: ldc 'Reordering files.'
/*     */       //   31: invokevirtual fine : (Ljava/lang/String;)V
/*     */       //   34: iconst_1
/*     */       //   35: istore #4
/*     */       //   37: aload_0
/*     */       //   38: getfield pkg : Lcom/sun/java/util/jar/pack/Package;
/*     */       //   41: aload_0
/*     */       //   42: getfield keepClassOrder : Z
/*     */       //   45: iload #4
/*     */       //   47: invokevirtual reorderFiles : (ZZ)V
/*     */       //   50: goto -> 165
/*     */       //   53: getstatic com/sun/java/util/jar/pack/PackerImpl$DoPack.$assertionsDisabled : Z
/*     */       //   56: ifne -> 87
/*     */       //   59: aload_0
/*     */       //   60: getfield pkg : Lcom/sun/java/util/jar/pack/Package;
/*     */       //   63: getfield files : Ljava/util/ArrayList;
/*     */       //   66: aload_0
/*     */       //   67: getfield pkg : Lcom/sun/java/util/jar/pack/Package;
/*     */       //   70: invokevirtual getClassStubs : ()Ljava/util/List;
/*     */       //   73: invokevirtual containsAll : (Ljava/util/Collection;)Z
/*     */       //   76: ifne -> 87
/*     */       //   79: new java/lang/AssertionError
/*     */       //   82: dup
/*     */       //   83: invokespecial <init> : ()V
/*     */       //   86: athrow
/*     */       //   87: aload_0
/*     */       //   88: getfield pkg : Lcom/sun/java/util/jar/pack/Package;
/*     */       //   91: getfield files : Ljava/util/ArrayList;
/*     */       //   94: astore #4
/*     */       //   96: getstatic com/sun/java/util/jar/pack/PackerImpl$DoPack.$assertionsDisabled : Z
/*     */       //   99: ifne -> 134
/*     */       //   102: new java/util/ArrayList
/*     */       //   105: dup
/*     */       //   106: aload_0
/*     */       //   107: getfield pkg : Lcom/sun/java/util/jar/pack/Package;
/*     */       //   110: getfield files : Ljava/util/ArrayList;
/*     */       //   113: invokespecial <init> : (Ljava/util/Collection;)V
/*     */       //   116: dup
/*     */       //   117: astore #4
/*     */       //   119: aload_0
/*     */       //   120: getfield pkg : Lcom/sun/java/util/jar/pack/Package;
/*     */       //   123: invokevirtual getClassStubs : ()Ljava/util/List;
/*     */       //   126: invokeinterface retainAll : (Ljava/util/Collection;)Z
/*     */       //   131: ifne -> 134
/*     */       //   134: getstatic com/sun/java/util/jar/pack/PackerImpl$DoPack.$assertionsDisabled : Z
/*     */       //   137: ifne -> 165
/*     */       //   140: aload #4
/*     */       //   142: aload_0
/*     */       //   143: getfield pkg : Lcom/sun/java/util/jar/pack/Package;
/*     */       //   146: invokevirtual getClassStubs : ()Ljava/util/List;
/*     */       //   149: invokeinterface equals : (Ljava/lang/Object;)Z
/*     */       //   154: ifne -> 165
/*     */       //   157: new java/lang/AssertionError
/*     */       //   160: dup
/*     */       //   161: invokespecial <init> : ()V
/*     */       //   164: athrow
/*     */       //   165: aload_0
/*     */       //   166: getfield pkg : Lcom/sun/java/util/jar/pack/Package;
/*     */       //   169: invokevirtual trimStubs : ()V
/*     */       //   172: aload_0
/*     */       //   173: getfield this$0 : Lcom/sun/java/util/jar/pack/PackerImpl;
/*     */       //   176: getfield props : Lcom/sun/java/util/jar/pack/PropMap;
/*     */       //   179: ldc 'com.sun.java.util.jar.pack.strip.debug'
/*     */       //   181: invokevirtual getBoolean : (Ljava/lang/String;)Z
/*     */       //   184: ifeq -> 196
/*     */       //   187: aload_0
/*     */       //   188: getfield pkg : Lcom/sun/java/util/jar/pack/Package;
/*     */       //   191: ldc 'Debug'
/*     */       //   193: invokevirtual stripAttributeKind : (Ljava/lang/String;)V
/*     */       //   196: aload_0
/*     */       //   197: getfield this$0 : Lcom/sun/java/util/jar/pack/PackerImpl;
/*     */       //   200: getfield props : Lcom/sun/java/util/jar/pack/PropMap;
/*     */       //   203: ldc 'com.sun.java.util.jar.pack.strip.compile'
/*     */       //   205: invokevirtual getBoolean : (Ljava/lang/String;)Z
/*     */       //   208: ifeq -> 220
/*     */       //   211: aload_0
/*     */       //   212: getfield pkg : Lcom/sun/java/util/jar/pack/Package;
/*     */       //   215: ldc 'Compile'
/*     */       //   217: invokevirtual stripAttributeKind : (Ljava/lang/String;)V
/*     */       //   220: aload_0
/*     */       //   221: getfield this$0 : Lcom/sun/java/util/jar/pack/PackerImpl;
/*     */       //   224: getfield props : Lcom/sun/java/util/jar/pack/PropMap;
/*     */       //   227: ldc 'com.sun.java.util.jar.pack.strip.constants'
/*     */       //   229: invokevirtual getBoolean : (Ljava/lang/String;)Z
/*     */       //   232: ifeq -> 244
/*     */       //   235: aload_0
/*     */       //   236: getfield pkg : Lcom/sun/java/util/jar/pack/Package;
/*     */       //   239: ldc 'Constant'
/*     */       //   241: invokevirtual stripAttributeKind : (Ljava/lang/String;)V
/*     */       //   244: aload_0
/*     */       //   245: getfield this$0 : Lcom/sun/java/util/jar/pack/PackerImpl;
/*     */       //   248: getfield props : Lcom/sun/java/util/jar/pack/PropMap;
/*     */       //   251: ldc 'com.sun.java.util.jar.pack.strip.exceptions'
/*     */       //   253: invokevirtual getBoolean : (Ljava/lang/String;)Z
/*     */       //   256: ifeq -> 268
/*     */       //   259: aload_0
/*     */       //   260: getfield pkg : Lcom/sun/java/util/jar/pack/Package;
/*     */       //   263: ldc 'Exceptions'
/*     */       //   265: invokevirtual stripAttributeKind : (Ljava/lang/String;)V
/*     */       //   268: aload_0
/*     */       //   269: getfield this$0 : Lcom/sun/java/util/jar/pack/PackerImpl;
/*     */       //   272: getfield props : Lcom/sun/java/util/jar/pack/PropMap;
/*     */       //   275: ldc 'com.sun.java.util.jar.pack.strip.innerclasses'
/*     */       //   277: invokevirtual getBoolean : (Ljava/lang/String;)Z
/*     */       //   280: ifeq -> 292
/*     */       //   283: aload_0
/*     */       //   284: getfield pkg : Lcom/sun/java/util/jar/pack/Package;
/*     */       //   287: ldc 'InnerClasses'
/*     */       //   289: invokevirtual stripAttributeKind : (Ljava/lang/String;)V
/*     */       //   292: new com/sun/java/util/jar/pack/PackageWriter
/*     */       //   295: dup
/*     */       //   296: aload_0
/*     */       //   297: getfield pkg : Lcom/sun/java/util/jar/pack/Package;
/*     */       //   300: aload_1
/*     */       //   301: invokespecial <init> : (Lcom/sun/java/util/jar/pack/Package;Ljava/io/OutputStream;)V
/*     */       //   304: astore #4
/*     */       //   306: aload #4
/*     */       //   308: iload_2
/*     */       //   309: putfield archiveNextCount : I
/*     */       //   312: aload #4
/*     */       //   314: invokevirtual write : ()V
/*     */       //   317: aload_1
/*     */       //   318: invokevirtual flush : ()V
/*     */       //   321: aload_0
/*     */       //   322: getfield verbose : I
/*     */       //   325: ifle -> 408
/*     */       //   328: aload #4
/*     */       //   330: getfield archiveSize0 : J
/*     */       //   333: aload #4
/*     */       //   335: getfield archiveSize1 : J
/*     */       //   338: ladd
/*     */       //   339: lstore #5
/*     */       //   341: aload_0
/*     */       //   342: dup
/*     */       //   343: getfield totalOutputSize : J
/*     */       //   346: lload #5
/*     */       //   348: ladd
/*     */       //   349: putfield totalOutputSize : J
/*     */       //   352: aload_0
/*     */       //   353: getfield segmentSize : J
/*     */       //   356: lstore #7
/*     */       //   358: getstatic com/sun/java/util/jar/pack/Utils.log : Lcom/sun/java/util/jar/pack/Utils$Pack200Logger;
/*     */       //   361: new java/lang/StringBuilder
/*     */       //   364: dup
/*     */       //   365: invokespecial <init> : ()V
/*     */       //   368: ldc 'Transmitted '
/*     */       //   370: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */       //   373: iload_3
/*     */       //   374: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */       //   377: ldc ' files of '
/*     */       //   379: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */       //   382: lload #7
/*     */       //   384: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*     */       //   387: ldc ' input bytes in a segment of '
/*     */       //   389: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */       //   392: lload #5
/*     */       //   394: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*     */       //   397: ldc ' bytes'
/*     */       //   399: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */       //   402: invokevirtual toString : ()Ljava/lang/String;
/*     */       //   405: invokevirtual info : (Ljava/lang/String;)V
/*     */       //   408: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #583	-> 0
/*     */       //   #584	-> 11
/*     */       //   #587	-> 18
/*     */       //   #588	-> 34
/*     */       //   #589	-> 37
/*     */       //   #590	-> 50
/*     */       //   #592	-> 53
/*     */       //   #594	-> 87
/*     */       //   #595	-> 96
/*     */       //   #596	-> 123
/*     */       //   #597	-> 134
/*     */       //   #599	-> 165
/*     */       //   #602	-> 172
/*     */       //   #603	-> 196
/*     */       //   #604	-> 220
/*     */       //   #605	-> 244
/*     */       //   #606	-> 268
/*     */       //   #608	-> 292
/*     */       //   #609	-> 306
/*     */       //   #610	-> 312
/*     */       //   #611	-> 317
/*     */       //   #612	-> 321
/*     */       //   #613	-> 328
/*     */       //   #614	-> 341
/*     */       //   #615	-> 352
/*     */       //   #616	-> 358
/*     */       //   #621	-> 408
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     List<InFile> scanJar(JarFile param1JarFile) throws IOException {
/* 625 */       ArrayList<InFile> arrayList = new ArrayList();
/*     */       try {
/* 627 */         for (JarEntry jarEntry : Collections.<JarEntry>list(param1JarFile.entries())) {
/* 628 */           InFile inFile = new InFile(param1JarFile, jarEntry);
/* 629 */           assert jarEntry.isDirectory() == inFile.name.endsWith("/");
/* 630 */           arrayList.add(inFile);
/*     */         } 
/* 632 */       } catch (IllegalStateException illegalStateException) {
/* 633 */         throw new IOException(illegalStateException.getLocalizedMessage(), illegalStateException);
/*     */       } 
/* 635 */       return arrayList;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/PackerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */