/*     */ package sun.print;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import javax.print.attribute.standard.MediaPrintableArea;
/*     */ import javax.print.attribute.standard.MediaSize;
/*     */ import javax.print.attribute.standard.MediaSizeName;
/*     */ import javax.print.attribute.standard.MediaTray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CUPSPrinter
/*     */ {
/*     */   private static final String debugPrefix = "CUPSPrinter>> ";
/*     */   private static final double PRINTER_DPI = 72.0D;
/*     */   private boolean initialized;
/*     */   private MediaPrintableArea[] cupsMediaPrintables;
/*     */   private MediaSizeName[] cupsMediaSNames;
/*     */   private CustomMediaSizeName[] cupsCustomMediaSNames;
/*     */   private MediaTray[] cupsMediaTrays;
/*  68 */   public int nPageSizes = 0;
/*  69 */   public int nTrays = 0;
/*     */   
/*     */   private String[] media;
/*     */   private float[] pageSizes;
/*     */   private String printer;
/*     */   private static boolean libFound;
/*  75 */   private static String cupsServer = null;
/*  76 */   private static int cupsPort = 0;
/*     */ 
/*     */   
/*     */   static {
/*  80 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/*  83 */             System.loadLibrary("awt");
/*  84 */             return null;
/*     */           }
/*     */         });
/*  87 */     libFound = initIDs();
/*  88 */     if (libFound) {
/*  89 */       cupsServer = getCupsServer();
/*  90 */       cupsPort = getCupsPort();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   CUPSPrinter(String paramString) {
/*  96 */     if (paramString == null) {
/*  97 */       throw new IllegalArgumentException("null printer name");
/*     */     }
/*  99 */     this.printer = paramString;
/* 100 */     this.cupsMediaSNames = null;
/* 101 */     this.cupsMediaPrintables = null;
/* 102 */     this.cupsMediaTrays = null;
/* 103 */     this.initialized = false;
/*     */     
/* 105 */     if (!libFound) {
/* 106 */       throw new RuntimeException("cups lib not found");
/*     */     }
/*     */     
/* 109 */     this.media = getMedia(this.printer);
/* 110 */     if (this.media == null)
/*     */     {
/* 112 */       throw new RuntimeException("error getting PPD");
/*     */     }
/*     */ 
/*     */     
/* 116 */     this.pageSizes = getPageSizes(this.printer);
/* 117 */     if (this.pageSizes != null) {
/* 118 */       this.nPageSizes = this.pageSizes.length / 6;
/*     */       
/* 120 */       this.nTrays = this.media.length / 2 - this.nPageSizes;
/* 121 */       assert this.nTrays >= 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MediaSizeName[] getMediaSizeNames() {
/* 131 */     initMedia();
/* 132 */     return this.cupsMediaSNames;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CustomMediaSizeName[] getCustomMediaSizeNames() {
/* 140 */     initMedia();
/* 141 */     return this.cupsCustomMediaSNames;
/*     */   }
/*     */   
/*     */   public int getDefaultMediaIndex() {
/* 145 */     return (this.pageSizes.length > 1) ? (int)this.pageSizes[this.pageSizes.length - 1] : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MediaPrintableArea[] getMediaPrintableArea() {
/* 152 */     initMedia();
/* 153 */     return this.cupsMediaPrintables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MediaTray[] getMediaTrays() {
/* 160 */     initMedia();
/* 161 */     return this.cupsMediaTrays;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void initMedia() {
/* 169 */     if (this.initialized) {
/*     */       return;
/*     */     }
/* 172 */     this.initialized = true;
/*     */ 
/*     */     
/* 175 */     if (this.pageSizes == null) {
/*     */       return;
/*     */     }
/*     */     
/* 179 */     this.cupsMediaPrintables = new MediaPrintableArea[this.nPageSizes];
/* 180 */     this.cupsMediaSNames = new MediaSizeName[this.nPageSizes];
/* 181 */     this.cupsCustomMediaSNames = new CustomMediaSizeName[this.nPageSizes];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     for (byte b1 = 0; b1 < this.nPageSizes; b1++) {
/*     */       
/* 190 */       float f2 = (float)(this.pageSizes[b1 * 6] / 72.0D);
/* 191 */       float f1 = (float)(this.pageSizes[b1 * 6 + 1] / 72.0D);
/*     */       
/* 193 */       float f3 = (float)(this.pageSizes[b1 * 6 + 2] / 72.0D);
/* 194 */       float f6 = (float)(this.pageSizes[b1 * 6 + 3] / 72.0D);
/* 195 */       float f5 = (float)(this.pageSizes[b1 * 6 + 4] / 72.0D);
/* 196 */       float f4 = (float)(this.pageSizes[b1 * 6 + 5] / 72.0D);
/*     */       
/* 198 */       CustomMediaSizeName customMediaSizeName = new CustomMediaSizeName(this.media[b1 * 2], this.media[b1 * 2 + 1], f2, f1);
/*     */ 
/*     */ 
/*     */       
/* 202 */       this.cupsMediaSNames[b1] = customMediaSizeName.getStandardMedia(); if (customMediaSizeName.getStandardMedia() == null) {
/*     */         
/* 204 */         this.cupsMediaSNames[b1] = customMediaSizeName;
/*     */ 
/*     */         
/* 207 */         if (f2 > 0.0D && f1 > 0.0D) {
/*     */           try {
/* 209 */             new MediaSize(f2, f1, 25400, customMediaSizeName);
/*     */           }
/* 211 */           catch (IllegalArgumentException illegalArgumentException) {
/*     */ 
/*     */ 
/*     */             
/* 215 */             new MediaSize(f1, f2, 25400, customMediaSizeName);
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 222 */       this.cupsCustomMediaSNames[b1] = customMediaSizeName;
/*     */       
/* 224 */       MediaPrintableArea mediaPrintableArea = null;
/*     */       try {
/* 226 */         mediaPrintableArea = new MediaPrintableArea(f3, f4, f5, f6, 25400);
/*     */       }
/* 228 */       catch (IllegalArgumentException illegalArgumentException) {
/* 229 */         if (f2 > 0.0F && f1 > 0.0F) {
/* 230 */           mediaPrintableArea = new MediaPrintableArea(0.0F, 0.0F, f2, f1, 25400);
/*     */         }
/*     */       } 
/*     */       
/* 234 */       this.cupsMediaPrintables[b1] = mediaPrintableArea;
/*     */     } 
/*     */ 
/*     */     
/* 238 */     this.cupsMediaTrays = new MediaTray[this.nTrays];
/*     */ 
/*     */     
/* 241 */     for (byte b2 = 0; b2 < this.nTrays; b2++) {
/* 242 */       CustomMediaTray customMediaTray = new CustomMediaTray(this.media[(this.nPageSizes + b2) * 2], this.media[(this.nPageSizes + b2) * 2 + 1]);
/*     */       
/* 244 */       this.cupsMediaTrays[b2] = customMediaTray;
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
/*     */   static String[] getDefaultPrinter() {
/* 256 */     String[] arrayOfString = new String[2];
/* 257 */     arrayOfString[0] = getCupsDefaultPrinter();
/*     */     
/* 259 */     if (arrayOfString[0] != null) {
/* 260 */       arrayOfString[1] = null;
/* 261 */       return (String[])arrayOfString.clone();
/*     */     } 
/*     */     try {
/* 264 */       URL uRL = new URL("http", getServer(), getPort(), "");
/*     */       
/* 266 */       final HttpURLConnection urlConnection = IPPPrintService.getIPPConnection(uRL);
/*     */       
/* 268 */       if (httpURLConnection != null) {
/*     */         
/* 270 */         OutputStream outputStream = AccessController.<OutputStream>doPrivileged(new PrivilegedAction<OutputStream>() {
/*     */               public Object run() {
/*     */                 try {
/* 273 */                   return urlConnection.getOutputStream();
/* 274 */                 } catch (Exception exception) {
/* 275 */                   IPPPrintService.debug_println("CUPSPrinter>> " + exception);
/*     */                   
/* 277 */                   return null;
/*     */                 } 
/*     */               }
/*     */             });
/* 281 */         if (outputStream == null) {
/* 282 */           return null;
/*     */         }
/*     */         
/* 285 */         AttributeClass[] arrayOfAttributeClass = { AttributeClass.ATTRIBUTES_CHARSET, AttributeClass.ATTRIBUTES_NATURAL_LANGUAGE, new AttributeClass("requested-attributes", 69, "printer-uri") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 293 */         if (IPPPrintService.writeIPPRequest(outputStream, "4001", arrayOfAttributeClass)) {
/*     */ 
/*     */ 
/*     */           
/* 297 */           HashMap hashMap = null;
/*     */           
/* 299 */           InputStream inputStream = httpURLConnection.getInputStream();
/* 300 */           HashMap[] arrayOfHashMap = IPPPrintService.readIPPResponse(inputStream);
/*     */           
/* 302 */           inputStream.close();
/*     */           
/* 304 */           if (arrayOfHashMap != null && arrayOfHashMap.length > 0) {
/* 305 */             hashMap = arrayOfHashMap[0];
/*     */           } else {
/* 307 */             IPPPrintService.debug_println("CUPSPrinter>>  empty response map for GET_DEFAULT.");
/*     */           } 
/*     */ 
/*     */           
/* 311 */           if (hashMap == null) {
/* 312 */             outputStream.close();
/* 313 */             httpURLConnection.disconnect();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 321 */             if (PrintServiceLookupProvider.isMac()) {
/* 322 */               arrayOfString[0] = 
/* 323 */                 PrintServiceLookupProvider.getDefaultPrinterNameSysV();
/* 324 */               arrayOfString[1] = null;
/* 325 */               return (String[])arrayOfString.clone();
/*     */             } 
/* 327 */             return null;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 333 */           AttributeClass attributeClass = (AttributeClass)hashMap.get("printer-name");
/*     */           
/* 335 */           if (attributeClass != null) {
/* 336 */             arrayOfString[0] = attributeClass.getStringValue();
/*     */             
/* 338 */             attributeClass = (AttributeClass)hashMap.get("printer-uri-supported");
/* 339 */             IPPPrintService.debug_println("CUPSPrinter>> printer-uri-supported=" + attributeClass);
/*     */             
/* 341 */             if (attributeClass != null) {
/* 342 */               arrayOfString[1] = attributeClass.getStringValue();
/*     */             } else {
/* 344 */               arrayOfString[1] = null;
/*     */             } 
/* 346 */             outputStream.close();
/* 347 */             httpURLConnection.disconnect();
/* 348 */             return (String[])arrayOfString.clone();
/*     */           } 
/*     */         } 
/* 351 */         outputStream.close();
/* 352 */         httpURLConnection.disconnect();
/*     */       } 
/* 354 */     } catch (Exception exception) {}
/*     */     
/* 356 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String[] getAllPrinters() {
/*     */     try {
/* 365 */       URL uRL = new URL("http", getServer(), getPort(), "");
/*     */ 
/*     */       
/* 368 */       final HttpURLConnection urlConnection = IPPPrintService.getIPPConnection(uRL);
/*     */       
/* 370 */       if (httpURLConnection != null)
/*     */       {
/* 372 */         OutputStream outputStream = AccessController.<OutputStream>doPrivileged(new PrivilegedAction<OutputStream>() {
/*     */               public Object run() {
/*     */                 try {
/* 375 */                   return urlConnection.getOutputStream();
/* 376 */                 } catch (Exception exception) {
/*     */                   
/* 378 */                   return null;
/*     */                 } 
/*     */               }
/*     */             });
/* 382 */         if (outputStream == null) {
/* 383 */           return null;
/*     */         }
/*     */         
/* 386 */         AttributeClass[] arrayOfAttributeClass = { AttributeClass.ATTRIBUTES_CHARSET, AttributeClass.ATTRIBUTES_NATURAL_LANGUAGE, new AttributeClass("requested-attributes", 68, "printer-uri-supported") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 394 */         if (IPPPrintService.writeIPPRequest(outputStream, "4002", arrayOfAttributeClass)) {
/*     */ 
/*     */           
/* 397 */           InputStream inputStream = httpURLConnection.getInputStream();
/*     */           
/* 399 */           HashMap[] arrayOfHashMap = IPPPrintService.readIPPResponse(inputStream);
/*     */           
/* 401 */           inputStream.close();
/* 402 */           outputStream.close();
/* 403 */           httpURLConnection.disconnect();
/*     */           
/* 405 */           if (arrayOfHashMap == null || arrayOfHashMap.length == 0) {
/* 406 */             return null;
/*     */           }
/*     */           
/* 409 */           ArrayList<String> arrayList = new ArrayList();
/* 410 */           for (byte b = 0; b < arrayOfHashMap.length; b++) {
/*     */             
/* 412 */             AttributeClass attributeClass = arrayOfHashMap[b].get("printer-uri-supported");
/*     */             
/* 414 */             if (attributeClass != null) {
/* 415 */               String str = attributeClass.getStringValue();
/* 416 */               arrayList.add(str);
/*     */             } 
/*     */           } 
/* 419 */           return arrayList.<String>toArray(new String[0]);
/*     */         } 
/* 421 */         outputStream.close();
/* 422 */         httpURLConnection.disconnect();
/*     */       }
/*     */     
/*     */     }
/* 426 */     catch (Exception exception) {}
/*     */     
/* 428 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getServer() {
/* 436 */     return cupsServer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPort() {
/* 443 */     return cupsPort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCupsRunning() {
/* 450 */     IPPPrintService.debug_println("CUPSPrinter>> libFound " + libFound);
/* 451 */     if (libFound) {
/* 452 */       IPPPrintService.debug_println("CUPSPrinter>> CUPS server " + getServer() + " port " + 
/* 453 */           getPort());
/* 454 */       return canConnect(getServer(), getPort());
/*     */     } 
/* 456 */     return false;
/*     */   }
/*     */   
/*     */   private static native String getCupsServer();
/*     */   
/*     */   private static native int getCupsPort();
/*     */   
/*     */   private static native String getCupsDefaultPrinter();
/*     */   
/*     */   private static native boolean canConnect(String paramString, int paramInt);
/*     */   
/*     */   private static native boolean initIDs();
/*     */   
/*     */   private static synchronized native String[] getMedia(String paramString);
/*     */   
/*     */   private static synchronized native float[] getPageSizes(String paramString);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/CUPSPrinter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */