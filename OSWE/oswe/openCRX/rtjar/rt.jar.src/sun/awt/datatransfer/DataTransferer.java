/*      */ package sun.awt.datatransfer;
/*      */ 
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Image;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.datatransfer.DataFlavor;
/*      */ import java.awt.datatransfer.FlavorMap;
/*      */ import java.awt.datatransfer.FlavorTable;
/*      */ import java.awt.datatransfer.Transferable;
/*      */ import java.awt.datatransfer.UnsupportedFlavorException;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.RenderedImage;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FilePermission;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.SequenceInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.io.StringReader;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.net.URI;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.CharBuffer;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.CharsetEncoder;
/*      */ import java.nio.charset.IllegalCharsetNameException;
/*      */ import java.nio.charset.UnsupportedCharsetException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ import java.util.Stack;
/*      */ import java.util.TreeMap;
/*      */ import java.util.TreeSet;
/*      */ import javax.imageio.ImageIO;
/*      */ import javax.imageio.ImageReadParam;
/*      */ import javax.imageio.ImageReader;
/*      */ import javax.imageio.ImageTypeSpecifier;
/*      */ import javax.imageio.ImageWriter;
/*      */ import javax.imageio.spi.ImageWriterSpi;
/*      */ import javax.imageio.stream.ImageInputStream;
/*      */ import javax.imageio.stream.ImageOutputStream;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.ComponentFactory;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.awt.image.ImageRepresentation;
/*      */ import sun.awt.image.ToolkitImage;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class DataTransferer
/*      */ {
/*      */   public static final DataFlavor plainTextStringFlavor;
/*      */   public static final DataFlavor javaTextEncodingFlavor;
/*      */   private static final Map textMIMESubtypeCharsetSupport;
/*      */   private static String defaultEncoding;
/*      */   
/*      */   private static class StandardEncodingsHolder
/*      */   {
/*  166 */     private static final SortedSet<String> standardEncodings = load();
/*      */     
/*      */     private static SortedSet<String> load() {
/*  169 */       DataTransferer.CharsetComparator charsetComparator = new DataTransferer.CharsetComparator(false);
/*      */       
/*  171 */       TreeSet<String> treeSet = new TreeSet(charsetComparator);
/*  172 */       treeSet.add("US-ASCII");
/*  173 */       treeSet.add("ISO-8859-1");
/*  174 */       treeSet.add("UTF-8");
/*  175 */       treeSet.add("UTF-16BE");
/*  176 */       treeSet.add("UTF-16LE");
/*  177 */       treeSet.add("UTF-16");
/*  178 */       treeSet.add(DataTransferer.getDefaultTextCharset());
/*  179 */       return Collections.unmodifiableSortedSet(treeSet);
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
/*      */ 
/*      */   
/*  202 */   private static final Set textNatives = Collections.synchronizedSet(new HashSet());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  208 */   private static final Map nativeCharsets = Collections.synchronizedMap(new HashMap<>());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  214 */   private static final Map nativeEOLNs = Collections.synchronizedMap(new HashMap<>());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  220 */   private static final Map nativeTerminators = Collections.synchronizedMap(new HashMap<>());
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String DATA_CONVERTER_KEY = "DATA_CONVERTER_KEY";
/*      */ 
/*      */ 
/*      */   
/*      */   private static DataTransferer transferer;
/*      */ 
/*      */ 
/*      */   
/*  233 */   private static final PlatformLogger dtLog = PlatformLogger.getLogger("sun.awt.datatransfer.DataTransfer");
/*      */   private static final String[] DEPLOYMENT_CACHE_PROPERTIES;
/*      */   private static final ArrayList<File> deploymentCacheDirectoryList;
/*  236 */   public static synchronized DataTransferer getInstance() { return ((ComponentFactory)Toolkit.getDefaultToolkit()).getDataTransferer(); } public static String canonicalName(String paramString) { if (paramString == null) return null;  try { return Charset.forName(paramString).name(); } catch (IllegalCharsetNameException illegalCharsetNameException) { return paramString; } catch (UnsupportedCharsetException unsupportedCharsetException) { return paramString; }  } public static String getTextCharset(DataFlavor paramDataFlavor) { if (!isFlavorCharsetTextType(paramDataFlavor)) return null;  String str = paramDataFlavor.getParameter("charset"); return (str != null) ? str : getDefaultTextCharset(); } public static String getDefaultTextCharset() { if (defaultEncoding != null) return defaultEncoding;  return defaultEncoding = Charset.defaultCharset().name(); } public static boolean doesSubtypeSupportCharset(DataFlavor paramDataFlavor) { if (dtLog.isLoggable(PlatformLogger.Level.FINE) && !"text".equals(paramDataFlavor.getPrimaryType())) dtLog.fine("Assertion (\"text\".equals(flavor.getPrimaryType())) failed");  String str = paramDataFlavor.getSubType(); if (str == null) return false;  Object object = textMIMESubtypeCharsetSupport.get(str); if (object != null) return (object == Boolean.TRUE);  boolean bool = (paramDataFlavor.getParameter("charset") != null) ? true : false; textMIMESubtypeCharsetSupport.put(str, bool ? Boolean.TRUE : Boolean.FALSE); return bool; } public static boolean doesSubtypeSupportCharset(String paramString1, String paramString2) { Object object = textMIMESubtypeCharsetSupport.get(paramString1); if (object != null) return (object == Boolean.TRUE);  boolean bool = (paramString2 != null) ? true : false; textMIMESubtypeCharsetSupport.put(paramString1, bool ? Boolean.TRUE : Boolean.FALSE); return bool; } public static boolean isFlavorCharsetTextType(DataFlavor paramDataFlavor) { if (DataFlavor.stringFlavor.equals(paramDataFlavor)) return true;  if (!"text".equals(paramDataFlavor.getPrimaryType()) || !doesSubtypeSupportCharset(paramDataFlavor)) return false;  Class<?> clazz = paramDataFlavor.getRepresentationClass(); if (paramDataFlavor.isRepresentationClassReader() || String.class.equals(clazz) || paramDataFlavor.isRepresentationClassCharBuffer() || char[].class.equals(clazz)) return true;  if (!paramDataFlavor.isRepresentationClassInputStream() && !paramDataFlavor.isRepresentationClassByteBuffer() && !byte[].class.equals(clazz)) return false;  String str = paramDataFlavor.getParameter("charset"); return (str != null) ? isEncodingSupported(str) : true; } public static boolean isFlavorNoncharsetTextType(DataFlavor paramDataFlavor) { if (!"text".equals(paramDataFlavor.getPrimaryType()) || doesSubtypeSupportCharset(paramDataFlavor)) return false;  return (paramDataFlavor.isRepresentationClassInputStream() || paramDataFlavor.isRepresentationClassByteBuffer() || byte[].class.equals(paramDataFlavor.getRepresentationClass())); } public static boolean isEncodingSupported(String paramString) { if (paramString == null) return false;  try { return Charset.isSupported(paramString); } catch (IllegalCharsetNameException illegalCharsetNameException) { return false; }  } public static boolean isRemote(Class<?> paramClass) { return RMI.isRemote(paramClass); } static { DataFlavor dataFlavor1 = null;
/*      */     try {
/*  238 */       dataFlavor1 = new DataFlavor("text/plain;charset=Unicode;class=java.lang.String");
/*      */     }
/*  240 */     catch (ClassNotFoundException classNotFoundException) {}
/*      */     
/*  242 */     plainTextStringFlavor = dataFlavor1;
/*      */     
/*  244 */     DataFlavor dataFlavor2 = null;
/*      */     try {
/*  246 */       dataFlavor2 = new DataFlavor("application/x-java-text-encoding;class=\"[B\"");
/*      */     }
/*  248 */     catch (ClassNotFoundException classNotFoundException) {}
/*      */     
/*  250 */     javaTextEncodingFlavor = dataFlavor2;
/*      */     
/*  252 */     HashMap<Object, Object> hashMap = new HashMap<>(17);
/*  253 */     hashMap.put("sgml", Boolean.TRUE);
/*  254 */     hashMap.put("xml", Boolean.TRUE);
/*  255 */     hashMap.put("html", Boolean.TRUE);
/*  256 */     hashMap.put("enriched", Boolean.TRUE);
/*  257 */     hashMap.put("richtext", Boolean.TRUE);
/*  258 */     hashMap.put("uri-list", Boolean.TRUE);
/*  259 */     hashMap.put("directory", Boolean.TRUE);
/*  260 */     hashMap.put("css", Boolean.TRUE);
/*  261 */     hashMap.put("calendar", Boolean.TRUE);
/*  262 */     hashMap.put("plain", Boolean.TRUE);
/*  263 */     hashMap.put("rtf", Boolean.FALSE);
/*  264 */     hashMap.put("tab-separated-values", Boolean.FALSE);
/*  265 */     hashMap.put("t140", Boolean.FALSE);
/*  266 */     hashMap.put("rfc822-headers", Boolean.FALSE);
/*  267 */     hashMap.put("parityfec", Boolean.FALSE);
/*  268 */     textMIMESubtypeCharsetSupport = Collections.synchronizedMap(hashMap);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1471 */     DEPLOYMENT_CACHE_PROPERTIES = new String[] { "deployment.system.cachedir", "deployment.user.cachedir", "deployment.javaws.cachedir", "deployment.javapi.cachedir" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1478 */     deploymentCacheDirectoryList = new ArrayList<>(); }
/*      */   public static Set<String> standardEncodings() { return StandardEncodingsHolder.standardEncodings; }
/*      */   public static FlavorTable adaptFlavorMap(final FlavorMap map) { if (map instanceof FlavorTable) return (FlavorTable)map;  return new FlavorTable() {
/*      */         public Map getNativesForFlavors(DataFlavor[] param1ArrayOfDataFlavor) { return map.getNativesForFlavors(param1ArrayOfDataFlavor); }
/*      */         public Map getFlavorsForNatives(String[] param1ArrayOfString) { return map.getFlavorsForNatives(param1ArrayOfString); } public List getNativesForFlavor(DataFlavor param1DataFlavor) { Map map = getNativesForFlavors(new DataFlavor[] { param1DataFlavor }); String str = (String)map.get(param1DataFlavor); if (str != null) { ArrayList<String> arrayList = new ArrayList(1); arrayList.add(str); return arrayList; }  return Collections.EMPTY_LIST; } public List getFlavorsForNative(String param1String) { Map map = getFlavorsForNatives(new String[] { param1String }); DataFlavor dataFlavor = (DataFlavor)map.get(param1String); if (dataFlavor != null) { ArrayList<DataFlavor> arrayList = new ArrayList(1); arrayList.add(dataFlavor); return arrayList; }  return Collections.EMPTY_LIST; }
/* 1483 */       }; } public void registerTextFlavorProperties(String paramString1, String paramString2, String paramString3, String paramString4) { Long long_ = getFormatForNativeAsLong(paramString1); textNatives.add(long_); nativeCharsets.put(long_, (paramString2 != null && paramString2.length() != 0) ? paramString2 : getDefaultTextCharset()); if (paramString3 != null && paramString3.length() != 0 && !paramString3.equals("\n")) nativeEOLNs.put(long_, paramString3);  if (paramString4 != null && paramString4.length() != 0) { Integer integer = Integer.valueOf(paramString4); if (integer.intValue() > 0) nativeTerminators.put(long_, integer);  }  } protected boolean isTextFormat(long paramLong) { return textNatives.contains(Long.valueOf(paramLong)); } protected String getCharsetForTextFormat(Long paramLong) { return (String)nativeCharsets.get(paramLong); } protected boolean isURIListFormat(long paramLong) { return false; } public SortedMap<Long, DataFlavor> getFormatsForTransferable(Transferable paramTransferable, FlavorTable paramFlavorTable) { DataFlavor[] arrayOfDataFlavor = paramTransferable.getTransferDataFlavors(); if (arrayOfDataFlavor == null) return new TreeMap<>();  return getFormatsForFlavors(arrayOfDataFlavor, paramFlavorTable); } public SortedMap getFormatsForFlavor(DataFlavor paramDataFlavor, FlavorTable paramFlavorTable) { return getFormatsForFlavors(new DataFlavor[] { paramDataFlavor }, paramFlavorTable); } public SortedMap<Long, DataFlavor> getFormatsForFlavors(DataFlavor[] paramArrayOfDataFlavor, FlavorTable paramFlavorTable) { HashMap<Object, Object> hashMap1 = new HashMap<>(paramArrayOfDataFlavor.length); HashMap<Object, Object> hashMap2 = new HashMap<>(paramArrayOfDataFlavor.length); HashMap<Object, Object> hashMap3 = new HashMap<>(paramArrayOfDataFlavor.length); HashMap<Object, Object> hashMap4 = new HashMap<>(paramArrayOfDataFlavor.length); int i = 0; for (int j = paramArrayOfDataFlavor.length - 1; j >= 0; j--) { DataFlavor dataFlavor = paramArrayOfDataFlavor[j]; if (dataFlavor != null) if (dataFlavor.isFlavorTextType() || dataFlavor.isFlavorJavaFileListType() || DataFlavor.imageFlavor.equals(dataFlavor) || dataFlavor.isRepresentationClassSerializable() || dataFlavor.isRepresentationClassInputStream() || dataFlavor.isRepresentationClassRemote()) { List<String> list = paramFlavorTable.getNativesForFlavor(dataFlavor); i += list.size(); for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) { Long long_ = getFormatForNativeAsLong(iterator.next()); Integer integer = Integer.valueOf(i--); hashMap1.put(long_, dataFlavor); hashMap3.put(long_, integer); if (("text".equals(dataFlavor.getPrimaryType()) && "plain".equals(dataFlavor.getSubType())) || dataFlavor.equals(DataFlavor.stringFlavor)) { hashMap2.put(long_, dataFlavor); hashMap4.put(long_, integer); }  }  i += list.size(); }   }  hashMap1.putAll(hashMap2); hashMap3.putAll(hashMap4); IndexOrderComparator indexOrderComparator = new IndexOrderComparator(hashMap3, false); TreeMap<Object, Object> treeMap = new TreeMap<>(indexOrderComparator); treeMap.putAll(hashMap1); return (SortedMap)treeMap; } private static boolean isFileInWebstartedCache(File paramFile) { if (deploymentCacheDirectoryList.isEmpty()) {
/* 1484 */       for (String str1 : DEPLOYMENT_CACHE_PROPERTIES) {
/* 1485 */         String str2 = System.getProperty(str1);
/* 1486 */         if (str2 != null) {
/*      */           try {
/* 1488 */             File file = (new File(str2)).getCanonicalFile();
/* 1489 */             if (file != null) {
/* 1490 */               deploymentCacheDirectoryList.add(file);
/*      */             }
/* 1492 */           } catch (IOException iOException) {}
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1497 */     for (File file1 : deploymentCacheDirectoryList) {
/* 1498 */       for (File file2 = paramFile; file2 != null; file2 = file2.getParentFile()) {
/* 1499 */         if (file2.equals(file1)) {
/* 1500 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1505 */     return false; } public long[] getFormatsForTransferableAsArray(Transferable paramTransferable, FlavorTable paramFlavorTable) { return keysToLongArray(getFormatsForTransferable(paramTransferable, paramFlavorTable)); }
/*      */   public long[] getFormatsForFlavorAsArray(DataFlavor paramDataFlavor, FlavorTable paramFlavorTable) { return keysToLongArray(getFormatsForFlavor(paramDataFlavor, paramFlavorTable)); }
/*      */   public long[] getFormatsForFlavorsAsArray(DataFlavor[] paramArrayOfDataFlavor, FlavorTable paramFlavorTable) { return keysToLongArray(getFormatsForFlavors(paramArrayOfDataFlavor, paramFlavorTable)); }
/*      */   public Map getFlavorsForFormat(long paramLong, FlavorTable paramFlavorTable) { return getFlavorsForFormats(new long[] { paramLong }, paramFlavorTable); }
/*      */   public Map getFlavorsForFormats(long[] paramArrayOflong, FlavorTable paramFlavorTable) { HashMap<Object, Object> hashMap = new HashMap<>(paramArrayOflong.length); HashSet<Object> hashSet = new HashSet(paramArrayOflong.length); HashSet<DataFlavor> hashSet1 = new HashSet(paramArrayOflong.length); for (byte b = 0; b < paramArrayOflong.length; b++) { long l = paramArrayOflong[b]; String str = getNativeForFormat(l); List<DataFlavor> list = paramFlavorTable.getFlavorsForNative(str); for (DataFlavor dataFlavor : list) { if (dataFlavor.isFlavorTextType() || dataFlavor.isFlavorJavaFileListType() || DataFlavor.imageFlavor.equals(dataFlavor) || dataFlavor.isRepresentationClassSerializable() || dataFlavor.isRepresentationClassInputStream() || dataFlavor.isRepresentationClassRemote()) { Long long_ = Long.valueOf(l); Object object = createMapping(long_, dataFlavor); hashMap.put(dataFlavor, long_); hashSet.add(object); hashSet1.add(dataFlavor); }  }  }  Iterator<DataFlavor> iterator = hashSet1.iterator(); while (iterator.hasNext()) { DataFlavor dataFlavor = iterator.next(); List<String> list = paramFlavorTable.getNativesForFlavor(dataFlavor); Iterator<String> iterator1 = list.iterator(); while (iterator1.hasNext()) { Long long_ = getFormatForNativeAsLong(iterator1.next()); Object object = createMapping(long_, dataFlavor); if (hashSet.contains(object)) hashMap.put(dataFlavor, long_);  }  }  return hashMap; }
/*      */   public Set getFlavorsForFormatsAsSet(long[] paramArrayOflong, FlavorTable paramFlavorTable) { HashSet<DataFlavor> hashSet = new HashSet(paramArrayOflong.length); for (byte b = 0; b < paramArrayOflong.length; b++) { String str = getNativeForFormat(paramArrayOflong[b]); List<DataFlavor> list = paramFlavorTable.getFlavorsForNative(str); for (DataFlavor dataFlavor : list) { if (dataFlavor.isFlavorTextType() || dataFlavor.isFlavorJavaFileListType() || DataFlavor.imageFlavor.equals(dataFlavor) || dataFlavor.isRepresentationClassSerializable() || dataFlavor.isRepresentationClassInputStream() || dataFlavor.isRepresentationClassRemote()) hashSet.add(dataFlavor);  }  }  return hashSet; }
/*      */   public DataFlavor[] getFlavorsForFormatAsArray(long paramLong, FlavorTable paramFlavorTable) { return getFlavorsForFormatsAsArray(new long[] { paramLong }, paramFlavorTable); }
/*      */   public DataFlavor[] getFlavorsForFormatsAsArray(long[] paramArrayOflong, FlavorTable paramFlavorTable) { return setToSortedDataFlavorArray(getFlavorsForFormatsAsSet(paramArrayOflong, paramFlavorTable)); }
/*      */   private static Object createMapping(Object paramObject1, Object paramObject2) { return Arrays.asList(new Object[] { paramObject1, paramObject2 }); }
/* 1514 */   public Object translateBytes(byte[] paramArrayOfbyte, DataFlavor paramDataFlavor, long paramLong, Transferable paramTransferable) throws IOException { Object object; List<File> list = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1519 */     if (isFileFormat(paramLong)) {
/* 1520 */       if (!DataFlavor.javaFileListFlavor.equals(paramDataFlavor)) {
/* 1521 */         throw new IOException("data translation failed");
/*      */       }
/* 1523 */       String[] arrayOfString = dragQueryFile(paramArrayOfbyte);
/* 1524 */       if (arrayOfString == null) {
/* 1525 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1529 */       File[] arrayOfFile = new File[arrayOfString.length];
/* 1530 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 1531 */         arrayOfFile[b] = new File(arrayOfString[b]);
/*      */       }
/*      */ 
/*      */       
/* 1535 */       list = Arrays.asList(arrayOfFile);
/*      */ 
/*      */     
/*      */     }
/* 1539 */     else if (isURIListFormat(paramLong) && DataFlavor.javaFileListFlavor
/* 1540 */       .equals(paramDataFlavor)) {
/*      */       
/* 1542 */       try (ByteArrayInputStream null = new ByteArrayInputStream(paramArrayOfbyte))
/*      */       {
/* 1544 */         URI[] arrayOfURI = dragQueryURIs(byteArrayInputStream, paramLong, paramTransferable);
/* 1545 */         if (arrayOfURI == null) {
/* 1546 */           return null;
/*      */         }
/* 1548 */         ArrayList<File> arrayList = new ArrayList();
/* 1549 */         for (URI uRI : arrayOfURI) {
/*      */           try {
/* 1551 */             arrayList.add(new File(uRI));
/* 1552 */           } catch (IllegalArgumentException illegalArgumentException) {}
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1558 */         list = arrayList;
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1563 */     else if (String.class.equals(paramDataFlavor.getRepresentationClass()) && 
/* 1564 */       isFlavorCharsetTextType(paramDataFlavor) && isTextFormat(paramLong)) {
/*      */       
/* 1566 */       String str = translateBytesToString(paramArrayOfbyte, paramLong, paramTransferable);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1571 */     else if (paramDataFlavor.isRepresentationClassReader()) {
/* 1572 */       try (ByteArrayInputStream null = new ByteArrayInputStream(paramArrayOfbyte)) {
/* 1573 */         object = translateStream(byteArrayInputStream, paramDataFlavor, paramLong, paramTransferable);
/*      */       }
/*      */     
/*      */     }
/* 1577 */     else if (paramDataFlavor.isRepresentationClassCharBuffer()) {
/* 1578 */       if (!isFlavorCharsetTextType(paramDataFlavor) || !isTextFormat(paramLong)) {
/* 1579 */         throw new IOException("cannot transfer non-text data as CharBuffer");
/*      */       }
/*      */ 
/*      */       
/* 1583 */       CharBuffer charBuffer = CharBuffer.wrap(
/* 1584 */           translateBytesToString(paramArrayOfbyte, paramLong, paramTransferable));
/*      */       
/* 1586 */       object = constructFlavoredObject(charBuffer, paramDataFlavor, CharBuffer.class);
/*      */ 
/*      */     
/*      */     }
/* 1590 */     else if (char[].class.equals(paramDataFlavor.getRepresentationClass())) {
/* 1591 */       if (!isFlavorCharsetTextType(paramDataFlavor) || !isTextFormat(paramLong)) {
/* 1592 */         throw new IOException("cannot transfer non-text data as char array");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1597 */       object = translateBytesToString(paramArrayOfbyte, paramLong, paramTransferable).toCharArray();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1603 */     else if (paramDataFlavor.isRepresentationClassByteBuffer()) {
/* 1604 */       if (isFlavorCharsetTextType(paramDataFlavor) && isTextFormat(paramLong))
/*      */       {
/* 1606 */         paramArrayOfbyte = translateBytesToString(paramArrayOfbyte, paramLong, paramTransferable).getBytes(
/* 1607 */             getTextCharset(paramDataFlavor));
/*      */       }
/*      */ 
/*      */       
/* 1611 */       ByteBuffer byteBuffer = ByteBuffer.wrap(paramArrayOfbyte);
/* 1612 */       object = constructFlavoredObject(byteBuffer, paramDataFlavor, ByteBuffer.class);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1618 */     else if (byte[].class.equals(paramDataFlavor.getRepresentationClass())) {
/* 1619 */       if (isFlavorCharsetTextType(paramDataFlavor) && isTextFormat(paramLong)) {
/*      */ 
/*      */         
/* 1622 */         object = translateBytesToString(paramArrayOfbyte, paramLong, paramTransferable).getBytes(getTextCharset(paramDataFlavor));
/*      */       } else {
/* 1624 */         object = paramArrayOfbyte;
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/* 1631 */     else if (paramDataFlavor.isRepresentationClassInputStream()) {
/*      */       
/* 1633 */       try (ByteArrayInputStream null = new ByteArrayInputStream(paramArrayOfbyte)) {
/* 1634 */         object = translateStream(byteArrayInputStream, paramDataFlavor, paramLong, paramTransferable);
/*      */       }
/*      */     
/* 1637 */     } else if (paramDataFlavor.isRepresentationClassRemote()) {
/* 1638 */       try(ByteArrayInputStream null = new ByteArrayInputStream(paramArrayOfbyte); 
/* 1639 */           ObjectInputStream null = new ObjectInputStream(byteArrayInputStream)) {
/*      */         
/* 1641 */         object = RMI.getMarshalledObject(objectInputStream.readObject());
/* 1642 */       } catch (Exception exception) {
/* 1643 */         throw new IOException(exception.getMessage());
/*      */       }
/*      */     
/*      */     }
/* 1647 */     else if (paramDataFlavor.isRepresentationClassSerializable()) {
/*      */       
/* 1649 */       try (ByteArrayInputStream null = new ByteArrayInputStream(paramArrayOfbyte)) {
/* 1650 */         object = translateStream(byteArrayInputStream, paramDataFlavor, paramLong, paramTransferable);
/*      */       }
/*      */     
/*      */     }
/* 1654 */     else if (DataFlavor.imageFlavor.equals(paramDataFlavor)) {
/* 1655 */       if (!isImageFormat(paramLong)) {
/* 1656 */         throw new IOException("data translation failed");
/*      */       }
/*      */       
/* 1659 */       object = platformImageBytesToImage(paramArrayOfbyte, paramLong);
/*      */     } 
/*      */     
/* 1662 */     if (object == null) {
/* 1663 */       throw new IOException("data translation failed");
/*      */     }
/*      */     
/* 1666 */     return object; } private String getBestCharsetForTextFormat(Long paramLong, Transferable paramTransferable) throws IOException { String str = null; if (paramTransferable != null && isLocaleDependentTextFormat(paramLong.longValue()) && paramTransferable.isDataFlavorSupported(javaTextEncodingFlavor)) { try { str = new String((byte[])paramTransferable.getTransferData(javaTextEncodingFlavor), "UTF-8"); } catch (UnsupportedFlavorException unsupportedFlavorException) {} } else { str = getCharsetForTextFormat(paramLong); }  if (str == null) str = getDefaultTextCharset();  return str; }
/*      */   private byte[] translateTransferableString(String paramString, long paramLong) throws IOException { Long long_ = Long.valueOf(paramLong); String str1 = getBestCharsetForTextFormat(long_, null); String str2 = (String)nativeEOLNs.get(long_); if (str2 != null) { int i = paramString.length(); StringBuffer stringBuffer = new StringBuffer(i * 2); for (int j = 0; j < i; j++) { if (paramString.startsWith(str2, j)) { stringBuffer.append(str2); j += str2.length() - 1; } else { char c = paramString.charAt(j); if (c == '\n') { stringBuffer.append(str2); } else { stringBuffer.append(c); }  }  }  paramString = stringBuffer.toString(); }  byte[] arrayOfByte = paramString.getBytes(str1); Integer integer = (Integer)nativeTerminators.get(long_); if (integer != null) { int i = integer.intValue(); byte[] arrayOfByte1 = new byte[arrayOfByte.length + i]; System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, arrayOfByte.length); for (int j = arrayOfByte.length; j < arrayOfByte1.length; j++) arrayOfByte1[j] = 0;  arrayOfByte = arrayOfByte1; }  return arrayOfByte; }
/*      */   private String translateBytesToString(byte[] paramArrayOfbyte, long paramLong, Transferable paramTransferable) throws IOException { int i; Long long_ = Long.valueOf(paramLong); String str1 = getBestCharsetForTextFormat(long_, paramTransferable); String str2 = (String)nativeEOLNs.get(long_); Integer integer = (Integer)nativeTerminators.get(long_); if (integer != null) { int j = integer.intValue(); for (i = 0; i < paramArrayOfbyte.length - j + 1;) { for (int k = i; k < i + j; k++) { if (paramArrayOfbyte[k] != 0) { i += j; continue; }  }  }  } else { i = paramArrayOfbyte.length; }  String str3 = new String(paramArrayOfbyte, 0, i, str1); if (str2 != null) { char[] arrayOfChar1 = str3.toCharArray(); char[] arrayOfChar2 = str2.toCharArray(); str3 = null; byte b = 0; for (int j = 0; j < arrayOfChar1.length; ) { if (j + arrayOfChar2.length > arrayOfChar1.length) { arrayOfChar1[b++] = arrayOfChar1[j++]; continue; }  boolean bool = true; byte b1; int k; for (b1 = 0, k = j; b1 < arrayOfChar2.length; b1++, k++) { if (arrayOfChar2[b1] != arrayOfChar1[k]) { bool = false; break; }  }  if (bool) { arrayOfChar1[b++] = '\n'; j += arrayOfChar2.length; continue; }  arrayOfChar1[b++] = arrayOfChar1[j++]; }  str3 = new String(arrayOfChar1, 0, b); }  return str3; }
/*      */   public byte[] translateTransferable(Transferable paramTransferable, DataFlavor paramDataFlavor, long paramLong) throws IOException { // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: aload_2
/*      */     //   2: invokeinterface getTransferData : (Ljava/awt/datatransfer/DataFlavor;)Ljava/lang/Object;
/*      */     //   7: astore #5
/*      */     //   9: aload #5
/*      */     //   11: ifnonnull -> 16
/*      */     //   14: aconst_null
/*      */     //   15: areturn
/*      */     //   16: aload_2
/*      */     //   17: getstatic java/awt/datatransfer/DataFlavor.plainTextFlavor : Ljava/awt/datatransfer/DataFlavor;
/*      */     //   20: invokevirtual equals : (Ljava/awt/datatransfer/DataFlavor;)Z
/*      */     //   23: ifeq -> 58
/*      */     //   26: aload #5
/*      */     //   28: instanceof java/io/InputStream
/*      */     //   31: ifne -> 58
/*      */     //   34: aload_1
/*      */     //   35: getstatic java/awt/datatransfer/DataFlavor.stringFlavor : Ljava/awt/datatransfer/DataFlavor;
/*      */     //   38: invokeinterface getTransferData : (Ljava/awt/datatransfer/DataFlavor;)Ljava/lang/Object;
/*      */     //   43: astore #5
/*      */     //   45: aload #5
/*      */     //   47: ifnonnull -> 52
/*      */     //   50: aconst_null
/*      */     //   51: areturn
/*      */     //   52: iconst_1
/*      */     //   53: istore #6
/*      */     //   55: goto -> 61
/*      */     //   58: iconst_0
/*      */     //   59: istore #6
/*      */     //   61: goto -> 79
/*      */     //   64: astore #7
/*      */     //   66: new java/io/IOException
/*      */     //   69: dup
/*      */     //   70: aload #7
/*      */     //   72: invokevirtual getMessage : ()Ljava/lang/String;
/*      */     //   75: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   78: athrow
/*      */     //   79: iload #6
/*      */     //   81: ifne -> 111
/*      */     //   84: ldc java/lang/String
/*      */     //   86: aload_2
/*      */     //   87: invokevirtual getRepresentationClass : ()Ljava/lang/Class;
/*      */     //   90: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   93: ifeq -> 132
/*      */     //   96: aload_2
/*      */     //   97: invokestatic isFlavorCharsetTextType : (Ljava/awt/datatransfer/DataFlavor;)Z
/*      */     //   100: ifeq -> 132
/*      */     //   103: aload_0
/*      */     //   104: lload_3
/*      */     //   105: invokevirtual isTextFormat : (J)Z
/*      */     //   108: ifeq -> 132
/*      */     //   111: aload_0
/*      */     //   112: aload_2
/*      */     //   113: aload_1
/*      */     //   114: aload #5
/*      */     //   116: checkcast java/lang/String
/*      */     //   119: invokespecial removeSuspectedData : (Ljava/awt/datatransfer/DataFlavor;Ljava/awt/datatransfer/Transferable;Ljava/lang/String;)Ljava/lang/String;
/*      */     //   122: astore #7
/*      */     //   124: aload_0
/*      */     //   125: aload #7
/*      */     //   127: lload_3
/*      */     //   128: invokespecial translateTransferableString : (Ljava/lang/String;J)[B
/*      */     //   131: areturn
/*      */     //   132: aload_2
/*      */     //   133: invokevirtual isRepresentationClassReader : ()Z
/*      */     //   136: ifeq -> 305
/*      */     //   139: aload_2
/*      */     //   140: invokestatic isFlavorCharsetTextType : (Ljava/awt/datatransfer/DataFlavor;)Z
/*      */     //   143: ifeq -> 154
/*      */     //   146: aload_0
/*      */     //   147: lload_3
/*      */     //   148: invokevirtual isTextFormat : (J)Z
/*      */     //   151: ifne -> 164
/*      */     //   154: new java/io/IOException
/*      */     //   157: dup
/*      */     //   158: ldc 'cannot transfer non-text data as Reader'
/*      */     //   160: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   163: athrow
/*      */     //   164: new java/lang/StringBuffer
/*      */     //   167: dup
/*      */     //   168: invokespecial <init> : ()V
/*      */     //   171: astore #7
/*      */     //   173: aload #5
/*      */     //   175: checkcast java/io/Reader
/*      */     //   178: astore #8
/*      */     //   180: aconst_null
/*      */     //   181: astore #9
/*      */     //   183: aload #8
/*      */     //   185: invokevirtual read : ()I
/*      */     //   188: dup
/*      */     //   189: istore #10
/*      */     //   191: iconst_m1
/*      */     //   192: if_icmpeq -> 207
/*      */     //   195: aload #7
/*      */     //   197: iload #10
/*      */     //   199: i2c
/*      */     //   200: invokevirtual append : (C)Ljava/lang/StringBuffer;
/*      */     //   203: pop
/*      */     //   204: goto -> 183
/*      */     //   207: aload #8
/*      */     //   209: ifnull -> 294
/*      */     //   212: aload #9
/*      */     //   214: ifnull -> 237
/*      */     //   217: aload #8
/*      */     //   219: invokevirtual close : ()V
/*      */     //   222: goto -> 294
/*      */     //   225: astore #10
/*      */     //   227: aload #9
/*      */     //   229: aload #10
/*      */     //   231: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*      */     //   234: goto -> 294
/*      */     //   237: aload #8
/*      */     //   239: invokevirtual close : ()V
/*      */     //   242: goto -> 294
/*      */     //   245: astore #10
/*      */     //   247: aload #10
/*      */     //   249: astore #9
/*      */     //   251: aload #10
/*      */     //   253: athrow
/*      */     //   254: astore #11
/*      */     //   256: aload #8
/*      */     //   258: ifnull -> 291
/*      */     //   261: aload #9
/*      */     //   263: ifnull -> 286
/*      */     //   266: aload #8
/*      */     //   268: invokevirtual close : ()V
/*      */     //   271: goto -> 291
/*      */     //   274: astore #12
/*      */     //   276: aload #9
/*      */     //   278: aload #12
/*      */     //   280: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*      */     //   283: goto -> 291
/*      */     //   286: aload #8
/*      */     //   288: invokevirtual close : ()V
/*      */     //   291: aload #11
/*      */     //   293: athrow
/*      */     //   294: aload_0
/*      */     //   295: aload #7
/*      */     //   297: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   300: lload_3
/*      */     //   301: invokespecial translateTransferableString : (Ljava/lang/String;J)[B
/*      */     //   304: areturn
/*      */     //   305: aload_2
/*      */     //   306: invokevirtual isRepresentationClassCharBuffer : ()Z
/*      */     //   309: ifeq -> 383
/*      */     //   312: aload_2
/*      */     //   313: invokestatic isFlavorCharsetTextType : (Ljava/awt/datatransfer/DataFlavor;)Z
/*      */     //   316: ifeq -> 327
/*      */     //   319: aload_0
/*      */     //   320: lload_3
/*      */     //   321: invokevirtual isTextFormat : (J)Z
/*      */     //   324: ifne -> 337
/*      */     //   327: new java/io/IOException
/*      */     //   330: dup
/*      */     //   331: ldc 'cannot transfer non-text data as CharBuffer'
/*      */     //   333: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   336: athrow
/*      */     //   337: aload #5
/*      */     //   339: checkcast java/nio/CharBuffer
/*      */     //   342: astore #7
/*      */     //   344: aload #7
/*      */     //   346: invokevirtual remaining : ()I
/*      */     //   349: istore #8
/*      */     //   351: iload #8
/*      */     //   353: newarray char
/*      */     //   355: astore #9
/*      */     //   357: aload #7
/*      */     //   359: aload #9
/*      */     //   361: iconst_0
/*      */     //   362: iload #8
/*      */     //   364: invokevirtual get : ([CII)Ljava/nio/CharBuffer;
/*      */     //   367: pop
/*      */     //   368: aload_0
/*      */     //   369: new java/lang/String
/*      */     //   372: dup
/*      */     //   373: aload #9
/*      */     //   375: invokespecial <init> : ([C)V
/*      */     //   378: lload_3
/*      */     //   379: invokespecial translateTransferableString : (Ljava/lang/String;J)[B
/*      */     //   382: areturn
/*      */     //   383: ldc [C
/*      */     //   385: aload_2
/*      */     //   386: invokevirtual getRepresentationClass : ()Ljava/lang/Class;
/*      */     //   389: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   392: ifeq -> 441
/*      */     //   395: aload_2
/*      */     //   396: invokestatic isFlavorCharsetTextType : (Ljava/awt/datatransfer/DataFlavor;)Z
/*      */     //   399: ifeq -> 410
/*      */     //   402: aload_0
/*      */     //   403: lload_3
/*      */     //   404: invokevirtual isTextFormat : (J)Z
/*      */     //   407: ifne -> 420
/*      */     //   410: new java/io/IOException
/*      */     //   413: dup
/*      */     //   414: ldc 'cannot transfer non-text data as char array'
/*      */     //   416: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   419: athrow
/*      */     //   420: aload_0
/*      */     //   421: new java/lang/String
/*      */     //   424: dup
/*      */     //   425: aload #5
/*      */     //   427: checkcast [C
/*      */     //   430: checkcast [C
/*      */     //   433: invokespecial <init> : ([C)V
/*      */     //   436: lload_3
/*      */     //   437: invokespecial translateTransferableString : (Ljava/lang/String;J)[B
/*      */     //   440: areturn
/*      */     //   441: aload_2
/*      */     //   442: invokevirtual isRepresentationClassByteBuffer : ()Z
/*      */     //   445: ifeq -> 520
/*      */     //   448: aload #5
/*      */     //   450: checkcast java/nio/ByteBuffer
/*      */     //   453: astore #7
/*      */     //   455: aload #7
/*      */     //   457: invokevirtual remaining : ()I
/*      */     //   460: istore #8
/*      */     //   462: iload #8
/*      */     //   464: newarray byte
/*      */     //   466: astore #9
/*      */     //   468: aload #7
/*      */     //   470: aload #9
/*      */     //   472: iconst_0
/*      */     //   473: iload #8
/*      */     //   475: invokevirtual get : ([BII)Ljava/nio/ByteBuffer;
/*      */     //   478: pop
/*      */     //   479: aload_2
/*      */     //   480: invokestatic isFlavorCharsetTextType : (Ljava/awt/datatransfer/DataFlavor;)Z
/*      */     //   483: ifeq -> 517
/*      */     //   486: aload_0
/*      */     //   487: lload_3
/*      */     //   488: invokevirtual isTextFormat : (J)Z
/*      */     //   491: ifeq -> 517
/*      */     //   494: aload_2
/*      */     //   495: invokestatic getTextCharset : (Ljava/awt/datatransfer/DataFlavor;)Ljava/lang/String;
/*      */     //   498: astore #10
/*      */     //   500: aload_0
/*      */     //   501: new java/lang/String
/*      */     //   504: dup
/*      */     //   505: aload #9
/*      */     //   507: aload #10
/*      */     //   509: invokespecial <init> : ([BLjava/lang/String;)V
/*      */     //   512: lload_3
/*      */     //   513: invokespecial translateTransferableString : (Ljava/lang/String;J)[B
/*      */     //   516: areturn
/*      */     //   517: aload #9
/*      */     //   519: areturn
/*      */     //   520: ldc [B
/*      */     //   522: aload_2
/*      */     //   523: invokevirtual getRepresentationClass : ()Ljava/lang/Class;
/*      */     //   526: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   529: ifeq -> 583
/*      */     //   532: aload #5
/*      */     //   534: checkcast [B
/*      */     //   537: checkcast [B
/*      */     //   540: astore #7
/*      */     //   542: aload_2
/*      */     //   543: invokestatic isFlavorCharsetTextType : (Ljava/awt/datatransfer/DataFlavor;)Z
/*      */     //   546: ifeq -> 580
/*      */     //   549: aload_0
/*      */     //   550: lload_3
/*      */     //   551: invokevirtual isTextFormat : (J)Z
/*      */     //   554: ifeq -> 580
/*      */     //   557: aload_2
/*      */     //   558: invokestatic getTextCharset : (Ljava/awt/datatransfer/DataFlavor;)Ljava/lang/String;
/*      */     //   561: astore #8
/*      */     //   563: aload_0
/*      */     //   564: new java/lang/String
/*      */     //   567: dup
/*      */     //   568: aload #7
/*      */     //   570: aload #8
/*      */     //   572: invokespecial <init> : ([BLjava/lang/String;)V
/*      */     //   575: lload_3
/*      */     //   576: invokespecial translateTransferableString : (Ljava/lang/String;J)[B
/*      */     //   579: areturn
/*      */     //   580: aload #7
/*      */     //   582: areturn
/*      */     //   583: getstatic java/awt/datatransfer/DataFlavor.imageFlavor : Ljava/awt/datatransfer/DataFlavor;
/*      */     //   586: aload_2
/*      */     //   587: invokevirtual equals : (Ljava/awt/datatransfer/DataFlavor;)Z
/*      */     //   590: ifeq -> 645
/*      */     //   593: aload_0
/*      */     //   594: lload_3
/*      */     //   595: invokevirtual isImageFormat : (J)Z
/*      */     //   598: ifne -> 611
/*      */     //   601: new java/io/IOException
/*      */     //   604: dup
/*      */     //   605: ldc 'Data translation failed: not an image format'
/*      */     //   607: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   610: athrow
/*      */     //   611: aload #5
/*      */     //   613: checkcast java/awt/Image
/*      */     //   616: astore #7
/*      */     //   618: aload_0
/*      */     //   619: aload #7
/*      */     //   621: lload_3
/*      */     //   622: invokevirtual imageToPlatformBytes : (Ljava/awt/Image;J)[B
/*      */     //   625: astore #8
/*      */     //   627: aload #8
/*      */     //   629: ifnonnull -> 642
/*      */     //   632: new java/io/IOException
/*      */     //   635: dup
/*      */     //   636: ldc 'Data translation failed: cannot convert java image to native format'
/*      */     //   638: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   641: athrow
/*      */     //   642: aload #8
/*      */     //   644: areturn
/*      */     //   645: aconst_null
/*      */     //   646: astore #7
/*      */     //   648: aload_0
/*      */     //   649: lload_3
/*      */     //   650: invokevirtual isFileFormat : (J)Z
/*      */     //   653: ifeq -> 807
/*      */     //   656: getstatic java/awt/datatransfer/DataFlavor.javaFileListFlavor : Ljava/awt/datatransfer/DataFlavor;
/*      */     //   659: aload_2
/*      */     //   660: invokevirtual equals : (Ljava/awt/datatransfer/DataFlavor;)Z
/*      */     //   663: ifne -> 676
/*      */     //   666: new java/io/IOException
/*      */     //   669: dup
/*      */     //   670: ldc 'data translation failed'
/*      */     //   672: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   675: athrow
/*      */     //   676: aload #5
/*      */     //   678: checkcast java/util/List
/*      */     //   681: astore #8
/*      */     //   683: aload_1
/*      */     //   684: invokestatic getUserProtectionDomain : (Ljava/awt/datatransfer/Transferable;)Ljava/security/ProtectionDomain;
/*      */     //   687: astore #9
/*      */     //   689: aload_0
/*      */     //   690: aload #8
/*      */     //   692: aload #9
/*      */     //   694: invokespecial castToFiles : (Ljava/util/List;Ljava/security/ProtectionDomain;)Ljava/util/ArrayList;
/*      */     //   697: astore #10
/*      */     //   699: aload_0
/*      */     //   700: aload #10
/*      */     //   702: invokevirtual convertFileListToBytes : (Ljava/util/ArrayList;)Ljava/io/ByteArrayOutputStream;
/*      */     //   705: astore #11
/*      */     //   707: aconst_null
/*      */     //   708: astore #12
/*      */     //   710: aload #11
/*      */     //   712: invokevirtual toByteArray : ()[B
/*      */     //   715: astore #7
/*      */     //   717: aload #11
/*      */     //   719: ifnull -> 804
/*      */     //   722: aload #12
/*      */     //   724: ifnull -> 747
/*      */     //   727: aload #11
/*      */     //   729: invokevirtual close : ()V
/*      */     //   732: goto -> 804
/*      */     //   735: astore #13
/*      */     //   737: aload #12
/*      */     //   739: aload #13
/*      */     //   741: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*      */     //   744: goto -> 804
/*      */     //   747: aload #11
/*      */     //   749: invokevirtual close : ()V
/*      */     //   752: goto -> 804
/*      */     //   755: astore #13
/*      */     //   757: aload #13
/*      */     //   759: astore #12
/*      */     //   761: aload #13
/*      */     //   763: athrow
/*      */     //   764: astore #14
/*      */     //   766: aload #11
/*      */     //   768: ifnull -> 801
/*      */     //   771: aload #12
/*      */     //   773: ifnull -> 796
/*      */     //   776: aload #11
/*      */     //   778: invokevirtual close : ()V
/*      */     //   781: goto -> 801
/*      */     //   784: astore #15
/*      */     //   786: aload #12
/*      */     //   788: aload #15
/*      */     //   790: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*      */     //   793: goto -> 801
/*      */     //   796: aload #11
/*      */     //   798: invokevirtual close : ()V
/*      */     //   801: aload #14
/*      */     //   803: athrow
/*      */     //   804: goto -> 1631
/*      */     //   807: aload_0
/*      */     //   808: lload_3
/*      */     //   809: invokevirtual isURIListFormat : (J)Z
/*      */     //   812: ifeq -> 1197
/*      */     //   815: getstatic java/awt/datatransfer/DataFlavor.javaFileListFlavor : Ljava/awt/datatransfer/DataFlavor;
/*      */     //   818: aload_2
/*      */     //   819: invokevirtual equals : (Ljava/awt/datatransfer/DataFlavor;)Z
/*      */     //   822: ifne -> 835
/*      */     //   825: new java/io/IOException
/*      */     //   828: dup
/*      */     //   829: ldc 'data translation failed'
/*      */     //   831: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   834: athrow
/*      */     //   835: aload_0
/*      */     //   836: lload_3
/*      */     //   837: invokevirtual getNativeForFormat : (J)Ljava/lang/String;
/*      */     //   840: astore #8
/*      */     //   842: aconst_null
/*      */     //   843: astore #9
/*      */     //   845: aload #8
/*      */     //   847: ifnull -> 881
/*      */     //   850: new java/awt/datatransfer/DataFlavor
/*      */     //   853: dup
/*      */     //   854: aload #8
/*      */     //   856: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   859: ldc 'charset'
/*      */     //   861: invokevirtual getParameter : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   864: astore #9
/*      */     //   866: goto -> 881
/*      */     //   869: astore #10
/*      */     //   871: new java/io/IOException
/*      */     //   874: dup
/*      */     //   875: aload #10
/*      */     //   877: invokespecial <init> : (Ljava/lang/Throwable;)V
/*      */     //   880: athrow
/*      */     //   881: aload #9
/*      */     //   883: ifnonnull -> 890
/*      */     //   886: ldc 'UTF-8'
/*      */     //   888: astore #9
/*      */     //   890: aload #5
/*      */     //   892: checkcast java/util/List
/*      */     //   895: astore #10
/*      */     //   897: aload_1
/*      */     //   898: invokestatic getUserProtectionDomain : (Ljava/awt/datatransfer/Transferable;)Ljava/security/ProtectionDomain;
/*      */     //   901: astore #11
/*      */     //   903: aload_0
/*      */     //   904: aload #10
/*      */     //   906: aload #11
/*      */     //   908: invokespecial castToFiles : (Ljava/util/List;Ljava/security/ProtectionDomain;)Ljava/util/ArrayList;
/*      */     //   911: astore #12
/*      */     //   913: new java/util/ArrayList
/*      */     //   916: dup
/*      */     //   917: aload #12
/*      */     //   919: invokevirtual size : ()I
/*      */     //   922: invokespecial <init> : (I)V
/*      */     //   925: astore #13
/*      */     //   927: aload #12
/*      */     //   929: invokevirtual iterator : ()Ljava/util/Iterator;
/*      */     //   932: astore #14
/*      */     //   934: aload #14
/*      */     //   936: invokeinterface hasNext : ()Z
/*      */     //   941: ifeq -> 1021
/*      */     //   944: aload #14
/*      */     //   946: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   951: checkcast java/lang/String
/*      */     //   954: astore #15
/*      */     //   956: new java/io/File
/*      */     //   959: dup
/*      */     //   960: aload #15
/*      */     //   962: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   965: invokevirtual toURI : ()Ljava/net/URI;
/*      */     //   968: astore #16
/*      */     //   970: aload #13
/*      */     //   972: new java/net/URI
/*      */     //   975: dup
/*      */     //   976: aload #16
/*      */     //   978: invokevirtual getScheme : ()Ljava/lang/String;
/*      */     //   981: ldc ''
/*      */     //   983: aload #16
/*      */     //   985: invokevirtual getPath : ()Ljava/lang/String;
/*      */     //   988: aload #16
/*      */     //   990: invokevirtual getFragment : ()Ljava/lang/String;
/*      */     //   993: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   996: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   999: invokevirtual add : (Ljava/lang/Object;)Z
/*      */     //   1002: pop
/*      */     //   1003: goto -> 1018
/*      */     //   1006: astore #17
/*      */     //   1008: new java/io/IOException
/*      */     //   1011: dup
/*      */     //   1012: aload #17
/*      */     //   1014: invokespecial <init> : (Ljava/lang/Throwable;)V
/*      */     //   1017: athrow
/*      */     //   1018: goto -> 934
/*      */     //   1021: ldc '\\r\\n'
/*      */     //   1023: aload #9
/*      */     //   1025: invokevirtual getBytes : (Ljava/lang/String;)[B
/*      */     //   1028: astore #14
/*      */     //   1030: new java/io/ByteArrayOutputStream
/*      */     //   1033: dup
/*      */     //   1034: invokespecial <init> : ()V
/*      */     //   1037: astore #15
/*      */     //   1039: aconst_null
/*      */     //   1040: astore #16
/*      */     //   1042: iconst_0
/*      */     //   1043: istore #17
/*      */     //   1045: iload #17
/*      */     //   1047: aload #13
/*      */     //   1049: invokevirtual size : ()I
/*      */     //   1052: if_icmpge -> 1100
/*      */     //   1055: aload #13
/*      */     //   1057: iload #17
/*      */     //   1059: invokevirtual get : (I)Ljava/lang/Object;
/*      */     //   1062: checkcast java/lang/String
/*      */     //   1065: aload #9
/*      */     //   1067: invokevirtual getBytes : (Ljava/lang/String;)[B
/*      */     //   1070: astore #18
/*      */     //   1072: aload #15
/*      */     //   1074: aload #18
/*      */     //   1076: iconst_0
/*      */     //   1077: aload #18
/*      */     //   1079: arraylength
/*      */     //   1080: invokevirtual write : ([BII)V
/*      */     //   1083: aload #15
/*      */     //   1085: aload #14
/*      */     //   1087: iconst_0
/*      */     //   1088: aload #14
/*      */     //   1090: arraylength
/*      */     //   1091: invokevirtual write : ([BII)V
/*      */     //   1094: iinc #17, 1
/*      */     //   1097: goto -> 1045
/*      */     //   1100: aload #15
/*      */     //   1102: invokevirtual toByteArray : ()[B
/*      */     //   1105: astore #7
/*      */     //   1107: aload #15
/*      */     //   1109: ifnull -> 1194
/*      */     //   1112: aload #16
/*      */     //   1114: ifnull -> 1137
/*      */     //   1117: aload #15
/*      */     //   1119: invokevirtual close : ()V
/*      */     //   1122: goto -> 1194
/*      */     //   1125: astore #17
/*      */     //   1127: aload #16
/*      */     //   1129: aload #17
/*      */     //   1131: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*      */     //   1134: goto -> 1194
/*      */     //   1137: aload #15
/*      */     //   1139: invokevirtual close : ()V
/*      */     //   1142: goto -> 1194
/*      */     //   1145: astore #17
/*      */     //   1147: aload #17
/*      */     //   1149: astore #16
/*      */     //   1151: aload #17
/*      */     //   1153: athrow
/*      */     //   1154: astore #19
/*      */     //   1156: aload #15
/*      */     //   1158: ifnull -> 1191
/*      */     //   1161: aload #16
/*      */     //   1163: ifnull -> 1186
/*      */     //   1166: aload #15
/*      */     //   1168: invokevirtual close : ()V
/*      */     //   1171: goto -> 1191
/*      */     //   1174: astore #20
/*      */     //   1176: aload #16
/*      */     //   1178: aload #20
/*      */     //   1180: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*      */     //   1183: goto -> 1191
/*      */     //   1186: aload #15
/*      */     //   1188: invokevirtual close : ()V
/*      */     //   1191: aload #19
/*      */     //   1193: athrow
/*      */     //   1194: goto -> 1631
/*      */     //   1197: aload_2
/*      */     //   1198: invokevirtual isRepresentationClassInputStream : ()Z
/*      */     //   1201: ifeq -> 1580
/*      */     //   1204: aload #5
/*      */     //   1206: instanceof java/io/InputStream
/*      */     //   1209: ifne -> 1216
/*      */     //   1212: iconst_0
/*      */     //   1213: newarray byte
/*      */     //   1215: areturn
/*      */     //   1216: new java/io/ByteArrayOutputStream
/*      */     //   1219: dup
/*      */     //   1220: invokespecial <init> : ()V
/*      */     //   1223: astore #8
/*      */     //   1225: aconst_null
/*      */     //   1226: astore #9
/*      */     //   1228: aload #5
/*      */     //   1230: checkcast java/io/InputStream
/*      */     //   1233: astore #10
/*      */     //   1235: aconst_null
/*      */     //   1236: astore #11
/*      */     //   1238: iconst_0
/*      */     //   1239: istore #12
/*      */     //   1241: aload #10
/*      */     //   1243: invokevirtual available : ()I
/*      */     //   1246: istore #13
/*      */     //   1248: iload #13
/*      */     //   1250: sipush #8192
/*      */     //   1253: if_icmple -> 1261
/*      */     //   1256: iload #13
/*      */     //   1258: goto -> 1264
/*      */     //   1261: sipush #8192
/*      */     //   1264: newarray byte
/*      */     //   1266: astore #14
/*      */     //   1268: aload #10
/*      */     //   1270: aload #14
/*      */     //   1272: iconst_0
/*      */     //   1273: aload #14
/*      */     //   1275: arraylength
/*      */     //   1276: invokevirtual read : ([BII)I
/*      */     //   1279: dup
/*      */     //   1280: istore #15
/*      */     //   1282: iconst_m1
/*      */     //   1283: if_icmpne -> 1290
/*      */     //   1286: iconst_1
/*      */     //   1287: goto -> 1291
/*      */     //   1290: iconst_0
/*      */     //   1291: dup
/*      */     //   1292: istore #12
/*      */     //   1294: ifne -> 1307
/*      */     //   1297: aload #8
/*      */     //   1299: aload #14
/*      */     //   1301: iconst_0
/*      */     //   1302: iload #15
/*      */     //   1304: invokevirtual write : ([BII)V
/*      */     //   1307: iload #12
/*      */     //   1309: ifeq -> 1268
/*      */     //   1312: aload #10
/*      */     //   1314: ifnull -> 1399
/*      */     //   1317: aload #11
/*      */     //   1319: ifnull -> 1342
/*      */     //   1322: aload #10
/*      */     //   1324: invokevirtual close : ()V
/*      */     //   1327: goto -> 1399
/*      */     //   1330: astore #12
/*      */     //   1332: aload #11
/*      */     //   1334: aload #12
/*      */     //   1336: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*      */     //   1339: goto -> 1399
/*      */     //   1342: aload #10
/*      */     //   1344: invokevirtual close : ()V
/*      */     //   1347: goto -> 1399
/*      */     //   1350: astore #12
/*      */     //   1352: aload #12
/*      */     //   1354: astore #11
/*      */     //   1356: aload #12
/*      */     //   1358: athrow
/*      */     //   1359: astore #21
/*      */     //   1361: aload #10
/*      */     //   1363: ifnull -> 1396
/*      */     //   1366: aload #11
/*      */     //   1368: ifnull -> 1391
/*      */     //   1371: aload #10
/*      */     //   1373: invokevirtual close : ()V
/*      */     //   1376: goto -> 1396
/*      */     //   1379: astore #22
/*      */     //   1381: aload #11
/*      */     //   1383: aload #22
/*      */     //   1385: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*      */     //   1388: goto -> 1396
/*      */     //   1391: aload #10
/*      */     //   1393: invokevirtual close : ()V
/*      */     //   1396: aload #21
/*      */     //   1398: athrow
/*      */     //   1399: aload_2
/*      */     //   1400: invokestatic isFlavorCharsetTextType : (Ljava/awt/datatransfer/DataFlavor;)Z
/*      */     //   1403: ifeq -> 1483
/*      */     //   1406: aload_0
/*      */     //   1407: lload_3
/*      */     //   1408: invokevirtual isTextFormat : (J)Z
/*      */     //   1411: ifeq -> 1483
/*      */     //   1414: aload #8
/*      */     //   1416: invokevirtual toByteArray : ()[B
/*      */     //   1419: astore #10
/*      */     //   1421: aload_2
/*      */     //   1422: invokestatic getTextCharset : (Ljava/awt/datatransfer/DataFlavor;)Ljava/lang/String;
/*      */     //   1425: astore #11
/*      */     //   1427: aload_0
/*      */     //   1428: new java/lang/String
/*      */     //   1431: dup
/*      */     //   1432: aload #10
/*      */     //   1434: aload #11
/*      */     //   1436: invokespecial <init> : ([BLjava/lang/String;)V
/*      */     //   1439: lload_3
/*      */     //   1440: invokespecial translateTransferableString : (Ljava/lang/String;J)[B
/*      */     //   1443: astore #12
/*      */     //   1445: aload #8
/*      */     //   1447: ifnull -> 1480
/*      */     //   1450: aload #9
/*      */     //   1452: ifnull -> 1475
/*      */     //   1455: aload #8
/*      */     //   1457: invokevirtual close : ()V
/*      */     //   1460: goto -> 1480
/*      */     //   1463: astore #13
/*      */     //   1465: aload #9
/*      */     //   1467: aload #13
/*      */     //   1469: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*      */     //   1472: goto -> 1480
/*      */     //   1475: aload #8
/*      */     //   1477: invokevirtual close : ()V
/*      */     //   1480: aload #12
/*      */     //   1482: areturn
/*      */     //   1483: aload #8
/*      */     //   1485: invokevirtual toByteArray : ()[B
/*      */     //   1488: astore #7
/*      */     //   1490: aload #8
/*      */     //   1492: ifnull -> 1577
/*      */     //   1495: aload #9
/*      */     //   1497: ifnull -> 1520
/*      */     //   1500: aload #8
/*      */     //   1502: invokevirtual close : ()V
/*      */     //   1505: goto -> 1577
/*      */     //   1508: astore #10
/*      */     //   1510: aload #9
/*      */     //   1512: aload #10
/*      */     //   1514: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*      */     //   1517: goto -> 1577
/*      */     //   1520: aload #8
/*      */     //   1522: invokevirtual close : ()V
/*      */     //   1525: goto -> 1577
/*      */     //   1528: astore #10
/*      */     //   1530: aload #10
/*      */     //   1532: astore #9
/*      */     //   1534: aload #10
/*      */     //   1536: athrow
/*      */     //   1537: astore #23
/*      */     //   1539: aload #8
/*      */     //   1541: ifnull -> 1574
/*      */     //   1544: aload #9
/*      */     //   1546: ifnull -> 1569
/*      */     //   1549: aload #8
/*      */     //   1551: invokevirtual close : ()V
/*      */     //   1554: goto -> 1574
/*      */     //   1557: astore #24
/*      */     //   1559: aload #9
/*      */     //   1561: aload #24
/*      */     //   1563: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
/*      */     //   1566: goto -> 1574
/*      */     //   1569: aload #8
/*      */     //   1571: invokevirtual close : ()V
/*      */     //   1574: aload #23
/*      */     //   1576: athrow
/*      */     //   1577: goto -> 1631
/*      */     //   1580: aload_2
/*      */     //   1581: invokevirtual isRepresentationClassRemote : ()Z
/*      */     //   1584: ifeq -> 1604
/*      */     //   1587: aload #5
/*      */     //   1589: invokestatic newMarshalledObject : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   1592: astore #8
/*      */     //   1594: aload #8
/*      */     //   1596: invokestatic convertObjectToBytes : (Ljava/lang/Object;)[B
/*      */     //   1599: astore #7
/*      */     //   1601: goto -> 1631
/*      */     //   1604: aload_2
/*      */     //   1605: invokevirtual isRepresentationClassSerializable : ()Z
/*      */     //   1608: ifeq -> 1621
/*      */     //   1611: aload #5
/*      */     //   1613: invokestatic convertObjectToBytes : (Ljava/lang/Object;)[B
/*      */     //   1616: astore #7
/*      */     //   1618: goto -> 1631
/*      */     //   1621: new java/io/IOException
/*      */     //   1624: dup
/*      */     //   1625: ldc 'data translation failed'
/*      */     //   1627: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1630: athrow
/*      */     //   1631: aload #7
/*      */     //   1633: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1105	-> 0
/*      */     //   #1106	-> 9
/*      */     //   #1107	-> 14
/*      */     //   #1109	-> 16
/*      */     //   #1112	-> 34
/*      */     //   #1113	-> 45
/*      */     //   #1114	-> 50
/*      */     //   #1116	-> 52
/*      */     //   #1118	-> 58
/*      */     //   #1122	-> 61
/*      */     //   #1120	-> 64
/*      */     //   #1121	-> 66
/*      */     //   #1126	-> 79
/*      */     //   #1127	-> 87
/*      */     //   #1128	-> 97
/*      */     //   #1130	-> 111
/*      */     //   #1132	-> 124
/*      */     //   #1138	-> 132
/*      */     //   #1139	-> 139
/*      */     //   #1140	-> 154
/*      */     //   #1144	-> 164
/*      */     //   #1145	-> 173
/*      */     //   #1147	-> 183
/*      */     //   #1148	-> 195
/*      */     //   #1150	-> 207
/*      */     //   #1145	-> 245
/*      */     //   #1150	-> 254
/*      */     //   #1152	-> 294
/*      */     //   #1153	-> 297
/*      */     //   #1152	-> 301
/*      */     //   #1157	-> 305
/*      */     //   #1158	-> 312
/*      */     //   #1159	-> 327
/*      */     //   #1163	-> 337
/*      */     //   #1164	-> 344
/*      */     //   #1165	-> 351
/*      */     //   #1166	-> 357
/*      */     //   #1168	-> 368
/*      */     //   #1173	-> 383
/*      */     //   #1174	-> 395
/*      */     //   #1175	-> 410
/*      */     //   #1179	-> 420
/*      */     //   #1186	-> 441
/*      */     //   #1187	-> 448
/*      */     //   #1188	-> 455
/*      */     //   #1189	-> 462
/*      */     //   #1190	-> 468
/*      */     //   #1192	-> 479
/*      */     //   #1193	-> 494
/*      */     //   #1194	-> 500
/*      */     //   #1198	-> 517
/*      */     //   #1204	-> 520
/*      */     //   #1205	-> 532
/*      */     //   #1207	-> 542
/*      */     //   #1208	-> 557
/*      */     //   #1209	-> 563
/*      */     //   #1213	-> 580
/*      */     //   #1216	-> 583
/*      */     //   #1217	-> 593
/*      */     //   #1218	-> 601
/*      */     //   #1222	-> 611
/*      */     //   #1223	-> 618
/*      */     //   #1225	-> 627
/*      */     //   #1226	-> 632
/*      */     //   #1229	-> 642
/*      */     //   #1232	-> 645
/*      */     //   #1236	-> 648
/*      */     //   #1237	-> 656
/*      */     //   #1238	-> 666
/*      */     //   #1241	-> 676
/*      */     //   #1243	-> 683
/*      */     //   #1245	-> 689
/*      */     //   #1247	-> 699
/*      */     //   #1248	-> 710
/*      */     //   #1249	-> 717
/*      */     //   #1247	-> 755
/*      */     //   #1249	-> 764
/*      */     //   #1253	-> 804
/*      */     //   #1254	-> 815
/*      */     //   #1255	-> 825
/*      */     //   #1257	-> 835
/*      */     //   #1258	-> 842
/*      */     //   #1259	-> 845
/*      */     //   #1261	-> 850
/*      */     //   #1264	-> 866
/*      */     //   #1262	-> 869
/*      */     //   #1263	-> 871
/*      */     //   #1266	-> 881
/*      */     //   #1267	-> 886
/*      */     //   #1269	-> 890
/*      */     //   #1270	-> 897
/*      */     //   #1271	-> 903
/*      */     //   #1272	-> 913
/*      */     //   #1273	-> 927
/*      */     //   #1274	-> 956
/*      */     //   #1277	-> 970
/*      */     //   #1280	-> 1003
/*      */     //   #1278	-> 1006
/*      */     //   #1279	-> 1008
/*      */     //   #1281	-> 1018
/*      */     //   #1283	-> 1021
/*      */     //   #1285	-> 1030
/*      */     //   #1286	-> 1042
/*      */     //   #1287	-> 1055
/*      */     //   #1288	-> 1072
/*      */     //   #1289	-> 1083
/*      */     //   #1286	-> 1094
/*      */     //   #1291	-> 1100
/*      */     //   #1292	-> 1107
/*      */     //   #1285	-> 1145
/*      */     //   #1292	-> 1154
/*      */     //   #1297	-> 1194
/*      */     //   #1303	-> 1204
/*      */     //   #1304	-> 1212
/*      */     //   #1307	-> 1216
/*      */     //   #1308	-> 1228
/*      */     //   #1309	-> 1238
/*      */     //   #1310	-> 1241
/*      */     //   #1311	-> 1248
/*      */     //   #1314	-> 1268
/*      */     //   #1315	-> 1297
/*      */     //   #1317	-> 1307
/*      */     //   #1318	-> 1312
/*      */     //   #1308	-> 1350
/*      */     //   #1318	-> 1359
/*      */     //   #1320	-> 1399
/*      */     //   #1321	-> 1414
/*      */     //   #1322	-> 1421
/*      */     //   #1323	-> 1427
/*      */     //   #1328	-> 1445
/*      */     //   #1323	-> 1480
/*      */     //   #1327	-> 1483
/*      */     //   #1328	-> 1490
/*      */     //   #1307	-> 1528
/*      */     //   #1328	-> 1537
/*      */     //   #1333	-> 1580
/*      */     //   #1335	-> 1587
/*      */     //   #1336	-> 1594
/*      */     //   #1339	-> 1601
/*      */     //   #1341	-> 1611
/*      */     //   #1344	-> 1621
/*      */     //   #1349	-> 1631
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   0	15	64	java/awt/datatransfer/UnsupportedFlavorException
/*      */     //   16	51	64	java/awt/datatransfer/UnsupportedFlavorException
/*      */     //   52	61	64	java/awt/datatransfer/UnsupportedFlavorException
/*      */     //   183	207	245	java/lang/Throwable
/*      */     //   183	207	254	finally
/*      */     //   217	222	225	java/lang/Throwable
/*      */     //   245	256	254	finally
/*      */     //   266	271	274	java/lang/Throwable
/*      */     //   710	717	755	java/lang/Throwable
/*      */     //   710	717	764	finally
/*      */     //   727	732	735	java/lang/Throwable
/*      */     //   755	766	764	finally
/*      */     //   776	781	784	java/lang/Throwable
/*      */     //   850	866	869	java/lang/ClassNotFoundException
/*      */     //   970	1003	1006	java/net/URISyntaxException
/*      */     //   1042	1107	1145	java/lang/Throwable
/*      */     //   1042	1107	1154	finally
/*      */     //   1117	1122	1125	java/lang/Throwable
/*      */     //   1145	1156	1154	finally
/*      */     //   1166	1171	1174	java/lang/Throwable
/*      */     //   1228	1445	1528	java/lang/Throwable
/*      */     //   1228	1445	1537	finally
/*      */     //   1238	1312	1350	java/lang/Throwable
/*      */     //   1238	1312	1359	finally
/*      */     //   1322	1327	1330	java/lang/Throwable
/*      */     //   1350	1361	1359	finally
/*      */     //   1371	1376	1379	java/lang/Throwable
/*      */     //   1455	1460	1463	java/lang/Throwable
/*      */     //   1483	1490	1528	java/lang/Throwable
/*      */     //   1483	1490	1537	finally
/*      */     //   1500	1505	1508	java/lang/Throwable
/*      */     //   1528	1539	1537	finally
/*      */     //   1549	1554	1557	java/lang/Throwable }
/*      */   private static byte[] convertObjectToBytes(Object paramObject) throws IOException { try(ByteArrayOutputStream null = new ByteArrayOutputStream(); ObjectOutputStream null = new ObjectOutputStream(byteArrayOutputStream)) { objectOutputStream.writeObject(paramObject); return byteArrayOutputStream.toByteArray(); }  }
/*      */   private String removeSuspectedData(DataFlavor paramDataFlavor, Transferable paramTransferable, final String str) throws IOException { if (null == System.getSecurityManager() || !paramDataFlavor.isMimeTypeEqual("text/uri-list")) return str;  String str = ""; final ProtectionDomain userProtectionDomain = getUserProtectionDomain(paramTransferable); try { str = AccessController.<String>doPrivileged(new PrivilegedExceptionAction<String>() { public Object run() { StringBuffer stringBuffer = new StringBuffer(str.length()); String[] arrayOfString = str.split("(\\s)+"); for (String str : arrayOfString) { File file = new File(str); if (file.exists() && !DataTransferer.isFileInWebstartedCache(file) && !DataTransferer.this.isForbiddenToRead(file, userProtectionDomain)) { if (0 != stringBuffer.length()) stringBuffer.append("\\r\\n");  stringBuffer.append(str); }  }  return stringBuffer.toString(); } }
/*      */         ); } catch (PrivilegedActionException privilegedActionException) { throw new IOException(privilegedActionException.getMessage(), privilegedActionException); }  return str; }
/*      */   private static ProtectionDomain getUserProtectionDomain(Transferable paramTransferable) { return paramTransferable.getClass().getProtectionDomain(); }
/*      */   private boolean isForbiddenToRead(File paramFile, ProtectionDomain paramProtectionDomain) { if (null == paramProtectionDomain) return false;  try { FilePermission filePermission = new FilePermission(paramFile.getCanonicalPath(), "read, delete"); if (paramProtectionDomain.implies(filePermission))
/*      */         return false;  } catch (IOException iOException) {} return true; }
/*      */   private ArrayList<String> castToFiles(final List files, final ProtectionDomain userProtectionDomain) throws IOException { final ArrayList<String> fileList = new ArrayList(); try { AccessController.doPrivileged(new PrivilegedExceptionAction() { public Object run() throws IOException { for (Object object : files) { File file = DataTransferer.this.castToFile(object); if (file != null && (null == System.getSecurityManager() || (!DataTransferer.isFileInWebstartedCache(file) && !DataTransferer.this.isForbiddenToRead(file, userProtectionDomain))))
/*      */                   fileList.add(file.getCanonicalPath());  }  return null; } }
/*      */         ); } catch (PrivilegedActionException privilegedActionException) { throw new IOException(privilegedActionException.getMessage()); }  return arrayList; }
/*      */   private File castToFile(Object paramObject) throws IOException { String str = null; if (paramObject instanceof File) { str = ((File)paramObject).getCanonicalPath(); } else if (paramObject instanceof String) { str = (String)paramObject; } else { return null; }  return new File(str); }
/* 1680 */   public Object translateStream(InputStream paramInputStream, DataFlavor paramDataFlavor, long paramLong, Transferable paramTransferable) throws IOException { Object object; ArrayList<File> arrayList = null;
/*      */ 
/*      */     
/* 1683 */     if (isURIListFormat(paramLong) && DataFlavor.javaFileListFlavor
/* 1684 */       .equals(paramDataFlavor)) {
/*      */ 
/*      */       
/* 1687 */       URI[] arrayOfURI = dragQueryURIs(paramInputStream, paramLong, paramTransferable);
/* 1688 */       if (arrayOfURI == null) {
/* 1689 */         return null;
/*      */       }
/* 1691 */       ArrayList<File> arrayList1 = new ArrayList();
/* 1692 */       for (URI uRI : arrayOfURI) {
/*      */         try {
/* 1694 */           arrayList1.add(new File(uRI));
/* 1695 */         } catch (IllegalArgumentException illegalArgumentException) {}
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1701 */       arrayList = arrayList1;
/*      */     }
/*      */     else {
/*      */       
/* 1705 */       if (String.class.equals(paramDataFlavor.getRepresentationClass()) && 
/* 1706 */         isFlavorCharsetTextType(paramDataFlavor) && isTextFormat(paramLong))
/*      */       {
/* 1708 */         return translateBytesToString(inputStreamToByteArray(paramInputStream), paramLong, paramTransferable);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1714 */       if (DataFlavor.plainTextFlavor.equals(paramDataFlavor)) {
/* 1715 */         StringReader stringReader = new StringReader(translateBytesToString(
/* 1716 */               inputStreamToByteArray(paramInputStream), paramLong, paramTransferable));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1723 */       else if (paramDataFlavor.isRepresentationClassInputStream()) {
/* 1724 */         object = translateStreamToInputStream(paramInputStream, paramDataFlavor, paramLong, paramTransferable);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1730 */       else if (paramDataFlavor.isRepresentationClassReader()) {
/* 1731 */         if (!isFlavorCharsetTextType(paramDataFlavor) || !isTextFormat(paramLong)) {
/* 1732 */           throw new IOException("cannot transfer non-text data as Reader");
/*      */         }
/*      */ 
/*      */         
/* 1736 */         InputStream inputStream = (InputStream)translateStreamToInputStream(paramInputStream, DataFlavor.plainTextFlavor, paramLong, paramTransferable);
/*      */ 
/*      */ 
/*      */         
/* 1740 */         String str = getTextCharset(DataFlavor.plainTextFlavor);
/*      */         
/* 1742 */         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, str);
/*      */         
/* 1744 */         object = constructFlavoredObject(inputStreamReader, paramDataFlavor, Reader.class);
/*      */       }
/* 1746 */       else if (byte[].class.equals(paramDataFlavor.getRepresentationClass())) {
/* 1747 */         if (isFlavorCharsetTextType(paramDataFlavor) && isTextFormat(paramLong)) {
/*      */           
/* 1749 */           object = translateBytesToString(inputStreamToByteArray(paramInputStream), paramLong, paramTransferable).getBytes(getTextCharset(paramDataFlavor));
/*      */         } else {
/* 1751 */           object = inputStreamToByteArray(paramInputStream);
/*      */         }
/*      */       
/* 1754 */       } else if (paramDataFlavor.isRepresentationClassRemote()) {
/*      */         
/* 1756 */         try (ObjectInputStream null = new ObjectInputStream(paramInputStream)) {
/*      */ 
/*      */           
/* 1759 */           object = RMI.getMarshalledObject(objectInputStream.readObject());
/* 1760 */         } catch (Exception exception) {
/* 1761 */           throw new IOException(exception.getMessage());
/*      */         }
/*      */       
/*      */       }
/* 1765 */       else if (paramDataFlavor.isRepresentationClassSerializable()) {
/* 1766 */         try (ObjectInputStream null = new ObjectInputStream(paramInputStream)) {
/*      */ 
/*      */           
/* 1769 */           object = objectInputStream.readObject();
/* 1770 */         } catch (Exception exception) {
/* 1771 */           throw new IOException(exception.getMessage());
/*      */         }
/*      */       
/* 1774 */       } else if (DataFlavor.imageFlavor.equals(paramDataFlavor)) {
/* 1775 */         if (!isImageFormat(paramLong)) {
/* 1776 */           throw new IOException("data translation failed");
/*      */         }
/* 1778 */         object = platformImageBytesToImage(inputStreamToByteArray(paramInputStream), paramLong);
/*      */       } 
/*      */     } 
/* 1781 */     if (object == null) {
/* 1782 */       throw new IOException("data translation failed");
/*      */     }
/*      */     
/* 1785 */     return object; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object translateStreamToInputStream(InputStream paramInputStream, DataFlavor paramDataFlavor, long paramLong, Transferable paramTransferable) throws IOException {
/* 1798 */     if (isFlavorCharsetTextType(paramDataFlavor) && isTextFormat(paramLong))
/*      */     {
/* 1800 */       paramInputStream = new ReencodingInputStream(paramInputStream, paramLong, getTextCharset(paramDataFlavor), paramTransferable);
/*      */     }
/*      */ 
/*      */     
/* 1804 */     return constructFlavoredObject(paramInputStream, paramDataFlavor, InputStream.class);
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
/*      */   private Object constructFlavoredObject(Object paramObject, DataFlavor paramDataFlavor, Class paramClass) throws IOException {
/* 1816 */     final Class<?> dfrc = paramDataFlavor.getRepresentationClass();
/*      */     
/* 1818 */     if (paramClass.equals(clazz)) {
/* 1819 */       return paramObject;
/*      */     }
/* 1821 */     Constructor[] arrayOfConstructor = null;
/*      */ 
/*      */     
/*      */     try {
/* 1825 */       arrayOfConstructor = AccessController.<Constructor[]>doPrivileged(new PrivilegedAction<Constructor>() {
/*      */             public Object run() {
/* 1827 */               return dfrc.getConstructors();
/*      */             }
/*      */           });
/* 1830 */     } catch (SecurityException securityException) {
/* 1831 */       throw new IOException(securityException.getMessage());
/*      */     } 
/*      */     
/* 1834 */     Constructor constructor = null;
/*      */     
/* 1836 */     for (byte b = 0; b < arrayOfConstructor.length; b++) {
/* 1837 */       if (Modifier.isPublic(arrayOfConstructor[b].getModifiers())) {
/*      */ 
/*      */ 
/*      */         
/* 1841 */         Class[] arrayOfClass = arrayOfConstructor[b].getParameterTypes();
/*      */         
/* 1843 */         if (arrayOfClass != null && arrayOfClass.length == 1 && paramClass
/* 1844 */           .equals(arrayOfClass[0])) {
/* 1845 */           constructor = arrayOfConstructor[b];
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1850 */     if (constructor == null) {
/* 1851 */       throw new IOException("can't find <init>(L" + paramClass + ";)V for class: " + clazz
/* 1852 */           .getName());
/*      */     }
/*      */     
/*      */     try {
/* 1856 */       return constructor.newInstance(new Object[] { paramObject });
/* 1857 */     } catch (Exception exception) {
/* 1858 */       throw new IOException(exception.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class ReencodingInputStream
/*      */     extends InputStream
/*      */   {
/*      */     protected BufferedReader wrapped;
/*      */     
/* 1869 */     protected final char[] in = new char[2];
/*      */     
/*      */     protected byte[] out;
/*      */     
/*      */     protected CharsetEncoder encoder;
/*      */     
/*      */     protected CharBuffer inBuf;
/*      */     
/*      */     protected ByteBuffer outBuf;
/*      */     
/*      */     protected char[] eoln;
/*      */     
/*      */     protected int numTerminators;
/*      */     protected boolean eos;
/*      */     protected int index;
/*      */     protected int limit;
/*      */     
/*      */     public ReencodingInputStream(InputStream param1InputStream, long param1Long, String param1String, Transferable param1Transferable) throws IOException {
/* 1887 */       Long long_ = Long.valueOf(param1Long);
/*      */       
/* 1889 */       String str1 = null;
/* 1890 */       if (DataTransferer.this.isLocaleDependentTextFormat(param1Long) && param1Transferable != null && param1Transferable
/*      */ 
/*      */         
/* 1893 */         .isDataFlavorSupported(DataTransferer.javaTextEncodingFlavor)) {
/*      */ 
/*      */         
/*      */         try {
/* 1897 */           str1 = new String((byte[])param1Transferable.getTransferData(DataTransferer.javaTextEncodingFlavor), "UTF-8");
/*      */         }
/* 1899 */         catch (UnsupportedFlavorException unsupportedFlavorException) {}
/*      */       } else {
/*      */         
/* 1902 */         str1 = DataTransferer.this.getCharsetForTextFormat(long_);
/*      */       } 
/*      */       
/* 1905 */       if (str1 == null)
/*      */       {
/* 1907 */         str1 = DataTransferer.getDefaultTextCharset();
/*      */       }
/* 1909 */       this.wrapped = new BufferedReader(new InputStreamReader(param1InputStream, str1));
/*      */ 
/*      */       
/* 1912 */       if (param1String == null)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1917 */         throw new NullPointerException("null target encoding");
/*      */       }
/*      */       
/*      */       try {
/* 1921 */         this.encoder = Charset.forName(param1String).newEncoder();
/* 1922 */         this.out = new byte[(int)((this.encoder.maxBytesPerChar() * 2.0F) + 0.5D)];
/* 1923 */         this.inBuf = CharBuffer.wrap(this.in);
/* 1924 */         this.outBuf = ByteBuffer.wrap(this.out);
/* 1925 */       } catch (IllegalCharsetNameException illegalCharsetNameException) {
/* 1926 */         throw new IOException(illegalCharsetNameException.toString());
/* 1927 */       } catch (UnsupportedCharsetException unsupportedCharsetException) {
/* 1928 */         throw new IOException(unsupportedCharsetException.toString());
/* 1929 */       } catch (UnsupportedOperationException unsupportedOperationException) {
/* 1930 */         throw new IOException(unsupportedOperationException.toString());
/*      */       } 
/*      */       
/* 1933 */       String str2 = (String)DataTransferer.nativeEOLNs.get(long_);
/* 1934 */       if (str2 != null) {
/* 1935 */         this.eoln = str2.toCharArray();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1940 */       Integer integer = (Integer)DataTransferer.nativeTerminators.get(long_);
/* 1941 */       if (integer != null) {
/* 1942 */         this.numTerminators = integer.intValue();
/*      */       }
/*      */     }
/*      */     
/*      */     private int readChar() throws IOException {
/* 1947 */       int i = this.wrapped.read();
/*      */       
/* 1949 */       if (i == -1) {
/* 1950 */         this.eos = true;
/* 1951 */         return -1;
/*      */       } 
/*      */ 
/*      */       
/* 1955 */       if (this.numTerminators > 0 && i == 0) {
/* 1956 */         this.eos = true;
/* 1957 */         return -1;
/* 1958 */       }  if (this.eoln != null && matchCharArray(this.eoln, i)) {
/* 1959 */         i = 10;
/*      */       }
/*      */       
/* 1962 */       return i;
/*      */     }
/*      */     
/*      */     public int read() throws IOException {
/* 1966 */       if (this.eos) {
/* 1967 */         return -1;
/*      */       }
/*      */       
/* 1970 */       if (this.index >= this.limit) {
/*      */         
/* 1972 */         int i = readChar();
/* 1973 */         if (i == -1) {
/* 1974 */           return -1;
/*      */         }
/*      */         
/* 1977 */         this.in[0] = (char)i;
/* 1978 */         this.in[1] = Character.MIN_VALUE;
/* 1979 */         this.inBuf.limit(1);
/* 1980 */         if (Character.isHighSurrogate((char)i)) {
/* 1981 */           i = readChar();
/* 1982 */           if (i != -1) {
/* 1983 */             this.in[1] = (char)i;
/* 1984 */             this.inBuf.limit(2);
/*      */           } 
/*      */         } 
/*      */         
/* 1988 */         this.inBuf.rewind();
/* 1989 */         this.outBuf.limit(this.out.length).rewind();
/* 1990 */         this.encoder.encode(this.inBuf, this.outBuf, false);
/* 1991 */         this.outBuf.flip();
/* 1992 */         this.limit = this.outBuf.limit();
/*      */         
/* 1994 */         this.index = 0;
/*      */         
/* 1996 */         return read();
/*      */       } 
/* 1998 */       return this.out[this.index++] & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     public int available() throws IOException {
/* 2003 */       return this.eos ? 0 : (this.limit - this.index);
/*      */     }
/*      */     
/*      */     public void close() throws IOException {
/* 2007 */       this.wrapped.close();
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
/*      */     private boolean matchCharArray(char[] param1ArrayOfchar, int param1Int) throws IOException {
/* 2020 */       this.wrapped.mark(param1ArrayOfchar.length);
/*      */       
/* 2022 */       byte b = 0;
/* 2023 */       if ((char)param1Int == param1ArrayOfchar[0]) {
/* 2024 */         for (b = 1; b < param1ArrayOfchar.length; b++) {
/* 2025 */           param1Int = this.wrapped.read();
/* 2026 */           if (param1Int == -1 || (char)param1Int != param1ArrayOfchar[b]) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/* 2032 */       if (b == param1ArrayOfchar.length) {
/* 2033 */         return true;
/*      */       }
/* 2035 */       this.wrapped.reset();
/* 2036 */       return false;
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
/*      */   protected URI[] dragQueryURIs(InputStream paramInputStream, long paramLong, Transferable paramTransferable) throws IOException {
/* 2054 */     throw new IOException(new UnsupportedOperationException("not implemented on this platform"));
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
/*      */   protected Image standardImageBytesToImage(byte[] paramArrayOfbyte, String paramString) throws IOException {
/* 2077 */     Iterator<ImageReader> iterator = ImageIO.getImageReadersByMIMEType(paramString);
/*      */     
/* 2079 */     if (!iterator.hasNext()) {
/* 2080 */       throw new IOException("No registered service provider can decode  an image from " + paramString);
/*      */     }
/*      */ 
/*      */     
/* 2084 */     IOException iOException = null;
/*      */     
/* 2086 */     while (iterator.hasNext()) {
/* 2087 */       ImageReader imageReader = iterator.next();
/* 2088 */       try (ByteArrayInputStream null = new ByteArrayInputStream(paramArrayOfbyte)) {
/*      */         
/* 2090 */         ImageInputStream imageInputStream = ImageIO.createImageInputStream(byteArrayInputStream);
/*      */         
/*      */         try {
/* 2093 */           ImageReadParam imageReadParam = imageReader.getDefaultReadParam();
/* 2094 */           imageReader.setInput(imageInputStream, true, true);
/*      */           
/* 2096 */           BufferedImage bufferedImage = imageReader.read(imageReader.getMinIndex(), imageReadParam);
/* 2097 */           if (bufferedImage != null) {
/* 2098 */             return bufferedImage;
/*      */           }
/*      */         } finally {
/* 2101 */           imageInputStream.close();
/* 2102 */           imageReader.dispose();
/*      */         } 
/* 2104 */       } catch (IOException iOException1) {
/* 2105 */         iOException = iOException1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2110 */     if (iOException == null) {
/* 2111 */       iOException = new IOException("Registered service providers failed to decode an image from " + paramString);
/*      */     }
/*      */ 
/*      */     
/* 2115 */     throw iOException;
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
/*      */   protected byte[] imageToStandardBytes(Image paramImage, String paramString) throws IOException {
/* 2133 */     IOException iOException = null;
/*      */     
/* 2135 */     Iterator<ImageWriter> iterator = ImageIO.getImageWritersByMIMEType(paramString);
/*      */     
/* 2137 */     if (!iterator.hasNext()) {
/* 2138 */       throw new IOException("No registered service provider can encode  an image to " + paramString);
/*      */     }
/*      */ 
/*      */     
/* 2142 */     if (paramImage instanceof RenderedImage) {
/*      */       
/*      */       try {
/* 2145 */         return imageToStandardBytesImpl((RenderedImage)paramImage, paramString);
/* 2146 */       } catch (IOException iOException1) {
/* 2147 */         iOException = iOException1;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 2152 */     int i = 0;
/* 2153 */     int j = 0;
/* 2154 */     if (paramImage instanceof ToolkitImage) {
/* 2155 */       ImageRepresentation imageRepresentation = ((ToolkitImage)paramImage).getImageRep();
/* 2156 */       imageRepresentation.reconstruct(32);
/* 2157 */       i = imageRepresentation.getWidth();
/* 2158 */       j = imageRepresentation.getHeight();
/*      */     } else {
/* 2160 */       i = paramImage.getWidth(null);
/* 2161 */       j = paramImage.getHeight(null);
/*      */     } 
/*      */     
/* 2164 */     ColorModel colorModel = ColorModel.getRGBdefault();
/*      */     
/* 2166 */     WritableRaster writableRaster = colorModel.createCompatibleWritableRaster(i, j);
/*      */ 
/*      */     
/* 2169 */     BufferedImage bufferedImage = new BufferedImage(colorModel, writableRaster, colorModel.isAlphaPremultiplied(), null);
/*      */ 
/*      */     
/* 2172 */     Graphics graphics = bufferedImage.getGraphics();
/*      */     try {
/* 2174 */       graphics.drawImage(paramImage, 0, 0, i, j, null);
/*      */     } finally {
/* 2176 */       graphics.dispose();
/*      */     } 
/*      */     
/*      */     try {
/* 2180 */       return imageToStandardBytesImpl(bufferedImage, paramString);
/* 2181 */     } catch (IOException iOException1) {
/* 2182 */       if (iOException != null) {
/* 2183 */         throw iOException;
/*      */       }
/* 2185 */       throw iOException1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] imageToStandardBytesImpl(RenderedImage paramRenderedImage, String paramString) throws IOException {
/* 2194 */     Iterator<ImageWriter> iterator = ImageIO.getImageWritersByMIMEType(paramString);
/*      */     
/* 2196 */     ImageTypeSpecifier imageTypeSpecifier = new ImageTypeSpecifier(paramRenderedImage);
/*      */ 
/*      */     
/* 2199 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 2200 */     IOException iOException = null;
/*      */     
/* 2202 */     while (iterator.hasNext()) {
/* 2203 */       ImageWriter imageWriter = iterator.next();
/* 2204 */       ImageWriterSpi imageWriterSpi = imageWriter.getOriginatingProvider();
/*      */       
/* 2206 */       if (!imageWriterSpi.canEncodeImage(imageTypeSpecifier)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/* 2212 */         ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(byteArrayOutputStream);
/*      */         try {
/* 2214 */           imageWriter.setOutput(imageOutputStream);
/* 2215 */           imageWriter.write(paramRenderedImage);
/* 2216 */           imageOutputStream.flush();
/*      */         } finally {
/* 2218 */           imageOutputStream.close();
/*      */         } 
/* 2220 */       } catch (IOException iOException1) {
/* 2221 */         imageWriter.dispose();
/* 2222 */         byteArrayOutputStream.reset();
/* 2223 */         iOException = iOException1;
/*      */         
/*      */         continue;
/*      */       } 
/* 2227 */       imageWriter.dispose();
/* 2228 */       byteArrayOutputStream.close();
/* 2229 */       return byteArrayOutputStream.toByteArray();
/*      */     } 
/*      */     
/* 2232 */     byteArrayOutputStream.close();
/*      */     
/* 2234 */     if (iOException == null) {
/* 2235 */       iOException = new IOException("Registered service providers failed to encode " + paramRenderedImage + " to " + paramString);
/*      */     }
/*      */ 
/*      */     
/* 2239 */     throw iOException;
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
/*      */   private Object concatData(Object paramObject1, Object paramObject2) {
/* 2261 */     InputStream inputStream1 = null;
/* 2262 */     InputStream inputStream2 = null;
/*      */     
/* 2264 */     if (paramObject1 instanceof byte[]) {
/* 2265 */       byte[] arrayOfByte = (byte[])paramObject1;
/* 2266 */       if (paramObject2 instanceof byte[]) {
/* 2267 */         byte[] arrayOfByte1 = (byte[])paramObject2;
/* 2268 */         byte[] arrayOfByte2 = new byte[arrayOfByte.length + arrayOfByte1.length];
/* 2269 */         System.arraycopy(arrayOfByte, 0, arrayOfByte2, 0, arrayOfByte.length);
/* 2270 */         System.arraycopy(arrayOfByte1, 0, arrayOfByte2, arrayOfByte.length, arrayOfByte1.length);
/* 2271 */         return arrayOfByte2;
/*      */       } 
/* 2273 */       inputStream1 = new ByteArrayInputStream(arrayOfByte);
/* 2274 */       inputStream2 = (InputStream)paramObject2;
/*      */     } else {
/*      */       
/* 2277 */       inputStream1 = (InputStream)paramObject1;
/* 2278 */       if (paramObject2 instanceof byte[]) {
/* 2279 */         inputStream2 = new ByteArrayInputStream((byte[])paramObject2);
/*      */       } else {
/* 2281 */         inputStream2 = (InputStream)paramObject2;
/*      */       } 
/*      */     } 
/*      */     
/* 2285 */     return new SequenceInputStream(inputStream1, inputStream2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] convertData(Object paramObject, final Transferable contents, final long format, final Map formatMap, boolean paramBoolean) throws IOException {
/* 2295 */     byte[] arrayOfByte = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2303 */     if (paramBoolean) { try {
/* 2304 */         final Stack<byte[]> stack = new Stack();
/* 2305 */         Runnable runnable = new Runnable() {
/*      */             private boolean done = false;
/*      */             
/*      */             public void run() {
/* 2309 */               if (this.done) {
/*      */                 return;
/*      */               }
/* 2312 */               byte[] arrayOfByte = null;
/*      */               try {
/* 2314 */                 DataFlavor dataFlavor = (DataFlavor)formatMap.get(Long.valueOf(format));
/* 2315 */                 if (dataFlavor != null) {
/* 2316 */                   arrayOfByte = DataTransferer.this.translateTransferable(contents, dataFlavor, format);
/*      */                 }
/* 2318 */               } catch (Exception exception) {
/* 2319 */                 exception.printStackTrace();
/* 2320 */                 arrayOfByte = null;
/*      */               } 
/*      */               try {
/* 2323 */                 DataTransferer.this.getToolkitThreadBlockedHandler().lock();
/* 2324 */                 stack.push(arrayOfByte);
/* 2325 */                 DataTransferer.this.getToolkitThreadBlockedHandler().exit();
/*      */               } finally {
/* 2327 */                 DataTransferer.this.getToolkitThreadBlockedHandler().unlock();
/* 2328 */                 this.done = true;
/*      */               } 
/*      */             }
/*      */           };
/*      */         
/* 2333 */         AppContext appContext = SunToolkit.targetToAppContext(paramObject);
/*      */         
/* 2335 */         getToolkitThreadBlockedHandler().lock();
/*      */         
/* 2337 */         if (appContext != null) {
/* 2338 */           appContext.put("DATA_CONVERTER_KEY", runnable);
/*      */         }
/*      */         
/* 2341 */         SunToolkit.executeOnEventHandlerThread(paramObject, runnable);
/*      */         
/* 2343 */         while (stack.empty()) {
/* 2344 */           getToolkitThreadBlockedHandler().enter();
/*      */         }
/*      */         
/* 2347 */         if (appContext != null) {
/* 2348 */           appContext.remove("DATA_CONVERTER_KEY");
/*      */         }
/*      */         
/* 2351 */         arrayOfByte = stack.pop();
/*      */       } finally {
/* 2353 */         getToolkitThreadBlockedHandler().unlock();
/*      */       }  }
/*      */     else
/* 2356 */     { DataFlavor dataFlavor = (DataFlavor)formatMap.get(Long.valueOf(format));
/* 2357 */       if (dataFlavor != null) {
/* 2358 */         arrayOfByte = translateTransferable(contents, dataFlavor, format);
/*      */       } }
/*      */ 
/*      */     
/* 2362 */     return arrayOfByte;
/*      */   }
/*      */   
/*      */   public void processDataConversionRequests() {
/* 2366 */     if (EventQueue.isDispatchThread()) {
/* 2367 */       AppContext appContext = AppContext.getAppContext();
/* 2368 */       getToolkitThreadBlockedHandler().lock();
/*      */       
/*      */       try {
/* 2371 */         Runnable runnable = (Runnable)appContext.get("DATA_CONVERTER_KEY");
/* 2372 */         if (runnable != null) {
/* 2373 */           runnable.run();
/* 2374 */           appContext.remove("DATA_CONVERTER_KEY");
/*      */         } 
/*      */       } finally {
/* 2377 */         getToolkitThreadBlockedHandler().unlock();
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
/*      */   
/*      */   public static long[] keysToLongArray(SortedMap paramSortedMap) {
/* 2392 */     Set set = paramSortedMap.keySet();
/* 2393 */     long[] arrayOfLong = new long[set.size()];
/* 2394 */     byte b = 0;
/* 2395 */     for (Iterator iterator = set.iterator(); iterator.hasNext(); b++) {
/* 2396 */       arrayOfLong[b] = ((Long)iterator.next()).longValue();
/*      */     }
/* 2398 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DataFlavor[] setToSortedDataFlavorArray(Set paramSet) {
/* 2406 */     DataFlavor[] arrayOfDataFlavor = new DataFlavor[paramSet.size()];
/* 2407 */     paramSet.toArray((Object[])arrayOfDataFlavor);
/* 2408 */     DataFlavorComparator dataFlavorComparator = new DataFlavorComparator(false);
/*      */     
/* 2410 */     Arrays.sort(arrayOfDataFlavor, dataFlavorComparator);
/* 2411 */     return arrayOfDataFlavor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static byte[] inputStreamToByteArray(InputStream paramInputStream) throws IOException {
/* 2420 */     try (ByteArrayOutputStream null = new ByteArrayOutputStream()) {
/* 2421 */       int i = 0;
/* 2422 */       byte[] arrayOfByte = new byte[8192];
/*      */       
/* 2424 */       while ((i = paramInputStream.read(arrayOfByte)) != -1) {
/* 2425 */         byteArrayOutputStream.write(arrayOfByte, 0, i);
/*      */       }
/*      */       
/* 2428 */       return byteArrayOutputStream.toByteArray();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LinkedHashSet<DataFlavor> getPlatformMappingsForNative(String paramString) {
/* 2438 */     return new LinkedHashSet<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LinkedHashSet<String> getPlatformMappingsForFlavor(DataFlavor paramDataFlavor) {
/* 2447 */     return new LinkedHashSet<>();
/*      */   }
/*      */   public abstract String getDefaultUnicodeEncoding();
/*      */   public abstract boolean isLocaleDependentTextFormat(long paramLong);
/*      */   public abstract boolean isFileFormat(long paramLong);
/*      */   public abstract boolean isImageFormat(long paramLong);
/*      */   protected abstract Long getFormatForNativeAsLong(String paramString);
/*      */   protected abstract String getNativeForFormat(long paramLong);
/*      */   protected abstract ByteArrayOutputStream convertFileListToBytes(ArrayList<String> paramArrayList) throws IOException;
/*      */   protected abstract String[] dragQueryFile(byte[] paramArrayOfbyte);
/*      */   
/*      */   protected abstract Image platformImageBytesToImage(byte[] paramArrayOfbyte, long paramLong) throws IOException;
/*      */   
/*      */   protected abstract byte[] imageToPlatformBytes(Image paramImage, long paramLong) throws IOException;
/*      */   
/*      */   public abstract ToolkitThreadBlockedHandler getToolkitThreadBlockedHandler();
/*      */   
/*      */   public static abstract class IndexedComparator implements Comparator { public static final boolean SELECT_BEST = true;
/*      */     public static final boolean SELECT_WORST = false;
/*      */     protected final boolean order;
/*      */     
/*      */     public IndexedComparator() {
/* 2469 */       this(true);
/*      */     }
/*      */     
/*      */     public IndexedComparator(boolean param1Boolean) {
/* 2473 */       this.order = param1Boolean;
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
/*      */ 
/*      */ 
/*      */     
/*      */     protected static int compareIndices(Map param1Map, Object param1Object1, Object param1Object2, Integer param1Integer) {
/* 2492 */       Integer integer1 = (Integer)param1Map.get(param1Object1);
/* 2493 */       Integer integer2 = (Integer)param1Map.get(param1Object2);
/*      */       
/* 2495 */       if (integer1 == null) {
/* 2496 */         integer1 = param1Integer;
/*      */       }
/* 2498 */       if (integer2 == null) {
/* 2499 */         integer2 = param1Integer;
/*      */       }
/*      */       
/* 2502 */       return integer1.compareTo(integer2);
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
/*      */ 
/*      */ 
/*      */     
/*      */     protected static int compareLongs(Map param1Map, Object param1Object1, Object param1Object2, Long param1Long) {
/* 2521 */       Long long_1 = (Long)param1Map.get(param1Object1);
/* 2522 */       Long long_2 = (Long)param1Map.get(param1Object2);
/*      */       
/* 2524 */       if (long_1 == null) {
/* 2525 */         long_1 = param1Long;
/*      */       }
/* 2527 */       if (long_2 == null) {
/* 2528 */         long_2 = param1Long;
/*      */       }
/*      */       
/* 2531 */       return long_1.compareTo(long_2);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class CharsetComparator
/*      */     extends IndexedComparator
/*      */   {
/*      */     private static final Map charsets;
/*      */ 
/*      */     
/*      */     private static String defaultEncoding;
/*      */ 
/*      */     
/* 2546 */     private static final Integer DEFAULT_CHARSET_INDEX = Integer.valueOf(2);
/* 2547 */     private static final Integer OTHER_CHARSET_INDEX = Integer.valueOf(1);
/* 2548 */     private static final Integer WORST_CHARSET_INDEX = Integer.valueOf(0);
/*      */     
/* 2550 */     private static final Integer UNSUPPORTED_CHARSET_INDEX = Integer.valueOf(-2147483648);
/*      */     
/*      */     private static final String UNSUPPORTED_CHARSET = "UNSUPPORTED";
/*      */     
/*      */     static {
/* 2555 */       HashMap<Object, Object> hashMap = new HashMap<>(8, 1.0F);
/*      */ 
/*      */       
/* 2558 */       hashMap.put(DataTransferer.canonicalName("UTF-16LE"), Integer.valueOf(4));
/* 2559 */       hashMap.put(DataTransferer.canonicalName("UTF-16BE"), Integer.valueOf(5));
/* 2560 */       hashMap.put(DataTransferer.canonicalName("UTF-8"), Integer.valueOf(6));
/* 2561 */       hashMap.put(DataTransferer.canonicalName("UTF-16"), Integer.valueOf(7));
/*      */ 
/*      */       
/* 2564 */       hashMap.put(DataTransferer.canonicalName("US-ASCII"), WORST_CHARSET_INDEX);
/*      */ 
/*      */       
/* 2567 */       String str = DataTransferer.canonicalName(DataTransferer.getDefaultTextCharset());
/*      */       
/* 2569 */       if (hashMap.get(defaultEncoding) == null) {
/* 2570 */         hashMap.put(defaultEncoding, DEFAULT_CHARSET_INDEX);
/*      */       }
/* 2572 */       hashMap.put("UNSUPPORTED", UNSUPPORTED_CHARSET_INDEX);
/*      */       
/* 2574 */       charsets = Collections.unmodifiableMap(hashMap);
/*      */     }
/*      */     
/*      */     public CharsetComparator() {
/* 2578 */       this(true);
/*      */     }
/*      */     
/*      */     public CharsetComparator(boolean param1Boolean) {
/* 2582 */       super(param1Boolean);
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
/*      */ 
/*      */ 
/*      */     
/*      */     public int compare(Object param1Object1, Object param1Object2) {
/* 2601 */       String str1 = null;
/* 2602 */       String str2 = null;
/* 2603 */       if (this.order == true) {
/* 2604 */         str1 = (String)param1Object1;
/* 2605 */         str2 = (String)param1Object2;
/*      */       } else {
/* 2607 */         str1 = (String)param1Object2;
/* 2608 */         str2 = (String)param1Object1;
/*      */       } 
/*      */       
/* 2611 */       return compareCharsets(str1, str2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int compareCharsets(String param1String1, String param1String2) {
/* 2638 */       param1String1 = getEncoding(param1String1);
/* 2639 */       param1String2 = getEncoding(param1String2);
/*      */       
/* 2641 */       int i = compareIndices(charsets, param1String1, param1String2, OTHER_CHARSET_INDEX);
/*      */ 
/*      */       
/* 2644 */       if (i == 0) {
/* 2645 */         return param1String2.compareTo(param1String1);
/*      */       }
/*      */       
/* 2648 */       return i;
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected static String getEncoding(String param1String) {
/* 2668 */       if (param1String == null)
/* 2669 */         return null; 
/* 2670 */       if (!DataTransferer.isEncodingSupported(param1String)) {
/* 2671 */         return "UNSUPPORTED";
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2677 */       String str = DataTransferer.canonicalName(param1String);
/* 2678 */       return charsets.containsKey(str) ? str : param1String;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class DataFlavorComparator
/*      */     extends IndexedComparator
/*      */   {
/*      */     private final DataTransferer.CharsetComparator charsetComparator;
/*      */ 
/*      */     
/*      */     private static final Map exactTypes;
/*      */ 
/*      */     
/*      */     private static final Map primaryTypes;
/*      */ 
/*      */     
/*      */     private static final Map nonTextRepresentations;
/*      */ 
/*      */     
/*      */     private static final Map textTypes;
/*      */ 
/*      */     
/*      */     private static final Map decodedTextRepresentations;
/*      */ 
/*      */     
/*      */     private static final Map encodedTextRepresentations;
/*      */     
/* 2707 */     private static final Integer UNKNOWN_OBJECT_LOSES = Integer.valueOf(-2147483648);
/*      */     
/* 2709 */     private static final Integer UNKNOWN_OBJECT_WINS = Integer.valueOf(2147483647);
/*      */ 
/*      */     
/* 2712 */     private static final Long UNKNOWN_OBJECT_LOSES_L = Long.valueOf(Long.MIN_VALUE);
/*      */     
/* 2714 */     private static final Long UNKNOWN_OBJECT_WINS_L = Long.valueOf(Long.MAX_VALUE);
/*      */ 
/*      */     
/*      */     static {
/* 2718 */       HashMap<Object, Object> hashMap = new HashMap<>(4, 1.0F);
/*      */ 
/*      */       
/* 2721 */       hashMap.put("application/x-java-file-list", 
/* 2722 */           Integer.valueOf(0));
/* 2723 */       hashMap.put("application/x-java-serialized-object", 
/* 2724 */           Integer.valueOf(1));
/* 2725 */       hashMap.put("application/x-java-jvm-local-objectref", 
/* 2726 */           Integer.valueOf(2));
/* 2727 */       hashMap.put("application/x-java-remote-object", 
/* 2728 */           Integer.valueOf(3));
/*      */       
/* 2730 */       exactTypes = Collections.unmodifiableMap(hashMap);
/*      */ 
/*      */ 
/*      */       
/* 2734 */       hashMap = new HashMap<>(1, 1.0F);
/*      */       
/* 2736 */       hashMap.put("application", Integer.valueOf(0));
/*      */       
/* 2738 */       primaryTypes = Collections.unmodifiableMap(hashMap);
/*      */ 
/*      */ 
/*      */       
/* 2742 */       hashMap = new HashMap<>(3, 1.0F);
/*      */       
/* 2744 */       hashMap.put(InputStream.class, 
/* 2745 */           Integer.valueOf(0));
/* 2746 */       hashMap.put(Serializable.class, 
/* 2747 */           Integer.valueOf(1));
/*      */       
/* 2749 */       Class<?> clazz = DataTransferer.RMI.remoteClass();
/* 2750 */       if (clazz != null) {
/* 2751 */         hashMap.put(clazz, 
/* 2752 */             Integer.valueOf(2));
/*      */       }
/*      */ 
/*      */       
/* 2756 */       nonTextRepresentations = Collections.unmodifiableMap(hashMap);
/*      */ 
/*      */ 
/*      */       
/* 2760 */       hashMap = new HashMap<>(16, 1.0F);
/*      */ 
/*      */       
/* 2763 */       hashMap.put("text/plain", Integer.valueOf(0));
/*      */ 
/*      */       
/* 2766 */       hashMap.put("application/x-java-serialized-object", 
/* 2767 */           Integer.valueOf(1));
/*      */ 
/*      */       
/* 2770 */       hashMap.put("text/calendar", Integer.valueOf(2));
/* 2771 */       hashMap.put("text/css", Integer.valueOf(3));
/* 2772 */       hashMap.put("text/directory", Integer.valueOf(4));
/* 2773 */       hashMap.put("text/parityfec", Integer.valueOf(5));
/* 2774 */       hashMap.put("text/rfc822-headers", Integer.valueOf(6));
/* 2775 */       hashMap.put("text/t140", Integer.valueOf(7));
/* 2776 */       hashMap.put("text/tab-separated-values", Integer.valueOf(8));
/* 2777 */       hashMap.put("text/uri-list", Integer.valueOf(9));
/*      */ 
/*      */       
/* 2780 */       hashMap.put("text/richtext", Integer.valueOf(10));
/* 2781 */       hashMap.put("text/enriched", Integer.valueOf(11));
/* 2782 */       hashMap.put("text/rtf", Integer.valueOf(12));
/*      */ 
/*      */       
/* 2785 */       hashMap.put("text/html", Integer.valueOf(13));
/* 2786 */       hashMap.put("text/xml", Integer.valueOf(14));
/* 2787 */       hashMap.put("text/sgml", Integer.valueOf(15));
/*      */       
/* 2789 */       textTypes = Collections.unmodifiableMap(hashMap);
/*      */ 
/*      */ 
/*      */       
/* 2793 */       hashMap = new HashMap<>(4, 1.0F);
/*      */       
/* 2795 */       hashMap
/* 2796 */         .put(char[].class, Integer.valueOf(0));
/* 2797 */       hashMap
/* 2798 */         .put(CharBuffer.class, Integer.valueOf(1));
/* 2799 */       hashMap
/* 2800 */         .put(String.class, Integer.valueOf(2));
/* 2801 */       hashMap
/* 2802 */         .put(Reader.class, Integer.valueOf(3));
/*      */ 
/*      */       
/* 2805 */       decodedTextRepresentations = Collections.unmodifiableMap(hashMap);
/*      */ 
/*      */ 
/*      */       
/* 2809 */       hashMap = new HashMap<>(3, 1.0F);
/*      */       
/* 2811 */       hashMap
/* 2812 */         .put(byte[].class, Integer.valueOf(0));
/* 2813 */       hashMap
/* 2814 */         .put(ByteBuffer.class, Integer.valueOf(1));
/* 2815 */       hashMap
/* 2816 */         .put(InputStream.class, Integer.valueOf(2));
/*      */ 
/*      */       
/* 2819 */       encodedTextRepresentations = Collections.unmodifiableMap(hashMap);
/*      */     }
/*      */ 
/*      */     
/*      */     public DataFlavorComparator() {
/* 2824 */       this(true);
/*      */     }
/*      */     
/*      */     public DataFlavorComparator(boolean param1Boolean) {
/* 2828 */       super(param1Boolean);
/*      */       
/* 2830 */       this.charsetComparator = new DataTransferer.CharsetComparator(param1Boolean);
/*      */     }
/*      */     
/*      */     public int compare(Object param1Object1, Object param1Object2) {
/* 2834 */       DataFlavor dataFlavor1 = null;
/* 2835 */       DataFlavor dataFlavor2 = null;
/* 2836 */       if (this.order == true) {
/* 2837 */         dataFlavor1 = (DataFlavor)param1Object1;
/* 2838 */         dataFlavor2 = (DataFlavor)param1Object2;
/*      */       } else {
/* 2840 */         dataFlavor1 = (DataFlavor)param1Object2;
/* 2841 */         dataFlavor2 = (DataFlavor)param1Object1;
/*      */       } 
/*      */       
/* 2844 */       if (dataFlavor1.equals(dataFlavor2)) {
/* 2845 */         return 0;
/*      */       }
/*      */       
/* 2848 */       int i = 0;
/*      */       
/* 2850 */       String str1 = dataFlavor1.getPrimaryType();
/* 2851 */       String str2 = dataFlavor1.getSubType();
/* 2852 */       String str3 = str1 + "/" + str2;
/* 2853 */       Class<?> clazz1 = dataFlavor1.getRepresentationClass();
/*      */       
/* 2855 */       String str4 = dataFlavor2.getPrimaryType();
/* 2856 */       String str5 = dataFlavor2.getSubType();
/* 2857 */       String str6 = str4 + "/" + str5;
/* 2858 */       Class<?> clazz2 = dataFlavor2.getRepresentationClass();
/*      */       
/* 2860 */       if (dataFlavor1.isFlavorTextType() && dataFlavor2.isFlavorTextType()) {
/*      */         
/* 2862 */         i = compareIndices(textTypes, str3, str6, UNKNOWN_OBJECT_LOSES);
/*      */         
/* 2864 */         if (i != 0) {
/* 2865 */           return i;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2875 */         if (DataTransferer.doesSubtypeSupportCharset(dataFlavor1)) {
/*      */ 
/*      */           
/* 2878 */           i = compareIndices(decodedTextRepresentations, clazz1, clazz2, UNKNOWN_OBJECT_LOSES);
/*      */           
/* 2880 */           if (i != 0) {
/* 2881 */             return i;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 2886 */           i = this.charsetComparator.compareCharsets(DataTransferer.getTextCharset(dataFlavor1), 
/* 2887 */               DataTransferer.getTextCharset(dataFlavor2));
/* 2888 */           if (i != 0) {
/* 2889 */             return i;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2895 */         i = compareIndices(encodedTextRepresentations, clazz1, clazz2, UNKNOWN_OBJECT_LOSES);
/*      */         
/* 2897 */         if (i != 0) {
/* 2898 */           return i;
/*      */         }
/*      */       } else {
/*      */         
/* 2902 */         if (dataFlavor1.isFlavorTextType()) {
/* 2903 */           return 1;
/*      */         }
/*      */         
/* 2906 */         if (dataFlavor2.isFlavorTextType()) {
/* 2907 */           return -1;
/*      */         }
/*      */ 
/*      */         
/* 2911 */         i = compareIndices(primaryTypes, str1, str4, UNKNOWN_OBJECT_LOSES);
/*      */         
/* 2913 */         if (i != 0) {
/* 2914 */           return i;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2920 */         i = compareIndices(exactTypes, str3, str6, UNKNOWN_OBJECT_WINS);
/*      */         
/* 2922 */         if (i != 0) {
/* 2923 */           return i;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2928 */         i = compareIndices(nonTextRepresentations, clazz1, clazz2, UNKNOWN_OBJECT_LOSES);
/*      */         
/* 2930 */         if (i != 0) {
/* 2931 */           return i;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2937 */       return dataFlavor1.getMimeType().compareTo(dataFlavor2.getMimeType());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class IndexOrderComparator
/*      */     extends IndexedComparator
/*      */   {
/*      */     private final Map indexMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2957 */     private static final Integer FALLBACK_INDEX = Integer.valueOf(-2147483648);
/*      */     
/*      */     public IndexOrderComparator(Map param1Map) {
/* 2960 */       super(true);
/* 2961 */       this.indexMap = param1Map;
/*      */     }
/*      */     
/*      */     public IndexOrderComparator(Map param1Map, boolean param1Boolean) {
/* 2965 */       super(param1Boolean);
/* 2966 */       this.indexMap = param1Map;
/*      */     }
/*      */     
/*      */     public int compare(Object param1Object1, Object param1Object2) {
/* 2970 */       if (!this.order) {
/* 2971 */         return -compareIndices(this.indexMap, param1Object1, param1Object2, FALLBACK_INDEX);
/*      */       }
/* 2973 */       return compareIndices(this.indexMap, param1Object1, param1Object2, FALLBACK_INDEX);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class RMI
/*      */   {
/* 2983 */     private static final Class<?> remoteClass = getClass("java.rmi.Remote");
/*      */     
/* 2985 */     private static final Class<?> marshallObjectClass = getClass("java.rmi.MarshalledObject");
/*      */     
/* 2987 */     private static final Constructor<?> marshallCtor = getConstructor(marshallObjectClass, new Class[] { Object.class });
/*      */     
/* 2989 */     private static final Method marshallGet = getMethod(marshallObjectClass, "get", new Class[0]);
/*      */     
/*      */     private static Class<?> getClass(String param1String) {
/*      */       try {
/* 2993 */         return Class.forName(param1String, true, null);
/* 2994 */       } catch (ClassNotFoundException classNotFoundException) {
/* 2995 */         return null;
/*      */       } 
/*      */     }
/*      */     
/*      */     private static Constructor<?> getConstructor(Class<?> param1Class, Class<?>... param1VarArgs) {
/*      */       try {
/* 3001 */         return (param1Class == null) ? null : param1Class.getDeclaredConstructor(param1VarArgs);
/* 3002 */       } catch (NoSuchMethodException noSuchMethodException) {
/* 3003 */         throw new AssertionError(noSuchMethodException);
/*      */       } 
/*      */     }
/*      */     
/*      */     private static Method getMethod(Class<?> param1Class, String param1String, Class<?>... param1VarArgs) {
/*      */       try {
/* 3009 */         return (param1Class == null) ? null : param1Class.getMethod(param1String, param1VarArgs);
/* 3010 */       } catch (NoSuchMethodException noSuchMethodException) {
/* 3011 */         throw new AssertionError(noSuchMethodException);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static boolean isRemote(Class<?> param1Class) {
/* 3019 */       return ((remoteClass == null) ? null : Boolean.valueOf(remoteClass.isAssignableFrom(param1Class))).booleanValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static Class<?> remoteClass() {
/* 3026 */       return remoteClass;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static Object newMarshalledObject(Object param1Object) throws IOException {
/*      */       try {
/* 3035 */         return marshallCtor.newInstance(new Object[] { param1Object });
/* 3036 */       } catch (InstantiationException instantiationException) {
/* 3037 */         throw new AssertionError(instantiationException);
/* 3038 */       } catch (IllegalAccessException illegalAccessException) {
/* 3039 */         throw new AssertionError(illegalAccessException);
/* 3040 */       } catch (InvocationTargetException invocationTargetException) {
/* 3041 */         Throwable throwable = invocationTargetException.getCause();
/* 3042 */         if (throwable instanceof IOException)
/* 3043 */           throw (IOException)throwable; 
/* 3044 */         throw new AssertionError(invocationTargetException);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static Object getMarshalledObject(Object param1Object) throws IOException, ClassNotFoundException {
/*      */       try {
/* 3055 */         return marshallGet.invoke(param1Object, new Object[0]);
/* 3056 */       } catch (IllegalAccessException illegalAccessException) {
/* 3057 */         throw new AssertionError(illegalAccessException);
/* 3058 */       } catch (InvocationTargetException invocationTargetException) {
/* 3059 */         Throwable throwable = invocationTargetException.getCause();
/* 3060 */         if (throwable instanceof IOException)
/* 3061 */           throw (IOException)throwable; 
/* 3062 */         if (throwable instanceof ClassNotFoundException)
/* 3063 */           throw (ClassNotFoundException)throwable; 
/* 3064 */         throw new AssertionError(invocationTargetException);
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/datatransfer/DataTransferer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */