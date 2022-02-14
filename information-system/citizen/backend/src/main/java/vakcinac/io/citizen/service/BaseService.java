package vakcinac.io.citizen.service;

import vakcinac.io.core.repository.ExistRepository;


public abstract class BaseService<T> {
	
	protected ExistRepository<T> baseRepository;
	
	protected BaseService(ExistRepository<T> baseRepository) {
		this.baseRepository = baseRepository;
	}
	
	public T read(String id) {
		return baseRepository.retrieve(id);
	}
	
	public T create(String id, T obj) {
		return baseRepository.store(id, obj);
	}

}
