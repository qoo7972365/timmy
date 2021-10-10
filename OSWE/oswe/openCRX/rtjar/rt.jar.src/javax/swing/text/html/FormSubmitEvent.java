/*    */ package javax.swing.text.html;
/*    */ 
/*    */ import java.net.URL;
/*    */ import javax.swing.event.HyperlinkEvent;
/*    */ import javax.swing.text.Element;
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
/*    */ public class FormSubmitEvent
/*    */   extends HTMLFrameHyperlinkEvent
/*    */ {
/*    */   private MethodType method;
/*    */   private String data;
/*    */   
/*    */   public enum MethodType
/*    */   {
/* 48 */     GET, POST;
/*    */   }
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
/*    */   FormSubmitEvent(Object paramObject, HyperlinkEvent.EventType paramEventType, URL paramURL, Element paramElement, String paramString1, MethodType paramMethodType, String paramString2) {
/* 65 */     super(paramObject, paramEventType, paramURL, paramElement, paramString1);
/* 66 */     this.method = paramMethodType;
/* 67 */     this.data = paramString2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MethodType getMethod() {
/* 78 */     return this.method;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getData() {
/* 87 */     return this.data;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/FormSubmitEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */