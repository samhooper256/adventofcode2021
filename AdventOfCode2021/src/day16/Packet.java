package day16;

public abstract sealed class Packet permits LiteralPacket, OperatorPacket {
	
	private final int version, typeID;
	
	public Packet(int version, int typeID) {
		this.version = version;
		this.typeID = typeID;
	}
	
	public int version() {
		return version;
	}
	
	public int typeID() {
		return typeID;
	}
	
	public abstract int versionSum();
	
	public abstract long value();
	
}