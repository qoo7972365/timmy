/*     */ package sun.print;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import javax.print.DocFlavor;
/*     */ import javax.print.MultiDocPrintService;
/*     */ import javax.print.PrintService;
/*     */ import javax.print.PrintServiceLookup;
/*     */ import javax.print.attribute.Attribute;
/*     */ import javax.print.attribute.AttributeSet;
/*     */ import javax.print.attribute.HashPrintRequestAttributeSet;
/*     */ import javax.print.attribute.HashPrintServiceAttributeSet;
/*     */ import javax.print.attribute.PrintServiceAttributeSet;
/*     */ import javax.print.attribute.standard.PrinterName;
/*     */ import javax.print.attribute.standard.PrinterURI;
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
/*     */ public class PrintServiceLookupProvider
/*     */   extends PrintServiceLookup
/*     */   implements BackgroundServiceLookup, Runnable
/*     */ {
/*     */   private String defaultPrinter;
/*     */   private PrintService defaultPrintService;
/*     */   private PrintService[] printServices;
/*  72 */   private Vector lookupListeners = null;
/*  73 */   private static String debugPrefix = "PrintServiceLookupProvider>> ";
/*     */   private static boolean pollServices = true;
/*     */   private static final int DEFAULT_MINREFRESH = 120;
/*  76 */   private static int minRefreshTime = 120;
/*     */ 
/*     */   
/*     */   static String osname;
/*     */ 
/*     */   
/*  82 */   String[] lpNameComAix = new String[] { "/usr/bin/lsallq", "/usr/bin/lpstat -W -p|/usr/bin/expand|/usr/bin/cut -f1 -d' '", "/usr/bin/lpstat -W -d|/usr/bin/expand|/usr/bin/cut -f1 -d' '", "/usr/bin/lpstat -W -v" };
/*     */   
/*     */   private static final int aix_lsallq = 0;
/*     */   
/*     */   private static final int aix_lpstat_p = 1;
/*     */   
/*     */   private static final int aix_lpstat_d = 2;
/*     */   
/*     */   private static final int aix_lpstat_v = 3;
/*     */   
/*  92 */   private static int aix_defaultPrinterEnumeration = 0;
/*     */   static final int UNINITIALIZED = -1; static final int BSD_LPD = 0; static final int BSD_LPD_NG = 1; static int cmdIndex;
/*     */   
/*     */   static boolean isMac() {
/*     */     return osname.startsWith("Mac");
/*     */   }
/*     */   
/*  99 */   static { String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.print.polling"));
/*     */ 
/*     */     
/* 102 */     if (str1 != null) {
/* 103 */       if (str1.equalsIgnoreCase("true")) {
/* 104 */         pollServices = true;
/* 105 */       } else if (str1.equalsIgnoreCase("false")) {
/* 106 */         pollServices = false;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     String str2 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.print.minRefreshTime"));
/*     */ 
/*     */ 
/*     */     
/* 118 */     if (str2 != null) {
/*     */       try {
/* 120 */         minRefreshTime = (new Integer(str2)).intValue();
/* 121 */       } catch (NumberFormatException numberFormatException) {}
/*     */       
/* 123 */       if (minRefreshTime < 120) {
/* 124 */         minRefreshTime = 120;
/*     */       }
/*     */     } 
/*     */     
/* 128 */     osname = AccessController.<String>doPrivileged(new GetPropertyAction("os.name"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     if (isAIX()) {
/* 137 */       String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.print.aix.lpstat"));
/*     */ 
/*     */       
/* 140 */       if (str != null) {
/* 141 */         if (str.equalsIgnoreCase("lpstat")) {
/* 142 */           aix_defaultPrinterEnumeration = 1;
/* 143 */         } else if (str.equalsIgnoreCase("lsallq")) {
/* 144 */           aix_defaultPrinterEnumeration = 0;
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     cmdIndex = -1; } static boolean isSysV() {
/*     */     return osname.equals("SunOS");
/* 177 */   } String[] lpcFirstCom = new String[] { "/usr/sbin/lpc status | grep : | sed -ne '1,1 s/://p'", "/usr/sbin/lpc status | grep -E '^[ 0-9a-zA-Z_-]*@' | awk -F'@' '{print $1}'" }; static boolean isLinux() { return osname.equals("Linux"); } static boolean isBSD() {
/*     */     return (osname.equals("Linux") || osname.contains("OS X"));
/*     */   } static boolean isAIX() {
/*     */     return osname.equals("AIX");
/*     */   }
/* 182 */   String[] lpcAllCom = new String[] { "/usr/sbin/lpc status all | grep : | sed -e 's/://'", "/usr/sbin/lpc status all | grep -E '^[ 0-9a-zA-Z_-]*@' | awk -F'@' '{print $1}' | sort" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 187 */   String[] lpcNameCom = new String[] { "| grep : | sed -ne 's/://p'", "| grep -E '^[ 0-9a-zA-Z_-]*@' | awk -F'@' '{print $1}'" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getBSDCommandIndex() {
/* 194 */     String str = "/usr/sbin/lpc status all";
/* 195 */     String[] arrayOfString = execCmd(str);
/*     */     
/* 197 */     if (arrayOfString == null || arrayOfString.length == 0) {
/* 198 */       return 1;
/*     */     }
/*     */     
/* 201 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 202 */       if (arrayOfString[b].indexOf('@') != -1) {
/* 203 */         return 1;
/*     */       }
/*     */     } 
/*     */     
/* 207 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PrintServiceLookupProvider() {
/* 213 */     if (pollServices) {
/* 214 */       PrinterChangeListener printerChangeListener = new PrinterChangeListener();
/* 215 */       printerChangeListener.setDaemon(true);
/* 216 */       printerChangeListener.start();
/* 217 */       IPPPrintService.debug_println(debugPrefix + "polling turned on");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized PrintService[] getPrintServices() {
/* 227 */     SecurityManager securityManager = System.getSecurityManager();
/* 228 */     if (securityManager != null) {
/* 229 */       securityManager.checkPrintJobAccess();
/*     */     }
/*     */     
/* 232 */     if (this.printServices == null || !pollServices) {
/* 233 */       refreshServices();
/*     */     }
/* 235 */     if (this.printServices == null) {
/* 236 */       return new PrintService[0];
/*     */     }
/* 238 */     return (PrintService[])this.printServices.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private int addPrintServiceToList(ArrayList<PrintService> paramArrayList, PrintService paramPrintService) {
/* 243 */     int i = paramArrayList.indexOf(paramPrintService);
/*     */     
/* 245 */     if (CUPSPrinter.isCupsRunning() && i != -1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 251 */       PrinterURI printerURI = paramPrintService.<PrinterURI>getAttribute(PrinterURI.class);
/* 252 */       if (printerURI.getURI().getHost().equals("localhost")) {
/* 253 */         IPPPrintService.debug_println(debugPrefix + "duplicate PrintService, ignoring the new local printer: " + paramPrintService);
/* 254 */         return i;
/*     */       } 
/* 256 */       PrintService printService = paramArrayList.get(i);
/* 257 */       printerURI = printService.<PrinterURI>getAttribute(PrinterURI.class);
/* 258 */       if (printerURI.getURI().getHost().equals("localhost")) {
/* 259 */         IPPPrintService.debug_println(debugPrefix + "duplicate PrintService, removing existing local printer: " + printService);
/* 260 */         paramArrayList.remove(printService);
/*     */       } else {
/* 262 */         return i;
/*     */       } 
/*     */     } 
/* 265 */     paramArrayList.add(paramPrintService);
/* 266 */     return paramArrayList.size() - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void refreshServices() {
/* 273 */     String[] arrayOfString1 = null;
/* 274 */     String[] arrayOfString2 = null;
/*     */     
/*     */     try {
/* 277 */       getDefaultPrintService();
/* 278 */     } catch (Throwable throwable) {
/* 279 */       IPPPrintService.debug_println(debugPrefix + "Exception getting default printer : " + throwable);
/*     */     } 
/*     */     
/* 282 */     if (CUPSPrinter.isCupsRunning()) {
/*     */       try {
/* 284 */         arrayOfString2 = CUPSPrinter.getAllPrinters();
/* 285 */         IPPPrintService.debug_println("CUPS URIs = " + arrayOfString2);
/* 286 */         if (arrayOfString2 != null) {
/* 287 */           for (byte b1 = 0; b1 < arrayOfString2.length; b1++) {
/* 288 */             IPPPrintService.debug_println("URI=" + arrayOfString2[b1]);
/*     */           }
/*     */         }
/* 291 */       } catch (Throwable throwable) {
/* 292 */         IPPPrintService.debug_println(debugPrefix + "Exception getting all CUPS printers : " + throwable);
/*     */       } 
/*     */       
/* 295 */       if (arrayOfString2 != null && arrayOfString2.length > 0) {
/* 296 */         arrayOfString1 = new String[arrayOfString2.length];
/* 297 */         for (byte b1 = 0; b1 < arrayOfString2.length; b1++) {
/* 298 */           int j = arrayOfString2[b1].lastIndexOf("/");
/* 299 */           arrayOfString1[b1] = arrayOfString2[b1].substring(j + 1);
/*     */         }
/*     */       
/*     */       } 
/* 303 */     } else if (isMac() || isSysV()) {
/* 304 */       arrayOfString1 = getAllPrinterNamesSysV();
/* 305 */     } else if (isAIX()) {
/* 306 */       arrayOfString1 = getAllPrinterNamesAIX();
/*     */     } else {
/* 308 */       arrayOfString1 = getAllPrinterNamesBSD();
/*     */     } 
/*     */ 
/*     */     
/* 312 */     if (arrayOfString1 == null) {
/* 313 */       if (this.defaultPrintService != null) {
/* 314 */         this.printServices = new PrintService[1];
/* 315 */         this.printServices[0] = this.defaultPrintService;
/*     */       } else {
/* 317 */         this.printServices = null;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 322 */     ArrayList<UnixPrintService> arrayList = new ArrayList();
/* 323 */     int i = -1; byte b;
/* 324 */     for (b = 0; b < arrayOfString1.length; b++) {
/* 325 */       if (arrayOfString1[b] != null)
/*     */       {
/*     */         
/* 328 */         if (this.defaultPrintService != null && arrayOfString1[b]
/* 329 */           .equals(getPrinterDestName(this.defaultPrintService))) {
/* 330 */           i = addPrintServiceToList(arrayList, this.defaultPrintService);
/*     */         }
/* 332 */         else if (this.printServices == null) {
/* 333 */           IPPPrintService.debug_println(debugPrefix + "total# of printers = " + arrayOfString1.length);
/*     */ 
/*     */           
/* 336 */           if (CUPSPrinter.isCupsRunning()) {
/*     */             try {
/* 338 */               addPrintServiceToList(arrayList, new IPPPrintService(arrayOfString1[b], arrayOfString2[b], true));
/*     */ 
/*     */             
/*     */             }
/* 342 */             catch (Exception exception) {
/* 343 */               IPPPrintService.debug_println(debugPrefix + " getAllPrinters Exception " + exception);
/*     */             }
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 349 */             arrayList.add(new UnixPrintService(arrayOfString1[b]));
/*     */           } 
/*     */         } else {
/*     */           byte b1;
/* 353 */           for (b1 = 0; b1 < this.printServices.length; b1++) {
/* 354 */             if (this.printServices[b1] != null && 
/* 355 */               arrayOfString1[b].equals(getPrinterDestName(this.printServices[b1]))) {
/* 356 */               arrayList.add(this.printServices[b1]);
/* 357 */               this.printServices[b1] = null;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */           
/* 363 */           if (b1 == this.printServices.length) {
/* 364 */             if (CUPSPrinter.isCupsRunning()) {
/*     */               try {
/* 366 */                 addPrintServiceToList(arrayList, new IPPPrintService(arrayOfString1[b], arrayOfString2[b], true));
/*     */ 
/*     */               
/*     */               }
/* 370 */               catch (Exception exception) {
/* 371 */                 IPPPrintService.debug_println(debugPrefix + " getAllPrinters Exception " + exception);
/*     */               }
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 377 */               arrayList.add(new UnixPrintService(arrayOfString1[b]));
/*     */             } 
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 385 */     if (this.printServices != null) {
/* 386 */       for (b = 0; b < this.printServices.length; b++) {
/* 387 */         if (this.printServices[b] instanceof UnixPrintService && 
/* 388 */           !this.printServices[b].equals(this.defaultPrintService)) {
/* 389 */           ((UnixPrintService)this.printServices[b]).invalidateService();
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 395 */     if (i == -1 && this.defaultPrintService != null) {
/* 396 */       i = addPrintServiceToList(arrayList, this.defaultPrintService);
/*     */     }
/*     */     
/* 399 */     this.printServices = arrayList.<PrintService>toArray(new PrintService[0]);
/*     */ 
/*     */ 
/*     */     
/* 403 */     if (i > 0) {
/* 404 */       PrintService printService = this.printServices[0];
/* 405 */       this.printServices[0] = this.printServices[i];
/* 406 */       this.printServices[i] = printService;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesAttributes(PrintService paramPrintService, PrintServiceAttributeSet paramPrintServiceAttributeSet) {
/* 413 */     Attribute[] arrayOfAttribute = paramPrintServiceAttributeSet.toArray();
/*     */     
/* 415 */     for (byte b = 0; b < arrayOfAttribute.length; b++) {
/*     */       
/* 417 */       Object object = paramPrintService.getAttribute((Class)arrayOfAttribute[b].getCategory());
/* 418 */       if (object == null || !object.equals(arrayOfAttribute[b])) {
/* 419 */         return false;
/*     */       }
/*     */     } 
/* 422 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkPrinterName(String paramString) {
/* 431 */     for (byte b = 0; b < paramString.length(); ) {
/* 432 */       char c = paramString.charAt(b);
/* 433 */       if (Character.isLetterOrDigit(c) || c == '-' || c == '_' || c == '.' || c == '/') {
/*     */         b++;
/*     */         continue;
/*     */       } 
/* 437 */       return false;
/*     */     } 
/*     */     
/* 440 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getPrinterDestName(PrintService paramPrintService) {
/* 448 */     if (isMac()) {
/* 449 */       return ((IPPPrintService)paramPrintService).getDest();
/*     */     }
/* 451 */     return paramPrintService.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PrintService getServiceByName(PrinterName paramPrinterName) {
/* 459 */     String str = paramPrinterName.getValue();
/* 460 */     if (str == null || str.equals("") || !checkPrinterName(str)) {
/* 461 */       return null;
/*     */     }
/*     */     
/* 464 */     if (this.printServices != null) {
/* 465 */       for (PrintService printService1 : this.printServices) {
/*     */         
/* 467 */         PrinterName printerName = printService1.<PrinterName>getAttribute(PrinterName.class);
/* 468 */         if (printerName.getValue().equals(str)) {
/* 469 */           return printService1;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 474 */     if (CUPSPrinter.isCupsRunning()) {
/*     */       try {
/* 476 */         return new IPPPrintService(str, new URL("http://" + 
/*     */               
/* 478 */               CUPSPrinter.getServer() + ":" + 
/* 479 */               CUPSPrinter.getPort() + "/" + str));
/*     */       }
/* 481 */       catch (Exception exception) {
/* 482 */         IPPPrintService.debug_println(debugPrefix + " getServiceByName Exception " + exception);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 488 */     PrintService printService = null;
/* 489 */     if (isMac() || isSysV()) {
/* 490 */       printService = getNamedPrinterNameSysV(str);
/* 491 */     } else if (isAIX()) {
/* 492 */       printService = getNamedPrinterNameAIX(str);
/*     */     } else {
/* 494 */       printService = getNamedPrinterNameBSD(str);
/*     */     } 
/* 496 */     return printService;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PrintService[] getPrintServices(PrintServiceAttributeSet paramPrintServiceAttributeSet) {
/* 502 */     if (paramPrintServiceAttributeSet == null || paramPrintServiceAttributeSet.isEmpty()) {
/* 503 */       return getPrintServices();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 514 */     PrinterName printerName = (PrinterName)paramPrintServiceAttributeSet.get(PrinterName.class);
/*     */     PrintService printService;
/* 516 */     if (printerName != null && (printService = getDefaultPrintService()) != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 523 */       PrinterName printerName1 = printService.<PrinterName>getAttribute(PrinterName.class);
/*     */       
/* 525 */       if (printerName1 != null && printerName.equals(printerName1)) {
/* 526 */         if (matchesAttributes(printService, paramPrintServiceAttributeSet)) {
/* 527 */           PrintService[] arrayOfPrintService1 = new PrintService[1];
/* 528 */           arrayOfPrintService1[0] = printService;
/* 529 */           return arrayOfPrintService1;
/*     */         } 
/* 531 */         return new PrintService[0];
/*     */       } 
/*     */ 
/*     */       
/* 535 */       PrintService printService1 = getServiceByName(printerName);
/* 536 */       if (printService1 != null && 
/* 537 */         matchesAttributes(printService1, paramPrintServiceAttributeSet)) {
/* 538 */         PrintService[] arrayOfPrintService1 = new PrintService[1];
/* 539 */         arrayOfPrintService1[0] = printService1;
/* 540 */         return arrayOfPrintService1;
/*     */       } 
/* 542 */       return new PrintService[0];
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 547 */     Vector<PrintService> vector = new Vector();
/* 548 */     PrintService[] arrayOfPrintService = getPrintServices(); byte b;
/* 549 */     for (b = 0; b < arrayOfPrintService.length; b++) {
/* 550 */       if (matchesAttributes(arrayOfPrintService[b], paramPrintServiceAttributeSet)) {
/* 551 */         vector.add(arrayOfPrintService[b]);
/*     */       }
/*     */     } 
/* 554 */     arrayOfPrintService = new PrintService[vector.size()];
/* 555 */     for (b = 0; b < arrayOfPrintService.length; b++) {
/* 556 */       arrayOfPrintService[b] = vector.elementAt(b);
/*     */     }
/* 558 */     return arrayOfPrintService;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrintService[] getPrintServices(DocFlavor paramDocFlavor, AttributeSet paramAttributeSet) {
/* 568 */     SecurityManager securityManager = System.getSecurityManager();
/* 569 */     if (securityManager != null) {
/* 570 */       securityManager.checkPrintJobAccess();
/*     */     }
/* 572 */     HashPrintRequestAttributeSet hashPrintRequestAttributeSet = null;
/* 573 */     HashPrintServiceAttributeSet hashPrintServiceAttributeSet = null;
/*     */     
/* 575 */     if (paramAttributeSet != null && !paramAttributeSet.isEmpty()) {
/*     */       
/* 577 */       hashPrintRequestAttributeSet = new HashPrintRequestAttributeSet();
/* 578 */       hashPrintServiceAttributeSet = new HashPrintServiceAttributeSet();
/*     */       
/* 580 */       Attribute[] arrayOfAttribute = paramAttributeSet.toArray();
/* 581 */       for (byte b = 0; b < arrayOfAttribute.length; b++) {
/* 582 */         if (arrayOfAttribute[b] instanceof javax.print.attribute.PrintRequestAttribute) {
/* 583 */           hashPrintRequestAttributeSet.add(arrayOfAttribute[b]);
/* 584 */         } else if (arrayOfAttribute[b] instanceof javax.print.attribute.PrintServiceAttribute) {
/* 585 */           hashPrintServiceAttributeSet.add(arrayOfAttribute[b]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 590 */     PrintService[] arrayOfPrintService = getPrintServices(hashPrintServiceAttributeSet);
/* 591 */     if (arrayOfPrintService.length == 0) {
/* 592 */       return arrayOfPrintService;
/*     */     }
/*     */     
/* 595 */     if (CUPSPrinter.isCupsRunning()) {
/* 596 */       ArrayList<PrintService> arrayList = new ArrayList();
/* 597 */       for (byte b = 0; b < arrayOfPrintService.length; b++) {
/*     */         try {
/* 599 */           if (arrayOfPrintService[b]
/* 600 */             .getUnsupportedAttributes(paramDocFlavor, hashPrintRequestAttributeSet) == null) {
/* 601 */             arrayList.add(arrayOfPrintService[b]);
/*     */           }
/* 603 */         } catch (IllegalArgumentException illegalArgumentException) {}
/*     */       } 
/*     */       
/* 606 */       arrayOfPrintService = new PrintService[arrayList.size()];
/* 607 */       return arrayList.<PrintService>toArray(arrayOfPrintService);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 613 */     PrintService printService = arrayOfPrintService[0];
/* 614 */     if ((paramDocFlavor == null || printService
/* 615 */       .isDocFlavorSupported(paramDocFlavor)) && printService
/* 616 */       .getUnsupportedAttributes(paramDocFlavor, hashPrintRequestAttributeSet) == null)
/*     */     {
/* 618 */       return arrayOfPrintService;
/*     */     }
/* 620 */     return new PrintService[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiDocPrintService[] getMultiDocPrintServices(DocFlavor[] paramArrayOfDocFlavor, AttributeSet paramAttributeSet) {
/* 631 */     SecurityManager securityManager = System.getSecurityManager();
/* 632 */     if (securityManager != null) {
/* 633 */       securityManager.checkPrintJobAccess();
/*     */     }
/* 635 */     return new MultiDocPrintService[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized PrintService getDefaultPrintService() {
/* 640 */     SecurityManager securityManager = System.getSecurityManager();
/* 641 */     if (securityManager != null) {
/* 642 */       securityManager.checkPrintJobAccess();
/*     */     }
/*     */ 
/*     */     
/* 646 */     this.defaultPrintService = null;
/* 647 */     String str = null;
/*     */     
/* 649 */     IPPPrintService.debug_println("isRunning ? " + 
/* 650 */         CUPSPrinter.isCupsRunning());
/* 651 */     if (CUPSPrinter.isCupsRunning()) {
/* 652 */       String[] arrayOfString = CUPSPrinter.getDefaultPrinter();
/* 653 */       if (arrayOfString != null && arrayOfString.length >= 2) {
/* 654 */         this.defaultPrinter = arrayOfString[0];
/* 655 */         str = arrayOfString[1];
/*     */       }
/*     */     
/* 658 */     } else if (isMac() || isSysV()) {
/* 659 */       this.defaultPrinter = getDefaultPrinterNameSysV();
/* 660 */     } else if (isAIX()) {
/* 661 */       this.defaultPrinter = getDefaultPrinterNameAIX();
/*     */     } else {
/* 663 */       this.defaultPrinter = getDefaultPrinterNameBSD();
/*     */     } 
/*     */     
/* 666 */     if (this.defaultPrinter == null) {
/* 667 */       return null;
/*     */     }
/* 669 */     this.defaultPrintService = null;
/* 670 */     if (this.printServices != null) {
/* 671 */       for (byte b = 0; b < this.printServices.length; b++) {
/* 672 */         if (this.defaultPrinter.equals(getPrinterDestName(this.printServices[b]))) {
/* 673 */           this.defaultPrintService = this.printServices[b];
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 678 */     if (this.defaultPrintService == null) {
/* 679 */       if (CUPSPrinter.isCupsRunning()) {
/*     */         try {
/*     */           IPPPrintService iPPPrintService;
/* 682 */           if (str != null && !str.startsWith("file")) {
/* 683 */             iPPPrintService = new IPPPrintService(this.defaultPrinter, str, true);
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */             
/* 689 */             iPPPrintService = new IPPPrintService(this.defaultPrinter, new URL("http://" + CUPSPrinter.getServer() + ":" + CUPSPrinter.getPort() + "/" + this.defaultPrinter));
/*     */           } 
/*     */           
/* 692 */           this.defaultPrintService = iPPPrintService;
/* 693 */         } catch (Exception exception) {}
/*     */       } else {
/*     */         
/* 696 */         this.defaultPrintService = new UnixPrintService(this.defaultPrinter);
/*     */       } 
/*     */     }
/*     */     
/* 700 */     return this.defaultPrintService;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void getServicesInbackground(BackgroundLookupListener paramBackgroundLookupListener) {
/* 705 */     if (this.printServices != null) {
/* 706 */       paramBackgroundLookupListener.notifyServices(this.printServices);
/*     */     }
/* 708 */     else if (this.lookupListeners == null) {
/* 709 */       this.lookupListeners = new Vector();
/* 710 */       this.lookupListeners.add(paramBackgroundLookupListener);
/* 711 */       Thread thread = new Thread(this);
/* 712 */       thread.start();
/*     */     } else {
/* 714 */       this.lookupListeners.add(paramBackgroundLookupListener);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PrintService[] copyOf(PrintService[] paramArrayOfPrintService) {
/* 724 */     if (paramArrayOfPrintService == null || paramArrayOfPrintService.length == 0) {
/* 725 */       return paramArrayOfPrintService;
/*     */     }
/* 727 */     PrintService[] arrayOfPrintService = new PrintService[paramArrayOfPrintService.length];
/* 728 */     System.arraycopy(paramArrayOfPrintService, 0, arrayOfPrintService, 0, paramArrayOfPrintService.length);
/* 729 */     return arrayOfPrintService;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 734 */     PrintService[] arrayOfPrintService = getPrintServices();
/* 735 */     synchronized (this) {
/*     */       
/* 737 */       for (byte b = 0; b < this.lookupListeners.size(); b++) {
/*     */         
/* 739 */         BackgroundLookupListener backgroundLookupListener = this.lookupListeners.elementAt(b);
/* 740 */         backgroundLookupListener.notifyServices(copyOf(arrayOfPrintService));
/*     */       } 
/* 742 */       this.lookupListeners = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getDefaultPrinterNameBSD() {
/* 747 */     if (cmdIndex == -1) {
/* 748 */       cmdIndex = getBSDCommandIndex();
/*     */     }
/* 750 */     String[] arrayOfString = execCmd(this.lpcFirstCom[cmdIndex]);
/* 751 */     if (arrayOfString == null || arrayOfString.length == 0) {
/* 752 */       return null;
/*     */     }
/*     */     
/* 755 */     if (cmdIndex == 1 && arrayOfString[0]
/* 756 */       .startsWith("missingprinter")) {
/* 757 */       return null;
/*     */     }
/* 759 */     return arrayOfString[0];
/*     */   }
/*     */   
/*     */   private PrintService getNamedPrinterNameBSD(String paramString) {
/* 763 */     if (cmdIndex == -1) {
/* 764 */       cmdIndex = getBSDCommandIndex();
/*     */     }
/* 766 */     String str = "/usr/sbin/lpc status " + paramString + this.lpcNameCom[cmdIndex];
/* 767 */     String[] arrayOfString = execCmd(str);
/*     */     
/* 769 */     if (arrayOfString == null || !arrayOfString[0].equals(paramString)) {
/* 770 */       return null;
/*     */     }
/* 772 */     return new UnixPrintService(paramString);
/*     */   }
/*     */   
/*     */   private String[] getAllPrinterNamesBSD() {
/* 776 */     if (cmdIndex == -1) {
/* 777 */       cmdIndex = getBSDCommandIndex();
/*     */     }
/* 779 */     String[] arrayOfString = execCmd(this.lpcAllCom[cmdIndex]);
/* 780 */     if (arrayOfString == null || arrayOfString.length == 0) {
/* 781 */       return null;
/*     */     }
/* 783 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   static String getDefaultPrinterNameSysV() {
/* 787 */     String str1 = "lp";
/* 788 */     String str2 = "/usr/bin/lpstat -d";
/*     */     
/* 790 */     String[] arrayOfString = execCmd(str2);
/* 791 */     if (arrayOfString == null || arrayOfString.length == 0) {
/* 792 */       return str1;
/*     */     }
/* 794 */     int i = arrayOfString[0].indexOf(":");
/* 795 */     if (i == -1 || arrayOfString[0].length() <= i + 1) {
/* 796 */       return null;
/*     */     }
/* 798 */     String str3 = arrayOfString[0].substring(i + 1).trim();
/* 799 */     if (str3.length() == 0) {
/* 800 */       return null;
/*     */     }
/* 802 */     return str3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PrintService getNamedPrinterNameSysV(String paramString) {
/* 810 */     String str = "/usr/bin/lpstat -v " + paramString;
/* 811 */     String[] arrayOfString = execCmd(str);
/*     */     
/* 813 */     if (arrayOfString == null || arrayOfString[0].indexOf("unknown printer") > 0) {
/* 814 */       return null;
/*     */     }
/* 816 */     return new UnixPrintService(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] getAllPrinterNamesSysV() {
/* 821 */     String str1 = "lp";
/* 822 */     String str2 = "/usr/bin/lpstat -v|/usr/bin/expand|/usr/bin/cut -f3 -d' ' |/usr/bin/cut -f1 -d':' | /usr/bin/sort";
/*     */     
/* 824 */     String[] arrayOfString = execCmd(str2);
/* 825 */     ArrayList<String> arrayList = new ArrayList();
/* 826 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 827 */       if (!arrayOfString[b].equals("_default") && 
/* 828 */         !arrayOfString[b].equals(str1) && 
/* 829 */         !arrayOfString[b].equals("")) {
/* 830 */         arrayList.add(arrayOfString[b]);
/*     */       }
/*     */     } 
/* 833 */     return arrayList.<String>toArray(new String[arrayList.size()]);
/*     */   }
/*     */   
/*     */   private String getDefaultPrinterNameAIX() {
/* 837 */     String[] arrayOfString = execCmd(this.lpNameComAix[2]);
/*     */     
/* 839 */     arrayOfString = UnixPrintService.filterPrinterNamesAIX(arrayOfString);
/* 840 */     if (arrayOfString == null || arrayOfString.length != 1)
/*     */     {
/* 842 */       return null;
/*     */     }
/* 844 */     return arrayOfString[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PrintService getNamedPrinterNameAIX(String paramString) {
/* 850 */     String[] arrayOfString = execCmd(this.lpNameComAix[3] + paramString);
/*     */     
/* 852 */     arrayOfString = UnixPrintService.filterPrinterNamesAIX(arrayOfString);
/* 853 */     if (arrayOfString == null || arrayOfString.length != 1) {
/* 854 */       return null;
/*     */     }
/* 856 */     return new UnixPrintService(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] getAllPrinterNamesAIX() {
/* 862 */     String[] arrayOfString = execCmd(this.lpNameComAix[aix_defaultPrinterEnumeration]);
/*     */ 
/*     */     
/* 865 */     arrayOfString = UnixPrintService.filterPrinterNamesAIX(arrayOfString);
/*     */     
/* 867 */     ArrayList<String> arrayList = new ArrayList();
/* 868 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 869 */       arrayList.add(arrayOfString[b]);
/*     */     }
/* 871 */     return arrayList.<String>toArray(new String[arrayList.size()]);
/*     */   }
/*     */   
/*     */   static String[] execCmd(String paramString) {
/* 875 */     ArrayList arrayList = null;
/*     */     try {
/* 877 */       final String[] cmd = new String[3];
/* 878 */       if (isSysV() || isAIX()) {
/* 879 */         arrayOfString[0] = "/usr/bin/sh";
/* 880 */         arrayOfString[1] = "-c";
/* 881 */         arrayOfString[2] = "env LC_ALL=C " + paramString;
/*     */       } else {
/* 883 */         arrayOfString[0] = "/bin/sh";
/* 884 */         arrayOfString[1] = "-c";
/* 885 */         arrayOfString[2] = "LC_ALL=C " + paramString;
/*     */       } 
/*     */       
/* 888 */       arrayList = AccessController.<ArrayList>doPrivileged(new PrivilegedExceptionAction<ArrayList>()
/*     */           {
/*     */             
/*     */             public Object run() throws IOException
/*     */             {
/* 893 */               BufferedReader bufferedReader = null;
/* 894 */               File file = Files.createTempFile("prn", "xc", (FileAttribute<?>[])new FileAttribute[0]).toFile();
/* 895 */               cmd[2] = cmd[2] + ">" + file.getAbsolutePath();
/*     */               
/* 897 */               Process process = Runtime.getRuntime().exec(cmd);
/*     */               try {
/* 899 */                 boolean bool = false;
/* 900 */                 while (!bool) {
/*     */                   try {
/* 902 */                     process.waitFor();
/* 903 */                     bool = true;
/* 904 */                   } catch (InterruptedException interruptedException) {}
/*     */                 } 
/*     */ 
/*     */                 
/* 908 */                 if (process.exitValue() == 0) {
/* 909 */                   FileReader fileReader = new FileReader(file);
/* 910 */                   bufferedReader = new BufferedReader(fileReader);
/*     */                   
/* 912 */                   ArrayList<String> arrayList = new ArrayList(); String str;
/* 913 */                   while ((str = bufferedReader.readLine()) != null)
/*     */                   {
/* 915 */                     arrayList.add(str);
/*     */                   }
/* 917 */                   return arrayList;
/*     */                 } 
/*     */               } finally {
/* 920 */                 file.delete();
/*     */                 
/* 922 */                 if (bufferedReader != null) {
/* 923 */                   bufferedReader.close();
/*     */                 }
/* 925 */                 process.getInputStream().close();
/* 926 */                 process.getErrorStream().close();
/* 927 */                 process.getOutputStream().close();
/*     */               } 
/* 929 */               return null;
/*     */             }
/*     */           });
/* 932 */     } catch (PrivilegedActionException privilegedActionException) {}
/*     */     
/* 934 */     if (arrayList == null) {
/* 935 */       return new String[0];
/*     */     }
/* 937 */     return (String[])arrayList.toArray((Object[])new String[arrayList.size()]);
/*     */   }
/*     */   
/*     */   private class PrinterChangeListener extends Thread {
/*     */     private PrinterChangeListener() {}
/*     */     
/*     */     public void run() {
/*     */       while (true) {
/*     */         int i;
/*     */         try {
/* 947 */           PrintServiceLookupProvider.this.refreshServices();
/* 948 */         } catch (Exception exception) {
/* 949 */           IPPPrintService.debug_println(PrintServiceLookupProvider.debugPrefix + "Exception in refresh thread.");
/*     */           
/*     */           break;
/*     */         } 
/* 953 */         if (PrintServiceLookupProvider.this.printServices != null && PrintServiceLookupProvider.this
/* 954 */           .printServices.length > PrintServiceLookupProvider.minRefreshTime) {
/*     */           
/* 956 */           i = PrintServiceLookupProvider.this.printServices.length;
/*     */         } else {
/* 958 */           i = PrintServiceLookupProvider.minRefreshTime;
/*     */         } 
/*     */         try {
/* 961 */           sleep((i * 1000));
/* 962 */         } catch (InterruptedException interruptedException) {
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/PrintServiceLookupProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */