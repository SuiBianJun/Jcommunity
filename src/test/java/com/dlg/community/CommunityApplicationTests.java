package com.dlg.community;

import com.dlg.community.entity.QuestionTag;
import com.dlg.community.entity.Tag;
import com.dlg.community.pojo.Question;
import com.dlg.community.repo.QuestionTagRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableRedisRepositories
public class CommunityApplicationTests {

    Logger logger = LoggerFactory.getLogger(CommunityApplicationTests.class);

    @Autowired
    QuestionTagRepo questionTagRepo;

	@Test
	public void contextLoads() {
	}

	@Test
	public void redisTest(){

	    QuestionTag questionTag = new QuestionTag();
        Tag tag = new Tag();
	    tag.setData(new String[]{"mysql", "sqlserver", "mongodb", "redis", "es", "sqlite"});
        tag.setName("数据库");
	    questionTag.getTagList().add(tag);

        tag = new Tag();
	    tag.setData(new String[]{"java", "php", "c", "c++", "html", "js"});
        tag.setName("开发语言");
	    questionTag.getTagList().add(tag);
        tag = new Tag();
	    tag.setData(new String[]{"idea", "eclipse", "myeclipse", "webstorm", "navicat"});
        tag.setName("开发工具");
	    questionTag.getTagList().add(tag);
        tag = new Tag();
	    tag.setData(new String[]{"spring", "springboot", "springmvc", "mybatis", "ssh"});
        tag.setName("平台框架");
	    questionTag.getTagList().add(tag);
        tag = new Tag();
	    tag.setData(new String[]{"linux", "centos", "redhat", "nginx", "iis"});
        tag.setName("服务器");
	    questionTag.getTagList().add(tag);
	    questionTagRepo.save(questionTag);

	    Iterable<QuestionTag> questionTagIterable =  questionTagRepo.findAll();
        Iterator<QuestionTag> questionIterator = questionTagIterable.iterator();

        /*while(questionIterator.hasNext()){
            QuestionTag questionTag = questionIterator.next();
            logger.info(questionTag.toString());
        }*/
    }
}
