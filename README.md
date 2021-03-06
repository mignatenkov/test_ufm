# Тестовое задание для UFM
project test_ufm

В приложении для организации параллельной обработки поступающих данных используется Akka v.2.5

Для определения правил обработки выходящих данных используется платформа для интеграции бизнес логики Drools v.6.0

В качестве NoSQL хранилища данных была выбрана MongoDB.

Приложение собирается из кода сборщиком Maven.

Для работы требует работающего инстанса MongoDB (см. _Параметры соединения с хранилищем данных_ ниже). 

Для запуска приложения:
`java -jar test_ufm.jar`

В директории запуска появятся три директории, соответствующие входящим(input), обработанным(processed) и результирующим(output) файлам.
Также результаты обработки буду доступны в вашем хранилище данных MongoDB, в соответствующей коллекции.

# Форматы данных
## Входные данные
`{
    "clientId" : <client id, Long>,
    "subscribers" : [
        {
            "id" : <subscriber id, Long>,
            "spent" : <spent amount, Long>
        }
    ]
}`
## Выходные данные
`{
    "clientId" : <client id, Long>,
    "spentTotal" : <total spent by all subsribers, Long>,
    "isBig" : <TRUE | FALSE, sign of BIG client, Boolean>
}`

# Настраиваемые параметры
## Входные и выходные директории приложения
**application.inputFilesDir** (значение по умолчанию: input)

Параметр, отвечающий за расположение директории с ещё необработанными входящими файлами. Если эта директория будет отсутствовать, она будет автоматически создана при старте приложения. Указанный в этом параметре путь будет рассматриваться как относительный от текущей директории, в который была отдана команда запуска приложения.

**application.processedFilesDir** (значение по умолчанию: processed)

Параметр, отвечающий за расположение директории с уже обработанными входящими файлами. Если эта директория будет отсутствовать, она будет автоматически создана при старте приложения. Указанный в этом параметре путь будет рассматриваться как относительный от текущей директории, в который была отдана команда запуска приложения.

**application.outputFilesDir** (значение по умолчанию: output)

Параметр, отвечающий за расположение директории с результирующими файлами. Если эта директория будет отсутствовать, она будет автоматически создана при старте приложения. Указанный в этом параметре путь будет рассматриваться как относительный от текущей директории, в который была отдана команда запуска приложения.

## Параметры обработки данных
**application.scheduler.interval** (значение по умолчанию: 1000)

Параметр, отвечающий за интервал опроса входящей директории на предмет наличия файлов. Указывается в милисекундах.

**application.scheduler.countActors** (значение по умолчанию: 20)

Параметр, отвечающий за количество работающих параллельно обработчиков.

## Параметры соединения с хранилищем данных
Для подробной информации по значению ниже приведенных параметров см. документацию по настройки и работе с MongoDB.

**mongodb.host** (значение по умолчанию: localhost)

**mongodb.port** (значение по умолчанию: 27017)

**mongodb.database.name** (значение по умолчанию: test)

**mongodb.collection.name** (значение по умолчанию: clients)

## Как задавать параметры
Параметры можно задать при старте приложения в параметрах запуска. Например, так:

`java -jar test_ufm.jar --application.scheduler.interval=10000`

# Примечания к сборке проекта
Для успешного прохождения тестов при сборке проекта командой `mvn clean install` необходим поднятый инстанс MongoDB на localhost:27017

Если желаете обойтись без установки и конфигурирования MongoDB, то можно пропустить этап тестирования во время сборки, можно вместо этого воспользоваться командой `mvn clean install -DskipTests`

# Возможные направления доработки и развития
- Дальнейшее развитие интеграции с Drools

- Динамическое порождение Actor'ов для Akka
