/*      */ package sun.print;
/*      */ 
/*      */ import java.awt.print.PageFormat;
/*      */ import java.awt.print.Pageable;
/*      */ import java.awt.print.Paper;
/*      */ import java.awt.print.Printable;
/*      */ import java.awt.print.PrinterException;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.StringWriter;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.attribute.FileAttribute;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Vector;
/*      */ import javax.print.CancelablePrintJob;
/*      */ import javax.print.Doc;
/*      */ import javax.print.DocFlavor;
/*      */ import javax.print.PrintException;
/*      */ import javax.print.PrintService;
/*      */ import javax.print.attribute.Attribute;
/*      */ import javax.print.attribute.AttributeSetUtilities;
/*      */ import javax.print.attribute.DocAttributeSet;
/*      */ import javax.print.attribute.HashPrintJobAttributeSet;
/*      */ import javax.print.attribute.HashPrintRequestAttributeSet;
/*      */ import javax.print.attribute.PrintJobAttributeSet;
/*      */ import javax.print.attribute.PrintRequestAttributeSet;
/*      */ import javax.print.attribute.standard.Copies;
/*      */ import javax.print.attribute.standard.Destination;
/*      */ import javax.print.attribute.standard.DocumentName;
/*      */ import javax.print.attribute.standard.Fidelity;
/*      */ import javax.print.attribute.standard.JobName;
/*      */ import javax.print.attribute.standard.JobOriginatingUserName;
/*      */ import javax.print.attribute.standard.JobSheets;
/*      */ import javax.print.attribute.standard.Media;
/*      */ import javax.print.attribute.standard.MediaSize;
/*      */ import javax.print.attribute.standard.MediaSizeName;
/*      */ import javax.print.attribute.standard.NumberUp;
/*      */ import javax.print.attribute.standard.OrientationRequested;
/*      */ import javax.print.attribute.standard.PrinterIsAcceptingJobs;
/*      */ import javax.print.attribute.standard.RequestingUserName;
/*      */ import javax.print.attribute.standard.Sides;
/*      */ import javax.print.event.PrintJobAttributeListener;
/*      */ import javax.print.event.PrintJobEvent;
/*      */ import javax.print.event.PrintJobListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class UnixPrintJob
/*      */   implements CancelablePrintJob
/*      */ {
/*   91 */   private static String debugPrefix = "UnixPrintJob>> ";
/*      */   
/*      */   private transient Vector jobListeners;
/*      */   
/*      */   private transient Vector attrListeners;
/*      */   private transient Vector listenedAttributeSets;
/*      */   private PrintService service;
/*      */   private boolean fidelity;
/*      */   private boolean printing = false;
/*      */   private boolean printReturned = false;
/*  101 */   private PrintRequestAttributeSet reqAttrSet = null;
/*  102 */   private PrintJobAttributeSet jobAttrSet = null;
/*      */ 
/*      */   
/*      */   private PrinterJob job;
/*      */   
/*      */   private Doc doc;
/*      */   
/*  109 */   private InputStream instream = null;
/*  110 */   private Reader reader = null;
/*      */ 
/*      */   
/*  113 */   private String jobName = "Java Printing";
/*  114 */   private int copies = 1;
/*  115 */   private MediaSizeName mediaName = MediaSizeName.NA_LETTER;
/*  116 */   private MediaSize mediaSize = MediaSize.NA.LETTER;
/*  117 */   private CustomMediaTray customTray = null;
/*  118 */   private OrientationRequested orient = OrientationRequested.PORTRAIT;
/*  119 */   private NumberUp nUp = null;
/*  120 */   private Sides sides = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrintService getPrintService() {
/*  132 */     return this.service;
/*      */   }
/*      */   
/*      */   public PrintJobAttributeSet getAttributes() {
/*  136 */     synchronized (this) {
/*  137 */       if (this.jobAttrSet == null) {
/*      */         
/*  139 */         HashPrintJobAttributeSet hashPrintJobAttributeSet = new HashPrintJobAttributeSet();
/*  140 */         return AttributeSetUtilities.unmodifiableView(hashPrintJobAttributeSet);
/*      */       } 
/*  142 */       return this.jobAttrSet;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addPrintJobListener(PrintJobListener paramPrintJobListener) {
/*  148 */     synchronized (this) {
/*  149 */       if (paramPrintJobListener == null) {
/*      */         return;
/*      */       }
/*  152 */       if (this.jobListeners == null) {
/*  153 */         this.jobListeners = new Vector();
/*      */       }
/*  155 */       this.jobListeners.add(paramPrintJobListener);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void removePrintJobListener(PrintJobListener paramPrintJobListener) {
/*  160 */     synchronized (this) {
/*  161 */       if (paramPrintJobListener == null || this.jobListeners == null) {
/*      */         return;
/*      */       }
/*  164 */       this.jobListeners.remove(paramPrintJobListener);
/*  165 */       if (this.jobListeners.isEmpty()) {
/*  166 */         this.jobListeners = null;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void closeDataStreams() {
/*  180 */     if (this.doc == null) {
/*      */       return;
/*      */     }
/*      */     
/*  184 */     Object object = null;
/*      */     
/*      */     try {
/*  187 */       object = this.doc.getPrintData();
/*  188 */     } catch (IOException iOException) {
/*      */       return;
/*      */     } 
/*      */     
/*  192 */     if (this.instream != null) {
/*      */       
/*  194 */       try { this.instream.close(); }
/*  195 */       catch (IOException iOException) {  }
/*      */       finally
/*  197 */       { this.instream = null; }
/*      */ 
/*      */     
/*  200 */     } else if (this.reader != null) {
/*      */       
/*  202 */       try { this.reader.close(); }
/*  203 */       catch (IOException iOException) {  }
/*      */       finally
/*  205 */       { this.reader = null; }
/*      */ 
/*      */     
/*  208 */     } else if (object instanceof InputStream) {
/*      */       try {
/*  210 */         ((InputStream)object).close();
/*  211 */       } catch (IOException iOException) {}
/*      */     
/*      */     }
/*  214 */     else if (object instanceof Reader) {
/*      */       try {
/*  216 */         ((Reader)object).close();
/*  217 */       } catch (IOException iOException) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void notifyEvent(int paramInt) {
/*  227 */     switch (paramInt) {
/*      */       case 101:
/*      */       case 102:
/*      */       case 103:
/*      */       case 105:
/*      */       case 106:
/*  233 */         closeDataStreams();
/*      */         break;
/*      */     } 
/*  236 */     synchronized (this) {
/*  237 */       if (this.jobListeners != null) {
/*      */         
/*  239 */         PrintJobEvent printJobEvent = new PrintJobEvent(this, paramInt);
/*  240 */         for (byte b = 0; b < this.jobListeners.size(); b++) {
/*  241 */           PrintJobListener printJobListener = this.jobListeners.elementAt(b);
/*  242 */           switch (paramInt) {
/*      */             
/*      */             case 101:
/*  245 */               printJobListener.printJobCanceled(printJobEvent);
/*      */               break;
/*      */             
/*      */             case 103:
/*  249 */               printJobListener.printJobFailed(printJobEvent);
/*      */               break;
/*      */             
/*      */             case 106:
/*  253 */               printJobListener.printDataTransferCompleted(printJobEvent);
/*      */               break;
/*      */             
/*      */             case 105:
/*  257 */               printJobListener.printJobNoMoreEvents(printJobEvent);
/*      */               break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPrintJobAttributeListener(PrintJobAttributeListener paramPrintJobAttributeListener, PrintJobAttributeSet paramPrintJobAttributeSet) {
/*  271 */     synchronized (this) {
/*  272 */       if (paramPrintJobAttributeListener == null) {
/*      */         return;
/*      */       }
/*  275 */       if (this.attrListeners == null) {
/*  276 */         this.attrListeners = new Vector();
/*  277 */         this.listenedAttributeSets = new Vector();
/*      */       } 
/*  279 */       this.attrListeners.add(paramPrintJobAttributeListener);
/*  280 */       if (paramPrintJobAttributeSet == null) {
/*  281 */         paramPrintJobAttributeSet = new HashPrintJobAttributeSet();
/*      */       }
/*  283 */       this.listenedAttributeSets.add(paramPrintJobAttributeSet);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void removePrintJobAttributeListener(PrintJobAttributeListener paramPrintJobAttributeListener) {
/*  289 */     synchronized (this) {
/*  290 */       if (paramPrintJobAttributeListener == null || this.attrListeners == null) {
/*      */         return;
/*      */       }
/*  293 */       int i = this.attrListeners.indexOf(paramPrintJobAttributeListener);
/*  294 */       if (i == -1) {
/*      */         return;
/*      */       }
/*  297 */       this.attrListeners.remove(i);
/*  298 */       this.listenedAttributeSets.remove(i);
/*  299 */       if (this.attrListeners.isEmpty()) {
/*  300 */         this.attrListeners = null;
/*  301 */         this.listenedAttributeSets = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void print(Doc paramDoc, PrintRequestAttributeSet paramPrintRequestAttributeSet) throws PrintException {
/*      */     Object object;
/*  310 */     synchronized (this) {
/*  311 */       if (this.printing) {
/*  312 */         throw new PrintException("already printing");
/*      */       }
/*  314 */       this.printing = true;
/*      */     } 
/*      */ 
/*      */     
/*  318 */     if ((PrinterIsAcceptingJobs)this.service.<PrinterIsAcceptingJobs>getAttribute(PrinterIsAcceptingJobs.class) == PrinterIsAcceptingJobs.NOT_ACCEPTING_JOBS)
/*      */     {
/*      */       
/*  321 */       throw new PrintException("Printer is not accepting job.");
/*      */     }
/*      */     
/*  324 */     this.doc = paramDoc;
/*      */     
/*  326 */     DocFlavor docFlavor = paramDoc.getDocFlavor();
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  331 */       object = paramDoc.getPrintData();
/*  332 */     } catch (IOException iOException) {
/*  333 */       notifyEvent(103);
/*  334 */       throw new PrintException("can't get print data: " + iOException.toString());
/*      */     } 
/*      */     
/*  337 */     if (object == null) {
/*  338 */       throw new PrintException("Null print data.");
/*      */     }
/*      */     
/*  341 */     if (docFlavor == null || !this.service.isDocFlavorSupported(docFlavor)) {
/*  342 */       notifyEvent(103);
/*  343 */       throw new PrintJobFlavorException("invalid flavor", docFlavor);
/*      */     } 
/*      */     
/*  346 */     initializeAttributeSets(paramDoc, paramPrintRequestAttributeSet);
/*      */     
/*  348 */     getAttributeValues(docFlavor);
/*      */ 
/*      */     
/*  351 */     if (this.service instanceof IPPPrintService && 
/*  352 */       CUPSPrinter.isCupsRunning()) {
/*      */       
/*  354 */       IPPPrintService.debug_println(debugPrefix + "instanceof IPPPrintService");
/*      */ 
/*      */       
/*  357 */       if (this.mediaName != null) {
/*      */         
/*  359 */         CustomMediaSizeName customMediaSizeName = ((IPPPrintService)this.service).findCustomMedia(this.mediaName);
/*  360 */         if (customMediaSizeName != null) {
/*  361 */           this.mOptions = " media=" + customMediaSizeName.getChoiceName();
/*      */         }
/*      */       } 
/*      */       
/*  365 */       if (this.customTray != null && this.customTray instanceof CustomMediaTray) {
/*      */         
/*  367 */         String str = this.customTray.getChoiceName();
/*  368 */         if (str != null) {
/*  369 */           this.mOptions += " media=" + str;
/*      */         }
/*      */       } 
/*      */       
/*  373 */       if (this.nUp != null) {
/*  374 */         this.mOptions += " number-up=" + this.nUp.getValue();
/*      */       }
/*      */       
/*  377 */       if (this.orient != OrientationRequested.PORTRAIT && docFlavor != null && 
/*      */         
/*  379 */         !docFlavor.equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE)) {
/*  380 */         this.mOptions += " orientation-requested=" + this.orient.getValue();
/*      */       }
/*      */       
/*  383 */       if (this.sides != null) {
/*  384 */         this.mOptions += " sides=" + this.sides;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  389 */     IPPPrintService.debug_println(debugPrefix + "mOptions " + this.mOptions);
/*  390 */     String str1 = docFlavor.getRepresentationClassName();
/*  391 */     String str2 = docFlavor.getParameter("charset");
/*  392 */     String str3 = "us-ascii";
/*  393 */     if (str2 != null && !str2.equals("")) {
/*  394 */       str3 = str2;
/*      */     }
/*      */     
/*  397 */     if (docFlavor.equals(DocFlavor.INPUT_STREAM.GIF) || docFlavor
/*  398 */       .equals(DocFlavor.INPUT_STREAM.JPEG) || docFlavor
/*  399 */       .equals(DocFlavor.INPUT_STREAM.PNG) || docFlavor
/*  400 */       .equals(DocFlavor.BYTE_ARRAY.GIF) || docFlavor
/*  401 */       .equals(DocFlavor.BYTE_ARRAY.JPEG) || docFlavor
/*  402 */       .equals(DocFlavor.BYTE_ARRAY.PNG))
/*      */     { try {
/*  404 */         this.instream = paramDoc.getStreamForBytes();
/*  405 */         if (this.instream == null) {
/*  406 */           notifyEvent(103);
/*  407 */           throw new PrintException("No stream for data");
/*      */         } 
/*  409 */         if (!(this.service instanceof IPPPrintService) || 
/*  410 */           !((IPPPrintService)this.service).isIPPSupportedImages(docFlavor
/*  411 */             .getMimeType())) {
/*  412 */           printableJob(new ImagePrinter(this.instream));
/*  413 */           ((UnixPrintService)this.service).wakeNotifier();
/*      */           return;
/*      */         } 
/*  416 */       } catch (ClassCastException classCastException) {
/*  417 */         notifyEvent(103);
/*  418 */         throw new PrintException(classCastException);
/*  419 */       } catch (IOException iOException) {
/*  420 */         notifyEvent(103);
/*  421 */         throw new PrintException(iOException);
/*      */       }  }
/*  423 */     else if (docFlavor.equals(DocFlavor.URL.GIF) || docFlavor
/*  424 */       .equals(DocFlavor.URL.JPEG) || docFlavor
/*  425 */       .equals(DocFlavor.URL.PNG))
/*      */     { try {
/*  427 */         URL uRL = (URL)object;
/*  428 */         if (this.service instanceof IPPPrintService && ((IPPPrintService)this.service)
/*  429 */           .isIPPSupportedImages(docFlavor
/*  430 */             .getMimeType())) {
/*  431 */           this.instream = uRL.openStream();
/*      */         } else {
/*  433 */           printableJob(new ImagePrinter(uRL));
/*  434 */           ((UnixPrintService)this.service).wakeNotifier();
/*      */           return;
/*      */         } 
/*  437 */       } catch (ClassCastException classCastException) {
/*  438 */         notifyEvent(103);
/*  439 */         throw new PrintException(classCastException);
/*  440 */       } catch (IOException iOException) {
/*  441 */         notifyEvent(103);
/*  442 */         throw new PrintException(iOException.toString());
/*      */       }  }
/*  444 */     else if (docFlavor.equals(DocFlavor.CHAR_ARRAY.TEXT_PLAIN) || docFlavor
/*  445 */       .equals(DocFlavor.READER.TEXT_PLAIN) || docFlavor
/*  446 */       .equals(DocFlavor.STRING.TEXT_PLAIN))
/*      */     { try {
/*  448 */         this.reader = paramDoc.getReaderForText();
/*  449 */         if (this.reader == null) {
/*  450 */           notifyEvent(103);
/*  451 */           throw new PrintException("No reader for data");
/*      */         } 
/*  453 */       } catch (IOException iOException) {
/*  454 */         notifyEvent(103);
/*  455 */         throw new PrintException(iOException.toString());
/*      */       }  }
/*  457 */     else if (str1.equals("[B") || str1
/*  458 */       .equals("java.io.InputStream"))
/*      */     { try {
/*  460 */         this.instream = paramDoc.getStreamForBytes();
/*  461 */         if (this.instream == null) {
/*  462 */           notifyEvent(103);
/*  463 */           throw new PrintException("No stream for data");
/*      */         } 
/*  465 */       } catch (IOException iOException) {
/*  466 */         notifyEvent(103);
/*  467 */         throw new PrintException(iOException.toString());
/*      */       }  }
/*  469 */     else if (str1.equals("java.net.URL"))
/*      */     
/*      */     { 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  480 */       URL uRL = (URL)object;
/*      */       try {
/*  482 */         this.instream = uRL.openStream();
/*  483 */       } catch (IOException iOException) {
/*  484 */         notifyEvent(103);
/*  485 */         throw new PrintException(iOException.toString());
/*      */       }  }
/*  487 */     else { if (str1.equals("java.awt.print.Pageable"))
/*      */         try {
/*  489 */           pageableJob((Pageable)paramDoc.getPrintData());
/*  490 */           if (this.service instanceof IPPPrintService) {
/*  491 */             ((IPPPrintService)this.service).wakeNotifier();
/*      */           } else {
/*  493 */             ((UnixPrintService)this.service).wakeNotifier();
/*      */           } 
/*      */           return;
/*  496 */         } catch (ClassCastException classCastException) {
/*  497 */           notifyEvent(103);
/*  498 */           throw new PrintException(classCastException);
/*  499 */         } catch (IOException iOException) {
/*  500 */           notifyEvent(103);
/*  501 */           throw new PrintException(iOException);
/*      */         }  
/*  503 */       if (str1.equals("java.awt.print.Printable")) {
/*      */         try {
/*  505 */           printableJob((Printable)paramDoc.getPrintData());
/*  506 */           if (this.service instanceof IPPPrintService) {
/*  507 */             ((IPPPrintService)this.service).wakeNotifier();
/*      */           } else {
/*  509 */             ((UnixPrintService)this.service).wakeNotifier();
/*      */           } 
/*      */           return;
/*  512 */         } catch (ClassCastException classCastException) {
/*  513 */           notifyEvent(103);
/*  514 */           throw new PrintException(classCastException);
/*  515 */         } catch (IOException iOException) {
/*  516 */           notifyEvent(103);
/*  517 */           throw new PrintException(iOException);
/*      */         } 
/*      */       }
/*  520 */       notifyEvent(103);
/*  521 */       throw new PrintException("unrecognized class: " + str1); }
/*      */ 
/*      */ 
/*      */     
/*  525 */     PrinterOpener printerOpener = new PrinterOpener();
/*  526 */     AccessController.doPrivileged(printerOpener);
/*  527 */     if (printerOpener.pex != null) {
/*  528 */       throw printerOpener.pex;
/*      */     }
/*  530 */     OutputStream outputStream = printerOpener.result;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  540 */     BufferedWriter bufferedWriter = null;
/*  541 */     if (this.instream == null && this.reader != null) {
/*  542 */       BufferedReader bufferedReader = new BufferedReader(this.reader);
/*  543 */       OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
/*  544 */       bufferedWriter = new BufferedWriter(outputStreamWriter);
/*  545 */       char[] arrayOfChar = new char[1024];
/*      */       
/*      */       try {
/*      */         int i;
/*  549 */         while ((i = bufferedReader.read(arrayOfChar, 0, arrayOfChar.length)) >= 0) {
/*  550 */           bufferedWriter.write(arrayOfChar, 0, i);
/*      */         }
/*  552 */         bufferedReader.close();
/*  553 */         bufferedWriter.flush();
/*  554 */         bufferedWriter.close();
/*  555 */       } catch (IOException iOException) {
/*  556 */         notifyEvent(103);
/*  557 */         throw new PrintException(iOException);
/*      */       } 
/*  559 */     } else if (this.instream != null && docFlavor
/*  560 */       .getMediaType().equalsIgnoreCase("text")) {
/*      */       
/*      */       try {
/*  563 */         InputStreamReader inputStreamReader = new InputStreamReader(this.instream, str3);
/*      */         
/*  565 */         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
/*  566 */         OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
/*  567 */         bufferedWriter = new BufferedWriter(outputStreamWriter);
/*  568 */         char[] arrayOfChar = new char[1024];
/*      */         
/*      */         int i;
/*  571 */         while ((i = bufferedReader.read(arrayOfChar, 0, arrayOfChar.length)) >= 0) {
/*  572 */           bufferedWriter.write(arrayOfChar, 0, i);
/*      */         }
/*  574 */         bufferedWriter.flush();
/*  575 */       } catch (IOException iOException) {
/*  576 */         notifyEvent(103);
/*  577 */         throw new PrintException(iOException);
/*      */       } finally {
/*      */         try {
/*  580 */           if (bufferedWriter != null) {
/*  581 */             bufferedWriter.close();
/*      */           }
/*  583 */         } catch (IOException iOException) {}
/*      */       }
/*      */     
/*  586 */     } else if (this.instream != null) {
/*  587 */       BufferedInputStream bufferedInputStream = new BufferedInputStream(this.instream);
/*  588 */       BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
/*  589 */       byte[] arrayOfByte = new byte[1024];
/*  590 */       int i = 0;
/*      */       
/*      */       try {
/*  593 */         while ((i = bufferedInputStream.read(arrayOfByte)) >= 0) {
/*  594 */           bufferedOutputStream.write(arrayOfByte, 0, i);
/*      */         }
/*  596 */         bufferedInputStream.close();
/*  597 */         bufferedOutputStream.flush();
/*  598 */         bufferedOutputStream.close();
/*  599 */       } catch (IOException iOException) {
/*  600 */         notifyEvent(103);
/*  601 */         throw new PrintException(iOException);
/*      */       } 
/*      */     } 
/*  604 */     notifyEvent(106);
/*      */     
/*  606 */     if (this.mDestType == DESTPRINTER) {
/*  607 */       PrinterSpooler printerSpooler = new PrinterSpooler();
/*  608 */       AccessController.doPrivileged(printerSpooler);
/*  609 */       if (printerSpooler.pex != null) {
/*  610 */         throw printerSpooler.pex;
/*      */       }
/*      */     } 
/*  613 */     notifyEvent(105);
/*  614 */     if (this.service instanceof IPPPrintService) {
/*  615 */       ((IPPPrintService)this.service).wakeNotifier();
/*      */     } else {
/*  617 */       ((UnixPrintService)this.service).wakeNotifier();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void printableJob(Printable paramPrintable) throws PrintException {
/*      */     try {
/*  623 */       synchronized (this) {
/*  624 */         if (this.job != null) {
/*  625 */           throw new PrintException("already printing");
/*      */         }
/*  627 */         this.job = new PSPrinterJob();
/*      */       } 
/*      */       
/*  630 */       this.job.setPrintService(getPrintService());
/*  631 */       this.job.setCopies(this.copies);
/*  632 */       this.job.setJobName(this.jobName);
/*  633 */       PageFormat pageFormat = new PageFormat();
/*  634 */       if (this.mediaSize != null) {
/*  635 */         Paper paper = new Paper();
/*  636 */         paper.setSize(this.mediaSize.getX(25400) * 72.0D, this.mediaSize
/*  637 */             .getY(25400) * 72.0D);
/*  638 */         paper.setImageableArea(72.0D, 72.0D, paper.getWidth() - 144.0D, paper
/*  639 */             .getHeight() - 144.0D);
/*  640 */         pageFormat.setPaper(paper);
/*      */       } 
/*  642 */       if (this.orient == OrientationRequested.REVERSE_LANDSCAPE) {
/*  643 */         pageFormat.setOrientation(2);
/*  644 */       } else if (this.orient == OrientationRequested.LANDSCAPE) {
/*  645 */         pageFormat.setOrientation(0);
/*      */       } 
/*  647 */       this.job.setPrintable(paramPrintable, pageFormat);
/*  648 */       this.job.print(this.reqAttrSet);
/*  649 */       notifyEvent(106);
/*      */       return;
/*  651 */     } catch (PrinterException printerException) {
/*  652 */       notifyEvent(103);
/*  653 */       throw new PrintException(printerException);
/*      */     } finally {
/*  655 */       this.printReturned = true;
/*  656 */       notifyEvent(105);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void pageableJob(Pageable paramPageable) throws PrintException {
/*      */     try {
/*  662 */       synchronized (this) {
/*  663 */         if (this.job != null) {
/*  664 */           throw new PrintException("already printing");
/*      */         }
/*  666 */         this.job = new PSPrinterJob();
/*      */       } 
/*      */       
/*  669 */       this.job.setPrintService(getPrintService());
/*  670 */       this.job.setCopies(this.copies);
/*  671 */       this.job.setJobName(this.jobName);
/*  672 */       this.job.setPageable(paramPageable);
/*  673 */       this.job.print(this.reqAttrSet);
/*  674 */       notifyEvent(106);
/*      */       return;
/*  676 */     } catch (PrinterException printerException) {
/*  677 */       notifyEvent(103);
/*  678 */       throw new PrintException(printerException);
/*      */     } finally {
/*  680 */       this.printReturned = true;
/*  681 */       notifyEvent(105);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void initializeAttributeSets(Doc paramDoc, PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/*  690 */     this.reqAttrSet = new HashPrintRequestAttributeSet();
/*  691 */     this.jobAttrSet = new HashPrintJobAttributeSet();
/*      */ 
/*      */     
/*  694 */     if (paramPrintRequestAttributeSet != null) {
/*  695 */       this.reqAttrSet.addAll(paramPrintRequestAttributeSet);
/*  696 */       Attribute[] arrayOfAttribute = paramPrintRequestAttributeSet.toArray();
/*  697 */       for (byte b = 0; b < arrayOfAttribute.length; b++) {
/*  698 */         if (arrayOfAttribute[b] instanceof javax.print.attribute.PrintJobAttribute) {
/*  699 */           this.jobAttrSet.add(arrayOfAttribute[b]);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  704 */     DocAttributeSet docAttributeSet = paramDoc.getAttributes();
/*  705 */     if (docAttributeSet != null) {
/*  706 */       Attribute[] arrayOfAttribute = docAttributeSet.toArray();
/*  707 */       for (byte b = 0; b < arrayOfAttribute.length; b++) {
/*  708 */         if (arrayOfAttribute[b] instanceof javax.print.attribute.PrintRequestAttribute) {
/*  709 */           this.reqAttrSet.add(arrayOfAttribute[b]);
/*      */         }
/*  711 */         if (arrayOfAttribute[b] instanceof javax.print.attribute.PrintJobAttribute) {
/*  712 */           this.jobAttrSet.add(arrayOfAttribute[b]);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  718 */     String str = "";
/*      */     try {
/*  720 */       str = System.getProperty("user.name");
/*  721 */     } catch (SecurityException securityException) {}
/*      */ 
/*      */     
/*  724 */     if (str == null || str.equals("")) {
/*      */       
/*  726 */       RequestingUserName requestingUserName = (RequestingUserName)paramPrintRequestAttributeSet.get(RequestingUserName.class);
/*  727 */       if (requestingUserName != null) {
/*  728 */         this.jobAttrSet.add(new JobOriginatingUserName(requestingUserName
/*  729 */               .getValue(), requestingUserName
/*  730 */               .getLocale()));
/*      */       } else {
/*  732 */         this.jobAttrSet.add(new JobOriginatingUserName("", null));
/*      */       } 
/*      */     } else {
/*  735 */       this.jobAttrSet.add(new JobOriginatingUserName(str, null));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  740 */     if (this.jobAttrSet.get(JobName.class) == null)
/*      */     {
/*  742 */       if (docAttributeSet != null && docAttributeSet.get(DocumentName.class) != null) {
/*      */         
/*  744 */         DocumentName documentName = (DocumentName)docAttributeSet.get(DocumentName.class);
/*  745 */         JobName jobName = new JobName(documentName.getValue(), documentName.getLocale());
/*  746 */         this.jobAttrSet.add(jobName);
/*      */       } else {
/*  748 */         String str1 = "JPS Job:" + paramDoc;
/*      */         try {
/*  750 */           Object object = paramDoc.getPrintData();
/*  751 */           if (object instanceof URL) {
/*  752 */             str1 = ((URL)paramDoc.getPrintData()).toString();
/*      */           }
/*  754 */         } catch (IOException iOException) {}
/*      */         
/*  756 */         JobName jobName = new JobName(str1, null);
/*  757 */         this.jobAttrSet.add(jobName);
/*      */       } 
/*      */     }
/*      */     
/*  761 */     this.jobAttrSet = AttributeSetUtilities.unmodifiableView(this.jobAttrSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getAttributeValues(DocFlavor paramDocFlavor) throws PrintException {
/*  768 */     if (this.reqAttrSet.get(Fidelity.class) == Fidelity.FIDELITY_TRUE) {
/*  769 */       this.fidelity = true;
/*      */     } else {
/*  771 */       this.fidelity = false;
/*      */     } 
/*      */     
/*  774 */     Attribute[] arrayOfAttribute = this.reqAttrSet.toArray();
/*  775 */     for (byte b = 0; b < arrayOfAttribute.length; b++) {
/*  776 */       Attribute attribute = arrayOfAttribute[b];
/*  777 */       Class<? extends Attribute> clazz = attribute.getCategory();
/*  778 */       if (this.fidelity == true) {
/*  779 */         if (!this.service.isAttributeCategorySupported(clazz)) {
/*  780 */           notifyEvent(103);
/*  781 */           throw new PrintJobAttributeException("unsupported category: " + clazz, clazz, null);
/*      */         } 
/*      */         
/*  784 */         if (!this.service.isAttributeValueSupported(attribute, paramDocFlavor, null)) {
/*  785 */           notifyEvent(103);
/*  786 */           throw new PrintJobAttributeException("unsupported attribute: " + attribute, null, attribute);
/*      */         } 
/*      */       } 
/*      */       
/*  790 */       if (clazz == Destination.class) {
/*  791 */         URI uRI = ((Destination)attribute).getURI();
/*  792 */         if (!"file".equals(uRI.getScheme())) {
/*  793 */           notifyEvent(103);
/*  794 */           throw new PrintException("Not a file: URI");
/*      */         } 
/*      */         try {
/*  797 */           this.mDestType = DESTFILE;
/*  798 */           this.mDestination = (new File(uRI)).getPath();
/*  799 */         } catch (Exception exception) {
/*  800 */           throw new PrintException(exception);
/*      */         } 
/*      */         
/*  803 */         SecurityManager securityManager = System.getSecurityManager();
/*  804 */         if (securityManager != null) {
/*      */           try {
/*  806 */             securityManager.checkWrite(this.mDestination);
/*  807 */           } catch (SecurityException securityException) {
/*  808 */             notifyEvent(103);
/*  809 */             throw new PrintException(securityException);
/*      */           }
/*      */         
/*      */         }
/*  813 */       } else if (clazz == JobSheets.class) {
/*  814 */         if ((JobSheets)attribute == JobSheets.NONE) {
/*  815 */           this.mNoJobSheet = true;
/*      */         }
/*  817 */       } else if (clazz == JobName.class) {
/*  818 */         this.jobName = ((JobName)attribute).getValue();
/*  819 */       } else if (clazz == Copies.class) {
/*  820 */         this.copies = ((Copies)attribute).getValue();
/*  821 */       } else if (clazz == Media.class) {
/*  822 */         if (attribute instanceof MediaSizeName) {
/*  823 */           this.mediaName = (MediaSizeName)attribute;
/*  824 */           IPPPrintService.debug_println(debugPrefix + "mediaName " + this.mediaName);
/*      */           
/*  826 */           if (!this.service.isAttributeValueSupported(attribute, null, null)) {
/*  827 */             this.mediaSize = MediaSize.getMediaSizeForName(this.mediaName);
/*      */           }
/*  829 */         } else if (attribute instanceof CustomMediaTray) {
/*  830 */           this.customTray = (CustomMediaTray)attribute;
/*      */         } 
/*  832 */       } else if (clazz == OrientationRequested.class) {
/*  833 */         this.orient = (OrientationRequested)attribute;
/*  834 */       } else if (clazz == NumberUp.class) {
/*  835 */         this.nUp = (NumberUp)attribute;
/*  836 */       } else if (clazz == Sides.class) {
/*  837 */         this.sides = (Sides)attribute;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private String[] printExecCmd(String paramString1, String paramString2, boolean paramBoolean, String paramString3, int paramInt, String paramString4) {
/*      */     String[] arrayOfString;
/*  845 */     byte b1 = 1;
/*  846 */     byte b2 = 2;
/*  847 */     byte b3 = 4;
/*  848 */     byte b4 = 8;
/*  849 */     byte b5 = 16;
/*  850 */     int i = 0;
/*      */     
/*  852 */     byte b6 = 2;
/*  853 */     byte b7 = 0;
/*      */ 
/*      */     
/*  856 */     if (paramString1 != null && !paramString1.equals("") && !paramString1.equals("lp")) {
/*  857 */       i |= b1;
/*  858 */       b6++;
/*      */     } 
/*  860 */     if (paramString2 != null && !paramString2.equals("")) {
/*  861 */       i |= b2;
/*  862 */       b6++;
/*      */     } 
/*  864 */     if (paramString3 != null && !paramString3.equals("")) {
/*  865 */       i |= b3;
/*  866 */       b6++;
/*      */     } 
/*  868 */     if (paramInt > 1) {
/*  869 */       i |= b4;
/*  870 */       b6++;
/*      */     } 
/*  872 */     if (paramBoolean) {
/*  873 */       i |= b5;
/*  874 */       b6++;
/*      */     } 
/*  876 */     if (PrintServiceLookupProvider.osname.equals("SunOS")) {
/*  877 */       b6++;
/*  878 */       arrayOfString = new String[b6];
/*  879 */       arrayOfString[b7++] = "/usr/bin/lp";
/*  880 */       arrayOfString[b7++] = "-c";
/*  881 */       if ((i & b1) != 0) {
/*  882 */         arrayOfString[b7++] = "-d" + paramString1;
/*      */       }
/*  884 */       if ((i & b3) != 0) {
/*  885 */         String str = "\"";
/*  886 */         arrayOfString[b7++] = "-t " + str + paramString3 + str;
/*      */       } 
/*  888 */       if ((i & b4) != 0) {
/*  889 */         arrayOfString[b7++] = "-n " + paramInt;
/*      */       }
/*  891 */       if ((i & b5) != 0) {
/*  892 */         arrayOfString[b7++] = "-o nobanner";
/*      */       }
/*  894 */       if ((i & b2) != 0) {
/*  895 */         arrayOfString[b7++] = "-o " + paramString2;
/*      */       }
/*      */     } else {
/*  898 */       arrayOfString = new String[b6];
/*  899 */       arrayOfString[b7++] = "/usr/bin/lpr";
/*  900 */       if ((i & b1) != 0) {
/*  901 */         arrayOfString[b7++] = "-P" + paramString1;
/*      */       }
/*  903 */       if ((i & b3) != 0) {
/*  904 */         arrayOfString[b7++] = "-J " + paramString3;
/*      */       }
/*  906 */       if ((i & b4) != 0) {
/*  907 */         arrayOfString[b7++] = "-#" + paramInt;
/*      */       }
/*  909 */       if ((i & b5) != 0) {
/*  910 */         arrayOfString[b7++] = "-h";
/*      */       }
/*  912 */       if ((i & b2) != 0) {
/*  913 */         arrayOfString[b7++] = "-o" + paramString2;
/*      */       }
/*      */     } 
/*  916 */     arrayOfString[b7++] = paramString4;
/*  917 */     if (IPPPrintService.debugPrint) {
/*  918 */       System.out.println("UnixPrintJob>> execCmd");
/*  919 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*  920 */         System.out.print(" " + arrayOfString[b]);
/*      */       }
/*  922 */       System.out.println();
/*      */     } 
/*  924 */     return arrayOfString;
/*      */   }
/*      */   
/*  927 */   private static int DESTPRINTER = 1; private int mDestType; private File spoolFile; private String mDestination;
/*  928 */   private static int DESTFILE = 2; private String mOptions; private boolean mNoJobSheet; UnixPrintJob(PrintService paramPrintService) {
/*  929 */     this.mDestType = DESTPRINTER;
/*      */ 
/*      */     
/*  932 */     this.mOptions = "";
/*  933 */     this.mNoJobSheet = false;
/*      */     this.service = paramPrintService;
/*      */     this.mDestination = paramPrintService.getName();
/*      */     if (PrintServiceLookupProvider.isMac())
/*      */       this.mDestination = ((IPPPrintService)paramPrintService).getDest(); 
/*      */     this.mDestType = DESTPRINTER;
/*      */   } private class PrinterOpener implements PrivilegedAction { PrintException pex; OutputStream result;
/*      */     private PrinterOpener() {}
/*      */     public Object run() {
/*      */       try {
/*  943 */         if (UnixPrintJob.this.mDestType == UnixPrintJob.DESTFILE) {
/*  944 */           UnixPrintJob.this.spoolFile = new File(UnixPrintJob.this.mDestination);
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  951 */           UnixPrintJob.this.spoolFile = Files.createTempFile("javaprint", "", (FileAttribute<?>[])new FileAttribute[0]).toFile();
/*  952 */           UnixPrintJob.this.spoolFile.deleteOnExit();
/*      */         } 
/*  954 */         this.result = new FileOutputStream(UnixPrintJob.this.spoolFile);
/*  955 */         return this.result;
/*  956 */       } catch (IOException iOException) {
/*      */         
/*  958 */         UnixPrintJob.this.notifyEvent(103);
/*  959 */         this.pex = new PrintException(iOException);
/*      */         
/*  961 */         return null;
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   private class PrinterSpooler implements PrivilegedAction {
/*      */     PrintException pex;
/*      */     
/*      */     private PrinterSpooler() {}
/*      */     
/*      */     private void handleProcessFailure(Process param1Process, String[] param1ArrayOfString, int param1Int) throws IOException {
/*  972 */       try(StringWriter null = new StringWriter(); 
/*  973 */           PrintWriter null = new PrintWriter(stringWriter)) {
/*  974 */         printWriter.append("error=").append(Integer.toString(param1Int));
/*  975 */         printWriter.append(" running:");
/*  976 */         for (String str : param1ArrayOfString) {
/*  977 */           printWriter.append(" '").append(str).append("'");
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object run() {
/*  994 */       if (UnixPrintJob.this.spoolFile == null || !UnixPrintJob.this.spoolFile.exists()) {
/*  995 */         this.pex = new PrintException("No spool file");
/*  996 */         UnixPrintJob.this.notifyEvent(103);
/*  997 */         return null;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1003 */         String str = UnixPrintJob.this.spoolFile.getAbsolutePath();
/* 1004 */         String[] arrayOfString = UnixPrintJob.this.printExecCmd(UnixPrintJob.this.mDestination, UnixPrintJob.this.mOptions, UnixPrintJob.this
/* 1005 */             .mNoJobSheet, UnixPrintJob.this.jobName, UnixPrintJob.this.copies, str);
/*      */         
/* 1007 */         Process process = Runtime.getRuntime().exec(arrayOfString);
/* 1008 */         process.waitFor();
/* 1009 */         int i = process.exitValue();
/* 1010 */         if (0 != i) {
/* 1011 */           handleProcessFailure(process, arrayOfString, i);
/*      */         }
/* 1013 */         UnixPrintJob.this.notifyEvent(106);
/* 1014 */       } catch (IOException iOException) {
/* 1015 */         UnixPrintJob.this.notifyEvent(103);
/*      */         
/* 1017 */         this.pex = new PrintException(iOException);
/* 1018 */       } catch (InterruptedException interruptedException) {
/* 1019 */         UnixPrintJob.this.notifyEvent(103);
/* 1020 */         this.pex = new PrintException(interruptedException);
/*      */       } finally {
/* 1022 */         UnixPrintJob.this.spoolFile.delete();
/* 1023 */         UnixPrintJob.this.notifyEvent(105);
/*      */       } 
/* 1025 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */   public void cancel() throws PrintException {
/* 1030 */     synchronized (this) {
/* 1031 */       if (!this.printing)
/* 1032 */         throw new PrintException("Job is not yet submitted."); 
/* 1033 */       if (this.job != null && !this.printReturned) {
/* 1034 */         this.job.cancel();
/* 1035 */         notifyEvent(101);
/*      */         return;
/*      */       } 
/* 1038 */       throw new PrintException("Job could not be cancelled.");
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/UnixPrintJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */