package io.github.yuankui.easyio.generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Line {
        private String line;
        private int indent = 0;
    }

    private String name;
    private List<TreeNode> children;
    
    private List<Line> toLines() {
        if (children == null) {
            return Collections.singletonList(new Line(name, 0));
        }

        List<Line> lines = new ArrayList<>();
        lines.add(new Line(name, 0));
        
        children.stream()
                .flatMap(n -> n.toLines().stream())
                .map(l -> new Line(l.getLine(), l.getIndent() + 1))
                .forEach(lines::add);
        return lines;
    }
    
    public List<String> toLine() {
        return toLines().stream()
                .map(l -> prefix(l.getIndent()) + l.getLine())
                .collect(Collectors.toList());
    }
    
    private String prefix(int indent) {
        if (indent <= 0) {
            return "+- ";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("|  ");
        }

        sb.append("+- ");
        return sb.toString();
    }

    public static TreeNode build(String name, Supplier<List<TreeNode>> nodeSupplier) {
        List<TreeNode> treeNodes = nodeSupplier.get();
        return new TreeNode(name, treeNodes);
    }
}
