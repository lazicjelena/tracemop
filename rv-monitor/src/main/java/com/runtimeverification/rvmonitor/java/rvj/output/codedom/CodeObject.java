package com.runtimeverification.rvmonitor.java.rvj.output.codedom;

import com.runtimeverification.rvmonitor.java.rvj.output.codedom.analysis.ICodeVisitable;
import com.runtimeverification.rvmonitor.java.rvj.output.codedom.helper.CodeFormatters;
import com.runtimeverification.rvmonitor.java.rvj.output.codedom.helper.ICodeFormatter;
import com.runtimeverification.rvmonitor.java.rvj.output.codedom.helper.ICodeGenerator;

/**
 * This class is the root of all nodes and terminals of ASTs, such as
 * expressions and statements. Subclasses of this class can be used to generate
 * code. At the time of writing this comment, this is solely used to generate
 * Java code.
 *
 * Previously, code is generated by concatenating strings. I replaced such
 * string-based code generation by CodeDOM-based code generation, meaning that
 * RV-Monitor builds an AST, which contains expressions and statements, and then
 * iterates over each node, which naturally yields Java code.
 *
 * A few advantages of the new method are as follows: 1. analysis-ready: the
 * previous method does not give any information about structure of the program.
 * For this reason, it was hard to look back what has been done. Also, this is
 * based on string concatenation, if code is once created, it's impossible to
 * eliminate part of it. In contrast, the new method builds an AST and,
 * therefore, one can eliminate dead code and even remove remove parameters. 2.
 * type-safe: the previous method does not carry type information; the generated
 * string simply contains the final Java code. The new one, in contrast, has a
 * notion of a variable and type. As a result, one can query the type of a
 * variable during code generation, which gives flexibility of the code
 * generation process.
 *
 * @author Choonghwan Lee <clee83@illinois.edu>
 */
public abstract class CodeObject implements ICodeGenerator, ICodeVisitable {
    /**
     * Returns a string representation of this code object for debugging. The
     * sole purpose of this method is to show the resulting code in the
     * debugger, and this method should not be used in your code.
     */
    @Override
    public String toString() {
        ICodeFormatter fmt = CodeFormatters.getDefault();
        this.getCode(fmt);
        return fmt.getCode();
    }
}
