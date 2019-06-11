# Jdbc Runner

# Examples

## Record

```java
// record support
interface TableRepo {
    @Sql("select * from xxTable where name = ? and age = ?")
    Record query(String name, int age);
}
// usage
Record record = tableRepo.query("yuankui", 22);
System.out.println("record = " + record);
String name = record.getString("name");
int age = record.getInt("age");
Person person = record.to(Person.class);
```

## Paging

```Java
// record support
interface TableRepo {
    @Sql("select * from xxTable where age = ?")
    Query<Record> query(int age);
}

// usage
// lazy object
Query<Record> result = tableRepo.query("yuankui", 22);
List<Record> records = result.offset(100)
        .limit(10)
        .get();
```

## Customized Type

```Java
// record support
interface TableRepo {
    @Sql("select * from xxTable where age = ?")
    Query<Person> query(int age);
}

// usage
// lazy object
Query<Person> result = tableRepo.query("yuankui", 22);
List<Person> persons = result.offset(100)
        .limit(10)
        .get();
```

## Mono & Flux (reactive)
```Java
// record support
interface TableRepo {
    @Sql("select * from xxTable where age = ?")
    Query<Person> query(int age);

    @Sql("select * from xxTable where age = ? limit ?,?")
    Flux<Person> query(int age, int offset, int limit);

    // you can also use Record or Customized-type as you like.
    @Sql("select * from xxTable where age = ? limit ?,?")
    Flux<Record> query(int age, int offset, int limit);
}

// usage
// lazy object
Query<Person> result = tableRepo.query("yuankui", 22);

// build from Query
Flux<Person> persons = result.offset(100)
        .limit(10)
        .flux();

// or build directly from method invoking
Flux<Person> persons = tableRepo.query(22, 100, 10)
```

## Insert & Update & Delete
```Java
// record support
interface TableRepo {
    @Insert
    int insert(Person person);

    // Person must contains field with @PK
    @Update
    int update(Person person);

    @Delete(table = "hello")
    int delete(String pk);

    // use where
    @Delete(table = "hello");
    int delete(@Where("name") String name);

    // you can use sql directly
    @Sql("delete from xxx where id = ?")
    int delete(String pk);
}
```
