# Клиент

`Socket` - точка, через которую происходит соединение. Объект класса создается на стороне клиента, сервер воссоздает его, получая сигнал на подключение.

Конструкторы

```java
Socket(String имя_хоста, int порт) throws UnknownHostException, IOException

Socket(InetAddress IP-адрес, int порт) throws UnknownHostException
```

При инициализации объекта типа `Socket`, клиент, которому тот принадлежит, объявляет в сети, что хочет соединиться с сервером про определённому адресу и номеру порта.

<br>

***Часто используемые методы класса Socket:***

`InetAddress getInetAddress()` возвращает объект содержащий данные о сокете. В случае если сокет не подключен – null

`int getPort()` - возвращает порт по которому происходит соединение с сервером

`int getLocalPort()` - возвращает порт к которому привязан сокет. Дело в том, что «общаться» клиент и сервер могут по одному порту, а порты, к которым они привязаны – могут быть совершенно другие

`boolean isConnected()` - возвращает true, если соединение установлено

`void connect(SocketAddress адрес)` - указывает новое соединение

`boolean isClosed()` возвращает true, если сокет закрыт

`boolean isBound()` возвращает true, если сокет действительно привязан к адресу

<br>

# Сервер

`ServerSocket` используется только на этапе создания соединения (принимает соединение(объект типа `Socket` на стороне клиента) и воссоздает его с помощью метода `accept()`, который возвращает объект клиента)

При объявлении `ServerSocket` не нужно указывать адрес соединения, потому что общение происходит на машине сервера. Только при многоканальном хосте нужно указать к какому ip привязан сокет сервера

Конструкторы
```java
ServerSocket() throws IOException
ServerSocket(int порт) throws IOException
ServerSocket(int порт, int максимум_подключений) throws IOException
ServerSocket(int порт, int максимум_подключений, InetAddress локальный_адрес) throws IOException

```

<br>

# Потоки ввода и вывода

Используются для общения клиента и сервера

Потоки, обернутые в буферизированные классы адаптеры.
```java
BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
```
Для двунаправленного общения операции проделываются на обих сторонах. Метод `flush()` для `BufferedWriter` выталкивает информацию для отправки. Принимающий поток ждет `\n` в конце строки, при отсутствии символа сообщение не будет принято.

***Автоматическое выталкивание буфера***

```java
PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

```

Отправка объектов через пакеты сокета при помощи сериализации

```java
ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
```