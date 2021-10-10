/*    */ package com.sun.org.apache.xerces.internal.parsers;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.DTDDVFactory;
/*    */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class XMLGrammarParser
/*    */   extends XMLParser
/*    */ {
/*    */   protected DTDDVFactory fDatatypeValidatorFactory;
/*    */   
/*    */   protected XMLGrammarParser(SymbolTable symbolTable) {
/* 50 */     super(new XIncludeAwareParserConfiguration());
/* 51 */     this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", symbolTable);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/parsers/XMLGrammarParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */