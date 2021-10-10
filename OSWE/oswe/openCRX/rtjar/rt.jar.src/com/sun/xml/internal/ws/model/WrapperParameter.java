/*     */ package com.sun.xml.internal.ws.model;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.internal.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.internal.ws.spi.db.WrapperComposite;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.jws.WebParam;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrapperParameter
/*     */   extends ParameterImpl
/*     */ {
/*  55 */   protected final List<ParameterImpl> wrapperChildren = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public WrapperParameter(JavaMethodImpl parent, TypeInfo typeRef, WebParam.Mode mode, int index) {
/*  59 */     super(parent, typeRef, mode, index);
/*     */     
/*  61 */     typeRef.properties().put(WrapperParameter.class.getName(), this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWrapperStyle() {
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ParameterImpl> getWrapperChildren() {
/*  78 */     return this.wrapperChildren;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addWrapperChild(ParameterImpl wrapperChild) {
/*  87 */     this.wrapperChildren.add(wrapperChild);
/*  88 */     wrapperChild.wrapper = this;
/*     */     
/*  90 */     assert wrapperChild.getBinding() == ParameterBinding.BODY;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  94 */     this.wrapperChildren.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   void fillTypes(List<TypeInfo> types) {
/*  99 */     super.fillTypes(types);
/* 100 */     if (WrapperComposite.class.equals((getTypeInfo()).type))
/* 101 */       for (ParameterImpl p : this.wrapperChildren) p.fillTypes(types);  
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/WrapperParameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */