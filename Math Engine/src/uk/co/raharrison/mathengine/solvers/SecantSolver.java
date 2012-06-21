package uk.co.raharrison.mathengine.solvers;

import uk.co.raharrison.mathengine.Function;

public final class SecantSolver extends RootBracketingMethod
{
	public SecantSolver(Function targetFunction)
	{
		super(targetFunction);
	}

	@Override
	public double solve()
	{
		checkRootFindingParams();

		Function f = this.targetFunction;

		double a = lowerBound;
		double b = upperBound;

		double fb = f.evaluate(b);
		double x = b - (b - a) * fb / (fb - f.evaluate(a));

		int iteration = 1;

		while (iteration <= this.iterations)
		{
			x = b - (b - a) * fb / (fb - f.evaluate(a));
			a = b;
			b = x;
			fb = f.evaluate(b);

			if (Math.abs(fb) < tolerance
					&& convergenceCriteria == ConvergenceCriteria.WithinTolerance)
			{
				return x;
			}

			iteration++;
		}

		if (convergenceCriteria == ConvergenceCriteria.NumberOfIterations)
		{
			return x;
		}

		throw new UnsupportedOperationException(
				"Unable to find the root within specified tolerance after " + iterations
						+ " iterations");
	}

}
