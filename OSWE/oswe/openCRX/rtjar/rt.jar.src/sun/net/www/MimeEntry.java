/*     */ package sun.net.www;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.net.URLConnection;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MimeEntry
/*     */   implements Cloneable
/*     */ {
/*     */   private String typeName;
/*     */   private String tempFileNameTemplate;
/*     */   private int action;
/*     */   private String command;
/*     */   private String description;
/*     */   private String imageFileName;
/*     */   private String[] fileExtensions;
/*     */   boolean starred;
/*     */   public static final int UNKNOWN = 0;
/*     */   public static final int LOAD_INTO_BROWSER = 1;
/*     */   public static final int SAVE_TO_FILE = 2;
/*     */   public static final int LAUNCH_APPLICATION = 3;
/*  49 */   static final String[] actionKeywords = new String[] { "unknown", "browser", "save", "application" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MimeEntry(String paramString) {
/*  62 */     this(paramString, 0, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MimeEntry(String paramString1, String paramString2, String paramString3) {
/*  73 */     this.typeName = paramString1.toLowerCase();
/*  74 */     this.action = 0;
/*  75 */     this.command = null;
/*  76 */     this.imageFileName = paramString2;
/*  77 */     setExtensions(paramString3);
/*  78 */     this.starred = isStarred(this.typeName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   MimeEntry(String paramString1, int paramInt, String paramString2, String paramString3) {
/*  84 */     this.typeName = paramString1.toLowerCase();
/*  85 */     this.action = paramInt;
/*  86 */     this.command = paramString2;
/*  87 */     this.imageFileName = null;
/*  88 */     this.fileExtensions = null;
/*     */     
/*  90 */     this.tempFileNameTemplate = paramString3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MimeEntry(String paramString1, int paramInt, String paramString2, String paramString3, String[] paramArrayOfString) {
/*  97 */     this.typeName = paramString1.toLowerCase();
/*  98 */     this.action = paramInt;
/*  99 */     this.command = paramString2;
/* 100 */     this.imageFileName = paramString3;
/* 101 */     this.fileExtensions = paramArrayOfString;
/*     */     
/* 103 */     this.starred = isStarred(paramString1);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized String getType() {
/* 108 */     return this.typeName;
/*     */   }
/*     */   
/*     */   public synchronized void setType(String paramString) {
/* 112 */     this.typeName = paramString.toLowerCase();
/*     */   }
/*     */   
/*     */   public synchronized int getAction() {
/* 116 */     return this.action;
/*     */   }
/*     */   
/*     */   public synchronized void setAction(int paramInt, String paramString) {
/* 120 */     this.action = paramInt;
/* 121 */     this.command = paramString;
/*     */   }
/*     */   
/*     */   public synchronized void setAction(int paramInt) {
/* 125 */     this.action = paramInt;
/*     */   }
/*     */   
/*     */   public synchronized String getLaunchString() {
/* 129 */     return this.command;
/*     */   }
/*     */   
/*     */   public synchronized void setCommand(String paramString) {
/* 133 */     this.command = paramString;
/*     */   }
/*     */   
/*     */   public synchronized String getDescription() {
/* 137 */     return (this.description != null) ? this.description : this.typeName;
/*     */   }
/*     */   
/*     */   public synchronized void setDescription(String paramString) {
/* 141 */     this.description = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getImageFileName() {
/* 149 */     return this.imageFileName;
/*     */   }
/*     */   
/*     */   public synchronized void setImageFileName(String paramString) {
/* 153 */     File file = new File(paramString);
/* 154 */     if (file.getParent() == null) {
/* 155 */       this.imageFileName = System.getProperty("java.net.ftp.imagepath." + paramString);
/*     */     }
/*     */     else {
/*     */       
/* 159 */       this.imageFileName = paramString;
/*     */     } 
/*     */     
/* 162 */     if (paramString.lastIndexOf('.') < 0) {
/* 163 */       this.imageFileName += ".gif";
/*     */     }
/*     */   }
/*     */   
/*     */   public String getTempFileTemplate() {
/* 168 */     return this.tempFileNameTemplate;
/*     */   }
/*     */   
/*     */   public synchronized String[] getExtensions() {
/* 172 */     return this.fileExtensions;
/*     */   }
/*     */   
/*     */   public synchronized String getExtensionsAsList() {
/* 176 */     String str = "";
/* 177 */     if (this.fileExtensions != null) {
/* 178 */       for (byte b = 0; b < this.fileExtensions.length; b++) {
/* 179 */         str = str + this.fileExtensions[b];
/* 180 */         if (b < this.fileExtensions.length - 1) {
/* 181 */           str = str + ",";
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 186 */     return str;
/*     */   }
/*     */   
/*     */   public synchronized void setExtensions(String paramString) {
/* 190 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, ",");
/* 191 */     int i = stringTokenizer.countTokens();
/* 192 */     String[] arrayOfString = new String[i];
/*     */     
/* 194 */     for (byte b = 0; b < i; b++) {
/* 195 */       String str = (String)stringTokenizer.nextElement();
/* 196 */       arrayOfString[b] = str.trim();
/*     */     } 
/*     */     
/* 199 */     this.fileExtensions = arrayOfString;
/*     */   }
/*     */   
/*     */   private boolean isStarred(String paramString) {
/* 203 */     return (paramString != null && paramString
/* 204 */       .length() > 0 && paramString
/* 205 */       .endsWith("/*"));
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
/*     */   public Object launch(URLConnection paramURLConnection, InputStream paramInputStream, MimeTable paramMimeTable) throws ApplicationLaunchException {
/*     */     String str;
/*     */     int i;
/* 220 */     switch (this.action) {
/*     */       
/*     */       case 2:
/*     */         try {
/* 224 */           return paramInputStream;
/* 225 */         } catch (Exception exception) {
/*     */           
/* 227 */           return "Load to file failed:\n" + exception;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/*     */         try {
/* 236 */           return paramURLConnection.getContent();
/* 237 */         } catch (Exception exception) {
/* 238 */           return null;
/*     */         } 
/*     */ 
/*     */       
/*     */       case 3:
/* 243 */         str = this.command;
/* 244 */         i = str.indexOf(' ');
/* 245 */         if (i > 0) {
/* 246 */           str = str.substring(0, i);
/*     */         }
/*     */         
/* 249 */         return new MimeLauncher(this, paramURLConnection, paramInputStream, paramMimeTable
/* 250 */             .getTempFileTemplate(), str);
/*     */ 
/*     */ 
/*     */       
/*     */       case 0:
/* 255 */         return null;
/*     */     } 
/*     */     
/* 258 */     return null;
/*     */   }
/*     */   
/*     */   public boolean matches(String paramString) {
/* 262 */     if (this.starred)
/*     */     {
/* 264 */       return paramString.startsWith(this.typeName);
/*     */     }
/* 266 */     return paramString.equals(this.typeName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 272 */     MimeEntry mimeEntry = new MimeEntry(this.typeName);
/* 273 */     mimeEntry.action = this.action;
/* 274 */     mimeEntry.command = this.command;
/* 275 */     mimeEntry.description = this.description;
/* 276 */     mimeEntry.imageFileName = this.imageFileName;
/* 277 */     mimeEntry.tempFileNameTemplate = this.tempFileNameTemplate;
/* 278 */     mimeEntry.fileExtensions = this.fileExtensions;
/*     */     
/* 280 */     return mimeEntry;
/*     */   }
/*     */   
/*     */   public synchronized String toProperty() {
/* 284 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 286 */     String str1 = "; ";
/* 287 */     boolean bool = false;
/*     */     
/* 289 */     int i = getAction();
/* 290 */     if (i != 0) {
/* 291 */       stringBuffer.append("action=" + actionKeywords[i]);
/* 292 */       bool = true;
/*     */     } 
/*     */     
/* 295 */     String str2 = getLaunchString();
/* 296 */     if (str2 != null && str2.length() > 0) {
/* 297 */       if (bool) {
/* 298 */         stringBuffer.append(str1);
/*     */       }
/* 300 */       stringBuffer.append("application=" + str2);
/* 301 */       bool = true;
/*     */     } 
/*     */     
/* 304 */     if (getImageFileName() != null) {
/* 305 */       if (bool) {
/* 306 */         stringBuffer.append(str1);
/*     */       }
/* 308 */       stringBuffer.append("icon=" + getImageFileName());
/* 309 */       bool = true;
/*     */     } 
/*     */     
/* 312 */     String str3 = getExtensionsAsList();
/* 313 */     if (str3.length() > 0) {
/* 314 */       if (bool) {
/* 315 */         stringBuffer.append(str1);
/*     */       }
/* 317 */       stringBuffer.append("file_extensions=" + str3);
/* 318 */       bool = true;
/*     */     } 
/*     */     
/* 321 */     String str4 = getDescription();
/* 322 */     if (str4 != null && !str4.equals(getType())) {
/* 323 */       if (bool) {
/* 324 */         stringBuffer.append(str1);
/*     */       }
/* 326 */       stringBuffer.append("description=" + str4);
/*     */     } 
/*     */     
/* 329 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 333 */     return "MimeEntry[contentType=" + this.typeName + ", image=" + this.imageFileName + ", action=" + this.action + ", command=" + this.command + ", extensions=" + 
/*     */ 
/*     */ 
/*     */       
/* 337 */       getExtensionsAsList() + "]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/MimeEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */