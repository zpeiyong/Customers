package com.dataint.service.datapack.service;

import com.dataint.service.datapack.db.entity.KnowsConf;
import com.dataint.service.datapack.model.vo.KnowsConfVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface IKnowsConfService {

    Map<String,Object> getRelativeDataFx(Long id);
}
