/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetaIndex
/*     */ {
/*     */   private static volatile Map<File, MetaIndex> jarMap;
/*     */   private String[] contents;
/*     */   private boolean isClassOnlyJar;
/*     */   
/*     */   public static MetaIndex forJar(File paramFile) {
/* 147 */     return getJarMap().get(paramFile);
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
/*     */   public static synchronized void registerDirectory(File paramFile) {
/* 162 */     File file = new File(paramFile, "meta-index");
/* 163 */     if (file.exists()) {
/*     */       try {
/* 165 */         BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
/* 166 */         String str1 = null;
/* 167 */         String str2 = null;
/* 168 */         boolean bool = false;
/* 169 */         ArrayList<String> arrayList = new ArrayList();
/* 170 */         Map<File, MetaIndex> map = getJarMap();
/*     */ 
/*     */         
/* 173 */         paramFile = paramFile.getCanonicalFile();
/*     */ 
/*     */ 
/*     */         
/* 177 */         str1 = bufferedReader.readLine();
/* 178 */         if (str1 == null || 
/* 179 */           !str1.equals("% VERSION 2")) {
/* 180 */           bufferedReader.close();
/*     */           return;
/*     */         } 
/* 183 */         while ((str1 = bufferedReader.readLine()) != null) {
/* 184 */           switch (str1.charAt(0)) {
/*     */             
/*     */             case '!':
/*     */             case '#':
/*     */             case '@':
/* 189 */               if (str2 != null && arrayList.size() > 0) {
/* 190 */                 map.put(new File(paramFile, str2), new MetaIndex(arrayList, bool));
/*     */ 
/*     */ 
/*     */                 
/* 194 */                 arrayList.clear();
/*     */               } 
/*     */               
/* 197 */               str2 = str1.substring(2);
/* 198 */               if (str1.charAt(0) == '!') {
/* 199 */                 bool = true; continue;
/* 200 */               }  if (bool) {
/* 201 */                 bool = false;
/*     */               }
/*     */               continue;
/*     */             
/*     */             case '%':
/*     */               continue;
/*     */           } 
/*     */           
/* 209 */           arrayList.add(str1);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 214 */         if (str2 != null && arrayList.size() > 0) {
/* 215 */           map.put(new File(paramFile, str2), new MetaIndex(arrayList, bool));
/*     */         }
/*     */ 
/*     */         
/* 219 */         bufferedReader.close();
/*     */       }
/* 221 */       catch (IOException iOException) {}
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
/*     */   public boolean mayContain(String paramString) {
/* 236 */     if (this.isClassOnlyJar && !paramString.endsWith(".class")) {
/* 237 */       return false;
/*     */     }
/*     */     
/* 240 */     String[] arrayOfString = this.contents;
/* 241 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 242 */       if (paramString.startsWith(arrayOfString[b])) {
/* 243 */         return true;
/*     */       }
/*     */     } 
/* 246 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MetaIndex(List<String> paramList, boolean paramBoolean) throws IllegalArgumentException {
/* 255 */     if (paramList == null) {
/* 256 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 259 */     this.contents = paramList.<String>toArray(new String[0]);
/* 260 */     this.isClassOnlyJar = paramBoolean;
/*     */   }
/*     */   
/*     */   private static Map<File, MetaIndex> getJarMap() {
/* 264 */     if (jarMap == null) {
/* 265 */       synchronized (MetaIndex.class) {
/* 266 */         if (jarMap == null) {
/* 267 */           jarMap = new HashMap<>();
/*     */         }
/*     */       } 
/*     */     }
/* 271 */     assert jarMap != null;
/* 272 */     return jarMap;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/MetaIndex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */