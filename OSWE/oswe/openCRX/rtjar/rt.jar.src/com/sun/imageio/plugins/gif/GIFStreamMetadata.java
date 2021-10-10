/*     */ package com.sun.imageio.plugins.gif;
/*     */ 
/*     */ import javax.imageio.metadata.IIOInvalidTreeException;
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
/*     */ public class GIFStreamMetadata
/*     */   extends GIFMetadata
/*     */ {
/*     */   static final String nativeMetadataFormatName = "javax_imageio_gif_stream_1.0";
/*  41 */   static final String[] versionStrings = new String[] { "87a", "89a" };
/*     */   
/*     */   public String version;
/*     */   
/*     */   public int logicalScreenWidth;
/*     */   
/*     */   public int logicalScreenHeight;
/*     */   public int colorResolution;
/*     */   public int pixelAspectRatio;
/*     */   public int backgroundColorIndex;
/*     */   public boolean sortFlag;
/*  52 */   static final String[] colorTableSizes = new String[] { "2", "4", "8", "16", "32", "64", "128", "256" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   public byte[] globalColorTable = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GIFStreamMetadata(boolean paramBoolean, String paramString1, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2) {
/*  65 */     super(paramBoolean, paramString1, paramString2, paramArrayOfString1, paramArrayOfString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GIFStreamMetadata() {
/*  73 */     this(true, "javax_imageio_gif_stream_1.0", "com.sun.imageio.plugins.gif.GIFStreamMetadataFormat", null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() {
/*  81 */     return true;
/*     */   }
/*     */   
/*     */   public Node getAsTree(String paramString) {
/*  85 */     if (paramString.equals("javax_imageio_gif_stream_1.0"))
/*  86 */       return getNativeTree(); 
/*  87 */     if (paramString
/*  88 */       .equals("javax_imageio_1.0")) {
/*  89 */       return getStandardTree();
/*     */     }
/*  91 */     throw new IllegalArgumentException("Not a recognized format!");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Node getNativeTree() {
/*  97 */     IIOMetadataNode iIOMetadataNode2 = new IIOMetadataNode("javax_imageio_gif_stream_1.0");
/*     */ 
/*     */     
/* 100 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Version");
/* 101 */     iIOMetadataNode1.setAttribute("value", this.version);
/* 102 */     iIOMetadataNode2.appendChild(iIOMetadataNode1);
/*     */ 
/*     */     
/* 105 */     iIOMetadataNode1 = new IIOMetadataNode("LogicalScreenDescriptor");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     iIOMetadataNode1.setAttribute("logicalScreenWidth", (this.logicalScreenWidth == -1) ? "" : 
/*     */         
/* 112 */         Integer.toString(this.logicalScreenWidth));
/* 113 */     iIOMetadataNode1.setAttribute("logicalScreenHeight", (this.logicalScreenHeight == -1) ? "" : 
/*     */         
/* 115 */         Integer.toString(this.logicalScreenHeight));
/*     */     
/* 117 */     iIOMetadataNode1.setAttribute("colorResolution", (this.colorResolution == -1) ? "" : 
/*     */         
/* 119 */         Integer.toString(this.colorResolution));
/* 120 */     iIOMetadataNode1.setAttribute("pixelAspectRatio", 
/* 121 */         Integer.toString(this.pixelAspectRatio));
/* 122 */     iIOMetadataNode2.appendChild(iIOMetadataNode1);
/*     */     
/* 124 */     if (this.globalColorTable != null) {
/* 125 */       iIOMetadataNode1 = new IIOMetadataNode("GlobalColorTable");
/* 126 */       int i = this.globalColorTable.length / 3;
/* 127 */       iIOMetadataNode1.setAttribute("sizeOfGlobalColorTable", 
/* 128 */           Integer.toString(i));
/* 129 */       iIOMetadataNode1.setAttribute("backgroundColorIndex", 
/* 130 */           Integer.toString(this.backgroundColorIndex));
/* 131 */       iIOMetadataNode1.setAttribute("sortFlag", this.sortFlag ? "TRUE" : "FALSE");
/*     */ 
/*     */       
/* 134 */       for (byte b = 0; b < i; b++) {
/* 135 */         IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("ColorTableEntry");
/*     */         
/* 137 */         iIOMetadataNode.setAttribute("index", Integer.toString(b));
/* 138 */         int j = this.globalColorTable[3 * b] & 0xFF;
/* 139 */         int k = this.globalColorTable[3 * b + 1] & 0xFF;
/* 140 */         int m = this.globalColorTable[3 * b + 2] & 0xFF;
/* 141 */         iIOMetadataNode.setAttribute("red", Integer.toString(j));
/* 142 */         iIOMetadataNode.setAttribute("green", Integer.toString(k));
/* 143 */         iIOMetadataNode.setAttribute("blue", Integer.toString(m));
/* 144 */         iIOMetadataNode1.appendChild(iIOMetadataNode);
/*     */       } 
/* 146 */       iIOMetadataNode2.appendChild(iIOMetadataNode1);
/*     */     } 
/*     */     
/* 149 */     return iIOMetadataNode2;
/*     */   }
/*     */   
/*     */   public IIOMetadataNode getStandardChromaNode() {
/* 153 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Chroma");
/* 154 */     IIOMetadataNode iIOMetadataNode2 = null;
/*     */     
/* 156 */     iIOMetadataNode2 = new IIOMetadataNode("ColorSpaceType");
/* 157 */     iIOMetadataNode2.setAttribute("name", "RGB");
/* 158 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     
/* 160 */     iIOMetadataNode2 = new IIOMetadataNode("BlackIsZero");
/* 161 */     iIOMetadataNode2.setAttribute("value", "TRUE");
/* 162 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     if (this.globalColorTable != null) {
/* 168 */       iIOMetadataNode2 = new IIOMetadataNode("Palette");
/* 169 */       int i = this.globalColorTable.length / 3;
/* 170 */       for (byte b = 0; b < i; b++) {
/* 171 */         IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("PaletteEntry");
/*     */         
/* 173 */         iIOMetadataNode.setAttribute("index", Integer.toString(b));
/* 174 */         iIOMetadataNode.setAttribute("red", 
/* 175 */             Integer.toString(this.globalColorTable[3 * b] & 0xFF));
/* 176 */         iIOMetadataNode.setAttribute("green", 
/* 177 */             Integer.toString(this.globalColorTable[3 * b + 1] & 0xFF));
/* 178 */         iIOMetadataNode.setAttribute("blue", 
/* 179 */             Integer.toString(this.globalColorTable[3 * b + 2] & 0xFF));
/* 180 */         iIOMetadataNode2.appendChild(iIOMetadataNode);
/*     */       } 
/* 182 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */ 
/*     */       
/* 185 */       iIOMetadataNode2 = new IIOMetadataNode("BackgroundIndex");
/* 186 */       iIOMetadataNode2.setAttribute("value", Integer.toString(this.backgroundColorIndex));
/* 187 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     } 
/*     */     
/* 190 */     return iIOMetadataNode1;
/*     */   }
/*     */   
/*     */   public IIOMetadataNode getStandardCompressionNode() {
/* 194 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Compression");
/* 195 */     IIOMetadataNode iIOMetadataNode2 = null;
/*     */     
/* 197 */     iIOMetadataNode2 = new IIOMetadataNode("CompressionTypeName");
/* 198 */     iIOMetadataNode2.setAttribute("value", "lzw");
/* 199 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     
/* 201 */     iIOMetadataNode2 = new IIOMetadataNode("Lossless");
/* 202 */     iIOMetadataNode2.setAttribute("value", "TRUE");
/* 203 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     return iIOMetadataNode1;
/*     */   }
/*     */   
/*     */   public IIOMetadataNode getStandardDataNode() {
/* 212 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Data");
/* 213 */     IIOMetadataNode iIOMetadataNode2 = null;
/*     */ 
/*     */ 
/*     */     
/* 217 */     iIOMetadataNode2 = new IIOMetadataNode("SampleFormat");
/* 218 */     iIOMetadataNode2.setAttribute("value", "Index");
/* 219 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     
/* 221 */     iIOMetadataNode2 = new IIOMetadataNode("BitsPerSample");
/* 222 */     iIOMetadataNode2.setAttribute("value", (this.colorResolution == -1) ? "" : 
/*     */         
/* 224 */         Integer.toString(this.colorResolution));
/* 225 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     return iIOMetadataNode1;
/*     */   }
/*     */   
/*     */   public IIOMetadataNode getStandardDimensionNode() {
/* 234 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Dimension");
/* 235 */     IIOMetadataNode iIOMetadataNode2 = null;
/*     */     
/* 237 */     iIOMetadataNode2 = new IIOMetadataNode("PixelAspectRatio");
/* 238 */     float f = 1.0F;
/* 239 */     if (this.pixelAspectRatio != 0) {
/* 240 */       f = (this.pixelAspectRatio + 15) / 64.0F;
/*     */     }
/* 242 */     iIOMetadataNode2.setAttribute("value", Float.toString(f));
/* 243 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     
/* 245 */     iIOMetadataNode2 = new IIOMetadataNode("ImageOrientation");
/* 246 */     iIOMetadataNode2.setAttribute("value", "Normal");
/* 247 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
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
/* 258 */     iIOMetadataNode2 = new IIOMetadataNode("HorizontalScreenSize");
/* 259 */     iIOMetadataNode2.setAttribute("value", (this.logicalScreenWidth == -1) ? "" : 
/*     */         
/* 261 */         Integer.toString(this.logicalScreenWidth));
/* 262 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     
/* 264 */     iIOMetadataNode2 = new IIOMetadataNode("VerticalScreenSize");
/* 265 */     iIOMetadataNode2.setAttribute("value", (this.logicalScreenHeight == -1) ? "" : 
/*     */         
/* 267 */         Integer.toString(this.logicalScreenHeight));
/* 268 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     
/* 270 */     return iIOMetadataNode1;
/*     */   }
/*     */   
/*     */   public IIOMetadataNode getStandardDocumentNode() {
/* 274 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Document");
/* 275 */     IIOMetadataNode iIOMetadataNode2 = null;
/*     */     
/* 277 */     iIOMetadataNode2 = new IIOMetadataNode("FormatVersion");
/* 278 */     iIOMetadataNode2.setAttribute("value", this.version);
/* 279 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 285 */     return iIOMetadataNode1;
/*     */   }
/*     */ 
/*     */   
/*     */   public IIOMetadataNode getStandardTextNode() {
/* 290 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public IIOMetadataNode getStandardTransparencyNode() {
/* 295 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFromTree(String paramString, Node paramNode) throws IIOInvalidTreeException {
/* 301 */     throw new IllegalStateException("Metadata is read-only!");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mergeNativeTree(Node paramNode) throws IIOInvalidTreeException {
/* 306 */     throw new IllegalStateException("Metadata is read-only!");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mergeStandardTree(Node paramNode) throws IIOInvalidTreeException {
/* 311 */     throw new IllegalStateException("Metadata is read-only!");
/*     */   }
/*     */   
/*     */   public void reset() {
/* 315 */     throw new IllegalStateException("Metadata is read-only!");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/gif/GIFStreamMetadata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */