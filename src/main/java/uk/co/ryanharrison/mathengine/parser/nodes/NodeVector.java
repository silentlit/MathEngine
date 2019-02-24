package uk.co.ryanharrison.mathengine.parser.nodes;

import uk.co.ryanharrison.mathengine.Utils;
import uk.co.ryanharrison.mathengine.linearalgebra.Vector;
import uk.co.ryanharrison.mathengine.parser.operators.Determinable;

import java.util.Arrays;
import java.util.function.BiFunction;

public final class NodeVector extends NodeConstant {
    private Node[] values;

    public NodeVector(Node[] values) {
        this.values = values;
    }

    public NodeVector(uk.co.ryanharrison.mathengine.linearalgebra.Vector v) {
        values = new NodeDouble[v.getSize()];

        for (int i = 0; i < values.length; i++) {
            values[i] = new NodeDouble(v.get(i));
        }
    }

    @Override
    public NodeVector applyDeterminable(Determinable deter) {
        NodeConstant[] result = new NodeConstant[values.length];

        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof NodeNumber) {
                result[i] = deter.getResult((NodeNumber) values[i]);
            } else {
                result[i] = ((NodeConstant) values[i]).applyDeterminable(deter);
            }
        }

        return new NodeVector(result);
    }

    @Override
    public int compareTo(NodeConstant cons) {
        double sum = getTransformer().toNodeNumber().doubleValue();

        if (cons instanceof NodeVector) {
            return Double.compare(sum, cons.getTransformer().toNodeNumber().doubleValue());
        } else {
            return new NodeDouble(sum).compareTo(cons);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof NodeVector) {
            return this.toDoubleVector().equals(
                    ((NodeVector) object).toDoubleVector());
        }
        return false;
    }

    public int getSize() {
        return values.length;
    }

    public Node[] getValues() {
        return values;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    private Vector toDoubleVector() {
        NodeConstant[] a = toNodeConstants();

        double[] v = new double[a.length];

        for (int i = 0; i < v.length; i++) {
            v[i] = a[i].getTransformer().toNodeNumber().doubleValue();
        }

        return new Vector(v);
    }

    @Override
    public String toString() {
        return "{ " + Utils.join(values, ", ") + " }";
    }

    @Override
    public NodeTransformer createTransformer() {
        return new NodeVectorTransformer();
    }

    private class NodeVectorTransformer implements NodeTransformer {

        @Override
        public NodeVector toNodeVector() {
            return NodeVector.this;
        }

        @Override
        public NodeMatrix toNodeMatrix() {
            return new NodeMatrix(new Node[][]{ values });
        }

        @Override
        public NodeNumber toNodeNumber() {
            NodeNumber sum = NodeFactory.createZeroNumber();
            for (NodeConstant constant : toNodeConstants()) {
                sum = sum.add(constant.getTransformer().toNodeNumber());
            }
            return sum;
        }
    }

    public NodeVector copy() {
        return new NodeVector(values.clone());
    }

    private NodeConstant[] toNodeConstants() {
        NodeConstant[] results = new NodeConstant[values.length];
        for (int i = 0; i < results.length; i++) {
            results[i] = (NodeConstant) values[i];
        }
        return results;
    }

    private void normalizeVectorSizes(NodeVector b) {
        if (this.values.length == b.values.length)
            return;

        int thisLength = this.values.length;
        int otherLength = b.values.length;

        int longest = Math.max(thisLength, otherLength);

        NodeConstant[] results = new NodeConstant[longest];

        if (thisLength != longest) {
            if (thisLength == 1) {
                for (int i = 0; i < longest; i++) {
                    results[i] = (NodeConstant) this.values[0].copy();
                }
            } else {
                for (int i = 0; i < thisLength; i++)
                    results[i] = (NodeConstant) this.values[i];
                for (int i = thisLength; i < longest; i++)
                    results[i] = NodeFactory.createZeroNumber();
            }

            this.values = results;
        } else {
            if (b.values.length == 1) {
                for (int i = 0; i < longest; i++) {
                    results[i] = (NodeConstant) b.values[0].copy();
                }
            }
            for (int i = 0; i < otherLength; i++)
                results[i] = (NodeConstant) b.values[i];
            for (int i = otherLength; i < longest; i++)
                results[i] = NodeFactory.createZeroNumber();

            b.values = results;
        }
    }


    public NodeVector appyBiFunc(NodeVector b, BiFunction<NodeNumber, NodeNumber, NodeConstant> func) {
        normalizeVectorSizes(b);

        NodeConstant[] results = new NodeConstant[values.length];

        for (int i = 0; i < values.length; i++) {
            results[i] = func.apply(this.values[i].getTransformer().toNodeNumber(), b.values[i].getTransformer().toNodeNumber());
        }

        return new NodeVector(results);
    }
}
