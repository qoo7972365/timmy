/*     */ package sun.net.www;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.AccessController;
/*     */ import java.util.StringTokenizer;
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
/*     */ class MimeLauncher
/*     */   extends Thread
/*     */ {
/*     */   URLConnection uc;
/*     */   MimeEntry m;
/*     */   String genericTempFileTemplate;
/*     */   InputStream is;
/*     */   String execPath;
/*     */   
/*     */   MimeLauncher(MimeEntry paramMimeEntry, URLConnection paramURLConnection, InputStream paramInputStream, String paramString1, String paramString2) throws ApplicationLaunchException {
/*  40 */     super(paramString2);
/*  41 */     this.m = paramMimeEntry;
/*  42 */     this.uc = paramURLConnection;
/*  43 */     this.is = paramInputStream;
/*  44 */     this.genericTempFileTemplate = paramString1;
/*     */ 
/*     */     
/*  47 */     String str = this.m.getLaunchString();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     if (!findExecutablePath(str)) {
/*     */       String str1;
/*     */       
/*  55 */       int i = str.indexOf(' ');
/*  56 */       if (i != -1) {
/*  57 */         str1 = str.substring(0, i);
/*     */       } else {
/*     */         
/*  60 */         str1 = str;
/*     */       } 
/*  62 */       throw new ApplicationLaunchException(str1);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getTempFileName(URL paramURL, String paramString) {
/*  67 */     String str1 = paramString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     int i = str1.lastIndexOf("%s");
/*  74 */     String str2 = str1.substring(0, i);
/*     */     
/*  76 */     String str3 = "";
/*  77 */     if (i < str1.length() - 2) {
/*  78 */       str3 = str1.substring(i + 2);
/*     */     }
/*     */     
/*  81 */     long l = System.currentTimeMillis() / 1000L;
/*  82 */     int j = 0;
/*  83 */     while ((j = str2.indexOf("%s")) >= 0)
/*     */     {
/*     */       
/*  86 */       str2 = str2.substring(0, j) + l + str2.substring(j + 2);
/*     */     }
/*     */ 
/*     */     
/*  90 */     String str4 = paramURL.getFile();
/*     */     
/*  92 */     String str5 = "";
/*  93 */     int k = str4.lastIndexOf('.');
/*     */ 
/*     */ 
/*     */     
/*  97 */     if (k >= 0 && k > str4.lastIndexOf('/')) {
/*  98 */       str5 = str4.substring(k);
/*     */     }
/*     */     
/* 101 */     str4 = "HJ" + paramURL.hashCode();
/*     */     
/* 103 */     str1 = str2 + str4 + l + str5 + str3;
/*     */     
/* 105 */     return str1;
/*     */   }
/*     */   
/*     */   public void run() {
/*     */     try {
/* 110 */       String str1 = this.m.getTempFileTemplate();
/* 111 */       if (str1 == null) {
/* 112 */         str1 = this.genericTempFileTemplate;
/*     */       }
/*     */       
/* 115 */       str1 = getTempFileName(this.uc.getURL(), str1);
/*     */       try {
/* 117 */         FileOutputStream fileOutputStream = new FileOutputStream(str1);
/* 118 */         byte[] arrayOfByte = new byte[2048];
/* 119 */         int j = 0;
/*     */         try {
/* 121 */           while ((j = this.is.read(arrayOfByte)) >= 0) {
/* 122 */             fileOutputStream.write(arrayOfByte, 0, j);
/*     */           }
/* 124 */         } catch (IOException iOException) {
/*     */ 
/*     */         
/*     */         } finally {
/* 128 */           fileOutputStream.close();
/* 129 */           this.is.close();
/*     */         } 
/* 131 */       } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 136 */       int i = 0;
/* 137 */       String str2 = this.execPath;
/* 138 */       while ((i = str2.indexOf("%t")) >= 0)
/*     */       {
/* 140 */         str2 = str2.substring(0, i) + this.uc.getContentType() + str2.substring(i + 2);
/*     */       }
/*     */       
/* 143 */       boolean bool = false;
/* 144 */       while ((i = str2.indexOf("%s")) >= 0) {
/* 145 */         str2 = str2.substring(0, i) + str1 + str2.substring(i + 2);
/* 146 */         bool = true;
/*     */       } 
/* 148 */       if (!bool) {
/* 149 */         str2 = str2 + " <" + str1;
/*     */       }
/*     */ 
/*     */       
/* 153 */       Runtime.getRuntime().exec(str2);
/* 154 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean findExecutablePath(String paramString) {
/*     */     String str1;
/* 164 */     if (paramString == null || paramString.length() == 0) {
/* 165 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 169 */     int i = paramString.indexOf(' ');
/* 170 */     if (i != -1) {
/* 171 */       str1 = paramString.substring(0, i);
/*     */     } else {
/*     */       
/* 174 */       str1 = paramString;
/*     */     } 
/*     */     
/* 177 */     File file = new File(str1);
/* 178 */     if (file.isFile()) {
/*     */       
/* 180 */       this.execPath = paramString;
/* 181 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 185 */     String str2 = AccessController.<String>doPrivileged(new GetPropertyAction("exec.path"));
/*     */     
/* 187 */     if (str2 == null)
/*     */     {
/* 189 */       return false;
/*     */     }
/*     */     
/* 192 */     StringTokenizer stringTokenizer = new StringTokenizer(str2, "|");
/* 193 */     while (stringTokenizer.hasMoreElements()) {
/* 194 */       String str3 = (String)stringTokenizer.nextElement();
/* 195 */       String str4 = str3 + File.separator + str1;
/* 196 */       file = new File(str4);
/* 197 */       if (file.isFile()) {
/* 198 */         this.execPath = str3 + File.separator + paramString;
/* 199 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/MimeLauncher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */