package MMA;

public interface MarkovState
{
	public double[] getTransitionDistribution();
	public double[] getEmissionDistribution();
	public String getStateName();
}