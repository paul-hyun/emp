/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_plug.icmp;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.savarese.rocksaw.net.RawSocket;

/**
 * <p>
 * ICMP 통신 채널
 * </p>
 *
 * @since 1.6
 * @create 2015. 3. 30.
 * @modified 2015. 3. 30.
 * @author cchyun
 *
 */
public final class Plug4ICMPClient {

	private class ICMPPair {

		public ICMPEchoPacket sendPacket;

		public long sendNano;

		public ICMPEchoPacket recvPacket;

		public long recvNano;

		public boolean isSuccess() {
			return sendPacket != null && recvPacket != null;
		}

		public int getResponse_time() {
			if (isSuccess()) {
				return (int) Math.max(0L, (recvNano - sendNano) / 1000000);
			} else {
				return -1;
			}
		}

	}

	private class ReceiveThread implements Runnable {

		@Override
		public void run() {
			try {
				Plug4ICMPClient.this.sendPacket(InetAddress.getLocalHost());
			} catch (Throwable e) {
				e.printStackTrace();
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, e);
				}
				System.exit(1);
			}

			int error_count = 0;

			while (true) {
				try {
					ICMPPair icmpPair = Plug4ICMPClient.this.recvPacket();
					error_count = 0;
					if (icmpPair != null) {
						synchronized (icmpPair) {
							icmpPair.notifyAll();
						}
					}
				} catch (Exception e) {
					if (1024 < error_count) {
						error_count = 0;

						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Fatal, null, e);
						}
					} else {
						error_count++;
					}
				}
			}
		}

	}

	private static Plug4ICMPClient instance;

	private static ReentrantLock lock = new ReentrantLock();

	public static Plug4ICMPClient getInstance() {
		if (instance == null) {
			lock.lock();
			try {
				if (instance == null) {
					instance = new Plug4ICMPClient();
				}
			} finally {
				lock.unlock();
			}
		}
		return instance;
	}

	private static AtomicInteger SEQUENCE = new AtomicInteger(0);

	private RawSocket socket;

	private static String CACHE_KEY = Plug4ICMPClient.class.getName();
	static {
		UtilCache.buildCache(CACHE_KEY, 20480, 30);
	}

	private static final BlackBox blackBox = new BlackBox(Plug4ICMPClient.class);

	private Plug4ICMPClient() {
		try {
			socket = new RawSocket();
			socket.open(RawSocket.PF_INET, RawSocket.getProtocolByName("icmp"));

			Thread thread = new Thread(new ReceiveThread(), "Plug4IcmpClient::ReceiveThread");
			thread.setDaemon(true);
			thread.start();

			Thread.sleep(1000L);
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * <p>
	 * ICMP 통신 시험
	 * </p>
	 *
	 * @param context
	 * @param address
	 * @param timeout
	 * @param retry
	 * @return
	 * @throws EmpException
	 */
	public int testNeSession(EmpContext context, InetAddress address, int timeout, int retry) throws EmpException {
		int response_time = -1;

		for (int doCount = 0; doCount <= retry; doCount++) {
			ICMPPair icmpPair = sendPacket(address);

			try {
				if (icmpPair.recvPacket == null) {
					synchronized (icmpPair) {
						icmpPair.wait(timeout);
					}
				}
			} catch (InterruptedException e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, e);
				}
			}

			if (icmpPair.recvPacket != null) {
				response_time = icmpPair.getResponse_time();
				break;
			}
		}

		return response_time;
	}

	/**
	 * <p>
	 * ICMP 통신 시험
	 * </p>
	 *
	 * @param context
	 * @param addresses
	 * @param timeout
	 * @param retry
	 * @return
	 * @throws EmpException
	 */
	public int[] testListNeSession(EmpContext context, InetAddress[] addresses, int timeout, int retry) throws EmpException {
		ICMPPair[] response_values = new ICMPPair[addresses.length];

		for (int doCount = 0; doCount <= retry; doCount++) {
			boolean doPing = false;
			for (int i = 0; i < addresses.length; i++) {
				if (response_values[i] == null || !response_values[i].isSuccess()) {
					doPing = true;
					break;
				}
			}
			if (!doPing) {
				break;
			}

			for (int i = 0; i < addresses.length; i++) {
				if (response_values[i] != null && response_values[i].isSuccess()) {
					continue;
				}

				response_values[i] = sendPacket(addresses[i]);
			}

			try {
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, e);
				}
			}
		}

		int[] response_times = new int[response_values.length];
		for (int i = 0; i < response_times.length; i++) {
			if (response_values[i] != null && response_values[i].isSuccess()) {
				response_times[i] = response_values[i].getResponse_time();
			} else {
				response_times[i] = -1;
			}
		}
		return response_times;
	}

	private ICMPPair sendPacket(InetAddress address) throws EmpException {
		try {
			byte[] data = new byte[84];
			ICMPEchoPacket packet = new ICMPEchoPacket(data);
			packet.setIPHeaderLength(5);
			packet.setICMPDataByteLength(56);

			int offset = packet.getIPHeaderByteLength();
			int dataOffset = offset + packet.getICMPHeaderByteLength();
			int length = packet.getICMPPacketByteLength();

			int sequence = SEQUENCE.incrementAndGet();
			packet.setType(ICMPPacket.TYPE_ECHO_REQUEST);
			packet.setCode(0);
			packet.setIdentifier(sequence);
			packet.setSequenceNumber(sequence);
			long nano = System.nanoTime();
			OctetConverter.longToOctets(nano, data, dataOffset);
			packet.computeICMPChecksum();

			String key = new StringBuilder().append(address.getHostAddress()).append(".").append(packet.getSequenceNumber()).toString();
			ICMPPair icmpValue = new ICMPPair();
			icmpValue.sendPacket = packet;
			icmpValue.sendNano = nano;
			UtilCache.put(CACHE_KEY, key, icmpValue);

			socket.write(address, data, offset, length);

			return icmpValue;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		}
	}

	private ICMPPair recvPacket() throws EmpException {
		try {
			byte[] data = new byte[84];
			// byte[] address = new byte[4];

			ICMPEchoPacket packet = new ICMPEchoPacket(data);
			packet.setIPHeaderLength(5);
			packet.setICMPDataByteLength(56);

			socket.read(data);

			String key = new StringBuilder().append(packet.getSourceAsInetAddress().getHostAddress()).append(".").append(packet.getSequenceNumber()).toString();

			ICMPPair icmpValue = (ICMPPair) UtilCache.get(CACHE_KEY, key);
			if (icmpValue != null) {
				UtilCache.remove(CACHE_KEY, key);

				icmpValue.recvPacket = packet;
				icmpValue.recvNano = System.nanoTime();
			}
			return icmpValue;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		}
	}

}
