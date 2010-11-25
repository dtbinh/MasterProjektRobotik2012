package device;

import javaclient3.BlobfinderInterface;
import javaclient3.PlayerClient;
import javaclient3.PlayerException;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.blobfinder.PlayerBlobfinderBlob;

import java.util.Vector;

import data.BlobfinderBlob;

public class Blobfinder implements Runnable {
	protected BlobfinderInterface  bfi  = null;
	protected int count = 0;
	protected int[] color = null;
	protected Vector<BlobfinderBlob> blobs = null;;
	protected final int SLEEPTIME = 100;
	
	// Every class of this type has it's own thread
	public Thread thread = new Thread ( this );
	
	public Blobfinder (PlayerClient host, int id) {
		try {
			this.bfi = host.requestInterfaceBlobfinder(0, PlayerConstants.PLAYER_OPEN_MODE);
			this.blobs = new Vector<BlobfinderBlob>();
			
			// Automatically start own thread in constructor
			this.thread.start();
			System.out.println("Running "
					+ this.toString()
					+ " in thread: "
					+ this.thread.getName()
					+ " of robot "
					+ id);

		} catch ( PlayerException e ) {
			System.err.println ("Blobfinder: > Error connecting to Player: ");
			System.err.println ("    [ " + e.toString() + " ]");
			System.exit (1);
		}
	}
	// Only to be called @~10Hz
	public void update () {
		// Wait for the laser readings
		while (!this.bfi.isDataReady()) {
			try { Thread.sleep (this.SLEEPTIME); }
			catch (InterruptedException e) { this.thread.interrupt(); }
		}
		this.count = this.bfi.getData().getBlobs_count();
		
		if (this.count > 0) {
			for (int i = 0; i<this.count; i++) {
				PlayerBlobfinderBlob unblob = this.bfi.getData().getBlobs()[i];
				// envelope for new blob
				BlobfinderBlob inblob = new BlobfinderBlob(
						unblob.getColor(),
						unblob.getArea(),
						unblob.getX(),
						unblob.getY(),
						unblob.getLeft(),
						unblob.getRight(),
						unblob.getTop(),
						unblob.getBottom(),
						unblob.getRange()	);
				
				if (i >= this.blobs.size()) {
					this.blobs.add(inblob);
				} else {
					this.blobs.set(i, inblob);
				}
			}
		}
	}
	
	public Vector<BlobfinderBlob> getBlobs () {
		return this.blobs;
	}
	public int getCount () {
		return this.count;
	}
	@Override
	public void run() {
		while ( ! this.thread.isInterrupted()) {
			this.update ();
		}
		System.out.println("Shutdown of " + this.toString());
	}
}