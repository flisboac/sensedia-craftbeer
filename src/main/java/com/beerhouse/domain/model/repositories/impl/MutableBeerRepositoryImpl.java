package com.beerhouse.domain.model.repositories.impl;

import com.beerhouse.common.core.lang.AppException;
import com.beerhouse.common.entities.services.EntityValidator;
import com.beerhouse.common.entities.validationGroups.Create;
import com.beerhouse.common.entities.validationGroups.Replace;
import com.beerhouse.common.entities.validationGroups.Update;
import com.beerhouse.domain.model.entities.Beer;
import com.beerhouse.domain.model.services.BeerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.groups.Default;
import java.time.ZonedDateTime;

@Service
public class MutableBeerRepositoryImpl implements MutableBeerRepository {
    private static final Logger logger = LoggerFactory.getLogger(MutableBeerRepositoryImpl.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EntityValidator entityValidator;

    @Autowired
    private BeerMapper beerMapper;

    @Override
    @Transactional
    public Beer create(Beer toCreate) throws AppException {
        toCreate.setCreatedAt(ZonedDateTime.now());
        entityValidator.validate(toCreate, Default.class, Create.class);
        toCreate = entityManager.merge(toCreate);
        logger.info("Creating beer: \"{}\"", toCreate.getName());
        entityManager.flush();
        logger.info("Created beer: \"{}\" (ID: {})", toCreate.getName(), toCreate.getId());
        return toCreate;
    }

    @Override
    @Transactional
    public Beer update(Beer toUpdate) throws AppException {
        toUpdate.setUpdatedAt(ZonedDateTime.now());
        entityValidator.validate(toUpdate, Default.class, Update.class);
        var entity = getById(toUpdate.getId());
        beerMapper.copy(entity, toUpdate);
        logger.info("Updating beer (ID: {})", toUpdate.getId());
        return entity;
    }

    @Override
    @Transactional
    public Beer replace(Beer toReplace) throws AppException {
        toReplace.setUpdatedAt(ZonedDateTime.now());
        entityValidator.validate(toReplace, Default.class, Replace.class);
        var id = toReplace.getId();
        var entity = getById(id);
        beerMapper.copy(entity, toReplace);
        logger.info("Replacing beer \"{}\" (ID: {})", toReplace.getName(), toReplace.getId());
        return entity;
    }

    private Beer getById(Integer id) {
        var entity = entityManager.find(Beer.class, id);
        if (entity != null) {
            return entity;
        }
        throw new EntityNotFoundException();
    }
}
