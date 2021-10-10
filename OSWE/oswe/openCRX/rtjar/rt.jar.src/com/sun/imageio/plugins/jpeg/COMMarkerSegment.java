/*     */ package com.sun.imageio.plugins.jpeg;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import javax.imageio.metadata.IIOInvalidTreeException;
/*     */ import javax.imageio.metadata.IIOMetadataNode;
/*     */ import javax.imageio.stream.ImageOutputStream;
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
/*     */ class COMMarkerSegment
/*     */   extends MarkerSegment
/*     */ {
/*     */   private static final String ENCODING = "ISO-8859-1";
/*     */   
/*     */   COMMarkerSegment(JPEGBuffer paramJPEGBuffer) throws IOException {
/*  56 */     super(paramJPEGBuffer);
/*  57 */     loadData(paramJPEGBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   COMMarkerSegment(String paramString) {
/*  66 */     super(254);
/*  67 */     this.data = paramString.getBytes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   COMMarkerSegment(Node paramNode) throws IIOInvalidTreeException {
/*  77 */     super(254);
/*  78 */     if (paramNode instanceof IIOMetadataNode) {
/*  79 */       IIOMetadataNode iIOMetadataNode = (IIOMetadataNode)paramNode;
/*  80 */       this.data = (byte[])iIOMetadataNode.getUserObject();
/*     */     } 
/*  82 */     if (this.data == null) {
/*     */       
/*  84 */       String str = paramNode.getAttributes().getNamedItem("comment").getNodeValue();
/*  85 */       if (str != null) {
/*  86 */         this.data = str.getBytes();
/*     */       } else {
/*  88 */         throw new IIOInvalidTreeException("Empty comment node!", paramNode);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getComment() {
/*     */     try {
/* 100 */       return new String(this.data, "ISO-8859-1");
/* 101 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 102 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   IIOMetadataNode getNativeNode() {
/* 111 */     IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("com");
/* 112 */     iIOMetadataNode.setAttribute("comment", getComment());
/* 113 */     if (this.data != null) {
/* 114 */       iIOMetadataNode.setUserObject(this.data.clone());
/*     */     }
/* 116 */     return iIOMetadataNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(ImageOutputStream paramImageOutputStream) throws IOException {
/* 124 */     this.length = 2 + this.data.length;
/* 125 */     writeTag(paramImageOutputStream);
/* 126 */     paramImageOutputStream.write(this.data);
/*     */   }
/*     */   
/*     */   void print() {
/* 130 */     printTag("COM");
/* 131 */     System.out.println("<" + getComment() + ">");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/COMMarkerSegment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */