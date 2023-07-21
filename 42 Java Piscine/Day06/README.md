# Плагины и зависимости, применяемые в проекте:

- `maven-surefire-plugin` - плагин, отвечающий за тестирование
- `junit-jupiter-engine` - ядро, работающее с тестом


- `junit-jupiter-params` - зависимость, позволяющая использовать аннотации, устанавливающие параметры тестов


- `junit-jupiter-api` - все аннотации


# ex00

> Тестовый класс должен иметь в своем имени 'Test'

API:
- Assertions
  - `assertEquals(a, b)` - удостоверивается, что `a = b`
  - `assertNotEquals(a, b)` - удостоверивается, что `a != b`
  - `assertTrue(значение)` - удостоверивается, что параметр `true`
  - `assertFalse(значение)` - удостоверивается, что параметр `false`
  - `assertNotNull(значение)`
  - `assertThrows(исключение, () -> метод)` - вернет `FAIL`, если в блоке не было сгенерировано исключение
- Test

Params:
 - Parametrized
 - ValueOf

```java
@Test
void myNewTest() {
    Assertions.assertEquals(5, 5); // Сравнивает значения на равенство
     Assertions.assertEquals(345, Math.addExact(300, 45));    
        }
```

```java
@ParameterizedTest // параметризированный тест
@ValueSource(ints = {95219, 11329, 10091}) // тип воходных параметров для тестов
    
```

## Порядок вызова аннотаций:
```java
@BeforeAll - перед запуском всех тестов (Class Level Setup)
@BeforeEach - каждый раз перед запуском нового теста (Setup)
@Test
@AfterEach - каждый раз после запуска нового теста (Cleanup)
@AfterAll - после выполнения всех тестов (Class Level Cleanup)
```


