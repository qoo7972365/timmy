/*    */ package com.sun.imageio.plugins.jpeg;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.imageio.metadata.IIOInvalidTreeException;
/*    */ import javax.imageio.metadata.IIOMetadataNode;
/*    */ import javax.imageio.stream.ImageOutputStream;
/*    */ import org.w3c.dom.Node;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class DRIMarkerSegment
/*    */   extends MarkerSegment
/*    */ {
/* 43 */   int restartInterval = 0;
/*    */ 
/*    */   
/*    */   DRIMarkerSegment(JPEGBuffer paramJPEGBuffer) throws IOException {
/* 47 */     super(paramJPEGBuffer);
/* 48 */     this.restartInterval = (paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF) << 8;
/* 49 */     this.restartInterval |= paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF;
/* 50 */     paramJPEGBuffer.bufAvail -= this.length;
/*    */   }
/*    */   
/*    */   DRIMarkerSegment(Node paramNode) throws IIOInvalidTreeException {
/* 54 */     super(221);
/* 55 */     updateFromNativeNode(paramNode, true);
/*    */   }
/*    */   
/*    */   IIOMetadataNode getNativeNode() {
/* 59 */     IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("dri");
/* 60 */     iIOMetadataNode.setAttribute("interval", Integer.toString(this.restartInterval));
/* 61 */     return iIOMetadataNode;
/*    */   }
/*    */ 
/*    */   
/*    */   void updateFromNativeNode(Node paramNode, boolean paramBoolean) throws IIOInvalidTreeException {
/* 66 */     this.restartInterval = getAttributeValue(paramNode, null, "interval", 0, 65535, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void write(ImageOutputStream paramImageOutputStream) throws IOException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void print() {
/* 79 */     printTag("DRI");
/* 80 */     System.out.println("Interval: " + 
/* 81 */         Integer.toString(this.restartInterval));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/DRIMarkerSegment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */