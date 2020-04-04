/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_util.map;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * OID를 저장하는 Map
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 2.
 * @modified 2015. 4. 2.
 * @author cchyun
 *
 */
public class UtilMapOID<V> {

	private class OID implements Comparable<OID> {

		private final Object[] keys;

		private int hash_code = -1;

		private OID(Object[] keys) {
			this.keys = keys;
		}

		@Override
		public boolean equals(Object obj) {
			if (OID.class == obj.getClass()) {
				@SuppressWarnings("unchecked")
				OID oid = (OID) obj;
				boolean equals = (keys.length == oid.keys.length);
				if (equals) {
					for (int i = 0; i < keys.length; i++) {
						if (keys[i] == null && oid.keys[i] == null) {
							equals = true;
						} else if (keys[i] == null || oid.keys[i] == null) {
							equals = false;
						} else {
							equals = keys[i].equals(oid.keys[i]);
						}
						if (!equals) {
							break;
						}
					}
				}
				return equals;
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			if (hash_code == -1) {
				int hash_code = 0;
				for (Object key : keys) {
					hash_code += (key == null ? 0 : key.hashCode());
				}
				this.hash_code = hash_code;
			}
			return hash_code;
		}

		public boolean like(Object obj) {
			if (OID.class == obj.getClass()) {
				@SuppressWarnings("unchecked")
				OID oid = (OID) obj;
				boolean like = (oid.keys.length <= keys.length);
				if (like) {
					for (int i = 0; i < oid.keys.length; i++) {
						if (oid.keys[i] == null) {
							like = true;
						} else {
							like = oid.keys[i].equals(keys[i]);
						}
						if (!like) {
							break;
						}
					}
				}
				return like;
			} else {
				return false;
			}
		}

		@Override
		public int compareTo(UtilMapOID<V>.OID o) {
			int compare = (keys.length - o.keys.length);
			if (compare == 0) {
				for (int i = 0; i < keys.length; i++) {
					if (keys[i] == null && o.keys[i] == null) {
						compare = 0;
					} else if (keys[i] == null) {
						compare = -1;
					} else if (o.keys[i] == null) {
						compare = 1;
					} else {
						compare = keys[i].toString().compareTo(o.keys[i].toString());
					}
					if (compare != 0) {
						break;
					}
				}
			}
			return compare;
		}

	}

	private Map<OID, V> oid_map = new HashMap<OID, V>();

	private ReentrantLock lock = new ReentrantLock();

	public V get(Object... keys) {
		lock.lock();
		try {
			OID oid = new OID(keys);
			V value = oid_map.get(oid);
			if (value == null) {
				OID like_oid = null;
				for (OID key : oid_map.keySet()) {
					if (oid.like(key) && (like_oid == null || 0 < like_oid.compareTo(key))) {
						like_oid = key;
					}
				}
				if (like_oid != null) {
					value = oid_map.get(like_oid);
					oid_map.put(oid, value);
				}
			}
			return value;
		} finally {
			lock.unlock();
		}
	}

	public V put(V value, Object... keys) {
		lock.lock();
		try {
			return oid_map.put(new OID(keys), value);
		} finally {
			lock.unlock();
		}
	}

	public V[] values(V[] a) {
		return oid_map.values().toArray(a);
	}

}
