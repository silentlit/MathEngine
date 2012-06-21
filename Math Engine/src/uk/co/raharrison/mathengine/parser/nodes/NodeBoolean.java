package uk.co.raharrison.mathengine.parser.nodes;

import uk.co.raharrison.mathengine.linearalgebra.Matrix;
import uk.co.raharrison.mathengine.linearalgebra.Vector;

public class NodeBoolean extends NodeConstant implements NodeMath
{
	private boolean value;

	public NodeBoolean(boolean bool)
	{
		this.value = bool;
	}

	@Override
	public NodeConstant add(NodeConstant arg2)
	{
		double d = asDouble();

		if (arg2 instanceof NodeDouble)
		{
			return new NodeDouble(d + ((NodeDouble) arg2).getValue());
		}
		else if (arg2 instanceof NodeMatrix)
		{
			Matrix m = ((NodeMatrix) arg2).asDoubleMatrix();
			Matrix m2 = new Matrix(d);

			m = m2.add(m);

			return new NodeMatrix(m);
		}
		else if (arg2 instanceof NodeVector)
		{
			Vector v = ((NodeVector) arg2).asDoubleVector();
			Vector v2 = new Vector(d);
			v = v2.add(v);

			return new NodeVector(v);
		}
		else
		{
			return new NodeDouble(d + ((NodeBoolean) arg2).asDouble());
		}
	}

	@Override
	public double asDouble()
	{
		return value ? 1 : 0;
	}

	@Override
	public int compareTo(NodeConstant cons)
	{
		if (cons instanceof NodeBoolean || cons instanceof NodeDouble)
			return Double.compare(this.asDouble(), cons.asDouble());

		return cons.compareTo(this);
	}

	@Override
	public NodeConstant divide(NodeConstant arg2)
	{
		double d = asDouble();

		if (arg2 instanceof NodeDouble)
		{
			return new NodeDouble(d / ((NodeDouble) arg2).getValue());
		}
		else if (arg2 instanceof NodeMatrix)
		{
			Matrix m = ((NodeMatrix) arg2).asDoubleMatrix();
			Matrix m2 = new Matrix(d);

			m = m2.divide(m);

			return new NodeMatrix(m);
		}
		else if (arg2 instanceof NodeVector)
		{
			Vector v = ((NodeVector) arg2).asDoubleVector();
			Vector v2 = new Vector(d);
			v = v2.divide(v);

			return new NodeVector(v);
		}
		else
		{
			return new NodeDouble(d / ((NodeBoolean) arg2).asDouble());
		}
	}

	@Override
	public boolean equals(Object object)
	{
		if (object instanceof NodeBoolean || object instanceof NodeDouble)
		{
			return Double.compare(((NodeConstant) object).asDouble(), this.asDouble()) == 0;
		}

		return false;
	}

	public boolean getValue()
	{
		return value;
	}

	@Override
	public int hashCode()
	{
		return Boolean.valueOf(value).hashCode();
	}

	@Override
	public NodeConstant multiply(NodeConstant arg2)
	{
		double d = asDouble();

		if (arg2 instanceof NodeDouble)
		{
			return new NodeDouble(d * ((NodeDouble) arg2).getValue());
		}
		else if (arg2 instanceof NodeMatrix)
		{
			Matrix m = ((NodeMatrix) arg2).asDoubleMatrix();
			Matrix m2 = new Matrix(d);

			m = m2.multiply(m);

			return new NodeMatrix(m);
		}
		else if (arg2 instanceof NodeVector)
		{
			Vector v = ((NodeVector) arg2).asDoubleVector();
			Vector v2 = new Vector(d);
			v = v2.multiply(v);

			return new NodeVector(v);
		}
		else
		{
			return new NodeDouble(d * ((NodeBoolean) arg2).asDouble());
		}
	}

	@Override
	public NodeConstant pow(NodeConstant arg2)
	{
		double d = asDouble();

		if (arg2 instanceof NodeDouble)
		{
			return new NodeDouble(Math.pow(d, ((NodeDouble) arg2).getValue()));
		}
		else if (arg2 instanceof NodeMatrix)
		{
			Matrix m = ((NodeMatrix) arg2).asDoubleMatrix();
			Matrix m2 = new Matrix(d);

			m = m2.pow(m);

			return new NodeMatrix(m);
		}
		else if (arg2 instanceof NodeVector)
		{
			Vector v = ((NodeVector) arg2).asDoubleVector();
			Vector v2 = new Vector(d);
			v = v2.pow(v);

			return new NodeVector(v);
		}
		else
		{
			return new NodeDouble(Math.pow(d, ((NodeBoolean) arg2).asDouble()));
		}
	}

	public void setValue(boolean value)
	{
		this.value = value;
	}

	@Override
	public NodeConstant subtract(NodeConstant arg2)
	{
		double d = asDouble();

		if (arg2 instanceof NodeDouble)
		{
			return new NodeDouble(d - ((NodeDouble) arg2).getValue());
		}
		else if (arg2 instanceof NodeMatrix)
		{
			Matrix m = ((NodeMatrix) arg2).asDoubleMatrix();
			Matrix m2 = new Matrix(d);

			m = m2.subtract(m);

			return new NodeMatrix(m);
		}
		else if (arg2 instanceof NodeVector)
		{
			Vector v = ((NodeVector) arg2).asDoubleVector();
			Vector v2 = new Vector(d);
			v = v2.subtract(v);

			return new NodeVector(v);
		}
		else
		{
			return new NodeDouble(d - ((NodeBoolean) arg2).asDouble());
		}
	}

	@Override
	public String toString()
	{
		return value ? "true" : "false";
	}
}
