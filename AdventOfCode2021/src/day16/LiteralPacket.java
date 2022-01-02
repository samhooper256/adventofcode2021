package day16;

public final class LiteralPacket extends Packet {

	private final long value;
	
	public LiteralPacket(long value, int version) {
		super(version, 4);
		this.value = value;
	}
	
	@Override
	public long value() {
		return value;
	}
	
	@Override
	public int versionSum() {
		return version();
	}

	@Override
	public String toString() {
		return String.format("(%d, ver=%d)", value, version());
	}
	
}
