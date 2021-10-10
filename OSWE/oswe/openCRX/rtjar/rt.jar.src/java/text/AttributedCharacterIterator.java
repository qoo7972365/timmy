/*     */ package java.text;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface AttributedCharacterIterator
/*     */   extends CharacterIterator
/*     */ {
/*     */   int getRunStart();
/*     */   
/*     */   int getRunStart(Attribute paramAttribute);
/*     */   
/*     */   int getRunStart(Set<? extends Attribute> paramSet);
/*     */   
/*     */   int getRunLimit();
/*     */   
/*     */   int getRunLimit(Attribute paramAttribute);
/*     */   
/*     */   int getRunLimit(Set<? extends Attribute> paramSet);
/*     */   
/*     */   Map<Attribute, Object> getAttributes();
/*     */   
/*     */   Object getAttribute(Attribute paramAttribute);
/*     */   
/*     */   Set<Attribute> getAllAttributeKeys();
/*     */   
/*     */   public static class Attribute
/*     */     implements Serializable
/*     */   {
/*     */     private String name;
/* 100 */     private static final Map<String, Attribute> instanceMap = new HashMap<>(7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Attribute(String param1String) {
/* 108 */       this.name = param1String;
/* 109 */       if (getClass() == Attribute.class) {
/* 110 */         instanceMap.put(param1String, this);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean equals(Object param1Object) {
/* 120 */       return super.equals(param1Object);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int hashCode() {
/* 128 */       return super.hashCode();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 137 */       return getClass().getName() + "(" + this.name + ")";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String getName() {
/* 146 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Object readResolve() throws InvalidObjectException {
/* 157 */       if (getClass() != Attribute.class) {
/* 158 */         throw new InvalidObjectException("subclass didn't correctly implement readResolve");
/*     */       }
/*     */       
/* 161 */       Attribute attribute = instanceMap.get(getName());
/* 162 */       if (attribute != null) {
/* 163 */         return attribute;
/*     */       }
/* 165 */       throw new InvalidObjectException("unknown attribute name");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     public static final Attribute LANGUAGE = new Attribute("language");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 186 */     public static final Attribute READING = new Attribute("reading");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     public static final Attribute INPUT_METHOD_SEGMENT = new Attribute("input_method_segment");
/*     */     private static final long serialVersionUID = -9142742483513960612L;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/AttributedCharacterIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */