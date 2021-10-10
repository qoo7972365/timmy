/*      */ package sun.print;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.Shape;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.PathIterator;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.print.PageFormat;
/*      */ import java.awt.print.Pageable;
/*      */ import java.awt.print.Paper;
/*      */ import java.awt.print.Printable;
/*      */ import java.awt.print.PrinterException;
/*      */ import java.awt.print.PrinterIOException;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.lang.reflect.Method;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.CharBuffer;
/*      */ import java.nio.charset.CharsetEncoder;
/*      */ import java.nio.charset.CoderMalfunctionError;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.attribute.FileAttribute;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Locale;
/*      */ import java.util.Properties;
/*      */ import javax.print.PrintService;
/*      */ import javax.print.StreamPrintService;
/*      */ import javax.print.attribute.HashPrintRequestAttributeSet;
/*      */ import javax.print.attribute.PrintServiceAttributeSet;
/*      */ import javax.print.attribute.standard.Copies;
/*      */ import javax.print.attribute.standard.Destination;
/*      */ import javax.print.attribute.standard.DialogTypeSelection;
/*      */ import javax.print.attribute.standard.JobName;
/*      */ import javax.print.attribute.standard.PrinterName;
/*      */ import javax.print.attribute.standard.Sides;
/*      */ import sun.awt.CharsetString;
/*      */ import sun.awt.FontConfiguration;
/*      */ import sun.awt.PlatformFont;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.font.FontUtilities;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PSPrinterJob
/*      */   extends RasterPrinterJob
/*      */ {
/*      */   protected static final int FILL_EVEN_ODD = 1;
/*      */   protected static final int FILL_WINDING = 2;
/*      */   private static final int MAX_PSSTR = 65535;
/*      */   private static final int RED_MASK = 16711680;
/*      */   private static final int GREEN_MASK = 65280;
/*      */   private static final int BLUE_MASK = 255;
/*      */   private static final int RED_SHIFT = 16;
/*      */   private static final int GREEN_SHIFT = 8;
/*      */   private static final int BLUE_SHIFT = 0;
/*      */   private static final int LOWNIBBLE_MASK = 15;
/*      */   private static final int HINIBBLE_MASK = 240;
/*      */   private static final int HINIBBLE_SHIFT = 4;
/*  145 */   private static final byte[] hexDigits = new byte[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int PS_XRES = 300;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int PS_YRES = 300;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String ADOBE_PS_STR = "%!PS-Adobe-3.0";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String EOF_COMMENT = "%%EOF";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String PAGE_COMMENT = "%%Page: ";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String READIMAGEPROC = "/imStr 0 def /imageSrc {currentfile /ASCII85Decode filter /RunLengthDecode filter  imStr readstring pop } def";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String COPIES = "/#copies exch def";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String PAGE_SAVE = "/pgSave save def";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String PAGE_RESTORE = "pgSave restore";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String SHOWPAGE = "showpage";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String IMAGE_SAVE = "/imSave save def";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String IMAGE_STR = " string /imStr exch def";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String IMAGE_RESTORE = "imSave restore";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String COORD_PREP = " 0 exch translate 1 -1 scale[72 300 div 0 0 72 300 div 0 0]concat";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String SetFontName = "F";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String DrawStringName = "S";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String EVEN_ODD_FILL_STR = "EF";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String WINDING_FILL_STR = "WF";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String EVEN_ODD_CLIP_STR = "EC";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String WINDING_CLIP_STR = "WC";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String MOVETO_STR = " M";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String LINETO_STR = " L";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String CURVETO_STR = " C";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String GRESTORE_STR = "R";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String GSAVE_STR = "G";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String NEWPATH_STR = "N";
/*      */ 
/*      */   
/*      */   private static final String CLOSEPATH_STR = "P";
/*      */ 
/*      */   
/*      */   private static final String SETRGBCOLOR_STR = " SC";
/*      */ 
/*      */   
/*      */   private static final String SETGRAY_STR = " SG";
/*      */ 
/*      */   
/*      */   private int mDestType;
/*      */ 
/*      */   
/*  264 */   private String mDestination = "lp";
/*      */ 
/*      */   
/*      */   private boolean mNoJobSheet = false;
/*      */   
/*      */   private String mOptions;
/*      */   
/*      */   private Font mLastFont;
/*      */   
/*      */   private Color mLastColor;
/*      */   
/*      */   private Shape mLastClip;
/*      */   
/*      */   private AffineTransform mLastTransform;
/*      */   
/*  279 */   private EPSPrinter epsPrinter = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   FontMetrics mCurMetrics;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   PrintStream mPSStream;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   File spoolFile;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  301 */   private String mFillOpStr = "WF";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  308 */   private String mClipOpStr = "WC";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  313 */   ArrayList mGStateStack = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float mPenX;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float mPenY;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float mStartPathX;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float mStartPathY;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  340 */   private static Properties mFontProps = null;
/*      */ 
/*      */   
/*      */   private static boolean isMac;
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  348 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run() {
/*  351 */             PSPrinterJob.mFontProps = PSPrinterJob.initProps();
/*  352 */             String str = System.getProperty("os.name");
/*  353 */             PSPrinterJob.isMac = str.startsWith("Mac");
/*  354 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Properties initProps() {
/*  367 */     String str = System.getProperty("java.home");
/*      */     
/*  369 */     if (str != null) {
/*  370 */       String str1 = SunToolkit.getStartupLocale().getLanguage();
/*      */       
/*      */       try {
/*  373 */         File file = new File(str + File.separator + "lib" + File.separator + "psfontj2d.properties." + str1);
/*      */ 
/*      */ 
/*      */         
/*  377 */         if (!file.canRead()) {
/*      */           
/*  379 */           file = new File(str + File.separator + "lib" + File.separator + "psfont.properties." + str1);
/*      */ 
/*      */           
/*  382 */           if (!file.canRead()) {
/*      */             
/*  384 */             file = new File(str + File.separator + "lib" + File.separator + "psfontj2d.properties");
/*      */ 
/*      */             
/*  387 */             if (!file.canRead()) {
/*      */               
/*  389 */               file = new File(str + File.separator + "lib" + File.separator + "psfont.properties");
/*      */ 
/*      */               
/*  392 */               if (!file.canRead()) {
/*  393 */                 return (Properties)null;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  401 */         BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file.getPath()));
/*  402 */         Properties properties = new Properties();
/*  403 */         properties.load(bufferedInputStream);
/*  404 */         bufferedInputStream.close();
/*  405 */         return properties;
/*  406 */       } catch (Exception exception) {
/*  407 */         return (Properties)null;
/*      */       } 
/*      */     } 
/*  410 */     return (Properties)null;
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
/*      */   public boolean printDialog() throws HeadlessException {
/*  432 */     if (GraphicsEnvironment.isHeadless()) {
/*  433 */       throw new HeadlessException();
/*      */     }
/*      */     
/*  436 */     if (this.attributes == null) {
/*  437 */       this.attributes = new HashPrintRequestAttributeSet();
/*      */     }
/*  439 */     this.attributes.add(new Copies(getCopies()));
/*  440 */     this.attributes.add(new JobName(getJobName(), null));
/*      */     
/*  442 */     boolean bool = false;
/*      */     
/*  444 */     DialogTypeSelection dialogTypeSelection = (DialogTypeSelection)this.attributes.get(DialogTypeSelection.class);
/*  445 */     if (dialogTypeSelection == DialogTypeSelection.NATIVE) {
/*      */ 
/*      */       
/*  448 */       this.attributes.remove(DialogTypeSelection.class);
/*  449 */       bool = printDialog(this.attributes);
/*      */       
/*  451 */       this.attributes.add(DialogTypeSelection.NATIVE);
/*      */     } else {
/*  453 */       bool = printDialog(this.attributes);
/*      */     } 
/*      */     
/*  456 */     if (bool) {
/*  457 */       JobName jobName = (JobName)this.attributes.get(JobName.class);
/*  458 */       if (jobName != null) {
/*  459 */         setJobName(jobName.getValue());
/*      */       }
/*  461 */       Copies copies = (Copies)this.attributes.get(Copies.class);
/*  462 */       if (copies != null) {
/*  463 */         setCopies(copies.getValue());
/*      */       }
/*      */       
/*  466 */       Destination destination = (Destination)this.attributes.get(Destination.class);
/*      */       
/*  468 */       if (destination != null) {
/*      */         try {
/*  470 */           this.mDestType = 1;
/*  471 */           this.mDestination = (new File(destination.getURI())).getPath();
/*  472 */         } catch (Exception exception) {
/*  473 */           this.mDestination = "out.ps";
/*      */         } 
/*      */       } else {
/*  476 */         this.mDestType = 0;
/*  477 */         PrintService printService = getPrintService();
/*  478 */         if (printService != null) {
/*  479 */           this.mDestination = printService.getName();
/*  480 */           if (isMac) {
/*  481 */             PrintServiceAttributeSet printServiceAttributeSet = printService.getAttributes();
/*  482 */             if (printServiceAttributeSet != null) {
/*  483 */               this.mDestination = printServiceAttributeSet.get(PrinterName.class).toString();
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  490 */     return bool;
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
/*      */   protected void startDoc() throws PrinterException {
/*  508 */     if (this.epsPrinter == null) {
/*  509 */       OutputStream outputStream; if (getPrintService() instanceof PSStreamPrintService) {
/*  510 */         StreamPrintService streamPrintService = (StreamPrintService)getPrintService();
/*  511 */         this.mDestType = 2;
/*  512 */         if (streamPrintService.isDisposed()) {
/*  513 */           throw new PrinterException("service is disposed");
/*      */         }
/*  515 */         outputStream = streamPrintService.getOutputStream();
/*  516 */         if (outputStream == null) {
/*  517 */           throw new PrinterException("Null output stream");
/*      */         }
/*      */       } else {
/*      */         
/*  521 */         this.mNoJobSheet = this.noJobSheet;
/*  522 */         if (this.destinationAttr != null) {
/*  523 */           this.mDestType = 1;
/*  524 */           this.mDestination = this.destinationAttr;
/*      */         } 
/*  526 */         if (this.mDestType == 1) {
/*      */           try {
/*  528 */             this.spoolFile = new File(this.mDestination);
/*  529 */             outputStream = new FileOutputStream(this.spoolFile);
/*  530 */           } catch (IOException iOException) {
/*  531 */             throw new PrinterIOException(iOException);
/*      */           } 
/*      */         } else {
/*  534 */           PrinterOpener printerOpener = new PrinterOpener();
/*  535 */           AccessController.doPrivileged(printerOpener);
/*  536 */           if (printerOpener.pex != null) {
/*  537 */             throw printerOpener.pex;
/*      */           }
/*  539 */           outputStream = printerOpener.result;
/*      */         } 
/*      */       } 
/*      */       
/*  543 */       this.mPSStream = new PrintStream(new BufferedOutputStream(outputStream));
/*  544 */       this.mPSStream.println("%!PS-Adobe-3.0");
/*      */     } 
/*      */     
/*  547 */     this.mPSStream.println("%%BeginProlog");
/*  548 */     this.mPSStream.println("/imStr 0 def /imageSrc {currentfile /ASCII85Decode filter /RunLengthDecode filter  imStr readstring pop } def");
/*  549 */     this.mPSStream.println("/BD {bind def} bind def");
/*  550 */     this.mPSStream.println("/D {def} BD");
/*  551 */     this.mPSStream.println("/C {curveto} BD");
/*  552 */     this.mPSStream.println("/L {lineto} BD");
/*  553 */     this.mPSStream.println("/M {moveto} BD");
/*  554 */     this.mPSStream.println("/R {grestore} BD");
/*  555 */     this.mPSStream.println("/G {gsave} BD");
/*  556 */     this.mPSStream.println("/N {newpath} BD");
/*  557 */     this.mPSStream.println("/P {closepath} BD");
/*  558 */     this.mPSStream.println("/EC {eoclip} BD");
/*  559 */     this.mPSStream.println("/WC {clip} BD");
/*  560 */     this.mPSStream.println("/EF {eofill} BD");
/*  561 */     this.mPSStream.println("/WF {fill} BD");
/*  562 */     this.mPSStream.println("/SG {setgray} BD");
/*  563 */     this.mPSStream.println("/SC {setrgbcolor} BD");
/*  564 */     this.mPSStream.println("/ISOF {");
/*  565 */     this.mPSStream.println("     dup findfont dup length 1 add dict begin {");
/*  566 */     this.mPSStream.println("             1 index /FID eq {pop pop} {D} ifelse");
/*  567 */     this.mPSStream.println("     } forall /Encoding ISOLatin1Encoding D");
/*  568 */     this.mPSStream.println("     currentdict end definefont");
/*  569 */     this.mPSStream.println("} BD");
/*  570 */     this.mPSStream.println("/NZ {dup 1 lt {pop 1} if} BD");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  579 */     this.mPSStream.println("/S {");
/*  580 */     this.mPSStream.println("     moveto 1 index stringwidth pop NZ sub");
/*  581 */     this.mPSStream.println("     1 index length 1 sub NZ div 0");
/*  582 */     this.mPSStream.println("     3 2 roll ashow newpath} BD");
/*  583 */     this.mPSStream.println("/FL [");
/*  584 */     if (mFontProps == null) {
/*  585 */       this.mPSStream.println(" /Helvetica ISOF");
/*  586 */       this.mPSStream.println(" /Helvetica-Bold ISOF");
/*  587 */       this.mPSStream.println(" /Helvetica-Oblique ISOF");
/*  588 */       this.mPSStream.println(" /Helvetica-BoldOblique ISOF");
/*  589 */       this.mPSStream.println(" /Times-Roman ISOF");
/*  590 */       this.mPSStream.println(" /Times-Bold ISOF");
/*  591 */       this.mPSStream.println(" /Times-Italic ISOF");
/*  592 */       this.mPSStream.println(" /Times-BoldItalic ISOF");
/*  593 */       this.mPSStream.println(" /Courier ISOF");
/*  594 */       this.mPSStream.println(" /Courier-Bold ISOF");
/*  595 */       this.mPSStream.println(" /Courier-Oblique ISOF");
/*  596 */       this.mPSStream.println(" /Courier-BoldOblique ISOF");
/*      */     } else {
/*  598 */       int i = Integer.parseInt(mFontProps.getProperty("font.num", "9"));
/*  599 */       for (byte b = 0; b < i; b++) {
/*  600 */         this.mPSStream.println("    /" + mFontProps
/*  601 */             .getProperty("font." + String.valueOf(b), "Courier ISOF"));
/*      */       }
/*      */     } 
/*  604 */     this.mPSStream.println("] D");
/*      */     
/*  606 */     this.mPSStream.println("/F {");
/*  607 */     this.mPSStream.println("     FL exch get exch scalefont");
/*  608 */     this.mPSStream.println("     [1 0 0 -1 0 0] makefont setfont} BD");
/*      */     
/*  610 */     this.mPSStream.println("%%EndProlog");
/*      */     
/*  612 */     this.mPSStream.println("%%BeginSetup");
/*  613 */     if (this.epsPrinter == null) {
/*      */       
/*  615 */       PageFormat pageFormat = getPageable().getPageFormat(0);
/*  616 */       double d1 = pageFormat.getPaper().getHeight();
/*  617 */       double d2 = pageFormat.getPaper().getWidth();
/*      */ 
/*      */ 
/*      */       
/*  621 */       this.mPSStream.print("<< /PageSize [" + d2 + " " + d1 + "]");
/*      */ 
/*      */       
/*  624 */       final PrintService pservice = getPrintService();
/*  625 */       Boolean bool = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*      */           {
/*      */             public Object run() {
/*      */               try {
/*  629 */                 Class<?> clazz = Class.forName("sun.print.IPPPrintService");
/*  630 */                 if (clazz.isInstance(pservice)) {
/*  631 */                   Method method = clazz.getMethod("isPostscript", (Class[])null);
/*      */                   
/*  633 */                   return method.invoke(pservice, (Object[])null);
/*      */                 } 
/*  635 */               } catch (Throwable throwable) {}
/*      */               
/*  637 */               return Boolean.TRUE;
/*      */             }
/*      */           });
/*      */       
/*  641 */       if (bool.booleanValue()) {
/*  642 */         this.mPSStream.print(" /DeferredMediaSelection true");
/*      */       }
/*      */       
/*  645 */       this.mPSStream.print(" /ImagingBBox null /ManualFeed false");
/*  646 */       this.mPSStream.print(isCollated() ? " /Collate true" : "");
/*  647 */       this.mPSStream.print(" /NumCopies " + getCopiesInt());
/*      */       
/*  649 */       if (this.sidesAttr != Sides.ONE_SIDED) {
/*  650 */         if (this.sidesAttr == Sides.TWO_SIDED_LONG_EDGE) {
/*  651 */           this.mPSStream.print(" /Duplex true ");
/*  652 */         } else if (this.sidesAttr == Sides.TWO_SIDED_SHORT_EDGE) {
/*  653 */           this.mPSStream.print(" /Duplex true /Tumble true ");
/*      */         } 
/*      */       }
/*  656 */       this.mPSStream.println(" >> setpagedevice ");
/*      */     } 
/*  658 */     this.mPSStream.println("%%EndSetup");
/*      */   }
/*      */ 
/*      */   
/*      */   private class PrinterOpener
/*      */     implements PrivilegedAction
/*      */   {
/*      */     PrinterException pex;
/*      */     
/*      */     OutputStream result;
/*      */ 
/*      */     
/*      */     private PrinterOpener() {}
/*      */ 
/*      */     
/*      */     public Object run() {
/*      */       try {
/*  675 */         PSPrinterJob.this.spoolFile = Files.createTempFile("javaprint", ".ps", (FileAttribute<?>[])new FileAttribute[0]).toFile();
/*  676 */         PSPrinterJob.this.spoolFile.deleteOnExit();
/*      */         
/*  678 */         this.result = new FileOutputStream(PSPrinterJob.this.spoolFile);
/*  679 */         return this.result;
/*  680 */       } catch (IOException iOException) {
/*      */         
/*  682 */         this.pex = new PrinterIOException(iOException);
/*      */         
/*  684 */         return null;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class PrinterSpooler implements PrivilegedAction {
/*      */     PrinterException pex;
/*      */     
/*      */     private PrinterSpooler() {}
/*      */     
/*      */     private void handleProcessFailure(Process param1Process, String[] param1ArrayOfString, int param1Int) throws IOException {
/*  695 */       try(StringWriter null = new StringWriter(); 
/*  696 */           PrintWriter null = new PrintWriter(stringWriter)) {
/*  697 */         printWriter.append("error=").append(Integer.toString(param1Int));
/*  698 */         printWriter.append(" running:");
/*  699 */         for (String str : param1ArrayOfString) {
/*  700 */           printWriter.append(" '").append(str).append("'");
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
/*  717 */       if (PSPrinterJob.this.spoolFile == null || !PSPrinterJob.this.spoolFile.exists()) {
/*  718 */         this.pex = new PrinterException("No spool file");
/*  719 */         return null;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  725 */         String str = PSPrinterJob.this.spoolFile.getAbsolutePath();
/*  726 */         String[] arrayOfString = PSPrinterJob.this.printExecCmd(PSPrinterJob.this.mDestination, PSPrinterJob.this.mOptions, PSPrinterJob.this
/*  727 */             .mNoJobSheet, PSPrinterJob.this.getJobNameInt(), 1, str);
/*      */ 
/*      */         
/*  730 */         Process process = Runtime.getRuntime().exec(arrayOfString);
/*  731 */         process.waitFor();
/*  732 */         int i = process.exitValue();
/*  733 */         if (0 != i) {
/*  734 */           handleProcessFailure(process, arrayOfString, i);
/*      */         }
/*  736 */       } catch (IOException iOException) {
/*  737 */         this.pex = new PrinterIOException(iOException);
/*  738 */       } catch (InterruptedException interruptedException) {
/*  739 */         this.pex = new PrinterException(interruptedException.toString());
/*      */       } finally {
/*  741 */         PSPrinterJob.this.spoolFile.delete();
/*      */       } 
/*  743 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void abortDoc() {
/*  752 */     if (this.mPSStream != null && this.mDestType != 2) {
/*  753 */       this.mPSStream.close();
/*      */     }
/*  755 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run()
/*      */           {
/*  759 */             if (PSPrinterJob.this.spoolFile != null && PSPrinterJob.this.spoolFile.exists()) {
/*  760 */               PSPrinterJob.this.spoolFile.delete();
/*      */             }
/*  762 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void endDoc() throws PrinterException {
/*  773 */     if (this.mPSStream != null) {
/*  774 */       this.mPSStream.println("%%EOF");
/*  775 */       this.mPSStream.flush();
/*  776 */       if (this.mDestType != 2) {
/*  777 */         this.mPSStream.close();
/*      */       }
/*      */     } 
/*  780 */     if (this.mDestType == 0) {
/*  781 */       PrintService printService = getPrintService();
/*  782 */       if (printService != null) {
/*  783 */         this.mDestination = printService.getName();
/*  784 */         if (isMac) {
/*  785 */           PrintServiceAttributeSet printServiceAttributeSet = printService.getAttributes();
/*  786 */           if (printServiceAttributeSet != null) {
/*  787 */             this.mDestination = printServiceAttributeSet.get(PrinterName.class).toString();
/*      */           }
/*      */         } 
/*      */       } 
/*  791 */       PrinterSpooler printerSpooler = new PrinterSpooler();
/*  792 */       AccessController.doPrivileged(printerSpooler);
/*  793 */       if (printerSpooler.pex != null) {
/*  794 */         throw printerSpooler.pex;
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
/*      */   protected void startPage(PageFormat paramPageFormat, Printable paramPrintable, int paramInt, boolean paramBoolean) throws PrinterException {
/*  807 */     double d1 = paramPageFormat.getPaper().getHeight();
/*  808 */     double d2 = paramPageFormat.getPaper().getWidth();
/*  809 */     int i = paramInt + 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  815 */     this.mGStateStack = new ArrayList();
/*  816 */     this.mGStateStack.add(new GState());
/*      */     
/*  818 */     this.mPSStream.println("%%Page: " + i + " " + i);
/*      */ 
/*      */ 
/*      */     
/*  822 */     if (paramInt > 0 && paramBoolean) {
/*      */       
/*  824 */       this.mPSStream.print("<< /PageSize [" + d2 + " " + d1 + "]");
/*      */ 
/*      */       
/*  827 */       final PrintService pservice = getPrintService();
/*      */       
/*  829 */       Boolean bool = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*      */           {
/*      */             
/*      */             public Object run()
/*      */             {
/*      */               try {
/*  835 */                 Class<?> clazz = Class.forName("sun.print.IPPPrintService");
/*  836 */                 if (clazz.isInstance(pservice))
/*      */                 {
/*  838 */                   Method method = clazz.getMethod("isPostscript", (Class[])null);
/*      */                   
/*  840 */                   return method
/*  841 */                     .invoke(pservice, (Object[])null);
/*      */                 }
/*      */               
/*  844 */               } catch (Throwable throwable) {}
/*      */               
/*  846 */               return Boolean.TRUE;
/*      */             }
/*      */           });
/*      */ 
/*      */       
/*  851 */       if (bool.booleanValue()) {
/*  852 */         this.mPSStream.print(" /DeferredMediaSelection true");
/*      */       }
/*  854 */       this.mPSStream.println(" >> setpagedevice");
/*      */     } 
/*  856 */     this.mPSStream.println("/pgSave save def");
/*  857 */     this.mPSStream.println(d1 + " 0 exch translate 1 -1 scale[72 300 div 0 0 72 300 div 0 0]concat");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void endPage(PageFormat paramPageFormat, Printable paramPrintable, int paramInt) throws PrinterException {
/*  868 */     this.mPSStream.println("pgSave restore");
/*  869 */     this.mPSStream.println("showpage");
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
/*      */   protected void drawImageBGR(byte[] paramArrayOfbyte, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, int paramInt1, int paramInt2) {
/*  893 */     setTransform(new AffineTransform());
/*  894 */     prepDrawing();
/*      */     
/*  896 */     int i = (int)paramFloat7;
/*  897 */     int j = (int)paramFloat8;
/*      */     
/*  899 */     this.mPSStream.println("/imSave save def");
/*      */ 
/*      */ 
/*      */     
/*  903 */     int k = 3 * i;
/*  904 */     while (k > 65535) {
/*  905 */       k /= 2;
/*      */     }
/*      */     
/*  908 */     this.mPSStream.println(k + " string /imStr exch def");
/*      */ 
/*      */ 
/*      */     
/*  912 */     this.mPSStream.println("[" + paramFloat3 + " 0 0 " + paramFloat4 + " " + paramFloat1 + " " + paramFloat2 + "]concat");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  919 */     this.mPSStream.println(i + " " + j + " " + '\b' + "[" + i + " 0 0 " + j + " 0 " + Character.MIN_VALUE + "]/imageSrc load false 3 colorimage");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  927 */     int m = 0;
/*  928 */     byte[] arrayOfByte = new byte[i * 3];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  934 */       m = (int)paramFloat6 * paramInt1;
/*      */       
/*  936 */       for (byte b = 0; b < j; b++)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  941 */         m += (int)paramFloat5;
/*      */         
/*  943 */         m = swapBGRtoRGB(paramArrayOfbyte, m, arrayOfByte);
/*  944 */         byte[] arrayOfByte1 = rlEncode(arrayOfByte);
/*  945 */         byte[] arrayOfByte2 = ascii85Encode(arrayOfByte1);
/*  946 */         this.mPSStream.write(arrayOfByte2);
/*  947 */         this.mPSStream.println("");
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  955 */     catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */     
/*  959 */     this.mPSStream.println("imSave restore");
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
/*      */   protected void printBand(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws PrinterException {
/*  975 */     this.mPSStream.println("/imSave save def");
/*      */ 
/*      */ 
/*      */     
/*  979 */     int i = 3 * paramInt3;
/*  980 */     while (i > 65535) {
/*  981 */       i /= 2;
/*      */     }
/*      */     
/*  984 */     this.mPSStream.println(i + " string /imStr exch def");
/*      */ 
/*      */ 
/*      */     
/*  988 */     this.mPSStream.println("[" + paramInt3 + " 0 0 " + paramInt4 + " " + paramInt1 + " " + paramInt2 + "]concat");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  995 */     this.mPSStream.println(paramInt3 + " " + paramInt4 + " " + '\b' + "[" + paramInt3 + " 0 0 " + -paramInt4 + " 0 " + paramInt4 + "]/imageSrc load false 3 colorimage");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1003 */     int j = 0;
/* 1004 */     byte[] arrayOfByte = new byte[paramInt3 * 3];
/*      */     
/*      */     try {
/* 1007 */       for (byte b = 0; b < paramInt4; b++) {
/* 1008 */         j = swapBGRtoRGB(paramArrayOfbyte, j, arrayOfByte);
/* 1009 */         byte[] arrayOfByte1 = rlEncode(arrayOfByte);
/* 1010 */         byte[] arrayOfByte2 = ascii85Encode(arrayOfByte1);
/* 1011 */         this.mPSStream.write(arrayOfByte2);
/* 1012 */         this.mPSStream.println("");
/*      */       }
/*      */     
/* 1015 */     } catch (IOException iOException) {
/* 1016 */       throw new PrinterIOException(iOException);
/*      */     } 
/*      */     
/* 1019 */     this.mPSStream.println("imSave restore");
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
/*      */   protected Graphics2D createPathGraphics(PeekGraphics paramPeekGraphics, PrinterJob paramPrinterJob, Printable paramPrintable, PageFormat paramPageFormat, int paramInt) {
/*      */     PSPathGraphics pSPathGraphics;
/* 1042 */     PeekMetrics peekMetrics = paramPeekGraphics.getMetrics();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1048 */     if (!forcePDL && (forceRaster == true || peekMetrics
/* 1049 */       .hasNonSolidColors() || peekMetrics
/* 1050 */       .hasCompositing())) {
/*      */       
/* 1052 */       pSPathGraphics = null;
/*      */     } else {
/*      */       
/* 1055 */       BufferedImage bufferedImage = new BufferedImage(8, 8, 1);
/*      */       
/* 1057 */       Graphics2D graphics2D = bufferedImage.createGraphics();
/* 1058 */       boolean bool = !paramPeekGraphics.getAWTDrawingOnly() ? true : false;
/*      */       
/* 1060 */       pSPathGraphics = new PSPathGraphics(graphics2D, paramPrinterJob, paramPrintable, paramPageFormat, paramInt, bool);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1065 */     return pSPathGraphics;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void selectClipPath() {
/* 1074 */     this.mPSStream.println(this.mClipOpStr);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setClip(Shape paramShape) {
/* 1079 */     this.mLastClip = paramShape;
/*      */   }
/*      */   
/*      */   protected void setTransform(AffineTransform paramAffineTransform) {
/* 1083 */     this.mLastTransform = paramAffineTransform;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean setFont(Font paramFont) {
/* 1091 */     this.mLastFont = paramFont;
/* 1092 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] getPSFontIndexArray(Font paramFont, CharsetString[] paramArrayOfCharsetString) {
/* 1103 */     int[] arrayOfInt = null;
/*      */     
/* 1105 */     if (mFontProps != null) {
/* 1106 */       arrayOfInt = new int[paramArrayOfCharsetString.length];
/*      */     }
/*      */     
/* 1109 */     for (byte b = 0; b < paramArrayOfCharsetString.length && arrayOfInt != null; b++) {
/*      */ 
/*      */ 
/*      */       
/* 1113 */       CharsetString charsetString = paramArrayOfCharsetString[b];
/*      */       
/* 1115 */       CharsetEncoder charsetEncoder = charsetString.fontDescriptor.encoder;
/* 1116 */       String str1 = charsetString.fontDescriptor.getFontCharsetName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1123 */       if ("Symbol".equals(str1)) {
/* 1124 */         str1 = "symbol";
/* 1125 */       } else if ("WingDings".equals(str1) || "X11Dingbats"
/* 1126 */         .equals(str1)) {
/* 1127 */         str1 = "dingbats";
/*      */       } else {
/* 1129 */         str1 = makeCharsetName(str1, charsetString.charsetChars);
/*      */       } 
/*      */ 
/*      */       
/* 1133 */       int i = paramFont.getStyle() | FontUtilities.getFont2D(paramFont).getStyle();
/*      */       
/* 1135 */       String str2 = FontConfiguration.getStyleString(i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1141 */       String str3 = paramFont.getFamily().toLowerCase(Locale.ENGLISH);
/* 1142 */       str3 = str3.replace(' ', '_');
/* 1143 */       String str4 = mFontProps.getProperty(str3, "");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1149 */       String str5 = mFontProps.getProperty(str4 + "." + str1 + "." + str2, null);
/*      */ 
/*      */       
/* 1152 */       if (str5 != null) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/* 1157 */           arrayOfInt[b] = 
/* 1158 */             Integer.parseInt(mFontProps.getProperty(str5));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 1165 */         catch (NumberFormatException numberFormatException) {
/* 1166 */           arrayOfInt = null;
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1173 */         arrayOfInt = null;
/*      */       } 
/*      */     } 
/*      */     
/* 1177 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String escapeParens(String paramString) {
/* 1182 */     if (paramString.indexOf('(') == -1 && paramString.indexOf(')') == -1) {
/* 1183 */       return paramString;
/*      */     }
/* 1185 */     byte b1 = 0;
/* 1186 */     int i = 0;
/* 1187 */     while ((i = paramString.indexOf('(', i)) != -1) {
/* 1188 */       b1++;
/* 1189 */       i++;
/*      */     } 
/* 1191 */     i = 0;
/* 1192 */     while ((i = paramString.indexOf(')', i)) != -1) {
/* 1193 */       b1++;
/* 1194 */       i++;
/*      */     } 
/* 1196 */     char[] arrayOfChar1 = paramString.toCharArray();
/* 1197 */     char[] arrayOfChar2 = new char[arrayOfChar1.length + b1];
/* 1198 */     i = 0;
/* 1199 */     for (byte b2 = 0; b2 < arrayOfChar1.length; b2++) {
/* 1200 */       if (arrayOfChar1[b2] == '(' || arrayOfChar1[b2] == ')') {
/* 1201 */         arrayOfChar2[i++] = '\\';
/*      */       }
/* 1203 */       arrayOfChar2[i++] = arrayOfChar1[b2];
/*      */     } 
/* 1205 */     return new String(arrayOfChar2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int platformFontCount(Font paramFont, String paramString) {
/* 1215 */     if (mFontProps == null) {
/* 1216 */       return 0;
/*      */     }
/*      */     
/* 1219 */     CharsetString[] arrayOfCharsetString = ((PlatformFont)paramFont.getPeer()).makeMultiCharsetString(paramString, false);
/* 1220 */     if (arrayOfCharsetString == null)
/*      */     {
/* 1222 */       return 0;
/*      */     }
/* 1224 */     int[] arrayOfInt = getPSFontIndexArray(paramFont, arrayOfCharsetString);
/* 1225 */     return (arrayOfInt == null) ? 0 : arrayOfInt.length;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean textOut(Graphics paramGraphics, String paramString, float paramFloat1, float paramFloat2, Font paramFont, FontRenderContext paramFontRenderContext, float paramFloat3) {
/* 1231 */     boolean bool = true;
/*      */     
/* 1233 */     if (mFontProps == null) {
/* 1234 */       return false;
/*      */     }
/* 1236 */     prepDrawing();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1248 */     paramString = removeControlChars(paramString);
/* 1249 */     if (paramString.length() == 0) {
/* 1250 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1254 */     CharsetString[] arrayOfCharsetString = ((PlatformFont)paramFont.getPeer()).makeMultiCharsetString(paramString, false);
/* 1255 */     if (arrayOfCharsetString == null)
/*      */     {
/* 1257 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1265 */     int[] arrayOfInt = getPSFontIndexArray(paramFont, arrayOfCharsetString);
/* 1266 */     if (arrayOfInt != null) {
/*      */       
/* 1268 */       for (byte b = 0; b < arrayOfCharsetString.length; b++) {
/* 1269 */         float f; CharsetString charsetString = arrayOfCharsetString[b];
/* 1270 */         CharsetEncoder charsetEncoder = charsetString.fontDescriptor.encoder;
/*      */         
/* 1272 */         StringBuffer stringBuffer = new StringBuffer();
/* 1273 */         byte[] arrayOfByte = new byte[charsetString.length * 2];
/* 1274 */         int i = 0;
/*      */         try {
/* 1276 */           ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
/* 1277 */           charsetEncoder.encode(CharBuffer.wrap(charsetString.charsetChars, charsetString.offset, charsetString.length), byteBuffer, true);
/*      */ 
/*      */ 
/*      */           
/* 1281 */           byteBuffer.flip();
/* 1282 */           i = byteBuffer.limit();
/* 1283 */         } catch (IllegalStateException illegalStateException) {
/*      */         
/* 1285 */         } catch (CoderMalfunctionError coderMalfunctionError) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1294 */         if (arrayOfCharsetString.length == 1 && paramFloat3 != 0.0F) {
/* 1295 */           f = paramFloat3;
/*      */         } else {
/*      */           
/* 1298 */           Rectangle2D rectangle2D = paramFont.getStringBounds(charsetString.charsetChars, charsetString.offset, charsetString.offset + charsetString.length, paramFontRenderContext);
/*      */ 
/*      */ 
/*      */           
/* 1302 */           f = (float)rectangle2D.getWidth();
/*      */         } 
/*      */ 
/*      */         
/* 1306 */         if (f == 0.0F) {
/* 1307 */           return bool;
/*      */         }
/* 1309 */         stringBuffer.append('<');
/* 1310 */         for (byte b1 = 0; b1 < i; b1++) {
/* 1311 */           byte b2 = arrayOfByte[b1];
/*      */           
/* 1313 */           String str = Integer.toHexString(b2);
/* 1314 */           int j = str.length();
/* 1315 */           if (j > 2) {
/* 1316 */             str = str.substring(j - 2, j);
/* 1317 */           } else if (j == 1) {
/* 1318 */             str = "0" + str;
/* 1319 */           } else if (j == 0) {
/* 1320 */             str = "00";
/*      */           } 
/* 1322 */           stringBuffer.append(str);
/*      */         } 
/* 1324 */         stringBuffer.append('>');
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1329 */         getGState().emitPSFont(arrayOfInt[b], paramFont.getSize2D());
/*      */ 
/*      */         
/* 1332 */         this.mPSStream.println(stringBuffer.toString() + " " + f + " " + paramFloat1 + " " + paramFloat2 + " " + "S");
/*      */ 
/*      */         
/* 1335 */         paramFloat1 += f;
/*      */       } 
/*      */     } else {
/* 1338 */       bool = false;
/*      */     } 
/*      */ 
/*      */     
/* 1342 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setFillMode(int paramInt) {
/* 1352 */     switch (paramInt) {
/*      */       
/*      */       case 1:
/* 1355 */         this.mFillOpStr = "EF";
/* 1356 */         this.mClipOpStr = "EC";
/*      */         return;
/*      */       
/*      */       case 2:
/* 1360 */         this.mFillOpStr = "WF";
/* 1361 */         this.mClipOpStr = "WC";
/*      */         return;
/*      */     } 
/*      */     
/* 1365 */     throw new IllegalArgumentException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setColor(Color paramColor) {
/* 1375 */     this.mLastColor = paramColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fillPath() {
/* 1384 */     this.mPSStream.println(this.mFillOpStr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void beginPath() {
/* 1392 */     prepDrawing();
/* 1393 */     this.mPSStream.println("N");
/*      */     
/* 1395 */     this.mPenX = 0.0F;
/* 1396 */     this.mPenY = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void closeSubpath() {
/* 1406 */     this.mPSStream.println("P");
/*      */     
/* 1408 */     this.mPenX = this.mStartPathX;
/* 1409 */     this.mPenY = this.mStartPathY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void moveTo(float paramFloat1, float paramFloat2) {
/* 1419 */     this.mPSStream.println(trunc(paramFloat1) + " " + trunc(paramFloat2) + " M");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1426 */     this.mStartPathX = paramFloat1;
/* 1427 */     this.mStartPathY = paramFloat2;
/*      */     
/* 1429 */     this.mPenX = paramFloat1;
/* 1430 */     this.mPenY = paramFloat2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void lineTo(float paramFloat1, float paramFloat2) {
/* 1438 */     this.mPSStream.println(trunc(paramFloat1) + " " + trunc(paramFloat2) + " L");
/*      */     
/* 1440 */     this.mPenX = paramFloat1;
/* 1441 */     this.mPenY = paramFloat2;
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
/*      */   protected void bezierTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
/* 1458 */     this.mPSStream.println(trunc(paramFloat1) + " " + trunc(paramFloat2) + " " + 
/* 1459 */         trunc(paramFloat3) + " " + trunc(paramFloat4) + " " + 
/* 1460 */         trunc(paramFloat5) + " " + trunc(paramFloat6) + " C");
/*      */ 
/*      */ 
/*      */     
/* 1464 */     this.mPenX = paramFloat5;
/* 1465 */     this.mPenY = paramFloat6;
/*      */   }
/*      */   
/*      */   String trunc(float paramFloat) {
/* 1469 */     float f = Math.abs(paramFloat);
/* 1470 */     if (f >= 1.0F && f <= 1000.0F) {
/* 1471 */       paramFloat = Math.round(paramFloat * 1000.0F) / 1000.0F;
/*      */     }
/* 1473 */     return Float.toString(paramFloat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getPenX() {
/* 1482 */     return this.mPenX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getPenY() {
/* 1490 */     return this.mPenY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double getXRes() {
/* 1498 */     return 300.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double getYRes() {
/* 1505 */     return 300.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double getPhysicalPrintableX(Paper paramPaper) {
/* 1513 */     return 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double getPhysicalPrintableY(Paper paramPaper) {
/* 1522 */     return 0.0D;
/*      */   }
/*      */   
/*      */   protected double getPhysicalPrintableWidth(Paper paramPaper) {
/* 1526 */     return paramPaper.getImageableWidth();
/*      */   }
/*      */   
/*      */   protected double getPhysicalPrintableHeight(Paper paramPaper) {
/* 1530 */     return paramPaper.getImageableHeight();
/*      */   }
/*      */   
/*      */   protected double getPhysicalPageWidth(Paper paramPaper) {
/* 1534 */     return paramPaper.getWidth();
/*      */   }
/*      */   
/*      */   protected double getPhysicalPageHeight(Paper paramPaper) {
/* 1538 */     return paramPaper.getHeight();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getNoncollatedCopies() {
/* 1548 */     return 1;
/*      */   }
/*      */   
/*      */   protected int getCollatedCopies() {
/* 1552 */     return 1;
/*      */   }
/*      */ 
/*      */   
/*      */   private String[] printExecCmd(String paramString1, String paramString2, boolean paramBoolean, String paramString3, int paramInt, String paramString4) {
/*      */     String[] arrayOfString;
/* 1558 */     byte b1 = 1;
/* 1559 */     byte b2 = 2;
/* 1560 */     byte b3 = 4;
/* 1561 */     byte b4 = 8;
/* 1562 */     byte b5 = 16;
/* 1563 */     int i = 0;
/*      */     
/* 1565 */     byte b6 = 2;
/* 1566 */     byte b7 = 0;
/*      */     
/* 1568 */     if (paramString1 != null && !paramString1.equals("") && !paramString1.equals("lp")) {
/* 1569 */       i |= b1;
/* 1570 */       b6++;
/*      */     } 
/* 1572 */     if (paramString2 != null && !paramString2.equals("")) {
/* 1573 */       i |= b2;
/* 1574 */       b6++;
/*      */     } 
/* 1576 */     if (paramString3 != null && !paramString3.equals("")) {
/* 1577 */       i |= b3;
/* 1578 */       b6++;
/*      */     } 
/* 1580 */     if (paramInt > 1) {
/* 1581 */       i |= b4;
/* 1582 */       b6++;
/*      */     } 
/* 1584 */     if (paramBoolean) {
/* 1585 */       i |= b5;
/* 1586 */       b6++;
/*      */     } 
/*      */     
/* 1589 */     String str = System.getProperty("os.name");
/* 1590 */     if (str.equals("Linux") || str.contains("OS X")) {
/* 1591 */       arrayOfString = new String[b6];
/* 1592 */       arrayOfString[b7++] = "/usr/bin/lpr";
/* 1593 */       if ((i & b1) != 0) {
/* 1594 */         arrayOfString[b7++] = "-P" + paramString1;
/*      */       }
/* 1596 */       if ((i & b3) != 0) {
/* 1597 */         arrayOfString[b7++] = "-J" + paramString3;
/*      */       }
/* 1599 */       if ((i & b4) != 0) {
/* 1600 */         arrayOfString[b7++] = "-#" + paramInt;
/*      */       }
/* 1602 */       if ((i & b5) != 0) {
/* 1603 */         arrayOfString[b7++] = "-h";
/*      */       }
/* 1605 */       if ((i & b2) != 0) {
/* 1606 */         arrayOfString[b7++] = new String(paramString2);
/*      */       }
/*      */     } else {
/* 1609 */       b6++;
/* 1610 */       arrayOfString = new String[b6];
/* 1611 */       arrayOfString[b7++] = "/usr/bin/lp";
/* 1612 */       arrayOfString[b7++] = "-c";
/* 1613 */       if ((i & b1) != 0) {
/* 1614 */         arrayOfString[b7++] = "-d" + paramString1;
/*      */       }
/* 1616 */       if ((i & b3) != 0) {
/* 1617 */         arrayOfString[b7++] = "-t" + paramString3;
/*      */       }
/* 1619 */       if ((i & b4) != 0) {
/* 1620 */         arrayOfString[b7++] = "-n" + paramInt;
/*      */       }
/* 1622 */       if ((i & b5) != 0) {
/* 1623 */         arrayOfString[b7++] = "-o nobanner";
/*      */       }
/* 1625 */       if ((i & b2) != 0) {
/* 1626 */         arrayOfString[b7++] = "-o" + paramString2;
/*      */       }
/*      */     } 
/* 1629 */     arrayOfString[b7++] = paramString4;
/* 1630 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   private static int swapBGRtoRGB(byte[] paramArrayOfbyte1, int paramInt, byte[] paramArrayOfbyte2) {
/* 1634 */     byte b = 0;
/* 1635 */     while (paramInt < paramArrayOfbyte1.length - 2 && b < paramArrayOfbyte2.length - 2) {
/* 1636 */       paramArrayOfbyte2[b++] = paramArrayOfbyte1[paramInt + 2];
/* 1637 */       paramArrayOfbyte2[b++] = paramArrayOfbyte1[paramInt + 1];
/* 1638 */       paramArrayOfbyte2[b++] = paramArrayOfbyte1[paramInt + 0];
/* 1639 */       paramInt += 3;
/*      */     } 
/* 1641 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String makeCharsetName(String paramString, char[] paramArrayOfchar) {
/* 1651 */     if (paramString.equals("Cp1252") || paramString.equals("ISO8859_1"))
/* 1652 */       return "latin1"; 
/* 1653 */     if (paramString.equals("UTF8")) {
/*      */       
/* 1655 */       for (byte b = 0; b < paramArrayOfchar.length; b++) {
/* 1656 */         if (paramArrayOfchar[b] > 'ÿ') {
/* 1657 */           return paramString.toLowerCase();
/*      */         }
/*      */       } 
/* 1660 */       return "latin1";
/* 1661 */     }  if (paramString.startsWith("ISO8859")) {
/*      */       
/* 1663 */       for (byte b = 0; b < paramArrayOfchar.length; b++) {
/* 1664 */         if (paramArrayOfchar[b] > '') {
/* 1665 */           return paramString.toLowerCase();
/*      */         }
/*      */       } 
/* 1668 */       return "latin1";
/*      */     } 
/* 1670 */     return paramString.toLowerCase();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void prepDrawing() {
/* 1680 */     while (!isOuterGState() && (
/* 1681 */       !getGState().canSetClip(this.mLastClip) || 
/* 1682 */       !(getGState()).mTransform.equals(this.mLastTransform)))
/*      */     {
/*      */       
/* 1685 */       grestore();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1691 */     getGState().emitPSColor(this.mLastColor);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1697 */     if (isOuterGState()) {
/* 1698 */       gsave();
/* 1699 */       getGState().emitTransform(this.mLastTransform);
/* 1700 */       getGState().emitPSClip(this.mLastClip);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GState getGState() {
/* 1722 */     int i = this.mGStateStack.size();
/* 1723 */     return this.mGStateStack.get(i - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void gsave() {
/* 1732 */     GState gState = getGState();
/* 1733 */     this.mGStateStack.add(new GState(gState));
/* 1734 */     this.mPSStream.println("G");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void grestore() {
/* 1743 */     int i = this.mGStateStack.size();
/* 1744 */     this.mGStateStack.remove(i - 1);
/* 1745 */     this.mPSStream.println("R");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isOuterGState() {
/* 1754 */     return (this.mGStateStack.size() == 1);
/*      */   }
/*      */ 
/*      */   
/*      */   private class GState
/*      */   {
/*      */     Color mColor;
/*      */     
/*      */     Shape mClip;
/*      */     
/*      */     Font mFont;
/*      */     
/*      */     AffineTransform mTransform;
/*      */     
/*      */     GState() {
/* 1769 */       this.mColor = Color.black;
/* 1770 */       this.mClip = null;
/* 1771 */       this.mFont = null;
/* 1772 */       this.mTransform = new AffineTransform();
/*      */     }
/*      */     
/*      */     GState(GState param1GState) {
/* 1776 */       this.mColor = param1GState.mColor;
/* 1777 */       this.mClip = param1GState.mClip;
/* 1778 */       this.mFont = param1GState.mFont;
/* 1779 */       this.mTransform = param1GState.mTransform;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean canSetClip(Shape param1Shape) {
/* 1784 */       return (this.mClip == null || this.mClip.equals(param1Shape));
/*      */     }
/*      */ 
/*      */     
/*      */     void emitPSClip(Shape param1Shape) {
/* 1789 */       if (param1Shape != null && (this.mClip == null || 
/* 1790 */         !this.mClip.equals(param1Shape))) {
/* 1791 */         String str1 = PSPrinterJob.this.mFillOpStr;
/* 1792 */         String str2 = PSPrinterJob.this.mClipOpStr;
/* 1793 */         PSPrinterJob.this.convertToPSPath(param1Shape.getPathIterator(new AffineTransform()));
/* 1794 */         PSPrinterJob.this.selectClipPath();
/* 1795 */         this.mClip = param1Shape;
/*      */         
/* 1797 */         PSPrinterJob.this.mClipOpStr = str1;
/* 1798 */         PSPrinterJob.this.mFillOpStr = str1;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void emitTransform(AffineTransform param1AffineTransform) {
/* 1804 */       if (param1AffineTransform != null && !param1AffineTransform.equals(this.mTransform)) {
/* 1805 */         double[] arrayOfDouble = new double[6];
/* 1806 */         param1AffineTransform.getMatrix(arrayOfDouble);
/* 1807 */         PSPrinterJob.this.mPSStream.println("[" + (float)arrayOfDouble[0] + " " + (float)arrayOfDouble[1] + " " + (float)arrayOfDouble[2] + " " + (float)arrayOfDouble[3] + " " + (float)arrayOfDouble[4] + " " + (float)arrayOfDouble[5] + "] concat");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1815 */         this.mTransform = param1AffineTransform;
/*      */       } 
/*      */     }
/*      */     
/*      */     void emitPSColor(Color param1Color) {
/* 1820 */       if (param1Color != null && !param1Color.equals(this.mColor)) {
/* 1821 */         float[] arrayOfFloat = param1Color.getRGBColorComponents(null);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1826 */         if (arrayOfFloat[0] == arrayOfFloat[1] && arrayOfFloat[1] == arrayOfFloat[2]) {
/* 1827 */           PSPrinterJob.this.mPSStream.println(arrayOfFloat[0] + " SG");
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1832 */           PSPrinterJob.this.mPSStream.println(arrayOfFloat[0] + " " + arrayOfFloat[1] + " " + arrayOfFloat[2] + " " + " SC");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1838 */         this.mColor = param1Color;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void emitPSFont(int param1Int, float param1Float) {
/* 1844 */       PSPrinterJob.this.mPSStream.println(param1Float + " " + param1Int + " " + "F");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void convertToPSPath(PathIterator paramPathIterator) {
/*      */     byte b;
/* 1855 */     float[] arrayOfFloat = new float[6];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1862 */     if (paramPathIterator.getWindingRule() == 0) {
/* 1863 */       b = 1;
/*      */     } else {
/* 1865 */       b = 2;
/*      */     } 
/*      */     
/* 1868 */     beginPath();
/*      */     
/* 1870 */     setFillMode(b);
/*      */     
/* 1872 */     while (!paramPathIterator.isDone()) {
/* 1873 */       float f1, f2, f3, f4, f5, f6; int i = paramPathIterator.currentSegment(arrayOfFloat);
/*      */       
/* 1875 */       switch (i) {
/*      */         case 0:
/* 1877 */           moveTo(arrayOfFloat[0], arrayOfFloat[1]);
/*      */           break;
/*      */         
/*      */         case 1:
/* 1881 */           lineTo(arrayOfFloat[0], arrayOfFloat[1]);
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/* 1887 */           f1 = getPenX();
/* 1888 */           f2 = getPenY();
/* 1889 */           f3 = f1 + (arrayOfFloat[0] - f1) * 2.0F / 3.0F;
/* 1890 */           f4 = f2 + (arrayOfFloat[1] - f2) * 2.0F / 3.0F;
/* 1891 */           f5 = arrayOfFloat[2] - (arrayOfFloat[2] - arrayOfFloat[0]) * 2.0F / 3.0F;
/* 1892 */           f6 = arrayOfFloat[3] - (arrayOfFloat[3] - arrayOfFloat[1]) * 2.0F / 3.0F;
/* 1893 */           bezierTo(f3, f4, f5, f6, arrayOfFloat[2], arrayOfFloat[3]);
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 3:
/* 1899 */           bezierTo(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3], arrayOfFloat[4], arrayOfFloat[5]);
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 4:
/* 1905 */           closeSubpath();
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1910 */       paramPathIterator.next();
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
/*      */   protected void deviceFill(PathIterator paramPathIterator, Color paramColor, AffineTransform paramAffineTransform, Shape paramShape) {
/* 1922 */     setTransform(paramAffineTransform);
/* 1923 */     setClip(paramShape);
/* 1924 */     setColor(paramColor);
/* 1925 */     convertToPSPath(paramPathIterator);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1930 */     this.mPSStream.println("G");
/* 1931 */     selectClipPath();
/* 1932 */     fillPath();
/* 1933 */     this.mPSStream.println("R N");
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
/*      */   private byte[] rlEncode(byte[] paramArrayOfbyte) {
/* 1955 */     byte b1 = 0;
/* 1956 */     byte b2 = 0;
/* 1957 */     byte b3 = 0;
/* 1958 */     byte b4 = 0;
/* 1959 */     byte[] arrayOfByte1 = new byte[paramArrayOfbyte.length * 2 + 2];
/* 1960 */     while (b1 < paramArrayOfbyte.length) {
/* 1961 */       if (!b4) {
/* 1962 */         b3 = b1++;
/* 1963 */         b4 = 1;
/*      */       } 
/*      */       
/* 1966 */       while (b4 < '' && b1 < paramArrayOfbyte.length && paramArrayOfbyte[b1] == paramArrayOfbyte[b3]) {
/*      */         
/* 1968 */         b4++;
/* 1969 */         b1++;
/*      */       } 
/*      */       
/* 1972 */       if (b4 > 1) {
/* 1973 */         arrayOfByte1[b2++] = (byte)(257 - b4);
/* 1974 */         arrayOfByte1[b2++] = paramArrayOfbyte[b3];
/* 1975 */         b4 = 0;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1980 */       while (b4 < '' && b1 < paramArrayOfbyte.length && paramArrayOfbyte[b1] != paramArrayOfbyte[b1 - 1]) {
/*      */         
/* 1982 */         b4++;
/* 1983 */         b1++;
/*      */       } 
/* 1985 */       arrayOfByte1[b2++] = (byte)(b4 - 1);
/* 1986 */       for (byte b = b3; b < b3 + b4; b++) {
/* 1987 */         arrayOfByte1[b2++] = paramArrayOfbyte[b];
/*      */       }
/* 1989 */       b4 = 0;
/*      */     } 
/* 1991 */     arrayOfByte1[b2++] = Byte.MIN_VALUE;
/* 1992 */     byte[] arrayOfByte2 = new byte[b2];
/* 1993 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, b2);
/*      */     
/* 1995 */     return arrayOfByte2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] ascii85Encode(byte[] paramArrayOfbyte) {
/* 2002 */     byte[] arrayOfByte1 = new byte[(paramArrayOfbyte.length + 4) * 5 / 4 + 2];
/* 2003 */     long l1 = 85L;
/* 2004 */     long l2 = l1 * l1;
/* 2005 */     long l3 = l1 * l2;
/* 2006 */     long l4 = l1 * l3;
/* 2007 */     byte b1 = 33;
/*      */     
/* 2009 */     byte b2 = 0;
/* 2010 */     byte b3 = 0;
/*      */ 
/*      */     
/* 2013 */     while (b2 + 3 < paramArrayOfbyte.length) {
/* 2014 */       long l5 = ((paramArrayOfbyte[b2++] & 0xFF) << 24L) + ((paramArrayOfbyte[b2++] & 0xFF) << 16L) + ((paramArrayOfbyte[b2++] & 0xFF) << 8L) + (paramArrayOfbyte[b2++] & 0xFF);
/*      */ 
/*      */ 
/*      */       
/* 2018 */       if (l5 == 0L) {
/* 2019 */         arrayOfByte1[b3++] = 122; continue;
/*      */       } 
/* 2021 */       long l6 = l5;
/* 2022 */       arrayOfByte1[b3++] = (byte)(int)(l6 / l4 + b1); l6 %= l4;
/* 2023 */       arrayOfByte1[b3++] = (byte)(int)(l6 / l3 + b1); l6 %= l3;
/* 2024 */       arrayOfByte1[b3++] = (byte)(int)(l6 / l2 + b1); l6 %= l2;
/* 2025 */       arrayOfByte1[b3++] = (byte)(int)(l6 / l1 + b1); l6 %= l1;
/* 2026 */       arrayOfByte1[b3++] = (byte)(int)(l6 + b1);
/*      */     } 
/*      */ 
/*      */     
/* 2030 */     if (b2 < paramArrayOfbyte.length) {
/* 2031 */       int i = paramArrayOfbyte.length - b2;
/*      */       
/* 2033 */       long l5 = 0L;
/* 2034 */       while (b2 < paramArrayOfbyte.length) {
/* 2035 */         l5 = (l5 << 8L) + (paramArrayOfbyte[b2++] & 0xFF);
/*      */       }
/*      */       
/* 2038 */       int j = 4 - i;
/* 2039 */       while (j-- > 0) {
/* 2040 */         l5 <<= 8L;
/*      */       }
/* 2042 */       byte[] arrayOfByte = new byte[5];
/* 2043 */       long l6 = l5;
/* 2044 */       arrayOfByte[0] = (byte)(int)(l6 / l4 + b1); l6 %= l4;
/* 2045 */       arrayOfByte[1] = (byte)(int)(l6 / l3 + b1); l6 %= l3;
/* 2046 */       arrayOfByte[2] = (byte)(int)(l6 / l2 + b1); l6 %= l2;
/* 2047 */       arrayOfByte[3] = (byte)(int)(l6 / l1 + b1); l6 %= l1;
/* 2048 */       arrayOfByte[4] = (byte)(int)(l6 + b1);
/*      */       
/* 2050 */       for (byte b = 0; b < i + 1; b++) {
/* 2051 */         arrayOfByte1[b3++] = arrayOfByte[b];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2056 */     arrayOfByte1[b3++] = 126; arrayOfByte1[b3++] = 62;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2068 */     byte[] arrayOfByte2 = new byte[b3];
/* 2069 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, b3);
/* 2070 */     return arrayOfByte2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class PluginPrinter
/*      */     implements Printable
/*      */   {
/*      */     private PSPrinterJob.EPSPrinter epsPrinter;
/*      */ 
/*      */ 
/*      */     
/*      */     private Component applet;
/*      */ 
/*      */ 
/*      */     
/*      */     private PrintStream stream;
/*      */ 
/*      */ 
/*      */     
/*      */     private String epsTitle;
/*      */ 
/*      */ 
/*      */     
/*      */     private int bx;
/*      */ 
/*      */ 
/*      */     
/*      */     private int by;
/*      */ 
/*      */ 
/*      */     
/*      */     private int bw;
/*      */ 
/*      */ 
/*      */     
/*      */     private int bh;
/*      */ 
/*      */ 
/*      */     
/*      */     private int width;
/*      */ 
/*      */     
/*      */     private int height;
/*      */ 
/*      */ 
/*      */     
/*      */     public PluginPrinter(Component param1Component, PrintStream param1PrintStream, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2119 */       this.applet = param1Component;
/* 2120 */       this.epsTitle = "Java Plugin Applet";
/* 2121 */       this.stream = param1PrintStream;
/* 2122 */       this.bx = param1Int1;
/* 2123 */       this.by = param1Int2;
/* 2124 */       this.bw = param1Int3;
/* 2125 */       this.bh = param1Int4;
/* 2126 */       this.width = (param1Component.size()).width;
/* 2127 */       this.height = (param1Component.size()).height;
/* 2128 */       this.epsPrinter = new PSPrinterJob.EPSPrinter(this, this.epsTitle, param1PrintStream, 0, 0, this.width, this.height);
/*      */     }
/*      */ 
/*      */     
/*      */     public void printPluginPSHeader() {
/* 2133 */       this.stream.println("%%BeginDocument: JavaPluginApplet");
/*      */     }
/*      */     
/*      */     public void printPluginApplet() {
/*      */       try {
/* 2138 */         this.epsPrinter.print();
/* 2139 */       } catch (PrinterException printerException) {}
/*      */     }
/*      */ 
/*      */     
/*      */     public void printPluginPSTrailer() {
/* 2144 */       this.stream.println("%%EndDocument: JavaPluginApplet");
/* 2145 */       this.stream.flush();
/*      */     }
/*      */     
/*      */     public void printAll() {
/* 2149 */       printPluginPSHeader();
/* 2150 */       printPluginApplet();
/* 2151 */       printPluginPSTrailer();
/*      */     }
/*      */     
/*      */     public int print(Graphics param1Graphics, PageFormat param1PageFormat, int param1Int) {
/* 2155 */       if (param1Int > 0) {
/* 2156 */         return 1;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2162 */       this.applet.printAll(param1Graphics);
/* 2163 */       return 0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class EPSPrinter
/*      */     implements Pageable
/*      */   {
/*      */     private PageFormat pf;
/*      */ 
/*      */     
/*      */     private PSPrinterJob job;
/*      */ 
/*      */     
/*      */     private int llx;
/*      */ 
/*      */     
/*      */     private int lly;
/*      */     
/*      */     private int urx;
/*      */     
/*      */     private int ury;
/*      */     
/*      */     private Printable printable;
/*      */     
/*      */     private PrintStream stream;
/*      */     
/*      */     private String epsTitle;
/*      */ 
/*      */     
/*      */     public EPSPrinter(Printable param1Printable, String param1String, PrintStream param1PrintStream, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2195 */       this.printable = param1Printable;
/* 2196 */       this.epsTitle = param1String;
/* 2197 */       this.stream = param1PrintStream;
/* 2198 */       this.llx = param1Int1;
/* 2199 */       this.lly = param1Int2;
/* 2200 */       this.urx = this.llx + param1Int3;
/* 2201 */       this.ury = this.lly + param1Int4;
/*      */ 
/*      */ 
/*      */       
/* 2205 */       Paper paper = new Paper();
/* 2206 */       paper.setSize(param1Int3, param1Int4);
/* 2207 */       paper.setImageableArea(0.0D, 0.0D, param1Int3, param1Int4);
/* 2208 */       this.pf = new PageFormat();
/* 2209 */       this.pf.setPaper(paper);
/*      */     }
/*      */     
/*      */     public void print() throws PrinterException {
/* 2213 */       this.stream.println("%!PS-Adobe-3.0 EPSF-3.0");
/* 2214 */       this.stream.println("%%BoundingBox: " + this.llx + " " + this.lly + " " + this.urx + " " + this.ury);
/*      */       
/* 2216 */       this.stream.println("%%Title: " + this.epsTitle);
/* 2217 */       this.stream.println("%%Creator: Java Printing");
/* 2218 */       this.stream.println("%%CreationDate: " + new Date());
/* 2219 */       this.stream.println("%%EndComments");
/* 2220 */       this.stream.println("/pluginSave save def");
/* 2221 */       this.stream.println("mark");
/*      */       
/* 2223 */       this.job = new PSPrinterJob();
/* 2224 */       this.job.epsPrinter = this;
/* 2225 */       this.job.mPSStream = this.stream;
/* 2226 */       this.job.mDestType = 2;
/*      */       
/* 2228 */       this.job.startDoc();
/*      */       try {
/* 2230 */         this.job.printPage(this, 0);
/* 2231 */       } catch (Throwable throwable) {
/* 2232 */         if (throwable instanceof PrinterException) {
/* 2233 */           throw (PrinterException)throwable;
/*      */         }
/* 2235 */         throw new PrinterException(throwable.toString());
/*      */       } finally {
/*      */         
/* 2238 */         this.stream.println("cleartomark");
/* 2239 */         this.stream.println("pluginSave restore");
/* 2240 */         this.job.endDoc();
/*      */       } 
/* 2242 */       this.stream.flush();
/*      */     }
/*      */     
/*      */     public int getNumberOfPages() {
/* 2246 */       return 1;
/*      */     }
/*      */     
/*      */     public PageFormat getPageFormat(int param1Int) {
/* 2250 */       if (param1Int > 0) {
/* 2251 */         throw new IndexOutOfBoundsException("pgIndex");
/*      */       }
/* 2253 */       return this.pf;
/*      */     }
/*      */ 
/*      */     
/*      */     public Printable getPrintable(int param1Int) {
/* 2258 */       if (param1Int > 0) {
/* 2259 */         throw new IndexOutOfBoundsException("pgIndex");
/*      */       }
/* 2261 */       return this.printable;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/PSPrinterJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */