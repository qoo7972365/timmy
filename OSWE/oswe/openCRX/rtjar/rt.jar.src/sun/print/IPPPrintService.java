/*      */ package sun.print;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.HttpURLConnection;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.print.DocFlavor;
/*      */ import javax.print.PrintService;
/*      */ import javax.print.attribute.Attribute;
/*      */ import javax.print.attribute.AttributeSet;
/*      */ import javax.print.attribute.HashAttributeSet;
/*      */ import javax.print.attribute.HashPrintServiceAttributeSet;
/*      */ import javax.print.attribute.PrintRequestAttribute;
/*      */ import javax.print.attribute.PrintServiceAttributeSet;
/*      */ import javax.print.attribute.standard.Chromaticity;
/*      */ import javax.print.attribute.standard.ColorSupported;
/*      */ import javax.print.attribute.standard.Copies;
/*      */ import javax.print.attribute.standard.CopiesSupported;
/*      */ import javax.print.attribute.standard.Destination;
/*      */ import javax.print.attribute.standard.Fidelity;
/*      */ import javax.print.attribute.standard.Finishings;
/*      */ import javax.print.attribute.standard.JobName;
/*      */ import javax.print.attribute.standard.JobSheets;
/*      */ import javax.print.attribute.standard.Media;
/*      */ import javax.print.attribute.standard.MediaPrintableArea;
/*      */ import javax.print.attribute.standard.MediaSize;
/*      */ import javax.print.attribute.standard.MediaSizeName;
/*      */ import javax.print.attribute.standard.MediaTray;
/*      */ import javax.print.attribute.standard.NumberUp;
/*      */ import javax.print.attribute.standard.OrientationRequested;
/*      */ import javax.print.attribute.standard.PDLOverrideSupported;
/*      */ import javax.print.attribute.standard.PageRanges;
/*      */ import javax.print.attribute.standard.PagesPerMinute;
/*      */ import javax.print.attribute.standard.PagesPerMinuteColor;
/*      */ import javax.print.attribute.standard.PrinterInfo;
/*      */ import javax.print.attribute.standard.PrinterIsAcceptingJobs;
/*      */ import javax.print.attribute.standard.PrinterMessageFromOperator;
/*      */ import javax.print.attribute.standard.PrinterName;
/*      */ import javax.print.attribute.standard.PrinterStateReasons;
/*      */ import javax.print.attribute.standard.PrinterURI;
/*      */ import javax.print.attribute.standard.QueuedJobCount;
/*      */ import javax.print.attribute.standard.RequestingUserName;
/*      */ import javax.print.attribute.standard.SheetCollate;
/*      */ import javax.print.attribute.standard.Sides;
/*      */ import javax.print.event.PrintServiceAttributeListener;
/*      */ 
/*      */ public class IPPPrintService implements PrintService, SunPrinterJobService {
/*      */   public static final boolean debugPrint;
/*      */   
/*      */   protected static void debug_println(String paramString) {
/*   70 */     if (debugPrint)
/*   71 */       System.out.println(paramString); 
/*      */   }
/*      */   private static final String debugPrefix = "IPPPrintService>> "; private static final String FORCE_PIPE_PROP = "sun.print.ippdebug";
/*      */   private String printer;
/*      */   private URI myURI;
/*      */   private URL myURL;
/*      */   
/*      */   static {
/*   79 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.print.ippdebug"));
/*      */ 
/*      */     
/*   82 */     debugPrint = "true".equalsIgnoreCase(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   88 */   private transient ServiceNotifier notifier = null;
/*      */   
/*   90 */   private static int MAXCOPIES = 1000;
/*   91 */   private static short MAX_ATTRIBUTE_LENGTH = 255;
/*      */   
/*      */   private CUPSPrinter cps;
/*   94 */   private HttpURLConnection urlConnection = null;
/*      */ 
/*      */   
/*      */   private DocFlavor[] supportedDocFlavors;
/*      */ 
/*      */   
/*      */   private Class[] supportedCats;
/*      */ 
/*      */   
/*      */   private MediaTray[] mediaTrays;
/*      */ 
/*      */   
/*      */   private MediaSizeName[] mediaSizeNames;
/*      */ 
/*      */   
/*      */   private CustomMediaSizeName[] customMediaSizeNames;
/*      */   
/*      */   private int defaultMediaIndex;
/*      */   
/*      */   private boolean isCupsPrinter;
/*      */   
/*      */   private boolean init;
/*      */   
/*      */   private Boolean isPS;
/*      */   
/*      */   private HashMap getAttMap;
/*      */   
/*      */   private boolean pngImagesAdded = false;
/*      */   
/*      */   private boolean gifImagesAdded = false;
/*      */   
/*      */   private boolean jpgImagesAdded = false;
/*      */   
/*      */   private static final byte STATUSCODE_SUCCESS = 0;
/*      */   
/*      */   private static final byte GRPTAG_OP_ATTRIBUTES = 1;
/*      */   
/*      */   private static final byte GRPTAG_JOB_ATTRIBUTES = 2;
/*      */   
/*      */   private static final byte GRPTAG_PRINTER_ATTRIBUTES = 4;
/*      */   
/*      */   private static final byte GRPTAG_END_ATTRIBUTES = 3;
/*      */   
/*      */   public static final String OP_GET_ATTRIBUTES = "000B";
/*      */   
/*      */   public static final String OP_CUPS_GET_DEFAULT = "4001";
/*      */   
/*      */   public static final String OP_CUPS_GET_PRINTERS = "4002";
/*      */   
/*  143 */   private static Object[] printReqAttribDefault = new Object[] { Chromaticity.COLOR, new Copies(1), Fidelity.FIDELITY_FALSE, Finishings.NONE, new JobName("", 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  153 */         Locale.getDefault()), JobSheets.NONE, MediaSizeName.NA_LETTER, new NumberUp(1), OrientationRequested.PORTRAIT, new PageRanges(1), new RequestingUserName("", 
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
/*  166 */         Locale.getDefault()), Sides.ONE_SIDED };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  176 */   private static Object[][] serviceAttributes = new Object[][] { { ColorSupported.class, "color-supported" }, { PagesPerMinute.class, "pages-per-minute" }, { PagesPerMinuteColor.class, "pages-per-minute-color" }, { PDLOverrideSupported.class, "pdl-override-supported" }, { PrinterInfo.class, "printer-info" }, { PrinterIsAcceptingJobs.class, "printer-is-accepting-jobs" }, { PrinterLocation.class, "printer-location" }, { PrinterMakeAndModel.class, "printer-make-and-model" }, { PrinterMessageFromOperator.class, "printer-message-from-operator" }, { PrinterMoreInfo.class, "printer-more-info" }, { PrinterMoreInfoManufacturer.class, "printer-more-info-manufacturer" }, { PrinterName.class, "printer-name" }, { PrinterState.class, "printer-state" }, { PrinterStateReasons.class, "printer-state-reasons" }, { PrinterURI.class, "printer-uri" }, { QueuedJobCount.class, "queued-job-count" } };
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
/*  202 */   private static DocFlavor[] appPDF = new DocFlavor[] { DocFlavor.BYTE_ARRAY.PDF, DocFlavor.INPUT_STREAM.PDF, DocFlavor.URL.PDF };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  209 */   private static DocFlavor[] appPostScript = new DocFlavor[] { DocFlavor.BYTE_ARRAY.POSTSCRIPT, DocFlavor.INPUT_STREAM.POSTSCRIPT, DocFlavor.URL.POSTSCRIPT };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  216 */   private static DocFlavor[] appOctetStream = new DocFlavor[] { DocFlavor.BYTE_ARRAY.AUTOSENSE, DocFlavor.INPUT_STREAM.AUTOSENSE, DocFlavor.URL.AUTOSENSE };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  223 */   private static DocFlavor[] textPlain = new DocFlavor[] { DocFlavor.BYTE_ARRAY.TEXT_PLAIN_UTF_8, DocFlavor.BYTE_ARRAY.TEXT_PLAIN_UTF_16, DocFlavor.BYTE_ARRAY.TEXT_PLAIN_UTF_16BE, DocFlavor.BYTE_ARRAY.TEXT_PLAIN_UTF_16LE, DocFlavor.BYTE_ARRAY.TEXT_PLAIN_US_ASCII, DocFlavor.INPUT_STREAM.TEXT_PLAIN_UTF_8, DocFlavor.INPUT_STREAM.TEXT_PLAIN_UTF_16, DocFlavor.INPUT_STREAM.TEXT_PLAIN_UTF_16BE, DocFlavor.INPUT_STREAM.TEXT_PLAIN_UTF_16LE, DocFlavor.INPUT_STREAM.TEXT_PLAIN_US_ASCII, DocFlavor.URL.TEXT_PLAIN_UTF_8, DocFlavor.URL.TEXT_PLAIN_UTF_16, DocFlavor.URL.TEXT_PLAIN_UTF_16BE, DocFlavor.URL.TEXT_PLAIN_UTF_16LE, DocFlavor.URL.TEXT_PLAIN_US_ASCII, DocFlavor.CHAR_ARRAY.TEXT_PLAIN, DocFlavor.STRING.TEXT_PLAIN, DocFlavor.READER.TEXT_PLAIN };
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
/*  244 */   private static DocFlavor[] textPlainHost = new DocFlavor[] { DocFlavor.BYTE_ARRAY.TEXT_PLAIN_HOST, DocFlavor.INPUT_STREAM.TEXT_PLAIN_HOST, DocFlavor.URL.TEXT_PLAIN_HOST };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  251 */   private static DocFlavor[] imageJPG = new DocFlavor[] { DocFlavor.BYTE_ARRAY.JPEG, DocFlavor.INPUT_STREAM.JPEG, DocFlavor.URL.JPEG };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  258 */   private static DocFlavor[] imageGIF = new DocFlavor[] { DocFlavor.BYTE_ARRAY.GIF, DocFlavor.INPUT_STREAM.GIF, DocFlavor.URL.GIF };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  265 */   private static DocFlavor[] imagePNG = new DocFlavor[] { DocFlavor.BYTE_ARRAY.PNG, DocFlavor.INPUT_STREAM.PNG, DocFlavor.URL.PNG };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  272 */   private static DocFlavor[] textHtml = new DocFlavor[] { DocFlavor.BYTE_ARRAY.TEXT_HTML_UTF_8, DocFlavor.BYTE_ARRAY.TEXT_HTML_UTF_16, DocFlavor.BYTE_ARRAY.TEXT_HTML_UTF_16BE, DocFlavor.BYTE_ARRAY.TEXT_HTML_UTF_16LE, DocFlavor.BYTE_ARRAY.TEXT_HTML_US_ASCII, DocFlavor.INPUT_STREAM.TEXT_HTML_UTF_8, DocFlavor.INPUT_STREAM.TEXT_HTML_UTF_16, DocFlavor.INPUT_STREAM.TEXT_HTML_UTF_16BE, DocFlavor.INPUT_STREAM.TEXT_HTML_UTF_16LE, DocFlavor.INPUT_STREAM.TEXT_HTML_US_ASCII, DocFlavor.URL.TEXT_HTML_UTF_8, DocFlavor.URL.TEXT_HTML_UTF_16, DocFlavor.URL.TEXT_HTML_UTF_16BE, DocFlavor.URL.TEXT_HTML_UTF_16LE, DocFlavor.URL.TEXT_HTML_US_ASCII };
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
/*  297 */   private static DocFlavor[] textHtmlHost = new DocFlavor[] { DocFlavor.BYTE_ARRAY.TEXT_HTML_HOST, DocFlavor.INPUT_STREAM.TEXT_HTML_HOST, DocFlavor.URL.TEXT_HTML_HOST };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  305 */   private static DocFlavor[] appPCL = new DocFlavor[] { DocFlavor.BYTE_ARRAY.PCL, DocFlavor.INPUT_STREAM.PCL, DocFlavor.URL.PCL };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  313 */   private static Object[] allDocFlavors = new Object[] { appPDF, appPostScript, appOctetStream, textPlain, imageJPG, imageGIF, imagePNG, textHtml, appPCL };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   IPPPrintService(String paramString, URL paramURL) {
/*  321 */     if (paramString == null || paramURL == null) {
/*  322 */       throw new IllegalArgumentException("null uri or printer name");
/*      */     }
/*  324 */     this.printer = paramString;
/*  325 */     this.supportedDocFlavors = null;
/*  326 */     this.supportedCats = null;
/*  327 */     this.mediaSizeNames = null;
/*  328 */     this.customMediaSizeNames = null;
/*  329 */     this.mediaTrays = null;
/*  330 */     this.myURL = paramURL;
/*  331 */     this.cps = null;
/*  332 */     this.isCupsPrinter = false;
/*  333 */     this.init = false;
/*  334 */     this.defaultMediaIndex = -1;
/*      */     
/*  336 */     String str = this.myURL.getHost();
/*  337 */     if (str != null && str.equals(CUPSPrinter.getServer())) {
/*  338 */       this.isCupsPrinter = true;
/*      */       try {
/*  340 */         this.myURI = new URI("ipp://" + str + "/printers/" + this.printer);
/*      */         
/*  342 */         debug_println("IPPPrintService>> IPPPrintService myURI : " + this.myURI);
/*  343 */       } catch (URISyntaxException uRISyntaxException) {
/*  344 */         throw new IllegalArgumentException("invalid url");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   IPPPrintService(String paramString1, String paramString2, boolean paramBoolean) {
/*  351 */     if (paramString1 == null || paramString2 == null) {
/*  352 */       throw new IllegalArgumentException("null uri or printer name");
/*      */     }
/*  354 */     this.printer = paramString1;
/*  355 */     this.supportedDocFlavors = null;
/*  356 */     this.supportedCats = null;
/*  357 */     this.mediaSizeNames = null;
/*  358 */     this.customMediaSizeNames = null;
/*  359 */     this.mediaTrays = null;
/*  360 */     this.cps = null;
/*  361 */     this.init = false;
/*  362 */     this.defaultMediaIndex = -1;
/*      */     try {
/*  364 */       this
/*  365 */         .myURL = new URL(paramString2.replaceFirst("ipp", "http"));
/*  366 */     } catch (Exception exception) {
/*  367 */       debug_println("IPPPrintService>>  IPPPrintService, myURL=" + this.myURL + " Exception= " + exception);
/*      */ 
/*      */ 
/*      */       
/*  371 */       throw new IllegalArgumentException("invalid url");
/*      */     } 
/*      */     
/*  374 */     this.isCupsPrinter = paramBoolean;
/*      */     try {
/*  376 */       this.myURI = new URI(paramString2);
/*  377 */       debug_println("IPPPrintService>> IPPPrintService myURI : " + this.myURI);
/*  378 */     } catch (URISyntaxException uRISyntaxException) {
/*  379 */       throw new IllegalArgumentException("invalid uri");
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
/*      */   private void initAttributes() {
/*  391 */     if (!this.init) {
/*      */       
/*  393 */       this.customMediaSizeNames = new CustomMediaSizeName[0];
/*      */       
/*  395 */       if ((this.urlConnection = getIPPConnection(this.myURL)) == null) {
/*  396 */         this.mediaSizeNames = new MediaSizeName[0];
/*  397 */         this.mediaTrays = new MediaTray[0];
/*  398 */         debug_println("IPPPrintService>> initAttributes, NULL urlConnection ");
/*  399 */         this.init = true;
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  404 */       opGetAttributes();
/*      */       
/*  406 */       if (this.isCupsPrinter) {
/*      */         
/*      */         try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  414 */           this.cps = new CUPSPrinter(this.printer);
/*  415 */           this.mediaSizeNames = this.cps.getMediaSizeNames();
/*  416 */           this.mediaTrays = this.cps.getMediaTrays();
/*  417 */           this.customMediaSizeNames = this.cps.getCustomMediaSizeNames();
/*  418 */           this.defaultMediaIndex = this.cps.getDefaultMediaIndex();
/*  419 */           this.urlConnection.disconnect();
/*  420 */           this.init = true;
/*      */           return;
/*  422 */         } catch (Exception exception) {
/*  423 */           debug_println("IPPPrintService>> initAttributes, error creating CUPSPrinter e=" + exception);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  429 */       Media[] arrayOfMedia = getSupportedMedia();
/*  430 */       ArrayList<Media> arrayList1 = new ArrayList();
/*  431 */       ArrayList<Media> arrayList2 = new ArrayList();
/*  432 */       for (byte b = 0; b < arrayOfMedia.length; b++) {
/*  433 */         if (arrayOfMedia[b] instanceof MediaSizeName) {
/*  434 */           arrayList1.add(arrayOfMedia[b]);
/*  435 */         } else if (arrayOfMedia[b] instanceof MediaTray) {
/*  436 */           arrayList2.add(arrayOfMedia[b]);
/*      */         } 
/*      */       } 
/*      */       
/*  440 */       if (arrayList1 != null) {
/*  441 */         this.mediaSizeNames = new MediaSizeName[arrayList1.size()];
/*  442 */         this.mediaSizeNames = arrayList1.<MediaSizeName>toArray(this.mediaSizeNames);
/*      */       } 
/*      */       
/*  445 */       if (arrayList2 != null) {
/*  446 */         this.mediaTrays = new MediaTray[arrayList2.size()];
/*  447 */         this.mediaTrays = arrayList2.<MediaTray>toArray(this.mediaTrays);
/*      */       } 
/*      */       
/*  450 */       this.urlConnection.disconnect();
/*      */       
/*  452 */       this.init = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public DocPrintJob createPrintJob() {
/*  458 */     SecurityManager securityManager = System.getSecurityManager();
/*  459 */     if (securityManager != null) {
/*  460 */       securityManager.checkPrintJobAccess();
/*      */     }
/*      */     
/*  463 */     return new UnixPrintJob(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Object getSupportedAttributeValues(Class<? extends Attribute> paramClass, DocFlavor paramDocFlavor, AttributeSet paramAttributeSet) {
/*  472 */     if (paramClass == null) {
/*  473 */       throw new NullPointerException("null category");
/*      */     }
/*  475 */     if (!Attribute.class.isAssignableFrom(paramClass)) {
/*  476 */       throw new IllegalArgumentException(paramClass + " does not implement Attribute");
/*      */     }
/*      */     
/*  479 */     if (paramDocFlavor != null) {
/*  480 */       if (!isDocFlavorSupported(paramDocFlavor)) {
/*  481 */         throw new IllegalArgumentException(paramDocFlavor + " is an unsupported flavor");
/*      */       }
/*  483 */       if (isAutoSense(paramDocFlavor)) {
/*  484 */         return null;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  489 */     if (!isAttributeCategorySupported(paramClass)) {
/*  490 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  494 */     if (!isDestinationSupported(paramDocFlavor, paramAttributeSet)) {
/*  495 */       return null;
/*      */     }
/*      */     
/*  498 */     initAttributes();
/*      */ 
/*      */     
/*  501 */     if (paramClass == Copies.class || paramClass == CopiesSupported.class) {
/*      */       
/*  503 */       if (paramDocFlavor == null || (
/*  504 */         !paramDocFlavor.equals(DocFlavor.INPUT_STREAM.POSTSCRIPT) && 
/*  505 */         !paramDocFlavor.equals(DocFlavor.URL.POSTSCRIPT) && 
/*  506 */         !paramDocFlavor.equals(DocFlavor.BYTE_ARRAY.POSTSCRIPT))) {
/*  507 */         CopiesSupported copiesSupported = new CopiesSupported(1, MAXCOPIES);
/*      */         
/*  509 */         AttributeClass attributeClass = (this.getAttMap != null) ? (AttributeClass)this.getAttMap.get(copiesSupported.getName()) : null;
/*  510 */         if (attributeClass != null) {
/*  511 */           int[] arrayOfInt = attributeClass.getIntRangeValue();
/*  512 */           copiesSupported = new CopiesSupported(arrayOfInt[0], arrayOfInt[1]);
/*      */         } 
/*  514 */         return copiesSupported;
/*      */       } 
/*  516 */       return null;
/*      */     } 
/*  518 */     if (paramClass == Chromaticity.class) {
/*  519 */       if (paramDocFlavor == null || paramDocFlavor
/*  520 */         .equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor
/*  521 */         .equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE) || 
/*  522 */         !isIPPSupportedImages(paramDocFlavor.getMimeType())) {
/*  523 */         Chromaticity[] arrayOfChromaticity = new Chromaticity[1];
/*  524 */         arrayOfChromaticity[0] = Chromaticity.COLOR;
/*  525 */         return arrayOfChromaticity;
/*      */       } 
/*  527 */       return null;
/*      */     } 
/*  529 */     if (paramClass == Destination.class) {
/*  530 */       if (paramDocFlavor == null || paramDocFlavor
/*  531 */         .equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor
/*  532 */         .equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
/*      */         try {
/*  534 */           return new Destination((new File("out.ps")).toURI());
/*  535 */         } catch (SecurityException securityException) {
/*      */           try {
/*  537 */             return new Destination(new URI("file:out.ps"));
/*  538 */           } catch (URISyntaxException uRISyntaxException) {
/*  539 */             return null;
/*      */           } 
/*      */         } 
/*      */       }
/*  543 */       return null;
/*  544 */     }  if (paramClass == Fidelity.class) {
/*  545 */       Fidelity[] arrayOfFidelity = new Fidelity[2];
/*  546 */       arrayOfFidelity[0] = Fidelity.FIDELITY_FALSE;
/*  547 */       arrayOfFidelity[1] = Fidelity.FIDELITY_TRUE;
/*  548 */       return arrayOfFidelity;
/*  549 */     }  if (paramClass == Finishings.class)
/*      */     
/*  551 */     { AttributeClass attributeClass = (this.getAttMap != null) ? (AttributeClass)this.getAttMap.get("finishings-supported") : null;
/*      */       
/*  553 */       if (attributeClass != null) {
/*  554 */         int[] arrayOfInt = attributeClass.getArrayOfIntValues();
/*  555 */         if (arrayOfInt != null && arrayOfInt.length > 0) {
/*  556 */           Finishings[] arrayOfFinishings = new Finishings[arrayOfInt.length];
/*  557 */           for (byte b = 0; b < arrayOfInt.length; b++) {
/*  558 */             arrayOfFinishings[b] = Finishings.NONE;
/*      */             
/*  560 */             Finishings[] arrayOfFinishings1 = (Finishings[])(new ExtFinishing(100)).getAll();
/*  561 */             for (byte b1 = 0; b1 < arrayOfFinishings1.length; b1++) {
/*  562 */               if (arrayOfInt[b] == arrayOfFinishings1[b1].getValue()) {
/*  563 */                 arrayOfFinishings[b] = arrayOfFinishings1[b1];
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*  568 */           return arrayOfFinishings;
/*      */         } 
/*      */       }  }
/*  571 */     else { if (paramClass == JobName.class)
/*  572 */         return new JobName("Java Printing", null); 
/*  573 */       if (paramClass == JobSheets.class) {
/*  574 */         JobSheets[] arrayOfJobSheets = new JobSheets[2];
/*  575 */         arrayOfJobSheets[0] = JobSheets.NONE;
/*  576 */         arrayOfJobSheets[1] = JobSheets.STANDARD;
/*  577 */         return arrayOfJobSheets;
/*      */       } 
/*  579 */       if (paramClass == Media.class) {
/*  580 */         Media[] arrayOfMedia = new Media[this.mediaSizeNames.length + this.mediaTrays.length];
/*      */         
/*      */         byte b;
/*  583 */         for (b = 0; b < this.mediaSizeNames.length; b++) {
/*  584 */           arrayOfMedia[b] = this.mediaSizeNames[b];
/*      */         }
/*      */         
/*  587 */         for (b = 0; b < this.mediaTrays.length; b++) {
/*  588 */           arrayOfMedia[b + this.mediaSizeNames.length] = this.mediaTrays[b];
/*      */         }
/*      */         
/*  591 */         if (arrayOfMedia.length == 0) {
/*  592 */           arrayOfMedia = new Media[1];
/*  593 */           arrayOfMedia[0] = (Media)getDefaultAttributeValue((Class)Media.class);
/*      */         } 
/*      */         
/*  596 */         return arrayOfMedia;
/*  597 */       }  if (paramClass == MediaPrintableArea.class) {
/*  598 */         MediaPrintableArea[] arrayOfMediaPrintableArea1 = null;
/*  599 */         if (this.cps != null) {
/*  600 */           arrayOfMediaPrintableArea1 = this.cps.getMediaPrintableArea();
/*      */         }
/*      */         
/*  603 */         if (arrayOfMediaPrintableArea1 == null) {
/*  604 */           arrayOfMediaPrintableArea1 = new MediaPrintableArea[1];
/*  605 */           arrayOfMediaPrintableArea1[0] = (MediaPrintableArea)
/*  606 */             getDefaultAttributeValue((Class)MediaPrintableArea.class);
/*      */         } 
/*      */         
/*  609 */         if (paramAttributeSet == null || paramAttributeSet.size() == 0) {
/*  610 */           ArrayList<MediaPrintableArea> arrayList = new ArrayList();
/*      */ 
/*      */           
/*  613 */           for (byte b1 = 0; b1 < arrayOfMediaPrintableArea1.length; b1++) {
/*  614 */             if (arrayOfMediaPrintableArea1[b1] != null) {
/*  615 */               arrayList.add(arrayOfMediaPrintableArea1[b1]);
/*      */             }
/*      */           } 
/*  618 */           if (arrayList.size() > 0) {
/*  619 */             arrayOfMediaPrintableArea1 = new MediaPrintableArea[arrayList.size()];
/*  620 */             arrayList.toArray(arrayOfMediaPrintableArea1);
/*      */           } 
/*  622 */           return arrayOfMediaPrintableArea1;
/*      */         } 
/*      */         
/*  625 */         byte b = -1;
/*  626 */         Media media = (Media)paramAttributeSet.get(Media.class);
/*  627 */         if (media != null && media instanceof MediaSizeName) {
/*  628 */           MediaSizeName mediaSizeName = (MediaSizeName)media;
/*      */ 
/*      */ 
/*      */           
/*  632 */           if (this.mediaSizeNames.length == 0 && mediaSizeName
/*  633 */             .equals(getDefaultAttributeValue((Class)Media.class)))
/*      */           {
/*  635 */             return arrayOfMediaPrintableArea1;
/*      */           }
/*      */           
/*  638 */           for (byte b1 = 0; b1 < this.mediaSizeNames.length; b1++) {
/*  639 */             if (mediaSizeName.equals(this.mediaSizeNames[b1])) {
/*  640 */               b = b1;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  645 */         if (b == -1) {
/*  646 */           return null;
/*      */         }
/*  648 */         MediaPrintableArea[] arrayOfMediaPrintableArea2 = new MediaPrintableArea[1];
/*  649 */         arrayOfMediaPrintableArea2[0] = arrayOfMediaPrintableArea1[b];
/*  650 */         return arrayOfMediaPrintableArea2;
/*      */       } 
/*  652 */       if (paramClass == NumberUp.class) {
/*      */         
/*  654 */         AttributeClass attributeClass = (this.getAttMap != null) ? (AttributeClass)this.getAttMap.get("number-up-supported") : null;
/*  655 */         if (attributeClass != null) {
/*  656 */           int[] arrayOfInt = attributeClass.getArrayOfIntValues();
/*  657 */           if (arrayOfInt != null) {
/*  658 */             NumberUp[] arrayOfNumberUp = new NumberUp[arrayOfInt.length];
/*  659 */             for (byte b = 0; b < arrayOfInt.length; b++) {
/*  660 */               arrayOfNumberUp[b] = new NumberUp(arrayOfInt[b]);
/*      */             }
/*  662 */             return arrayOfNumberUp;
/*      */           } 
/*  664 */           return null;
/*      */         } 
/*      */       } else {
/*  667 */         if (paramClass == OrientationRequested.class) {
/*  668 */           if (paramDocFlavor != null && (paramDocFlavor
/*  669 */             .equals(DocFlavor.INPUT_STREAM.POSTSCRIPT) || paramDocFlavor
/*  670 */             .equals(DocFlavor.URL.POSTSCRIPT) || paramDocFlavor
/*  671 */             .equals(DocFlavor.BYTE_ARRAY.POSTSCRIPT))) {
/*  672 */             return null;
/*      */           }
/*      */           
/*  675 */           boolean bool = false;
/*  676 */           OrientationRequested[] arrayOfOrientationRequested = null;
/*      */ 
/*      */           
/*  679 */           AttributeClass attributeClass = (this.getAttMap != null) ? (AttributeClass)this.getAttMap.get("orientation-requested-supported") : null;
/*      */           
/*  681 */           if (attributeClass != null) {
/*  682 */             int[] arrayOfInt = attributeClass.getArrayOfIntValues();
/*  683 */             if (arrayOfInt != null && arrayOfInt.length > 0) {
/*  684 */               arrayOfOrientationRequested = new OrientationRequested[arrayOfInt.length];
/*      */               
/*  686 */               for (byte b = 0; b < arrayOfInt.length; b++) {
/*  687 */                 switch (arrayOfInt[b]) {
/*      */                   
/*      */                   default:
/*  690 */                     arrayOfOrientationRequested[b] = OrientationRequested.PORTRAIT;
/*      */                     break;
/*      */                   case 4:
/*  693 */                     arrayOfOrientationRequested[b] = OrientationRequested.LANDSCAPE;
/*      */                     break;
/*      */                   case 5:
/*  696 */                     arrayOfOrientationRequested[b] = OrientationRequested.REVERSE_LANDSCAPE;
/*      */                     break;
/*      */                   
/*      */                   case 6:
/*  700 */                     arrayOfOrientationRequested[b] = OrientationRequested.REVERSE_PORTRAIT;
/*      */                     
/*  702 */                     bool = true;
/*      */                     break;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*  708 */           if (paramDocFlavor == null || paramDocFlavor
/*  709 */             .equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor
/*  710 */             .equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
/*      */             
/*  712 */             if (bool && paramDocFlavor == null) {
/*  713 */               OrientationRequested[] arrayOfOrientationRequested2 = new OrientationRequested[4];
/*  714 */               arrayOfOrientationRequested2[0] = OrientationRequested.PORTRAIT;
/*  715 */               arrayOfOrientationRequested2[1] = OrientationRequested.LANDSCAPE;
/*  716 */               arrayOfOrientationRequested2[2] = OrientationRequested.REVERSE_LANDSCAPE;
/*  717 */               arrayOfOrientationRequested2[3] = OrientationRequested.REVERSE_PORTRAIT;
/*  718 */               return arrayOfOrientationRequested2;
/*      */             } 
/*  720 */             OrientationRequested[] arrayOfOrientationRequested1 = new OrientationRequested[3];
/*  721 */             arrayOfOrientationRequested1[0] = OrientationRequested.PORTRAIT;
/*  722 */             arrayOfOrientationRequested1[1] = OrientationRequested.LANDSCAPE;
/*  723 */             arrayOfOrientationRequested1[2] = OrientationRequested.REVERSE_LANDSCAPE;
/*  724 */             return arrayOfOrientationRequested1;
/*      */           } 
/*      */           
/*  727 */           return arrayOfOrientationRequested;
/*      */         } 
/*  729 */         if (paramClass == PageRanges.class) {
/*  730 */           if (paramDocFlavor == null || paramDocFlavor
/*  731 */             .equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor
/*  732 */             .equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
/*  733 */             PageRanges[] arrayOfPageRanges = new PageRanges[1];
/*  734 */             arrayOfPageRanges[0] = new PageRanges(1, 2147483647);
/*  735 */             return arrayOfPageRanges;
/*      */           } 
/*      */           
/*  738 */           return null;
/*      */         } 
/*  740 */         if (paramClass == RequestingUserName.class) {
/*  741 */           String str = "";
/*      */           try {
/*  743 */             str = System.getProperty("user.name", "");
/*  744 */           } catch (SecurityException securityException) {}
/*      */           
/*  746 */           return new RequestingUserName(str, null);
/*  747 */         }  if (paramClass == Sides.class) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  754 */           AttributeClass attributeClass = (this.getAttMap != null) ? (AttributeClass)this.getAttMap.get("sides-supported") : null;
/*      */           
/*  756 */           if (attributeClass != null) {
/*  757 */             String[] arrayOfString = attributeClass.getArrayOfStringValues();
/*  758 */             if (arrayOfString != null && arrayOfString.length > 0) {
/*  759 */               Sides[] arrayOfSides = new Sides[arrayOfString.length];
/*  760 */               for (byte b = 0; b < arrayOfString.length; b++) {
/*  761 */                 if (arrayOfString[b].endsWith("long-edge")) {
/*  762 */                   arrayOfSides[b] = Sides.TWO_SIDED_LONG_EDGE;
/*  763 */                 } else if (arrayOfString[b].endsWith("short-edge")) {
/*  764 */                   arrayOfSides[b] = Sides.TWO_SIDED_SHORT_EDGE;
/*      */                 } else {
/*  766 */                   arrayOfSides[b] = Sides.ONE_SIDED;
/*      */                 } 
/*      */               } 
/*  769 */               return arrayOfSides;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }  }
/*  774 */      return null;
/*      */   }
/*      */   
/*      */   private class ExtFinishing
/*      */     extends Finishings {
/*      */     ExtFinishing(int param1Int) {
/*  780 */       super(100);
/*      */     }
/*      */     
/*      */     EnumSyntax[] getAll() {
/*  784 */       return getEnumValueTable();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeSet getUnsupportedAttributes(DocFlavor paramDocFlavor, AttributeSet paramAttributeSet) {
/*  792 */     if (paramDocFlavor != null && !isDocFlavorSupported(paramDocFlavor)) {
/*  793 */       throw new IllegalArgumentException("flavor " + paramDocFlavor + "is not supported");
/*      */     }
/*      */ 
/*      */     
/*  797 */     if (paramAttributeSet == null) {
/*  798 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  802 */     HashAttributeSet hashAttributeSet = new HashAttributeSet();
/*  803 */     Attribute[] arrayOfAttribute = paramAttributeSet.toArray();
/*  804 */     for (byte b = 0; b < arrayOfAttribute.length; b++) {
/*      */       try {
/*  806 */         Attribute attribute = arrayOfAttribute[b];
/*  807 */         if (!isAttributeCategorySupported(attribute.getCategory())) {
/*  808 */           hashAttributeSet.add(attribute);
/*  809 */         } else if (!isAttributeValueSupported(attribute, paramDocFlavor, paramAttributeSet)) {
/*      */           
/*  811 */           hashAttributeSet.add(attribute);
/*      */         } 
/*  813 */       } catch (ClassCastException classCastException) {}
/*      */     } 
/*      */     
/*  816 */     if (hashAttributeSet.isEmpty()) {
/*  817 */       return null;
/*      */     }
/*  819 */     return hashAttributeSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized DocFlavor[] getSupportedDocFlavors() {
/*  826 */     if (this.supportedDocFlavors != null) {
/*  827 */       int i = this.supportedDocFlavors.length;
/*  828 */       DocFlavor[] arrayOfDocFlavor = new DocFlavor[i];
/*  829 */       System.arraycopy(this.supportedDocFlavors, 0, arrayOfDocFlavor, 0, i);
/*  830 */       return arrayOfDocFlavor;
/*      */     } 
/*  832 */     initAttributes();
/*      */     
/*  834 */     if (this.getAttMap != null && this.getAttMap
/*  835 */       .containsKey("document-format-supported")) {
/*      */ 
/*      */       
/*  838 */       AttributeClass attributeClass = (AttributeClass)this.getAttMap.get("document-format-supported");
/*  839 */       if (attributeClass != null) {
/*      */         
/*  841 */         boolean bool1 = false;
/*  842 */         String[] arrayOfString = attributeClass.getArrayOfStringValues();
/*      */         
/*  844 */         HashSet<DocFlavor.BYTE_ARRAY> hashSet = new HashSet();
/*      */ 
/*      */         
/*  847 */         String str = DocFlavor.hostEncoding.toLowerCase(Locale.ENGLISH);
/*      */ 
/*      */         
/*  850 */         boolean bool2 = (!str.equals("utf-8") && !str.equals("utf-16") && !str.equals("utf-16be") && !str.equals("utf-16le") && !str.equals("us-ascii")) ? true : false;
/*      */         int i;
/*  852 */         for (i = 0; i < arrayOfString.length; i++) {
/*  853 */           byte b; for (b = 0; b < allDocFlavors.length; b++) {
/*  854 */             DocFlavor[] arrayOfDocFlavor1 = (DocFlavor[])allDocFlavors[b];
/*      */             
/*  856 */             String str1 = arrayOfDocFlavor1[0].getMimeType();
/*  857 */             if (str1.startsWith(arrayOfString[i])) {
/*      */               
/*  859 */               hashSet.addAll(Arrays.asList(arrayOfDocFlavor1));
/*      */               
/*  861 */               if (str1.equals("text/plain") && bool2) {
/*      */                 
/*  863 */                 hashSet.add(Arrays.asList(textPlainHost)); break;
/*  864 */               }  if (str1.equals("text/html") && bool2) {
/*      */                 
/*  866 */                 hashSet.add(Arrays.asList(textHtmlHost)); break;
/*  867 */               }  if (str1.equals("image/png")) {
/*  868 */                 this.pngImagesAdded = true; break;
/*  869 */               }  if (str1.equals("image/gif")) {
/*  870 */                 this.gifImagesAdded = true; break;
/*  871 */               }  if (str1.equals("image/jpeg")) {
/*  872 */                 this.jpgImagesAdded = true; break;
/*  873 */               }  if (str1.indexOf("postscript") != -1) {
/*  874 */                 bool1 = true;
/*      */               }
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */           
/*  881 */           if (b == allDocFlavors.length) {
/*      */             
/*  883 */             hashSet.add(new DocFlavor.BYTE_ARRAY(arrayOfString[i]));
/*  884 */             hashSet.add(new DocFlavor.INPUT_STREAM(arrayOfString[i]));
/*  885 */             hashSet.add(new DocFlavor.URL(arrayOfString[i]));
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  891 */         if (bool1 || this.isCupsPrinter) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  897 */           hashSet.add(DocFlavor.SERVICE_FORMATTED.PAGEABLE);
/*  898 */           hashSet.add(DocFlavor.SERVICE_FORMATTED.PRINTABLE);
/*      */           
/*  900 */           hashSet.addAll((Collection)Arrays.asList(imageJPG));
/*  901 */           hashSet.addAll((Collection)Arrays.asList(imagePNG));
/*  902 */           hashSet.addAll((Collection)Arrays.asList(imageGIF));
/*      */         } 
/*  904 */         this.supportedDocFlavors = new DocFlavor[hashSet.size()];
/*  905 */         hashSet.toArray(this.supportedDocFlavors);
/*  906 */         i = this.supportedDocFlavors.length;
/*  907 */         DocFlavor[] arrayOfDocFlavor = new DocFlavor[i];
/*  908 */         System.arraycopy(this.supportedDocFlavors, 0, arrayOfDocFlavor, 0, i);
/*  909 */         return arrayOfDocFlavor;
/*      */       } 
/*      */     } 
/*  912 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDocFlavorSupported(DocFlavor paramDocFlavor) {
/*  917 */     if (this.supportedDocFlavors == null) {
/*  918 */       getSupportedDocFlavors();
/*      */     }
/*  920 */     if (this.supportedDocFlavors != null) {
/*  921 */       for (byte b = 0; b < this.supportedDocFlavors.length; b++) {
/*  922 */         if (paramDocFlavor.equals(this.supportedDocFlavors[b])) {
/*  923 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*  927 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CustomMediaSizeName findCustomMedia(MediaSizeName paramMediaSizeName) {
/*  935 */     if (this.customMediaSizeNames == null) {
/*  936 */       return null;
/*      */     }
/*  938 */     for (byte b = 0; b < this.customMediaSizeNames.length; b++) {
/*  939 */       CustomMediaSizeName customMediaSizeName = this.customMediaSizeNames[b];
/*      */       
/*  941 */       MediaSizeName mediaSizeName = customMediaSizeName.getStandardMedia();
/*  942 */       if (paramMediaSizeName.equals(mediaSizeName)) {
/*  943 */         return this.customMediaSizeNames[b];
/*      */       }
/*      */     } 
/*  946 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Media getIPPMedia(String paramString) {
/*  954 */     CustomMediaSizeName customMediaSizeName = new CustomMediaSizeName("sample", "", 0.0F, 0.0F);
/*      */     
/*  956 */     Media[] arrayOfMedia1 = customMediaSizeName.getSuperEnumTable();
/*  957 */     for (byte b1 = 0; b1 < arrayOfMedia1.length; b1++) {
/*  958 */       if (paramString.equals("" + arrayOfMedia1[b1])) {
/*  959 */         return arrayOfMedia1[b1];
/*      */       }
/*      */     } 
/*  962 */     CustomMediaTray customMediaTray = new CustomMediaTray("sample", "");
/*  963 */     Media[] arrayOfMedia2 = customMediaTray.getSuperEnumTable();
/*  964 */     for (byte b2 = 0; b2 < arrayOfMedia2.length; b2++) {
/*  965 */       if (paramString.equals("" + arrayOfMedia2[b2])) {
/*  966 */         return arrayOfMedia2[b2];
/*      */       }
/*      */     } 
/*  969 */     return null;
/*      */   }
/*      */   
/*      */   private Media[] getSupportedMedia() {
/*  973 */     if (this.getAttMap != null && this.getAttMap
/*  974 */       .containsKey("media-supported")) {
/*      */ 
/*      */       
/*  977 */       AttributeClass attributeClass = (AttributeClass)this.getAttMap.get("media-supported");
/*      */       
/*  979 */       if (attributeClass != null) {
/*  980 */         String[] arrayOfString = attributeClass.getArrayOfStringValues();
/*      */         
/*  982 */         Media[] arrayOfMedia = new Media[arrayOfString.length];
/*      */         
/*  984 */         for (byte b = 0; b < arrayOfString.length; b++) {
/*  985 */           Media media = getIPPMedia(arrayOfString[b]);
/*      */           
/*  987 */           arrayOfMedia[b] = media;
/*      */         } 
/*  989 */         return arrayOfMedia;
/*      */       } 
/*      */     } 
/*  992 */     return new Media[0];
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized Class[] getSupportedAttributeCategories() {
/*  997 */     if (this.supportedCats != null) {
/*  998 */       Class[] arrayOfClass1 = new Class[this.supportedCats.length];
/*  999 */       System.arraycopy(this.supportedCats, 0, arrayOfClass1, 0, arrayOfClass1.length);
/* 1000 */       return arrayOfClass1;
/*      */     } 
/*      */     
/* 1003 */     initAttributes();
/*      */     
/* 1005 */     ArrayList<Class<? extends Attribute>> arrayList = new ArrayList();
/*      */ 
/*      */     
/* 1008 */     for (byte b = 0; b < printReqAttribDefault.length; b++) {
/* 1009 */       PrintRequestAttribute printRequestAttribute = (PrintRequestAttribute)printReqAttribDefault[b];
/*      */       
/* 1011 */       if (this.getAttMap != null && this.getAttMap
/* 1012 */         .containsKey(printRequestAttribute.getName() + "-supported")) {
/* 1013 */         Class<? extends Attribute> clazz = printRequestAttribute.getCategory();
/* 1014 */         arrayList.add(clazz);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1021 */     if (this.isCupsPrinter) {
/* 1022 */       if (!arrayList.contains(Media.class)) {
/* 1023 */         arrayList.add(Media.class);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1028 */       arrayList.add(MediaPrintableArea.class);
/*      */ 
/*      */       
/* 1031 */       arrayList.add(Destination.class);
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
/* 1045 */       if (!PrintServiceLookupProvider.isLinux()) {
/* 1046 */         arrayList.add(SheetCollate.class);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1052 */     if (this.getAttMap != null && this.getAttMap.containsKey("color-supported")) {
/* 1053 */       arrayList.add(Chromaticity.class);
/*      */     }
/* 1055 */     this.supportedCats = new Class[arrayList.size()];
/* 1056 */     arrayList.toArray((Class<?>[][])this.supportedCats);
/* 1057 */     Class[] arrayOfClass = new Class[this.supportedCats.length];
/* 1058 */     System.arraycopy(this.supportedCats, 0, arrayOfClass, 0, arrayOfClass.length);
/* 1059 */     return arrayOfClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAttributeCategorySupported(Class<? extends Attribute> paramClass) {
/* 1066 */     if (paramClass == null) {
/* 1067 */       throw new NullPointerException("null category");
/*      */     }
/* 1069 */     if (!Attribute.class.isAssignableFrom(paramClass)) {
/* 1070 */       throw new IllegalArgumentException(paramClass + " is not an Attribute");
/*      */     }
/*      */ 
/*      */     
/* 1074 */     if (this.supportedCats == null) {
/* 1075 */       getSupportedAttributeCategories();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1082 */     if (paramClass == OrientationRequested.class) {
/* 1083 */       return true;
/*      */     }
/*      */     
/* 1086 */     for (byte b = 0; b < this.supportedCats.length; b++) {
/* 1087 */       if (paramClass == this.supportedCats[b]) {
/* 1088 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1092 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized <T extends PrintServiceAttribute> T getAttribute(Class<T> paramClass) {
/* 1099 */     if (paramClass == null) {
/* 1100 */       throw new NullPointerException("category");
/*      */     }
/* 1102 */     if (!PrintServiceAttribute.class.isAssignableFrom(paramClass)) {
/* 1103 */       throw new IllegalArgumentException("Not a PrintServiceAttribute");
/*      */     }
/*      */     
/* 1106 */     initAttributes();
/*      */     
/* 1108 */     if (paramClass == PrinterName.class)
/* 1109 */       return (T)new PrinterName(this.printer, null); 
/* 1110 */     if (paramClass == PrinterInfo.class) {
/* 1111 */       PrinterInfo printerInfo = new PrinterInfo(this.printer, null);
/*      */       
/* 1113 */       AttributeClass attributeClass = (this.getAttMap != null) ? (AttributeClass)this.getAttMap.get(printerInfo.getName()) : null;
/*      */       
/* 1115 */       if (attributeClass != null) {
/* 1116 */         return (T)new PrinterInfo(attributeClass.getStringValue(), null);
/*      */       }
/* 1118 */       return (T)printerInfo;
/* 1119 */     }  if (paramClass == QueuedJobCount.class) {
/* 1120 */       QueuedJobCount queuedJobCount = new QueuedJobCount(0);
/*      */       
/* 1122 */       AttributeClass attributeClass = (this.getAttMap != null) ? (AttributeClass)this.getAttMap.get(queuedJobCount.getName()) : null;
/*      */       
/* 1124 */       if (attributeClass != null) {
/* 1125 */         queuedJobCount = new QueuedJobCount(attributeClass.getIntValue());
/*      */       }
/* 1127 */       return (T)queuedJobCount;
/* 1128 */     }  if (paramClass == PrinterIsAcceptingJobs.class) {
/* 1129 */       PrinterIsAcceptingJobs printerIsAcceptingJobs = PrinterIsAcceptingJobs.ACCEPTING_JOBS;
/*      */ 
/*      */       
/* 1132 */       AttributeClass attributeClass = (this.getAttMap != null) ? (AttributeClass)this.getAttMap.get(printerIsAcceptingJobs.getName()) : null;
/*      */       
/* 1134 */       if (attributeClass != null && attributeClass.getByteValue() == 0) {
/* 1135 */         printerIsAcceptingJobs = PrinterIsAcceptingJobs.NOT_ACCEPTING_JOBS;
/*      */       }
/* 1137 */       return (T)printerIsAcceptingJobs;
/* 1138 */     }  if (paramClass == ColorSupported.class) {
/* 1139 */       ColorSupported colorSupported = ColorSupported.SUPPORTED;
/*      */       
/* 1141 */       AttributeClass attributeClass = (this.getAttMap != null) ? (AttributeClass)this.getAttMap.get(colorSupported.getName()) : null;
/*      */       
/* 1143 */       if (attributeClass != null && attributeClass.getByteValue() == 0) {
/* 1144 */         colorSupported = ColorSupported.NOT_SUPPORTED;
/*      */       }
/* 1146 */       return (T)colorSupported;
/* 1147 */     }  if (paramClass == PDLOverrideSupported.class) {
/*      */       
/* 1149 */       if (this.isCupsPrinter)
/*      */       {
/* 1151 */         return (T)PDLOverrideSupported.NOT_ATTEMPTED;
/*      */       }
/*      */       
/* 1154 */       return (T)PDLOverrideSupported.NOT_ATTEMPTED;
/*      */     } 
/* 1156 */     if (paramClass == PrinterURI.class) {
/* 1157 */       return (T)new PrinterURI(this.myURI);
/*      */     }
/* 1159 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized PrintServiceAttributeSet getAttributes() {
/* 1166 */     this.init = false;
/* 1167 */     initAttributes();
/*      */     
/* 1169 */     HashPrintServiceAttributeSet hashPrintServiceAttributeSet = new HashPrintServiceAttributeSet();
/*      */ 
/*      */     
/* 1172 */     for (byte b = 0; b < serviceAttributes.length; b++) {
/* 1173 */       String str = (String)serviceAttributes[b][1];
/* 1174 */       if (this.getAttMap != null && this.getAttMap.containsKey(str)) {
/* 1175 */         Class<Object> clazz = (Class)serviceAttributes[b][0];
/* 1176 */         Attribute attribute = (Attribute)getAttribute(clazz);
/* 1177 */         if (attribute != null) {
/* 1178 */           hashPrintServiceAttributeSet.add(attribute);
/*      */         }
/*      */       } 
/*      */     } 
/* 1182 */     return AttributeSetUtilities.unmodifiableView(hashPrintServiceAttributeSet);
/*      */   }
/*      */   
/*      */   public boolean isIPPSupportedImages(String paramString) {
/* 1186 */     if (this.supportedDocFlavors == null) {
/* 1187 */       getSupportedDocFlavors();
/*      */     }
/*      */     
/* 1190 */     if (paramString.equals("image/png") && this.pngImagesAdded)
/* 1191 */       return true; 
/* 1192 */     if (paramString.equals("image/gif") && this.gifImagesAdded)
/* 1193 */       return true; 
/* 1194 */     if (paramString.equals("image/jpeg") && this.jpgImagesAdded) {
/* 1195 */       return true;
/*      */     }
/*      */     
/* 1198 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isSupportedCopies(Copies paramCopies) {
/*      */     byte b;
/*      */     int i;
/* 1204 */     CopiesSupported copiesSupported = (CopiesSupported)getSupportedAttributeValues((Class)Copies.class, null, null);
/* 1205 */     int[][] arrayOfInt = copiesSupported.getMembers();
/*      */     
/* 1207 */     if (arrayOfInt.length > 0 && (arrayOfInt[0]).length > 0) {
/* 1208 */       b = arrayOfInt[0][0];
/* 1209 */       i = arrayOfInt[0][1];
/*      */     } else {
/* 1211 */       b = 1;
/* 1212 */       i = MAXCOPIES;
/*      */     } 
/*      */     
/* 1215 */     int j = paramCopies.getValue();
/* 1216 */     return (j >= b && j <= i);
/*      */   }
/*      */   
/*      */   private boolean isAutoSense(DocFlavor paramDocFlavor) {
/* 1220 */     if (paramDocFlavor.equals(DocFlavor.BYTE_ARRAY.AUTOSENSE) || paramDocFlavor
/* 1221 */       .equals(DocFlavor.INPUT_STREAM.AUTOSENSE) || paramDocFlavor
/* 1222 */       .equals(DocFlavor.URL.AUTOSENSE)) {
/* 1223 */       return true;
/*      */     }
/*      */     
/* 1226 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private synchronized boolean isSupportedMediaTray(MediaTray paramMediaTray) {
/* 1231 */     initAttributes();
/*      */     
/* 1233 */     if (this.mediaTrays != null) {
/* 1234 */       for (byte b = 0; b < this.mediaTrays.length; b++) {
/* 1235 */         if (paramMediaTray.equals(this.mediaTrays[b])) {
/* 1236 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/* 1240 */     return false;
/*      */   }
/*      */   
/*      */   private synchronized boolean isSupportedMedia(MediaSizeName paramMediaSizeName) {
/* 1244 */     initAttributes();
/*      */     
/* 1246 */     if (paramMediaSizeName.equals(getDefaultAttributeValue((Class)Media.class))) {
/* 1247 */       return true;
/*      */     }
/* 1249 */     for (byte b = 0; b < this.mediaSizeNames.length; b++) {
/* 1250 */       debug_println("IPPPrintService>> isSupportedMedia, mediaSizeNames[i] " + this.mediaSizeNames[b]);
/* 1251 */       if (paramMediaSizeName.equals(this.mediaSizeNames[b])) {
/* 1252 */         return true;
/*      */       }
/*      */     } 
/* 1255 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isDestinationSupported(DocFlavor paramDocFlavor, AttributeSet paramAttributeSet) {
/* 1264 */     if (paramAttributeSet != null && paramAttributeSet
/* 1265 */       .get(Destination.class) != null && paramDocFlavor != null && 
/*      */       
/* 1267 */       !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) && 
/* 1268 */       !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
/* 1269 */       return false;
/*      */     }
/* 1271 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAttributeValueSupported(Attribute paramAttribute, DocFlavor paramDocFlavor, AttributeSet paramAttributeSet) {
/* 1278 */     if (paramAttribute == null) {
/* 1279 */       throw new NullPointerException("null attribute");
/*      */     }
/* 1281 */     if (paramDocFlavor != null) {
/* 1282 */       if (!isDocFlavorSupported(paramDocFlavor)) {
/* 1283 */         throw new IllegalArgumentException(paramDocFlavor + " is an unsupported flavor");
/*      */       }
/* 1285 */       if (isAutoSense(paramDocFlavor)) {
/* 1286 */         return false;
/*      */       }
/*      */     } 
/* 1289 */     Class<? extends Attribute> clazz = paramAttribute.getCategory();
/* 1290 */     if (!isAttributeCategorySupported(clazz)) {
/* 1291 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1295 */     if (!isDestinationSupported(paramDocFlavor, paramAttributeSet)) {
/* 1296 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1300 */     if (paramAttribute.getCategory() == Chromaticity.class) {
/* 1301 */       if (paramDocFlavor == null || paramDocFlavor
/* 1302 */         .equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor
/* 1303 */         .equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE) || 
/* 1304 */         !isIPPSupportedImages(paramDocFlavor.getMimeType())) {
/* 1305 */         return (paramAttribute == Chromaticity.COLOR);
/*      */       }
/* 1307 */       return false;
/*      */     } 
/* 1309 */     if (paramAttribute.getCategory() == Copies.class) {
/* 1310 */       return ((paramDocFlavor == null || (
/* 1311 */         !paramDocFlavor.equals(DocFlavor.INPUT_STREAM.POSTSCRIPT) && 
/* 1312 */         !paramDocFlavor.equals(DocFlavor.URL.POSTSCRIPT) && 
/* 1313 */         !paramDocFlavor.equals(DocFlavor.BYTE_ARRAY.POSTSCRIPT))) && 
/* 1314 */         isSupportedCopies((Copies)paramAttribute));
/*      */     }
/* 1316 */     if (paramAttribute.getCategory() == Destination.class) {
/* 1317 */       if (paramDocFlavor == null || paramDocFlavor
/* 1318 */         .equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor
/* 1319 */         .equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
/* 1320 */         URI uRI = ((Destination)paramAttribute).getURI();
/* 1321 */         if ("file".equals(uRI.getScheme()) && 
/* 1322 */           !uRI.getSchemeSpecificPart().equals("")) {
/* 1323 */           return true;
/*      */         }
/*      */       } 
/* 1326 */       return false;
/* 1327 */     }  if (paramAttribute.getCategory() == Media.class) {
/* 1328 */       if (paramAttribute instanceof MediaSizeName) {
/* 1329 */         return isSupportedMedia((MediaSizeName)paramAttribute);
/*      */       }
/* 1331 */       if (paramAttribute instanceof MediaTray) {
/* 1332 */         return isSupportedMediaTray((MediaTray)paramAttribute);
/*      */       }
/* 1334 */     } else if (paramAttribute.getCategory() == PageRanges.class) {
/* 1335 */       if (paramDocFlavor != null && 
/* 1336 */         !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) && 
/* 1337 */         !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
/* 1338 */         return false;
/*      */       }
/* 1340 */     } else if (paramAttribute.getCategory() == SheetCollate.class) {
/* 1341 */       if (paramDocFlavor != null && 
/* 1342 */         !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) && 
/* 1343 */         !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE))
/* 1344 */         return false; 
/*      */     } else {
/* 1346 */       if (paramAttribute.getCategory() == Sides.class) {
/* 1347 */         Sides[] arrayOfSides = (Sides[])getSupportedAttributeValues((Class)Sides.class, paramDocFlavor, paramAttributeSet);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1352 */         if (arrayOfSides != null) {
/* 1353 */           for (byte b = 0; b < arrayOfSides.length; b++) {
/* 1354 */             if (arrayOfSides[b] == (Sides)paramAttribute) {
/* 1355 */               return true;
/*      */             }
/*      */           } 
/*      */         }
/* 1359 */         return false;
/* 1360 */       }  if (paramAttribute.getCategory() == OrientationRequested.class) {
/*      */         
/* 1362 */         OrientationRequested[] arrayOfOrientationRequested = (OrientationRequested[])getSupportedAttributeValues((Class)OrientationRequested.class, paramDocFlavor, paramAttributeSet);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1367 */         if (arrayOfOrientationRequested != null) {
/* 1368 */           for (byte b = 0; b < arrayOfOrientationRequested.length; b++) {
/* 1369 */             if (arrayOfOrientationRequested[b] == (OrientationRequested)paramAttribute) {
/* 1370 */               return true;
/*      */             }
/*      */           } 
/*      */         }
/* 1374 */         return false;
/*      */       } 
/* 1376 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Object getDefaultAttributeValue(Class<? extends Attribute> paramClass) {
/* 1383 */     if (paramClass == null) {
/* 1384 */       throw new NullPointerException("null category");
/*      */     }
/* 1386 */     if (!Attribute.class.isAssignableFrom(paramClass)) {
/* 1387 */       throw new IllegalArgumentException(paramClass + " is not an Attribute");
/*      */     }
/*      */     
/* 1390 */     if (!isAttributeCategorySupported(paramClass)) {
/* 1391 */       return null;
/*      */     }
/*      */     
/* 1394 */     initAttributes();
/*      */     
/* 1396 */     String str1 = null;
/* 1397 */     for (byte b = 0; b < printReqAttribDefault.length; b++) {
/* 1398 */       PrintRequestAttribute printRequestAttribute = (PrintRequestAttribute)printReqAttribDefault[b];
/*      */       
/* 1400 */       if (printRequestAttribute.getCategory() == paramClass) {
/* 1401 */         str1 = printRequestAttribute.getName();
/*      */         break;
/*      */       } 
/*      */     } 
/* 1405 */     String str2 = str1 + "-default";
/*      */     
/* 1407 */     AttributeClass attributeClass = (this.getAttMap != null) ? (AttributeClass)this.getAttMap.get(str2) : null;
/*      */     
/* 1409 */     if (paramClass == Copies.class) {
/* 1410 */       if (attributeClass != null) {
/* 1411 */         return new Copies(attributeClass.getIntValue());
/*      */       }
/* 1413 */       return new Copies(1);
/*      */     } 
/* 1415 */     if (paramClass == Chromaticity.class)
/* 1416 */       return Chromaticity.COLOR; 
/* 1417 */     if (paramClass == Destination.class)
/*      */       try {
/* 1419 */         return new Destination((new File("out.ps")).toURI());
/* 1420 */       } catch (SecurityException securityException) {
/*      */         try {
/* 1422 */           return new Destination(new URI("file:out.ps"));
/* 1423 */         } catch (URISyntaxException uRISyntaxException) {
/* 1424 */           return null;
/*      */         } 
/*      */       }  
/* 1427 */     if (paramClass == Fidelity.class)
/* 1428 */       return Fidelity.FIDELITY_FALSE; 
/* 1429 */     if (paramClass == Finishings.class)
/* 1430 */       return Finishings.NONE; 
/* 1431 */     if (paramClass == JobName.class)
/* 1432 */       return new JobName("Java Printing", null); 
/* 1433 */     if (paramClass == JobSheets.class) {
/* 1434 */       if (attributeClass != null && attributeClass
/* 1435 */         .getStringValue().equals("none")) {
/* 1436 */         return JobSheets.NONE;
/*      */       }
/* 1438 */       return JobSheets.STANDARD;
/*      */     } 
/* 1440 */     if (paramClass == Media.class) {
/* 1441 */       if (this.defaultMediaIndex == -1) {
/* 1442 */         this.defaultMediaIndex = 0;
/*      */       }
/* 1444 */       if (this.mediaSizeNames.length == 0) {
/* 1445 */         String str = Locale.getDefault().getCountry();
/* 1446 */         if (str != null && (str
/* 1447 */           .equals("") || str
/* 1448 */           .equals(Locale.US.getCountry()) || str
/* 1449 */           .equals(Locale.CANADA.getCountry()))) {
/* 1450 */           return MediaSizeName.NA_LETTER;
/*      */         }
/* 1452 */         return MediaSizeName.ISO_A4;
/*      */       } 
/*      */ 
/*      */       
/* 1456 */       if (attributeClass != null) {
/* 1457 */         String str = attributeClass.getStringValue();
/* 1458 */         if (this.isCupsPrinter) {
/* 1459 */           return this.mediaSizeNames[this.defaultMediaIndex];
/*      */         }
/* 1461 */         for (byte b1 = 0; b1 < this.mediaSizeNames.length; b1++) {
/* 1462 */           if (this.mediaSizeNames[b1].toString().indexOf(str) != -1) {
/* 1463 */             this.defaultMediaIndex = b1;
/* 1464 */             return this.mediaSizeNames[this.defaultMediaIndex];
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1469 */       return this.mediaSizeNames[this.defaultMediaIndex];
/*      */     } 
/* 1471 */     if (paramClass == MediaPrintableArea.class) {
/*      */       float f1, f2; MediaPrintableArea[] arrayOfMediaPrintableArea;
/* 1473 */       if (this.cps != null && (
/* 1474 */         arrayOfMediaPrintableArea = this.cps.getMediaPrintableArea()) != null) {
/* 1475 */         if (this.defaultMediaIndex == -1)
/*      */         {
/* 1477 */           getDefaultAttributeValue((Class)Media.class);
/*      */         }
/* 1479 */         return arrayOfMediaPrintableArea[this.defaultMediaIndex];
/*      */       } 
/* 1481 */       String str = Locale.getDefault().getCountry();
/*      */       
/* 1483 */       if (str != null && (str
/* 1484 */         .equals("") || str
/* 1485 */         .equals(Locale.US.getCountry()) || str
/* 1486 */         .equals(Locale.CANADA.getCountry()))) {
/* 1487 */         f1 = MediaSize.NA.LETTER.getX(25400) - 0.5F;
/* 1488 */         f2 = MediaSize.NA.LETTER.getY(25400) - 0.5F;
/*      */       } else {
/* 1490 */         f1 = MediaSize.ISO.A4.getX(25400) - 0.5F;
/* 1491 */         f2 = MediaSize.ISO.A4.getY(25400) - 0.5F;
/*      */       } 
/* 1493 */       return new MediaPrintableArea(0.25F, 0.25F, f1, f2, 25400);
/*      */     } 
/*      */     
/* 1496 */     if (paramClass == NumberUp.class)
/* 1497 */       return new NumberUp(1); 
/* 1498 */     if (paramClass == OrientationRequested.class) {
/* 1499 */       if (attributeClass != null) {
/* 1500 */         switch (attributeClass.getIntValue())
/*      */         { default:
/* 1502 */             return OrientationRequested.PORTRAIT;
/* 1503 */           case 4: return OrientationRequested.LANDSCAPE;
/* 1504 */           case 5: return OrientationRequested.REVERSE_LANDSCAPE;
/* 1505 */           case 6: break; }  return OrientationRequested.REVERSE_PORTRAIT;
/*      */       } 
/*      */       
/* 1508 */       return OrientationRequested.PORTRAIT;
/*      */     } 
/* 1510 */     if (paramClass == PageRanges.class) {
/* 1511 */       if (attributeClass != null) {
/* 1512 */         int[] arrayOfInt = attributeClass.getIntRangeValue();
/* 1513 */         return new PageRanges(arrayOfInt[0], arrayOfInt[1]);
/*      */       } 
/* 1515 */       return new PageRanges(1, 2147483647);
/*      */     } 
/* 1517 */     if (paramClass == RequestingUserName.class) {
/* 1518 */       String str = "";
/*      */       try {
/* 1520 */         str = System.getProperty("user.name", "");
/* 1521 */       } catch (SecurityException securityException) {}
/*      */       
/* 1523 */       return new RequestingUserName(str, null);
/* 1524 */     }  if (paramClass == SheetCollate.class)
/* 1525 */       return SheetCollate.UNCOLLATED; 
/* 1526 */     if (paramClass == Sides.class) {
/* 1527 */       if (attributeClass != null) {
/* 1528 */         if (attributeClass.getStringValue().endsWith("long-edge"))
/* 1529 */           return Sides.TWO_SIDED_LONG_EDGE; 
/* 1530 */         if (attributeClass.getStringValue().endsWith("short-edge"))
/*      */         {
/* 1532 */           return Sides.TWO_SIDED_SHORT_EDGE;
/*      */         }
/*      */       } 
/* 1535 */       return Sides.ONE_SIDED;
/*      */     } 
/*      */     
/* 1538 */     return null;
/*      */   }
/*      */   
/*      */   public ServiceUIFactory getServiceUIFactory() {
/* 1542 */     return null;
/*      */   }
/*      */   
/*      */   public void wakeNotifier() {
/* 1546 */     synchronized (this) {
/* 1547 */       if (this.notifier != null) {
/* 1548 */         this.notifier.wake();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addPrintServiceAttributeListener(PrintServiceAttributeListener paramPrintServiceAttributeListener) {
/* 1555 */     synchronized (this) {
/* 1556 */       if (paramPrintServiceAttributeListener == null) {
/*      */         return;
/*      */       }
/* 1559 */       if (this.notifier == null) {
/* 1560 */         this.notifier = new ServiceNotifier(this);
/*      */       }
/* 1562 */       this.notifier.addListener(paramPrintServiceAttributeListener);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void removePrintServiceAttributeListener(PrintServiceAttributeListener paramPrintServiceAttributeListener) {
/* 1568 */     synchronized (this) {
/* 1569 */       if (paramPrintServiceAttributeListener == null || this.notifier == null) {
/*      */         return;
/*      */       }
/* 1572 */       this.notifier.removeListener(paramPrintServiceAttributeListener);
/* 1573 */       if (this.notifier.isEmpty()) {
/* 1574 */         this.notifier.stopNotifier();
/* 1575 */         this.notifier = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   String getDest() {
/* 1581 */     return this.printer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 1589 */     if (PrintServiceLookupProvider.isMac()) {
/* 1590 */       PrintServiceAttributeSet printServiceAttributeSet = getAttributes();
/* 1591 */       if (printServiceAttributeSet != null) {
/* 1592 */         PrinterInfo printerInfo = (PrinterInfo)printServiceAttributeSet.get(PrinterInfo.class);
/* 1593 */         if (printerInfo != null) {
/* 1594 */           return printerInfo.toString();
/*      */         }
/*      */       } 
/*      */     } 
/* 1598 */     return this.printer;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean usesClass(Class<PSPrinterJob> paramClass) {
/* 1603 */     return (paramClass == PSPrinterJob.class);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static HttpURLConnection getIPPConnection(URL paramURL) {
/*      */     URLConnection uRLConnection;
/*      */     try {
/* 1611 */       uRLConnection = paramURL.openConnection();
/* 1612 */     } catch (IOException iOException) {
/* 1613 */       return null;
/*      */     } 
/* 1615 */     if (!(uRLConnection instanceof HttpURLConnection)) {
/* 1616 */       return null;
/*      */     }
/* 1618 */     HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection;
/* 1619 */     httpURLConnection.setUseCaches(false);
/* 1620 */     httpURLConnection.setDefaultUseCaches(false);
/* 1621 */     httpURLConnection.setDoInput(true);
/* 1622 */     httpURLConnection.setDoOutput(true);
/* 1623 */     httpURLConnection.setRequestProperty("Content-type", "application/ipp");
/* 1624 */     return httpURLConnection;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized boolean isPostscript() {
/* 1629 */     if (this.isPS == null) {
/* 1630 */       this.isPS = Boolean.TRUE;
/* 1631 */       if (this.isCupsPrinter) {
/*      */         try {
/* 1633 */           this.urlConnection = getIPPConnection(new URL(this.myURL + ".ppd"));
/*      */ 
/*      */           
/* 1636 */           InputStream inputStream = this.urlConnection.getInputStream();
/* 1637 */           if (inputStream != null) {
/*      */ 
/*      */             
/* 1640 */             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("ISO-8859-1")));
/*      */             String str;
/* 1642 */             while ((str = bufferedReader.readLine()) != null) {
/* 1643 */               if (str.startsWith("*cupsFilter:")) {
/* 1644 */                 this.isPS = Boolean.FALSE;
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/* 1649 */         } catch (IOException iOException) {
/* 1650 */           debug_println(" isPostscript, e= " + iOException);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1658 */     return this.isPS.booleanValue();
/*      */   }
/*      */ 
/*      */   
/*      */   private void opGetAttributes() {
/*      */     try {
/* 1664 */       debug_println("IPPPrintService>> opGetAttributes myURI " + this.myURI + " myURL " + this.myURL);
/*      */       
/* 1666 */       AttributeClass[] arrayOfAttributeClass1 = { AttributeClass.ATTRIBUTES_CHARSET, AttributeClass.ATTRIBUTES_NATURAL_LANGUAGE };
/*      */ 
/*      */ 
/*      */       
/* 1670 */       AttributeClass[] arrayOfAttributeClass2 = { AttributeClass.ATTRIBUTES_CHARSET, AttributeClass.ATTRIBUTES_NATURAL_LANGUAGE, new AttributeClass("printer-uri", 69, "" + this.myURI) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1678 */       OutputStream outputStream = AccessController.<OutputStream>doPrivileged(new PrivilegedAction<OutputStream>() {
/*      */             public Object run() {
/*      */               try {
/* 1681 */                 return IPPPrintService.this.urlConnection.getOutputStream();
/* 1682 */               } catch (Exception exception) {
/*      */                 
/* 1684 */                 return null;
/*      */               } 
/*      */             }
/*      */           });
/* 1688 */       if (outputStream == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1694 */       boolean bool = (this.myURI == null) ? writeIPPRequest(outputStream, "000B", arrayOfAttributeClass1) : writeIPPRequest(outputStream, "000B", arrayOfAttributeClass2);
/* 1695 */       if (bool) {
/* 1696 */         InputStream inputStream = null;
/* 1697 */         if ((inputStream = this.urlConnection.getInputStream()) != null) {
/* 1698 */           HashMap[] arrayOfHashMap = readIPPResponse(inputStream);
/*      */           
/* 1700 */           if (arrayOfHashMap != null && arrayOfHashMap.length > 0) {
/* 1701 */             this.getAttMap = arrayOfHashMap[0];
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1706 */             if (arrayOfHashMap.length > 1) {
/* 1707 */               for (byte b = 1; b < arrayOfHashMap.length; b++) {
/* 1708 */                 Set<Map.Entry<?, ?>> set = arrayOfHashMap[b].entrySet();
/* 1709 */                 for (Map.Entry<?, ?> entry : set) {
/* 1710 */                   if (!this.getAttMap.containsKey(entry.getValue())) {
/* 1711 */                     this.getAttMap.put(entry.getKey(), entry.getValue());
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } else {
/* 1718 */           debug_println("IPPPrintService>> opGetAttributes - null input stream");
/*      */         } 
/* 1720 */         inputStream.close();
/*      */       } 
/* 1722 */       outputStream.close();
/* 1723 */     } catch (IOException iOException) {
/* 1724 */       debug_println("IPPPrintService>> opGetAttributes - input/output stream: " + iOException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean writeIPPRequest(OutputStream paramOutputStream, String paramString, AttributeClass[] paramArrayOfAttributeClass) {
/*      */     OutputStreamWriter outputStreamWriter;
/*      */     try {
/* 1734 */       outputStreamWriter = new OutputStreamWriter(paramOutputStream, "UTF-8");
/* 1735 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 1736 */       debug_println("IPPPrintService>> writeIPPRequest, UTF-8 not supported? Exception: " + unsupportedEncodingException);
/* 1737 */       return false;
/*      */     } 
/* 1739 */     debug_println("IPPPrintService>> writeIPPRequest, op code= " + paramString);
/* 1740 */     char[] arrayOfChar1 = new char[2];
/* 1741 */     arrayOfChar1[0] = (char)Byte.parseByte(paramString.substring(0, 2), 16);
/* 1742 */     arrayOfChar1[1] = (char)Byte.parseByte(paramString.substring(2, 4), 16);
/* 1743 */     char[] arrayOfChar2 = { '\001', '\001', Character.MIN_VALUE, '\001' };
/*      */     try {
/* 1745 */       outputStreamWriter.write(arrayOfChar2, 0, 2);
/* 1746 */       outputStreamWriter.write(arrayOfChar1, 0, 2);
/* 1747 */       arrayOfChar2[0] = Character.MIN_VALUE; arrayOfChar2[1] = Character.MIN_VALUE;
/* 1748 */       outputStreamWriter.write(arrayOfChar2, 0, 4);
/*      */       
/* 1750 */       arrayOfChar2[0] = '\001';
/* 1751 */       outputStreamWriter.write(arrayOfChar2[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1757 */       for (byte b = 0; b < paramArrayOfAttributeClass.length; b++) {
/* 1758 */         AttributeClass attributeClass = paramArrayOfAttributeClass[b];
/* 1759 */         outputStreamWriter.write(attributeClass.getType());
/*      */         
/* 1761 */         char[] arrayOfChar = attributeClass.getLenChars();
/* 1762 */         outputStreamWriter.write(arrayOfChar, 0, 2);
/* 1763 */         outputStreamWriter.write("" + attributeClass, 0, attributeClass.getName().length());
/*      */ 
/*      */         
/* 1766 */         if (attributeClass.getType() >= 53 && attributeClass
/* 1767 */           .getType() <= 73) {
/* 1768 */           String str = (String)attributeClass.getObjectValue();
/* 1769 */           arrayOfChar2[0] = Character.MIN_VALUE; arrayOfChar2[1] = (char)str.length();
/* 1770 */           outputStreamWriter.write(arrayOfChar2, 0, 2);
/* 1771 */           outputStreamWriter.write(str, 0, str.length());
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1776 */       outputStreamWriter.write(3);
/* 1777 */       outputStreamWriter.flush();
/* 1778 */       outputStreamWriter.close();
/* 1779 */     } catch (IOException iOException) {
/* 1780 */       debug_println("IPPPrintService>> writeIPPRequest, IPPPrintService Exception in writeIPPRequest: " + iOException);
/* 1781 */       return false;
/*      */     } 
/* 1783 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static HashMap[] readIPPResponse(InputStream paramInputStream) {
/* 1789 */     if (paramInputStream == null) {
/* 1790 */       return null;
/*      */     }
/*      */     
/* 1793 */     byte[] arrayOfByte = new byte[MAX_ATTRIBUTE_LENGTH];
/*      */     
/*      */     try {
/* 1796 */       DataInputStream dataInputStream = new DataInputStream(paramInputStream);
/*      */ 
/*      */       
/* 1799 */       if (dataInputStream.read(arrayOfByte, 0, 8) > -1 && arrayOfByte[2] == 0) {
/*      */ 
/*      */ 
/*      */         
/* 1803 */         byte b = 0;
/* 1804 */         short s = 0;
/* 1805 */         String str = null;
/*      */         
/* 1807 */         byte b1 = 68;
/* 1808 */         ArrayList<HashMap<Object, Object>> arrayList = new ArrayList();
/* 1809 */         HashMap<Object, Object> hashMap = new HashMap<>();
/*      */         
/* 1811 */         arrayOfByte[0] = dataInputStream.readByte();
/*      */ 
/*      */         
/* 1814 */         while (arrayOfByte[0] >= 1 && arrayOfByte[0] <= 4 && arrayOfByte[0] != 3) {
/*      */ 
/*      */           
/* 1817 */           debug_println("IPPPrintService>> readIPPResponse, checking group tag,  response[0]= " + arrayOfByte[0]);
/*      */ 
/*      */           
/* 1820 */           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*      */           
/* 1822 */           b = 0;
/* 1823 */           str = null;
/*      */ 
/*      */           
/* 1826 */           arrayOfByte[0] = dataInputStream.readByte();
/* 1827 */           while (arrayOfByte[0] >= 16 && arrayOfByte[0] <= 74) {
/*      */ 
/*      */             
/* 1830 */             s = dataInputStream.readShort();
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1835 */             if (s != 0 && str != null) {
/*      */               
/* 1837 */               byteArrayOutputStream.write(b);
/* 1838 */               byteArrayOutputStream.flush();
/* 1839 */               byteArrayOutputStream.close();
/* 1840 */               byte[] arrayOfByte1 = byteArrayOutputStream.toByteArray();
/*      */ 
/*      */               
/* 1843 */               if (hashMap.containsKey(str)) {
/* 1844 */                 arrayList.add(hashMap);
/* 1845 */                 hashMap = new HashMap<>();
/*      */               } 
/*      */ 
/*      */               
/* 1849 */               if (b1 >= 33) {
/* 1850 */                 AttributeClass attributeClass = new AttributeClass(str, b1, arrayOfByte1);
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1855 */                 hashMap.put(attributeClass.getName(), attributeClass);
/* 1856 */                 debug_println("IPPPrintService>> readIPPResponse " + attributeClass);
/*      */               } 
/*      */               
/* 1859 */               byteArrayOutputStream = new ByteArrayOutputStream();
/* 1860 */               b = 0;
/*      */             } 
/*      */             
/* 1863 */             if (!b) {
/* 1864 */               b1 = arrayOfByte[0];
/*      */             }
/*      */             
/* 1867 */             if (s != 0) {
/*      */ 
/*      */               
/* 1870 */               if (s > MAX_ATTRIBUTE_LENGTH) {
/* 1871 */                 arrayOfByte = new byte[s];
/*      */               }
/* 1873 */               dataInputStream.read(arrayOfByte, 0, s);
/* 1874 */               str = new String(arrayOfByte, 0, s);
/*      */             } 
/*      */             
/* 1877 */             s = dataInputStream.readShort();
/*      */             
/* 1879 */             byteArrayOutputStream.write(s);
/*      */             
/* 1881 */             if (s > MAX_ATTRIBUTE_LENGTH) {
/* 1882 */               arrayOfByte = new byte[s];
/*      */             }
/* 1884 */             dataInputStream.read(arrayOfByte, 0, s);
/*      */             
/* 1886 */             byteArrayOutputStream.write(arrayOfByte, 0, s);
/* 1887 */             b++;
/*      */             
/* 1889 */             arrayOfByte[0] = dataInputStream.readByte();
/*      */           } 
/*      */           
/* 1892 */           if (str != null) {
/* 1893 */             byteArrayOutputStream.write(b);
/* 1894 */             byteArrayOutputStream.flush();
/* 1895 */             byteArrayOutputStream.close();
/*      */ 
/*      */             
/* 1898 */             if (b != 0 && hashMap
/* 1899 */               .containsKey(str)) {
/* 1900 */               arrayList.add(hashMap);
/* 1901 */               hashMap = new HashMap<>();
/*      */             } 
/*      */             
/* 1904 */             byte[] arrayOfByte1 = byteArrayOutputStream.toByteArray();
/*      */             
/* 1906 */             AttributeClass attributeClass = new AttributeClass(str, b1, arrayOfByte1);
/*      */ 
/*      */ 
/*      */             
/* 1910 */             hashMap.put(attributeClass.getName(), attributeClass);
/*      */           } 
/*      */         } 
/* 1913 */         dataInputStream.close();
/* 1914 */         if (hashMap != null && hashMap.size() > 0) {
/* 1915 */           arrayList.add(hashMap);
/*      */         }
/* 1917 */         return arrayList.<HashMap>toArray(
/* 1918 */             new HashMap[arrayList.size()]);
/*      */       } 
/* 1920 */       debug_println("IPPPrintService>> readIPPResponse client error, IPP status code: 0x" + 
/*      */           
/* 1922 */           toHex(arrayOfByte[2]) + toHex(arrayOfByte[3]));
/* 1923 */       return null;
/*      */     
/*      */     }
/* 1926 */     catch (IOException iOException) {
/* 1927 */       debug_println("IPPPrintService>> readIPPResponse: " + iOException);
/* 1928 */       if (debugPrint) {
/* 1929 */         iOException.printStackTrace();
/*      */       }
/* 1931 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String toHex(byte paramByte) {
/* 1936 */     String str = Integer.toHexString(paramByte & 0xFF);
/* 1937 */     return (str.length() == 2) ? str : ("0" + str);
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1941 */     return "IPP Printer : " + getName();
/*      */   }
/*      */   
/*      */   public boolean equals(Object paramObject) {
/* 1945 */     return (paramObject == this || (paramObject instanceof IPPPrintService && ((IPPPrintService)paramObject)
/*      */       
/* 1947 */       .getName().equals(getName())));
/*      */   }
/*      */   
/*      */   public int hashCode() {
/* 1951 */     return getClass().hashCode() + getName().hashCode();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/IPPPrintService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */