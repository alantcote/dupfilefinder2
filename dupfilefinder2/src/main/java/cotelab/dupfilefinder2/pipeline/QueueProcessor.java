/**
 * 
 */
package cotelab.dupfilefinder2.pipeline;

/**
 * An object that consumes input from one queue and produces output to another.
 */
public interface QueueProcessor {
	/**
	 * Get the input queue.
	 * 
	 * @return the input queue.
	 */
	public PipelineQueue getInputQueue();

	/**
	 * Get the output queue.
	 * 
	 * @return the output queue.
	 */
	public PipelineQueue getOutputQueue();

	/**
	 * Set the input queue.
	 * 
	 * @param queue the input queue.
	 */
	public void setInputQueue(PipelineQueue queue);

	/**
	 * Set the output queue.
	 * 
	 * @param queue the output queue.
	 */
	public void setOutputQueue(PipelineQueue queue);
}
