/*     */ package sun.rmi.server;
/*     */ 
/*     */ import java.io.ObjectOutput;
/*     */ import java.rmi.server.RMIClientSocketFactory;
/*     */ import java.rmi.server.RMIServerSocketFactory;
/*     */ import java.rmi.server.RemoteRef;
/*     */ import sun.misc.ObjectInputFilter;
/*     */ import sun.rmi.transport.LiveRef;
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
/*     */ public class UnicastServerRef2
/*     */   extends UnicastServerRef
/*     */ {
/*     */   private static final long serialVersionUID = -2289703812660767614L;
/*     */   
/*     */   public UnicastServerRef2() {}
/*     */   
/*     */   public UnicastServerRef2(LiveRef paramLiveRef) {
/*  61 */     super(paramLiveRef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicastServerRef2(LiveRef paramLiveRef, ObjectInputFilter paramObjectInputFilter) {
/*  71 */     super(paramLiveRef, paramObjectInputFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicastServerRef2(int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory) {
/*  82 */     super(new LiveRef(paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory));
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
/*     */   public UnicastServerRef2(int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory, ObjectInputFilter paramObjectInputFilter) {
/*  94 */     super(new LiveRef(paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory), paramObjectInputFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRefClass(ObjectOutput paramObjectOutput) {
/* 102 */     return "UnicastServerRef2";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RemoteRef getClientRef() {
/* 112 */     return new UnicastRef2(this.ref);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/server/UnicastServerRef2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */