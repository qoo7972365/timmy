/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.JarOutputStream;
/*     */ import java.util.jar.Pack200;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Driver
/*     */ {
/*  61 */   private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("com.sun.java.util.jar.pack.DriverResource"); private static final String PACK200_OPTION_MAP = "--repack                 $ \n  -r +>- @--repack              $ \n--no-gzip                $ \n  -g +>- @--no-gzip             $ \n--strip-debug            $ \n  -G +>- @--strip-debug         $ \n--no-keep-file-order     $ \n  -O +>- @--no-keep-file-order  $ \n--segment-limit=      *> = \n  -S +>  @--segment-limit=      = \n--effort=             *> = \n  -E +>  @--effort=             = \n--deflate-hint=       *> = \n  -H +>  @--deflate-hint=       = \n--modification-time=  *> = \n  -m +>  @--modification-time=  = \n--pass-file=        *> &\000 \n  -P +>  @--pass-file=        &\000 \n--unknown-attribute=  *> = \n  -U +>  @--unknown-attribute=  = \n--class-attribute=  *> &\000 \n  -C +>  @--class-attribute=  &\000 \n--field-attribute=  *> &\000 \n  -F +>  @--field-attribute=  &\000 \n--method-attribute= *> &\000 \n  -M +>  @--method-attribute= &\000 \n--code-attribute=   *> &\000 \n  -D +>  @--code-attribute=   &\000 \n--config-file=      *>   . \n  -f +>  @--config-file=        . \n--no-strip-debug  !--strip-debug         \n--gzip            !--no-gzip             \n--keep-file-order !--no-keep-file-order  \n--verbose                $ \n  -v +>- @--verbose             $ \n--quiet        !--verbose  \n  -q +>- !--verbose               \n--log-file=           *> = \n  -l +>  @--log-file=           = \n--version                . \n  -V +>  @--version             . \n--help               . \n  -? +> @--help . \n  -h +> @--help . \n--           . \n-   +?    >- . \n";
/*     */   public static void main(String[] paramArrayOfString) throws IOException {
/*     */     String str4, arrayOfString[];
/*  64 */     ArrayList<String> arrayList = new ArrayList(Arrays.asList((Object[])paramArrayOfString));
/*     */     
/*  66 */     boolean bool1 = true;
/*  67 */     boolean bool2 = false;
/*  68 */     boolean bool3 = false;
/*  69 */     boolean bool4 = true;
/*  70 */     String str1 = null;
/*  71 */     String str2 = "com.sun.java.util.jar.pack.verbose";
/*     */ 
/*     */ 
/*     */     
/*  75 */     String str3 = arrayList.isEmpty() ? "" : arrayList.get(0);
/*  76 */     switch (str3) {
/*     */       case "--pack":
/*  78 */         arrayList.remove(0);
/*     */         break;
/*     */       case "--unpack":
/*  81 */         arrayList.remove(0);
/*  82 */         bool1 = false;
/*  83 */         bool2 = true;
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  89 */     HashMap<Object, Object> hashMap1 = new HashMap<>();
/*  90 */     hashMap1.put(str2, System.getProperty(str2));
/*     */ 
/*     */ 
/*     */     
/*  94 */     if (bool1) {
/*  95 */       str4 = "--repack                 $ \n  -r +>- @--repack              $ \n--no-gzip                $ \n  -g +>- @--no-gzip             $ \n--strip-debug            $ \n  -G +>- @--strip-debug         $ \n--no-keep-file-order     $ \n  -O +>- @--no-keep-file-order  $ \n--segment-limit=      *> = \n  -S +>  @--segment-limit=      = \n--effort=             *> = \n  -E +>  @--effort=             = \n--deflate-hint=       *> = \n  -H +>  @--deflate-hint=       = \n--modification-time=  *> = \n  -m +>  @--modification-time=  = \n--pass-file=        *> &\000 \n  -P +>  @--pass-file=        &\000 \n--unknown-attribute=  *> = \n  -U +>  @--unknown-attribute=  = \n--class-attribute=  *> &\000 \n  -C +>  @--class-attribute=  &\000 \n--field-attribute=  *> &\000 \n  -F +>  @--field-attribute=  &\000 \n--method-attribute= *> &\000 \n  -M +>  @--method-attribute= &\000 \n--code-attribute=   *> &\000 \n  -D +>  @--code-attribute=   &\000 \n--config-file=      *>   . \n  -f +>  @--config-file=        . \n--no-strip-debug  !--strip-debug         \n--gzip            !--no-gzip             \n--keep-file-order !--no-keep-file-order  \n--verbose                $ \n  -v +>- @--verbose             $ \n--quiet        !--verbose  \n  -q +>- !--verbose               \n--log-file=           *> = \n  -l +>  @--log-file=           = \n--version                . \n  -V +>  @--version             . \n--help               . \n  -? +> @--help . \n  -h +> @--help . \n--           . \n-   +?    >- . \n";
/*  96 */       arrayOfString = PACK200_PROPERTY_TO_OPTION;
/*     */     } else {
/*  98 */       str4 = "--deflate-hint=       *> = \n  -H +>  @--deflate-hint=       = \n--verbose                $ \n  -v +>- @--verbose             $ \n--quiet        !--verbose  \n  -q +>- !--verbose               \n--remove-pack-file       $ \n  -r +>- @--remove-pack-file    $ \n--log-file=           *> = \n  -l +>  @--log-file=           = \n--config-file=        *> . \n  -f +>  @--config-file=        . \n--           . \n-   +?    >- . \n--version                . \n  -V +>  @--version             . \n--help               . \n  -? +> @--help . \n  -h +> @--help . \n";
/*  99 */       arrayOfString = UNPACK200_PROPERTY_TO_OPTION;
/*     */     } 
/*     */ 
/*     */     
/* 103 */     HashMap<Object, Object> hashMap2 = new HashMap<>(); try {
/*     */       String str;
/*     */       while (true) {
/* 106 */         str = parseCommandOptions(arrayList, str4, (Map)hashMap2);
/*     */ 
/*     */         
/* 109 */         Iterator<String> iterator = hashMap2.keySet().iterator();
/* 110 */         while (iterator.hasNext()) {
/* 111 */           String str10 = iterator.next();
/* 112 */           String str11 = null;
/* 113 */           for (byte b = 0; b < arrayOfString.length; b += 2) {
/* 114 */             if (str10.equals(arrayOfString[1 + b])) {
/* 115 */               str11 = arrayOfString[0 + b];
/*     */               break;
/*     */             } 
/*     */           } 
/* 119 */           if (str11 != null) {
/* 120 */             String str12 = (String)hashMap2.get(str10);
/* 121 */             iterator.remove();
/* 122 */             if (!str11.endsWith(".")) {
/*     */               
/* 124 */               if (!str10.equals("--verbose") && 
/* 125 */                 !str10.endsWith("=")) {
/*     */                 
/* 127 */                 boolean bool = (str12 != null) ? true : false;
/* 128 */                 if (str10.startsWith("--no-"))
/* 129 */                   bool = !bool ? true : false; 
/* 130 */                 str12 = bool ? "true" : "false";
/*     */               } 
/* 132 */               hashMap1.put(str11, str12); continue;
/* 133 */             }  if (str11.contains(".attribute.")) {
/* 134 */               for (String str13 : str12.split("\000")) {
/* 135 */                 String[] arrayOfString1 = str13.split("=", 2);
/* 136 */                 hashMap1.put(str11 + arrayOfString1[0], arrayOfString1[1]);
/*     */               } 
/*     */               continue;
/*     */             } 
/* 140 */             byte b1 = 1;
/* 141 */             for (String str13 : str12.split("\000")) {
/*     */               
/*     */               while (true) {
/* 144 */                 String str14 = str11 + "cli." + b1++;
/* 145 */                 if (!hashMap1.containsKey(str14)) {
/* 146 */                   hashMap1.put(str14, str13);
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 153 */         if ("--config-file=".equals(str)) {
/* 154 */           String str10 = arrayList.remove(0);
/* 155 */           Properties properties = new Properties();
/* 156 */           try (FileInputStream null = new FileInputStream(str10)) {
/* 157 */             properties.load(fileInputStream);
/*     */           } 
/* 159 */           if (hashMap1.get(str2) != null)
/* 160 */             properties.list(System.out); 
/* 161 */           for (Map.Entry<Object, Object> entry : properties.entrySet())
/* 162 */             hashMap1.put(entry.getKey(), entry.getValue());  continue;
/*     */         }  break;
/* 164 */       }  if ("--version".equals(str)) {
/* 165 */         System.out.println(MessageFormat.format(RESOURCE.getString("VERSION"), new Object[] { Driver.class.getName(), "1.31, 07/05/05" })); return;
/*     */       } 
/* 167 */       if ("--help".equals(str)) {
/* 168 */         printUsage(bool1, true, System.out);
/* 169 */         System.exit(1);
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/* 175 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 176 */       System.err.println(MessageFormat.format(RESOURCE.getString("BAD_ARGUMENT"), new Object[] { illegalArgumentException }));
/* 177 */       printUsage(bool1, false, System.err);
/* 178 */       System.exit(2);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 183 */     for (String str10 : hashMap2.keySet()) {
/* 184 */       String str11 = (String)hashMap2.get(str10);
/* 185 */       switch (str10) {
/*     */         case "--repack":
/* 187 */           bool3 = true;
/*     */           continue;
/*     */         case "--no-gzip":
/* 190 */           bool4 = (str11 == null) ? true : false;
/*     */           continue;
/*     */         case "--log-file=":
/* 193 */           str1 = str11;
/*     */           continue;
/*     */       } 
/* 196 */       throw new InternalError(MessageFormat.format(RESOURCE
/* 197 */             .getString("BAD_OPTION"), new Object[] { str10, hashMap2
/* 198 */               .get(str10) }));
/*     */     } 
/*     */ 
/*     */     
/* 202 */     if (str1 != null && !str1.equals("")) {
/* 203 */       if (str1.equals("-")) {
/* 204 */         System.setErr(System.out);
/*     */       } else {
/* 206 */         FileOutputStream fileOutputStream = new FileOutputStream(str1);
/*     */         
/* 208 */         System.setErr(new PrintStream(fileOutputStream));
/*     */       } 
/*     */     }
/*     */     
/* 212 */     boolean bool5 = (hashMap1.get(str2) != null) ? true : false;
/*     */     
/* 214 */     String str5 = "";
/* 215 */     if (!arrayList.isEmpty()) {
/* 216 */       str5 = arrayList.remove(0);
/*     */     }
/* 218 */     String str6 = "";
/* 219 */     if (!arrayList.isEmpty()) {
/* 220 */       str6 = arrayList.remove(0);
/*     */     }
/* 222 */     String str7 = "";
/* 223 */     String str8 = "";
/* 224 */     String str9 = "";
/* 225 */     if (bool3) {
/*     */ 
/*     */ 
/*     */       
/* 229 */       if (str5.toLowerCase().endsWith(".pack") || str5
/* 230 */         .toLowerCase().endsWith(".pac") || str5
/* 231 */         .toLowerCase().endsWith(".gz")) {
/* 232 */         System.err.println(MessageFormat.format(RESOURCE
/* 233 */               .getString("BAD_REPACK_OUTPUT"), new Object[] { str5 }));
/*     */         
/* 235 */         printUsage(bool1, false, System.err);
/* 236 */         System.exit(2);
/*     */       } 
/* 238 */       str7 = str5;
/*     */       
/* 240 */       if (str6.equals(""))
/*     */       {
/*     */         
/* 243 */         str6 = str7;
/*     */       }
/* 245 */       str9 = createTempFile(str7, ".pack").getPath();
/* 246 */       str5 = str9;
/* 247 */       bool4 = false;
/*     */     } 
/*     */     
/* 250 */     if (!arrayList.isEmpty() || (
/*     */ 
/*     */       
/* 253 */       !str6.toLowerCase().endsWith(".jar") && 
/* 254 */       !str6.toLowerCase().endsWith(".zip") && (
/* 255 */       !str6.equals("-") || bool1))) {
/* 256 */       printUsage(bool1, false, System.err);
/* 257 */       System.exit(2);
/*     */       
/*     */       return;
/*     */     } 
/* 261 */     if (bool3) {
/* 262 */       bool1 = bool2 = true;
/* 263 */     } else if (bool1) {
/* 264 */       bool2 = false;
/*     */     } 
/* 266 */     Pack200.Packer packer = Pack200.newPacker();
/* 267 */     Pack200.Unpacker unpacker = Pack200.newUnpacker();
/*     */     
/* 269 */     packer.properties().putAll(hashMap1);
/* 270 */     unpacker.properties().putAll(hashMap1);
/* 271 */     if (bool3 && str7.equals(str6)) {
/* 272 */       String str = getZipComment(str6);
/* 273 */       if (bool5 && str.length() > 0)
/* 274 */         System.out.println(MessageFormat.format(RESOURCE.getString("DETECTED_ZIP_COMMENT"), new Object[] { str })); 
/* 275 */       if (str.indexOf("PACK200") >= 0) {
/* 276 */         System.out.println(MessageFormat.format(RESOURCE.getString("SKIP_FOR_REPACKED"), new Object[] { str6 }));
/* 277 */         bool1 = false;
/* 278 */         bool2 = false;
/* 279 */         bool3 = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 285 */       if (bool1) {
/*     */         BufferedOutputStream bufferedOutputStream;
/* 287 */         JarFile jarFile = new JarFile(new File(str6));
/*     */ 
/*     */         
/* 290 */         if (str5.equals("-")) {
/* 291 */           PrintStream printStream = System.out;
/*     */           
/* 293 */           System.setOut(System.err);
/* 294 */         } else if (bool4) {
/* 295 */           if (!str5.endsWith(".gz")) {
/* 296 */             System.err.println(MessageFormat.format(RESOURCE.getString("WRITE_PACK_FILE"), new Object[] { str5 }));
/* 297 */             printUsage(bool1, false, System.err);
/* 298 */             System.exit(2);
/*     */           } 
/* 300 */           FileOutputStream fileOutputStream = new FileOutputStream(str5);
/* 301 */           BufferedOutputStream bufferedOutputStream1 = new BufferedOutputStream(fileOutputStream);
/* 302 */           GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(bufferedOutputStream1);
/*     */         } else {
/* 304 */           if (!str5.toLowerCase().endsWith(".pack") && 
/* 305 */             !str5.toLowerCase().endsWith(".pac")) {
/* 306 */             System.err.println(MessageFormat.format(RESOURCE.getString("WRITE_PACKGZ_FILE"), new Object[] { str5 }));
/* 307 */             printUsage(bool1, false, System.err);
/* 308 */             System.exit(2);
/*     */           } 
/* 310 */           FileOutputStream fileOutputStream = new FileOutputStream(str5);
/* 311 */           bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
/*     */         } 
/* 313 */         packer.pack(jarFile, bufferedOutputStream);
/*     */         
/* 315 */         bufferedOutputStream.close();
/*     */       } 
/*     */       
/* 318 */       if (bool3 && str7.equals(str6)) {
/*     */ 
/*     */ 
/*     */         
/* 322 */         File file = createTempFile(str6, ".bak");
/*     */         
/* 324 */         file.delete();
/* 325 */         boolean bool = (new File(str6)).renameTo(file);
/* 326 */         if (!bool) {
/* 327 */           throw new Error(MessageFormat.format(RESOURCE.getString("SKIP_FOR_MOVE_FAILED"), new Object[] { str8 }));
/*     */         }
/*     */         
/* 330 */         str8 = file.getPath();
/*     */       } 
/*     */ 
/*     */       
/* 334 */       if (bool2) {
/*     */         FileOutputStream fileOutputStream;
/*     */         
/* 337 */         if (str5.equals("-")) {
/* 338 */           inputStream = System.in;
/*     */         } else {
/* 340 */           inputStream = new FileInputStream(new File(str5));
/* 341 */         }  BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
/* 342 */         InputStream inputStream = bufferedInputStream;
/* 343 */         if (Utils.isGZIPMagic(Utils.readMagic(bufferedInputStream))) {
/* 344 */           inputStream = new GZIPInputStream(inputStream);
/*     */         }
/* 346 */         String str = str7.equals("") ? str6 : str7;
/*     */         
/* 348 */         if (str.equals("-")) {
/* 349 */           PrintStream printStream = System.out;
/*     */         } else {
/* 351 */           fileOutputStream = new FileOutputStream(str);
/* 352 */         }  BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
/* 353 */         try (JarOutputStream null = new JarOutputStream(bufferedOutputStream)) {
/* 354 */           unpacker.unpack(inputStream, jarOutputStream);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 360 */       if (!str8.equals(""))
/*     */       {
/* 362 */         (new File(str8)).delete();
/* 363 */         str8 = "";
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 368 */       if (!str8.equals("")) {
/* 369 */         File file = new File(str6);
/* 370 */         file.delete();
/* 371 */         (new File(str8)).renameTo(file);
/*     */       } 
/*     */       
/* 374 */       if (!str9.equals(""))
/* 375 */         (new File(str9)).delete(); 
/*     */     } 
/*     */   }
/*     */   private static final String UNPACK200_OPTION_MAP = "--deflate-hint=       *> = \n  -H +>  @--deflate-hint=       = \n--verbose                $ \n  -v +>- @--verbose             $ \n--quiet        !--verbose  \n  -q +>- !--verbose               \n--remove-pack-file       $ \n  -r +>- @--remove-pack-file    $ \n--log-file=           *> = \n  -l +>  @--log-file=           = \n--config-file=        *> . \n  -f +>  @--config-file=        . \n--           . \n-   +?    >- . \n--version                . \n  -V +>  @--version             . \n--help               . \n  -? +> @--help . \n  -h +> @--help . \n";
/*     */   
/*     */   private static File createTempFile(String paramString1, String paramString2) throws IOException {
/* 381 */     File file1 = new File(paramString1);
/* 382 */     String str = file1.getName();
/* 383 */     if (str.length() < 3) str = str + "tmp";
/*     */ 
/*     */ 
/*     */     
/* 387 */     File file2 = (file1.getParentFile() == null && paramString2.equals(".bak")) ? (new File(".")).getAbsoluteFile() : file1.getParentFile();
/*     */ 
/*     */ 
/*     */     
/* 391 */     Path path = (file2 == null) ? Files.createTempFile(str, paramString2, (FileAttribute<?>[])new FileAttribute[0]) : Files.createTempFile(file2.toPath(), str, paramString2, (FileAttribute<?>[])new FileAttribute[0]);
/*     */     
/* 393 */     return path.toFile();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void printUsage(boolean paramBoolean1, boolean paramBoolean2, PrintStream paramPrintStream) {
/* 398 */     String str = paramBoolean1 ? "pack200" : "unpack200";
/* 399 */     String[] arrayOfString1 = (String[])RESOURCE.getObject("PACK_HELP");
/* 400 */     String[] arrayOfString2 = (String[])RESOURCE.getObject("UNPACK_HELP");
/* 401 */     String[] arrayOfString3 = paramBoolean1 ? arrayOfString1 : arrayOfString2;
/* 402 */     for (byte b = 0; b < arrayOfString3.length; b++) {
/* 403 */       paramPrintStream.println(arrayOfString3[b]);
/* 404 */       if (!paramBoolean2) {
/* 405 */         paramPrintStream.println(MessageFormat.format(RESOURCE.getString("MORE_INFO"), new Object[] { str }));
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getZipComment(String paramString) throws IOException {
/* 413 */     byte[] arrayOfByte = new byte[1000];
/* 414 */     long l1 = (new File(paramString)).length();
/* 415 */     if (l1 <= 0L) return ""; 
/* 416 */     long l2 = Math.max(0L, l1 - arrayOfByte.length);
/* 417 */     try (FileInputStream null = new FileInputStream(new File(paramString))) {
/* 418 */       fileInputStream.skip(l2);
/* 419 */       fileInputStream.read(arrayOfByte);
/* 420 */       for (int i = arrayOfByte.length - 4; i >= 0; i--) {
/* 421 */         if (arrayOfByte[i + 0] == 80 && arrayOfByte[i + 1] == 75 && arrayOfByte[i + 2] == 5 && arrayOfByte[i + 3] == 6) {
/*     */ 
/*     */           
/* 424 */           i += 22;
/* 425 */           if (i < arrayOfByte.length)
/* 426 */             return new String(arrayOfByte, i, arrayOfByte.length - i, "UTF8"); 
/* 427 */           return "";
/*     */         } 
/*     */       } 
/* 430 */       return "";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 488 */   private static final String[] PACK200_PROPERTY_TO_OPTION = new String[] { "pack.segment.limit", "--segment-limit=", "pack.keep.file.order", "--no-keep-file-order", "pack.effort", "--effort=", "pack.deflate.hint", "--deflate-hint=", "pack.modification.time", "--modification-time=", "pack.pass.file.", "--pass-file=", "pack.unknown.attribute", "--unknown-attribute=", "pack.class.attribute.", "--class-attribute=", "pack.field.attribute.", "--field-attribute=", "pack.method.attribute.", "--method-attribute=", "pack.code.attribute.", "--code-attribute=", "com.sun.java.util.jar.pack.verbose", "--verbose", "com.sun.java.util.jar.pack.strip.debug", "--strip-debug" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 505 */   private static final String[] UNPACK200_PROPERTY_TO_OPTION = new String[] { "unpack.deflate.hint", "--deflate-hint=", "com.sun.java.util.jar.pack.verbose", "--verbose", "com.sun.java.util.jar.pack.unpack.remove.packfile", "--remove-pack-file" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String parseCommandOptions(List<String> paramList, String paramString, Map<String, String> paramMap) {
/* 525 */     String str = null;
/*     */ 
/*     */     
/* 528 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*     */     
/* 530 */     for (String str1 : paramString.split("\n")) {
/* 531 */       String[] arrayOfString = str1.split("\\p{Space}+");
/* 532 */       if (arrayOfString.length != 0) {
/* 533 */         String str2 = arrayOfString[0];
/* 534 */         arrayOfString[0] = "";
/* 535 */         if (str2.length() == 0 && arrayOfString.length >= 1) {
/* 536 */           str2 = arrayOfString[1];
/* 537 */           arrayOfString[1] = "";
/*     */         } 
/* 539 */         if (str2.length() != 0) {
/* 540 */           String[] arrayOfString1 = (String[])treeMap.put(str2, arrayOfString);
/* 541 */           if (arrayOfString1 != null)
/* 542 */             throw new RuntimeException(MessageFormat.format(RESOURCE.getString("DUPLICATE_OPTION"), new Object[] { str1.trim() })); 
/*     */         } 
/*     */       } 
/*     */     } 
/* 546 */     ListIterator<String> listIterator = paramList.listIterator();
/* 547 */     ListIterator<?> listIterator1 = (new ArrayList()).listIterator();
/*     */ 
/*     */     
/*     */     label144: while (true) {
/*     */       String str1;
/*     */       
/* 553 */       if (listIterator1.hasPrevious()) {
/* 554 */         str1 = (String)listIterator1.previous();
/* 555 */         listIterator1.remove();
/* 556 */       } else if (listIterator.hasNext()) {
/* 557 */         str1 = listIterator.next();
/*     */       } else {
/*     */         break;
/*     */       } 
/*     */ 
/*     */       
/* 563 */       int i = str1.length();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 569 */         String str2 = str1.substring(0, i);
/* 570 */         if (treeMap.containsKey(str2)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 581 */           str2 = str2.intern();
/* 582 */           assert str1.startsWith(str2);
/* 583 */           assert str2.length() == i;
/* 584 */           String str3 = str1.substring(i);
/*     */ 
/*     */ 
/*     */           
/* 588 */           boolean bool1 = false;
/* 589 */           boolean bool2 = false;
/*     */           
/* 591 */           int j = listIterator1.nextIndex();
/* 592 */           String[] arrayOfString = (String[])treeMap.get(str2);
/*     */           
/* 594 */           for (String str4 : arrayOfString) {
/* 595 */             if (str4.length() != 0) {
/* 596 */               boolean bool; if (str4.startsWith("#"))
/* 597 */                 break;  byte b = 0;
/* 598 */               char c = str4.charAt(b++);
/*     */ 
/*     */ 
/*     */               
/* 602 */               switch (c) {
/*     */                 
/*     */                 case '+':
/* 605 */                   bool = (str3.length() != 0) ? true : false;
/* 606 */                   c = str4.charAt(b++);
/*     */                   break;
/*     */                 
/*     */                 case '*':
/* 610 */                   bool = true;
/* 611 */                   c = str4.charAt(b++);
/*     */                   break;
/*     */ 
/*     */                 
/*     */                 default:
/* 616 */                   bool = (str3.length() == 0) ? true : false;
/*     */                   break;
/*     */               } 
/* 619 */               if (bool) {
/*     */                 String str6, str7; boolean bool3;
/* 621 */                 String str8, str5 = str4.substring(b);
/* 622 */                 switch (c) {
/*     */                   case '.':
/* 624 */                     str = (str5.length() != 0) ? str5.intern() : str2;
/*     */                     break label144;
/*     */                   case '?':
/* 627 */                     str = (str5.length() != 0) ? str5.intern() : str1;
/* 628 */                     bool2 = true;
/*     */                     break;
/*     */                   case '@':
/* 631 */                     str2 = str5.intern();
/*     */                     break;
/*     */                   case '>':
/* 634 */                     listIterator1.add(str5 + str3);
/* 635 */                     str3 = "";
/*     */                     break;
/*     */                   case '!':
/* 638 */                     str6 = (str5.length() != 0) ? str5.intern() : str2;
/* 639 */                     paramMap.remove(str6);
/* 640 */                     paramMap.put(str6, null);
/* 641 */                     bool1 = true;
/*     */                     break;
/*     */                   
/*     */                   case '$':
/* 645 */                     if (str5.length() != 0) {
/*     */                       
/* 647 */                       str7 = str5;
/*     */                     } else {
/* 649 */                       String str9 = paramMap.get(str2);
/* 650 */                       if (str9 == null || str9.length() == 0) {
/* 651 */                         str7 = "1";
/*     */                       } else {
/*     */                         
/* 654 */                         str7 = "" + (1 + Integer.parseInt(str9));
/*     */                       } 
/*     */                     } 
/* 657 */                     paramMap.put(str2, str7);
/* 658 */                     bool1 = true;
/*     */                     break;
/*     */                   
/*     */                   case '&':
/*     */                   case '=':
/* 663 */                     bool3 = (c == '&') ? true : false;
/*     */                     
/* 665 */                     if (listIterator1.hasPrevious()) {
/* 666 */                       str8 = (String)listIterator1.previous();
/* 667 */                       listIterator1.remove();
/* 668 */                     } else if (listIterator.hasNext()) {
/* 669 */                       str8 = listIterator.next();
/*     */                     } else {
/* 671 */                       str = str1 + " ?";
/* 672 */                       bool2 = true;
/*     */                       break;
/*     */                     } 
/* 675 */                     if (bool3) {
/* 676 */                       String str9 = paramMap.get(str2);
/* 677 */                       if (str9 != null) {
/*     */                         
/* 679 */                         String str10 = str5;
/* 680 */                         if (str10.length() == 0) str10 = " "; 
/* 681 */                         str8 = str9 + str5 + str8;
/*     */                       } 
/*     */                     } 
/* 684 */                     paramMap.put(str2, str8);
/* 685 */                     bool1 = true;
/*     */                     break;
/*     */                   default:
/* 688 */                     throw new RuntimeException(MessageFormat.format(RESOURCE.getString("BAD_SPEC"), new Object[] { str2, str4 }));
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/* 693 */           if (bool1 && !bool2) {
/*     */             continue label144;
/*     */           }
/*     */ 
/*     */           
/* 698 */           while (listIterator1.nextIndex() > j) {
/*     */             
/* 700 */             listIterator1.previous();
/* 701 */             listIterator1.remove();
/*     */           } 
/*     */           
/* 704 */           if (bool2) {
/* 705 */             throw new IllegalArgumentException(str);
/*     */           }
/*     */           
/* 708 */           if (i != 0) {
/*     */             i--; continue;
/*     */           } 
/*     */         } else if (i != 0) {
/*     */           SortedMap<Object, Object> sortedMap = treeMap.headMap(str2); boolean bool = sortedMap.isEmpty() ? false : ((String)sortedMap.lastKey()).length(); i = Math.min(bool, i - 1);
/*     */           str2 = str1.substring(0, i);
/*     */           continue;
/*     */         } 
/* 716 */         listIterator1.add(str1); break;
/*     */       } 
/*     */       break;
/*     */     } 
/* 720 */     paramList.subList(0, listIterator.nextIndex()).clear();
/*     */     
/* 722 */     while (listIterator1.hasPrevious()) {
/* 723 */       paramList.add(0, (String)listIterator1.previous());
/*     */     }
/*     */     
/* 726 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/Driver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */