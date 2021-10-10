/*     */ package javax.naming;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BinaryRefAddr
/*     */   extends RefAddr
/*     */ {
/*  70 */   private byte[] buf = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -3415254970957330361L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinaryRefAddr(String paramString, byte[] paramArrayOfbyte) {
/*  81 */     this(paramString, paramArrayOfbyte, 0, paramArrayOfbyte.length);
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
/*     */   public BinaryRefAddr(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  97 */     super(paramString);
/*  98 */     this.buf = new byte[paramInt2];
/*  99 */     System.arraycopy(paramArrayOfbyte, paramInt1, this.buf, 0, paramInt2);
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
/*     */   public Object getContent() {
/* 112 */     return this.buf;
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
/*     */   public boolean equals(Object paramObject) {
/* 124 */     if (paramObject != null && paramObject instanceof BinaryRefAddr) {
/* 125 */       BinaryRefAddr binaryRefAddr = (BinaryRefAddr)paramObject;
/* 126 */       if (this.addrType.compareTo(binaryRefAddr.addrType) == 0) {
/* 127 */         if (this.buf == null && binaryRefAddr.buf == null)
/* 128 */           return true; 
/* 129 */         if (this.buf == null || binaryRefAddr.buf == null || this.buf.length != binaryRefAddr.buf.length)
/*     */         {
/* 131 */           return false; } 
/* 132 */         for (byte b = 0; b < this.buf.length; b++) {
/* 133 */           if (this.buf[b] != binaryRefAddr.buf[b])
/* 134 */             return false; 
/* 135 */         }  return true;
/*     */       } 
/*     */     } 
/* 138 */     return false;
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
/*     */   public int hashCode() {
/* 151 */     int i = this.addrType.hashCode();
/* 152 */     for (byte b = 0; b < this.buf.length; b++) {
/* 153 */       i += this.buf[b];
/*     */     }
/* 155 */     return i;
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
/*     */   public String toString() {
/* 168 */     StringBuffer stringBuffer = new StringBuffer("Address Type: " + this.addrType + "\n");
/*     */     
/* 170 */     stringBuffer.append("AddressContents: ");
/* 171 */     for (byte b = 0; b < this.buf.length && b < 32; b++) {
/* 172 */       stringBuffer.append(Integer.toHexString(this.buf[b]) + " ");
/*     */     }
/* 174 */     if (this.buf.length >= 32)
/* 175 */       stringBuffer.append(" ...\n"); 
/* 176 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/BinaryRefAddr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */