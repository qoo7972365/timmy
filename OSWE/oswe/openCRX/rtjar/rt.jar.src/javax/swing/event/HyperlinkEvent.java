/*     */ package javax.swing.event;
/*     */ 
/*     */ import java.awt.event.InputEvent;
/*     */ import java.net.URL;
/*     */ import java.util.EventObject;
/*     */ import javax.swing.text.Element;
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
/*     */ public class HyperlinkEvent
/*     */   extends EventObject
/*     */ {
/*     */   private EventType type;
/*     */   private URL u;
/*     */   private String desc;
/*     */   private Element sourceElement;
/*     */   private InputEvent inputEvent;
/*     */   
/*     */   public HyperlinkEvent(Object paramObject, EventType paramEventType, URL paramURL) {
/*  61 */     this(paramObject, paramEventType, paramURL, null);
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
/*     */   public HyperlinkEvent(Object paramObject, EventType paramEventType, URL paramURL, String paramString) {
/*  77 */     this(paramObject, paramEventType, paramURL, paramString, null);
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
/*     */   public HyperlinkEvent(Object paramObject, EventType paramEventType, URL paramURL, String paramString, Element paramElement) {
/*  97 */     super(paramObject);
/*  98 */     this.type = paramEventType;
/*  99 */     this.u = paramURL;
/* 100 */     this.desc = paramString;
/* 101 */     this.sourceElement = paramElement;
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
/*     */   public HyperlinkEvent(Object paramObject, EventType paramEventType, URL paramURL, String paramString, Element paramElement, InputEvent paramInputEvent) {
/* 122 */     super(paramObject);
/* 123 */     this.type = paramEventType;
/* 124 */     this.u = paramURL;
/* 125 */     this.desc = paramString;
/* 126 */     this.sourceElement = paramElement;
/* 127 */     this.inputEvent = paramInputEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventType getEventType() {
/* 136 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 146 */     return this.desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getURL() {
/* 155 */     return this.u;
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
/*     */   public Element getSourceElement() {
/* 169 */     return this.sourceElement;
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
/*     */   public InputEvent getInputEvent() {
/* 182 */     return this.inputEvent;
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
/*     */   public static final class EventType
/*     */   {
/*     */     private EventType(String param1String) {
/* 199 */       this.typeString = param1String;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     public static final EventType ENTERED = new EventType("ENTERED");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 210 */     public static final EventType EXITED = new EventType("EXITED");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     public static final EventType ACTIVATED = new EventType("ACTIVATED");
/*     */ 
/*     */     
/*     */     private String typeString;
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 223 */       return this.typeString;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/event/HyperlinkEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */