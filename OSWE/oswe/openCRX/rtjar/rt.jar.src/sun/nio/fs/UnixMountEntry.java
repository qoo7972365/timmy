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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class UnixMountEntry
/*    */ {
/*    */   private byte[] name;
/*    */   private byte[] dir;
/*    */   private byte[] fstype;
/*    */   private byte[] opts;
/*    */   private long dev;
/*    */   private volatile String fstypeAsString;
/*    */   private volatile String optionsAsString;
/*    */   
/*    */   String name() {
/* 46 */     return Util.toString(this.name);
/*    */   }
/*    */   
/*    */   String fstype() {
/* 50 */     if (this.fstypeAsString == null)
/* 51 */       this.fstypeAsString = Util.toString(this.fstype); 
/* 52 */     return this.fstypeAsString;
/*    */   }
/*    */   
/*    */   byte[] dir() {
/* 56 */     return this.dir;
/*    */   }
/*    */   
/*    */   long dev() {
/* 60 */     return this.dev;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean hasOption(String paramString) {
/* 67 */     if (this.optionsAsString == null)
/* 68 */       this.optionsAsString = Util.toString(this.opts); 
/* 69 */     for (String str : Util.split(this.optionsAsString, ',')) {
/* 70 */       if (str.equals(paramString))
/* 71 */         return true; 
/*    */     } 
/* 73 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   boolean isIgnored() {
/* 78 */     return hasOption("ignore");
/*    */   }
/*    */ 
/*    */   
/*    */   boolean isReadOnly() {
/* 83 */     return hasOption("ro");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixMountEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */