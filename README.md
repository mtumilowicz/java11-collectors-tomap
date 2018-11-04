# java11-collectors-tomap
The main goal of this project is to show `Collectors.toMap` 
functionality.

# preface
1. keyMapper, valueMapper
    ```
    public static <T, K, U>
        Collector<T, ?, Map<K,U>> toMap(
                                    Function<? super T, ? extends K> keyMapper,
                                    Function<? super T, ? extends U> valueMapper)
    ```
1. keyMapper, valueMapper, mergeFunction
    ```
    public static <T, K, U>
        Collector<T, ?, Map<K,U>> toMap(
                                    Function<? super T, ? extends K> keyMapper,
                                    Function<? super T, ? extends U> valueMapper,
                                    BinaryOperator<U> mergeFunction)
    ```
1. keyMapper, valueMapper, mergeFunction, mapFactory
    ```
    public static <T, K, U, M extends Map<K, U>>
        Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper,
                                 Function<? super T, ? extends U> valueMapper,
                                 BinaryOperator<U> mergeFunction,
                                 Supplier<M> mapFactory)
    ```

# project description
In `CollectorsToMapTest` we show examples of above methods.

* keyMapper is null -> `NullPointerException`
* valueMapper is null -> `NullPointerException`
* keyMapper returns null -> OK
* valueMapper returns null -> `NullPointerException`
* same keys + 1. type -> `IllegalStateException`
* examples of merge function (takeFirst, takeLast, exception if same)
* valuable example of factoryMap with `TreeMap`