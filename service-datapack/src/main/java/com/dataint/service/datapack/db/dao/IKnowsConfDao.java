package com.dataint.service.datapack.db.dao;


import com.dataint.service.datapack.db.entity.DiseaseCountryCase;
import com.dataint.service.datapack.db.entity.KnowsConf;
import com.dataint.service.datapack.model.vo.KnowsConfVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface IKnowsConfDao extends JpaRepository<KnowsConf, Long>, JpaSpecificationExecutor<KnowsConf> {

    @Query(" from KnowsConf kc where kc.diseaseId=?1")
    KnowsConf getRelativeDateFx(Integer id);

    @Query(" from KnowsConf kc where kc.parentId in ?1 ")
    List<KnowsConf> getChildKnowsConfs(List parentIds);

}
