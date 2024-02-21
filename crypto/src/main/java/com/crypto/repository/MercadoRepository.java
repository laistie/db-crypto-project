package com.crypto.repository; // OBS: Repositories have direct access to the DB

import com.crypto.models.Mercado;
import org.springframework.data.repository.CrudRepository;

public interface MercadoRepository extends CrudRepository<Mercado, String> {
}
