/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.security.AccessController;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JarIndex
/*     */ {
/*  76 */   private static final boolean metaInfFilenames = "true"
/*  77 */     .equals(AccessController.doPrivileged(new GetPropertyAction("sun.misc.JarIndex.metaInfFilenames")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private HashMap<String, LinkedList<String>> indexMap = new HashMap<>();
/*  85 */   private HashMap<String, LinkedList<String>> jarMap = new HashMap<>();
/*     */   
/*     */   private String[] jarFiles;
/*     */   
/*     */   public static final String INDEX_NAME = "META-INF/INDEX.LIST";
/*     */   
/*     */   public JarIndex() {}
/*     */   
/*     */   public JarIndex(InputStream paramInputStream) throws IOException {
/*  94 */     this();
/*  95 */     read(paramInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JarIndex(String[] paramArrayOfString) throws IOException {
/* 104 */     this();
/* 105 */     this.jarFiles = paramArrayOfString;
/* 106 */     parseJars(paramArrayOfString);
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
/*     */   public static JarIndex getJarIndex(JarFile paramJarFile) throws IOException {
/* 119 */     return getJarIndex(paramJarFile, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JarIndex getJarIndex(JarFile paramJarFile, MetaIndex paramMetaIndex) throws IOException {
/* 129 */     JarIndex jarIndex = null;
/*     */ 
/*     */ 
/*     */     
/* 133 */     if (paramMetaIndex != null && 
/* 134 */       !paramMetaIndex.mayContain("META-INF/INDEX.LIST")) {
/* 135 */       return null;
/*     */     }
/* 137 */     JarEntry jarEntry = paramJarFile.getJarEntry("META-INF/INDEX.LIST");
/*     */     
/* 139 */     if (jarEntry != null) {
/* 140 */       jarIndex = new JarIndex(paramJarFile.getInputStream(jarEntry));
/*     */     }
/* 142 */     return jarIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getJarFiles() {
/* 149 */     return this.jarFiles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addToList(String paramString1, String paramString2, HashMap<String, LinkedList<String>> paramHashMap) {
/* 158 */     LinkedList<String> linkedList = paramHashMap.get(paramString1);
/* 159 */     if (linkedList == null) {
/* 160 */       linkedList = new LinkedList();
/* 161 */       linkedList.add(paramString2);
/* 162 */       paramHashMap.put(paramString1, linkedList);
/* 163 */     } else if (!linkedList.contains(paramString2)) {
/* 164 */       linkedList.add(paramString2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinkedList<String> get(String paramString) {
/* 174 */     LinkedList<String> linkedList = null;
/* 175 */     if ((linkedList = this.indexMap.get(paramString)) == null) {
/*     */       int i;
/*     */       
/* 178 */       if ((i = paramString.lastIndexOf("/")) != -1) {
/* 179 */         linkedList = this.indexMap.get(paramString.substring(0, i));
/*     */       }
/*     */     } 
/* 182 */     return linkedList;
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
/*     */   public void add(String paramString1, String paramString2) {
/*     */     String str;
/*     */     int i;
/* 201 */     if ((i = paramString1.lastIndexOf("/")) != -1) {
/* 202 */       str = paramString1.substring(0, i);
/*     */     } else {
/* 204 */       str = paramString1;
/*     */     } 
/*     */     
/* 207 */     addMapping(str, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addMapping(String paramString1, String paramString2) {
/* 217 */     addToList(paramString1, paramString2, this.indexMap);
/*     */ 
/*     */     
/* 220 */     addToList(paramString2, paramString1, this.jarMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseJars(String[] paramArrayOfString) throws IOException {
/* 228 */     if (paramArrayOfString == null) {
/*     */       return;
/*     */     }
/*     */     
/* 232 */     String str = null;
/*     */     
/* 234 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 235 */       str = paramArrayOfString[b];
/*     */       
/* 237 */       ZipFile zipFile = new ZipFile(str.replace('/', File.separatorChar));
/*     */       
/* 239 */       Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
/* 240 */       while (enumeration.hasMoreElements()) {
/* 241 */         ZipEntry zipEntry = enumeration.nextElement();
/* 242 */         String str1 = zipEntry.getName();
/*     */ 
/*     */ 
/*     */         
/* 246 */         if (str1.equals("META-INF/") || str1
/* 247 */           .equals("META-INF/INDEX.LIST") || str1
/* 248 */           .equals("META-INF/MANIFEST.MF")) {
/*     */           continue;
/*     */         }
/* 251 */         if (!metaInfFilenames || !str1.startsWith("META-INF/")) {
/* 252 */           add(str1, str); continue;
/* 253 */         }  if (!zipEntry.isDirectory())
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 258 */           addMapping(str1, str);
/*     */         }
/*     */       } 
/*     */       
/* 262 */       zipFile.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(OutputStream paramOutputStream) throws IOException {
/* 273 */     BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(paramOutputStream, "UTF8"));
/*     */     
/* 275 */     bufferedWriter.write("JarIndex-Version: 1.0\n\n");
/*     */     
/* 277 */     if (this.jarFiles != null) {
/* 278 */       for (byte b = 0; b < this.jarFiles.length; b++) {
/*     */         
/* 280 */         String str = this.jarFiles[b];
/* 281 */         bufferedWriter.write(str + "\n");
/* 282 */         LinkedList linkedList = this.jarMap.get(str);
/* 283 */         if (linkedList != null) {
/* 284 */           Iterator<String> iterator = linkedList.iterator();
/* 285 */           while (iterator.hasNext()) {
/* 286 */             bufferedWriter.write((String)iterator.next() + "\n");
/*     */           }
/*     */         } 
/* 289 */         bufferedWriter.write("\n");
/*     */       } 
/* 291 */       bufferedWriter.flush();
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
/*     */   public void read(InputStream paramInputStream) throws IOException {
/* 303 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(paramInputStream, "UTF8"));
/*     */     
/* 305 */     String str1 = null;
/* 306 */     String str2 = null;
/*     */ 
/*     */     
/* 309 */     Vector<String> vector = new Vector();
/*     */ 
/*     */     
/* 312 */     while ((str1 = bufferedReader.readLine()) != null && !str1.endsWith(".jar"));
/*     */     
/* 314 */     for (; str1 != null; str1 = bufferedReader.readLine()) {
/* 315 */       if (str1.length() != 0)
/*     */       {
/*     */         
/* 318 */         if (str1.endsWith(".jar")) {
/* 319 */           str2 = str1;
/* 320 */           vector.add(str2);
/*     */         } else {
/* 322 */           String str = str1;
/* 323 */           addMapping(str, str2);
/*     */         } 
/*     */       }
/*     */     } 
/* 327 */     this.jarFiles = vector.<String>toArray(new String[vector.size()]);
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
/*     */   public void merge(JarIndex paramJarIndex, String paramString) {
/* 341 */     Iterator<Map.Entry> iterator = this.indexMap.entrySet().iterator();
/* 342 */     while (iterator.hasNext()) {
/* 343 */       Map.Entry entry = iterator.next();
/* 344 */       String str = (String)entry.getKey();
/* 345 */       LinkedList linkedList = (LinkedList)entry.getValue();
/* 346 */       Iterator<String> iterator1 = linkedList.iterator();
/* 347 */       while (iterator1.hasNext()) {
/* 348 */         String str1 = iterator1.next();
/* 349 */         if (paramString != null) {
/* 350 */           str1 = paramString.concat(str1);
/*     */         }
/* 352 */         paramJarIndex.addMapping(str, str1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/JarIndex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */