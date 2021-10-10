/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.RefAddr;
/*     */ import javax.naming.Reference;
/*     */ import javax.naming.Referenceable;
/*     */ import javax.naming.StringRefAddr;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.BasicAttribute;
/*     */ import javax.naming.directory.BasicAttributes;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.directory.InvalidAttributeValueException;
/*     */ import javax.naming.directory.InvalidAttributesException;
/*     */ import javax.naming.spi.DirStateFactory;
/*     */ import javax.naming.spi.DirectoryManager;
/*     */ import sun.misc.BASE64Decoder;
/*     */ import sun.misc.BASE64Encoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Obj
/*     */ {
/*  63 */   static VersionHelper helper = VersionHelper.getVersionHelper();
/*     */ 
/*     */   
/*  66 */   static final String[] JAVA_ATTRIBUTES = new String[] { "objectClass", "javaSerializedData", "javaClassName", "javaFactory", "javaCodeBase", "javaReferenceAddress", "javaClassNames", "javaRemoteLocation" };
/*     */ 
/*     */   
/*     */   static final int OBJECT_CLASS = 0;
/*     */ 
/*     */   
/*     */   static final int SERIALIZED_DATA = 1;
/*     */ 
/*     */   
/*     */   static final int CLASSNAME = 2;
/*     */ 
/*     */   
/*     */   static final int FACTORY = 3;
/*     */ 
/*     */   
/*     */   static final int CODEBASE = 4;
/*     */ 
/*     */   
/*     */   static final int REF_ADDR = 5;
/*     */   
/*     */   static final int TYPENAME = 6;
/*     */   
/*     */   @Deprecated
/*     */   private static final int REMOTE_LOC = 7;
/*     */   
/*  91 */   static final String[] JAVA_OBJECT_CLASSES = new String[] { "javaContainer", "javaObject", "javaNamingReference", "javaSerializedObject", "javaMarshalledObject" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   static final String[] JAVA_OBJECT_CLASSES_LOWER = new String[] { "javacontainer", "javaobject", "javanamingreference", "javaserializedobject", "javamarshalledobject" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int STRUCTURAL = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int BASE_OBJECT = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int REF_OBJECT = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int SER_OBJECT = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int MAR_OBJECT = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Attributes encodeObject(char paramChar, Object paramObject, Attributes paramAttributes, Attribute paramAttribute, boolean paramBoolean) throws NamingException {
/* 135 */     boolean bool = (paramAttribute.size() == 0 || (paramAttribute.size() == 1 && paramAttribute.contains("top"))) ? true : false;
/*     */     
/* 137 */     if (bool) {
/* 138 */       paramAttribute.add(JAVA_OBJECT_CLASSES[0]);
/*     */     }
/*     */ 
/*     */     
/* 142 */     if (paramObject instanceof Referenceable) {
/* 143 */       paramAttribute.add(JAVA_OBJECT_CLASSES[1]);
/* 144 */       paramAttribute.add(JAVA_OBJECT_CLASSES[2]);
/* 145 */       if (!paramBoolean) {
/* 146 */         paramAttributes = (Attributes)paramAttributes.clone();
/*     */       }
/* 148 */       paramAttributes.put(paramAttribute);
/* 149 */       return encodeReference(paramChar, ((Referenceable)paramObject)
/* 150 */           .getReference(), paramAttributes, paramObject);
/*     */     } 
/*     */     
/* 153 */     if (paramObject instanceof Reference) {
/* 154 */       paramAttribute.add(JAVA_OBJECT_CLASSES[1]);
/* 155 */       paramAttribute.add(JAVA_OBJECT_CLASSES[2]);
/* 156 */       if (!paramBoolean) {
/* 157 */         paramAttributes = (Attributes)paramAttributes.clone();
/*     */       }
/* 159 */       paramAttributes.put(paramAttribute);
/* 160 */       return encodeReference(paramChar, (Reference)paramObject, paramAttributes, null);
/*     */     } 
/*     */     
/* 163 */     if (paramObject instanceof java.io.Serializable) {
/* 164 */       paramAttribute.add(JAVA_OBJECT_CLASSES[1]);
/* 165 */       if (!paramAttribute.contains(JAVA_OBJECT_CLASSES[4]) && 
/* 166 */         !paramAttribute.contains(JAVA_OBJECT_CLASSES_LOWER[4])) {
/* 167 */         paramAttribute.add(JAVA_OBJECT_CLASSES[3]);
/*     */       }
/* 169 */       if (!paramBoolean) {
/* 170 */         paramAttributes = (Attributes)paramAttributes.clone();
/*     */       }
/* 172 */       paramAttributes.put(paramAttribute);
/* 173 */       paramAttributes.put(new BasicAttribute(JAVA_ATTRIBUTES[1], 
/* 174 */             serializeObject(paramObject)));
/* 175 */       if (paramAttributes.get(JAVA_ATTRIBUTES[2]) == null) {
/* 176 */         paramAttributes.put(JAVA_ATTRIBUTES[2], paramObject
/* 177 */             .getClass().getName());
/*     */       }
/* 179 */       if (paramAttributes.get(JAVA_ATTRIBUTES[6]) == null)
/*     */       {
/* 181 */         Attribute attribute = LdapCtxFactory.createTypeNameAttr(paramObject.getClass());
/* 182 */         if (attribute != null) {
/* 183 */           paramAttributes.put(attribute);
/*     */         }
/*     */       }
/*     */     
/* 187 */     } else if (!(paramObject instanceof DirContext)) {
/*     */ 
/*     */       
/* 190 */       throw new IllegalArgumentException("can only bind Referenceable, Serializable, DirContext");
/*     */     } 
/*     */ 
/*     */     
/* 194 */     return paramAttributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] getCodebases(Attribute paramAttribute) throws NamingException {
/* 205 */     if (paramAttribute == null) {
/* 206 */       return null;
/*     */     }
/*     */     
/* 209 */     StringTokenizer stringTokenizer = new StringTokenizer((String)paramAttribute.get());
/* 210 */     Vector<String> vector = new Vector(10);
/* 211 */     while (stringTokenizer.hasMoreTokens()) {
/* 212 */       vector.addElement(stringTokenizer.nextToken());
/*     */     }
/* 214 */     String[] arrayOfString = new String[vector.size()];
/* 215 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 216 */       arrayOfString[b] = vector.elementAt(b);
/*     */     }
/* 218 */     return arrayOfString;
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
/*     */   static Object decodeObject(Attributes paramAttributes) throws NamingException {
/* 235 */     String[] arrayOfString = getCodebases(paramAttributes.get(JAVA_ATTRIBUTES[4])); try {
/*     */       Attribute attribute;
/* 237 */       if ((attribute = paramAttributes.get(JAVA_ATTRIBUTES[1])) != null) {
/* 238 */         ClassLoader classLoader = helper.getURLClassLoader(arrayOfString);
/* 239 */         return deserializeObject((byte[])attribute.get(), classLoader);
/* 240 */       }  if ((attribute = paramAttributes.get(JAVA_ATTRIBUTES[7])) != null)
/*     */       {
/* 242 */         return decodeRmiObject((String)paramAttributes
/* 243 */             .get(JAVA_ATTRIBUTES[2]).get(), (String)attribute
/* 244 */             .get(), arrayOfString);
/*     */       }
/*     */       
/* 247 */       attribute = paramAttributes.get(JAVA_ATTRIBUTES[0]);
/* 248 */       if (attribute != null && (attribute
/* 249 */         .contains(JAVA_OBJECT_CLASSES[2]) || attribute
/* 250 */         .contains(JAVA_OBJECT_CLASSES_LOWER[2]))) {
/* 251 */         return decodeReference(paramAttributes, arrayOfString);
/*     */       }
/* 253 */       return null;
/* 254 */     } catch (IOException iOException) {
/* 255 */       NamingException namingException = new NamingException();
/* 256 */       namingException.setRootCause(iOException);
/* 257 */       throw namingException;
/*     */     } 
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
/*     */   private static Attributes encodeReference(char paramChar, Reference paramReference, Attributes paramAttributes, Object paramObject) throws NamingException {
/* 294 */     if (paramReference == null) {
/* 295 */       return paramAttributes;
/*     */     }
/*     */     
/*     */     String str;
/* 299 */     if ((str = paramReference.getClassName()) != null) {
/* 300 */       paramAttributes.put(new BasicAttribute(JAVA_ATTRIBUTES[2], str));
/*     */     }
/*     */     
/* 303 */     if ((str = paramReference.getFactoryClassName()) != null) {
/* 304 */       paramAttributes.put(new BasicAttribute(JAVA_ATTRIBUTES[3], str));
/*     */     }
/*     */     
/* 307 */     if ((str = paramReference.getFactoryClassLocation()) != null) {
/* 308 */       paramAttributes.put(new BasicAttribute(JAVA_ATTRIBUTES[4], str));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 313 */     if (paramObject != null && paramAttributes.get(JAVA_ATTRIBUTES[6]) != null) {
/*     */       
/* 315 */       Attribute attribute = LdapCtxFactory.createTypeNameAttr(paramObject.getClass());
/* 316 */       if (attribute != null) {
/* 317 */         paramAttributes.put(attribute);
/*     */       }
/*     */     } 
/*     */     
/* 321 */     int i = paramReference.size();
/*     */     
/* 323 */     if (i > 0) {
/*     */       
/* 325 */       BasicAttribute basicAttribute = new BasicAttribute(JAVA_ATTRIBUTES[5]);
/*     */       
/* 327 */       BASE64Encoder bASE64Encoder = null;
/*     */       
/* 329 */       for (byte b = 0; b < i; b++) {
/* 330 */         RefAddr refAddr = paramReference.get(b);
/*     */         
/* 332 */         if (refAddr instanceof StringRefAddr) {
/* 333 */           basicAttribute.add("" + paramChar + b + paramChar + refAddr
/* 334 */               .getType() + paramChar + refAddr
/* 335 */               .getContent());
/*     */         } else {
/* 337 */           if (bASE64Encoder == null) {
/* 338 */             bASE64Encoder = new BASE64Encoder();
/*     */           }
/* 340 */           basicAttribute.add("" + paramChar + b + paramChar + refAddr
/* 341 */               .getType() + paramChar + paramChar + bASE64Encoder
/*     */               
/* 343 */               .encodeBuffer(serializeObject(refAddr)));
/*     */         } 
/*     */       } 
/* 346 */       paramAttributes.put(basicAttribute);
/*     */     } 
/* 348 */     return paramAttributes;
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
/*     */   private static Object decodeRmiObject(String paramString1, String paramString2, String[] paramArrayOfString) throws NamingException {
/* 367 */     return new Reference(paramString1, new StringRefAddr("URL", paramString2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Reference decodeReference(Attributes paramAttributes, String[] paramArrayOfString) throws NamingException, IOException {
/* 378 */     String str1, str2 = null;
/*     */     Attribute attribute;
/* 380 */     if ((attribute = paramAttributes.get(JAVA_ATTRIBUTES[2])) != null) {
/* 381 */       str1 = (String)attribute.get();
/*     */     } else {
/* 383 */       throw new InvalidAttributesException(JAVA_ATTRIBUTES[2] + " attribute is required");
/*     */     } 
/*     */ 
/*     */     
/* 387 */     if ((attribute = paramAttributes.get(JAVA_ATTRIBUTES[3])) != null) {
/* 388 */       str2 = (String)attribute.get();
/*     */     }
/*     */     
/* 391 */     Reference reference = new Reference(str1, str2, (paramArrayOfString != null) ? paramArrayOfString[0] : null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 401 */     if ((attribute = paramAttributes.get(JAVA_ATTRIBUTES[5])) != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 406 */       BASE64Decoder bASE64Decoder = null;
/*     */       
/* 408 */       ClassLoader classLoader = helper.getURLClassLoader(paramArrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 414 */       Vector<StringRefAddr> vector = new Vector();
/* 415 */       vector.setSize(attribute.size());
/*     */       
/* 417 */       for (NamingEnumeration<?> namingEnumeration = attribute.getAll(); namingEnumeration.hasMore(); ) {
/*     */         int k;
/* 419 */         String str3 = (String)namingEnumeration.next();
/*     */         
/* 421 */         if (str3.length() == 0) {
/* 422 */           throw new InvalidAttributeValueException("malformed " + JAVA_ATTRIBUTES[5] + " attribute - empty attribute value");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 427 */         char c = str3.charAt(0);
/* 428 */         int i = 1;
/*     */         
/*     */         int j;
/* 431 */         if ((j = str3.indexOf(c, i)) < 0) {
/* 432 */           throw new InvalidAttributeValueException("malformed " + JAVA_ATTRIBUTES[5] + " attribute - separator '" + c + "'not found");
/*     */         }
/*     */         
/*     */         String str4;
/* 436 */         if ((str4 = str3.substring(i, j)) == null) {
/* 437 */           throw new InvalidAttributeValueException("malformed " + JAVA_ATTRIBUTES[5] + " attribute - empty RefAddr position");
/*     */         }
/*     */ 
/*     */         
/*     */         try {
/* 442 */           k = Integer.parseInt(str4);
/* 443 */         } catch (NumberFormatException numberFormatException) {
/* 444 */           throw new InvalidAttributeValueException("malformed " + JAVA_ATTRIBUTES[5] + " attribute - RefAddr position not an integer");
/*     */         } 
/*     */ 
/*     */         
/* 448 */         i = j + 1;
/*     */ 
/*     */         
/* 451 */         if ((j = str3.indexOf(c, i)) < 0) {
/* 452 */           throw new InvalidAttributeValueException("malformed " + JAVA_ATTRIBUTES[5] + " attribute - RefAddr type not found");
/*     */         }
/*     */         
/*     */         String str5;
/* 456 */         if ((str5 = str3.substring(i, j)) == null) {
/* 457 */           throw new InvalidAttributeValueException("malformed " + JAVA_ATTRIBUTES[5] + " attribute - empty RefAddr type");
/*     */         }
/*     */ 
/*     */         
/* 461 */         i = j + 1;
/*     */ 
/*     */         
/* 464 */         if (i == str3.length()) {
/*     */           
/* 466 */           vector.setElementAt(new StringRefAddr(str5, null), k); continue;
/* 467 */         }  if (str3.charAt(i) == c) {
/*     */ 
/*     */ 
/*     */           
/* 471 */           i++;
/*     */ 
/*     */           
/* 474 */           if (bASE64Decoder == null) {
/* 475 */             bASE64Decoder = new BASE64Decoder();
/*     */           }
/*     */           
/* 478 */           RefAddr refAddr = (RefAddr)deserializeObject(bASE64Decoder
/* 479 */               .decodeBuffer(str3.substring(i)), classLoader);
/*     */ 
/*     */           
/* 482 */           vector.setElementAt(refAddr, k);
/*     */           continue;
/*     */         } 
/* 485 */         vector.setElementAt(new StringRefAddr(str5, str3
/* 486 */               .substring(i)), k);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 491 */       for (byte b = 0; b < vector.size(); b++) {
/* 492 */         reference.add(vector.elementAt(b));
/*     */       }
/*     */     } 
/*     */     
/* 496 */     return reference;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] serializeObject(Object paramObject) throws NamingException {
/*     */     try {
/* 505 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 506 */       try (ObjectOutputStream null = new ObjectOutputStream(byteArrayOutputStream)) {
/* 507 */         objectOutputStream.writeObject(paramObject);
/*     */       } 
/*     */       
/* 510 */       return byteArrayOutputStream.toByteArray();
/*     */     }
/* 512 */     catch (IOException iOException) {
/* 513 */       NamingException namingException = new NamingException();
/* 514 */       namingException.setRootCause(iOException);
/* 515 */       throw namingException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object deserializeObject(byte[] paramArrayOfbyte, ClassLoader paramClassLoader) throws NamingException {
/*     */     try {
/* 527 */       ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte);
/* 528 */       try (ObjectInputStream null = (paramClassLoader == null) ? new ObjectInputStream(byteArrayInputStream) : new LoaderInputStream(byteArrayInputStream, paramClassLoader)) {
/*     */ 
/*     */         
/* 531 */         return objectInputStream.readObject();
/* 532 */       } catch (ClassNotFoundException classNotFoundException) {
/* 533 */         NamingException namingException = new NamingException();
/* 534 */         namingException.setRootCause(classNotFoundException);
/* 535 */         throw namingException;
/*     */       } 
/* 537 */     } catch (IOException iOException) {
/* 538 */       NamingException namingException = new NamingException();
/* 539 */       namingException.setRootCause(iOException);
/* 540 */       throw namingException;
/*     */     } 
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
/*     */   static Attributes determineBindAttrs(char paramChar, Object paramObject, Attributes paramAttributes, boolean paramBoolean, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws NamingException {
/*     */     Attribute attribute;
/* 554 */     DirStateFactory.Result result = DirectoryManager.getStateToBind(paramObject, paramName, paramContext, paramHashtable, paramAttributes);
/* 555 */     paramObject = result.getObject();
/* 556 */     paramAttributes = result.getAttributes();
/*     */ 
/*     */     
/* 559 */     if (paramObject == null) {
/* 560 */       return paramAttributes;
/*     */     }
/*     */ 
/*     */     
/* 564 */     if (paramAttributes == null && paramObject instanceof DirContext) {
/* 565 */       paramBoolean = true;
/* 566 */       paramAttributes = ((DirContext)paramObject).getAttributes("");
/*     */     } 
/*     */     
/* 569 */     boolean bool = false;
/*     */ 
/*     */ 
/*     */     
/* 573 */     if (paramAttributes == null || paramAttributes.size() == 0) {
/* 574 */       paramAttributes = new BasicAttributes(true);
/* 575 */       paramBoolean = true;
/*     */ 
/*     */       
/* 578 */       attribute = new BasicAttribute("objectClass", "top");
/*     */     }
/*     */     else {
/*     */       
/* 582 */       attribute = paramAttributes.get("objectClass");
/* 583 */       if (attribute == null && !paramAttributes.isCaseIgnored())
/*     */       {
/* 585 */         attribute = paramAttributes.get("objectclass");
/*     */       }
/*     */ 
/*     */       
/* 589 */       if (attribute == null) {
/* 590 */         attribute = new BasicAttribute("objectClass", "top");
/* 591 */       } else if (bool || !paramBoolean) {
/* 592 */         attribute = (Attribute)attribute.clone();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 597 */     paramAttributes = encodeObject(paramChar, paramObject, paramAttributes, attribute, paramBoolean);
/*     */ 
/*     */     
/* 600 */     return paramAttributes;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class LoaderInputStream
/*     */     extends ObjectInputStream
/*     */   {
/*     */     private ClassLoader classLoader;
/*     */     
/*     */     LoaderInputStream(InputStream param1InputStream, ClassLoader param1ClassLoader) throws IOException {
/* 610 */       super(param1InputStream);
/* 611 */       this.classLoader = param1ClassLoader;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Class<?> resolveClass(ObjectStreamClass param1ObjectStreamClass) throws IOException, ClassNotFoundException {
/*     */       try {
/* 619 */         return this.classLoader.loadClass(param1ObjectStreamClass.getName());
/* 620 */       } catch (ClassNotFoundException classNotFoundException) {
/* 621 */         return super.resolveClass(param1ObjectStreamClass);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected Class<?> resolveProxyClass(String[] param1ArrayOfString) throws IOException, ClassNotFoundException {
/* 627 */       ClassLoader classLoader = null;
/* 628 */       boolean bool = false;
/*     */ 
/*     */       
/* 631 */       Class[] arrayOfClass = new Class[param1ArrayOfString.length];
/* 632 */       for (byte b = 0; b < param1ArrayOfString.length; b++) {
/* 633 */         Class<?> clazz = Class.forName(param1ArrayOfString[b], false, this.classLoader);
/* 634 */         if ((clazz.getModifiers() & 0x1) == 0) {
/* 635 */           if (bool) {
/* 636 */             if (classLoader != clazz.getClassLoader()) {
/* 637 */               throw new IllegalAccessError("conflicting non-public interface class loaders");
/*     */             }
/*     */           } else {
/*     */             
/* 641 */             classLoader = clazz.getClassLoader();
/* 642 */             bool = true;
/*     */           } 
/*     */         }
/* 645 */         arrayOfClass[b] = clazz;
/*     */       } 
/*     */       try {
/* 648 */         return Proxy.getProxyClass(bool ? classLoader : this.classLoader, arrayOfClass);
/*     */       }
/* 650 */       catch (IllegalArgumentException illegalArgumentException) {
/* 651 */         throw new ClassNotFoundException(null, illegalArgumentException);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/Obj.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */