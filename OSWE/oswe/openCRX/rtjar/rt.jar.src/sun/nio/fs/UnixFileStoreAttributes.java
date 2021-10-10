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
/*    */ class UnixFileStoreAttributes
/*    */ {
/*    */   private long f_frsize;
/*    */   private long f_blocks;
/*    */   private long f_bfree;
/*    */   private long f_bavail;
/*    */   
/*    */   static UnixFileStoreAttributes get(UnixPath paramUnixPath) throws UnixException {
/* 38 */     UnixFileStoreAttributes unixFileStoreAttributes = new UnixFileStoreAttributes();
/* 39 */     UnixNativeDispatcher.statvfs(paramUnixPath, unixFileStoreAttributes);
/* 40 */     return unixFileStoreAttributes;
/*    */   }
/*    */   
/*    */   long blockSize() {
/* 44 */     return this.f_frsize;
/*    */   }
/*    */   
/*    */   long totalBlocks() {
/* 48 */     return this.f_blocks;
/*    */   }
/*    */   
/*    */   long freeBlocks() {
/* 52 */     return this.f_bfree;
/*    */   }
/*    */   
/*    */   long availableBlocks() {
/* 56 */     return this.f_bavail;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixFileStoreAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */