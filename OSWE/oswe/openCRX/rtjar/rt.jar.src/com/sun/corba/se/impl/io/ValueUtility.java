/*     */ package com.sun.corba.se.impl.io;
/*     */ 
/*     */ import com.sun.corba.se.impl.util.RepositoryId;
/*     */ import com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescription;
/*     */ import com.sun.org.omg.CORBA._IDLTypeStub;
/*     */ import com.sun.org.omg.SendingContext.CodeBase;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.rmi.Remote;
/*     */ import java.util.Stack;
/*     */ import javax.rmi.CORBA.ValueHandler;
/*     */ import org.omg.CORBA.IDLType;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.TCKind;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.ValueMember;
/*     */ import sun.corba.JavaCorbaAccess;
/*     */ import sun.corba.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValueUtility
/*     */ {
/*     */   public static final short PRIVATE_MEMBER = 0;
/*     */   public static final short PUBLIC_MEMBER = 1;
/*  60 */   private static final String[] primitiveConstants = new String[] { null, null, "S", "I", "S", "I", "F", "D", "Z", "C", "B", null, null, null, null, null, null, null, null, null, null, null, null, "J", "J", "D", "C", null, null, null, null, null, null };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  97 */     SharedSecrets.setJavaCorbaAccess(new JavaCorbaAccess() {
/*     */           public ValueHandlerImpl newValueHandlerImpl() {
/*  99 */             return ValueHandlerImpl.getInstance();
/*     */           }
/*     */           public Class<?> loadClass(String param1String) throws ClassNotFoundException {
/* 102 */             if (Thread.currentThread().getContextClassLoader() != null) {
/* 103 */               return Thread.currentThread().getContextClassLoader()
/* 104 */                 .loadClass(param1String);
/*     */             }
/* 106 */             return ClassLoader.getSystemClassLoader().loadClass(param1String);
/*     */           }
/*     */         });
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
/*     */   public static String getSignature(ValueMember paramValueMember) throws ClassNotFoundException {
/* 120 */     if (paramValueMember.type.kind().value() == 30 || paramValueMember.type
/* 121 */       .kind().value() == 29 || paramValueMember.type
/* 122 */       .kind().value() == 14) {
/* 123 */       Class<?> clazz = RepositoryId.cache.getId(paramValueMember.id).getClassFromType();
/* 124 */       return ObjectStreamClass.getSignature(clazz);
/*     */     } 
/*     */ 
/*     */     
/* 128 */     return primitiveConstants[paramValueMember.type.kind().value()];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FullValueDescription translate(ORB paramORB, ObjectStreamClass paramObjectStreamClass, ValueHandler paramValueHandler) {
/* 136 */     FullValueDescription fullValueDescription = new FullValueDescription();
/* 137 */     Class<?> clazz1 = paramObjectStreamClass.forClass();
/*     */     
/* 139 */     ValueHandlerImpl valueHandlerImpl = (ValueHandlerImpl)paramValueHandler;
/* 140 */     String str = valueHandlerImpl.createForAnyType(clazz1);
/*     */ 
/*     */     
/* 143 */     fullValueDescription.name = valueHandlerImpl.getUnqualifiedName(str);
/* 144 */     if (fullValueDescription.name == null) {
/* 145 */       fullValueDescription.name = "";
/*     */     }
/*     */     
/* 148 */     fullValueDescription.id = valueHandlerImpl.getRMIRepositoryID(clazz1);
/* 149 */     if (fullValueDescription.id == null) {
/* 150 */       fullValueDescription.id = "";
/*     */     }
/*     */     
/* 153 */     fullValueDescription.is_abstract = ObjectStreamClassCorbaExt.isAbstractInterface(clazz1);
/*     */ 
/*     */     
/* 156 */     fullValueDescription.is_custom = (paramObjectStreamClass.hasWriteObject() || paramObjectStreamClass.isExternalizable());
/*     */ 
/*     */     
/* 159 */     fullValueDescription.defined_in = valueHandlerImpl.getDefinedInId(str);
/* 160 */     if (fullValueDescription.defined_in == null) {
/* 161 */       fullValueDescription.defined_in = "";
/*     */     }
/*     */     
/* 164 */     fullValueDescription.version = valueHandlerImpl.getSerialVersionUID(str);
/* 165 */     if (fullValueDescription.version == null) {
/* 166 */       fullValueDescription.version = "";
/*     */     }
/*     */     
/* 169 */     fullValueDescription.operations = new com.sun.org.omg.CORBA.OperationDescription[0];
/*     */ 
/*     */     
/* 172 */     fullValueDescription.attributes = new com.sun.org.omg.CORBA.AttributeDescription[0];
/*     */ 
/*     */ 
/*     */     
/* 176 */     IdentityKeyValueStack identityKeyValueStack = new IdentityKeyValueStack();
/*     */     
/* 178 */     fullValueDescription.members = translateMembers(paramORB, paramObjectStreamClass, paramValueHandler, identityKeyValueStack);
/*     */ 
/*     */     
/* 181 */     fullValueDescription.initializers = new com.sun.org.omg.CORBA.Initializer[0];
/*     */     
/* 183 */     Class[] arrayOfClass = paramObjectStreamClass.forClass().getInterfaces();
/* 184 */     byte b1 = 0;
/*     */ 
/*     */     
/* 187 */     fullValueDescription.supported_interfaces = new String[arrayOfClass.length]; byte b2;
/* 188 */     for (b2 = 0; b2 < arrayOfClass.length; 
/* 189 */       b2++) {
/* 190 */       fullValueDescription.supported_interfaces[b2] = valueHandlerImpl
/* 191 */         .createForAnyType(arrayOfClass[b2]);
/*     */       
/* 193 */       if (!Remote.class.isAssignableFrom(arrayOfClass[b2]) || 
/* 194 */         !Modifier.isPublic(arrayOfClass[b2].getModifiers())) {
/* 195 */         b1++;
/*     */       }
/*     */     } 
/*     */     
/* 199 */     fullValueDescription.abstract_base_values = new String[b1];
/* 200 */     for (b2 = 0; b2 < arrayOfClass.length; 
/* 201 */       b2++) {
/* 202 */       if (!Remote.class.isAssignableFrom(arrayOfClass[b2]) || 
/* 203 */         !Modifier.isPublic(arrayOfClass[b2].getModifiers())) {
/* 204 */         fullValueDescription.abstract_base_values[b2] = valueHandlerImpl
/* 205 */           .createForAnyType(arrayOfClass[b2]);
/*     */       }
/*     */     } 
/*     */     
/* 209 */     fullValueDescription.is_truncatable = false;
/*     */ 
/*     */     
/* 212 */     Class<?> clazz2 = paramObjectStreamClass.forClass().getSuperclass();
/* 213 */     if (Serializable.class.isAssignableFrom(clazz2)) {
/* 214 */       fullValueDescription.base_value = valueHandlerImpl.getRMIRepositoryID(clazz2);
/*     */     } else {
/* 216 */       fullValueDescription.base_value = "";
/*     */     } 
/*     */ 
/*     */     
/* 220 */     fullValueDescription.type = paramORB.get_primitive_tc(TCKind.tk_value);
/*     */     
/* 222 */     return fullValueDescription;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ValueMember[] translateMembers(ORB paramORB, ObjectStreamClass paramObjectStreamClass, ValueHandler paramValueHandler, IdentityKeyValueStack paramIdentityKeyValueStack) {
/* 231 */     ValueHandlerImpl valueHandlerImpl = (ValueHandlerImpl)paramValueHandler;
/* 232 */     ObjectStreamField[] arrayOfObjectStreamField = paramObjectStreamClass.getFields();
/* 233 */     int i = arrayOfObjectStreamField.length;
/* 234 */     ValueMember[] arrayOfValueMember = new ValueMember[i];
/*     */ 
/*     */     
/* 237 */     for (byte b = 0; b < i; b++) {
/* 238 */       String str = valueHandlerImpl.getRMIRepositoryID(arrayOfObjectStreamField[b].getClazz());
/* 239 */       arrayOfValueMember[b] = new ValueMember();
/* 240 */       (arrayOfValueMember[b]).name = arrayOfObjectStreamField[b].getName();
/* 241 */       (arrayOfValueMember[b]).id = str;
/* 242 */       (arrayOfValueMember[b]).defined_in = valueHandlerImpl.getDefinedInId(str);
/* 243 */       (arrayOfValueMember[b]).version = "1.0";
/* 244 */       (arrayOfValueMember[b]).type_def = (IDLType)new _IDLTypeStub();
/*     */       
/* 246 */       if (arrayOfObjectStreamField[b].getField() == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 252 */         (arrayOfValueMember[b]).access = 0;
/*     */       } else {
/* 254 */         int j = arrayOfObjectStreamField[b].getField().getModifiers();
/* 255 */         if (Modifier.isPublic(j)) {
/* 256 */           (arrayOfValueMember[b]).access = 1;
/*     */         } else {
/* 258 */           (arrayOfValueMember[b]).access = 0;
/*     */         } 
/*     */       } 
/* 261 */       switch (arrayOfObjectStreamField[b].getTypeCode()) {
/*     */         case 'B':
/* 263 */           (arrayOfValueMember[b]).type = paramORB.get_primitive_tc(TCKind.tk_octet);
/*     */           break;
/*     */         case 'C':
/* 266 */           (arrayOfValueMember[b])
/* 267 */             .type = paramORB.get_primitive_tc(valueHandlerImpl.getJavaCharTCKind());
/*     */           break;
/*     */         case 'F':
/* 270 */           (arrayOfValueMember[b]).type = paramORB.get_primitive_tc(TCKind.tk_float);
/*     */           break;
/*     */         case 'D':
/* 273 */           (arrayOfValueMember[b]).type = paramORB.get_primitive_tc(TCKind.tk_double);
/*     */           break;
/*     */         case 'I':
/* 276 */           (arrayOfValueMember[b]).type = paramORB.get_primitive_tc(TCKind.tk_long);
/*     */           break;
/*     */         case 'J':
/* 279 */           (arrayOfValueMember[b]).type = paramORB.get_primitive_tc(TCKind.tk_longlong);
/*     */           break;
/*     */         case 'S':
/* 282 */           (arrayOfValueMember[b]).type = paramORB.get_primitive_tc(TCKind.tk_short);
/*     */           break;
/*     */         case 'Z':
/* 285 */           (arrayOfValueMember[b]).type = paramORB.get_primitive_tc(TCKind.tk_boolean);
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         default:
/* 292 */           (arrayOfValueMember[b]).type = createTypeCodeForClassInternal(paramORB, arrayOfObjectStreamField[b].getClazz(), (ValueHandler)valueHandlerImpl, paramIdentityKeyValueStack);
/*     */           
/* 294 */           (arrayOfValueMember[b]).id = valueHandlerImpl.createForAnyType(arrayOfObjectStreamField[b].getType());
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 300 */     return arrayOfValueMember;
/*     */   }
/*     */   
/*     */   private static boolean exists(String paramString, String[] paramArrayOfString) {
/* 304 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 305 */       if (paramString.equals(paramArrayOfString[b]))
/* 306 */         return true; 
/*     */     } 
/* 308 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAssignableFrom(String paramString, FullValueDescription paramFullValueDescription, CodeBase paramCodeBase) {
/* 314 */     if (exists(paramString, paramFullValueDescription.supported_interfaces)) {
/* 315 */       return true;
/*     */     }
/* 317 */     if (paramString.equals(paramFullValueDescription.id)) {
/* 318 */       return true;
/*     */     }
/* 320 */     if (paramFullValueDescription.base_value != null && 
/* 321 */       !paramFullValueDescription.base_value.equals("")) {
/* 322 */       FullValueDescription fullValueDescription = paramCodeBase.meta(paramFullValueDescription.base_value);
/*     */       
/* 324 */       return isAssignableFrom(paramString, fullValueDescription, paramCodeBase);
/*     */     } 
/*     */     
/* 327 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeCode createTypeCodeForClass(ORB paramORB, Class paramClass, ValueHandler paramValueHandler) {
/* 333 */     IdentityKeyValueStack identityKeyValueStack = new IdentityKeyValueStack();
/*     */     
/* 335 */     return createTypeCodeForClassInternal(paramORB, paramClass, paramValueHandler, identityKeyValueStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static TypeCode createTypeCodeForClassInternal(ORB paramORB, Class paramClass, ValueHandler paramValueHandler, IdentityKeyValueStack paramIdentityKeyValueStack) {
/* 345 */     TypeCode typeCode = null;
/* 346 */     String str = (String)paramIdentityKeyValueStack.get(paramClass);
/* 347 */     if (str != null) {
/* 348 */       return paramORB.create_recursive_tc(str);
/*     */     }
/* 350 */     str = paramValueHandler.getRMIRepositoryID(paramClass);
/* 351 */     if (str == null) str = "";
/*     */ 
/*     */     
/* 354 */     paramIdentityKeyValueStack.push(paramClass, str);
/* 355 */     typeCode = createTypeCodeInternal(paramORB, paramClass, paramValueHandler, str, paramIdentityKeyValueStack);
/* 356 */     paramIdentityKeyValueStack.pop();
/* 357 */     return typeCode;
/*     */   }
/*     */   
/*     */   private static class IdentityKeyValueStack
/*     */   {
/*     */     private static class KeyValuePair {
/*     */       Object key;
/*     */       Object value;
/*     */       
/*     */       KeyValuePair(Object param2Object1, Object param2Object2) {
/* 367 */         this.key = param2Object1;
/* 368 */         this.value = param2Object2;
/*     */       }
/*     */       boolean equals(KeyValuePair param2KeyValuePair) {
/* 371 */         return (param2KeyValuePair.key == this.key);
/*     */       }
/*     */     }
/*     */     
/* 375 */     Stack pairs = null;
/*     */     
/*     */     Object get(Object param1Object) {
/* 378 */       if (this.pairs == null) {
/* 379 */         return null;
/*     */       }
/* 381 */       for (KeyValuePair keyValuePair : this.pairs) {
/*     */         
/* 383 */         if (keyValuePair.key == param1Object) {
/* 384 */           return keyValuePair.value;
/*     */         }
/*     */       } 
/* 387 */       return null;
/*     */     }
/*     */     
/*     */     void push(Object param1Object1, Object param1Object2) {
/* 391 */       if (this.pairs == null) {
/* 392 */         this.pairs = new Stack();
/*     */       }
/* 394 */       this.pairs.push(new KeyValuePair(param1Object1, param1Object2));
/*     */     }
/*     */     
/*     */     void pop() {
/* 398 */       this.pairs.pop();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private IdentityKeyValueStack() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static TypeCode createTypeCodeInternal(ORB paramORB, Class<String> paramClass, ValueHandler paramValueHandler, String paramString, IdentityKeyValueStack paramIdentityKeyValueStack) {
/* 408 */     if (paramClass.isArray()) {
/*     */       TypeCode typeCode1;
/* 410 */       Class<?> clazz1 = paramClass.getComponentType();
/*     */       
/* 412 */       if (clazz1.isPrimitive()) {
/*     */         
/* 414 */         typeCode1 = getPrimitiveTypeCodeForClass(paramORB, clazz1, paramValueHandler);
/*     */       }
/*     */       else {
/*     */         
/* 418 */         typeCode1 = createTypeCodeForClassInternal(paramORB, clazz1, paramValueHandler, paramIdentityKeyValueStack);
/*     */       } 
/*     */       
/* 421 */       TypeCode typeCode2 = paramORB.create_sequence_tc(0, typeCode1);
/* 422 */       return paramORB.create_value_box_tc(paramString, "Sequence", typeCode2);
/* 423 */     }  if (paramClass == String.class) {
/*     */       
/* 425 */       TypeCode typeCode1 = paramORB.create_string_tc(0);
/* 426 */       return paramORB.create_value_box_tc(paramString, "StringValue", typeCode1);
/* 427 */     }  if (Remote.class.isAssignableFrom(paramClass))
/* 428 */       return paramORB.get_primitive_tc(TCKind.tk_objref); 
/* 429 */     if (Object.class.isAssignableFrom(paramClass)) {
/* 430 */       return paramORB.get_primitive_tc(TCKind.tk_objref);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 435 */     ObjectStreamClass objectStreamClass = ObjectStreamClass.lookup(paramClass);
/*     */     
/* 437 */     if (objectStreamClass == null) {
/* 438 */       return paramORB.create_value_box_tc(paramString, "Value", paramORB.get_primitive_tc(TCKind.tk_value));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 443 */     boolean bool = objectStreamClass.isCustomMarshaled() ? true : false;
/*     */ 
/*     */     
/* 446 */     TypeCode typeCode = null;
/* 447 */     Class<? super String> clazz = paramClass.getSuperclass();
/* 448 */     if (clazz != null && Serializable.class.isAssignableFrom(clazz)) {
/* 449 */       typeCode = createTypeCodeForClassInternal(paramORB, clazz, paramValueHandler, paramIdentityKeyValueStack);
/*     */     }
/*     */ 
/*     */     
/* 453 */     ValueMember[] arrayOfValueMember = translateMembers(paramORB, objectStreamClass, paramValueHandler, paramIdentityKeyValueStack);
/*     */     
/* 455 */     return paramORB.create_value_tc(paramString, paramClass.getName(), bool, typeCode, arrayOfValueMember);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeCode getPrimitiveTypeCodeForClass(ORB paramORB, Class<int> paramClass, ValueHandler paramValueHandler) {
/* 462 */     if (paramClass == int.class)
/* 463 */       return paramORB.get_primitive_tc(TCKind.tk_long); 
/* 464 */     if (paramClass == byte.class)
/* 465 */       return paramORB.get_primitive_tc(TCKind.tk_octet); 
/* 466 */     if (paramClass == long.class)
/* 467 */       return paramORB.get_primitive_tc(TCKind.tk_longlong); 
/* 468 */     if (paramClass == float.class)
/* 469 */       return paramORB.get_primitive_tc(TCKind.tk_float); 
/* 470 */     if (paramClass == double.class)
/* 471 */       return paramORB.get_primitive_tc(TCKind.tk_double); 
/* 472 */     if (paramClass == short.class)
/* 473 */       return paramORB.get_primitive_tc(TCKind.tk_short); 
/* 474 */     if (paramClass == char.class)
/* 475 */       return paramORB.get_primitive_tc(((ValueHandlerImpl)paramValueHandler).getJavaCharTCKind()); 
/* 476 */     if (paramClass == boolean.class) {
/* 477 */       return paramORB.get_primitive_tc(TCKind.tk_boolean);
/*     */     }
/*     */     
/* 480 */     return paramORB.get_primitive_tc(TCKind.tk_any);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/io/ValueUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */