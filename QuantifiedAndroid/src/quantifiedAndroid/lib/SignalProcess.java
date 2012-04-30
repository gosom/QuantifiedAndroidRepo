package quantifiedAndroid.lib;

public class SignalProcess {
	
	public  double calculatePowerDb(short[] sdata, int off, int samples) {
	    // Calculate the sum of the values, and the sum of the squared values.
	    // We need longs to avoid running out of bits.
	    double sum = 0;
	    double sqsum = 0;
	    for (int i = 0; i < samples; i++) {
	        final long v = sdata[off + i];
	        sum += v;
	        sqsum += v * v;
	    }
	    
	    // sqsum is the sum of all (signal+bias)², so
	    //     sqsum = sum(signal²) + samples * bias²
	    // hence
	    //     sum(signal²) = sqsum - samples * bias²
	    // Bias is simply the average value, i.e.
	    //     bias = sum / samples
	    // Since power = sum(signal²) / samples, we have
	    //     power = (sqsum - samples * sum² / samples²) / samples
	    // so
	    //     power = (sqsum - sum² / samples) / samples
	    double power = (sqsum - sum * sum / samples) / samples;
	
	    // Scale to the range 0 - 1.
	    power /= MAX_16_BIT * MAX_16_BIT;
	
	    // Convert to dB, with 0 being max power.  Add a fudge factor to make
	    // a "real" fully saturated input come to 0 dB.
	        return Math.log10(power) * 10f + FUDGE;
	    }
	
	
	/* PRIVATE VARIABLES */
	
	  // Maximum signal amplitude for 16-bit data.
	private static final float MAX_16_BIT = 32768;
	
	// This fudge factor is added to the output to make a realistically
	// fully-saturated signal come to 0dB.  Without it, the signal would
	// have to be solid samples of -32768 to read zero, which is not
	// realistic.  This really is a fudge, because the best value depends
	// on the input frequency and sampling rate.  We optimise here for
	// a 1kHz signal at 16,000 samples/sec.
	private static final float FUDGE = 0.6f;
  

}
