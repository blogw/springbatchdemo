# Getting Started
    mvn package -Dmaven.test.skip=true
    java -jar demo-0.0.1-SNAPSHOT.jar --spring.batch.job.names=taskletJob --spring.batch.job.enabled=true
    java -jar demo-0.0.1-SNAPSHOT.jar csvfile=person.csv --spring.batch.job.names=chunkJob --spring.batch.job.enabled=true

### listener
    JobExecutionListener
    StepExecutionListener
    ChunkListener
    ItemReadListener
    ItemProcessListener
    ItemWriteListener
    SkipListener

### JPA
* ddl-auto:create----每次运行该程序，没有表格会新建表格，表内有数据会清空
* ddl-auto:create-drop----每次程序结束的时候会清空表
* ddl-auto:update----每次运行程序，没有表格会新建表格，表内有数据不会清空，只会更新
* ddl-auto:validate----运行程序会校验数据与数据库的字段类型是否相同，不同会报错

### Tips
* reader和writer都尽量使用spring batch原生的builder，因为可以配合chunk，可以从上次失败处restart
* demo中有step和tasklet包，但没有在step中放class，实际大的项目中可以把step抽出分别定义，job中组合step
