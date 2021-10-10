/*    */ package sun.audio;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.SequenceInputStream;
/*    */ import java.util.Enumeration;
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
/*    */ public final class AudioStreamSequence
/*    */   extends SequenceInputStream
/*    */ {
/*    */   Enumeration e;
/*    */   InputStream in;
/*    */   
/*    */   public AudioStreamSequence(Enumeration<? extends InputStream> paramEnumeration) {
/* 56 */     super(paramEnumeration);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/audio/AudioStreamSequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */