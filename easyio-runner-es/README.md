# easyio-runner-runner

## Get started

refer to `io.github.yuankui.easyio.runner.es.demo.EsDemoServiceTest`

```java
@RunWith(EsRunner.class)
@Host(host = "localhost", port = 9200)
public interface EsDemoService {
    
    Result<Person> findByName(@Term("name") String name);
    SearchResponse findByName2(@Term("name") String name, Page page);
    SearchResponse findByName3(SearchRequest request, Page page);
    
    // async
    Mono<Result<Person>> findByName4(@Term("name") String name);
}
```

```text
execution plan for method: public abstract io.github.yuankui.easyio.runner.es.resource.Result io.github.yuankui.easyio.runner.es.demo.EsDemoService.findByName(java.lang.String)
- [O] [SearchResultProvider] status=OK, msg=null
   - (response)
      - [O] [SearchResponseProvider] status=OK, msg=null
         - (request)
            - [O] [FilterSearchRequestProvider] status=OK, msg=null
               - (query)
                  - [O] [TermQueryProvider] status=OK, msg=null
               - (page)
                  - [X] [PageProvider] status=ERR, msg=no Page param found
            - [X] [RawSearchRequestProvider] status=ERR, msg=no SearchRequest in params
         - (client)
            - [O] [EsClientProvider] status=OK, msg=null
   - (responseParser)
      - [O] [PojoResponseParserProvider] status=OK, msg=null
      - [X] [RawResponseParserProvider] status=ERR, msg=return type not SearchResponse
execution plan for method: public abstract org.elasticsearch.action.search.SearchResponse io.github.yuankui.easyio.runner.es.demo.EsDemoService.findByName2(java.lang.String,io.github.yuankui.easyio.runner.es.resource.Page)
- [O] [SearchResultProvider] status=OK, msg=null
   - (response)
      - [O] [SearchResponseProvider] status=OK, msg=null
         - (request)
            - [O] [FilterSearchRequestProvider] status=OK, msg=null
               - (query)
                  - [O] [TermQueryProvider] status=OK, msg=null
               - (page)
                  - [O] [PageProvider] status=OK, msg=null
            - [X] [RawSearchRequestProvider] status=ERR, msg=no SearchRequest in params
         - (client)
            - [O] [EsClientProvider] status=OK, msg=null
   - (responseParser)
      - [X] [PojoResponseParserProvider] status=ERR, msg=return type not generic
      - [O] [RawResponseParserProvider] status=OK, msg=null
execution plan for method: public abstract org.elasticsearch.action.search.SearchResponse io.github.yuankui.easyio.runner.es.demo.EsDemoService.findByName3(org.elasticsearch.action.search.SearchRequest,io.github.yuankui.easyio.runner.es.resource.Page)
- [O] [SearchResultProvider] status=OK, msg=null
   - (response)
      - [O] [SearchResponseProvider] status=OK, msg=null
         - (request)
            - [X] [FilterSearchRequestProvider] status=OK, msg=null
               - (query)
                  - [O] [TermQueryProvider] status=OK, msg=null
               - (page)
                  - [O] [PageProvider] status=OK, msg=null
            - [O] [RawSearchRequestProvider] status=OK, msg=null
         - (client)
            - [O] [EsClientProvider] status=OK, msg=null
   - (responseParser)
      - [X] [PojoResponseParserProvider] status=ERR, msg=return type not generic
      - [O] [RawResponseParserProvider] status=OK, msg=null
```
## features

- `Page`
- `SearchRequest`
- `SearchResponse`
- `@Term`
- `@Host`
- `Result<POJO>`
- `@Terms` (TODO)
- `@Match` (TODO)
- `Mono<T>`