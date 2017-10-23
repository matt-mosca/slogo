package backend.math_nodes;

import backend.SyntaxNode;

/**
 * @author Ben Schwennesen
 */
public class PiNode implements SyntaxNode {

    @Override
    public double execute() {
        return Math.PI;
    }

}
