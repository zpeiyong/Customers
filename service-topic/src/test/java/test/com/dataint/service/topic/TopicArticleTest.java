package test.com.dataint.service.topic;

import com.dataint.topic.service.ITopicService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;


@SpringBootTest
public class TopicArticleTest {


    @Autowired
    private ITopicService topicService;

    @Test
    public void testGetPopularFeelingsTj() {

        System.out.println(this.topicService);
        List<Map<String, Object>> popularFeelingsTj = this.topicService.getPopularFeelingsTj("2020-01-01");

        System.out.println(popularFeelingsTj);

    }
}
