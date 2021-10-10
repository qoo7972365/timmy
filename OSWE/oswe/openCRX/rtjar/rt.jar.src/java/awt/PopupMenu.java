/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.peer.PopupMenuPeer;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import sun.awt.AWTAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PopupMenu
/*     */   extends Menu
/*     */ {
/*     */   private static final String base = "popup";
/*  49 */   static int nameCounter = 0;
/*     */   transient boolean isTrayIconPopup = false;
/*     */   private static final long serialVersionUID = -4620452533522760060L;
/*     */   
/*     */   static {
/*  54 */     AWTAccessor.setPopupMenuAccessor(new AWTAccessor.PopupMenuAccessor()
/*     */         {
/*     */           public boolean isTrayIconPopup(PopupMenu param1PopupMenu) {
/*  57 */             return param1PopupMenu.isTrayIconPopup;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public PopupMenu() throws HeadlessException {
/*  74 */     this("");
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
/*     */   public PopupMenu(String paramString) throws HeadlessException {
/*  87 */     super(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuContainer getParent() {
/*  94 */     if (this.isTrayIconPopup) {
/*  95 */       return null;
/*     */     }
/*  97 */     return super.getParent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String constructComponentName() {
/* 105 */     synchronized (PopupMenu.class) {
/* 106 */       return "popup" + nameCounter++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotify() {
/* 116 */     synchronized (getTreeLock()) {
/*     */ 
/*     */       
/* 119 */       if (this.parent != null && !(this.parent instanceof Component)) {
/* 120 */         super.addNotify();
/*     */       } else {
/*     */         
/* 123 */         if (this.peer == null)
/* 124 */           this.peer = Toolkit.getDefaultToolkit().createPopupMenu(this); 
/* 125 */         int i = getItemCount();
/* 126 */         for (byte b = 0; b < i; b++) {
/* 127 */           MenuItem menuItem = getItem(b);
/* 128 */           menuItem.parent = this;
/* 129 */           menuItem.addNotify();
/*     */         } 
/*     */       } 
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
/*     */   public void show(Component paramComponent, int paramInt1, int paramInt2) {
/* 158 */     MenuContainer menuContainer = this.parent;
/* 159 */     if (menuContainer == null) {
/* 160 */       throw new NullPointerException("parent is null");
/*     */     }
/* 162 */     if (!(menuContainer instanceof Component)) {
/* 163 */       throw new IllegalArgumentException("PopupMenus with non-Component parents cannot be shown");
/*     */     }
/*     */     
/* 166 */     Component component = (Component)menuContainer;
/*     */ 
/*     */ 
/*     */     
/* 170 */     if (component != paramComponent) {
/* 171 */       if (component instanceof Container) {
/* 172 */         if (!((Container)component).isAncestorOf(paramComponent)) {
/* 173 */           throw new IllegalArgumentException("origin not in parent's hierarchy");
/*     */         }
/*     */       } else {
/* 176 */         throw new IllegalArgumentException("origin not in parent's hierarchy");
/*     */       } 
/*     */     }
/* 179 */     if (component.getPeer() == null || !component.isShowing()) {
/* 180 */       throw new RuntimeException("parent not showing on screen");
/*     */     }
/* 182 */     if (this.peer == null) {
/* 183 */       addNotify();
/*     */     }
/* 185 */     synchronized (getTreeLock()) {
/* 186 */       if (this.peer != null) {
/* 187 */         ((PopupMenuPeer)this.peer).show(new Event(paramComponent, 0L, 501, paramInt1, paramInt2, 0, 0));
/*     */       }
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
/*     */   public AccessibleContext getAccessibleContext() {
/* 207 */     if (this.accessibleContext == null) {
/* 208 */       this.accessibleContext = new AccessibleAWTPopupMenu();
/*     */     }
/* 210 */     return this.accessibleContext;
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
/*     */   protected class AccessibleAWTPopupMenu
/*     */     extends Menu.AccessibleAWTMenu
/*     */   {
/*     */     private static final long serialVersionUID = -4282044795947239955L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleRole getAccessibleRole() {
/* 236 */       return AccessibleRole.POPUP_MENU;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/PopupMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */