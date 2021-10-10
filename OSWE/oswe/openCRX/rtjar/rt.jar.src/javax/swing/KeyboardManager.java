/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import sun.awt.EmbeddedFrame;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class KeyboardManager
/*     */ {
/*  66 */   static KeyboardManager currentManager = new KeyboardManager();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   Hashtable<Container, Hashtable> containerMap = new Hashtable<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   Hashtable<ComponentKeyStrokePair, Container> componentKeyStrokeMap = new Hashtable<>();
/*     */   
/*     */   public static KeyboardManager getCurrentManager() {
/*  80 */     return currentManager;
/*     */   }
/*     */   
/*     */   public static void setCurrentManager(KeyboardManager paramKeyboardManager) {
/*  84 */     currentManager = paramKeyboardManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerKeyStroke(KeyStroke paramKeyStroke, JComponent paramJComponent) {
/*  94 */     Container container = getTopAncestor(paramJComponent);
/*  95 */     if (container == null) {
/*     */       return;
/*     */     }
/*  98 */     Hashtable<KeyStroke, JComponent> hashtable = this.containerMap.get(container);
/*     */     
/* 100 */     if (hashtable == null) {
/* 101 */       hashtable = registerNewTopContainer(container);
/*     */     }
/*     */     
/* 104 */     Object object = hashtable.get(paramKeyStroke);
/* 105 */     if (object == null) {
/* 106 */       hashtable.put(paramKeyStroke, paramJComponent);
/* 107 */     } else if (object instanceof Vector) {
/* 108 */       Vector<JComponent> vector = (Vector)object;
/* 109 */       if (!vector.contains(paramJComponent)) {
/* 110 */         vector.addElement(paramJComponent);
/*     */       }
/* 112 */     } else if (object instanceof JComponent) {
/*     */ 
/*     */ 
/*     */       
/* 116 */       if (object != paramJComponent) {
/* 117 */         Vector<JComponent> vector = new Vector();
/* 118 */         vector.addElement((JComponent)object);
/* 119 */         vector.addElement(paramJComponent);
/* 120 */         hashtable.put(paramKeyStroke, vector);
/*     */       } 
/*     */     } else {
/* 123 */       System.out.println("Unexpected condition in registerKeyStroke");
/* 124 */       Thread.dumpStack();
/*     */     } 
/*     */     
/* 127 */     this.componentKeyStrokeMap.put(new ComponentKeyStrokePair(paramJComponent, paramKeyStroke), container);
/*     */ 
/*     */ 
/*     */     
/* 131 */     if (container instanceof EmbeddedFrame) {
/* 132 */       ((EmbeddedFrame)container).registerAccelerator(paramKeyStroke);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Container getTopAncestor(JComponent paramJComponent) {
/* 140 */     for (Container container = paramJComponent.getParent(); container != null; container = container.getParent()) {
/* 141 */       if ((container instanceof Window && ((Window)container).isFocusableWindow()) || container instanceof java.applet.Applet || container instanceof JInternalFrame)
/*     */       {
/*     */         
/* 144 */         return container;
/*     */       }
/*     */     } 
/* 147 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterKeyStroke(KeyStroke paramKeyStroke, JComponent paramJComponent) {
/* 155 */     ComponentKeyStrokePair componentKeyStrokePair = new ComponentKeyStrokePair(paramJComponent, paramKeyStroke);
/*     */     
/* 157 */     Container container = this.componentKeyStrokeMap.get(componentKeyStrokePair);
/*     */     
/* 159 */     if (container == null) {
/*     */       return;
/*     */     }
/*     */     
/* 163 */     Hashtable hashtable = this.containerMap.get(container);
/* 164 */     if (hashtable == null) {
/* 165 */       Thread.dumpStack();
/*     */       
/*     */       return;
/*     */     } 
/* 169 */     Object object = hashtable.get(paramKeyStroke);
/* 170 */     if (object == null) {
/* 171 */       Thread.dumpStack();
/*     */       
/*     */       return;
/*     */     } 
/* 175 */     if (object instanceof JComponent && object == paramJComponent) {
/* 176 */       hashtable.remove(paramKeyStroke);
/*     */     }
/* 178 */     else if (object instanceof Vector) {
/* 179 */       Vector vector = (Vector)object;
/* 180 */       vector.removeElement(paramJComponent);
/* 181 */       if (vector.isEmpty()) {
/* 182 */         hashtable.remove(paramKeyStroke);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 187 */     if (hashtable.isEmpty()) {
/* 188 */       this.containerMap.remove(container);
/*     */     }
/*     */ 
/*     */     
/* 192 */     this.componentKeyStrokeMap.remove(componentKeyStrokePair);
/*     */ 
/*     */ 
/*     */     
/* 196 */     if (container instanceof EmbeddedFrame) {
/* 197 */       ((EmbeddedFrame)container).unregisterAccelerator(paramKeyStroke);
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
/*     */   public boolean fireKeyboardAction(KeyEvent paramKeyEvent, boolean paramBoolean, Container paramContainer) {
/*     */     KeyStroke keyStroke1;
/* 210 */     if (paramKeyEvent.isConsumed()) {
/* 211 */       System.out.println("Acquired pre-used event!");
/* 212 */       Thread.dumpStack();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     KeyStroke keyStroke2 = null;
/*     */ 
/*     */     
/* 221 */     if (paramKeyEvent.getID() == 400) {
/* 222 */       keyStroke1 = KeyStroke.getKeyStroke(paramKeyEvent.getKeyChar());
/*     */     } else {
/* 224 */       if (paramKeyEvent.getKeyCode() != paramKeyEvent.getExtendedKeyCode()) {
/* 225 */         keyStroke2 = KeyStroke.getKeyStroke(paramKeyEvent.getExtendedKeyCode(), paramKeyEvent.getModifiers(), !paramBoolean);
/*     */       }
/* 227 */       keyStroke1 = KeyStroke.getKeyStroke(paramKeyEvent.getKeyCode(), paramKeyEvent.getModifiers(), !paramBoolean);
/*     */     } 
/*     */     
/* 230 */     Hashtable hashtable = this.containerMap.get(paramContainer);
/* 231 */     if (hashtable != null) {
/*     */       
/* 233 */       Object object = null;
/*     */       
/* 235 */       if (keyStroke2 != null) {
/* 236 */         object = hashtable.get(keyStroke2);
/* 237 */         if (object != null) {
/* 238 */           keyStroke1 = keyStroke2;
/*     */         }
/*     */       } 
/* 241 */       if (object == null) {
/* 242 */         object = hashtable.get(keyStroke1);
/*     */       }
/*     */       
/* 245 */       if (object != null)
/*     */       {
/* 247 */         if (object instanceof JComponent) {
/* 248 */           JComponent jComponent = (JComponent)object;
/* 249 */           if (jComponent.isShowing() && jComponent.isEnabled()) {
/* 250 */             fireBinding(jComponent, keyStroke1, paramKeyEvent, paramBoolean);
/*     */           }
/* 252 */         } else if (object instanceof Vector) {
/* 253 */           Vector<JComponent> vector = (Vector)object;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 259 */           for (int i = vector.size() - 1; i >= 0; i--) {
/* 260 */             JComponent jComponent = vector.elementAt(i);
/*     */             
/* 262 */             if (jComponent.isShowing() && jComponent.isEnabled()) {
/* 263 */               fireBinding(jComponent, keyStroke1, paramKeyEvent, paramBoolean);
/* 264 */               if (paramKeyEvent.isConsumed())
/* 265 */                 return true; 
/*     */             } 
/*     */           } 
/*     */         } else {
/* 269 */           System.out.println("Unexpected condition in fireKeyboardAction " + object);
/*     */           
/* 271 */           Thread.dumpStack();
/*     */         } 
/*     */       }
/*     */     } 
/* 275 */     if (paramKeyEvent.isConsumed()) {
/* 276 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 281 */     if (hashtable != null) {
/* 282 */       Vector vector = (Vector)hashtable.get(JMenuBar.class);
/* 283 */       if (vector != null) {
/* 284 */         Enumeration<JMenuBar> enumeration = vector.elements();
/* 285 */         while (enumeration.hasMoreElements()) {
/* 286 */           JMenuBar jMenuBar = enumeration.nextElement();
/* 287 */           if (jMenuBar.isShowing() && jMenuBar.isEnabled()) {
/* 288 */             boolean bool = (keyStroke2 != null && !keyStroke2.equals(keyStroke1)) ? true : false;
/* 289 */             if (bool) {
/* 290 */               fireBinding(jMenuBar, keyStroke2, paramKeyEvent, paramBoolean);
/*     */             }
/* 292 */             if (!bool || !paramKeyEvent.isConsumed()) {
/* 293 */               fireBinding(jMenuBar, keyStroke1, paramKeyEvent, paramBoolean);
/*     */             }
/* 295 */             if (paramKeyEvent.isConsumed()) {
/* 296 */               return true;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 303 */     return paramKeyEvent.isConsumed();
/*     */   }
/*     */   
/*     */   void fireBinding(JComponent paramJComponent, KeyStroke paramKeyStroke, KeyEvent paramKeyEvent, boolean paramBoolean) {
/* 307 */     if (paramJComponent.processKeyBinding(paramKeyStroke, paramKeyEvent, 2, paramBoolean))
/*     */     {
/* 309 */       paramKeyEvent.consume();
/*     */     }
/*     */   }
/*     */   
/*     */   public void registerMenuBar(JMenuBar paramJMenuBar) {
/* 314 */     Container container = getTopAncestor(paramJMenuBar);
/* 315 */     if (container == null) {
/*     */       return;
/*     */     }
/* 318 */     Hashtable<Class<JMenuBar>, Vector> hashtable = this.containerMap.get(container);
/*     */     
/* 320 */     if (hashtable == null) {
/* 321 */       hashtable = registerNewTopContainer(container);
/*     */     }
/*     */     
/* 324 */     Vector<JMenuBar> vector = (Vector)hashtable.get(JMenuBar.class);
/*     */     
/* 326 */     if (vector == null) {
/*     */       
/* 328 */       vector = new Vector();
/* 329 */       hashtable.put(JMenuBar.class, vector);
/*     */     } 
/*     */     
/* 332 */     if (!vector.contains(paramJMenuBar)) {
/* 333 */       vector.addElement(paramJMenuBar);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregisterMenuBar(JMenuBar paramJMenuBar) {
/* 339 */     Container container = getTopAncestor(paramJMenuBar);
/* 340 */     if (container == null) {
/*     */       return;
/*     */     }
/* 343 */     Hashtable hashtable = this.containerMap.get(container);
/* 344 */     if (hashtable != null) {
/* 345 */       Vector vector = (Vector)hashtable.get(JMenuBar.class);
/* 346 */       if (vector != null) {
/* 347 */         vector.removeElement(paramJMenuBar);
/* 348 */         if (vector.isEmpty()) {
/* 349 */           hashtable.remove(JMenuBar.class);
/* 350 */           if (hashtable.isEmpty())
/*     */           {
/* 352 */             this.containerMap.remove(container); } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Hashtable registerNewTopContainer(Container paramContainer) {
/* 359 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 360 */     this.containerMap.put(paramContainer, hashtable);
/* 361 */     return hashtable;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class ComponentKeyStrokePair
/*     */   {
/*     */     Object component;
/*     */     
/*     */     Object keyStroke;
/*     */ 
/*     */     
/*     */     public ComponentKeyStrokePair(Object param1Object1, Object param1Object2) {
/* 374 */       this.component = param1Object1;
/* 375 */       this.keyStroke = param1Object2;
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 379 */       if (!(param1Object instanceof ComponentKeyStrokePair)) {
/* 380 */         return false;
/*     */       }
/* 382 */       ComponentKeyStrokePair componentKeyStrokePair = (ComponentKeyStrokePair)param1Object;
/* 383 */       return (this.component.equals(componentKeyStrokePair.component) && this.keyStroke.equals(componentKeyStrokePair.keyStroke));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 387 */       return this.component.hashCode() * this.keyStroke.hashCode();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/KeyboardManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */