/*     */ package javax.imageio.plugins.jpeg;
/*     */ 
/*     */ import javax.imageio.ImageReadParam;
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
/*     */ public class JPEGImageReadParam
/*     */   extends ImageReadParam
/*     */ {
/*  81 */   private JPEGQTable[] qTables = null;
/*  82 */   private JPEGHuffmanTable[] DCHuffmanTables = null;
/*  83 */   private JPEGHuffmanTable[] ACHuffmanTables = null;
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
/*     */   public boolean areTablesSet() {
/*  98 */     return (this.qTables != null);
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
/*     */   public void setDecodeTables(JPEGQTable[] paramArrayOfJPEGQTable, JPEGHuffmanTable[] paramArrayOfJPEGHuffmanTable1, JPEGHuffmanTable[] paramArrayOfJPEGHuffmanTable2) {
/* 125 */     if (paramArrayOfJPEGQTable == null || paramArrayOfJPEGHuffmanTable1 == null || paramArrayOfJPEGHuffmanTable2 == null || paramArrayOfJPEGQTable.length > 4 || paramArrayOfJPEGHuffmanTable1.length > 4 || paramArrayOfJPEGHuffmanTable2.length > 4 || paramArrayOfJPEGHuffmanTable1.length != paramArrayOfJPEGHuffmanTable2.length)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 132 */       throw new IllegalArgumentException("Invalid JPEG table arrays");
/*     */     }
/*     */     
/* 135 */     this.qTables = (JPEGQTable[])paramArrayOfJPEGQTable.clone();
/* 136 */     this.DCHuffmanTables = (JPEGHuffmanTable[])paramArrayOfJPEGHuffmanTable1.clone();
/* 137 */     this.ACHuffmanTables = (JPEGHuffmanTable[])paramArrayOfJPEGHuffmanTable2.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetDecodeTables() {
/* 147 */     this.qTables = null;
/* 148 */     this.DCHuffmanTables = null;
/* 149 */     this.ACHuffmanTables = null;
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
/*     */   public JPEGQTable[] getQTables() {
/* 163 */     return (this.qTables != null) ? (JPEGQTable[])this.qTables.clone() : null;
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
/*     */   public JPEGHuffmanTable[] getDCHuffmanTables() {
/* 177 */     return (this.DCHuffmanTables != null) ? (JPEGHuffmanTable[])this.DCHuffmanTables
/* 178 */       .clone() : null;
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
/*     */   public JPEGHuffmanTable[] getACHuffmanTables() {
/* 193 */     return (this.ACHuffmanTables != null) ? (JPEGHuffmanTable[])this.ACHuffmanTables
/* 194 */       .clone() : null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/plugins/jpeg/JPEGImageReadParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */