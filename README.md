# macOS玩转Kafka

环境配置好之后先使用Producer发送消息，再使用Consumer消费消息

## 安装kafka

安装zookeeper 3.4.13和kafka 2.3.0

```shell script
brew install kafka
```

注：假设已安装好Java和Homebrew

## 编辑kafka配置文件

将`listeners=PLAINTEXT://localhost:9092`的注释去掉

```shell script
vim /usr/local/etc/kafka/server.properties
```

## 启动zookeeper和kafka

先以服务的形式启动zookeeper

```shell script
brew services start zookeeper
```

再启动kafka

```shell script
brew services start kafka
```

## 创建主题

创建名为test的消息主题

```shell script
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```

生产消息

```shell script
kafka-console-producer --broker-list localhost:9092 --topic test
```

消费消息

```shell script
kafka-console-consumer --bootstrap-server localhost:9092 --topic test --from-beginning
```






