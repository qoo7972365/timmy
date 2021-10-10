/*     */ package java.awt.print;
/*     */ 
/*     */ import java.awt.AWTError;
/*     */ import java.awt.HeadlessException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javax.print.DocFlavor;
/*     */ import javax.print.PrintService;
/*     */ import javax.print.PrintServiceLookup;
/*     */ import javax.print.StreamPrintServiceFactory;
/*     */ import javax.print.attribute.PrintRequestAttributeSet;
/*     */ import javax.print.attribute.standard.Media;
/*     */ import javax.print.attribute.standard.MediaPrintableArea;
/*     */ import javax.print.attribute.standard.MediaSize;
/*     */ import javax.print.attribute.standard.MediaSizeName;
/*     */ import javax.print.attribute.standard.OrientationRequested;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PrinterJob
/*     */ {
/*     */   public static PrinterJob getPrinterJob() {
/*  73 */     SecurityManager securityManager = System.getSecurityManager();
/*  74 */     if (securityManager != null) {
/*  75 */       securityManager.checkPrintJobAccess();
/*     */     }
/*  77 */     return AccessController.<PrinterJob>doPrivileged(new PrivilegedAction<PrinterJob>()
/*     */         {
/*     */           public Object run() {
/*  80 */             String str = System.getProperty("java.awt.printerjob", null);
/*     */             try {
/*  82 */               return Class.forName(str).newInstance();
/*  83 */             } catch (ClassNotFoundException classNotFoundException) {
/*  84 */               throw new AWTError("PrinterJob not found: " + str);
/*  85 */             } catch (InstantiationException instantiationException) {
/*  86 */               throw new AWTError("Could not instantiate PrinterJob: " + str);
/*  87 */             } catch (IllegalAccessException illegalAccessException) {
/*  88 */               throw new AWTError("Could not access PrinterJob: " + str);
/*     */             } 
/*     */           }
/*     */         });
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
/*     */   public static PrintService[] lookupPrintServices() {
/* 107 */     return 
/* 108 */       PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PAGEABLE, null);
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
/*     */   public static StreamPrintServiceFactory[] lookupStreamPrintServices(String paramString) {
/* 148 */     return StreamPrintServiceFactory.lookupStreamPrintServiceFactories(DocFlavor.SERVICE_FORMATTED.PAGEABLE, paramString);
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
/*     */   public PrintService getPrintService() {
/* 174 */     return null;
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
/*     */   public void setPrintService(PrintService paramPrintService) throws PrinterException {
/* 195 */     throw new PrinterException("Setting a service is not supported on this class");
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
/*     */   public abstract void setPrintable(Printable paramPrintable);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setPrintable(Printable paramPrintable, PageFormat paramPageFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setPageable(Pageable paramPageable) throws NullPointerException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean printDialog() throws HeadlessException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean printDialog(PrintRequestAttributeSet paramPrintRequestAttributeSet) throws HeadlessException {
/* 308 */     if (paramPrintRequestAttributeSet == null) {
/* 309 */       throw new NullPointerException("attributes");
/*     */     }
/* 311 */     return printDialog();
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
/*     */   public abstract PageFormat pageDialog(PageFormat paramPageFormat) throws HeadlessException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageFormat pageDialog(PrintRequestAttributeSet paramPrintRequestAttributeSet) throws HeadlessException {
/* 371 */     if (paramPrintRequestAttributeSet == null) {
/* 372 */       throw new NullPointerException("attributes");
/*     */     }
/* 374 */     return pageDialog(defaultPage());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract PageFormat defaultPage(PageFormat paramPageFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageFormat defaultPage() {
/* 393 */     return defaultPage(new PageFormat());
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
/*     */   public PageFormat getPageFormat(PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/* 415 */     PrintService printService = getPrintService();
/* 416 */     PageFormat pageFormat = defaultPage();
/*     */     
/* 418 */     if (printService == null || paramPrintRequestAttributeSet == null) {
/* 419 */       return pageFormat;
/*     */     }
/*     */     
/* 422 */     Media media = (Media)paramPrintRequestAttributeSet.get(Media.class);
/*     */     
/* 424 */     MediaPrintableArea mediaPrintableArea = (MediaPrintableArea)paramPrintRequestAttributeSet.get(MediaPrintableArea.class);
/*     */     
/* 426 */     OrientationRequested orientationRequested = (OrientationRequested)paramPrintRequestAttributeSet.get(OrientationRequested.class);
/*     */     
/* 428 */     if (media == null && mediaPrintableArea == null && orientationRequested == null) {
/* 429 */       return pageFormat;
/*     */     }
/* 431 */     Paper paper = pageFormat.getPaper();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 436 */     if (mediaPrintableArea == null && media != null && printService
/* 437 */       .isAttributeCategorySupported((Class)MediaPrintableArea.class)) {
/*     */       
/* 439 */       Object object = printService.getSupportedAttributeValues((Class)MediaPrintableArea.class, null, paramPrintRequestAttributeSet);
/*     */       
/* 441 */       if (object instanceof MediaPrintableArea[] && ((MediaPrintableArea[])object).length > 0)
/*     */       {
/* 443 */         mediaPrintableArea = ((MediaPrintableArea[])object)[0];
/*     */       }
/*     */     } 
/*     */     
/* 447 */     if (media != null && printService
/* 448 */       .isAttributeValueSupported(media, null, paramPrintRequestAttributeSet) && 
/* 449 */       media instanceof MediaSizeName) {
/* 450 */       MediaSizeName mediaSizeName = (MediaSizeName)media;
/* 451 */       MediaSize mediaSize = MediaSize.getMediaSizeForName(mediaSizeName);
/* 452 */       if (mediaSize != null) {
/* 453 */         double d1 = 72.0D;
/* 454 */         double d2 = mediaSize.getX(25400) * d1;
/* 455 */         double d3 = mediaSize.getY(25400) * d1;
/* 456 */         paper.setSize(d2, d3);
/* 457 */         if (mediaPrintableArea == null) {
/* 458 */           paper.setImageableArea(d1, d1, d2 - 2.0D * d1, d3 - 2.0D * d1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 466 */     if (mediaPrintableArea != null && printService
/* 467 */       .isAttributeValueSupported(mediaPrintableArea, null, paramPrintRequestAttributeSet)) {
/*     */       
/* 469 */       float[] arrayOfFloat = mediaPrintableArea.getPrintableArea(25400);
/* 470 */       for (byte b = 0; b < arrayOfFloat.length; b++) {
/* 471 */         arrayOfFloat[b] = arrayOfFloat[b] * 72.0F;
/*     */       }
/* 473 */       paper.setImageableArea(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
/*     */     } 
/*     */ 
/*     */     
/* 477 */     if (orientationRequested != null && printService
/* 478 */       .isAttributeValueSupported(orientationRequested, null, paramPrintRequestAttributeSet)) {
/*     */       boolean bool;
/* 480 */       if (orientationRequested.equals(OrientationRequested.REVERSE_LANDSCAPE)) {
/* 481 */         bool = true;
/* 482 */       } else if (orientationRequested.equals(OrientationRequested.LANDSCAPE)) {
/* 483 */         bool = false;
/*     */       } else {
/* 485 */         bool = true;
/*     */       } 
/* 487 */       pageFormat.setOrientation(bool);
/*     */     } 
/*     */     
/* 490 */     pageFormat.setPaper(paper);
/* 491 */     pageFormat = validatePage(pageFormat);
/* 492 */     return pageFormat;
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
/*     */   public abstract PageFormat validatePage(PageFormat paramPageFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void print() throws PrinterException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void print(PrintRequestAttributeSet paramPrintRequestAttributeSet) throws PrinterException {
/* 561 */     print();
/*     */   }
/*     */   
/*     */   public abstract void setCopies(int paramInt);
/*     */   
/*     */   public abstract int getCopies();
/*     */   
/*     */   public abstract String getUserName();
/*     */   
/*     */   public abstract void setJobName(String paramString);
/*     */   
/*     */   public abstract String getJobName();
/*     */   
/*     */   public abstract void cancel();
/*     */   
/*     */   public abstract boolean isCancelled();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/print/PrinterJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */