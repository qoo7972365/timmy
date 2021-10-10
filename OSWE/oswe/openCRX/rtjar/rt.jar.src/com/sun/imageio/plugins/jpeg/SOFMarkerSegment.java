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
/*     */ 
/*     */ 
/*     */ class SOFMarkerSegment
/*     */   extends MarkerSegment
/*     */ {
/*     */   int samplePrecision;
/*     */   int numLines;
/*     */   int samplesPerLine;
/*     */   ComponentSpec[] componentSpecs;
/*     */   
/*     */   SOFMarkerSegment(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, byte[] paramArrayOfbyte, int paramInt) {
/*  53 */     super(paramBoolean1 ? 194 : (paramBoolean2 ? 193 : 192));
/*     */ 
/*     */     
/*  56 */     this.samplePrecision = 8;
/*  57 */     this.numLines = 0;
/*  58 */     this.samplesPerLine = 0;
/*  59 */     this.componentSpecs = new ComponentSpec[paramInt];
/*  60 */     for (byte b = 0; b < paramInt; b++) {
/*  61 */       byte b1 = 1;
/*  62 */       boolean bool = false;
/*  63 */       if (paramBoolean3) {
/*  64 */         b1 = 2;
/*  65 */         if (b == 1 || b == 2) {
/*  66 */           b1 = 1;
/*  67 */           bool = true;
/*     */         } 
/*     */       } 
/*  70 */       this.componentSpecs[b] = new ComponentSpec(paramArrayOfbyte[b], b1, bool);
/*     */     } 
/*     */   }
/*     */   
/*     */   SOFMarkerSegment(JPEGBuffer paramJPEGBuffer) throws IOException {
/*  75 */     super(paramJPEGBuffer);
/*  76 */     this.samplePrecision = paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++];
/*  77 */     this.numLines = (paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF) << 8;
/*  78 */     this.numLines |= paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF;
/*  79 */     this.samplesPerLine = (paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF) << 8;
/*  80 */     this.samplesPerLine |= paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF;
/*  81 */     int i = paramJPEGBuffer.buf[paramJPEGBuffer.bufPtr++] & 0xFF;
/*  82 */     this.componentSpecs = new ComponentSpec[i];
/*  83 */     for (byte b = 0; b < i; b++) {
/*  84 */       this.componentSpecs[b] = new ComponentSpec(paramJPEGBuffer);
/*     */     }
/*  86 */     paramJPEGBuffer.bufAvail -= this.length;
/*     */   }
/*     */ 
/*     */   
/*     */   SOFMarkerSegment(Node paramNode) throws IIOInvalidTreeException {
/*  91 */     super(192);
/*  92 */     this.samplePrecision = 8;
/*  93 */     this.numLines = 0;
/*  94 */     this.samplesPerLine = 0;
/*  95 */     updateFromNativeNode(paramNode, true);
/*     */   }
/*     */   
/*     */   protected Object clone() {
/*  99 */     SOFMarkerSegment sOFMarkerSegment = (SOFMarkerSegment)super.clone();
/* 100 */     if (this.componentSpecs != null) {
/* 101 */       sOFMarkerSegment.componentSpecs = (ComponentSpec[])this.componentSpecs.clone();
/* 102 */       for (byte b = 0; b < this.componentSpecs.length; b++) {
/* 103 */         sOFMarkerSegment.componentSpecs[b] = (ComponentSpec)this.componentSpecs[b]
/* 104 */           .clone();
/*     */       }
/*     */     } 
/* 107 */     return sOFMarkerSegment;
/*     */   }
/*     */   
/*     */   IIOMetadataNode getNativeNode() {
/* 111 */     IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("sof");
/* 112 */     iIOMetadataNode.setAttribute("process", Integer.toString(this.tag - 192));
/* 113 */     iIOMetadataNode.setAttribute("samplePrecision", 
/* 114 */         Integer.toString(this.samplePrecision));
/* 115 */     iIOMetadataNode.setAttribute("numLines", 
/* 116 */         Integer.toString(this.numLines));
/* 117 */     iIOMetadataNode.setAttribute("samplesPerLine", 
/* 118 */         Integer.toString(this.samplesPerLine));
/* 119 */     iIOMetadataNode.setAttribute("numFrameComponents", 
/* 120 */         Integer.toString(this.componentSpecs.length));
/* 121 */     for (byte b = 0; b < this.componentSpecs.length; b++) {
/* 122 */       iIOMetadataNode.appendChild(this.componentSpecs[b].getNativeNode());
/*     */     }
/*     */     
/* 125 */     return iIOMetadataNode;
/*     */   }
/*     */ 
/*     */   
/*     */   void updateFromNativeNode(Node paramNode, boolean paramBoolean) throws IIOInvalidTreeException {
/* 130 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/* 131 */     int i = getAttributeValue(paramNode, namedNodeMap, "process", 0, 2, false);
/* 132 */     this.tag = (i != -1) ? (i + 192) : this.tag;
/*     */ 
/*     */     
/* 135 */     i = getAttributeValue(paramNode, namedNodeMap, "samplePrecision", 8, 8, false);
/* 136 */     i = getAttributeValue(paramNode, namedNodeMap, "numLines", 0, 65535, false);
/* 137 */     this.numLines = (i != -1) ? i : this.numLines;
/* 138 */     i = getAttributeValue(paramNode, namedNodeMap, "samplesPerLine", 0, 65535, false);
/* 139 */     this.samplesPerLine = (i != -1) ? i : this.samplesPerLine;
/* 140 */     int j = getAttributeValue(paramNode, namedNodeMap, "numFrameComponents", 1, 4, false);
/*     */     
/* 142 */     NodeList nodeList = paramNode.getChildNodes();
/* 143 */     if (nodeList.getLength() != j) {
/* 144 */       throw new IIOInvalidTreeException("numFrameComponents must match number of children", paramNode);
/*     */     }
/*     */     
/* 147 */     this.componentSpecs = new ComponentSpec[j];
/* 148 */     for (byte b = 0; b < j; b++) {
/* 149 */       this.componentSpecs[b] = new ComponentSpec(nodeList.item(b));
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
/* 162 */     printTag("SOF");
/* 163 */     System.out.print("Sample precision: ");
/* 164 */     System.out.println(this.samplePrecision);
/* 165 */     System.out.print("Number of lines: ");
/* 166 */     System.out.println(this.numLines);
/* 167 */     System.out.print("Samples per line: ");
/* 168 */     System.out.println(this.samplesPerLine);
/* 169 */     System.out.print("Number of components: ");
/* 170 */     System.out.println(this.componentSpecs.length);
/* 171 */     for (byte b = 0; b < this.componentSpecs.length; b++) {
/* 172 */       this.componentSpecs[b].print();
/*     */     }
/*     */   }
/*     */   
/*     */   int getIDencodedCSType() {
/* 177 */     for (byte b = 0; b < this.componentSpecs.length; b++) {
/* 178 */       if ((this.componentSpecs[b]).componentId < 65) {
/* 179 */         return 0;
/*     */       }
/*     */     } 
/* 182 */     switch (this.componentSpecs.length) {
/*     */       case 3:
/* 184 */         if ((this.componentSpecs[0]).componentId == 82 && (this.componentSpecs[0]).componentId == 71 && (this.componentSpecs[0]).componentId == 66)
/*     */         {
/*     */           
/* 187 */           return 2;
/*     */         }
/* 189 */         if ((this.componentSpecs[0]).componentId == 89 && (this.componentSpecs[0]).componentId == 67 && (this.componentSpecs[0]).componentId == 99)
/*     */         {
/*     */           
/* 192 */           return 5;
/*     */         }
/*     */         break;
/*     */       case 4:
/* 196 */         if ((this.componentSpecs[0]).componentId == 82 && (this.componentSpecs[0]).componentId == 71 && (this.componentSpecs[0]).componentId == 66 && (this.componentSpecs[0]).componentId == 65)
/*     */         {
/*     */ 
/*     */           
/* 200 */           return 6;
/*     */         }
/* 202 */         if ((this.componentSpecs[0]).componentId == 89 && (this.componentSpecs[0]).componentId == 67 && (this.componentSpecs[0]).componentId == 99 && (this.componentSpecs[0]).componentId == 65)
/*     */         {
/*     */ 
/*     */           
/* 206 */           return 10;
/*     */         }
/*     */         break;
/*     */     } 
/* 210 */     return 0;
/*     */   }
/*     */   
/*     */   ComponentSpec getComponentSpec(byte paramByte, int paramInt1, int paramInt2) {
/* 214 */     return new ComponentSpec(paramByte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   class ComponentSpec
/*     */     implements Cloneable
/*     */   {
/*     */     int componentId;
/*     */     int HsamplingFactor;
/*     */     int VsamplingFactor;
/*     */     int QtableSelector;
/*     */     
/*     */     ComponentSpec(byte param1Byte, int param1Int1, int param1Int2) {
/* 227 */       this.componentId = param1Byte;
/* 228 */       this.HsamplingFactor = param1Int1;
/* 229 */       this.VsamplingFactor = param1Int1;
/* 230 */       this.QtableSelector = param1Int2;
/*     */     }
/*     */ 
/*     */     
/*     */     ComponentSpec(JPEGBuffer param1JPEGBuffer) {
/* 235 */       this.componentId = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr++];
/* 236 */       this.HsamplingFactor = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr] >>> 4;
/* 237 */       this.VsamplingFactor = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr++] & 0xF;
/* 238 */       this.QtableSelector = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr++];
/*     */     }
/*     */     
/*     */     ComponentSpec(Node param1Node) throws IIOInvalidTreeException {
/* 242 */       NamedNodeMap namedNodeMap = param1Node.getAttributes();
/* 243 */       this.componentId = MarkerSegment.getAttributeValue(param1Node, namedNodeMap, "componentId", 0, 255, true);
/* 244 */       this.HsamplingFactor = MarkerSegment.getAttributeValue(param1Node, namedNodeMap, "HsamplingFactor", 1, 255, true);
/*     */       
/* 246 */       this.VsamplingFactor = MarkerSegment.getAttributeValue(param1Node, namedNodeMap, "VsamplingFactor", 1, 255, true);
/*     */       
/* 248 */       this.QtableSelector = MarkerSegment.getAttributeValue(param1Node, namedNodeMap, "QtableSelector", 0, 3, true);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Object clone() {
/*     */       try {
/* 254 */         return super.clone();
/* 255 */       } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 256 */         return null;
/*     */       } 
/*     */     }
/*     */     IIOMetadataNode getNativeNode() {
/* 260 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("componentSpec");
/* 261 */       iIOMetadataNode.setAttribute("componentId", 
/* 262 */           Integer.toString(this.componentId));
/* 263 */       iIOMetadataNode.setAttribute("HsamplingFactor", 
/* 264 */           Integer.toString(this.HsamplingFactor));
/* 265 */       iIOMetadataNode.setAttribute("VsamplingFactor", 
/* 266 */           Integer.toString(this.VsamplingFactor));
/* 267 */       iIOMetadataNode.setAttribute("QtableSelector", 
/* 268 */           Integer.toString(this.QtableSelector));
/* 269 */       return iIOMetadataNode;
/*     */     }
/*     */     
/*     */     void print() {
/* 273 */       System.out.print("Component ID: ");
/* 274 */       System.out.println(this.componentId);
/* 275 */       System.out.print("H sampling factor: ");
/* 276 */       System.out.println(this.HsamplingFactor);
/* 277 */       System.out.print("V sampling factor: ");
/* 278 */       System.out.println(this.VsamplingFactor);
/* 279 */       System.out.print("Q table selector: ");
/* 280 */       System.out.println(this.QtableSelector);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/SOFMarkerSegment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */