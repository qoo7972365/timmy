/*     */ package com.sun.imageio.plugins.gif;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ class GIFWritableImageMetadata
/*     */   extends GIFImageMetadata
/*     */ {
/*     */   static final String NATIVE_FORMAT_NAME = "javax_imageio_gif_image_1.0";
/*     */   
/*     */   GIFWritableImageMetadata() {
/*  48 */     super(true, "javax_imageio_gif_image_1.0", "com.sun.imageio.plugins.gif.GIFImageMetadataFormat", (String[])null, (String[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() {
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  60 */     this.imageLeftPosition = 0;
/*  61 */     this.imageTopPosition = 0;
/*  62 */     this.imageWidth = 0;
/*  63 */     this.imageHeight = 0;
/*  64 */     this.interlaceFlag = false;
/*  65 */     this.sortFlag = false;
/*  66 */     this.localColorTable = null;
/*     */ 
/*     */     
/*  69 */     this.disposalMethod = 0;
/*  70 */     this.userInputFlag = false;
/*  71 */     this.transparentColorFlag = false;
/*  72 */     this.delayTime = 0;
/*  73 */     this.transparentColorIndex = 0;
/*     */ 
/*     */     
/*  76 */     this.hasPlainTextExtension = false;
/*  77 */     this.textGridLeft = 0;
/*  78 */     this.textGridTop = 0;
/*  79 */     this.textGridWidth = 0;
/*  80 */     this.textGridHeight = 0;
/*  81 */     this.characterCellWidth = 0;
/*  82 */     this.characterCellHeight = 0;
/*  83 */     this.textForegroundColor = 0;
/*  84 */     this.textBackgroundColor = 0;
/*  85 */     this.text = null;
/*     */ 
/*     */     
/*  88 */     this.applicationIDs = null;
/*  89 */     this.authenticationCodes = null;
/*  90 */     this.applicationData = null;
/*     */ 
/*     */ 
/*     */     
/*  94 */     this.comments = null;
/*     */   }
/*     */   
/*     */   private byte[] fromISO8859(String paramString) {
/*     */     try {
/*  99 */       return paramString.getBytes("ISO-8859-1");
/* 100 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 101 */       return "".getBytes();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void mergeNativeTree(Node paramNode) throws IIOInvalidTreeException {
/* 106 */     Node node = paramNode;
/* 107 */     if (!node.getNodeName().equals("javax_imageio_gif_image_1.0")) {
/* 108 */       fatal(node, "Root must be javax_imageio_gif_image_1.0");
/*     */     }
/*     */     
/* 111 */     node = node.getFirstChild();
/* 112 */     while (node != null) {
/* 113 */       String str = node.getNodeName();
/*     */       
/* 115 */       if (str.equals("ImageDescriptor")) {
/* 116 */         this.imageLeftPosition = getIntAttribute(node, "imageLeftPosition", -1, true, true, 0, 65535);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 121 */         this.imageTopPosition = getIntAttribute(node, "imageTopPosition", -1, true, true, 0, 65535);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 126 */         this.imageWidth = getIntAttribute(node, "imageWidth", -1, true, true, 1, 65535);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 131 */         this.imageHeight = getIntAttribute(node, "imageHeight", -1, true, true, 1, 65535);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 136 */         this.interlaceFlag = getBooleanAttribute(node, "interlaceFlag", false, true);
/*     */       }
/* 138 */       else if (str.equals("LocalColorTable")) {
/*     */         
/* 140 */         int i = getIntAttribute(node, "sizeOfLocalColorTable", true, 2, 256);
/*     */         
/* 142 */         if (i != 2 && i != 4 && i != 8 && i != 16 && i != 32 && i != 64 && i != 128 && i != 256)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 150 */           fatal(node, "Bad value for LocalColorTable attribute sizeOfLocalColorTable!");
/*     */         }
/*     */ 
/*     */         
/* 154 */         this.sortFlag = getBooleanAttribute(node, "sortFlag", false, true);
/*     */         
/* 156 */         this.localColorTable = getColorTable(node, "ColorTableEntry", true, i);
/*     */       }
/* 158 */       else if (str.equals("GraphicControlExtension")) {
/*     */         
/* 160 */         String str1 = getStringAttribute(node, "disposalMethod", null, true, disposalMethodNames);
/*     */         
/* 162 */         this.disposalMethod = 0;
/* 163 */         while (!str1.equals(disposalMethodNames[this.disposalMethod])) {
/* 164 */           this.disposalMethod++;
/*     */         }
/*     */         
/* 167 */         this.userInputFlag = getBooleanAttribute(node, "userInputFlag", false, true);
/*     */ 
/*     */         
/* 170 */         this
/* 171 */           .transparentColorFlag = getBooleanAttribute(node, "transparentColorFlag", false, true);
/*     */ 
/*     */         
/* 174 */         this.delayTime = getIntAttribute(node, "delayTime", -1, true, true, 0, 65535);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 179 */         this
/* 180 */           .transparentColorIndex = getIntAttribute(node, "transparentColorIndex", -1, true, true, 0, 65535);
/*     */       
/*     */       }
/* 183 */       else if (str.equals("PlainTextExtension")) {
/* 184 */         this.hasPlainTextExtension = true;
/*     */         
/* 186 */         this.textGridLeft = getIntAttribute(node, "textGridLeft", -1, true, true, 0, 65535);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 191 */         this.textGridTop = getIntAttribute(node, "textGridTop", -1, true, true, 0, 65535);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 196 */         this.textGridWidth = getIntAttribute(node, "textGridWidth", -1, true, true, 1, 65535);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 201 */         this.textGridHeight = getIntAttribute(node, "textGridHeight", -1, true, true, 1, 65535);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 206 */         this.characterCellWidth = getIntAttribute(node, "characterCellWidth", -1, true, true, 1, 65535);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 211 */         this.characterCellHeight = getIntAttribute(node, "characterCellHeight", -1, true, true, 1, 65535);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 216 */         this.textForegroundColor = getIntAttribute(node, "textForegroundColor", -1, true, true, 0, 255);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 221 */         this.textBackgroundColor = getIntAttribute(node, "textBackgroundColor", -1, true, true, 0, 255);
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
/* 233 */         String str1 = getStringAttribute(node, "text", "", false, null);
/* 234 */         this.text = fromISO8859(str1);
/* 235 */       } else if (str.equals("ApplicationExtensions")) {
/*     */         
/* 237 */         IIOMetadataNode iIOMetadataNode = (IIOMetadataNode)node.getFirstChild();
/*     */         
/* 239 */         if (!iIOMetadataNode.getNodeName().equals("ApplicationExtension")) {
/* 240 */           fatal(node, "Only a ApplicationExtension may be a child of a ApplicationExtensions!");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 245 */         String str1 = getStringAttribute(iIOMetadataNode, "applicationID", null, true, null);
/*     */ 
/*     */ 
/*     */         
/* 249 */         String str2 = getStringAttribute(iIOMetadataNode, "authenticationCode", null, true, null);
/*     */ 
/*     */ 
/*     */         
/* 253 */         Object object = iIOMetadataNode.getUserObject();
/* 254 */         if (object == null || !(object instanceof byte[]))
/*     */         {
/* 256 */           fatal(iIOMetadataNode, "Bad user object in ApplicationExtension!");
/*     */         }
/*     */ 
/*     */         
/* 260 */         if (this.applicationIDs == null) {
/* 261 */           this.applicationIDs = new ArrayList();
/* 262 */           this.authenticationCodes = new ArrayList();
/* 263 */           this.applicationData = new ArrayList();
/*     */         } 
/*     */         
/* 266 */         this.applicationIDs.add(fromISO8859(str1));
/* 267 */         this.authenticationCodes.add(fromISO8859(str2));
/* 268 */         this.applicationData.add(object);
/* 269 */       } else if (str.equals("CommentExtensions")) {
/* 270 */         Node node1 = node.getFirstChild();
/* 271 */         if (node1 != null) {
/* 272 */           while (node1 != null) {
/* 273 */             if (!node1.getNodeName().equals("CommentExtension")) {
/* 274 */               fatal(node, "Only a CommentExtension may be a child of a CommentExtensions!");
/*     */             }
/*     */ 
/*     */             
/* 278 */             if (this.comments == null) {
/* 279 */               this.comments = new ArrayList();
/*     */             }
/*     */ 
/*     */             
/* 283 */             String str1 = getStringAttribute(node1, "value", null, true, null);
/*     */ 
/*     */             
/* 286 */             this.comments.add(fromISO8859(str1));
/*     */             
/* 288 */             node1 = node1.getNextSibling();
/*     */           } 
/*     */         }
/*     */       } else {
/* 292 */         fatal(node, "Unknown child of root node!");
/*     */       } 
/*     */       
/* 295 */       node = node.getNextSibling();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mergeStandardTree(Node paramNode) throws IIOInvalidTreeException {
/* 301 */     Node node = paramNode;
/*     */     
/* 303 */     if (!node.getNodeName().equals("javax_imageio_1.0")) {
/* 304 */       fatal(node, "Root must be javax_imageio_1.0");
/*     */     }
/*     */ 
/*     */     
/* 308 */     node = node.getFirstChild();
/* 309 */     while (node != null) {
/* 310 */       String str = node.getNodeName();
/*     */       
/* 312 */       if (str.equals("Chroma")) {
/* 313 */         Node node1 = node.getFirstChild();
/* 314 */         while (node1 != null) {
/* 315 */           String str1 = node1.getNodeName();
/* 316 */           if (str1.equals("Palette")) {
/* 317 */             this.localColorTable = getColorTable(node1, "PaletteEntry", false, -1);
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 322 */           node1 = node1.getNextSibling();
/*     */         } 
/* 324 */       } else if (str.equals("Compression")) {
/* 325 */         Node node1 = node.getFirstChild();
/* 326 */         while (node1 != null) {
/* 327 */           String str1 = node1.getNodeName();
/* 328 */           if (str1.equals("NumProgressiveScans")) {
/*     */             
/* 330 */             int i = getIntAttribute(node1, "value", 4, false, true, 1, 2147483647);
/*     */             
/* 332 */             if (i > 1) {
/* 333 */               this.interlaceFlag = true;
/*     */             }
/*     */             break;
/*     */           } 
/* 337 */           node1 = node1.getNextSibling();
/*     */         } 
/* 339 */       } else if (str.equals("Dimension")) {
/* 340 */         Node node1 = node.getFirstChild();
/* 341 */         while (node1 != null) {
/* 342 */           String str1 = node1.getNodeName();
/* 343 */           if (str1.equals("HorizontalPixelOffset")) {
/* 344 */             this.imageLeftPosition = getIntAttribute(node1, "value", -1, true, true, 0, 65535);
/*     */ 
/*     */           
/*     */           }
/* 348 */           else if (str1.equals("VerticalPixelOffset")) {
/* 349 */             this.imageTopPosition = getIntAttribute(node1, "value", -1, true, true, 0, 65535);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 354 */           node1 = node1.getNextSibling();
/*     */         } 
/* 356 */       } else if (str.equals("Text")) {
/* 357 */         Node node1 = node.getFirstChild();
/* 358 */         while (node1 != null) {
/* 359 */           String str1 = node1.getNodeName();
/* 360 */           if (str1.equals("TextEntry") && 
/* 361 */             getAttribute(node1, "compression", "none", false)
/* 362 */             .equals("none") && 
/* 363 */             Charset.isSupported(getAttribute(node1, "encoding", "ISO-8859-1", false))) {
/*     */ 
/*     */ 
/*     */             
/* 367 */             String str2 = getAttribute(node1, "value");
/* 368 */             byte[] arrayOfByte = fromISO8859(str2);
/* 369 */             if (this.comments == null) {
/* 370 */               this.comments = new ArrayList();
/*     */             }
/* 372 */             this.comments.add(arrayOfByte);
/*     */           } 
/* 374 */           node1 = node1.getNextSibling();
/*     */         } 
/* 376 */       } else if (str.equals("Transparency")) {
/* 377 */         Node node1 = node.getFirstChild();
/* 378 */         while (node1 != null) {
/* 379 */           String str1 = node1.getNodeName();
/* 380 */           if (str1.equals("TransparentIndex")) {
/* 381 */             this.transparentColorIndex = getIntAttribute(node1, "value", -1, true, true, 0, 255);
/*     */ 
/*     */ 
/*     */             
/* 385 */             this.transparentColorFlag = true;
/*     */             break;
/*     */           } 
/* 388 */           node1 = node1.getNextSibling();
/*     */         } 
/*     */       } 
/*     */       
/* 392 */       node = node.getNextSibling();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFromTree(String paramString, Node paramNode) throws IIOInvalidTreeException {
/* 399 */     reset();
/* 400 */     mergeTree(paramString, paramNode);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/gif/GIFWritableImageMetadata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */