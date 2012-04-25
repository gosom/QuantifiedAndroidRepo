package quantifiedAndroid.utilities;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SoundProccess {
	
	public static short getShortFromBytes(byte firstByte, byte secondByte){
		
		ByteBuffer bb = ByteBuffer.allocate(2);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put(firstByte);
		bb.put(secondByte);
		short shortVal = bb.getShort(0);
		
		return shortVal;
	}
	
	public static int byteArrayToInt(byte b[])
	{
	  int start = 0;
	  byte firstbyte = b[start];
	  byte secondbyte = b[start+1];
	  
	  int low = firstbyte & 0xff;
	  int high = secondbyte & 0xff;
	  return (int)( high << 8 | low );
	}
	

}
