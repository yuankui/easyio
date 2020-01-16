package io.github.yuankui.easyio.runner.rxjdbc.providers;

import com.alibaba.fastjson.JSONObject;
import io.github.yuankui.easyio.generic2.*;
import io.github.yuankui.easyio.runner.rxjdbc.RxsqlProvider;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import org.apache.commons.lang3.tuple.Pair;
import org.davidmoten.rx.jdbc.Database;
import org.davidmoten.rx.jdbc.SelectBuilder;
import org.davidmoten.rx.jdbc.exceptions.SQLRuntimeException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RxsqlProvider
@Component
public class SelectResultProvider implements Provider<Object> {
    @Override
    public Result<Object> init(Method method, InitContext context) {

        // database
        Collection<Resource<Database>> databases = context.getResources("database");
        if (databases.isEmpty()) {
            return Result.fail("missing resource: database");
        }
        Callable<Database> databaseCallable = databases.stream()
                .findFirst()
                .get()
                .getCallable();

        // sql
        Collection<Resource<String>> sqls = context.getResources("sql");
        if (sqls.isEmpty()) {
            return Result.fail("missing resource: sql");
        }
        Callable<String> sqlCallable = sqls.stream()
                .findFirst()
                .get()
                .getCallable();

        // sql param
        Collection<Resource<List<Object>>> sqlParam = context.getResources("sqlParam");
        Callable<List<Object>> paramCallable = sqlParam.isEmpty() ? null : sqlParam.stream()
                .findFirst()
                .get()
                .getCallable();

        // return type
        Type type;
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
            if (parameterizedType.getRawType() != Flowable.class) {
                return Result.fail("return type not Flowable");
            }
            type = parameterizedType.getActualTypeArguments()[0];
        } else {
            return Result.fail("return type not Flowable<T>");
        }
        return Result.success(ioContext -> {
            Database database = databaseCallable.call(ioContext);
            String sql = sqlCallable.call(ioContext);
            SelectBuilder select = database.select(sql);

            if (paramCallable != null) {
                List<Object> params = paramCallable.call(ioContext);
                select = select.parameters(params);
            }

            // refer: org.davidmoten.rx.jdbc.Util.getObject(java.sql.ResultSet, java.lang.Class<T>, int)
            return select.get(this::rsToJsonObject)
                    .map(json -> json.toJavaObject(type));
        });
    }

    private JSONObject rsToJsonObject(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            return IntStream.range(1, columnCount + 1)
                    .boxed()
                    .flatMap(i -> {
                        try {
                            Object value = getObject(rs, i);
                            if (value == null) {
                                return Stream.empty();
                            }
                            return Stream.of(Pair.of(metaData.getColumnName(i), value));
                        } catch (SQLException e) {
                            return Stream.empty();
                        }
                    })
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o, o2) -> o2, JSONObject::new));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getObject(ResultSet rs, int i) {
        try {
            if (rs.getObject(i) == null) {
                return null;
            } else {
                int type = rs.getMetaData().getColumnType(i);
                if (type == 91) {
                    return rs.getDate(i, Calendar.getInstance());
                } else if (type == 92) {
                    return rs.getTime(i, Calendar.getInstance());
                } else if (type == 93) {
                    return rs.getTimestamp(i, Calendar.getInstance());
                } else {
                    return rs.getObject(i);
                }
            }
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        }
    }


    @Override
    public String resourceName() {
        return "result";
    }
}
