/*     */ package java.awt;
/*     */ 
/*     */ import sun.awt.AppContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class ModalEventFilter
/*     */   implements EventFilter
/*     */ {
/*     */   protected Dialog modalDialog;
/*     */   protected boolean disabled;
/*     */   
/*     */   protected ModalEventFilter(Dialog paramDialog) {
/*  37 */     this.modalDialog = paramDialog;
/*  38 */     this.disabled = false;
/*     */   }
/*     */   
/*     */   Dialog getModalDialog() {
/*  42 */     return this.modalDialog;
/*     */   }
/*     */   
/*     */   public EventFilter.FilterAction acceptEvent(AWTEvent paramAWTEvent) {
/*  46 */     if (this.disabled || !this.modalDialog.isVisible()) {
/*  47 */       return EventFilter.FilterAction.ACCEPT;
/*     */     }
/*  49 */     int i = paramAWTEvent.getID();
/*  50 */     if ((i >= 500 && i <= 507) || (i >= 1001 && i <= 1001) || i == 201) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  56 */       Object object = paramAWTEvent.getSource();
/*  57 */       if (!(object instanceof sun.awt.ModalExclude))
/*     */       {
/*     */         
/*  60 */         if (object instanceof Component) {
/*  61 */           Component component = (Component)object;
/*  62 */           while (component != null && !(component instanceof Window)) {
/*  63 */             component = component.getParent_NoClientCode();
/*     */           }
/*  65 */           if (component != null)
/*  66 */             return acceptWindow((Window)component); 
/*     */         } 
/*     */       }
/*     */     } 
/*  70 */     return EventFilter.FilterAction.ACCEPT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract EventFilter.FilterAction acceptWindow(Window paramWindow);
/*     */ 
/*     */ 
/*     */   
/*     */   void disable() {
/*  81 */     this.disabled = true;
/*     */   }
/*     */   
/*     */   int compareTo(ModalEventFilter paramModalEventFilter) {
/*  85 */     Dialog dialog1 = paramModalEventFilter.getModalDialog();
/*     */ 
/*     */     
/*  88 */     Dialog dialog2 = this.modalDialog;
/*  89 */     while (dialog2 != null) {
/*  90 */       if (dialog2 == dialog1) {
/*  91 */         return 1;
/*     */       }
/*  93 */       Container container = dialog2.getParent_NoClientCode();
/*     */     } 
/*  95 */     dialog2 = dialog1;
/*  96 */     while (dialog2 != null) {
/*  97 */       if (dialog2 == this.modalDialog) {
/*  98 */         return -1;
/*     */       }
/* 100 */       Container container = dialog2.getParent_NoClientCode();
/*     */     } 
/*     */     
/* 103 */     Dialog dialog3 = this.modalDialog.getModalBlocker();
/* 104 */     while (dialog3 != null) {
/* 105 */       if (dialog3 == dialog1) {
/* 106 */         return -1;
/*     */       }
/* 108 */       dialog3 = dialog3.getModalBlocker();
/*     */     } 
/* 110 */     dialog3 = dialog1.getModalBlocker();
/* 111 */     while (dialog3 != null) {
/* 112 */       if (dialog3 == this.modalDialog) {
/* 113 */         return 1;
/*     */       }
/* 115 */       dialog3 = dialog3.getModalBlocker();
/*     */     } 
/*     */     
/* 118 */     return this.modalDialog.getModalityType().compareTo(dialog1.getModalityType());
/*     */   }
/*     */   
/*     */   static ModalEventFilter createFilterForDialog(Dialog paramDialog) {
/* 122 */     switch (paramDialog.getModalityType()) { case DOCUMENT_MODAL:
/* 123 */         return new DocumentModalEventFilter(paramDialog);
/* 124 */       case APPLICATION_MODAL: return new ApplicationModalEventFilter(paramDialog);
/* 125 */       case TOOLKIT_MODAL: return new ToolkitModalEventFilter(paramDialog); }
/*     */     
/* 127 */     return null;
/*     */   }
/*     */   
/*     */   private static class ToolkitModalEventFilter
/*     */     extends ModalEventFilter {
/*     */     private AppContext appContext;
/*     */     
/*     */     ToolkitModalEventFilter(Dialog param1Dialog) {
/* 135 */       super(param1Dialog);
/* 136 */       this.appContext = param1Dialog.appContext;
/*     */     }
/*     */     
/*     */     protected EventFilter.FilterAction acceptWindow(Window param1Window) {
/* 140 */       if (param1Window.isModalExcluded(Dialog.ModalExclusionType.TOOLKIT_EXCLUDE)) {
/* 141 */         return EventFilter.FilterAction.ACCEPT;
/*     */       }
/* 143 */       if (param1Window.appContext != this.appContext) {
/* 144 */         return EventFilter.FilterAction.REJECT;
/*     */       }
/* 146 */       while (param1Window != null) {
/* 147 */         if (param1Window == this.modalDialog) {
/* 148 */           return EventFilter.FilterAction.ACCEPT_IMMEDIATELY;
/*     */         }
/* 150 */         param1Window = param1Window.getOwner();
/*     */       } 
/* 152 */       return EventFilter.FilterAction.REJECT;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ApplicationModalEventFilter
/*     */     extends ModalEventFilter {
/*     */     private AppContext appContext;
/*     */     
/*     */     ApplicationModalEventFilter(Dialog param1Dialog) {
/* 161 */       super(param1Dialog);
/* 162 */       this.appContext = param1Dialog.appContext;
/*     */     }
/*     */     
/*     */     protected EventFilter.FilterAction acceptWindow(Window param1Window) {
/* 166 */       if (param1Window.isModalExcluded(Dialog.ModalExclusionType.APPLICATION_EXCLUDE)) {
/* 167 */         return EventFilter.FilterAction.ACCEPT;
/*     */       }
/* 169 */       if (param1Window.appContext == this.appContext) {
/* 170 */         while (param1Window != null) {
/* 171 */           if (param1Window == this.modalDialog) {
/* 172 */             return EventFilter.FilterAction.ACCEPT_IMMEDIATELY;
/*     */           }
/* 174 */           param1Window = param1Window.getOwner();
/*     */         } 
/* 176 */         return EventFilter.FilterAction.REJECT;
/*     */       } 
/* 178 */       return EventFilter.FilterAction.ACCEPT;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DocumentModalEventFilter
/*     */     extends ModalEventFilter {
/*     */     private Window documentRoot;
/*     */     
/*     */     DocumentModalEventFilter(Dialog param1Dialog) {
/* 187 */       super(param1Dialog);
/* 188 */       this.documentRoot = param1Dialog.getDocumentRoot();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected EventFilter.FilterAction acceptWindow(Window param1Window) {
/* 194 */       if (param1Window.isModalExcluded(Dialog.ModalExclusionType.APPLICATION_EXCLUDE)) {
/* 195 */         Window window = this.modalDialog.getOwner();
/* 196 */         while (window != null) {
/* 197 */           if (window == param1Window) {
/* 198 */             return EventFilter.FilterAction.REJECT;
/*     */           }
/* 200 */           window = window.getOwner();
/*     */         } 
/* 202 */         return EventFilter.FilterAction.ACCEPT;
/*     */       } 
/* 204 */       while (param1Window != null) {
/* 205 */         if (param1Window == this.modalDialog) {
/* 206 */           return EventFilter.FilterAction.ACCEPT_IMMEDIATELY;
/*     */         }
/* 208 */         if (param1Window == this.documentRoot) {
/* 209 */           return EventFilter.FilterAction.REJECT;
/*     */         }
/* 211 */         param1Window = param1Window.getOwner();
/*     */       } 
/* 213 */       return EventFilter.FilterAction.ACCEPT;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/ModalEventFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */