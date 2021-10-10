/*      */ package sun.tools.jar;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileDescriptor;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.nio.file.CopyOption;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.StandardCopyOption;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.jar.Attributes;
/*      */ import java.util.jar.JarFile;
/*      */ import java.util.jar.JarOutputStream;
/*      */ import java.util.jar.Manifest;
/*      */ import java.util.jar.Pack200;
/*      */ import java.util.zip.CRC32;
/*      */ import java.util.zip.ZipEntry;
/*      */ import java.util.zip.ZipFile;
/*      */ import java.util.zip.ZipInputStream;
/*      */ import java.util.zip.ZipOutputStream;
/*      */ import sun.misc.JarIndex;
/*      */ 
/*      */ public class Main {
/*      */   String program;
/*      */   PrintStream out;
/*      */   PrintStream err;
/*      */   String fname;
/*      */   String mname;
/*      */   String ename;
/*   52 */   String zname = "";
/*      */   String[] files;
/*   54 */   String rootjar = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   59 */   Map<String, File> entryMap = new HashMap<>();
/*      */ 
/*      */   
/*   62 */   Set<File> entries = new LinkedHashSet<>();
/*      */ 
/*      */   
/*   65 */   Set<String> paths = new HashSet<>();
/*      */   
/*      */   boolean cflag;
/*      */   
/*      */   boolean uflag;
/*      */   
/*      */   boolean xflag;
/*      */   
/*      */   boolean tflag;
/*      */   
/*      */   boolean vflag;
/*      */   
/*      */   boolean flag0;
/*      */   
/*      */   boolean Mflag;
/*      */   
/*      */   boolean iflag;
/*      */   
/*      */   boolean nflag;
/*      */   
/*      */   boolean pflag;
/*      */   
/*      */   static final String MANIFEST_DIR = "META-INF/";
/*      */   
/*      */   static final String VERSION = "1.0";
/*      */   
/*      */   private static ResourceBundle rsrc;
/*      */   
/*   93 */   private static final boolean useExtractionTime = Boolean.getBoolean("sun.tools.jar.useExtractionTime");
/*      */   private boolean ok;
/*      */   private byte[] copyBuf;
/*      */   private HashSet<String> jarPaths;
/*      */   
/*      */   static {
/*      */     try {
/*  100 */       rsrc = ResourceBundle.getBundle("sun.tools.jar.resources.jar");
/*  101 */     } catch (MissingResourceException missingResourceException) {
/*  102 */       throw new Error("Fatal: Resource for jar is missing");
/*      */     } 
/*      */   }
/*      */   
/*      */   private String getMsg(String paramString) {
/*      */     try {
/*  108 */       return rsrc.getString(paramString);
/*  109 */     } catch (MissingResourceException missingResourceException) {
/*  110 */       throw new Error("Error in message file");
/*      */     } 
/*      */   }
/*      */   
/*      */   private String formatMsg(String paramString1, String paramString2) {
/*  115 */     String str = getMsg(paramString1);
/*  116 */     String[] arrayOfString = new String[1];
/*  117 */     arrayOfString[0] = paramString2;
/*  118 */     return MessageFormat.format(str, (Object[])arrayOfString);
/*      */   }
/*      */   
/*      */   private String formatMsg2(String paramString1, String paramString2, String paramString3) {
/*  122 */     String str = getMsg(paramString1);
/*  123 */     String[] arrayOfString = new String[2];
/*  124 */     arrayOfString[0] = paramString2;
/*  125 */     arrayOfString[1] = paramString3;
/*  126 */     return MessageFormat.format(str, (Object[])arrayOfString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static File createTempFileInSameDirectoryAs(File paramFile) throws IOException {
/*  141 */     File file = paramFile.getParentFile();
/*  142 */     if (file == null)
/*  143 */       file = new File("."); 
/*  144 */     return File.createTempFile("jartmp", null, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean run(String[] paramArrayOfString) {
/*  153 */     this.ok = true;
/*  154 */     if (!parseArgs(paramArrayOfString)) {
/*  155 */       return false;
/*      */     }
/*      */     try {
/*  158 */       if ((this.cflag || this.uflag) && 
/*  159 */         this.fname != null) {
/*      */ 
/*      */ 
/*      */         
/*  163 */         this.zname = this.fname.replace(File.separatorChar, '/');
/*  164 */         if (this.zname.startsWith("./")) {
/*  165 */           this.zname = this.zname.substring(2);
/*      */         }
/*      */       } 
/*      */       
/*  169 */       if (this.cflag) {
/*  170 */         FileOutputStream fileOutputStream1; Manifest manifest = null;
/*  171 */         FileInputStream fileInputStream = null;
/*      */         
/*  173 */         if (!this.Mflag) {
/*  174 */           if (this.mname != null) {
/*  175 */             fileInputStream = new FileInputStream(this.mname);
/*  176 */             manifest = new Manifest(new BufferedInputStream(fileInputStream));
/*      */           } else {
/*  178 */             manifest = new Manifest();
/*      */           } 
/*  180 */           addVersion(manifest);
/*  181 */           addCreatedBy(manifest);
/*  182 */           if (isAmbiguousMainClass(manifest)) {
/*  183 */             if (fileInputStream != null) {
/*  184 */               fileInputStream.close();
/*      */             }
/*  186 */             return false;
/*      */           } 
/*  188 */           if (this.ename != null) {
/*  189 */             addMainClass(manifest, this.ename);
/*      */           }
/*      */         } 
/*  192 */         expand(null, this.files, false);
/*      */         
/*  194 */         if (this.fname != null) {
/*  195 */           fileOutputStream1 = new FileOutputStream(this.fname);
/*      */         } else {
/*  197 */           fileOutputStream1 = new FileOutputStream(FileDescriptor.out);
/*  198 */           if (this.vflag)
/*      */           {
/*      */ 
/*      */             
/*  202 */             this.vflag = false;
/*      */           }
/*      */         } 
/*  205 */         File file = null;
/*  206 */         FileOutputStream fileOutputStream2 = fileOutputStream1;
/*      */ 
/*      */         
/*  209 */         String str = (this.fname == null) ? "tmpjar" : this.fname.substring(this.fname.indexOf(File.separatorChar) + 1);
/*  210 */         if (this.nflag) {
/*  211 */           file = createTemporaryFile(str, ".jar");
/*  212 */           fileOutputStream1 = new FileOutputStream(file);
/*      */         } 
/*  214 */         create(new BufferedOutputStream(fileOutputStream1, 4096), manifest);
/*  215 */         if (fileInputStream != null) {
/*  216 */           fileInputStream.close();
/*      */         }
/*  218 */         fileOutputStream1.close();
/*  219 */         if (this.nflag) {
/*  220 */           JarFile jarFile = null;
/*  221 */           File file1 = null;
/*  222 */           JarOutputStream jarOutputStream = null;
/*      */           try {
/*  224 */             Pack200.Packer packer = Pack200.newPacker();
/*  225 */             SortedMap<String, String> sortedMap = packer.properties();
/*  226 */             sortedMap.put("pack.effort", "1");
/*  227 */             jarFile = new JarFile(file.getCanonicalPath());
/*  228 */             file1 = createTemporaryFile(str, ".pack");
/*  229 */             fileOutputStream1 = new FileOutputStream(file1);
/*  230 */             packer.pack(jarFile, fileOutputStream1);
/*  231 */             jarOutputStream = new JarOutputStream(fileOutputStream2);
/*  232 */             Pack200.Unpacker unpacker = Pack200.newUnpacker();
/*  233 */             unpacker.unpack(file1, jarOutputStream);
/*  234 */           } catch (IOException iOException) {
/*  235 */             fatalError(iOException);
/*      */           } finally {
/*  237 */             if (jarFile != null) {
/*  238 */               jarFile.close();
/*      */             }
/*  240 */             if (fileOutputStream1 != null) {
/*  241 */               fileOutputStream1.close();
/*      */             }
/*  243 */             if (jarOutputStream != null) {
/*  244 */               jarOutputStream.close();
/*      */             }
/*  246 */             if (file != null && file.exists()) {
/*  247 */               file.delete();
/*      */             }
/*  249 */             if (file1 != null && file1.exists()) {
/*  250 */               file1.delete();
/*      */             }
/*      */           } 
/*      */         } 
/*  254 */       } else if (this.uflag) {
/*  255 */         FileInputStream fileInputStream1; FileOutputStream fileOutputStream; File file1 = null, file2 = null;
/*      */ 
/*      */         
/*  258 */         if (this.fname != null) {
/*  259 */           file1 = new File(this.fname);
/*  260 */           file2 = createTempFileInSameDirectoryAs(file1);
/*  261 */           fileInputStream1 = new FileInputStream(file1);
/*  262 */           fileOutputStream = new FileOutputStream(file2);
/*      */         } else {
/*  264 */           fileInputStream1 = new FileInputStream(FileDescriptor.in);
/*  265 */           fileOutputStream = new FileOutputStream(FileDescriptor.out);
/*  266 */           this.vflag = false;
/*      */         } 
/*  268 */         FileInputStream fileInputStream2 = (!this.Mflag && this.mname != null) ? new FileInputStream(this.mname) : null;
/*      */         
/*  270 */         expand(null, this.files, true);
/*  271 */         boolean bool = update(fileInputStream1, new BufferedOutputStream(fileOutputStream), fileInputStream2, null);
/*      */         
/*  273 */         if (this.ok) {
/*  274 */           this.ok = bool;
/*      */         }
/*  276 */         fileInputStream1.close();
/*  277 */         fileOutputStream.close();
/*  278 */         if (fileInputStream2 != null) {
/*  279 */           fileInputStream2.close();
/*      */         }
/*  281 */         if (this.ok && this.fname != null) {
/*      */           
/*  283 */           file1.delete();
/*  284 */           if (!file2.renameTo(file1)) {
/*  285 */             file2.delete();
/*  286 */             throw new IOException(getMsg("error.write.file"));
/*      */           } 
/*  288 */           file2.delete();
/*      */         } 
/*  290 */       } else if (this.tflag) {
/*  291 */         replaceFSC(this.files);
/*  292 */         if (this.fname != null) {
/*  293 */           list(this.fname, this.files);
/*      */         } else {
/*  295 */           FileInputStream fileInputStream = new FileInputStream(FileDescriptor.in);
/*      */           try {
/*  297 */             list(new BufferedInputStream(fileInputStream), this.files);
/*      */           } finally {
/*  299 */             fileInputStream.close();
/*      */           } 
/*      */         } 
/*  302 */       } else if (this.xflag) {
/*  303 */         replaceFSC(this.files);
/*  304 */         if (this.fname != null && this.files != null) {
/*  305 */           extract(this.fname, this.files);
/*      */         } else {
/*  307 */           FileInputStream fileInputStream = (this.fname == null) ? new FileInputStream(FileDescriptor.in) : new FileInputStream(this.fname);
/*      */ 
/*      */           
/*      */           try {
/*  311 */             extract(new BufferedInputStream(fileInputStream), this.files);
/*      */           } finally {
/*  313 */             fileInputStream.close();
/*      */           } 
/*      */         } 
/*  316 */       } else if (this.iflag) {
/*  317 */         genIndex(this.rootjar, this.files);
/*      */       } 
/*  319 */     } catch (IOException iOException) {
/*  320 */       fatalError(iOException);
/*  321 */       this.ok = false;
/*  322 */     } catch (Error error) {
/*  323 */       error.printStackTrace();
/*  324 */       this.ok = false;
/*  325 */     } catch (Throwable throwable) {
/*  326 */       throwable.printStackTrace();
/*  327 */       this.ok = false;
/*      */     } 
/*  329 */     this.out.flush();
/*  330 */     this.err.flush();
/*  331 */     return this.ok;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean parseArgs(String[] paramArrayOfString) {
/*      */     try {
/*  340 */       paramArrayOfString = CommandLine.parse(paramArrayOfString);
/*  341 */     } catch (FileNotFoundException fileNotFoundException) {
/*  342 */       fatalError(formatMsg("error.cant.open", fileNotFoundException.getMessage()));
/*  343 */       return false;
/*  344 */     } catch (IOException iOException) {
/*  345 */       fatalError(iOException);
/*  346 */       return false;
/*      */     } 
/*      */     
/*  349 */     byte b = 1;
/*      */     try {
/*  351 */       String str = paramArrayOfString[0];
/*  352 */       if (str.startsWith("-")) {
/*  353 */         str = str.substring(1);
/*      */       }
/*  355 */       for (byte b1 = 0; b1 < str.length(); b1++) {
/*  356 */         switch (str.charAt(b1)) {
/*      */           case 'c':
/*  358 */             if (this.xflag || this.tflag || this.uflag || this.iflag) {
/*  359 */               usageError();
/*  360 */               return false;
/*      */             } 
/*  362 */             this.cflag = true;
/*      */             break;
/*      */           case 'u':
/*  365 */             if (this.cflag || this.xflag || this.tflag || this.iflag) {
/*  366 */               usageError();
/*  367 */               return false;
/*      */             } 
/*  369 */             this.uflag = true;
/*      */             break;
/*      */           case 'x':
/*  372 */             if (this.cflag || this.uflag || this.tflag || this.iflag) {
/*  373 */               usageError();
/*  374 */               return false;
/*      */             } 
/*  376 */             this.xflag = true;
/*      */             break;
/*      */           case 't':
/*  379 */             if (this.cflag || this.uflag || this.xflag || this.iflag) {
/*  380 */               usageError();
/*  381 */               return false;
/*      */             } 
/*  383 */             this.tflag = true;
/*      */             break;
/*      */           case 'M':
/*  386 */             this.Mflag = true;
/*      */             break;
/*      */           case 'v':
/*  389 */             this.vflag = true;
/*      */             break;
/*      */           case 'f':
/*  392 */             this.fname = paramArrayOfString[b++];
/*      */             break;
/*      */           case 'm':
/*  395 */             this.mname = paramArrayOfString[b++];
/*      */             break;
/*      */           case '0':
/*  398 */             this.flag0 = true;
/*      */             break;
/*      */           case 'i':
/*  401 */             if (this.cflag || this.uflag || this.xflag || this.tflag) {
/*  402 */               usageError();
/*  403 */               return false;
/*      */             } 
/*      */             
/*  406 */             this.rootjar = paramArrayOfString[b++];
/*  407 */             this.iflag = true;
/*      */             break;
/*      */           case 'n':
/*  410 */             this.nflag = true;
/*      */             break;
/*      */           case 'e':
/*  413 */             this.ename = paramArrayOfString[b++];
/*      */             break;
/*      */           case 'P':
/*  416 */             this.pflag = true;
/*      */             break;
/*      */           default:
/*  419 */             error(formatMsg("error.illegal.option", 
/*  420 */                   String.valueOf(str.charAt(b1))));
/*  421 */             usageError();
/*  422 */             return false;
/*      */         } 
/*      */       } 
/*  425 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/*  426 */       usageError();
/*  427 */       return false;
/*      */     } 
/*  429 */     if (!this.cflag && !this.tflag && !this.xflag && !this.uflag && !this.iflag) {
/*  430 */       error(getMsg("error.bad.option"));
/*  431 */       usageError();
/*  432 */       return false;
/*      */     } 
/*      */     
/*  435 */     int i = paramArrayOfString.length - b;
/*  436 */     if (i > 0)
/*  437 */     { byte b1 = 0;
/*  438 */       String[] arrayOfString = new String[i];
/*      */       try {
/*  440 */         for (byte b2 = b; b2 < paramArrayOfString.length; b2++) {
/*  441 */           if (paramArrayOfString[b2].equals("-C")) {
/*      */             
/*  443 */             String str = paramArrayOfString[++b2];
/*  444 */             str = str.endsWith(File.separator) ? str : (str + File.separator);
/*      */             
/*  446 */             str = str.replace(File.separatorChar, '/');
/*  447 */             while (str.indexOf("//") > -1) {
/*  448 */               str = str.replace("//", "/");
/*      */             }
/*  450 */             this.paths.add(str.replace(File.separatorChar, '/'));
/*  451 */             arrayOfString[b1++] = str + paramArrayOfString[++b2];
/*      */           } else {
/*  453 */             arrayOfString[b1++] = paramArrayOfString[b2];
/*      */           } 
/*      */         } 
/*  456 */       } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/*  457 */         usageError();
/*  458 */         return false;
/*      */       } 
/*  460 */       this.files = new String[b1];
/*  461 */       System.arraycopy(arrayOfString, 0, this.files, 0, b1); }
/*  462 */     else { if (this.cflag && this.mname == null) {
/*  463 */         error(getMsg("error.bad.cflag"));
/*  464 */         usageError();
/*  465 */         return false;
/*  466 */       }  if (this.uflag) {
/*  467 */         if (this.mname != null || this.ename != null)
/*      */         {
/*  469 */           return true;
/*      */         }
/*  471 */         error(getMsg("error.bad.uflag"));
/*  472 */         usageError();
/*  473 */         return false;
/*      */       }  }
/*      */     
/*  476 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void expand(File paramFile, String[] paramArrayOfString, boolean paramBoolean) {
/*  484 */     if (paramArrayOfString == null) {
/*      */       return;
/*      */     }
/*  487 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*      */       File file;
/*  489 */       if (paramFile == null) {
/*  490 */         file = new File(paramArrayOfString[b]);
/*      */       } else {
/*  492 */         file = new File(paramFile, paramArrayOfString[b]);
/*      */       } 
/*  494 */       if (file.isFile()) {
/*  495 */         if (this.entries.add(file) && 
/*  496 */           paramBoolean) {
/*  497 */           this.entryMap.put(entryName(file.getPath()), file);
/*      */         }
/*  499 */       } else if (file.isDirectory()) {
/*  500 */         if (this.entries.add(file)) {
/*  501 */           if (paramBoolean) {
/*  502 */             String str = file.getPath();
/*  503 */             str = str.endsWith(File.separator) ? str : (str + File.separator);
/*      */             
/*  505 */             this.entryMap.put(entryName(str), file);
/*      */           } 
/*  507 */           expand(file, file.list(), paramBoolean);
/*      */         } 
/*      */       } else {
/*  510 */         error(formatMsg("error.nosuch.fileordir", String.valueOf(file)));
/*  511 */         this.ok = false;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void create(OutputStream paramOutputStream, Manifest paramManifest) throws IOException {
/*  522 */     JarOutputStream jarOutputStream = new JarOutputStream(paramOutputStream);
/*  523 */     if (this.flag0) {
/*  524 */       jarOutputStream.setMethod(0);
/*      */     }
/*  526 */     if (paramManifest != null) {
/*  527 */       if (this.vflag) {
/*  528 */         output(getMsg("out.added.manifest"));
/*      */       }
/*  530 */       ZipEntry zipEntry = new ZipEntry("META-INF/");
/*  531 */       zipEntry.setTime(System.currentTimeMillis());
/*  532 */       zipEntry.setSize(0L);
/*  533 */       zipEntry.setCrc(0L);
/*  534 */       jarOutputStream.putNextEntry(zipEntry);
/*  535 */       zipEntry = new ZipEntry("META-INF/MANIFEST.MF");
/*  536 */       zipEntry.setTime(System.currentTimeMillis());
/*  537 */       if (this.flag0) {
/*  538 */         crc32Manifest(zipEntry, paramManifest);
/*      */       }
/*  540 */       jarOutputStream.putNextEntry(zipEntry);
/*  541 */       paramManifest.write(jarOutputStream);
/*  542 */       jarOutputStream.closeEntry();
/*      */     } 
/*  544 */     for (File file : this.entries) {
/*  545 */       addFile(jarOutputStream, file);
/*      */     }
/*  547 */     jarOutputStream.close();
/*      */   }
/*      */   
/*      */   private char toUpperCaseASCII(char paramChar) {
/*  551 */     return (paramChar < 'a' || paramChar > 'z') ? paramChar : (char)(paramChar + 65 - 97);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean equalsIgnoreCase(String paramString1, String paramString2) {
/*  561 */     assert paramString2.toUpperCase(Locale.ENGLISH).equals(paramString2);
/*      */     int i;
/*  563 */     if ((i = paramString1.length()) != paramString2.length())
/*  564 */       return false; 
/*  565 */     for (byte b = 0; b < i; b++) {
/*  566 */       char c1 = paramString1.charAt(b);
/*  567 */       char c2 = paramString2.charAt(b);
/*  568 */       if (c1 != c2 && toUpperCaseASCII(c1) != c2)
/*  569 */         return false; 
/*      */     } 
/*  571 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean update(InputStream paramInputStream1, OutputStream paramOutputStream, InputStream paramInputStream2, JarIndex paramJarIndex) throws IOException {
/*  581 */     ZipInputStream zipInputStream = new ZipInputStream(paramInputStream1);
/*  582 */     JarOutputStream jarOutputStream = new JarOutputStream(paramOutputStream);
/*  583 */     ZipEntry zipEntry = null;
/*  584 */     boolean bool1 = false;
/*  585 */     boolean bool2 = true;
/*      */     
/*  587 */     if (paramJarIndex != null) {
/*  588 */       addIndex(paramJarIndex, jarOutputStream);
/*      */     }
/*      */ 
/*      */     
/*  592 */     while ((zipEntry = zipInputStream.getNextEntry()) != null) {
/*  593 */       String str = zipEntry.getName();
/*      */       
/*  595 */       boolean bool = equalsIgnoreCase(str, "META-INF/MANIFEST.MF");
/*      */       
/*  597 */       if ((paramJarIndex != null && equalsIgnoreCase(str, "META-INF/INDEX.LIST")) || (this.Mflag && bool)) {
/*      */         continue;
/*      */       }
/*  600 */       if (bool && (paramInputStream2 != null || this.ename != null)) {
/*      */         
/*  602 */         bool1 = true;
/*  603 */         if (paramInputStream2 != null) {
/*      */ 
/*      */ 
/*      */           
/*  607 */           FileInputStream fileInputStream = new FileInputStream(this.mname);
/*  608 */           boolean bool3 = isAmbiguousMainClass(new Manifest(fileInputStream));
/*  609 */           fileInputStream.close();
/*  610 */           if (bool3) {
/*  611 */             return false;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  616 */         Manifest manifest = new Manifest(zipInputStream);
/*  617 */         if (paramInputStream2 != null) {
/*  618 */           manifest.read(paramInputStream2);
/*      */         }
/*  620 */         if (!updateManifest(manifest, jarOutputStream))
/*  621 */           return false; 
/*      */         continue;
/*      */       } 
/*  624 */       if (!this.entryMap.containsKey(str)) {
/*      */         
/*  626 */         ZipEntry zipEntry1 = new ZipEntry(str);
/*  627 */         zipEntry1.setMethod(zipEntry.getMethod());
/*  628 */         zipEntry1.setTime(zipEntry.getTime());
/*  629 */         zipEntry1.setComment(zipEntry.getComment());
/*  630 */         zipEntry1.setExtra(zipEntry.getExtra());
/*  631 */         if (zipEntry.getMethod() == 0) {
/*  632 */           zipEntry1.setSize(zipEntry.getSize());
/*  633 */           zipEntry1.setCrc(zipEntry.getCrc());
/*      */         } 
/*  635 */         jarOutputStream.putNextEntry(zipEntry1);
/*  636 */         copy(zipInputStream, jarOutputStream); continue;
/*      */       } 
/*  638 */       File file = this.entryMap.get(str);
/*  639 */       addFile(jarOutputStream, file);
/*  640 */       this.entryMap.remove(str);
/*  641 */       this.entries.remove(file);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  647 */     for (File file : this.entries) {
/*  648 */       addFile(jarOutputStream, file);
/*      */     }
/*  650 */     if (!bool1) {
/*  651 */       if (paramInputStream2 != null) {
/*  652 */         Manifest manifest = new Manifest(paramInputStream2);
/*  653 */         bool2 = !isAmbiguousMainClass(manifest) ? true : false;
/*  654 */         if (bool2 && 
/*  655 */           !updateManifest(manifest, jarOutputStream)) {
/*  656 */           bool2 = false;
/*      */         }
/*      */       }
/*  659 */       else if (this.ename != null && 
/*  660 */         !updateManifest(new Manifest(), jarOutputStream)) {
/*  661 */         bool2 = false;
/*      */       } 
/*      */     }
/*      */     
/*  665 */     zipInputStream.close();
/*  666 */     jarOutputStream.close();
/*  667 */     return bool2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addIndex(JarIndex paramJarIndex, ZipOutputStream paramZipOutputStream) throws IOException {
/*  673 */     ZipEntry zipEntry = new ZipEntry("META-INF/INDEX.LIST");
/*  674 */     zipEntry.setTime(System.currentTimeMillis());
/*  675 */     if (this.flag0) {
/*  676 */       CRC32OutputStream cRC32OutputStream = new CRC32OutputStream();
/*  677 */       paramJarIndex.write(cRC32OutputStream);
/*  678 */       cRC32OutputStream.updateEntry(zipEntry);
/*      */     } 
/*  680 */     paramZipOutputStream.putNextEntry(zipEntry);
/*  681 */     paramJarIndex.write(paramZipOutputStream);
/*  682 */     paramZipOutputStream.closeEntry();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean updateManifest(Manifest paramManifest, ZipOutputStream paramZipOutputStream) throws IOException {
/*  688 */     addVersion(paramManifest);
/*  689 */     addCreatedBy(paramManifest);
/*  690 */     if (this.ename != null) {
/*  691 */       addMainClass(paramManifest, this.ename);
/*      */     }
/*  693 */     ZipEntry zipEntry = new ZipEntry("META-INF/MANIFEST.MF");
/*  694 */     zipEntry.setTime(System.currentTimeMillis());
/*  695 */     if (this.flag0) {
/*  696 */       crc32Manifest(zipEntry, paramManifest);
/*      */     }
/*  698 */     paramZipOutputStream.putNextEntry(zipEntry);
/*  699 */     paramManifest.write(paramZipOutputStream);
/*  700 */     if (this.vflag) {
/*  701 */       output(getMsg("out.update.manifest"));
/*      */     }
/*  703 */     return true;
/*      */   }
/*      */   
/*      */   private static final boolean isWinDriveLetter(char paramChar) {
/*  707 */     return ((paramChar >= 'a' && paramChar <= 'z') || (paramChar >= 'A' && paramChar <= 'Z'));
/*      */   }
/*      */   
/*      */   private String safeName(String paramString) {
/*  711 */     if (!this.pflag) {
/*  712 */       int i = paramString.length();
/*  713 */       int j = paramString.lastIndexOf("../");
/*  714 */       if (j == -1) {
/*  715 */         j = 0;
/*      */       } else {
/*  717 */         j += 3;
/*      */       } 
/*  719 */       if (File.separatorChar == '\\') {
/*      */ 
/*      */         
/*  722 */         while (j < i) {
/*  723 */           int k = j;
/*  724 */           if (j + 1 < i && paramString
/*  725 */             .charAt(j + 1) == ':' && 
/*  726 */             isWinDriveLetter(paramString.charAt(j))) {
/*  727 */             j += 2;
/*      */           }
/*  729 */           while (j < i && paramString.charAt(j) == '/') {
/*  730 */             j++;
/*      */           }
/*  732 */           if (j == k) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } else {
/*  737 */         while (j < i && paramString.charAt(j) == '/') {
/*  738 */           j++;
/*      */         }
/*      */       } 
/*  741 */       if (j != 0) {
/*  742 */         paramString = paramString.substring(j);
/*      */       }
/*      */     } 
/*  745 */     return paramString;
/*      */   }
/*      */   
/*      */   private String entryName(String paramString) {
/*  749 */     paramString = paramString.replace(File.separatorChar, '/');
/*  750 */     String str = "";
/*  751 */     for (String str1 : this.paths) {
/*  752 */       if (paramString.startsWith(str1) && str1
/*  753 */         .length() > str.length()) {
/*  754 */         str = str1;
/*      */       }
/*      */     } 
/*  757 */     paramString = paramString.substring(str.length());
/*  758 */     paramString = safeName(paramString);
/*      */ 
/*      */     
/*  761 */     if (paramString.startsWith("./")) {
/*  762 */       paramString = paramString.substring(2);
/*      */     }
/*  764 */     return paramString;
/*      */   }
/*      */   
/*      */   private void addVersion(Manifest paramManifest) {
/*  768 */     Attributes attributes = paramManifest.getMainAttributes();
/*  769 */     if (attributes.getValue(Attributes.Name.MANIFEST_VERSION) == null) {
/*  770 */       attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
/*      */     }
/*      */   }
/*      */   
/*      */   private void addCreatedBy(Manifest paramManifest) {
/*  775 */     Attributes attributes = paramManifest.getMainAttributes();
/*  776 */     if (attributes.getValue(new Attributes.Name("Created-By")) == null) {
/*  777 */       String str1 = System.getProperty("java.vendor");
/*  778 */       String str2 = System.getProperty("java.version");
/*  779 */       attributes.put(new Attributes.Name("Created-By"), str2 + " (" + str1 + ")");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void addMainClass(Manifest paramManifest, String paramString) {
/*  785 */     Attributes attributes = paramManifest.getMainAttributes();
/*      */ 
/*      */     
/*  788 */     attributes.put(Attributes.Name.MAIN_CLASS, paramString);
/*      */   }
/*      */   
/*      */   private boolean isAmbiguousMainClass(Manifest paramManifest) {
/*  792 */     if (this.ename != null) {
/*  793 */       Attributes attributes = paramManifest.getMainAttributes();
/*  794 */       if (attributes.get(Attributes.Name.MAIN_CLASS) != null) {
/*  795 */         error(getMsg("error.bad.eflag"));
/*  796 */         usageError();
/*  797 */         return true;
/*      */       } 
/*      */     } 
/*  800 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addFile(ZipOutputStream paramZipOutputStream, File paramFile) throws IOException {
/*  807 */     String str = paramFile.getPath();
/*  808 */     boolean bool = paramFile.isDirectory();
/*  809 */     if (bool) {
/*  810 */       str = str.endsWith(File.separator) ? str : (str + File.separator);
/*      */     }
/*      */     
/*  813 */     str = entryName(str);
/*      */     
/*  815 */     if (str.equals("") || str.equals(".") || str.equals(this.zname))
/*      */       return; 
/*  817 */     if ((str.equals("META-INF/") || str.equals("META-INF/MANIFEST.MF")) && !this.Mflag) {
/*      */       
/*  819 */       if (this.vflag) {
/*  820 */         output(formatMsg("out.ignore.entry", str));
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*  825 */     long l = bool ? 0L : paramFile.length();
/*      */     
/*  827 */     if (this.vflag) {
/*  828 */       this.out.print(formatMsg("out.adding", str));
/*      */     }
/*  830 */     ZipEntry zipEntry = new ZipEntry(str);
/*  831 */     zipEntry.setTime(paramFile.lastModified());
/*  832 */     if (l == 0L) {
/*  833 */       zipEntry.setMethod(0);
/*  834 */       zipEntry.setSize(0L);
/*  835 */       zipEntry.setCrc(0L);
/*  836 */     } else if (this.flag0) {
/*  837 */       crc32File(zipEntry, paramFile);
/*      */     } 
/*  839 */     paramZipOutputStream.putNextEntry(zipEntry);
/*  840 */     if (!bool) {
/*  841 */       copy(paramFile, paramZipOutputStream);
/*      */     }
/*  843 */     paramZipOutputStream.closeEntry();
/*      */     
/*  845 */     if (this.vflag) {
/*  846 */       l = zipEntry.getSize();
/*  847 */       long l1 = zipEntry.getCompressedSize();
/*  848 */       this.out.print(formatMsg2("out.size", String.valueOf(l), 
/*  849 */             String.valueOf(l1)));
/*  850 */       if (zipEntry.getMethod() == 8) {
/*  851 */         long l2 = 0L;
/*  852 */         if (l != 0L) {
/*  853 */           l2 = (l - l1) * 100L / l;
/*      */         }
/*  855 */         output(formatMsg("out.deflated", String.valueOf(l2)));
/*      */       } else {
/*  857 */         output(getMsg("out.stored"));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Main(PrintStream paramPrintStream1, PrintStream paramPrintStream2, String paramString) {
/*  867 */     this.copyBuf = new byte[8192];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1146 */     this.jarPaths = new HashSet<>(); this.out = paramPrintStream1; this.err = paramPrintStream2; this.program = paramString;
/*      */   } private void copy(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException { int i; while ((i = paramInputStream.read(this.copyBuf)) != -1) paramOutputStream.write(this.copyBuf, 0, i);  } private void copy(File paramFile, OutputStream paramOutputStream) throws IOException { FileInputStream fileInputStream = new FileInputStream(paramFile); try { copy(fileInputStream, paramOutputStream); } finally { fileInputStream.close(); }  } private void copy(InputStream paramInputStream, File paramFile) throws IOException { FileOutputStream fileOutputStream = new FileOutputStream(paramFile); try { copy(paramInputStream, fileOutputStream); } finally { fileOutputStream.close(); }  }
/*      */   private void crc32Manifest(ZipEntry paramZipEntry, Manifest paramManifest) throws IOException { CRC32OutputStream cRC32OutputStream = new CRC32OutputStream(); paramManifest.write(cRC32OutputStream); cRC32OutputStream.updateEntry(paramZipEntry); }
/*      */   private void crc32File(ZipEntry paramZipEntry, File paramFile) throws IOException { CRC32OutputStream cRC32OutputStream = new CRC32OutputStream(); copy(paramFile, cRC32OutputStream); if (cRC32OutputStream.n != paramFile.length()) throw new JarException(formatMsg("error.incorrect.length", paramFile.getPath()));  cRC32OutputStream.updateEntry(paramZipEntry); }
/*      */   void replaceFSC(String[] paramArrayOfString) { if (paramArrayOfString != null) for (byte b = 0; b < paramArrayOfString.length; b++) paramArrayOfString[b] = paramArrayOfString[b].replace(File.separatorChar, '/');   }
/*      */   Set<ZipEntry> newDirSet() { return new HashSet<ZipEntry>() { public boolean add(ZipEntry param1ZipEntry) { return (param1ZipEntry == null || Main.useExtractionTime) ? false : super.add(param1ZipEntry); } }
/*      */       ; }
/* 1153 */   List<String> getJarPath(String paramString) throws IOException { ArrayList<String> arrayList = new ArrayList();
/* 1154 */     arrayList.add(paramString);
/* 1155 */     this.jarPaths.add(paramString);
/*      */ 
/*      */     
/* 1158 */     String str = paramString.substring(0, Math.max(0, paramString.lastIndexOf('/') + 1));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1163 */     JarFile jarFile = new JarFile(paramString.replace('/', File.separatorChar));
/*      */     
/* 1165 */     if (jarFile != null) {
/* 1166 */       Manifest manifest = jarFile.getManifest();
/* 1167 */       if (manifest != null) {
/* 1168 */         Attributes attributes = manifest.getMainAttributes();
/* 1169 */         if (attributes != null) {
/* 1170 */           String str1 = attributes.getValue(Attributes.Name.CLASS_PATH);
/* 1171 */           if (str1 != null) {
/* 1172 */             StringTokenizer stringTokenizer = new StringTokenizer(str1);
/* 1173 */             while (stringTokenizer.hasMoreTokens()) {
/* 1174 */               String str2 = stringTokenizer.nextToken();
/* 1175 */               if (!str2.endsWith("/")) {
/* 1176 */                 str2 = str.concat(str2);
/*      */                 
/* 1178 */                 if (!this.jarPaths.contains(str2)) {
/* 1179 */                   arrayList.addAll(getJarPath(str2));
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1187 */     jarFile.close();
/* 1188 */     return arrayList; } void updateLastModifiedTime(Set<ZipEntry> paramSet) throws IOException { for (ZipEntry zipEntry : paramSet) { long l = zipEntry.getTime(); if (l != -1L) { String str = safeName(zipEntry.getName().replace(File.separatorChar, '/')); if (str.length() != 0) { File file = new File(str.replace('/', File.separatorChar)); file.setLastModified(l); }  }  }  }
/*      */   void extract(InputStream paramInputStream, String[] paramArrayOfString) throws IOException { ZipInputStream zipInputStream = new ZipInputStream(paramInputStream); Set<ZipEntry> set = newDirSet(); ZipEntry zipEntry; while ((zipEntry = zipInputStream.getNextEntry()) != null) { if (paramArrayOfString == null) { set.add(extractFile(zipInputStream, zipEntry)); continue; }  String str = zipEntry.getName(); for (String str1 : paramArrayOfString) { if (str.startsWith(str1)) { set.add(extractFile(zipInputStream, zipEntry)); break; }  }  }  updateLastModifiedTime(set); }
/*      */   void extract(String paramString, String[] paramArrayOfString) throws IOException { ZipFile zipFile = new ZipFile(paramString); Set<ZipEntry> set = newDirSet(); Enumeration<? extends ZipEntry> enumeration = zipFile.entries(); while (enumeration.hasMoreElements()) { ZipEntry zipEntry = enumeration.nextElement(); if (paramArrayOfString == null) { set.add(extractFile(zipFile.getInputStream(zipEntry), zipEntry)); continue; }
/*      */        String str = zipEntry.getName(); for (String str1 : paramArrayOfString) { if (str.startsWith(str1)) { set.add(extractFile(zipFile.getInputStream(zipEntry), zipEntry)); break; }
/*      */          }
/*      */        }
/*      */      zipFile.close(); updateLastModifiedTime(set); }
/* 1195 */   void genIndex(String paramString, String[] paramArrayOfString) throws IOException { List<String> list = getJarPath(paramString);
/* 1196 */     int i = list.size();
/*      */ 
/*      */     
/* 1199 */     if (i == 1 && paramArrayOfString != null) {
/*      */ 
/*      */       
/* 1202 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 1203 */         list.addAll(getJarPath(paramArrayOfString[b]));
/*      */       }
/* 1205 */       i = list.size();
/*      */     } 
/* 1207 */     String[] arrayOfString = list.<String>toArray(new String[i]);
/* 1208 */     JarIndex jarIndex = new JarIndex(arrayOfString);
/* 1209 */     dumpIndex(paramString, jarIndex); }
/*      */   ZipEntry extractFile(InputStream paramInputStream, ZipEntry paramZipEntry) throws IOException { ZipEntry zipEntry = null; String str = safeName(paramZipEntry.getName().replace(File.separatorChar, '/')); if (str.length() == 0) return zipEntry;  File file = new File(str.replace('/', File.separatorChar)); if (paramZipEntry.isDirectory()) { if (file.exists()) { if (!file.isDirectory())
/*      */           throw new IOException(formatMsg("error.create.dir", file.getPath()));  } else { if (!file.mkdirs())
/*      */           throw new IOException(formatMsg("error.create.dir", file.getPath()));  zipEntry = paramZipEntry; }  if (this.vflag)
/*      */         output(formatMsg("out.create", str));  } else { if (file.getParent() != null) { File file1 = new File(file.getParent()); if ((!file1.exists() && !file1.mkdirs()) || !file1.isDirectory())
/*      */           throw new IOException(formatMsg("error.create.dir", file1.getPath()));  }  try { copy(paramInputStream, file); } finally { if (paramInputStream instanceof ZipInputStream) { ((ZipInputStream)paramInputStream).closeEntry(); } else { paramInputStream.close(); }  }  if (this.vflag)
/*      */         if (paramZipEntry.getMethod() == 8) { output(formatMsg("out.inflated", str)); } else { output(formatMsg("out.extracted", str)); }   }  if (!useExtractionTime) { long l = paramZipEntry.getTime(); if (l != -1L)
/* 1216 */         file.setLastModified(l);  }  return zipEntry; } void printEntry(ZipEntry paramZipEntry, String[] paramArrayOfString) throws IOException { if (paramArrayOfString == null) {
/* 1217 */       printEntry(paramZipEntry);
/*      */     } else {
/* 1219 */       String str = paramZipEntry.getName();
/* 1220 */       for (String str1 : paramArrayOfString)
/* 1221 */       { if (str.startsWith(str1))
/* 1222 */         { printEntry(paramZipEntry); return; }  } 
/*      */     }  }
/*      */   void list(InputStream paramInputStream, String[] paramArrayOfString) throws IOException { ZipInputStream zipInputStream = new ZipInputStream(paramInputStream); ZipEntry zipEntry; while ((zipEntry = zipInputStream.getNextEntry()) != null) { zipInputStream.closeEntry(); printEntry(zipEntry, paramArrayOfString); }
/*      */      }
/*      */   void list(String paramString, String[] paramArrayOfString) throws IOException { ZipFile zipFile = new ZipFile(paramString); Enumeration<? extends ZipEntry> enumeration = zipFile.entries(); while (enumeration.hasMoreElements())
/*      */       printEntry(enumeration.nextElement(), paramArrayOfString);  zipFile.close(); }
/*      */   void dumpIndex(String paramString, JarIndex paramJarIndex) throws IOException { File file = new File(paramString); Path path1 = file.toPath(); Path path2 = createTempFileInSameDirectoryAs(file).toPath(); try { if (update(Files.newInputStream(path1, new java.nio.file.OpenOption[0]), Files.newOutputStream(path2, new java.nio.file.OpenOption[0]), null, paramJarIndex))
/*      */         try { Files.move(path2, path1, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING }); }
/*      */         catch (IOException iOException) { throw new IOException(getMsg("error.write.file"), iOException); }
/*      */           }
/*      */     finally { Files.deleteIfExists(path2); }
/* 1233 */      } void printEntry(ZipEntry paramZipEntry) throws IOException { if (this.vflag) {
/* 1234 */       StringBuilder stringBuilder = new StringBuilder();
/* 1235 */       String str = Long.toString(paramZipEntry.getSize());
/* 1236 */       for (int i = 6 - str.length(); i > 0; i--) {
/* 1237 */         stringBuilder.append(' ');
/*      */       }
/* 1239 */       stringBuilder.append(str).append(' ').append((new Date(paramZipEntry.getTime())).toString());
/* 1240 */       stringBuilder.append(' ').append(paramZipEntry.getName());
/* 1241 */       output(stringBuilder.toString());
/*      */     } else {
/* 1243 */       output(paramZipEntry.getName());
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void usageError() {
/* 1251 */     error(getMsg("usage"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void fatalError(Exception paramException) {
/* 1258 */     paramException.printStackTrace();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void fatalError(String paramString) {
/* 1266 */     error(this.program + ": " + paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void output(String paramString) {
/* 1273 */     this.out.println(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void error(String paramString) {
/* 1280 */     this.err.println(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void main(String[] paramArrayOfString) {
/* 1287 */     Main main = new Main(System.out, System.err, "jar");
/* 1288 */     System.exit(main.run(paramArrayOfString) ? 0 : 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CRC32OutputStream
/*      */     extends OutputStream
/*      */   {
/* 1297 */     final CRC32 crc = new CRC32();
/* 1298 */     long n = 0L;
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(int param1Int) throws IOException {
/* 1303 */       this.crc.update(param1Int);
/* 1304 */       this.n++;
/*      */     }
/*      */     
/*      */     public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 1308 */       this.crc.update(param1ArrayOfbyte, param1Int1, param1Int2);
/* 1309 */       this.n += param1Int2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void updateEntry(ZipEntry param1ZipEntry) {
/* 1317 */       param1ZipEntry.setMethod(0);
/* 1318 */       param1ZipEntry.setSize(this.n);
/* 1319 */       param1ZipEntry.setCrc(this.crc.getValue());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private File createTemporaryFile(String paramString1, String paramString2) {
/* 1328 */     File file = null;
/*      */     
/*      */     try {
/* 1331 */       file = File.createTempFile(paramString1, paramString2);
/* 1332 */     } catch (IOException|SecurityException iOException) {}
/*      */ 
/*      */     
/* 1335 */     if (file == null)
/*      */     {
/* 1337 */       if (this.fname != null) {
/*      */         try {
/* 1339 */           File file1 = (new File(this.fname)).getAbsoluteFile().getParentFile();
/* 1340 */           file = File.createTempFile(this.fname, ".tmp" + paramString2, file1);
/* 1341 */         } catch (IOException iOException) {
/*      */           
/* 1343 */           fatalError(iOException);
/*      */         } 
/*      */       } else {
/*      */         
/* 1347 */         fatalError(new IOException(getMsg("error.create.tempfile")));
/*      */       } 
/*      */     }
/* 1350 */     return file;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/tools/jar/Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */