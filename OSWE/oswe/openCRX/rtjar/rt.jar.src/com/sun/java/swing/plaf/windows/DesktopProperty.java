/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DesktopProperty
/*     */   implements UIDefaults.ActiveValue
/*     */ {
/*     */   private static boolean updatePending;
/*  52 */   private static final ReferenceQueue<DesktopProperty> queue = new ReferenceQueue<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WeakPCL pcl;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String key;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object value;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Object fallback;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void flushUnreferencedProperties() {
/*     */     WeakPCL weakPCL;
/*  79 */     while ((weakPCL = (WeakPCL)queue.poll()) != null) {
/*  80 */       weakPCL.dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized void setUpdatePending(boolean paramBoolean) {
/*  89 */     updatePending = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized boolean isUpdatePending() {
/*  96 */     return updatePending;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void updateAllUIs() {
/* 105 */     Class<?> clazz = UIManager.getLookAndFeel().getClass();
/* 106 */     if (clazz.getPackage().equals(DesktopProperty.class.getPackage())) {
/* 107 */       XPStyle.invalidateStyle();
/*     */     }
/* 109 */     Frame[] arrayOfFrame = Frame.getFrames();
/* 110 */     for (Frame frame : arrayOfFrame) {
/* 111 */       updateWindowUI(frame);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void updateWindowUI(Window paramWindow) {
/* 119 */     SwingUtilities.updateComponentTreeUI(paramWindow);
/* 120 */     Window[] arrayOfWindow = paramWindow.getOwnedWindows();
/* 121 */     for (Window window : arrayOfWindow) {
/* 122 */       updateWindowUI(window);
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
/*     */   public DesktopProperty(String paramString, Object paramObject) {
/* 134 */     this.key = paramString;
/* 135 */     this.fallback = paramObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     flushUnreferencedProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createValue(UIDefaults paramUIDefaults) {
/* 153 */     if (this.value == null) {
/* 154 */       this.value = configureValue(getValueFromDesktop());
/* 155 */       if (this.value == null) {
/* 156 */         this.value = configureValue(getDefaultValue());
/*     */       }
/*     */     } 
/* 159 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object getValueFromDesktop() {
/* 166 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*     */     
/* 168 */     if (this.pcl == null) {
/* 169 */       this.pcl = new WeakPCL(this, getKey(), UIManager.getLookAndFeel());
/* 170 */       toolkit.addPropertyChangeListener(getKey(), this.pcl);
/*     */     } 
/*     */     
/* 173 */     return toolkit.getDesktopProperty(getKey());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object getDefaultValue() {
/* 180 */     return this.fallback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate(LookAndFeel paramLookAndFeel) {
/* 189 */     invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {
/* 197 */     this.value = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateUI() {
/* 208 */     if (!isUpdatePending()) {
/* 209 */       setUpdatePending(true);
/* 210 */       Runnable runnable = new Runnable() {
/*     */           public void run() {
/* 212 */             DesktopProperty.updateAllUIs();
/* 213 */             DesktopProperty.setUpdatePending(false);
/*     */           }
/*     */         };
/* 216 */       SwingUtilities.invokeLater(runnable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object configureValue(Object paramObject) {
/* 225 */     if (paramObject != null) {
/* 226 */       if (paramObject instanceof Color) {
/* 227 */         return new ColorUIResource((Color)paramObject);
/*     */       }
/* 229 */       if (paramObject instanceof Font) {
/* 230 */         return new FontUIResource((Font)paramObject);
/*     */       }
/* 232 */       if (paramObject instanceof UIDefaults.LazyValue) {
/* 233 */         paramObject = ((UIDefaults.LazyValue)paramObject).createValue(null);
/*     */       }
/* 235 */       else if (paramObject instanceof UIDefaults.ActiveValue) {
/* 236 */         paramObject = ((UIDefaults.ActiveValue)paramObject).createValue(null);
/*     */       } 
/*     */     } 
/* 239 */     return paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getKey() {
/* 246 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WeakPCL
/*     */     extends WeakReference<DesktopProperty>
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private String key;
/*     */ 
/*     */     
/*     */     private LookAndFeel laf;
/*     */ 
/*     */     
/*     */     WeakPCL(DesktopProperty param1DesktopProperty, String param1String, LookAndFeel param1LookAndFeel) {
/* 262 */       super(param1DesktopProperty, DesktopProperty.queue);
/* 263 */       this.key = param1String;
/* 264 */       this.laf = param1LookAndFeel;
/*     */     }
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 268 */       DesktopProperty desktopProperty = get();
/*     */       
/* 270 */       if (desktopProperty == null || this.laf != UIManager.getLookAndFeel()) {
/*     */ 
/*     */         
/* 273 */         dispose();
/*     */       } else {
/*     */         
/* 276 */         desktopProperty.invalidate(this.laf);
/* 277 */         desktopProperty.updateUI();
/*     */       } 
/*     */     }
/*     */     
/*     */     void dispose() {
/* 282 */       Toolkit.getDefaultToolkit().removePropertyChangeListener(this.key, this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/DesktopProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */