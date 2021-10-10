/*     */ package com.sun.org.apache.bcel.internal.util;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassPath
/*     */   implements Serializable
/*     */ {
/*  73 */   public static final ClassPath SYSTEM_CLASS_PATH = new ClassPath();
/*     */ 
/*     */   
/*     */   private PathEntry[] paths;
/*     */   
/*     */   private String class_path;
/*     */ 
/*     */   
/*     */   public ClassPath(String class_path) {
/*  82 */     this.class_path = class_path;
/*     */     
/*  84 */     ArrayList<Dir> vec = new ArrayList();
/*     */ 
/*     */     
/*  87 */     StringTokenizer tok = new StringTokenizer(class_path, SecuritySupport.getSystemProperty("path.separator"));
/*  88 */     while (tok.hasMoreTokens()) {
/*     */       
/*  90 */       String path = tok.nextToken();
/*     */       
/*  92 */       if (!path.equals("")) {
/*  93 */         File file = new File(path);
/*     */         
/*     */         try {
/*  96 */           if (SecuritySupport.getFileExists(file)) {
/*  97 */             if (file.isDirectory()) {
/*  98 */               vec.add(new Dir(path)); continue;
/*     */             } 
/* 100 */             vec.add(new Zip(new ZipFile(file)));
/*     */           } 
/* 102 */         } catch (IOException e) {
/* 103 */           System.err.println("CLASSPATH component " + file + ": " + e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     this.paths = new PathEntry[vec.size()];
/* 109 */     vec.toArray(this.paths);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassPath() {
/* 118 */     this("");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 124 */     return this.class_path;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 128 */     return this.class_path.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 132 */     if (o instanceof ClassPath) {
/* 133 */       return this.class_path.equals(((ClassPath)o).class_path);
/*     */     }
/*     */     
/* 136 */     return false;
/*     */   }
/*     */   
/*     */   private static final void getPathComponents(String path, ArrayList<String> list) {
/* 140 */     if (path != null) {
/* 141 */       StringTokenizer tok = new StringTokenizer(path, File.pathSeparator);
/*     */       
/* 143 */       while (tok.hasMoreTokens()) {
/* 144 */         String name = tok.nextToken();
/* 145 */         File file = new File(name);
/*     */         
/* 147 */         if (SecuritySupport.getFileExists(file)) {
/* 148 */           list.add(name);
/*     */         }
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
/*     */   public static final String getClassPath() {
/*     */     String class_path, boot_path, ext_path;
/*     */     try {
/* 164 */       class_path = SecuritySupport.getSystemProperty("java.class.path");
/* 165 */       boot_path = SecuritySupport.getSystemProperty("sun.boot.class.path");
/* 166 */       ext_path = SecuritySupport.getSystemProperty("java.ext.dirs");
/*     */     }
/* 168 */     catch (SecurityException securityException) {
/* 169 */       return "";
/*     */     } 
/*     */     
/* 172 */     ArrayList<String> list = new ArrayList();
/*     */     
/* 174 */     getPathComponents(class_path, list);
/* 175 */     getPathComponents(boot_path, list);
/*     */     
/* 177 */     ArrayList dirs = new ArrayList();
/* 178 */     getPathComponents(ext_path, dirs);
/*     */     
/* 180 */     for (Iterator<String> e = dirs.iterator(); e.hasNext(); ) {
/* 181 */       File ext_dir = new File(e.next());
/* 182 */       String[] extensions = SecuritySupport.getFileList(ext_dir, new FilenameFilter() {
/*     */             public boolean accept(File dir, String name) {
/* 184 */               name = name.toLowerCase();
/* 185 */               return (name.endsWith(".zip") || name.endsWith(".jar"));
/*     */             }
/*     */           });
/*     */       
/* 189 */       if (extensions != null)
/* 190 */         for (int i = 0; i < extensions.length; i++) {
/* 191 */           list.add(ext_path + File.separatorChar + extensions[i]);
/*     */         } 
/*     */     } 
/* 194 */     StringBuffer buf = new StringBuffer();
/*     */     
/* 196 */     for (Iterator<String> iterator1 = list.iterator(); iterator1.hasNext(); ) {
/* 197 */       buf.append(iterator1.next());
/*     */       
/* 199 */       if (iterator1.hasNext()) {
/* 200 */         buf.append(File.pathSeparatorChar);
/*     */       }
/*     */     } 
/* 203 */     return buf.toString().intern();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getInputStream(String name) throws IOException {
/* 211 */     return getInputStream(name, ".class");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getInputStream(String name, String suffix) throws IOException {
/* 222 */     InputStream is = null;
/*     */     
/*     */     try {
/* 225 */       is = getClass().getClassLoader().getResourceAsStream(name + suffix);
/* 226 */     } catch (Exception exception) {}
/*     */     
/* 228 */     if (is != null) {
/* 229 */       return is;
/*     */     }
/* 231 */     return getClassFile(name, suffix).getInputStream();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassFile getClassFile(String name, String suffix) throws IOException {
/* 240 */     for (int i = 0; i < this.paths.length; i++) {
/*     */       ClassFile cf;
/*     */       
/* 243 */       if ((cf = this.paths[i].getClassFile(name, suffix)) != null) {
/* 244 */         return cf;
/*     */       }
/*     */     } 
/* 247 */     throw new IOException("Couldn't find: " + name + suffix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassFile getClassFile(String name) throws IOException {
/* 255 */     return getClassFile(name, ".class");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBytes(String name, String suffix) throws IOException {
/* 264 */     InputStream is = getInputStream(name, suffix);
/*     */     
/* 266 */     if (is == null) {
/* 267 */       throw new IOException("Couldn't find: " + name + suffix);
/*     */     }
/* 269 */     DataInputStream dis = new DataInputStream(is);
/* 270 */     byte[] bytes = new byte[is.available()];
/* 271 */     dis.readFully(bytes);
/* 272 */     dis.close(); is.close();
/*     */     
/* 274 */     return bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBytes(String name) throws IOException {
/* 281 */     return getBytes(name, ".class");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPath(String name) throws IOException {
/* 289 */     int index = name.lastIndexOf('.');
/* 290 */     String suffix = "";
/*     */     
/* 292 */     if (index > 0) {
/* 293 */       suffix = name.substring(index);
/* 294 */       name = name.substring(0, index);
/*     */     } 
/*     */     
/* 297 */     return getPath(name, suffix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPath(String name, String suffix) throws IOException {
/* 306 */     return getClassFile(name, suffix).getPath();
/*     */   }
/*     */ 
/*     */   
/*     */   private static abstract class PathEntry
/*     */     implements Serializable
/*     */   {
/*     */     private PathEntry() {}
/*     */ 
/*     */     
/*     */     abstract ClassPath.ClassFile getClassFile(String param1String1, String param1String2) throws IOException;
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface ClassFile
/*     */   {
/*     */     InputStream getInputStream() throws IOException;
/*     */ 
/*     */     
/*     */     String getPath();
/*     */ 
/*     */     
/*     */     String getBase();
/*     */     
/*     */     long getTime();
/*     */     
/*     */     long getSize();
/*     */   }
/*     */   
/*     */   private static class Dir
/*     */     extends PathEntry
/*     */   {
/*     */     private String dir;
/*     */     
/*     */     Dir(String d) {
/* 341 */       this.dir = d;
/*     */     }
/*     */     
/*     */     ClassPath.ClassFile getClassFile(String name, String suffix) throws IOException {
/* 345 */       final File file = new File(this.dir + File.separatorChar + name.replace('.', File.separatorChar) + suffix);
/*     */       
/* 347 */       return SecuritySupport.getFileExists(file) ? new ClassPath.ClassFile() { public InputStream getInputStream() throws IOException {
/* 348 */             return new FileInputStream(file);
/*     */           } public String getPath() {
/*     */             
/* 351 */             try { return file.getCanonicalPath(); }
/* 352 */             catch (IOException e) { return null; }
/*     */           
/*     */           }
/* 355 */           public long getTime() { return file.lastModified(); }
/* 356 */           public long getSize() { return file.length(); } public String getBase() {
/* 357 */             return ClassPath.Dir.this.dir;
/*     */           } }
/*     */          : null;
/*     */     }
/*     */     public String toString() {
/* 362 */       return this.dir;
/*     */     } }
/*     */   
/*     */   private static class Zip extends PathEntry { private ZipFile zip;
/*     */     
/*     */     Zip(ZipFile z) {
/* 368 */       this.zip = z;
/*     */     }
/*     */     ClassPath.ClassFile getClassFile(String name, String suffix) throws IOException {
/* 371 */       final ZipEntry entry = this.zip.getEntry(name.replace('.', '/') + suffix);
/*     */       
/* 373 */       return (entry != null) ? new ClassPath.ClassFile() {
/* 374 */           public InputStream getInputStream() throws IOException { return ClassPath.Zip.this.zip.getInputStream(entry); }
/* 375 */           public String getPath() { return entry.toString(); }
/* 376 */           public long getTime() { return entry.getTime(); } public long getSize() {
/* 377 */             return entry.getSize();
/*     */           } public String getBase() {
/* 379 */             return ClassPath.Zip.this.zip.getName();
/*     */           }
/*     */         } : null;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/util/ClassPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */