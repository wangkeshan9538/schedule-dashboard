package quartTest;


import com.wks.quartzService.Application;
import com.wks.quartzService.service.QuartzContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class) // 指定spring-boot的启动类
public class ContextTest {


    @Autowired
    QuartzContext context;

    @Test
    public void TestAddJobAndTrigger() {
        try {
            context.addjobs("Task", "aaa", "com.wks.quartzService.jobs.Task1");
            context.addTrigger("Task", "trigger1", "bbb", "*/2 * * * * ?");
            Thread.sleep(1_000_000L);
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
