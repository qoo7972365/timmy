/*     */ package com.sun.imageio.plugins.jpeg;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.imageio.IIOException;
/*     */ import javax.imageio.metadata.IIOInvalidTreeException;
/*     */ import javax.imageio.metadata.IIOMetadataNode;
/*     */ import javax.imageio.stream.ImageOutputStream;
/*     */ import org.w3c.dom.NamedNodeMap;
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
/*     */ class MarkerSegment
/*     */   implements Cloneable
/*     */ {
/*     */   protected static final int LENGTH_SIZE = 2;
/*     */   int tag;
/*     */   int length;
/*  50 */   byte[] data = null;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean unknown = false;
/*     */ 
/*     */ 
/*     */   
/*     */   MarkerSegment(JPEGBuffer paramJPEGBuffer) throws IOException {
/*  59 */     paramJPEGBuffer.loadBuf(3);
/*  60 */     this.tag = paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF;
/*  61 */     this.length = (paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF) << 8;
/*  62 */     this.length |= paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF;
/*  63 */     this.length -= 2;
/*     */     
/*  65 */     if (this.length < 0) {
/*  66 */       throw new IIOException("Invalid segment length: " + this.length);
/*     */     }
/*  68 */     paramJPEGBuffer.bufAvail -= 3;
/*     */ 
/*     */     
/*  71 */     paramJPEGBuffer.loadBuf(this.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MarkerSegment(int paramInt) {
/*  79 */     this.tag = paramInt;
/*  80 */     this.length = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MarkerSegment(Node paramNode) throws IIOInvalidTreeException {
/*  89 */     this.tag = getAttributeValue(paramNode, null, "MarkerTag", 0, 255, true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     this.length = 0;
/*     */     
/*  96 */     if (paramNode instanceof IIOMetadataNode) {
/*  97 */       IIOMetadataNode iIOMetadataNode = (IIOMetadataNode)paramNode;
/*     */       try {
/*  99 */         this.data = (byte[])iIOMetadataNode.getUserObject();
/* 100 */       } catch (Exception exception) {
/* 101 */         IIOInvalidTreeException iIOInvalidTreeException = new IIOInvalidTreeException("Can't get User Object", paramNode);
/*     */ 
/*     */         
/* 104 */         iIOInvalidTreeException.initCause(exception);
/* 105 */         throw iIOInvalidTreeException;
/*     */       } 
/*     */     } else {
/* 108 */       throw new IIOInvalidTreeException("Node must have User Object", paramNode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object clone() {
/* 117 */     MarkerSegment markerSegment = null;
/*     */     try {
/* 119 */       markerSegment = (MarkerSegment)super.clone();
/* 120 */     } catch (CloneNotSupportedException cloneNotSupportedException) {}
/* 121 */     if (this.data != null) {
/* 122 */       markerSegment.data = (byte[])this.data.clone();
/*     */     }
/* 124 */     return markerSegment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void loadData(JPEGBuffer paramJPEGBuffer) throws IOException {
/* 132 */     this.data = new byte[this.length];
/* 133 */     paramJPEGBuffer.readData(this.data);
/*     */   }
/*     */   
/*     */   IIOMetadataNode getNativeNode() {
/* 137 */     IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("unknown");
/* 138 */     iIOMetadataNode.setAttribute("MarkerTag", Integer.toString(this.tag));
/* 139 */     iIOMetadataNode.setUserObject(this.data);
/*     */     
/* 141 */     return iIOMetadataNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getAttributeValue(Node paramNode, NamedNodeMap paramNamedNodeMap, String paramString, int paramInt1, int paramInt2, boolean paramBoolean) throws IIOInvalidTreeException {
/* 151 */     if (paramNamedNodeMap == null) {
/* 152 */       paramNamedNodeMap = paramNode.getAttributes();
/*     */     }
/* 154 */     String str = paramNamedNodeMap.getNamedItem(paramString).getNodeValue();
/* 155 */     int i = -1;
/* 156 */     if (str == null) {
/* 157 */       if (paramBoolean) {
/* 158 */         throw new IIOInvalidTreeException(paramString + " attribute not found", paramNode);
/*     */       }
/*     */     } else {
/*     */       
/* 162 */       i = Integer.parseInt(str);
/* 163 */       if (i < paramInt1 || i > paramInt2) {
/* 164 */         throw new IIOInvalidTreeException(paramString + " attribute out of range", paramNode);
/*     */       }
/*     */     } 
/*     */     
/* 168 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void writeTag(ImageOutputStream paramImageOutputStream) throws IOException {
/* 177 */     paramImageOutputStream.write(255);
/* 178 */     paramImageOutputStream.write(this.tag);
/* 179 */     write2bytes(paramImageOutputStream, this.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(ImageOutputStream paramImageOutputStream) throws IOException {
/* 187 */     this.length = 2 + ((this.data != null) ? this.data.length : 0);
/* 188 */     writeTag(paramImageOutputStream);
/* 189 */     if (this.data != null) {
/* 190 */       paramImageOutputStream.write(this.data);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static void write2bytes(ImageOutputStream paramImageOutputStream, int paramInt) throws IOException {
/* 196 */     paramImageOutputStream.write(paramInt >> 8 & 0xFF);
/* 197 */     paramImageOutputStream.write(paramInt & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   void printTag(String paramString) {
/* 202 */     System.out.println(paramString + " marker segment - marker = 0x" + 
/* 203 */         Integer.toHexString(this.tag));
/* 204 */     System.out.println("length: " + this.length);
/*     */   }
/*     */   
/*     */   void print() {
/* 208 */     printTag("Unknown");
/* 209 */     if (this.length > 10) {
/* 210 */       System.out.print("First 5 bytes:"); int i;
/* 211 */       for (i = 0; i < 5; i++) {
/* 212 */         System.out.print(" Ox" + 
/* 213 */             Integer.toHexString(this.data[i]));
/*     */       }
/* 215 */       System.out.print("\nLast 5 bytes:");
/* 216 */       for (i = this.data.length - 5; i < this.data.length; i++) {
/* 217 */         System.out.print(" Ox" + 
/* 218 */             Integer.toHexString(this.data[i]));
/*     */       }
/*     */     } else {
/* 221 */       System.out.print("Data:");
/* 222 */       for (byte b = 0; b < this.data.length; b++) {
/* 223 */         System.out.print(" Ox" + 
/* 224 */             Integer.toHexString(this.data[b]));
/*     */       }
/*     */     } 
/* 227 */     System.out.println();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/MarkerSegment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */