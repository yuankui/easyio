package io.github.yuankui.easyio.runner.es.demo;

import io.github.yuankui.easyio.core.RunWith;
import io.github.yuankui.easyio.runner.es.EsRunner;
import io.github.yuankui.easyio.runner.es.annotation.Host;
import io.github.yuankui.easyio.runner.es.annotation.Term;
import io.github.yuankui.easyio.runner.es.resource.Page;
import io.github.yuankui.easyio.runner.es.resource.Result;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import reactor.core.publisher.Mono;

@RunWith(EsRunner.class)
@Host(host = "localhost", port = 9200)
public interface EsDemoService {
    
    Result<Person> findByName(@Term("name") String name);
    SearchResponse findByName2(@Term("name") String name, Page page);
    SearchResponse findByName3(SearchRequest request, Page page);
    
    // async
    Mono<Result<Person>> findByName4(@Term("name") String name);
}
