/*     */ package javax.swing;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
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
/*     */ class MultiUIDefaults
/*     */   extends UIDefaults
/*     */ {
/*     */   private UIDefaults[] tables;
/*     */   
/*     */   public MultiUIDefaults(UIDefaults[] paramArrayOfUIDefaults) {
/*  47 */     this.tables = paramArrayOfUIDefaults;
/*     */   }
/*     */ 
/*     */   
/*     */   public MultiUIDefaults() {
/*  52 */     this.tables = new UIDefaults[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object paramObject) {
/*  58 */     Object object = super.get(paramObject);
/*  59 */     if (object != null) {
/*  60 */       return object;
/*     */     }
/*     */     
/*  63 */     for (UIDefaults uIDefaults : this.tables) {
/*  64 */       object = (uIDefaults != null) ? uIDefaults.get(paramObject) : null;
/*  65 */       if (object != null) {
/*  66 */         return object;
/*     */       }
/*     */     } 
/*     */     
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object paramObject, Locale paramLocale) {
/*  76 */     Object object = super.get(paramObject, paramLocale);
/*  77 */     if (object != null) {
/*  78 */       return object;
/*     */     }
/*     */     
/*  81 */     for (UIDefaults uIDefaults : this.tables) {
/*  82 */       object = (uIDefaults != null) ? uIDefaults.get(paramObject, paramLocale) : null;
/*  83 */       if (object != null) {
/*  84 */         return object;
/*     */       }
/*     */     } 
/*     */     
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  93 */     return entrySet().size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  98 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<Object> keys() {
/* 104 */     return new MultiUIDefaultsEnumerator(MultiUIDefaultsEnumerator.Type.KEYS, 
/* 105 */         entrySet());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<Object> elements() {
/* 111 */     return new MultiUIDefaultsEnumerator(MultiUIDefaultsEnumerator.Type.ELEMENTS, 
/* 112 */         entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<Object, Object>> entrySet() {
/* 117 */     HashSet<Map.Entry<Object, Object>> hashSet = new HashSet();
/* 118 */     for (int i = this.tables.length - 1; i >= 0; i--) {
/* 119 */       if (this.tables[i] != null) {
/* 120 */         hashSet.addAll(this.tables[i].entrySet());
/*     */       }
/*     */     } 
/* 123 */     hashSet.addAll(super.entrySet());
/* 124 */     return hashSet;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getUIError(String paramString) {
/* 129 */     if (this.tables.length > 0) {
/* 130 */       this.tables[0].getUIError(paramString);
/*     */     } else {
/* 132 */       super.getUIError(paramString);
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum Type
/*     */   {
/* 138 */     KEYS, ELEMENTS; } private static class MultiUIDefaultsEnumerator implements Enumeration<Object> { private Iterator<Map.Entry<Object, Object>> iterator; private Type type; public enum Type { KEYS, ELEMENTS; }
/*     */ 
/*     */ 
/*     */     
/*     */     MultiUIDefaultsEnumerator(Type param1Type, Set<Map.Entry<Object, Object>> param1Set) {
/* 143 */       this.type = param1Type;
/* 144 */       this.iterator = param1Set.iterator();
/*     */     }
/*     */     
/*     */     public boolean hasMoreElements() {
/* 148 */       return this.iterator.hasNext();
/*     */     }
/*     */     
/*     */     public Object nextElement() {
/* 152 */       switch (this.type) { case KEYS:
/* 153 */           return ((Map.Entry)this.iterator.next()).getKey();
/* 154 */         case ELEMENTS: return ((Map.Entry)this.iterator.next()).getValue(); }
/* 155 */        return null;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object remove(Object paramObject) {
/* 163 */     Object object1 = null;
/* 164 */     for (int i = this.tables.length - 1; i >= 0; i--) {
/* 165 */       if (this.tables[i] != null) {
/* 166 */         Object object = this.tables[i].remove(paramObject);
/* 167 */         if (object != null) {
/* 168 */           object1 = object;
/*     */         }
/*     */       } 
/*     */     } 
/* 172 */     Object object2 = super.remove(paramObject);
/* 173 */     if (object2 != null) {
/* 174 */       object1 = object2;
/*     */     }
/*     */     
/* 177 */     return object1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 182 */     super.clear();
/* 183 */     for (UIDefaults uIDefaults : this.tables) {
/* 184 */       if (uIDefaults != null) {
/* 185 */         uIDefaults.clear();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized String toString() {
/* 192 */     StringBuffer stringBuffer = new StringBuffer();
/* 193 */     stringBuffer.append("{");
/* 194 */     Enumeration<Object> enumeration = keys();
/* 195 */     while (enumeration.hasMoreElements()) {
/* 196 */       Object object = enumeration.nextElement();
/* 197 */       stringBuffer.append(object + "=" + get(object) + ", ");
/*     */     } 
/* 199 */     int i = stringBuffer.length();
/* 200 */     if (i > 1) {
/* 201 */       stringBuffer.delete(i - 2, i);
/*     */     }
/* 203 */     stringBuffer.append("}");
/* 204 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/MultiUIDefaults.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */