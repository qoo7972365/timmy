/*      */ package sun.print;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.security.AccessController;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Locale;
/*      */ import javax.print.DocFlavor;
/*      */ import javax.print.DocPrintJob;
/*      */ import javax.print.PrintService;
/*      */ import javax.print.ServiceUIFactory;
/*      */ import javax.print.attribute.Attribute;
/*      */ import javax.print.attribute.AttributeSet;
/*      */ import javax.print.attribute.AttributeSetUtilities;
/*      */ import javax.print.attribute.HashAttributeSet;
/*      */ import javax.print.attribute.HashPrintServiceAttributeSet;
/*      */ import javax.print.attribute.PrintServiceAttribute;
/*      */ import javax.print.attribute.PrintServiceAttributeSet;
/*      */ import javax.print.attribute.standard.Chromaticity;
/*      */ import javax.print.attribute.standard.Copies;
/*      */ import javax.print.attribute.standard.CopiesSupported;
/*      */ import javax.print.attribute.standard.Destination;
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
/*      */ import javax.print.attribute.standard.PrinterName;
/*      */ import javax.print.attribute.standard.PrinterState;
/*      */ import javax.print.attribute.standard.PrinterStateReason;
/*      */ import javax.print.attribute.standard.PrinterStateReasons;
/*      */ import javax.print.attribute.standard.QueuedJobCount;
/*      */ import javax.print.attribute.standard.RequestingUserName;
/*      */ import javax.print.attribute.standard.Severity;
/*      */ import javax.print.attribute.standard.SheetCollate;
/*      */ import javax.print.attribute.standard.Sides;
/*      */ import javax.print.event.PrintServiceAttributeListener;
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
/*      */ public class UnixPrintService
/*      */   implements PrintService, AttributeUpdater, SunPrinterJobService
/*      */ {
/*   79 */   private static String encoding = "ISO8859_1";
/*      */   
/*      */   private static DocFlavor textByteFlavor;
/*   82 */   private static DocFlavor[] supportedDocFlavors = null;
/*   83 */   private static final DocFlavor[] supportedDocFlavorsInit = new DocFlavor[] { DocFlavor.BYTE_ARRAY.POSTSCRIPT, DocFlavor.INPUT_STREAM.POSTSCRIPT, DocFlavor.URL.POSTSCRIPT, DocFlavor.BYTE_ARRAY.GIF, DocFlavor.INPUT_STREAM.GIF, DocFlavor.URL.GIF, DocFlavor.BYTE_ARRAY.JPEG, DocFlavor.INPUT_STREAM.JPEG, DocFlavor.URL.JPEG, DocFlavor.BYTE_ARRAY.PNG, DocFlavor.INPUT_STREAM.PNG, DocFlavor.URL.PNG, DocFlavor.CHAR_ARRAY.TEXT_PLAIN, DocFlavor.READER.TEXT_PLAIN, DocFlavor.STRING.TEXT_PLAIN, DocFlavor.BYTE_ARRAY.TEXT_PLAIN_UTF_8, DocFlavor.BYTE_ARRAY.TEXT_PLAIN_UTF_16, DocFlavor.BYTE_ARRAY.TEXT_PLAIN_UTF_16BE, DocFlavor.BYTE_ARRAY.TEXT_PLAIN_UTF_16LE, DocFlavor.BYTE_ARRAY.TEXT_PLAIN_US_ASCII, DocFlavor.INPUT_STREAM.TEXT_PLAIN_UTF_8, DocFlavor.INPUT_STREAM.TEXT_PLAIN_UTF_16, DocFlavor.INPUT_STREAM.TEXT_PLAIN_UTF_16BE, DocFlavor.INPUT_STREAM.TEXT_PLAIN_UTF_16LE, DocFlavor.INPUT_STREAM.TEXT_PLAIN_US_ASCII, DocFlavor.URL.TEXT_PLAIN_UTF_8, DocFlavor.URL.TEXT_PLAIN_UTF_16, DocFlavor.URL.TEXT_PLAIN_UTF_16BE, DocFlavor.URL.TEXT_PLAIN_UTF_16LE, DocFlavor.URL.TEXT_PLAIN_US_ASCII, DocFlavor.SERVICE_FORMATTED.PAGEABLE, DocFlavor.SERVICE_FORMATTED.PRINTABLE, DocFlavor.BYTE_ARRAY.AUTOSENSE, DocFlavor.URL.AUTOSENSE, DocFlavor.INPUT_STREAM.AUTOSENSE };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  129 */   private static final DocFlavor[] supportedHostDocFlavors = new DocFlavor[] { DocFlavor.BYTE_ARRAY.TEXT_PLAIN_HOST, DocFlavor.INPUT_STREAM.TEXT_PLAIN_HOST, DocFlavor.URL.TEXT_PLAIN_HOST };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  135 */   String[] lpcStatusCom = new String[] { "", "| grep -E '^[ 0-9a-zA-Z_-]*@' | awk '{print $2, $3}'" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  140 */   String[] lpcQueueCom = new String[] { "", "| grep -E '^[ 0-9a-zA-Z_-]*@' | awk '{print $4}'" }; private static final Class[] serviceAttrCats; private static final Class[] otherAttrCats; private static int MAXCOPIES; private static final MediaSizeName[] mediaSizes;
/*      */   private String printer;
/*      */   private PrinterName name;
/*      */   private boolean isInvalid;
/*      */   private transient PrintServiceAttributeSet lastSet;
/*      */   
/*  146 */   static { encoding = AccessController.<String>doPrivileged(new GetPropertyAction("file.encoding"));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  151 */     serviceAttrCats = new Class[] { PrinterName.class, PrinterIsAcceptingJobs.class, QueuedJobCount.class };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  160 */     otherAttrCats = new Class[] { Chromaticity.class, Copies.class, Destination.class, Fidelity.class, JobName.class, JobSheets.class, Media.class, MediaPrintableArea.class, OrientationRequested.class, PageRanges.class, RequestingUserName.class, SheetCollate.class, Sides.class };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  176 */     MAXCOPIES = 1000;
/*      */     
/*  178 */     mediaSizes = new MediaSizeName[] { MediaSizeName.NA_LETTER, MediaSizeName.TABLOID, MediaSizeName.LEDGER, MediaSizeName.NA_LEGAL, MediaSizeName.EXECUTIVE, MediaSizeName.ISO_A3, MediaSizeName.ISO_A4, MediaSizeName.ISO_A5, MediaSizeName.ISO_B4, MediaSizeName.ISO_B5 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  910 */     mpas = null; } private transient ServiceNotifier notifier = null; private static MediaPrintableArea[] mpas; UnixPrintService(String paramString) { if (paramString == null) throw new IllegalArgumentException("null printer name");  this.printer = paramString; this.isInvalid = false; } public void invalidateService() { this.isInvalid = true; } public String getName() { return this.printer; } private PrinterName getPrinterName() { if (this.name == null) this.name = new PrinterName(this.printer, null);  return this.name; } private PrinterIsAcceptingJobs getPrinterIsAcceptingJobsSysV() { String str = "/usr/bin/lpstat -a " + this.printer; String[] arrayOfString = PrintServiceLookupProvider.execCmd(str); if (arrayOfString != null && arrayOfString.length > 0) { if (arrayOfString[0].startsWith(this.printer + " accepting requests")) return PrinterIsAcceptingJobs.ACCEPTING_JOBS;  if (arrayOfString[0].startsWith(this.printer)) { int i = this.printer.length(); String str1 = arrayOfString[0]; if (str1.length() > i && str1.charAt(i) == '@' && str1.indexOf(" accepting requests", i) > 0 && str1.indexOf(" not accepting requests", i) == -1) return PrinterIsAcceptingJobs.ACCEPTING_JOBS;  }  }  return PrinterIsAcceptingJobs.NOT_ACCEPTING_JOBS; } private PrinterIsAcceptingJobs getPrinterIsAcceptingJobsBSD() { if (PrintServiceLookupProvider.cmdIndex == -1) PrintServiceLookupProvider.cmdIndex = PrintServiceLookupProvider.getBSDCommandIndex();  String str = "/usr/sbin/lpc status " + this.printer + this.lpcStatusCom[PrintServiceLookupProvider.cmdIndex]; String[] arrayOfString = PrintServiceLookupProvider.execCmd(str); if (arrayOfString != null && arrayOfString.length > 0) if (PrintServiceLookupProvider.cmdIndex == 1) { if (arrayOfString[0].startsWith("enabled enabled")) return PrinterIsAcceptingJobs.ACCEPTING_JOBS;  } else if ((arrayOfString[1].trim().startsWith("queuing is enabled") && arrayOfString[2].trim().startsWith("printing is enabled")) || (arrayOfString.length >= 4 && arrayOfString[2].trim().startsWith("queuing is enabled") && arrayOfString[3].trim().startsWith("printing is enabled"))) { return PrinterIsAcceptingJobs.ACCEPTING_JOBS; }   return PrinterIsAcceptingJobs.NOT_ACCEPTING_JOBS; } protected static String[] filterPrinterNamesAIX(String[] paramArrayOfString) { ArrayList<String> arrayList = new ArrayList(); for (byte b = 0; b < paramArrayOfString.length; b++) { if (!paramArrayOfString[b].startsWith("---") && !paramArrayOfString[b].startsWith("Queue") && !paramArrayOfString[b].equals("")) { String[] arrayOfString = paramArrayOfString[b].split(" "); if (arrayOfString.length >= 1 && !arrayOfString[0].trim().endsWith(":")) arrayList.add(paramArrayOfString[b]);  }  }  return arrayList.<String>toArray(new String[arrayList.size()]); } private PrinterIsAcceptingJobs getPrinterIsAcceptingJobsAIX() { String str = "/usr/bin/lpstat -a" + this.printer; String[] arrayOfString = PrintServiceLookupProvider.execCmd(str); arrayOfString = filterPrinterNamesAIX(arrayOfString); if (arrayOfString != null && arrayOfString.length > 0) for (byte b = 0; b < arrayOfString.length; b++) { if (arrayOfString[b].contains("READY") || arrayOfString[b].contains("RUNNING")) return PrinterIsAcceptingJobs.ACCEPTING_JOBS;  }   return PrinterIsAcceptingJobs.NOT_ACCEPTING_JOBS; } private PrinterIsAcceptingJobs getPrinterIsAcceptingJobs() { if (PrintServiceLookupProvider.isSysV()) return getPrinterIsAcceptingJobsSysV();  if (PrintServiceLookupProvider.isBSD()) return getPrinterIsAcceptingJobsBSD();  if (PrintServiceLookupProvider.isAIX()) return getPrinterIsAcceptingJobsAIX();  return PrinterIsAcceptingJobs.ACCEPTING_JOBS; } private PrinterState getPrinterState() { if (this.isInvalid) return PrinterState.STOPPED;  return null; } private PrinterStateReasons getPrinterStateReasons() { if (this.isInvalid) { PrinterStateReasons printerStateReasons = new PrinterStateReasons(); printerStateReasons.put(PrinterStateReason.SHUTDOWN, Severity.ERROR); return printerStateReasons; }  return null; } private QueuedJobCount getQueuedJobCountSysV() { String str = "/usr/bin/lpstat -R " + this.printer; String[] arrayOfString = PrintServiceLookupProvider.execCmd(str); boolean bool = (arrayOfString == null) ? false : arrayOfString.length; return new QueuedJobCount(bool); } private QueuedJobCount getQueuedJobCountBSD() { if (PrintServiceLookupProvider.cmdIndex == -1) PrintServiceLookupProvider.cmdIndex = PrintServiceLookupProvider.getBSDCommandIndex();  int i = 0; String str = "/usr/sbin/lpc status " + this.printer + this.lpcQueueCom[PrintServiceLookupProvider.cmdIndex]; String[] arrayOfString = PrintServiceLookupProvider.execCmd(str); if (arrayOfString != null && arrayOfString.length > 0) { String str1; if (PrintServiceLookupProvider.cmdIndex == 1) { str1 = arrayOfString[0]; } else { str1 = arrayOfString[3].trim(); if (str1.startsWith("no")) return new QueuedJobCount(0);  str1 = str1.substring(0, str1.indexOf(' ')); }  try { i = Integer.parseInt(str1); } catch (NumberFormatException numberFormatException) {} }  return new QueuedJobCount(i); } private QueuedJobCount getQueuedJobCountAIX() { String str = "/usr/bin/lpstat -a" + this.printer; String[] arrayOfString = PrintServiceLookupProvider.execCmd(str); arrayOfString = filterPrinterNamesAIX(arrayOfString); byte b = 0; if (arrayOfString != null && arrayOfString.length > 0) for (byte b1 = 0; b1 < arrayOfString.length; b1++) { if (arrayOfString[b1].contains("QUEUED")) b++;  }   return new QueuedJobCount(b); } private QueuedJobCount getQueuedJobCount() { if (PrintServiceLookupProvider.isSysV()) return getQueuedJobCountSysV();  if (PrintServiceLookupProvider.isBSD()) return getQueuedJobCountBSD();  if (PrintServiceLookupProvider.isAIX()) return getQueuedJobCountAIX();  return new QueuedJobCount(0); }
/*      */   private PrintServiceAttributeSet getSysVServiceAttributes() { HashPrintServiceAttributeSet hashPrintServiceAttributeSet = new HashPrintServiceAttributeSet(); hashPrintServiceAttributeSet.add(getQueuedJobCountSysV()); hashPrintServiceAttributeSet.add(getPrinterIsAcceptingJobsSysV()); return hashPrintServiceAttributeSet; }
/*      */   private PrintServiceAttributeSet getBSDServiceAttributes() { HashPrintServiceAttributeSet hashPrintServiceAttributeSet = new HashPrintServiceAttributeSet(); hashPrintServiceAttributeSet.add(getQueuedJobCountBSD()); hashPrintServiceAttributeSet.add(getPrinterIsAcceptingJobsBSD()); return hashPrintServiceAttributeSet; }
/*  913 */   private MediaPrintableArea[] getAllPrintableAreas() { if (mpas == null) {
/*  914 */       Media[] arrayOfMedia = (Media[])getSupportedAttributeValues((Class)Media.class, null, null);
/*      */       
/*  916 */       mpas = new MediaPrintableArea[arrayOfMedia.length];
/*  917 */       for (byte b = 0; b < mpas.length; b++) {
/*  918 */         if (arrayOfMedia[b] instanceof MediaSizeName) {
/*  919 */           MediaSizeName mediaSizeName = (MediaSizeName)arrayOfMedia[b];
/*  920 */           MediaSize mediaSize = MediaSize.getMediaSizeForName(mediaSizeName);
/*  921 */           if (mediaSize == null) {
/*  922 */             mpas[b] = (MediaPrintableArea)
/*  923 */               getDefaultAttributeValue((Class)MediaPrintableArea.class);
/*      */           } else {
/*  925 */             mpas[b] = new MediaPrintableArea(0.25F, 0.25F, mediaSize
/*  926 */                 .getX(25400) - 0.5F, mediaSize
/*  927 */                 .getY(25400) - 0.5F, 25400);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  933 */     MediaPrintableArea[] arrayOfMediaPrintableArea = new MediaPrintableArea[mpas.length];
/*  934 */     System.arraycopy(mpas, 0, arrayOfMediaPrintableArea, 0, mpas.length);
/*  935 */     return arrayOfMediaPrintableArea; } private PrintServiceAttributeSet getAIXServiceAttributes() { HashPrintServiceAttributeSet hashPrintServiceAttributeSet = new HashPrintServiceAttributeSet(); hashPrintServiceAttributeSet.add(getQueuedJobCountAIX()); hashPrintServiceAttributeSet.add(getPrinterIsAcceptingJobsAIX()); return hashPrintServiceAttributeSet; } private boolean isSupportedCopies(Copies paramCopies) { int i = paramCopies.getValue(); return (i > 0 && i < MAXCOPIES); } private boolean isSupportedMedia(MediaSizeName paramMediaSizeName) { for (byte b = 0; b < mediaSizes.length; b++) { if (paramMediaSizeName.equals(mediaSizes[b])) return true;  }  return false; }
/*      */   public DocPrintJob createPrintJob() { SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) securityManager.checkPrintJobAccess();  return new UnixPrintJob(this); }
/*      */   private PrintServiceAttributeSet getDynamicAttributes() { if (PrintServiceLookupProvider.isSysV()) return getSysVServiceAttributes();  if (PrintServiceLookupProvider.isAIX()) return getAIXServiceAttributes();  return getBSDServiceAttributes(); }
/*      */   public PrintServiceAttributeSet getUpdatedAttributes() { PrintServiceAttributeSet printServiceAttributeSet = getDynamicAttributes(); if (this.lastSet == null) { this.lastSet = printServiceAttributeSet; return AttributeSetUtilities.unmodifiableView(printServiceAttributeSet); }  HashPrintServiceAttributeSet hashPrintServiceAttributeSet = new HashPrintServiceAttributeSet(); Attribute[] arrayOfAttribute = printServiceAttributeSet.toArray(); for (byte b = 0; b < arrayOfAttribute.length; b++) { Attribute attribute = arrayOfAttribute[b]; if (!this.lastSet.containsValue(attribute)) hashPrintServiceAttributeSet.add(attribute);  }  this.lastSet = printServiceAttributeSet; return AttributeSetUtilities.unmodifiableView(hashPrintServiceAttributeSet); }
/*      */   public void wakeNotifier() { synchronized (this) { if (this.notifier != null) this.notifier.wake();  }  }
/*      */   public void addPrintServiceAttributeListener(PrintServiceAttributeListener paramPrintServiceAttributeListener) { synchronized (this) { if (paramPrintServiceAttributeListener == null) return;  if (this.notifier == null) this.notifier = new ServiceNotifier(this);  this.notifier.addListener(paramPrintServiceAttributeListener); }  }
/*      */   public void removePrintServiceAttributeListener(PrintServiceAttributeListener paramPrintServiceAttributeListener) { synchronized (this) { if (paramPrintServiceAttributeListener == null || this.notifier == null) return;  this.notifier.removeListener(paramPrintServiceAttributeListener); if (this.notifier.isEmpty()) { this.notifier.stopNotifier(); this.notifier = null; }  }  }
/*  942 */   private boolean isServiceFormattedFlavor(DocFlavor paramDocFlavor) { return (paramDocFlavor
/*  943 */       .equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor
/*  944 */       .equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE) || paramDocFlavor
/*  945 */       .equals(DocFlavor.BYTE_ARRAY.GIF) || paramDocFlavor
/*  946 */       .equals(DocFlavor.INPUT_STREAM.GIF) || paramDocFlavor
/*  947 */       .equals(DocFlavor.URL.GIF) || paramDocFlavor
/*  948 */       .equals(DocFlavor.BYTE_ARRAY.JPEG) || paramDocFlavor
/*  949 */       .equals(DocFlavor.INPUT_STREAM.JPEG) || paramDocFlavor
/*  950 */       .equals(DocFlavor.URL.JPEG) || paramDocFlavor
/*  951 */       .equals(DocFlavor.BYTE_ARRAY.PNG) || paramDocFlavor
/*  952 */       .equals(DocFlavor.INPUT_STREAM.PNG) || paramDocFlavor
/*  953 */       .equals(DocFlavor.URL.PNG)); } public <T extends PrintServiceAttribute> T getAttribute(Class<T> paramClass) { if (paramClass == null) throw new NullPointerException("category");  if (!PrintServiceAttribute.class.isAssignableFrom(paramClass)) throw new IllegalArgumentException("Not a PrintServiceAttribute");  if (paramClass == PrinterName.class) return (T)getPrinterName();  if (paramClass == PrinterState.class) return (T)getPrinterState();  if (paramClass == PrinterStateReasons.class) return (T)getPrinterStateReasons();  if (paramClass == QueuedJobCount.class) return (T)getQueuedJobCount();  if (paramClass == PrinterIsAcceptingJobs.class) return (T)getPrinterIsAcceptingJobs();  return null; }
/*      */   public PrintServiceAttributeSet getAttributes() { HashPrintServiceAttributeSet hashPrintServiceAttributeSet = new HashPrintServiceAttributeSet(); hashPrintServiceAttributeSet.add(getPrinterName()); hashPrintServiceAttributeSet.add(getPrinterIsAcceptingJobs()); PrinterState printerState = getPrinterState(); if (printerState != null) hashPrintServiceAttributeSet.add(printerState);  PrinterStateReasons printerStateReasons = getPrinterStateReasons(); if (printerStateReasons != null) hashPrintServiceAttributeSet.add(printerStateReasons);  hashPrintServiceAttributeSet.add(getQueuedJobCount()); return AttributeSetUtilities.unmodifiableView(hashPrintServiceAttributeSet); }
/*      */   private void initSupportedDocFlavors() { String str = DocFlavor.hostEncoding.toLowerCase(Locale.ENGLISH); if (!str.equals("utf-8") && !str.equals("utf-16") && !str.equals("utf-16be") && !str.equals("utf-16le") && !str.equals("us-ascii")) { int i = supportedDocFlavorsInit.length; DocFlavor[] arrayOfDocFlavor = new DocFlavor[i + supportedHostDocFlavors.length]; System.arraycopy(supportedHostDocFlavors, 0, arrayOfDocFlavor, i, supportedHostDocFlavors.length); System.arraycopy(supportedDocFlavorsInit, 0, arrayOfDocFlavor, 0, i); supportedDocFlavors = arrayOfDocFlavor; } else { supportedDocFlavors = supportedDocFlavorsInit; }  }
/*      */   public DocFlavor[] getSupportedDocFlavors() { if (supportedDocFlavors == null) initSupportedDocFlavors();  int i = supportedDocFlavors.length; DocFlavor[] arrayOfDocFlavor = new DocFlavor[i]; System.arraycopy(supportedDocFlavors, 0, arrayOfDocFlavor, 0, i); return arrayOfDocFlavor; }
/*      */   public boolean isDocFlavorSupported(DocFlavor paramDocFlavor) { if (supportedDocFlavors == null) initSupportedDocFlavors();  for (byte b = 0; b < supportedDocFlavors.length; b++) { if (paramDocFlavor.equals(supportedDocFlavors[b]))
/*      */         return true;  }  return false; }
/*  959 */   public boolean isAttributeValueSupported(Attribute paramAttribute, DocFlavor paramDocFlavor, AttributeSet paramAttributeSet) { if (paramAttribute == null) {
/*  960 */       throw new NullPointerException("null attribute");
/*      */     }
/*  962 */     if (paramDocFlavor != null) {
/*  963 */       if (!isDocFlavorSupported(paramDocFlavor)) {
/*  964 */         throw new IllegalArgumentException(paramDocFlavor + " is an unsupported flavor");
/*      */       }
/*  966 */       if (isAutoSense(paramDocFlavor)) {
/*  967 */         return false;
/*      */       }
/*      */     } 
/*  970 */     Class<? extends Attribute> clazz = paramAttribute.getCategory();
/*  971 */     if (!isAttributeCategorySupported(clazz)) {
/*  972 */       return false;
/*      */     }
/*  974 */     if (paramAttribute.getCategory() == Chromaticity.class) {
/*  975 */       if (paramDocFlavor == null || isServiceFormattedFlavor(paramDocFlavor)) {
/*  976 */         return (paramAttribute == Chromaticity.COLOR);
/*      */       }
/*  978 */       return false;
/*      */     } 
/*      */     
/*  981 */     if (paramAttribute.getCategory() == Copies.class)
/*  982 */       return ((paramDocFlavor == null || (
/*  983 */         !paramDocFlavor.equals(DocFlavor.INPUT_STREAM.POSTSCRIPT) && 
/*  984 */         !paramDocFlavor.equals(DocFlavor.URL.POSTSCRIPT) && 
/*  985 */         !paramDocFlavor.equals(DocFlavor.BYTE_ARRAY.POSTSCRIPT))) && 
/*  986 */         isSupportedCopies((Copies)paramAttribute)); 
/*  987 */     if (paramAttribute.getCategory() == Destination.class) {
/*  988 */       URI uRI = ((Destination)paramAttribute).getURI();
/*  989 */       if ("file".equals(uRI.getScheme()) && 
/*  990 */         !uRI.getSchemeSpecificPart().equals("")) {
/*  991 */         return true;
/*      */       }
/*  993 */       return false;
/*      */     } 
/*  995 */     if (paramAttribute.getCategory() == Media.class) {
/*  996 */       if (paramAttribute instanceof MediaSizeName) {
/*  997 */         return isSupportedMedia((MediaSizeName)paramAttribute);
/*      */       }
/*  999 */       return false;
/*      */     } 
/* 1001 */     if (paramAttribute.getCategory() == OrientationRequested.class) {
/* 1002 */       if (paramAttribute == OrientationRequested.REVERSE_PORTRAIT || (paramDocFlavor != null && 
/*      */         
/* 1004 */         !isServiceFormattedFlavor(paramDocFlavor))) {
/* 1005 */         return false;
/*      */       }
/* 1007 */     } else if (paramAttribute.getCategory() == PageRanges.class) {
/* 1008 */       if (paramDocFlavor != null && 
/* 1009 */         !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) && 
/* 1010 */         !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
/* 1011 */         return false;
/*      */       }
/* 1013 */     } else if (paramAttribute.getCategory() == SheetCollate.class) {
/* 1014 */       if (paramDocFlavor != null && 
/* 1015 */         !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) && 
/* 1016 */         !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
/* 1017 */         return false;
/*      */       }
/* 1019 */     } else if (paramAttribute.getCategory() == Sides.class && 
/* 1020 */       paramDocFlavor != null && 
/* 1021 */       !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) && 
/* 1022 */       !paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) {
/* 1023 */       return false;
/*      */     } 
/*      */     
/* 1026 */     return true; } public Class[] getSupportedAttributeCategories() { int i = otherAttrCats.length; Class[] arrayOfClass = new Class[i]; System.arraycopy(otherAttrCats, 0, arrayOfClass, 0, otherAttrCats.length); return arrayOfClass; }
/*      */   public boolean isAttributeCategorySupported(Class<? extends Attribute> paramClass) { if (paramClass == null)
/*      */       throw new NullPointerException("null category");  if (!Attribute.class.isAssignableFrom(paramClass))
/*      */       throw new IllegalArgumentException(paramClass + " is not an Attribute");  for (byte b = 0; b < otherAttrCats.length; b++) { if (paramClass == otherAttrCats[b])
/*      */         return true;  }
/*      */      return false; }
/* 1032 */   public AttributeSet getUnsupportedAttributes(DocFlavor paramDocFlavor, AttributeSet paramAttributeSet) { if (paramDocFlavor != null && !isDocFlavorSupported(paramDocFlavor)) {
/* 1033 */       throw new IllegalArgumentException("flavor " + paramDocFlavor + "is not supported");
/*      */     }
/*      */ 
/*      */     
/* 1037 */     if (paramAttributeSet == null) {
/* 1038 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1042 */     HashAttributeSet hashAttributeSet = new HashAttributeSet();
/* 1043 */     Attribute[] arrayOfAttribute = paramAttributeSet.toArray();
/* 1044 */     for (byte b = 0; b < arrayOfAttribute.length; b++) {
/*      */       try {
/* 1046 */         Attribute attribute = arrayOfAttribute[b];
/* 1047 */         if (!isAttributeCategorySupported(attribute.getCategory())) {
/* 1048 */           hashAttributeSet.add(attribute);
/* 1049 */         } else if (!isAttributeValueSupported(attribute, paramDocFlavor, paramAttributeSet)) {
/*      */           
/* 1051 */           hashAttributeSet.add(attribute);
/*      */         } 
/* 1053 */       } catch (ClassCastException classCastException) {}
/*      */     } 
/*      */     
/* 1056 */     if (hashAttributeSet.isEmpty()) {
/* 1057 */       return null;
/*      */     }
/* 1059 */     return hashAttributeSet; }
/*      */   public Object getDefaultAttributeValue(Class<? extends Attribute> paramClass) { if (paramClass == null) throw new NullPointerException("null category");  if (!Attribute.class.isAssignableFrom(paramClass)) throw new IllegalArgumentException(paramClass + " is not an Attribute");  if (!isAttributeCategorySupported(paramClass)) return null;  if (paramClass == Copies.class) return new Copies(1);  if (paramClass == Chromaticity.class) return Chromaticity.COLOR;  if (paramClass == Destination.class) try { return new Destination((new File("out.ps")).toURI()); } catch (SecurityException securityException) { try { return new Destination(new URI("file:out.ps")); } catch (URISyntaxException uRISyntaxException) { return null; }  }   if (paramClass == Fidelity.class) return Fidelity.FIDELITY_FALSE;  if (paramClass == JobName.class) return new JobName("Java Printing", null);  if (paramClass == JobSheets.class) return JobSheets.STANDARD;  if (paramClass == Media.class) { String str = Locale.getDefault().getCountry(); if (str != null && (str.equals("") || str.equals(Locale.US.getCountry()) || str.equals(Locale.CANADA.getCountry()))) return MediaSizeName.NA_LETTER;  return MediaSizeName.ISO_A4; }  if (paramClass == MediaPrintableArea.class) { float f1, f2; String str = Locale.getDefault().getCountry(); if (str != null && (str.equals("") || str.equals(Locale.US.getCountry()) || str.equals(Locale.CANADA.getCountry()))) { f1 = MediaSize.NA.LETTER.getX(25400) - 0.5F; f2 = MediaSize.NA.LETTER.getY(25400) - 0.5F; } else { f1 = MediaSize.ISO.A4.getX(25400) - 0.5F; f2 = MediaSize.ISO.A4.getY(25400) - 0.5F; }  return new MediaPrintableArea(0.25F, 0.25F, f1, f2, 25400); }  if (paramClass == OrientationRequested.class)
/*      */       return OrientationRequested.PORTRAIT;  if (paramClass == PageRanges.class)
/*      */       return new PageRanges(1, 2147483647);  if (paramClass == RequestingUserName.class) { String str = ""; try { str = System.getProperty("user.name", ""); } catch (SecurityException securityException) {} return new RequestingUserName(str, null); }  if (paramClass == SheetCollate.class)
/*      */       return SheetCollate.UNCOLLATED;  if (paramClass == Sides.class)
/* 1064 */       return Sides.ONE_SIDED;  return null; } public ServiceUIFactory getServiceUIFactory() { return null; }
/*      */   private boolean isAutoSense(DocFlavor paramDocFlavor) { if (paramDocFlavor.equals(DocFlavor.BYTE_ARRAY.AUTOSENSE) || paramDocFlavor.equals(DocFlavor.INPUT_STREAM.AUTOSENSE) || paramDocFlavor.equals(DocFlavor.URL.AUTOSENSE)) return true;  return false; }
/*      */   public Object getSupportedAttributeValues(Class<? extends Attribute> paramClass, DocFlavor paramDocFlavor, AttributeSet paramAttributeSet) { if (paramClass == null) throw new NullPointerException("null category");  if (!Attribute.class.isAssignableFrom(paramClass)) throw new IllegalArgumentException(paramClass + " does not implement Attribute");  if (paramDocFlavor != null) { if (!isDocFlavorSupported(paramDocFlavor)) throw new IllegalArgumentException(paramDocFlavor + " is an unsupported flavor");  if (isAutoSense(paramDocFlavor)) return null;  }  if (!isAttributeCategorySupported(paramClass)) return null;  if (paramClass == Chromaticity.class) { if (paramDocFlavor == null || isServiceFormattedFlavor(paramDocFlavor)) { Chromaticity[] arrayOfChromaticity = new Chromaticity[1]; arrayOfChromaticity[0] = Chromaticity.COLOR; return arrayOfChromaticity; }  return null; }  if (paramClass == Destination.class) try { return new Destination((new File("out.ps")).toURI()); } catch (SecurityException securityException) { try { return new Destination(new URI("file:out.ps")); } catch (URISyntaxException uRISyntaxException) { return null; }  }   if (paramClass == JobName.class) return new JobName("Java Printing", null);  if (paramClass == JobSheets.class) { JobSheets[] arrayOfJobSheets = new JobSheets[2]; arrayOfJobSheets[0] = JobSheets.NONE; arrayOfJobSheets[1] = JobSheets.STANDARD; return arrayOfJobSheets; }  if (paramClass == RequestingUserName.class) { String str = ""; try { str = System.getProperty("user.name", ""); } catch (SecurityException securityException) {} return new RequestingUserName(str, null); }  if (paramClass == OrientationRequested.class) { if (paramDocFlavor == null || isServiceFormattedFlavor(paramDocFlavor)) { OrientationRequested[] arrayOfOrientationRequested = new OrientationRequested[3]; arrayOfOrientationRequested[0] = OrientationRequested.PORTRAIT; arrayOfOrientationRequested[1] = OrientationRequested.LANDSCAPE; arrayOfOrientationRequested[2] = OrientationRequested.REVERSE_LANDSCAPE; return arrayOfOrientationRequested; }  return null; }  if (paramClass == Copies.class || paramClass == CopiesSupported.class) { if (paramDocFlavor == null || (!paramDocFlavor.equals(DocFlavor.INPUT_STREAM.POSTSCRIPT) && !paramDocFlavor.equals(DocFlavor.URL.POSTSCRIPT) && !paramDocFlavor.equals(DocFlavor.BYTE_ARRAY.POSTSCRIPT))) return new CopiesSupported(1, MAXCOPIES);  return null; }  if (paramClass == Media.class) { Media[] arrayOfMedia = new Media[mediaSizes.length]; System.arraycopy(mediaSizes, 0, arrayOfMedia, 0, mediaSizes.length); return arrayOfMedia; }  if (paramClass == Fidelity.class) { Fidelity[] arrayOfFidelity = new Fidelity[2]; arrayOfFidelity[0] = Fidelity.FIDELITY_FALSE; arrayOfFidelity[1] = Fidelity.FIDELITY_TRUE; return arrayOfFidelity; }  if (paramClass == MediaPrintableArea.class) { if (paramAttributeSet == null)
/*      */         return getAllPrintableAreas();  MediaSize mediaSize = (MediaSize)paramAttributeSet.get(MediaSize.class); Media media = (Media)paramAttributeSet.get(Media.class); MediaPrintableArea[] arrayOfMediaPrintableArea = new MediaPrintableArea[1]; if (mediaSize == null)
/* 1068 */         if (media instanceof MediaSizeName) { MediaSizeName mediaSizeName = (MediaSizeName)media; mediaSize = MediaSize.getMediaSizeForName(mediaSizeName); if (mediaSize == null) { media = (Media)getDefaultAttributeValue((Class)Media.class); if (media instanceof MediaSizeName) { mediaSizeName = (MediaSizeName)media; mediaSize = MediaSize.getMediaSizeForName(mediaSizeName); }  if (mediaSize == null) { arrayOfMediaPrintableArea[0] = new MediaPrintableArea(0.25F, 0.25F, 8.0F, 10.5F, 25400); return arrayOfMediaPrintableArea; }  }  } else { return getAllPrintableAreas(); }   assert mediaSize != null; arrayOfMediaPrintableArea[0] = new MediaPrintableArea(0.25F, 0.25F, mediaSize.getX(25400) - 0.5F, mediaSize.getY(25400) - 0.5F, 25400); return arrayOfMediaPrintableArea; }  if (paramClass == PageRanges.class) { if (paramDocFlavor == null || paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) { PageRanges[] arrayOfPageRanges = new PageRanges[1]; arrayOfPageRanges[0] = new PageRanges(1, 2147483647); return arrayOfPageRanges; }  return null; }  if (paramClass == SheetCollate.class) { if (paramDocFlavor == null || paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) { SheetCollate[] arrayOfSheetCollate = new SheetCollate[2]; arrayOfSheetCollate[0] = SheetCollate.UNCOLLATED; arrayOfSheetCollate[1] = SheetCollate.COLLATED; return arrayOfSheetCollate; }  return null; }  if (paramClass == Sides.class) { if (paramDocFlavor == null || paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE) || paramDocFlavor.equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE)) { Sides[] arrayOfSides = new Sides[3]; arrayOfSides[0] = Sides.ONE_SIDED; arrayOfSides[1] = Sides.TWO_SIDED_LONG_EDGE; arrayOfSides[2] = Sides.TWO_SIDED_SHORT_EDGE; return arrayOfSides; }  return null; }  return null; } public String toString() { return "Unix Printer : " + getName(); }
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/* 1072 */     return (paramObject == this || (paramObject instanceof UnixPrintService && ((UnixPrintService)paramObject)
/*      */       
/* 1074 */       .getName().equals(getName())));
/*      */   }
/*      */   
/*      */   public int hashCode() {
/* 1078 */     return getClass().hashCode() + getName().hashCode();
/*      */   }
/*      */   
/*      */   public boolean usesClass(Class<PSPrinterJob> paramClass) {
/* 1082 */     return (paramClass == PSPrinterJob.class);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/UnixPrintService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */