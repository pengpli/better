package mylun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class StorageCore implements Serializable {

	private Integer maxCapacity;
	private Integer currentCapacity;
	private Integer LIMIT;
	private static ConcurrentHashMap<String, Integer> luns;
	// use ConcurrentHashMap so that allow multiple client request to read the
	// LUNs, thus improve performance.

	// Singleton this class
	private static StorageCore singleton = new StorageCore();

	public static StorageCore getInstance() {
		return singleton;
	}

	public Object readResolve() {
		return getInstance();
	}

	public static void persistant() {
		StorageCore sc = StorageCore.getInstance();
		try {
			FileOutputStream fileOut = new FileOutputStream("/tmp/StorageCore.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(sc);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	public static StorageCore dePersistant() throws ClassNotFoundException, IOException {
		FileInputStream fileIn1 = new FileInputStream("/tmp/StorageCore.ser");
		ObjectInputStream in1 = new ObjectInputStream(fileIn1);
		StorageCore sc = (StorageCore) in1.readObject();
		return sc;
	}

	public boolean addLun(String id, Integer size) {
		if (luns.keySet().contains(id))
			return false;
		else if (!isValid(id))
			return false;
		else if (size > maxCapacity - currentCapacity)
			return false;
		else {
			luns.put(id, size);
			currentCapacity += size;
			return true;
		}
	}

	public boolean resizeLun(String id, Integer newSize) {
		if (!luns.keySet().contains(id))
			return false;
		else if (newSize < LIMIT)
			return false;
		else if (newSize > maxCapacity - currentCapacity + luns.get(id))
			return false;
		else {
			currentCapacity = currentCapacity - luns.get(id) + newSize;
			luns.put(id, newSize);
			return true;
		}
	}

	public boolean deleteLun(String id) {
		if (!luns.keySet().contains(id))
			return false;
		else {
			currentCapacity -= luns.get(id);
			luns.remove(id);
			return true;
		}
	}

	public Integer getLunSize(String id) {
		if (!luns.keySet().contains(id))
			return -1;
		else {
			return luns.get(id);
		}
	}

	public boolean addLuns(HashMap<String, Integer> newLuns) {

		int requestOverallSize = 0;
		for (String key : newLuns.keySet()) {
			requestOverallSize += newLuns.get(key);
		}
		// check overall size request don't exceed
		if (requestOverallSize > maxCapacity - currentCapacity)
			return false;
		// to check if any of newLuns exist in luns already
		else if (luns.keySet().stream().anyMatch(newLuns.keySet()::contains))
			return false;
		else {
			luns.putAll(newLuns);
			return true;
		}
	}

	private static boolean isValid(String id) {
		// check if id contain illegal characters, not implement yet I don't
		// know what characters are illegal
		return true;
	}

}
