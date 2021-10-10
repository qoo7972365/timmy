/*     */ package sun.print;
/*     */ 
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Pageable;
/*     */ import java.awt.print.Paper;
/*     */ import java.awt.print.Printable;
/*     */ import java.awt.print.PrinterException;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.net.URL;
/*     */ import java.util.Vector;
/*     */ import javax.print.CancelablePrintJob;
/*     */ import javax.print.Doc;
/*     */ import javax.print.DocFlavor;
/*     */ import javax.print.PrintException;
/*     */ import javax.print.PrintService;
/*     */ import javax.print.attribute.Attribute;
/*     */ import javax.print.attribute.AttributeSetUtilities;
/*     */ import javax.print.attribute.DocAttributeSet;
/*     */ import javax.print.attribute.HashPrintJobAttributeSet;
/*     */ import javax.print.attribute.HashPrintRequestAttributeSet;
/*     */ import javax.print.attribute.PrintJobAttributeSet;
/*     */ import javax.print.attribute.PrintRequestAttributeSet;
/*     */ import javax.print.attribute.standard.Copies;
/*     */ import javax.print.attribute.standard.DocumentName;
/*     */ import javax.print.attribute.standard.Fidelity;
/*     */ import javax.print.attribute.standard.JobName;
/*     */ import javax.print.attribute.standard.JobOriginatingUserName;
/*     */ import javax.print.attribute.standard.Media;
/*     */ import javax.print.attribute.standard.MediaSize;
/*     */ import javax.print.attribute.standard.MediaSizeName;
/*     */ import javax.print.attribute.standard.OrientationRequested;
/*     */ import javax.print.attribute.standard.RequestingUserName;
/*     */ import javax.print.event.PrintJobAttributeListener;
/*     */ import javax.print.event.PrintJobEvent;
/*     */ import javax.print.event.PrintJobListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PSStreamPrintJob
/*     */   implements CancelablePrintJob
/*     */ {
/*     */   private transient Vector jobListeners;
/*     */   private transient Vector attrListeners;
/*     */   private transient Vector listenedAttributeSets;
/*     */   private PSStreamPrintService service;
/*     */   private boolean fidelity;
/*     */   private boolean printing = false;
/*     */   private boolean printReturned = false;
/*  77 */   private PrintRequestAttributeSet reqAttrSet = null;
/*  78 */   private PrintJobAttributeSet jobAttrSet = null;
/*     */ 
/*     */   
/*     */   private PrinterJob job;
/*     */   
/*     */   private Doc doc;
/*     */   
/*  85 */   private InputStream instream = null;
/*  86 */   private Reader reader = null;
/*     */ 
/*     */   
/*  89 */   private String jobName = "Java Printing";
/*  90 */   private int copies = 1;
/*  91 */   private MediaSize mediaSize = MediaSize.NA.LETTER;
/*  92 */   private OrientationRequested orient = OrientationRequested.PORTRAIT;
/*     */   
/*     */   PSStreamPrintJob(PSStreamPrintService paramPSStreamPrintService) {
/*  95 */     this.service = paramPSStreamPrintService;
/*     */   }
/*     */   
/*     */   public PrintService getPrintService() {
/*  99 */     return this.service;
/*     */   }
/*     */   
/*     */   public PrintJobAttributeSet getAttributes() {
/* 103 */     synchronized (this) {
/* 104 */       if (this.jobAttrSet == null) {
/*     */         
/* 106 */         HashPrintJobAttributeSet hashPrintJobAttributeSet = new HashPrintJobAttributeSet();
/* 107 */         return AttributeSetUtilities.unmodifiableView(hashPrintJobAttributeSet);
/*     */       } 
/* 109 */       return this.jobAttrSet;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPrintJobListener(PrintJobListener paramPrintJobListener) {
/* 115 */     synchronized (this) {
/* 116 */       if (paramPrintJobListener == null) {
/*     */         return;
/*     */       }
/* 119 */       if (this.jobListeners == null) {
/* 120 */         this.jobListeners = new Vector();
/*     */       }
/* 122 */       this.jobListeners.add(paramPrintJobListener);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removePrintJobListener(PrintJobListener paramPrintJobListener) {
/* 127 */     synchronized (this) {
/* 128 */       if (paramPrintJobListener == null || this.jobListeners == null) {
/*     */         return;
/*     */       }
/* 131 */       this.jobListeners.remove(paramPrintJobListener);
/* 132 */       if (this.jobListeners.isEmpty()) {
/* 133 */         this.jobListeners = null;
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
/*     */   private void closeDataStreams() {
/* 146 */     if (this.doc == null) {
/*     */       return;
/*     */     }
/*     */     
/* 150 */     Object object = null;
/*     */     
/*     */     try {
/* 153 */       object = this.doc.getPrintData();
/* 154 */     } catch (IOException iOException) {
/*     */       return;
/*     */     } 
/*     */     
/* 158 */     if (this.instream != null) {
/*     */       
/* 160 */       try { this.instream.close(); }
/* 161 */       catch (IOException iOException) {  }
/*     */       finally
/* 163 */       { this.instream = null; }
/*     */ 
/*     */     
/* 166 */     } else if (this.reader != null) {
/*     */       
/* 168 */       try { this.reader.close(); }
/* 169 */       catch (IOException iOException) {  }
/*     */       finally
/* 171 */       { this.reader = null; }
/*     */ 
/*     */     
/* 174 */     } else if (object instanceof InputStream) {
/*     */       try {
/* 176 */         ((InputStream)object).close();
/* 177 */       } catch (IOException iOException) {}
/*     */     
/*     */     }
/* 180 */     else if (object instanceof Reader) {
/*     */       try {
/* 182 */         ((Reader)object).close();
/* 183 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void notifyEvent(int paramInt) {
/* 189 */     synchronized (this) {
/* 190 */       if (this.jobListeners != null) {
/*     */         
/* 192 */         PrintJobEvent printJobEvent = new PrintJobEvent(this, paramInt);
/* 193 */         for (byte b = 0; b < this.jobListeners.size(); b++) {
/* 194 */           PrintJobListener printJobListener = this.jobListeners.elementAt(b);
/* 195 */           switch (paramInt) {
/*     */             
/*     */             case 101:
/* 198 */               printJobListener.printJobCanceled(printJobEvent);
/*     */               break;
/*     */             
/*     */             case 103:
/* 202 */               printJobListener.printJobFailed(printJobEvent);
/*     */               break;
/*     */             
/*     */             case 106:
/* 206 */               printJobListener.printDataTransferCompleted(printJobEvent);
/*     */               break;
/*     */             
/*     */             case 105:
/* 210 */               printJobListener.printJobNoMoreEvents(printJobEvent);
/*     */               break;
/*     */             
/*     */             case 102:
/* 214 */               printJobListener.printJobCompleted(printJobEvent);
/*     */               break;
/*     */           } 
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
/*     */   public void addPrintJobAttributeListener(PrintJobAttributeListener paramPrintJobAttributeListener, PrintJobAttributeSet paramPrintJobAttributeSet) {
/* 228 */     synchronized (this) {
/* 229 */       if (paramPrintJobAttributeListener == null) {
/*     */         return;
/*     */       }
/* 232 */       if (this.attrListeners == null) {
/* 233 */         this.attrListeners = new Vector();
/* 234 */         this.listenedAttributeSets = new Vector();
/*     */       } 
/* 236 */       this.attrListeners.add(paramPrintJobAttributeListener);
/* 237 */       if (paramPrintJobAttributeSet == null) {
/* 238 */         paramPrintJobAttributeSet = new HashPrintJobAttributeSet();
/*     */       }
/* 240 */       this.listenedAttributeSets.add(paramPrintJobAttributeSet);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePrintJobAttributeListener(PrintJobAttributeListener paramPrintJobAttributeListener) {
/* 246 */     synchronized (this) {
/* 247 */       if (paramPrintJobAttributeListener == null || this.attrListeners == null) {
/*     */         return;
/*     */       }
/* 250 */       int i = this.attrListeners.indexOf(paramPrintJobAttributeListener);
/* 251 */       if (i == -1) {
/*     */         return;
/*     */       }
/* 254 */       this.attrListeners.remove(i);
/* 255 */       this.listenedAttributeSets.remove(i);
/* 256 */       if (this.attrListeners.isEmpty()) {
/* 257 */         this.attrListeners = null;
/* 258 */         this.listenedAttributeSets = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void print(Doc paramDoc, PrintRequestAttributeSet paramPrintRequestAttributeSet) throws PrintException {
/*     */     Object object;
/* 267 */     synchronized (this) {
/* 268 */       if (this.printing) {
/* 269 */         throw new PrintException("already printing");
/*     */       }
/* 271 */       this.printing = true;
/*     */     } 
/*     */ 
/*     */     
/* 275 */     this.doc = paramDoc;
/*     */     
/* 277 */     DocFlavor docFlavor = paramDoc.getDocFlavor();
/*     */ 
/*     */     
/*     */     try {
/* 281 */       object = paramDoc.getPrintData();
/* 282 */     } catch (IOException iOException) {
/* 283 */       notifyEvent(103);
/* 284 */       throw new PrintException("can't get print data: " + iOException.toString());
/*     */     } 
/*     */     
/* 287 */     if (docFlavor == null || !this.service.isDocFlavorSupported(docFlavor)) {
/* 288 */       notifyEvent(103);
/* 289 */       throw new PrintJobFlavorException("invalid flavor", docFlavor);
/*     */     } 
/*     */     
/* 292 */     initializeAttributeSets(paramDoc, paramPrintRequestAttributeSet);
/*     */     
/* 294 */     getAttributeValues(docFlavor);
/*     */     
/* 296 */     String str = docFlavor.getRepresentationClassName();
/* 297 */     if (docFlavor.equals(DocFlavor.INPUT_STREAM.GIF) || docFlavor
/* 298 */       .equals(DocFlavor.INPUT_STREAM.JPEG) || docFlavor
/* 299 */       .equals(DocFlavor.INPUT_STREAM.PNG) || docFlavor
/* 300 */       .equals(DocFlavor.BYTE_ARRAY.GIF) || docFlavor
/* 301 */       .equals(DocFlavor.BYTE_ARRAY.JPEG) || docFlavor
/* 302 */       .equals(DocFlavor.BYTE_ARRAY.PNG))
/*     */       try {
/* 304 */         this.instream = paramDoc.getStreamForBytes();
/* 305 */         printableJob(new ImagePrinter(this.instream), this.reqAttrSet);
/*     */         return;
/* 307 */       } catch (ClassCastException classCastException) {
/* 308 */         notifyEvent(103);
/* 309 */         throw new PrintException(classCastException);
/* 310 */       } catch (IOException iOException) {
/* 311 */         notifyEvent(103);
/* 312 */         throw new PrintException(iOException);
/*     */       }  
/* 314 */     if (docFlavor.equals(DocFlavor.URL.GIF) || docFlavor
/* 315 */       .equals(DocFlavor.URL.JPEG) || docFlavor
/* 316 */       .equals(DocFlavor.URL.PNG))
/*     */       try {
/* 318 */         printableJob(new ImagePrinter((URL)object), this.reqAttrSet);
/*     */         return;
/* 320 */       } catch (ClassCastException classCastException) {
/* 321 */         notifyEvent(103);
/* 322 */         throw new PrintException(classCastException);
/*     */       }  
/* 324 */     if (str.equals("java.awt.print.Pageable"))
/*     */       try {
/* 326 */         pageableJob((Pageable)paramDoc.getPrintData(), this.reqAttrSet);
/*     */         return;
/* 328 */       } catch (ClassCastException classCastException) {
/* 329 */         notifyEvent(103);
/* 330 */         throw new PrintException(classCastException);
/* 331 */       } catch (IOException iOException) {
/* 332 */         notifyEvent(103);
/* 333 */         throw new PrintException(iOException);
/*     */       }  
/* 335 */     if (str.equals("java.awt.print.Printable")) {
/*     */       try {
/* 337 */         printableJob((Printable)paramDoc.getPrintData(), this.reqAttrSet);
/*     */         return;
/* 339 */       } catch (ClassCastException classCastException) {
/* 340 */         notifyEvent(103);
/* 341 */         throw new PrintException(classCastException);
/* 342 */       } catch (IOException iOException) {
/* 343 */         notifyEvent(103);
/* 344 */         throw new PrintException(iOException);
/*     */       } 
/*     */     }
/* 347 */     notifyEvent(103);
/* 348 */     throw new PrintException("unrecognized class: " + str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printableJob(Printable paramPrintable, PrintRequestAttributeSet paramPrintRequestAttributeSet) throws PrintException {
/*     */     try {
/* 356 */       synchronized (this) {
/* 357 */         if (this.job != null) {
/* 358 */           throw new PrintException("already printing");
/*     */         }
/* 360 */         this.job = new PSPrinterJob();
/*     */       } 
/*     */       
/* 363 */       this.job.setPrintService(getPrintService());
/* 364 */       PageFormat pageFormat = new PageFormat();
/* 365 */       if (this.mediaSize != null) {
/* 366 */         Paper paper = new Paper();
/* 367 */         paper.setSize(this.mediaSize.getX(25400) * 72.0D, this.mediaSize
/* 368 */             .getY(25400) * 72.0D);
/* 369 */         paper.setImageableArea(72.0D, 72.0D, paper.getWidth() - 144.0D, paper
/* 370 */             .getHeight() - 144.0D);
/* 371 */         pageFormat.setPaper(paper);
/*     */       } 
/* 373 */       if (this.orient == OrientationRequested.REVERSE_LANDSCAPE) {
/* 374 */         pageFormat.setOrientation(2);
/* 375 */       } else if (this.orient == OrientationRequested.LANDSCAPE) {
/* 376 */         pageFormat.setOrientation(0);
/*     */       } 
/* 378 */       this.job.setPrintable(paramPrintable, pageFormat);
/* 379 */       this.job.print(paramPrintRequestAttributeSet);
/* 380 */       notifyEvent(102);
/*     */       return;
/* 382 */     } catch (PrinterException printerException) {
/* 383 */       notifyEvent(103);
/* 384 */       throw new PrintException(printerException);
/*     */     } finally {
/* 386 */       this.printReturned = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void pageableJob(Pageable paramPageable, PrintRequestAttributeSet paramPrintRequestAttributeSet) throws PrintException {
/*     */     try {
/* 394 */       synchronized (this) {
/* 395 */         if (this.job != null) {
/* 396 */           throw new PrintException("already printing");
/*     */         }
/* 398 */         this.job = new PSPrinterJob();
/*     */       } 
/*     */       
/* 401 */       this.job.setPrintService(getPrintService());
/* 402 */       this.job.setPageable(paramPageable);
/* 403 */       this.job.print(paramPrintRequestAttributeSet);
/* 404 */       notifyEvent(102);
/*     */       return;
/* 406 */     } catch (PrinterException printerException) {
/* 407 */       notifyEvent(103);
/* 408 */       throw new PrintException(printerException);
/*     */     } finally {
/* 410 */       this.printReturned = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void initializeAttributeSets(Doc paramDoc, PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/* 420 */     this.reqAttrSet = new HashPrintRequestAttributeSet();
/* 421 */     this.jobAttrSet = new HashPrintJobAttributeSet();
/*     */ 
/*     */     
/* 424 */     if (paramPrintRequestAttributeSet != null) {
/* 425 */       this.reqAttrSet.addAll(paramPrintRequestAttributeSet);
/* 426 */       Attribute[] arrayOfAttribute = paramPrintRequestAttributeSet.toArray();
/* 427 */       for (byte b = 0; b < arrayOfAttribute.length; b++) {
/* 428 */         if (arrayOfAttribute[b] instanceof javax.print.attribute.PrintJobAttribute) {
/* 429 */           this.jobAttrSet.add(arrayOfAttribute[b]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 434 */     DocAttributeSet docAttributeSet = paramDoc.getAttributes();
/* 435 */     if (docAttributeSet != null) {
/* 436 */       Attribute[] arrayOfAttribute = docAttributeSet.toArray();
/* 437 */       for (byte b = 0; b < arrayOfAttribute.length; b++) {
/* 438 */         if (arrayOfAttribute[b] instanceof javax.print.attribute.PrintRequestAttribute) {
/* 439 */           this.reqAttrSet.add(arrayOfAttribute[b]);
/*     */         }
/* 441 */         if (arrayOfAttribute[b] instanceof javax.print.attribute.PrintJobAttribute) {
/* 442 */           this.jobAttrSet.add(arrayOfAttribute[b]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 448 */     String str = "";
/*     */     try {
/* 450 */       str = System.getProperty("user.name");
/* 451 */     } catch (SecurityException securityException) {}
/*     */ 
/*     */     
/* 454 */     if (str == null || str.equals("")) {
/*     */       
/* 456 */       RequestingUserName requestingUserName = (RequestingUserName)paramPrintRequestAttributeSet.get(RequestingUserName.class);
/* 457 */       if (requestingUserName != null) {
/* 458 */         this.jobAttrSet.add(new JobOriginatingUserName(requestingUserName
/* 459 */               .getValue(), requestingUserName
/* 460 */               .getLocale()));
/*     */       } else {
/* 462 */         this.jobAttrSet.add(new JobOriginatingUserName("", null));
/*     */       } 
/*     */     } else {
/* 465 */       this.jobAttrSet.add(new JobOriginatingUserName(str, null));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 470 */     if (this.jobAttrSet.get(JobName.class) == null)
/*     */     {
/* 472 */       if (docAttributeSet != null && docAttributeSet.get(DocumentName.class) != null) {
/*     */         
/* 474 */         DocumentName documentName = (DocumentName)docAttributeSet.get(DocumentName.class);
/* 475 */         JobName jobName = new JobName(documentName.getValue(), documentName.getLocale());
/* 476 */         this.jobAttrSet.add(jobName);
/*     */       } else {
/* 478 */         String str1 = "JPS Job:" + paramDoc;
/*     */         try {
/* 480 */           Object object = paramDoc.getPrintData();
/* 481 */           if (object instanceof URL) {
/* 482 */             str1 = ((URL)paramDoc.getPrintData()).toString();
/*     */           }
/* 484 */         } catch (IOException iOException) {}
/*     */         
/* 486 */         JobName jobName = new JobName(str1, null);
/* 487 */         this.jobAttrSet.add(jobName);
/*     */       } 
/*     */     }
/*     */     
/* 491 */     this.jobAttrSet = AttributeSetUtilities.unmodifiableView(this.jobAttrSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getAttributeValues(DocFlavor paramDocFlavor) throws PrintException {
/* 499 */     if (this.reqAttrSet.get(Fidelity.class) == Fidelity.FIDELITY_TRUE) {
/* 500 */       this.fidelity = true;
/*     */     } else {
/* 502 */       this.fidelity = false;
/*     */     } 
/*     */     
/* 505 */     Attribute[] arrayOfAttribute = this.reqAttrSet.toArray();
/* 506 */     for (byte b = 0; b < arrayOfAttribute.length; b++) {
/* 507 */       Attribute attribute = arrayOfAttribute[b];
/* 508 */       Class<? extends Attribute> clazz = attribute.getCategory();
/* 509 */       if (this.fidelity == true) {
/* 510 */         if (!this.service.isAttributeCategorySupported(clazz)) {
/* 511 */           notifyEvent(103);
/* 512 */           throw new PrintJobAttributeException("unsupported category: " + clazz, clazz, null);
/*     */         } 
/*     */         
/* 515 */         if (!this.service.isAttributeValueSupported(attribute, paramDocFlavor, null)) {
/* 516 */           notifyEvent(103);
/* 517 */           throw new PrintJobAttributeException("unsupported attribute: " + attribute, null, attribute);
/*     */         } 
/*     */       } 
/*     */       
/* 521 */       if (clazz == JobName.class) {
/* 522 */         this.jobName = ((JobName)attribute).getValue();
/* 523 */       } else if (clazz == Copies.class) {
/* 524 */         this.copies = ((Copies)attribute).getValue();
/* 525 */       } else if (clazz == Media.class) {
/* 526 */         if (attribute instanceof MediaSizeName && this.service
/* 527 */           .isAttributeValueSupported(attribute, null, null)) {
/* 528 */           this
/* 529 */             .mediaSize = MediaSize.getMediaSizeForName((MediaSizeName)attribute);
/*     */         }
/* 531 */       } else if (clazz == OrientationRequested.class) {
/* 532 */         this.orient = (OrientationRequested)attribute;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancel() throws PrintException {
/* 539 */     synchronized (this) {
/* 540 */       if (!this.printing)
/* 541 */         throw new PrintException("Job is not yet submitted."); 
/* 542 */       if (this.job != null && !this.printReturned) {
/* 543 */         this.job.cancel();
/* 544 */         notifyEvent(101);
/*     */         return;
/*     */       } 
/* 547 */       throw new PrintException("Job could not be cancelled.");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/PSStreamPrintJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */