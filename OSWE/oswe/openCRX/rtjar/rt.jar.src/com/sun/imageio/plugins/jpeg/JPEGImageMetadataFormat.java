/*     */ package com.sun.imageio.plugins.jpeg;
/*     */ 
/*     */ import java.awt.color.ICC_Profile;
/*     */ import java.util.ArrayList;
/*     */ import javax.imageio.ImageTypeSpecifier;
/*     */ import javax.imageio.metadata.IIOMetadataFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JPEGImageMetadataFormat
/*     */   extends JPEGMetadataFormat
/*     */ {
/*  41 */   private static JPEGImageMetadataFormat theInstance = null;
/*     */   
/*     */   private JPEGImageMetadataFormat() {
/*  44 */     super("javax_imageio_jpeg_image_1.0", 1);
/*     */ 
/*     */     
/*  47 */     addElement("JPEGvariety", "javax_imageio_jpeg_image_1.0", 3);
/*     */ 
/*     */ 
/*     */     
/*  51 */     addElement("markerSequence", "javax_imageio_jpeg_image_1.0", 4);
/*     */ 
/*     */ 
/*     */     
/*  55 */     addElement("app0JFIF", "JPEGvariety", 2);
/*     */     
/*  57 */     addStreamElements("markerSequence");
/*     */     
/*  59 */     addElement("app14Adobe", "markerSequence", 0);
/*     */     
/*  61 */     addElement("sof", "markerSequence", 1, 4);
/*     */     
/*  63 */     addElement("sos", "markerSequence", 1, 4);
/*     */     
/*  65 */     addElement("JFXX", "app0JFIF", 1, 2147483647);
/*     */     
/*  67 */     addElement("app0JFXX", "JFXX", 3);
/*     */     
/*  69 */     addElement("app2ICC", "app0JFIF", 0);
/*     */     
/*  71 */     addAttribute("app0JFIF", "majorVersion", 2, false, "1", "0", "255", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     addAttribute("app0JFIF", "minorVersion", 2, false, "2", "0", "255", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     ArrayList<String> arrayList1 = new ArrayList();
/*  86 */     arrayList1.add("0");
/*  87 */     arrayList1.add("1");
/*  88 */     arrayList1.add("2");
/*  89 */     addAttribute("app0JFIF", "resUnits", 2, false, "0", arrayList1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     addAttribute("app0JFIF", "Xdensity", 2, false, "1", "1", "65535", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     addAttribute("app0JFIF", "Ydensity", 2, false, "1", "1", "65535", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     addAttribute("app0JFIF", "thumbWidth", 2, false, "0", "0", "255", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     addAttribute("app0JFIF", "thumbHeight", 2, false, "0", "0", "255", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     addElement("JFIFthumbJPEG", "app0JFXX", 2);
/* 125 */     addElement("JFIFthumbPalette", "app0JFXX", 0);
/* 126 */     addElement("JFIFthumbRGB", "app0JFXX", 0);
/*     */     
/* 128 */     ArrayList<String> arrayList2 = new ArrayList();
/* 129 */     arrayList2.add("16");
/* 130 */     arrayList2.add("17");
/* 131 */     arrayList2.add("19");
/* 132 */     addAttribute("app0JFXX", "extensionCode", 2, false, (String)null, arrayList2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     addChildElement("markerSequence", "JFIFthumbJPEG");
/*     */     
/* 141 */     addAttribute("JFIFthumbPalette", "thumbWidth", 2, false, null, "0", "255", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     addAttribute("JFIFthumbPalette", "thumbHeight", 2, false, null, "0", "255", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     addAttribute("JFIFthumbRGB", "thumbWidth", 2, false, null, "0", "255", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     addAttribute("JFIFthumbRGB", "thumbHeight", 2, false, null, "0", "255", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     addObjectValue("app2ICC", ICC_Profile.class, false, (ICC_Profile)null);
/*     */     
/* 173 */     addAttribute("app14Adobe", "version", 2, false, "100", "100", "255", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     addAttribute("app14Adobe", "flags0", 2, false, "0", "0", "65535", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     addAttribute("app14Adobe", "flags1", 2, false, "0", "0", "65535", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     ArrayList<String> arrayList3 = new ArrayList();
/* 196 */     arrayList3.add("0");
/* 197 */     arrayList3.add("1");
/* 198 */     arrayList3.add("2");
/* 199 */     addAttribute("app14Adobe", "transform", 2, true, (String)null, arrayList3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     addElement("componentSpec", "sof", 0);
/*     */     
/* 208 */     ArrayList<String> arrayList4 = new ArrayList();
/* 209 */     arrayList4.add("0");
/* 210 */     arrayList4.add("1");
/* 211 */     arrayList4.add("2");
/* 212 */     addAttribute("sof", "process", 2, false, (String)null, arrayList4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     addAttribute("sof", "samplePrecision", 2, false, "8");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     addAttribute("sof", "numLines", 2, false, null, "0", "65535", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     addAttribute("sof", "samplesPerLine", 2, false, null, "0", "65535", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     ArrayList<String> arrayList5 = new ArrayList();
/* 238 */     arrayList5.add("1");
/* 239 */     arrayList5.add("2");
/* 240 */     arrayList5.add("3");
/* 241 */     arrayList5.add("4");
/* 242 */     addAttribute("sof", "numFrameComponents", 2, false, (String)null, arrayList5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     addAttribute("componentSpec", "componentId", 2, true, null, "0", "255", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 256 */     addAttribute("componentSpec", "HsamplingFactor", 2, true, null, "1", "255", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 263 */     addAttribute("componentSpec", "VsamplingFactor", 2, true, null, "1", "255", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 270 */     ArrayList<String> arrayList6 = new ArrayList();
/* 271 */     arrayList6.add("0");
/* 272 */     arrayList6.add("1");
/* 273 */     arrayList6.add("2");
/* 274 */     arrayList6.add("3");
/* 275 */     addAttribute("componentSpec", "QtableSelector", 2, true, (String)null, arrayList6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 282 */     addElement("scanComponentSpec", "sos", 0);
/*     */     
/* 284 */     addAttribute("sos", "numScanComponents", 2, true, (String)null, arrayList5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 290 */     addAttribute("sos", "startSpectralSelection", 2, false, "0", "0", "63", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 297 */     addAttribute("sos", "endSpectralSelection", 2, false, "63", "0", "63", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     addAttribute("sos", "approxHigh", 2, false, "0", "0", "15", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 311 */     addAttribute("sos", "approxLow", 2, false, "0", "0", "15", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 319 */     addAttribute("scanComponentSpec", "componentSelector", 2, true, null, "0", "255", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 326 */     addAttribute("scanComponentSpec", "dcHuffTable", 2, true, (String)null, arrayList6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 332 */     addAttribute("scanComponentSpec", "acHuffTable", 2, true, (String)null, arrayList6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canNodeAppear(String paramString, ImageTypeSpecifier paramImageTypeSpecifier) {
/* 343 */     if (paramString.equals(getRootName()) || paramString
/* 344 */       .equals("JPEGvariety") || 
/* 345 */       isInSubtree(paramString, "markerSequence")) {
/* 346 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 351 */     if (isInSubtree(paramString, "app0JFIF") && 
/* 352 */       JPEG.isJFIFcompliant(paramImageTypeSpecifier, true)) {
/* 353 */       return true;
/*     */     }
/*     */     
/* 356 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized IIOMetadataFormat getInstance() {
/* 361 */     if (theInstance == null) {
/* 362 */       theInstance = new JPEGImageMetadataFormat();
/*     */     }
/* 364 */     return theInstance;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/JPEGImageMetadataFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */