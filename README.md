# PgClientIssue175Reproducer

Reproducer project for https://github.com/reactiverse/reactive-pg-client/issues/175

Tries to retrieve rows of a table with a bigint[] column followed by a text/varchar column in 3 different ways:

- Selecting the text column before the bigint[] column works as expected
- Selecting the bigint[] column before the text column when the bigint[] column has a null value works as expected
- Selecting the bigint[] column before the text columnn with a non null value in the bigint[] column leads to an indexOutOfBoundException in the RowResultDecoder

The relevant code is located in the ReproducerVerticle and PostgreSQLClientWrapper classes.

# Setup

1. Create a PostgreSQL database "reproducerdb" then execute reproducerdb.sql to create the database table schema.
2. Fill in the database connection details in DataBaseConfig.json
3. Build the project with maven package
4. Run it with java -jar PgClientIssue175Reproducer-1.0.jar DataBaseConfig.json

# Output

The console output should look something like this:

Sep 13, 2018 5:06:56 AM PostgreSQLClientWrapper\
INFORMATION: ColumnName: id Value: 1\
Sep 13, 2018 5:06:56 AM PostgreSQLClientWrapper\
INFORMATION: ColumnName: text Value: test\
Sep 13, 2018 5:06:56 AM PostgreSQLClientWrapper\
INFORMATION: ColumnName: array Value: [Ljava.lang.Long;@1dcc7325\
Sep 13, 2018 5:06:56 AM ReproducerVerticle\
INFORMATION: Result of testSelectWorking():\
Sep 13, 2018 5:06:56 AM ReproducerVerticle\
INFORMATION: [ {\
  "id" : 1,\
  "text" : "test",\
  "array" : [ 0 ]\ 
} ]\
Sep 13, 2018 5:06:56 AM PostgreSQLClientWrapper\
INFORMATION: ColumnName: id Value: 1\
Sep 13, 2018 5:06:56 AM PostgreSQLClientWrapper\
INFORMATION: ColumnName: text Value: test\
Sep 13, 2018 5:06:56 AM ReproducerVerticle\
INFORMATION: Result of testSelectWorkingNullValue():\
Sep 13, 2018 5:06:56 AM ReproducerVerticle\
INFORMATION: [ {\
  "id" : 1,\ 
  "text" : "test"\
} ]\
Sep 13, 2018 5:06:56 AM PostgreSQLClientWrapper\
SCHWERWIEGEND: java.lang.IndexOutOfBoundsException: readerIndex(100) + length(4) exceeds writerIndex(101): PooledUnsafeDirectByteBuf(ridx: 100, widx: 101, cap: 512)\
java.lang.IndexOutOfBoundsException: readerIndex(100) + length(4) exceeds writerIndex(101): PooledUnsafeDirectByteBuf(ridx: 100, widx: 101, cap: 512)\
	at io.netty.buffer.AbstractByteBuf.checkReadableBytes0(AbstractByteBuf.java:1403)\	
	at io.netty.buffer.AbstractByteBuf.readInt(AbstractByteBuf.java:786)\	
	at io.reactiverse.pgclient.impl.RowResultDecoder.decodeRow(RowResultDecoder.java:73)	\
	at io.reactiverse.pgclient.impl.codec.decoder.MessageDecoder.decodeDataRow(MessageDecoder.java:207)	\
	at io.reactiverse.pgclient.impl.codec.decoder.MessageDecoder.channelRead(MessageDecoder.java:110)	\
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)	\
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)	\
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340)	\
	at io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1359)	\
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)	\
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)	\
	at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:935)	\
	at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:141)	\
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:645)	\
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:580)	\
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:497)	\
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:459)	\
	at io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:886)	\
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)	\
	at java.lang.Thread.run(Thread.java:748)\
Sep 13, 2018 5:06:56 AM Main\
INFORMATION: Startup completed