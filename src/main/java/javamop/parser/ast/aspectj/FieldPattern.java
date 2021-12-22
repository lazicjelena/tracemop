// Copyright (c) 2002-2014 JavaMOP Team. All Rights Reserved.
package javamop.parser.ast.aspectj;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;;

public class FieldPattern extends Node {
    
    private final int modifiers;
    private final int not_modifiers;
    private final TypePattern type;
    private final TypePattern owner;
    private final String name;
    
    public FieldPattern(TokenRange tokenRange, int modifiers, int not_modifiers, TypePattern type, TypePattern owner, String name){
        super(tokenRange);
        this.modifiers = modifiers;
        this.not_modifiers = not_modifiers;
        if (type != null)
            this.type = type;
        else
            this.type = new BaseTypePattern(tokenRange, "*");
        
        this.owner = owner;
        this.name = name;
    }
    
    public int getModifiers() { return modifiers; }
    public int getNotModifiers() { return not_modifiers; }
    public TypePattern getType() { return type; }
    public TypePattern getOwner() { return owner; }
    public String getMemberName() { return name; }
    
    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        v.visit(this, arg);
    }
    
    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }
}
