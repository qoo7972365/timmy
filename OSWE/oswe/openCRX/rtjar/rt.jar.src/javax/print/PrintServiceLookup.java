/*     */ package javax.print;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ import javax.print.attribute.AttributeSet;
/*     */ import sun.awt.AppContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PrintServiceLookup
/*     */ {
/*     */   static class Services
/*     */   {
/*  72 */     private ArrayList listOfLookupServices = null;
/*  73 */     private ArrayList registeredServices = null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Services getServicesForContext() {
/*  78 */     Services services = (Services)AppContext.getAppContext().get(Services.class);
/*  79 */     if (services == null) {
/*  80 */       services = new Services();
/*  81 */       AppContext.getAppContext().put(Services.class, services);
/*     */     } 
/*  83 */     return services;
/*     */   }
/*     */   
/*     */   private static ArrayList getListOfLookupServices() {
/*  87 */     return (getServicesForContext()).listOfLookupServices;
/*     */   }
/*     */   
/*     */   private static ArrayList initListOfLookupServices() {
/*  91 */     ArrayList arrayList = new ArrayList();
/*  92 */     (getServicesForContext()).listOfLookupServices = arrayList;
/*  93 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ArrayList getRegisteredServices() {
/*  98 */     return (getServicesForContext()).registeredServices;
/*     */   }
/*     */   
/*     */   private static ArrayList initRegisteredServices() {
/* 102 */     ArrayList arrayList = new ArrayList();
/* 103 */     (getServicesForContext()).registeredServices = arrayList;
/* 104 */     return arrayList;
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
/*     */ 
/*     */   
/*     */   public static final PrintService[] lookupPrintServices(DocFlavor paramDocFlavor, AttributeSet paramAttributeSet) {
/* 123 */     ArrayList arrayList = getServices(paramDocFlavor, paramAttributeSet);
/* 124 */     return (PrintService[])arrayList.toArray((Object[])new PrintService[arrayList.size()]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final MultiDocPrintService[] lookupMultiDocPrintServices(DocFlavor[] paramArrayOfDocFlavor, AttributeSet paramAttributeSet) {
/* 151 */     ArrayList arrayList = getMultiDocServices(paramArrayOfDocFlavor, paramAttributeSet);
/* 152 */     return (MultiDocPrintService[])arrayList
/* 153 */       .toArray((Object[])new MultiDocPrintService[arrayList.size()]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final PrintService lookupDefaultPrintService() {
/* 180 */     Iterator<PrintServiceLookup> iterator = getAllLookupServices().iterator();
/* 181 */     while (iterator.hasNext()) {
/*     */       try {
/* 183 */         PrintServiceLookup printServiceLookup = iterator.next();
/* 184 */         PrintService printService = printServiceLookup.getDefaultPrintService();
/* 185 */         if (printService != null) {
/* 186 */           return printService;
/*     */         }
/* 188 */       } catch (Exception exception) {}
/*     */     } 
/*     */     
/* 191 */     return null;
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
/*     */ 
/*     */   
/*     */   public static boolean registerServiceProvider(PrintServiceLookup paramPrintServiceLookup) {
/* 210 */     synchronized (PrintServiceLookup.class) {
/* 211 */       Iterator<Object> iterator = getAllLookupServices().iterator();
/* 212 */       while (iterator.hasNext()) {
/*     */         try {
/* 214 */           Object object = iterator.next();
/* 215 */           if (object.getClass() == paramPrintServiceLookup.getClass()) {
/* 216 */             return false;
/*     */           }
/* 218 */         } catch (Exception exception) {}
/*     */       } 
/*     */       
/* 221 */       getListOfLookupServices().add(paramPrintServiceLookup);
/* 222 */       return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean registerService(PrintService paramPrintService) {
/* 248 */     synchronized (PrintServiceLookup.class) {
/* 249 */       if (paramPrintService instanceof StreamPrintService) {
/* 250 */         return false;
/*     */       }
/* 252 */       ArrayList<PrintService> arrayList = getRegisteredServices();
/* 253 */       if (arrayList == null) {
/* 254 */         arrayList = initRegisteredServices();
/*     */       
/*     */       }
/* 257 */       else if (arrayList.contains(paramPrintService)) {
/* 258 */         return false;
/*     */       } 
/*     */       
/* 261 */       arrayList.add(paramPrintService);
/* 262 */       return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ArrayList getAllLookupServices() {
/* 330 */     synchronized (PrintServiceLookup.class) {
/* 331 */       ArrayList arrayList = getListOfLookupServices();
/* 332 */       if (arrayList != null) {
/* 333 */         return arrayList;
/*     */       }
/* 335 */       arrayList = initListOfLookupServices();
/*     */       
/*     */       try {
/* 338 */         AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */             {
/*     */               
/*     */               public Object run()
/*     */               {
/* 343 */                 Iterator iterator = ServiceLoader.<PrintServiceLookup>load(PrintServiceLookup.class).iterator();
/* 344 */                 ArrayList arrayList = PrintServiceLookup.getListOfLookupServices();
/* 345 */                 while (iterator.hasNext()) {
/*     */                   try {
/* 347 */                     arrayList.add(iterator.next());
/* 348 */                   } catch (ServiceConfigurationError serviceConfigurationError) {
/*     */                     
/* 350 */                     if (System.getSecurityManager() != null) {
/* 351 */                       serviceConfigurationError.printStackTrace(); continue;
/*     */                     } 
/* 353 */                     throw serviceConfigurationError;
/*     */                   } 
/*     */                 } 
/*     */                 
/* 357 */                 return null;
/*     */               }
/*     */             });
/* 360 */       } catch (PrivilegedActionException privilegedActionException) {}
/*     */ 
/*     */       
/* 363 */       return arrayList;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ArrayList getServices(DocFlavor paramDocFlavor, AttributeSet paramAttributeSet) {
/* 370 */     ArrayList<PrintService> arrayList = new ArrayList();
/* 371 */     Iterator<PrintServiceLookup> iterator = getAllLookupServices().iterator();
/* 372 */     while (iterator.hasNext()) {
/*     */       try {
/* 374 */         PrintServiceLookup printServiceLookup = iterator.next();
/* 375 */         PrintService[] arrayOfPrintService = null;
/* 376 */         if (paramDocFlavor == null && paramAttributeSet == null) {
/*     */           try {
/* 378 */             arrayOfPrintService = printServiceLookup.getPrintServices();
/* 379 */           } catch (Throwable throwable) {}
/*     */         } else {
/*     */           
/* 382 */           arrayOfPrintService = printServiceLookup.getPrintServices(paramDocFlavor, paramAttributeSet);
/*     */         } 
/* 384 */         if (arrayOfPrintService == null) {
/*     */           continue;
/*     */         }
/* 387 */         for (byte b = 0; b < arrayOfPrintService.length; b++) {
/* 388 */           arrayList.add(arrayOfPrintService[b]);
/*     */         }
/* 390 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */     
/* 394 */     ArrayList arrayList1 = null;
/*     */     try {
/* 396 */       SecurityManager securityManager = System.getSecurityManager();
/* 397 */       if (securityManager != null) {
/* 398 */         securityManager.checkPrintJobAccess();
/*     */       }
/* 400 */       arrayList1 = getRegisteredServices();
/* 401 */     } catch (SecurityException securityException) {}
/*     */     
/* 403 */     if (arrayList1 != null) {
/*     */       
/* 405 */       PrintService[] arrayOfPrintService = (PrintService[])arrayList1.toArray(
/* 406 */           (Object[])new PrintService[arrayList1.size()]);
/* 407 */       for (byte b = 0; b < arrayOfPrintService.length; b++) {
/* 408 */         if (!arrayList.contains(arrayOfPrintService[b])) {
/* 409 */           if (paramDocFlavor == null && paramAttributeSet == null) {
/* 410 */             arrayList.add(arrayOfPrintService[b]);
/* 411 */           } else if (((paramDocFlavor != null && arrayOfPrintService[b]
/* 412 */             .isDocFlavorSupported(paramDocFlavor)) || paramDocFlavor == null) && null == arrayOfPrintService[b]
/*     */             
/* 414 */             .getUnsupportedAttributes(paramDocFlavor, paramAttributeSet)) {
/*     */             
/* 416 */             arrayList.add(arrayOfPrintService[b]);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 421 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ArrayList getMultiDocServices(DocFlavor[] paramArrayOfDocFlavor, AttributeSet paramAttributeSet) {
/* 428 */     ArrayList<MultiDocPrintService> arrayList = new ArrayList();
/* 429 */     Iterator<PrintServiceLookup> iterator = getAllLookupServices().iterator();
/* 430 */     while (iterator.hasNext()) {
/*     */       try {
/* 432 */         PrintServiceLookup printServiceLookup = iterator.next();
/*     */         
/* 434 */         MultiDocPrintService[] arrayOfMultiDocPrintService = printServiceLookup.getMultiDocPrintServices(paramArrayOfDocFlavor, paramAttributeSet);
/* 435 */         if (arrayOfMultiDocPrintService == null) {
/*     */           continue;
/*     */         }
/* 438 */         for (byte b = 0; b < arrayOfMultiDocPrintService.length; b++) {
/* 439 */           arrayList.add(arrayOfMultiDocPrintService[b]);
/*     */         }
/* 441 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */     
/* 445 */     ArrayList arrayList1 = null;
/*     */     try {
/* 447 */       SecurityManager securityManager = System.getSecurityManager();
/* 448 */       if (securityManager != null) {
/* 449 */         securityManager.checkPrintJobAccess();
/*     */       }
/* 451 */       arrayList1 = getRegisteredServices();
/* 452 */     } catch (Exception exception) {}
/*     */     
/* 454 */     if (arrayList1 != null) {
/*     */       
/* 456 */       PrintService[] arrayOfPrintService = (PrintService[])arrayList1.toArray(
/* 457 */           (Object[])new PrintService[arrayList1.size()]);
/* 458 */       for (byte b = 0; b < arrayOfPrintService.length; b++) {
/* 459 */         if (arrayOfPrintService[b] instanceof MultiDocPrintService && 
/* 460 */           !arrayList.contains(arrayOfPrintService[b])) {
/* 461 */           if (paramArrayOfDocFlavor == null || paramArrayOfDocFlavor.length == 0) {
/* 462 */             arrayList.add(arrayOfPrintService[b]);
/*     */           } else {
/* 464 */             boolean bool = true;
/* 465 */             for (byte b1 = 0; b1 < paramArrayOfDocFlavor.length; b1++) {
/* 466 */               if (arrayOfPrintService[b].isDocFlavorSupported(paramArrayOfDocFlavor[b1])) {
/*     */                 
/* 468 */                 if (arrayOfPrintService[b].getUnsupportedAttributes(paramArrayOfDocFlavor[b1], paramAttributeSet) != null) {
/*     */                   
/* 470 */                   bool = false;
/*     */                   break;
/*     */                 } 
/*     */               } else {
/* 474 */                 bool = false;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 478 */             if (bool) {
/* 479 */               arrayList.add(arrayOfPrintService[b]);
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 485 */     return arrayList;
/*     */   }
/*     */   
/*     */   public abstract PrintService[] getPrintServices(DocFlavor paramDocFlavor, AttributeSet paramAttributeSet);
/*     */   
/*     */   public abstract PrintService[] getPrintServices();
/*     */   
/*     */   public abstract MultiDocPrintService[] getMultiDocPrintServices(DocFlavor[] paramArrayOfDocFlavor, AttributeSet paramAttributeSet);
/*     */   
/*     */   public abstract PrintService getDefaultPrintService();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/PrintServiceLookup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */