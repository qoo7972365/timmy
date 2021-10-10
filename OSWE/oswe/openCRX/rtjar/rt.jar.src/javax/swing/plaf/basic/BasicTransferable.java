/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import sun.awt.datatransfer.DataTransferer;
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
/*     */ class BasicTransferable
/*     */   implements Transferable, UIResource
/*     */ {
/*     */   protected String plainData;
/*     */   protected String htmlData;
/*     */   private static DataFlavor[] htmlFlavors;
/*     */   private static DataFlavor[] stringFlavors;
/*     */   private static DataFlavor[] plainFlavors;
/*     */   
/*     */   static {
/*     */     try {
/*  50 */       htmlFlavors = new DataFlavor[3];
/*  51 */       htmlFlavors[0] = new DataFlavor("text/html;class=java.lang.String");
/*  52 */       htmlFlavors[1] = new DataFlavor("text/html;class=java.io.Reader");
/*  53 */       htmlFlavors[2] = new DataFlavor("text/html;charset=unicode;class=java.io.InputStream");
/*     */       
/*  55 */       plainFlavors = new DataFlavor[3];
/*  56 */       plainFlavors[0] = new DataFlavor("text/plain;class=java.lang.String");
/*  57 */       plainFlavors[1] = new DataFlavor("text/plain;class=java.io.Reader");
/*  58 */       plainFlavors[2] = new DataFlavor("text/plain;charset=unicode;class=java.io.InputStream");
/*     */       
/*  60 */       stringFlavors = new DataFlavor[2];
/*  61 */       stringFlavors[0] = new DataFlavor("application/x-java-jvm-local-objectref;class=java.lang.String");
/*  62 */       stringFlavors[1] = DataFlavor.stringFlavor;
/*     */     }
/*  64 */     catch (ClassNotFoundException classNotFoundException) {
/*  65 */       System.err.println("error initializing javax.swing.plaf.basic.BasicTranserable");
/*     */     } 
/*     */   }
/*     */   
/*     */   public BasicTransferable(String paramString1, String paramString2) {
/*  70 */     this.plainData = paramString1;
/*  71 */     this.htmlData = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataFlavor[] getTransferDataFlavors() {
/*  82 */     DataFlavor[] arrayOfDataFlavor1 = getRicherFlavors();
/*  83 */     byte b1 = (arrayOfDataFlavor1 != null) ? arrayOfDataFlavor1.length : 0;
/*  84 */     byte b2 = isHTMLSupported() ? htmlFlavors.length : 0;
/*  85 */     byte b3 = isPlainSupported() ? plainFlavors.length : 0;
/*  86 */     byte b4 = isPlainSupported() ? stringFlavors.length : 0;
/*  87 */     int i = b1 + b2 + b3 + b4;
/*  88 */     DataFlavor[] arrayOfDataFlavor2 = new DataFlavor[i];
/*     */ 
/*     */     
/*  91 */     int j = 0;
/*  92 */     if (b1 > 0) {
/*  93 */       System.arraycopy(arrayOfDataFlavor1, 0, arrayOfDataFlavor2, j, b1);
/*  94 */       j += b1;
/*     */     } 
/*  96 */     if (b2 > 0) {
/*  97 */       System.arraycopy(htmlFlavors, 0, arrayOfDataFlavor2, j, b2);
/*  98 */       j += b2;
/*     */     } 
/* 100 */     if (b3 > 0) {
/* 101 */       System.arraycopy(plainFlavors, 0, arrayOfDataFlavor2, j, b3);
/* 102 */       j += b3;
/*     */     } 
/* 104 */     if (b4 > 0) {
/* 105 */       System.arraycopy(stringFlavors, 0, arrayOfDataFlavor2, j, b4);
/* 106 */       j += b4;
/*     */     } 
/* 108 */     return arrayOfDataFlavor2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDataFlavorSupported(DataFlavor paramDataFlavor) {
/* 118 */     DataFlavor[] arrayOfDataFlavor = getTransferDataFlavors();
/* 119 */     for (byte b = 0; b < arrayOfDataFlavor.length; b++) {
/* 120 */       if (arrayOfDataFlavor[b].equals(paramDataFlavor)) {
/* 121 */         return true;
/*     */       }
/*     */     } 
/* 124 */     return false;
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
/*     */ 
/*     */   
/*     */   public Object getTransferData(DataFlavor paramDataFlavor) throws UnsupportedFlavorException, IOException {
/* 139 */     DataFlavor[] arrayOfDataFlavor = getRicherFlavors();
/* 140 */     if (isRicherFlavor(paramDataFlavor))
/* 141 */       return getRicherData(paramDataFlavor); 
/* 142 */     if (isHTMLFlavor(paramDataFlavor)) {
/* 143 */       String str = getHTMLData();
/* 144 */       str = (str == null) ? "" : str;
/* 145 */       if (String.class.equals(paramDataFlavor.getRepresentationClass()))
/* 146 */         return str; 
/* 147 */       if (Reader.class.equals(paramDataFlavor.getRepresentationClass()))
/* 148 */         return new StringReader(str); 
/* 149 */       if (InputStream.class.equals(paramDataFlavor.getRepresentationClass())) {
/* 150 */         return createInputStream(paramDataFlavor, str);
/*     */       }
/*     */     }
/* 153 */     else if (isPlainFlavor(paramDataFlavor)) {
/* 154 */       String str = getPlainData();
/* 155 */       str = (str == null) ? "" : str;
/* 156 */       if (String.class.equals(paramDataFlavor.getRepresentationClass()))
/* 157 */         return str; 
/* 158 */       if (Reader.class.equals(paramDataFlavor.getRepresentationClass()))
/* 159 */         return new StringReader(str); 
/* 160 */       if (InputStream.class.equals(paramDataFlavor.getRepresentationClass())) {
/* 161 */         return createInputStream(paramDataFlavor, str);
/*     */       
/*     */       }
/*     */     }
/* 165 */     else if (isStringFlavor(paramDataFlavor)) {
/* 166 */       String str = getPlainData();
/* 167 */       str = (str == null) ? "" : str;
/* 168 */       return str;
/*     */     } 
/* 170 */     throw new UnsupportedFlavorException(paramDataFlavor);
/*     */   }
/*     */ 
/*     */   
/*     */   private InputStream createInputStream(DataFlavor paramDataFlavor, String paramString) throws IOException, UnsupportedFlavorException {
/* 175 */     String str = DataTransferer.getTextCharset(paramDataFlavor);
/* 176 */     if (str == null) {
/* 177 */       throw new UnsupportedFlavorException(paramDataFlavor);
/*     */     }
/* 179 */     return new ByteArrayInputStream(paramString.getBytes(str));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isRicherFlavor(DataFlavor paramDataFlavor) {
/* 185 */     DataFlavor[] arrayOfDataFlavor = getRicherFlavors();
/* 186 */     byte b1 = (arrayOfDataFlavor != null) ? arrayOfDataFlavor.length : 0;
/* 187 */     for (byte b2 = 0; b2 < b1; b2++) {
/* 188 */       if (arrayOfDataFlavor[b2].equals(paramDataFlavor)) {
/* 189 */         return true;
/*     */       }
/*     */     } 
/* 192 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DataFlavor[] getRicherFlavors() {
/* 201 */     return null;
/*     */   }
/*     */   
/*     */   protected Object getRicherData(DataFlavor paramDataFlavor) throws UnsupportedFlavorException {
/* 205 */     return null;
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
/*     */   protected boolean isHTMLFlavor(DataFlavor paramDataFlavor) {
/* 217 */     DataFlavor[] arrayOfDataFlavor = htmlFlavors;
/* 218 */     for (byte b = 0; b < arrayOfDataFlavor.length; b++) {
/* 219 */       if (arrayOfDataFlavor[b].equals(paramDataFlavor)) {
/* 220 */         return true;
/*     */       }
/*     */     } 
/* 223 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isHTMLSupported() {
/* 231 */     return (this.htmlData != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHTMLData() {
/* 238 */     return this.htmlData;
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
/*     */   protected boolean isPlainFlavor(DataFlavor paramDataFlavor) {
/* 250 */     DataFlavor[] arrayOfDataFlavor = plainFlavors;
/* 251 */     for (byte b = 0; b < arrayOfDataFlavor.length; b++) {
/* 252 */       if (arrayOfDataFlavor[b].equals(paramDataFlavor)) {
/* 253 */         return true;
/*     */       }
/*     */     } 
/* 256 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isPlainSupported() {
/* 264 */     return (this.plainData != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getPlainData() {
/* 271 */     return this.plainData;
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
/*     */   protected boolean isStringFlavor(DataFlavor paramDataFlavor) {
/* 283 */     DataFlavor[] arrayOfDataFlavor = stringFlavors;
/* 284 */     for (byte b = 0; b < arrayOfDataFlavor.length; b++) {
/* 285 */       if (arrayOfDataFlavor[b].equals(paramDataFlavor)) {
/* 286 */         return true;
/*     */       }
/*     */     } 
/* 289 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicTransferable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */