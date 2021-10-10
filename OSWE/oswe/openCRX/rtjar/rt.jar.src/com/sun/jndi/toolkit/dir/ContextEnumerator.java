/*     */ package com.sun.jndi.toolkit.dir;
/*     */ 
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameParser;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContextEnumerator
/*     */   implements NamingEnumeration<Binding>
/*     */ {
/*     */   private static boolean debug = false;
/*  39 */   private NamingEnumeration<Binding> children = null;
/*  40 */   private Binding currentChild = null;
/*     */   private boolean currentReturned = false;
/*     */   private Context root;
/*  43 */   private ContextEnumerator currentChildEnum = null;
/*     */   private boolean currentChildExpanded = false;
/*     */   private boolean rootProcessed = false;
/*  46 */   private int scope = 2;
/*  47 */   private String contextName = "";
/*     */   
/*     */   public ContextEnumerator(Context paramContext) throws NamingException {
/*  50 */     this(paramContext, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContextEnumerator(Context paramContext, int paramInt) throws NamingException {
/*  56 */     this(paramContext, paramInt, "", (paramInt != 1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ContextEnumerator(Context paramContext, int paramInt, String paramString, boolean paramBoolean) throws NamingException {
/*  62 */     if (paramContext == null) {
/*  63 */       throw new IllegalArgumentException("null context passed");
/*     */     }
/*     */     
/*  66 */     this.root = paramContext;
/*     */ 
/*     */     
/*  69 */     if (paramInt != 0) {
/*  70 */       this.children = getImmediateChildren(paramContext);
/*     */     }
/*  72 */     this.scope = paramInt;
/*  73 */     this.contextName = paramString;
/*     */     
/*  75 */     this.rootProcessed = !paramBoolean;
/*  76 */     prepNextChild();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<Binding> getImmediateChildren(Context paramContext) throws NamingException {
/*  82 */     return paramContext.listBindings("");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ContextEnumerator newEnumerator(Context paramContext, int paramInt, String paramString, boolean paramBoolean) throws NamingException {
/*  88 */     return new ContextEnumerator(paramContext, paramInt, paramString, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean hasMore() throws NamingException {
/*  92 */     return (!this.rootProcessed || (this.scope != 0 && 
/*  93 */       hasMoreDescendants()));
/*     */   }
/*     */   
/*     */   public boolean hasMoreElements() {
/*     */     try {
/*  98 */       return hasMore();
/*  99 */     } catch (NamingException namingException) {
/* 100 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Binding nextElement() {
/*     */     try {
/* 106 */       return next();
/* 107 */     } catch (NamingException namingException) {
/* 108 */       throw new NoSuchElementException(namingException.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   public Binding next() throws NamingException {
/* 113 */     if (!this.rootProcessed) {
/* 114 */       this.rootProcessed = true;
/* 115 */       return new Binding("", this.root.getClass().getName(), this.root, true);
/*     */     } 
/*     */ 
/*     */     
/* 119 */     if (this.scope != 0 && hasMoreDescendants()) {
/* 120 */       return getNextDescendant();
/*     */     }
/*     */     
/* 123 */     throw new NoSuchElementException();
/*     */   }
/*     */   
/*     */   public void close() throws NamingException {
/* 127 */     this.root = null;
/*     */   }
/*     */   
/*     */   private boolean hasMoreChildren() throws NamingException {
/* 131 */     return (this.children != null && this.children.hasMore());
/*     */   }
/*     */   
/*     */   private Binding getNextChild() throws NamingException {
/* 135 */     Binding binding1 = this.children.next();
/* 136 */     Binding binding2 = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     if (binding1.isRelative() && !this.contextName.equals("")) {
/* 142 */       NameParser nameParser = this.root.getNameParser("");
/* 143 */       Name name = nameParser.parse(this.contextName);
/* 144 */       name.add(binding1.getName());
/* 145 */       if (debug) {
/* 146 */         System.out.println("ContextEnumerator: adding " + name);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 151 */       binding2 = new Binding(name.toString(), binding1.getClassName(), binding1.getObject(), binding1.isRelative());
/*     */     } else {
/* 153 */       if (debug) {
/* 154 */         System.out.println("ContextEnumerator: using old binding");
/*     */       }
/* 156 */       binding2 = binding1;
/*     */     } 
/*     */     
/* 159 */     return binding2;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasMoreDescendants() throws NamingException {
/* 164 */     if (!this.currentReturned) {
/* 165 */       if (debug) System.out.println("hasMoreDescendants returning " + ((this.currentChild != null) ? 1 : 0));
/*     */       
/* 167 */       return (this.currentChild != null);
/* 168 */     }  if (this.currentChildExpanded && this.currentChildEnum.hasMore()) {
/*     */       
/* 170 */       if (debug) System.out.println("hasMoreDescendants returning true");
/*     */ 
/*     */       
/* 173 */       return true;
/*     */     } 
/* 175 */     if (debug) System.out.println("hasMoreDescendants returning hasMoreChildren");
/*     */     
/* 177 */     return hasMoreChildren();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Binding getNextDescendant() throws NamingException {
/* 183 */     if (!this.currentReturned) {
/*     */       
/* 185 */       if (debug) System.out.println("getNextDescedant: simple case");
/*     */       
/* 187 */       this.currentReturned = true;
/* 188 */       return this.currentChild;
/*     */     } 
/* 190 */     if (this.currentChildExpanded && this.currentChildEnum.hasMore()) {
/*     */       
/* 192 */       if (debug) System.out.println("getNextDescedant: expanded case");
/*     */ 
/*     */       
/* 195 */       return this.currentChildEnum.next();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 200 */     if (debug) System.out.println("getNextDescedant: next case");
/*     */     
/* 202 */     prepNextChild();
/* 203 */     return getNextDescendant();
/*     */   }
/*     */ 
/*     */   
/*     */   private void prepNextChild() throws NamingException {
/* 208 */     if (hasMoreChildren()) {
/*     */       try {
/* 210 */         this.currentChild = getNextChild();
/* 211 */         this.currentReturned = false;
/* 212 */       } catch (NamingException namingException) {
/* 213 */         if (debug) System.out.println(namingException); 
/* 214 */         if (debug) namingException.printStackTrace(); 
/*     */       } 
/*     */     } else {
/* 217 */       this.currentChild = null;
/*     */       
/*     */       return;
/*     */     } 
/* 221 */     if (this.scope == 2 && this.currentChild
/* 222 */       .getObject() instanceof Context) {
/* 223 */       this.currentChildEnum = newEnumerator((Context)this.currentChild
/* 224 */           .getObject(), this.scope, this.currentChild
/* 225 */           .getName(), false);
/*     */       
/* 227 */       this.currentChildExpanded = true;
/* 228 */       if (debug) System.out.println("prepNextChild: expanded"); 
/*     */     } else {
/* 230 */       this.currentChildExpanded = false;
/* 231 */       this.currentChildEnum = null;
/* 232 */       if (debug) System.out.println("prepNextChild: normal"); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/dir/ContextEnumerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */