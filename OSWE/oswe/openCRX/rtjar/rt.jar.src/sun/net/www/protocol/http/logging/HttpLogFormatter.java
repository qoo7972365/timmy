/*     */ package sun.net.www.protocol.http.logging;
/*     */ 
/*     */ import java.util.logging.LogRecord;
/*     */ import java.util.logging.SimpleFormatter;
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
/*     */ public class HttpLogFormatter
/*     */   extends SimpleFormatter
/*     */ {
/*  39 */   private static volatile Pattern pattern = null;
/*     */   
/*  41 */   private static volatile Pattern cpattern = null;
/*     */   
/*     */   public HttpLogFormatter() {
/*  44 */     if (pattern == null) {
/*  45 */       pattern = Pattern.compile("\\{[^\\}]*\\}");
/*  46 */       cpattern = Pattern.compile("[^,\\] ]{2,}");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String format(LogRecord paramLogRecord) {
/*  52 */     String str1 = paramLogRecord.getSourceClassName();
/*  53 */     if (str1 == null || (
/*  54 */       !str1.startsWith("sun.net.www.protocol.http") && 
/*  55 */       !str1.startsWith("sun.net.www.http"))) {
/*  56 */       return super.format(paramLogRecord);
/*     */     }
/*  58 */     String str2 = paramLogRecord.getMessage();
/*  59 */     StringBuilder stringBuilder = new StringBuilder("HTTP: ");
/*  60 */     if (str2.startsWith("sun.net.www.MessageHeader@")) {
/*     */ 
/*     */ 
/*     */       
/*  64 */       Matcher matcher = pattern.matcher(str2);
/*  65 */       while (matcher.find()) {
/*  66 */         int i = matcher.start();
/*  67 */         int j = matcher.end();
/*  68 */         String str = str2.substring(i + 1, j - 1);
/*  69 */         if (str.startsWith("null: ")) {
/*  70 */           str = str.substring(6);
/*     */         }
/*  72 */         if (str.endsWith(": null")) {
/*  73 */           str = str.substring(0, str.length() - 6);
/*     */         }
/*  75 */         stringBuilder.append("\t").append(str).append("\n");
/*     */       } 
/*  77 */     } else if (str2.startsWith("Cookies retrieved: {")) {
/*     */       
/*  79 */       String str = str2.substring(20);
/*  80 */       stringBuilder.append("Cookies from handler:\n");
/*  81 */       while (str.length() >= 7) {
/*  82 */         if (str.startsWith("Cookie=[")) {
/*  83 */           String str3 = str.substring(8);
/*  84 */           int i = str3.indexOf("Cookie2=[");
/*  85 */           if (i > 0) {
/*  86 */             str3 = str3.substring(0, i - 1);
/*  87 */             str = str3.substring(i);
/*     */           } else {
/*  89 */             str = "";
/*     */           } 
/*  91 */           if (str3.length() < 4) {
/*     */             continue;
/*     */           }
/*  94 */           Matcher matcher = cpattern.matcher(str3);
/*  95 */           while (matcher.find()) {
/*  96 */             int j = matcher.start();
/*  97 */             int k = matcher.end();
/*  98 */             if (j >= 0) {
/*  99 */               String str4 = str3.substring(j + 1, (k > 0) ? (k - 1) : (str3.length() - 1));
/* 100 */               stringBuilder.append("\t").append(str4).append("\n");
/*     */             } 
/*     */           } 
/*     */         } 
/* 104 */         if (str.startsWith("Cookie2=[")) {
/* 105 */           String str3 = str.substring(9);
/* 106 */           int i = str3.indexOf("Cookie=[");
/* 107 */           if (i > 0) {
/* 108 */             str3 = str3.substring(0, i - 1);
/* 109 */             str = str3.substring(i);
/*     */           } else {
/* 111 */             str = "";
/*     */           } 
/* 113 */           Matcher matcher = cpattern.matcher(str3);
/* 114 */           while (matcher.find()) {
/* 115 */             int j = matcher.start();
/* 116 */             int k = matcher.end();
/* 117 */             if (j >= 0) {
/* 118 */               String str4 = str3.substring(j + 1, (k > 0) ? (k - 1) : (str3.length() - 1));
/* 119 */               stringBuilder.append("\t").append(str4).append("\n");
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 126 */       stringBuilder.append(str2).append("\n");
/*     */     } 
/* 128 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/http/logging/HttpLogFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */