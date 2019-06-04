package io.github.yuankui.easyio.generic.resource;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DependencyPrinter {

    @Data
    @AllArgsConstructor
    private static class Item {
        private String content;
        private int indent;
    }

    public static void print(ResourceProvider provider, Consumer<String> consumer) {
        Stream<Item> itemStream = expandProvider(provider, 0);
        itemStream.forEach(i -> {
            consumer.accept(indent("   ", i.getIndent()) + "- " + i.getContent());
        });
    }
    
    private static String indent(String prefix, int time) {
        return IntStream.range(0, time)
                .mapToObj(i -> prefix)
                .collect(Collectors.joining());
    }

    private static Stream<Item> expandProvider(ResourceProvider provider, int indent) {
        Stream<Item> providerLine = Stream.of(new Item(formatProvider(provider), indent));
        Stream<Item> children = Optional.ofNullable(provider.getDependencies())
                .orElse(Collections.emptyMap())
                .entrySet()
                .stream()
                .flatMap(kv -> expandResource(kv.getKey(), kv.getValue(), indent + 1));
        return Stream.concat(providerLine, children);
    }

    private static Stream<Item> expandResource(String resource, List<ResourceProvider> providers, int indent) {
        Stream<Item> resourceLine = Stream.of(new Item("(" + resource + ")", indent));
        Stream<Item> children = Optional.ofNullable(providers)
                .orElse(Collections.emptyList())
                .stream()
                .flatMap(p -> expandProvider(p, indent + 1));

        return Stream.concat(resourceLine, children);
    }

    private static String formatProvider(ResourceProvider provider) {
        String select = provider.isSelected() ? "[O]" : "[X]";
        String name = provider.getProvider();
        Status status = provider.getStatus();
        String msg = provider.getMsg();
        return String.format("%s [%s] status=%s, msg=%s", select, name, status, msg);
    }
}
