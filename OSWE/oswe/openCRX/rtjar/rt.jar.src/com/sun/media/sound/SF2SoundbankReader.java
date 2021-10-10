/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import javax.sound.midi.InvalidMidiDataException;
/*    */ import javax.sound.midi.Soundbank;
/*    */ import javax.sound.midi.spi.SoundbankReader;
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
/*    */ public final class SF2SoundbankReader
/*    */   extends SoundbankReader
/*    */ {
/*    */   public Soundbank getSoundbank(URL paramURL) throws InvalidMidiDataException, IOException {
/*    */     try {
/* 46 */       return new SF2Soundbank(paramURL);
/* 47 */     } catch (RIFFInvalidFormatException rIFFInvalidFormatException) {
/* 48 */       return null;
/* 49 */     } catch (IOException iOException) {
/* 50 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Soundbank getSoundbank(InputStream paramInputStream) throws InvalidMidiDataException, IOException {
/*    */     try {
/* 57 */       paramInputStream.mark(512);
/* 58 */       return new SF2Soundbank(paramInputStream);
/* 59 */     } catch (RIFFInvalidFormatException rIFFInvalidFormatException) {
/* 60 */       paramInputStream.reset();
/* 61 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Soundbank getSoundbank(File paramFile) throws InvalidMidiDataException, IOException {
/*    */     try {
/* 68 */       return new SF2Soundbank(paramFile);
/* 69 */     } catch (RIFFInvalidFormatException rIFFInvalidFormatException) {
/* 70 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SF2SoundbankReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */