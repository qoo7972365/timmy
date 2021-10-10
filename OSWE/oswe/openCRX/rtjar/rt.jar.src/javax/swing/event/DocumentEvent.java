/*     */ package javax.swing.event;
/*     */ 
/*     */ import javax.swing.text.Document;
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
/*     */ public interface DocumentEvent
/*     */ {
/*     */   int getOffset();
/*     */   
/*     */   int getLength();
/*     */   
/*     */   Document getDocument();
/*     */   
/*     */   EventType getType();
/*     */   
/*     */   ElementChange getChange(Element paramElement);
/*     */   
/*     */   public static interface ElementChange
/*     */   {
/*     */     Element getElement();
/*     */     
/*     */     int getIndex();
/*     */     
/*     */     Element[] getChildrenRemoved();
/*     */     
/*     */     Element[] getChildrenAdded();
/*     */   }
/*     */   
/*     */   public static final class EventType
/*     */   {
/*     */     private EventType(String param1String) {
/* 110 */       this.typeString = param1String;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     public static final EventType INSERT = new EventType("INSERT");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     public static final EventType REMOVE = new EventType("REMOVE");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     public static final EventType CHANGE = new EventType("CHANGE");
/*     */ 
/*     */     
/*     */     private String typeString;
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 134 */       return this.typeString;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/event/DocumentEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */