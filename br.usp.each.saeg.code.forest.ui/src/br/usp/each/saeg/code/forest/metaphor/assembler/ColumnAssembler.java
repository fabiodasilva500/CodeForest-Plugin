package br.usp.each.saeg.code.forest.metaphor.assembler;

import java.util.*;
import br.usp.each.saeg.code.forest.domain.*;
import br.usp.each.saeg.code.forest.metaphor.*;
import br.usp.each.saeg.code.forest.util.*;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
public class ColumnAssembler {

    private double maxLeft;
    private double maxRight;
    private List<Trunk> trunk = new ArrayList<Trunk>();
    private double maxRadius;

    public ColumnAssembler(List<TreeData> data, ForestRestrictions restrictions) {
        for (TreeData tree : data) {
            trunk.add(new Trunk(tree, restrictions));
        }
        findMax();
    }

    private void findMax() {
        List<Double> left = new ArrayList<Double>();
        List<Double> right = new ArrayList<Double>();
        List<Float> radius = new ArrayList<Float>();
        for (Trunk each : trunk) {
            left.add(each.maxLeftBranchSize());
            right.add(each.maxRightBranchSize());
            radius.add(each.getRadius());
        }
        maxLeft = CollectionUtils.max(left);
        maxRight = CollectionUtils.max(right);
        maxRadius = (double) CollectionUtils.max(radius);
    }

    public double getMaxLeft() {
        return maxLeft;
    }

    public double getMaxRight() {
        return maxRight;
    }

    public double getMaxRadius() {
        return maxRadius;
    }

    public List<Trunk> getTrunk() {
        return trunk;
    }
}
