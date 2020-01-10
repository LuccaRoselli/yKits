package com.luccadev.br.constructors;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

public class Server {

	String adress;
	int port;
	String general_getter;

	@SuppressWarnings("resource")
	public Server(String ip, int porta){
		this.adress = ip;
		this.port = porta;
		try {
			Socket socket = new Socket();
			OutputStream outputStream;
			DataOutputStream dataOutputStream;
			InputStream inputStream;
			InputStreamReader inputStreamReader;

			socket.setSoTimeout(2500);

			socket.connect(new InetSocketAddress(adress, port), 2500);

			outputStream = socket.getOutputStream();
			dataOutputStream = new DataOutputStream(outputStream);

			inputStream = socket.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-16BE"));

			dataOutputStream.write(new byte[] { (byte) 0xFE, (byte) 0x01 });

			int packetId = inputStream.read();

			if (packetId == -1) {
				throw new IOException("Premature end of stream.");
			}

			if (packetId != 0xFF) {
				throw new IOException("Invalid packet ID (" + packetId + ").");
			}

			int length = inputStreamReader.read();

			if (length == -1) {
				throw new IOException("Premature end of stream.");
			}

			if (length == 0) {
				throw new IOException("Invalid string length.");
			}

			char[] chars = new char[length];

			if (inputStreamReader.read(chars, 0, length) != length) {
				throw new IOException("Premature end of stream.");
			}

	       this.general_getter = new String(chars);
		} catch (Exception e) {
			//ndeu
		}
	}
	
	public int getOnlinePlayers(){
		if (this.general_getter.startsWith("§")) {
			String[] data = this.general_getter.split("\0");
			return Integer.parseInt(data[4]);
		} else {
			String[] data = this.general_getter.split("§");
			return Integer.parseInt(data[1]);
		}
	}

	public int getMaxOnlinePlayers(){
		if (this.general_getter.startsWith("§")) {
			String[] data = this.general_getter.split("\0");
			return Integer.parseInt(data[5]);
		} else {
			String[] data = this.general_getter.split("§");
			return Integer.parseInt(data[2]);
		}
	}
	
	public String getMotd(){
        String[] data = this.general_getter.split("\0");
        String serverMotd = data[3];
        return serverMotd;
	}
}
