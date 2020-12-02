package com.dataint.service.datapack.service.impl;

import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.service.datapack.db.dao.ICountryDao;
import com.dataint.service.datapack.db.dao.ISourceDao;
import com.dataint.service.datapack.db.entity.Country;
import com.dataint.service.datapack.db.entity.Source;
import com.dataint.service.datapack.model.form.SourceForm;
import com.dataint.service.datapack.model.form.SourceUpdateForm;
import com.dataint.service.datapack.model.vo.SourceVO;
import com.dataint.service.datapack.service.ISourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class SourceServiceImpl implements ISourceService {

    @Autowired
    private ISourceDao sourceDao;

    @Autowired
    private ICountryDao countryDao;

    @Override
    public SourceVO add(SourceForm sourceForm) {
        Country country = countryDao.getOne(sourceForm.getCountryId());

        Source source = sourceForm.toPo(Source.class);
        source.setCountry(country);
        source.setSourceStatus("Y");
        source.setCreatedTime(new Date());
        sourceDao.save(source);

        return new SourceVO(source);
    }

    @Override
    public SourceVO updateSource(SourceUpdateForm sourceUpdateForm) {
        // check if Source exist
        Optional<Source> sourceOpt = sourceDao.findById(sourceUpdateForm.getSourceId());
        if (!sourceOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Source source = sourceOpt.get();

        // get country
        Country country = countryDao.getOne(sourceUpdateForm.getCountryId());
        // update
        source = sourceUpdateForm.toPo(source.getId(), Source.class);
        source.setCountry(country);
        source.setUpdatedTime(new Date());
        sourceDao.save(source);

        return new SourceVO(source);
    }

    @Override
    public boolean delSource(Long sourceId) {
        // check if Source exist
        Optional<Source> sourceOpt = sourceDao.findById(sourceId);
        if (!sourceOpt.isPresent()) {
            throw new DataNotExistException();
        }
        sourceDao.deleteById(sourceId);

        return true;
    }
}
