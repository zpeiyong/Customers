package com.dataint.service.datapack;

import com.dataint.service.datapack.db.dao.ICountryDao;
import com.dataint.service.datapack.db.dao.IFocusDiseaseDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServiceStatisticApplicationTests {

    @Autowired
    private ICountryDao countryDao;

    @Autowired
    private IFocusDiseaseDao diseaseDao;

    @Test
    void contextLoads() {

        System.out.println(this.countryDao.findByNameCnOrAlias("aa","%|aa|%"));

        System.out.println(this.diseaseDao.findByNameCnOrAlias("aa","%|aa|%"));
    }

}
