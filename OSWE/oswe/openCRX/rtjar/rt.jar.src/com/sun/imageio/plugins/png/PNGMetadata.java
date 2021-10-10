/*      */ package com.sun.imageio.plugins.png;
/*      */ 
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.IndexColorModel;
/*      */ import java.awt.image.SampleModel;
/*      */ import java.util.ArrayList;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.imageio.ImageTypeSpecifier;
/*      */ import javax.imageio.metadata.IIOInvalidTreeException;
/*      */ import javax.imageio.metadata.IIOMetadata;
/*      */ import javax.imageio.metadata.IIOMetadataNode;
/*      */ import org.w3c.dom.Node;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PNGMetadata
/*      */   extends IIOMetadata
/*      */   implements Cloneable
/*      */ {
/*      */   public static final String nativeMetadataFormatName = "javax_imageio_png_1.0";
/*      */   protected static final String nativeMetadataFormatClassName = "com.sun.imageio.plugins.png.PNGMetadataFormat";
/*   50 */   static final String[] IHDR_colorTypeNames = new String[] { "Grayscale", null, "RGB", "Palette", "GrayAlpha", null, "RGBAlpha" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   55 */   static final int[] IHDR_numChannels = new int[] { 1, 0, 3, 3, 2, 0, 4 };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   60 */   static final String[] IHDR_bitDepths = new String[] { "1", "2", "4", "8", "16" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   65 */   static final String[] IHDR_compressionMethodNames = new String[] { "deflate" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   70 */   static final String[] IHDR_filterMethodNames = new String[] { "adaptive" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   75 */   static final String[] IHDR_interlaceMethodNames = new String[] { "none", "adam7" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   80 */   static final String[] iCCP_compressionMethodNames = new String[] { "deflate" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   85 */   static final String[] zTXt_compressionMethodNames = new String[] { "deflate" };
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int PHYS_UNIT_UNKNOWN = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int PHYS_UNIT_METER = 1;
/*      */ 
/*      */   
/*   96 */   static final String[] unitSpecifierNames = new String[] { "unknown", "meter" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  101 */   static final String[] renderingIntentNames = new String[] { "Perceptual", "Relative colorimetric", "Saturation", "Absolute colorimetric" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  110 */   static final String[] colorSpaceTypeNames = new String[] { "GRAY", null, "RGB", "RGB", "GRAY", null, "RGB" };
/*      */   
/*      */   public boolean IHDR_present;
/*      */   
/*      */   public int IHDR_width;
/*      */   
/*      */   public int IHDR_height;
/*      */   
/*      */   public int IHDR_bitDepth;
/*      */   
/*      */   public int IHDR_colorType;
/*      */   
/*      */   public int IHDR_compressionMethod;
/*      */   
/*      */   public int IHDR_filterMethod;
/*      */   
/*      */   public int IHDR_interlaceMethod;
/*      */   
/*      */   public boolean PLTE_present;
/*      */   
/*      */   public byte[] PLTE_red;
/*      */   
/*      */   public byte[] PLTE_green;
/*      */   
/*      */   public byte[] PLTE_blue;
/*      */   
/*  136 */   public int[] PLTE_order = null;
/*      */   
/*      */   public boolean bKGD_present;
/*      */   
/*      */   public int bKGD_colorType;
/*      */   
/*      */   public int bKGD_index;
/*      */   
/*      */   public int bKGD_gray;
/*      */   
/*      */   public int bKGD_red;
/*      */   
/*      */   public int bKGD_green;
/*      */   
/*      */   public int bKGD_blue;
/*      */   
/*      */   public boolean cHRM_present;
/*      */   
/*      */   public int cHRM_whitePointX;
/*      */   
/*      */   public int cHRM_whitePointY;
/*      */   
/*      */   public int cHRM_redX;
/*      */   
/*      */   public int cHRM_redY;
/*      */   
/*      */   public int cHRM_greenX;
/*      */   
/*      */   public int cHRM_greenY;
/*      */   public int cHRM_blueX;
/*      */   public int cHRM_blueY;
/*      */   public boolean gAMA_present;
/*      */   public int gAMA_gamma;
/*      */   public boolean hIST_present;
/*      */   public char[] hIST_histogram;
/*      */   public boolean iCCP_present;
/*      */   public String iCCP_profileName;
/*      */   public int iCCP_compressionMethod;
/*      */   public byte[] iCCP_compressedProfile;
/*  175 */   public ArrayList<String> iTXt_keyword = new ArrayList<>();
/*  176 */   public ArrayList<Boolean> iTXt_compressionFlag = new ArrayList<>();
/*  177 */   public ArrayList<Integer> iTXt_compressionMethod = new ArrayList<>();
/*  178 */   public ArrayList<String> iTXt_languageTag = new ArrayList<>();
/*  179 */   public ArrayList<String> iTXt_translatedKeyword = new ArrayList<>();
/*  180 */   public ArrayList<String> iTXt_text = new ArrayList<>();
/*      */   
/*      */   public boolean pHYs_present;
/*      */   
/*      */   public int pHYs_pixelsPerUnitXAxis;
/*      */   
/*      */   public int pHYs_pixelsPerUnitYAxis;
/*      */   
/*      */   public int pHYs_unitSpecifier;
/*      */   
/*      */   public boolean sBIT_present;
/*      */   
/*      */   public int sBIT_colorType;
/*      */   
/*      */   public int sBIT_grayBits;
/*      */   
/*      */   public int sBIT_redBits;
/*      */   
/*      */   public int sBIT_greenBits;
/*      */   
/*      */   public int sBIT_blueBits;
/*      */   public int sBIT_alphaBits;
/*      */   public boolean sPLT_present;
/*      */   public String sPLT_paletteName;
/*      */   public int sPLT_sampleDepth;
/*      */   public int[] sPLT_red;
/*      */   public int[] sPLT_green;
/*      */   public int[] sPLT_blue;
/*      */   public int[] sPLT_alpha;
/*      */   public int[] sPLT_frequency;
/*      */   public boolean sRGB_present;
/*      */   public int sRGB_renderingIntent;
/*  212 */   public ArrayList<String> tEXt_keyword = new ArrayList<>();
/*  213 */   public ArrayList<String> tEXt_text = new ArrayList<>();
/*      */   
/*      */   public boolean tIME_present;
/*      */   
/*      */   public int tIME_year;
/*      */   
/*      */   public int tIME_month;
/*      */   
/*      */   public int tIME_day;
/*      */   
/*      */   public int tIME_hour;
/*      */   
/*      */   public int tIME_minute;
/*      */   
/*      */   public int tIME_second;
/*      */   
/*      */   public boolean tRNS_present;
/*      */   public int tRNS_colorType;
/*      */   public byte[] tRNS_alpha;
/*      */   public int tRNS_gray;
/*      */   public int tRNS_red;
/*      */   public int tRNS_green;
/*      */   public int tRNS_blue;
/*  236 */   public ArrayList<String> zTXt_keyword = new ArrayList<>();
/*  237 */   public ArrayList<Integer> zTXt_compressionMethod = new ArrayList<>();
/*  238 */   public ArrayList<String> zTXt_text = new ArrayList<>();
/*      */ 
/*      */   
/*  241 */   public ArrayList<String> unknownChunkType = new ArrayList<>();
/*  242 */   public ArrayList<byte[]> unknownChunkData = (ArrayList)new ArrayList<>();
/*      */   
/*      */   public PNGMetadata() {
/*  245 */     super(true, "javax_imageio_png_1.0", "com.sun.imageio.plugins.png.PNGMetadataFormat", null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PNGMetadata(IIOMetadata paramIIOMetadata) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initialize(ImageTypeSpecifier paramImageTypeSpecifier, int paramInt) {
/*  261 */     ColorModel colorModel = paramImageTypeSpecifier.getColorModel();
/*  262 */     SampleModel sampleModel = paramImageTypeSpecifier.getSampleModel();
/*      */ 
/*      */     
/*  265 */     int[] arrayOfInt = sampleModel.getSampleSize();
/*  266 */     int i = arrayOfInt[0];
/*      */ 
/*      */     
/*  269 */     for (byte b = 1; b < arrayOfInt.length; b++) {
/*  270 */       if (arrayOfInt[b] > i) {
/*  271 */         i = arrayOfInt[b];
/*      */       }
/*      */     } 
/*      */     
/*  275 */     if (arrayOfInt.length > 1 && i < 8) {
/*  276 */       i = 8;
/*      */     }
/*      */ 
/*      */     
/*  280 */     if (i > 2 && i < 4) {
/*  281 */       i = 4;
/*  282 */     } else if (i > 4 && i < 8) {
/*  283 */       i = 8;
/*  284 */     } else if (i > 8 && i < 16) {
/*  285 */       i = 16;
/*  286 */     } else if (i > 16) {
/*  287 */       throw new RuntimeException("bitDepth > 16!");
/*      */     } 
/*  289 */     this.IHDR_bitDepth = i;
/*      */ 
/*      */     
/*  292 */     if (colorModel instanceof IndexColorModel) {
/*  293 */       IndexColorModel indexColorModel = (IndexColorModel)colorModel;
/*  294 */       int j = indexColorModel.getMapSize();
/*      */       
/*  296 */       byte[] arrayOfByte1 = new byte[j];
/*  297 */       indexColorModel.getReds(arrayOfByte1);
/*  298 */       byte[] arrayOfByte2 = new byte[j];
/*  299 */       indexColorModel.getGreens(arrayOfByte2);
/*  300 */       byte[] arrayOfByte3 = new byte[j];
/*  301 */       indexColorModel.getBlues(arrayOfByte3);
/*      */ 
/*      */ 
/*      */       
/*  305 */       boolean bool = false;
/*  306 */       if (!this.IHDR_present || this.IHDR_colorType != 3) {
/*      */         
/*  308 */         bool = true;
/*  309 */         int k = 255 / ((1 << this.IHDR_bitDepth) - 1);
/*  310 */         for (byte b1 = 0; b1 < j; b1++) {
/*  311 */           byte b2 = arrayOfByte1[b1];
/*  312 */           if (b2 != (byte)(b1 * k) || b2 != arrayOfByte2[b1] || b2 != arrayOfByte3[b1]) {
/*      */ 
/*      */             
/*  315 */             bool = false;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  322 */       boolean bool1 = colorModel.hasAlpha();
/*      */       
/*  324 */       byte[] arrayOfByte4 = null;
/*  325 */       if (bool1) {
/*  326 */         arrayOfByte4 = new byte[j];
/*  327 */         indexColorModel.getAlphas(arrayOfByte4);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  339 */       if (bool && bool1 && (i == 8 || i == 16)) {
/*  340 */         this.IHDR_colorType = 4;
/*  341 */       } else if (bool && !bool1) {
/*  342 */         this.IHDR_colorType = 0;
/*      */       } else {
/*  344 */         this.IHDR_colorType = 3;
/*  345 */         this.PLTE_present = true;
/*  346 */         this.PLTE_order = null;
/*  347 */         this.PLTE_red = (byte[])arrayOfByte1.clone();
/*  348 */         this.PLTE_green = (byte[])arrayOfByte2.clone();
/*  349 */         this.PLTE_blue = (byte[])arrayOfByte3.clone();
/*      */         
/*  351 */         if (bool1) {
/*  352 */           this.tRNS_present = true;
/*  353 */           this.tRNS_colorType = 3;
/*      */           
/*  355 */           this.PLTE_order = new int[arrayOfByte4.length];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  364 */           byte[] arrayOfByte5 = new byte[arrayOfByte4.length];
/*      */ 
/*      */ 
/*      */           
/*  368 */           byte b1 = 0; byte b2;
/*  369 */           for (b2 = 0; b2 < arrayOfByte4.length; b2++) {
/*  370 */             if (arrayOfByte4[b2] != -1) {
/*  371 */               this.PLTE_order[b2] = b1;
/*  372 */               arrayOfByte5[b1] = arrayOfByte4[b2];
/*  373 */               b1++;
/*      */             } 
/*      */           } 
/*  376 */           b2 = b1;
/*      */ 
/*      */ 
/*      */           
/*  380 */           for (byte b3 = 0; b3 < arrayOfByte4.length; b3++) {
/*  381 */             if (arrayOfByte4[b3] == -1) {
/*  382 */               this.PLTE_order[b3] = b1++;
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/*  387 */           byte[] arrayOfByte6 = this.PLTE_red;
/*  388 */           byte[] arrayOfByte7 = this.PLTE_green;
/*  389 */           byte[] arrayOfByte8 = this.PLTE_blue;
/*  390 */           int k = arrayOfByte6.length;
/*  391 */           this.PLTE_red = new byte[k];
/*  392 */           this.PLTE_green = new byte[k];
/*  393 */           this.PLTE_blue = new byte[k];
/*  394 */           for (byte b4 = 0; b4 < k; b4++) {
/*  395 */             this.PLTE_red[this.PLTE_order[b4]] = arrayOfByte6[b4];
/*  396 */             this.PLTE_green[this.PLTE_order[b4]] = arrayOfByte7[b4];
/*  397 */             this.PLTE_blue[this.PLTE_order[b4]] = arrayOfByte8[b4];
/*      */           } 
/*      */ 
/*      */           
/*  401 */           this.tRNS_alpha = new byte[b2];
/*  402 */           System.arraycopy(arrayOfByte5, 0, this.tRNS_alpha, 0, b2);
/*      */         }
/*      */       
/*      */       }
/*      */     
/*  407 */     } else if (paramInt == 1) {
/*  408 */       this.IHDR_colorType = 0;
/*  409 */     } else if (paramInt == 2) {
/*  410 */       this.IHDR_colorType = 4;
/*  411 */     } else if (paramInt == 3) {
/*  412 */       this.IHDR_colorType = 2;
/*  413 */     } else if (paramInt == 4) {
/*  414 */       this.IHDR_colorType = 6;
/*      */     } else {
/*  416 */       throw new RuntimeException("Number of bands not 1-4!");
/*      */     } 
/*      */ 
/*      */     
/*  420 */     this.IHDR_present = true;
/*      */   }
/*      */   
/*      */   public boolean isReadOnly() {
/*  424 */     return false;
/*      */   }
/*      */   
/*      */   private ArrayList<byte[]> cloneBytesArrayList(ArrayList<byte[]> paramArrayList) {
/*  428 */     if (paramArrayList == null) {
/*  429 */       return null;
/*      */     }
/*  431 */     ArrayList<byte[]> arrayList = new ArrayList(paramArrayList.size());
/*  432 */     for (byte[] arrayOfByte : paramArrayList) {
/*  433 */       arrayList.add((arrayOfByte == null) ? null : (byte[])arrayOfByte.clone());
/*      */     }
/*  435 */     return arrayList;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*      */     PNGMetadata pNGMetadata;
/*      */     try {
/*  443 */       pNGMetadata = (PNGMetadata)super.clone();
/*  444 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*  445 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  449 */     pNGMetadata
/*  450 */       .unknownChunkData = cloneBytesArrayList(this.unknownChunkData);
/*      */     
/*  452 */     return pNGMetadata;
/*      */   }
/*      */   
/*      */   public Node getAsTree(String paramString) {
/*  456 */     if (paramString.equals("javax_imageio_png_1.0"))
/*  457 */       return getNativeTree(); 
/*  458 */     if (paramString
/*  459 */       .equals("javax_imageio_1.0")) {
/*  460 */       return getStandardTree();
/*      */     }
/*  462 */     throw new IllegalArgumentException("Not a recognized format!");
/*      */   }
/*      */ 
/*      */   
/*      */   private Node getNativeTree() {
/*  467 */     IIOMetadataNode iIOMetadataNode1 = null;
/*  468 */     IIOMetadataNode iIOMetadataNode2 = new IIOMetadataNode("javax_imageio_png_1.0");
/*      */ 
/*      */     
/*  471 */     if (this.IHDR_present) {
/*  472 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("IHDR");
/*  473 */       iIOMetadataNode.setAttribute("width", Integer.toString(this.IHDR_width));
/*  474 */       iIOMetadataNode.setAttribute("height", Integer.toString(this.IHDR_height));
/*  475 */       iIOMetadataNode.setAttribute("bitDepth", 
/*  476 */           Integer.toString(this.IHDR_bitDepth));
/*  477 */       iIOMetadataNode.setAttribute("colorType", IHDR_colorTypeNames[this.IHDR_colorType]);
/*      */ 
/*      */       
/*  480 */       iIOMetadataNode.setAttribute("compressionMethod", IHDR_compressionMethodNames[this.IHDR_compressionMethod]);
/*      */ 
/*      */       
/*  483 */       iIOMetadataNode.setAttribute("filterMethod", IHDR_filterMethodNames[this.IHDR_filterMethod]);
/*      */       
/*  485 */       iIOMetadataNode.setAttribute("interlaceMethod", IHDR_interlaceMethodNames[this.IHDR_interlaceMethod]);
/*      */       
/*  487 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  491 */     if (this.PLTE_present) {
/*  492 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("PLTE");
/*  493 */       int i = this.PLTE_red.length;
/*  494 */       for (byte b = 0; b < i; b++) {
/*  495 */         IIOMetadataNode iIOMetadataNode3 = new IIOMetadataNode("PLTEEntry");
/*  496 */         iIOMetadataNode3.setAttribute("index", Integer.toString(b));
/*  497 */         iIOMetadataNode3.setAttribute("red", 
/*  498 */             Integer.toString(this.PLTE_red[b] & 0xFF));
/*  499 */         iIOMetadataNode3.setAttribute("green", 
/*  500 */             Integer.toString(this.PLTE_green[b] & 0xFF));
/*  501 */         iIOMetadataNode3.setAttribute("blue", 
/*  502 */             Integer.toString(this.PLTE_blue[b] & 0xFF));
/*  503 */         iIOMetadataNode.appendChild(iIOMetadataNode3);
/*      */       } 
/*      */       
/*  506 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  510 */     if (this.bKGD_present) {
/*  511 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("bKGD");
/*      */       
/*  513 */       if (this.bKGD_colorType == 3) {
/*  514 */         iIOMetadataNode1 = new IIOMetadataNode("bKGD_Palette");
/*  515 */         iIOMetadataNode1.setAttribute("index", Integer.toString(this.bKGD_index));
/*  516 */       } else if (this.bKGD_colorType == 0) {
/*  517 */         iIOMetadataNode1 = new IIOMetadataNode("bKGD_Grayscale");
/*  518 */         iIOMetadataNode1.setAttribute("gray", Integer.toString(this.bKGD_gray));
/*  519 */       } else if (this.bKGD_colorType == 2) {
/*  520 */         iIOMetadataNode1 = new IIOMetadataNode("bKGD_RGB");
/*  521 */         iIOMetadataNode1.setAttribute("red", Integer.toString(this.bKGD_red));
/*  522 */         iIOMetadataNode1.setAttribute("green", Integer.toString(this.bKGD_green));
/*  523 */         iIOMetadataNode1.setAttribute("blue", Integer.toString(this.bKGD_blue));
/*      */       } 
/*  525 */       iIOMetadataNode.appendChild(iIOMetadataNode1);
/*      */       
/*  527 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  531 */     if (this.cHRM_present) {
/*  532 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("cHRM");
/*  533 */       iIOMetadataNode.setAttribute("whitePointX", 
/*  534 */           Integer.toString(this.cHRM_whitePointX));
/*  535 */       iIOMetadataNode.setAttribute("whitePointY", 
/*  536 */           Integer.toString(this.cHRM_whitePointY));
/*  537 */       iIOMetadataNode.setAttribute("redX", Integer.toString(this.cHRM_redX));
/*  538 */       iIOMetadataNode.setAttribute("redY", Integer.toString(this.cHRM_redY));
/*  539 */       iIOMetadataNode.setAttribute("greenX", Integer.toString(this.cHRM_greenX));
/*  540 */       iIOMetadataNode.setAttribute("greenY", Integer.toString(this.cHRM_greenY));
/*  541 */       iIOMetadataNode.setAttribute("blueX", Integer.toString(this.cHRM_blueX));
/*  542 */       iIOMetadataNode.setAttribute("blueY", Integer.toString(this.cHRM_blueY));
/*      */       
/*  544 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  548 */     if (this.gAMA_present) {
/*  549 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("gAMA");
/*  550 */       iIOMetadataNode.setAttribute("value", Integer.toString(this.gAMA_gamma));
/*      */       
/*  552 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  556 */     if (this.hIST_present) {
/*  557 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("hIST");
/*      */       
/*  559 */       for (byte b = 0; b < this.hIST_histogram.length; b++) {
/*  560 */         IIOMetadataNode iIOMetadataNode3 = new IIOMetadataNode("hISTEntry");
/*      */         
/*  562 */         iIOMetadataNode3.setAttribute("index", Integer.toString(b));
/*  563 */         iIOMetadataNode3.setAttribute("value", 
/*  564 */             Integer.toString(this.hIST_histogram[b]));
/*  565 */         iIOMetadataNode.appendChild(iIOMetadataNode3);
/*      */       } 
/*      */       
/*  568 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  572 */     if (this.iCCP_present) {
/*  573 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("iCCP");
/*  574 */       iIOMetadataNode.setAttribute("profileName", this.iCCP_profileName);
/*  575 */       iIOMetadataNode.setAttribute("compressionMethod", iCCP_compressionMethodNames[this.iCCP_compressionMethod]);
/*      */ 
/*      */       
/*  578 */       Object object = this.iCCP_compressedProfile;
/*  579 */       if (object != null) {
/*  580 */         object = object.clone();
/*      */       }
/*  582 */       iIOMetadataNode.setUserObject(object);
/*      */       
/*  584 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  588 */     if (this.iTXt_keyword.size() > 0) {
/*  589 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("iTXt");
/*  590 */       for (byte b = 0; b < this.iTXt_keyword.size(); b++) {
/*  591 */         IIOMetadataNode iIOMetadataNode3 = new IIOMetadataNode("iTXtEntry");
/*  592 */         iIOMetadataNode3.setAttribute("keyword", this.iTXt_keyword.get(b));
/*  593 */         iIOMetadataNode3.setAttribute("compressionFlag", 
/*  594 */             ((Boolean)this.iTXt_compressionFlag.get(b)).booleanValue() ? "TRUE" : "FALSE");
/*  595 */         iIOMetadataNode3.setAttribute("compressionMethod", ((Integer)this.iTXt_compressionMethod
/*  596 */             .get(b)).toString());
/*  597 */         iIOMetadataNode3.setAttribute("languageTag", this.iTXt_languageTag
/*  598 */             .get(b));
/*  599 */         iIOMetadataNode3.setAttribute("translatedKeyword", this.iTXt_translatedKeyword
/*  600 */             .get(b));
/*  601 */         iIOMetadataNode3.setAttribute("text", this.iTXt_text.get(b));
/*      */         
/*  603 */         iIOMetadataNode.appendChild(iIOMetadataNode3);
/*      */       } 
/*      */       
/*  606 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  610 */     if (this.pHYs_present) {
/*  611 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("pHYs");
/*  612 */       iIOMetadataNode.setAttribute("pixelsPerUnitXAxis", 
/*  613 */           Integer.toString(this.pHYs_pixelsPerUnitXAxis));
/*  614 */       iIOMetadataNode.setAttribute("pixelsPerUnitYAxis", 
/*  615 */           Integer.toString(this.pHYs_pixelsPerUnitYAxis));
/*  616 */       iIOMetadataNode.setAttribute("unitSpecifier", unitSpecifierNames[this.pHYs_unitSpecifier]);
/*      */ 
/*      */       
/*  619 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  623 */     if (this.sBIT_present) {
/*  624 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("sBIT");
/*      */       
/*  626 */       if (this.sBIT_colorType == 0) {
/*  627 */         iIOMetadataNode1 = new IIOMetadataNode("sBIT_Grayscale");
/*  628 */         iIOMetadataNode1.setAttribute("gray", 
/*  629 */             Integer.toString(this.sBIT_grayBits));
/*  630 */       } else if (this.sBIT_colorType == 4) {
/*  631 */         iIOMetadataNode1 = new IIOMetadataNode("sBIT_GrayAlpha");
/*  632 */         iIOMetadataNode1.setAttribute("gray", 
/*  633 */             Integer.toString(this.sBIT_grayBits));
/*  634 */         iIOMetadataNode1.setAttribute("alpha", 
/*  635 */             Integer.toString(this.sBIT_alphaBits));
/*  636 */       } else if (this.sBIT_colorType == 2) {
/*  637 */         iIOMetadataNode1 = new IIOMetadataNode("sBIT_RGB");
/*  638 */         iIOMetadataNode1.setAttribute("red", 
/*  639 */             Integer.toString(this.sBIT_redBits));
/*  640 */         iIOMetadataNode1.setAttribute("green", 
/*  641 */             Integer.toString(this.sBIT_greenBits));
/*  642 */         iIOMetadataNode1.setAttribute("blue", 
/*  643 */             Integer.toString(this.sBIT_blueBits));
/*  644 */       } else if (this.sBIT_colorType == 6) {
/*  645 */         iIOMetadataNode1 = new IIOMetadataNode("sBIT_RGBAlpha");
/*  646 */         iIOMetadataNode1.setAttribute("red", 
/*  647 */             Integer.toString(this.sBIT_redBits));
/*  648 */         iIOMetadataNode1.setAttribute("green", 
/*  649 */             Integer.toString(this.sBIT_greenBits));
/*  650 */         iIOMetadataNode1.setAttribute("blue", 
/*  651 */             Integer.toString(this.sBIT_blueBits));
/*  652 */         iIOMetadataNode1.setAttribute("alpha", 
/*  653 */             Integer.toString(this.sBIT_alphaBits));
/*  654 */       } else if (this.sBIT_colorType == 3) {
/*  655 */         iIOMetadataNode1 = new IIOMetadataNode("sBIT_Palette");
/*  656 */         iIOMetadataNode1.setAttribute("red", 
/*  657 */             Integer.toString(this.sBIT_redBits));
/*  658 */         iIOMetadataNode1.setAttribute("green", 
/*  659 */             Integer.toString(this.sBIT_greenBits));
/*  660 */         iIOMetadataNode1.setAttribute("blue", 
/*  661 */             Integer.toString(this.sBIT_blueBits));
/*      */       } 
/*  663 */       iIOMetadataNode.appendChild(iIOMetadataNode1);
/*      */       
/*  665 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  669 */     if (this.sPLT_present) {
/*  670 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("sPLT");
/*      */       
/*  672 */       iIOMetadataNode.setAttribute("name", this.sPLT_paletteName);
/*  673 */       iIOMetadataNode.setAttribute("sampleDepth", 
/*  674 */           Integer.toString(this.sPLT_sampleDepth));
/*      */       
/*  676 */       int i = this.sPLT_red.length;
/*  677 */       for (byte b = 0; b < i; b++) {
/*  678 */         IIOMetadataNode iIOMetadataNode3 = new IIOMetadataNode("sPLTEntry");
/*  679 */         iIOMetadataNode3.setAttribute("index", Integer.toString(b));
/*  680 */         iIOMetadataNode3.setAttribute("red", Integer.toString(this.sPLT_red[b]));
/*  681 */         iIOMetadataNode3.setAttribute("green", Integer.toString(this.sPLT_green[b]));
/*  682 */         iIOMetadataNode3.setAttribute("blue", Integer.toString(this.sPLT_blue[b]));
/*  683 */         iIOMetadataNode3.setAttribute("alpha", Integer.toString(this.sPLT_alpha[b]));
/*  684 */         iIOMetadataNode3.setAttribute("frequency", 
/*  685 */             Integer.toString(this.sPLT_frequency[b]));
/*  686 */         iIOMetadataNode.appendChild(iIOMetadataNode3);
/*      */       } 
/*      */       
/*  689 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  693 */     if (this.sRGB_present) {
/*  694 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("sRGB");
/*  695 */       iIOMetadataNode.setAttribute("renderingIntent", renderingIntentNames[this.sRGB_renderingIntent]);
/*      */ 
/*      */       
/*  698 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  702 */     if (this.tEXt_keyword.size() > 0) {
/*  703 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("tEXt");
/*  704 */       for (byte b = 0; b < this.tEXt_keyword.size(); b++) {
/*  705 */         IIOMetadataNode iIOMetadataNode3 = new IIOMetadataNode("tEXtEntry");
/*  706 */         iIOMetadataNode3.setAttribute("keyword", this.tEXt_keyword.get(b));
/*  707 */         iIOMetadataNode3.setAttribute("value", this.tEXt_text.get(b));
/*      */         
/*  709 */         iIOMetadataNode.appendChild(iIOMetadataNode3);
/*      */       } 
/*      */       
/*  712 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  716 */     if (this.tIME_present) {
/*  717 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("tIME");
/*  718 */       iIOMetadataNode.setAttribute("year", Integer.toString(this.tIME_year));
/*  719 */       iIOMetadataNode.setAttribute("month", Integer.toString(this.tIME_month));
/*  720 */       iIOMetadataNode.setAttribute("day", Integer.toString(this.tIME_day));
/*  721 */       iIOMetadataNode.setAttribute("hour", Integer.toString(this.tIME_hour));
/*  722 */       iIOMetadataNode.setAttribute("minute", Integer.toString(this.tIME_minute));
/*  723 */       iIOMetadataNode.setAttribute("second", Integer.toString(this.tIME_second));
/*      */       
/*  725 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  729 */     if (this.tRNS_present) {
/*  730 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("tRNS");
/*      */       
/*  732 */       if (this.tRNS_colorType == 3) {
/*  733 */         iIOMetadataNode1 = new IIOMetadataNode("tRNS_Palette");
/*      */         
/*  735 */         for (byte b = 0; b < this.tRNS_alpha.length; b++) {
/*  736 */           IIOMetadataNode iIOMetadataNode3 = new IIOMetadataNode("tRNS_PaletteEntry");
/*      */           
/*  738 */           iIOMetadataNode3.setAttribute("index", Integer.toString(b));
/*  739 */           iIOMetadataNode3.setAttribute("alpha", 
/*  740 */               Integer.toString(this.tRNS_alpha[b] & 0xFF));
/*  741 */           iIOMetadataNode1.appendChild(iIOMetadataNode3);
/*      */         } 
/*  743 */       } else if (this.tRNS_colorType == 0) {
/*  744 */         iIOMetadataNode1 = new IIOMetadataNode("tRNS_Grayscale");
/*  745 */         iIOMetadataNode1.setAttribute("gray", Integer.toString(this.tRNS_gray));
/*  746 */       } else if (this.tRNS_colorType == 2) {
/*  747 */         iIOMetadataNode1 = new IIOMetadataNode("tRNS_RGB");
/*  748 */         iIOMetadataNode1.setAttribute("red", Integer.toString(this.tRNS_red));
/*  749 */         iIOMetadataNode1.setAttribute("green", Integer.toString(this.tRNS_green));
/*  750 */         iIOMetadataNode1.setAttribute("blue", Integer.toString(this.tRNS_blue));
/*      */       } 
/*  752 */       iIOMetadataNode.appendChild(iIOMetadataNode1);
/*      */       
/*  754 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  758 */     if (this.zTXt_keyword.size() > 0) {
/*  759 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("zTXt");
/*  760 */       for (byte b = 0; b < this.zTXt_keyword.size(); b++) {
/*  761 */         IIOMetadataNode iIOMetadataNode3 = new IIOMetadataNode("zTXtEntry");
/*  762 */         iIOMetadataNode3.setAttribute("keyword", this.zTXt_keyword.get(b));
/*      */         
/*  764 */         int i = ((Integer)this.zTXt_compressionMethod.get(b)).intValue();
/*  765 */         iIOMetadataNode3.setAttribute("compressionMethod", zTXt_compressionMethodNames[i]);
/*      */ 
/*      */         
/*  768 */         iIOMetadataNode3.setAttribute("text", this.zTXt_text.get(b));
/*      */         
/*  770 */         iIOMetadataNode.appendChild(iIOMetadataNode3);
/*      */       } 
/*      */       
/*  773 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */ 
/*      */     
/*  777 */     if (this.unknownChunkType.size() > 0) {
/*  778 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("UnknownChunks");
/*      */       
/*  780 */       for (byte b = 0; b < this.unknownChunkType.size(); b++) {
/*  781 */         IIOMetadataNode iIOMetadataNode3 = new IIOMetadataNode("UnknownChunk");
/*      */         
/*  783 */         iIOMetadataNode3.setAttribute("type", this.unknownChunkType
/*  784 */             .get(b));
/*  785 */         iIOMetadataNode3.setUserObject(this.unknownChunkData.get(b));
/*      */         
/*  787 */         iIOMetadataNode.appendChild(iIOMetadataNode3);
/*      */       } 
/*      */       
/*  790 */       iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */     } 
/*      */     
/*  793 */     return iIOMetadataNode2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int getNumChannels() {
/*  799 */     int i = IHDR_numChannels[this.IHDR_colorType];
/*  800 */     if (this.IHDR_colorType == 3 && this.tRNS_present && this.tRNS_colorType == this.IHDR_colorType)
/*      */     {
/*  802 */       i = 4;
/*      */     }
/*  804 */     return i;
/*      */   }
/*      */   
/*      */   public IIOMetadataNode getStandardChromaNode() {
/*  808 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Chroma");
/*  809 */     IIOMetadataNode iIOMetadataNode2 = null;
/*      */     
/*  811 */     iIOMetadataNode2 = new IIOMetadataNode("ColorSpaceType");
/*  812 */     iIOMetadataNode2.setAttribute("name", colorSpaceTypeNames[this.IHDR_colorType]);
/*  813 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     
/*  815 */     iIOMetadataNode2 = new IIOMetadataNode("NumChannels");
/*  816 */     iIOMetadataNode2.setAttribute("value", Integer.toString(getNumChannels()));
/*  817 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     
/*  819 */     if (this.gAMA_present) {
/*  820 */       iIOMetadataNode2 = new IIOMetadataNode("Gamma");
/*  821 */       iIOMetadataNode2.setAttribute("value", Float.toString(this.gAMA_gamma * 1.0E-5F));
/*  822 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     } 
/*      */     
/*  825 */     iIOMetadataNode2 = new IIOMetadataNode("BlackIsZero");
/*  826 */     iIOMetadataNode2.setAttribute("value", "TRUE");
/*  827 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     
/*  829 */     if (this.PLTE_present) {
/*  830 */       boolean bool = (this.tRNS_present && this.tRNS_colorType == 3) ? true : false;
/*      */ 
/*      */       
/*  833 */       iIOMetadataNode2 = new IIOMetadataNode("Palette");
/*  834 */       for (byte b = 0; b < this.PLTE_red.length; b++) {
/*  835 */         IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("PaletteEntry");
/*      */         
/*  837 */         iIOMetadataNode.setAttribute("index", Integer.toString(b));
/*  838 */         iIOMetadataNode.setAttribute("red", 
/*  839 */             Integer.toString(this.PLTE_red[b] & 0xFF));
/*  840 */         iIOMetadataNode.setAttribute("green", 
/*  841 */             Integer.toString(this.PLTE_green[b] & 0xFF));
/*  842 */         iIOMetadataNode.setAttribute("blue", 
/*  843 */             Integer.toString(this.PLTE_blue[b] & 0xFF));
/*  844 */         if (bool) {
/*  845 */           boolean bool1 = (b < this.tRNS_alpha.length) ? (this.tRNS_alpha[b] & 0xFF) : true;
/*      */           
/*  847 */           iIOMetadataNode.setAttribute("alpha", Integer.toString(bool1));
/*      */         } 
/*  849 */         iIOMetadataNode2.appendChild(iIOMetadataNode);
/*      */       } 
/*  851 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     } 
/*      */     
/*  854 */     if (this.bKGD_present) {
/*  855 */       if (this.bKGD_colorType == 3) {
/*  856 */         iIOMetadataNode2 = new IIOMetadataNode("BackgroundIndex");
/*  857 */         iIOMetadataNode2.setAttribute("value", Integer.toString(this.bKGD_index));
/*      */       } else {
/*  859 */         int i, j, k; iIOMetadataNode2 = new IIOMetadataNode("BackgroundColor");
/*      */ 
/*      */         
/*  862 */         if (this.bKGD_colorType == 0) {
/*  863 */           i = j = k = this.bKGD_gray;
/*      */         } else {
/*  865 */           i = this.bKGD_red;
/*  866 */           j = this.bKGD_green;
/*  867 */           k = this.bKGD_blue;
/*      */         } 
/*  869 */         iIOMetadataNode2.setAttribute("red", Integer.toString(i));
/*  870 */         iIOMetadataNode2.setAttribute("green", Integer.toString(j));
/*  871 */         iIOMetadataNode2.setAttribute("blue", Integer.toString(k));
/*      */       } 
/*  873 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     } 
/*      */     
/*  876 */     return iIOMetadataNode1;
/*      */   }
/*      */   
/*      */   public IIOMetadataNode getStandardCompressionNode() {
/*  880 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Compression");
/*  881 */     IIOMetadataNode iIOMetadataNode2 = null;
/*      */     
/*  883 */     iIOMetadataNode2 = new IIOMetadataNode("CompressionTypeName");
/*  884 */     iIOMetadataNode2.setAttribute("value", "deflate");
/*  885 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     
/*  887 */     iIOMetadataNode2 = new IIOMetadataNode("Lossless");
/*  888 */     iIOMetadataNode2.setAttribute("value", "TRUE");
/*  889 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     
/*  891 */     iIOMetadataNode2 = new IIOMetadataNode("NumProgressiveScans");
/*  892 */     iIOMetadataNode2.setAttribute("value", (this.IHDR_interlaceMethod == 0) ? "1" : "7");
/*      */     
/*  894 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     
/*  896 */     return iIOMetadataNode1;
/*      */   }
/*      */   
/*      */   private String repeat(String paramString, int paramInt) {
/*  900 */     if (paramInt == 1) {
/*  901 */       return paramString;
/*      */     }
/*  903 */     StringBuffer stringBuffer = new StringBuffer((paramString.length() + 1) * paramInt - 1);
/*  904 */     stringBuffer.append(paramString);
/*  905 */     for (byte b = 1; b < paramInt; b++) {
/*  906 */       stringBuffer.append(" ");
/*  907 */       stringBuffer.append(paramString);
/*      */     } 
/*  909 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   public IIOMetadataNode getStandardDataNode() {
/*  913 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Data");
/*  914 */     IIOMetadataNode iIOMetadataNode2 = null;
/*      */     
/*  916 */     iIOMetadataNode2 = new IIOMetadataNode("PlanarConfiguration");
/*  917 */     iIOMetadataNode2.setAttribute("value", "PixelInterleaved");
/*  918 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     
/*  920 */     iIOMetadataNode2 = new IIOMetadataNode("SampleFormat");
/*  921 */     iIOMetadataNode2.setAttribute("value", (this.IHDR_colorType == 3) ? "Index" : "UnsignedIntegral");
/*      */ 
/*      */     
/*  924 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     
/*  926 */     String str = Integer.toString(this.IHDR_bitDepth);
/*  927 */     iIOMetadataNode2 = new IIOMetadataNode("BitsPerSample");
/*  928 */     iIOMetadataNode2.setAttribute("value", repeat(str, getNumChannels()));
/*  929 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     
/*  931 */     if (this.sBIT_present) {
/*  932 */       String str1; iIOMetadataNode2 = new IIOMetadataNode("SignificantBitsPerSample");
/*      */       
/*  934 */       if (this.sBIT_colorType == 0 || this.sBIT_colorType == 4) {
/*      */         
/*  936 */         str1 = Integer.toString(this.sBIT_grayBits);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  941 */         str1 = Integer.toString(this.sBIT_redBits) + " " + Integer.toString(this.sBIT_greenBits) + " " + Integer.toString(this.sBIT_blueBits);
/*      */       } 
/*      */       
/*  944 */       if (this.sBIT_colorType == 4 || this.sBIT_colorType == 6)
/*      */       {
/*  946 */         str1 = str1 + " " + Integer.toString(this.sBIT_alphaBits);
/*      */       }
/*      */       
/*  949 */       iIOMetadataNode2.setAttribute("value", str1);
/*  950 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  955 */     return iIOMetadataNode1;
/*      */   }
/*      */   
/*      */   public IIOMetadataNode getStandardDimensionNode() {
/*  959 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Dimension");
/*  960 */     IIOMetadataNode iIOMetadataNode2 = null;
/*      */     
/*  962 */     iIOMetadataNode2 = new IIOMetadataNode("PixelAspectRatio");
/*  963 */     float f = this.pHYs_present ? (this.pHYs_pixelsPerUnitXAxis / this.pHYs_pixelsPerUnitYAxis) : 1.0F;
/*      */     
/*  965 */     iIOMetadataNode2.setAttribute("value", Float.toString(f));
/*  966 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     
/*  968 */     iIOMetadataNode2 = new IIOMetadataNode("ImageOrientation");
/*  969 */     iIOMetadataNode2.setAttribute("value", "Normal");
/*  970 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     
/*  972 */     if (this.pHYs_present && this.pHYs_unitSpecifier == 1) {
/*  973 */       iIOMetadataNode2 = new IIOMetadataNode("HorizontalPixelSize");
/*  974 */       iIOMetadataNode2.setAttribute("value", 
/*  975 */           Float.toString(1000.0F / this.pHYs_pixelsPerUnitXAxis));
/*  976 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */       
/*  978 */       iIOMetadataNode2 = new IIOMetadataNode("VerticalPixelSize");
/*  979 */       iIOMetadataNode2.setAttribute("value", 
/*  980 */           Float.toString(1000.0F / this.pHYs_pixelsPerUnitYAxis));
/*  981 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     } 
/*      */     
/*  984 */     return iIOMetadataNode1;
/*      */   }
/*      */   
/*      */   public IIOMetadataNode getStandardDocumentNode() {
/*  988 */     if (!this.tIME_present) {
/*  989 */       return null;
/*      */     }
/*      */     
/*  992 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Document");
/*  993 */     IIOMetadataNode iIOMetadataNode2 = null;
/*      */     
/*  995 */     iIOMetadataNode2 = new IIOMetadataNode("ImageModificationTime");
/*  996 */     iIOMetadataNode2.setAttribute("year", Integer.toString(this.tIME_year));
/*  997 */     iIOMetadataNode2.setAttribute("month", Integer.toString(this.tIME_month));
/*  998 */     iIOMetadataNode2.setAttribute("day", Integer.toString(this.tIME_day));
/*  999 */     iIOMetadataNode2.setAttribute("hour", Integer.toString(this.tIME_hour));
/* 1000 */     iIOMetadataNode2.setAttribute("minute", Integer.toString(this.tIME_minute));
/* 1001 */     iIOMetadataNode2.setAttribute("second", Integer.toString(this.tIME_second));
/* 1002 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     
/* 1004 */     return iIOMetadataNode1;
/*      */   }
/*      */ 
/*      */   
/*      */   public IIOMetadataNode getStandardTextNode() {
/* 1009 */     int i = this.tEXt_keyword.size() + this.iTXt_keyword.size() + this.zTXt_keyword.size();
/* 1010 */     if (i == 0) {
/* 1011 */       return null;
/*      */     }
/*      */     
/* 1014 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Text");
/* 1015 */     IIOMetadataNode iIOMetadataNode2 = null;
/*      */     byte b;
/* 1017 */     for (b = 0; b < this.tEXt_keyword.size(); b++) {
/* 1018 */       iIOMetadataNode2 = new IIOMetadataNode("TextEntry");
/* 1019 */       iIOMetadataNode2.setAttribute("keyword", this.tEXt_keyword.get(b));
/* 1020 */       iIOMetadataNode2.setAttribute("value", this.tEXt_text.get(b));
/* 1021 */       iIOMetadataNode2.setAttribute("encoding", "ISO-8859-1");
/* 1022 */       iIOMetadataNode2.setAttribute("compression", "none");
/*      */       
/* 1024 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     } 
/*      */     
/* 1027 */     for (b = 0; b < this.iTXt_keyword.size(); b++) {
/* 1028 */       iIOMetadataNode2 = new IIOMetadataNode("TextEntry");
/* 1029 */       iIOMetadataNode2.setAttribute("keyword", this.iTXt_keyword.get(b));
/* 1030 */       iIOMetadataNode2.setAttribute("value", this.iTXt_text.get(b));
/* 1031 */       iIOMetadataNode2.setAttribute("language", this.iTXt_languageTag
/* 1032 */           .get(b));
/* 1033 */       if (((Boolean)this.iTXt_compressionFlag.get(b)).booleanValue()) {
/* 1034 */         iIOMetadataNode2.setAttribute("compression", "zip");
/*      */       } else {
/* 1036 */         iIOMetadataNode2.setAttribute("compression", "none");
/*      */       } 
/*      */       
/* 1039 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     } 
/*      */     
/* 1042 */     for (b = 0; b < this.zTXt_keyword.size(); b++) {
/* 1043 */       iIOMetadataNode2 = new IIOMetadataNode("TextEntry");
/* 1044 */       iIOMetadataNode2.setAttribute("keyword", this.zTXt_keyword.get(b));
/* 1045 */       iIOMetadataNode2.setAttribute("value", this.zTXt_text.get(b));
/* 1046 */       iIOMetadataNode2.setAttribute("compression", "zip");
/*      */       
/* 1048 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     } 
/*      */     
/* 1051 */     return iIOMetadataNode1;
/*      */   }
/*      */   
/*      */   public IIOMetadataNode getStandardTransparencyNode() {
/* 1055 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Transparency");
/*      */     
/* 1057 */     IIOMetadataNode iIOMetadataNode2 = null;
/*      */     
/* 1059 */     iIOMetadataNode2 = new IIOMetadataNode("Alpha");
/* 1060 */     boolean bool = (this.IHDR_colorType == 6 || this.IHDR_colorType == 4 || (this.IHDR_colorType == 3 && this.tRNS_present && this.tRNS_colorType == this.IHDR_colorType && this.tRNS_alpha != null)) ? true : false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1067 */     iIOMetadataNode2.setAttribute("value", bool ? "nonpremultipled" : "none");
/* 1068 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     
/* 1070 */     if (this.tRNS_present) {
/* 1071 */       iIOMetadataNode2 = new IIOMetadataNode("TransparentColor");
/* 1072 */       if (this.tRNS_colorType == 2) {
/* 1073 */         iIOMetadataNode2.setAttribute("value", 
/* 1074 */             Integer.toString(this.tRNS_red) + " " + 
/* 1075 */             Integer.toString(this.tRNS_green) + " " + 
/* 1076 */             Integer.toString(this.tRNS_blue));
/* 1077 */       } else if (this.tRNS_colorType == 0) {
/* 1078 */         iIOMetadataNode2.setAttribute("value", Integer.toString(this.tRNS_gray));
/*      */       } 
/* 1080 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*      */     } 
/*      */     
/* 1083 */     return iIOMetadataNode1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void fatal(Node paramNode, String paramString) throws IIOInvalidTreeException {
/* 1089 */     throw new IIOInvalidTreeException(paramString, paramNode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getStringAttribute(Node paramNode, String paramString1, String paramString2, boolean paramBoolean) throws IIOInvalidTreeException {
/* 1096 */     Node node = paramNode.getAttributes().getNamedItem(paramString1);
/* 1097 */     if (node == null) {
/* 1098 */       if (!paramBoolean) {
/* 1099 */         return paramString2;
/*      */       }
/* 1101 */       fatal(paramNode, "Required attribute " + paramString1 + " not present!");
/*      */     } 
/*      */     
/* 1104 */     return node.getNodeValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getIntAttribute(Node paramNode, String paramString, int paramInt, boolean paramBoolean) throws IIOInvalidTreeException {
/* 1112 */     String str = getStringAttribute(paramNode, paramString, null, paramBoolean);
/* 1113 */     if (str == null) {
/* 1114 */       return paramInt;
/*      */     }
/* 1116 */     return Integer.parseInt(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float getFloatAttribute(Node paramNode, String paramString, float paramFloat, boolean paramBoolean) throws IIOInvalidTreeException {
/* 1123 */     String str = getStringAttribute(paramNode, paramString, null, paramBoolean);
/* 1124 */     if (str == null) {
/* 1125 */       return paramFloat;
/*      */     }
/* 1127 */     return Float.parseFloat(str);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int getIntAttribute(Node paramNode, String paramString) throws IIOInvalidTreeException {
/* 1133 */     return getIntAttribute(paramNode, paramString, -1, true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private float getFloatAttribute(Node paramNode, String paramString) throws IIOInvalidTreeException {
/* 1139 */     return getFloatAttribute(paramNode, paramString, -1.0F, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getBooleanAttribute(Node paramNode, String paramString, boolean paramBoolean1, boolean paramBoolean2) throws IIOInvalidTreeException {
/* 1147 */     Node node = paramNode.getAttributes().getNamedItem(paramString);
/* 1148 */     if (node == null) {
/* 1149 */       if (!paramBoolean2) {
/* 1150 */         return paramBoolean1;
/*      */       }
/* 1152 */       fatal(paramNode, "Required attribute " + paramString + " not present!");
/*      */     } 
/*      */     
/* 1155 */     String str = node.getNodeValue();
/*      */     
/* 1157 */     if (str.equals("TRUE") || str.equals("true"))
/* 1158 */       return true; 
/* 1159 */     if (str.equals("FALSE") || str.equals("false")) {
/* 1160 */       return false;
/*      */     }
/* 1162 */     fatal(paramNode, "Attribute " + paramString + " must be 'TRUE' or 'FALSE'!");
/* 1163 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getBooleanAttribute(Node paramNode, String paramString) throws IIOInvalidTreeException {
/* 1170 */     return getBooleanAttribute(paramNode, paramString, false, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getEnumeratedAttribute(Node paramNode, String paramString, String[] paramArrayOfString, int paramInt, boolean paramBoolean) throws IIOInvalidTreeException {
/* 1178 */     Node node = paramNode.getAttributes().getNamedItem(paramString);
/* 1179 */     if (node == null) {
/* 1180 */       if (!paramBoolean) {
/* 1181 */         return paramInt;
/*      */       }
/* 1183 */       fatal(paramNode, "Required attribute " + paramString + " not present!");
/*      */     } 
/*      */     
/* 1186 */     String str = node.getNodeValue();
/* 1187 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 1188 */       if (str.equals(paramArrayOfString[b])) {
/* 1189 */         return b;
/*      */       }
/*      */     } 
/*      */     
/* 1193 */     fatal(paramNode, "Illegal value for attribute " + paramString + "!");
/* 1194 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getEnumeratedAttribute(Node paramNode, String paramString, String[] paramArrayOfString) throws IIOInvalidTreeException {
/* 1201 */     return getEnumeratedAttribute(paramNode, paramString, paramArrayOfString, -1, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getAttribute(Node paramNode, String paramString1, String paramString2, boolean paramBoolean) throws IIOInvalidTreeException {
/* 1208 */     Node node = paramNode.getAttributes().getNamedItem(paramString1);
/* 1209 */     if (node == null) {
/* 1210 */       if (!paramBoolean) {
/* 1211 */         return paramString2;
/*      */       }
/* 1213 */       fatal(paramNode, "Required attribute " + paramString1 + " not present!");
/*      */     } 
/*      */     
/* 1216 */     return node.getNodeValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getAttribute(Node paramNode, String paramString) throws IIOInvalidTreeException {
/* 1222 */     return getAttribute(paramNode, paramString, null, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public void mergeTree(String paramString, Node paramNode) throws IIOInvalidTreeException {
/* 1227 */     if (paramString.equals("javax_imageio_png_1.0")) {
/* 1228 */       if (paramNode == null) {
/* 1229 */         throw new IllegalArgumentException("root == null!");
/*      */       }
/* 1231 */       mergeNativeTree(paramNode);
/* 1232 */     } else if (paramString
/* 1233 */       .equals("javax_imageio_1.0")) {
/* 1234 */       if (paramNode == null) {
/* 1235 */         throw new IllegalArgumentException("root == null!");
/*      */       }
/* 1237 */       mergeStandardTree(paramNode);
/*      */     } else {
/* 1239 */       throw new IllegalArgumentException("Not a recognized format!");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void mergeNativeTree(Node paramNode) throws IIOInvalidTreeException {
/* 1245 */     Node node = paramNode;
/* 1246 */     if (!node.getNodeName().equals("javax_imageio_png_1.0")) {
/* 1247 */       fatal(node, "Root must be javax_imageio_png_1.0");
/*      */     }
/*      */     
/* 1250 */     node = node.getFirstChild();
/* 1251 */     while (node != null) {
/* 1252 */       String str = node.getNodeName();
/*      */       
/* 1254 */       if (str.equals("IHDR")) {
/* 1255 */         this.IHDR_width = getIntAttribute(node, "width");
/* 1256 */         this.IHDR_height = getIntAttribute(node, "height");
/* 1257 */         this
/* 1258 */           .IHDR_bitDepth = Integer.valueOf(IHDR_bitDepths[
/* 1259 */               getEnumeratedAttribute(node, "bitDepth", IHDR_bitDepths)]).intValue();
/*      */ 
/*      */         
/* 1262 */         this.IHDR_colorType = getEnumeratedAttribute(node, "colorType", IHDR_colorTypeNames);
/*      */         
/* 1264 */         this
/* 1265 */           .IHDR_compressionMethod = getEnumeratedAttribute(node, "compressionMethod", IHDR_compressionMethodNames);
/*      */         
/* 1267 */         this
/* 1268 */           .IHDR_filterMethod = getEnumeratedAttribute(node, "filterMethod", IHDR_filterMethodNames);
/*      */ 
/*      */         
/* 1271 */         this
/* 1272 */           .IHDR_interlaceMethod = getEnumeratedAttribute(node, "interlaceMethod", IHDR_interlaceMethodNames);
/*      */         
/* 1274 */         this.IHDR_present = true;
/* 1275 */       } else if (str.equals("PLTE")) {
/* 1276 */         byte[] arrayOfByte1 = new byte[256];
/* 1277 */         byte[] arrayOfByte2 = new byte[256];
/* 1278 */         byte[] arrayOfByte3 = new byte[256];
/* 1279 */         int i = -1;
/*      */         
/* 1281 */         Node node1 = node.getFirstChild();
/* 1282 */         if (node1 == null) {
/* 1283 */           fatal(node, "Palette has no entries!");
/*      */         }
/*      */         
/* 1286 */         while (node1 != null) {
/* 1287 */           if (!node1.getNodeName().equals("PLTEEntry")) {
/* 1288 */             fatal(node, "Only a PLTEEntry may be a child of a PLTE!");
/*      */           }
/*      */ 
/*      */           
/* 1292 */           int k = getIntAttribute(node1, "index");
/* 1293 */           if (k < 0 || k > 255) {
/* 1294 */             fatal(node, "Bad value for PLTEEntry attribute index!");
/*      */           }
/*      */           
/* 1297 */           if (k > i) {
/* 1298 */             i = k;
/*      */           }
/* 1300 */           arrayOfByte1[k] = 
/* 1301 */             (byte)getIntAttribute(node1, "red");
/* 1302 */           arrayOfByte2[k] = 
/* 1303 */             (byte)getIntAttribute(node1, "green");
/* 1304 */           arrayOfByte3[k] = 
/* 1305 */             (byte)getIntAttribute(node1, "blue");
/*      */           
/* 1307 */           node1 = node1.getNextSibling();
/*      */         } 
/*      */         
/* 1310 */         int j = i + 1;
/* 1311 */         this.PLTE_red = new byte[j];
/* 1312 */         this.PLTE_green = new byte[j];
/* 1313 */         this.PLTE_blue = new byte[j];
/* 1314 */         System.arraycopy(arrayOfByte1, 0, this.PLTE_red, 0, j);
/* 1315 */         System.arraycopy(arrayOfByte2, 0, this.PLTE_green, 0, j);
/* 1316 */         System.arraycopy(arrayOfByte3, 0, this.PLTE_blue, 0, j);
/* 1317 */         this.PLTE_present = true;
/* 1318 */       } else if (str.equals("bKGD")) {
/* 1319 */         this.bKGD_present = false;
/* 1320 */         Node node1 = node.getFirstChild();
/* 1321 */         if (node1 == null) {
/* 1322 */           fatal(node, "bKGD node has no children!");
/*      */         }
/* 1324 */         String str1 = node1.getNodeName();
/* 1325 */         if (str1.equals("bKGD_Palette")) {
/* 1326 */           this.bKGD_index = getIntAttribute(node1, "index");
/* 1327 */           this.bKGD_colorType = 3;
/* 1328 */         } else if (str1.equals("bKGD_Grayscale")) {
/* 1329 */           this.bKGD_gray = getIntAttribute(node1, "gray");
/* 1330 */           this.bKGD_colorType = 0;
/* 1331 */         } else if (str1.equals("bKGD_RGB")) {
/* 1332 */           this.bKGD_red = getIntAttribute(node1, "red");
/* 1333 */           this.bKGD_green = getIntAttribute(node1, "green");
/* 1334 */           this.bKGD_blue = getIntAttribute(node1, "blue");
/* 1335 */           this.bKGD_colorType = 2;
/*      */         } else {
/* 1337 */           fatal(node, "Bad child of a bKGD node!");
/*      */         } 
/* 1339 */         if (node1.getNextSibling() != null) {
/* 1340 */           fatal(node, "bKGD node has more than one child!");
/*      */         }
/*      */         
/* 1343 */         this.bKGD_present = true;
/* 1344 */       } else if (str.equals("cHRM")) {
/* 1345 */         this.cHRM_whitePointX = getIntAttribute(node, "whitePointX");
/* 1346 */         this.cHRM_whitePointY = getIntAttribute(node, "whitePointY");
/* 1347 */         this.cHRM_redX = getIntAttribute(node, "redX");
/* 1348 */         this.cHRM_redY = getIntAttribute(node, "redY");
/* 1349 */         this.cHRM_greenX = getIntAttribute(node, "greenX");
/* 1350 */         this.cHRM_greenY = getIntAttribute(node, "greenY");
/* 1351 */         this.cHRM_blueX = getIntAttribute(node, "blueX");
/* 1352 */         this.cHRM_blueY = getIntAttribute(node, "blueY");
/*      */         
/* 1354 */         this.cHRM_present = true;
/* 1355 */       } else if (str.equals("gAMA")) {
/* 1356 */         this.gAMA_gamma = getIntAttribute(node, "value");
/* 1357 */         this.gAMA_present = true;
/* 1358 */       } else if (str.equals("hIST")) {
/* 1359 */         char[] arrayOfChar = new char[256];
/* 1360 */         int i = -1;
/*      */         
/* 1362 */         Node node1 = node.getFirstChild();
/* 1363 */         if (node1 == null) {
/* 1364 */           fatal(node, "hIST node has no children!");
/*      */         }
/*      */         
/* 1367 */         while (node1 != null) {
/* 1368 */           if (!node1.getNodeName().equals("hISTEntry")) {
/* 1369 */             fatal(node, "Only a hISTEntry may be a child of a hIST!");
/*      */           }
/*      */ 
/*      */           
/* 1373 */           int k = getIntAttribute(node1, "index");
/* 1374 */           if (k < 0 || k > 255) {
/* 1375 */             fatal(node, "Bad value for histEntry attribute index!");
/*      */           }
/*      */           
/* 1378 */           if (k > i) {
/* 1379 */             i = k;
/*      */           }
/* 1381 */           arrayOfChar[k] = 
/* 1382 */             (char)getIntAttribute(node1, "value");
/*      */           
/* 1384 */           node1 = node1.getNextSibling();
/*      */         } 
/*      */         
/* 1387 */         int j = i + 1;
/* 1388 */         this.hIST_histogram = new char[j];
/* 1389 */         System.arraycopy(arrayOfChar, 0, this.hIST_histogram, 0, j);
/*      */         
/* 1391 */         this.hIST_present = true;
/* 1392 */       } else if (str.equals("iCCP")) {
/* 1393 */         this.iCCP_profileName = getAttribute(node, "profileName");
/* 1394 */         this
/* 1395 */           .iCCP_compressionMethod = getEnumeratedAttribute(node, "compressionMethod", iCCP_compressionMethodNames);
/*      */ 
/*      */         
/* 1398 */         Object object = ((IIOMetadataNode)node).getUserObject();
/* 1399 */         if (object == null) {
/* 1400 */           fatal(node, "No ICCP profile present in user object!");
/*      */         }
/* 1402 */         if (!(object instanceof byte[])) {
/* 1403 */           fatal(node, "User object not a byte array!");
/*      */         }
/*      */         
/* 1406 */         this
/* 1407 */           .iCCP_compressedProfile = (byte[])((byte[])object).clone();
/*      */         
/* 1409 */         this.iCCP_present = true;
/* 1410 */       } else if (str.equals("iTXt")) {
/* 1411 */         Node node1 = node.getFirstChild();
/* 1412 */         while (node1 != null) {
/* 1413 */           if (!node1.getNodeName().equals("iTXtEntry")) {
/* 1414 */             fatal(node, "Only an iTXtEntry may be a child of an iTXt!");
/*      */           }
/*      */ 
/*      */           
/* 1418 */           String str1 = getAttribute(node1, "keyword");
/* 1419 */           if (isValidKeyword(str1)) {
/* 1420 */             this.iTXt_keyword.add(str1);
/*      */ 
/*      */             
/* 1423 */             boolean bool = getBooleanAttribute(node1, "compressionFlag");
/* 1424 */             this.iTXt_compressionFlag.add(Boolean.valueOf(bool));
/*      */ 
/*      */             
/* 1427 */             String str2 = getAttribute(node1, "compressionMethod");
/* 1428 */             this.iTXt_compressionMethod.add(Integer.valueOf(str2));
/*      */ 
/*      */             
/* 1431 */             String str3 = getAttribute(node1, "languageTag");
/* 1432 */             this.iTXt_languageTag.add(str3);
/*      */ 
/*      */             
/* 1435 */             String str4 = getAttribute(node1, "translatedKeyword");
/* 1436 */             this.iTXt_translatedKeyword.add(str4);
/*      */             
/* 1438 */             String str5 = getAttribute(node1, "text");
/* 1439 */             this.iTXt_text.add(str5);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1444 */           node1 = node1.getNextSibling();
/*      */         } 
/* 1446 */       } else if (str.equals("pHYs")) {
/* 1447 */         this
/* 1448 */           .pHYs_pixelsPerUnitXAxis = getIntAttribute(node, "pixelsPerUnitXAxis");
/* 1449 */         this
/* 1450 */           .pHYs_pixelsPerUnitYAxis = getIntAttribute(node, "pixelsPerUnitYAxis");
/* 1451 */         this
/* 1452 */           .pHYs_unitSpecifier = getEnumeratedAttribute(node, "unitSpecifier", unitSpecifierNames);
/*      */ 
/*      */         
/* 1455 */         this.pHYs_present = true;
/* 1456 */       } else if (str.equals("sBIT")) {
/* 1457 */         this.sBIT_present = false;
/* 1458 */         Node node1 = node.getFirstChild();
/* 1459 */         if (node1 == null) {
/* 1460 */           fatal(node, "sBIT node has no children!");
/*      */         }
/* 1462 */         String str1 = node1.getNodeName();
/* 1463 */         if (str1.equals("sBIT_Grayscale")) {
/* 1464 */           this.sBIT_grayBits = getIntAttribute(node1, "gray");
/* 1465 */           this.sBIT_colorType = 0;
/* 1466 */         } else if (str1.equals("sBIT_GrayAlpha")) {
/* 1467 */           this.sBIT_grayBits = getIntAttribute(node1, "gray");
/* 1468 */           this.sBIT_alphaBits = getIntAttribute(node1, "alpha");
/* 1469 */           this.sBIT_colorType = 4;
/* 1470 */         } else if (str1.equals("sBIT_RGB")) {
/* 1471 */           this.sBIT_redBits = getIntAttribute(node1, "red");
/* 1472 */           this.sBIT_greenBits = getIntAttribute(node1, "green");
/* 1473 */           this.sBIT_blueBits = getIntAttribute(node1, "blue");
/* 1474 */           this.sBIT_colorType = 2;
/* 1475 */         } else if (str1.equals("sBIT_RGBAlpha")) {
/* 1476 */           this.sBIT_redBits = getIntAttribute(node1, "red");
/* 1477 */           this.sBIT_greenBits = getIntAttribute(node1, "green");
/* 1478 */           this.sBIT_blueBits = getIntAttribute(node1, "blue");
/* 1479 */           this.sBIT_alphaBits = getIntAttribute(node1, "alpha");
/* 1480 */           this.sBIT_colorType = 6;
/* 1481 */         } else if (str1.equals("sBIT_Palette")) {
/* 1482 */           this.sBIT_redBits = getIntAttribute(node1, "red");
/* 1483 */           this.sBIT_greenBits = getIntAttribute(node1, "green");
/* 1484 */           this.sBIT_blueBits = getIntAttribute(node1, "blue");
/* 1485 */           this.sBIT_colorType = 3;
/*      */         } else {
/* 1487 */           fatal(node, "Bad child of an sBIT node!");
/*      */         } 
/* 1489 */         if (node1.getNextSibling() != null) {
/* 1490 */           fatal(node, "sBIT node has more than one child!");
/*      */         }
/*      */         
/* 1493 */         this.sBIT_present = true;
/* 1494 */       } else if (str.equals("sPLT")) {
/* 1495 */         this.sPLT_paletteName = getAttribute(node, "name");
/* 1496 */         this.sPLT_sampleDepth = getIntAttribute(node, "sampleDepth");
/*      */         
/* 1498 */         int[] arrayOfInt1 = new int[256];
/* 1499 */         int[] arrayOfInt2 = new int[256];
/* 1500 */         int[] arrayOfInt3 = new int[256];
/* 1501 */         int[] arrayOfInt4 = new int[256];
/* 1502 */         int[] arrayOfInt5 = new int[256];
/* 1503 */         int i = -1;
/*      */         
/* 1505 */         Node node1 = node.getFirstChild();
/* 1506 */         if (node1 == null) {
/* 1507 */           fatal(node, "sPLT node has no children!");
/*      */         }
/*      */         
/* 1510 */         while (node1 != null) {
/* 1511 */           if (!node1.getNodeName().equals("sPLTEntry")) {
/* 1512 */             fatal(node, "Only an sPLTEntry may be a child of an sPLT!");
/*      */           }
/*      */ 
/*      */           
/* 1516 */           int k = getIntAttribute(node1, "index");
/* 1517 */           if (k < 0 || k > 255) {
/* 1518 */             fatal(node, "Bad value for PLTEEntry attribute index!");
/*      */           }
/*      */           
/* 1521 */           if (k > i) {
/* 1522 */             i = k;
/*      */           }
/* 1524 */           arrayOfInt1[k] = getIntAttribute(node1, "red");
/* 1525 */           arrayOfInt2[k] = getIntAttribute(node1, "green");
/* 1526 */           arrayOfInt3[k] = getIntAttribute(node1, "blue");
/* 1527 */           arrayOfInt4[k] = getIntAttribute(node1, "alpha");
/* 1528 */           arrayOfInt5[k] = 
/* 1529 */             getIntAttribute(node1, "frequency");
/*      */           
/* 1531 */           node1 = node1.getNextSibling();
/*      */         } 
/*      */         
/* 1534 */         int j = i + 1;
/* 1535 */         this.sPLT_red = new int[j];
/* 1536 */         this.sPLT_green = new int[j];
/* 1537 */         this.sPLT_blue = new int[j];
/* 1538 */         this.sPLT_alpha = new int[j];
/* 1539 */         this.sPLT_frequency = new int[j];
/* 1540 */         System.arraycopy(arrayOfInt1, 0, this.sPLT_red, 0, j);
/* 1541 */         System.arraycopy(arrayOfInt2, 0, this.sPLT_green, 0, j);
/* 1542 */         System.arraycopy(arrayOfInt3, 0, this.sPLT_blue, 0, j);
/* 1543 */         System.arraycopy(arrayOfInt4, 0, this.sPLT_alpha, 0, j);
/* 1544 */         System.arraycopy(arrayOfInt5, 0, this.sPLT_frequency, 0, j);
/*      */ 
/*      */         
/* 1547 */         this.sPLT_present = true;
/* 1548 */       } else if (str.equals("sRGB")) {
/* 1549 */         this
/* 1550 */           .sRGB_renderingIntent = getEnumeratedAttribute(node, "renderingIntent", renderingIntentNames);
/*      */ 
/*      */         
/* 1553 */         this.sRGB_present = true;
/* 1554 */       } else if (str.equals("tEXt")) {
/* 1555 */         Node node1 = node.getFirstChild();
/* 1556 */         while (node1 != null) {
/* 1557 */           if (!node1.getNodeName().equals("tEXtEntry")) {
/* 1558 */             fatal(node, "Only an tEXtEntry may be a child of an tEXt!");
/*      */           }
/*      */ 
/*      */           
/* 1562 */           String str1 = getAttribute(node1, "keyword");
/* 1563 */           this.tEXt_keyword.add(str1);
/*      */           
/* 1565 */           String str2 = getAttribute(node1, "value");
/* 1566 */           this.tEXt_text.add(str2);
/*      */           
/* 1568 */           node1 = node1.getNextSibling();
/*      */         } 
/* 1570 */       } else if (str.equals("tIME")) {
/* 1571 */         this.tIME_year = getIntAttribute(node, "year");
/* 1572 */         this.tIME_month = getIntAttribute(node, "month");
/* 1573 */         this.tIME_day = getIntAttribute(node, "day");
/* 1574 */         this.tIME_hour = getIntAttribute(node, "hour");
/* 1575 */         this.tIME_minute = getIntAttribute(node, "minute");
/* 1576 */         this.tIME_second = getIntAttribute(node, "second");
/*      */         
/* 1578 */         this.tIME_present = true;
/* 1579 */       } else if (str.equals("tRNS")) {
/* 1580 */         this.tRNS_present = false;
/* 1581 */         Node node1 = node.getFirstChild();
/* 1582 */         if (node1 == null) {
/* 1583 */           fatal(node, "tRNS node has no children!");
/*      */         }
/* 1585 */         String str1 = node1.getNodeName();
/* 1586 */         if (str1.equals("tRNS_Palette")) {
/* 1587 */           byte[] arrayOfByte = new byte[256];
/* 1588 */           int i = -1;
/*      */           
/* 1590 */           Node node2 = node1.getFirstChild();
/* 1591 */           if (node2 == null) {
/* 1592 */             fatal(node, "tRNS_Palette node has no children!");
/*      */           }
/* 1594 */           while (node2 != null) {
/* 1595 */             if (!node2.getNodeName().equals("tRNS_PaletteEntry"))
/*      */             {
/* 1597 */               fatal(node, "Only a tRNS_PaletteEntry may be a child of a tRNS_Palette!");
/*      */             }
/*      */ 
/*      */             
/* 1601 */             int k = getIntAttribute(node2, "index");
/* 1602 */             if (k < 0 || k > 255) {
/* 1603 */               fatal(node, "Bad value for tRNS_PaletteEntry attribute index!");
/*      */             }
/*      */             
/* 1606 */             if (k > i) {
/* 1607 */               i = k;
/*      */             }
/* 1609 */             arrayOfByte[k] = 
/* 1610 */               (byte)getIntAttribute(node2, "alpha");
/*      */ 
/*      */ 
/*      */             
/* 1614 */             node2 = node2.getNextSibling();
/*      */           } 
/*      */           
/* 1617 */           int j = i + 1;
/* 1618 */           this.tRNS_alpha = new byte[j];
/* 1619 */           this.tRNS_colorType = 3;
/* 1620 */           System.arraycopy(arrayOfByte, 0, this.tRNS_alpha, 0, j);
/* 1621 */         } else if (str1.equals("tRNS_Grayscale")) {
/* 1622 */           this.tRNS_gray = getIntAttribute(node1, "gray");
/* 1623 */           this.tRNS_colorType = 0;
/* 1624 */         } else if (str1.equals("tRNS_RGB")) {
/* 1625 */           this.tRNS_red = getIntAttribute(node1, "red");
/* 1626 */           this.tRNS_green = getIntAttribute(node1, "green");
/* 1627 */           this.tRNS_blue = getIntAttribute(node1, "blue");
/* 1628 */           this.tRNS_colorType = 2;
/*      */         } else {
/* 1630 */           fatal(node, "Bad child of a tRNS node!");
/*      */         } 
/* 1632 */         if (node1.getNextSibling() != null) {
/* 1633 */           fatal(node, "tRNS node has more than one child!");
/*      */         }
/*      */         
/* 1636 */         this.tRNS_present = true;
/* 1637 */       } else if (str.equals("zTXt")) {
/* 1638 */         Node node1 = node.getFirstChild();
/* 1639 */         while (node1 != null) {
/* 1640 */           if (!node1.getNodeName().equals("zTXtEntry")) {
/* 1641 */             fatal(node, "Only an zTXtEntry may be a child of an zTXt!");
/*      */           }
/*      */ 
/*      */           
/* 1645 */           String str1 = getAttribute(node1, "keyword");
/* 1646 */           this.zTXt_keyword.add(str1);
/*      */ 
/*      */           
/* 1649 */           int i = getEnumeratedAttribute(node1, "compressionMethod", zTXt_compressionMethodNames);
/*      */           
/* 1651 */           this.zTXt_compressionMethod.add(new Integer(i));
/*      */           
/* 1653 */           String str2 = getAttribute(node1, "text");
/* 1654 */           this.zTXt_text.add(str2);
/*      */           
/* 1656 */           node1 = node1.getNextSibling();
/*      */         } 
/* 1658 */       } else if (str.equals("UnknownChunks")) {
/* 1659 */         Node node1 = node.getFirstChild();
/* 1660 */         while (node1 != null) {
/* 1661 */           if (!node1.getNodeName().equals("UnknownChunk")) {
/* 1662 */             fatal(node, "Only an UnknownChunk may be a child of an UnknownChunks!");
/*      */           }
/*      */           
/* 1665 */           String str1 = getAttribute(node1, "type");
/*      */           
/* 1667 */           Object object = ((IIOMetadataNode)node1).getUserObject();
/*      */           
/* 1669 */           if (str1.length() != 4) {
/* 1670 */             fatal(node1, "Chunk type must be 4 characters!");
/*      */           }
/*      */           
/* 1673 */           if (object == null) {
/* 1674 */             fatal(node1, "No chunk data present in user object!");
/*      */           }
/*      */           
/* 1677 */           if (!(object instanceof byte[])) {
/* 1678 */             fatal(node1, "User object not a byte array!");
/*      */           }
/*      */           
/* 1681 */           this.unknownChunkType.add(str1);
/* 1682 */           this.unknownChunkData.add(((byte[])object).clone());
/*      */           
/* 1684 */           node1 = node1.getNextSibling();
/*      */         } 
/*      */       } else {
/* 1687 */         fatal(node, "Unknown child of root node!");
/*      */       } 
/*      */       
/* 1690 */       node = node.getNextSibling();
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
/*      */   private boolean isValidKeyword(String paramString) {
/* 1704 */     int i = paramString.length();
/* 1705 */     if (i < 1 || i >= 80) {
/* 1706 */       return false;
/*      */     }
/* 1708 */     if (paramString.startsWith(" ") || paramString.endsWith(" ") || paramString.contains("  ")) {
/* 1709 */       return false;
/*      */     }
/* 1711 */     return isISOLatin(paramString, false);
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
/*      */   private boolean isISOLatin(String paramString, boolean paramBoolean) {
/* 1724 */     int i = paramString.length();
/* 1725 */     for (byte b = 0; b < i; b++) {
/* 1726 */       char c = paramString.charAt(b);
/* 1727 */       if (c < ' ' || c > 'ÿ' || (c > '~' && c < '¡'))
/*      */       {
/*      */         
/* 1730 */         if (!paramBoolean || c != '\020') {
/* 1731 */           return false;
/*      */         }
/*      */       }
/*      */     } 
/* 1735 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private void mergeStandardTree(Node paramNode) throws IIOInvalidTreeException {
/* 1740 */     Node node = paramNode;
/*      */     
/* 1742 */     if (!node.getNodeName().equals("javax_imageio_1.0")) {
/* 1743 */       fatal(node, "Root must be javax_imageio_1.0");
/*      */     }
/*      */ 
/*      */     
/* 1747 */     node = node.getFirstChild();
/* 1748 */     while (node != null) {
/* 1749 */       String str = node.getNodeName();
/*      */       
/* 1751 */       if (str.equals("Chroma")) {
/* 1752 */         Node node1 = node.getFirstChild();
/* 1753 */         while (node1 != null) {
/* 1754 */           String str1 = node1.getNodeName();
/* 1755 */           if (str1.equals("Gamma")) {
/* 1756 */             float f = getFloatAttribute(node1, "value");
/* 1757 */             this.gAMA_present = true;
/* 1758 */             this.gAMA_gamma = (int)((f * 100000.0F) + 0.5D);
/* 1759 */           } else if (str1.equals("Palette")) {
/* 1760 */             byte[] arrayOfByte1 = new byte[256];
/* 1761 */             byte[] arrayOfByte2 = new byte[256];
/* 1762 */             byte[] arrayOfByte3 = new byte[256];
/* 1763 */             int i = -1;
/*      */             
/* 1765 */             Node node2 = node1.getFirstChild();
/* 1766 */             while (node2 != null) {
/* 1767 */               int k = getIntAttribute(node2, "index");
/* 1768 */               if (k >= 0 && k <= 255) {
/* 1769 */                 arrayOfByte1[k] = 
/* 1770 */                   (byte)getIntAttribute(node2, "red");
/* 1771 */                 arrayOfByte2[k] = 
/* 1772 */                   (byte)getIntAttribute(node2, "green");
/* 1773 */                 arrayOfByte3[k] = 
/* 1774 */                   (byte)getIntAttribute(node2, "blue");
/* 1775 */                 if (k > i) {
/* 1776 */                   i = k;
/*      */                 }
/*      */               } 
/* 1779 */               node2 = node2.getNextSibling();
/*      */             } 
/*      */             
/* 1782 */             int j = i + 1;
/* 1783 */             this.PLTE_red = new byte[j];
/* 1784 */             this.PLTE_green = new byte[j];
/* 1785 */             this.PLTE_blue = new byte[j];
/* 1786 */             System.arraycopy(arrayOfByte1, 0, this.PLTE_red, 0, j);
/* 1787 */             System.arraycopy(arrayOfByte2, 0, this.PLTE_green, 0, j);
/* 1788 */             System.arraycopy(arrayOfByte3, 0, this.PLTE_blue, 0, j);
/* 1789 */             this.PLTE_present = true;
/* 1790 */           } else if (str1.equals("BackgroundIndex")) {
/* 1791 */             this.bKGD_present = true;
/* 1792 */             this.bKGD_colorType = 3;
/* 1793 */             this.bKGD_index = getIntAttribute(node1, "value");
/* 1794 */           } else if (str1.equals("BackgroundColor")) {
/* 1795 */             int i = getIntAttribute(node1, "red");
/* 1796 */             int j = getIntAttribute(node1, "green");
/* 1797 */             int k = getIntAttribute(node1, "blue");
/* 1798 */             if (i == j && i == k) {
/* 1799 */               this.bKGD_colorType = 0;
/* 1800 */               this.bKGD_gray = i;
/*      */             } else {
/* 1802 */               this.bKGD_red = i;
/* 1803 */               this.bKGD_green = j;
/* 1804 */               this.bKGD_blue = k;
/*      */             } 
/* 1806 */             this.bKGD_present = true;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1811 */           node1 = node1.getNextSibling();
/*      */         } 
/* 1813 */       } else if (str.equals("Compression")) {
/* 1814 */         Node node1 = node.getFirstChild();
/* 1815 */         while (node1 != null) {
/* 1816 */           String str1 = node1.getNodeName();
/* 1817 */           if (str1.equals("NumProgressiveScans")) {
/*      */             
/* 1819 */             int i = getIntAttribute(node1, "value");
/* 1820 */             this.IHDR_interlaceMethod = (i > 1) ? 1 : 0;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1825 */           node1 = node1.getNextSibling();
/*      */         } 
/* 1827 */       } else if (str.equals("Data")) {
/* 1828 */         Node node1 = node.getFirstChild();
/* 1829 */         while (node1 != null) {
/* 1830 */           String str1 = node1.getNodeName();
/* 1831 */           if (str1.equals("BitsPerSample")) {
/* 1832 */             String str2 = getAttribute(node1, "value");
/* 1833 */             StringTokenizer stringTokenizer = new StringTokenizer(str2);
/* 1834 */             int i = -1;
/* 1835 */             while (stringTokenizer.hasMoreTokens()) {
/* 1836 */               int j = Integer.parseInt(stringTokenizer.nextToken());
/* 1837 */               if (j > i) {
/* 1838 */                 i = j;
/*      */               }
/*      */             } 
/* 1841 */             if (i < 1) {
/* 1842 */               i = 1;
/*      */             }
/* 1844 */             if (i == 3) i = 4; 
/* 1845 */             if (i > 4 || i < 8) {
/* 1846 */               i = 8;
/*      */             }
/* 1848 */             if (i > 8) {
/* 1849 */               i = 16;
/*      */             }
/* 1851 */             this.IHDR_bitDepth = i;
/* 1852 */           } else if (str1.equals("SignificantBitsPerSample")) {
/* 1853 */             String str2 = getAttribute(node1, "value");
/* 1854 */             StringTokenizer stringTokenizer = new StringTokenizer(str2);
/* 1855 */             int i = stringTokenizer.countTokens();
/* 1856 */             if (i == 1) {
/* 1857 */               this.sBIT_colorType = 0;
/* 1858 */               this.sBIT_grayBits = Integer.parseInt(stringTokenizer.nextToken());
/* 1859 */             } else if (i == 2) {
/* 1860 */               this.sBIT_colorType = 4;
/*      */               
/* 1862 */               this.sBIT_grayBits = Integer.parseInt(stringTokenizer.nextToken());
/* 1863 */               this.sBIT_alphaBits = Integer.parseInt(stringTokenizer.nextToken());
/* 1864 */             } else if (i == 3) {
/* 1865 */               this.sBIT_colorType = 2;
/* 1866 */               this.sBIT_redBits = Integer.parseInt(stringTokenizer.nextToken());
/* 1867 */               this.sBIT_greenBits = Integer.parseInt(stringTokenizer.nextToken());
/* 1868 */               this.sBIT_blueBits = Integer.parseInt(stringTokenizer.nextToken());
/* 1869 */             } else if (i == 4) {
/* 1870 */               this.sBIT_colorType = 6;
/*      */               
/* 1872 */               this.sBIT_redBits = Integer.parseInt(stringTokenizer.nextToken());
/* 1873 */               this.sBIT_greenBits = Integer.parseInt(stringTokenizer.nextToken());
/* 1874 */               this.sBIT_blueBits = Integer.parseInt(stringTokenizer.nextToken());
/* 1875 */               this.sBIT_alphaBits = Integer.parseInt(stringTokenizer.nextToken());
/*      */             } 
/* 1877 */             if (i >= 1 && i <= 4) {
/* 1878 */               this.sBIT_present = true;
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1884 */           node1 = node1.getNextSibling();
/*      */         } 
/* 1886 */       } else if (str.equals("Dimension")) {
/* 1887 */         boolean bool1 = false;
/* 1888 */         boolean bool2 = false;
/* 1889 */         boolean bool3 = false;
/*      */         
/* 1891 */         float f1 = -1.0F;
/* 1892 */         float f2 = -1.0F;
/* 1893 */         float f3 = -1.0F;
/*      */         
/* 1895 */         Node node1 = node.getFirstChild();
/* 1896 */         while (node1 != null) {
/* 1897 */           String str1 = node1.getNodeName();
/* 1898 */           if (str1.equals("PixelAspectRatio")) {
/* 1899 */             f3 = getFloatAttribute(node1, "value");
/* 1900 */             bool3 = true;
/* 1901 */           } else if (str1.equals("HorizontalPixelSize")) {
/* 1902 */             f1 = getFloatAttribute(node1, "value");
/* 1903 */             bool1 = true;
/* 1904 */           } else if (str1.equals("VerticalPixelSize")) {
/* 1905 */             f2 = getFloatAttribute(node1, "value");
/* 1906 */             bool2 = true;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1917 */           node1 = node1.getNextSibling();
/*      */         } 
/*      */         
/* 1920 */         if (bool1 && bool2) {
/* 1921 */           this.pHYs_present = true;
/* 1922 */           this.pHYs_unitSpecifier = 1;
/* 1923 */           this.pHYs_pixelsPerUnitXAxis = (int)(f1 * 1000.0F + 0.5F);
/* 1924 */           this.pHYs_pixelsPerUnitYAxis = (int)(f2 * 1000.0F + 0.5F);
/* 1925 */         } else if (bool3) {
/* 1926 */           this.pHYs_present = true;
/* 1927 */           this.pHYs_unitSpecifier = 0;
/*      */ 
/*      */           
/* 1930 */           byte b = 1;
/* 1931 */           for (; b < 100; b++) {
/* 1932 */             int i = (int)(f3 * b);
/* 1933 */             if (Math.abs((i / b) - f3) < 0.001D) {
/*      */               break;
/*      */             }
/*      */           } 
/* 1937 */           this.pHYs_pixelsPerUnitXAxis = (int)(f3 * b);
/* 1938 */           this.pHYs_pixelsPerUnitYAxis = b;
/*      */         } 
/* 1940 */       } else if (str.equals("Document")) {
/* 1941 */         Node node1 = node.getFirstChild();
/* 1942 */         while (node1 != null) {
/* 1943 */           String str1 = node1.getNodeName();
/* 1944 */           if (str1.equals("ImageModificationTime")) {
/* 1945 */             this.tIME_present = true;
/* 1946 */             this.tIME_year = getIntAttribute(node1, "year");
/* 1947 */             this.tIME_month = getIntAttribute(node1, "month");
/* 1948 */             this.tIME_day = getIntAttribute(node1, "day");
/* 1949 */             this
/* 1950 */               .tIME_hour = getIntAttribute(node1, "hour", 0, false);
/* 1951 */             this
/* 1952 */               .tIME_minute = getIntAttribute(node1, "minute", 0, false);
/* 1953 */             this
/* 1954 */               .tIME_second = getIntAttribute(node1, "second", 0, false);
/*      */           } 
/*      */ 
/*      */           
/* 1958 */           node1 = node1.getNextSibling();
/*      */         } 
/* 1960 */       } else if (str.equals("Text")) {
/* 1961 */         Node node1 = node.getFirstChild();
/* 1962 */         while (node1 != null) {
/* 1963 */           String str1 = node1.getNodeName();
/* 1964 */           if (str1.equals("TextEntry")) {
/*      */             
/* 1966 */             String str2 = getAttribute(node1, "keyword", "", false);
/* 1967 */             String str3 = getAttribute(node1, "value");
/*      */             
/* 1969 */             String str4 = getAttribute(node1, "language", "", false);
/*      */             
/* 1971 */             String str5 = getAttribute(node1, "compression", "none", false);
/*      */             
/* 1973 */             if (isValidKeyword(str2))
/*      */             {
/* 1975 */               if (isISOLatin(str3, true)) {
/* 1976 */                 if (str5.equals("zip")) {
/*      */                   
/* 1978 */                   this.zTXt_keyword.add(str2);
/* 1979 */                   this.zTXt_text.add(str3);
/* 1980 */                   this.zTXt_compressionMethod.add(Integer.valueOf(0));
/*      */                 } else {
/*      */                   
/* 1983 */                   this.tEXt_keyword.add(str2);
/* 1984 */                   this.tEXt_text.add(str3);
/*      */                 } 
/*      */               } else {
/*      */                 
/* 1988 */                 this.iTXt_keyword.add(str2);
/* 1989 */                 this.iTXt_compressionFlag.add(Boolean.valueOf(str5.equals("zip")));
/* 1990 */                 this.iTXt_compressionMethod.add(Integer.valueOf(0));
/* 1991 */                 this.iTXt_languageTag.add(str4);
/* 1992 */                 this.iTXt_translatedKeyword.add(str2);
/* 1993 */                 this.iTXt_text.add(str3);
/*      */               }  } 
/*      */           } 
/* 1996 */           node1 = node1.getNextSibling();
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2014 */       node = node.getNextSibling();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void reset() {
/* 2020 */     this.IHDR_present = false;
/* 2021 */     this.PLTE_present = false;
/* 2022 */     this.bKGD_present = false;
/* 2023 */     this.cHRM_present = false;
/* 2024 */     this.gAMA_present = false;
/* 2025 */     this.hIST_present = false;
/* 2026 */     this.iCCP_present = false;
/* 2027 */     this.iTXt_keyword = new ArrayList<>();
/* 2028 */     this.iTXt_compressionFlag = new ArrayList<>();
/* 2029 */     this.iTXt_compressionMethod = new ArrayList<>();
/* 2030 */     this.iTXt_languageTag = new ArrayList<>();
/* 2031 */     this.iTXt_translatedKeyword = new ArrayList<>();
/* 2032 */     this.iTXt_text = new ArrayList<>();
/* 2033 */     this.pHYs_present = false;
/* 2034 */     this.sBIT_present = false;
/* 2035 */     this.sPLT_present = false;
/* 2036 */     this.sRGB_present = false;
/* 2037 */     this.tEXt_keyword = new ArrayList<>();
/* 2038 */     this.tEXt_text = new ArrayList<>();
/* 2039 */     this.tIME_present = false;
/* 2040 */     this.tRNS_present = false;
/* 2041 */     this.zTXt_keyword = new ArrayList<>();
/* 2042 */     this.zTXt_compressionMethod = new ArrayList<>();
/* 2043 */     this.zTXt_text = new ArrayList<>();
/* 2044 */     this.unknownChunkType = new ArrayList<>();
/* 2045 */     this.unknownChunkData = (ArrayList)new ArrayList<>();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/png/PNGMetadata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */