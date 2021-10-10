/*     */ package javax.management.loading;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MLetParser
/*     */ {
/*     */   private int c;
/*  64 */   private static String tag = "mlet";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skipSpace(Reader paramReader) throws IOException {
/*  89 */     while (this.c >= 0 && (this.c == 32 || this.c == 9 || this.c == 10 || this.c == 13)) {
/*  90 */       this.c = paramReader.read();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String scanIdentifier(Reader paramReader) throws IOException {
/*  98 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 100 */     while ((this.c >= 97 && this.c <= 122) || (this.c >= 65 && this.c <= 90) || (this.c >= 48 && this.c <= 57) || this.c == 95) {
/*     */ 
/*     */       
/* 103 */       stringBuilder.append((char)this.c);
/* 104 */       this.c = paramReader.read();
/*     */     } 
/* 106 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> scanTag(Reader paramReader) throws IOException {
/* 115 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 116 */     skipSpace(paramReader);
/* 117 */     while (this.c >= 0 && this.c != 62) {
/* 118 */       if (this.c == 60)
/* 119 */         throw new IOException("Missing '>' in tag"); 
/* 120 */       String str1 = scanIdentifier(paramReader);
/* 121 */       String str2 = "";
/* 122 */       skipSpace(paramReader);
/* 123 */       if (this.c == 61) {
/* 124 */         int i = -1;
/* 125 */         this.c = paramReader.read();
/* 126 */         skipSpace(paramReader);
/* 127 */         if (this.c == 39 || this.c == 34) {
/* 128 */           i = this.c;
/* 129 */           this.c = paramReader.read();
/*     */         } 
/* 131 */         StringBuilder stringBuilder = new StringBuilder();
/* 132 */         while (this.c > 0 && ((i < 0 && this.c != 32 && this.c != 9 && this.c != 10 && this.c != 13 && this.c != 62) || (i >= 0 && this.c != i))) {
/*     */ 
/*     */ 
/*     */           
/* 136 */           stringBuilder.append((char)this.c);
/* 137 */           this.c = paramReader.read();
/*     */         } 
/* 139 */         if (this.c == i) {
/* 140 */           this.c = paramReader.read();
/*     */         }
/* 142 */         skipSpace(paramReader);
/* 143 */         str2 = stringBuilder.toString();
/*     */       } 
/* 145 */       hashMap.put(str1.toLowerCase(), str2);
/* 146 */       skipSpace(paramReader);
/*     */     } 
/* 148 */     return (Map)hashMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<MLetContent> parse(URL paramURL) throws IOException {
/* 155 */     String str1 = "parse";
/*     */     
/* 157 */     String str2 = "<arg type=... value=...> tag requires type parameter.";
/* 158 */     String str3 = "<arg type=... value=...> tag requires value parameter.";
/* 159 */     String str4 = "<arg> tag outside <mlet> ... </mlet>.";
/* 160 */     String str5 = "<mlet> tag requires either code or object parameter.";
/* 161 */     String str6 = "<mlet> tag requires archive parameter.";
/*     */ 
/*     */ 
/*     */     
/* 165 */     URLConnection uRLConnection = paramURL.openConnection();
/* 166 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRLConnection.getInputStream(), "UTF-8"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     paramURL = uRLConnection.getURL();
/*     */     
/* 174 */     ArrayList<MLetContent> arrayList = new ArrayList();
/* 175 */     Map<String, String> map = null;
/*     */     
/* 177 */     ArrayList<String> arrayList1 = new ArrayList();
/* 178 */     ArrayList<String> arrayList2 = new ArrayList();
/*     */ 
/*     */     
/*     */     while (true) {
/* 182 */       this.c = bufferedReader.read();
/* 183 */       if (this.c == -1)
/*     */         break; 
/* 185 */       if (this.c == 60) {
/* 186 */         this.c = bufferedReader.read();
/* 187 */         if (this.c == 47) {
/* 188 */           this.c = bufferedReader.read();
/* 189 */           String str7 = scanIdentifier(bufferedReader);
/* 190 */           if (this.c != 62)
/* 191 */             throw new IOException("Missing '>' in tag"); 
/* 192 */           if (str7.equalsIgnoreCase(tag)) {
/* 193 */             if (map != null) {
/* 194 */               arrayList.add(new MLetContent(paramURL, map, arrayList1, arrayList2));
/*     */             }
/* 196 */             map = null;
/* 197 */             arrayList1 = new ArrayList<>();
/* 198 */             arrayList2 = new ArrayList<>();
/*     */           }  continue;
/*     */         } 
/* 201 */         String str = scanIdentifier(bufferedReader);
/* 202 */         if (str.equalsIgnoreCase("arg")) {
/* 203 */           Map<String, String> map1 = scanTag(bufferedReader);
/* 204 */           String str7 = map1.get("type");
/* 205 */           if (str7 == null) {
/* 206 */             JmxProperties.MLET_LOGGER.logp(Level.FINER, MLetParser.class
/* 207 */                 .getName(), str1, str2);
/*     */             
/* 209 */             throw new IOException(str2);
/*     */           } 
/* 211 */           if (map != null) {
/* 212 */             arrayList1.add(str7);
/*     */           } else {
/* 214 */             JmxProperties.MLET_LOGGER.logp(Level.FINER, MLetParser.class
/* 215 */                 .getName(), str1, str4);
/*     */             
/* 217 */             throw new IOException(str4);
/*     */           } 
/*     */           
/* 220 */           String str8 = map1.get("value");
/* 221 */           if (str8 == null) {
/* 222 */             JmxProperties.MLET_LOGGER.logp(Level.FINER, MLetParser.class
/* 223 */                 .getName(), str1, str3);
/*     */             
/* 225 */             throw new IOException(str3);
/*     */           } 
/* 227 */           if (map != null) {
/* 228 */             arrayList2.add(str8); continue;
/*     */           } 
/* 230 */           JmxProperties.MLET_LOGGER.logp(Level.FINER, MLetParser.class
/* 231 */               .getName(), str1, str4);
/*     */           
/* 233 */           throw new IOException(str4);
/*     */         } 
/*     */ 
/*     */         
/* 237 */         if (str.equalsIgnoreCase(tag)) {
/* 238 */           map = scanTag(bufferedReader);
/* 239 */           if (map.get("code") == null && map.get("object") == null) {
/* 240 */             JmxProperties.MLET_LOGGER.logp(Level.FINER, MLetParser.class
/* 241 */                 .getName(), str1, str5);
/*     */             
/* 243 */             throw new IOException(str5);
/*     */           } 
/* 245 */           if (map.get("archive") == null) {
/* 246 */             JmxProperties.MLET_LOGGER.logp(Level.FINER, MLetParser.class
/* 247 */                 .getName(), str1, str6);
/*     */             
/* 249 */             throw new IOException(str6);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 256 */     bufferedReader.close();
/* 257 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<MLetContent> parseURL(String paramString) throws IOException {
/*     */     URL uRL;
/* 267 */     if (paramString.indexOf(':') <= 1) {
/* 268 */       String str2, str1 = System.getProperty("user.dir");
/*     */       
/* 270 */       if (str1.charAt(0) == '/' || str1
/* 271 */         .charAt(0) == File.separatorChar) {
/* 272 */         str2 = "file:";
/*     */       } else {
/* 274 */         str2 = "file:/";
/*     */       } 
/*     */       
/* 277 */       uRL = new URL(str2 + str1.replace(File.separatorChar, '/') + "/");
/* 278 */       uRL = new URL(uRL, paramString);
/*     */     } else {
/* 280 */       uRL = new URL(paramString);
/*     */     } 
/*     */ 
/*     */     
/* 284 */     return parse(uRL);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/loading/MLetParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */