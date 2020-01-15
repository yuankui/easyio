package io.github.yuankui.easyio.generic2;

import java.lang.reflect.Method;

/**
 * 
 * 该provider负责出资源T的筹措方案
 * 
 * @param <T> 资源类型
 *           
 * note: 直接实现，不要间接实现这个接口
 */
public interface Provider<T> {

    /**
     * 来来看，这是现在的现状 {method}，这是已有的资源 {context}， 你能否出一个得到资源的T的方案吗？
     * 
     * 能就是Result.success,并给出方案T,否则就是Result.fail
     * 
     * @param method 方法
     * @param context 当前局势
     * @return 方案
     */
    Result<T> init(Method method, InitContext context);

    /**
     * 资源名
     * @return
     */
    String resourceName();
    /**
     * 竞标
     * 
     * 如果你认为你比对手厉害，请说出你比他厉害多少{level}，并给出理由{msg}；
     * 如果你觉得你比对手弱，请说出你比他弱多少{level}，并给出理由{msg}；
     * 
     * 如果不说，就默认你跟他一样咯
     * 
     * @param competitor 对手
     * @return 比较结果
     */
    default CompareResult compare(Provider<T> competitor) {
        return CompareResult.same();
    }
}
