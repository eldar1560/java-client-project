package presenter;

import java.io.Serializable;

public class Properties implements Serializable{
	
	private static final long serialVersionUID = 42L;
	int x;
	int y;
	int z;
	String name;
	String uc;
	String ip;
	int port;

	public Properties() {
		this.x = 6;
		this.y = 6;
		this.z = 6;
		this.name = "Maze1";
		this.uc = "CLI";
		this.port = 5400;
		this.ip = "127.0.0.1";
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUc() {
		return uc;
	}

	public void setUc(String uc) {
		this.uc = uc;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}
