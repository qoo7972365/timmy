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
/*    */ 
/*    */ public final class DLSSoundbankReader
/*    */   extends SoundbankReader
/*    */ {
/*    */   public Soundbank getSoundbank(URL paramURL) throws InvalidMidiDataException, IOException {
/*    */     try {
/* 47 */       return new DLSSoundbank(paramURL);
/* 48 */     } catch (RIFFInvalidFormatException rIFFInvalidFormatException) {
/* 49 */       return null;
/* 50 */     } catch (IOException iOException) {
/* 51 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Soundbank getSoundbank(InputStream paramInputStream) throws InvalidMidiDataException, IOException {
/*    */     try {
/* 58 */       paramInputStream.mark(512);
/* 59 */       return new DLSSoundbank(paramInputStream);
/* 60 */     } catch (RIFFInvalidFormatException rIFFInvalidFormatException) {
/* 61 */       paramInputStream.reset();
/* 62 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Soundbank getSoundbank(File paramFile) throws InvalidMidiDataException, IOException {
/*    */     try {
/* 69 */       return new DLSSoundbank(paramFile);
/* 70 */     } catch (RIFFInvalidFormatException rIFFInvalidFormatException) {
/* 71 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/DLSSoundbankReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */