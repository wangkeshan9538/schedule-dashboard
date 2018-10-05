# schedule-dashboard
使用vue + quartz 搭建定时任务管理面板，后端使用springboot2集成quartz，
完成
 * 增加任务；
 * 暂停任务；
 * 触发任务；
 * 删除任务；
 * 开启任务
 * 增加触发器；
 * 暂停触发器；
 * 开启触发器；
 * 修改任务的触发器；
 * 获得任务的 列表和 触发器列表；
 * 删除触发规则；
quartz的配置使用持久层配置 ，即配置都存放在数据库，
使用时 继承BaseJob 并实现executeJob方法， 然后在界面上增加对应的job 就可以了，
```apple java
public class Task1 extends BaseJob {

  @Override
  public void executeJob(JobExecutionContext context) {

    System.out.println("Task执行");

  }

}
```
## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Run your tests
```
npm run test
```

### Lints and fixes files
```
npm run lint
```


# 待改进的地方
1. 前端的请求层为了方便调用 ，并没有为每一个请求写一个service方法，而是只写了axios的get，post，方法，在view层进行参数的填充，这样不容易看出
请求参数，
2. 前端的view 缺少分页，和 搜索，
