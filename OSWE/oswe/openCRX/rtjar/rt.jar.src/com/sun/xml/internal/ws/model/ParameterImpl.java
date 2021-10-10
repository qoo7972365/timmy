/*     */ package com.sun.xml.internal.ws.model;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.bind.api.TypeReference;
/*     */ import com.sun.xml.internal.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.internal.ws.api.model.Parameter;
/*     */ import com.sun.xml.internal.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.spi.db.RepeatedElementBridge;
/*     */ import com.sun.xml.internal.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.internal.ws.spi.db.WrapperComposite;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import java.util.List;
/*     */ import javax.jws.WebParam;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.Holder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParameterImpl
/*     */   implements Parameter
/*     */ {
/*     */   private ParameterBinding binding;
/*     */   private ParameterBinding outBinding;
/*     */   private String partName;
/*     */   private final int index;
/*     */   private final WebParam.Mode mode;
/*     */   private TypeReference typeReference;
/*     */   private TypeInfo typeInfo;
/*     */   private QName name;
/*     */   private final JavaMethodImpl parent;
/*     */   WrapperParameter wrapper;
/*     */   TypeInfo itemTypeInfo;
/*     */   
/*     */   public ParameterImpl(JavaMethodImpl parent, TypeInfo type, WebParam.Mode mode, int index) {
/*  74 */     assert type != null;
/*     */     
/*  76 */     this.typeInfo = type;
/*  77 */     this.name = type.tagName;
/*  78 */     this.mode = mode;
/*  79 */     this.index = index;
/*  80 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public AbstractSEIModelImpl getOwner() {
/*  84 */     return this.parent.owner;
/*     */   }
/*     */   
/*     */   public JavaMethod getParent() {
/*  88 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/*  95 */     return this.name;
/*     */   }
/*     */   
/*     */   public XMLBridge getXMLBridge() {
/*  99 */     return getOwner().getXMLBridge(this.typeInfo);
/*     */   }
/*     */   
/*     */   public XMLBridge getInlinedRepeatedElementBridge() {
/* 103 */     TypeInfo itemType = getItemType();
/* 104 */     if (itemType != null) {
/* 105 */       XMLBridge xb = getOwner().getXMLBridge(itemType);
/* 106 */       if (xb != null) return (XMLBridge)new RepeatedElementBridge(this.typeInfo, xb); 
/*     */     } 
/* 108 */     return null;
/*     */   }
/*     */   
/*     */   public TypeInfo getItemType() {
/* 112 */     if (this.itemTypeInfo != null) return this.itemTypeInfo;
/*     */     
/* 114 */     if (this.parent.getBinding().isRpcLit() || this.wrapper == null) return null;
/*     */     
/* 116 */     if (!WrapperComposite.class.equals((this.wrapper.getTypeInfo()).type)) return null; 
/* 117 */     if (!getBinding().isBody()) return null; 
/* 118 */     this.itemTypeInfo = this.typeInfo.getItemType();
/* 119 */     return this.itemTypeInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bridge getBridge() {
/* 124 */     return getOwner().getBridge(this.typeReference);
/*     */   }
/*     */   
/*     */   protected Bridge getBridge(TypeReference typeRef) {
/* 128 */     return getOwner().getBridge(typeRef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeReference getTypeReference() {
/* 138 */     return this.typeReference;
/*     */   }
/*     */   public TypeInfo getTypeInfo() {
/* 141 */     return this.typeInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setTypeReference(TypeReference type) {
/* 150 */     this.typeReference = type;
/* 151 */     this.name = type.tagName;
/*     */   }
/*     */ 
/*     */   
/*     */   public WebParam.Mode getMode() {
/* 156 */     return this.mode;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/* 160 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWrapperStyle() {
/* 167 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isReturnValue() {
/* 171 */     return (this.index == -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterBinding getBinding() {
/* 178 */     if (this.binding == null)
/* 179 */       return ParameterBinding.BODY; 
/* 180 */     return this.binding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBinding(ParameterBinding binding) {
/* 187 */     this.binding = binding;
/*     */   }
/*     */   
/*     */   public void setInBinding(ParameterBinding binding) {
/* 191 */     this.binding = binding;
/*     */   }
/*     */   
/*     */   public void setOutBinding(ParameterBinding binding) {
/* 195 */     this.outBinding = binding;
/*     */   }
/*     */   
/*     */   public ParameterBinding getInBinding() {
/* 199 */     return this.binding;
/*     */   }
/*     */   
/*     */   public ParameterBinding getOutBinding() {
/* 203 */     if (this.outBinding == null)
/* 204 */       return this.binding; 
/* 205 */     return this.outBinding;
/*     */   }
/*     */   
/*     */   public boolean isIN() {
/* 209 */     return (this.mode == WebParam.Mode.IN);
/*     */   }
/*     */   
/*     */   public boolean isOUT() {
/* 213 */     return (this.mode == WebParam.Mode.OUT);
/*     */   }
/*     */   
/*     */   public boolean isINOUT() {
/* 217 */     return (this.mode == WebParam.Mode.INOUT);
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
/*     */   public boolean isResponse() {
/* 229 */     return (this.index == -1);
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
/*     */   public Object getHolderValue(Object obj) {
/* 241 */     if (obj != null && obj instanceof Holder)
/* 242 */       return ((Holder)obj).value; 
/* 243 */     return obj;
/*     */   }
/*     */   
/*     */   public String getPartName() {
/* 247 */     if (this.partName == null)
/* 248 */       return this.name.getLocalPart(); 
/* 249 */     return this.partName;
/*     */   }
/*     */   
/*     */   public void setPartName(String partName) {
/* 253 */     this.partName = partName;
/*     */   }
/*     */   
/*     */   void fillTypes(List<TypeInfo> types) {
/* 257 */     TypeInfo itemType = getItemType();
/* 258 */     types.add((itemType != null) ? itemType : getTypeInfo());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/ParameterImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */