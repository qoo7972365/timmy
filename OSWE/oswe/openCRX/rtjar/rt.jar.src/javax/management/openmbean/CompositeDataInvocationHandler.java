/*     */ package javax.management.openmbean;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.DefaultMXBeanMappingFactory;
/*     */ import com.sun.jmx.mbeanserver.MXBeanLookup;
/*     */ import com.sun.jmx.mbeanserver.MXBeanMapping;
/*     */ import com.sun.jmx.mbeanserver.MXBeanMappingFactory;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompositeDataInvocationHandler
/*     */   implements InvocationHandler
/*     */ {
/*     */   private final CompositeData compositeData;
/*     */   private final MXBeanLookup lookup;
/*     */   
/*     */   public CompositeDataInvocationHandler(CompositeData paramCompositeData) {
/* 120 */     this(paramCompositeData, null);
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
/*     */   CompositeDataInvocationHandler(CompositeData paramCompositeData, MXBeanLookup paramMXBeanLookup) {
/* 135 */     if (paramCompositeData == null)
/* 136 */       throw new IllegalArgumentException("compositeData"); 
/* 137 */     this.compositeData = paramCompositeData;
/* 138 */     this.lookup = paramMXBeanLookup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompositeData getCompositeData() {
/* 148 */     assert this.compositeData != null;
/* 149 */     return this.compositeData;
/*     */   }
/*     */   
/*     */   public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
/*     */     Object object;
/* 154 */     String str1 = paramMethod.getName();
/*     */ 
/*     */     
/* 157 */     if (paramMethod.getDeclaringClass() == Object.class) {
/* 158 */       if (str1.equals("toString") && paramArrayOfObject == null)
/* 159 */         return "Proxy[" + this.compositeData + "]"; 
/* 160 */       if (str1.equals("hashCode") && paramArrayOfObject == null)
/* 161 */         return Integer.valueOf(this.compositeData.hashCode() + 1128548680); 
/* 162 */       if (str1.equals("equals") && paramArrayOfObject.length == 1 && paramMethod
/* 163 */         .getParameterTypes()[0] == Object.class) {
/* 164 */         return Boolean.valueOf(equals(paramObject, paramArrayOfObject[0]));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 174 */       return paramMethod.invoke(this, paramArrayOfObject);
/*     */     } 
/*     */ 
/*     */     
/* 178 */     String str2 = DefaultMXBeanMappingFactory.propertyName(paramMethod);
/* 179 */     if (str2 == null) {
/* 180 */       throw new IllegalArgumentException("Method is not getter: " + paramMethod
/* 181 */           .getName());
/*     */     }
/*     */     
/* 184 */     if (this.compositeData.containsKey(str2)) {
/* 185 */       object = this.compositeData.get(str2);
/*     */     } else {
/* 187 */       String str = DefaultMXBeanMappingFactory.decapitalize(str2);
/* 188 */       if (this.compositeData.containsKey(str)) {
/* 189 */         object = this.compositeData.get(str);
/*     */       }
/*     */       else {
/*     */         
/* 193 */         String str3 = "No CompositeData item " + str2 + (str.equals(str2) ? "" : (" or " + str)) + " to match " + str1;
/*     */         
/* 195 */         throw new IllegalArgumentException(str3);
/*     */       } 
/*     */     } 
/*     */     
/* 199 */     MXBeanMapping mXBeanMapping = MXBeanMappingFactory.DEFAULT.mappingForType(paramMethod.getGenericReturnType(), MXBeanMappingFactory.DEFAULT);
/*     */     
/* 201 */     return mXBeanMapping.fromOpenValue(object);
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
/*     */   private boolean equals(Object paramObject1, Object paramObject2) {
/* 228 */     if (paramObject2 == null) {
/* 229 */       return false;
/*     */     }
/* 231 */     Class<?> clazz1 = paramObject1.getClass();
/* 232 */     Class<?> clazz2 = paramObject2.getClass();
/* 233 */     if (clazz1 != clazz2)
/* 234 */       return false; 
/* 235 */     InvocationHandler invocationHandler = Proxy.getInvocationHandler(paramObject2);
/* 236 */     if (!(invocationHandler instanceof CompositeDataInvocationHandler))
/* 237 */       return false; 
/* 238 */     CompositeDataInvocationHandler compositeDataInvocationHandler = (CompositeDataInvocationHandler)invocationHandler;
/*     */     
/* 240 */     return this.compositeData.equals(compositeDataInvocationHandler.compositeData);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/openmbean/CompositeDataInvocationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */