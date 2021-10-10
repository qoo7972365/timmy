/*     */ package com.sun.imageio.plugins.jpeg;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.imageio.metadata.IIOInvalidTreeException;
/*     */ import javax.imageio.metadata.IIOMetadataNode;
/*     */ import javax.imageio.stream.ImageOutputStream;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ class SOSMarkerSegment
/*     */   extends MarkerSegment
/*     */ {
/*     */   int startSpectralSelection;
/*     */   int endSpectralSelection;
/*     */   int approxHigh;
/*     */   int approxLow;
/*     */   ScanComponentSpec[] componentSpecs;
/*     */   
/*     */   SOSMarkerSegment(boolean paramBoolean, byte[] paramArrayOfbyte, int paramInt) {
/*  52 */     super(218);
/*  53 */     this.startSpectralSelection = 0;
/*  54 */     this.endSpectralSelection = 63;
/*  55 */     this.approxHigh = 0;
/*  56 */     this.approxLow = 0;
/*  57 */     this.componentSpecs = new ScanComponentSpec[paramInt];
/*  58 */     for (byte b = 0; b < paramInt; b++) {
/*  59 */       boolean bool = false;
/*  60 */       if (paramBoolean && (
/*  61 */         b == 1 || b == 2)) {
/*  62 */         bool = true;
/*     */       }
/*     */       
/*  65 */       this.componentSpecs[b] = new ScanComponentSpec(paramArrayOfbyte[b], bool);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SOSMarkerSegment(JPEGBuffer paramJPEGBuffer) throws IOException {
/*  71 */     super(paramJPEGBuffer);
/*  72 */     byte b = paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++];
/*  73 */     this.componentSpecs = new ScanComponentSpec[b];
/*  74 */     for (byte b1 = 0; b1 < b; b1++) {
/*  75 */       this.componentSpecs[b1] = new ScanComponentSpec(paramJPEGBuffer);
/*     */     }
/*  77 */     this.startSpectralSelection = paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++];
/*  78 */     this.endSpectralSelection = paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++];
/*  79 */     this.approxHigh = paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr] >> 4;
/*  80 */     this.approxLow = paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xF;
/*  81 */     paramJPEGBuffer.bufAvail -= this.length;
/*     */   }
/*     */   
/*     */   SOSMarkerSegment(Node paramNode) throws IIOInvalidTreeException {
/*  85 */     super(218);
/*  86 */     this.startSpectralSelection = 0;
/*  87 */     this.endSpectralSelection = 63;
/*  88 */     this.approxHigh = 0;
/*  89 */     this.approxLow = 0;
/*  90 */     updateFromNativeNode(paramNode, true);
/*     */   }
/*     */   
/*     */   protected Object clone() {
/*  94 */     SOSMarkerSegment sOSMarkerSegment = (SOSMarkerSegment)super.clone();
/*  95 */     if (this.componentSpecs != null) {
/*  96 */       sOSMarkerSegment
/*  97 */         .componentSpecs = (ScanComponentSpec[])this.componentSpecs.clone();
/*  98 */       for (byte b = 0; b < this.componentSpecs.length; b++) {
/*  99 */         sOSMarkerSegment.componentSpecs[b] = (ScanComponentSpec)this.componentSpecs[b]
/* 100 */           .clone();
/*     */       }
/*     */     } 
/* 103 */     return sOSMarkerSegment;
/*     */   }
/*     */   
/*     */   IIOMetadataNode getNativeNode() {
/* 107 */     IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("sos");
/* 108 */     iIOMetadataNode.setAttribute("numScanComponents", 
/* 109 */         Integer.toString(this.componentSpecs.length));
/* 110 */     iIOMetadataNode.setAttribute("startSpectralSelection", 
/* 111 */         Integer.toString(this.startSpectralSelection));
/* 112 */     iIOMetadataNode.setAttribute("endSpectralSelection", 
/* 113 */         Integer.toString(this.endSpectralSelection));
/* 114 */     iIOMetadataNode.setAttribute("approxHigh", 
/* 115 */         Integer.toString(this.approxHigh));
/* 116 */     iIOMetadataNode.setAttribute("approxLow", 
/* 117 */         Integer.toString(this.approxLow));
/* 118 */     for (byte b = 0; b < this.componentSpecs.length; b++) {
/* 119 */       iIOMetadataNode.appendChild(this.componentSpecs[b].getNativeNode());
/*     */     }
/*     */     
/* 122 */     return iIOMetadataNode;
/*     */   }
/*     */ 
/*     */   
/*     */   void updateFromNativeNode(Node paramNode, boolean paramBoolean) throws IIOInvalidTreeException {
/* 127 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/* 128 */     int i = getAttributeValue(paramNode, namedNodeMap, "numScanComponents", 1, 4, true);
/*     */     
/* 130 */     int j = getAttributeValue(paramNode, namedNodeMap, "startSpectralSelection", 0, 63, false);
/*     */     
/* 132 */     this.startSpectralSelection = (j != -1) ? j : this.startSpectralSelection;
/* 133 */     j = getAttributeValue(paramNode, namedNodeMap, "endSpectralSelection", 0, 63, false);
/*     */     
/* 135 */     this.endSpectralSelection = (j != -1) ? j : this.endSpectralSelection;
/* 136 */     j = getAttributeValue(paramNode, namedNodeMap, "approxHigh", 0, 15, false);
/* 137 */     this.approxHigh = (j != -1) ? j : this.approxHigh;
/* 138 */     j = getAttributeValue(paramNode, namedNodeMap, "approxLow", 0, 15, false);
/* 139 */     this.approxLow = (j != -1) ? j : this.approxLow;
/*     */ 
/*     */     
/* 142 */     NodeList nodeList = paramNode.getChildNodes();
/* 143 */     if (nodeList.getLength() != i) {
/* 144 */       throw new IIOInvalidTreeException("numScanComponents must match the number of children", paramNode);
/*     */     }
/*     */     
/* 147 */     this.componentSpecs = new ScanComponentSpec[i];
/* 148 */     for (byte b = 0; b < i; b++) {
/* 149 */       this.componentSpecs[b] = new ScanComponentSpec(nodeList.item(b));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(ImageOutputStream paramImageOutputStream) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void print() {
/* 162 */     printTag("SOS");
/* 163 */     System.out.print("Start spectral selection: ");
/* 164 */     System.out.println(this.startSpectralSelection);
/* 165 */     System.out.print("End spectral selection: ");
/* 166 */     System.out.println(this.endSpectralSelection);
/* 167 */     System.out.print("Approx high: ");
/* 168 */     System.out.println(this.approxHigh);
/* 169 */     System.out.print("Approx low: ");
/* 170 */     System.out.println(this.approxLow);
/* 171 */     System.out.print("Num scan components: ");
/* 172 */     System.out.println(this.componentSpecs.length);
/* 173 */     for (byte b = 0; b < this.componentSpecs.length; b++) {
/* 174 */       this.componentSpecs[b].print();
/*     */     }
/*     */   }
/*     */   
/*     */   ScanComponentSpec getScanComponentSpec(byte paramByte, int paramInt) {
/* 179 */     return new ScanComponentSpec(paramByte, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   class ScanComponentSpec
/*     */     implements Cloneable
/*     */   {
/*     */     int componentSelector;
/*     */     int dcHuffTable;
/*     */     int acHuffTable;
/*     */     
/*     */     ScanComponentSpec(byte param1Byte, int param1Int) {
/* 191 */       this.componentSelector = param1Byte;
/* 192 */       this.dcHuffTable = param1Int;
/* 193 */       this.acHuffTable = param1Int;
/*     */     }
/*     */ 
/*     */     
/*     */     ScanComponentSpec(JPEGBuffer param1JPEGBuffer) {
/* 198 */       this.componentSelector = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr++];
/* 199 */       this.dcHuffTable = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr] >> 4;
/* 200 */       this.acHuffTable = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr++] & 0xF;
/*     */     }
/*     */     
/*     */     ScanComponentSpec(Node param1Node) throws IIOInvalidTreeException {
/* 204 */       NamedNodeMap namedNodeMap = param1Node.getAttributes();
/* 205 */       this.componentSelector = MarkerSegment.getAttributeValue(param1Node, namedNodeMap, "componentSelector", 0, 255, true);
/*     */       
/* 207 */       this.dcHuffTable = MarkerSegment.getAttributeValue(param1Node, namedNodeMap, "dcHuffTable", 0, 3, true);
/*     */       
/* 209 */       this.acHuffTable = MarkerSegment.getAttributeValue(param1Node, namedNodeMap, "acHuffTable", 0, 3, true);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Object clone() {
/*     */       try {
/* 215 */         return super.clone();
/* 216 */       } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 217 */         return null;
/*     */       } 
/*     */     }
/*     */     IIOMetadataNode getNativeNode() {
/* 221 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("scanComponentSpec");
/* 222 */       iIOMetadataNode.setAttribute("componentSelector", 
/* 223 */           Integer.toString(this.componentSelector));
/* 224 */       iIOMetadataNode.setAttribute("dcHuffTable", 
/* 225 */           Integer.toString(this.dcHuffTable));
/* 226 */       iIOMetadataNode.setAttribute("acHuffTable", 
/* 227 */           Integer.toString(this.acHuffTable));
/* 228 */       return iIOMetadataNode;
/*     */     }
/*     */     
/*     */     void print() {
/* 232 */       System.out.print("Component Selector: ");
/* 233 */       System.out.println(this.componentSelector);
/* 234 */       System.out.print("DC huffman table: ");
/* 235 */       System.out.println(this.dcHuffTable);
/* 236 */       System.out.print("AC huffman table: ");
/* 237 */       System.out.println(this.acHuffTable);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/SOSMarkerSegment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */