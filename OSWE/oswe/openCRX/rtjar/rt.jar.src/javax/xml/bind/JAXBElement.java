/*     */ package javax.xml.bind;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JAXBElement<T>
/*     */   implements Serializable
/*     */ {
/*     */   protected final QName name;
/*     */   protected final Class<T> declaredType;
/*     */   protected final Class scope;
/*     */   protected T value;
/*     */   protected boolean nil = false;
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public static final class GlobalScope {}
/*     */   
/*     */   public JAXBElement(QName name, Class<T> declaredType, Class<GlobalScope> scope, T value) {
/* 110 */     if (declaredType == null || name == null)
/* 111 */       throw new IllegalArgumentException(); 
/* 112 */     this.declaredType = declaredType;
/* 113 */     if (scope == null) scope = GlobalScope.class; 
/* 114 */     this.scope = scope;
/* 115 */     this.name = name;
/* 116 */     setValue(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBElement(QName name, Class<T> declaredType, T value) {
/* 125 */     this(name, declaredType, GlobalScope.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<T> getDeclaredType() {
/* 132 */     return this.declaredType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/* 139 */     return this.name;
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
/*     */   public void setValue(T t) {
/* 151 */     this.value = t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getValue() {
/* 161 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getScope() {
/* 171 */     return this.scope;
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
/*     */   public boolean isNil() {
/* 184 */     return (this.value == null || this.nil);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNil(boolean value) {
/* 193 */     this.nil = value;
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
/*     */   public boolean isGlobalScope() {
/* 205 */     return (this.scope == GlobalScope.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTypeSubstituted() {
/* 213 */     if (this.value == null) return false; 
/* 214 */     return (this.value.getClass() != this.declaredType);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/JAXBElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */