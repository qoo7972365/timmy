/*     */ package com.sun.xml.internal.ws.model;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.RuntimeInlineAnnotationReader;
/*     */ import com.sun.xml.internal.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.internal.ws.org.objectweb.asm.AnnotationVisitor;
/*     */ import com.sun.xml.internal.ws.org.objectweb.asm.ClassWriter;
/*     */ import com.sun.xml.internal.ws.org.objectweb.asm.FieldVisitor;
/*     */ import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;
/*     */ import com.sun.xml.internal.ws.org.objectweb.asm.Type;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlMimeType;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.Holder;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrapperBeanGenerator
/*     */ {
/*  58 */   private static final Logger LOGGER = Logger.getLogger(WrapperBeanGenerator.class.getName());
/*     */   
/*  60 */   private static final FieldFactory FIELD_FACTORY = new FieldFactory();
/*     */   
/*  62 */   private static final AbstractWrapperBeanGenerator RUNTIME_GENERATOR = new RuntimeWrapperBeanGenerator((AnnotationReader<Type, Class, ?, Method>)new RuntimeInlineAnnotationReader(), Utils.REFLECTION_NAVIGATOR, FIELD_FACTORY);
/*     */ 
/*     */   
/*     */   private static final class RuntimeWrapperBeanGenerator
/*     */     extends AbstractWrapperBeanGenerator<Type, Class, Method, Field>
/*     */   {
/*     */     protected RuntimeWrapperBeanGenerator(AnnotationReader<Type, Class<?>, ?, Method> annReader, Navigator<Type, Class<?>, ?, Method> nav, AbstractWrapperBeanGenerator.BeanMemberFactory<Type, WrapperBeanGenerator.Field> beanMemberFactory) {
/*  69 */       super(annReader, nav, beanMemberFactory);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Type getSafeType(Type type) {
/*  74 */       return type;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Type getHolderValueType(Type paramType) {
/*  79 */       if (paramType instanceof ParameterizedType) {
/*  80 */         ParameterizedType p = (ParameterizedType)paramType;
/*  81 */         if (p.getRawType().equals(Holder.class)) {
/*  82 */           return p.getActualTypeArguments()[0];
/*     */         }
/*     */       } 
/*  85 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isVoidType(Type type) {
/*  90 */       return (type == void.class);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class FieldFactory
/*     */     implements AbstractWrapperBeanGenerator.BeanMemberFactory<Type, Field> {
/*     */     private FieldFactory() {}
/*     */     
/*     */     public WrapperBeanGenerator.Field createWrapperBeanMember(Type paramType, String paramName, List<Annotation> jaxb) {
/*  99 */       return new WrapperBeanGenerator.Field(paramName, paramType, WrapperBeanGenerator.getASMType(paramType), jaxb);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] createBeanImage(String className, String rootName, String rootNS, String typeName, String typeNS, Collection<Field> fields) throws Exception {
/* 109 */     ClassWriter cw = new ClassWriter(0);
/*     */ 
/*     */     
/* 112 */     cw.visit(49, 33, replaceDotWithSlash(className), null, "java/lang/Object", null);
/*     */     
/* 114 */     AnnotationVisitor root = cw.visitAnnotation("Ljavax/xml/bind/annotation/XmlRootElement;", true);
/* 115 */     root.visit("name", rootName);
/* 116 */     root.visit("namespace", rootNS);
/* 117 */     root.visitEnd();
/*     */     
/* 119 */     AnnotationVisitor type = cw.visitAnnotation("Ljavax/xml/bind/annotation/XmlType;", true);
/* 120 */     type.visit("name", typeName);
/* 121 */     type.visit("namespace", typeNS);
/* 122 */     if (fields.size() > 1) {
/* 123 */       AnnotationVisitor propVisitor = type.visitArray("propOrder");
/* 124 */       for (Field field : fields) {
/* 125 */         propVisitor.visit("propOrder", field.fieldName);
/*     */       }
/* 127 */       propVisitor.visitEnd();
/*     */     } 
/* 129 */     type.visitEnd();
/*     */     
/* 131 */     for (Field field : fields) {
/* 132 */       FieldVisitor fv = cw.visitField(1, field.fieldName, field.asmType.getDescriptor(), field.getSignature(), null);
/*     */       
/* 134 */       for (Annotation ann : field.jaxbAnnotations) {
/* 135 */         if (ann instanceof XmlMimeType) {
/* 136 */           AnnotationVisitor mime = fv.visitAnnotation("Ljavax/xml/bind/annotation/XmlMimeType;", true);
/* 137 */           mime.visit("value", ((XmlMimeType)ann).value());
/* 138 */           mime.visitEnd(); continue;
/* 139 */         }  if (ann instanceof XmlJavaTypeAdapter) {
/* 140 */           AnnotationVisitor ada = fv.visitAnnotation("Ljavax/xml/bind/annotation/adapters/XmlJavaTypeAdapter;", true);
/* 141 */           ada.visit("value", getASMType(((XmlJavaTypeAdapter)ann).value()));
/*     */ 
/*     */           
/* 144 */           ada.visitEnd(); continue;
/* 145 */         }  if (ann instanceof javax.xml.bind.annotation.XmlAttachmentRef) {
/* 146 */           AnnotationVisitor att = fv.visitAnnotation("Ljavax/xml/bind/annotation/XmlAttachmentRef;", true);
/* 147 */           att.visitEnd(); continue;
/* 148 */         }  if (ann instanceof javax.xml.bind.annotation.XmlList) {
/* 149 */           AnnotationVisitor list = fv.visitAnnotation("Ljavax/xml/bind/annotation/XmlList;", true);
/* 150 */           list.visitEnd(); continue;
/* 151 */         }  if (ann instanceof XmlElement) {
/* 152 */           AnnotationVisitor elem = fv.visitAnnotation("Ljavax/xml/bind/annotation/XmlElement;", true);
/* 153 */           XmlElement xmlElem = (XmlElement)ann;
/* 154 */           elem.visit("name", xmlElem.name());
/* 155 */           elem.visit("namespace", xmlElem.namespace());
/* 156 */           if (xmlElem.nillable()) {
/* 157 */             elem.visit("nillable", Boolean.valueOf(true));
/*     */           }
/* 159 */           if (xmlElem.required()) {
/* 160 */             elem.visit("required", Boolean.valueOf(true));
/*     */           }
/* 162 */           elem.visitEnd(); continue;
/*     */         } 
/* 164 */         throw new WebServiceException("Unknown JAXB annotation " + ann);
/*     */       } 
/*     */ 
/*     */       
/* 168 */       fv.visitEnd();
/*     */     } 
/*     */     
/* 171 */     MethodVisitor mv = cw.visitMethod(1, "<init>", "()V", null, null);
/* 172 */     mv.visitCode();
/* 173 */     mv.visitVarInsn(25, 0);
/* 174 */     mv.visitMethodInsn(183, "java/lang/Object", "<init>", "()V");
/* 175 */     mv.visitInsn(177);
/* 176 */     mv.visitMaxs(1, 1);
/* 177 */     mv.visitEnd();
/*     */     
/* 179 */     cw.visitEnd();
/*     */     
/* 181 */     if (LOGGER.isLoggable(Level.FINE)) {
/*     */       
/* 183 */       StringBuilder sb = new StringBuilder();
/* 184 */       sb.append("\n");
/* 185 */       sb.append("@XmlRootElement(name=").append(rootName)
/* 186 */         .append(", namespace=").append(rootNS).append(")");
/*     */ 
/*     */       
/* 189 */       sb.append("\n");
/* 190 */       sb.append("@XmlType(name=").append(typeName)
/* 191 */         .append(", namespace=").append(typeNS);
/* 192 */       if (fields.size() > 1) {
/* 193 */         sb.append(", propOrder={");
/* 194 */         for (Field field : fields) {
/* 195 */           sb.append(" ");
/* 196 */           sb.append(field.fieldName);
/*     */         } 
/* 198 */         sb.append(" }");
/*     */       } 
/* 200 */       sb.append(")");
/*     */ 
/*     */       
/* 203 */       sb.append("\n");
/* 204 */       sb.append("public class ").append(className).append(" {");
/*     */ 
/*     */       
/* 207 */       for (Field field : fields) {
/* 208 */         sb.append("\n");
/*     */ 
/*     */         
/* 211 */         for (Annotation ann : field.jaxbAnnotations) {
/* 212 */           sb.append("\n    ");
/*     */           
/* 214 */           if (ann instanceof XmlMimeType) {
/* 215 */             sb.append("@XmlMimeType(value=").append(((XmlMimeType)ann).value()).append(")"); continue;
/* 216 */           }  if (ann instanceof XmlJavaTypeAdapter) {
/* 217 */             sb.append("@XmlJavaTypeAdapter(value=").append(getASMType(((XmlJavaTypeAdapter)ann).value())).append(")"); continue;
/* 218 */           }  if (ann instanceof javax.xml.bind.annotation.XmlAttachmentRef) {
/* 219 */             sb.append("@XmlAttachmentRef"); continue;
/* 220 */           }  if (ann instanceof javax.xml.bind.annotation.XmlList) {
/* 221 */             sb.append("@XmlList"); continue;
/* 222 */           }  if (ann instanceof XmlElement) {
/* 223 */             XmlElement xmlElem = (XmlElement)ann;
/* 224 */             sb.append("\n    ");
/* 225 */             sb.append("@XmlElement(name=").append(xmlElem.name())
/* 226 */               .append(", namespace=").append(xmlElem.namespace());
/* 227 */             if (xmlElem.nillable()) {
/* 228 */               sb.append(", nillable=true");
/*     */             }
/* 230 */             if (xmlElem.required()) {
/* 231 */               sb.append(", required=true");
/*     */             }
/* 233 */             sb.append(")"); continue;
/*     */           } 
/* 235 */           throw new WebServiceException("Unknown JAXB annotation " + ann);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 240 */         sb.append("\n    ");
/* 241 */         sb.append("public ");
/* 242 */         if (field.getSignature() == null) {
/* 243 */           sb.append(field.asmType.getDescriptor());
/*     */         } else {
/* 245 */           sb.append(field.getSignature());
/*     */         } 
/* 247 */         sb.append(" ");
/* 248 */         sb.append(field.fieldName);
/*     */       } 
/*     */       
/* 251 */       sb.append("\n\n}");
/* 252 */       LOGGER.fine(sb.toString());
/*     */     } 
/*     */     
/* 255 */     return cw.toByteArray();
/*     */   }
/*     */   
/*     */   private static String replaceDotWithSlash(String name) {
/* 259 */     return name.replace('.', '/');
/*     */   }
/*     */   
/*     */   static Class createRequestWrapperBean(String className, Method method, QName reqElemName, ClassLoader cl) {
/*     */     byte[] image;
/* 264 */     if (LOGGER.isLoggable(Level.FINE)) {
/* 265 */       LOGGER.log(Level.FINE, "Request Wrapper Class : {0}", className);
/*     */     }
/*     */     
/* 268 */     List<Field> requestMembers = RUNTIME_GENERATOR.collectRequestBeanMembers(method);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 273 */       image = createBeanImage(className, reqElemName.getLocalPart(), reqElemName.getNamespaceURI(), reqElemName
/* 274 */           .getLocalPart(), reqElemName.getNamespaceURI(), requestMembers);
/*     */     }
/* 276 */     catch (Exception e) {
/* 277 */       throw new WebServiceException(e);
/*     */     } 
/*     */     
/* 280 */     return Injector.inject(cl, className, image);
/*     */   }
/*     */   
/*     */   static Class createResponseWrapperBean(String className, Method method, QName resElemName, ClassLoader cl) {
/*     */     byte[] image;
/* 285 */     if (LOGGER.isLoggable(Level.FINE)) {
/* 286 */       LOGGER.log(Level.FINE, "Response Wrapper Class : {0}", className);
/*     */     }
/*     */     
/* 289 */     List<Field> responseMembers = RUNTIME_GENERATOR.collectResponseBeanMembers(method);
/*     */ 
/*     */     
/*     */     try {
/* 293 */       image = createBeanImage(className, resElemName.getLocalPart(), resElemName.getNamespaceURI(), resElemName
/* 294 */           .getLocalPart(), resElemName.getNamespaceURI(), responseMembers);
/*     */     }
/* 296 */     catch (Exception e) {
/* 297 */       throw new WebServiceException(e);
/*     */     } 
/*     */ 
/*     */     
/* 301 */     return Injector.inject(cl, className, image);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Type getASMType(Type t) {
/* 306 */     assert t != null;
/*     */     
/* 308 */     if (t instanceof Class) {
/* 309 */       return Type.getType((Class)t);
/*     */     }
/*     */     
/* 312 */     if (t instanceof ParameterizedType) {
/* 313 */       ParameterizedType pt = (ParameterizedType)t;
/* 314 */       if (pt.getRawType() instanceof Class) {
/* 315 */         return Type.getType((Class)pt.getRawType());
/*     */       }
/*     */     } 
/* 318 */     if (t instanceof java.lang.reflect.GenericArrayType) {
/* 319 */       return Type.getType(FieldSignature.vms(t));
/*     */     }
/*     */     
/* 322 */     if (t instanceof java.lang.reflect.WildcardType) {
/* 323 */       return Type.getType(FieldSignature.vms(t));
/*     */     }
/*     */     
/* 326 */     if (t instanceof TypeVariable) {
/* 327 */       TypeVariable tv = (TypeVariable)t;
/* 328 */       if (tv.getBounds()[0] instanceof Class) {
/* 329 */         return Type.getType((Class)tv.getBounds()[0]);
/*     */       }
/*     */     } 
/*     */     
/* 333 */     throw new IllegalArgumentException("Not creating ASM Type for type = " + t);
/*     */   }
/*     */ 
/*     */   
/*     */   static Class createExceptionBean(String className, Class exception, String typeNS, String elemName, String elemNS, ClassLoader cl) {
/* 338 */     return createExceptionBean(className, exception, typeNS, elemName, elemNS, cl, true);
/*     */   }
/*     */   
/*     */   static Class createExceptionBean(String className, Class exception, String typeNS, String elemName, String elemNS, ClassLoader cl, boolean decapitalizeExceptionBeanProperties) {
/*     */     byte[] image;
/* 343 */     Collection<Field> fields = RUNTIME_GENERATOR.collectExceptionBeanMembers(exception, decapitalizeExceptionBeanProperties);
/*     */ 
/*     */     
/*     */     try {
/* 347 */       image = createBeanImage(className, elemName, elemNS, exception
/* 348 */           .getSimpleName(), typeNS, fields);
/*     */     }
/* 350 */     catch (Exception e) {
/* 351 */       throw new WebServiceException(e);
/*     */     } 
/*     */     
/* 354 */     return Injector.inject(cl, className, image);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Field
/*     */     implements Comparable<Field>
/*     */   {
/*     */     private final Type reflectType;
/*     */     
/*     */     private final Type asmType;
/*     */     private final String fieldName;
/*     */     private final List<Annotation> jaxbAnnotations;
/*     */     
/*     */     Field(String paramName, Type paramType, Type asmType, List<Annotation> jaxbAnnotations) {
/* 368 */       this.reflectType = paramType;
/* 369 */       this.asmType = asmType;
/* 370 */       this.fieldName = paramName;
/* 371 */       this.jaxbAnnotations = jaxbAnnotations;
/*     */     }
/*     */     
/*     */     String getSignature() {
/* 375 */       if (this.reflectType instanceof Class) {
/* 376 */         return null;
/*     */       }
/* 378 */       if (this.reflectType instanceof TypeVariable) {
/* 379 */         return null;
/*     */       }
/* 381 */       return FieldSignature.vms(this.reflectType);
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(Field o) {
/* 386 */       return this.fieldName.compareTo(o.fieldName);
/*     */     }
/*     */   }
/*     */   
/*     */   static void write(byte[] b, String className) {
/* 391 */     className = className.substring(className.lastIndexOf(".") + 1);
/*     */     try {
/* 393 */       FileOutputStream fo = new FileOutputStream(className + ".class");
/* 394 */       fo.write(b);
/* 395 */       fo.flush();
/* 396 */       fo.close();
/* 397 */     } catch (IOException e) {
/* 398 */       LOGGER.log(Level.INFO, "Error Writing class", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/WrapperBeanGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */