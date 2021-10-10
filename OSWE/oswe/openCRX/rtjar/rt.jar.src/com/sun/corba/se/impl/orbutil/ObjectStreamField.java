/*     */ package com.sun.corba.se.impl.orbutil;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ObjectStreamField
/*     */   implements Comparable
/*     */ {
/*     */   private String name;
/*     */   private char type;
/*     */   private Field field;
/*     */   private String typeString;
/*     */   private Class clazz;
/*     */   private String signature;
/*     */   private long fieldID;
/*     */   
/*     */   ObjectStreamField(Field paramField) {
/*     */     this(paramField.getName(), paramField.getType());
/*     */     this.field = paramField;
/*     */   }
/*     */   
/*     */   ObjectStreamField(String paramString, Class<int> paramClass) {
/* 268 */     this.fieldID = -1L; this.name = paramString; this.clazz = paramClass; if (paramClass.isPrimitive()) { if (paramClass == int.class) { this.type = 'I'; } else if (paramClass == byte.class) { this.type = 'B'; } else if (paramClass == long.class) { this.type = 'J'; } else if (paramClass == float.class) { this.type = 'F'; } else if (paramClass == double.class) { this.type = 'D'; } else if (paramClass == short.class) { this.type = 'S'; } else if (paramClass == char.class) { this.type = 'C'; } else if (paramClass == boolean.class) { this.type = 'Z'; }  } else if (paramClass.isArray()) { this.type = '['; this.typeString = ObjectStreamClass_1_3_1.getSignature(paramClass); } else { this.type = 'L'; this.typeString = ObjectStreamClass_1_3_1.getSignature(paramClass); }  if (this.typeString != null) { this.signature = this.typeString; } else { this.signature = String.valueOf(this.type); }  } ObjectStreamField(String paramString1, char paramChar, Field paramField, String paramString2) { this.fieldID = -1L; this.name = paramString1; this.type = paramChar; this.field = paramField; this.typeString = paramString2; if (this.typeString != null) { this.signature = this.typeString; } else { this.signature = String.valueOf(this.type); }  } public String getName() { return this.name; } ObjectStreamField() { this.fieldID = -1L; }
/*     */ 
/*     */   
/*     */   public Class getType() {
/*     */     if (this.clazz != null)
/*     */       return this.clazz; 
/*     */     switch (this.type) {
/*     */       case 'B':
/*     */         this.clazz = byte.class;
/*     */         break;
/*     */       case 'C':
/*     */         this.clazz = char.class;
/*     */         break;
/*     */       case 'S':
/*     */         this.clazz = short.class;
/*     */         break;
/*     */       case 'I':
/*     */         this.clazz = int.class;
/*     */         break;
/*     */       case 'J':
/*     */         this.clazz = long.class;
/*     */         break;
/*     */       case 'F':
/*     */         this.clazz = float.class;
/*     */         break;
/*     */       case 'D':
/*     */         this.clazz = double.class;
/*     */         break;
/*     */       case 'Z':
/*     */         this.clazz = boolean.class;
/*     */         break;
/*     */       case 'L':
/*     */       case '[':
/*     */         this.clazz = Object.class;
/*     */         break;
/*     */     } 
/*     */     return this.clazz;
/*     */   }
/*     */   
/*     */   public char getTypeCode() {
/*     */     return this.type;
/*     */   }
/*     */   
/*     */   public String getTypeString() {
/*     */     return this.typeString;
/*     */   }
/*     */   
/*     */   Field getField() {
/*     */     return this.field;
/*     */   }
/*     */   
/*     */   void setField(Field paramField) {
/*     */     this.field = paramField;
/*     */     this.fieldID = -1L;
/*     */   }
/*     */   
/*     */   public boolean isPrimitive() {
/*     */     return (this.type != '[' && this.type != 'L');
/*     */   }
/*     */   
/*     */   public int compareTo(Object paramObject) {
/*     */     ObjectStreamField objectStreamField = (ObjectStreamField)paramObject;
/*     */     boolean bool1 = (this.typeString == null) ? true : false;
/*     */     boolean bool2 = (objectStreamField.typeString == null) ? true : false;
/*     */     if (bool1 != bool2)
/*     */       return bool1 ? -1 : 1; 
/*     */     return this.name.compareTo(objectStreamField.name);
/*     */   }
/*     */   
/*     */   public boolean typeEquals(ObjectStreamField paramObjectStreamField) {
/*     */     if (paramObjectStreamField == null || this.type != paramObjectStreamField.type)
/*     */       return false; 
/*     */     if (this.typeString == null && paramObjectStreamField.typeString == null)
/*     */       return true; 
/*     */     return ObjectStreamClass_1_3_1.compareClassNames(this.typeString, paramObjectStreamField.typeString, '/');
/*     */   }
/*     */   
/*     */   public String getSignature() {
/*     */     return this.signature;
/*     */   }
/*     */   
/*     */   public String toString() {
/*     */     if (this.typeString != null)
/*     */       return this.typeString + " " + this.name; 
/*     */     return this.type + " " + this.name;
/*     */   }
/*     */   
/*     */   public Class getClazz() {
/*     */     return this.clazz;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/ObjectStreamField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */