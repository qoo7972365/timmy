/*    */ package javax.naming.event;
/*    */ 
/*    */ import java.util.EventObject;
/*    */ import javax.naming.NamingException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NamingExceptionEvent
/*    */   extends EventObject
/*    */ {
/*    */   private NamingException exception;
/*    */   private static final long serialVersionUID = -4877678086134736336L;
/*    */   
/*    */   public NamingExceptionEvent(EventContext paramEventContext, NamingException paramNamingException) {
/* 62 */     super(paramEventContext);
/* 63 */     this.exception = paramNamingException;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NamingException getException() {
/* 71 */     return this.exception;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EventContext getEventContext() {
/* 80 */     return (EventContext)getSource();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dispatch(NamingListener paramNamingListener) {
/* 90 */     paramNamingListener.namingExceptionThrown(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/event/NamingExceptionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */