package br.usp.each.saeg.code.forest.metaphor;

import java.awt.Font;
import java.util.List;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import org.apache.commons.lang3.StringUtils;

import br.usp.each.saeg.code.forest.domain.BranchData;
import br.usp.each.saeg.code.forest.metaphor.assembler.ForestRestrictions;
import br.usp.each.saeg.code.forest.metaphor.assembler.LeafAssembler;
import br.usp.each.saeg.code.forest.metaphor.assembler.LeafDistribution;
import br.usp.each.saeg.code.forest.metaphor.building.blocks.CodeCone;
import br.usp.each.saeg.code.forest.metaphor.building.blocks.CodeCylinder;
import br.usp.each.saeg.code.forest.metaphor.building.blocks.GroupDataItem;
import br.usp.each.saeg.code.forest.metaphor.util.ImageUtils;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;


/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
public class Branch extends CodeGeometry {

    public static final int PROPORTION = 2;
    public static final float OUTSIDE = .97f;

    private double angle;
    private int signal;
    private int index;

    private LeafDistribution distribution;
    private LeafAssembler assembler;

    private CodeCylinder body;
    private CodeCone tip;

    private Trunk trunk;
    private BranchData data;
    private final ForestRestrictions restrictions;
    BranchGroup bgText;

    public Branch(Trunk trunk, BranchData data, ForestRestrictions restr, int index) {
        appearance = ImageUtils.getBranchAppearance(getMaterial(trunk.getColor()));
        this.index = index;
        this.trunk = trunk;
        this.data = data;
        this.restrictions = restr;

        if (isLeft()) {
            signal = -1;
            angle = Math.toRadians(90);
        } else {
            signal = 1;
            angle = Math.toRadians(270);
        }
        distribution = new LeafDistribution(data, trunk);

        calculateTranslation();
        calculateBranchBody();
        changeAlign();
        createBranch();
        createTip();
        assembler = new LeafAssembler(distribution, translation, trunk, data.getCoveredLeafData(), this, restrictions);
        addInformation();
    }

    private void createBranch() {
        Transform3D tr = getTransform3D();
        tr.setTranslation(translation);
        GroupDataItem item = new GroupDataItem(tr, body);
        groupData.add(item);
        trunk.add(item.getTransformGroup());
    }

    private void createTip() {
        Transform3D tr = getTransform3D();
        Vector3d tipTranslation = new Vector3d(translation);
        tipTranslation.x = (signal * body.getHeight() / 2) + tipTranslation.x + signal * body.getRadius() / 2;
        tr.setTranslation(tipTranslation);

        tip = new CodeCone(body.getRadius(), body.getRadius(), Cone.GENERATE_NORMALS | Cone.GENERATE_TEXTURE_COORDS,  appearance);
        GroupDataItem item = new GroupDataItem(tr, tip);
        groupData.add(item);
        trunk.add(item.getTransformGroup());
    }

    private void calculateBranchBody() {
        body =  new CodeCylinder(trunk.getRadius()/3, (float) distribution.getSize(), Cylinder.GENERATE_NORMALS | Cylinder.GENERATE_TEXTURE_COORDS, appearance);
        body.setCapability(CodeCylinder.ALLOW_CHILDREN_WRITE);
        body.setCapability(CodeCylinder.ALLOW_CHILDREN_EXTEND);
    }

    private void calculateTranslation() {
        double x = signal * (trunk.getRadius()) + signal * (distribution.getSize()/PROPORTION) * OUTSIDE;
        double y = (trunk.getOffset() * trunk.getStep()) + ((trunk.getStep() / 2 - trunk.getRadius() / 4) * index);
        translation = new Vector3d(x, y, 0);
    }

    private void addInformation() {
        body.setGeometry(this);
        tip.setGeometry(this);
    }

    private void changeAlign() {
        translation.y = translation.y + translation.y/(5);
    }

    @Override
    public void changeStatus() {
        if (isEnabled()) {
            disable();
            for (Leaf leaf : assembler.getLeaves()) {
                leaf.disable();
            }
        } else {
            refresh();
        }
    }

    private Transform3D getTransform3D() {
        Transform3D tr = new Transform3D();
        tr.rotZ(angle);
        return tr;
    }

    public boolean isLeft() {
        return index % 2 == 0;
    }

    public double getSize() {
        return body.getHeight() + tip.getHeight();
    }

    public List<Leaf> getLeaves() {
        return assembler.getLeaves();
    }

    public BranchData getData() {
        return data;
    }

    @Override
    public void refresh() {
        if (!containsTerm() || !containsScore()) {
            disable();
            for (Leaf leaf : assembler.getLeaves()) {
                leaf.disable();
            }
            return;
        }
        enable();
        for (Leaf leaf : assembler.getLeaves()) {
            leaf.refresh();
        }
    }

    private boolean containsTerm() {
        if (StringUtils.isBlank(restrictions.getTerm())) {
            return true;
        }
        if (restrictions.isBranches()) {
            if (restrictions.containsTerm(data.getValue())) {
                return true;
            }
        }

        if (restrictions.isLeaves()) {
            for (Leaf leaf : assembler.getLeaves()) {
                if (restrictions.containsTerm(leaf.getData().getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean containsScore() {
        if (restrictions.isBranches()) {
            if (data.isScoreBetween(restrictions.getMinScore(), restrictions.getMaxScore())) {
                return true;
            }
        }

        if (restrictions.isLeaves()) {
            for (Leaf leaf : assembler.getLeaves()) {
                if (leaf.getData().isScoreBetween(restrictions.getMinScore(), restrictions.getMaxScore())) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getSignal() {
        return signal;
    }

    @Override
    public Trunk getTrunk() {
        return trunk;
    }
    
    public void ativarLabel(){
	    Text3D tex = new Text3D();
	    String fontName = data.getName().substring(0, data.getName().lastIndexOf("(")) + " - " + String.format("%.2f", data.getScore());
	    Font font = new Font("Arial", Font.PLAIN, 2);
	    FontExtrusion extrusion = new FontExtrusion( );
	    Font3D font3d = new Font3D(font, extrusion);
	    tex.setFont3D(font3d);
	    tex.setString(fontName);
	    tex.setAlignment(Text3D.ALIGN_CENTER);
	    Shape3D shape = new Shape3D(tex, appearance);
	    bgText =  new BranchGroup();
	    bgText.setCapability(BranchGroup.ALLOW_DETACH);
	    bgText.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	    bgText.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
	    
	    Transform3D trText = new Transform3D();
	    trText.setTranslation(new Vector3d(15, 0, 15));
	    TransformGroup tgText = new TransformGroup();
	    tgText.setTransform(trText);
	    tgText.addChild(shape);
	    bgText.addChild(tgText);
	   	this.getTrunk().body.addChild(bgText);
    	Trunk.bgLabel.add(bgText);
    }
}
