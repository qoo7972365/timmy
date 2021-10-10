/*     */ package javax.xml.bind.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.ValidationEventHandler;
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
/*     */ public class ValidationEventCollector
/*     */   implements ValidationEventHandler
/*     */ {
/*  52 */   private final List<ValidationEvent> events = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValidationEvent[] getEvents() {
/*  63 */     return this.events.<ValidationEvent>toArray(new ValidationEvent[this.events.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  70 */     this.events.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasEvents() {
/*  81 */     return !this.events.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean handleEvent(ValidationEvent event) {
/*  85 */     this.events.add(event);
/*     */     
/*  87 */     boolean retVal = true;
/*  88 */     switch (event.getSeverity())
/*     */     { case 0:
/*  90 */         retVal = true;
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
/* 105 */         return retVal;case 1: retVal = true; return retVal;case 2: retVal = false; return retVal; }  _assert(false, Messages.format("ValidationEventCollector.UnrecognizedSeverity", Integer.valueOf(event.getSeverity()))); return retVal;
/*     */   }
/*     */   
/*     */   private static void _assert(boolean b, String msg) {
/* 109 */     if (!b)
/* 110 */       throw new InternalError(msg); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/util/ValidationEventCollector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */