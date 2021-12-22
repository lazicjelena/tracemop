// Copyright (c) 2002-2014 JavaMOP Team. All Rights Reserved.
package javamop.parser.ast.aspectj;

import com.github.javaparser.TokenRange;
import javamop.parser.ast.visitor.PointcutVisitor;
import javamop.parser.astex.ExtNode;

public abstract class PointCut extends ExtNode {
    
    private final String type;
    
    public PointCut(TokenRange tokenRange, String type){
        super(tokenRange);
        this.type = type;
    }
    
    public String getType() { return type; }
    
    public <R, A> R accept(PointcutVisitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }

}
