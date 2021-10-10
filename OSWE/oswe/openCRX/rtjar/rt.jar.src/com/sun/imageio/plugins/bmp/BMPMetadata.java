/*     */ package com.sun.imageio.plugins.bmp;
/*     */ 
/*     */ import com.sun.imageio.plugins.common.I18N;
/*     */ import com.sun.imageio.plugins.common.ImageUtil;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.List;
/*     */ import javax.imageio.metadata.IIOMetadata;
/*     */ import javax.imageio.metadata.IIOMetadataNode;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class BMPMetadata
/*     */   extends IIOMetadata
/*     */   implements BMPConstants
/*     */ {
/*     */   public static final String nativeMetadataFormatName = "javax_imageio_bmp_1.0";
/*     */   public String bmpVersion;
/*     */   public int width;
/*     */   public int height;
/*     */   public short bitsPerPixel;
/*     */   public int compression;
/*     */   public int imageSize;
/*     */   public int xPixelsPerMeter;
/*     */   public int yPixelsPerMeter;
/*     */   public int colorsUsed;
/*     */   public int colorsImportant;
/*     */   public int redMask;
/*     */   public int greenMask;
/*     */   public int blueMask;
/*     */   public int alphaMask;
/*     */   public int colorSpace;
/*     */   public double redX;
/*     */   public double redY;
/*     */   public double redZ;
/*     */   public double greenX;
/*     */   public double greenY;
/*     */   public double greenZ;
/*     */   public double blueX;
/*     */   public double blueY;
/*     */   public double blueZ;
/*     */   public int gammaRed;
/*     */   public int gammaGreen;
/*     */   public int gammaBlue;
/*     */   public int intent;
/*  88 */   public byte[] palette = null;
/*     */   
/*     */   public int paletteSize;
/*     */   
/*     */   public int red;
/*     */   
/*     */   public int green;
/*     */   public int blue;
/*  96 */   public List comments = null;
/*     */   
/*     */   public BMPMetadata() {
/*  99 */     super(true, "javax_imageio_bmp_1.0", "com.sun.imageio.plugins.bmp.BMPMetadataFormat", null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() {
/* 106 */     return true;
/*     */   }
/*     */   
/*     */   public Node getAsTree(String paramString) {
/* 110 */     if (paramString.equals("javax_imageio_bmp_1.0"))
/* 111 */       return getNativeTree(); 
/* 112 */     if (paramString
/* 113 */       .equals("javax_imageio_1.0")) {
/* 114 */       return getStandardTree();
/*     */     }
/* 116 */     throw new IllegalArgumentException(I18N.getString("BMPMetadata0"));
/*     */   }
/*     */ 
/*     */   
/*     */   private String toISO8859(byte[] paramArrayOfbyte) {
/*     */     try {
/* 122 */       return new String(paramArrayOfbyte, "ISO-8859-1");
/* 123 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 124 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   private Node getNativeTree() {
/* 129 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("javax_imageio_bmp_1.0");
/*     */ 
/*     */     
/* 132 */     addChildNode(iIOMetadataNode1, "BMPVersion", this.bmpVersion);
/* 133 */     addChildNode(iIOMetadataNode1, "Width", new Integer(this.width));
/* 134 */     addChildNode(iIOMetadataNode1, "Height", new Integer(this.height));
/* 135 */     addChildNode(iIOMetadataNode1, "BitsPerPixel", new Short(this.bitsPerPixel));
/* 136 */     addChildNode(iIOMetadataNode1, "Compression", new Integer(this.compression));
/* 137 */     addChildNode(iIOMetadataNode1, "ImageSize", new Integer(this.imageSize));
/*     */     
/* 139 */     IIOMetadataNode iIOMetadataNode2 = addChildNode(iIOMetadataNode1, "PixelsPerMeter", null);
/* 140 */     addChildNode(iIOMetadataNode2, "X", new Integer(this.xPixelsPerMeter));
/* 141 */     addChildNode(iIOMetadataNode2, "Y", new Integer(this.yPixelsPerMeter));
/*     */     
/* 143 */     addChildNode(iIOMetadataNode1, "ColorsUsed", new Integer(this.colorsUsed));
/* 144 */     addChildNode(iIOMetadataNode1, "ColorsImportant", new Integer(this.colorsImportant));
/*     */     
/* 146 */     int i = 0; int j;
/* 147 */     for (j = 0; j < this.bmpVersion.length(); j++) {
/* 148 */       if (Character.isDigit(this.bmpVersion.charAt(j)))
/* 149 */         i = this.bmpVersion.charAt(j) - 48; 
/*     */     } 
/* 151 */     if (i >= 4) {
/* 152 */       iIOMetadataNode2 = addChildNode(iIOMetadataNode1, "Mask", null);
/* 153 */       addChildNode(iIOMetadataNode2, "Red", new Integer(this.redMask));
/* 154 */       addChildNode(iIOMetadataNode2, "Green", new Integer(this.greenMask));
/* 155 */       addChildNode(iIOMetadataNode2, "Blue", new Integer(this.blueMask));
/* 156 */       addChildNode(iIOMetadataNode2, "Alpha", new Integer(this.alphaMask));
/*     */       
/* 158 */       addChildNode(iIOMetadataNode1, "ColorSpaceType", new Integer(this.colorSpace));
/*     */       
/* 160 */       iIOMetadataNode2 = addChildNode(iIOMetadataNode1, "CIEXYZEndPoints", null);
/* 161 */       addXYZPoints(iIOMetadataNode2, "Red", this.redX, this.redY, this.redZ);
/* 162 */       addXYZPoints(iIOMetadataNode2, "Green", this.greenX, this.greenY, this.greenZ);
/* 163 */       addXYZPoints(iIOMetadataNode2, "Blue", this.blueX, this.blueY, this.blueZ);
/*     */       
/* 165 */       iIOMetadataNode2 = addChildNode(iIOMetadataNode1, "Intent", new Integer(this.intent));
/*     */     } 
/*     */ 
/*     */     
/* 169 */     if (this.palette != null && this.paletteSize > 0) {
/* 170 */       iIOMetadataNode2 = addChildNode(iIOMetadataNode1, "Palette", null);
/* 171 */       j = this.palette.length / this.paletteSize;
/*     */       
/* 173 */       for (byte b1 = 0, b2 = 0; b1 < this.paletteSize; b1++) {
/*     */         
/* 175 */         IIOMetadataNode iIOMetadataNode = addChildNode(iIOMetadataNode2, "PaletteEntry", null);
/* 176 */         this.red = this.palette[b2++] & 0xFF;
/* 177 */         this.green = this.palette[b2++] & 0xFF;
/* 178 */         this.blue = this.palette[b2++] & 0xFF;
/* 179 */         addChildNode(iIOMetadataNode, "Red", new Byte((byte)this.red));
/* 180 */         addChildNode(iIOMetadataNode, "Green", new Byte((byte)this.green));
/* 181 */         addChildNode(iIOMetadataNode, "Blue", new Byte((byte)this.blue));
/* 182 */         if (j == 4) {
/* 183 */           addChildNode(iIOMetadataNode, "Alpha", new Byte((byte)(this.palette[b2++] & 0xFF)));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 188 */     return iIOMetadataNode1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IIOMetadataNode getStandardChromaNode() {
/* 194 */     if (this.palette != null && this.paletteSize > 0) {
/* 195 */       IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Chroma");
/* 196 */       IIOMetadataNode iIOMetadataNode2 = new IIOMetadataNode("Palette");
/* 197 */       int i = this.palette.length / this.paletteSize;
/* 198 */       iIOMetadataNode2.setAttribute("value", "" + i);
/*     */       
/* 200 */       for (byte b1 = 0, b2 = 0; b1 < this.paletteSize; b1++) {
/* 201 */         IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("PaletteEntry");
/* 202 */         iIOMetadataNode.setAttribute("index", "" + b1);
/* 203 */         iIOMetadataNode.setAttribute("red", "" + this.palette[b2++]);
/* 204 */         iIOMetadataNode.setAttribute("green", "" + this.palette[b2++]);
/* 205 */         iIOMetadataNode.setAttribute("blue", "" + this.palette[b2++]);
/* 206 */         if (i == 4 && this.palette[b2] != 0)
/* 207 */           iIOMetadataNode.setAttribute("alpha", "" + this.palette[b2++]); 
/* 208 */         iIOMetadataNode2.appendChild(iIOMetadataNode);
/*     */       } 
/* 210 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/* 211 */       return iIOMetadataNode1;
/*     */     } 
/*     */     
/* 214 */     return null;
/*     */   }
/*     */   
/*     */   protected IIOMetadataNode getStandardCompressionNode() {
/* 218 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Compression");
/*     */ 
/*     */     
/* 221 */     IIOMetadataNode iIOMetadataNode2 = new IIOMetadataNode("CompressionTypeName");
/* 222 */     iIOMetadataNode2.setAttribute("value", BMPCompressionTypes.getName(this.compression));
/* 223 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/* 224 */     return iIOMetadataNode1;
/*     */   }
/*     */   
/*     */   protected IIOMetadataNode getStandardDataNode() {
/* 228 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Data");
/*     */     
/* 230 */     String str = "";
/* 231 */     if (this.bitsPerPixel == 24) {
/* 232 */       str = "8 8 8 ";
/* 233 */     } else if (this.bitsPerPixel == 16 || this.bitsPerPixel == 32) {
/*     */       
/* 235 */       str = "" + countBits(this.redMask) + " " + countBits(this.greenMask) + countBits(this.blueMask) + "" + countBits(this.alphaMask);
/*     */     } 
/*     */     
/* 238 */     IIOMetadataNode iIOMetadataNode2 = new IIOMetadataNode("BitsPerSample");
/* 239 */     iIOMetadataNode2.setAttribute("value", str);
/* 240 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     
/* 242 */     return iIOMetadataNode1;
/*     */   }
/*     */   
/*     */   protected IIOMetadataNode getStandardDimensionNode() {
/* 246 */     if (this.yPixelsPerMeter > 0.0F && this.xPixelsPerMeter > 0.0F) {
/* 247 */       IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Dimension");
/* 248 */       float f = (this.yPixelsPerMeter / this.xPixelsPerMeter);
/* 249 */       IIOMetadataNode iIOMetadataNode2 = new IIOMetadataNode("PixelAspectRatio");
/* 250 */       iIOMetadataNode2.setAttribute("value", "" + f);
/* 251 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */       
/* 253 */       iIOMetadataNode2 = new IIOMetadataNode("HorizontalPhysicalPixelSpacing");
/* 254 */       iIOMetadataNode2.setAttribute("value", "" + (1 / this.xPixelsPerMeter * 1000));
/* 255 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */       
/* 257 */       iIOMetadataNode2 = new IIOMetadataNode("VerticalPhysicalPixelSpacing");
/* 258 */       iIOMetadataNode2.setAttribute("value", "" + (1 / this.yPixelsPerMeter * 1000));
/* 259 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */       
/* 261 */       return iIOMetadataNode1;
/*     */     } 
/* 263 */     return null;
/*     */   }
/*     */   
/*     */   public void setFromTree(String paramString, Node paramNode) {
/* 267 */     throw new IllegalStateException(I18N.getString("BMPMetadata1"));
/*     */   }
/*     */   
/*     */   public void mergeTree(String paramString, Node paramNode) {
/* 271 */     throw new IllegalStateException(I18N.getString("BMPMetadata1"));
/*     */   }
/*     */   
/*     */   public void reset() {
/* 275 */     throw new IllegalStateException(I18N.getString("BMPMetadata1"));
/*     */   }
/*     */   
/*     */   private String countBits(int paramInt) {
/* 279 */     byte b = 0;
/* 280 */     while (paramInt > 0) {
/* 281 */       if ((paramInt & 0x1) == 1)
/* 282 */         b++; 
/* 283 */       paramInt >>>= 1;
/*     */     } 
/*     */     
/* 286 */     return (b == 0) ? "" : ("" + b);
/*     */   }
/*     */   
/*     */   private void addXYZPoints(IIOMetadataNode paramIIOMetadataNode, String paramString, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 290 */     IIOMetadataNode iIOMetadataNode = addChildNode(paramIIOMetadataNode, paramString, null);
/* 291 */     addChildNode(iIOMetadataNode, "X", new Double(paramDouble1));
/* 292 */     addChildNode(iIOMetadataNode, "Y", new Double(paramDouble2));
/* 293 */     addChildNode(iIOMetadataNode, "Z", new Double(paramDouble3));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IIOMetadataNode addChildNode(IIOMetadataNode paramIIOMetadataNode, String paramString, Object paramObject) {
/* 299 */     IIOMetadataNode iIOMetadataNode = new IIOMetadataNode(paramString);
/* 300 */     if (paramObject != null) {
/* 301 */       iIOMetadataNode.setUserObject(paramObject);
/* 302 */       iIOMetadataNode.setNodeValue(ImageUtil.convertObjectToString(paramObject));
/*     */     } 
/* 304 */     paramIIOMetadataNode.appendChild(iIOMetadataNode);
/* 305 */     return iIOMetadataNode;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/bmp/BMPMetadata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */