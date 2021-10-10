/*     */ package com.sun.imageio.plugins.common;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import javax.imageio.ImageTypeSpecifier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardMetadataFormat
/*     */   extends IIOMetadataFormatImpl
/*     */ {
/*     */   private void addSingleAttributeElement(String paramString1, String paramString2, int paramInt) {
/*  39 */     addElement(paramString1, paramString2, 0);
/*  40 */     addAttribute(paramString1, "value", paramInt, true, null);
/*     */   }
/*     */   
/*     */   public StandardMetadataFormat() {
/*  44 */     super("javax_imageio_1.0", 2);
/*     */ 
/*     */ 
/*     */     
/*  48 */     addElement("Chroma", "javax_imageio_1.0", 2);
/*     */ 
/*     */ 
/*     */     
/*  52 */     addElement("ColorSpaceType", "Chroma", 0);
/*     */ 
/*     */     
/*  55 */     ArrayList<String> arrayList = new ArrayList();
/*  56 */     arrayList.add("XYZ");
/*  57 */     arrayList.add("Lab");
/*  58 */     arrayList.add("Luv");
/*  59 */     arrayList.add("YCbCr");
/*  60 */     arrayList.add("Yxy");
/*  61 */     arrayList.add("YCCK");
/*  62 */     arrayList.add("PhotoYCC");
/*  63 */     arrayList.add("RGB");
/*  64 */     arrayList.add("GRAY");
/*  65 */     arrayList.add("HSV");
/*  66 */     arrayList.add("HLS");
/*  67 */     arrayList.add("CMYK");
/*  68 */     arrayList.add("CMY");
/*  69 */     arrayList.add("2CLR");
/*  70 */     arrayList.add("3CLR");
/*  71 */     arrayList.add("4CLR");
/*  72 */     arrayList.add("5CLR");
/*  73 */     arrayList.add("6CLR");
/*  74 */     arrayList.add("7CLR");
/*  75 */     arrayList.add("8CLR");
/*  76 */     arrayList.add("9CLR");
/*  77 */     arrayList.add("ACLR");
/*  78 */     arrayList.add("BCLR");
/*  79 */     arrayList.add("CCLR");
/*  80 */     arrayList.add("DCLR");
/*  81 */     arrayList.add("ECLR");
/*  82 */     arrayList.add("FCLR");
/*  83 */     addAttribute("ColorSpaceType", "name", 0, true, (String)null, arrayList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     addElement("NumChannels", "Chroma", 0);
/*     */     
/*  93 */     addAttribute("NumChannels", "value", 2, true, 0, 2147483647);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     addElement("Gamma", "Chroma", 0);
/* 100 */     addAttribute("Gamma", "value", 3, true, null);
/*     */ 
/*     */ 
/*     */     
/* 104 */     addElement("BlackIsZero", "Chroma", 0);
/* 105 */     addBooleanAttribute("BlackIsZero", "value", true, true);
/*     */ 
/*     */     
/* 108 */     addElement("Palette", "Chroma", 0, 2147483647);
/*     */ 
/*     */     
/* 111 */     addElement("PaletteEntry", "Palette", 0);
/* 112 */     addAttribute("PaletteEntry", "index", 2, true, null);
/*     */     
/* 114 */     addAttribute("PaletteEntry", "red", 2, true, null);
/*     */     
/* 116 */     addAttribute("PaletteEntry", "green", 2, true, null);
/*     */     
/* 118 */     addAttribute("PaletteEntry", "blue", 2, true, null);
/*     */     
/* 120 */     addAttribute("PaletteEntry", "alpha", 2, false, "255");
/*     */ 
/*     */ 
/*     */     
/* 124 */     addElement("BackgroundIndex", "Chroma", 0);
/* 125 */     addAttribute("BackgroundIndex", "value", 2, true, null);
/*     */ 
/*     */ 
/*     */     
/* 129 */     addElement("BackgroundColor", "Chroma", 0);
/* 130 */     addAttribute("BackgroundColor", "red", 2, true, null);
/*     */     
/* 132 */     addAttribute("BackgroundColor", "green", 2, true, null);
/*     */     
/* 134 */     addAttribute("BackgroundColor", "blue", 2, true, null);
/*     */ 
/*     */ 
/*     */     
/* 138 */     addElement("Compression", "javax_imageio_1.0", 2);
/*     */ 
/*     */ 
/*     */     
/* 142 */     addSingleAttributeElement("CompressionTypeName", "Compression", 0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     addElement("Lossless", "Compression", 0);
/* 148 */     addBooleanAttribute("Lossless", "value", true, true);
/*     */ 
/*     */     
/* 151 */     addSingleAttributeElement("NumProgressiveScans", "Compression", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     addSingleAttributeElement("BitRate", "Compression", 3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     addElement("Data", "javax_imageio_1.0", 2);
/*     */ 
/*     */ 
/*     */     
/* 165 */     addElement("PlanarConfiguration", "Data", 0);
/*     */     
/* 167 */     arrayList = new ArrayList<>();
/* 168 */     arrayList.add("PixelInterleaved");
/* 169 */     arrayList.add("PlaneInterleaved");
/* 170 */     arrayList.add("LineInterleaved");
/* 171 */     arrayList.add("TileInterleaved");
/* 172 */     addAttribute("PlanarConfiguration", "value", 0, true, (String)null, arrayList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     addElement("SampleFormat", "Data", 0);
/*     */     
/* 181 */     arrayList = new ArrayList<>();
/* 182 */     arrayList.add("SignedIntegral");
/* 183 */     arrayList.add("UnsignedIntegral");
/* 184 */     arrayList.add("Real");
/* 185 */     arrayList.add("Index");
/* 186 */     addAttribute("SampleFormat", "value", 0, true, (String)null, arrayList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     addElement("BitsPerSample", "Data", 0);
/*     */     
/* 195 */     addAttribute("BitsPerSample", "value", 2, true, 1, 2147483647);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     addElement("SignificantBitsPerSample", "Data", 0);
/* 202 */     addAttribute("SignificantBitsPerSample", "value", 2, true, 1, 2147483647);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     addElement("SampleMSB", "Data", 0);
/*     */     
/* 210 */     addAttribute("SampleMSB", "value", 2, true, 1, 2147483647);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     addElement("Dimension", "javax_imageio_1.0", 2);
/*     */ 
/*     */ 
/*     */     
/* 220 */     addSingleAttributeElement("PixelAspectRatio", "Dimension", 3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     addElement("ImageOrientation", "Dimension", 0);
/*     */ 
/*     */     
/* 228 */     arrayList = new ArrayList<>();
/* 229 */     arrayList.add("Normal");
/* 230 */     arrayList.add("Rotate90");
/* 231 */     arrayList.add("Rotate180");
/* 232 */     arrayList.add("Rotate270");
/* 233 */     arrayList.add("FlipH");
/* 234 */     arrayList.add("FlipV");
/* 235 */     arrayList.add("FlipHRotate90");
/* 236 */     arrayList.add("FlipVRotate90");
/* 237 */     addAttribute("ImageOrientation", "value", 0, true, (String)null, arrayList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 244 */     addSingleAttributeElement("HorizontalPixelSize", "Dimension", 3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     addSingleAttributeElement("VerticalPixelSize", "Dimension", 3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 254 */     addSingleAttributeElement("HorizontalPhysicalPixelSpacing", "Dimension", 3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     addSingleAttributeElement("VerticalPhysicalPixelSpacing", "Dimension", 3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 264 */     addSingleAttributeElement("HorizontalPosition", "Dimension", 3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     addSingleAttributeElement("VerticalPosition", "Dimension", 3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 274 */     addSingleAttributeElement("HorizontalPixelOffset", "Dimension", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 279 */     addSingleAttributeElement("VerticalPixelOffset", "Dimension", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     addSingleAttributeElement("HorizontalScreenSize", "Dimension", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     addSingleAttributeElement("VerticalScreenSize", "Dimension", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 295 */     addElement("Document", "javax_imageio_1.0", 2);
/*     */ 
/*     */ 
/*     */     
/* 299 */     addElement("FormatVersion", "Document", 0);
/*     */     
/* 301 */     addAttribute("FormatVersion", "value", 0, true, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     addElement("SubimageInterpretation", "Document", 0);
/*     */     
/* 309 */     arrayList = new ArrayList<>();
/* 310 */     arrayList.add("Standalone");
/* 311 */     arrayList.add("SinglePage");
/* 312 */     arrayList.add("FullResolution");
/* 313 */     arrayList.add("ReducedResolution");
/* 314 */     arrayList.add("PyramidLayer");
/* 315 */     arrayList.add("Preview");
/* 316 */     arrayList.add("VolumeSlice");
/* 317 */     arrayList.add("ObjectView");
/* 318 */     arrayList.add("Panorama");
/* 319 */     arrayList.add("AnimationFrame");
/* 320 */     arrayList.add("TransparencyMask");
/* 321 */     arrayList.add("CompositingLayer");
/* 322 */     arrayList.add("SpectralSlice");
/* 323 */     arrayList.add("Unknown");
/* 324 */     addAttribute("SubimageInterpretation", "value", 0, true, (String)null, arrayList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 331 */     addElement("ImageCreationTime", "Document", 0);
/*     */     
/* 333 */     addAttribute("ImageCreationTime", "year", 2, true, null);
/*     */ 
/*     */ 
/*     */     
/* 337 */     addAttribute("ImageCreationTime", "month", 2, true, null, "1", "12", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 342 */     addAttribute("ImageCreationTime", "day", 2, true, null, "1", "31", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 347 */     addAttribute("ImageCreationTime", "hour", 2, false, "0", "0", "23", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 352 */     addAttribute("ImageCreationTime", "minute", 2, false, "0", "0", "59", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 358 */     addAttribute("ImageCreationTime", "second", 2, false, "0", "0", "60", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 365 */     addElement("ImageModificationTime", "Document", 0);
/*     */     
/* 367 */     addAttribute("ImageModificationTime", "year", 2, true, null);
/*     */ 
/*     */ 
/*     */     
/* 371 */     addAttribute("ImageModificationTime", "month", 2, true, null, "1", "12", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 376 */     addAttribute("ImageModificationTime", "day", 2, true, null, "1", "31", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 381 */     addAttribute("ImageModificationTime", "hour", 2, false, "0", "0", "23", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 386 */     addAttribute("ImageModificationTime", "minute", 2, false, "0", "0", "59", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 392 */     addAttribute("ImageModificationTime", "second", 2, false, "0", "0", "60", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 399 */     addElement("Text", "javax_imageio_1.0", 0, 2147483647);
/*     */ 
/*     */ 
/*     */     
/* 403 */     addElement("TextEntry", "Text", 0);
/* 404 */     addAttribute("TextEntry", "keyword", 0, false, null);
/*     */ 
/*     */ 
/*     */     
/* 408 */     addAttribute("TextEntry", "value", 0, true, null);
/*     */ 
/*     */ 
/*     */     
/* 412 */     addAttribute("TextEntry", "language", 0, false, null);
/*     */ 
/*     */ 
/*     */     
/* 416 */     addAttribute("TextEntry", "encoding", 0, false, null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 421 */     arrayList = new ArrayList<>();
/* 422 */     arrayList.add("none");
/* 423 */     arrayList.add("lzw");
/* 424 */     arrayList.add("zip");
/* 425 */     arrayList.add("bzip");
/* 426 */     arrayList.add("other");
/* 427 */     addAttribute("TextEntry", "compression", 0, false, "none", arrayList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 434 */     addElement("Transparency", "javax_imageio_1.0", 2);
/*     */ 
/*     */ 
/*     */     
/* 438 */     addElement("Alpha", "Transparency", 0);
/*     */     
/* 440 */     arrayList = new ArrayList<>();
/* 441 */     arrayList.add("none");
/* 442 */     arrayList.add("premultiplied");
/* 443 */     arrayList.add("nonpremultiplied");
/* 444 */     addAttribute("Alpha", "value", 0, false, "none", arrayList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 451 */     addSingleAttributeElement("TransparentIndex", "Transparency", 2);
/*     */ 
/*     */ 
/*     */     
/* 455 */     addElement("TransparentColor", "Transparency", 0);
/*     */     
/* 457 */     addAttribute("TransparentColor", "value", 2, true, 0, 2147483647);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 463 */     addElement("TileTransparencies", "Transparency", 0, 2147483647);
/*     */ 
/*     */ 
/*     */     
/* 467 */     addElement("TransparentTile", "TileTransparencies", 0);
/*     */     
/* 469 */     addAttribute("TransparentTile", "x", 2, true, null);
/*     */ 
/*     */ 
/*     */     
/* 473 */     addAttribute("TransparentTile", "y", 2, true, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 479 */     addElement("TileOpacities", "Transparency", 0, 2147483647);
/*     */ 
/*     */ 
/*     */     
/* 483 */     addElement("OpaqueTile", "TileOpacities", 0);
/*     */     
/* 485 */     addAttribute("OpaqueTile", "x", 2, true, null);
/*     */ 
/*     */ 
/*     */     
/* 489 */     addAttribute("OpaqueTile", "y", 2, true, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canNodeAppear(String paramString, ImageTypeSpecifier paramImageTypeSpecifier) {
/* 497 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/common/StandardMetadataFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */