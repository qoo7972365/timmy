/*     */ package com.sun.imageio.plugins.gif;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import javax.imageio.ImageTypeSpecifier;
/*     */ import javax.imageio.metadata.IIOMetadataFormat;
/*     */ import javax.imageio.metadata.IIOMetadataFormatImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GIFStreamMetadataFormat
/*     */   extends IIOMetadataFormatImpl
/*     */ {
/*  35 */   private static IIOMetadataFormat instance = null;
/*     */   
/*     */   private GIFStreamMetadataFormat() {
/*  38 */     super("javax_imageio_gif_stream_1.0", 2);
/*     */ 
/*     */ 
/*     */     
/*  42 */     addElement("Version", "javax_imageio_gif_stream_1.0", 0);
/*     */     
/*  44 */     addAttribute("Version", "value", 0, true, (String)null, 
/*     */         
/*  46 */         Arrays.asList(GIFStreamMetadata.versionStrings));
/*     */ 
/*     */     
/*  49 */     addElement("LogicalScreenDescriptor", "javax_imageio_gif_stream_1.0", 0);
/*     */ 
/*     */     
/*  52 */     addAttribute("LogicalScreenDescriptor", "logicalScreenWidth", 2, true, null, "1", "65535", true, true);
/*     */ 
/*     */     
/*  55 */     addAttribute("LogicalScreenDescriptor", "logicalScreenHeight", 2, true, null, "1", "65535", true, true);
/*     */ 
/*     */     
/*  58 */     addAttribute("LogicalScreenDescriptor", "colorResolution", 2, true, null, "1", "8", true, true);
/*     */ 
/*     */     
/*  61 */     addAttribute("LogicalScreenDescriptor", "pixelAspectRatio", 2, true, null, "0", "255", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     addElement("GlobalColorTable", "javax_imageio_gif_stream_1.0", 2, 256);
/*     */ 
/*     */     
/*  69 */     addAttribute("GlobalColorTable", "sizeOfGlobalColorTable", 2, true, (String)null, 
/*     */         
/*  71 */         Arrays.asList(GIFStreamMetadata.colorTableSizes));
/*  72 */     addAttribute("GlobalColorTable", "backgroundColorIndex", 2, true, null, "0", "255", true, true);
/*     */ 
/*     */     
/*  75 */     addBooleanAttribute("GlobalColorTable", "sortFlag", false, false);
/*     */ 
/*     */ 
/*     */     
/*  79 */     addElement("ColorTableEntry", "GlobalColorTable", 0);
/*     */     
/*  81 */     addAttribute("ColorTableEntry", "index", 2, true, null, "0", "255", true, true);
/*     */ 
/*     */     
/*  84 */     addAttribute("ColorTableEntry", "red", 2, true, null, "0", "255", true, true);
/*     */ 
/*     */     
/*  87 */     addAttribute("ColorTableEntry", "green", 2, true, null, "0", "255", true, true);
/*     */ 
/*     */     
/*  90 */     addAttribute("ColorTableEntry", "blue", 2, true, null, "0", "255", true, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canNodeAppear(String paramString, ImageTypeSpecifier paramImageTypeSpecifier) {
/*  97 */     return true;
/*     */   }
/*     */   
/*     */   public static synchronized IIOMetadataFormat getInstance() {
/* 101 */     if (instance == null) {
/* 102 */       instance = new GIFStreamMetadataFormat();
/*     */     }
/* 104 */     return instance;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/gif/GIFStreamMetadataFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */