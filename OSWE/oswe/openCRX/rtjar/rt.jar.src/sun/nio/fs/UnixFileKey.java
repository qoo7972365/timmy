/*    */ package sun.nio.fs;
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
/*    */ class UnixFileKey
/*    */ {
/*    */   private final long st_dev;
/*    */   private final long st_ino;
/*    */   
/*    */   UnixFileKey(long paramLong1, long paramLong2) {
/* 37 */     this.st_dev = paramLong1;
/* 38 */     this.st_ino = paramLong2;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 43 */     return (int)(this.st_dev ^ this.st_dev >>> 32L) + (int)(this.st_ino ^ this.st_ino >>> 32L);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 49 */     if (paramObject == this)
/* 50 */       return true; 
/* 51 */     if (!(paramObject instanceof UnixFileKey))
/* 52 */       return false; 
/* 53 */     UnixFileKey unixFileKey = (UnixFileKey)paramObject;
/* 54 */     return (this.st_dev == unixFileKey.st_dev && this.st_ino == unixFileKey.st_ino);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     StringBuilder stringBuilder = new StringBuilder();
/* 60 */     stringBuilder.append("(dev=")
/* 61 */       .append(Long.toHexString(this.st_dev))
/* 62 */       .append(",ino=")
/* 63 */       .append(this.st_ino)
/* 64 */       .append(')');
/* 65 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixFileKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */