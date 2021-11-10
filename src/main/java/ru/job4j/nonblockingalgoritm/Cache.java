package ru.job4j.nonblockingalgoritm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
	private final Map<Integer, Base> memory = new ConcurrentHashMap<>();
	
	public boolean add(Base model) {
		return memory.putIfAbsent(model.getId(), model) == null;
	}
	
	public boolean update(Base model) {
		Base base = memory.get(model.getId());
		if (base == null) {
			throw new OptimisticException("нет в кеше");
		}
		if (base.getVersion() != model.getVersion()) {
			throw new OptimisticException("разные версии не обновляем");
		}
		memory.put(model.getId(), new Base(model.getId(), model.getVersion() + 1));
		return true;
	}
	
	public void delete(Base model) {
		if (memory.get(model.getId()).getVersion() != model.getVersion()) {
			throw new OptimisticException("разные версии не удаляем");
		}
		memory.remove(model.getId());
	}
}