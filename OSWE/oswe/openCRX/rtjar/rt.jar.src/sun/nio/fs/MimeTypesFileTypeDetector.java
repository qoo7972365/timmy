/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MimeTypesFileTypeDetector
/*     */   extends AbstractFileTypeDetector
/*     */ {
/*     */   private final Path mimeTypesFile;
/*     */   private Map<String, String> mimeTypeMap;
/*     */   private volatile boolean loaded = false;
/*     */   
/*     */   public MimeTypesFileTypeDetector(Path paramPath) {
/*  58 */     this.mimeTypesFile = paramPath;
/*     */   }
/*     */   
/*     */   protected String implProbeContentType(Path paramPath) {
/*     */     String str2;
/*  63 */     Path path = paramPath.getFileName();
/*  64 */     if (path == null) {
/*  65 */       return null;
/*     */     }
/*  67 */     String str1 = getExtension(path.toString());
/*  68 */     if (str1.isEmpty()) {
/*  69 */       return null;
/*     */     }
/*  71 */     loadMimeTypes();
/*  72 */     if (this.mimeTypeMap == null || this.mimeTypeMap.isEmpty()) {
/*  73 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     do {
/*  78 */       str2 = this.mimeTypeMap.get(str1);
/*  79 */       if (str2 != null)
/*  80 */         continue;  str1 = getExtension(str1);
/*  81 */     } while (str2 == null && !str1.isEmpty());
/*     */     
/*  83 */     return str2;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getExtension(String paramString) {
/*  88 */     String str = "";
/*  89 */     if (paramString != null && !paramString.isEmpty()) {
/*  90 */       int i = paramString.indexOf('.');
/*  91 */       if (i >= 0 && i < paramString.length() - 1) {
/*  92 */         str = paramString.substring(i + 1);
/*     */       }
/*     */     } 
/*  95 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadMimeTypes() {
/* 105 */     if (!this.loaded) {
/* 106 */       synchronized (this) {
/* 107 */         if (!this.loaded) {
/* 108 */           List list = AccessController.<List>doPrivileged((PrivilegedAction)new PrivilegedAction<List<String>>()
/*     */               {
/*     */                 public List<String> run()
/*     */                 {
/*     */                   try {
/* 113 */                     return Files.readAllLines(MimeTypesFileTypeDetector.this.mimeTypesFile, 
/* 114 */                         Charset.defaultCharset());
/* 115 */                   } catch (IOException iOException) {
/* 116 */                     return Collections.emptyList();
/*     */                   } 
/*     */                 }
/*     */               });
/*     */           
/* 121 */           this.mimeTypeMap = new HashMap<>(list.size());
/* 122 */           String str = "";
/* 123 */           for (String str1 : list) {
/* 124 */             str = str + str1;
/* 125 */             if (str.endsWith("\\")) {
/* 126 */               str = str.substring(0, str.length() - 1);
/*     */               continue;
/*     */             } 
/* 129 */             parseMimeEntry(str);
/* 130 */             str = "";
/*     */           } 
/* 132 */           if (!str.isEmpty()) {
/* 133 */             parseMimeEntry(str);
/*     */           }
/* 135 */           this.loaded = true;
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseMimeEntry(String paramString) {
/* 152 */     paramString = paramString.trim();
/* 153 */     if (paramString.isEmpty() || paramString.charAt(0) == '#') {
/*     */       return;
/*     */     }
/* 156 */     paramString = paramString.replaceAll("\\s*#.*", "");
/* 157 */     int i = paramString.indexOf('=');
/* 158 */     if (i > 0) {
/*     */ 
/*     */       
/* 161 */       String str = "\\btype=(\"\\p{Graph}+?/\\p{Graph}+?\"|\\p{Graph}+/\\p{Graph}+\\b)";
/*     */       
/* 163 */       Pattern pattern = Pattern.compile(str);
/* 164 */       Matcher matcher = pattern.matcher(paramString);
/*     */       
/* 166 */       if (matcher.find()) {
/* 167 */         String str1 = matcher.group().substring("type=".length());
/* 168 */         if (str1.charAt(0) == '"') {
/* 169 */           str1 = str1.substring(1, str1.length() - 1);
/*     */         }
/*     */ 
/*     */         
/* 173 */         String str2 = "\\bexts=(\"[\\p{Graph}\\p{Blank}]+?\"|\\p{Graph}+\\b)";
/*     */         
/* 175 */         Pattern pattern1 = Pattern.compile(str2);
/* 176 */         Matcher matcher1 = pattern1.matcher(paramString);
/*     */         
/* 178 */         if (matcher1.find()) {
/*     */           
/* 180 */           String str3 = matcher1.group().substring("exts=".length());
/* 181 */           if (str3.charAt(0) == '"') {
/* 182 */             str3 = str3.substring(1, str3.length() - 1);
/*     */           }
/* 184 */           String[] arrayOfString = str3.split("[\\p{Blank}\\p{Punct}]+");
/* 185 */           for (String str4 : arrayOfString) {
/* 186 */             putIfAbsent(str4, str1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 192 */       String[] arrayOfString = paramString.split("\\s+");
/* 193 */       byte b = 1;
/* 194 */       while (b < arrayOfString.length) {
/* 195 */         putIfAbsent(arrayOfString[b++], arrayOfString[0]);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void putIfAbsent(String paramString1, String paramString2) {
/* 201 */     if (paramString1 != null && !paramString1.isEmpty() && paramString2 != null && 
/* 202 */       !paramString2.isEmpty() && 
/* 203 */       !this.mimeTypeMap.containsKey(paramString1))
/*     */     {
/* 205 */       this.mimeTypeMap.put(paramString1, paramString2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/MimeTypesFileTypeDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */