package vakcinac.io.civil.servant.service;

import vakcinac.io.core.repository.ExistRepository;


public abstract class BaseService<T> {
	
	protected ExistRepository<T> baseRepository;
	
	protected BaseService(ExistRepository<T> baseRepository) {
		this.baseRepository = baseRepository;
	}
	
	public T read(String id) {
		return baseRepository.retrieve(id);
	}
		
	public abstract T create(T obj);
	
	protected T create(String id, T obj) {
		return baseRepository.store(id, obj);
	}
	
	public T delete(String id) {
		return baseRepository.remove(id);
	}

}
