package cmpt355.project.codegen;

import cmpt355.project.InternalCompilerException;
import cmpt355.project.jvm.Instruction;
import cmpt355.project.jvm.InstructionAddressOperand;
import cmpt355.project.language.Method;

import java.util.*;

public class MethodContext {

    private Map<String, Integer> methodVariableMap = new HashMap<>();
    private List<Instruction> methodCode = new ArrayList<>();
    private List<DynamicLabel> labels = new ArrayList<>();
    private int numLocals;
    private int runningStackSize = 0;
    private int maxStackSize = 0;

    public MethodContext() {
    }

    public void defineMethodVariable(String name, int index) {
        methodVariableMap.put(name, index);
    }

    public int findMethodVariable(String name) {
        Integer index = methodVariableMap.getOrDefault(name, null);
        if (index == null)
            throw new InternalCompilerException("Cannot find method variable with name " + name);
        return methodVariableMap.get(name);
    }

    private void updateStackSize(int delta) {
        runningStackSize += delta;
        maxStackSize = Math.max(maxStackSize, runningStackSize);
    }

    public void addCode(Instruction instr) {
        methodCode.add(instr);
        updateStackSize(instr.computeStackDelta());
    }

    public void addCode(List<Instruction> instrs) {
        methodCode.addAll(instrs);
        for (var instr : instrs)
            updateStackSize(instr.computeStackDelta());
    }

    public void adjustStackSize(int delta) {
        updateStackSize(delta);
    }

    public int getMaxStackSize() {
        return Math.max(maxStackSize, 0);
    }

    public List<Instruction> getCode() {
        return Collections.unmodifiableList(methodCode);
    }

    public Label newLabel() {
        return newLabel(null);
    }

    public Label newLabel(String tag) {
        var newLabel = new DynamicLabel(this, methodCode.size(), tag);
        labels.add(newLabel);
        return newLabel;
    }

    private void mark(DynamicLabel label) {
        label.setCodeIndex(methodCode.size());
        labels.sort(Comparator.comparing(DynamicLabel::getCodeIndex));
    }

    void applyLabels() {
        int lastCodeIndex = -1;
        DynamicLabel lastLabel = null;

        for (var label : labels) {
            if (label.getCodeIndex() == lastCodeIndex) {
                lastLabel.appendTag(label.getTag());
                label.proxyFor(lastLabel);
            } else {
                int codeIndex = label.getCodeIndex();
                methodCode.get(codeIndex).setLabel(label);

                lastCodeIndex = codeIndex;
                lastLabel = label;
            }
        }
    }

    void setNumLocals(int numLocals) {
        this.numLocals = numLocals;
    }

    int getNumLocals() {
        return numLocals;
    }

    private class DynamicLabel extends Label {

        private final MethodContext methodContext;
        private int codeIndex;
        private String tag;
        private DynamicLabel proxy;

        private DynamicLabel(MethodContext methodContext, int codeIndex) {
            this(methodContext, codeIndex, null);
        }

        private DynamicLabel(MethodContext methodContext, int codeIndex, String tag) {
            super("(auto)");
            this.methodContext = Objects.requireNonNull(methodContext);
            this.codeIndex = codeIndex;
            this.tag = tag;
        }

        private int getCodeIndex() {
            return codeIndex;
        }

        public String getTag() {
            return tag;
        }

        private void appendTag(String s) {
            if (s != null)
                tag += "_" + s;
            else
                tag = s;
        }

        @Override
        public String getLabel() {
            if (proxy != null)
                return proxy.getLabel();
            if (tag == null)
                return String.format("L%d", codeIndex);
            else
                return String.format("L%d_%s", codeIndex, tag);
        }

        @Override
        public void mark() {
            methodContext.mark(this);
        }

        private void setCodeIndex(int codeIndex) {
            this.codeIndex = codeIndex;
        }

        private void proxyFor(DynamicLabel other) {
            this.proxy = other;
        }
    }

}
