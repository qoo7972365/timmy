/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.security.AccessController;
/*     */ import java.util.ArrayList;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.Timer;
/*     */ import javax.swing.UIManager;
/*     */ import sun.awt.AppContext;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ import sun.swing.UIClientPropertyKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AnimationController
/*     */   implements ActionListener, PropertyChangeListener
/*     */ {
/*  73 */   private static final boolean VISTA_ANIMATION_DISABLED = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("swing.disablevistaanimation"))).booleanValue();
/*     */ 
/*     */   
/*  76 */   private static final Object ANIMATION_CONTROLLER_KEY = new StringBuilder("ANIMATION_CONTROLLER_KEY");
/*     */ 
/*     */   
/*  79 */   private final Map<JComponent, Map<TMSchema.Part, AnimationState>> animationStateMap = new WeakHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private final Timer timer = new Timer(33, this);
/*     */ 
/*     */   
/*     */   private static synchronized AnimationController getAnimationController() {
/*  88 */     AppContext appContext = AppContext.getAppContext();
/*  89 */     Object object = appContext.get(ANIMATION_CONTROLLER_KEY);
/*  90 */     if (object == null) {
/*  91 */       object = new AnimationController();
/*  92 */       appContext.put(ANIMATION_CONTROLLER_KEY, object);
/*     */     } 
/*  94 */     return (AnimationController)object;
/*     */   }
/*     */   
/*     */   private AnimationController() {
/*  98 */     this.timer.setRepeats(true);
/*  99 */     this.timer.setCoalesce(true);
/*     */     
/* 101 */     UIManager.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void triggerAnimation(JComponent paramJComponent, TMSchema.Part paramPart, TMSchema.State paramState) {
/* 106 */     if (paramJComponent instanceof javax.swing.JTabbedPane || paramPart == TMSchema.Part.TP_BUTTON) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     AnimationController animationController = getAnimationController();
/* 117 */     TMSchema.State state = animationController.getState(paramJComponent, paramPart);
/* 118 */     if (state != paramState) {
/* 119 */       animationController.putState(paramJComponent, paramPart, paramState);
/* 120 */       if (paramState == TMSchema.State.DEFAULTED)
/*     */       {
/*     */         
/* 123 */         state = TMSchema.State.HOT;
/*     */       }
/* 125 */       if (state != null) {
/*     */         long l;
/* 127 */         if (paramState == TMSchema.State.DEFAULTED) {
/*     */ 
/*     */ 
/*     */           
/* 131 */           l = 1000L;
/*     */         } else {
/* 133 */           XPStyle xPStyle = XPStyle.getXP();
/*     */           
/* 135 */           l = (xPStyle != null) ? xPStyle.getThemeTransitionDuration(paramJComponent, paramPart, 
/*     */               
/* 137 */               normalizeState(state), 
/* 138 */               normalizeState(paramState), TMSchema.Prop.TRANSITIONDURATIONS) : 1000L;
/*     */         } 
/*     */ 
/*     */         
/* 142 */         animationController.startAnimation(paramJComponent, paramPart, state, paramState, l);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static TMSchema.State normalizeState(TMSchema.State paramState) {
/* 152 */     switch (paramState)
/*     */     
/*     */     { 
/*     */       case DOWNPRESSED:
/*     */       case LEFTPRESSED:
/*     */       case RIGHTPRESSED:
/* 158 */         state = TMSchema.State.UPPRESSED;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 189 */         return state;case DOWNDISABLED: case LEFTDISABLED: case RIGHTDISABLED: state = TMSchema.State.UPDISABLED; return state;case DOWNHOT: case LEFTHOT: case RIGHTHOT: state = TMSchema.State.UPHOT; return state;case DOWNNORMAL: case LEFTNORMAL: case RIGHTNORMAL: state = TMSchema.State.UPNORMAL; return state; }  TMSchema.State state = paramState; return state;
/*     */   }
/*     */   
/*     */   private synchronized TMSchema.State getState(JComponent paramJComponent, TMSchema.Part paramPart) {
/* 193 */     TMSchema.State state = null;
/*     */     
/* 195 */     Object object = paramJComponent.getClientProperty(PartUIClientPropertyKey.getKey(paramPart));
/* 196 */     if (object instanceof TMSchema.State) {
/* 197 */       state = (TMSchema.State)object;
/*     */     }
/* 199 */     return state;
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void putState(JComponent paramJComponent, TMSchema.Part paramPart, TMSchema.State paramState) {
/* 204 */     paramJComponent.putClientProperty(PartUIClientPropertyKey.getKey(paramPart), paramState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void startAnimation(JComponent paramJComponent, TMSchema.Part paramPart, TMSchema.State paramState1, TMSchema.State paramState2, long paramLong) {
/* 213 */     boolean bool = false;
/* 214 */     if (paramState2 == TMSchema.State.DEFAULTED) {
/* 215 */       bool = true;
/*     */     }
/* 217 */     Map<TMSchema.Part, Object> map = (Map)this.animationStateMap.get(paramJComponent);
/* 218 */     if (paramLong <= 0L) {
/* 219 */       if (map != null) {
/* 220 */         map.remove(paramPart);
/* 221 */         if (map.size() == 0) {
/* 222 */           this.animationStateMap.remove(paramJComponent);
/*     */         }
/*     */       } 
/*     */       return;
/*     */     } 
/* 227 */     if (map == null) {
/* 228 */       map = new EnumMap<>(TMSchema.Part.class);
/* 229 */       this.animationStateMap.put(paramJComponent, map);
/*     */     } 
/* 231 */     map.put(paramPart, new AnimationState(paramState1, paramLong, bool));
/*     */     
/* 233 */     if (!this.timer.isRunning()) {
/* 234 */       this.timer.start();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static void paintSkin(JComponent paramJComponent, XPStyle.Skin paramSkin, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, TMSchema.State paramState) {
/* 240 */     if (VISTA_ANIMATION_DISABLED) {
/* 241 */       paramSkin.paintSkinRaw(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, paramState);
/*     */       return;
/*     */     } 
/* 244 */     triggerAnimation(paramJComponent, paramSkin.part, paramState);
/* 245 */     AnimationController animationController = getAnimationController();
/* 246 */     synchronized (animationController) {
/* 247 */       AnimationState animationState = null;
/*     */       
/* 249 */       Map map = animationController.animationStateMap.get(paramJComponent);
/* 250 */       if (map != null) {
/* 251 */         animationState = (AnimationState)map.get(paramSkin.part);
/*     */       }
/* 253 */       if (animationState != null) {
/* 254 */         animationState.paintSkin(paramSkin, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, paramState);
/*     */       } else {
/* 256 */         paramSkin.paintSkinRaw(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, paramState);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 262 */     if ("lookAndFeel" == paramPropertyChangeEvent.getPropertyName() && 
/* 263 */       !(paramPropertyChangeEvent.getNewValue() instanceof WindowsLookAndFeel)) {
/* 264 */       dispose();
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void actionPerformed(ActionEvent paramActionEvent) {
/* 269 */     ArrayList<JComponent> arrayList = null;
/* 270 */     List<TMSchema.Part> list = null;
/* 271 */     for (JComponent jComponent : this.animationStateMap.keySet()) {
/* 272 */       jComponent.repaint();
/* 273 */       if (list != null) {
/* 274 */         list.clear();
/*     */       }
/* 276 */       Map map = this.animationStateMap.get(jComponent);
/* 277 */       if (!jComponent.isShowing() || map == null || map
/*     */         
/* 279 */         .size() == 0) {
/* 280 */         if (arrayList == null) {
/* 281 */           arrayList = new ArrayList();
/*     */         }
/* 283 */         arrayList.add(jComponent);
/*     */         continue;
/*     */       } 
/* 286 */       for (TMSchema.Part part : map.keySet()) {
/* 287 */         if (((AnimationState)map.get(part)).isDone()) {
/* 288 */           if (list == null) {
/* 289 */             list = new ArrayList();
/*     */           }
/* 291 */           list.add(part);
/*     */         } 
/*     */       } 
/* 294 */       if (list != null) {
/* 295 */         if (list.size() == map.size()) {
/*     */           
/* 297 */           if (arrayList == null) {
/* 298 */             arrayList = new ArrayList<>();
/*     */           }
/* 300 */           arrayList.add(jComponent); continue;
/*     */         } 
/* 302 */         for (TMSchema.Part part : list) {
/* 303 */           map.remove(part);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 308 */     if (arrayList != null) {
/* 309 */       for (JComponent jComponent : arrayList) {
/* 310 */         this.animationStateMap.remove(jComponent);
/*     */       }
/*     */     }
/* 313 */     if (this.animationStateMap.size() == 0) {
/* 314 */       this.timer.stop();
/*     */     }
/*     */   }
/*     */   
/*     */   private synchronized void dispose() {
/* 319 */     this.timer.stop();
/* 320 */     UIManager.removePropertyChangeListener(this);
/* 321 */     synchronized (AnimationController.class) {
/* 322 */       AppContext.getAppContext()
/* 323 */         .put(ANIMATION_CONTROLLER_KEY, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class AnimationState
/*     */   {
/*     */     private final TMSchema.State startState;
/*     */ 
/*     */     
/*     */     private final long duration;
/*     */ 
/*     */     
/*     */     private long startTime;
/*     */ 
/*     */     
/*     */     private boolean isForward = true;
/*     */ 
/*     */     
/*     */     private boolean isForwardAndReverse;
/*     */ 
/*     */     
/*     */     private float progress;
/*     */ 
/*     */ 
/*     */     
/*     */     AnimationState(TMSchema.State param1State, long param1Long, boolean param1Boolean) {
/* 351 */       assert param1State != null && param1Long > 0L;
/* 352 */       assert SwingUtilities.isEventDispatchThread();
/*     */       
/* 354 */       this.startState = param1State;
/* 355 */       this.duration = param1Long * 1000000L;
/* 356 */       this.startTime = System.nanoTime();
/* 357 */       this.isForwardAndReverse = param1Boolean;
/* 358 */       this.progress = 0.0F;
/*     */     }
/*     */     private void updateProgress() {
/* 361 */       assert SwingUtilities.isEventDispatchThread();
/*     */       
/* 363 */       if (isDone()) {
/*     */         return;
/*     */       }
/* 366 */       long l = System.nanoTime();
/*     */       
/* 368 */       this.progress = (float)(l - this.startTime) / (float)this.duration;
/*     */       
/* 370 */       this.progress = Math.max(this.progress, 0.0F);
/* 371 */       if (this.progress >= 1.0F) {
/* 372 */         this.progress = 1.0F;
/* 373 */         if (this.isForwardAndReverse) {
/* 374 */           this.startTime = l;
/* 375 */           this.progress = 0.0F;
/* 376 */           this.isForward = !this.isForward;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     void paintSkin(XPStyle.Skin param1Skin, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, TMSchema.State param1State) {
/* 382 */       assert SwingUtilities.isEventDispatchThread();
/*     */       
/* 384 */       updateProgress();
/* 385 */       if (!isDone()) {
/* 386 */         float f; Graphics2D graphics2D = (Graphics2D)param1Graphics.create();
/* 387 */         param1Skin.paintSkinRaw(graphics2D, param1Int1, param1Int2, param1Int3, param1Int4, this.startState);
/*     */         
/* 389 */         if (this.isForward) {
/* 390 */           f = this.progress;
/*     */         } else {
/* 392 */           f = 1.0F - this.progress;
/*     */         } 
/* 394 */         graphics2D.setComposite(AlphaComposite.SrcOver.derive(f));
/* 395 */         param1Skin.paintSkinRaw(graphics2D, param1Int1, param1Int2, param1Int3, param1Int4, param1State);
/* 396 */         graphics2D.dispose();
/*     */       } else {
/* 398 */         param1Skin.paintSkinRaw(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1State);
/*     */       } 
/*     */     }
/*     */     boolean isDone() {
/* 402 */       assert SwingUtilities.isEventDispatchThread();
/*     */       
/* 404 */       return (this.progress >= 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class PartUIClientPropertyKey
/*     */     implements UIClientPropertyKey
/*     */   {
/* 411 */     private static final Map<TMSchema.Part, PartUIClientPropertyKey> map = new EnumMap<>(TMSchema.Part.class);
/*     */     private final TMSchema.Part part;
/*     */     
/*     */     static synchronized PartUIClientPropertyKey getKey(TMSchema.Part param1Part) {
/* 415 */       PartUIClientPropertyKey partUIClientPropertyKey = map.get(param1Part);
/* 416 */       if (partUIClientPropertyKey == null) {
/* 417 */         partUIClientPropertyKey = new PartUIClientPropertyKey(param1Part);
/* 418 */         map.put(param1Part, partUIClientPropertyKey);
/*     */       } 
/* 420 */       return partUIClientPropertyKey;
/*     */     }
/*     */ 
/*     */     
/*     */     private PartUIClientPropertyKey(TMSchema.Part param1Part) {
/* 425 */       this.part = param1Part;
/*     */     }
/*     */     public String toString() {
/* 428 */       return this.part.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/AnimationController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */