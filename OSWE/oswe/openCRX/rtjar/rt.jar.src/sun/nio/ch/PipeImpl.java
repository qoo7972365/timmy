/*    */ package sun.nio.ch;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.nio.channels.Pipe;
/*    */ import java.nio.channels.spi.SelectorProvider;
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
/*    */ class PipeImpl
/*    */   extends Pipe
/*    */ {
/*    */   private final Pipe.SourceChannel source;
/*    */   private final Pipe.SinkChannel sink;
/*    */   
/*    */   PipeImpl(SelectorProvider paramSelectorProvider) {
/* 42 */     long l = IOUtil.makePipe(true);
/* 43 */     int i = (int)(l >>> 32L);
/* 44 */     int j = (int)l;
/* 45 */     FileDescriptor fileDescriptor1 = new FileDescriptor();
/* 46 */     IOUtil.setfdVal(fileDescriptor1, i);
/* 47 */     this.source = new SourceChannelImpl(paramSelectorProvider, fileDescriptor1);
/* 48 */     FileDescriptor fileDescriptor2 = new FileDescriptor();
/* 49 */     IOUtil.setfdVal(fileDescriptor2, j);
/* 50 */     this.sink = new SinkChannelImpl(paramSelectorProvider, fileDescriptor2);
/*    */   }
/*    */   
/*    */   public Pipe.SourceChannel source() {
/* 54 */     return this.source;
/*    */   }
/*    */   
/*    */   public Pipe.SinkChannel sink() {
/* 58 */     return this.sink;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/PipeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */