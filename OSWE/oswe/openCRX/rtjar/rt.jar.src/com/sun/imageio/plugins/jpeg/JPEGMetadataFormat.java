/*     */ package com.sun.imageio.plugins.jpeg;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import javax.imageio.ImageTypeSpecifier;
/*     */ import javax.imageio.metadata.IIOMetadataFormatImpl;
/*     */ import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
/*     */ import javax.imageio.plugins.jpeg.JPEGQTable;
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
/*     */ abstract class JPEGMetadataFormat
/*     */   extends IIOMetadataFormatImpl
/*     */ {
/*     */   private static final int MAX_JPEG_DATA_SIZE = 65533;
/*  41 */   String resourceBaseName = getClass().getName() + "Resources";
/*     */   
/*     */   JPEGMetadataFormat(String paramString, int paramInt) {
/*  44 */     super(paramString, paramInt);
/*  45 */     setResourceBaseName(this.resourceBaseName);
/*     */   }
/*     */ 
/*     */   
/*     */   void addStreamElements(String paramString) {
/*  50 */     addElement("dqt", paramString, 1, 4);
/*     */     
/*  52 */     addElement("dqtable", "dqt", 0);
/*     */     
/*  54 */     addAttribute("dqtable", "elementPrecision", 2, false, "0");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     ArrayList<String> arrayList1 = new ArrayList();
/*  60 */     arrayList1.add("0");
/*  61 */     arrayList1.add("1");
/*  62 */     arrayList1.add("2");
/*  63 */     arrayList1.add("3");
/*  64 */     addAttribute("dqtable", "qtableId", 2, true, (String)null, arrayList1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     addObjectValue("dqtable", JPEGQTable.class, true, (JPEGQTable)null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     addElement("dht", paramString, 1, 4);
/*  76 */     addElement("dhtable", "dht", 0);
/*  77 */     ArrayList<String> arrayList2 = new ArrayList();
/*  78 */     arrayList2.add("0");
/*  79 */     arrayList2.add("1");
/*  80 */     addAttribute("dhtable", "class", 2, true, (String)null, arrayList2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     addAttribute("dhtable", "htableId", 2, true, (String)null, arrayList1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     addObjectValue("dhtable", JPEGHuffmanTable.class, true, (JPEGHuffmanTable)null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     addElement("dri", paramString, 0);
/*  99 */     addAttribute("dri", "interval", 2, true, null, "0", "65535", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     addElement("com", paramString, 0);
/* 108 */     addAttribute("com", "comment", 0, false, null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     addObjectValue("com", byte[].class, 1, 65533);
/*     */     
/* 115 */     addElement("unknown", paramString, 0);
/* 116 */     addAttribute("unknown", "MarkerTag", 2, true, null, "0", "255", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     addObjectValue("unknown", byte[].class, 1, 65533);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canNodeAppear(String paramString, ImageTypeSpecifier paramImageTypeSpecifier) {
/* 129 */     if (isInSubtree(paramString, getRootName())) {
/* 130 */       return true;
/*     */     }
/* 132 */     return false;
/*     */   }
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
/*     */   protected boolean isInSubtree(String paramString1, String paramString2) {
/* 145 */     if (paramString1.equals(paramString2)) {
/* 146 */       return true;
/*     */     }
/* 148 */     String[] arrayOfString = getChildNames(paramString1);
/* 149 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 150 */       if (isInSubtree(paramString1, arrayOfString[b])) {
/* 151 */         return true;
/*     */       }
/*     */     } 
/* 154 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/JPEGMetadataFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */