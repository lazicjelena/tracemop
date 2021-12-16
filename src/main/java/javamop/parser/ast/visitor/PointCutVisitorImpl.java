// Copyright (c) 2002-2014 JavaMOP Team. All Rights Reserved.
package javamop.parser.ast.visitor;

import javamop.parser.ast.aspectj.*;

public class PointCutVisitorImpl<R, A> implements PointcutVisitor<R, A> {

	public R visit(PointCut p, A arg){
		return null;
	}
	
	public R visit(ArgsPointCut p, A arg){
		return null;
	}

	public R visit(CombinedPointCut p, A arg){
		return null;
	}

	public R visit(NotPointCut p, A arg){
		PointCut p2 = p.getPointCut().accept(this, arg);

		return new NotPointCut(p.getBeginLine(), p.getBeginColumn(), p2);
	}

	public R visit(ConditionPointCut p, A arg){
		return null;
	}

	public R visit(FieldPointCut p, A arg){
		return null;
	}

	public R visit(MethodPointCut p, A arg){
		return null;
	}

	public R visit(TargetPointCut p, A arg){
		return null;
	}

	public R visit(ThisPointCut p, A arg){
		return null;
	}

	public R visit(CFlowPointCut p, A arg){
		PointCut p2 = p.getPointCut().accept(this, arg);

		return new CFlowPointCut(p.getBeginLine(), p.getBeginColumn(), p.getType(), p2);
	}

	public R visit(IFPointCut p, A arg){
		return null;
	}

	public R visit(IDPointCut p, A arg){
		return null;
	}

	public R visit(WithinPointCut p, A arg){
		return null;
	}

	public R visit(ThreadPointCut p, A arg){
		return null;
	}
	
	public R visit(ThreadNamePointCut p, A arg){
		return null;
	}
	
	public R visit(ThreadBlockedPointCut p, A arg){
		return null;
	}

	public R visit(EndProgramPointCut p, A arg){
		return null;
	}

	public R visit(EndThreadPointCut p, A arg){
		return null;
	}
	
	public R visit(EndObjectPointCut p, A arg){
		return null;
	}

	public R visit(StartThreadPointCut p, A arg){
		return null;
	}
}
