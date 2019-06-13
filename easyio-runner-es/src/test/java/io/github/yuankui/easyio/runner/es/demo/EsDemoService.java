package io.github.yuankui.easyio.runner.es.demo;

import io.github.yuankui.easyio.core.RunWith;
import io.github.yuankui.easyio.runner.es.EsRunner;
import io.github.yuankui.easyio.runner.es.annotation.Host;
import io.github.yuankui.easyio.runner.es.annotation.Term;
import io.github.yuankui.easyio.runner.es.resource.Result;

@RunWith(EsRunner.class)
@Host(host = "localhost", port = 9200)
public interface EsDemoService {
    
    Result<Person> findByName(@Term("name") String name);
}
