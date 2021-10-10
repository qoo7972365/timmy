/*     */ package com.sun.imageio.plugins.jpeg;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.imageio.metadata.IIOInvalidTreeException;
/*     */ import javax.imageio.metadata.IIOMetadataNode;
/*     */ import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
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
/*     */ class DHTMarkerSegment
/*     */   extends MarkerSegment
/*     */ {
/*  46 */   List tables = new ArrayList();
/*     */   
/*     */   DHTMarkerSegment(boolean paramBoolean) {
/*  49 */     super(196);
/*  50 */     this.tables.add(new Htable(JPEGHuffmanTable.StdDCLuminance, true, 0));
/*  51 */     if (paramBoolean) {
/*  52 */       this.tables.add(new Htable(JPEGHuffmanTable.StdDCChrominance, true, 1));
/*     */     }
/*  54 */     this.tables.add(new Htable(JPEGHuffmanTable.StdACLuminance, false, 0));
/*  55 */     if (paramBoolean) {
/*  56 */       this.tables.add(new Htable(JPEGHuffmanTable.StdACChrominance, false, 1));
/*     */     }
/*     */   }
/*     */   
/*     */   DHTMarkerSegment(JPEGBuffer paramJPEGBuffer) throws IOException {
/*  61 */     super(paramJPEGBuffer);
/*  62 */     int i = this.length;
/*  63 */     while (i > 0) {
/*  64 */       Htable htable = new Htable(paramJPEGBuffer);
/*  65 */       this.tables.add(htable);
/*  66 */       i -= 17 + htable.values.length;
/*     */     } 
/*  68 */     paramJPEGBuffer.bufAvail -= this.length;
/*     */   }
/*     */ 
/*     */   
/*     */   DHTMarkerSegment(JPEGHuffmanTable[] paramArrayOfJPEGHuffmanTable1, JPEGHuffmanTable[] paramArrayOfJPEGHuffmanTable2) {
/*  73 */     super(196); byte b;
/*  74 */     for (b = 0; b < paramArrayOfJPEGHuffmanTable1.length; b++) {
/*  75 */       this.tables.add(new Htable(paramArrayOfJPEGHuffmanTable1[b], true, b));
/*     */     }
/*  77 */     for (b = 0; b < paramArrayOfJPEGHuffmanTable2.length; b++) {
/*  78 */       this.tables.add(new Htable(paramArrayOfJPEGHuffmanTable2[b], false, b));
/*     */     }
/*     */   }
/*     */   
/*     */   DHTMarkerSegment(Node paramNode) throws IIOInvalidTreeException {
/*  83 */     super(196);
/*  84 */     NodeList nodeList = paramNode.getChildNodes();
/*  85 */     int i = nodeList.getLength();
/*  86 */     if (i < 1 || i > 4) {
/*  87 */       throw new IIOInvalidTreeException("Invalid DHT node", paramNode);
/*     */     }
/*  89 */     for (byte b = 0; b < i; b++) {
/*  90 */       this.tables.add(new Htable(nodeList.item(b)));
/*     */     }
/*     */   }
/*     */   
/*     */   protected Object clone() {
/*  95 */     DHTMarkerSegment dHTMarkerSegment = (DHTMarkerSegment)super.clone();
/*  96 */     dHTMarkerSegment.tables = new ArrayList(this.tables.size());
/*  97 */     Iterator<Htable> iterator = this.tables.iterator();
/*  98 */     while (iterator.hasNext()) {
/*  99 */       Htable htable = iterator.next();
/* 100 */       dHTMarkerSegment.tables.add(htable.clone());
/*     */     } 
/* 102 */     return dHTMarkerSegment;
/*     */   }
/*     */   
/*     */   IIOMetadataNode getNativeNode() {
/* 106 */     IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("dht");
/* 107 */     for (byte b = 0; b < this.tables.size(); b++) {
/* 108 */       Htable htable = this.tables.get(b);
/* 109 */       iIOMetadataNode.appendChild(htable.getNativeNode());
/*     */     } 
/* 111 */     return iIOMetadataNode;
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
/* 123 */     printTag("DHT");
/* 124 */     System.out.println("Num tables: " + 
/* 125 */         Integer.toString(this.tables.size()));
/* 126 */     for (byte b = 0; b < this.tables.size(); b++) {
/* 127 */       Htable htable = this.tables.get(b);
/* 128 */       htable.print();
/*     */     } 
/* 130 */     System.out.println();
/*     */   }
/*     */ 
/*     */   
/*     */   Htable getHtableFromNode(Node paramNode) throws IIOInvalidTreeException {
/* 135 */     return new Htable(paramNode);
/*     */   }
/*     */   
/*     */   void addHtable(JPEGHuffmanTable paramJPEGHuffmanTable, boolean paramBoolean, int paramInt) {
/* 139 */     this.tables.add(new Htable(paramJPEGHuffmanTable, paramBoolean, paramInt));
/*     */   }
/*     */ 
/*     */   
/*     */   class Htable
/*     */     implements Cloneable
/*     */   {
/*     */     int tableClass;
/*     */     
/*     */     int tableID;
/*     */     private static final int NUM_LENGTHS = 16;
/* 150 */     short[] numCodes = new short[16];
/*     */     short[] values;
/*     */     
/*     */     Htable(JPEGBuffer param1JPEGBuffer) {
/* 154 */       this.tableClass = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr] >>> 4;
/* 155 */       this.tableID = param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr++] & 0xF; int i;
/* 156 */       for (i = 0; i < 16; i++) {
/* 157 */         this.numCodes[i] = (short)(param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr++] & 0xFF);
/*     */       }
/*     */       
/* 160 */       i = 0; byte b;
/* 161 */       for (b = 0; b < 16; b++) {
/* 162 */         i += this.numCodes[b];
/*     */       }
/* 164 */       this.values = new short[i];
/* 165 */       for (b = 0; b < i; b++) {
/* 166 */         this.values[b] = (short)(param1JPEGBuffer.buf[param1JPEGBuffer.bufPtr++] & 0xFF);
/*     */       }
/*     */     }
/*     */     
/*     */     Htable(JPEGHuffmanTable param1JPEGHuffmanTable, boolean param1Boolean, int param1Int) {
/* 171 */       this.tableClass = param1Boolean ? 0 : 1;
/* 172 */       this.tableID = param1Int;
/* 173 */       this.numCodes = param1JPEGHuffmanTable.getLengths();
/* 174 */       this.values = param1JPEGHuffmanTable.getValues();
/*     */     }
/*     */     
/*     */     Htable(Node param1Node) throws IIOInvalidTreeException {
/* 178 */       if (param1Node.getNodeName().equals("dhtable")) {
/* 179 */         NamedNodeMap namedNodeMap = param1Node.getAttributes();
/* 180 */         int i = namedNodeMap.getLength();
/* 181 */         if (i != 2) {
/* 182 */           throw new IIOInvalidTreeException("dhtable node must have 2 attributes", param1Node);
/*     */         }
/*     */         
/* 185 */         this.tableClass = MarkerSegment.getAttributeValue(param1Node, namedNodeMap, "class", 0, 1, true);
/* 186 */         this.tableID = MarkerSegment.getAttributeValue(param1Node, namedNodeMap, "htableId", 0, 3, true);
/* 187 */         if (param1Node instanceof IIOMetadataNode) {
/* 188 */           IIOMetadataNode iIOMetadataNode = (IIOMetadataNode)param1Node;
/*     */           
/* 190 */           JPEGHuffmanTable jPEGHuffmanTable = (JPEGHuffmanTable)iIOMetadataNode.getUserObject();
/* 191 */           if (jPEGHuffmanTable == null) {
/* 192 */             throw new IIOInvalidTreeException("dhtable node must have user object", param1Node);
/*     */           }
/*     */           
/* 195 */           this.numCodes = jPEGHuffmanTable.getLengths();
/* 196 */           this.values = jPEGHuffmanTable.getValues();
/*     */         } else {
/* 198 */           throw new IIOInvalidTreeException("dhtable node must have user object", param1Node);
/*     */         } 
/*     */       } else {
/*     */         
/* 202 */         throw new IIOInvalidTreeException("Invalid node, expected dqtable", param1Node);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected Object clone() {
/* 209 */       Htable htable = null;
/*     */       try {
/* 211 */         htable = (Htable)super.clone();
/* 212 */       } catch (CloneNotSupportedException cloneNotSupportedException) {}
/* 213 */       if (this.numCodes != null) {
/* 214 */         htable.numCodes = (short[])this.numCodes.clone();
/*     */       }
/* 216 */       if (this.values != null) {
/* 217 */         htable.values = (short[])this.values.clone();
/*     */       }
/* 219 */       return htable;
/*     */     }
/*     */     
/*     */     IIOMetadataNode getNativeNode() {
/* 223 */       IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("dhtable");
/* 224 */       iIOMetadataNode.setAttribute("class", Integer.toString(this.tableClass));
/* 225 */       iIOMetadataNode.setAttribute("htableId", Integer.toString(this.tableID));
/*     */       
/* 227 */       iIOMetadataNode.setUserObject(new JPEGHuffmanTable(this.numCodes, this.values));
/*     */       
/* 229 */       return iIOMetadataNode;
/*     */     }
/*     */ 
/*     */     
/*     */     void print() {
/* 234 */       System.out.println("Huffman Table");
/* 235 */       System.out.println("table class: " + ((this.tableClass == 0) ? "DC" : "AC"));
/*     */       
/* 237 */       System.out.println("table id: " + Integer.toString(this.tableID));
/*     */       
/* 239 */       (new JPEGHuffmanTable(this.numCodes, this.values)).toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/DHTMarkerSegment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */