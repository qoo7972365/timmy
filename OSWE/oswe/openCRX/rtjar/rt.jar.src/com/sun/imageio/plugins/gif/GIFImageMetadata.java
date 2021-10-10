/*     */ package com.sun.imageio.plugins.gif;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class GIFImageMetadata
/*     */   extends GIFMetadata
/*     */ {
/*     */   static final String nativeMetadataFormatName = "javax_imageio_gif_image_1.0";
/*  46 */   static final String[] disposalMethodNames = new String[] { "none", "doNotDispose", "restoreToBackgroundColor", "restoreToPrevious", "undefinedDisposalMethod4", "undefinedDisposalMethod5", "undefinedDisposalMethod6", "undefinedDisposalMethod7" };
/*     */ 
/*     */   
/*     */   public int imageLeftPosition;
/*     */ 
/*     */   
/*     */   public int imageTopPosition;
/*     */ 
/*     */   
/*     */   public int imageWidth;
/*     */ 
/*     */   
/*     */   public int imageHeight;
/*     */   
/*     */   public boolean interlaceFlag = false;
/*     */   
/*     */   public boolean sortFlag = false;
/*     */   
/*  64 */   public byte[] localColorTable = null;
/*     */ 
/*     */   
/*  67 */   public int disposalMethod = 0;
/*     */   public boolean userInputFlag = false;
/*     */   public boolean transparentColorFlag = false;
/*  70 */   public int delayTime = 0;
/*  71 */   public int transparentColorIndex = 0;
/*     */   
/*     */   public boolean hasPlainTextExtension = false;
/*     */   
/*     */   public int textGridLeft;
/*     */   
/*     */   public int textGridTop;
/*     */   
/*     */   public int textGridWidth;
/*     */   
/*     */   public int textGridHeight;
/*     */   public int characterCellWidth;
/*     */   public int characterCellHeight;
/*     */   public int textForegroundColor;
/*     */   public int textBackgroundColor;
/*     */   public byte[] text;
/*  87 */   public List applicationIDs = null;
/*     */ 
/*     */   
/*  90 */   public List authenticationCodes = null;
/*     */ 
/*     */   
/*  93 */   public List applicationData = null;
/*     */ 
/*     */ 
/*     */   
/*  97 */   public List comments = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GIFImageMetadata(boolean paramBoolean, String paramString1, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2) {
/* 105 */     super(paramBoolean, paramString1, paramString2, paramArrayOfString1, paramArrayOfString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GIFImageMetadata() {
/* 113 */     this(true, "javax_imageio_gif_image_1.0", "com.sun.imageio.plugins.gif.GIFImageMetadataFormat", null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() {
/* 120 */     return true;
/*     */   }
/*     */   
/*     */   public Node getAsTree(String paramString) {
/* 124 */     if (paramString.equals("javax_imageio_gif_image_1.0"))
/* 125 */       return getNativeTree(); 
/* 126 */     if (paramString
/* 127 */       .equals("javax_imageio_1.0")) {
/* 128 */       return getStandardTree();
/*     */     }
/* 130 */     throw new IllegalArgumentException("Not a recognized format!");
/*     */   }
/*     */ 
/*     */   
/*     */   private String toISO8859(byte[] paramArrayOfbyte) {
/*     */     try {
/* 136 */       return new String(paramArrayOfbyte, "ISO-8859-1");
/* 137 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 138 */       return "";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Node getNativeTree() {
/* 144 */     IIOMetadataNode iIOMetadataNode2 = new IIOMetadataNode("javax_imageio_gif_image_1.0");
/*     */ 
/*     */ 
/*     */     
/* 148 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("ImageDescriptor");
/* 149 */     iIOMetadataNode1.setAttribute("imageLeftPosition", 
/* 150 */         Integer.toString(this.imageLeftPosition));
/* 151 */     iIOMetadataNode1.setAttribute("imageTopPosition", 
/* 152 */         Integer.toString(this.imageTopPosition));
/* 153 */     iIOMetadataNode1.setAttribute("imageWidth", Integer.toString(this.imageWidth));
/* 154 */     iIOMetadataNode1.setAttribute("imageHeight", Integer.toString(this.imageHeight));
/* 155 */     iIOMetadataNode1.setAttribute("interlaceFlag", this.interlaceFlag ? "TRUE" : "FALSE");
/*     */     
/* 157 */     iIOMetadataNode2.appendChild(iIOMetadataNode1);
/*     */ 
/*     */     
/* 160 */     if (this.localColorTable != null) {
/* 161 */       iIOMetadataNode1 = new IIOMetadataNode("LocalColorTable");
/* 162 */       int i = this.localColorTable.length / 3;
/* 163 */       iIOMetadataNode1.setAttribute("sizeOfLocalColorTable", 
/* 164 */           Integer.toString(i));
/* 165 */       iIOMetadataNode1.setAttribute("sortFlag", this.sortFlag ? "TRUE" : "FALSE");
/*     */ 
/*     */       
/* 168 */       for (byte b = 0; b < i; b++) {
/* 169 */         IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("ColorTableEntry");
/*     */         
/* 171 */         iIOMetadataNode.setAttribute("index", Integer.toString(b));
/* 172 */         int j = this.localColorTable[3 * b] & 0xFF;
/* 173 */         int k = this.localColorTable[3 * b + 1] & 0xFF;
/* 174 */         int m = this.localColorTable[3 * b + 2] & 0xFF;
/* 175 */         iIOMetadataNode.setAttribute("red", Integer.toString(j));
/* 176 */         iIOMetadataNode.setAttribute("green", Integer.toString(k));
/* 177 */         iIOMetadataNode.setAttribute("blue", Integer.toString(m));
/* 178 */         iIOMetadataNode1.appendChild(iIOMetadataNode);
/*     */       } 
/* 180 */       iIOMetadataNode2.appendChild(iIOMetadataNode1);
/*     */     } 
/*     */ 
/*     */     
/* 184 */     iIOMetadataNode1 = new IIOMetadataNode("GraphicControlExtension");
/* 185 */     iIOMetadataNode1.setAttribute("disposalMethod", disposalMethodNames[this.disposalMethod]);
/*     */     
/* 187 */     iIOMetadataNode1.setAttribute("userInputFlag", this.userInputFlag ? "TRUE" : "FALSE");
/*     */     
/* 189 */     iIOMetadataNode1.setAttribute("transparentColorFlag", this.transparentColorFlag ? "TRUE" : "FALSE");
/*     */     
/* 191 */     iIOMetadataNode1.setAttribute("delayTime", 
/* 192 */         Integer.toString(this.delayTime));
/* 193 */     iIOMetadataNode1.setAttribute("transparentColorIndex", 
/* 194 */         Integer.toString(this.transparentColorIndex));
/* 195 */     iIOMetadataNode2.appendChild(iIOMetadataNode1);
/*     */     
/* 197 */     if (this.hasPlainTextExtension) {
/* 198 */       iIOMetadataNode1 = new IIOMetadataNode("PlainTextExtension");
/* 199 */       iIOMetadataNode1.setAttribute("textGridLeft", 
/* 200 */           Integer.toString(this.textGridLeft));
/* 201 */       iIOMetadataNode1.setAttribute("textGridTop", 
/* 202 */           Integer.toString(this.textGridTop));
/* 203 */       iIOMetadataNode1.setAttribute("textGridWidth", 
/* 204 */           Integer.toString(this.textGridWidth));
/* 205 */       iIOMetadataNode1.setAttribute("textGridHeight", 
/* 206 */           Integer.toString(this.textGridHeight));
/* 207 */       iIOMetadataNode1.setAttribute("characterCellWidth", 
/* 208 */           Integer.toString(this.characterCellWidth));
/* 209 */       iIOMetadataNode1.setAttribute("characterCellHeight", 
/* 210 */           Integer.toString(this.characterCellHeight));
/* 211 */       iIOMetadataNode1.setAttribute("textForegroundColor", 
/* 212 */           Integer.toString(this.textForegroundColor));
/* 213 */       iIOMetadataNode1.setAttribute("textBackgroundColor", 
/* 214 */           Integer.toString(this.textBackgroundColor));
/* 215 */       iIOMetadataNode1.setAttribute("text", toISO8859(this.text));
/*     */       
/* 217 */       iIOMetadataNode2.appendChild(iIOMetadataNode1);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 222 */     byte b1 = (this.applicationIDs == null) ? 0 : this.applicationIDs.size();
/* 223 */     if (b1) {
/* 224 */       iIOMetadataNode1 = new IIOMetadataNode("ApplicationExtensions");
/* 225 */       for (byte b = 0; b < b1; b++) {
/* 226 */         IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("ApplicationExtension");
/*     */         
/* 228 */         byte[] arrayOfByte1 = this.applicationIDs.get(b);
/* 229 */         iIOMetadataNode.setAttribute("applicationID", 
/* 230 */             toISO8859(arrayOfByte1));
/* 231 */         byte[] arrayOfByte2 = this.authenticationCodes.get(b);
/* 232 */         iIOMetadataNode.setAttribute("authenticationCode", 
/* 233 */             toISO8859(arrayOfByte2));
/* 234 */         byte[] arrayOfByte3 = this.applicationData.get(b);
/* 235 */         iIOMetadataNode.setUserObject(arrayOfByte3.clone());
/* 236 */         iIOMetadataNode1.appendChild(iIOMetadataNode);
/*     */       } 
/*     */       
/* 239 */       iIOMetadataNode2.appendChild(iIOMetadataNode1);
/*     */     } 
/*     */ 
/*     */     
/* 243 */     byte b2 = (this.comments == null) ? 0 : this.comments.size();
/* 244 */     if (b2) {
/* 245 */       iIOMetadataNode1 = new IIOMetadataNode("CommentExtensions");
/* 246 */       for (byte b = 0; b < b2; b++) {
/* 247 */         IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("CommentExtension");
/*     */         
/* 249 */         byte[] arrayOfByte = this.comments.get(b);
/* 250 */         iIOMetadataNode.setAttribute("value", toISO8859(arrayOfByte));
/* 251 */         iIOMetadataNode1.appendChild(iIOMetadataNode);
/*     */       } 
/*     */       
/* 254 */       iIOMetadataNode2.appendChild(iIOMetadataNode1);
/*     */     } 
/*     */     
/* 257 */     return iIOMetadataNode2;
/*     */   }
/*     */   
/*     */   public IIOMetadataNode getStandardChromaNode() {
/* 261 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Chroma");
/* 262 */     IIOMetadataNode iIOMetadataNode2 = null;
/*     */     
/* 264 */     iIOMetadataNode2 = new IIOMetadataNode("ColorSpaceType");
/* 265 */     iIOMetadataNode2.setAttribute("name", "RGB");
/* 266 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     
/* 268 */     iIOMetadataNode2 = new IIOMetadataNode("NumChannels");
/* 269 */     iIOMetadataNode2.setAttribute("value", this.transparentColorFlag ? "4" : "3");
/* 270 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */ 
/*     */ 
/*     */     
/* 274 */     iIOMetadataNode2 = new IIOMetadataNode("BlackIsZero");
/* 275 */     iIOMetadataNode2.setAttribute("value", "TRUE");
/* 276 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     
/* 278 */     if (this.localColorTable != null) {
/* 279 */       iIOMetadataNode2 = new IIOMetadataNode("Palette");
/* 280 */       int i = this.localColorTable.length / 3;
/* 281 */       for (byte b = 0; b < i; b++) {
/* 282 */         IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("PaletteEntry");
/*     */         
/* 284 */         iIOMetadataNode.setAttribute("index", Integer.toString(b));
/* 285 */         iIOMetadataNode.setAttribute("red", 
/* 286 */             Integer.toString(this.localColorTable[3 * b] & 0xFF));
/* 287 */         iIOMetadataNode.setAttribute("green", 
/* 288 */             Integer.toString(this.localColorTable[3 * b + 1] & 0xFF));
/* 289 */         iIOMetadataNode.setAttribute("blue", 
/* 290 */             Integer.toString(this.localColorTable[3 * b + 2] & 0xFF));
/* 291 */         iIOMetadataNode2.appendChild(iIOMetadataNode);
/*     */       } 
/* 293 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     return iIOMetadataNode1;
/*     */   }
/*     */   
/*     */   public IIOMetadataNode getStandardCompressionNode() {
/* 303 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Compression");
/* 304 */     IIOMetadataNode iIOMetadataNode2 = null;
/*     */     
/* 306 */     iIOMetadataNode2 = new IIOMetadataNode("CompressionTypeName");
/* 307 */     iIOMetadataNode2.setAttribute("value", "lzw");
/* 308 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     
/* 310 */     iIOMetadataNode2 = new IIOMetadataNode("Lossless");
/* 311 */     iIOMetadataNode2.setAttribute("value", "TRUE");
/* 312 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     
/* 314 */     iIOMetadataNode2 = new IIOMetadataNode("NumProgressiveScans");
/* 315 */     iIOMetadataNode2.setAttribute("value", this.interlaceFlag ? "4" : "1");
/* 316 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */ 
/*     */ 
/*     */     
/* 320 */     return iIOMetadataNode1;
/*     */   }
/*     */   
/*     */   public IIOMetadataNode getStandardDataNode() {
/* 324 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Data");
/* 325 */     IIOMetadataNode iIOMetadataNode2 = null;
/*     */ 
/*     */ 
/*     */     
/* 329 */     iIOMetadataNode2 = new IIOMetadataNode("SampleFormat");
/* 330 */     iIOMetadataNode2.setAttribute("value", "Index");
/* 331 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 337 */     return iIOMetadataNode1;
/*     */   }
/*     */   
/*     */   public IIOMetadataNode getStandardDimensionNode() {
/* 341 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Dimension");
/* 342 */     IIOMetadataNode iIOMetadataNode2 = null;
/*     */ 
/*     */ 
/*     */     
/* 346 */     iIOMetadataNode2 = new IIOMetadataNode("ImageOrientation");
/* 347 */     iIOMetadataNode2.setAttribute("value", "Normal");
/* 348 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 357 */     iIOMetadataNode2 = new IIOMetadataNode("HorizontalPixelOffset");
/* 358 */     iIOMetadataNode2.setAttribute("value", Integer.toString(this.imageLeftPosition));
/* 359 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     
/* 361 */     iIOMetadataNode2 = new IIOMetadataNode("VerticalPixelOffset");
/* 362 */     iIOMetadataNode2.setAttribute("value", Integer.toString(this.imageTopPosition));
/* 363 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 368 */     return iIOMetadataNode1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIOMetadataNode getStandardTextNode() {
/* 374 */     if (this.comments == null) {
/* 375 */       return null;
/*     */     }
/* 377 */     Iterator<byte[]> iterator = this.comments.iterator();
/* 378 */     if (!iterator.hasNext()) {
/* 379 */       return null;
/*     */     }
/*     */     
/* 382 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Text");
/* 383 */     IIOMetadataNode iIOMetadataNode2 = null;
/*     */     
/* 385 */     while (iterator.hasNext()) {
/* 386 */       byte[] arrayOfByte = iterator.next();
/* 387 */       String str = null;
/*     */       try {
/* 389 */         str = new String(arrayOfByte, "ISO-8859-1");
/* 390 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 391 */         throw new RuntimeException("Encoding ISO-8859-1 unknown!");
/*     */       } 
/*     */       
/* 394 */       iIOMetadataNode2 = new IIOMetadataNode("TextEntry");
/* 395 */       iIOMetadataNode2.setAttribute("value", str);
/* 396 */       iIOMetadataNode2.setAttribute("encoding", "ISO-8859-1");
/* 397 */       iIOMetadataNode2.setAttribute("compression", "none");
/* 398 */       iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     } 
/*     */     
/* 401 */     return iIOMetadataNode1;
/*     */   }
/*     */   
/*     */   public IIOMetadataNode getStandardTransparencyNode() {
/* 405 */     if (!this.transparentColorFlag) {
/* 406 */       return null;
/*     */     }
/*     */     
/* 409 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Transparency");
/*     */     
/* 411 */     IIOMetadataNode iIOMetadataNode2 = null;
/*     */ 
/*     */ 
/*     */     
/* 415 */     iIOMetadataNode2 = new IIOMetadataNode("TransparentIndex");
/* 416 */     iIOMetadataNode2.setAttribute("value", 
/* 417 */         Integer.toString(this.transparentColorIndex));
/* 418 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 424 */     return iIOMetadataNode1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFromTree(String paramString, Node paramNode) throws IIOInvalidTreeException {
/* 430 */     throw new IllegalStateException("Metadata is read-only!");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mergeNativeTree(Node paramNode) throws IIOInvalidTreeException {
/* 435 */     throw new IllegalStateException("Metadata is read-only!");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mergeStandardTree(Node paramNode) throws IIOInvalidTreeException {
/* 440 */     throw new IllegalStateException("Metadata is read-only!");
/*     */   }
/*     */   
/*     */   public void reset() {
/* 444 */     throw new IllegalStateException("Metadata is read-only!");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/gif/GIFImageMetadata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */