/*      */ package sun.print;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Frame;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.print.Book;
/*      */ import java.awt.print.PageFormat;
/*      */ import java.awt.print.Pageable;
/*      */ import java.awt.print.Paper;
/*      */ import java.awt.print.Printable;
/*      */ import java.awt.print.PrinterAbortException;
/*      */ import java.awt.print.PrinterException;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.io.File;
/*      */ import java.io.FilePermission;
/*      */ import java.io.IOException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Locale;
/*      */ import javax.print.DocFlavor;
/*      */ import javax.print.DocPrintJob;
/*      */ import javax.print.PrintException;
/*      */ import javax.print.PrintService;
/*      */ import javax.print.PrintServiceLookup;
/*      */ import javax.print.ServiceUI;
/*      */ import javax.print.StreamPrintService;
/*      */ import javax.print.StreamPrintServiceFactory;
/*      */ import javax.print.attribute.Attribute;
/*      */ import javax.print.attribute.AttributeSet;
/*      */ import javax.print.attribute.HashPrintRequestAttributeSet;
/*      */ import javax.print.attribute.PrintRequestAttributeSet;
/*      */ import javax.print.attribute.standard.Copies;
/*      */ import javax.print.attribute.standard.Destination;
/*      */ import javax.print.attribute.standard.DialogTypeSelection;
/*      */ import javax.print.attribute.standard.Fidelity;
/*      */ import javax.print.attribute.standard.JobName;
/*      */ import javax.print.attribute.standard.JobSheets;
/*      */ import javax.print.attribute.standard.Media;
/*      */ import javax.print.attribute.standard.MediaPrintableArea;
/*      */ import javax.print.attribute.standard.MediaSize;
/*      */ import javax.print.attribute.standard.MediaSizeName;
/*      */ import javax.print.attribute.standard.OrientationRequested;
/*      */ import javax.print.attribute.standard.PageRanges;
/*      */ import javax.print.attribute.standard.PrinterIsAcceptingJobs;
/*      */ import javax.print.attribute.standard.PrinterState;
/*      */ import javax.print.attribute.standard.PrinterStateReason;
/*      */ import javax.print.attribute.standard.PrinterStateReasons;
/*      */ import javax.print.attribute.standard.RequestingUserName;
/*      */ import javax.print.attribute.standard.SheetCollate;
/*      */ import javax.print.attribute.standard.Sides;
/*      */ import sun.awt.image.ByteInterleavedRaster;
/*      */ import sun.security.action.GetPropertyAction;
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
/*      */ public abstract class RasterPrinterJob
/*      */   extends PrinterJob
/*      */ {
/*      */   protected static final int PRINTER = 0;
/*      */   protected static final int FILE = 1;
/*      */   protected static final int STREAM = 2;
/*      */   protected static final int MAX_UNKNOWN_PAGES = 9999;
/*      */   protected static final int PD_ALLPAGES = 0;
/*      */   protected static final int PD_SELECTION = 1;
/*      */   protected static final int PD_PAGENUMS = 2;
/*      */   protected static final int PD_NOSELECTION = 4;
/*      */   private static final int MAX_BAND_SIZE = 4194304;
/*      */   private static final float DPI = 72.0F;
/*      */   private static final String FORCE_PIPE_PROP = "sun.java2d.print.pipeline";
/*      */   private static final String FORCE_RASTER = "raster";
/*      */   private static final String FORCE_PDL = "pdl";
/*      */   private static final String SHAPE_TEXT_PROP = "sun.java2d.print.shapetext";
/*      */   public static boolean forcePDL = false;
/*      */   public static boolean forceRaster = false;
/*      */   public static boolean shapeTextProp = false;
/*      */   
/*      */   static {
/*  185 */     String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.print.pipeline"));
/*      */ 
/*      */     
/*  188 */     if (str1 != null) {
/*  189 */       if (str1.equalsIgnoreCase("pdl")) {
/*  190 */         forcePDL = true;
/*  191 */       } else if (str1.equalsIgnoreCase("raster")) {
/*  192 */         forceRaster = true;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  197 */     String str2 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.print.shapetext"));
/*      */ 
/*      */     
/*  200 */     if (str2 != null) {
/*  201 */       shapeTextProp = true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  210 */   private int cachedBandWidth = 0;
/*  211 */   private int cachedBandHeight = 0;
/*  212 */   private BufferedImage cachedBand = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  217 */   private int mNumCopies = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean mCollate = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  236 */   private int mFirstPage = -1;
/*  237 */   private int mLastPage = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Paper previousPaper;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  254 */   protected Pageable mDocument = new Book();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  259 */   private String mDocName = "Java Printing";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean performingPrinting = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean userCancelled = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FilePermission printToFilePermission;
/*      */ 
/*      */ 
/*      */   
/*  278 */   private ArrayList redrawList = new ArrayList();
/*      */   
/*      */   private int copiesAttr;
/*      */   
/*      */   private String jobNameAttr;
/*      */   
/*      */   private String userNameAttr;
/*      */   
/*      */   private PageRanges pageRangesAttr;
/*      */   
/*      */   protected Sides sidesAttr;
/*      */   protected String destinationAttr;
/*      */   protected boolean noJobSheet = false;
/*  291 */   protected int mDestType = 1;
/*  292 */   protected String mDestination = "";
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean collateAttReq = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean landscapeRotates270 = false;
/*      */ 
/*      */ 
/*      */   
/*  304 */   protected PrintRequestAttributeSet attributes = null;
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
/*      */   protected PrintService myService;
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
/*      */   private class GraphicsState
/*      */   {
/*      */     Rectangle2D region;
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
/*      */     Shape theClip;
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
/*      */     AffineTransform theTransform;
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
/*      */     double sx;
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
/*      */     double sy;
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
/*      */     private GraphicsState() {}
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveState(AffineTransform paramAffineTransform, Shape paramShape, Rectangle2D paramRectangle2D, double paramDouble1, double paramDouble2) {
/*  421 */     GraphicsState graphicsState = new GraphicsState();
/*  422 */     graphicsState.theTransform = paramAffineTransform;
/*  423 */     graphicsState.theClip = paramShape;
/*  424 */     graphicsState.region = paramRectangle2D;
/*  425 */     graphicsState.sx = paramDouble1;
/*  426 */     graphicsState.sy = paramDouble2;
/*  427 */     this.redrawList.add(graphicsState);
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
/*      */   
/*      */   protected static PrintService lookupDefaultPrintService() {
/*  440 */     PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
/*      */ 
/*      */     
/*  443 */     if (printService != null && printService
/*  444 */       .isDocFlavorSupported(DocFlavor.SERVICE_FORMATTED.PAGEABLE) && printService
/*      */       
/*  446 */       .isDocFlavorSupported(DocFlavor.SERVICE_FORMATTED.PRINTABLE))
/*      */     {
/*  448 */       return printService;
/*      */     }
/*      */     
/*  451 */     PrintService[] arrayOfPrintService = PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PAGEABLE, null);
/*      */     
/*  453 */     if (arrayOfPrintService.length > 0) {
/*  454 */       return arrayOfPrintService[0];
/*      */     }
/*      */     
/*  457 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrintService getPrintService() {
/*  468 */     if (this.myService == null) {
/*  469 */       PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
/*  470 */       if (printService != null && printService
/*  471 */         .isDocFlavorSupported(DocFlavor.SERVICE_FORMATTED.PAGEABLE)) {
/*      */         
/*      */         try {
/*  474 */           setPrintService(printService);
/*  475 */           this.myService = printService;
/*  476 */         } catch (PrinterException printerException) {}
/*      */       }
/*      */       
/*  479 */       if (this.myService == null) {
/*  480 */         PrintService[] arrayOfPrintService = PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PAGEABLE, null);
/*      */         
/*  482 */         if (arrayOfPrintService.length > 0) {
/*      */           try {
/*  484 */             setPrintService(arrayOfPrintService[0]);
/*  485 */             this.myService = arrayOfPrintService[0];
/*  486 */           } catch (PrinterException printerException) {}
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  491 */     return this.myService;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPrintService(PrintService paramPrintService) throws PrinterException {
/*  507 */     if (paramPrintService == null)
/*  508 */       throw new PrinterException("Service cannot be null"); 
/*  509 */     if (!(paramPrintService instanceof StreamPrintService) && paramPrintService
/*  510 */       .getName() == null) {
/*  511 */       throw new PrinterException("Null PrintService name.");
/*      */     }
/*      */ 
/*      */     
/*  515 */     PrinterState printerState = paramPrintService.<PrinterState>getAttribute(PrinterState.class);
/*      */     
/*  517 */     if (printerState == PrinterState.STOPPED) {
/*      */       
/*  519 */       PrinterStateReasons printerStateReasons = paramPrintService.<PrinterStateReasons>getAttribute(PrinterStateReasons.class);
/*      */       
/*  521 */       if (printerStateReasons != null && printerStateReasons
/*  522 */         .containsKey(PrinterStateReason.SHUTDOWN))
/*      */       {
/*  524 */         throw new PrinterException("PrintService is no longer available.");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  529 */     if (paramPrintService.isDocFlavorSupported(DocFlavor.SERVICE_FORMATTED.PAGEABLE) && paramPrintService
/*      */       
/*  531 */       .isDocFlavorSupported(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
/*      */       
/*  533 */       this.myService = paramPrintService;
/*      */     } else {
/*  535 */       throw new PrinterException("Not a 2D print service: " + paramPrintService);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private PageFormat attributeToPageFormat(PrintService paramPrintService, PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/*  542 */     PageFormat pageFormat = defaultPage();
/*      */     
/*  544 */     if (paramPrintService == null) {
/*  545 */       return pageFormat;
/*      */     }
/*      */ 
/*      */     
/*  549 */     OrientationRequested orientationRequested = (OrientationRequested)paramPrintRequestAttributeSet.get(OrientationRequested.class);
/*  550 */     if (orientationRequested == null)
/*      */     {
/*  552 */       orientationRequested = (OrientationRequested)paramPrintService.getDefaultAttributeValue((Class)OrientationRequested.class);
/*      */     }
/*  554 */     if (orientationRequested == OrientationRequested.REVERSE_LANDSCAPE) {
/*  555 */       pageFormat.setOrientation(2);
/*  556 */     } else if (orientationRequested == OrientationRequested.LANDSCAPE) {
/*  557 */       pageFormat.setOrientation(0);
/*      */     } else {
/*  559 */       pageFormat.setOrientation(1);
/*      */     } 
/*      */     
/*  562 */     Media media = (Media)paramPrintRequestAttributeSet.get(Media.class);
/*  563 */     MediaSize mediaSize = getMediaSize(media, paramPrintService, pageFormat);
/*      */     
/*  565 */     Paper paper = new Paper();
/*  566 */     float[] arrayOfFloat = mediaSize.getSize(1);
/*  567 */     double d1 = Math.rint(arrayOfFloat[0] * 72.0D / 25400.0D);
/*  568 */     double d2 = Math.rint(arrayOfFloat[1] * 72.0D / 25400.0D);
/*  569 */     paper.setSize(d1, d2);
/*      */ 
/*      */     
/*  572 */     MediaPrintableArea mediaPrintableArea = (MediaPrintableArea)paramPrintRequestAttributeSet.get(MediaPrintableArea.class);
/*  573 */     if (mediaPrintableArea == null) {
/*  574 */       mediaPrintableArea = getDefaultPrintableArea(pageFormat, d1, d2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  580 */     double d3 = Math.rint((mediaPrintableArea
/*  581 */         .getX(25400) * 72.0F));
/*  582 */     double d5 = Math.rint((mediaPrintableArea
/*  583 */         .getY(25400) * 72.0F));
/*  584 */     double d4 = Math.rint((mediaPrintableArea
/*  585 */         .getWidth(25400) * 72.0F));
/*  586 */     double d6 = Math.rint((mediaPrintableArea
/*  587 */         .getHeight(25400) * 72.0F));
/*  588 */     paper.setImageableArea(d3, d5, d4, d6);
/*  589 */     pageFormat.setPaper(paper);
/*  590 */     return pageFormat;
/*      */   }
/*      */   
/*      */   protected MediaSize getMediaSize(Media paramMedia, PrintService paramPrintService, PageFormat paramPageFormat) {
/*  594 */     if (paramMedia == null) {
/*  595 */       paramMedia = (Media)paramPrintService.getDefaultAttributeValue((Class)Media.class);
/*      */     }
/*  597 */     if (!(paramMedia instanceof MediaSizeName)) {
/*  598 */       paramMedia = MediaSizeName.NA_LETTER;
/*      */     }
/*  600 */     MediaSize mediaSize = MediaSize.getMediaSizeForName((MediaSizeName)paramMedia);
/*  601 */     return (mediaSize != null) ? mediaSize : MediaSize.NA.LETTER;
/*      */   } protected MediaPrintableArea getDefaultPrintableArea(PageFormat paramPageFormat, double paramDouble1, double paramDouble2) {
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*      */     double d4;
/*  607 */     if (paramDouble1 >= 432.0D) {
/*  608 */       d1 = 72.0D;
/*  609 */       d2 = paramDouble1 - 144.0D;
/*      */     } else {
/*  611 */       d1 = paramDouble1 / 6.0D;
/*  612 */       d2 = paramDouble1 * 0.75D;
/*      */     } 
/*  614 */     if (paramDouble2 >= 432.0D) {
/*  615 */       d3 = 72.0D;
/*  616 */       d4 = paramDouble2 - 144.0D;
/*      */     } else {
/*  618 */       d3 = paramDouble2 / 6.0D;
/*  619 */       d4 = paramDouble2 * 0.75D;
/*      */     } 
/*      */     
/*  622 */     return new MediaPrintableArea((float)(d1 / 72.0D), (float)(d3 / 72.0D), (float)(d2 / 72.0D), (float)(d4 / 72.0D), 25400);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updatePageAttributes(PrintService paramPrintService, PageFormat paramPageFormat) {
/*  628 */     if (this.attributes == null) {
/*  629 */       this.attributes = new HashPrintRequestAttributeSet();
/*      */     }
/*      */     
/*  632 */     updateAttributesWithPageFormat(paramPrintService, paramPageFormat, this.attributes);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateAttributesWithPageFormat(PrintService paramPrintService, PageFormat paramPageFormat, PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/*      */     OrientationRequested orientationRequested;
/*  638 */     if (paramPrintService == null || paramPageFormat == null || paramPrintRequestAttributeSet == null) {
/*      */       return;
/*      */     }
/*      */     
/*  642 */     float f1 = (float)Math.rint(paramPageFormat
/*  643 */         .getPaper().getWidth() * 25400.0D / 72.0D) / 25400.0F;
/*      */     
/*  645 */     float f2 = (float)Math.rint(paramPageFormat
/*  646 */         .getPaper().getHeight() * 25400.0D / 72.0D) / 25400.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  653 */     Media[] arrayOfMedia = (Media[])paramPrintService.getSupportedAttributeValues((Class)Media.class, null, null);
/*      */     
/*  655 */     Media media = null;
/*      */     try {
/*  657 */       media = CustomMediaSizeName.findMedia(arrayOfMedia, f1, f2, 25400);
/*      */     }
/*  659 */     catch (IllegalArgumentException illegalArgumentException) {}
/*      */     
/*  661 */     if (media == null || 
/*  662 */       !paramPrintService.isAttributeValueSupported(media, null, null)) {
/*  663 */       media = (Media)paramPrintService.getDefaultAttributeValue((Class)Media.class);
/*      */     }
/*      */ 
/*      */     
/*  667 */     switch (paramPageFormat.getOrientation()) {
/*      */       case 0:
/*  669 */         orientationRequested = OrientationRequested.LANDSCAPE;
/*      */         break;
/*      */       case 2:
/*  672 */         orientationRequested = OrientationRequested.REVERSE_LANDSCAPE;
/*      */         break;
/*      */       default:
/*  675 */         orientationRequested = OrientationRequested.PORTRAIT;
/*      */         break;
/*      */     } 
/*  678 */     if (media != null) {
/*  679 */       paramPrintRequestAttributeSet.add(media);
/*      */     }
/*  681 */     paramPrintRequestAttributeSet.add(orientationRequested);
/*      */     
/*  683 */     float f3 = (float)(paramPageFormat.getPaper().getImageableX() / 72.0D);
/*  684 */     float f4 = (float)(paramPageFormat.getPaper().getImageableWidth() / 72.0D);
/*  685 */     float f5 = (float)(paramPageFormat.getPaper().getImageableY() / 72.0D);
/*  686 */     float f6 = (float)(paramPageFormat.getPaper().getImageableHeight() / 72.0D);
/*  687 */     if (f3 < 0.0F) f3 = 0.0F;  if (f5 < 0.0F) f5 = 0.0F; 
/*      */     try {
/*  689 */       paramPrintRequestAttributeSet.add(new MediaPrintableArea(f3, f5, f4, f6, 25400));
/*      */     }
/*  691 */     catch (IllegalArgumentException illegalArgumentException) {}
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
/*      */   public PageFormat pageDialog(PageFormat paramPageFormat) throws HeadlessException {
/*  719 */     if (GraphicsEnvironment.isHeadless()) {
/*  720 */       throw new HeadlessException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  725 */     final GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
/*      */ 
/*      */     
/*  728 */     PrintService printService = AccessController.<PrintService>doPrivileged(new PrivilegedAction<PrintService>()
/*      */         {
/*      */           public Object run() {
/*  731 */             PrintService printService = RasterPrinterJob.this.getPrintService();
/*  732 */             if (printService == null) {
/*  733 */               ServiceDialog.showNoPrintService(gc);
/*  734 */               return null;
/*      */             } 
/*  736 */             return printService;
/*      */           }
/*      */         });
/*      */     
/*  740 */     if (printService == null) {
/*  741 */       return paramPageFormat;
/*      */     }
/*  743 */     updatePageAttributes(printService, paramPageFormat);
/*      */     
/*  745 */     PageFormat pageFormat = null;
/*      */     
/*  747 */     DialogTypeSelection dialogTypeSelection = (DialogTypeSelection)this.attributes.get(DialogTypeSelection.class);
/*  748 */     if (dialogTypeSelection == DialogTypeSelection.NATIVE) {
/*      */ 
/*      */       
/*  751 */       this.attributes.remove(DialogTypeSelection.class);
/*  752 */       pageFormat = pageDialog(this.attributes);
/*      */       
/*  754 */       this.attributes.add(DialogTypeSelection.NATIVE);
/*      */     } else {
/*  756 */       pageFormat = pageDialog(this.attributes);
/*      */     } 
/*      */     
/*  759 */     if (pageFormat == null) {
/*  760 */       return paramPageFormat;
/*      */     }
/*  762 */     return pageFormat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PageFormat pageDialog(PrintRequestAttributeSet paramPrintRequestAttributeSet) throws HeadlessException {
/*  772 */     if (GraphicsEnvironment.isHeadless()) {
/*  773 */       throw new HeadlessException();
/*      */     }
/*      */ 
/*      */     
/*  777 */     DialogTypeSelection dialogTypeSelection = (DialogTypeSelection)paramPrintRequestAttributeSet.get(DialogTypeSelection.class);
/*      */ 
/*      */     
/*  780 */     if (dialogTypeSelection == DialogTypeSelection.NATIVE) {
/*  781 */       PrintService printService1 = getPrintService();
/*  782 */       PageFormat pageFormat1 = attributeToPageFormat(printService1, paramPrintRequestAttributeSet);
/*      */       
/*  784 */       setParentWindowID(paramPrintRequestAttributeSet);
/*  785 */       PageFormat pageFormat2 = pageDialog(pageFormat1);
/*  786 */       clearParentWindowID();
/*      */ 
/*      */ 
/*      */       
/*  790 */       if (pageFormat2 == pageFormat1) {
/*  791 */         return null;
/*      */       }
/*  793 */       updateAttributesWithPageFormat(printService1, pageFormat2, paramPrintRequestAttributeSet);
/*  794 */       return pageFormat2;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  799 */     final GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
/*  800 */     Rectangle rectangle = graphicsConfiguration.getBounds();
/*  801 */     int i = rectangle.x + rectangle.width / 3;
/*  802 */     int j = rectangle.y + rectangle.height / 3;
/*      */ 
/*      */     
/*  805 */     PrintService printService = AccessController.<PrintService>doPrivileged(new PrivilegedAction<PrintService>()
/*      */         {
/*      */           public Object run() {
/*  808 */             PrintService printService = RasterPrinterJob.this.getPrintService();
/*  809 */             if (printService == null) {
/*  810 */               ServiceDialog.showNoPrintService(gc);
/*  811 */               return null;
/*      */             } 
/*  813 */             return printService;
/*      */           }
/*      */         });
/*      */     
/*  817 */     if (printService == null) {
/*  818 */       return null;
/*      */     }
/*      */     
/*  821 */     if (this.onTop != null) {
/*  822 */       paramPrintRequestAttributeSet.add(this.onTop);
/*      */     }
/*      */     
/*  825 */     ServiceDialog serviceDialog = new ServiceDialog(graphicsConfiguration, i, j, printService, DocFlavor.SERVICE_FORMATTED.PAGEABLE, paramPrintRequestAttributeSet, (Frame)null);
/*      */ 
/*      */     
/*  828 */     serviceDialog.show();
/*      */     
/*  830 */     if (serviceDialog.getStatus() == 1) {
/*      */       
/*  832 */       PrintRequestAttributeSet printRequestAttributeSet = serviceDialog.getAttributes();
/*  833 */       Class<SunAlternateMedia> clazz = SunAlternateMedia.class;
/*      */       
/*  835 */       if (paramPrintRequestAttributeSet.containsKey(clazz) && 
/*  836 */         !printRequestAttributeSet.containsKey(clazz)) {
/*  837 */         paramPrintRequestAttributeSet.remove(clazz);
/*      */       }
/*  839 */       paramPrintRequestAttributeSet.addAll(printRequestAttributeSet);
/*  840 */       return attributeToPageFormat(printService, paramPrintRequestAttributeSet);
/*      */     } 
/*  842 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected PageFormat getPageFormatFromAttributes() {
/*  847 */     Pageable pageable = null;
/*  848 */     if (this.attributes == null || this.attributes.isEmpty() || 
/*  849 */       !(pageable = getPageable() instanceof OpenBook)) {
/*  850 */       return null;
/*      */     }
/*      */     
/*  853 */     PageFormat pageFormat1 = attributeToPageFormat(
/*  854 */         getPrintService(), this.attributes);
/*  855 */     PageFormat pageFormat2 = null;
/*  856 */     if ((pageFormat2 = pageable.getPageFormat(0)) != null) {
/*      */ 
/*      */ 
/*      */       
/*  860 */       if (this.attributes.get(OrientationRequested.class) == null) {
/*  861 */         pageFormat1.setOrientation(pageFormat2.getOrientation());
/*      */       }
/*      */       
/*  864 */       Paper paper1 = pageFormat1.getPaper();
/*  865 */       Paper paper2 = pageFormat2.getPaper();
/*  866 */       boolean bool = false;
/*  867 */       if (this.attributes.get(MediaSizeName.class) == null) {
/*  868 */         paper1.setSize(paper2.getWidth(), paper2.getHeight());
/*  869 */         bool = true;
/*      */       } 
/*  871 */       if (this.attributes.get(MediaPrintableArea.class) == null) {
/*  872 */         paper1.setImageableArea(paper2
/*  873 */             .getImageableX(), paper2.getImageableY(), paper2
/*  874 */             .getImageableWidth(), paper2
/*  875 */             .getImageableHeight());
/*  876 */         bool = true;
/*      */       } 
/*  878 */       if (bool) {
/*  879 */         pageFormat1.setPaper(paper1);
/*      */       }
/*      */     } 
/*  882 */     return pageFormat1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean printDialog(PrintRequestAttributeSet paramPrintRequestAttributeSet) throws HeadlessException {
/*      */     PrintService arrayOfPrintService[], printService2;
/*  903 */     if (GraphicsEnvironment.isHeadless()) {
/*  904 */       throw new HeadlessException();
/*      */     }
/*      */ 
/*      */     
/*  908 */     DialogTypeSelection dialogTypeSelection = (DialogTypeSelection)paramPrintRequestAttributeSet.get(DialogTypeSelection.class);
/*      */ 
/*      */     
/*  911 */     if (dialogTypeSelection == DialogTypeSelection.NATIVE) {
/*  912 */       this.attributes = paramPrintRequestAttributeSet;
/*      */       try {
/*  914 */         debug_println("calling setAttributes in printDialog");
/*  915 */         setAttributes(paramPrintRequestAttributeSet);
/*      */       }
/*  917 */       catch (PrinterException printerException) {}
/*      */ 
/*      */ 
/*      */       
/*  921 */       setParentWindowID(paramPrintRequestAttributeSet);
/*  922 */       boolean bool = printDialog();
/*  923 */       clearParentWindowID();
/*  924 */       this.attributes = paramPrintRequestAttributeSet;
/*  925 */       return bool;
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
/*      */     
/*  940 */     final GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
/*      */ 
/*      */     
/*  943 */     PrintService printService1 = AccessController.<PrintService>doPrivileged(new PrivilegedAction<PrintService>()
/*      */         {
/*      */           public Object run() {
/*  946 */             PrintService printService = RasterPrinterJob.this.getPrintService();
/*  947 */             if (printService == null) {
/*  948 */               ServiceDialog.showNoPrintService(gc);
/*  949 */               return null;
/*      */             } 
/*  951 */             return printService;
/*      */           }
/*      */         });
/*      */     
/*  955 */     if (printService1 == null) {
/*  956 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  960 */     StreamPrintServiceFactory[] arrayOfStreamPrintServiceFactory = null;
/*  961 */     if (printService1 instanceof StreamPrintService) {
/*  962 */       arrayOfStreamPrintServiceFactory = lookupStreamPrintServices(null);
/*  963 */       StreamPrintService[] arrayOfStreamPrintService = new StreamPrintService[arrayOfStreamPrintServiceFactory.length];
/*  964 */       for (byte b = 0; b < arrayOfStreamPrintServiceFactory.length; b++) {
/*  965 */         arrayOfStreamPrintService[b] = arrayOfStreamPrintServiceFactory[b].getPrintService(null);
/*      */       }
/*      */     } else {
/*      */       
/*  969 */       arrayOfPrintService = AccessController.<PrintService[]>doPrivileged(new PrivilegedAction<PrintService>()
/*      */           {
/*      */             public Object run() {
/*  972 */               return PrinterJob.lookupPrintServices();
/*      */             }
/*      */           });
/*      */ 
/*      */       
/*  977 */       if (arrayOfPrintService == null || arrayOfPrintService.length == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  982 */         arrayOfPrintService = new PrintService[1];
/*  983 */         arrayOfPrintService[0] = printService1;
/*      */       } 
/*      */     } 
/*      */     
/*  987 */     Rectangle rectangle = graphicsConfiguration.getBounds();
/*  988 */     int i = rectangle.x + rectangle.width / 3;
/*  989 */     int j = rectangle.y + rectangle.height / 3;
/*      */ 
/*      */     
/*  992 */     PrinterJobWrapper printerJobWrapper = new PrinterJobWrapper(this);
/*  993 */     paramPrintRequestAttributeSet.add(printerJobWrapper);
/*      */     
/*      */     try {
/*  996 */       printService2 = ServiceUI.printDialog(graphicsConfiguration, i, j, arrayOfPrintService, printService1, DocFlavor.SERVICE_FORMATTED.PAGEABLE, paramPrintRequestAttributeSet);
/*      */ 
/*      */     
/*      */     }
/* 1000 */     catch (IllegalArgumentException illegalArgumentException) {
/* 1001 */       printService2 = ServiceUI.printDialog(graphicsConfiguration, i, j, arrayOfPrintService, arrayOfPrintService[0], DocFlavor.SERVICE_FORMATTED.PAGEABLE, paramPrintRequestAttributeSet);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1006 */     paramPrintRequestAttributeSet.remove(PrinterJobWrapper.class);
/*      */     
/* 1008 */     if (printService2 == null) {
/* 1009 */       return false;
/*      */     }
/*      */     
/* 1012 */     if (!printService1.equals(printService2)) {
/*      */       try {
/* 1014 */         setPrintService(printService2);
/* 1015 */       } catch (PrinterException printerException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1021 */         this.myService = printService2;
/*      */       } 
/*      */     }
/* 1024 */     return true;
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
/*      */ 
/*      */   
/*      */   public boolean printDialog() throws HeadlessException {
/* 1038 */     if (GraphicsEnvironment.isHeadless()) {
/* 1039 */       throw new HeadlessException();
/*      */     }
/*      */     
/* 1042 */     HashPrintRequestAttributeSet hashPrintRequestAttributeSet = new HashPrintRequestAttributeSet();
/*      */     
/* 1044 */     hashPrintRequestAttributeSet.add(new Copies(getCopies()));
/* 1045 */     hashPrintRequestAttributeSet.add(new JobName(getJobName(), null));
/* 1046 */     boolean bool = printDialog(hashPrintRequestAttributeSet);
/* 1047 */     if (bool) {
/* 1048 */       JobName jobName = (JobName)hashPrintRequestAttributeSet.get(JobName.class);
/* 1049 */       if (jobName != null) {
/* 1050 */         setJobName(jobName.getValue());
/*      */       }
/* 1052 */       Copies copies = (Copies)hashPrintRequestAttributeSet.get(Copies.class);
/* 1053 */       if (copies != null) {
/* 1054 */         setCopies(copies.getValue());
/*      */       }
/*      */       
/* 1057 */       Destination destination = (Destination)hashPrintRequestAttributeSet.get(Destination.class);
/*      */       
/* 1059 */       if (destination != null) {
/*      */         try {
/* 1061 */           this.mDestType = 1;
/* 1062 */           this.mDestination = (new File(destination.getURI())).getPath();
/* 1063 */         } catch (Exception exception) {
/* 1064 */           this.mDestination = "out.prn";
/* 1065 */           PrintService printService = getPrintService();
/* 1066 */           if (printService != null) {
/*      */             
/* 1068 */             Destination destination1 = (Destination)printService.getDefaultAttributeValue((Class)Destination.class);
/* 1069 */             if (destination1 != null) {
/* 1070 */               this.mDestination = (new File(destination1.getURI())).getPath();
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1075 */         this.mDestType = 0;
/* 1076 */         PrintService printService = getPrintService();
/* 1077 */         if (printService != null) {
/* 1078 */           this.mDestination = printService.getName();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1083 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPrintable(Printable paramPrintable) {
/* 1093 */     setPageable(new OpenBook(defaultPage(new PageFormat()), paramPrintable));
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
/*      */   public void setPrintable(Printable paramPrintable, PageFormat paramPageFormat) {
/* 1105 */     setPageable(new OpenBook(paramPageFormat, paramPrintable));
/* 1106 */     updatePageAttributes(getPrintService(), paramPageFormat);
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
/*      */ 
/*      */   
/*      */   public void setPageable(Pageable paramPageable) throws NullPointerException {
/* 1120 */     if (paramPageable != null) {
/* 1121 */       this.mDocument = paramPageable;
/*      */     } else {
/*      */       
/* 1124 */       throw new NullPointerException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initPrinter() {}
/*      */ 
/*      */   
/*      */   protected boolean isSupportedValue(Attribute paramAttribute, PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/* 1134 */     PrintService printService = getPrintService();
/* 1135 */     return (paramAttribute != null && printService != null && printService
/*      */       
/* 1137 */       .isAttributeValueSupported(paramAttribute, DocFlavor.SERVICE_FORMATTED.PAGEABLE, paramPrintRequestAttributeSet));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setAttributes(PrintRequestAttributeSet paramPrintRequestAttributeSet) throws PrinterException {
/* 1148 */     setCollated(false);
/* 1149 */     this.sidesAttr = null;
/* 1150 */     this.pageRangesAttr = null;
/* 1151 */     this.copiesAttr = 0;
/* 1152 */     this.jobNameAttr = null;
/* 1153 */     this.userNameAttr = null;
/* 1154 */     this.destinationAttr = null;
/* 1155 */     this.collateAttReq = false;
/*      */     
/* 1157 */     PrintService printService = getPrintService();
/* 1158 */     if (paramPrintRequestAttributeSet == null || printService == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1162 */     boolean bool = false;
/* 1163 */     Fidelity fidelity = (Fidelity)paramPrintRequestAttributeSet.get(Fidelity.class);
/* 1164 */     if (fidelity != null && fidelity == Fidelity.FIDELITY_TRUE) {
/* 1165 */       bool = true;
/*      */     }
/*      */     
/* 1168 */     if (bool == true) {
/*      */       
/* 1170 */       AttributeSet attributeSet = printService.getUnsupportedAttributes(DocFlavor.SERVICE_FORMATTED.PAGEABLE, paramPrintRequestAttributeSet);
/*      */ 
/*      */       
/* 1173 */       if (attributeSet != null) {
/* 1174 */         throw new PrinterException("Fidelity cannot be satisfied");
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
/* 1185 */     SheetCollate sheetCollate = (SheetCollate)paramPrintRequestAttributeSet.get(SheetCollate.class);
/* 1186 */     if (isSupportedValue(sheetCollate, paramPrintRequestAttributeSet)) {
/* 1187 */       setCollated((sheetCollate == SheetCollate.COLLATED));
/*      */     }
/*      */     
/* 1190 */     this.sidesAttr = (Sides)paramPrintRequestAttributeSet.get(Sides.class);
/* 1191 */     if (!isSupportedValue(this.sidesAttr, paramPrintRequestAttributeSet)) {
/* 1192 */       this.sidesAttr = Sides.ONE_SIDED;
/*      */     }
/*      */     
/* 1195 */     this.pageRangesAttr = (PageRanges)paramPrintRequestAttributeSet.get(PageRanges.class);
/* 1196 */     if (!isSupportedValue(this.pageRangesAttr, paramPrintRequestAttributeSet)) {
/* 1197 */       this.pageRangesAttr = null;
/*      */     }
/* 1199 */     else if ((SunPageSelection)paramPrintRequestAttributeSet.get(SunPageSelection.class) == SunPageSelection.RANGE) {
/*      */ 
/*      */       
/* 1202 */       int[][] arrayOfInt = this.pageRangesAttr.getMembers();
/*      */       
/* 1204 */       setPageRange(arrayOfInt[0][0] - 1, arrayOfInt[0][1] - 1);
/*      */     } else {
/* 1206 */       setPageRange(-1, -1);
/*      */     } 
/*      */ 
/*      */     
/* 1210 */     Copies copies = (Copies)paramPrintRequestAttributeSet.get(Copies.class);
/* 1211 */     if (isSupportedValue(copies, paramPrintRequestAttributeSet) || (!bool && copies != null)) {
/*      */       
/* 1213 */       this.copiesAttr = copies.getValue();
/* 1214 */       setCopies(this.copiesAttr);
/*      */     } else {
/* 1216 */       this.copiesAttr = getCopies();
/*      */     } 
/*      */ 
/*      */     
/* 1220 */     Destination destination = (Destination)paramPrintRequestAttributeSet.get(Destination.class);
/*      */     
/* 1222 */     if (isSupportedValue(destination, paramPrintRequestAttributeSet)) {
/*      */       
/*      */       try {
/*      */ 
/*      */         
/* 1227 */         this
/* 1228 */           .destinationAttr = "" + new File(destination.getURI().getSchemeSpecificPart());
/* 1229 */       } catch (Exception exception) {
/*      */         
/* 1231 */         Destination destination1 = (Destination)printService.getDefaultAttributeValue((Class)Destination.class);
/* 1232 */         if (destination1 != null) {
/* 1233 */           this
/* 1234 */             .destinationAttr = "" + new File(destination1.getURI().getSchemeSpecificPart());
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1239 */     JobSheets jobSheets = (JobSheets)paramPrintRequestAttributeSet.get(JobSheets.class);
/* 1240 */     if (jobSheets != null) {
/* 1241 */       this.noJobSheet = (jobSheets == JobSheets.NONE);
/*      */     }
/*      */     
/* 1244 */     JobName jobName = (JobName)paramPrintRequestAttributeSet.get(JobName.class);
/* 1245 */     if (isSupportedValue(jobName, paramPrintRequestAttributeSet) || (!bool && jobName != null)) {
/*      */       
/* 1247 */       this.jobNameAttr = jobName.getValue();
/* 1248 */       setJobName(this.jobNameAttr);
/*      */     } else {
/* 1250 */       this.jobNameAttr = getJobName();
/*      */     } 
/*      */ 
/*      */     
/* 1254 */     RequestingUserName requestingUserName = (RequestingUserName)paramPrintRequestAttributeSet.get(RequestingUserName.class);
/* 1255 */     if (isSupportedValue(requestingUserName, paramPrintRequestAttributeSet) || (!bool && requestingUserName != null)) {
/*      */       
/* 1257 */       this.userNameAttr = requestingUserName.getValue();
/*      */     } else {
/*      */       try {
/* 1260 */         this.userNameAttr = getUserName();
/* 1261 */       } catch (SecurityException securityException) {
/* 1262 */         this.userNameAttr = "";
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1269 */     Media media = (Media)paramPrintRequestAttributeSet.get(Media.class);
/*      */     
/* 1271 */     OrientationRequested orientationRequested = (OrientationRequested)paramPrintRequestAttributeSet.get(OrientationRequested.class);
/*      */     
/* 1273 */     MediaPrintableArea mediaPrintableArea = (MediaPrintableArea)paramPrintRequestAttributeSet.get(MediaPrintableArea.class);
/*      */     
/* 1275 */     if ((orientationRequested != null || media != null || mediaPrintableArea != null) && 
/* 1276 */       getPageable() instanceof OpenBook) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1281 */       Pageable pageable = getPageable();
/* 1282 */       Printable printable = pageable.getPrintable(0);
/* 1283 */       PageFormat pageFormat = (PageFormat)pageable.getPageFormat(0).clone();
/* 1284 */       Paper paper = pageFormat.getPaper();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1289 */       if (mediaPrintableArea == null && media != null && printService
/*      */         
/* 1291 */         .isAttributeCategorySupported((Class)MediaPrintableArea.class)) {
/*      */         
/* 1293 */         Object object = printService.getSupportedAttributeValues((Class)MediaPrintableArea.class, null, paramPrintRequestAttributeSet);
/*      */         
/* 1295 */         if (object instanceof MediaPrintableArea[] && ((MediaPrintableArea[])object).length > 0)
/*      */         {
/* 1297 */           mediaPrintableArea = ((MediaPrintableArea[])object)[0];
/*      */         }
/*      */       } 
/*      */       
/* 1301 */       if (isSupportedValue(orientationRequested, paramPrintRequestAttributeSet) || (!bool && orientationRequested != null)) {
/*      */         boolean bool1;
/*      */         
/* 1304 */         if (orientationRequested.equals(OrientationRequested.REVERSE_LANDSCAPE)) {
/* 1305 */           bool1 = true;
/* 1306 */         } else if (orientationRequested.equals(OrientationRequested.LANDSCAPE)) {
/* 1307 */           bool1 = false;
/*      */         } else {
/* 1309 */           bool1 = true;
/*      */         } 
/* 1311 */         pageFormat.setOrientation(bool1);
/*      */       } 
/*      */       
/* 1314 */       if (isSupportedValue(media, paramPrintRequestAttributeSet) || (!bool && media != null))
/*      */       {
/* 1316 */         if (media instanceof MediaSizeName) {
/* 1317 */           MediaSizeName mediaSizeName = (MediaSizeName)media;
/* 1318 */           MediaSize mediaSize = MediaSize.getMediaSizeForName(mediaSizeName);
/* 1319 */           if (mediaSize != null) {
/* 1320 */             float f1 = mediaSize.getX(25400) * 72.0F;
/* 1321 */             float f2 = mediaSize.getY(25400) * 72.0F;
/* 1322 */             paper.setSize(f1, f2);
/* 1323 */             if (mediaPrintableArea == null) {
/* 1324 */               paper.setImageableArea(72.0D, 72.0D, f1 - 144.0D, f2 - 144.0D);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1332 */       if (isSupportedValue(mediaPrintableArea, paramPrintRequestAttributeSet) || (!bool && mediaPrintableArea != null)) {
/*      */ 
/*      */         
/* 1335 */         float[] arrayOfFloat = mediaPrintableArea.getPrintableArea(25400);
/* 1336 */         for (byte b = 0; b < arrayOfFloat.length; b++) {
/* 1337 */           arrayOfFloat[b] = arrayOfFloat[b] * 72.0F;
/*      */         }
/* 1339 */         paper.setImageableArea(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
/*      */       } 
/*      */ 
/*      */       
/* 1343 */       pageFormat.setPaper(paper);
/* 1344 */       pageFormat = validatePage(pageFormat);
/* 1345 */       setPrintable(printable, pageFormat);
/*      */     }
/*      */     else {
/*      */       
/* 1349 */       this.attributes = paramPrintRequestAttributeSet;
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
/*      */ 
/*      */ 
/*      */   
/*      */   protected void spoolToService(PrintService paramPrintService, PrintRequestAttributeSet paramPrintRequestAttributeSet) throws PrinterException {
/* 1365 */     if (paramPrintService == null) {
/* 1366 */       throw new PrinterException("No print service found.");
/*      */     }
/*      */     
/* 1369 */     DocPrintJob docPrintJob = paramPrintService.createPrintJob();
/* 1370 */     PageableDoc pageableDoc = new PageableDoc(getPageable());
/* 1371 */     if (paramPrintRequestAttributeSet == null) {
/* 1372 */       paramPrintRequestAttributeSet = new HashPrintRequestAttributeSet();
/*      */     }
/*      */     try {
/* 1375 */       docPrintJob.print(pageableDoc, paramPrintRequestAttributeSet);
/* 1376 */     } catch (PrintException printException) {
/* 1377 */       throw new PrinterException(printException.toString());
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
/*      */   public void print() throws PrinterException {
/* 1390 */     print(this.attributes);
/*      */   }
/*      */   public static boolean debugPrint = false;
/*      */   
/*      */   protected void debug_println(String paramString) {
/* 1395 */     if (debugPrint) {
/* 1396 */       System.out.println("RasterPrinterJob " + paramString + " " + this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private int deviceWidth;
/*      */   
/*      */   private int deviceHeight;
/*      */   
/*      */   private AffineTransform defaultDeviceTransform;
/*      */   
/*      */   private PrinterGraphicsConfig pgConfig;
/*      */   
/*      */   private DialogOnTop onTop;
/*      */   
/*      */   private long parentWindowID;
/*      */ 
/*      */   
/*      */   public void print(PrintRequestAttributeSet paramPrintRequestAttributeSet) throws PrinterException {
/* 1415 */     PrintService printService = getPrintService();
/* 1416 */     debug_println("psvc = " + printService);
/* 1417 */     if (printService == null) {
/* 1418 */       throw new PrinterException("No print service found.");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1423 */     PrinterState printerState = printService.<PrinterState>getAttribute(PrinterState.class);
/*      */     
/* 1425 */     if (printerState == PrinterState.STOPPED) {
/*      */       
/* 1427 */       PrinterStateReasons printerStateReasons = printService.<PrinterStateReasons>getAttribute(PrinterStateReasons.class);
/*      */       
/* 1429 */       if (printerStateReasons != null && printerStateReasons
/* 1430 */         .containsKey(PrinterStateReason.SHUTDOWN))
/*      */       {
/* 1432 */         throw new PrinterException("PrintService is no longer available.");
/*      */       }
/*      */     } 
/*      */     
/* 1436 */     if ((PrinterIsAcceptingJobs)printService.<PrinterIsAcceptingJobs>getAttribute(PrinterIsAcceptingJobs.class) == PrinterIsAcceptingJobs.NOT_ACCEPTING_JOBS)
/*      */     {
/*      */       
/* 1439 */       throw new PrinterException("Printer is not accepting job.");
/*      */     }
/*      */     
/* 1442 */     if (printService instanceof SunPrinterJobService && ((SunPrinterJobService)printService)
/* 1443 */       .usesClass(getClass())) {
/* 1444 */       setAttributes(paramPrintRequestAttributeSet);
/*      */       
/* 1446 */       if (this.destinationAttr != null) {
/* 1447 */         validateDestination(this.destinationAttr);
/*      */       }
/*      */     } else {
/* 1450 */       spoolToService(printService, paramPrintRequestAttributeSet);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1455 */     initPrinter();
/*      */     
/* 1457 */     int i = getCollatedCopies();
/* 1458 */     int j = getNoncollatedCopies();
/* 1459 */     debug_println("getCollatedCopies()  " + i + " getNoncollatedCopies() " + j);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1467 */     int k = this.mDocument.getNumberOfPages();
/* 1468 */     if (k == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1472 */     int m = getFirstPage();
/* 1473 */     int n = getLastPage();
/* 1474 */     if (n == -1) {
/* 1475 */       int i1 = this.mDocument.getNumberOfPages();
/* 1476 */       if (i1 != -1) {
/* 1477 */         n = this.mDocument.getNumberOfPages() - 1;
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/* 1482 */       synchronized (this) {
/* 1483 */         this.performingPrinting = true;
/* 1484 */         this.userCancelled = false;
/*      */       } 
/*      */       
/* 1487 */       startDoc();
/* 1488 */       if (isCancelled()) {
/* 1489 */         cancelDoc();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1494 */       boolean bool = true;
/* 1495 */       if (paramPrintRequestAttributeSet != null) {
/*      */         
/* 1497 */         SunPageSelection sunPageSelection = (SunPageSelection)paramPrintRequestAttributeSet.get(SunPageSelection.class);
/* 1498 */         if (sunPageSelection != null && sunPageSelection != SunPageSelection.RANGE) {
/* 1499 */           bool = false;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1504 */       debug_println("after startDoc rangeSelected? " + bool + " numNonCollatedCopies " + j);
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
/* 1521 */       for (byte b = 0; b < i; b++) {
/* 1522 */         int i1 = m, i2 = 0;
/*      */ 
/*      */         
/* 1525 */         for (; (i1 <= n || n == -1) && !i2; 
/* 1526 */           i1++) {
/*      */ 
/*      */           
/* 1529 */           if (this.pageRangesAttr != null && bool) {
/* 1530 */             int i3 = this.pageRangesAttr.next(i1);
/* 1531 */             if (i3 == -1)
/*      */               break; 
/* 1533 */             if (i3 != i1 + 1) {
/*      */               continue;
/*      */             }
/*      */           } 
/*      */           
/* 1538 */           byte b1 = 0;
/*      */           
/* 1540 */           for (; b1 < j && !i2; 
/* 1541 */             b1++) {
/*      */             
/* 1543 */             if (isCancelled()) {
/* 1544 */               cancelDoc();
/*      */             }
/* 1546 */             debug_println("printPage " + i1);
/* 1547 */             i2 = printPage(this.mDocument, i1);
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*      */       } 
/* 1553 */       if (isCancelled()) {
/* 1554 */         cancelDoc();
/*      */       }
/*      */     }
/*      */     finally {
/*      */       
/* 1559 */       this.previousPaper = null;
/* 1560 */       synchronized (this) {
/* 1561 */         if (this.performingPrinting) {
/* 1562 */           endDoc();
/*      */         }
/* 1564 */         this.performingPrinting = false;
/* 1565 */         notify();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void validateDestination(String paramString) throws PrinterException {
/* 1571 */     if (paramString == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1578 */     File file1 = new File(paramString);
/*      */     
/*      */     try {
/* 1581 */       if (file1.createNewFile()) {
/* 1582 */         file1.delete();
/*      */       }
/* 1584 */     } catch (IOException iOException) {
/* 1585 */       throw new PrinterException("Cannot write to file:" + paramString);
/*      */     }
/* 1587 */     catch (SecurityException securityException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1594 */     File file2 = file1.getParentFile();
/* 1595 */     if ((file1.exists() && (
/* 1596 */       !file1.isFile() || !file1.canWrite())) || (file2 != null && (
/*      */       
/* 1598 */       !file2.exists() || (file2.exists() && !file2.canWrite())))) {
/* 1599 */       throw new PrinterException("Cannot write to file:" + paramString);
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
/*      */ 
/*      */   
/*      */   protected void validatePaper(Paper paramPaper1, Paper paramPaper2) {
/* 1614 */     if (paramPaper1 == null || paramPaper2 == null) {
/*      */       return;
/*      */     }
/* 1617 */     double d1 = paramPaper1.getWidth();
/* 1618 */     double d2 = paramPaper1.getHeight();
/* 1619 */     double d3 = paramPaper1.getImageableX();
/* 1620 */     double d4 = paramPaper1.getImageableY();
/* 1621 */     double d5 = paramPaper1.getImageableWidth();
/* 1622 */     double d6 = paramPaper1.getImageableHeight();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1627 */     Paper paper = new Paper();
/* 1628 */     d1 = (d1 > 0.0D) ? d1 : paper.getWidth();
/* 1629 */     d2 = (d2 > 0.0D) ? d2 : paper.getHeight();
/* 1630 */     d3 = (d3 > 0.0D) ? d3 : paper.getImageableX();
/* 1631 */     d4 = (d4 > 0.0D) ? d4 : paper.getImageableY();
/* 1632 */     d5 = (d5 > 0.0D) ? d5 : paper.getImageableWidth();
/* 1633 */     d6 = (d6 > 0.0D) ? d6 : paper.getImageableHeight();
/*      */ 
/*      */ 
/*      */     
/* 1637 */     if (d5 > d1) {
/* 1638 */       d5 = d1;
/*      */     }
/* 1640 */     if (d6 > d2) {
/* 1641 */       d6 = d2;
/*      */     }
/* 1643 */     if (d3 + d5 > d1) {
/* 1644 */       d3 = d1 - d5;
/*      */     }
/* 1646 */     if (d4 + d6 > d2) {
/* 1647 */       d4 = d2 - d6;
/*      */     }
/* 1649 */     paramPaper2.setSize(d1, d2);
/* 1650 */     paramPaper2.setImageableArea(d3, d4, d5, d6);
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
/*      */   public PageFormat defaultPage(PageFormat paramPageFormat) {
/* 1662 */     PageFormat pageFormat = (PageFormat)paramPageFormat.clone();
/* 1663 */     pageFormat.setOrientation(1);
/* 1664 */     Paper paper = new Paper();
/* 1665 */     double d = 72.0D;
/*      */     
/* 1667 */     Media media = null;
/*      */     
/* 1669 */     PrintService printService = getPrintService();
/* 1670 */     if (printService != null) {
/*      */ 
/*      */       
/* 1673 */       media = (Media)printService.getDefaultAttributeValue((Class)Media.class);
/*      */       MediaSize mediaSize;
/* 1675 */       if (media instanceof MediaSizeName && (
/* 1676 */         mediaSize = MediaSize.getMediaSizeForName((MediaSizeName)media)) != null) {
/*      */         
/* 1678 */         double d1 = mediaSize.getX(25400) * d;
/* 1679 */         double d2 = mediaSize.getY(25400) * d;
/* 1680 */         paper.setSize(d1, d2);
/* 1681 */         paper.setImageableArea(d, d, d1 - 2.0D * d, d2 - 2.0D * d);
/*      */ 
/*      */         
/* 1684 */         pageFormat.setPaper(paper);
/* 1685 */         return pageFormat;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1692 */     String str = Locale.getDefault().getCountry();
/* 1693 */     if (!Locale.getDefault().equals(Locale.ENGLISH) && str != null && 
/*      */       
/* 1695 */       !str.equals(Locale.US.getCountry()) && 
/* 1696 */       !str.equals(Locale.CANADA.getCountry())) {
/*      */       
/* 1698 */       double d3 = 25.4D;
/* 1699 */       double d1 = Math.rint(210.0D * d / d3);
/* 1700 */       double d2 = Math.rint(297.0D * d / d3);
/* 1701 */       paper.setSize(d1, d2);
/* 1702 */       paper.setImageableArea(d, d, d1 - 2.0D * d, d2 - 2.0D * d);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1707 */     pageFormat.setPaper(paper);
/*      */     
/* 1709 */     return pageFormat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PageFormat validatePage(PageFormat paramPageFormat) {
/* 1717 */     PageFormat pageFormat = (PageFormat)paramPageFormat.clone();
/* 1718 */     Paper paper = new Paper();
/* 1719 */     validatePaper(pageFormat.getPaper(), paper);
/* 1720 */     pageFormat.setPaper(paper);
/*      */     
/* 1722 */     return pageFormat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCopies(int paramInt) {
/* 1729 */     this.mNumCopies = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCopies() {
/* 1736 */     return this.mNumCopies;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getCopiesInt() {
/* 1743 */     return (this.copiesAttr > 0) ? this.copiesAttr : getCopies();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUserName() {
/* 1751 */     return System.getProperty("user.name");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getUserNameInt() {
/* 1758 */     if (this.userNameAttr != null) {
/* 1759 */       return this.userNameAttr;
/*      */     }
/*      */     try {
/* 1762 */       return getUserName();
/* 1763 */     } catch (SecurityException securityException) {
/* 1764 */       return "";
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setJobName(String paramString) {
/* 1774 */     if (paramString != null) {
/* 1775 */       this.mDocName = paramString;
/*      */     } else {
/* 1777 */       throw new NullPointerException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getJobName() {
/* 1785 */     return this.mDocName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getJobNameInt() {
/* 1792 */     return (this.jobNameAttr != null) ? this.jobNameAttr : getJobName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setPageRange(int paramInt1, int paramInt2) {
/* 1803 */     if (paramInt1 >= 0 && paramInt2 >= 0) {
/* 1804 */       this.mFirstPage = paramInt1;
/* 1805 */       this.mLastPage = paramInt2;
/* 1806 */       if (this.mLastPage < this.mFirstPage) this.mLastPage = this.mFirstPage; 
/*      */     } else {
/* 1808 */       this.mFirstPage = -1;
/* 1809 */       this.mLastPage = -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getFirstPage() {
/* 1818 */     return (this.mFirstPage == -1) ? 0 : this.mFirstPage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getLastPage() {
/* 1826 */     return this.mLastPage;
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
/*      */   protected void setCollated(boolean paramBoolean) {
/* 1838 */     this.mCollate = paramBoolean;
/* 1839 */     this.collateAttReq = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isCollated() {
/* 1847 */     return this.mCollate;
/*      */   }
/*      */   
/*      */   protected final int getSelectAttrib() {
/* 1851 */     if (this.attributes != null) {
/*      */       
/* 1853 */       SunPageSelection sunPageSelection = (SunPageSelection)this.attributes.get(SunPageSelection.class);
/* 1854 */       if (sunPageSelection == SunPageSelection.RANGE)
/* 1855 */         return 2; 
/* 1856 */       if (sunPageSelection == SunPageSelection.SELECTION)
/* 1857 */         return 1; 
/* 1858 */       if (sunPageSelection == SunPageSelection.ALL) {
/* 1859 */         return 0;
/*      */       }
/*      */     } 
/* 1862 */     return 4;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final int getFromPageAttrib() {
/* 1867 */     if (this.attributes != null) {
/*      */       
/* 1869 */       PageRanges pageRanges = (PageRanges)this.attributes.get(PageRanges.class);
/* 1870 */       if (pageRanges != null) {
/* 1871 */         int[][] arrayOfInt = pageRanges.getMembers();
/* 1872 */         return arrayOfInt[0][0];
/*      */       } 
/*      */     } 
/* 1875 */     return getMinPageAttrib();
/*      */   }
/*      */ 
/*      */   
/*      */   protected final int getToPageAttrib() {
/* 1880 */     if (this.attributes != null) {
/*      */       
/* 1882 */       PageRanges pageRanges = (PageRanges)this.attributes.get(PageRanges.class);
/* 1883 */       if (pageRanges != null) {
/* 1884 */         int[][] arrayOfInt = pageRanges.getMembers();
/* 1885 */         return arrayOfInt[arrayOfInt.length - 1][1];
/*      */       } 
/*      */     } 
/* 1888 */     return getMaxPageAttrib();
/*      */   }
/*      */   
/*      */   protected final int getMinPageAttrib() {
/* 1892 */     if (this.attributes != null) {
/*      */       
/* 1894 */       SunMinMaxPage sunMinMaxPage = (SunMinMaxPage)this.attributes.get(SunMinMaxPage.class);
/* 1895 */       if (sunMinMaxPage != null) {
/* 1896 */         return sunMinMaxPage.getMin();
/*      */       }
/*      */     } 
/* 1899 */     return 1;
/*      */   }
/*      */   
/*      */   protected final int getMaxPageAttrib() {
/* 1903 */     if (this.attributes != null) {
/*      */       
/* 1905 */       SunMinMaxPage sunMinMaxPage = (SunMinMaxPage)this.attributes.get(SunMinMaxPage.class);
/* 1906 */       if (sunMinMaxPage != null) {
/* 1907 */         return sunMinMaxPage.getMax();
/*      */       }
/*      */     } 
/*      */     
/* 1911 */     Pageable pageable = getPageable();
/* 1912 */     if (pageable != null) {
/* 1913 */       int i = pageable.getNumberOfPages();
/* 1914 */       if (i <= -1) {
/* 1915 */         i = 9999;
/*      */       }
/* 1917 */       return (i == 0) ? 1 : i;
/*      */     } 
/*      */     
/* 1920 */     return Integer.MAX_VALUE;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void cancelDoc() throws PrinterAbortException {
/* 1939 */     abortDoc();
/* 1940 */     synchronized (this) {
/* 1941 */       this.userCancelled = false;
/* 1942 */       this.performingPrinting = false;
/* 1943 */       notify();
/*      */     } 
/* 1945 */     throw new PrinterAbortException();
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
/*      */   protected int getCollatedCopies() {
/* 1957 */     return isCollated() ? getCopiesInt() : 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getNoncollatedCopies() {
/* 1967 */     return isCollated() ? 1 : getCopiesInt();
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
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void setGraphicsConfigInfo(AffineTransform paramAffineTransform, double paramDouble1, double paramDouble2) {
/* 1982 */     Point2D.Double double_ = new Point2D.Double(paramDouble1, paramDouble2);
/* 1983 */     paramAffineTransform.transform(double_, double_);
/*      */     
/* 1985 */     if (this.pgConfig == null || this.defaultDeviceTransform == null || 
/*      */       
/* 1987 */       !paramAffineTransform.equals(this.defaultDeviceTransform) || this.deviceWidth != 
/* 1988 */       (int)double_.getX() || this.deviceHeight != 
/* 1989 */       (int)double_.getY()) {
/*      */       
/* 1991 */       this.deviceWidth = (int)double_.getX();
/* 1992 */       this.deviceHeight = (int)double_.getY();
/* 1993 */       this.defaultDeviceTransform = paramAffineTransform;
/* 1994 */       this.pgConfig = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   synchronized PrinterGraphicsConfig getPrinterGraphicsConfig() {
/* 1999 */     if (this.pgConfig != null) {
/* 2000 */       return this.pgConfig;
/*      */     }
/* 2002 */     String str = "Printer Device";
/* 2003 */     PrintService printService = getPrintService();
/* 2004 */     if (printService != null) {
/* 2005 */       str = printService.toString();
/*      */     }
/* 2007 */     this.pgConfig = new PrinterGraphicsConfig(str, this.defaultDeviceTransform, this.deviceWidth, this.deviceHeight);
/*      */ 
/*      */     
/* 2010 */     return this.pgConfig;
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
/*      */   
/*      */   protected int printPage(Pageable paramPageable, int paramInt) throws PrinterException {
/*      */     PageFormat pageFormat1, pageFormat2;
/*      */     Printable printable;
/*      */     try {
/* 2026 */       pageFormat2 = paramPageable.getPageFormat(paramInt);
/* 2027 */       pageFormat1 = (PageFormat)pageFormat2.clone();
/* 2028 */       printable = paramPageable.getPrintable(paramInt);
/* 2029 */     } catch (Exception exception) {
/* 2030 */       PrinterException printerException = new PrinterException("Error getting page or printable.[ " + exception + " ]");
/*      */ 
/*      */       
/* 2033 */       printerException.initCause(exception);
/* 2034 */       throw printerException;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2040 */     Paper paper = pageFormat1.getPaper();
/*      */     
/* 2042 */     if (pageFormat1.getOrientation() != 1 && this.landscapeRotates270) {
/*      */ 
/*      */       
/* 2045 */       double d3 = paper.getImageableX();
/* 2046 */       double d4 = paper.getImageableY();
/* 2047 */       double d5 = paper.getImageableWidth();
/* 2048 */       double d6 = paper.getImageableHeight();
/* 2049 */       paper.setImageableArea(paper.getWidth() - d3 - d5, paper
/* 2050 */           .getHeight() - d4 - d6, d5, d6);
/*      */       
/* 2052 */       pageFormat1.setPaper(paper);
/* 2053 */       if (pageFormat1.getOrientation() == 0) {
/* 2054 */         pageFormat1.setOrientation(2);
/*      */       } else {
/* 2056 */         pageFormat1.setOrientation(0);
/*      */       } 
/*      */     } 
/*      */     
/* 2060 */     double d1 = getXRes() / 72.0D;
/* 2061 */     double d2 = getYRes() / 72.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2070 */     Rectangle2D.Double double_1 = new Rectangle2D.Double(paper.getImageableX() * d1, paper.getImageableY() * d2, paper.getImageableWidth() * d1, paper.getImageableHeight() * d2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2076 */     AffineTransform affineTransform1 = new AffineTransform();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2081 */     AffineTransform affineTransform2 = new AffineTransform();
/* 2082 */     affineTransform2.scale(d1, d2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2087 */     int i = (int)double_1.getWidth();
/* 2088 */     if (i % 4 != 0) {
/* 2089 */       i += 4 - i % 4;
/*      */     }
/* 2091 */     if (i <= 0) {
/* 2092 */       throw new PrinterException("Paper's imageable width is too small.");
/*      */     }
/*      */     
/* 2095 */     int j = (int)double_1.getHeight();
/* 2096 */     if (j <= 0) {
/* 2097 */       throw new PrinterException("Paper's imageable height is too small.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2105 */     int k = 4194304 / i / 3;
/*      */     
/* 2107 */     int m = (int)Math.rint(paper.getImageableX() * d1);
/* 2108 */     int n = (int)Math.rint(paper.getImageableY() * d2);
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
/* 2119 */     AffineTransform affineTransform3 = new AffineTransform();
/* 2120 */     affineTransform3.translate(-m, n);
/* 2121 */     affineTransform3.translate(0.0D, k);
/* 2122 */     affineTransform3.scale(1.0D, -1.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2132 */     BufferedImage bufferedImage = new BufferedImage(1, 1, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2139 */     PeekGraphics peekGraphics = createPeekGraphics(bufferedImage.createGraphics(), this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2146 */     Rectangle2D.Double double_2 = new Rectangle2D.Double(pageFormat1.getImageableX(), pageFormat1.getImageableY(), pageFormat1.getImageableWidth(), pageFormat1.getImageableHeight());
/* 2147 */     peekGraphics.transform(affineTransform2);
/* 2148 */     peekGraphics.translate(-getPhysicalPrintableX(paper) / d1, 
/* 2149 */         -getPhysicalPrintableY(paper) / d2);
/* 2150 */     peekGraphics.transform(new AffineTransform(pageFormat1.getMatrix()));
/* 2151 */     initPrinterGraphics(peekGraphics, double_2);
/* 2152 */     AffineTransform affineTransform4 = peekGraphics.getTransform();
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
/* 2163 */     setGraphicsConfigInfo(affineTransform2, paper
/* 2164 */         .getWidth(), paper.getHeight());
/* 2165 */     int i1 = printable.print(peekGraphics, pageFormat2, paramInt);
/* 2166 */     debug_println("pageResult " + i1);
/* 2167 */     if (i1 == 0) {
/* 2168 */       debug_println("startPage " + paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2175 */       Paper paper1 = pageFormat1.getPaper();
/*      */ 
/*      */ 
/*      */       
/* 2179 */       boolean bool = (this.previousPaper == null || paper1.getWidth() != this.previousPaper.getWidth() || paper1.getHeight() != this.previousPaper.getHeight()) ? true : false;
/* 2180 */       this.previousPaper = paper1;
/*      */       
/* 2182 */       startPage(pageFormat1, printable, paramInt, bool);
/* 2183 */       Graphics2D graphics2D = createPathGraphics(peekGraphics, this, printable, pageFormat1, paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2193 */       if (graphics2D != null) {
/* 2194 */         graphics2D.transform(affineTransform2);
/*      */         
/* 2196 */         graphics2D.translate(-getPhysicalPrintableX(paper) / d1, 
/* 2197 */             -getPhysicalPrintableY(paper) / d2);
/* 2198 */         graphics2D.transform(new AffineTransform(pageFormat1.getMatrix()));
/* 2199 */         initPrinterGraphics(graphics2D, double_2);
/*      */         
/* 2201 */         this.redrawList.clear();
/*      */         
/* 2203 */         AffineTransform affineTransform = graphics2D.getTransform();
/*      */         
/* 2205 */         printable.print(graphics2D, pageFormat2, paramInt);
/*      */         
/* 2207 */         for (byte b = 0; b < this.redrawList.size(); b++) {
/* 2208 */           GraphicsState graphicsState = this.redrawList.get(b);
/* 2209 */           graphics2D.setTransform(affineTransform);
/* 2210 */           ((PathGraphics)graphics2D).redrawRegion(graphicsState.region, graphicsState.sx, graphicsState.sy, graphicsState.theClip, graphicsState.theTransform);
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/* 2222 */         BufferedImage bufferedImage1 = this.cachedBand;
/* 2223 */         if (this.cachedBand == null || i != this.cachedBandWidth || k != this.cachedBandHeight) {
/*      */ 
/*      */           
/* 2226 */           bufferedImage1 = new BufferedImage(i, k, 5);
/*      */           
/* 2228 */           this.cachedBand = bufferedImage1;
/* 2229 */           this.cachedBandWidth = i;
/* 2230 */           this.cachedBandHeight = k;
/*      */         } 
/* 2232 */         Graphics2D graphics2D1 = bufferedImage1.createGraphics();
/*      */         
/* 2234 */         Rectangle2D.Double double_ = new Rectangle2D.Double(0.0D, 0.0D, i, k);
/*      */ 
/*      */         
/* 2237 */         initPrinterGraphics(graphics2D1, double_);
/*      */         
/* 2239 */         ProxyGraphics2D proxyGraphics2D = new ProxyGraphics2D(graphics2D1, this);
/*      */ 
/*      */         
/* 2242 */         Graphics2D graphics2D2 = bufferedImage1.createGraphics();
/* 2243 */         graphics2D2.setColor(Color.white);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2252 */         ByteInterleavedRaster byteInterleavedRaster = (ByteInterleavedRaster)bufferedImage1.getRaster();
/* 2253 */         byte[] arrayOfByte = byteInterleavedRaster.getDataStorage();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2259 */         int i2 = n + j;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2266 */         int i3 = (int)getPhysicalPrintableX(paper);
/* 2267 */         int i4 = (int)getPhysicalPrintableY(paper);
/*      */         int i5;
/* 2269 */         for (i5 = 0; i5 <= j; 
/* 2270 */           i5 += k) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2276 */           graphics2D2.fillRect(0, 0, i, k);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2283 */           graphics2D1.setTransform(affineTransform1);
/* 2284 */           graphics2D1.transform(affineTransform3);
/* 2285 */           affineTransform3.translate(0.0D, -k);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2290 */           graphics2D1.transform(affineTransform2);
/* 2291 */           graphics2D1.transform(new AffineTransform(pageFormat1.getMatrix()));
/*      */           
/* 2293 */           Rectangle rectangle = graphics2D1.getClipBounds();
/* 2294 */           rectangle = affineTransform4.createTransformedShape(rectangle).getBounds();
/*      */           
/* 2296 */           if (rectangle == null || (peekGraphics.hitsDrawingArea(rectangle) && i > 0 && k > 0)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2306 */             int i6 = m - i3;
/* 2307 */             if (i6 < 0) {
/* 2308 */               graphics2D1.translate(i6 / d1, 0.0D);
/* 2309 */               i6 = 0;
/*      */             } 
/* 2311 */             int i7 = n + i5 - i4;
/* 2312 */             if (i7 < 0) {
/* 2313 */               graphics2D1.translate(0.0D, i7 / d2);
/* 2314 */               i7 = 0;
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 2319 */             proxyGraphics2D.setDelegate((Graphics2D)graphics2D1.create());
/* 2320 */             printable.print(proxyGraphics2D, pageFormat2, paramInt);
/* 2321 */             proxyGraphics2D.dispose();
/* 2322 */             printBand(arrayOfByte, i6, i7, i, k);
/*      */           } 
/*      */         } 
/*      */         
/* 2326 */         graphics2D2.dispose();
/* 2327 */         graphics2D1.dispose();
/*      */       } 
/*      */       
/* 2330 */       debug_println("calling endPage " + paramInt);
/* 2331 */       endPage(pageFormat1, printable, paramInt);
/*      */     } 
/*      */     
/* 2334 */     return i1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cancel() {
/* 2345 */     synchronized (this) {
/* 2346 */       if (this.performingPrinting) {
/* 2347 */         this.userCancelled = true;
/*      */       }
/* 2349 */       notify();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCancelled() {
/* 2360 */     boolean bool = false;
/*      */     
/* 2362 */     synchronized (this) {
/* 2363 */       bool = (this.performingPrinting && this.userCancelled) ? true : false;
/* 2364 */       notify();
/*      */     } 
/*      */     
/* 2367 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Pageable getPageable() {
/* 2374 */     return this.mDocument;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Graphics2D createPathGraphics(PeekGraphics paramPeekGraphics, PrinterJob paramPrinterJob, Printable paramPrintable, PageFormat paramPageFormat, int paramInt) {
/* 2395 */     return null;
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
/*      */ 
/*      */ 
/*      */   
/*      */   protected PeekGraphics createPeekGraphics(Graphics2D paramGraphics2D, PrinterJob paramPrinterJob) {
/* 2410 */     return new PeekGraphics(paramGraphics2D, paramPrinterJob);
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
/*      */   
/*      */   protected void initPrinterGraphics(Graphics2D paramGraphics2D, Rectangle2D paramRectangle2D) {
/* 2423 */     paramGraphics2D.setClip(paramRectangle2D);
/* 2424 */     paramGraphics2D.setPaint(Color.black);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkAllowedToPrintToFile() {
/*      */     try {
/* 2434 */       throwPrintToFile();
/* 2435 */       return true;
/* 2436 */     } catch (SecurityException securityException) {
/* 2437 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void throwPrintToFile() {
/* 2447 */     SecurityManager securityManager = System.getSecurityManager();
/* 2448 */     if (securityManager != null) {
/* 2449 */       if (this.printToFilePermission == null) {
/* 2450 */         this.printToFilePermission = new FilePermission("<<ALL FILES>>", "read,write");
/*      */       }
/*      */       
/* 2453 */       securityManager.checkPermission(this.printToFilePermission);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String removeControlChars(String paramString) {
/* 2464 */     char[] arrayOfChar1 = paramString.toCharArray();
/* 2465 */     int i = arrayOfChar1.length;
/* 2466 */     char[] arrayOfChar2 = new char[i];
/* 2467 */     byte b1 = 0;
/*      */     
/* 2469 */     for (byte b2 = 0; b2 < i; b2++) {
/* 2470 */       char c = arrayOfChar1[b2];
/* 2471 */       if (c > '\r' || c < '\t' || c == '\013' || c == '\f') {
/* 2472 */         arrayOfChar2[b1++] = c;
/*      */       }
/*      */     } 
/* 2475 */     if (b1 == i) {
/* 2476 */       return paramString;
/*      */     }
/* 2478 */     return new String(arrayOfChar2, 0, b1);
/*      */   }
/*      */   
/*      */   public RasterPrinterJob() {
/* 2482 */     this.onTop = null;
/*      */     
/* 2484 */     this.parentWindowID = 0L;
/*      */   }
/*      */   
/*      */   private long getParentWindowID() {
/* 2488 */     return this.parentWindowID;
/*      */   }
/*      */   
/*      */   private void clearParentWindowID() {
/* 2492 */     this.parentWindowID = 0L;
/* 2493 */     this.onTop = null;
/*      */   }
/*      */   
/*      */   private void setParentWindowID(PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/* 2497 */     this.parentWindowID = 0L;
/* 2498 */     this.onTop = (DialogOnTop)paramPrintRequestAttributeSet.get(DialogOnTop.class);
/* 2499 */     if (this.onTop != null)
/* 2500 */       this.parentWindowID = this.onTop.getID(); 
/*      */   }
/*      */   
/*      */   protected abstract double getXRes();
/*      */   
/*      */   protected abstract double getYRes();
/*      */   
/*      */   protected abstract double getPhysicalPrintableX(Paper paramPaper);
/*      */   
/*      */   protected abstract double getPhysicalPrintableY(Paper paramPaper);
/*      */   
/*      */   protected abstract double getPhysicalPrintableWidth(Paper paramPaper);
/*      */   
/*      */   protected abstract double getPhysicalPrintableHeight(Paper paramPaper);
/*      */   
/*      */   protected abstract double getPhysicalPageWidth(Paper paramPaper);
/*      */   
/*      */   protected abstract double getPhysicalPageHeight(Paper paramPaper);
/*      */   
/*      */   protected abstract void startPage(PageFormat paramPageFormat, Printable paramPrintable, int paramInt, boolean paramBoolean) throws PrinterException;
/*      */   
/*      */   protected abstract void endPage(PageFormat paramPageFormat, Printable paramPrintable, int paramInt) throws PrinterException;
/*      */   
/*      */   protected abstract void printBand(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws PrinterException;
/*      */   
/*      */   protected abstract void startDoc() throws PrinterException;
/*      */   
/*      */   protected abstract void endDoc() throws PrinterException;
/*      */   
/*      */   protected abstract void abortDoc();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/RasterPrinterJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */