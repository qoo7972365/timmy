/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DistinctOps
/*     */ {
/*     */   static <T> ReferencePipeline<T, T> makeRef(AbstractPipeline<?, T, ?> paramAbstractPipeline) {
/*  55 */     return new ReferencePipeline.StatefulOp<T, T>(paramAbstractPipeline, StreamShape.REFERENCE, StreamOpFlag.IS_DISTINCT | StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/*     */         <P_IN> Node<T> reduce(PipelineHelper<T> param1PipelineHelper, Spliterator<P_IN> param1Spliterator)
/*     */         {
/*  62 */           TerminalOp<?, ?> terminalOp = ReduceOps.makeRef(java.util.LinkedHashSet::new, HashSet::add, AbstractCollection::addAll);
/*     */           
/*  64 */           return Nodes.node((Collection<T>)terminalOp.<P_IN>evaluateParallel(param1PipelineHelper, param1Spliterator));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         <P_IN> Node<T> opEvaluateParallel(PipelineHelper<T> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<T[]> param1IntFunction) {
/*     */           HashSet<T> hashSet;
/*  71 */           if (StreamOpFlag.DISTINCT.isKnown(param1PipelineHelper.getStreamAndOpFlags()))
/*     */           {
/*  73 */             return param1PipelineHelper.evaluate(param1Spliterator, false, param1IntFunction);
/*     */           }
/*  75 */           if (StreamOpFlag.ORDERED.isKnown(param1PipelineHelper.getStreamAndOpFlags())) {
/*  76 */             return reduce(param1PipelineHelper, param1Spliterator);
/*     */           }
/*     */ 
/*     */           
/*  80 */           AtomicBoolean atomicBoolean = new AtomicBoolean(false);
/*  81 */           ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>();
/*  82 */           TerminalOp<?, Void> terminalOp = ForEachOps.makeRef(param1Object -> { if (param1Object == null) { param1AtomicBoolean.set(true); } else { param1ConcurrentHashMap.putIfAbsent(param1Object, Boolean.TRUE); }  }false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  88 */           terminalOp.evaluateParallel(param1PipelineHelper, param1Spliterator);
/*     */ 
/*     */ 
/*     */           
/*  92 */           ConcurrentHashMap.KeySetView<Object, Object> keySetView = concurrentHashMap.keySet();
/*  93 */           if (atomicBoolean.get()) {
/*     */             
/*  95 */             hashSet = new HashSet(keySetView);
/*  96 */             hashSet.add(null);
/*     */           } 
/*  98 */           return Nodes.node(hashSet);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         <P_IN> Spliterator<T> opEvaluateParallelLazy(PipelineHelper<T> param1PipelineHelper, Spliterator<P_IN> param1Spliterator) {
/* 104 */           if (StreamOpFlag.DISTINCT.isKnown(param1PipelineHelper.getStreamAndOpFlags()))
/*     */           {
/* 106 */             return param1PipelineHelper.wrapSpliterator(param1Spliterator);
/*     */           }
/* 108 */           if (StreamOpFlag.ORDERED.isKnown(param1PipelineHelper.getStreamAndOpFlags()))
/*     */           {
/* 110 */             return reduce(param1PipelineHelper, param1Spliterator).spliterator();
/*     */           }
/*     */ 
/*     */           
/* 114 */           return new StreamSpliterators.DistinctSpliterator<>(param1PipelineHelper.wrapSpliterator(param1Spliterator));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         Sink<T> opWrapSink(int param1Int, Sink<T> param1Sink) {
/* 120 */           Objects.requireNonNull(param1Sink);
/*     */           
/* 122 */           if (StreamOpFlag.DISTINCT.isKnown(param1Int))
/* 123 */             return param1Sink; 
/* 124 */           if (StreamOpFlag.SORTED.isKnown(param1Int)) {
/* 125 */             return new Sink.ChainedReference<T, T>(param1Sink)
/*     */               {
/*     */                 boolean seenNull;
/*     */                 T lastSeen;
/*     */                 
/*     */                 public void begin(long param2Long) {
/* 131 */                   this.seenNull = false;
/* 132 */                   this.lastSeen = null;
/* 133 */                   this.downstream.begin(-1L);
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public void end() {
/* 138 */                   this.seenNull = false;
/* 139 */                   this.lastSeen = null;
/* 140 */                   this.downstream.end();
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public void accept(T param2T) {
/* 145 */                   if (param2T == null) {
/* 146 */                     if (!this.seenNull) {
/* 147 */                       this.seenNull = true;
/* 148 */                       this.downstream.accept(this.lastSeen = null);
/*     */                     } 
/* 150 */                   } else if (this.lastSeen == null || !param2T.equals(this.lastSeen)) {
/* 151 */                     this.downstream.accept(this.lastSeen = param2T);
/*     */                   } 
/*     */                 }
/*     */               };
/*     */           }
/* 156 */           return new Sink.ChainedReference<T, T>(param1Sink)
/*     */             {
/*     */               Set<T> seen;
/*     */               
/*     */               public void begin(long param2Long) {
/* 161 */                 this.seen = new HashSet<>();
/* 162 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void end() {
/* 167 */                 this.seen = null;
/* 168 */                 this.downstream.end();
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(T param2T) {
/* 173 */                 if (!this.seen.contains(param2T)) {
/* 174 */                   this.seen.add(param2T);
/* 175 */                   this.downstream.accept(param2T);
/*     */                 } 
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/DistinctOps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */