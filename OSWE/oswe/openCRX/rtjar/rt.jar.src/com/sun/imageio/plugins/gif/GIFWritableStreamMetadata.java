/*     */ package com.sun.imageio.plugins.gif;
/*     */ 
/*     */ import javax.imageio.metadata.IIOInvalidTreeException;
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
/*     */ class GIFWritableStreamMetadata
/*     */   extends GIFStreamMetadata
/*     */ {
/*     */   static final String NATIVE_FORMAT_NAME = "javax_imageio_gif_stream_1.0";
/*     */   
/*     */   public GIFWritableStreamMetadata() {
/*  49 */     super(true, "javax_imageio_gif_stream_1.0", "com.sun.imageio.plugins.gif.GIFStreamMetadataFormat", (String[])null, (String[])null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     reset();
/*     */   }
/*     */   
/*     */   public boolean isReadOnly() {
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void mergeTree(String paramString, Node paramNode) throws IIOInvalidTreeException {
/*  64 */     if (paramString.equals("javax_imageio_gif_stream_1.0")) {
/*  65 */       if (paramNode == null) {
/*  66 */         throw new IllegalArgumentException("root == null!");
/*     */       }
/*  68 */       mergeNativeTree(paramNode);
/*  69 */     } else if (paramString
/*  70 */       .equals("javax_imageio_1.0")) {
/*  71 */       if (paramNode == null) {
/*  72 */         throw new IllegalArgumentException("root == null!");
/*     */       }
/*  74 */       mergeStandardTree(paramNode);
/*     */     } else {
/*  76 */       throw new IllegalArgumentException("Not a recognized format!");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset() {
/*  81 */     this.version = null;
/*     */     
/*  83 */     this.logicalScreenWidth = -1;
/*  84 */     this.logicalScreenHeight = -1;
/*  85 */     this.colorResolution = -1;
/*  86 */     this.pixelAspectRatio = 0;
/*     */     
/*  88 */     this.backgroundColorIndex = 0;
/*  89 */     this.sortFlag = false;
/*  90 */     this.globalColorTable = null;
/*     */   }
/*     */   
/*     */   protected void mergeNativeTree(Node paramNode) throws IIOInvalidTreeException {
/*  94 */     Node node = paramNode;
/*  95 */     if (!node.getNodeName().equals("javax_imageio_gif_stream_1.0")) {
/*  96 */       fatal(node, "Root must be javax_imageio_gif_stream_1.0");
/*     */     }
/*     */     
/*  99 */     node = node.getFirstChild();
/* 100 */     while (node != null) {
/* 101 */       String str = node.getNodeName();
/*     */       
/* 103 */       if (str.equals("Version")) {
/* 104 */         this.version = getStringAttribute(node, "value", null, true, versionStrings);
/*     */       }
/* 106 */       else if (str.equals("LogicalScreenDescriptor")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 112 */         this.logicalScreenWidth = getIntAttribute(node, "logicalScreenWidth", -1, true, true, 1, 65535);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 118 */         this.logicalScreenHeight = getIntAttribute(node, "logicalScreenHeight", -1, true, true, 1, 65535);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 124 */         this.colorResolution = getIntAttribute(node, "colorResolution", -1, true, true, 1, 8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 130 */         this.pixelAspectRatio = getIntAttribute(node, "pixelAspectRatio", 0, true, true, 0, 255);
/*     */ 
/*     */       
/*     */       }
/* 134 */       else if (str.equals("GlobalColorTable")) {
/*     */         
/* 136 */         int i = getIntAttribute(node, "sizeOfGlobalColorTable", true, 2, 256);
/*     */         
/* 138 */         if (i != 2 && i != 4 && i != 8 && i != 16 && i != 32 && i != 64 && i != 128 && i != 256)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 146 */           fatal(node, "Bad value for GlobalColorTable attribute sizeOfGlobalColorTable!");
/*     */         }
/*     */ 
/*     */         
/* 150 */         this.backgroundColorIndex = getIntAttribute(node, "backgroundColorIndex", 0, true, true, 0, 255);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 155 */         this.sortFlag = getBooleanAttribute(node, "sortFlag", false, true);
/*     */         
/* 157 */         this.globalColorTable = getColorTable(node, "ColorTableEntry", true, i);
/*     */       } else {
/*     */         
/* 160 */         fatal(node, "Unknown child of root node!");
/*     */       } 
/*     */       
/* 163 */       node = node.getNextSibling();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mergeStandardTree(Node paramNode) throws IIOInvalidTreeException {
/* 169 */     Node node = paramNode;
/*     */     
/* 171 */     if (!node.getNodeName().equals("javax_imageio_1.0")) {
/* 172 */       fatal(node, "Root must be javax_imageio_1.0");
/*     */     }
/*     */ 
/*     */     
/* 176 */     node = node.getFirstChild();
/* 177 */     while (node != null) {
/* 178 */       String str = node.getNodeName();
/*     */       
/* 180 */       if (str.equals("Chroma")) {
/* 181 */         Node node1 = node.getFirstChild();
/* 182 */         while (node1 != null) {
/* 183 */           String str1 = node1.getNodeName();
/* 184 */           if (str1.equals("Palette")) {
/* 185 */             this.globalColorTable = getColorTable(node1, "PaletteEntry", false, -1);
/*     */ 
/*     */           
/*     */           }
/* 189 */           else if (str1.equals("BackgroundIndex")) {
/* 190 */             this.backgroundColorIndex = getIntAttribute(node1, "value", -1, true, true, 0, 255);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 195 */           node1 = node1.getNextSibling();
/*     */         } 
/* 197 */       } else if (str.equals("Data")) {
/* 198 */         Node node1 = node.getFirstChild();
/* 199 */         while (node1 != null) {
/* 200 */           String str1 = node1.getNodeName();
/* 201 */           if (str1.equals("BitsPerSample")) {
/* 202 */             this.colorResolution = getIntAttribute(node1, "value", -1, true, true, 1, 8);
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 208 */           node1 = node1.getNextSibling();
/*     */         } 
/* 210 */       } else if (str.equals("Dimension")) {
/* 211 */         Node node1 = node.getFirstChild();
/* 212 */         while (node1 != null) {
/* 213 */           String str1 = node1.getNodeName();
/* 214 */           if (str1.equals("PixelAspectRatio")) {
/* 215 */             float f = getFloatAttribute(node1, "value");
/*     */             
/* 217 */             if (f == 1.0F) {
/* 218 */               this.pixelAspectRatio = 0;
/*     */             } else {
/* 220 */               int i = (int)(f * 64.0F - 15.0F);
/* 221 */               this
/* 222 */                 .pixelAspectRatio = Math.max(Math.min(i, 255), 0);
/*     */             } 
/* 224 */           } else if (str1.equals("HorizontalScreenSize")) {
/* 225 */             this.logicalScreenWidth = getIntAttribute(node1, "value", -1, true, true, 1, 65535);
/*     */ 
/*     */           
/*     */           }
/* 229 */           else if (str1.equals("VerticalScreenSize")) {
/* 230 */             this.logicalScreenHeight = getIntAttribute(node1, "value", -1, true, true, 1, 65535);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 235 */           node1 = node1.getNextSibling();
/*     */         } 
/* 237 */       } else if (str.equals("Document")) {
/* 238 */         Node node1 = node.getFirstChild();
/* 239 */         while (node1 != null) {
/* 240 */           String str1 = node1.getNodeName();
/* 241 */           if (str1.equals("FormatVersion")) {
/*     */             
/* 243 */             String str2 = getStringAttribute(node1, "value", null, true, null);
/*     */             
/* 245 */             for (byte b = 0; b < versionStrings.length; b++) {
/* 246 */               if (str2.equals(versionStrings[b])) {
/* 247 */                 this.version = str2;
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */             break;
/*     */           } 
/* 253 */           node1 = node1.getNextSibling();
/*     */         } 
/*     */       } 
/*     */       
/* 257 */       node = node.getNextSibling();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFromTree(String paramString, Node paramNode) throws IIOInvalidTreeException {
/* 264 */     reset();
/* 265 */     mergeTree(paramString, paramNode);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/gif/GIFWritableStreamMetadata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */