/*     */ package sun.net.www.http;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import java.util.regex.Pattern;
/*     */ import sun.net.NetProperties;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpCapture
/*     */ {
/*  57 */   private File file = null;
/*     */   private boolean incoming = true;
/*  59 */   private BufferedWriter out = null;
/*     */   private static boolean initialized = false;
/*  61 */   private static volatile ArrayList<Pattern> patterns = null;
/*  62 */   private static volatile ArrayList<String> capFiles = null;
/*     */   
/*     */   private static synchronized void init() {
/*  65 */     initialized = true;
/*  66 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run() {
/*  69 */             return NetProperties.get("sun.net.http.captureRules");
/*     */           }
/*     */         });
/*  72 */     if (str != null && !str.isEmpty()) {
/*     */       BufferedReader bufferedReader;
/*     */       try {
/*  75 */         bufferedReader = new BufferedReader(new FileReader(str));
/*  76 */       } catch (FileNotFoundException fileNotFoundException) {
/*     */         return;
/*     */       } 
/*     */       
/*  80 */       try { String str1 = bufferedReader.readLine();
/*  81 */         while (str1 != null) {
/*  82 */           str1 = str1.trim();
/*  83 */           if (!str1.startsWith("#")) {
/*     */             
/*  85 */             String[] arrayOfString = str1.split(",");
/*  86 */             if (arrayOfString.length == 2) {
/*  87 */               if (patterns == null) {
/*  88 */                 patterns = new ArrayList<>();
/*  89 */                 capFiles = new ArrayList<>();
/*     */               } 
/*  91 */               patterns.add(Pattern.compile(arrayOfString[0].trim()));
/*  92 */               capFiles.add(arrayOfString[1].trim());
/*     */             } 
/*     */           } 
/*  95 */           str1 = bufferedReader.readLine();
/*     */         }  }
/*  97 */       catch (IOException iOException)
/*     */       
/*     */       { 
/*     */         try {
/* 101 */           bufferedReader.close();
/* 102 */         } catch (IOException iOException1) {} } finally { try { bufferedReader.close(); } catch (IOException iOException) {} }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static synchronized boolean isInitialized() {
/* 109 */     return initialized;
/*     */   }
/*     */   
/*     */   private HttpCapture(File paramFile, URL paramURL) {
/* 113 */     this.file = paramFile;
/*     */     try {
/* 115 */       this.out = new BufferedWriter(new FileWriter(this.file, true));
/* 116 */       this.out.write("URL: " + paramURL + "\n");
/* 117 */     } catch (IOException iOException) {
/* 118 */       PlatformLogger.getLogger(HttpCapture.class.getName()).severe((String)null, iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void sent(int paramInt) throws IOException {
/* 123 */     if (this.incoming) {
/* 124 */       this.out.write("\n------>\n");
/* 125 */       this.incoming = false;
/* 126 */       this.out.flush();
/*     */     } 
/* 128 */     this.out.write(paramInt);
/*     */   }
/*     */   
/*     */   public synchronized void received(int paramInt) throws IOException {
/* 132 */     if (!this.incoming) {
/* 133 */       this.out.write("\n<------\n");
/* 134 */       this.incoming = true;
/* 135 */       this.out.flush();
/*     */     } 
/* 137 */     this.out.write(paramInt);
/*     */   }
/*     */   
/*     */   public synchronized void flush() throws IOException {
/* 141 */     this.out.flush();
/*     */   }
/*     */   
/*     */   public static HttpCapture getCapture(URL paramURL) {
/* 145 */     if (!isInitialized()) {
/* 146 */       init();
/*     */     }
/* 148 */     if (patterns == null || patterns.isEmpty()) {
/* 149 */       return null;
/*     */     }
/* 151 */     String str = paramURL.toString();
/* 152 */     for (byte b = 0; b < patterns.size(); b++) {
/* 153 */       Pattern pattern = patterns.get(b);
/* 154 */       if (pattern.matcher(str).find()) {
/* 155 */         File file; String str1 = capFiles.get(b);
/*     */         
/* 157 */         if (str1.indexOf("%d") >= 0) {
/* 158 */           Random random = new Random();
/*     */           do {
/* 160 */             String str2 = str1.replace("%d", Integer.toString(random.nextInt()));
/* 161 */             file = new File(str2);
/* 162 */           } while (file.exists());
/*     */         } else {
/* 164 */           file = new File(str1);
/*     */         } 
/* 166 */         return new HttpCapture(file, paramURL);
/*     */       } 
/*     */     } 
/* 169 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/http/HttpCapture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */