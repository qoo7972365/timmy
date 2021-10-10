/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.peer.MenuComponentPeer;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.util.Locale;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleComponent;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleSelection;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AppContext;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class MenuComponent
/*      */   implements Serializable
/*      */ {
/*      */   transient MenuComponentPeer peer;
/*      */   transient MenuContainer parent;
/*      */   transient AppContext appContext;
/*      */   volatile Font font;
/*      */   private String name;
/*      */   
/*      */   static {
/*   54 */     Toolkit.loadLibraries();
/*   55 */     if (!GraphicsEnvironment.isHeadless()) {
/*   56 */       initIDs();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  133 */     AWTAccessor.setMenuComponentAccessor(new AWTAccessor.MenuComponentAccessor()
/*      */         {
/*      */           public AppContext getAppContext(MenuComponent param1MenuComponent) {
/*  136 */             return param1MenuComponent.appContext;
/*      */           }
/*      */           
/*      */           public void setAppContext(MenuComponent param1MenuComponent, AppContext param1AppContext) {
/*  140 */             param1MenuComponent.appContext = param1AppContext;
/*      */           }
/*      */           public MenuContainer getParent(MenuComponent param1MenuComponent) {
/*  143 */             return param1MenuComponent.parent;
/*      */           }
/*      */           public Font getFont_NoClientCode(MenuComponent param1MenuComponent) {
/*  146 */             return param1MenuComponent.getFont_NoClientCode();
/*      */           }
/*      */           
/*      */           public <T extends MenuComponentPeer> T getPeer(MenuComponent param1MenuComponent) {
/*  150 */             return (T)param1MenuComponent.peer;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean nameExplicitlySet = false;
/*      */   
/*      */   boolean newEventsOnly = false;
/*      */   private volatile transient AccessControlContext acc = AccessController.getContext();
/*      */   static final String actionListenerK = "actionL";
/*      */   static final String itemListenerK = "itemL";
/*      */   private static final long serialVersionUID = -4536902356223894379L;
/*      */   AccessibleContext accessibleContext;
/*      */   
/*      */   final AccessControlContext getAccessControlContext() {
/*      */     if (this.acc == null) {
/*      */       throw new SecurityException("MenuComponent is missing AccessControlContext");
/*      */     }
/*      */     return this.acc;
/*      */   }
/*      */   
/*      */   String constructComponentName() {
/*  173 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  185 */     if (this.name == null && !this.nameExplicitlySet)
/*  186 */       synchronized (this) {
/*  187 */         if (this.name == null && !this.nameExplicitlySet) {
/*  188 */           this.name = constructComponentName();
/*      */         }
/*      */       }  
/*  191 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setName(String paramString) {
/*  201 */     synchronized (this) {
/*  202 */       this.name = paramString;
/*  203 */       this.nameExplicitlySet = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MenuContainer getParent() {
/*  214 */     return getParent_NoClientCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final MenuContainer getParent_NoClientCode() {
/*  221 */     return this.parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public MenuComponentPeer getPeer() {
/*  230 */     return this.peer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getFont() {
/*  240 */     Font font = this.font;
/*  241 */     if (font != null) {
/*  242 */       return font;
/*      */     }
/*  244 */     MenuContainer menuContainer = this.parent;
/*  245 */     if (menuContainer != null) {
/*  246 */       return menuContainer.getFont();
/*      */     }
/*  248 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Font getFont_NoClientCode() {
/*  256 */     Font font = this.font;
/*  257 */     if (font != null) {
/*  258 */       return font;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  265 */     MenuContainer menuContainer = this.parent;
/*  266 */     if (menuContainer != null) {
/*  267 */       if (menuContainer instanceof Component) {
/*  268 */         font = ((Component)menuContainer).getFont_NoClientCode();
/*  269 */       } else if (menuContainer instanceof MenuComponent) {
/*  270 */         font = ((MenuComponent)menuContainer).getFont_NoClientCode();
/*      */       } 
/*      */     }
/*  273 */     return font;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFont(Font paramFont) {
/*  295 */     synchronized (getTreeLock()) {
/*  296 */       this.font = paramFont;
/*      */       
/*  298 */       MenuComponentPeer menuComponentPeer = this.peer;
/*  299 */       if (menuComponentPeer != null) {
/*  300 */         menuComponentPeer.setFont(paramFont);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNotify() {
/*  311 */     synchronized (getTreeLock()) {
/*  312 */       MenuComponentPeer menuComponentPeer = this.peer;
/*  313 */       if (menuComponentPeer != null) {
/*  314 */         Toolkit.getEventQueue().removeSourceEvents(this, true);
/*  315 */         this.peer = null;
/*  316 */         menuComponentPeer.dispose();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean postEvent(Event paramEvent) {
/*  333 */     MenuContainer menuContainer = this.parent;
/*  334 */     if (menuContainer != null) {
/*  335 */       menuContainer.postEvent(paramEvent);
/*      */     }
/*  337 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void dispatchEvent(AWTEvent paramAWTEvent) {
/*  345 */     dispatchEventImpl(paramAWTEvent);
/*      */   }
/*      */   
/*      */   void dispatchEventImpl(AWTEvent paramAWTEvent) {
/*  349 */     EventQueue.setCurrentEventAndMostRecentTime(paramAWTEvent);
/*      */     
/*  351 */     Toolkit.getDefaultToolkit().notifyAWTEventListeners(paramAWTEvent);
/*      */     
/*  353 */     if (this.newEventsOnly || (this.parent != null && this.parent instanceof MenuComponent && ((MenuComponent)this.parent).newEventsOnly)) {
/*      */ 
/*      */       
/*  356 */       if (eventEnabled(paramAWTEvent)) {
/*  357 */         processEvent(paramAWTEvent);
/*  358 */       } else if (paramAWTEvent instanceof java.awt.event.ActionEvent && this.parent != null) {
/*  359 */         paramAWTEvent.setSource(this.parent);
/*  360 */         ((MenuComponent)this.parent).dispatchEvent(paramAWTEvent);
/*      */       } 
/*      */     } else {
/*      */       
/*  364 */       Event event = paramAWTEvent.convertToOld();
/*  365 */       if (event != null) {
/*  366 */         postEvent(event);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   boolean eventEnabled(AWTEvent paramAWTEvent) {
/*  373 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processEvent(AWTEvent paramAWTEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String paramString() {
/*  397 */     String str = getName();
/*  398 */     return (str != null) ? str : "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  406 */     return getClass().getName() + "[" + paramString() + "]";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Object getTreeLock() {
/*  416 */     return Component.LOCK;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException, HeadlessException {
/*  432 */     GraphicsEnvironment.checkHeadless();
/*      */     
/*  434 */     this.acc = AccessController.getContext();
/*      */     
/*  436 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*  438 */     this.appContext = AppContext.getAppContext();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MenuComponent() throws HeadlessException {
/*  455 */     this.accessibleContext = null;
/*      */     GraphicsEnvironment.checkHeadless();
/*      */     this.appContext = AppContext.getAppContext();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AccessibleContext getAccessibleContext() {
/*  471 */     return this.accessibleContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract class AccessibleAWTMenuComponent
/*      */     extends AccessibleContext
/*      */     implements Serializable, AccessibleComponent, AccessibleSelection
/*      */   {
/*      */     private static final long serialVersionUID = -4269533416223798698L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleSelection getAccessibleSelection() {
/*  512 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAccessibleName() {
/*  529 */       return this.accessibleName;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAccessibleDescription() {
/*  548 */       return this.accessibleDescription;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleRole getAccessibleRole() {
/*  559 */       return AccessibleRole.AWT_COMPONENT;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleStateSet getAccessibleStateSet() {
/*  570 */       return MenuComponent.this.getAccessibleStateSet();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleParent() {
/*  583 */       if (this.accessibleParent != null) {
/*  584 */         return this.accessibleParent;
/*      */       }
/*  586 */       MenuContainer menuContainer = MenuComponent.this.getParent();
/*  587 */       if (menuContainer instanceof Accessible) {
/*  588 */         return (Accessible)menuContainer;
/*      */       }
/*      */       
/*  591 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleIndexInParent() {
/*  602 */       return MenuComponent.this.getAccessibleIndexInParent();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleChildrenCount() {
/*  613 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleChild(int param1Int) {
/*  623 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Locale getLocale() {
/*  632 */       MenuContainer menuContainer = MenuComponent.this.getParent();
/*  633 */       if (menuContainer instanceof Component) {
/*  634 */         return ((Component)menuContainer).getLocale();
/*      */       }
/*  636 */       return Locale.getDefault();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleComponent getAccessibleComponent() {
/*  646 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Color getBackground() {
/*  659 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBackground(Color param1Color) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Color getForeground() {
/*  680 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setForeground(Color param1Color) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Cursor getCursor() {
/*  699 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setCursor(Cursor param1Cursor) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Font getFont() {
/*  721 */       return MenuComponent.this.getFont();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setFont(Font param1Font) {
/*  730 */       MenuComponent.this.setFont(param1Font);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FontMetrics getFontMetrics(Font param1Font) {
/*  742 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isEnabled() {
/*  751 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setEnabled(boolean param1Boolean) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isVisible() {
/*  773 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setVisible(boolean param1Boolean) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isShowing() {
/*  795 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Point param1Point) {
/*  808 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Point getLocationOnScreen() {
/*  818 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Point getLocation() {
/*  832 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setLocation(Point param1Point) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Rectangle getBounds() {
/*  852 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBounds(Rectangle param1Rectangle) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Dimension getSize() {
/*  879 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setSize(Dimension param1Dimension) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleAt(Point param1Point) {
/*  905 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isFocusTraversable() {
/*  914 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void requestFocus() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addFocusListener(FocusListener param1FocusListener) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeFocusListener(FocusListener param1FocusListener) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleSelectionCount() {
/*  954 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleSelection(int param1Int) {
/*  970 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isAccessibleChildSelected(int param1Int) {
/*  983 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addAccessibleSelection(int param1Int) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeAccessibleSelection(int param1Int) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void clearAccessibleSelection() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void selectAllAccessibleSelection() {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getAccessibleIndexInParent() {
/* 1037 */     MenuContainer menuContainer = this.parent;
/* 1038 */     if (!(menuContainer instanceof MenuComponent))
/*      */     {
/* 1040 */       return -1;
/*      */     }
/* 1042 */     MenuComponent menuComponent = (MenuComponent)menuContainer;
/* 1043 */     return menuComponent.getAccessibleChildIndex(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getAccessibleChildIndex(MenuComponent paramMenuComponent) {
/* 1054 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   AccessibleStateSet getAccessibleStateSet() {
/* 1065 */     return new AccessibleStateSet();
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/MenuComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */