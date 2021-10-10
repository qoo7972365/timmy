package javax.sound.midi;

public interface Receiver extends AutoCloseable {
  void send(MidiMessage paramMidiMessage, long paramLong);
  
  void close();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/midi/Receiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */