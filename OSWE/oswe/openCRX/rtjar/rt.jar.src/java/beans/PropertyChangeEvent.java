/*     */ package java.beans;
/*     */ 
/*     */ import java.util.EventObject;
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
/*     */ public class PropertyChangeEvent
/*     */   extends EventObject
/*     */ {
/*     */   private static final long serialVersionUID = 7042693688939648123L;
/*     */   private String propertyName;
/*     */   private Object newValue;
/*     */   private Object oldValue;
/*     */   private Object propagationId;
/*     */   
/*     */   public PropertyChangeEvent(Object paramObject1, String paramString, Object paramObject2, Object paramObject3) {
/*  62 */     super(paramObject1);
/*  63 */     this.propertyName = paramString;
/*  64 */     this.newValue = paramObject3;
/*  65 */     this.oldValue = paramObject2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPropertyName() {
/*  75 */     return this.propertyName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getNewValue() {
/*  85 */     return this.newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getOldValue() {
/*  95 */     return this.oldValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropagationId(Object paramObject) {
/* 104 */     this.propagationId = paramObject;
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
/*     */   public Object getPropagationId() {
/* 118 */     return this.propagationId;
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
/*     */   public String toString() {
/* 154 */     StringBuilder stringBuilder = new StringBuilder(getClass().getName());
/* 155 */     stringBuilder.append("[propertyName=").append(getPropertyName());
/* 156 */     appendTo(stringBuilder);
/* 157 */     stringBuilder.append("; oldValue=").append(getOldValue());
/* 158 */     stringBuilder.append("; newValue=").append(getNewValue());
/* 159 */     stringBuilder.append("; propagationId=").append(getPropagationId());
/* 160 */     stringBuilder.append("; source=").append(getSource());
/* 161 */     return stringBuilder.append("]").toString();
/*     */   }
/*     */   
/*     */   void appendTo(StringBuilder paramStringBuilder) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/PropertyChangeEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */