/*     */ package com.sun.xml.internal.ws.api.message;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.binding.SOAPBindingImpl;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.BitSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HeaderList
/*     */   extends ArrayList<Header>
/*     */   implements MessageHeaders
/*     */ {
/*     */   private static final long serialVersionUID = -6358045781349627237L;
/*     */   private int understoodBits;
/* 130 */   private BitSet moreUnderstoodBits = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SOAPVersion soapVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderList(SOAPVersion soapVersion) {
/* 148 */     this.soapVersion = soapVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderList(HeaderList that) {
/* 155 */     super(that);
/* 156 */     this.understoodBits = that.understoodBits;
/* 157 */     if (that.moreUnderstoodBits != null) {
/* 158 */       this.moreUnderstoodBits = (BitSet)that.moreUnderstoodBits.clone();
/*     */     }
/*     */   }
/*     */   
/*     */   public HeaderList(MessageHeaders that) {
/* 163 */     super(that.asList());
/* 164 */     if (that instanceof HeaderList) {
/* 165 */       HeaderList hThat = (HeaderList)that;
/* 166 */       this.understoodBits = hThat.understoodBits;
/* 167 */       if (hThat.moreUnderstoodBits != null) {
/* 168 */         this.moreUnderstoodBits = (BitSet)hThat.moreUnderstoodBits.clone();
/*     */       }
/*     */     } else {
/* 171 */       Set<QName> understood = that.getUnderstoodHeaders();
/* 172 */       if (understood != null) {
/* 173 */         for (QName qname : understood) {
/* 174 */           understood(qname);
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 185 */     return super.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasHeaders() {
/* 190 */     return !isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void addAll(Header... headers) {
/* 199 */     addAll(Arrays.asList(headers));
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
/*     */   public Header get(int index) {
/* 212 */     return super.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void understood(int index) {
/* 221 */     if (index >= size()) {
/* 222 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/*     */     
/* 225 */     if (index < 32) {
/* 226 */       this.understoodBits |= 1 << index;
/*     */     } else {
/* 228 */       if (this.moreUnderstoodBits == null) {
/* 229 */         this.moreUnderstoodBits = new BitSet();
/*     */       }
/* 231 */       this.moreUnderstoodBits.set(index - 32);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnderstood(int index) {
/* 241 */     if (index >= size()) {
/* 242 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/*     */     
/* 245 */     if (index < 32) {
/* 246 */       return (this.understoodBits == (this.understoodBits | 1 << index));
/*     */     }
/* 248 */     if (this.moreUnderstoodBits == null) {
/* 249 */       return false;
/*     */     }
/* 251 */     return this.moreUnderstoodBits.get(index - 32);
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
/*     */   public void understood(@NotNull Header header) {
/* 271 */     int sz = size();
/* 272 */     for (int i = 0; i < sz; i++) {
/* 273 */       if (get(i) == header) {
/* 274 */         understood(i);
/*     */         return;
/*     */       } 
/*     */     } 
/* 278 */     throw new IllegalArgumentException();
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
/*     */   @Nullable
/*     */   public Header get(@NotNull String nsUri, @NotNull String localName, boolean markAsUnderstood) {
/* 291 */     int len = size();
/* 292 */     for (int i = 0; i < len; i++) {
/* 293 */       Header h = get(i);
/* 294 */       if (h.getLocalPart().equals(localName) && h.getNamespaceURI().equals(nsUri)) {
/* 295 */         if (markAsUnderstood) {
/* 296 */           understood(i);
/*     */         }
/* 298 */         return h;
/*     */       } 
/*     */     } 
/* 301 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Header get(String nsUri, String localName) {
/* 309 */     return get(nsUri, localName, true);
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
/*     */   @Nullable
/*     */   public Header get(@NotNull QName name, boolean markAsUnderstood) {
/* 323 */     return get(name.getNamespaceURI(), name.getLocalPart(), markAsUnderstood);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Header get(@NotNull QName name) {
/* 333 */     return get(name, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Header> getHeaders(String nsUri, String localName) {
/* 341 */     return getHeaders(nsUri, localName, true);
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
/*     */   @NotNull
/*     */   public Iterator<Header> getHeaders(@NotNull final String nsUri, @NotNull final String localName, final boolean markAsUnderstood) {
/* 358 */     return new Iterator<Header>()
/*     */       {
/* 360 */         int idx = 0;
/*     */         
/*     */         Header next;
/*     */         
/*     */         public boolean hasNext() {
/* 365 */           if (this.next == null) {
/* 366 */             fetch();
/*     */           }
/* 368 */           return (this.next != null);
/*     */         }
/*     */ 
/*     */         
/*     */         public Header next() {
/* 373 */           if (this.next == null) {
/* 374 */             fetch();
/* 375 */             if (this.next == null) {
/* 376 */               throw new NoSuchElementException();
/*     */             }
/*     */           } 
/*     */           
/* 380 */           if (markAsUnderstood) {
/* 381 */             assert HeaderList.this.get(this.idx - 1) == this.next;
/* 382 */             HeaderList.this.understood(this.idx - 1);
/*     */           } 
/*     */           
/* 385 */           Header r = this.next;
/* 386 */           this.next = null;
/* 387 */           return r;
/*     */         }
/*     */         
/*     */         private void fetch() {
/* 391 */           while (this.idx < HeaderList.this.size()) {
/* 392 */             Header h = HeaderList.this.get(this.idx++);
/* 393 */             if (h.getLocalPart().equals(localName) && h.getNamespaceURI().equals(nsUri)) {
/* 394 */               this.next = h;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 402 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Iterator<Header> getHeaders(@NotNull QName headerName, boolean markAsUnderstood) {
/* 414 */     return getHeaders(headerName.getNamespaceURI(), headerName.getLocalPart(), markAsUnderstood);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Iterator<Header> getHeaders(@NotNull String nsUri) {
/* 424 */     return getHeaders(nsUri, true);
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
/*     */   @NotNull
/*     */   public Iterator<Header> getHeaders(@NotNull final String nsUri, final boolean markAsUnderstood) {
/* 442 */     return new Iterator<Header>()
/*     */       {
/* 444 */         int idx = 0;
/*     */         
/*     */         Header next;
/*     */         
/*     */         public boolean hasNext() {
/* 449 */           if (this.next == null) {
/* 450 */             fetch();
/*     */           }
/* 452 */           return (this.next != null);
/*     */         }
/*     */ 
/*     */         
/*     */         public Header next() {
/* 457 */           if (this.next == null) {
/* 458 */             fetch();
/* 459 */             if (this.next == null) {
/* 460 */               throw new NoSuchElementException();
/*     */             }
/*     */           } 
/*     */           
/* 464 */           if (markAsUnderstood) {
/* 465 */             assert HeaderList.this.get(this.idx - 1) == this.next;
/* 466 */             HeaderList.this.understood(this.idx - 1);
/*     */           } 
/*     */           
/* 469 */           Header r = this.next;
/* 470 */           this.next = null;
/* 471 */           return r;
/*     */         }
/*     */         
/*     */         private void fetch() {
/* 475 */           while (this.idx < HeaderList.this.size()) {
/* 476 */             Header h = HeaderList.this.get(this.idx++);
/* 477 */             if (h.getNamespaceURI().equals(nsUri)) {
/* 478 */               this.next = h;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 486 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
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
/*     */   public String getTo(AddressingVersion av, SOAPVersion sv) {
/* 503 */     return AddressingUtils.getTo(this, av, sv);
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
/*     */   public String getAction(@NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 518 */     return AddressingUtils.getAction(this, av, sv);
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
/*     */   public WSEndpointReference getReplyTo(@NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 533 */     return AddressingUtils.getReplyTo(this, av, sv);
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
/*     */   public WSEndpointReference getFaultTo(@NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 548 */     return AddressingUtils.getFaultTo(this, av, sv);
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
/*     */   public String getMessageID(@NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 563 */     return AddressingUtils.getMessageID(this, av, sv);
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
/*     */   public String getRelatesTo(@NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 578 */     return AddressingUtils.getRelatesTo(this, av, sv);
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
/*     */   public void fillRequestAddressingHeaders(Packet packet, AddressingVersion av, SOAPVersion sv, boolean oneway, String action, boolean mustUnderstand) {
/* 599 */     AddressingUtils.fillRequestAddressingHeaders(this, packet, av, sv, oneway, action, mustUnderstand);
/*     */   }
/*     */   
/*     */   public void fillRequestAddressingHeaders(Packet packet, AddressingVersion av, SOAPVersion sv, boolean oneway, String action) {
/* 603 */     AddressingUtils.fillRequestAddressingHeaders(this, packet, av, sv, oneway, action);
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
/*     */   public void fillRequestAddressingHeaders(WSDLPort wsdlPort, @NotNull WSBinding binding, Packet packet) {
/* 626 */     AddressingUtils.fillRequestAddressingHeaders(this, wsdlPort, binding, packet);
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
/*     */   public boolean add(Header header) {
/* 642 */     return super.add(header);
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
/*     */   @Nullable
/*     */   public Header remove(@NotNull String nsUri, @NotNull String localName) {
/* 656 */     int len = size();
/* 657 */     for (int i = 0; i < len; i++) {
/* 658 */       Header h = get(i);
/* 659 */       if (h.getLocalPart().equals(localName) && h.getNamespaceURI().equals(nsUri)) {
/* 660 */         return remove(i);
/*     */       }
/*     */     } 
/* 663 */     return null;
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
/*     */   public boolean addOrReplace(Header header) {
/* 679 */     for (int i = 0; i < size(); i++) {
/* 680 */       Header hdr = get(i);
/* 681 */       if (hdr.getNamespaceURI().equals(header.getNamespaceURI()) && hdr
/* 682 */         .getLocalPart().equals(header.getLocalPart())) {
/*     */ 
/*     */         
/* 685 */         removeInternal(i);
/* 686 */         addInternal(i, header);
/* 687 */         return true;
/*     */       } 
/*     */     } 
/* 690 */     return add(header);
/*     */   }
/*     */ 
/*     */   
/*     */   public void replace(Header old, Header header) {
/* 695 */     for (int i = 0; i < size(); i++) {
/* 696 */       Header hdr = get(i);
/* 697 */       if (hdr.getNamespaceURI().equals(header.getNamespaceURI()) && hdr
/* 698 */         .getLocalPart().equals(header.getLocalPart())) {
/*     */ 
/*     */         
/* 701 */         removeInternal(i);
/* 702 */         addInternal(i, header);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 707 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   protected void addInternal(int index, Header header) {
/* 711 */     add(index, (E)header);
/*     */   }
/*     */   
/*     */   protected Header removeInternal(int index) {
/* 715 */     return super.remove(index);
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
/*     */   @Nullable
/*     */   public Header remove(@NotNull QName name) {
/* 729 */     return remove(name.getNamespaceURI(), name.getLocalPart());
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
/*     */   public Header remove(int index) {
/* 741 */     removeUnderstoodBit(index);
/* 742 */     return super.remove(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeUnderstoodBit(int index) {
/* 752 */     assert index < size();
/*     */     
/* 754 */     if (index < 32) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 773 */       int shiftedUpperBits = this.understoodBits >>> -31 + index << index;
/* 774 */       int lowerBits = this.understoodBits << -index >>> 31 - index >>> 1;
/* 775 */       this.understoodBits = shiftedUpperBits | lowerBits;
/*     */       
/* 777 */       if (this.moreUnderstoodBits != null && this.moreUnderstoodBits.cardinality() > 0) {
/* 778 */         if (this.moreUnderstoodBits.get(0)) {
/* 779 */           this.understoodBits |= Integer.MIN_VALUE;
/*     */         }
/*     */         
/* 782 */         this.moreUnderstoodBits.clear(0); int i;
/* 783 */         for (i = this.moreUnderstoodBits.nextSetBit(1); i > 0; i = this.moreUnderstoodBits.nextSetBit(i + 1)) {
/* 784 */           this.moreUnderstoodBits.set(i - 1);
/* 785 */           this.moreUnderstoodBits.clear(i);
/*     */         } 
/*     */       } 
/* 788 */     } else if (this.moreUnderstoodBits != null && this.moreUnderstoodBits.cardinality() > 0) {
/* 789 */       index -= 32;
/* 790 */       this.moreUnderstoodBits.clear(index);
/* 791 */       for (int i = this.moreUnderstoodBits.nextSetBit(index); i >= 1; i = this.moreUnderstoodBits.nextSetBit(i + 1)) {
/* 792 */         this.moreUnderstoodBits.set(i - 1);
/* 793 */         this.moreUnderstoodBits.clear(i);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 798 */     if (size() - 1 <= 33 && this.moreUnderstoodBits != null) {
/* 799 */       this.moreUnderstoodBits = null;
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
/*     */   public boolean remove(Object o) {
/* 818 */     if (o != null) {
/* 819 */       for (int index = 0; index < size(); index++) {
/* 820 */         if (o.equals(get(index))) {
/* 821 */           remove(index);
/* 822 */           return true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 827 */     return false;
/*     */   }
/*     */   
/*     */   public Header remove(Header h) {
/* 831 */     if (remove(h)) {
/* 832 */       return h;
/*     */     }
/* 834 */     return null;
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
/*     */   public static HeaderList copy(MessageHeaders original) {
/* 847 */     if (original == null) {
/* 848 */       return null;
/*     */     }
/* 850 */     return new HeaderList(original);
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
/*     */   public static HeaderList copy(HeaderList original) {
/* 863 */     return copy(original);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readResponseAddressingHeaders(WSDLPort wsdlPort, WSBinding binding) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void understood(QName name) {
/* 874 */     get(name, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void understood(String nsUri, String localName) {
/* 879 */     get(nsUri, localName, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<QName> getUnderstoodHeaders() {
/* 884 */     Set<QName> understoodHdrs = new HashSet<>();
/* 885 */     for (int i = 0; i < size(); i++) {
/* 886 */       if (isUnderstood(i)) {
/* 887 */         Header header = get(i);
/* 888 */         understoodHdrs.add(new QName(header.getNamespaceURI(), header.getLocalPart()));
/*     */       } 
/*     */     } 
/* 891 */     return understoodHdrs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnderstood(Header header) {
/* 897 */     return isUnderstood(header.getNamespaceURI(), header.getLocalPart());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnderstood(String nsUri, String localName) {
/* 902 */     for (int i = 0; i < size(); i++) {
/* 903 */       Header h = get(i);
/* 904 */       if (h.getLocalPart().equals(localName) && h.getNamespaceURI().equals(nsUri)) {
/* 905 */         return isUnderstood(i);
/*     */       }
/*     */     } 
/* 908 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnderstood(QName name) {
/* 913 */     return isUnderstood(name.getNamespaceURI(), name.getLocalPart());
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<QName> getNotUnderstoodHeaders(Set<String> roles, Set<QName> knownHeaders, WSBinding binding) {
/* 918 */     Set<QName> notUnderstoodHeaders = null;
/* 919 */     if (roles == null) {
/* 920 */       roles = new HashSet<>();
/*     */     }
/* 922 */     SOAPVersion effectiveSoapVersion = getEffectiveSOAPVersion(binding);
/* 923 */     roles.add(effectiveSoapVersion.implicitRole);
/* 924 */     for (int i = 0; i < size(); i++) {
/* 925 */       if (!isUnderstood(i)) {
/* 926 */         Header header = get(i);
/* 927 */         if (!header.isIgnorable(effectiveSoapVersion, roles)) {
/* 928 */           QName qName = new QName(header.getNamespaceURI(), header.getLocalPart());
/* 929 */           if (binding == null) {
/*     */ 
/*     */ 
/*     */             
/* 933 */             if (notUnderstoodHeaders == null) {
/* 934 */               notUnderstoodHeaders = new HashSet<>();
/*     */             }
/* 936 */             notUnderstoodHeaders.add(qName);
/*     */           
/*     */           }
/* 939 */           else if (binding instanceof SOAPBindingImpl && !((SOAPBindingImpl)binding).understandsHeader(qName) && 
/* 940 */             !knownHeaders.contains(qName)) {
/*     */             
/* 942 */             if (notUnderstoodHeaders == null) {
/* 943 */               notUnderstoodHeaders = new HashSet<>();
/*     */             }
/* 945 */             notUnderstoodHeaders.add(qName);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 952 */     return notUnderstoodHeaders;
/*     */   }
/*     */   
/*     */   private SOAPVersion getEffectiveSOAPVersion(WSBinding binding) {
/* 956 */     SOAPVersion mySOAPVersion = (this.soapVersion != null) ? this.soapVersion : binding.getSOAPVersion();
/* 957 */     if (mySOAPVersion == null) {
/* 958 */       mySOAPVersion = SOAPVersion.SOAP_11;
/*     */     }
/* 960 */     return mySOAPVersion;
/*     */   }
/*     */   
/*     */   public void setSoapVersion(SOAPVersion soapVersion) {
/* 964 */     this.soapVersion = soapVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Header> getHeaders() {
/* 969 */     return iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Header> asList() {
/* 974 */     return this;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public HeaderList() {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/HeaderList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */