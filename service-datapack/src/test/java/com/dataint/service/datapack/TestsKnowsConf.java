package com.dataint.service.datapack;

import com.dataint.service.datapack.service.IKnowsConfService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class TestsKnowsConf {

    @Autowired
    private IKnowsConfService knowsConfService;
    @Test
    void testGetRelativeDataFx() {

        Map<String,Object> result = this.knowsConfService.getRelativeDataFx(2L);

        System.out.println("--------" + ((List)result.get("links")).size());

        System.out.println(result);
    }

}
