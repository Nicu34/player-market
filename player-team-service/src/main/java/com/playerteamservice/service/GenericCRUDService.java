package com.playerteamservice.service;

import com.playerteamservice.converter.GenericResponseConverter;
import com.model.BaseResponse;
import com.playerteamservice.entity.BaseEntity;
import com.playerteamservice.exception.EntityNotFoundException;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Getter
public abstract class GenericCRUDService<E extends BaseEntity, OUT extends BaseResponse, R> {

    public abstract JpaRepository<E, Integer> getRepository();

    public abstract GenericResponseConverter<E, OUT> getResponseConverter();

    public abstract void save(R request) throws EntityNotFoundException;

    public abstract void update(R request, Integer id) throws EntityNotFoundException;

    public List<OUT> findAll(Pageable pageable) {
        return getResponseConverter().convertFrom(getRepository().findAll(pageable).getContent());
    }

    public void delete(Integer id) {
        getRepository().deleteById(id);
    }

    public OUT getById(Integer id) throws EntityNotFoundException {
        E entity = getRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No entity with id %s found.", id)));

        return getResponseConverter().convert(entity);
    }
}
