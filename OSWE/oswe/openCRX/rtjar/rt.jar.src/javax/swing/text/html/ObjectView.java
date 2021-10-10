/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.beans.BeanInfo;
/*     */ import java.beans.IntrospectionException;
/*     */ import java.beans.Introspector;
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.ComponentView;
/*     */ import javax.swing.text.Element;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectView
/*     */   extends ComponentView
/*     */ {
/*     */   public ObjectView(Element paramElement) {
/*  81 */     super(paramElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Component createComponent() {
/*  90 */     AttributeSet attributeSet = getElement().getAttributes();
/*  91 */     String str = (String)attributeSet.getAttribute(HTML.Attribute.CLASSID);
/*     */     try {
/*  93 */       ReflectUtil.checkPackageAccess(str);
/*  94 */       Class<?> clazz = Class.forName(str, true, Thread.currentThread()
/*  95 */           .getContextClassLoader());
/*  96 */       Object object = clazz.newInstance();
/*  97 */       if (object instanceof Component) {
/*  98 */         Component component = (Component)object;
/*  99 */         setParameters(component, attributeSet);
/* 100 */         return component;
/*     */       } 
/* 102 */     } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     return getUnloadableRepresentation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Component getUnloadableRepresentation() {
/* 117 */     JLabel jLabel = new JLabel("??");
/* 118 */     jLabel.setForeground(Color.red);
/* 119 */     return jLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setParameters(Component paramComponent, AttributeSet paramAttributeSet) {
/*     */     BeanInfo beanInfo;
/* 128 */     Class<?> clazz = paramComponent.getClass();
/*     */     
/*     */     try {
/* 131 */       beanInfo = Introspector.getBeanInfo(clazz);
/* 132 */     } catch (IntrospectionException introspectionException) {
/* 133 */       System.err.println("introspector failed, ex: " + introspectionException);
/*     */       return;
/*     */     } 
/* 136 */     PropertyDescriptor[] arrayOfPropertyDescriptor = beanInfo.getPropertyDescriptors();
/* 137 */     for (byte b = 0; b < arrayOfPropertyDescriptor.length; b++) {
/*     */       
/* 139 */       Object object = paramAttributeSet.getAttribute(arrayOfPropertyDescriptor[b].getName());
/* 140 */       if (object instanceof String) {
/*     */         
/* 142 */         String str = (String)object;
/* 143 */         Method method = arrayOfPropertyDescriptor[b].getWriteMethod();
/* 144 */         if (method == null) {
/*     */           return;
/*     */         }
/*     */         
/* 148 */         Class[] arrayOfClass = method.getParameterTypes();
/* 149 */         if (arrayOfClass.length != 1) {
/*     */           return;
/*     */         }
/*     */         
/* 153 */         Object[] arrayOfObject = { str };
/*     */         try {
/* 155 */           MethodUtil.invoke(method, paramComponent, arrayOfObject);
/* 156 */         } catch (Exception exception) {
/* 157 */           System.err.println("Invocation failed");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/ObjectView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */