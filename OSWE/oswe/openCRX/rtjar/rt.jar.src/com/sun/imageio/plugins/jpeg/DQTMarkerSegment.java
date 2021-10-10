/*     */ package com.sun.imageio.plugins.jpeg;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.imageio.IIOException;
/*     */ import javax.imageio.metadata.IIOInvalidTreeException;
/*     */ import javax.imageio.metadata.IIOMetadataNode;
/*     */ import javax.imageio.plugins.jpeg.JPEGQTable;
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
/*     */ class DQTMarkerSegment
/*     */   extends MarkerSegment
/*     */ {
/*  47 */   List tables = new ArrayList();
/*     */   
/*     */   DQTMarkerSegment(float paramFloat, boolean paramBoolean) {
/*  50 */     super(219);
/*  51 */     this.tables.add(new Qtable(true, paramFloat));
/*  52 */     if (paramBoolean) {
/*  53 */       this.tables.add(new Qtable(false, paramFloat));
/*     */     }
/*     */   }
/*     */   
/*     */   DQTMarkerSegment(JPEGBuffer paramJPEGBuffer) throws IOException {
/*  58 */     super(paramJPEGBuffer);
/*  59 */     int i = this.length;
/*  60 */     while (i > 0) {
/*  61 */       Qtable qtable = new Qtable(paramJPEGBuffer);
/*  62 */       this.tables.add(qtable);
/*  63 */       i -= qtable.data.length + 1;
/*     */     } 
/*  65 */     paramJPEGBuffer.bufAvail -= this.length;
/*     */   }
/*     */   
/*     */   DQTMarkerSegment(JPEGQTable[] paramArrayOfJPEGQTable) {
/*  69 */     super(219);
/*  70 */     for (byte b = 0; b < paramArrayOfJPEGQTable.length; b++) {
/*  71 */       this.tables.add(new Qtable(paramArrayOfJPEGQTable[b], b));
/*     */     }
/*     */   }
/*     */   
/*     */   DQTMarkerSegment(Node paramNode) throws IIOInvalidTreeException {
/*  76 */     super(219);
/*  77 */     NodeList nodeList = paramNode.getChildNodes();
/*  78 */     int i = nodeList.getLength();
/*  79 */     if (i < 1 || i > 4) {
/*  80 */       throw new IIOInvalidTreeException("Invalid DQT node", paramNode);
/*     */     }
/*  82 */     for (byte b = 0; b < i; b++) {
/*  83 */       this.tables.add(new Qtable(nodeList.item(b)));
/*     */     }
/*     */   }
/*     */   
/*     */   protected Object clone() {
/*  88 */     DQTMarkerSegment dQTMarkerSegment = (DQTMarkerSegment)super.clone();
/*  89 */     dQTMarkerSegment.tables = new ArrayList(this.tables.size());
/*  90 */     Iterator<Qtable> iterator = this.tables.iterator();
/*  91 */     while (iterator.hasNext()) {
/*  92 */       Qtable qtable = iterator.next();
/*  93 */       dQTMarkerSegment.tables.add(qtable.clone());
/*     */     } 
/*  95 */     return dQTMarkerSegment;
/*     */   }
/*     */   
/*     */   IIOMetadataNode getNativeNode() {
/*  99 */     IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("dqt");
/* 100 */     for (byte b = 0; b < this.tables.size(); b++) {
/* 101 */       Qtable qtable = this.tables.get(b);
/* 102 */       iIOMetadataNode.appendChild(qtable.getNativeNode());
/*     */     } 
/* 104 */     return iIOMetadataNode;
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
/* 116 */     printTag("DQT");
/* 117 */     System.out.println("Num tables: " + 
/* 118 */         Integer.toString(this.tables.size()));
/* 119 */     for (byte b = 0; b < this.tables.size(); b++) {
/* 120 */       Qtable qtable = this.tables.get(b);
/* 121 */       qtable.print();
/*     */     } 
/* 123 */     System.out.println();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Qtable getChromaForLuma(Qtable paramQtable) {
/* 132 */     Qtable qtable = null;
/*     */ 
/*     */     
/* 135 */     boolean bool = true;
/* 136 */     byte b = 1; paramQtable.getClass(); for (; b < 64; b++) {
/* 137 */       if (paramQtable.data[b] != paramQtable.data[b - 1]) {
/* 138 */         bool = false;
/*     */         break;
/*     */       } 
/*     */     } 
/* 142 */     if (bool) {
/* 143 */       qtable = (Qtable)paramQtable.clone();
/* 144 */       qtable.tableID = 1;
/*     */     }
/*     */     else {
/*     */       
/* 148 */       b = 0;
/* 149 */       byte b1 = 1; paramQtable.getClass(); for (; b1 < 64; b1++) {
/* 150 */         if (paramQtable.data[b1] > paramQtable.data[b]) {
/* 151 */           b = b1;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 161 */       float f = paramQtable.data[b] / JPEGQTable.K1Div2Luminance.getTable()[b];
/*     */ 
/*     */       
/* 164 */       JPEGQTable jPEGQTable = JPEGQTable.K2Div2Chrominance.getScaledInstance(f, true);
/*     */       
/* 166 */       qtable = new Qtable(jPEGQTable, 1);
/*     */     } 
/* 168 */     return qtable;
/*     */   }
/*     */   
/*     */   Qtable getQtableFromNode(Node paramNode) throws IIOInvalidTreeException {
/* 172 */     return new Qtable(paramNode);
/*     */   }
/*     */ 
/*     */   
/*     */   class Qtable
/*     */     implements Cloneable
/*     */   {
/*     */     int elementPrecision;
/*     */     int tableID;
/* 181 */     final int QTABLE_SIZE = 64;
/*     */ 
/*     */ 
/*     */     
/*     */     int[] data;
/*     */ 
/*     */     
/* 188 */     private final int[] zigzag = new int[] { 0, 1, 5, 6, 14, 15, 27, 28, 2, 4, 7, 13, 16, 26, 29, 42, 3, 8, 12, 17, 25, 30, 41, 43, 9, 11, 18, 24, 31, 40, 44, 53, 10, 19, 23, 32, 39, 45, 52, 54, 20, 22, 33, 38, 46, 51, 55, 60, 21, 34, 37, 47, 50, 56, 59, 61, 35, 36, 48, 49, 57, 58, 62, 63 };
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
/*     */     Qtable(boolean param1Boolean, float param1Float) {
/* 200 */       this.elementPrecision = 0;
/* 201 */       JPEGQTable jPEGQTable = null;
/* 202 */       if (param1Boolean) {
/* 203 */         this.tableID = 0;
/* 204 */         jPEGQTable = JPEGQTable.K1Div2Luminance;
/*     */       } else {
/* 206 */         this.tableID = 1;
/* 207 */         jPEGQTable = JPEGQTable.K2Div2Chrominance;
/*     */       } 
/* 209 */       if (param1Float != 0.75F) {
/* 210 */         param1Float = JPEG.convertToLinearQuality(param1Float);
/* 211 */         if (param1Boolean) {
/*     */           
/* 213 */           jPEGQTable = JPEGQTable.K1Luminance.getScaledInstance(param1Float, true);
/*     */         } else {
/*     */           
/* 216 */           jPEGQTable = JPEGQTable.K2Div2Chrominance.getScaledInstance(param1Float, true);
/*     */         } 
/*     */       } 
/* 219 */       this.data = jPEGQTable.getTable();
/*     */     }
/*     */     
/*     */     Qtable(JPEGBuffer param1JPEGBuffer) throws IIOException {
/* 223 */       this.elementPrecision = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr] >>> 4;
/* 224 */       this.tableID = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr++] & 0xF;
/* 225 */       if (this.elementPrecision != 0)
/*     */       {
/* 227 */         throw new IIOException("Unsupported element precision");
/*     */       }
/* 229 */       this.data = new int[64];
/*     */       
/* 231 */       for (byte b = 0; b < 64; b++) {
/* 232 */         this.data[b] = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr + this.zigzag[b]] & 0xFF;
/*     */       }
/* 234 */       param1JPEGBuffer.bufPtr += 64;
/*     */     }
/*     */     
/*     */     Qtable(JPEGQTable param1JPEGQTable, int param1Int) {
/* 238 */       this.elementPrecision = 0;
/* 239 */       this.tableID = param1Int;
/* 240 */       this.data = param1JPEGQTable.getTable();
/*     */     }
/*     */     
/*     */     Qtable(Node param1Node) throws IIOInvalidTreeException {
/* 244 */       if (param1Node.getNodeName().equals("dqtable")) {
/* 245 */         NamedNodeMap namedNodeMap = param1Node.getAttributes();
/* 246 */         int i = namedNodeMap.getLength();
/* 247 */         if (i < 1 || i > 2) {
/* 248 */           throw new IIOInvalidTreeException("dqtable node must have 1 or 2 attributes", param1Node);
/*     */         }
/*     */         
/* 251 */         this.elementPrecision = 0;
/* 252 */         this.tableID = MarkerSegment.getAttributeValue(param1Node, namedNodeMap, "qtableId", 0, 3, true);
/* 253 */         if (param1Node instanceof IIOMetadataNode) {
/* 254 */           IIOMetadataNode iIOMetadataNode = (IIOMetadataNode)param1Node;
/* 255 */           JPEGQTable jPEGQTable = (JPEGQTable)iIOMetadataNode.getUserObject();
/* 256 */           if (jPEGQTable == null) {
/* 257 */             throw new IIOInvalidTreeException("dqtable node must have user object", param1Node);
/*     */           }
/*     */           
/* 260 */           this.data = jPEGQTable.getTable();
/*     */         } else {
/* 262 */           throw new IIOInvalidTreeException("dqtable node must have user object", param1Node);
/*     */         } 
/*     */       } else {
/*     */         
/* 266 */         throw new IIOInvalidTreeException("Invalid node, expected dqtable", param1Node);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected Object clone() {
/* 272 */       Qtable qtable = null;
/*     */       try {
/* 274 */         qtable = (Qtable)super.clone();
/* 275 */       } catch (CloneNotSupportedException cloneNotSupportedException) {}
/* 276 */       if (this.data != null) {
/* 277 */         qtable.data = (int[])this.data.clone();
/*     */       }
/* 279 */       return qtable;
/*     */     }
/*     */     
/*     */     IIOMetadataNode getNativeNode() {
/* 283 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("dqtable");
/* 284 */       iIOMetadataNode.setAttribute("elementPrecision", 
/* 285 */           Integer.toString(this.elementPrecision));
/* 286 */       iIOMetadataNode.setAttribute("qtableId", 
/* 287 */           Integer.toString(this.tableID));
/* 288 */       iIOMetadataNode.setUserObject(new JPEGQTable(this.data));
/* 289 */       return iIOMetadataNode;
/*     */     }
/*     */     
/*     */     void print() {
/* 293 */       System.out.println("Table id: " + Integer.toString(this.tableID));
/* 294 */       System.out.println("Element precision: " + 
/* 295 */           Integer.toString(this.elementPrecision));
/*     */       
/* 297 */       (new JPEGQTable(this.data)).toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/DQTMarkerSegment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */