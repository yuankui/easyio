package io.github.yuankui.easyio.runner.rxjdbc.demo;

import io.github.yuankui.easyio.runner.rxjdbc.RxsqlRunner;
import io.github.yuankui.easyio.runner.rxjdbc.annotations.Param;
import io.github.yuankui.easyio.runner.rxjdbc.annotations.Sql;
import io.github.yuankui.easyio.spring.RunWith;
import io.reactivex.Flowable;

@RunWith(RxsqlRunner.class)
public interface SomeService {
    @Sql("select * from profile_label where dim_id = ?")
    Flowable<Label> getLabels(@Param int dimId);
}
