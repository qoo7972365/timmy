package javax.sound.midi;

public interface Soundbank {
  String getName();
  
  String getVersion();
  
  String getVendor();
  
  String getDescription();
  
  SoundbankResource[] getResources();
  
  Instrument[] getInstruments();
  
  Instrument getInstrument(Patch paramPatch);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/midi/Soundbank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */