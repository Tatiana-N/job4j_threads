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
		Base newBase = new Base(model.getId(), model.getVersion() + 1);
		newBase.setName(model.getName());
		return memory.computeIfPresent(model.getId(), (k, v) -> v.getVersion() == model.getVersion() ? newBase : v) == newBase;
	}
	
	public void delete(Base model) {
		memory.remove(model.getId());
	}
}